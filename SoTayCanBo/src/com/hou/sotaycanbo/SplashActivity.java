package com.hou.sotaycanbo;

import org.json.JSONException;
import org.json.JSONObject;

import com.hou.app.Const;
import com.hou.app.Global;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SplashActivity extends Activity {

	private final int SPLASH_DISPLAY_LENGTH = 500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		String first = Global.getPreference(getApplicationContext(), "first");
		if (first.equals("") || first == null) {
			// first
			dialogConfirmEmail();
		} else {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					changeActivity();
				}
			}, SPLASH_DISPLAY_LENGTH);
		}
	}

	private void changeActivity() {
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
	private boolean check() {
		boolean check = false;
		String saveLogin = Global.getPreference(getApplicationContext(),
				Const.SAVE_LOGIN);
		String maCanbo = Global.getPreference(getApplicationContext(),
				Const.USER_MACANBO);
		if (saveLogin.equals(Const.SAVE_LOGIN_TRUE)) {
			if (!maCanbo.equals("") && maCanbo != null) {
				check = true;
			}
		}
		return check;
	}

	private void dialogConfirmEmail() {
		final Dialog dialogSendUserguide = new Dialog(this);
		dialogSendUserguide.setTitle(getResources().getString(
				R.string.splash_dialog_title));
		dialogSendUserguide.setContentView(R.layout.dialog_splash_sendemail);
		dialogSendUserguide.setCancelable(false);

		final EditText edtEmailConfirm;
		edtEmailConfirm = (EditText) dialogSendUserguide
				.findViewById(R.id.edtEmailConfirm);

		Button btnCancel, btnOK;
		btnCancel = (Button) dialogSendUserguide.findViewById(R.id.btnCancelD);
		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialogSendUserguide.dismiss();
				changeActivity();
				Global.savePreference(getApplicationContext(), "first",
						"HOU");
			}
		});

		btnOK = (Button) dialogSendUserguide.findViewById(R.id.btnOKD);
		btnOK.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String email = edtEmailConfirm.getText().toString().trim();
				if (!email.equals("") && email != null) {
					// send email to server
					AsyncHttpClient client = new AsyncHttpClient();
					RequestParams params = new RequestParams();
					params.put("email", email);
					client.post(Const.URL_USER_GUIDE, params,
							new AsyncHttpResponseHandler() {
								public void onSuccess(String response) {
									// TODO
									try {
										JSONObject obj = new JSONObject(
												response);
										String status = obj.optString(
												"status", "");
										if (status.equals("success")) {
											Global.savePreference(
													getApplicationContext(),
													"first", "HOU");
											Toast.makeText(
													getBaseContext(),
													getResources()
															.getString(
																	R.string.splash_dialog_success),
													Toast.LENGTH_SHORT)
													.show();
											dialogSendUserguide.dismiss();
											changeActivity();
										} else {
											Toast.makeText(
													getBaseContext(),
													getResources()
															.getString(
																	R.string.splash_dialog_false),
													Toast.LENGTH_SHORT)
													.show();
											edtEmailConfirm.setText(null);
										}
									} catch (JSONException e) {
										Log.d("dialogConfirmEmail_success",
												e.getMessage());
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
		dialogSendUserguide.show();
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
