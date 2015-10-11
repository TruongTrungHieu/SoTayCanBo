package com.hou.fragment;

import java.util.ArrayList;

import com.hou.adapters.GhichuAdapter;
import com.hou.models.GhiChu;
import com.hou.sotaycanbo.NoteActivity;
import com.hou.sotaycanbo.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

public class GhichuFragment extends Fragment {

	private ListView lvGhichu;
	private ArrayList<GhiChu> listGhichu = null;
	private GhichuAdapter adapter = null;

	private FrameLayout flNoNote;

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_ghichu, container, false);

		lvGhichu = (ListView) view.findViewById(R.id.lvGhichu);

		listGhichu = new ArrayList<GhiChu>();
		listGhichu.add(new GhiChu("1", "DB", 1436207202, "khẩn trương",
				1436207202, 0, 0, 0, 1, "st1"));
		listGhichu.add(new GhiChu("1", "DB", 1436207202, "khẩn trương",
				1436207202, 0, 0, 0, 0, "st1"));
		listGhichu.add(new GhiChu("1", "DB", 1436207202, "khẩn trương",
				1436207202, 0, 0, 0, 1, "st1"));
		listGhichu.add(new GhiChu("1", "DB", 1436207202, "khẩn trương",
				1436207202, 0, 0, 0, 0, "st1"));
		listGhichu.add(new GhiChu("1", "DB", 1436207202, "khẩn trương",
				1436207202, 0, 0, 0, 1, "st1"));

		adapter = new GhichuAdapter(getActivity().getApplicationContext(),
				R.layout.itemlist_sotay, listGhichu);

		lvGhichu.setAdapter(adapter);

		flNoNote = (FrameLayout) view.findViewById(R.id.flNoNote);
		if (listGhichu.size() > 0) {
			flNoNote.setVisibility(View.GONE);
		} else {
			flNoNote.setVisibility(View.VISIBLE);
		}

		onClickListener();

		return view;
	}

	public void onClickListener() {
		lvGhichu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent note = new Intent(getActivity().getApplicationContext(),
						NoteActivity.class);
				note.putExtra("TYPE_NOTE", "READ");
				note.putExtra("NOTE", listGhichu.get(position));
				startActivity(note);
			}
		});

		lvGhichu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getActivity(), "Long Item click",
						Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		flNoNote.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent createNote = new Intent(getActivity()
						.getApplicationContext(), NoteActivity.class);
				createNote.putExtra("TYPE_NOTE", "CREATE_NEW");
				startActivity(createNote);
			}
		});
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.fragment_ghichu, menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch (id) {
		case R.id.action_ghichu_addnew:
			Intent createNote = new Intent(getActivity()
					.getApplicationContext(), NoteActivity.class);
			createNote.putExtra("TYPE_NOTE", "CREATE_NEW");
			startActivity(createNote);
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}
