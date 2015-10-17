package com.hou.sotaycanbo;

import com.hou.models.CanBo;
import com.hou.ultis.CircularImageView;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_canbo);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		mCanbo = (CanBo) getIntent().getSerializableExtra("canbo");
		if (mCanbo != null) {
			initData();
		}
	}

	private void initData() {
		
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
