package main.hex.graphics;

import main.engine.graphics.model.*;

public class Meshes {
	public static Mesh getQuad() {
		Mesh mesh = new Mesh();
		mesh.addVertex(
				new Vertex(
						0.0f, 0.0f, 0.0f,
						0.0f, 0.0f,
						0.0f, 0.0f, 1.0f
				));
		
		mesh.addVertex(
				new Vertex(
						1.0f, 0.0f, 0.0f,
						1.0f, 0.0f,
						0.0f, 0.0f, 1.0f
				));
		
		mesh.addVertex(
				new Vertex(
						1.0f, 1.0f, 0.0f,
						1.0f, 1.0f,
						0.0f, 0.0f, 1.0f
				));
		
		mesh.addVertex(
				new Vertex(
						0.0f, 1.0f, 0.0f,
						0.0f, 1.0f,
						0.0f, 0.0f, 1.0f
				));
		
		mesh.addIndex(0);
		mesh.addIndex(1);
		mesh.addIndex(2);
		mesh.addIndex(2);
		mesh.addIndex(3);
		mesh.addIndex(0);
		
		return mesh;
	}
	
	public static Mesh buildHexTile(float width, float height) {
		Mesh mesh = new Mesh();
		
		float sep = height / 2.0f;
		float span = width / 2.0f;
		
		float spanX = span * 0.866025404f;
		
		//Top face
		addHexFace(mesh, sep, span, 0, 1.0f);
		
		//Bottom face
		addHexFace(mesh, -sep, span, 7, -1.0f);
		
		
		//Bottom-right side quad
		addHexQuad(mesh, 0.0f, span, spanX, span / 2.0f, sep, 14);
		
		//Right side quad
		addHexQuad(mesh, spanX, span / 2.0f, spanX, -span / 2.0f, sep, 18);
		
		//Top-right side quad
		addHexQuad(mesh, spanX, -span / 2.0f, 0.0f, -span, sep, 22);
		
		//Top-left side quad
		addHexQuad(mesh, 0.0f, -span, -spanX, -span / 2.0f, sep, 26);
		
		//Left side quad
		addHexQuad(mesh, -spanX, -span / 2.0f, -spanX, span / 2.0f, sep, 30);
		
		//Bottom-left side quad
		addHexQuad(mesh, -spanX, span / 2.0f, 0.0f, span, sep, 34);
		
		return mesh;
	}

	private static void addHexFace(Mesh mesh, float y, float span, int indexOffset, float normal) {
		float mid = span * 0.5f;
		float c = 0.866025404f; // cos(30)
		float spanX = span * c;
		mesh.addVertex(
				new Vertex(
						0.0f, y, 0.0f,
						0.0f, 0.0f,
						0.0f, normal, 0.0f
				));
		mesh.addVertex(
				new Vertex(
						0.0f, y, span,
						0.0f, span,
						0.0f, normal, 0.0f
				));
		mesh.addVertex(
				new Vertex(
						-spanX, y, mid,
						0.0f, 0.0f,
						0.0f, normal, 0.0f
				));
		mesh.addVertex(
				new Vertex(
						-spanX, y, -mid,
						0.0f, 0.0f,
						0.0f, normal, 0.0f
				));
		mesh.addVertex(
				new Vertex(
						0.0f, y, -span,
						0.0f, 0.0f,
						0.0f, normal, 0.0f
				));
		mesh.addVertex(
				new Vertex(
						spanX, y, -mid,
						0.0f, 0.0f,
						0.0f, normal, 0.0f
				));
		
		mesh.addVertex(
				new Vertex(
						spanX, y, mid,
						0.0f, 0.0f,
						0.0f, normal, 0.0f
				));
		
		mesh.addIndex(0 + indexOffset);
		mesh.addIndex(1 + indexOffset);
		mesh.addIndex(2 + indexOffset);
		
		mesh.addIndex(0 + indexOffset);
		mesh.addIndex(2 + indexOffset);
		mesh.addIndex(3 + indexOffset);
		
		mesh.addIndex(0 + indexOffset);
		mesh.addIndex(3 + indexOffset);
		mesh.addIndex(4 + indexOffset);
		
		mesh.addIndex(0 + indexOffset);
		mesh.addIndex(4 + indexOffset);
		mesh.addIndex(5 + indexOffset);
		
		mesh.addIndex(0 + indexOffset);
		mesh.addIndex(5 + indexOffset);
		mesh.addIndex(6 + indexOffset);
		
		mesh.addIndex(0 + indexOffset);
		mesh.addIndex(6 + indexOffset);
		mesh.addIndex(1 + indexOffset);
	}
	
	private static void addHexQuad(Mesh mesh, 
			float x1, float y1, float x2, float y2, 
			float thickness, int indexOffset) {
		mesh.addVertex(
				new Vertex(
						x1, -thickness, y1,
						0.0f, 0.0f,
						x1 + x2, 0.0f, y1 + y2
				));
		
		mesh.addVertex(
				new Vertex(
						x2, -thickness, y2,
						1.0f, 0.0f,
						x1 + x2, 0.0f, y1 + y2
				));
		
		mesh.addVertex(
				new Vertex(
						x2, thickness, y2,
						0.0f, 0.0f,
						x1 + x2, 0.0f, y1 + y2
				));
		
		mesh.addVertex(
				new Vertex(
						x1, thickness, y1,
						1.0f, 0.0f,
						x1 + x2, 0.0f, y1 + y2
				));
		
		mesh.addIndex(0 + indexOffset);
		mesh.addIndex(1 + indexOffset);
		mesh.addIndex(2 + indexOffset);
		mesh.addIndex(2 + indexOffset);
		mesh.addIndex(3 + indexOffset);
		mesh.addIndex(0 + indexOffset);
		
	}
}