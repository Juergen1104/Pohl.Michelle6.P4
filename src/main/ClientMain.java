package main;

import client.ClientUI;

public class ClientMain {
	
	public static void main(String[] args){
		ClientUI client = ClientUI.getInstance();
		client.setVisible(true);
	}

}