package com.hou.fragment;

import java.util.ArrayList;
import java.util.Calendar;

import com.hou.adapters.LichNgayAdapter;
import com.hou.app.Const;
import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.hou.models.DayNumberOfContents;
import com.hou.models.SuKien;
import com.hou.sotaycanbo.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class LichTuanFragment extends Fragment {
	private ArrayList<SuKien> listLichtuan;
	private ArrayList<DayNumberOfContents> parentItems;
	private ArrayList<SuKien> child2s;
	private ArrayList<SuKien> child3s;
	private ArrayList<SuKien> child4s;
	private ArrayList<SuKien> child5s;
	private ArrayList<SuKien> child6s;
	private ArrayList<SuKien> child7s;
	private ArrayList<SuKien> child8s;

	private ExpandableListView expandableList;
	private LichNgayAdapter adapter;

	// 2 is Monday
	private int posOpened = 2;
	private ExecuteQuery exeQ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		getActivity().setTitle(
				getActivity().getResources().getString(
						R.string.manager_lichtuan)
						+ " "
						+ Global.getPreference(getActivity(), Const.LICHTUAN));

		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek > 1) {
			posOpened = dayOfWeek - 2;
		} else {
			posOpened = 6;
		}
	}

	public void setGroupParents() {
		DayNumberOfContents day2 = new DayNumberOfContents(
				getString(R.string.Mon), child2s);
		parentItems.add(day2);
		DayNumberOfContents day3 = new DayNumberOfContents(
				getString(R.string.Tue), child3s);
		parentItems.add(day3);
		DayNumberOfContents day4 = new DayNumberOfContents(
				getString(R.string.Wed), child4s);
		parentItems.add(day4);
		DayNumberOfContents day5 = new DayNumberOfContents(
				getString(R.string.Thus), child5s);
		parentItems.add(day5);
		DayNumberOfContents day6 = new DayNumberOfContents(
				getString(R.string.Fri), child6s);
		parentItems.add(day6);
		DayNumberOfContents day7 = new DayNumberOfContents(
				getString(R.string.Sat), child7s);
		parentItems.add(day7);
		DayNumberOfContents day8 = new DayNumberOfContents(
				getString(R.string.Sun), child8s);
		parentItems.add(day8);
	}

	private void phanListCv(SuKien sk) {
		switch (sk.getThu()) {
		case "2":
			child2s.add(sk);
			break;
		case "3":
			child3s.add(sk);
			break;
		case "4":
			child4s.add(sk);
			break;
		case "5":
			child5s.add(sk);
			break;
		case "6":
			child6s.add(sk);
			break;
		case "7":
			child7s.add(sk);
			break;
		case "CN":
			child8s.add(sk);
			break;
		default:
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_lich_tuan, container, false);

		exeQ = new ExecuteQuery(getActivity());
		exeQ.createDatabase();
		exeQ.open();

		listLichtuan = new ArrayList<SuKien>();
		listLichtuan = exeQ.getAllSukienTuan();

		parentItems = new ArrayList<DayNumberOfContents>();
		child2s = new ArrayList<SuKien>();
		child3s = new ArrayList<SuKien>();
		child4s = new ArrayList<SuKien>();
		child5s = new ArrayList<SuKien>();
		child6s = new ArrayList<SuKien>();
		child7s = new ArrayList<SuKien>();
		child8s = new ArrayList<SuKien>();

		expandableList = (ExpandableListView) view.findViewById(R.id.elvDays);
		expandableList.setGroupIndicator(null);
		expandableList.setClickable(true);

		for (SuKien sk : listLichtuan) {
			phanListCv(sk);
		}

		setGroupParents();

		adapter = new LichNgayAdapter(getActivity().getApplicationContext(),
				parentItems);
		adapter.setInflater(inflater, getActivity());
		expandableList.setAdapter(adapter);
		expandableList.expandGroup(posOpened, true);

		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.fragment_lichtuan, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch (id) {
		case R.id.action_lichtuan_collapse:
			int count = adapter.getGroupCount();
			for (int i = 0; i < count; ++i) {
				expandableList.collapseGroup(i);
			}
			expandableList.expandGroup(posOpened, true);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
