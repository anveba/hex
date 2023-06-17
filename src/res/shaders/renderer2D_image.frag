//Author: Andreas - s214971

#version 330 core

out vec4 frag_col;

in vec2 uv;
				
uniform sampler2D u_tex;
uniform vec4 u_col;

void main() 
{
	float gamma = 2.2;
	vec4 sampled = texture(u_tex, vec2(uv.x, uv.y));
	sampled.xyz = pow(sampled.xyz, vec3(gamma));
	
	frag_col = sampled * u_col;
	
	frag_col.xyz = pow(frag_col.xyz, vec3(1.0 / gamma));
}
