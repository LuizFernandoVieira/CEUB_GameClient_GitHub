package com.luiz.game.entities;

import com.luiz.game.Screen;
import com.luiz.game.gfx.Sprite;
import com.luiz.game.input.Keyboard;

public class Player extends Mob {
	
	private String username;
	private Keyboard input;

	public Player(String username) {
		this.username = username;
	}
	
	public Player(String username, Keyboard input) {
		this.username = username;
		this.input = input;
	}
	
	public void update() {
		int xa=0, ya=0;
		
		if(input!= null) {
			if(input.up) ya--;
			if(input.down) ya++;
			if(input.left) xa--;
			if(input.right) xa++;
		}
		
		if(xa!=0 || ya!=0) move(xa,ya);
	}
	
	public void render(Screen screen) {
		screen.renderPlayer(x, y, Sprite.player);
	}
	
	public String getUsername() {
		return username;
	}
}
