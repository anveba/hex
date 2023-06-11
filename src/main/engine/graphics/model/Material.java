package main.engine.graphics.model;

import main.engine.EngineException;
import main.engine.graphics.Texture;

/**
 * Represents a material for use in 3D rendering. Immutable.
 * @author andreas
 *
 */
public class Material {
	public final float aR, aG, aB; //Ambient rgb
	public final float dR, dG, dB; //Diffuse rgb
	public final float sR, sG, sB; //Specular rgb
	
	public final float shininess;
	public final float reflectance;
	public final float fuzziness;
	
	public final Texture diffuseMap;
	public final Texture specularMap;
	
	public Material(
			float aR, float aG, float aB,
			float dR, float dG, float dB,
			float sR, float sG, float sB,
			float shininess,
			Texture diffuseMap, Texture specularMap) {
		this(aR, aG, aB, dR, dG, dB, sR, sG, sB, 
				shininess, 0.0f, 0.0f,
				diffuseMap, specularMap);
	}
	
	public Material(
			float aR, float aG, float aB,
			float dR, float dG, float dB,
			float sR, float sG, float sB,
			float shininess, float reflectance, float fuzziness,
			Texture diffuseMap, Texture specularMap) {
		if (diffuseMap == null)
			throw new EngineException("Texture was null");
		this.aR = aR; this.aG = aG; this.aB = aB;
		this.dR = dR; this.dG = dG; this.dB = dB;
		this.sR = sR; this.sG = sG; this.sB = sB;
		this.shininess = shininess;
		this.reflectance = reflectance;
		this.fuzziness = fuzziness;
		this.diffuseMap = diffuseMap;
		this.specularMap = specularMap;
	}
}
