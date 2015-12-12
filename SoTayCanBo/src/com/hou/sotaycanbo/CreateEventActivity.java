package com.hou.sotaycanbo;

import java.util.Calendar;

import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.hou.models.Event;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateEventActivity extends ActionBarActivity {

	private EditText edtTenEvent, edtDiadiem, edtThoigian, edtMota;
	private CheckBox ckAlarm;
	private TimePickerDialog timePickerDialog;
	
	private ExecuteQuery exeQ;
	private String currentDay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_sukien);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		currentDay = getIntent().getStringExtra("currentDay");
		
		exeQ = new ExecuteQuery(getApplicationContext());
		exeQ.createDatabase();
		exeQ.open();
		
		edtTenEvent = (EditText)findViewById(R.id.edtTenEvent);
		edtDiadiem = (EditText)findViewById(R.id.edtDiadiem);
		edtThoigian = (EditText)findViewById(R.id.edtThoigian);
		edtMota = (EditText)findViewById(R.id.edtMota);
		ckAlarm = (CheckBox)findViewById(R.id.ckAlarm);
		
		edtThoigian.setOnClickListener(new View.OnClickListener() {
			Calendar newCalendar = Calendar.getInstance();
			final Calendar newDate = Calendar.getInstance();
			@Override
			public void onClick(View v) {
				timePickerDialog = new TimePickerDialog(CreateEventActivity.this,
						new OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker timePicker, int hour,
									int minute) {
								// TODO Auto-generated method stub
								newDate.set(Calendar.HOUR_OF_DAY, hour);
								newDate.set(Calendar.MINUTE, minute);						
								String timeChoice = newDate.get(Calendar.HOUR_OF_DAY) + ":" + newDate.get(Calendar.MINUTE);			
								edtThoigian.setText(timeChoice);
							}
						}, newCalendar.get(Calendar.HOUR_OF_DAY),
						newCalendar.get(Calendar.MINUTE), false);
				timePickerDialog.show();
			}
		});
	}

	private void createEvent() {
		String ten = "";
		String diadiem = "";
		String thoigian = "";
		String mota = "";
		ten = edtTenEvent.getText().toString().trim();
		if (ten != null && !ten.equals("")) {
			diadiem = edtDiadiem.getText().toString().trim();
			thoigian = edtThoigian.getText().toString().trim();
			mota = edtMota.getText().toString().trim();
			// 1 - turn on alarm
			// 0 - turn off alarm
			String maLoinhac = ckAlarm.isChecked() ? (1 + "") : (0 + "");
			String maEvent = Global.getMaCanBo(this) + "_E_" + Global.getCurrentDateTime();
			Event e = new Event(maEvent, ten, diadiem, thoigian, "", maLoinhac, currentDay, Global.getMaCanBo(this), mota, "");
			if (exeQ.insert_tblEvent_single(e)) {
				Toast.makeText(getBaseContext(), getString(R.string.createevent_success), Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getBaseContext(), getString(R.string.createevent_fail), Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(getBaseContext(), getString(R.string.createevent_validate), Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sukien, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.action_event_save:
			createEvent();
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
