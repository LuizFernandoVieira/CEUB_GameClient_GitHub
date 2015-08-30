package com.luiz.game.gfx.tiles;

import com.luiz.game.Screen;
import com.luiz.game.gfx.Sprite;

public class Tile {

	public int x, y;
	public Sprite sprite;
	
	public static Tile emptyTile = new EmptyTile(Sprite.empty);
	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile water = new WaterTile(Sprite.water);
	
	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x, y, grass);
	}
	
	/********************************
	 * diz se ele pode ou nao
	 * passar pelo tile
	 */
	public boolean solid() {
		return false;
	}	
	
}
