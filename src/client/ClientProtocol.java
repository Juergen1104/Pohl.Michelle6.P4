package client;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import message.*;

public class ClientProtocol extends Thread {
	private ClientUI gui;
	private Socket clientSocket;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	
	private int points;
	private int attempts = 0;
	
	private boolean running = true; // Gibt an, ob das Protokoll noch laeuft
	
		
	public ClientProtocol(String ip, int port, ClientUI gui) {
		this.gui = gui;
		try {
			this.clientSocket = new Socket(ip, port);
			this.toServer = new ObjectOutputStream(clientSocket.getOutputStream());
			this.fromServer = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println("Client connected");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("Could not establish connection to " + ip + ":" + port);
		}
		
		this.points = 0;
	}	
		 
		
	// TODO
	public void sendDicesPosition(int x1, int y1, int x2, int y2) {
		
	}	
	
	// TODO
	//Protokoll abwickeln
	public void run() {
				
	}
	
		
	// prueft die Verbindung
	public boolean isOK(){
		return (clientSocket != null) && (clientSocket.isConnected()) && !(clientSocket.isClosed()); 
	}
	
	
	//Thread, Stroeme & Socket schliessen
	private void closeConnection(){
		System.out.println("Client: close connection");
		running = false;
		try {
			if (isOK()){
				clientSocket.close(); // close streams and socket
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	
	//Client beim Server abmelden
	public void disconnect(){
		try {
			running = false;
			System.out.println("Client: send disconnect");
			if (isOK()){
				this.toServer.writeObject(new DisconnectMessage());
				this.closeConnection();
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}