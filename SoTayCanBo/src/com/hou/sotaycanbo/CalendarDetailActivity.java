package com.hou.sotaycanbo;

import com.hou.database_handler.ExecuteQuery;
import com.hou.models.*;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class CalendarDetailActivity extends ActionBarActivity {

	private Event event;
	private ExecuteQuery exeQ;
	
	private TextView tvTenEvent, tvThoigian, tvDiadiem, tvMota;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_detail);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		tvTenEvent = (TextView)findViewById(R.id.tvTenEvent);
		tvThoigian = (TextView)findViewById(R.id.tvThoigian);
		tvDiadiem = (TextView)findViewById(R.id.tvDiadiem);
		tvMota = (TextView)findViewById(R.id.tvMota);
		
		exeQ = new ExecuteQuery(this);
		exeQ.createDatabase();
		exeQ.open();
		
		event = (Event) getIntent().getSerializableExtra("event");
		if (event != null) {
			setupData();
		}
	}

	private void setupData() {
		tvTenEvent.setText(event.getTenEvent());
		tvThoigian.setText(event.getThoigianbatdau() + " - " + event.getNgay_event());
		tvDiadiem.setText(event.getDiadiem());
		tvMota.setText(event.getMota());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calendar_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_delete_event) {
			AlertDialog.Builder buider = new Builder(
					CalendarDetailActivity.this);
			buider.setMessage(CalendarDetailActivity.this
					.getString(R.string.calendar_detail_message));
			buider.setNegativeButton(getString(R.string.calendar_detail_cancel), new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			buider.setNeutralButton(getString(R.string.calendar_detail_ok), new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (event != null) {
						if (exeQ.delete_tblEvent_byMaEvent(event.getMaEvent())) {
							Toast.makeText(getBaseContext(), getString(R.string.calendar_detail_del_ok), Toast.LENGTH_SHORT).show();
							CalendarDetailActivity.this.onBackPressed();
						}
					}
				}
			});
			buider.create().show();
			return true;
		}
		if (id == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
}
