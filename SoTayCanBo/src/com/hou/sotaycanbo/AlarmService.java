package com.hou.sotaycanbo;

import com.hou.models.GhiChu;
import com.hou.sotaycanbo.R;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmService extends IntentService {
	private NotificationManager alarmNotificationManager;

	public AlarmService() {
		super("AlarmService");
	}

	@Override
	public void onHandleIntent(Intent intent) {
		sendNotification("Wake Up! Wake Up!", intent);

	}

	private void sendNotification(String msg, Intent intent) {
		Log.d("AlarmService", "Preparing to send notification...: " + msg);
		alarmNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);

		NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
				this).setContentTitle("Alarm")
				.setSmallIcon(R.drawable.ic_launcher)
				.setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
				.setContentText(msg);
		GhiChu ghichu = (GhiChu) intent.getSerializableExtra("note");
		Intent t = new Intent(this, NoteActivity.class);
		t.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		t.putExtra("NOTE", ghichu);
		t.putExtra("TYPE_NOTE", "READ");
		t.putExtra("from_alarm", true);
		// startActivity(t);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, t, PendingIntent.FLAG_CANCEL_CURRENT);
		alamNotificationBuilder.setContentIntent(contentIntent);
		alarmNotificationManager.notify(1, alamNotificationBuilder.build());
		Log.d("AlarmService", "Notification sent.");
	}

}