package com.hou.sotaycanbo;

import java.io.File;

import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.hou.models.CanBo;
import com.hou.ultis.CircularImageView;
import com.hou.ultis.ImageUltiFunctions;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CanboActivity extends ActionBarActivity {

	private CanBo mCanbo;

	private CircularImageView imgAvatar;
	private TextView tvTenCanbo, tvTenDonvi;
	private EditText edtChucvu, edtSdt, edtEmail, edtDiachi;

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
		edtChucvu = (EditText) findViewById(R.id.edtChucvu);
		edtSdt = (EditText) findViewById(R.id.edtSdt);
		edtEmail = (EditText) findViewById(R.id.edtEmail);
		edtDiachi = (EditText) findViewById(R.id.edtDiachi);

		mCanbo = (CanBo) getIntent().getSerializableExtra("canbo_details");
		if (mCanbo != null) {
			initData();
		}
		edtSdt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String sdt = edtSdt.getText().toString().trim();
				if (sdt.length() > 0) {
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:" + sdt));
					startActivity(callIntent);
				}
			}
		});

		edtEmail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String email = edtEmail.getText().toString().trim();
				if (email.length() > 0) {
					if (Global.hasNetworkConnection(getApplicationContext())) {
						Intent gmail = new Intent(Intent.ACTION_VIEW);
						gmail.setClassName("com.google.android.gm",
								"com.google.android.gm.ComposeActivityGmail");
						gmail.putExtra(Intent.EXTRA_EMAIL,
								new String[] { email });
						gmail.setType("plain/text");
						startActivity(gmail);
					} else {
						Toast.makeText(getBaseContext(),
								getString(R.string.no_internet),
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}

	private void initData() {
		String tenDv = exeQ.getTenDonviByMaCanbo(mCanbo.getMaCanbo());
		tvTenCanbo.setText(mCanbo.getTenCanbo().toString());
		tvTenDonvi.setText(tenDv);
		String tenChucvu = exeQ.getChucvuByMaCanbo(mCanbo.getMaCanbo());
		edtChucvu.setText(tenChucvu);

		String sdt = mCanbo.getSdt().toString().trim();
		if (sdt != null && !sdt.equals("null")) {
			edtSdt.setText(sdt);
		} else {
			edtSdt.setText("");
		}

		String email = mCanbo.getEmail().toString().trim();
		if (email != null && !email.equals("null")) {
			edtEmail.setText(email);
		} else {
			edtEmail.setText("");
		}

		String diachi = mCanbo.getDiachi().toString().trim();
		if (diachi != null && !diachi.equals("null")) {
			edtDiachi.setText(diachi);
		} else {
			edtDiachi.setText("");
		}

		String urlAvatar = mCanbo.getAvatar();
		File f = ImageUltiFunctions.getFileFromUri(Global.getURI(urlAvatar));
		if (f != null) {
			Bitmap b = ImageUltiFunctions.decodeSampledBitmapFromFile(f, 500,
					500);
			imgAvatar.setImageBitmap(b);

		} else {
			imgAvatar.setImageResource(R.drawable.test1);
			if (Global.hasNetworkConnection(getApplicationContext())) {
				DownloadAvatarAsync download = new DownloadAvatarAsync(mCanbo);
				download.execute();
			}
		}
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
			File f = ImageUltiFunctions.getFileFromUri(Global.getURI(mCanbo
					.getAvatar()));
			if (f != null) {
				Bitmap b = ImageUltiFunctions.decodeSampledBitmapFromFile(f,
						500, 500);
				imgAvatar.setImageBitmap(b);

			} else {
				imgAvatar.setImageResource(R.drawable.test1);
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
