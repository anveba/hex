#version 330 core

out vec4 frag_col;

in vec2 uv;
				
uniform sampler2D u_tex;
uniform vec4 u_col;

void main() 
{
	float gamma = 2.2;
	
	frag_col = vec4(1.0, 1.0, 1.0, texture(u_tex, vec2(uv.x, uv.y)).r) * u_col;
	
	frag_col.xyz = pow(frag_col.xyz, vec3(1.0 / gamma));
}
