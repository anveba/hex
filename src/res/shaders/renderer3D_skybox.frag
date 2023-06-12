#version 330 core

out vec4 frag_color;

in vec3 uv;

uniform samplerCube u_cubemap;
uniform float u_strength;

void main() 
{
    float gamma = 2.2; 
    frag_color = texture(u_cubemap, uv) * u_strength;
    frag_color.xyz = pow(frag_color.xyz, vec3(1.0 / gamma));
}