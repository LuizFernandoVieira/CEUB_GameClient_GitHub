package com.luiz.game.gui;

import javax.swing.JFrame;

import com.luiz.game.Game;

public class Window extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public Window(Game game) {
		this.setResizable(false);
		this.setTitle("Title");
		this.add(game);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}