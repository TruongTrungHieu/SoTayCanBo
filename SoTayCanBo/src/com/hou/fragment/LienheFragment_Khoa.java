package com.hou.fragment;

import com.hou.adapters.DonviAdapter;
import com.hou.app.Global;
import com.hou.sotaycanbo.DanhSachCanBoActivity;
import com.hou.sotaycanbo.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
		
		lvDonviKhoa.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), DanhSachCanBoActivity.class);
				i.putExtra("donvi", Global.listDvKhoa.get(position));
				startActivity(i);
				getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
			}
		});
		
		lvDonviKhoa.setAdapter(adapter);
		
		return view;
	}

	public void onResume() {
		super.onResume();
		adapter = new DonviAdapter(getActivity().getApplicationContext(), R.layout.itemlist_donvi, Global.listDvKhoa);
		lvDonviKhoa.setAdapter(adapter);
	}
}
