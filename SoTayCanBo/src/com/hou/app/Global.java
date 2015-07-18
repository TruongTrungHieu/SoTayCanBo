package com.hou.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Global {

	public static void savePreference(Context mContext, String key, String value) {
		SharedPreferences mSharedPrefences = mContext.getSharedPreferences(
				Const.XML_FILE_NAME, Context.MODE_PRIVATE);
		Editor mEditor = mSharedPrefences.edit();
		mEditor.putString(key, value);
		mEditor.commit();
	}

	public static String getPreference(Context mContext, String key) {
		SharedPreferences mSharedPrefences = mContext.getSharedPreferences(
				Const.XML_FILE_NAME, Context.MODE_PRIVATE);
		return mSharedPrefences.getString(key, Const.XML_DEFAULT);
	}

	public static boolean hasNetworkConnection(Context mContext) {
		boolean hasConnectedWifi = false;
		boolean hasConnectedMobile = false;

		final ConnectivityManager connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo[] netInfo = connectivityManager.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					hasConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					hasConnectedMobile = true;
		}
		boolean hasNetworkConnection = hasConnectedWifi || hasConnectedMobile;
		return hasNetworkConnection;
	}
}
