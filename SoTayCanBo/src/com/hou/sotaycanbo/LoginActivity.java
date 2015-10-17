package com.hou.sotaycanbo;

import com.hou.app.Const;
import com.hou.app.Global;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText edtEmail, edtPass;
	private Button btnLogin;
	private TextView tvForgetPass;

	private String _email;
	private String _pass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		edtEmail = (EditText) findViewById(R.id.edt_email);
		edtPass = (EditText) findViewById(R.id.edt_pass);
		btnLogin = (Button) findViewById(R.id.btn_login);
		tvForgetPass = (TextView) findViewById(R.id.tv_quenmatkhau);

		edtEmail.setText("admin");
		edtPass.setText("a");
		
		OnClickListeners();
	}

	public void OnClickListeners() {
		btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				_email = edtEmail.getText().toString();
				_pass = edtPass.getText().toString();
				if ((_email.toString().trim().length() > 0)
						&& (_pass.toString().trim().length() > 0)) {
					if (checkUser()) {
						Global.savePreference(getApplicationContext(), Const.USER_EMAIL, _email);
						Global.savePreference(getApplicationContext(), Const.USER_MACANBO, "ID_TEST");
						Intent manager = new Intent(LoginActivity.this,
								FragmentManagerActivity.class);
						startActivity(manager);
						finish();
					} else {
						Toast.makeText(
								getApplicationContext(),
								getResources().getString(R.string.login_failed),
								Toast.LENGTH_SHORT).show();
						return;
					}
				} else {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.login_notnull),
							Toast.LENGTH_SHORT).show();
					return;
				}

			}
		});

		tvForgetPass.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Global.hasNetworkConnection(getApplicationContext())) {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
							.parse("http://www.hou.edu.vn/index.php?option=com_user&view=reset"));
					startActivity(browserIntent);
				} else {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.no_internet),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public boolean checkUser() {
		boolean chek = false;
		if (_email.equals("admin") && (_pass.equals("a"))) {
			chek = true;
		}
		return chek;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
