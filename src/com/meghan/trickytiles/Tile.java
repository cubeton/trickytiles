package com.meghan.trickytiles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.widget.Button;

public class Tile extends Button{
	public int xLocation;
	public int yLocation;
	public int numberId;
	public boolean isBlank;
	
	@SuppressLint("NewApi")
	public Tile(Context context, int x, int y, int id, boolean isBlank) {
		super(context);
		this.xLocation = x;
		this.yLocation = y;
		this.numberId = id;
		this.isBlank = isBlank;
		if(isBlank) {
			numberId = 15;
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
		System.out.println("Setting tileId " + String.valueOf(numberId) + " to " + String.valueOf(isBlank));
		this.isBlank = isBlank;
		if(this.isBlank) {
			this.setText("");
			this.setNumberId(15); //TODO: Fix to not be hard coded, instead use row*height
		}
	}
	
	public boolean isBlank() {
		return isBlank;
	}	
	
	public void setNumberId(int id) {
		this.numberId = id;
		System.out.println("setNumberId of tileId " + String.valueOf(numberId) + " to " + String.valueOf(isBlank));
		if(!this.isBlank) {
			System.out.println("setting text for tileId " + String.valueOf(numberId));
			this.setText(String.valueOf(this.numberId));
		}
	}
	
	public int getNumberId() {
		return numberId;
	}
}
