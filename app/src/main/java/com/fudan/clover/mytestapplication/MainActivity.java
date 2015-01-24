package com.fudan.clover.mytestapplication;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ListActivity {

	public final static String EXTRA_NOTE_ID = "com.example.android.notepad.NOTEID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
		getListView().setOnCreateContextMenuListener(this);

		NoteDbHelper db = new NoteDbHelper(this);

		String[] dataColumns = new String[]{"title", "content"};
		int[] viewIds = new int[]{R.id.item_title, R.id.item_summary};

		Cursor cursor = db.getAllNotesCursor();

		try {
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(
					this,
					R.layout.notelist_item,
					cursor,
					dataColumns,
					viewIds
			);
			setListAdapter(adapter);
		} catch (Exception e) {
			Log.d("Error:", "1", e);
		}
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
			case R.id.action_settings:
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
			default:
				return super.onOptionsItemSelected(item);
		}
    }

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info;

		try {
			info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		} catch (ClassCastException e) {
			Log.e("SimpleNotePad", "bad menuInfo", e);
			return false;
		}

		NoteDbHelper db = new NoteDbHelper(this);

		switch (item.getItemId()) {
			case R.id.context_delete:
				int noteId = (int) info.id;
				if (noteId > 0) {
					db.deleteNote(noteId);
				}
				reloadNote();
				return true;
			default:
				return super.onContextItemSelected(item);
		}
	}

	private void reloadNote() {
		SimpleCursorAdapter adapter = (SimpleCursorAdapter) getListAdapter();
		if (adapter != null) {
			NoteDbHelper db = new NoteDbHelper(this);
			Cursor cursor = db.getAllNotesCursor();
			adapter.swapCursor(cursor);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		reloadNote();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(this, NoteEditorActivity.class);
		intent.putExtra(EXTRA_NOTE_ID, (int) id);
		startActivity(intent);
	}
}
