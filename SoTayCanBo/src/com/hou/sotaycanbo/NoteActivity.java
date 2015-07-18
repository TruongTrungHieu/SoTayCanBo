package com.hou.sotaycanbo;

import com.hou.models.GhiChu;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NoteActivity extends ActionBarActivity {

	private NoteMode mNoteMode;
	private Menu currentMenu;
	private Dialog mDialogAttachment;

	private TextView tvTensotay;
	private EditText edtNoidung, edtTenghichu;
	private ImageView imgClock;

	private GhiChu mGhichu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		/*
		 * Init
		 */
		tvTensotay = (TextView) findViewById(R.id.tvTensotay);
		edtNoidung = (EditText) findViewById(R.id.edtNoidung);
		edtTenghichu = (EditText) findViewById(R.id.edtTenghichu);
		imgClock = (ImageView) findViewById(R.id.imgClock);

		String type = getIntent().getExtras().getString("TYPE_NOTE");
		if (type.equals("READ")) {
			// READ NOTE FROM LIST NOTE
			showNoteFromIntent();
			mNoteMode = NoteMode.READ;
		} else {
			// CREATE NEW NOTE
			mNoteMode = NoteMode.EDIT;
		}

		onClickListener();

	}

	public void onClickListener() {
		imgClock.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				hideKeyboard();
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.oclock),
						Toast.LENGTH_LONG).show();
			}
		});

		tvTensotay.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				hideKeyboard();
			}
		});
	}

	private enum NoteMode {
		EDIT, READ
	}

	public void changeMode() {
		if (mNoteMode == NoteMode.READ) {
			currentMenu.getItem(0).setVisible(false);
			currentMenu.getItem(1).setVisible(false);
			currentMenu.getItem(2).setVisible(false);
			currentMenu.getItem(3).setVisible(true);
			currentMenu.getItem(4).setVisible(true);
			currentMenu.getItem(5).setVisible(true);
		} else {
			currentMenu.getItem(0).setVisible(true);
			currentMenu.getItem(1).setVisible(true);
			currentMenu.getItem(2).setVisible(true);
			currentMenu.getItem(3).setVisible(false);
			currentMenu.getItem(4).setVisible(false);
			currentMenu.getItem(5).setVisible(false);
		}
	}

	public void showNoteFromIntent() {
		mGhichu = (GhiChu) getIntent().getSerializableExtra("NOTE");
		edtTenghichu.setText(mGhichu.getTenGhiChu());
		edtNoidung.setText(mGhichu.getNoidung());

	}

	public void showDialogAttachment() {
		mDialogAttachment = new Dialog(this, R.style.TransparentDialogTheme);
		mDialogAttachment.getWindow().setGravity(Gravity.BOTTOM);
		mDialogAttachment.setContentView(R.layout.dialog_note_attachment);
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		Window window = mDialogAttachment.getWindow();
		params.copyFrom(window.getAttributes());
		params.width = WindowManager.LayoutParams.MATCH_PARENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		window.setAttributes(params);

		TextView tvDialog_album = (TextView) mDialogAttachment
				.findViewById(R.id.tvDialog_album);
		TextView tvDialog_file = (TextView) mDialogAttachment
				.findViewById(R.id.tvDialog_file);
		TextView tvDialog_voice = (TextView) mDialogAttachment
				.findViewById(R.id.tvDialog_voice);

		tvDialog_album.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "ALBUM",
						Toast.LENGTH_SHORT).show();
				mDialogAttachment.dismiss();
			}
		});

		tvDialog_file.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "FILE",
						Toast.LENGTH_SHORT).show();
				mDialogAttachment.dismiss();
			}
		});

		tvDialog_voice.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "VOICE",
						Toast.LENGTH_SHORT).show();
				mDialogAttachment.dismiss();
			}
		});

		mDialogAttachment.show();
	}

	private void hideKeyboard() {
		// Check if no view has focus:
		View view = this.getCurrentFocus();
		if (view != null) {
			InputMethodManager inputManager = (InputMethodManager) this
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(view.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.note, menu);
		currentMenu = menu;
		changeMode();
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		hideKeyboard();
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			Intent home = new Intent(NoteActivity.this,
					FragmentManagerActivity.class);
			startActivity(home);
			NoteActivity.this.finish();
			break;

		case R.id.action_note_dinhkem:
			showDialogAttachment();
			break;

		case R.id.action_note_chupanh:
			break;

		case R.id.action_note_create:
			mNoteMode = NoteMode.READ;
			changeMode();
			break;

		case R.id.action_note_message:
			break;

		case R.id.action_note_share:
			String body = edtTenghichu.getText().toString() + "\n"
					+ edtNoidung.getText().toString();
			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND);
			shareIntent.putExtra(Intent.EXTRA_STREAM, body);
			shareIntent.setType("text/plan");
			startActivity(Intent.createChooser(shareIntent, getResources()
					.getString(R.string.title_share)));
			break;

		case R.id.action_note_modify:
			mNoteMode = NoteMode.EDIT;
			changeMode();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
