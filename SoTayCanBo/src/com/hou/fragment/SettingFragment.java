package com.hou.fragment;

import com.hou.app.Const;
import com.hou.app.Global;
import com.hou.sotaycanbo.DoiMatkhauActivity;
import com.hou.sotaycanbo.LoginActivity;
import com.hou.sotaycanbo.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

public class SettingFragment extends Fragment {

	private LinearLayout llChangePass, llSaveLogin, llLogout, llGetNotif,
			llAutoUpdate, llHelp;
	private Switch swSaveLogin, swGetNotif;
	private Boolean isSaveLogin, isGetNotif;
	private ProgressDialog pbUpdate;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_setting, container, false);

		llChangePass = (LinearLayout) v.findViewById(R.id.llChangePass);
		llSaveLogin = (LinearLayout) v.findViewById(R.id.llSaveLogin);
		llLogout = (LinearLayout) v.findViewById(R.id.llLogout);
		llGetNotif = (LinearLayout) v.findViewById(R.id.llGetNotif);
		llAutoUpdate = (LinearLayout) v.findViewById(R.id.llAutoUpdate);
		llHelp = (LinearLayout) v.findViewById(R.id.llHelp);
		swSaveLogin = (Switch) v.findViewById(R.id.swSaveLogin);
		swGetNotif = (Switch) v.findViewById(R.id.swGetNotify);

		String save = Global.getPreference(getActivity(), Const.SAVE_LOGIN);
		if (save.equals(Const.SAVE_LOGIN_TRUE)) {
			isSaveLogin = true;
		} else {
			isSaveLogin = false;
		}
		String get = Global.getPreference(getActivity(), Const.GET_NOTIF);
		if (get.equals(Const.GET_NOTIF_TRUE) || get.equals("") || get == null) {
			isGetNotif = true;
		} else {
			isGetNotif = false;
		}
		swSaveLogin.setChecked(isSaveLogin);
		swGetNotif.setChecked(isGetNotif);

		swSaveLogin
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						isSaveLogin = !isSaveLogin;
						swSaveLogin.setChecked(isSaveLogin);
						Global.savePreference(getActivity(), Const.SAVE_LOGIN,
								isSaveLogin ? Const.SAVE_LOGIN_TRUE
										: Const.SAVE_LOGIN_FALSE);
					}
				});

		swGetNotif
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						isGetNotif = !isGetNotif;
						swGetNotif.setChecked(isGetNotif);
						Global.savePreference(getActivity(), Const.GET_NOTIF,
								isGetNotif ? Const.GET_NOTIF_TRUE
										: Const.GET_NOTIF_FALSE);
					}
				});

		llChangePass.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent changepass = new Intent(getActivity()
						.getApplicationContext(), DoiMatkhauActivity.class);
				changepass.putExtra("isFirstLogin", false);
				startActivity(changepass);
			}
		});

		llSaveLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		llLogout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Global.savePreference(getActivity().getApplicationContext(),
						Const.SAVE_LOGIN, Const.SAVE_LOGIN_FALSE);
				Global.savePreference(getActivity().getApplicationContext(),
						Const.GET_NOTIF, Const.GET_NOTIF_TRUE);
				Global.savePreference(getActivity().getApplicationContext(),
						Const.USER_MACANBO, "");

				Intent login = new Intent(
						getActivity().getApplicationContext(),
						LoginActivity.class);
				startActivity(login);
			}
		});

		llGetNotif.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		llAutoUpdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Global.hasNetworkConnection(getActivity())) {
					pbUpdate = new ProgressDialog(getActivity());
					pbUpdate.setMessage(getString(R.string.setting_app_update));
					pbUpdate.setIndeterminate(false);
					pbUpdate.setCancelable(false);
					pbUpdate.show();
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							pbUpdate.dismiss();
							Toast.makeText(getActivity().getBaseContext(),
									getString(R.string.setting_app_update_success), Toast.LENGTH_LONG)
									.show();
						}
					}, 5000);
				} else {
					Toast.makeText(getActivity().getBaseContext(),
							getString(R.string.no_internet), Toast.LENGTH_LONG)
							.show();
				}
			}
		});

		llHelp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		return v;
	}
}
