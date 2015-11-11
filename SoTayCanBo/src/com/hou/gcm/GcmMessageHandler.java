package com.hou.gcm;

import com.hou.sotaycanbo.SplashActivity;

import android.R;
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

		title = extras.getString("title");
		mes = extras.getString("mes");
		SoundNoti();
		Log.i("GCM", title + "-" + mes);

		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	@SuppressWarnings("deprecation")
	private void SoundNoti() {
		try {
			NotificationManager NM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notify = new Notification(android.R.drawable.alert_light_frame, "HaNoi Open Universty",
					System.currentTimeMillis());
			notify.flags = Notification.FLAG_AUTO_CANCEL;

			Intent intent = new Intent(getApplicationContext(),
					SplashActivity.class);
			
			PendingIntent pending = PendingIntent.getActivity(
					getApplicationContext(), 0, intent, 0);
			notify.setLatestEventInfo(getApplicationContext(), title, mes,
					pending);
			NM.notify(0, notify);

			Uri notification = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),
					notification);
			r.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}