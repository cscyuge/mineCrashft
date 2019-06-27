package main;

import java.util.Random;

import org.lwjgl.glfw.GLFW;

import engine.graphics.Mesh;
import engine.graphics.Renderer;
import engine.graphics.Vertex;
import engine.io.Input;
import engine.io.Window;
import engine.maths.Vectorf3f;

public class Main implements Runnable {
    public Thread game;
    public static Window window;
    public Renderer renderer;
    public static final int WIDTH = 1280,HEIGHT = 760;
    
    public Mesh mesh = new Mesh(new Vertex[] {
            new Vertex(new Vectorf3f(-0.5f,  0.5f,  0.0f)),
            new Vertex(new Vectorf3f( 0.5f,  0.5f,  0.0f)),
            new Vertex(new Vectorf3f( 0.5f, -0.5f,  0.0f)),
            new Vertex(new Vectorf3f(-0.5f, -0.5f,  0.0f))
            
    },new int[] {
                 0,1,2,
                 0,3,2
            });
    
    public void start() {
        game = new Thread(this, "game");
        game.start();
    }

    public void init() {
        window =new Window(WIDTH,HEIGHT,"Game");
        window.setBackground(0, 1.0f, 0);
        window.create();
        window.setFullScreen(false);
        renderer= new Renderer();
        mesh.create();
        
    }

    public void update() {
        window.update();
    }

    public void render() {
        renderer.renderMesh(mesh);
        window.swapBuffers();
    }

    public void run() {
        init();
        
        while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            update();
            render();
//            if (Input.isKeyDown(GLFW.GLFW_KEY_F11) ) {
//                window.setFullScreen(!window.isFullScreen());
//            }
            if (Input.isKeyDownBeforeAndJustRelease(GLFW.GLFW_KEY_F11) ) {
                window.setFullScreen(!window.isFullScreen());
            }
            
            if (Input.isKeyDown(GLFW.GLFW_KEY_E)) {
                Random rnd = new Random();
                window.setBackground(rnd.nextFloat(),rnd.nextFloat() , rnd.nextFloat());
            }
        }
        window.destroy();
    }

    public static void main(String args[]) {
        new Main().start();
    }
}
