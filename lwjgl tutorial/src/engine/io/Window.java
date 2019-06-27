package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import engine.maths.Vector3f;

public class Window {
    private int width, height;
    private String title;
    private long window;
    public int frames = 0;
    public long time;
    public Input input;
    private GLFWWindowSizeCallback sizeCallback;
    private boolean isResized = false;
    private boolean isFullscreen = false;
    private int[] windowPosX = new int[1];
    private int[] windowPosY = new int[1];
    private Vector3f background = new Vector3f(0.f, 0.f, 0.f);

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void create() {
        if (!GLFW.glfwInit()) {
            System.out.println("GLFW wasn't initialized");
            return;
        }
        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);

        input = new Input();

        if (window == 0) {
            System.out.println("window wasn't created");
            return;
        }
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        windowPosX[0] = (videoMode.width() - width) / 2; 
        windowPosY[0] = (videoMode.height() - height) / 2;
        GLFW.glfwSetWindowPos(window, windowPosX[0], windowPosY[0]);
        GLFW.glfwMakeContextCurrent(window);

        GL.createCapabilities(); // add the ability to render
        
        GLFW.glfwShowWindow(window);
//        GLFW.glfwSwapInterval(1); // make 60 FPS

        createCallbacks();

        time = System.currentTimeMillis();
    }

    public void createCallbacks() {
        sizeCallback = new GLFWWindowSizeCallback() {
            
            @Override
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                isResized = true;
            }
        };

        GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
        GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonCallback());
    }
    
    public void update() {
        if (isResized) {
            GL11.glViewport(0, 0, width, height);
            isResized = false;
        }
        GL11.glClearColor(background.getX(), background.getY(), background.getZ(), 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GLFW.glfwPollEvents();
        ++frames;
        if (System.currentTimeMillis() > time + 1000) {
            GLFW.glfwSetWindowTitle(window, title + " [FPS: " + frames + "]");
            time = System.currentTimeMillis();
            frames = 0;
        }
    }
    
    public boolean isFullscreen() {
        return isFullscreen;
    }
    
    public void setFullscreen(boolean value) {
        isFullscreen = value;
        if (isFullscreen) {
            GLFW.glfwGetWindowPos(window, windowPosX, windowPosY);
            GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
        } else {
            GLFW.glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], width, height, 0);
        }
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(window);
    }
    
    public void setBackground(float r, float g, float b) {
        background.set(r, g, b);
    }

    public void destroy() {
        input.destroy();
        GLFW.glfwWindowShouldClose(window);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
}
