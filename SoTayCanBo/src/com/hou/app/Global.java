package com.hou.app;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Global
 * 
 * Contains various methods used through the application.
 */

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
	
	@SuppressLint("SimpleDateFormat")
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy, HH:mm");

	/**
	 * Returns current time
	 * 
	 * 
	 * @return time in milliseconds
	 * @throws Exception
	 */
	public static long getCurrentDateTime() {
		long currentTime = System.currentTimeMillis();
		return currentTime;
	}

	/**
	 * Returns time formatted as: JAN 24, 13:30
	 * 
	 * @param timeInSeconds
	 * @return date as a String
	 */
	public static String getFormattedDateTime(long timeInSeconds) {
		Date dateTime = new Date(timeInSeconds * 1000);
		StringBuilder date = new StringBuilder(DATE_FORMAT.format(dateTime));
		return date.toString();
	}
}
