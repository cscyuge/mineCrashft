package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class Window {
    private int width, height;
    private String title;
    private long window;
    public int frames=0;
    public long time;
    public Input input;

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
        GLFW.glfwSetWindowPos(window, (videoMode.width()-width)/2, (videoMode.height()-height)/2);
        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwShowWindow(window);
        GLFW.glfwSwapInterval(1);//´¹Ö±Í¬²½£¿£¿£¿
        
        GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonCallback());
        
        
        time=System.currentTimeMillis();
    }
    
    public void update() {
        GLFW.glfwPollEvents();
        frames++;
        if (System.currentTimeMillis() > time+1000) {
            GLFW.glfwSetWindowTitle(window, title+"| FPS: "+frames);
            time = System.currentTimeMillis();
            frames=0;
        }
    }
    
    public void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
    }
    
    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(window);
    }
    
    public void destory() {
        input.destory();
        GLFW.glfwWindowShouldClose(window);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

}
