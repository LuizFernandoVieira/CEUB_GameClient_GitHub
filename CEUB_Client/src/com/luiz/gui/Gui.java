package com.luiz.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.luiz.client.Client;
import com.luiz.client.packets.Packet00Login;
import com.luiz.client.packets.Packet01Disconnect;

public class Gui extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	Client client;
	
	public boolean clientOn = false;
	
	public Gui() {
		
		this.setLayout(new BorderLayout());
		this.setTitle("Title");
	    this.setSize(150, 90);
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    
	    Panel panel = new Panel();
	    this.add(panel);
	    
	    TextField textField = new TextField(10);
	    panel.add(textField, BorderLayout.NORTH);
	    
	    Button button = new Button("connect");
	    panel.add(button, BorderLayout.SOUTH);
	    button.addActionListener(new UsernameHandler(textField, this));
	    button.addActionListener(this);
	    
	    this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(!clientOn) {
			clientOn = true;
			this.setBackground(Color.CYAN);
		} else {
			clientOn = false;
			this.setBackground(Color.LIGHT_GRAY);
		}
	}
}

class UsernameHandler implements ActionListener {
	
	TextField tf;
	String username;
	Client client;
	Gui gui;

	public UsernameHandler(TextField tf, Gui gui) {
		this.username = tf.getText();
		this.tf = tf;
		this.gui = gui;
	}
	
	public void actionPerformed(ActionEvent e) {
		this.username = this.tf.getText();
		if(!gui.clientOn) {
			client = new Client("localhost");
			client.start();
			Packet00Login loginPacket = new Packet00Login(username);
			loginPacket.writeData(client);
		} else {
			Packet01Disconnect logoutPacket = new Packet01Disconnect(username);
			logoutPacket.writeData(client);
		}
	}
}
