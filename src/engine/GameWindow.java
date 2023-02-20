package engine;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;
import java.util.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import engine.graphics.GraphicsContext;

public abstract class GameWindow implements GraphicsContext {

    private long windowHandle;
    private boolean initialised;
    private int viewportWidth, viewportHeight;

    public GameWindow() {
        initialised = false;
    }

    public void startGame(String name, int width, int height) {
        startWindow(name, width, height);
        startLoop();

        glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void startWindow(String name, int width, int height) {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);

        windowHandle = glfwCreateWindow(width, height, name, NULL, NULL);
        if (windowHandle == NULL)
            throw new EngineException("Failed to create the GLFW window");

        glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });

        viewportWidth = width;
        viewportHeight = height;
        glfwSetFramebufferSizeCallback(windowHandle, (window, w, h) -> {
            viewportWidth = w;
            viewportHeight = h;
            glViewport(0, 0, w, h);
        });

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        glfwSetWindowPos(windowHandle, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

        glfwMakeContextCurrent(windowHandle);

        GL.createCapabilities();

        glfwSwapInterval(1);

        initialised = true;

        glfwShowWindow(windowHandle);

        setClearColor(0.0f, 0.0f, 0.0f);
    }

    private void startLoop() {

        begin();

        float timeSinceLast = (float) glfwGetTime();

        while (!glfwWindowShouldClose(windowHandle)) {

            float currentTime = (float) glfwGetTime();
            float deltaTime = currentTime - timeSinceLast;
            timeSinceLast = currentTime;

            update(new TimeRecord(deltaTime));

            draw();

            checkErrors();

            glfwSwapBuffers(windowHandle);

            glfwPollEvents();
        }
    }

    private void checkErrors() {
        List<Integer> errors = new ArrayList<Integer>();
        int error = glGetError();
        while (error != 0) {
            errors.add(error);
            error = glGetError();
        }

        if (errors.size() > 0) {
            String message = "OpenGL error(s) occured:\n";
            for (int i = 0; i < errors.size(); i++) {
                message += "Code: " + errors.get(i) + "\n";
            }
            throw new EngineException(message);
        }
    }

    protected abstract void begin();

    protected abstract void update(TimeRecord elapsed);

    protected abstract void draw();

    public void clear() {
        if (!initialised)
            throw new EngineException("window not initialised");
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    }

    public void setClearColor(float r, float g, float b) {
        if (!initialised)
            throw new EngineException("window not initialised");
        glClearColor(r, g, b, 0.0f);
    }

    public int getViewportWidth() {
        return viewportWidth;
    }

    public int getViewportHeight() {
        return viewportHeight;
    }

}
