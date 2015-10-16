package com.hou.fragment;

import java.util.ArrayList;

import com.astuetz.PagerSlidingTabStrip;
import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.hou.models.DonVi;
import com.hou.sotaycanbo.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LienheFragment extends Fragment {

	private ViewPager pager;
	private PagerSlidingTabStrip pstWalletTabs;
	private MyPagerAdapter adapter;

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
				"(+84)0403345654", "96 Định Công, Hoàng Mai, Hà Nội", "khoa"));
		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 Định Công, Hoàng Mai, Hà Nội", "khoa"));
		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 Định Công, Hoàng Mai, Hà Nội", "khoa"));
		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 Định Công, Hoàng Mai, Hà Nội", "khoa"));
		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 Định Công, Hoàng Mai, Hà Nội", "khoa"));
		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 Định Công, Hoàng Mai, Hà Nội", "khoa"));
		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 Định Công, Hoàng Mai, Hà Nội", "khoa"));
		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 Định Công, Hoàng Mai, Hà Nội", "khoa"));
		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 Định Công, Hoàng Mai, Hà Nội", "khoa"));
		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 Định Công, Hoàng Mai, Hà Nội", "khoa"));

		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 Định Công, Hoàng Mai, Hà Nội", "phong"));

		listDonvi.add(new DonVi("1", "Khoa công nghệ thông tin",
				"(+84)0403345654", "fithou@gmail.com", "fithou.edu.com.vn",
				"(+84)0403345654", "96 Định Công, Hoàng Mai, Hà Nội",
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater
				.inflate(R.layout.fragment_lienhe, container, false);
		initView(view);
		setupTabs();
		return view;
	}

	public void initView(View view) {
		pager = (ViewPager) view.findViewById(R.id.pager);
		pstWalletTabs = (PagerSlidingTabStrip) view
				.findViewById(R.id.pstWalletTabs);
	}

	public void setupTabs() {
		adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
		int pagerMargin = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 10, getResources()
						.getDisplayMetrics());
		pager.setPageMargin(pagerMargin);
		pager.setAdapter(adapter);
		pstWalletTabs.setShouldExpand(true);
		pstWalletTabs.setViewPager(pager);
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "Khoa", "Phòng", "Trung tâm" };

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new Fragment();
			switch (position) {
			case 0:
				fragment = new LienheFragment_Khoa();
				break;
			case 1:
				fragment = new LienheFragment_Phong();
				break;
			case 2:
				fragment = new LienheFragment_Trungtam();
				break;
			}
			return fragment;
		}
	}

}
