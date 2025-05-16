package main;

import server.ServerUI;

public class ServerMain {
	public static void main(String[] args) {
		ServerUI serverUI = ServerUI.getInstance();
		serverUI.setVisible(true);
	}
}