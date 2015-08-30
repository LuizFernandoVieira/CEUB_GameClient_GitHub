package com.luiz.game.level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.luiz.game.Screen;
import com.luiz.game.gfx.tiles.Tile;

public class Level {

	protected int width, height;
	protected int[] tiles;
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new int[width*height];
		initLevel();
	}
	
	public Level(String path) {
		loadLevel(path);
	}
	
	protected void initLevel() {
		
	}
	
	private void loadLevel(String path) {
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			
			String line = br.readLine();
			String[] str = line.split(",");
			
			this.width = (Integer.parseInt(str[0]));
			this.height = (Integer.parseInt(str[1]));
			tiles = new int[width*height];
			
			int lineNumber = 0;
			line = br.readLine();
			while (line != null) {
				str = line.split(",");
				for(int i=0; i<str.length; i++) {
					int tileType = (Integer.parseInt(str[i]));
					tiles[i + (lineNumber*width)] = tileType;
				}
				lineNumber++;
				line = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		
	}
	
	public void time() {
		
	}
	
	/********************************
	 * xScroll seria o minimo do x (o
	 * lugar mais a esquerda da sua janela)
	 */
	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		/********************************
		 * nossos tiles sao 16 entao
		 * quando passamos 2,2 queremos
		 * dizer 16*2,16*2
		 */	
		/********************************
		 * lembrando do gamemaker
		 * x0 seria o ponto mais em cima
		 * a esquerda do tile e x1 seria
		 * o mais abaixo a direitq
		 */		
		/********************************
		 * o 16 e o tamanho de 1 tile, dessa
		 * forma nao fica quele preto sendo
		 * carregado quando andamos
		 */		
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;
		
		for(int y=y0; y<y1; y++) {
			for(int x=x0; x<x1; x++) {
				getTile(x,y).render(x, y, screen);;
			}
		}
	}
	
	public Tile getTile(int x, int y) {
		if(x<0 || y<0 || x>=width || y>=height) return Tile.emptyTile;
		if(tiles[x+y*width] == 1) return Tile.grass;
		else if (tiles[x+y*width] == 2) return Tile.water;
		else return Tile.emptyTile;
	}	
	
}
