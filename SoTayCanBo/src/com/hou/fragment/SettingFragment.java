package com.hou.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hou.app.Const;
import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.hou.models.CanBo;
import com.hou.models.Chucvu_Canbo;
import com.hou.models.DonVi;
import com.hou.sotaycanbo.DoiMatkhauActivity;
import com.hou.sotaycanbo.HelpActivity;
import com.hou.sotaycanbo.LoginActivity;
import com.hou.sotaycanbo.R;
import com.hou.ultis.ImageUltiFunctions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

public class SettingFragment extends Fragment {

	private LinearLayout llChangePass, llSaveLogin, llLogout, llGetNotif,
			llAutoUpdate, llHelp;
	private Switch swSaveLogin, swGetNotif;
	private Boolean isSaveLogin, isGetNotif;
	private ProgressDialog pbUpdate;
	private ExecuteQuery exeQ;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		exeQ = new ExecuteQuery(getActivity());
		exeQ.createDatabase();
		exeQ.open();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_setting, container, false);

		llChangePass = (LinearLayout) v.findViewById(R.id.llChangePass);
		llSaveLogin = (LinearLayout) v.findViewById(R.id.llSaveLogin);
		llLogout = (LinearLayout) v.findViewById(R.id.llLogout);
		llGetNotif = (LinearLayout) v.findViewById(R.id.llGetNotif);
		llAutoUpdate = (LinearLayout) v.findViewById(R.id.llAutoUpdate);
		llHelp = (LinearLayout) v.findViewById(R.id.llHelp);
		swSaveLogin = (Switch) v.findViewById(R.id.swSaveLogin);
		swGetNotif = (Switch) v.findViewById(R.id.swGetNotify);

		String save = Global.getPreference(getActivity(), Const.SAVE_LOGIN);
		if (save.equals(Const.SAVE_LOGIN_TRUE)) {
			isSaveLogin = true;
		} else {
			isSaveLogin = false;
		}
		String get = Global.getPreference(getActivity(), Const.GET_NOTIF);
		if (get.equals(Const.GET_NOTIF_TRUE) || get.equals("") || get == null) {
			isGetNotif = true;
		} else {
			isGetNotif = false;
		}
		swSaveLogin.setChecked(isSaveLogin);
		swGetNotif.setChecked(isGetNotif);

		swSaveLogin
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						isSaveLogin = !isSaveLogin;
						swSaveLogin.setChecked(isSaveLogin);
						Global.savePreference(getActivity(), Const.SAVE_LOGIN,
								isSaveLogin ? Const.SAVE_LOGIN_TRUE
										: Const.SAVE_LOGIN_FALSE);
					}
				});

		swGetNotif
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						isGetNotif = !isGetNotif;
						swGetNotif.setChecked(isGetNotif);
						Global.savePreference(getActivity(), Const.GET_NOTIF,
								isGetNotif ? Const.GET_NOTIF_TRUE
										: Const.GET_NOTIF_FALSE);
					}
				});

		llChangePass.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent changepass = new Intent(getActivity()
						.getApplicationContext(), DoiMatkhauActivity.class);
				changepass.putExtra("isFirstLogin", false);
				startActivity(changepass);
			}
		});

		llSaveLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		llLogout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.savePreference(getActivity().getApplicationContext(),
						Const.SAVE_LOGIN, Const.SAVE_LOGIN_FALSE);
				Global.savePreference(getActivity().getApplicationContext(),
						Const.GET_NOTIF, Const.GET_NOTIF_TRUE);
				Global.savePreference(getActivity().getApplicationContext(),
						Const.USER_MACANBO, "");

				Intent login = new Intent(
						getActivity().getApplicationContext(),
						LoginActivity.class);
				startActivity(login);
			}
		});

		llGetNotif.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		llAutoUpdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Global.hasNetworkConnection(getActivity())) {
					pbUpdate = new ProgressDialog(getActivity());
					pbUpdate.setMessage(getString(R.string.setting_app_update));
					pbUpdate.setIndeterminate(false);
					pbUpdate.setCancelable(false);
					pbUpdate.show();
					updateDonvi();
					updateCanbo();
				} else {
					Toast.makeText(getActivity().getBaseContext(),
							getString(R.string.no_internet), Toast.LENGTH_LONG)
							.show();
				}
			}
		});

		llHelp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent help = new Intent(getActivity().getApplicationContext(), HelpActivity.class);
				startActivity(help);
			}
		});
		return v;
	}

	private void updateDonvi() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		client.get(Const.URL_DONVI, params, new AsyncHttpResponseHandler() {
			public void onSuccess(String response) {
				saveDonviIntoSQLite(response);
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
			}
		} catch (JSONException e) {
			Log.e("saveSukienIntoSQL", e.getMessage());
		}
	}

	private void updateCanbo() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		client.get(Const.URL_NHANVIEN_ALL, params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Global.savePreference(getActivity(),
								Const.DOWNLOAD_FIRST, "done");
						saveCanboIntoSQLite(response);
					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						switch (statusCode) {
						case 0:
							Toast.makeText(
									getActivity().getBaseContext(),
									getResources().getString(
											R.string.check_internet)
											+ " - " + statusCode,
									Toast.LENGTH_LONG).show();
							break;
						default:
							Toast.makeText(
									getActivity().getBaseContext(),
									getResources().getString(
											R.string.database_error)
											+ " - " + statusCode,
									Toast.LENGTH_LONG).show();
							break;
						}
					}
				});
	}

	private void saveCanboIntoSQLite(String response) {
		exeQ.delete_tblCanbo_allrecord();
		exeQ.delete_tbl_chucvu_canbo_allrecord();
		try {
			JSONArray arr = new JSONArray(response);
			for (int i = 0; i < arr.length(); ++i) {
				JSONObject cbObj = arr.getJSONObject(i);
				String ma_nv = cbObj.optString("ma_nv", "");
				String hoten_nv = cbObj.optString("hoten_nv", "");
				String chitietdiachihientai = cbObj.optString(
						"chitietdiachihientai", "");
				String sdt = cbObj.optString("sdt", "").replace("_", "").trim();
				String email_nv = cbObj.optString("email_nv", "");
				String so_cmnd = cbObj.optString("so_cmnd", "");
				String ten_hocvi = cbObj.optString("ten_hocvi", "");
				String ten_hocham = cbObj.optString("ten_hocham", "");
				String anh_nv = cbObj.optString("anh_nv", "");

				CanBo cb = new CanBo(ma_nv, hoten_nv, chitietdiachihientai,
						email_nv, so_cmnd, ten_hocham, ten_hocvi, anh_nv, sdt,
						"", "");
				exeQ.insert_tblCanbo_single(cb);
				
				JSONArray chucvuArr = cbObj.getJSONArray("ds_dvcv");
				for (int j = 0;j < chucvuArr.length(); ++j) {
					JSONObject objCC = chucvuArr.getJSONObject(j);
					String maDonvi = objCC.optString("ma_dv", "");
					String tenChucvu = objCC.optString("ten_dmcv", "");
					String uutien = objCC.optString("uutien", "");
					
					Chucvu_Canbo cc = new Chucvu_Canbo(ma_nv, maDonvi, tenChucvu, uutien);
					exeQ.insert_tbl_chucvu_canbo_single(cc);
				}
				
				DownloadAvatarCBAsync downAvatar = new DownloadAvatarCBAsync(cb);
				downAvatar.execute();
			}
			pbUpdate.dismiss();
			Toast.makeText(getActivity().getBaseContext(),
					getString(R.string.setting_app_update_success),
					Toast.LENGTH_LONG).show();
		} catch (JSONException e) {
			Log.e("saveCanboIntoSQLite", e.getMessage());
		}
	}

	class DownloadAvatarCBAsync extends AsyncTask<Void, Void, Void> {

		CanBo cb;

		public DownloadAvatarCBAsync(CanBo cb) {
			// TODO Auto-generated constructor stub
			this.cb = cb;
		}

		@Override
		protected Void doInBackground(Void... params) {
			ImageUltiFunctions.downloadFileFromServer(cb.getAvatar());
			publishProgress();
			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
	}

}
