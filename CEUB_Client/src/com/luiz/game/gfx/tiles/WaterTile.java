package com.luiz.game.gfx.tiles;

import com.luiz.game.Screen;
import com.luiz.game.gfx.Sprite;

public class WaterTile extends Tile {

	public WaterTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}		
	
}
