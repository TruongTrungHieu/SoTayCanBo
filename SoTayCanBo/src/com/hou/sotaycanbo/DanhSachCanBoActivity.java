package com.hou.sotaycanbo;

import java.util.ArrayList;

import com.hou.adapters.CanboAdapter;
import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.hou.models.CanBo;
import com.hou.models.DonVi;
import com.hou.quickaction.ActionItem;
import com.hou.quickaction.QuickAction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class DanhSachCanBoActivity extends ActionBarActivity {

	private ListView lvCanbo;

	private ExecuteQuery exeQ;
	private ArrayList<CanBo> listCanbo = null;
	private CanboAdapter adapter = null;

	private DonVi mDonvi;
	private int posI = -1;

	// Quick Action
	private static final int ID_INFOR = 1;
	private static final int ID_CALL = 2;
	private static final int ID_SMS = 3;
	private static final int ID_EMAIL = 4;

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
		ActionItem callItem = new ActionItem(ID_CALL, " Call ", getResources()
				.getDrawable(R.drawable.quick_call));
		ActionItem smsItem = new ActionItem(ID_SMS, "SMS", getResources()
				.getDrawable(R.drawable.quick_sms));
		ActionItem emailItem = new ActionItem(ID_EMAIL, "Email", getResources()
				.getDrawable(R.drawable.quick_email));
		
		final QuickAction quickAction = new QuickAction(this,
				QuickAction.HORIZONTAL);

		quickAction.addActionItem(inforItem);
		quickAction.addActionItem(callItem);
		quickAction.addActionItem(smsItem);
		quickAction.addActionItem(emailItem);

		quickAction
				.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
					@Override
					public void onItemClick(QuickAction source, int pos,
							int actionId) {
						switch (actionId) {
						case ID_INFOR:
							Intent i = new Intent(DanhSachCanBoActivity.this,
									CanboActivity.class);
							i.putExtra("canbo_details", listCanbo.get(posI));
							startActivity(i);
							overridePendingTransition(R.anim.slide_in_right,
									R.anim.fade_out);
							break;
						case ID_CALL:
							String sdt = listCanbo.get(posI).getSdt().trim();
							if (sdt.length() > 0) {
								Intent callIntent = new Intent(
										Intent.ACTION_CALL);
								callIntent.setData(Uri.parse("tel:" + sdt));
								startActivity(callIntent);
							}
							break;
						case ID_SMS:
							String sms = listCanbo.get(posI).getSdt().trim();
							if (sms.length() > 0) {
								String uri = "smsto:" + sms;
								Intent intent = new Intent(
										Intent.ACTION_SENDTO, Uri.parse(uri));
								intent.putExtra("compose_mode", true);
								startActivity(intent);
							}
							break;
						case ID_EMAIL:
							String email = listCanbo.get(posI).getEmail().trim();
							if (email.length() > 0) {
								if (Global.hasNetworkConnection(getApplicationContext())) {
									Intent gmail = new Intent(Intent.ACTION_VIEW);
									gmail.setClassName("com.google.android.gm",
											"com.google.android.gm.ComposeActivityGmail");
									gmail.putExtra(Intent.EXTRA_EMAIL,
											new String[] { email });
									gmail.setType("plain/text");
									startActivity(gmail);
								} else {
									Toast.makeText(getBaseContext(),
											getString(R.string.no_internet),
											Toast.LENGTH_SHORT).show();
								}
							}
						default:
							break;
						}
					}
				});

		listCanbo = new ArrayList<CanBo>();
		listCanbo = exeQ.getAllCanboFromDV(mDonvi.getMaDonvi());

		lvCanbo = (ListView) findViewById(R.id.lvCanbo);
		adapter = new CanboAdapter(getApplicationContext(),
				R.layout.itemlist_canbo, listCanbo);

		lvCanbo.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				posI = position;
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
