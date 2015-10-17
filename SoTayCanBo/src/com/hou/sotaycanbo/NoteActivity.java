package com.hou.sotaycanbo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.hou.adapters.AttachmentAdapter;
import com.hou.app.Const;
import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.hou.models.DinhKem;
import com.hou.models.GhiChu;
import com.hou.models.SoTay;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NoteActivity extends ActionBarActivity implements OnClickListener {

	private NoteMode mNoteMode;
	private Menu currentMenu;
	private Dialog mDialogAttachment;

	private TextView tvTensotay;
	private EditText edtNoidung, edtTenghichu;
	private ImageView imgClock;

	private GhiChu mGhichu;
	private SoTay mSotay;

	private AttachmentAdapter adapter = null;
	private ListView lvAttachment;
	private ArrayList<DinhKem> listAttachment = null;

	private boolean isCreatNew = false;

	private ExecuteQuery exeQ;
	private DatePickerDialog datePickerDialog;
	private SimpleDateFormat dateFormatter;
	private long datePicked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		exeQ = new ExecuteQuery(getApplicationContext());
		exeQ.createDatabase();
		exeQ.open();

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
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		} else {
			// CREATE NEW NOTE
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
			mNoteMode = NoteMode.EDIT;
			isCreatNew = true;
			mGhichu = new GhiChu();
		}

		// Listview attachment
		lvAttachment = (ListView) findViewById(R.id.lvAttachment);

		listAttachment = new ArrayList<DinhKem>();
		listAttachment.add(new DinhKem("1", "1", Const.ATTACHMENT_FILE
				.toString(), "", "abc.txt"));
		listAttachment.add(new DinhKem("1", "1", Const.ATTACHMENT_IMAGE
				.toString(), "", "abc.png"));
		listAttachment.add(new DinhKem("1", "1", Const.ATTACHMENT_VOICE
				.toString(), "", "abc.mp3"));
		adapter = new AttachmentAdapter(this, R.layout.itemlist_attachment,
				listAttachment);

		lvAttachment.setAdapter(adapter);

		imgClock.setOnClickListener(this);
		tvTensotay.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub\
		switch (v.getId()) {
		case R.id.imgClock:
			hideKeyboard();
			setUpDateTimePicker();
			datePickerDialog.show();
			break;

		case R.id.tvTensotay:
			hideKeyboard();
			break;
		}

	}

	private enum NoteMode {
		EDIT, READ
	}

	public void changeMode() {
		if (mNoteMode == NoteMode.READ) {
			currentMenu.getItem(0).setVisible(false);
			currentMenu.getItem(1).setVisible(false);
			currentMenu.getItem(2).setVisible(true);
			currentMenu.getItem(3).setVisible(true);
//			currentMenu.getItem(4).setVisible(true);
//			currentMenu.getItem(5).setVisible(true);

			edtTenghichu.setEnabled(false);
			edtNoidung.setEnabled(false);
			imgClock.setEnabled(false);
		} else {
			currentMenu.getItem(0).setVisible(true);
			currentMenu.getItem(1).setVisible(true);
			currentMenu.getItem(2).setVisible(false);
			currentMenu.getItem(3).setVisible(false);
//			currentMenu.getItem(4).setVisible(false);
//			currentMenu.getItem(5).setVisible(false);

			edtTenghichu.setEnabled(true);
			edtNoidung.setEnabled(true);
			imgClock.setEnabled(true);
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

		// Set animation when opening dialog
		window.getAttributes().windowAnimations = R.style.DialogAnimation;

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
			onBackPressed();
			NoteActivity.this.finish();
			break;

		case R.id.action_note_dinhkem:
			showDialogAttachment();
			break;

//		case R.id.action_note_chupanh:
//			break;

		case R.id.action_note_create:
			mNoteMode = NoteMode.READ;
			changeMode();
			if (isCreatNew) {
				Toast.makeText(getApplicationContext(), "INSERT",
						Toast.LENGTH_SHORT).show();
				isCreatNew = !isCreatNew;
				createNewNote();
			} else {
				Toast.makeText(getApplicationContext(), "UPDATE",
						Toast.LENGTH_SHORT).show();
			}
			break;

//		case R.id.action_note_message:
//			break;

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
	public void setUpDateTimePicker() {        
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(NoteActivity.this, new DatePickerDialog.OnDateSetListener() {            

			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                datePicked = newDate.getTimeInMillis();           
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
	public void createNewNote() {
		if ((edtNoidung.toString().trim().length() <= 0)
				&& (edtTenghichu.toString().trim().length() <= 0)) {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.cant_insert),
					Toast.LENGTH_SHORT).show();
			Intent home = new Intent(NoteActivity.this,
					FragmentManagerActivity.class);
			startActivity(home);
			NoteActivity.this.finish();
		} else {
			// Insert
			int dinhkem = listAttachment.size();
			String maGhichu = Global.getPreference(getApplicationContext(),
					Const.USER_EMAIL) + "_" + Global.getCurrentDateTime();
			String noidung = "";
			if (edtNoidung.toString().trim().length() > 0) {
				noidung = edtNoidung.getText().toString();
			}
			String tenGhichu = "";
			if (edtTenghichu.toString().trim().length() > 0) {
				tenGhichu = edtTenghichu.getText().toString();
			} else {
				tenGhichu = noidung;
			}
			long ngaytao = Global.getCurrentDateTime();
			long ngaysua = Global.getCurrentDateTime();
			long ngaythuchien = datePicked;
			int trangthai = 0;
			int bookmark = 0;
			String maSotay = "1";
			// String maSotay = mSotay.getMaSoTay();

			mGhichu.setMaGhiChu(maGhichu);
			mGhichu.setTenGhiChu(tenGhichu);
			mGhichu.setNgaytao(ngaytao);
			mGhichu.setNoidung(noidung);
			mGhichu.setNgaysua(ngaysua);
			mGhichu.setTrangthai(trangthai);
			mGhichu.setDinhkem(dinhkem);
			mGhichu.setNgaythuchien(ngaythuchien);
			mGhichu.setBookmark(bookmark);
			mGhichu.setMaSotay(maSotay);

			if (exeQ.insert_tblGhichu_single(mGhichu)) {
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.save_sucessful),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

}
