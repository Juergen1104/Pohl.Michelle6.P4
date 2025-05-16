package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import client.ClientProtocol;
import data.Coloring;
import data.Dice;
import data.Pair;
import server.Server;


public class ClientUI extends JFrame{
	private static final long serialVersionUID = 1L;
	
	
	private static ClientUI instance;
	public Server server;
	public ClientProtocol connection;
	
	private JPanel startPanel, gamePanel;
	private JButton[][] dicesMap = new Dice[6][11];
	private JButton submitButton;
	
	
	private List<Dice> selectedDices = new ArrayList<>();
	private List<Pair> dicesList = new ArrayList<>();
	private int points = 0;
	
	
	private final static Color pastelRed = new Color(255,128,128);
	private final static Color beige = new Color(255,250,235);
		
	private int w1 = 350,h1 = 400;                               
	
	public static ClientUI getInstance() {
		if (instance == null) {
			instance = new ClientUI();
		}
		return instance;
	}
	
	public ClientUI() {
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // allgemeines Schliessen verhindern
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if ((connection != null) && connection.isAlive()){
					ClientUI.this.disconnect();
					ClientUI.this.resetUI();
				} else {
					System.exit(0);
				}
				
			}
		});
		
		
		this.startPanel = createStartPanel();
		this.setContentPane(this.startPanel);
		this.getContentPane().setPreferredSize(new Dimension(w1,h1));
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	
	JPanel createStartPanel(){
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(Box.createRigidArea(new Dimension(w1,(h1-50)/2)),BorderLayout.NORTH);
		p.add(Box.createRigidArea(new Dimension(w1,(h1-50)/2)),BorderLayout.SOUTH);
		p.add(Box.createRigidArea(new Dimension(100,100)),BorderLayout.EAST);
		p.add(Box.createRigidArea(new Dimension(100,100)),BorderLayout.WEST);
		JButton b = new JButton("Connect");

		ActionListener connect =
				e -> {
					connectToServer("localhost", 1234);
				};
		b.addActionListener(connect);
		b.setBackground(pastelRed);
		p.add(b);
		p.setPreferredSize(new Dimension(w1,h1));
		p.setBackground(beige);
		p.setOpaque(true);
		return p;
	}
	
	
	
	public void createGameUI() {
		this.setContentPane(createGamePanel());
		this.revalidate();
		this.pack();
	}
	
	private JPanel createGamePanel() {
		this.gamePanel = new JPanel();
		this.gamePanel.setBackground(beige);
		this.gamePanel.setLayout(new BorderLayout());
		this.gamePanel.setPreferredSize(new Dimension(800,600));
		this.gamePanel.add(createProfilePanel(),BorderLayout.NORTH);
		this.gamePanel.add(createDicePanel());
		return this.gamePanel;
	}
	
	
	
	
	private JPanel createProfilePanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		panel.setLayout(new FlowLayout(FlowLayout.CENTER,150,0));

		panel.setOpaque(false);			
		
		JPanel left = new JPanel();
		left.setLayout(new FlowLayout(FlowLayout.CENTER));
		left.setOpaque(false);
		
		JLabel points = new JLabel("" + this.points, JLabel.CENTER);
		points.setPreferredSize(new Dimension(150,80));
		points.setBackground(pastelRed);
		points.setFont(new Font(null, Font.BOLD, 30));
		points.setOpaque(true);
		TitledBorder border = new TitledBorder("Current points:");
		border.setTitleFont(new Font(null, Font.ITALIC,15));
		points.setBorder(BorderFactory.createTitledBorder(border));
		left.add(points);
		
		JLabel fullPoints = new JLabel("40", JLabel.CENTER);
		fullPoints.setBackground(pastelRed);
		fullPoints.setPreferredSize(new Dimension(80, 80));
		fullPoints.setFont(new Font(null, Font.BOLD, 30));
		fullPoints.setOpaque(true);
		TitledBorder border2 = new TitledBorder("Goal:");
		border2.setTitleFont(new Font(null, Font.ITALIC,15));
		fullPoints.setBorder(BorderFactory.createTitledBorder(border2));
		left.add(Box.createRigidArea(new Dimension(70,100)));
		left.add(fullPoints);
		
		JPanel right = new JPanel();
		right.setOpaque(false);
		submitButton = new JButton("Submit");
		submitButton.setOpaque(true);
		submitButton.setBackground(pastelRed);
		submitButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		submitButton.setPreferredSize(new Dimension(150, 80));
		submitButton.setFont(new Font(null, Font.BOLD, 20));		
		submitButton.setEnabled(false);
		ActionListener submitComb = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedDices.size() == 2) {
					Dice dice1 = selectedDices.get(0);
					Dice dice2 = selectedDices.get(1);
					int d1x = dice1.getPositionX(), d1y = dice1.getPositionY(), 
							d2x = dice2.getPositionX(), d2y = dice2.getPositionY();
					
					connection.sendDicesPosition(d1x, d1y, d2x, d2y);
					
					selectedDices.clear();									
				}
			}
		};
		submitButton.addActionListener(submitComb);

		right.add(submitButton);
		
		panel.add(left);
		panel.add(right);
		
		return panel;
		
		
	}
	
		
	private JPanel createDicePanel() {
		JPanel dices = new JPanel();
		dices.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		dices.setLayout(new GridLayout(6, 11, 10, 10));
		dices.setOpaque(false);
		ActionListener dicePanelSelection = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dice d = (Dice) e.getSource();
				if (d.getSelected()) {
					d.setSelected(false); // nicht ausgewaehlt
					selectedDices.remove(d);
					if (selectedDices.size() != 2) {
						submitButton.setEnabled(false);
					} else {
						submitButton.setEnabled(true);
					}
				} else {
					d.setSelected(true); // ausgewaehlt
					selectedDices.add(d);
					if (selectedDices.size() == 2) {
						submitButton.setEnabled(true);
					} else {
						submitButton.setEnabled(false);
					}
				}
				ClientUI.this.repaint();
			}
		};
		int i = 0;
		int j = 0;
		for (Pair p : this.dicesList) {
			if (i < 6) {
				Dice dice = new Dice(p.getNumber(), Coloring.getColoringByString(p.getColor()), i,j);
				if (dice.getHole()) {
					dice.setEnabled(false);
				}
				dice.addActionListener(dicePanelSelection);
				dicesMap[i][j] = dice;
				dice.setOpaque(true);
				dices.add(dice);
				
				j++;
				if (j >= 11) {
					j = 0;
					i++;
				}
			}
		}
		
		return dices;
	}

			
	public void connectToServer(String host, int port) {
		this.connection = new ClientProtocol(host, port, ClientUI.this);
		if (this.connection.isOK()){
			this.connection.start();
		}
		
	}
	
	public void disconnect() {
		if (this.connection != null){
			this.connection.disconnect();
			this.connection = null;
		}
	}
	
		
	public void resetUI(){
		this.points = 0;
		this.setContentPane(startPanel);
		this.setJMenuBar(null);
		this.revalidate();
		this.getContentPane().setPreferredSize(new Dimension(w1,h1));
		this.pack();
	}
	
	public void setDicesList(List<Pair> list) {
		this.dicesList = list;
	}	
	
	public void setPoints(int points) {
		this.points = points;
		
	}
	
	public void showWinningMessage(String message) {
		JOptionPane.showMessageDialog(this,message);
		this.disconnect();
		this.resetUI();
	}
}