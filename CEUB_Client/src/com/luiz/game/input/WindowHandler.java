package com.luiz.game.input;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import com.luiz.game.Game;
import com.luiz.game.client.packets.Packet01Disconnect;

public class WindowHandler implements WindowListener{

	private final Game game;
		
	public WindowHandler(Game game) {
		this.game = game;
		this.game.frame.addWindowListener(this);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		Packet01Disconnect packet = new Packet01Disconnect(this.game.client.
				getPlayer().getUsername());
		packet.writeData(this.game.client);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}
}
