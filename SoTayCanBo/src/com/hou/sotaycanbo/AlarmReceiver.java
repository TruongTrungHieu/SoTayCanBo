package com.hou.sotaycanbo;

import com.hou.models.GhiChu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;

public class AlarmReceiver extends WakefulBroadcastReceiver {

	@Override
	public void onReceive(final Context context, Intent intent) {
		// this will update the UI with message
		// AlarmActivity inst = AlarmActivity.instance();
		// inst.setAlarmText("Alarm! Wake up! Wake up!");

		// this will sound the alarm tone
		// this will sound the alarm once, if you wish to
		// raise alarm in loop continuously then use MediaPlayer and
		// setLooping(true)
		GhiChu ghichu = (GhiChu) intent.getSerializableExtra("note");
		Intent t = new Intent(context, NoteActivity.class);		
		t.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		t.putExtra("NOTE", ghichu);
		t.putExtra("TYPE_NOTE", "READ");
		t.putExtra("from_alarm", true);
		context.startActivity(t);

		// this will send a notification message
		ComponentName comp = new ComponentName(context.getPackageName(),
				AlarmService.class.getName());
		startWakefulService(context, (intent.setComponent(comp)));
		setResultCode(Activity.RESULT_OK);
	}

}
