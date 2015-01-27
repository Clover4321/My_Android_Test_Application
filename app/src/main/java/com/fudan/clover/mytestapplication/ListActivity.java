package com.fudan.clover.mytestapplication;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ListActivity extends ActionBarActivity {

	public final static String EXTRA_NOTE_ID = "com.example.android.notepad.NOTEID";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		ActionBar actionBar = getSupportActionBar();
		actionBar.show();
		getFragmentManager().beginTransaction()
				.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
				.add(R.id.listFragment_content, new MyListFragment())
				.commit();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		switch (id) {
			case R.id.action_camera:
				Intent camIntent = new Intent(this, Camera.class);
				startActivity(camIntent);
				return true;
			case R.id.note_add:
				Intent intent = new Intent(this, NoteEditorActivity.class);
				intent.putExtra(EXTRA_NOTE_ID, -1);
				startActivity(intent);
				return true;
			case R.id.jump_fragment:
				Intent fragIntent = new Intent(this, BasicFragmentActivity.class);
				startActivity(fragIntent);
				return true;
			case R.id.jump_login:
				Intent loginIntent = new Intent(this, LoginActivity.class);
				startActivity(loginIntent);
				return true;
			case R.id.jump_navigation:
				Intent naviIntent = new Intent(this, NavigationActivity.class);
				startActivity(naviIntent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
