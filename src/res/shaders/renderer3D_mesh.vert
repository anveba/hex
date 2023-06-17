//Author: Andreas - s214971

#version 330 core

layout (location = 0) in vec3 a_pos;
layout (location = 1) in vec2 a_uv;
layout (location = 2) in vec3 a_normal;

out vec2 uv;
out vec3 unit_normal;
out vec3 frag_pos;

uniform mat4 u_model;
uniform mat4 u_view;
uniform mat4 u_proj;
				
void main() 
{
	gl_Position = u_proj * u_view * u_model * vec4(a_pos, 1.0);
	uv = a_uv;
	unit_normal = normalize(vec3(u_view * u_model * vec4(a_normal, 0.0)));
	frag_pos = vec3(u_view * u_model * vec4(a_pos, 1.0));
}