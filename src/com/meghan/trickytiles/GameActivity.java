package com.meghan.trickytiles;

import java.util.Random;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class GameActivity extends Activity {
	
	TableLayout mainTable;
	int numRows  = 4;
	int numColumns = 4;
	Tile[][] tileArray = new Tile[numRows][numColumns];
	Random randomInteger = new Random();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_layout);
		
		//Add tiles to the board
		mainTable = (TableLayout)findViewById(R.id.mainTable);
		initializeGame();
		checkForWin();

	}
	
	private void initializeGame() {
		int id = 0; //Tile number
		int blankspace = randomInteger.nextInt(15);
		for(int i=0; i<numColumns; i++) {
			TableRow row = new TableRow(this);
			for(int j=0; j<numRows; j++) {
					Tile newTile;
				if(blankspace == id) {
					newTile = new Tile(this.getBaseContext(), i, j, 16, true);
					blankspace = -1; //resetting it so no other tile gets called a blank
				} else { 
					newTile = new Tile(this.getBaseContext(), i, j, id, false);
					id++;
				}
				tileArray[i][j] = newTile;
				row.addView(newTile);

			}
			mainTable.addView(row); 		
		}		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	private void switchTiles() {
		
	}
	
	private boolean checkForWin() {
		int numberId = 0;
		for(int i=0; i<numColumns; i++) {
			for(int j=0; j<numRows; j++) {
				if(numberId != tileArray[i][j].getNumberId()) {
					return false; //not a game win
				}
				numberId++;
			}
		}
		Toast.makeText(getApplicationContext(), "Game winner!",
				   Toast.LENGTH_SHORT).show();
		return true; //We have a winner!
	}
}

