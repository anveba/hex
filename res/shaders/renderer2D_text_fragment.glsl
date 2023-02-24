#version 330 core

out vec4 frag_col;

in vec2 uv;
				
uniform sampler2D u_tex;

void main() 
{
	frag_col = vec4(1.0, 1.0, 1.0, texture(u_tex, vec2(uv.x, uv.y)).r);
}
