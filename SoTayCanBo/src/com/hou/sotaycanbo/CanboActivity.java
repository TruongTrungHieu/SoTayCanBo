package com.hou.sotaycanbo;

import java.io.File;

import com.hou.app.Global;
import com.hou.models.CanBo;
import com.hou.ultis.BlurBuilder;
import com.hou.ultis.CircularImageView;
import com.hou.ultis.ImageUltiFunctions;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CanboActivity extends ActionBarActivity {

	private CanBo mCanbo;
	
	private RelativeLayout rlImgInfo;
	private CircularImageView imgAvatar;
	private TextView tvTenCanbo, tvTenDonvi;
	private EditText edtFullname, edtSdt, edtEmail, edtDiachi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_canbo);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		rlImgInfo = (RelativeLayout) findViewById(R.id.rlImgInfo);
		imgAvatar = (CircularImageView) findViewById(R.id.imgAvatar);
		tvTenCanbo = (TextView) findViewById(R.id.tvUserName);
		tvTenDonvi = (TextView) findViewById(R.id.tvDonvi);
		edtFullname = (EditText) findViewById(R.id.edtFullName);
		edtSdt = (EditText) findViewById(R.id.edtSdt);
		edtEmail = (EditText) findViewById(R.id.edtEmail);
		edtDiachi = (EditText) findViewById(R.id.edtDiachi);
		
		mCanbo = new CanBo();
		mCanbo = (CanBo) getIntent().getSerializableExtra("canbo");
		if (mCanbo != null) {
			initData();
		}
	}

	@SuppressWarnings("deprecation")
	private void initData() {
		tvTenCanbo.setText(mCanbo.getTenCanbo().toString());
		tvTenDonvi.setText("");
		edtFullname.setText(mCanbo.getTenCanbo().toString());
		edtSdt.setText(mCanbo.getSdt().toString());
		edtEmail.setText(mCanbo.getEmail().toString());
		edtDiachi.setText(mCanbo.getDiachi().toString());
		String urlAvatar = mCanbo.getAvatar();
		File f = ImageUltiFunctions
				.getFileFromUri(Global.getURI(urlAvatar));
		if (f != null) {
			Bitmap b = ImageUltiFunctions.decodeSampledBitmapFromFile(f, 500,
					500);
			imgAvatar.setImageBitmap(b);
			
			Bitmap bg = BlurBuilder.CreateBlurredImage(b, this);
			BitmapDrawable background = new BitmapDrawable(bg);
			rlImgInfo.setBackground(background);
		} else {
			imgAvatar.setImageResource(R.drawable.test1);
			if(Build.VERSION.SDK_INT < VERSION_CODES.JELLY_BEAN) {
				rlImgInfo.setBackgroundDrawable( getResources().getDrawable(R.drawable.bg) );
			} else {
				rlImgInfo.setBackground(getResources().getDrawable(R.drawable.bg));
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.canbo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
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
}
