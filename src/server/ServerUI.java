package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ServerUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton s1,s2;
	private static ServerUI instance;
	private Server server;
	
	public static final Color beige = new Color(255,250,235);
	public static final Color pastelRed = new Color(255,128,128);
	
	public static ServerUI getInstance() {
		if (instance == null){
			instance = new ServerUI();
		}
		return instance;
	}
	
	private ServerUI(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.createComponents();
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (server != null){
					server.stopServer();
					server = null;
				}
				System.exit(0);
			}
		});
		this.setLocation(100,100);	
		this.pack();
	}
	
	private void createComponents(){
		Container c = this.getContentPane();
		JPanel panel = new JPanel();
		panel.setBackground(beige);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER,15,15));
		s1 = new JButton("Start Server");
		s2 = new JButton("Stop Server");
		s2.setEnabled(false);
		ActionListener sbLis = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = ((JButton)e.getSource()).getText();
				if (s.equals("Start Server")){
					ServerUI.this.s1.setEnabled(false);
					ServerUI.this.s2.setEnabled(true);
					ServerUI.this.server = new Server();
					Runnable r = new Runnable(){
						public void run(){
							server.listen();
						}
					};
					new Thread(r).start();
				} else {
					ServerUI.this.s1.setEnabled(true);
					ServerUI.this.s2.setEnabled(false);
					ServerUI.this.server.stopServer();
					ServerUI.this.server = null;
				}
				ServerUI.this.repaint();
			}
		};
		s1.addActionListener(sbLis);
		s2.addActionListener(sbLis);
		s1.setBackground(new Color(152,251,152));
		s2.setBackground(pastelRed);
		Font font = new Font("Arial", Font.BOLD,14);
		s1.setFont(font);
		s2.setFont(font);
		panel.add(s1); panel.add(s2);
		c.add(panel);		
	}
}