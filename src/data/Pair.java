package data;

import java.io.Serializable;

public class Pair implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String color = "";
	private int number = 0;
	
	public Pair(String color, int number) {
		this.color = color;
		this.number = number;
	}
	
	public String getColor() {
		return this.color;
	}
	
	public int getNumber() {
		return this.number;
	}
	
}