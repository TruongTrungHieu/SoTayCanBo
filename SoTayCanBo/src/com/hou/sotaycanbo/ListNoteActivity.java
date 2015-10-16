package com.hou.sotaycanbo;

import java.util.ArrayList;

import com.hou.adapters.GhichuAdapter;
import com.hou.models.GhiChu;
import com.hou.models.SoTay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ListNoteActivity extends ActionBarActivity {

	private ActionBar actionbar;
	private SoTay mSoTay;
	private TextView tvSubTitle;
	private ListView lvGhichu;
	private GhichuAdapter adapter;
	private ArrayList<GhiChu> listGhichu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_note);

		actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setHomeButtonEnabled(true);
		actionbar.setElevation(0);

		mSoTay = (SoTay) getIntent().getSerializableExtra("SoTay");

		this.setTitle(mSoTay.getTenSoTay());

		tvSubTitle = (TextView) findViewById(R.id.tvSubTitle);
		tvSubTitle.setText(mSoTay.getSoGhiChu() + " Ghi chú");

		lvGhichu = (ListView) findViewById(R.id.lvGhichu);

		listGhichu = new ArrayList<GhiChu>();
		listGhichu.add(new GhiChu("1", "DB", 1436207202, "khẩn trương", 1436207202, 0, 0, 0, 1, "st1"));
		listGhichu.add(new GhiChu("1", "DB", 1436207202, "khẩn trương", 1436207202, 0, 0, 0, 1, "st1"));
		listGhichu.add(new GhiChu("1", "DB", 1436207202, "khẩn trương", 1436207202, 0, 0, 0, 1, "st1"));
		listGhichu.add(new GhiChu("1", "DB", 1436207202, "khẩn trương", 1436207202, 0, 0, 0, 1, "st1"));
		listGhichu.add(new GhiChu("1", "DB", 1436207202, "khẩn trương", 1436207202, 0, 0, 0, 1, "st1"));
		listGhichu.add(new GhiChu("1", "DB", 1436207202, "khẩn trương", 1436207202, 0, 0, 0, 1, "st1"));
		listGhichu.add(new GhiChu("1", "DB", 1436207202, "khẩn trương", 1436207202, 0, 0, 0, 1, "st1"));
		listGhichu.add(new GhiChu("1", "DB", 1436207202, "khẩn trương", 1436207202, 0, 0, 0, 1, "st1"));

		adapter = new GhichuAdapter(getApplicationContext(),
				R.layout.itemlist_sotay, listGhichu);

		lvGhichu.setAdapter(adapter);

		onClickListener();

	}

	public void onClickListener() {
		lvGhichu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent note = new Intent(ListNoteActivity.this,
						NoteActivity.class);
				note.putExtra("TYPE_NOTE", "READ");
				note.putExtra("NOTE", listGhichu.get(position));
				startActivity(note);
			}
		});

		lvGhichu.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		lvGhichu.setMultiChoiceModeListener(new MultiChoiceModeListener() {

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				// TODO Auto-generated method stub
				adapter.removeSelection();
				tvSubTitle.setVisibility(View.VISIBLE);
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				mode.getMenuInflater().inflate(
						R.menu.choice_multi_note_listnote, menu);
				tvSubTitle.setVisibility(View.GONE);
				return true;
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				// TODO Auto-generated method stub
				switch (item.getItemId()) {
				case R.id.delete_multinote:

					SparseBooleanArray selected = adapter.getSelectedIds();
					// Captures all selected ids with a loop
					for (int i = (selected.size() - 1); i >= 0; i--) {
						if (selected.valueAt(i)) {
							GhiChu selecteditem = adapter.getItem(selected
									.keyAt(i));
							// Remove selected items following the ids
							adapter.remove(selecteditem);
						}
					}

					mode.finish();
					return true;
				default:
					return false;
				}

			}

			@Override
			public void onItemCheckedStateChanged(ActionMode mode,
					int position, long id, boolean checked) {
				// TODO Auto-generated method stub
				final int checkedCount = lvGhichu.getCheckedItemCount();
				mode.setTitle(checkedCount + " "
						+ getResources().getString(R.string.listnote_ghichu));
				adapter.toggleSelection(position);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_note, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			onBackPressed();
			break;

		case R.id.action_listnote_share:
			if (tvSubTitle.isShown()) {
				tvSubTitle.setVisibility(View.GONE);
			} else {
				tvSubTitle.setVisibility(View.VISIBLE);
			}
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
