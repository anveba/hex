package main.engine.graphics.model;

/**
 * Represents a vertex in a mesh, one part of three in a triangle.
 * @author Andreas - s214971
 *
 */
public class Vertex {
	public float x, y, z;
	public float u, v;
	public float nX, nY, nZ;
	
	public Vertex(float x, float y, float z, 
			float u, float v, 
			float nX, float nY, float nZ) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.u = u;
		this.v = v;
		this.nX = nX;
		this.nY = nY;
		this.nZ = nZ;
	}
}
