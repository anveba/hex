#version 330 core

out vec4 frag_col;

in vec2 uv;
in vec3 unit_normal;
in vec3 frag_pos;
				
//uniform sampler2D u_tex;
uniform vec4 u_col;

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
};

uniform Light u_light;
uniform Material u_material;

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
	
	frag_col = vec4(ambient + diffuse + specular, 1.0);
	
	frag_col.xyz = pow(frag_col.xyz, vec3(1.0 / gamma));
}
