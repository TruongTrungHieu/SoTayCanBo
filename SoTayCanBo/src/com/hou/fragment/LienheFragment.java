package com.hou.fragment;

import java.util.ArrayList;

import com.astuetz.PagerSlidingTabStrip;
import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.hou.models.DonVi;
import com.hou.sotaycanbo.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LienheFragment extends Fragment {
	PagerSlidingTabStrip pstWalletTabs;
	ViewPager pager;

	private ExecuteQuery exeQ;
	private ArrayList<DonVi> listDonvi;

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);

		exeQ = new ExecuteQuery(getActivity().getApplicationContext());
		exeQ.createDatabase();
		exeQ.open();

		listDonvi = new ArrayList<DonVi>();
		// listDonvi = exeQ.getAllDonvi();

		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 �?ịnh Công, Hoàng Mai, Hà Nội", "khoa"));
		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 �?ịnh Công, Hoàng Mai, Hà Nội", "khoa"));
		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 �?ịnh Công, Hoàng Mai, Hà Nội", "khoa"));
		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 �?ịnh Công, Hoàng Mai, Hà Nội", "khoa"));
		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 �?ịnh Công, Hoàng Mai, Hà Nội", "khoa"));
		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 �?ịnh Công, Hoàng Mai, Hà Nội", "khoa"));
		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 �?ịnh Công, Hoàng Mai, Hà Nội", "khoa"));
		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 �?ịnh Công, Hoàng Mai, Hà Nội", "khoa"));
		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 �?ịnh Công, Hoàng Mai, Hà Nội", "khoa"));
		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 �?ịnh Công, Hoàng Mai, Hà Nội", "khoa"));

		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 �?ịnh Công, Hoàng Mai, Hà Nội", "phong"));

		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 �?ịnh Công, Hoàng Mai, Hà Nội",
				"trungtam"));

		Global.listDvPhong = new ArrayList<DonVi>();
		Global.listDvKhoa = new ArrayList<DonVi>();
		Global.listDvTrungtam = new ArrayList<DonVi>();

		for (DonVi dv : listDonvi) {
			String maNhomDonvi = dv.getMaNhomdonvi();
			switch (maNhomDonvi) {
			case "phong":
				Global.listDvPhong.add(dv);
				break;
			case "khoa":
				Global.listDvKhoa.add(dv);
				break;
			case "trungtam":
				Global.listDvTrungtam.add(dv);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void onResume() {
	    super.onResume();  	    
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_lienhe, container, false);
		initView(view);
		return view;
	}

	public void initView(View view) {
		pstWalletTabs = (PagerSlidingTabStrip) view.findViewById(R.id.pstWalletTabs);
		pager = (ViewPager) view.findViewById(R.id.pager);
	}

}
