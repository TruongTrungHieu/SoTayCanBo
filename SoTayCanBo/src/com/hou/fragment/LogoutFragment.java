package com.hou.fragment;

import com.hou.app.Const;
import com.hou.app.Global;
import com.hou.sotaycanbo.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class LogoutFragment extends Fragment {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		getActivity().finish();
	}
	
}
