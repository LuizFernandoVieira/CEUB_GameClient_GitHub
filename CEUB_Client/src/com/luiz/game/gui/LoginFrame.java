package com.luiz.game.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.luiz.game.Facade;

public class LoginFrame extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private static Facade facade = new Facade();
	
	Panel panel = new Panel();
	TextField textField = new TextField(10);
	Button button = new Button("connect");
	
	public LoginFrame() {
		facade = facade.getInstance();

		this.setLayout(new BorderLayout());
		this.setTitle("Title");
	    this.setSize(new Dimension(150, 90));
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    
	    this.add(panel);
	    panel.add(textField, BorderLayout.NORTH);
	    panel.add(button, BorderLayout.SOUTH);
	    
	    button.addActionListener(this);
	    
	    this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		String username = getUsername();
		try {
			handleUsername(username);
		} catch(Exception exep) {
			exep.printStackTrace();
		}
	}
	
	private String getUsername() {
		return textField.getText();
	}
	
	private void handleUsername(String username) {
		this.setBackground(Color.CYAN);
		this.dispose();
		facade.initGame(username);
		facade.startGame();
	}
}
