package data;

import java.awt.Color;

public enum Coloring {
	RED, GREEN, BLUE, CYAN, MAGENTA, YELLOW, BLACK, WHITE, GRAY, PINK, ORANGE;
	
	public Color getColor(){
		Color c = null;
		switch(this){
		case BLUE:
			c = Color.BLUE;
			break;
		case CYAN:
			c = Color.CYAN;
			break;
		case YELLOW:
			c = Color.YELLOW;
			break;
		case GREEN:
			c = Color.GREEN;
			break;
		case MAGENTA:
			c = Color.MAGENTA;
			break;
		case RED:
			c = Color.RED;
			break;
		case BLACK:
			c = Color.BLACK;
			break;
		case GRAY:
			c = Color.LIGHT_GRAY;
			break;
		case WHITE:
			c = Color.WHITE;
			break;
		case PINK:
			c = Color.PINK;
			break;
		case ORANGE:
			c = Color.ORANGE;
			break;
		default:
			c = Color.LIGHT_GRAY;
			break;		
		}
		return c;
	}
	
	public static Coloring getColoringByString(String s){
		Coloring f = null;
		switch (s){
		 case "RED":
			 f = RED;
			 break;
		 case "GREEN":
			 f = GREEN;
			 break;
		 case "BLUE":
			 f = BLUE;
			 break;
		 case "CYAN": 
			 f = CYAN;
			 break;
		 case "MAGENTA":
			 f = MAGENTA;
			 break;
		 case "YELLOW":
			 f = YELLOW;
			 break;
		 case "BLACK":
			 f = BLACK;
			 break;
		 case "WHITE":
			 f = WHITE;
			 break;
		 case "GRAY":
			 f = GRAY;
			 break;
		 case "PINK":
			 f = PINK;
			 break;
		 case "ORANGE":
			 f = ORANGE;
			 break;
		}
		return f;
	}
	
	
	public static Coloring getColoringByColor(Color c){
		Coloring f = null;
		switch (c.getRGB()){
		 case -65536:
			 f = RED;
			 break;
		 case -16711936:
			 f = GREEN;
			 break;
		 case -16776961:
			 f = BLUE;
			 break;
		 case -16711681: 
			 f = CYAN;
			 break;
		 case -65281:
			 f = MAGENTA;
			 break;
		 case -256:
			 f = YELLOW;
			 break;
		 case -16777216:
			 f = BLACK;
			 break;
		 case -1:
			 f = WHITE;
			 break;
		 case -4144960:
			 f = GRAY;
			 break;
		 case -20561:
			 f = PINK;
			 break;
		 case -14336:
			 f = ORANGE;
			 break;
		 default:
			 f = GRAY;
		}
		return f;
	}
	
	public String toString(){
		String s = null;
		switch(this){
		case BLUE:
			s = "BLUE";
			break;
		case CYAN:
			s = "CYAN";
			break;
		case YELLOW:
			s = "YELLOW";
			break;
		case GREEN:
			s = "GREEN";
			break;
		case MAGENTA:
			s = "MAGENTA";
			break;
		case RED:
			s = "RED";
			break;
		case BLACK:
			s = "BLACK";
			break;
		case GRAY:
			s = "GRAY";
			break;
		case WHITE:
			s = "WHITE";
			break;
		case PINK:
			s = "PINK";
			break;
		case ORANGE:
			s = "ORANGE";
			break;
		default:
			s = "";
			break;		
		}
		return s;
	}
	
	public Color getBrighterColor() {
		Color c = null;
		switch(this){
		case BLUE:
			c = new Color(150,150,255);
			break;
		case CYAN:
			c = new Color(150,255,255);
			break;
		case YELLOW:
			c = new Color(255,255,150);
			break;
		case GREEN:
			c = new Color(150,255,150);
			break;
		case MAGENTA:
			c = new Color(255,150,255);
			break;
		case RED:
			c = new Color(255,150,150);
			break;
		case BLACK:
			c = new Color(60,60,60);
			break;
		case GRAY:
			c = Color.LIGHT_GRAY;
			break;
		case WHITE:
			c = new Color(240,230,210);
			break;
		case PINK:
			c = new Color(255,200,200);
			break;
		case ORANGE:
			c = new Color(255,180,130);
			break;
		default:
			c = Color.LIGHT_GRAY;
			break;		
		}
		return c;
	}
}