//Author: Andreas - s214971

#version 330 core

layout (location = 0) in vec2 a_pos;
layout (location = 1) in vec2 a_uv;

out vec2 uv;

uniform mat4 u_model;
				
void main() 
{
	gl_Position = u_model * vec4(a_pos, 0.0, 1.0);
	uv = a_uv;
}