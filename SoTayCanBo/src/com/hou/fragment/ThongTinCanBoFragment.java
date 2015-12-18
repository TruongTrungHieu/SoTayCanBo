package com.hou.fragment;

import java.io.File;
import java.io.FileNotFoundException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hou.app.Const;
import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.hou.sotaycanbo.FragmentManagerActivity;
import com.hou.sotaycanbo.R;
import com.hou.ultis.CircularImageView;
import com.hou.ultis.ImageUltiFunctions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ThongTinCanBoFragment extends Fragment implements OnClickListener {

	private EditText edtFullName, edtSdt, edtEmail, edtDiachi;
	private TextView tvUsername, tvDonvi;
	private CircularImageView imgAvatar;
	private ExecuteQuery exeQ;
	private Menu mMenu;

	private String email;
	private String sdt;
	private String address;

	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_FILE = 2;
	private File fromCameraFile;
	private Uri mImageCaptureUri;

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_profile, null);

		tvUsername = (TextView) view.findViewById(R.id.tvUserNameF);
		tvDonvi = (TextView) view.findViewById(R.id.tvDonviF);
		edtFullName = (EditText) view.findViewById(R.id.edtFullNameF);
		edtSdt = (EditText) view.findViewById(R.id.edtSdtF);
		edtEmail = (EditText) view.findViewById(R.id.edtEmailF);
		edtDiachi = (EditText) view.findViewById(R.id.edtDiachiF);
		imgAvatar = (CircularImageView) view.findViewById(R.id.imgAvatarF);
		imgAvatar.setEnabled(false);
		imgAvatar.setOnClickListener(this);

		exeQ = new ExecuteQuery(getActivity());
		exeQ.createDatabase();
		exeQ.open();

		initData();

		return view;
	}

	private void initData() {
		// TODO Auto-generated method stub
		String maDonvi = Global
				.getPreference(getActivity(), Const.USER_MADONVI);
		String tenDonvi = exeQ.getTendonviByMadonvi(maDonvi);
		tvDonvi.setText(tenDonvi);

		String hoten = Global.getPreference(getActivity(), Const.USER_HOTEN);
		String sdt = Global.getPreference(getActivity(), Const.USER_SDT);
		String diachi = Global.getPreference(getActivity(), Const.USER_DIACHI);
		String email = Global.getPreference(getActivity(), Const.USER_EMAIL);

		if (hoten == null || hoten.equals("null")) {
			tvUsername.setText("");
			edtFullName.setText("");
		} else {
			tvUsername.setText(hoten);
			edtFullName.setText(hoten);
		}

		if (sdt == null || sdt.equals("null")) {
			edtSdt.setText("");
		} else {
			edtSdt.setText(sdt);
		}

		if (email == null || email.equals("null")) {
			edtEmail.setText("");
		} else {
			edtEmail.setText(email);
		}
		if (diachi == null || diachi.equals("null")) {
			edtDiachi.setText("");
		} else {
			edtDiachi.setText(diachi);
		}

		String name = Global.getPreference(getActivity(), Const.USER_ANH);
		String uri = Global.getURI(name);
		File f = ImageUltiFunctions.getFileFromUri(uri);
		if (f != null) {
			Bitmap b = ImageUltiFunctions.decodeSampledBitmapFromFile(f, 500,
					500);
			imgAvatar.setImageBitmap(b);
		} else {
			DownloadAvatarAsync down = new DownloadAvatarAsync(
					Global.getPreference(getActivity(), Const.USER_ANH));
			down.execute();
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.fragment_profile, menu);
		super.onCreateOptionsMenu(menu, inflater);
		mMenu = menu;
		mMenu.getItem(0).setVisible(true);
		mMenu.getItem(1).setVisible(false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch (id) {
		case R.id.action_thongtin_edit:
			setEnable(true);
			break;
		case R.id.action_thongtin_done:
			if (checkValidate()) {
				setEnable(false);
				updateProfile();
			}
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void setEnable(boolean isEnable) {
		mMenu.getItem(0).setVisible(!isEnable);
		mMenu.getItem(1).setVisible(isEnable);
		edtSdt.setEnabled(isEnable);
		edtEmail.setEnabled(isEnable);
		edtDiachi.setEnabled(isEnable);
		imgAvatar.setEnabled(isEnable);
	}

	private boolean checkValidate() {
		sdt = edtSdt.getText().toString().trim();
		email = edtEmail.getText().toString().trim();
		address = edtDiachi.getText().toString().trim();
		if (sdt.equals("") || email.equals("") || address.equals("")) {
			Toast.makeText(getActivity().getBaseContext(),
					getString(R.string.profile_validate), Toast.LENGTH_LONG)
					.show();
			return false;
		} else {
			return true;
		}
	}

	private void updateProfile() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ma_nv",
				Global.getPreference(getActivity(), Const.USER_MACANBO));
		params.put("email_nv", email);
		params.put("sdt_nv", sdt);
		params.put("dc_nv", address);
		client.post(Const.URL_UPDATE_PROFILE, params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						updateSuccess(response);
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

	private void updateSuccess(String respone) {
		try {
			JSONObject obj = new JSONObject(respone);
			String status = obj.optString("status", "");
			if (!status.equals("false")) {
				// succes
				if (status.equals("true")) {
					JSONArray arr = obj.getJSONArray("ttnhanvien");
					JSONObject ttnhanvien = arr.getJSONObject(0);
					String email = ttnhanvien.optString("email_nv", "");
					String sdt = ttnhanvien.optString("sdt", "");
					String add = ttnhanvien.optString("chitietdiachihientai",
							"");

					Global.savePreference(getActivity(), Const.USER_EMAIL,
							email);
					Global.savePreference(getActivity(), Const.USER_SDT, sdt);
					Global.savePreference(getActivity(), Const.USER_DIACHI, add);

					Toast.makeText(getActivity().getBaseContext(),
							getString(R.string.profile_update_success),
							Toast.LENGTH_LONG).show();
				} else {
					// nothing
					Toast.makeText(getActivity().getBaseContext(),
							getString(R.string.profile_update_success),
							Toast.LENGTH_LONG).show();
				}
			} else {
				// false
				Toast.makeText(getActivity().getBaseContext(),
						getString(R.string.profile_update_fail),
						Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.imgAvatarF:
			new ImageDialog(getActivity(), R.layout.select_image_layout).show();
			break;
		default:
			break;
		}
	}

	class DownloadAvatarAsync extends AsyncTask<Void, Void, Void> {

		String fileName;

		public DownloadAvatarAsync(String fileName) {
			// TODO Auto-generated constructor stub
			this.fileName = fileName;
		}

		@Override
		protected Void doInBackground(Void... params) {
			ImageUltiFunctions.downloadFileFromServer(fileName);
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
//			File f = new File(ImageUltiFunctions.getRealPathFromURI(Global.getURI(fileName), getActivity()));
//			if (f != null) {
//				Bitmap b = ImageUltiFunctions.decodeSampledBitmapFromFile(f,
//						500, 500);
//				imgAvatar.setImageBitmap(b);
//			}
			initData();
		}
	}

	public class ImageDialog extends Dialog implements View.OnClickListener {
		private final TextView tvFromCamera;
		private final TextView tvFromGallery;
		Context mContext;

		public ImageDialog(Context context, int resource) {
			super(context);
			this.mContext = context;
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setCancelable(true);
			setContentView(resource);
			tvFromCamera = (TextView) findViewById(R.id.tvFromCamera);
			tvFromGallery = (TextView) findViewById(R.id.tvFromGallery);
			tvFromCamera.setOnClickListener(this);
			tvFromGallery.setOnClickListener(this);

			WindowManager.LayoutParams wmlp = getWindow().getAttributes();
			wmlp.gravity = Gravity.CENTER;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tvFromGallery:
				// Photos
				Intent galleryIntent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				// Start the Intent
				startActivityForResult(galleryIntent, PICK_FROM_FILE);
				this.cancel();
				break;
			case R.id.tvFromCamera:
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				fromCameraFile = new File(
						Environment.getExternalStorageDirectory() + "/",
						"tmp_avatar_"
								+ String.valueOf(System.currentTimeMillis())
								+ ".jpg");
				mImageCaptureUri = Uri.fromFile(fromCameraFile);
				try {
					intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
							mImageCaptureUri);
					intent.putExtra("return-data", true);
					startActivityForResult(intent, PICK_FROM_CAMERA);
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.cancel();
				break;
			}
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == getActivity().RESULT_OK) {
			if (requestCode == PICK_FROM_FILE) {
				Uri selectedImage = data.getData();
				fromCameraFile = new File(
						ImageUltiFunctions.getRealPathFromURI(
								selectedImage.toString(), getActivity()));
			}
			if (fromCameraFile != null) {
				Bitmap bm = ImageUltiFunctions.decodeSampledBitmapFromFile(
						fromCameraFile, 500, 500);
				imgAvatar.setImageBitmap(bm);
				updateAva(bm);
			}
		}
	}

	public void updateAva(final Bitmap bm) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ma_nv",
				Global.getPreference(getActivity(), Const.USER_MACANBO));
		try {
			params.put("anhnhanvien", fromCameraFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.post(Const.URL_UPLOAD_AVATAR, params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						Toast.makeText(
								getActivity(),
								getActivity().getResources().getString(
										R.string.profile_update_ava_success),
								Toast.LENGTH_LONG).show();
						Global.savePreference(getActivity(), Const.USER_ANH,
								fileName(fromCameraFile));
//						String manv = Global.getPreference(getActivity(), Const.USER_MACANBO);
//						Global.savePreference(getActivity(), Const.USER_ANH, manv + ".jpg");
						((FragmentManagerActivity)getActivity()).getAccount().setPhoto(bm);
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

	public String fileName(File file) {
		String path = file.getAbsolutePath();
		String[] temp = path.split("/");
		return temp[temp.length - 1];
	}
}
