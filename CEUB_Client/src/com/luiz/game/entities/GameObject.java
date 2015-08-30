package com.luiz.game.entities;

import com.luiz.game.Screen;

public class GameObject {
	public int x = 100, y=100;
	private boolean removed = false;
	
	public void update() {
		
	}
	
	public void render(Screen screen) {
		
	}
	
	public void remove() {
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}	
}
