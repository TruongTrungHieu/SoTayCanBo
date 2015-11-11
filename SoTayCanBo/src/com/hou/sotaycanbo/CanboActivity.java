package com.hou.sotaycanbo;

import java.io.File;

import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.hou.models.CanBo;
import com.hou.ultis.CircularImageView;
import com.hou.ultis.ImageUltiFunctions;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class CanboActivity extends ActionBarActivity {

	private CanBo mCanbo;
	
	private CircularImageView imgAvatar;
	private TextView tvTenCanbo, tvTenDonvi;
	private EditText edtFullname, edtSdt, edtEmail, edtDiachi;
	
	private ExecuteQuery exeQ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_canbo);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		exeQ = new ExecuteQuery(getApplicationContext());
		exeQ.createDatabase();
		exeQ.open();
		
		imgAvatar = (CircularImageView) findViewById(R.id.imgAvatar);
		tvTenCanbo = (TextView) findViewById(R.id.tvUserName);
		tvTenDonvi = (TextView) findViewById(R.id.tvDonvi);
		edtFullname = (EditText) findViewById(R.id.edtFullName);
		edtSdt = (EditText) findViewById(R.id.edtSdt);
		edtEmail = (EditText) findViewById(R.id.edtEmail);
		edtDiachi = (EditText) findViewById(R.id.edtDiachi);
		
		mCanbo = (CanBo) getIntent().getSerializableExtra("canbo_details");
		if (mCanbo != null) {
			initData();
		}
	}

	private void initData() {
		String tenDv = exeQ.getTendonviByMadonvi(mCanbo.getMaDonvi());
		tvTenCanbo.setText(mCanbo.getTenCanbo().toString());
		tvTenDonvi.setText(tenDv);
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
			
		} else {
			imgAvatar.setImageResource(R.drawable.test1);
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
