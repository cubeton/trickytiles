package com.meghan.trickytiles;

public class Tile {
	public int xLocation;
	public int yLocation;
	public int numberId;
	public boolean isBlank;
	
	public Tile(int x, int y, int id, boolean isBlank) {
		this.xLocation = x;
		this.yLocation = y;
		this.numberId = id;
		this.isBlank = isBlank;
	}
	public int getXLocation() {
		return xLocation;
	}
	
	public void setXLocation(int newXLocation) {
		this.xLocation = newXLocation;
	}

	public int getYLocation() {
		return yLocation;
	}
	
	public void setYLocation(int newYLocation) {
		this.yLocation = newYLocation;
	}	
	
	public boolean isBlank() {
		return isBlank;
	}
}
