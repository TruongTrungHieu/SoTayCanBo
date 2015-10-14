package com.hou.sotaycanbo;

import java.util.ArrayList;

import com.hou.adapters.CanboAdapter;
import com.hou.database_handler.ExecuteQuery;
import com.hou.models.CanBo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class DanhSachCanBoActivity extends ActionBarActivity {

	private ListView lvCanbo;

	private ExecuteQuery exeQ;
	private ArrayList<CanBo> listCanbo = null;
	private CanboAdapter adapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_danh_sach_can_bo);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		exeQ = new ExecuteQuery(getApplicationContext());
		exeQ.createDatabase();
		exeQ.open();

		listCanbo = new ArrayList<CanBo>();
		// listCanbo = exeQ.getAllCanboFromDV();
		listCanbo.add(new CanBo("CB1", "1", "2", "Mai Lan", "Lương Định Của",
				"aa@gmail.com", "111111", "Thac sy", "", ""));
		listCanbo.add(new CanBo("CB1", "1", "2", "Nguyễn Thị Húngw",
				"Lương Định Của", "aa@gmail.com", "111111", "Thac sy", "", ""));
		listCanbo.add(new CanBo("CB1", "1", "2", "Mai Lan", "Lương Định Của",
				"aa@gmail.com", "111111", "Thac sy", "", ""));
		listCanbo.add(new CanBo("CB1", "1", "2", "Tùng", "Lương Định Của",
				"aa@gmail.com", "111111", "Thac sy", "", ""));
		listCanbo.add(new CanBo("CB1", "1", "2", "Mai Lan", "Lương Định Của",
				"aa@gmail.com", "111111", "Thac sy", "", ""));

		lvCanbo = (ListView) findViewById(R.id.lvCanbo);
		adapter = new CanboAdapter(getApplicationContext(),
				R.layout.itemlist_canbo, listCanbo);

		lvCanbo.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// open CanboActivity
			}
		});
		
		lvCanbo.setAdapter(adapter);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.danh_sach_can_bo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			onBackPressed();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
