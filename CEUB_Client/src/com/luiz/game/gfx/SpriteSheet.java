package com.luiz.game.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	public final int SIZE;
	public int[] pixels;
	
	/********************************
	 * o path fica meio esquisito pq
	 * foi configurado em propriedades
	 * javabuildpath libraries de tal
	 * forma que img seja um class folder,
	 * logo, o acesso é direto, nao
	 * precisa passa img/nomedaimagem
	 */
//	public static SpriteSheet tiles = new SpriteSheet("/teste_branco.png", 128);
	public static SpriteSheet empty = new SpriteSheet("/empty.png", 16);
	public static SpriteSheet player = new SpriteSheet("/play.png", 16);
	public static SpriteSheet grass = new SpriteSheet("/grass.png", 16);
	public static SpriteSheet water = new SpriteSheet("/water.png", 16);
	
	public SpriteSheet(String path, int size) {
		this.path = path;
		this.SIZE = size;
		pixels = new int[SIZE*SIZE];
		load();
	}
	
	private void load() {
		try {
			/********************************
			 * cria um buffer para a imagem e 
			 * seta as informacoes de image
			 * como as informacoes da outra 
			 * imagem que esta no path
			 */
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			/********************************
			 * ponto inicial x
			 * ponto inicial y
			 * width
			 * height
			 * onde queremos guardar os pixels rgb
			 * offset de onde comeca a scanear
			 * de que forma queremos escanear (no caso queremos percorrer horizontalmente) (width)
			 */
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
