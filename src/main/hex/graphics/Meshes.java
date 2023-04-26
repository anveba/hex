package main.hex.graphics;

import main.engine.graphics.model.*;
import main.engine.math.Vector3;

public class Meshes {
	public static Mesh buildParallelepipedMesh(Vector3 i, Vector3 j, Vector3 k) {
		
		Mesh mesh = new Mesh();
		
		addParallelepipedQuadToMesh(mesh, 
				new Vector3(), 
				i, 
				Vector3.add(i, j), 
				j,
				false
				);
		
		addParallelepipedQuadToMesh(mesh, 
				Vector3.add(new Vector3(), k), 
				Vector3.add(i, k), 
				Vector3.add(Vector3.add(i, j), k), 
				Vector3.add(j, k),
				true
				);
		
		addParallelepipedQuadToMesh(mesh, 
				new Vector3(), 
				k, 
				Vector3.add(k, i), 
				i,
				false
				);
		
		addParallelepipedQuadToMesh(mesh, 
				j, 
				Vector3.add(j, k), 
				Vector3.add(Vector3.add(k, i), j), 
				Vector3.add(i, j),
				true
				);
		
		addParallelepipedQuadToMesh(mesh, 
				new Vector3(), 
				j, 
				Vector3.add(j, k), 
				k,
				false
				);
		
		addParallelepipedQuadToMesh(mesh, 
				i, 
				Vector3.add(j, i), 
				Vector3.add(Vector3.add(j, k), i), 
				Vector3.add(k, i),
				true
				);
		
		return mesh;
	}
	
	private static void addParallelepipedQuadToMesh(Mesh mesh, Vector3 p1, Vector3 p2, 
			Vector3 p3, Vector3 p4, boolean flipNormal) {
		Vector3 normal = Vector3.cross(
				Vector3.add(p1,
						Vector3.multiply(-1.0f, p2)),
				Vector3.add(p1,
						Vector3.multiply(-1.0f, p3))
				);
		if (flipNormal)
			normal = Vector3.multiply(-1.0f, normal);
		int offset = mesh.vertexCount();
		mesh.addVertex(
				new Vertex(
						p1.x, p1.y, p1.z,
						0.0f, 0.25f,
						normal.x, normal.y, normal.z
				));
		
		mesh.addVertex(
				new Vertex(
						p2.x, p2.y, p2.z,
						1.0f, 0.25f,
						normal.x, normal.y, normal.z
				));
		
		mesh.addVertex(
				new Vertex(
						p3.x, p3.y, p3.z,
						1.0f, 0.75f,
						normal.x, normal.y, normal.z
				));
		
		mesh.addVertex(
				new Vertex(
						p4.x, p4.y, p4.z,
						0.0f, 0.75f,
						normal.x, normal.y, normal.z
				));
		
		mesh.addIndex(0 + offset);
		mesh.addIndex(1 + offset);
		mesh.addIndex(2 + offset);
		mesh.addIndex(2 + offset);
		mesh.addIndex(3 + offset);
		mesh.addIndex(0 + offset);
	}
	
	public static Mesh buildQuadMesh(Vector3 p1, Vector3 p2, 
			Vector3 p3, Vector3 p4, boolean flipNormal) {
		
		Mesh mesh = new Mesh();
		
		Vector3 normal = Vector3.cross(
				Vector3.add(p1,
						Vector3.multiply(-1.0f, p2)),
				Vector3.add(p1,
						Vector3.multiply(-1.0f, p3))
				);
		if (flipNormal)
			normal = Vector3.multiply(-1.0f, normal);
		mesh.addVertex(
				new Vertex(
						p1.x, p1.y, p1.z,
						0.0f, 0.25f,
						normal.x, normal.y, normal.z
				));
		
		mesh.addVertex(
				new Vertex(
						p2.x, p2.y, p2.z,
						1.0f, 0.25f,
						normal.x, normal.y, normal.z
				));
		
		mesh.addVertex(
				new Vertex(
						p3.x, p3.y, p3.z,
						1.0f, 0.75f,
						normal.x, normal.y, normal.z
				));
		
		mesh.addVertex(
				new Vertex(
						p4.x, p4.y, p4.z,
						0.0f, 0.75f,
						normal.x, normal.y, normal.z
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
						0.5f, 0.5f,
						0.0f, normal, 0.0f
				));
		mesh.addVertex(
				new Vertex(
						0.0f, y, span,
						0.5f, 0.0f,
						0.0f, normal, 0.0f
				));
		mesh.addVertex(
				new Vertex(
						-spanX, y, mid,
						0.0f, 0.25f,
						0.0f, normal, 0.0f
				));
		mesh.addVertex(
				new Vertex(
						-spanX, y, -mid,
						0.0f, 0.75f,
						0.0f, normal, 0.0f
				));
		mesh.addVertex(
				new Vertex(
						0.0f, y, -span,
						0.5f, 1.0f,
						0.0f, normal, 0.0f
				));
		mesh.addVertex(
				new Vertex(
						spanX, y, -mid,
						1.0f, 0.75f,
						0.0f, normal, 0.0f
				));
		
		mesh.addVertex(
				new Vertex(
						spanX, y, mid,
						1.0f, 0.25f,
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
						0.0f, 0.25f,
						x1 + x2, 0.0f, y1 + y2
				));
		
		mesh.addVertex(
				new Vertex(
						x2, -thickness, y2,
						1.0f, 0.25f,
						x1 + x2, 0.0f, y1 + y2
				));
		
		mesh.addVertex(
				new Vertex(
						x2, thickness, y2,
						1.0f, 0.75f,
						x1 + x2, 0.0f, y1 + y2
				));
		
		mesh.addVertex(
				new Vertex(
						x1, thickness, y1,
						0.0f, 0.75f,
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
