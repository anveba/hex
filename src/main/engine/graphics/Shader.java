package main.engine.graphics;

import static org.lwjgl.opengl.GL33.*;

import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import org.lwjgl.BufferUtils;

import main.engine.*;

public class Shader {
    
    //TODO clean up

    private int handle;

    public Shader(String vertex, String fragment) {
        Path vertexPath = Path.of(vertex);
        Path fragmentPath = Path.of(fragment);
        String vertexSource, fragmentSource;
        try {
            vertexSource = Files.readString(vertexPath);
            fragmentSource = Files.readString(fragmentPath);
        } catch (IOException ex) {
            throw new EngineException(ex);
        }

        int vertexHandle = compileShader(vertexSource, GL_VERTEX_SHADER);
        int fragmentHandle = compileShader(fragmentSource, GL_FRAGMENT_SHADER);
        handle = glCreateProgram();
        glAttachShader(handle, vertexHandle);
        glAttachShader(handle, fragmentHandle);
        glLinkProgram(handle);

        glDeleteShader(vertexHandle);
        glDeleteShader(fragmentHandle);

        IntBuffer linkResult = BufferUtils.createIntBuffer(1);
        glGetProgramiv(handle, GL_LINK_STATUS, linkResult);
        if (linkResult.get() == GL_FALSE) {
            IntBuffer messageLength = BufferUtils.createIntBuffer(1);
            glGetProgramiv(handle, GL_INFO_LOG_LENGTH, messageLength);
            String log = glGetProgramInfoLog(handle, messageLength.get());
            throw new EngineException("shader link error: " + log);
        }
    }

    private int compileShader(String src, int type) {
        int shader = glCreateShader(type);
        glShaderSource(shader, src);
        glCompileShader(shader);

        IntBuffer compileResult = BufferUtils.createIntBuffer(1);
        glGetShaderiv(shader, GL_COMPILE_STATUS, compileResult);
        if (compileResult.get() == GL_FALSE) {
            IntBuffer messageLength = BufferUtils.createIntBuffer(1);
            glGetShaderiv(shader, GL_INFO_LOG_LENGTH, messageLength);
            String log = glGetShaderInfoLog(shader, messageLength.get());
            throw new EngineException("shader compile error: " + log);
        }
        return shader;
    }

    @Override
    protected void finalize() {
        glDeleteProgram(handle);
    }

    public void use() {
        glUseProgram(handle);
    }

    private int getLocation(String field) {
        // TODO check uniform validity
        return glGetUniformLocation(handle, field);
    }

    public void setInt(String field, int i) {
        glUniform1i(getLocation(field), i);
    }
}
