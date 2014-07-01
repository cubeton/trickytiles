package com.meghan.trickytiles;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;

public class Tile extends Button{
	public int xLocation;
	public int yLocation;
	public int numberId;
	public boolean isBlank;
	
	public Tile(Context context, int x, int y, int id, boolean isBlank) {
		super(context);
		this.xLocation = x;
		this.yLocation = y;
		this.numberId = id;
		this.isBlank = isBlank;
		if(isBlank) {
			setText("Blank space");
		} else {
			setText(String.valueOf(numberId));			
		}

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
	
	public int getNumberId() {
		return numberId;
	}
}
