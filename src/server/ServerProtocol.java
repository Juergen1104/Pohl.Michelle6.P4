package server;
//import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import data.DicePanel;
import message.*;

public class ServerProtocol extends Thread {
	private Socket socket;
	private ObjectInputStream fromClient;
	private ObjectOutputStream toClient;
	private Server server;
	private DicePanel dicePanel;
	private boolean running = true;
	
	//Ein- & Ausgabestroeme anlegen und oeffnen
	ServerProtocol(Socket client, Server server) {
		this.socket = client;
		this.server = server;
		try {
			toClient = new ObjectOutputStream(socket.getOutputStream());
			fromClient = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Spielfeld mit Wuerfeln erstellen
		dicePanel = new DicePanel();
		
		//Liste mit Informationen fuer Wuerfel an Client senden
		sendListToClient();
	}
	
	// TODO
	//Liste mit Informationen fuer Wuerfel an Client senden
	private void sendListToClient() {
		
	}
	
	// TODO
	//Message an Client senden
	private void sendMessageToClient(Message m){
		
	}
	
	// TODO
	//Thread starten: Protokollabwicklung
	public void run() {
		
	}
	
	/* send message to client to signal disconnect process */
	//Message an Client schicken dass Verbindung unterbrochen wird
	public void sendDisconnect(){
		DisconnectMessage dcm = new DisconnectMessage();
		sendMessageToClient(dcm);
	}
	
	//Verbindung zu Client entfernen
	private void removeConnection(){
		server.removeClientConnection(this);
		closeConnection();
	}
	
	// Stroeme und Socket schliessen 
	public void closeConnection() {
		running = false;
		try {
			if (socket.isClosed()){
				System.out.println("Server: Socket was already closed.");
			} else {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
}