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
			setText("B");
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
	
	
	
	public void setIsBlank(boolean isBlank) {
		this.isBlank = isBlank;
		if(isBlank) {
			this.setNumberId(16); //Fix to not be hard coded, instead use row*height
		}
	}
	
	public boolean isBlank() {
		return isBlank;
	}
		
	
	
	
	public void setNumberId(int id) {
		this.numberId = id;
		if(this.isBlank) {
			this.setText("B");
		}
		else {
			this.setText(String.valueOf(this.numberId));
		}
		

	}
	
	public int getNumberId() {
		return numberId;
	}
}
