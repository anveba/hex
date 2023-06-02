#version 330 core

out vec4 frag_color;

in vec3 uv;

uniform samplerCube u_cubemap;

void main() 
{
    //Assumes no gamma correction later
    frag_color = texture(u_cubemap, uv);
}