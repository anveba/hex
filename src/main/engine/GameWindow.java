package main.engine;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import main.hex.resources.TextureLibrary;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import main.engine.graphics.*;
import main.engine.input.*;
import main.engine.ui.FrameStack;

/**
 * The backbone of the program. Handles windowing and abstracts the low level
 * operating system details.
 * @author Andreas - s214971
 *
 */
public abstract class GameWindow implements GraphicsContext {

    private long windowHandle;
    private boolean initialised;
    private int viewportWidth, viewportHeight;
    private int framebufferWidth, framebufferHeight;
    private ControlsListener controlsListener;

    protected GameWindow() {
        initialised = false;
    }

    public final void startGame(String name, int width, int height) {
        
        if (initialised)
            throw new EngineException("Game already started");
        
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
        glfwWindowHint(GLFW_SAMPLES, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        

        windowHandle = glfwCreateWindow(width, height, name, NULL, NULL);
        if (windowHandle == NULL)
            throw new EngineException("Failed to create window");

        viewportWidth = width;
        viewportHeight = height;
        glfwSetFramebufferSizeCallback(windowHandle, (window, w, h) -> {
        	framebufferWidth = w;
        	framebufferHeight = h;
            glViewport(0, 0, w, h);
        });
        
        glfwSetWindowSizeCallback(windowHandle, (window, w, h) -> {
            viewportWidth = w;
            viewportHeight = h;
        });
        	
        int[] wPointer = new int[1];
        int[] hPointer = new int[1];
        glfwGetFramebufferSize(windowHandle, wPointer, hPointer);
        framebufferWidth = wPointer[0];
        framebufferHeight = hPointer[0];
        glfwGetWindowSize(windowHandle, wPointer, hPointer);
        viewportWidth = wPointer[0];
        viewportHeight = hPointer[0];
        
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        glfwSetWindowPos(windowHandle, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

        glfwMakeContextCurrent(windowHandle);
        GL.createCapabilities();

        glfwSwapInterval(1);
        
        initialised = true;
        
        controlsListener = new ControlsListener(
                this::setControlsCallback,
                this::setCursorPositionCallback,
                this::setTextInputCallback);

        glfwShowWindow(windowHandle);

        setClearColor(0.0f, 0.0f, 0.0f);
    }

    private void startLoop() {

        begin();

        float timeSinceLast = (float) glfwGetTime();
        float totalTime = 0.0f;

        while (!glfwWindowShouldClose(windowHandle)) {

            float currentTime = (float) glfwGetTime();
            float deltaTime = currentTime - timeSinceLast;
            timeSinceLast = currentTime;
            totalTime += deltaTime;

            update(new TimeRecord(deltaTime, totalTime));

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
            StringBuilder message = new StringBuilder("OpenGL error(s) occurred:\n");
            for (Integer integer : errors) {
                message.append("Code: ").append(integer).append("\n");
            }
            throw new EngineException(message.toString());
        }
    }

    protected abstract void begin();

    protected abstract void update(TimeRecord elapsed);

    protected abstract void draw();

    public final void clear() {
        if (!initialised)
            throw new EngineException("window not initialised");
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    }

    public final void setClearColor(float r, float g, float b) {
        if (!initialised)
            throw new EngineException("window not initialised");
        glClearColor(r, g, b, 0.0f);
    }
    
    public final void closeWindow() {
        if (!initialised)
            throw new EngineException("window not initialised");
        glfwSetWindowShouldClose(windowHandle, true);
    }

    public int getViewportWidth() {
        return viewportWidth;
    }

    public int getViewportHeight() {
        return viewportHeight;
    }
    
    public int getFramebufferWidth() {
        return framebufferWidth;
    }

    public int getFramebufferHeight() {
        return framebufferHeight;
    }
    
    public ControlsListener getControlsListener() { 
        return controlsListener; 
    }
    
    private void setControlsCallback(BiConsumer<Controls, InputType> callback) {
        glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            Controls c;
            InputType t;
            try {
                c = InputMapper.mapGLFWInputToControls(key);
                t = InputMapper.mapGLFWInputTypeToInputType(action);
            }
            catch (EngineException ex) {
                System.out.println("Error in input processing: " + ex.getMessage());
                return;
            }
            callback.accept(c, t);
        });
        
        glfwSetMouseButtonCallback(windowHandle, (window, key, action, mods) -> {
            Controls c;
            InputType t;
            try {
                c = InputMapper.mapGLFWInputToControls(key);
                t = InputMapper.mapGLFWInputTypeToInputType(action);
            }
            catch (EngineException ex) {
                System.out.println("Error in input processing: " + ex.getMessage());
                return;
            }
            callback.accept(c, t);
        });
    }
    
    private void setCursorPositionCallback(BiConsumer<Float, Float> callback) {
        glfwSetCursorPosCallback(windowHandle, (window, rawX, rawY) -> {
            float w = (float)getViewportWidth();
            float h = (float)getViewportHeight();
            float cursorX = 2.0f * ((float)rawX - w / 2.0f) / h;
            float cursorY = 2.0f * (-((float)rawY - h / 2.0f)) / h;
            callback.accept(cursorX, cursorY);
        });
    }
    
    private void setTextInputCallback(Consumer<Character> callback) {
        glfwSetCharCallback(windowHandle, (window, ch) -> {
            callback.accept((char)ch);
        });
    }

}
