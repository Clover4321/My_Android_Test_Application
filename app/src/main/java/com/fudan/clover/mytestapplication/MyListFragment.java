package com.fudan.clover.mytestapplication;


import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyListFragment extends ListFragment {

	public final static String EXTRA_NOTE_ID = "com.example.android.notepad.NOTEID";

	public MyListFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		String[] dataColumns = new String[]{"title", "content"};
		int[] viewIds = new int[]{R.id.item_title, R.id.item_summary};

		Activity parentActivity = getActivity();

		NoteDbHelper db = new NoteDbHelper(parentActivity);
		Cursor cursor = db.getAllNotesCursor();

		try {
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(
					parentActivity,
					R.layout.notelist_item,
					cursor,
					dataColumns,
					viewIds
			);
			setListAdapter(adapter);
		} catch (Exception e) {
			Log.d("Error:", "1", e);
		}
		return inflater.inflate(R.layout.fragment_list, container, false);
	}

	@Override
	public void onViewCreated (View view, Bundle savedInstanceState) {
		ListView lv = getListView();
		registerForContextMenu(getListView());
		/*lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d("contextItemSelected", "selected");

				return true;
			}
		});*/
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		menu.add(this.getId(), R.id.context_delete, 0, R.string.menu_delete);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info;

		Log.d("contextItemSelected", "selected");

		try {
			info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		} catch (ClassCastException e) {
			Log.e("SimpleNotePad", "bad menuInfo", e);
			return false;
		}

		NoteDbHelper db = new NoteDbHelper(getActivity());

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
			NoteDbHelper db = new NoteDbHelper(getActivity());
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
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity(), NoteEditorActivity.class);
		intent.putExtra(EXTRA_NOTE_ID, (int) id);
		startActivity(intent);
	}
}
