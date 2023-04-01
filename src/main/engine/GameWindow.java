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

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import main.engine.graphics.*;
import main.engine.input.*;
import main.engine.ui.FrameStack;

public abstract class GameWindow implements GraphicsContext {

    private long windowHandle;
    private boolean initialised;
    private int viewportWidth, viewportHeight;
    private int framebufferWidth, framebufferHeight;
    private ControlsListener controlsListener;

    protected GameWindow() {
        initialised = false;
    }

    public void startGame(String name, int width, int height) {
        
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
    
    public void closeWindow() {
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
                c = mapGLFWInputToControls(key);
                t = mapGLFWInputTypeToInputType(action);
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
                c = mapGLFWInputToControls(key);
                t = mapGLFWInputTypeToInputType(action);
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
    
    private Controls mapGLFWInputToControls(int input) {
        switch(input) {
        case GLFW_KEY_Q:
            return Controls.Q;
        case GLFW_KEY_W:
            return Controls.W;
        case GLFW_KEY_E:
            return Controls.E;
        case GLFW_KEY_R:
            return Controls.R;
        case GLFW_KEY_T:
            return Controls.T;
        case GLFW_KEY_Y:
            return Controls.Y;
        case GLFW_KEY_U:
            return Controls.U;
        case GLFW_KEY_I:
            return Controls.I;
        case GLFW_KEY_O:
            return Controls.O;
        case GLFW_KEY_P:
            return Controls.P;
        case GLFW_KEY_A:
            return Controls.A;
        case GLFW_KEY_S:
            return Controls.S;
        case GLFW_KEY_D:
            return Controls.D;
        case GLFW_KEY_F:
            return Controls.F;
        case GLFW_KEY_G:
            return Controls.G;
        case GLFW_KEY_H:
            return Controls.H;
        case GLFW_KEY_J:
            return Controls.J;
        case GLFW_KEY_K:
            return Controls.K;
        case GLFW_KEY_L:
            return Controls.L;
        case GLFW_KEY_Z:
            return Controls.Z;
        case GLFW_KEY_X:
            return Controls.X;
        case GLFW_KEY_C:
            return Controls.C;
        case GLFW_KEY_V:
            return Controls.V;
        case GLFW_KEY_B:
            return Controls.B;
        case GLFW_KEY_N:
            return Controls.N;
        case GLFW_KEY_M:
            return Controls.M;
        case GLFW_KEY_SPACE:
            return Controls.SPACE;
        case GLFW_KEY_BACKSPACE:
            return Controls.BACKSPACE;
        case GLFW_KEY_ENTER:
            return Controls.ENTER;
        case GLFW_MOUSE_BUTTON_1:
            return Controls.LEFT_MOUSE;
        case GLFW_MOUSE_BUTTON_2:
            return Controls.RIGHT_MOUSE;
        case GLFW_KEY_ESCAPE:
            return Controls.ESCAPE;
        default:
            throw new EngineException("No corresponding key for GLFW value: " + input);
        }
    }
    
    private InputType mapGLFWInputTypeToInputType(int t) {
        switch (t) {
        case GLFW_PRESS:
            return InputType.PRESSED;
        case GLFW_RELEASE:
            return InputType.RELEASED;
        case GLFW_REPEAT:
            return InputType.REPEAT;
            default:
                throw new EngineException("No corresponding input type for GLFW value: " + t);
        }
    }

}
