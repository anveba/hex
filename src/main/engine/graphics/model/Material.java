package main.engine.graphics.model;

import main.engine.graphics.Texture;

public class Material {
	public final float aR, aG, aB; //Ambient rgb
	public final float dR, dG, dB; //Diffuse rgb
	public final float sR, sG, sB; //Specular rgb
	
	public final float shininess;
	
	public final Texture diffuseMap;
	public final Texture specularMap;
	
	public Material(
			float aR, float aG, float aB,
			float dR, float dG, float dB,
			float sR, float sG, float sB,
			float shininess,
			Texture diffuseMap, Texture specularMap) {
		this.aR = aR; this.aG = aG; this.aB = aB;
		this.dR = dR; this.dG = dG; this.dB = dB;
		this.sR = sR; this.sG = sG; this.sB = sB;
		this.shininess = shininess;
		this.diffuseMap = diffuseMap;
		this.specularMap = specularMap;
	}
}
