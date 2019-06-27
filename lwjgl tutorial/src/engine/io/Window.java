package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import engine.maths.Vectorf3f;


public class Window {
    private int width, height;
    private String title;
    private long window;
    public int frames=0;
    public long time;
    public Input input;
    private Vectorf3f background= new Vectorf3f(0, 0, 0);
    private GLFWWindowSizeCallback sizeCallback;
    private boolean isResized;
    private boolean isFullScreen;
    private int[] windowPosX = new int[1],windowPosY= new int[1];

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
        window = GLFW.glfwCreateWindow(width, height, title, isFullScreen ? GLFW.glfwGetPrimaryMonitor() : 0, 0);
        
        input = new Input();
        
        if (window == 0) {
            System.out.println("window wasn't created");
            return;
        }
        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        
        setWindowPosX((videoMode.width()-width)/2);
        setWindowPosY((videoMode.height()-height)/2);
        
        GLFW.glfwSetWindowPos(window, windowPosX[0], windowPosY[0]);
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        
        GLFW.glfwShowWindow(window);
        GLFW.glfwSwapInterval(1);//´¹Ö±Í¬²½£¿£¿£¿
        
        
        createCallbacks();
        
        time=System.currentTimeMillis();
    }
    
    public void createCallbacks() {
            sizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int w, int h) {
                   width=w;
                   height=h;
                   isResized=true;
            }
        };
        
        GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonCallback());
        GLFW.glfwSetScrollCallback(window, input.getScrollCallback());
        GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
    }
    
    public void update() {
        if (isResized) {
//            GL11.glViewport(0, 0, width, height);
//            GL11.glViewport(100, 100, width, height);
            
            isResized=false;
            
        }
        GL11.glClearColor(background.getX(),background.getY() , background.getZ(), 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT| GL11.GL_DEPTH_BUFFER_BIT);
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
    
    public void destroy() {
        input.destroy();
        sizeCallback.free();
        GLFW.glfwWindowShouldClose(window);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
    
    public void setBackground(float r,float g,float b) {
        background.set(r, g, b);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getWindow() {
        return window;
    }

    public void setWindow(long window) {
        this.window = window;
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public void setFullScreen(boolean isFullScreen) {
        this.isFullScreen = isFullScreen;
        isResized=true;
        if (isFullScreen) {
            GLFW.glfwGetWindowPos(window, windowPosX, windowPosY);
            GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
        }else {
            
            GLFW.glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], width, height, 0);    
        }
    }

    public int getFrames() {
        return frames;
    }

    public long getTime() {
        return time;
    }

    public Input getInput() {
        return input;
    }

    public Vectorf3f getBackground() {
        return background;
    }

    public GLFWWindowSizeCallback getSizeCallback() {
        return sizeCallback;
    }

    public boolean isResized() {
        return isResized;
    }

    public int getWindowPosX() {
        return windowPosX[0];
    }

    public void setWindowPosX(int windowPosX) {
        this.windowPosX[0] = windowPosX;
    }

    public int getWindowPosY() {
        return windowPosY[0];
    }

    public void setWindowPosY(int windowPosY) {
        this.windowPosY[0] = windowPosY;
    }

}
