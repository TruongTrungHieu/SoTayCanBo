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
		lvDonviPhong
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						Intent i = new Intent(getActivity(),
								DanhSachCanBoActivity.class);
						i.putExtra("donvi", Global.listDvPhong.get(position));
						startActivity(i);
						getActivity().overridePendingTransition(
								R.anim.slide_in_right, R.anim.fade_out);
					}
				});
		adapter = new DonviAdapter(getActivity(),
				R.layout.itemlist_donvi, Global.listDvPhong);
		lvDonviPhong.setAdapter(adapter);
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}

}
