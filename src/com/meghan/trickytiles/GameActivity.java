package com.meghan.trickytiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class GameActivity extends Activity implements OnClickListener{
	
	TableLayout mainTable;
	int numRows  = 4;
	int numColumns = 4;
	int blankXLocation;
	int blankYLocation;
	Animation animationTranslateLeft;
	Animation animationTranslateRight;
	Animation animationTranslateUp;
	Animation animationTranslateDown;
	
	Tile[][] tileArray = new Tile[numRows][numColumns];
	Random randomInteger = new Random();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_layout);
		
		animationTranslateLeft = AnimationUtils.loadAnimation(this, R.anim.anim_translate_left);
		animationTranslateRight = AnimationUtils.loadAnimation(this, R.anim.anim_translate_right);
		animationTranslateUp = AnimationUtils.loadAnimation(this, R.anim.anim_translate_up);
		animationTranslateDown = AnimationUtils.loadAnimation(this, R.anim.anim_translate_down);
		
		
		//Add tiles to the board
		mainTable = (TableLayout)findViewById(R.id.mainTable);
		initializeGame();
		checkForWin();

	}
	
	private void initializeGame() {
		
		TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
        TableRow.LayoutParams itemParams = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);	
		
		int blankspace = randomInteger.nextInt(15); //Pick what tile gets the blank spot
		
		List<Integer> samples = new ArrayList<Integer>(); //setting up array to randomly pick id values from
		for(int entries=0; entries<(numColumns*numRows); entries++) {
			samples.add(entries);
		}
		Collections.shuffle(samples);
		
		int index = 0;
		for(int i=0; i<numColumns; i++) {
			TableRow row = new TableRow(this);
			for(int j=0; j<numRows; j++) {
				Tile newTile = new Tile(this.getBaseContext(), i, j, samples.get(index), blankspace == samples.get(index));
				if(blankspace == samples.get(index)) {
					blankXLocation = i;
					blankYLocation = j;
				}
				index++;
				tileArray[i][j] = newTile;
				newTile.setLayoutParams(itemParams);
			    newTile.setOnClickListener(this);
				row.addView(newTile);
			}
			row.setLayoutParams(rowParams);
			
			mainTable.addView(row); 		
		}	
		
		//Don't want to start with a winning game, reset game if it's correct
		if(checkForWin()) {
			initializeGame();
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

	@Override
	public void onClick(View v) {
		Tile tilePressed = (Tile) v;

		
		
		
		/*		tilePressed.startAnimation(animationTranslateRight);*/
/*		int pressedXLocation = tilePressed.getXLocation();
		int pressedYLocation = tilePressed.getYLocation();
		
		boolean blankIsAbove = (pressedYLocation + 1 == blankYLocation) && (pressedXLocation == blankXLocation);
		boolean blankIsToTheRight = (pressedYLocation == blankYLocation) && (pressedXLocation + 1 == blankXLocation);
		boolean blankIsToTheLeft = (pressedYLocation == blankYLocation) && (pressedXLocation - 1 == blankXLocation);
		boolean blankIsBelow = (pressedYLocation -1 == blankYLocation) && (pressedXLocation == blankXLocation);
		
		if (blankIsAbove || blankIsToTheRight ||  blankIsToTheLeft || blankIsBelow) {
			Toast.makeText(getApplicationContext(), "Next to blank tile!",
					   Toast.LENGTH_LONG).show();
			swapTile(tilePressed);
						
		}*/
	}
	
	private void swipeDown(Tile tilePressed) {
		
	}
	
	private void swipeLeft(Tile tilePressed) {
		
	}
	
	private void swipeRight(Tile tilePressed) {
		
	}
	
	private void swipeUp(Tile tilePressed) {
		
	}	

	private void swapTile(Tile tilePressed) {
		
		int newBlankXLocation = tilePressed.getXLocation();
		int newBlankYLocation = tilePressed.getYLocation();
		
		tileArray[blankXLocation][blankYLocation].setText(tilePressed.getText());
		tileArray[blankXLocation][blankYLocation].setIsBlank(false);
		tileArray[blankXLocation][blankYLocation].setNumberId(tilePressed.getNumberId());		
		
		tileArray[newBlankXLocation][newBlankYLocation].setIsBlank(true);	
		
		blankXLocation = newBlankXLocation;
		blankYLocation = newBlankYLocation;
		
	}
	
   
   
/*	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Tile tilePressed = (Tile) v;
		int action = MotionEventCompat.getActionMasked(event);
		switch(action) 
		{
		 case (MotionEvent.ACTION_MOVE):
	         int x = (int)event.getX() - (int)tilePressed.getX();
	         int y = (int)event.getY() - (int)tilePressed.getY();	
	         
				Toast.makeText(getApplicationContext(), "X change: " + String.valueOf(x) + "Y change: " + y,
						   Toast.LENGTH_SHORT).show();
		
		}
	         int w = getWindowManager().getDefaultDisplay().getWidth() - 100;
	         int h = getWindowManager().getDefaultDisplay().getHeight() - 100;
	         
	         if(x > w) {
	        	 x = w;
	         }        	 

	         if(y > h) {
	        	 y = h;
	         }

return false;
	}*/



/*		switch(action) {
		case(MotionEvent.ACTION_DOWN): 
			swipeDown(tilePressed);
			Toast.makeText(getApplicationContext(), "SWIPE DOWN Tile #:" + String.valueOf(tilePressed.getNumberId()),
					   Toast.LENGTH_LONG).show();						
		break;
		case(MotionEvent.ACTION_UP): 
			swipeUp(tilePressed);
			Toast.makeText(getApplicationContext(), "SWIPE UP Tile #:" + String.valueOf(tilePressed.getNumberId()),
					   Toast.LENGTH_LONG).show();						
		break;
		}
		case(MotionEvent.ACTION_LEFT): 
			swipeUp(tilePressed);
			Toast.makeText(getApplicationContext(), "SWIPE UP Tile #:" + String.valueOf(tilePressed.getNumberId()),
				   Toast.LENGTH_LONG).show();						
			break;
		}
	case(MotionEvent.ACTION_UP): 
		swipeUp(tilePressed);
		Toast.makeText(getApplicationContext(), "SWIPE UP Tile #:" + String.valueOf(tilePressed.getNumberId()),
			   Toast.LENGTH_LONG).show();						
		break;
	}
		return false;
	}*/
}

