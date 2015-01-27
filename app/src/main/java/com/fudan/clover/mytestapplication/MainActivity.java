package com.fudan.clover.mytestapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends ActionBarActivity {

	public final static String EXTRA_NOTE_ID = "com.example.android.notepad.NOTEID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		actionBar.show();
	}

	/*
	private void bindAddAction() {
		Button btnAdd = (Button)findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				EditText mailInputText = (EditText)findViewById(R.id.inputEmail);
				String ori = mailInputText.getText().toString();
				String added = ori + "@fudan.edu.cn";
				mailInputText.setText(added);
			}
		});
	}*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.list_context_menu, menu);
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
			case R.id.jump_list:
				Intent listIntent = new Intent(this, com.fudan.clover.mytestapplication.ListActivity.class);
				startActivity(listIntent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
    }
}
