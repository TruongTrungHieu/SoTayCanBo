package com.hou.sotaycanbo;

import org.json.JSONException;
import org.json.JSONObject;

import com.hou.app.Const;
import com.hou.models.CanBo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DoiMatkhauActivity extends ActionBarActivity {

	private Boolean isFirstLogin = true;
	private EditText edtTaikhoan, edtPass, edtNpass;
	private Button btnDoimk;

	private String taikhoan, pass, npass;

	private CanBo cb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doi_matkhau);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		isFirstLogin = getIntent().getBooleanExtra("isFirstLogin", true);
		cb = (CanBo) getIntent().getSerializableExtra("canbo");

		edtTaikhoan = (EditText) findViewById(R.id.edtTaikhoan);
		edtPass = (EditText) findViewById(R.id.edtPasscu);
		edtNpass = (EditText) findViewById(R.id.edtPassmoi);
		btnDoimk = (Button) findViewById(R.id.btnDoimk);

		btnDoimk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkValidate()) {
					doiMatkhauToServer();
				}
			}
		});
	}

	private boolean checkValidate() {
		taikhoan = edtTaikhoan.getText().toString().trim();
		pass = edtPass.getText().toString().trim();
		npass = edtNpass.getText().toString().trim();
		if (taikhoan.length() > 0 && pass.length() > 0 && npass.length() > 0) {
			if (!pass.equals(npass)) {
				if (pass.length() >= 8 && npass.length() >= 8) {
					return true;
				} else {
					Toast.makeText(getBaseContext(),
							getString(R.string.doimatkhau_passshort),
							Toast.LENGTH_SHORT).show();
					return false;
				}
			} else {
				Toast.makeText(getBaseContext(),
						getString(R.string.doimatkhau_pass), Toast.LENGTH_SHORT)
						.show();
				return false;
			}
		} else {
			Toast.makeText(getBaseContext(),
					getString(R.string.doimatkhau_validate), Toast.LENGTH_SHORT)
					.show();
			return false;
		}
	}

	private void doiMatkhauToServer() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("id", taikhoan);
		params.put("pass", pass);
		params.put("npass", npass);
		client.post(Const.URL_DOIMATKHAU, params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						try {
							JSONObject obj = new JSONObject(response);
							String status = obj.optString("status", "false");
							if (status.equals("true")) {
								Toast.makeText(
										getBaseContext(),
										getResources().getString(
												R.string.doimatkhau_success),
										Toast.LENGTH_LONG).show();
								Intent t = new Intent(DoiMatkhauActivity.this,
										FragmentManagerActivity.class);
								if (cb != null) {
									t.putExtra("canbo", cb);
								}
								startActivity(t);
								finish();
							} else {
								Toast.makeText(
										getBaseContext(),
										getResources().getString(
												R.string.doimatkhau_false),
										Toast.LENGTH_LONG).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						switch (statusCode) {
						case 0:
							Toast.makeText(
									getApplicationContext(),
									getResources().getString(
											R.string.check_internet)
											+ " - " + statusCode,
									Toast.LENGTH_LONG).show();
							break;
						default:
							Toast.makeText(
									getApplicationContext(),
									getResources().getString(
											R.string.database_error)
											+ " - " + statusCode,
									Toast.LENGTH_LONG).show();
							break;
						}
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doi_matkhau, menu);
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
			if (isFirstLogin && isFirstLogin != null) {
				Toast.makeText(getBaseContext(),
						getString(R.string.doimatkhau_isfirst),
						Toast.LENGTH_LONG).show();
			} else {
				onBackPressed();
			}
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
