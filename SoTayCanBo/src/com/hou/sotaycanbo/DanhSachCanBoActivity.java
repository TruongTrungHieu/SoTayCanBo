package com.hou.sotaycanbo;

import java.util.ArrayList;

import com.hou.adapters.CanboAdapter;
import com.hou.database_handler.ExecuteQuery;
import com.hou.models.CanBo;
import com.hou.models.DonVi;
import com.hou.quickaction.ActionItem;
import com.hou.quickaction.QuickAction;

import android.content.Intent;
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

	private DonVi mDonvi;
	@SuppressWarnings("unused")
	private int pos = -1;

	// Quick Action
	private static final int ID_INFOR = 1;
	private static final int ID_CALL = 2;
	private static final int ID_SMS = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_danh_sach_can_bo);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		mDonvi = (DonVi) getIntent().getSerializableExtra("donvi");
		if (mDonvi != null) {
			this.setTitle(mDonvi.getTenDonvi().toString());
		}

		exeQ = new ExecuteQuery(getApplicationContext());
		exeQ.createDatabase();
		exeQ.open();

		/*
		 * Quick Action
		 */
		ActionItem inforItem = new ActionItem(ID_INFOR, "Infor", getResources()
				.getDrawable(R.drawable.quick_infor));
		ActionItem callItem = new ActionItem(ID_CALL, "Call", getResources()
				.getDrawable(R.drawable.quick_call));
		ActionItem smsItem = new ActionItem(ID_SMS, "SMS", getResources()
				.getDrawable(R.drawable.quick_sms));
		
		final QuickAction quickAction = new QuickAction(this,
				QuickAction.HORIZONTAL);

		quickAction.addActionItem(inforItem);
		quickAction.addActionItem(callItem);
		quickAction.addActionItem(smsItem);
		
		quickAction
				.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
					@Override
					public void onItemClick(QuickAction source, int pos,
							int actionId) {
						switch (actionId) {
						case ID_INFOR:
							Intent i = new Intent(DanhSachCanBoActivity.this, CanboActivity.class);
							i.putExtra("canbo", listCanbo.get(pos));
							startActivity(i);
							overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
							break;
						case ID_CALL:
							
							break;
						case ID_SMS:
							
							break;
						default:
							break;
						}
					}
				});

		listCanbo = new ArrayList<CanBo>();
		// listCanbo = exeQ.getAllCanboFromDV(String maDonvi);
		listCanbo.add(new CanBo("CB1", "1", "Mai Lan", "Lương Định Của", "a@gmail.com",
				"aa@gmail.com", "111111", "Thac sy", "avatar.jpeg", ""));
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
				pos = position;
				quickAction.show(view);
			}
		});

		lvCanbo.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.danh_sach_can_bo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
