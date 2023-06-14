package main.engine.graphics;

import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryStack.stackPush;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import main.engine.*;
import main.engine.math.Matrix4;
import main.engine.math.Vector3;
import main.engine.math.Vector4;

/**
 * Abstracts a shader program, that is, a program that runs on the GPU.
 * @author Andreas - s214971
 *
 */
public class Shader {

    private int handle;

    public Shader(String vertexSource, String fragmentSource) {
        handle = createProgram(vertexSource, fragmentSource);        
    }
    
    private static int createProgram(String vertexSource, String fragmentSource) {
        int vertexHandle = compileShader(vertexSource, GL_VERTEX_SHADER);
        int fragmentHandle = compileShader(fragmentSource, GL_FRAGMENT_SHADER);
        int program = glCreateProgram();
        glAttachShader(program, vertexHandle);
        glAttachShader(program, fragmentHandle);
        glLinkProgram(program);

        glDeleteShader(vertexHandle);
        glDeleteShader(fragmentHandle);
        
        checkLinkStatus(program);
        
        return program;
    }
    
    private static void checkLinkStatus(int program) {
    	IntBuffer linkResult = BufferUtils.createIntBuffer(1);
        glGetProgramiv(program, GL_LINK_STATUS, linkResult);
        if (linkResult.get() == GL_FALSE) {
            IntBuffer messageLength = BufferUtils.createIntBuffer(1);
            glGetProgramiv(program, GL_INFO_LOG_LENGTH, messageLength);
            String log = glGetProgramInfoLog(program, messageLength.get());
            throw new EngineException("Shader link error: " + log);
        }
    }

    private static int compileShader(String src, int type) {
        int shader = glCreateShader(type);
        glShaderSource(shader, src);
        glCompileShader(shader);
        
        checkCompileStatus(shader);
        
        return shader;
    }
    
    private static void checkCompileStatus(int shader) {
        IntBuffer compileResult = BufferUtils.createIntBuffer(1);
        glGetShaderiv(shader, GL_COMPILE_STATUS, compileResult);
        if (compileResult.get() == GL_FALSE) {
            IntBuffer messageLength = BufferUtils.createIntBuffer(1);
            glGetShaderiv(shader, GL_INFO_LOG_LENGTH, messageLength);
            String log = glGetShaderInfoLog(shader, messageLength.get());
            throw new EngineException("Shader compile error: " + log);
        }
    }

    @Override
    protected void finalize() {
    	if (handle != 0)
    		glDeleteProgram(handle);
    }

    public void use() {
        glUseProgram(handle);
    }

    private int getLocation(String field) {
        int loc = glGetUniformLocation(handle, field);
        if (loc == -1)
        	throw new EngineException("No uniform with name: " + field);
        return loc;
    }

    public void setInt(String field, int i) {
        glUniform1i(getLocation(field), i);
    }
    
    public void setVec4(String field, 
    		float v1, float v2, float v3, float v4) {
        glUniform4f(getLocation(field), v1, v2, v3, v4);
    }
    
    public void setVec4(String field, Vector4 v) {
        setVec4(field, v.x, v.y, v.z, v.w);
    }
    
    public void setVec3(String field, 
    		float v1, float v2, float v3) {
        glUniform3f(getLocation(field), v1, v2, v3);
    }
    
    public void setVec3(String field, Vector3 v) {
    	setVec3(field, v.x, v.y, v.z);
    }
    
    public void setMat4(String field, Matrix4 mat) {
    	if (mat == null)
    		throw new EngineException("Matrix was null.");
    	try (MemoryStack stack = stackPush()) {
        	FloatBuffer buffer = stack.mallocFloat(16);
        	mat.populateBuffer(buffer);
        	glUniformMatrix4fv(getLocation(field), false, buffer);
    	}
    }

	public void setFloat(String field, float f) {
		glUniform1f(getLocation(field), f);
	}
}
