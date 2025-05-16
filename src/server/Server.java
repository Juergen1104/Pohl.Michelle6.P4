package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.LinkedList;


public class Server {
	
	private ServerSocket serverSocket;
	private boolean running;  
	
	// Verbindungen zu Clients
	private Collection<ServerProtocol> clientConnections = new LinkedList<>();
	
	private ServerProtocol clientConnectionThread;
	
		
	// Server einrichten & warten auf Anfragen von Clients
	public void listen() {
		running = true;   // Server aktiv
		try {
			serverSocket = new ServerSocket(1234);
			System.out.println("Server runs");
			
			while (running) {
				Socket clientSocket = serverSocket.accept();
				
				//Protokoll erstellen und Client-Thread starten
				clientConnectionThread = new ServerProtocol(clientSocket, this);
				synchronized(clientConnections) {
					clientConnections.add(clientConnectionThread);
				}
				clientConnectionThread.start();				
			}			

		} catch (IOException e) {
			if (serverSocket != null && serverSocket.isClosed()){
				System.out.println("Server stopped.");
			} else {
				e.printStackTrace();
			}
		}
	}
	
		
	//Server stoppen
	public void stopServer() {
		if (!clientConnections.isEmpty()) {
			synchronized (clientConnections) {
				for (ServerProtocol a4sp : clientConnections) {
					a4sp.sendDisconnect();
					a4sp.closeConnection();
				}
				clientConnections.clear();
			}
		}
			
		running = false;
		
		if (!serverSocket.isClosed()){
			try {
				serverSocket.close();
			} catch (IOException e){
				e.printStackTrace();
				System.exit(0);
			}
		}

	}
	
	public void removeClientConnection(ServerProtocol client){
		synchronized (clientConnections) {
			clientConnections.remove(client);
		}
	}
}