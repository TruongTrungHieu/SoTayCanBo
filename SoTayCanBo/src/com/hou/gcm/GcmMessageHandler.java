package com.hou.gcm;

import com.hou.app.Const;
import com.hou.app.Global;
import com.hou.sotaycanbo.R.drawable;
import com.hou.sotaycanbo.R;
import com.hou.sotaycanbo.SplashActivity;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

@SuppressWarnings("unused")
public class GcmMessageHandler extends IntentService {

	private String mes;
	private String title;
	private Handler handler;

	public GcmMessageHandler() {
		super("GcmMessageHandler");
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		handler = new Handler();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		String getNotif = Global.getPreference(getApplicationContext(),
				Const.GET_NOTIF);
		String ma = Global.getPreference(getApplicationContext(),
				Const.USER_MACANBO);
		if (!getNotif.equals("") && getNotif != null
				&& getNotif.equals(Const.GET_NOTIF_TRUE) && !ma.equals("")
				&& ma != null) {
			title = extras.getString("title");
			mes = extras.getString("mes");
			SoundNoti();
			GcmBroadcastReceiver.completeWakefulIntent(intent);
		}
	}

	private void SoundNoti() {
		try {
			NotificationManager mNotifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

			Intent intent = new Intent(getApplicationContext(),
					SplashActivity.class);
			PendingIntent pending = PendingIntent.getActivity(
					getApplicationContext(), 0, intent, 0);

			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
					getApplicationContext())
					.setSmallIcon(drawable.icon_gcm)
					.setWhen(System.currentTimeMillis())
					.setContentTitle(title)
					.setContentText(mes)
					.setContentIntent(pending)
					.setAutoCancel(false)
					.setTicker(
							getApplicationContext().getResources().getString(
									R.string.gcm_notif_ticker));

			Notification mNotificaton = mBuilder.build();
			mNotificaton.flags |= Notification.FLAG_AUTO_CANCEL;
			mNotificaton.defaults |= Notification.DEFAULT_SOUND;
			mNotificaton.defaults |= Notification.DEFAULT_VIBRATE;
			mNotificaton.defaults |= Notification.DEFAULT_LIGHTS;

			mNotifManager.notify(0, mNotificaton);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}