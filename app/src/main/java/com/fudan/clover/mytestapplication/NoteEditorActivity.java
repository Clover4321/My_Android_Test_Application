package com.fudan.clover.mytestapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class NoteEditorActivity extends ActionBarActivity {
	private EditText _noteTitleEditText;
	private EditText _noteContentEditText;
	private int _noteId;
	private NoteDbHelper _db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_editor);

		ActionBar actionBar = getSupportActionBar();
		actionBar.show();

		_noteTitleEditText = (EditText) findViewById(R.id.noteTitleEditText);
		_noteContentEditText = (EditText) findViewById(R.id.noteContentEditText);

		_db = new NoteDbHelper(this);

		Intent intent = getIntent();
		_noteId = intent.getIntExtra(MainActivity.EXTRA_NOTE_ID, -1);

		if (_noteId > 0) {
			Note note = _db.getNote(_noteId);
			_noteTitleEditText.setText(note.getTitle());
			_noteContentEditText.setText(note.getContent());
			setTitle(R.string.title_edit);
		}
		else {
			setTitle(R.string.title_new);
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.note_editor, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		switch (id) {
			case R.id.menu_save:
				addOrUpgradeNote();
				finish();
				return true;
			case R.id.menu_delete:
				_db.deleteNote(_noteId);
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private final int addOrUpgradeNote() {
		String title = _noteTitleEditText.getText().toString();
		String content = _noteContentEditText.getText().toString();

		int newNoteId = -1;

		if (_noteId > 0) {
			Note note = _db.getNote(_noteId);
			note.setTitle(title);
			note.setContent(content);
			note.setModified(System.currentTimeMillis());
			_db.updateNote(note);
		}
		else {
			Note newNote = new Note(title, content);
			newNoteId = _db.addNote(newNote);
		}

		return newNoteId;
	}
}
