package server;
//import java.awt.Color;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import data.DicePanel;
import message.*;

import static message.MessageType.DISCONNECT;

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

        try {
            sendMessageToClient(new StartMessage(dicePanel.getList()));
        } catch (Exception e) {
            System.err.println("Fehler beim Senden der Würfelliste an den Client: " + e.getMessage());
            running = false;
            removeConnection();
        }

    }

    // TODO
    //Message an Client senden
    private void sendMessageToClient(Message m) {

        try {
            toClient.reset(); // sorgt dafür, dass alte Referenzen verworfen werden
            toClient.writeObject(m);
            toClient.flush();
        } catch (IOException e) {
            System.err.println("Fehler beim Senden der Nachricht an den Client: " + e.getMessage());
            running = false;
            removeConnection();
        }

    }

    // TODO
    //Thread starten: Protokollabwicklung
    public void run() {

        running = true;

        // Zu Beginn: Spielfeld senden
        sendListToClient();

        while (running) {
            try {
                Object obj = fromClient.readObject();
                if (obj instanceof Message message) {

                    switch (message.getMessageType()) {
                        case CHECK -> {
                            if (message instanceof CheckMessage checkMsg) {
                                int points = dicePanel.checkPoints(
                                        checkMsg.getX1(), checkMsg.getY1(),
                                        checkMsg.getX2(), checkMsg.getY2()
                                );
                                sendMessageToClient(new ResultMessage(points, dicePanel.getList()));
                            }
                        }

                        case DISCONNECT -> {
                            running = false;
                            removeConnection();
                        }

                        default -> {
                            System.err.println("Typ unbekannt --> Ende der Verbindung ");
                            running = false;
                            removeConnection();
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("IO-Fehler im ServerProtocol: " + e.getMessage());
                running = false;
                removeConnection();
            } catch (Exception e) {
                e.printStackTrace(); // für alle anderen Fehler
            }
        }


    }

    /* send message to client to signal disconnect process */
    //Message an Client schicken dass Verbindung unterbrochen wird
    public void sendDisconnect() {
        DisconnectMessage dcm = new DisconnectMessage();
        sendMessageToClient(dcm);
    }

    //Verbindung zu Client entfernen
    private void removeConnection() {
        server.removeClientConnection(this);
        closeConnection();
    }

    // Stroeme und Socket schliessen
    public void closeConnection() {
        running = false;
        try {
            if (socket.isClosed()) {
                System.out.println("Server: Socket was already closed.");
            } else {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}