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

public class LienheFragment_Trungtam extends Fragment {
	
	private ListView lvDonviTrungtam;
	private DonviAdapter adapter;
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_lienhe_trungtam, container, false);
		
		lvDonviTrungtam = (ListView) view.findViewById(R.id.lvDonvi_Trungtam);
		adapter = new DonviAdapter(getActivity().getApplicationContext(), R.layout.itemlist_donvi, Global.listDvTrungtam);
		
		lvDonviTrungtam.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});
		
		lvDonviTrungtam.setAdapter(adapter);
		
		return view;
	}
}
