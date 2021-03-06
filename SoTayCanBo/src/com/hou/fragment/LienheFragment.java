package com.hou.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.astuetz.PagerSlidingTabStrip;
import com.hou.app.Const;
import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.hou.models.DonVi;
import com.hou.sotaycanbo.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater
				.inflate(R.layout.fragment_lienhe, container, false);
		initView(view);
		setupTabs();
		exeQ = new ExecuteQuery(getActivity().getApplicationContext());
		exeQ.createDatabase();
		exeQ.open();

		listDonvi = new ArrayList<DonVi>();
		listDonvi = exeQ.getAllDonvi();

		if (listDonvi.size() <= 0) {
			if (Global.hasNetworkConnection(getActivity())) {
				getDonviFromServer();
			} else {
				Toast.makeText(getActivity().getBaseContext(),
						getString(R.string.check_internet), Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			for (DonVi dv : listDonvi) {
				String maNhomDonvi = dv.getMaNhomdonvi().charAt(0) + "";
				switch (maNhomDonvi) {
				case "1":
					Global.listDvTochuc.add(dv);
					break;
				case "2":
					Global.listDvPhong.add(dv);
					break;
				case "3":
					Global.listDvKhoa.add(dv);
					break;
				case "4":
					Global.listDvTrungtam.add(dv);
					break;
				default:
					break;
				}
			}
		}
		return view;
	}

	public void initView(View view) {
		pager = (ViewPager) view.findViewById(R.id.pager);
		pstWalletTabs = (PagerSlidingTabStrip) view
				.findViewById(R.id.pstWalletTabs);
	}

	public void setupTabs() {
		adapter = new MyPagerAdapter(getChildFragmentManager());
		pager.setAdapter(adapter);
		pstWalletTabs.setShouldExpand(true);
		pstWalletTabs.setViewPager(pager);
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = {
				getString(R.string.lienhe_title_tochuc),
				getString(R.string.lienhe_title_phong),
				getString(R.string.lienhe_title_khoa),
				getString(R.string.lienhe_title_trungtam) };

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
			Fragment fragment;
			switch (position) {
			case 0:
				fragment = new LienheFragment_Khoa(Global.listDvTochuc);
				break;
			case 1:
				fragment = new LienheFragment_Khoa(Global.listDvPhong);
				break;
			case 2:
				fragment = new LienheFragment_Khoa(Global.listDvKhoa);
				break;
			case 3:
				fragment = new LienheFragment_Khoa(Global.listDvTrungtam);
				break;
			default:
				fragment = new LienheFragment_Khoa(Global.listDvTochuc);
				break;
			}
			return fragment;
		}
	}

	private void getDonviFromServer() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		client.get(Const.URL_DONVI, params, new AsyncHttpResponseHandler() {
			public void onSuccess(String response) {
				saveDonviIntoSQLite(response);
				setupTabs();
			}

			@Override
			public void onFailure(int statusCode, Throwable error,
					String content) {
				switch (statusCode) {
				case 0:
					Toast.makeText(
							getActivity().getBaseContext(),
							getResources().getString(R.string.check_internet)
									+ " - " + statusCode, Toast.LENGTH_LONG)
							.show();
					break;
				default:
					Toast.makeText(
							getActivity().getBaseContext(),
							getResources().getString(R.string.database_error)
									+ " - " + statusCode, Toast.LENGTH_LONG)
							.show();
					break;
				}
			}
		});
	}

	private void saveDonviIntoSQLite(String response) {
		exeQ.delete_tblDonvi_allrecord();
		try {
			JSONArray arr = new JSONArray(response);
			for (int i = 0; i < arr.length(); ++i) {
				JSONObject donvi = arr.getJSONObject(i);

				String ma_dv = donvi.optString("ma_dv", "");
				String ten_dv = donvi.optString("ten_dv", "");
				String diachi_dv = donvi.optString("diachi_dv", "");
				String sdt_dv = donvi.optString("sdt_dv", "").replace("_", "").trim();
				String email_dv = donvi.optString("email_dv", "");
				String nhom_uutien = donvi.optString("nhom_uutien", "");
				
				String website = "";
				String fax = "";

				DonVi dv = new DonVi(ma_dv, ten_dv, sdt_dv, email_dv, website,
						fax, diachi_dv, nhom_uutien);

				exeQ.insert_tblDonvi_single(dv);
				listDonvi.add(dv);
			}

			for (DonVi dv : listDonvi) {
				String maNhomDonvi = dv.getMaNhomdonvi().charAt(0) + "";
				switch (maNhomDonvi) {
				case "1":
					Global.listDvTochuc.add(dv);
					break;
				case "2":
					Global.listDvPhong.add(dv);
					break;
				case "3":
					Global.listDvKhoa.add(dv);
					break;
				case "4":
					Global.listDvTrungtam.add(dv);
					break;
				default:
					break;
				}
			}
			setupTabs();
		} catch (JSONException e) {
			Log.e("saveSukienIntoSQL", e.getMessage());
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		listDonvi = new ArrayList<DonVi>();
		listDonvi = exeQ.getAllDonvi();

		Global.listDvTochuc = new ArrayList<DonVi>();
		Global.listDvKhoa = new ArrayList<DonVi>();
		Global.listDvPhong = new ArrayList<DonVi>();
		Global.listDvTrungtam = new ArrayList<DonVi>();
		
		if (listDonvi.size() > 0) {
			for (DonVi dv : listDonvi) {
				String maNhomDonvi = dv.getMaNhomdonvi().charAt(0) + "";
				switch (maNhomDonvi) {
				case "1":
					Global.listDvTochuc.add(dv);
					break;
				case "2":
					Global.listDvPhong.add(dv);
					break;
				case "3":
					Global.listDvKhoa.add(dv);
					break;
				case "4":
					Global.listDvTrungtam.add(dv);
					break;
				default:
					break;
				}
			}
			setupTabs();
		}
	}
}