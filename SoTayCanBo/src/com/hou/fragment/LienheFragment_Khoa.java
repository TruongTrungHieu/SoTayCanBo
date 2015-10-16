package com.hou.fragment;

import com.hou.adapters.DonviAdapter;
import com.hou.app.Global;
import com.hou.sotaycanbo.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class LienheFragment_Khoa extends Fragment {

	private ListView lvDonviKhoa;
	private DonviAdapter adapter;
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_lienhe_khoa, container,
				false);
		
		lvDonviKhoa = (ListView) view.findViewById(R.id.lvDonvi_Khoa);
		adapter = new DonviAdapter(getActivity().getApplicationContext(), R.layout.itemlist_donvi, Global.listDvKhoa);
		lvDonviKhoa.setAdapter(adapter);
		
		return view;
	}

}
