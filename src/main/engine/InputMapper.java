package main.engine;

import static org.lwjgl.glfw.GLFW.*;

import main.engine.input.Controls;
import main.engine.input.InputType;

class InputMapper {
	static Controls mapGLFWInputToControls(int input) {
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
        case GLFW_KEY_LEFT:
            return Controls.LEFT_ARROW;
        case GLFW_KEY_UP:
            return Controls.UP_ARROW;
        case GLFW_KEY_RIGHT:
            return Controls.RIGHT_ARROW;
        case GLFW_KEY_DOWN:
            return Controls.DOWN_ARROW;
        case GLFW_KEY_LEFT_SHIFT:
            return Controls.LEFT_SHIFT;
        case GLFW_KEY_LEFT_CONTROL:
            return Controls.LEFT_CONTROL;
        default:
            throw new EngineException("No corresponding key for GLFW value: " + input);
        }
    }
    
    static InputType mapGLFWInputTypeToInputType(int t) {
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
