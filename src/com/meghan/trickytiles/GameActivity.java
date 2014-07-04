package com.meghan.trickytiles;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

@SuppressLint("NewApi")
public class GameActivity extends Activity implements OnTouchListener{

	TableLayout mainTable;
	int numRows  = 4;
	int numColumns = 4;
	int blankXLocation = -1;
	int blankYLocation;
	float downXValue;
	float downYValue;
	public MenuItem play, pause;
	public boolean gamePlayOn = true;
	Animation animationTranslateLeft;
	Animation animationTranslateRight;
	Animation animationTranslateUp;
	Animation animationTranslateDown;
	public long timeWhenStopped = 0; //for keeping track of the chronometer pausing and playing
	public TextView move_text;
	public Chronometer time_text; 
	public int move_count = 0;
	
	
	Tile[][] tileArray = new Tile[numRows][numColumns];
	Random randomInteger = new Random();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_layout);
		
		move_text = (TextView) findViewById(R.id.move_text_number_id);
		time_text = (Chronometer) findViewById(R.id.time_id_number);
		time_text.start();	 
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		
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
		gamePlayOn = true;
		TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);
		TableRow.LayoutParams itemParams = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f);	
		itemParams.setMargins(3, 3, 3, 3);

		int blankspace = randomInteger.nextInt(numColumns*numRows); //Pick what tile gets the blank spot
/*		Toast.makeText(getApplicationContext(), "Blank is on tile: " + String.valueOf(blankspace),
				   Toast.LENGTH_SHORT).show();*/
		List<Integer> samples = new ArrayList<Integer>(); //setting up array to randomly pick id values from
		for(int entries=0; entries<(numColumns*numRows); entries++) {
			samples.add(entries);
		}
		Collections.shuffle(samples);
		System.out.println(samples);
		System.out.println("Blank is on tile: " + String.valueOf(blankspace));
		int index = 0;
		for(int i=0; i<numColumns; i++) {
			TableRow row = new TableRow(this);
			for(int j=0; j<numRows; j++) {
				Tile newTile;
/*				System.out.println("Adding index" + String.valueOf(index));*/
				if(samples.get(index) == ((numColumns*numRows)-1)) {
					newTile = new Tile(this.getBaseContext(), i, j, samples.get(index), true);
					blankXLocation = i;
					blankYLocation = j;				
				} else {
					newTile = new Tile(this.getBaseContext(), i, j, samples.get(index), false);
				}

				//Setting tile colors
				//TODO: Fix to not hard code values
/*				System.out.println("Samples" + String.valueOf(samples));
				System.out.println("Index to look up..." + String.valueOf((index)));
				System.out.println("Right before coloring..." + String.valueOf(samples.get(index)));*/
/*				if (index < 7) {
					newTile.setBackground(getResources().getDrawable(R.drawable.tile_style_blue));	
					System.out.println("Coloring blue index" + String.valueOf(samples.get(index)));
				}*/
				if (samples.get(index) >= 0 && samples.get(index) <= 4) {
					newTile.setBackground(getResources().getDrawable(R.drawable.tile_style_blue));	
					System.out.println("Coloring blue index" + String.valueOf(samples.get(index)));
				}
				else if (samples.get(index) > 4 && samples.get(index) <= 8) {
					newTile.setBackground(getResources().getDrawable(R.drawable.tile_style_purple));			
					System.out.println("Coloring purple index" + String.valueOf(samples.get(index)));
				} else if (samples.get(index) > 8 && samples.get(index) <= 12) {
					newTile.setBackground(getResources().getDrawable(R.drawable.tile_style_green));			
					System.out.println("Coloring green index" + String.valueOf(samples.get(index)));
				} else if (samples.get(index) > 12 && samples.get(index) <= 14) {
					newTile.setBackground(getResources().getDrawable(R.drawable.tile_style_orange));			
					System.out.println("Coloring orange index" + String.valueOf(samples.get(index)));
				} else if (samples.get(index) == 15) {
					newTile.setBackground(getResources().getDrawable(R.drawable.tile_style_blank));			
					System.out.println("Coloring blank index" + String.valueOf(samples.get(index)));					
				}
				

				index++;
				tileArray[i][j] = newTile;
				newTile.setLayoutParams(itemParams);

				//Don't need the blank tile to have a listener on it

				newTile.setOnTouchListener(this);
				
				row.addView(newTile);
			}
			row.setLayoutParams(rowParams);

			mainTable.addView(row); 		
		}	

	}

	private void checkForWin() {
		int numberId = 0;
		for(int i=0; i<numColumns; i++) {
			for(int j=0; j<numRows; j++) {
				if(numberId != tileArray[i][j].getNumberId()) {
					return; //not a game win
				}
				numberId++;
			}
		}
		//Is a win!
		time_text.stop();
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
 
			// set title
			alertDialogBuilder.setTitle("YOU WON!");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("Play again?")
				.setCancelable(false)
				.setPositiveButton("Yes yes yes RIGHT NOW!",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						resetGame();
					}
				  })
				.setNegativeButton("no I don't like fun",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
			}	

	private void swapTile(Tile tilePressed) {
		move_count +=1;
		move_text.setText(String.valueOf(move_count));
		int newBlankXLocation = tilePressed.getXLocation();
		int newBlankYLocation = tilePressed.getYLocation();

		tileArray[blankXLocation][blankYLocation].setText(tilePressed.getText());
		tileArray[blankXLocation][blankYLocation].setIsBlank(false);
		tileArray[blankXLocation][blankYLocation].setNumberId(tilePressed.getNumberId());		

		tileArray[newBlankXLocation][newBlankYLocation].setIsBlank(true);	

		blankXLocation = newBlankXLocation;
		blankYLocation = newBlankYLocation;

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Tile tilePressed = (Tile) v;
		if(tilePressed.isBlank()) {
			return true;
		}

		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			float downXValue = event.getX();
			float downYValue = event.getY();
			break;
		}
		case MotionEvent.ACTION_UP: {
			
			int pressedXLocation = tilePressed.getXLocation();
			int pressedYLocation = tilePressed.getYLocation();

			boolean blankIsAbove = (pressedYLocation == blankYLocation) && (pressedXLocation - 1 == blankXLocation);
			boolean blankIsToTheRight = (pressedYLocation + 1 == blankYLocation) && (pressedXLocation  == blankXLocation);
			boolean blankIsToTheLeft = (pressedYLocation  - 1 == blankYLocation) && (pressedXLocation== blankXLocation);
			boolean blankIsBelow = (pressedYLocation  == blankYLocation) && (pressedXLocation + 1 == blankXLocation);

			float currentX = event.getX();
			float currentY = event.getY();
			// check if horizontal or vertical movement was bigger

			if (Math.abs(downXValue - currentX) > Math.abs(downYValue
					- currentY)) {
				// going backwards: pushing stuff to the right
				if (downXValue < currentX) {
					tilePressed.startAnimation(animationTranslateRight);
					if(blankIsToTheRight) 
					{					
						swapTile (tilePressed);
					}
				}
				// going forwards: pushing stuff to the left
				if (downXValue > currentX) {
					tilePressed.startAnimation(animationTranslateLeft);   	
					if(blankIsToTheLeft) 
					{
						swapTile (tilePressed);
					}					
				}
			} else {
				if (downYValue < currentY) {
					tilePressed.startAnimation(animationTranslateDown);
					if(blankIsBelow) 
					{
						swapTile (tilePressed);
					}
				}
				if (downYValue > currentY) {
					tilePressed.startAnimation(animationTranslateUp);
					if(blankIsAbove) 
					{
						swapTile (tilePressed);
					}					
				}
			}
			break;
			}
		}

		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		play = menu.findItem(R.id.action_play);
		pause = menu.findItem(R.id.action_pause);
		play.setVisible(false);
		return true;
	}
	
	@Override 
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case (R.id.action_help) :
/*			Intent myIntent = new Intent(this, HelpActivity.class);*/
/*			startActivityForResult(myIntent, 1);*/
			break;
		case (R.id.action_refresh) :
			Toast.makeText(getApplicationContext(), "Game reset",
					   Toast.LENGTH_SHORT).show();
			resetGame();
			break;
		case(R.id.action_pause) :
			toggleGameplay(false); //Pausing game
			break;
		case(R.id.action_play) :
			toggleGameplay(true); //Playing game
 			break;
		case (android.R.id.home) :
			Intent returnIntent = new Intent();
			setResult(RESULT_OK, returnIntent);
			finish();
			break;
		}
		return true;
	}
	
	
	private void resetGame() {
		mainTable.removeAllViews();
		move_count = 0;
		move_text.setText(String.valueOf(move_count));
		initializeGame();
		time_text.setBase(SystemClock.elapsedRealtime());
		time_text.start();
	}
	
	private void toggleGameplay(boolean turnOn) {
		if(!turnOn) { //means we're pausing the game
			pauseChronometer();
			pause.setVisible(false);
			play.setVisible(true);			
			gamePlayOn = false;
		} else { //means we're turning the game back on
			resumeChronometer();
			pause.setVisible(true);
			play.setVisible(false);			
			gamePlayOn = true;
		}
	}

	private void pauseChronometer() {
		timeWhenStopped = time_text.getBase() - SystemClock.elapsedRealtime();
		time_text.stop();
	}
	
	private void resumeChronometer() {
		time_text.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
		time_text.start();
		timeWhenStopped = 0;
	}
	
}

