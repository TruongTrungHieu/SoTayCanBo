package com.hou.fragment;

import java.io.File;

import com.hou.app.Const;
import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.hou.sotaycanbo.R;
import com.hou.ultis.CircularImageView;
import com.hou.ultis.ImageUltiFunctions;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
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

@SuppressLint("InflateParams")
public class ThongTinCanBoFragment extends Fragment implements OnClickListener {

	private EditText edtFullName, edtSdt, edtEmail, edtDiachi;
	private TextView tvUsername, tvDonvi;
	private CircularImageView imgAvatar;
	public static Point screenSize = new Point();
	private ExecuteQuery exeQ;
	private Menu mMenu;
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
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
		imgAvatar.setOnClickListener(this);

		exeQ = new ExecuteQuery(getActivity());
		exeQ.createDatabase();
		exeQ.open();
		
		initData();
		
		return view;
	}

	private void initData() {
		// TODO Auto-generated method stub
		String maDonvi = Global.getPreference(getActivity(), Const.USER_MADONVI);
		String tenDonvi = exeQ.getTendonviByMadonvi(maDonvi);
		tvDonvi.setText(tenDonvi);
		tvUsername.setText(Global.getPreference(getActivity(), Const.USER_HOTEN));
		edtFullName.setText(Global.getPreference(getActivity(), Const.USER_HOTEN));
		edtSdt.setText(Global.getPreference(getActivity(), Const.USER_SDT));
		edtEmail.setText(Global.getPreference(getActivity(), Const.USER_EMAIL));
		edtDiachi.setText(Global.getPreference(getActivity(), Const.USER_DIACHI));
		
		File f = ImageUltiFunctions.getFileFromUri(Global.getURI(Global.getPreference(getActivity(), Const.USER_ANH)));
		if (f != null) {
			Bitmap b = ImageUltiFunctions.decodeSampledBitmapFromFile(f, 500,
					500);
			imgAvatar.setImageBitmap(b);
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
			setEnable(false);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void setEnable(boolean isEnable) {
		mMenu.getItem(0).setVisible(!isEnable);
		mMenu.getItem(1).setVisible(isEnable);
		edtFullName.setEnabled(isEnable);
		edtSdt.setEnabled(isEnable);
		edtEmail.setEnabled(isEnable);
		edtDiachi.setEnabled(isEnable);
		imgAvatar.setEnabled(isEnable);
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

	public class ImageDialog extends Dialog implements View.OnClickListener {
		private static final int PICK_FROM_FILE = 0;
		private static final int PICK_FROM_CAMERA = 1;
		private final TextView tvFromCamera;
		private final TextView tvFromGallery;
		Context mContext;
		private File fromCameraFile;
		private Uri mImageCaptureUri;

		public ImageDialog(Context context, int resource) {
			super(context);
			mContext = context;
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setCancelable(true);
			setContentView(resource);
			tvFromCamera = (TextView) findViewById(R.id.tvFromCamera);
			tvFromGallery = (TextView) findViewById(R.id.tvFromGallery);
			tvFromCamera.setOnClickListener(this);
			tvFromGallery.setOnClickListener(this);
			getWindow().setLayout((int) (screenSize.x * 0.95),
					ViewGroup.LayoutParams.WRAP_CONTENT);

			WindowManager.LayoutParams wmlp = getWindow().getAttributes();

			wmlp.gravity = Gravity.TOP | Gravity.LEFT;
			wmlp.x = 100; // x position
			wmlp.y = 100; // y position
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

}
