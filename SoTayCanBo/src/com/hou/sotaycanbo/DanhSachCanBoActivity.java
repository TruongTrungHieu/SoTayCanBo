package com.hou.sotaycanbo;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hou.adapters.CanboAdapter;
import com.hou.app.Const;
import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.hou.models.CanBo;
import com.hou.models.DonVi;
import com.hou.quickaction.ActionItem;
import com.hou.quickaction.QuickAction;
import com.hou.ultis.ImageUltiFunctions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class DanhSachCanBoActivity extends ActionBarActivity {

	private ListView lvCanbo;

	private ExecuteQuery exeQ;
	private ArrayList<CanBo> listCanbo = null;
	private CanboAdapter adapter = null;

	private DonVi mDonvi;
	@SuppressWarnings("unused")
	private int pos = -1;

	// Quick Action
	private static final int ID_INFOR = 1;
	private static final int ID_CALL = 2;
	private static final int ID_SMS = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_danh_sach_can_bo);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		mDonvi = (DonVi) getIntent().getSerializableExtra("donvi");
		if (mDonvi != null) {
			this.setTitle(mDonvi.getTenDonvi().toString());
		}

		exeQ = new ExecuteQuery(getApplicationContext());
		exeQ.createDatabase();
		exeQ.open();

		/*
		 * Quick Action
		 */
		ActionItem inforItem = new ActionItem(ID_INFOR, "Infor", getResources()
				.getDrawable(R.drawable.quick_infor));
		ActionItem callItem = new ActionItem(ID_CALL, "Call", getResources()
				.getDrawable(R.drawable.quick_call));
		ActionItem smsItem = new ActionItem(ID_SMS, "SMS", getResources()
				.getDrawable(R.drawable.quick_sms));

		final QuickAction quickAction = new QuickAction(this,
				QuickAction.HORIZONTAL);

		quickAction.addActionItem(inforItem);
		quickAction.addActionItem(callItem);
		quickAction.addActionItem(smsItem);

		quickAction
				.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
					@Override
					public void onItemClick(QuickAction source, int pos,
							int actionId) {
						switch (actionId) {
						case ID_INFOR:
							Intent i = new Intent(DanhSachCanBoActivity.this,
									CanboActivity.class);
							i.putExtra("canbo_details", listCanbo.get(pos));
							startActivity(i);
							overridePendingTransition(R.anim.slide_in_right,
									R.anim.fade_out);
							break;
						case ID_CALL:
							String sdt = listCanbo.get(pos).getSdt();
							if (sdt.length() > 0) {
								Intent callIntent = new Intent(
										Intent.ACTION_CALL);
								callIntent.setData(Uri.parse("tel:" + sdt));
								startActivity(callIntent);
							}
							break;
						case ID_SMS:
							String sms = listCanbo.get(pos).getSdt();
							if (sms.length() > 0) {
								String uri = "smsto:" + sms;
								Intent intent = new Intent(
										Intent.ACTION_SENDTO, Uri.parse(uri));
								intent.putExtra("compose_mode", true);
								startActivity(intent);
							}
							break;
						default:
							break;
						}
					}
				});

		listCanbo = new ArrayList<CanBo>();
		listCanbo = exeQ.getAllCanboFromDV(mDonvi.getMaDonvi());

		if (listCanbo.size() <= 0) {
			if (Global.hasNetworkConnection(getApplicationContext())) {
				getCanBoByMaDVFromServer();
			}
		}

		lvCanbo = (ListView) findViewById(R.id.lvCanbo);
		adapter = new CanboAdapter(getApplicationContext(),
				R.layout.itemlist_canbo, listCanbo);

		lvCanbo.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				pos = position;
				quickAction.show(view);
			}
		});

		lvCanbo.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.danh_sach_can_bo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			onBackPressed();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void getCanBoByMaDVFromServer() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("madv", mDonvi.getMaDonvi());
		client.get(Const.URL_NHANVIEN_DONVI, params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						saveCanboIntoSQLite(response);
					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						switch (statusCode) {
						case 0:
							Toast.makeText(
									getBaseContext(),
									getResources().getString(
											R.string.check_internet)
											+ " - " + statusCode,
									Toast.LENGTH_LONG).show();
							break;
						default:
							Toast.makeText(
									getBaseContext(),
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
				String ma_dv = cbObj.optString("ma_dv", "");
				String anh_nv = cbObj.optString("anh_nv", "");

				CanBo cb = new CanBo(ma_nv, ma_dv, hoten_nv,
						chitietdiachihientai, email_nv, so_cmnd, ten_hocham,
						ten_hocvi, anh_nv, sdt);

				listCanbo.add(cb);
				exeQ.insert_tblCanbo_single(cb);
				DownloadAvatarAsync downAvatar = new DownloadAvatarAsync(cb);
				downAvatar.execute();
			}
		} catch (JSONException e) {
			Log.e("saveCanboIntoSQLite", e.getMessage());
		}
		adapter.notifyDataSetChanged();
	}

	class DownloadAvatarAsync extends AsyncTask<Void, Void, Void> {

		CanBo cb;

		public DownloadAvatarAsync(CanBo cb) {
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
			adapter.notifyDataSetChanged();
		}
	}
}
