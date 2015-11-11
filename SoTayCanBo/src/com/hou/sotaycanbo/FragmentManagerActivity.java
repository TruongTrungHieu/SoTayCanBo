package com.hou.sotaycanbo;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hou.app.Const;
import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.hou.fragment.CalendarFragment;
import com.hou.fragment.GhichuFragment;
import com.hou.fragment.LichTuanFragment;
import com.hou.fragment.LienheFragment;
import com.hou.fragment.SotayFragment;
import com.hou.models.CanBo;
import com.hou.models.SuKien;
import com.hou.ultis.ImageUltiFunctions;
import com.hou.fragment.ThongTinCanBoFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;

public class FragmentManagerActivity extends MaterialNavigationDrawer<Object>
		implements MaterialAccountListener {

	private MaterialAccount account;

	private MaterialSection<?> SoTay;
	private MaterialSection<?> GhiChu;
	private MaterialSection<?> Lich;
	private MaterialSection<?> LichTuan;
	private MaterialSection<?> LienHe;
	// private MaterialSection<?> DongBo;
	private MaterialSection<?> CaiDat;
	// private MaterialSection<?> ThongTin;
	private MaterialSection<?> ThongTinCanBo;

	public static FragmentManagerActivity sInstance;

	private ExecuteQuery exeQ;

	public MaterialAccount getAccount() {
		return account;
	}

	public void setAccount(MaterialAccount account) {
		this.account = account;
	}

	@Override
	public void init(Bundle savedInstanceState) {
		sInstance = this;
		this.disableLearningPattern();

		exeQ = new ExecuteQuery(getApplicationContext());
		exeQ.createDatabase();
		exeQ.open();

		saveInfoCanbo();
		String tenDonvi = exeQ.getTendonviByMadonvi(Global.getPreference(
				getApplicationContext(), Const.USER_MADONVI));
		String tenCanbo = Global.getPreference(getApplicationContext(),
				Const.USER_HOTEN);
		String anh = Global.getPreference(getApplicationContext(),
				Const.USER_ANH);
		File f = ImageUltiFunctions.getFileFromUri(Global.getURI(anh));
		if (f != null) {
			Bitmap b = ImageUltiFunctions.decodeSampledBitmapFromFile(f, 500,
					500);
			account = new MaterialAccount(this.getResources(), tenCanbo,
					tenDonvi, b, R.drawable.bg);
		} else {
			account = new MaterialAccount(this.getResources(), tenCanbo,
					tenDonvi, R.drawable.account_ava, R.drawable.bg);
			DownloadProfileAvatarAsync download = new DownloadProfileAvatarAsync();
			download.execute();
		}

		this.addAccount(account);

		this.setAccountListener(this);
		this.setDrawerBackgroundColor(this.getResources().getColor(
				R.color.white));

		GhiChu = newSection(getResources().getString(R.string.manager_ghichu),
				R.drawable.menu_ghichu_material, new GhichuFragment());

		SoTay = newSection(getResources().getString(R.string.manager_sotay),
				R.drawable.menu_sotay_material, new SotayFragment());

		Lich = newSection(getResources().getString(R.string.manager_lich),
				R.drawable.menu_lich_material, new CalendarFragment());

		LichTuan = newSection(
				getResources().getString(R.string.manager_lichtuan),
				R.drawable.menu_lichtuan_material, new LichTuanFragment());

		LienHe = newSection(getResources().getString(R.string.manager_lienhe),
				R.drawable.menu_lienhe_material, new LienheFragment());

		// DongBo =
		// newSection(getResources().getString(R.string.manager_dongbo),
		// R.drawable.menu_dongbo_material, new SotayFragment());

		CaiDat = newSection(getResources().getString(R.string.manager_caidat),
				R.drawable.menu_caidat_material, new SotayFragment());

		// ThongTin = newSection(
		// getResources().getString(R.string.manager_thongtin),
		// R.drawable.menu_thongtin_material, new SotayFragment());

		ThongTinCanBo = newSection(
				getResources().getString(R.string.manager_thongtincanbo),
				R.drawable.menu_thongtin_material, new ThongTinCanBoFragment());

		this.addSection(GhiChu);
		this.addSection(SoTay);
		this.addSection(Lich);
		this.addSection(LichTuan);
		this.addSection(LienHe);
		this.addSection(ThongTinCanBo);
		// this.addSection(DongBo);
		this.addBottomSection(CaiDat);
		// this.addSection(ThongTin);

		if (Global.hasNetworkConnection(getApplicationContext())) {
			getSukienFromServer();
		}
	}

	private void saveInfoCanbo() {
		CanBo cb;
		cb = (CanBo) getIntent().getSerializableExtra("canbo");
		if (cb != null) {
			Global.savePreference(getApplicationContext(), Const.USER_MACANBO,
					cb.getMaCanbo());
			Global.savePreference(getApplicationContext(), Const.USER_HOTEN,
					cb.getTenCanbo());
			Global.savePreference(getApplicationContext(), Const.USER_DIACHI,
					cb.getDiachi());
			Global.savePreference(getApplicationContext(), Const.USER_SDT,
					cb.getSdt());
			Global.savePreference(getApplicationContext(), Const.USER_EMAIL,
					cb.getEmail());
			Global.savePreference(getApplicationContext(), Const.USER_CMND,
					cb.getCmnd());
			Global.savePreference(getApplicationContext(), Const.USER_HOCVI,
					cb.getHocvi());
			Global.savePreference(getApplicationContext(), Const.USER_HOCHAM,
					cb.getHocham());
			Global.savePreference(getApplicationContext(), Const.USER_ANH,
					cb.getAvatar());
			Global.savePreference(getApplicationContext(), Const.USER_MADONVI,
					cb.getMaDonvi());
		}
	}

	@Override
	public void onAccountOpening(MaterialAccount account) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onChangeAccount(MaterialAccount newAccount) {
		// TODO Auto-generated method stub
	}

	private void getSukienFromServer() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		client.get(Const.URL_SUKIEN, params, new AsyncHttpResponseHandler() {
			public void onSuccess(String response) {
				saveSukienIntoSQL(response);
			}

			@Override
			public void onFailure(int statusCode, Throwable error,
					String content) {
				switch (statusCode) {
				case 0:
					Toast.makeText(
							getBaseContext(),
							getResources().getString(R.string.check_internet)
									+ " - " + statusCode, Toast.LENGTH_LONG)
							.show();
					break;
				default:
					Toast.makeText(
							getBaseContext(),
							getResources().getString(R.string.database_error)
									+ " - " + statusCode, Toast.LENGTH_LONG)
							.show();
					break;
				}
			}
		});
	}

	private void saveSukienIntoSQL(String response) {
		exeQ.delete_tblSukienTuan_allrecord();
		try {
			JSONArray arr = new JSONArray(response);
			for (int i = 0; i < arr.length(); ++i) {
				JSONObject lich = arr.getJSONObject(i);

				String ma_sk = lich.optString("ma_sk", "");
				String ten_sk = lich.optString("ten_sk", "");
				String tp_thamgiakhac = lich.optString("tp_thamgiakhac", "");
				String tg_batdau = lich.optString("tg_batdau", "");
				String dia_diem = lich.optString("dia_diem", "");
				String noi_dung = lich.optString("noi_dung", "");
				String ten_phong = lich.optString("ten_phong", "");
				String sang_chieu = lich.optString("sang_chieu", "");
				String thu = lich.optString("thu", "");
				String ngay = lich.optString("ngay", "");
				String ds_thamgia_chitiet = lich.optString(
						"ds_thamgia_chitiet", "");

				SuKien sk = new SuKien(ma_sk, thu, sang_chieu, ngay, ten_sk,
						ten_phong, dia_diem, ds_thamgia_chitiet + "##"
								+ tp_thamgiakhac, tg_batdau, 1, "", noi_dung);

				exeQ.insert_tblSukienTuan_single(sk);
			}
		} catch (JSONException e) {
			Log.e("saveSukienIntoSQL", e.getMessage());
		}
	}

	class DownloadProfileAvatarAsync extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			String imageName = Global.getPreference(getApplicationContext(),
					Const.USER_ANH);
			ImageUltiFunctions.downloadFileFromServer(imageName);
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
