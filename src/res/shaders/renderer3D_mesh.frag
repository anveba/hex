#version 330 core

out vec4 frag_col;

in vec2 uv;
in vec3 unit_normal;
in vec3 frag_pos;
				
uniform vec4 u_col;
uniform samplerCube u_skybox;
uniform float u_environment_strength;
uniform mat4 u_view;

struct Light 
{
	vec3 direction;
	
	vec3 ambient;
	vec3 diffuse;
	vec3 specular;
};

struct Material 
{
	vec3 ambient;
    vec3 diffuse;
    vec3 specular;

    sampler2D map_diffuse;
    //sampler2D map_specular;

    float shininess;
    float reflectance;
    float fuzziness;
};

uniform Light u_light;
uniform Material u_material;

// https://math.stackexchange.com/questions/137362/how-to-find-perpendicular-vector-to-another-vector
vec3 perpendicular(vec3 v) 
{
   float sxz = sign(sign(sign(v.x) + 0.5) * (sign(v.z) + 0.5)); 
   float syz = sign(sign(sign(v.y) + 0.5) * (sign(v.z) + 0.5)); 
   
   return vec3(sxz * v.z, syz * v.z, -sxz * v.x - syz * v.y);
}

vec3 sample_environment(vec3 v) 
{
    v = normalize(v);
	float blur_amount = u_material.fuzziness;

    float kernel[9] = float[](
        1, 2, 1,
        2, 4, 2,
        1, 2, 1
    );
    
    vec3 p1 = normalize(perpendicular(v));
    vec3 p2 = cross(v, p1);
    
    vec3 res = vec3(0);
    for (int i = 0; i < 3; i++) 
    {
        for (int j = 0; j < 3; j++) 
        {
            vec2 offset_vec = vec2((i - 1) * blur_amount, (j - 1) * blur_amount);
            vec3 sample_vec = offset_vec.x * p1 + offset_vec.y * p2 + v;
            res += texture(u_skybox, sample_vec).rgb * kernel[i] / 16.0;
        }
    }
    
    return res;
}

void main() 
{
	float gamma = 2.2;
	
	vec3 sampled_diffuse = texture(u_material.map_diffuse, vec2(uv.x, uv.y)).xyz;
	sampled_diffuse = pow(sampled_diffuse, vec3(gamma)) * u_col.xyz;
	vec3 sampled_specular = vec3(1.0);
	vec3 unit_light_dir = normalize(u_light.direction);
	
	vec3 ambient = sampled_diffuse * u_light.ambient * u_material.ambient;
	
	vec3 diffuse = max(dot(-unit_light_dir, unit_normal), 0.0)
        * sampled_diffuse * u_light.diffuse * u_material.diffuse;
	
	vec3 unit_reverse_cam_dir = normalize(-frag_pos);
	vec3 halfway_dir = normalize(-unit_light_dir + unit_reverse_cam_dir);
	vec3 specular = pow(max(dot(unit_normal, halfway_dir), 0.0), u_material.shininess)
        * sampled_specular * u_light.specular * u_material.specular;
	
	vec3 reflection_dir = transpose(mat3(u_view)) * reflect(-unit_reverse_cam_dir, unit_normal);
	
	vec3 env_col = sample_environment(reflection_dir) * u_environment_strength;
	
	env_col = env_col * u_material.reflectance;
	
	frag_col = vec4(ambient + diffuse + specular + env_col, 1.0);
	
	frag_col.xyz = pow(frag_col.xyz, vec3(1.0 / gamma));
}
