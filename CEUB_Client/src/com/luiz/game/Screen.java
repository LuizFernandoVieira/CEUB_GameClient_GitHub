package com.luiz.game;

import java.util.Random;

import com.luiz.game.gfx.Sprite;
import com.luiz.game.gfx.tiles.Tile;

public class Screen {

	public int width;
	public int height;
	public int[] pixels;
	public final int MAP_SIZE = 64;
	
	public int xOffset, yOffset;
	
	
	/********************************
	 * o 64 vem da quantidade de tiles
	 * que temos no nosso mapa, ou seja,
	 * 64 altura 64 largura
	 */
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	
	/********************************
	 * gerador de numeros aleatorios
	 */
	private Random random = new Random();
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		
		/********************************
		 * 64 eh a quantidade de tiles
		 */
		for(int i = 0; i< MAP_SIZE * MAP_SIZE; i++) {
			/********************************
			 * vai gerar um inteiro em hexadecimal
			 * a partir do branco, ou seja, do ffffff
			 * ate o ultimo (preto) 000000
			 * eh como se fosse a range que ele pode
			 * gerar o numero, sempre comeca em 0 ate
			 * o numero passado, no caso o numero
			 * era ffffff ou seja a ultima cor(preta)
			 */
			tiles[i] = random.nextInt(0xffffff);
		}
		
	}
	
	public void clear() {
		for(int i=0; i<pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	/********************************
	 * depois de construir a renderTile
	 * comentamos esse render
	 */
//	public void render(int xOffset, int yOffset) {
//		for(int y=0; y<height; y++) {
//			int real_y = y + yOffset;
//			if(real_y<0 || real_y>=height) continue;
////			int yy = y + yOffset;
////			if(yy<0 || yy>=height) break;
//			for(int x=0; x<width; x++) {
//				int real_x = x + xOffset;
//				if(real_x < 0 || real_x >= width) continue;
////				int xx = x + xOffset;
////				if(xx<0 || xx>=width) break;
//				/********************************
//				 * aqui cada tile tem 32 pixels
//				 * poderiamos escrever x/32 mas 
//				 * vamos escrever x >> 5 que eh
//				 * basicamente x shiftado 5 bits
//				 * 2 a 5 corresponde a 32
//				 */
//				/********************************
//				 * operador & funciona da seguinte forma,
//				 * quando xx>>4 se torna maior que 64 (0-63)
//				 * ele retorna ao zero
//				 */
////				int tileIndex = ((xx>>4) & (MAP_SIZE-1)) + ((yy>>4) & (MAP_SIZE-1)) * MAP_SIZE;
////				int tileIndex = (xx/32) + (yy/32) * 64;
//				/********************************
//				 * 0x indica que o proximo numero
//				 * sera uma hexadecimal
//				 * antes, quando renderizavamos
//				 * pixel a pixel tinhamos essas 
//				 * duas linhas, agora teremos as
//				 * linhas correspondentes aos tiles
//				 */
//				//pixels[20+20*width] = 0xff00ff;
//				//pixels[x+y*width] = 0xff00ff;
//				
//				//pixels[x+y*width] = tiles[tileIndex];
//				pixels[real_x+real_y*width] = Sprite.grass.pixels[(x&15) + (y&15) * Sprite.grass.SIZE];
//			}
//		}
//	}
	
	public void renderTile(int xp, int yp, Tile tile) {
		/********************************
		 * xp e yp vao compensar o offset
		 * quando nosso player andar e a 
		 * camera mudar
		 */
		xp -= xOffset;
		yp -= yOffset;
		for(int y=0; y<tile.sprite.SIZE; y++) {
			int ya= y + yp;
			for(int x=0; x<tile.sprite.SIZE; x++) {
				int xa= x+ xp;
				if(xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya>=height) break;
				/********************************
				 * gambiarra pra nao crashar quando
				 * andamos para esquerda, nao queremos
				 * um array index out of bounds
				 */
				if(xa < 0) xa = 0;
				/********************************
				 * do lado direito da igualdade nao
				 * usamos xa ya pois estamos nos 
				 * referindo ao sprite em si enquanto
				 * do lado esquerda estamos nos referindo
				 * ao inicio do sprite na scene
				 * resumindo, a imagem real do tile
				 * nao eh offsetada, mas sim sua localizacao
				 * na tela
				 */
				pixels[xa+ya*width] = tile.sprite.pixels[x+y*tile.sprite.SIZE];
			}
		}
	}
	
	public void renderPlayer(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for(int y=0; y<sprite.SIZE; y++) {
			int ya= y + yp;
			for(int x=0; x<sprite.SIZE; x++) {
				int xa = x + xp;
				if(xa<-sprite.SIZE || xa>=width || ya<0 || ya>=height) break;
				if(xa < 0) xa = 0;
				//nao renderizar a transparencia (0xff) (aaaaaa é o fundo da imagem)
				int col = sprite.pixels[x+y*sprite.SIZE];
				if(col != 0xffaaaaaa)
					pixels[xa+ya*width] = col;
			}
		}
	}
	
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	
}
