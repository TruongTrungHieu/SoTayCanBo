package com.hou.fragment;

import com.hou.adapters.DonviAdapter;
import com.hou.app.Global;
import com.hou.sotaycanbo.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class LienheFragment_Phong extends Fragment {
	
	private ListView lvDonviPhong;
	private DonviAdapter adapter;
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_lienhe_phong, container,
				false);
		
		lvDonviPhong = (ListView) view.findViewById(R.id.lvDonvi_Phong);
		adapter = new DonviAdapter(getActivity().getApplicationContext(), R.layout.itemlist_donvi, Global.listDvPhong);
		
		lvDonviPhong.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});
		
		lvDonviPhong.setAdapter(adapter);
		
		return view;
	}

}
