package com.hou.fragment;

import java.io.File;

import com.hou.app.Global;
import com.hou.models.CanBo;
import com.hou.sotaycanbo.R;
import com.hou.ultis.CircularImageView;

import android.R.bool;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

public class ThongTinCanBoFragment extends Fragment implements OnClickListener{

	private CanBo mCanbo;
	private EditText edtFullName, edtSdt, edtEmail, edtDiachi;
	private CircularImageView imgAvatar;
	public Point screenSize = new Point();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.activity_canbo, null);
		initView(view);
		imgAvatar.setOnClickListener(this);
		
		mCanbo = (CanBo) getActivity().getIntent()
				.getSerializableExtra("canbo");
		if (mCanbo != null) {
			initData();
		}
		
		hasOptionsMenu();
		return view;
	}

	private void initData() {
		// TODO Auto-generated method stub
		edtFullName.setText(mCanbo.getTenCanbo());
		edtSdt.setText(mCanbo.getSdt());
		edtEmail.setText(mCanbo.getEmail());
		edtDiachi.setText(mCanbo.getDiachi());
	}

	public void initView(View view) {
		edtFullName = (EditText) view.findViewById(R.id.edtFullName);
		edtSdt = (EditText) view.findViewById(R.id.edtSdt);
		edtEmail = (EditText) view.findViewById(R.id.edtEmail);
		edtDiachi = (EditText) view.findViewById(R.id.edtDiachi);
		imgAvatar = (CircularImageView) view.findViewById(R.id.imgAvatar);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.canbo, menu);
		super.onCreateOptionsMenu(menu, inflater);
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
	
	public void setEnable(boolean isEnable){
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
		case R.id.imgAvatar:
			
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
			getWindow().setLayout((int) (screenSize.x * 0.95), ViewGroup.LayoutParams.WRAP_CONTENT);

			WindowManager.LayoutParams wmlp = getWindow().getAttributes();

			wmlp.gravity = Gravity.TOP | Gravity.LEFT;
			wmlp.x = 100; // x position
			wmlp.y = 100; // y position
			// getWindow().getDecorView().setBackgroundResource(0);
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tvFromGallery:
				// Intent cameraIntent = new Intent();
				// cameraIntent.setType("image/*");
				// cameraIntent.setAction(Intent.ACTION_GET_CONTENT);
				// startActivityForResult(Intent.createChooser(cameraIntent,
				// "Complete action using"), PICK_FROM_FILE);
				// Create intent to Open Image applications like Gallery, Google
				// Photos
				Intent galleryIntent = new Intent(Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				// Start the Intent
				startActivityForResult(galleryIntent, PICK_FROM_FILE);
				this.cancel();
				break;
			case R.id.tvFromCamera:
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				//Global.createFolderDULIBU();
				fromCameraFile = new File(Environment.getExternalStorageDirectory() + "/",
						"tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
				mImageCaptureUri = Uri.fromFile(fromCameraFile);
				try {
					intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
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
