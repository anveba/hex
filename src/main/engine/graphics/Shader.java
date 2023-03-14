package main.engine.graphics;

import static org.lwjgl.opengl.GL33.*;

import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.file.*;
import org.lwjgl.BufferUtils;

import main.engine.*;

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
}
