package com.luiz.game.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.luiz.game.client.packets.Packet;
import com.luiz.game.client.packets.Packet.PacketTypes;
import com.luiz.game.client.packets.Packet00Login;
import com.luiz.game.client.packets.Packet01Disconnect;
import com.luiz.game.client.packets.Packet02Move;
import com.luiz.game.entities.Player;

public class Client extends Thread {
	private InetAddress ipAddress;
	private DatagramSocket socket;
	private Player player;
	public List<Player> connectedPlayers = new ArrayList<Player>();
	
	public Client(String ipAddress) {
		try {
			this.socket = new DatagramSocket();
			System.out.println("CLIENT> started");
			this.ipAddress = InetAddress.getByName(ipAddress);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void initPlayer(Player player) {
		this.player = player;
	}
	
	public void run() {
		while(true) {
			byte[] data = new byte[1024]; 
			DatagramPacket packet = new DatagramPacket(data, data.length);

			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
	}
	
	private void parsePacket(byte[] data, InetAddress address, int port) {
		String message = new String(data).trim();
		PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
		
		Packet packet = null;
		switch(type) {
			default:
			case INVALID:
				break;
			case LOGIN:
				packet = new Packet00Login(data);
				System.out.println("["+address.getHostAddress()+":"+
						port+"] "+((Packet00Login) packet).getUsername()+
							" has joined the game...");
				this.addPlayer(new Player(((Packet00Login) packet).getUsername()));
				break;
			case DISCONNECT:
				packet = new Packet01Disconnect(data);
				System.out.println("["+address.getHostAddress()+":"+
						port+"] "+((Packet01Disconnect) packet).getUsername()+
							" has left the game...");
				this.removePlayer(((Packet01Disconnect)packet).getUsername());
				break;
			case MOVE:
				packet = new Packet02Move(data);
				System.out.println("["+address.getHostAddress()+":"+
						port+"] "+((Packet02Move) packet).getUsername()+
							" has moved...");				
				break;
			case SERVEROFF:
				//aqui obrigaremos o cliente a se desconectar do servidor
				break;
		}	
	}
	
	private void addPlayer(Player player) {
		this.connectedPlayers.add(player);
	}
	
	private void removePlayer(String name) {
		for(int i=0; i<connectedPlayers.size(); i++) {
			if(connectedPlayers.get(i).getUsername().equalsIgnoreCase(name)) {
				connectedPlayers.remove(i);
				return;
			}
		}
	}

	public void sendMessage(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Player getPlayer() {
		return player;
	}
}
