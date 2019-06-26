package main;

public class Main implements Runnable{
	public Thread game;
	
	public void start() {
		game =new Thread(this,"game");
		game.start();
	}
	
	public static void init() {
		
		System.out.println("hello");
		
	}

	public void update() {
		System.out.println("updating");
		
	}
	public void render() {
		System.out.println("ring");
		
	}
	
	public void run() {
		init();
		while (true) {
			update();
			render();
		}
	}
	
	public static void main(String args[]) {
		 new Main().start();
		
		
	}
}
