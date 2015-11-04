package com.hou.sotaycanbo;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.hou.app.Const;
import com.hou.app.Global;
import com.hou.models.CanBo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText edtEmail, edtPass;
	private Button btnLogin;
	private TextView tvForgetPass;

	private String _email;
	private String _pass;

	private String regid;
	private GoogleCloudMessaging gcm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		edtEmail = (EditText) findViewById(R.id.edt_email);
		edtPass = (EditText) findViewById(R.id.edt_pass);
		btnLogin = (Button) findViewById(R.id.btn_login);
		tvForgetPass = (TextView) findViewById(R.id.tv_quenmatkhau);

		edtEmail.setText("levanthanh");
		edtPass.setText("ns123456");

		getRegId();
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
					loginToServer();
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
					dialogForgotPass();
				} else {
					Toast.makeText(getApplicationContext(),
							getResources().getString(R.string.no_internet),
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void loginToServer() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("id", _email);
		params.put("pass", _pass);
		params.put("gcm_id", regid);
		client.post(Const.URL_DANG_NHAP, params,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String response) {
						checkLogin(response);
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

	private void checkLogin(String response) {
		Log.d("checkLogin", response);
		try {
			JSONObject obj = new JSONObject(response);
			String status = obj.optString("status", "false");
			if (status.equalsIgnoreCase("true")) {
				int trangthai = obj.optInt("trangthai", 1);

				String taikhoan = obj.optString("taikhoan", "");
				Global.savePreference(getApplicationContext(),
						Const.USER_TAIKHOAN, taikhoan);

				JSONArray arr = obj.getJSONArray("ttnhanvien");
				JSONObject canbo = arr.getJSONObject(0);

				String ma_nv = canbo.optString("ma_nv", "");
				String hoten_nv = canbo.optString("hoten_nv", "");
				String chitietdiachihientai = canbo.optString(
						"chitietdiachihientai", "");
				String sdt = canbo.optString("sdt", "").replace("_", "").trim();
				String email_nv = canbo.optString("email_nv", "");
				String so_cmnd = canbo.optString("so_cmnd", "");
				String ten_hocvi = canbo.optString("ten_hocvi", "");
				String ten_hocham = canbo.optString("ten_hocham", "");
				String anh_nv = canbo.optString("anh_nv", "");
				String ma_donvi = canbo.optString("ma_donvi", "");
				
				CanBo cb = new CanBo(ma_nv, ma_donvi, hoten_nv, chitietdiachihientai, email_nv, so_cmnd, ten_hocham, ten_hocvi, anh_nv, sdt);
				
				Intent t;
				if (trangthai == 1) {
					t = new Intent(LoginActivity.this,
							FragmentManagerActivity.class);

				} else {
					// change pass
					t = new Intent(LoginActivity.this,
							DoiMatkhauActivity.class);
					t.putExtra("isFirstLogin", true);
				}
				
				t.putExtra("canbo", cb);
				startActivity(t);
				finish();
			} else {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.login_failed),
						Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.login_failed),
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	public void getRegId() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging
								.getInstance(getApplicationContext());
					}
					regid = gcm.register(Const.PROJECT_NUMBER);
				} catch (IOException ex) {
					msg = ex.getMessage();
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				if (!msg.equals("")) {
					Toast.makeText(getApplicationContext(),
							getString(R.string.check_internet) + "\n" + msg,
							Toast.LENGTH_SHORT).show();
				}
			}
		}.execute(null, null, null);
	}

	private void dialogForgotPass() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				LoginActivity.this);
		alertDialog.setTitle(getString(R.string.forgotpass_title));
		alertDialog.setMessage(getString(R.string.forgotpass_message));

		final EditText input = new EditText(LoginActivity.this);
		input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
		input.getBackground().setColorFilter(
				getResources().getColor(R.color.grey), Mode.SRC_IN);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		input.setLayoutParams(lp);
		alertDialog.setView(input);
		alertDialog.setIcon(R.drawable.forgotpass_icon);

		alertDialog.setPositiveButton(
				getResources().getString(R.string.forgotpass_ok),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(final DialogInterface dialog, int which) {
						String email = input.getText().toString().trim();
						if (!email.equals("") && email != null) {
							// send email to server
							AsyncHttpClient client = new AsyncHttpClient();
							RequestParams params = new RequestParams();
							params.put("email", email);
							client.post(Const.URL_QUENMATKHAU, params,
									new AsyncHttpResponseHandler() {
										public void onSuccess(String response) {
											try {
												JSONObject obj = new JSONObject(
														response);
												String status = obj.optString(
														"status", "false");
												if (status.equals("success")) {
													Toast.makeText(
															getBaseContext(),
															getString(R.string.forgotpass_success),
															Toast.LENGTH_SHORT)
															.show();
												} else {
													Toast.makeText(
															getBaseContext(),
															getString(R.string.forgotpass_false),
															Toast.LENGTH_SHORT)
															.show();
												}
												dialog.dismiss();
											} catch (JSONException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}
										}

										@Override
										public void onFailure(int statusCode,
												Throwable error, String content) {
											switch (statusCode) {
											case 0:
												Toast.makeText(
														getBaseContext(),
														getResources()
																.getString(
																		R.string.check_internet)
																+ " - "
																+ statusCode,
														Toast.LENGTH_LONG)
														.show();
												break;
											default:
												Toast.makeText(
														getBaseContext(),
														getResources()
																.getString(
																		R.string.database_error)
																+ " - "
																+ statusCode,
														Toast.LENGTH_LONG)
														.show();
												break;
											}
										}
									});
						} else {
							Toast.makeText(
									getBaseContext(),
									getResources().getString(
											R.string.forgotpass_validate),
									Toast.LENGTH_LONG).show();
						}
					}
				});

		alertDialog.setNegativeButton(
				getResources().getString(R.string.forgotpass_cancel),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		alertDialog.show();
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
