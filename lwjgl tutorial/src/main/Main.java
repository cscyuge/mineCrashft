package main;

import org.lwjgl.glfw.GLFW;

import engine.io.Input;
import engine.io.Window;

public class Main implements Runnable {
    public Thread game;
    public static Window window;
    public static final int WIDTH = 1280,HEIGHT = 760;
    
    public void start() {
        game = new Thread(this, "game");
        game.start();
    }

    public static void init() {
        window =new Window(WIDTH,HEIGHT,"Game");
        window.create();
    }

    public void update() {
        window.update();
    }

    public void render() {
        window.swapBuffers();
    }

    public void run() {
        init();
        while (!window.shouldClose()) {
            update();
            render();
            if (Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) break;
        }
        window.destory();
    }

    public static void main(String args[]) {
        new Main().start();
    }
}
