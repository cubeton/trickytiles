package com.meghan.trickytiles;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;


public class MenuActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		ListView menuList = (ListView) findViewById(R.id.ListView_Menu);
		String[] items = { getResources().getString(R.string.menu_item_play),
						   getResources().getString(R.string.menu_item_help)};
		ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, R.layout.menu_item, items);
		menuList.setAdapter(adapt);
		menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
				TextView textView = (TextView) itemClicked;
				String strText = textView.getText().toString();
				if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_play))) {
					//Start game
					Intent gameIntent = new Intent(MenuActivity.this, GameActivity.class);
					startActivityForResult(gameIntent, 1);
				} else if (strText.equalsIgnoreCase(getResources().getString(R.string.menu_item_help))){
					//Show help menu
					startActivity(new Intent(MenuActivity.this, HelpActivity.class));
				}
			}
		});
	}
}

