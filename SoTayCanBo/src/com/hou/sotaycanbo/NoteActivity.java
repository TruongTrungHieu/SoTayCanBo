package com.hou.sotaycanbo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.hou.adapters.AttachmentAdapter;
import com.hou.adapters.SotayAdapter;
import com.hou.app.Const;
import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.hou.models.DinhKem;
import com.hou.models.GhiChu;
import com.hou.models.SoTay;
import com.hou.ultis.IntentUtils;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class NoteActivity extends ActionBarActivity implements OnClickListener {

	private static final int REQUESTCODE_PICK_FILE = 3;
	private static final int REQUESTCODE_PICK_IMAGE = 1;
	@SuppressWarnings("unused")
	private static final int REQUESTCODE_PICK_AUDIO = 2;
	private NoteMode mNoteMode;
	private Menu currentMenu;
	private Dialog mDialogAttachment;

	private TextView tvTensotay;
	private EditText edtNoidung, edtTenghichu;
	private ImageView imgClock;

	private GhiChu mGhichu;
	private AttachmentAdapter adapter = null;
	private ListView lvAttachment;
	private ArrayList<DinhKem> listAttachment = null;

	private boolean isCreatNew = false;

	private ExecuteQuery exeQ;
	private DatePickerDialog datePickerDialog;
	private long datePicked = 0;
	private SoTay sotay = new SoTay();
	String MACANBO, CURRENTTIME;
	List<SoTay> listSotay;

	private boolean isRecording = false;
	public MediaRecorder recorder;
	private Point screenSize;
	boolean isSave = false;
	TimePickerDialog timePickerDialog;
	private PendingIntent pendingIntent;
	private AlarmManager alarmManager;

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
		MACANBO = Global.getMaCanBo(NoteActivity.this);
		CURRENTTIME = Global.getCurrentTime();
		listSotay = exeQ.getAllSotay();
		tvTensotay = (TextView) findViewById(R.id.tvTensotay);
		edtNoidung = (EditText) findViewById(R.id.edtNoidung);
		edtTenghichu = (EditText) findViewById(R.id.edtTenghichu);
		imgClock = (ImageView) findViewById(R.id.imgClock);
		showNoteFromIntent();
		String type = getIntent().getExtras().getString("TYPE_NOTE");
		if (type.equals("READ")) {
			// READ NOTE FROM LIST NOTE

			mNoteMode = NoteMode.READ;
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		} else {
			// CREATE NEW NOTE
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
			mNoteMode = NoteMode.EDIT;
			isCreatNew = true;
		}
		if (getIntent().getBooleanExtra("from_alarm", false)) {
			Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
	        if (alarmUri == null) {
	            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	        }
	        final Ringtone ringtone = RingtoneManager.getRingtone(this, alarmUri);
	        ringtone.play();
//	        Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
//			vibrator.vibrate(3000);
	        new Timer().schedule(new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					ringtone.stop();
				}
			}, 3000);
			
		}
		// Listview attachment
		lvAttachment = (ListView) findViewById(R.id.lvAttachment);
		listAttachment = exeQ.getAllDinhKemByMaGhiChu(mGhichu.getMaGhiChu());
		adapter = new AttachmentAdapter(this, R.layout.itemlist_attachment,
				listAttachment);
		lvAttachment.setAdapter(adapter);
		lvAttachment.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String url = listAttachment.get(arg2).getUrl();
				String type = getMimeType(url);
				if (type.equals("video/3gpp")) {
					type = "audio/*";
				}
				Intent viewIntent = new Intent(Intent.ACTION_VIEW);
				File file = new File(url);
				viewIntent.setDataAndType(Uri.fromFile(file), type);
				startActivity(Intent.createChooser(viewIntent, null));
			}
		});
		imgClock.setOnClickListener(this);
		tvTensotay.setOnClickListener(this);
		if (screenSize == null) {
			screenSize = new Point();
			getWindowManager().getDefaultDisplay().getSize(screenSize);
		}
		
		pendingIntent = setupPendingIntent();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
	}

	public static String getMimeType(String url) {
		String type = null;
		String extension = MimeTypeMap.getFileExtensionFromUrl(url);
		if (extension != null) {
			type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
					extension);
		}
		return type;
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

			if (listSotay.size() > 0 && mNoteMode == NoteMode.EDIT) {
				showDialogAttachment(R.layout.fragment_sotay);
			}
			if (listSotay.size() == 0) {
				tvTensotay.setText("Sổ tay mới");
				sotay = new SoTay("ST" + CURRENTTIME, "Sổ tay mới",
						Global.getCurrentDateTime(), 0, MACANBO);
				exeQ.insert_tblSotay_single(sotay);
			}
			break;

		case R.id.tvDialog_album:
			showIntentGetContent("image", REQUESTCODE_PICK_IMAGE);
			mDialogAttachment.dismiss();
			break;

		case R.id.tvDialog_file:
			showIntentGetContent("file", REQUESTCODE_PICK_FILE);
			mDialogAttachment.dismiss();
			break;

		case R.id.tvDialog_voice:
			new DialogRecording(this).show();
			mDialogAttachment.dismiss();
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && data != null) {
			switch (requestCode) {
			case REQUESTCODE_PICK_IMAGE:
				getAttachFileInfo(data, Const.ATTACHMENT_IMAGE);
				break;
			case REQUESTCODE_PICK_FILE:
				getAttachFileInfo(data, Const.ATTACHMENT_FILE);
				break;
			}
		} else {
			Toast.makeText(NoteActivity.this,
					"Can't get your file, please select different file",
					Toast.LENGTH_LONG).show();
		}
	}

	public void getAttachFileInfo(Intent data, String type) {
		String filePath = "";
		String fileName = "";

		filePath = IntentUtils.getPath(this, data.getData());
		String[] temp = filePath.split("/");
		fileName = temp[temp.length - 1];
		listAttachment.add(new DinhKem("1", "1", type, filePath, fileName));

		adapter.notifyDataSetChanged();
	}

	public void showDialogAttachment(int layout) { // Dialog to attach file
		mDialogAttachment = new Dialog(this, R.style.TransparentDialogTheme);
		mDialogAttachment.getWindow().setGravity(Gravity.BOTTOM);
		mDialogAttachment.setContentView(layout);
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		Window window = mDialogAttachment.getWindow();
		params.copyFrom(window.getAttributes());
		params.width = WindowManager.LayoutParams.MATCH_PARENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		window.setAttributes(params);

		// Set animation when opening dialog
		window.getAttributes().windowAnimations = R.style.DialogAnimation;
		switch (layout) {
		case R.layout.dialog_note_attachment:
			TextView tvDialog_album = (TextView) mDialogAttachment
					.findViewById(R.id.tvDialog_album);
			TextView tvDialog_file = (TextView) mDialogAttachment
					.findViewById(R.id.tvDialog_file);
			TextView tvDialog_voice = (TextView) mDialogAttachment
					.findViewById(R.id.tvDialog_voice);
			tvDialog_album.setOnClickListener(this);
			tvDialog_file.setOnClickListener(this);
			tvDialog_voice.setOnClickListener(this);
			break;

		case R.layout.fragment_sotay:
			ListView lvSotay = (ListView) mDialogAttachment
					.findViewById(R.id.lvSotay);
			SotayAdapter adapter = new SotayAdapter(NoteActivity.this,
					R.layout.itemlist_sotay, listSotay);
			lvSotay.setAdapter(adapter);
			lvSotay.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					sotay = listSotay.get(arg2);
					tvTensotay.setText(sotay.getTenSoTay());
					mDialogAttachment.dismiss();
				}
			});
			break;
		}

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
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if (!isSave) {
			for (DinhKem dinhKem : listAttachment) {
				if (dinhKem.getLoaifile().equals(Const.ATTACHMENT_VOICE)) {
					File file = new File(dinhKem.getUrl());
					file.delete();
				}
			}
		}
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
			showDialogAttachment(R.layout.dialog_note_attachment);
			break;

		// case R.id.action_note_chupanh:
		// break;

		case R.id.action_note_create:
			mNoteMode = NoteMode.READ;
			changeMode();
			createNewNote();
			if (isCreatNew) {
				isCreatNew = !isCreatNew;
			} else {
				
			}
			isSave = true;
			if (datePicked > Calendar.getInstance().getTimeInMillis()) {
				alarmManager.set(AlarmManager.RTC, datePicked, pendingIntent);	
			}			
			break;

		// case R.id.action_note_message:
		// break;

		case R.id.action_note_share:
			String body = edtTenghichu.getText().toString() + "\n"
					+ edtNoidung.getText().toString();
			Intent shareIntent = new Intent();

			shareIntent.setAction(Intent.ACTION_SEND);
			shareIntent.putExtra(Intent.EXTRA_TEXT, body);
			ArrayList<Uri> listUri = new ArrayList<Uri>();
			for (DinhKem dinhKem : listAttachment) {
				Uri uri = Uri.fromFile(new File(dinhKem.getUrl()));
				listUri.add(uri);
			}
			shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,
					listUri);
			shareIntent.setType("*/*");
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

	@SuppressWarnings("unused")
	public void setUpDateTimePicker() {
		Calendar newCalendar = Calendar.getInstance();
		final int hourSelected = 0;
		final int minuteSelected = 0;
		final Calendar newDate = Calendar.getInstance();
		
		timePickerDialog = new TimePickerDialog(NoteActivity.this,
				new OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker timePicker, int hour,
							int minute) {
						// TODO Auto-generated method stub
						newDate.set(Calendar.HOUR_OF_DAY, hour);
						newDate.set(Calendar.MINUTE, minute);						
						datePicked = newDate.getTimeInMillis();
						Log.d("Date Select", newDate.get(Calendar.DAY_OF_MONTH) + " " + newDate.get(Calendar.MONTH) + " " + newDate.get(Calendar.YEAR) + " " + newDate.get(Calendar.HOUR_OF_DAY) + " " + newDate.get(Calendar.MINUTE));
						Log.d("Date Select", datePicked + "");
						Log.d("Date Select", newDate.getTime().toString());					
						
					}
				}, newCalendar.get(Calendar.HOUR_OF_DAY),
				newCalendar.get(Calendar.MINUTE), false);
		datePickerDialog = new DatePickerDialog(NoteActivity.this,
				new DatePickerDialog.OnDateSetListener() {

					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						newDate.set(year, monthOfYear, dayOfMonth);
						timePickerDialog.show();						
					}

				}, newCalendar.get(Calendar.YEAR),
				newCalendar.get(Calendar.MONTH),
				newCalendar.get(Calendar.DAY_OF_MONTH));

	}
	
	public PendingIntent setupPendingIntent(){
    	Intent myIntent = new Intent(NoteActivity.this, AlarmReceiver.class);
    	myIntent.putExtra("note", mGhichu);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);
        return pendingIntent;
    }

	public void createNewNote() {
		if ((edtNoidung.toString().trim().length() <= 0)
				&& (edtTenghichu.toString().trim().length() <= 0)) {
			Toast.makeText(getBaseContext(),
					getResources().getString(R.string.cant_insert),
					Toast.LENGTH_SHORT).show();
			Intent home = new Intent(NoteActivity.this,
					FragmentManagerActivity.class);
			startActivity(home);
			NoteActivity.this.finish();
		} else {
			// Insert
			int dinhkem = listAttachment.size();
			String noidung = edtNoidung.getText().toString().trim();
			String tenGhichu = edtTenghichu.getText().toString().trim();
			long ngaytao = Global.getCurrentDateTime();
			long ngaysua = Global.getCurrentDateTime();
			long ngaythuchien = datePicked;
			int trangthai = 0;
			int bookmark = 0;
			String maSotay = sotay.getMaSoTay();
			
			mGhichu.setTenGhiChu(tenGhichu);
			mGhichu.setNgaytao(ngaytao);
			mGhichu.setNoidung(noidung);
			mGhichu.setNgaysua(ngaysua);
			mGhichu.setTrangthai(trangthai);
			mGhichu.setDinhkem(dinhkem);
			mGhichu.setNgaythuchien(ngaythuchien);
			mGhichu.setBookmark(bookmark);
			mGhichu.setMaSotay(maSotay);
			
			if (isCreatNew) {
				mGhichu.setMaGhiChu("NT" + CURRENTTIME);
				mGhichu.setNgaytao(ngaytao);
				if (exeQ.insert_tblGhichu_single(mGhichu)) {
					Toast.makeText(getBaseContext(),
							getResources().getString(R.string.save_sucessful),
							Toast.LENGTH_SHORT).show();
					exeQ.update_tblSotay_soGhichu(mGhichu.getMaSotay(), true);
				}
			} else {
				if (exeQ.update_tblGhichu(mGhichu)) {
					Toast.makeText(getBaseContext(),
							getResources().getString(R.string.save_sucessful),
							Toast.LENGTH_SHORT).show();
				}
			}
			if (dinhkem > 0) {
				for (int i = 0; i < dinhkem; i++) {
					DinhKem dinhKem2 = listAttachment.get(i);
					if (dinhKem2.getMaDinhkem().equals("1")) {
						dinhKem2.setMaGhichu(mGhichu.getMaGhiChu());
						dinhKem2.setMaDinhkem("DK_" + mGhichu.getMaGhiChu()
								+ "_" + i);
						exeQ.insert_tblDinhkem_single(dinhKem2);
					}
				}
			}
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

			edtTenghichu.setEnabled(false);
			edtNoidung.setEnabled(false);
			imgClock.setEnabled(false);
		} else {
			currentMenu.getItem(0).setVisible(true);
			currentMenu.getItem(1).setVisible(true);
			currentMenu.getItem(2).setVisible(false);
			currentMenu.getItem(3).setVisible(false);

			edtTenghichu.setEnabled(true);
			edtNoidung.setEnabled(true);
			imgClock.setEnabled(true);
		}
	}

	public void showNoteFromIntent() {
		mGhichu = (GhiChu) getIntent().getSerializableExtra("NOTE");
		if (mGhichu != null) {
			edtTenghichu.setText(mGhichu.getTenGhiChu());
			edtNoidung.setText(mGhichu.getNoidung());
			sotay = exeQ.getSotayByMaSotay(mGhichu.getMaSotay());
			tvTensotay.setText(sotay.getTenSoTay());
		} else {
			mGhichu = new GhiChu();
		}
	}

	public void showIntentGetContent(String type, int requestCode) {
		String typeStr = type + "/*";
		Intent mediaIntent = new Intent(Intent.ACTION_GET_CONTENT);
		mediaIntent.setType(typeStr); // set mime type as per requirement
		mediaIntent.putExtra("return-data", true);
		startActivityForResult(mediaIntent, requestCode);
	}

	public class DialogRecording extends Dialog implements OnClickListener {

		TextView tvTimeCounter, tvSave;
		ImageView ivRecording;
		NoteActivity activity;
		MediaRecorder myAudioRecorder;
		CountDownTimer timer;
		String outputFile, name;

		public DialogRecording(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			activity = (NoteActivity) context;
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.dialog_recording_audio);
			setCancelable(false);
			getWindow().setLayout((int) (screenSize.x * 0.99),
					(int) (screenSize.y * 0.55));
			getWindow().getDecorView().setBackgroundResource(0);
			WindowManager.LayoutParams attributes = getWindow().getAttributes();
			attributes.gravity = Gravity.CENTER;
			initView();
			name = Calendar.getInstance().getTimeInMillis() + "_record.3gp";
			outputFile = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/" + name;

			myAudioRecorder = new MediaRecorder();
			myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			myAudioRecorder
					.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
			myAudioRecorder.setOutputFile(outputFile);
			timer = new CountDownTimer(TimeUnit.MINUTES.toMillis(1000),
					TimeUnit.SECONDS.toMillis(1), activity, tvTimeCounter);
		}

		public void initView() {
			tvTimeCounter = (TextView) findViewById(R.id.tvTimeCounter);
			ivRecording = (ImageView) findViewById(R.id.ivRecording);

			ivRecording.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.ivRecording:
				if (isRecording) {
					timer.cancel();
					myAudioRecorder.stop();
					myAudioRecorder.release();
					dismiss();
					listAttachment.add(new DinhKem("1", "1",
							Const.ATTACHMENT_VOICE, outputFile, name));
					adapter.notifyDataSetChanged();
				} else {
					ivRecording.setImageResource(R.drawable.pause);
					timer.start();
					try {
						myAudioRecorder.prepare();
						myAudioRecorder.start();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				isRecording = !isRecording;
				break;
			}

		}

		class CountDownTimer extends android.os.CountDownTimer {
			@SuppressWarnings("unused")
			private Context context;
			private long timeTick, millisInFuture;
			private TextView txtCountDown;

			public CountDownTimer(long millisInFuture, long countDownInterval,
					Context context, TextView txtCountDown) {
				super(millisInFuture, countDownInterval);
				this.context = context;
				this.txtCountDown = txtCountDown;
				this.millisInFuture = millisInFuture;
			}

			public long getCurrentTime() {
				return timeTick;
			}

			@Override
			public void onTick(long millisUntilFinished) {
				timeTick = millisUntilFinished;
				txtCountDown.setText(String.format("%02d:%02d",
						((millisInFuture - millisUntilFinished) / 60000),
						59 - ((millisUntilFinished) % 60000 / 1000)));
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub

			}
		}
	}
}
