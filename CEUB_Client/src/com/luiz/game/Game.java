package com.luiz.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.luiz.game.client.Client;
import com.luiz.game.client.packets.Packet00Login;
import com.luiz.game.entities.Player;
import com.luiz.game.gui.Window;
import com.luiz.game.input.Keyboard;
import com.luiz.game.input.WindowHandler;
import com.luiz.game.level.Level;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static int width = 200;
	public static int height = 200;
	public static int scale = 3;
	
	private Thread thread;
	private boolean running = false;
	
	private BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Window frame;
	public WindowHandler windowHandler;
	private Screen screen;
	public Keyboard key;
	private Level level;
	
	public String username;
	public Client client;
	
	public Game() {
		Dimension size = new Dimension(width*scale, height*scale);
		this.setPreferredSize(size);
		
		screen = new Screen(width,height);
		frame = new Window(this);
		frame.add(this);
		windowHandler = new WindowHandler(this);
		key = new Keyboard();
		level = new Level("level/level.txt");
		
		addKeyListener(key);
	}
	
	public void init(String username) {
		this.username = username;
		client = new Client("localhost");
		client.initPlayer(new Player(username,key));
		client.start();
		Packet00Login loginPacket = new Packet00Login(username);
		loginPacket.writeData(client);
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		
		while(running) {
			
			long now = System.nanoTime();
			delta += (now-lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(updates+"/"+frames);
				updates = 0;
				frames = 0;
			}
			
		}
	}
	
	public void update() {
		key.update();
		
		level.update();
		client.getPlayer().update();
		
		for(int i=0; i<client.connectedPlayers.size(); i++) {
			client.connectedPlayers.get(i).update();
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		
		int xScroll = client.getPlayer().x - screen.width/2;
		int yScroll = client.getPlayer().y - screen.height/2;
		
		level.render(xScroll, yScroll, screen);
		client.getPlayer().render(screen);
		
		for(int i=0; i<client.connectedPlayers.size(); i++) {
			client.connectedPlayers.get(i).render(screen);
		}
		
		for(int i=0; i<pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
}
