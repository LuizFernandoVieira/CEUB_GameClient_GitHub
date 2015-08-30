package com.luiz.game.entities;

import com.luiz.game.gfx.Sprite;

public class Mob extends GameObject {

	protected Sprite sprite;
	protected int dir = 0;
	protected boolean moving = false;
	
	/********************************
	 * 0 -> cima
	 * 1 -> dir
	 * 2 -> baixo
	 * 3 -> esq
	 */	
	public void move(int xa, int ya) {
		if(xa>0) dir = 1;
		if(xa<0) dir = 3;
		if(ya>0) dir = 2;
		if(ya<0) dir = 0;
		
		if(!collision()) {
			x += xa;
			y += ya;
		}
	}
	
	public void update() {
		
	}
	
	public void render() {
		
	}
	
	private boolean collision() {
		return false;
	}
}
