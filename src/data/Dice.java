package data;

import javax.swing.*;
import java.awt.*;

public class Dice extends JButton {
	
	private static final long serialVersionUID = 1L;
	private int number;
	private Color brightColor;
	private Coloring color;
	private final static Color beige = new Color(255,250,235);
	private boolean selected;
	private int positionX;
	private int positionY;
	private boolean hole = false;
	
	public Dice(int number, Coloring color, int positionX, int positionY) {
		super();
		this.number = number;
		if (number == -10) { //Swap: Schwarzes Loch
			this.hole = true;
		}
		this.color = color;
		this.positionX = positionX;
		this.positionY = positionY;
		this.setPreferredSize(new Dimension(60,60));
		setBorderPainted(false);
		setOpaque(false);
		selected = false;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		int size = Math.min(getWidth(), getHeight());
		
		int margin = size / 20;
		g2d.setColor(beige);
		g2d.fillRect(0, 0, 300, 300);
		
		if (selected){ //Wuerfel ausgewaehlt: hellere Farbe
			g2d.setColor(color.getBrighterColor());
		} else {
			g2d.setColor(color.getColor());
		}
		
		if (hole) { //Schwarzes Loch: schwarzer Kreis
			g2d.setColor(Color.black);
			g2d.fillOval(margin, margin, size-2*margin, size - 2*margin);
		} else { //rundes Rechteck
			g2d.fillRoundRect(margin, margin, size - 2 * margin, size - 2 * margin, size / 5, size / 5);
		}
		
		if (!hole) {
			if (color == Coloring.BLACK) {
				g2d.setColor(Color.white);

			} else if (color == Coloring.WHITE) {
				g2d.setColor(Color.DARK_GRAY);
				g2d.drawRoundRect(margin, margin, size - 2 * margin, size - 2 * margin, size / 5, size / 5);
			} else {
				g2d.setColor(Color.BLACK);
			}

			drawDiceDot(g2d, size);
			if (selected) {
				g2d.setColor(Color.black);
				g2d.setStroke(new BasicStroke(7, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
				g2d.drawRoundRect(margin, margin, size - 2 * margin, size - 2 * margin, size / 5, size / 5);
			}
		}
	}

	
	//Punkte der Wuerfel zeichnen
	private void drawDiceDot(Graphics2D g2d, int size) {
		int dotSize = size / 5;
		double x;
		double y;
		switch (number) {
		case 1:
			x = size * (0.4);
			y = size * (0.4);
			g2d.fillOval((int) x, (int) y, dotSize, dotSize);
			break;
		case 2:
			x = size * (0.2);
			y = size * (0.2);
			g2d.fillOval((int) x, (int) y, dotSize, dotSize); // top left
			x = size * (0.6);
			y = size * (0.6);
			g2d.fillOval((int) x, (int) y, dotSize, dotSize); // bottom right
			break;
		case 3:
			x = size * (0.1);
			y = size * (0.1);
			g2d.fillOval((int) x, (int) y, dotSize, dotSize); // top right
			x = size * (0.4);
			y = size * (0.4);
			g2d.fillOval((int) x, (int) y, dotSize, dotSize); // middle
			x = size * (0.7);
			y = size * (0.7);
			g2d.fillOval((int) x, (int) y, dotSize, dotSize); // bottom right
			break;
		case 4:
			x = size * (0.2);
			y = size * (0.2);
			g2d.fillOval((int) x, (int) y, dotSize, dotSize); // top left
			x = size * (0.6);
			y = size * (0.2);
			g2d.fillOval((int) x, (int) y, dotSize, dotSize); // top right
			x = size * (0.2);
			y = size * (0.6);
			g2d.fillOval((int) x, (int) y, dotSize, dotSize); // bottom left
			x = size * (0.6);
			y = size * (0.6);
			g2d.fillOval((int) x, (int) y, dotSize, dotSize); // bottom left
			break;
		case 5:
			x = size * (0.1);
			y = size * (0.1);
			g2d.fillOval((int) x, (int) y, dotSize, dotSize); // top left
			x = size * (0.7);
			y = size * (0.1);
			g2d.fillOval((int) x, (int) y, dotSize, dotSize); // top right
			x = size * (0.4);
			y = size * (0.4);
			g2d.fillOval((int) x, (int) y, dotSize, dotSize); // middle
			x = size * (0.1);
			y = size * (0.7);
			g2d.fillOval((int) x, (int) y, dotSize, dotSize); // bottom left
			x = size * (0.7);
			y = size * (0.7);
			g2d.fillOval((int) x, (int) y, dotSize, dotSize); // bottom left
			break;
		case 6:
			x = size * (0.2);
			y = size * (0.1);
			g2d.fillOval((int) x, (int) y, dotSize, dotSize); // top left
			x = size * (0.6);
			y = size * (0.1);
			g2d.fillOval((int) x, (int) y, dotSize, dotSize); // top right
			x = size * (0.2);
			y = size * (0.4);
			g2d.fillOval((int) x, (int) y, dotSize, dotSize); // middle left
			x = size * (0.6);
			y = size * (0.4);
			g2d.fillOval((int) x, (int) y, dotSize, dotSize); // middle right
			x = size * (0.2);
			y = size * (0.7);
			g2d.fillOval((int) x, (int) y, dotSize, dotSize); // bottom left
			x = size * (0.6);
			y = size * (0.7);
			g2d.fillOval((int) x, (int) y, dotSize, dotSize); // bottom left
			break;
		}

	}
	
	/*
	public void selectBrightColor() {
		if (color.equals(Color.white)) {
			this.brightColor = new Color(240,230,210);
		} else if (color.equals(Color.black)) {
			this.brightColor = new Color(60,60,60);
		} else if (color.equals(Color.red)) {
			this.brightColor = new Color(255,150,150);
		} else if (color.equals(Color.green)) {
			this.brightColor = new Color(150,255,150);
		} else if (color.equals(Color.yellow)) {
			this.brightColor = new Color(255,255,150);
		} else if (color.equals(Color.blue)) {
			this.brightColor = new Color(150,150,255);
		} else if (color.equals(Color.cyan)) {
			this.brightColor = new Color(150,255,255);
		} else if (color.equals(Color.magenta)) {
			this.brightColor = new Color(255,150,255);
		} else if (color.equals(Color.orange)) {
			this.brightColor = new Color(255,180,130);
		} else if (color.equals(Color.pink)) {
			this.brightColor = new Color(255,200,200);
		} else if (color.equals(Color.gray)) {
			this.brightColor = new Color(200,200,200);
		} 		
	}*/
	
	//Getter Methoden
	public int getNumber() {
		return this.number;
	}
	
	public boolean getSelected() {
		return this.selected;
	}
	
	public Coloring getColor() {
		return this.color;
	}
	
	public Color getBrightColor() {
		return this.brightColor;
	}
	
	public int getPositionX() {
		return this.positionX;
	}

	public int getPositionY() {
		return positionY;
	}
	
	public boolean getHole() {
		return this.hole;
	}
	
    //Setter Methoden
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}
	
	public void setHole(boolean hole) {
		this.number = -10;
		this.hole = hole;
	}

}