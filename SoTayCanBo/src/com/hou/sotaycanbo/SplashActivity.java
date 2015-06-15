package com.hou.sotaycanbo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

public class SplashActivity extends Activity {

	private final int SPLASH_DISPLAY_LENGTH = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				changeActivity();
			}
		}, SPLASH_DISPLAY_LENGTH);
	}

	public void changeActivity() {
		if (check()) {
			Intent main = new Intent(SplashActivity.this,
					FragmentManagerActivity.class);
			startActivity(main);
		} else {
			Intent login = new Intent(SplashActivity.this, LoginActivity.class);
			startActivity(login);
		}

		finish();
	}

	// kiem tra user da dang nhap lan nao chua
	public boolean check() {
		boolean check = false;
		if (true) {
			// kiểm tra đã lưu references user và chọn nhớ mật khẩu
			check = true;
		}
		return check;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
