package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class Input {
    private static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private static boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private static double mouseX, mouseY;

    private GLFWKeyCallback keyboard;
    private GLFWCursorPosCallback mouseMove;
    private GLFWMouseButtonCallback mouseButtons;

    public Input() {
        keyboard = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                try {
                    if (action == GLFW.GLFW_PRESS) keys[key] = true;
                    else if (action == GLFW.GLFW_RELEASE) keys[key] = false;
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    System.err.println("ERROR: Keyboard Input out of Bound");
                }
            }
        };

        mouseMove = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mouseX = xpos;
                mouseY = ypos;
            }
        };

        mouseButtons = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                try {
                    if (action == GLFW.GLFW_PRESS) buttons[button] = true;
                    else if (action == GLFW.GLFW_RELEASE) buttons[button] = false;
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    System.err.println("ERROR: Keyboard Input out of Bound");
                }
            }
        };
    }

    public static boolean isKeyDown(int key) {
        return keys[key];
    }

    public static boolean isButtonDown(int button) {
        return buttons[button];
    }

    public void destroy() {
        keyboard.free();
        mouseButtons.free();
        mouseMove.free();
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public GLFWKeyCallback getKeyboardCallback() {
        return keyboard;
    }

    public GLFWCursorPosCallback getMouseMoveCallback() {
        return mouseMove;
    }
    
    public GLFWMouseButtonCallback getMouseButtonCallback() {
        return mouseButtons;
    }
    
    public static void debug() {
        for (int i = 0; i < GLFW.GLFW_KEY_LAST; ++i) {
            System.out.print(keys[i] ? "1" : "0");
        }
        System.out.println("");
        System.out.println("");
    }
}
