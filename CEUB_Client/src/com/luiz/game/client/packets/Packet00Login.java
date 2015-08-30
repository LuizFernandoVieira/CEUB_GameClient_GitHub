package com.luiz.game.client.packets;

import com.luiz.game.client.Client;

public class Packet00Login extends Packet {
	private String username;

	public Packet00Login(byte[] data) {
		super(00);
		this.username = readData(data);
	}
	
	public Packet00Login(String username) {
		super(00);
		this.username = username;
	}

	public void writeData(Client client) {
		client.sendMessage(getData());
	}

	public byte[] getData() {
		return ("00" + this.username).getBytes();
	}

	public String getUsername() {
		return username;
	}
}
