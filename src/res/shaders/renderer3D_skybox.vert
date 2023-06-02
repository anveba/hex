#version 330 core

layout (location = 0) in vec3 a_pos;

out vec3 uv;

uniform mat4 u_view;
uniform mat4 u_proj;

void main() 
{
    vec4 p = u_proj * vec4(mat3(u_view) * a_pos, 1.0);
    gl_Position = p.xyww;
    uv = a_pos;
}