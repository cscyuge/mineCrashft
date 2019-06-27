package main;

import java.util.Random;

import org.lwjgl.glfw.GLFW;

import engine.graphics.Mesh;
import engine.graphics.Renderer;
import engine.graphics.Vertex;
import engine.io.Input;
import engine.io.Window;
import engine.maths.Vector3f;

public class Main implements Runnable {
    public Thread game;
    public static Window window;
    public static final int WIDTH = 1280,HEIGHT = 760;
    int cnt = 0;
    
    public Mesh mesh = new Mesh(new Vertex[] {
            new Vertex(new Vector3f(-0.5f,  0.5f, 0.0f)), 
            new Vertex(new Vector3f( 0.5f,  0.5f, 0.0f)), 
            new Vertex(new Vector3f( 0.5f, -0.5f, 0.0f)), 
            new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f))
    }, new int[] {
            0, 1, 2, 
            0, 3, 2
    });
    public Renderer renderer;
    
    public void start() {
        game = new Thread(this, "game");
        game.start();
    }

    public void init() {
        renderer = new Renderer();
        window = new Window(WIDTH, HEIGHT, "Minecrash");
        window.create();
        mesh.create();
        window.setBackground(1.0f, 0f, 0f);
    }

    public void update() {
        window.update();
    }

    public void render() {
        renderer.RenderMesh(mesh);
        window.swapBuffers();
    }

    public void change() {
        System.out.println("NOW HAVING CHANGES");
        Random ran = new Random();
        window.setBackground(ran.nextFloat(), ran.nextFloat(), ran.nextFloat());
    }
    
    public void run() {
        init();
        while (!window.shouldClose()) { 
            if (Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE))
                break;
            if (Input.isKeyDown(GLFW.GLFW_KEY_F11))
                window.setFullscreen(!window.isFullscreen());
            if (Input.isKeyDown(GLFW.GLFW_KEY_ENTER))
                change();
            update();
            render();
        }
        window.destroy();
    }

    public static void main(String args[]) {
        new Main().start();
    }
}
