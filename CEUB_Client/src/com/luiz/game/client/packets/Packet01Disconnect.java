package com.luiz.game.client.packets;

import com.luiz.game.client.Client;

public class Packet01Disconnect extends Packet {
	private String username;

	public Packet01Disconnect(byte[] data) {
		super(01);
		this.username = readData(data);
	}
	
	public Packet01Disconnect(String username) {
		super(01);
		this.username = username;
	}

	public void writeData(Client client) {
		client.sendMessage(getData());
	}

	public byte[] getData() {
		return ("01" + this.username).getBytes();
	}
	
	public String getUsername() {
		return username;
	}
}
