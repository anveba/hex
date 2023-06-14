package main.engine.graphics;

import main.engine.EngineException;

/**
 * Represents a colour using rgba values. Immutable.
 * @author Andreas - s214971
 *
 */
public class Colour {
	private final float r, g, b, a;
	
	public Colour() {
		this(0.0f, 0.0f, 0.0f, 1.0f);
	}
	
	public Colour(float v) {
		this(v, v, v, 1.0f);
	}
	
	public Colour(float r, float g, float b, float a) {
		this.r = r; this.g = g; this.b = b; this.a = a;
	}
	
	public float r() { return r; }
	public float g() { return g; }
	public float b() { return b; }
	public float a() { return a; }
	
	public static final Colour White = new Colour(1.0f, 1.0f, 1.0f, 1.0f);
	public static final Colour Grey = new Colour(0.5f, 0.5f, 0.5f, 1.0f);
	public static final Colour LightGrey = new Colour(0.75f, 0.75f, 0.75f, 1.0f);
	public static final Colour Black = new Colour(0.0f, 0.0f, 0.0f, 1.0f);
	public static final Colour Red = new Colour(1.0f, 0.0f, 0.0f, 1.0f);
	public static final Colour Pink = new Colour(1.0f, 0.2f, 0.9f, 1.0f);
	public static final Colour Purple = new Colour(0.25f, 0.05f, 0.55f, 1.0f);
	public static final Colour Blue = new Colour(0.0f, 0.0f, 1.0f, 1.0f);
	public static final Colour Aqua = new Colour(0.0f, 0.8f, 0.7f, 1.0f);
	public static final Colour Green = new Colour(0.0f, 1.0f, 0.0f, 1.0f);
	public static final Colour Yellow = new Colour(1.0f, 1.0f, 0.0f, 1.0f);
	public static final Colour Orange = new Colour(1.0f, 0.15f, 0.0f, 1.0f);

	public static Colour add(Colour c1, Colour c2) {
		if (c1 == null || c2 == null)
			throw new EngineException("Null given as argument");
		return new Colour(
				c1.r() + c2.r(),
				c1.g() + c2.g(),
				c1.b() + c2.b(),
				c1.a() + c2.a()
				);
	}
	
	public static Colour multiply(Colour c1, Colour c2) {
		if (c1 == null || c2 == null)
			throw new EngineException("Null given as argument");
		return new Colour(
				c1.r() * c2.r(),
				c1.g() * c2.g(),
				c1.b() * c2.b(),
				c1.a() * c2.a()
				);
	}
}


