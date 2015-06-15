package com.hou.sotaycanbo;

import android.app.Activity;
import android.content.Intent;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		edtEmail = (EditText) findViewById(R.id.edt_email);
		edtPass = (EditText) findViewById(R.id.edt_pass);
		btnLogin = (Button) findViewById(R.id.btn_login);
		tvForgetPass = (TextView) findViewById(R.id.tv_quenmatkhau);
		
		OnClickListeners();
	}

	public void OnClickListeners() {
		btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent manager = new Intent(LoginActivity.this, FragmentManagerActivity.class);
				startActivity(manager);
				finish();
			}
		});
		
		tvForgetPass.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Quen Pass", Toast.LENGTH_LONG).show();
			}
		});
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
