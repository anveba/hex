package main.engine.graphics.model;

import java.util.*;

import main.engine.EngineException;

/**
 * Represents a 3D mesh, that is, a collection of triangles.
 * @author Andreas - s214971
 *
 */
public class Mesh {
	
	private List<Vertex> vertices;
	private List<Integer> indices;
	
	public Mesh() {
		vertices = new ArrayList<Vertex>();
		indices = new ArrayList<Integer>();
	}
	
	public void addVertex(Vertex vert) {
		if (vert == null)
			throw new EngineException("Vertex was null");
		vertices.add(vert);
	}
	
	public void addIndex(int index) {
		if (index < 0)
			throw new EngineException("Index was negative");
		indices.add(index);
	}
	
	public float[] getVertexBuffer() {
		float[] raw = new float[vertices.size() * 8];
		for (int i = 0; i < vertices.size(); i++) {
			var v = vertices.get(i);
			raw[i * 8 + 0] = v.x;
			raw[i * 8 + 1] = v.y;
			raw[i * 8 + 2] = v.z;
			raw[i * 8 + 3] = v.u;
			raw[i * 8 + 4] = v.v;
			raw[i * 8 + 5] = v.nX;
			raw[i * 8 + 6] = v.nY;
			raw[i * 8 + 7] = v.nZ;
		}
		return raw;
	}
	
	public int[] getIndexBuffer() {
		return indices.stream().mapToInt((i) -> i).toArray();
	}

	public int vertexCount() {
		return vertices.size();
	}
}
