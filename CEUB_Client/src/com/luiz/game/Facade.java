package com.luiz.game;

public class Facade {
	
	private static Facade instance;
	
	Game game;

	public Facade getInstance() {
		if(instance == null) {
			instance = new Facade();
		}
		return instance;
	}
	
	public void initGame(String username) {
		game = new Game();
		game.init(username);
	}
	
	public void startGame() {
		game.start();
	}
	
}
