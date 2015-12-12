package com.hou.sotaycanbo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class HelpActivity extends ActionBarActivity {

	private TextView tvEmailTeam, tvPhoneTeam;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		tvEmailTeam = (TextView) findViewById(R.id.tvEmailTeam);
		tvPhoneTeam = (TextView) findViewById(R.id.tvPhoneTeam);
		
		tvEmailTeam.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent gmail = new Intent(Intent.ACTION_VIEW);
				gmail.setClassName("com.google.android.gm",
						"com.google.android.gm.ComposeActivityGmail");
				gmail.putExtra(Intent.EXTRA_EMAIL,
						new String[] { "h2p.groups@gmail.com" });
				gmail.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.help_subject_mail));
				gmail.setType("plain/text");
				startActivity(gmail);
			}
		});
		
		tvPhoneTeam.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:" + "01646970797"));
				startActivity(callIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == android.R.id.home) {
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
