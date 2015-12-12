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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

public class LichTuanFragment extends Fragment {
	private ArrayList<SuKien> listLichtuan;
	private ArrayList<DayNumberOfContents> parentItems;
	private ArrayList<Object> childItems;
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
				getString(R.string.Mon), child2s.size());
		parentItems.add(day2);
		DayNumberOfContents day3 = new DayNumberOfContents(
				getString(R.string.Tue), child3s.size());
		parentItems.add(day3);
		DayNumberOfContents day4 = new DayNumberOfContents(
				getString(R.string.Wed), child4s.size());
		parentItems.add(day4);
		DayNumberOfContents day5 = new DayNumberOfContents(
				getString(R.string.Thus), child5s.size());
		parentItems.add(day5);
		DayNumberOfContents day6 = new DayNumberOfContents(
				getString(R.string.Fri), child6s.size());
		parentItems.add(day6);
		DayNumberOfContents day7 = new DayNumberOfContents(
				getString(R.string.Sat), child7s.size());
		parentItems.add(day7);
		DayNumberOfContents day8 = new DayNumberOfContents(
				getString(R.string.Sun), child8s.size());
		parentItems.add(day8);
	}

	public void setChildData() {
		childItems.add(child2s);
		childItems.add(child3s);
		childItems.add(child4s);
		childItems.add(child5s);
		childItems.add(child6s);
		childItems.add(child7s);
		childItems.add(child8s);
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
		childItems = new ArrayList<Object>();
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
		setChildData();

		adapter = new LichNgayAdapter(parentItems, childItems);
		adapter.setInflater(inflater, getActivity());
		expandableList.setAdapter(adapter);
		expandableList.expandGroup(posOpened, true);

	
		expandableList.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Toast.makeText(getActivity(), "Group " + groupPosition + "Child " + childPosition, Toast.LENGTH_SHORT).show();
				
				return false;
			}
		});
		
		return view;
	}

}
