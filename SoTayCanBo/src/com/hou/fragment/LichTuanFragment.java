package com.hou.fragment;

import java.util.ArrayList;

import com.hou.adapters.LichNgayAdapter;
import com.hou.models.DayNumberOfContents;
import com.hou.models.SuKien;
import com.hou.sotaycanbo.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class LichTuanFragment extends Fragment {
	ArrayList<SuKien> arrSuViec;
	private ArrayList<DayNumberOfContents> parentItems = new ArrayList<DayNumberOfContents>();
	private ArrayList<Object> childItems = new ArrayList<Object>();
	private ArrayList<SuKien> child2s = new ArrayList<SuKien>();
	private ArrayList<SuKien> child3s = new ArrayList<SuKien>();
	private ArrayList<SuKien> child4s = new ArrayList<SuKien>();
	private ArrayList<SuKien> child5s = new ArrayList<SuKien>();
	private ArrayList<SuKien> child6s = new ArrayList<SuKien>();
	private ArrayList<SuKien> child7s = new ArrayList<SuKien>();
	private ArrayList<SuKien> child8s = new ArrayList<SuKien>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

	}
	public void setGroupParents() {
		DayNumberOfContents day2 = new DayNumberOfContents(getString(R.string.Mon), child2s.size());
		parentItems.add(day2);
		DayNumberOfContents day3 = new DayNumberOfContents(getString(R.string.Tue), child3s.size());
		parentItems.add(day3);
		DayNumberOfContents day4 = new DayNumberOfContents(getString(R.string.Wed), child4s.size());
		parentItems.add(day4);
		DayNumberOfContents day5 = new DayNumberOfContents(getString(R.string.Thus), child5s.size());
		parentItems.add(day5);
		DayNumberOfContents day6 = new DayNumberOfContents(getString(R.string.Fri), child6s.size());
		parentItems.add(day6);
		DayNumberOfContents day7 = new DayNumberOfContents(getString(R.string.Sat), child7s.size());
		parentItems.add(day7);
		DayNumberOfContents day8 = new DayNumberOfContents(getString(R.string.Sun), child7s.size());
		parentItems.add(day8);
		/*parentItems.add(getString(R.string.Tue));
		parentItems.add(getString(R.string.Wed));
		parentItems.add(getString(R.string.Thus));
		parentItems.add(getString(R.string.Fri));
		parentItems.add(getString(R.string.Sat));
		parentItems.add(getString(R.string.Sun));*/
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
		case "8":
			child8s.add(sk);
			break;
		default:
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_lich_tuan, container, false);
		SuKien sv1 = new SuKien("sukien1", "2", "Sáng", 12/10/2015, "Dulibu version 1.0 - bản thứ nhất", "p33", "96 DC", "member", "7.00 am", 1, "note có rất nhiều thứ để mong đợi :v hi vọng nó sẽ thành công", "Ra mắt version1");
		SuKien sv2 = new SuKien("sukien2","3", "Sáng", 13/10/2015, "Dulibu version 2.0", "p33", "96 DC","member", "7.00 am", 1, "note", "Ra mắt version1");
		SuKien sv3 = new SuKien("sukien3", "3", "Sáng", 14/10/2015, "Dulibu version 3.0", "p33", "96 DC", "member", "7.00 am", 1, "note", "Ra mắt version1");
		SuKien sv4 = new SuKien("sukien3", "2", "Chiều", 14/10/2015, "Dulibu version 4.0", "p33", "96 DC", "member", "2.00 pm", 1, "note", "Ra mắt version1");
		arrSuViec = new ArrayList<SuKien>();
		arrSuViec.add(sv1);
		arrSuViec.add(sv2);
		arrSuViec.add(sv3);
		arrSuViec.add(sv4);
		
		ExpandableListView expandableList = (ExpandableListView) view.findViewById(R.id.elvDays);
		expandableList.setGroupIndicator(null);
		expandableList.setClickable(true);

		
		for (SuKien sk : arrSuViec) {
			if(sk.getBuoi().equalsIgnoreCase("Sáng")){
			phanListCv(sk);
			}
		}
		for (SuKien sk : arrSuViec) {
			if(sk.getBuoi().equalsIgnoreCase("Chiều")){
			phanListCv(sk);
			}
		}
		setGroupParents();
		setChildData();

		LichNgayAdapter adapter = new LichNgayAdapter(parentItems, childItems);
		adapter.setInflater(inflater, getActivity());
		expandableList.setAdapter(adapter);
		return view;
	}
}
