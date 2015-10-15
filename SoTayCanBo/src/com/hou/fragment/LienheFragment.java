package com.hou.fragment;

import com.astuetz.PagerSlidingTabStrip;
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
        pstWalletTabs = (PagerSlidingTabStrip) view.findViewById(R.id.pstWalletTabs);
	}
	
	public void setupTabs(){
		adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        int pagerMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
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
