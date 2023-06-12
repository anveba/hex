package main.engine.math;

import java.nio.FloatBuffer;

import main.engine.EngineException;

/**
 * Immutable.
 */
public class Matrix4 {

    private float m00, m01, m02, m03;
    private float m10, m11, m12, m13;
    private float m20, m21, m22, m23;
    private float m30, m31, m32, m33;
    
    private static Matrix4 identity;
    
    public Matrix4(Vector4 col0, Vector4 col1, Vector4 col2, Vector4 col3) {
    	m00 = col0.x;
    	m10 = col0.y;
    	m20 = col0.z;
    	m30 = col0.w;
    	
    	m01 = col1.x;
    	m11 = col1.y;
    	m21 = col1.z;
    	m31 = col1.w;
    	
    	m02 = col2.x;
    	m12 = col2.y;
    	m22 = col2.z;
    	m32 = col2.w;
    	
    	m03 = col3.x;
    	m13 = col3.y;
    	m23 = col3.z;
    	m33 = col3.w;
    }
    
    private Matrix4() {
    	
    }
    
    public static Matrix4 identity() {
    	if (identity == null) {
    		identity = new Matrix4();
    		identity.setIdentity();
    	}
    	return identity;
    }
    
    private void setIdentity() {
		m00 = 1.0f;
		m11 = 1.0f;
		m22 = 1.0f;
		m33 = 1.0f;
		
		m10 = 0.0f;
		m20 = 0.0f;
		m30 = 0.0f;
		
		m01 = 0.0f;
		m21 = 0.0f;
		m31 = 0.0f;
		
		m02 = 0.0f;
		m12 = 0.0f;
		m32 = 0.0f;
		
		m03 = 0.0f;
		m13 = 0.0f;
		m23 = 0.0f;	
    }
    
    public static Matrix4 multiply(Matrix4 mat1, Matrix4 mat2) {
    	if (mat1 == null || mat2 == null)
    		throw new EngineException("Null matrix was given.");
    	
    	Matrix4 res = new Matrix4();
    	
    	res.m00 = mat1.m00 * mat2.m00 + mat1.m01 * mat2.m10 + mat1.m02 * mat2.m20 + mat1.m03 * mat2.m30;
        res.m10 = mat1.m10 * mat2.m00 + mat1.m11 * mat2.m10 + mat1.m12 * mat2.m20 + mat1.m13 * mat2.m30;
        res.m20 = mat1.m20 * mat2.m00 + mat1.m21 * mat2.m10 + mat1.m22 * mat2.m20 + mat1.m23 * mat2.m30;
        res.m30 = mat1.m30 * mat2.m00 + mat1.m31 * mat2.m10 + mat1.m32 * mat2.m20 + mat1.m33 * mat2.m30;

        res.m01 = mat1.m00 * mat2.m01 + mat1.m01 * mat2.m11 + mat1.m02 * mat2.m21 + mat1.m03 * mat2.m31;
        res.m11 = mat1.m10 * mat2.m01 + mat1.m11 * mat2.m11 + mat1.m12 * mat2.m21 + mat1.m13 * mat2.m31;
        res.m21 = mat1.m20 * mat2.m01 + mat1.m21 * mat2.m11 + mat1.m22 * mat2.m21 + mat1.m23 * mat2.m31;
        res.m31 = mat1.m30 * mat2.m01 + mat1.m31 * mat2.m11 + mat1.m32 * mat2.m21 + mat1.m33 * mat2.m31;

        res.m02 = mat1.m00 * mat2.m02 + mat1.m01 * mat2.m12 + mat1.m02 * mat2.m22 + mat1.m03 * mat2.m32;
        res.m12 = mat1.m10 * mat2.m02 + mat1.m11 * mat2.m12 + mat1.m12 * mat2.m22 + mat1.m13 * mat2.m32;
        res.m22 = mat1.m20 * mat2.m02 + mat1.m21 * mat2.m12 + mat1.m22 * mat2.m22 + mat1.m23 * mat2.m32;
        res.m32 = mat1.m30 * mat2.m02 + mat1.m31 * mat2.m12 + mat1.m32 * mat2.m22 + mat1.m33 * mat2.m32;

        res.m03 = mat1.m00 * mat2.m03 + mat1.m01 * mat2.m13 + mat1.m02 * mat2.m23 + mat1.m03 * mat2.m33;
        res.m13 = mat1.m10 * mat2.m03 + mat1.m11 * mat2.m13 + mat1.m12 * mat2.m23 + mat1.m13 * mat2.m33;
        res.m23 = mat1.m20 * mat2.m03 + mat1.m21 * mat2.m13 + mat1.m22 * mat2.m23 + mat1.m23 * mat2.m33;
        res.m33 = mat1.m30 * mat2.m03 + mat1.m31 * mat2.m13 + mat1.m32 * mat2.m23 + mat1.m33 * mat2.m33;
        
        return res;
    }
    
    public static Vector4 multiply(Matrix4 mat, Vector4 vec) {
    	if (mat == null || vec == null)
    		throw new EngineException("Null was given.");
    	
    	float x = mat.m00 * vec.x + mat.m01 * vec.y + mat.m02 * vec.z + mat.m03 * vec.w;
        float y = mat.m10 * vec.x + mat.m11 * vec.y + mat.m12 * vec.z + mat.m13 * vec.w;
        float z = mat.m20 * vec.x + mat.m21 * vec.y + mat.m22 * vec.z + mat.m23 * vec.w;
        float w = mat.m30 * vec.x + mat.m31 * vec.y + mat.m32 * vec.z + mat.m33 * vec.w;
    	
    	return new Vector4(x, y, z, w);
    }
    
    public Matrix4 transpose() {
        Matrix4 res = new Matrix4();

        res.m00 = m00;
        res.m10 = m01;
        res.m20 = m02;
        res.m30 = m03;

        res.m01 = m10;
        res.m11 = m11;
        res.m21 = m12;
        res.m31 = m13;

        res.m02 = m20;
        res.m12 = m21;
        res.m22 = m22;
        res.m32 = m23;

        res.m03 = m30;
        res.m13 = m31;
        res.m23 = m32;
        res.m33 = m33;

        return res;
    }
    
    public static Matrix4 perspective(float verticalFOV, float aspectRatio, float near, float far) {
        Matrix4 perspective = new Matrix4();
        perspective.setIdentity();
        
        //https://gamedev.stackexchange.com/questions/120338/what-does-a-perspective-projection-matrix-look-like-in-opengl/120345
        float invTan = 1.0f / (float)Math.tan(verticalFOV * 0.5f);

        perspective.m00 = invTan / aspectRatio;
        perspective.m11 = invTan;
        perspective.m22 = (far + near) / (near - far);
        perspective.m32 = -1.0f;
        perspective.m23 = (2.0f * far * near) / (near - far);
        perspective.m33 = 0.0f;

        return perspective;
    }
    
    public static Matrix4 translate(float x, float y, float z) {
        Matrix4 translation = new Matrix4();
        translation.setIdentity();

        translation.m03 = x;
        translation.m13 = y;
        translation.m23 = z;

        return translation;
    }
    
    public static Matrix4 rotateAroundAxis(float angle, Vector3 axis) {
    	
        float length = axis.length();
    	
    	if (length == 0.0f || !Float.isFinite(length))
    		throw new EngineException("Invalid vector.");
    	
        Matrix4 rot = new Matrix4();
        rot.setIdentity();
        
        // https://en.wikipedia.org/wiki/Rotation_matrix

        float ca = (float)Math.cos(angle);
        float sa = (float)Math.sin(angle);
        float x = axis.x / length;
        float y = axis.y / length;
        float z = axis.z / length;

        rot.m00 = x * x * (1f - ca) + ca;
        rot.m10 = y * x * (1f - ca) + z * sa;
        rot.m20 = x * z * (1f - ca) - y * sa;
        rot.m01 = x * y * (1f - ca) - z * sa;
        rot.m11 = y * y * (1f - ca) + ca;
        rot.m21 = y * z * (1f - ca) + x * sa;
        rot.m02 = x * z * (1f - ca) + y * sa;
        rot.m12 = y * z * (1f - ca) - x * sa;
        rot.m22 = z * z * (1f - ca) + ca;

        return rot;
    }
    

    public static Matrix4 rotateYawPitchRoll(float yaw, float pitch, float roll) {
    	
        Matrix4 rot = new Matrix4();
        rot.setIdentity();

        // https://en.wikipedia.org/wiki/Rotation_matrix
        
        float ca = (float)Math.cos(roll);
        float sa = (float)Math.sin(roll);
        float cb = (float)Math.cos(yaw);
        float sb = (float)Math.sin(yaw);
        float cg = (float)Math.cos(pitch);
        float sg = (float)Math.sin(pitch);

        rot.m00 = ca * cb;
        rot.m10 = sa * cb;
        rot.m20 = -sb;
        rot.m01 = ca * sb * sg - sa * cg;
        rot.m11 = sa * sb * sg + ca * cg;
        rot.m21 = cb * sg;
        rot.m02 = ca * sb * cg + sa * sg;
        rot.m12 = sa * sb * cg - ca * sg;
        rot.m22 = cb * cg;

        return rot;
    }
    
    public static Matrix4 scale(float x, float y, float z) {
    	
        Matrix4 scale = new Matrix4();
        scale.setIdentity();

        scale.m00 = x;
        scale.m11 = y;
        scale.m22 = z;

        return scale;
    }
    
    
	public void populateBuffer(FloatBuffer buffer) {
		buffer.put(m00).put(m10).put(m20).put(m30);
        buffer.put(m01).put(m11).put(m21).put(m31);
        buffer.put(m02).put(m12).put(m22).put(m32);
        buffer.put(m03).put(m13).put(m23).put(m33);
        buffer.flip();
	}
}
