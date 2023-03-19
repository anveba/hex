#version 330 core

out vec4 frag_col;

in vec2 uv;
				
uniform sampler2D u_tex;
uniform vec4 u_col;

void main() 
{
	frag_col = texture(u_tex, vec2(uv.x, uv.y)) * u_col;
}
