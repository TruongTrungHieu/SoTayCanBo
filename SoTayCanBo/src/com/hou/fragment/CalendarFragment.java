package com.hou.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hou.sotaycanbo.NoteActivity;
import com.hou.sotaycanbo.R;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

public class CalendarFragment extends Fragment {

	private boolean undo = false;
	private CaldroidFragment caldroidFragment;
	private Calendar cal;
	Date currentDate;
	private final SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		cal = Calendar.getInstance();
		caldroidFragment = new CaldroidFragment();
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_main, container, false);

		// If Activity is created after rotation
		if (savedInstanceState != null) {
			caldroidFragment.restoreStatesFromKey(savedInstanceState,
					"CALDROID_SAVED_STATE");
		}
		// If activity is created from fresh
		else {
			Bundle args = new Bundle();
//			Calendar cal = Calendar.getInstance();
			args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
			args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
			args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
			args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

			// Uncomment this to customize startDayOfWeek
			// args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
			// CaldroidFragment.TUESDAY); // Tuesday
			caldroidFragment.setArguments(args);
		}

		setCustomResourceForDates();

		// Attach to the activity
		FragmentTransaction t = getChildFragmentManager().beginTransaction();
		t.replace(R.id.calendar1, caldroidFragment);
		t.commit();

		// Setup listener
		final CaldroidListener listener = new CaldroidListener() {

			@Override
			public void onSelectDate(Date date, View view) {
				
				if (currentDate != null && currentDate != date) {
					caldroidFragment.setBackgroundResourceForDate(R.color.white, currentDate);
					caldroidFragment.setTextColorForDate(R.color.black, currentDate);
					caldroidFragment.clearSelectedDates();
					caldroidFragment.clearDisableDates();
					caldroidFragment.refreshView();
					caldroidFragment.setBackgroundResourceForDate(R.drawable.red_border, new Date());
					caldroidFragment.setTextColorForDate(R.color.black, new Date());
				}				
				Toast.makeText(getActivity().getApplicationContext(),
						formatter.format(date), Toast.LENGTH_SHORT).show();
				currentDate = date;
				caldroidFragment.setBackgroundResourceForDate(R.color.blue,
						date);
				caldroidFragment.setTextColorForDate(R.color.white, date);
				caldroidFragment.setCalendarDate(date);
			}

			@Override
			public void onChangeMonth(int month, int year) {
//				String text = "month: " + month + " year: " + year;
//				Toast.makeText(getActivity().getApplicationContext(), text,
//						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onLongClickDate(Date date, View view) {
//				Toast.makeText(getActivity().getApplicationContext(),
//						"Long click " + formatter.format(date),
//						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onCaldroidViewCreated() {
				if (caldroidFragment.getLeftArrowButton() != null) {
//					Toast.makeText(getActivity().getApplicationContext(),
//							"Caldroid view is created", Toast.LENGTH_SHORT)
//							.show();
				}
			}

		};

		// Setup Caldroid
		caldroidFragment.setCaldroidListener(listener);

		final TextView textView = (TextView) view.findViewById(R.id.textview);

		final Button customizeButton = (Button) view
				.findViewById(R.id.customize_button);

		// Customize the calendar
		customizeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (undo) {
					customizeButton.setText(getString(R.string.customize));
					textView.setText("");

					// Reset calendar
					caldroidFragment.clearDisableDates();
					caldroidFragment.clearSelectedDates();
					caldroidFragment.setMinDate(null);
					caldroidFragment.setMaxDate(null);
					caldroidFragment.setShowNavigationArrows(true);
					caldroidFragment.setEnableSwipe(true);
					caldroidFragment.refreshView();
					undo = false;
					return;
				}

				// Else
				undo = true;
				customizeButton.setText(getString(R.string.undo));
//				Calendar cal = Calendar.getInstance();

				// Min date is last 7 days
				cal.add(Calendar.DATE, -7);
				Date minDate = cal.getTime();

				// Max date is next 7 days
				cal = Calendar.getInstance();
				cal.add(Calendar.DATE, 14);
				Date maxDate = cal.getTime();

				// Set selected dates
				// From Date
				cal = Calendar.getInstance();
				cal.add(Calendar.DATE, 2);
				Date fromDate = cal.getTime();

				// To Date
				cal = Calendar.getInstance();
				cal.add(Calendar.DATE, 3);
				Date toDate = cal.getTime();

				// Set disabled dates
				ArrayList<Date> disabledDates = new ArrayList<Date>();
				for (int i = 5; i < 8; i++) {
					cal = Calendar.getInstance();
					cal.add(Calendar.DATE, i);
					disabledDates.add(cal.getTime());
				}

				// Customize
				caldroidFragment.setMinDate(minDate);
				caldroidFragment.setMaxDate(maxDate);
				caldroidFragment.setDisableDates(disabledDates);
				caldroidFragment.setSelectedDates(fromDate, toDate);
				caldroidFragment.setShowNavigationArrows(false);
				caldroidFragment.setEnableSwipe(false);

				caldroidFragment.refreshView();

				// Move to date
				// cal = Calendar.getInstance();
				// cal.add(Calendar.MONTH, 12);
				// caldroidFragment.moveToDate(cal.getTime());

				String text = "Today: " + formatter.format(new Date()) + "\n";
				text += "Min Date: " + formatter.format(minDate) + "\n";
				text += "Max Date: " + formatter.format(maxDate) + "\n";
				text += "Select From Date: " + formatter.format(fromDate)
						+ "\n";
				text += "Select To Date: " + formatter.format(toDate) + "\n";
				for (Date date : disabledDates) {
					text += "Disabled Date: " + formatter.format(date) + "\n";
				}

				textView.setText(text);
			}
		});
		setHasOptionsMenu(true);
		return view;
	}

	private void setCustomResourceForDates() {
//		Calendar cal = Calendar.getInstance();

		// Min date is last 7 days
		cal.add(Calendar.DATE, -18);
		Date blueDate = cal.getTime();

		// Max date is next 7 days
		cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 16);
		Date greenDate = cal.getTime();

		if (caldroidFragment != null) {
			caldroidFragment.setBackgroundResourceForDate(R.color.green,
					blueDate);
			caldroidFragment.setBackgroundResourceForDate(R.color.green,
					greenDate);
			caldroidFragment.setTextColorForDate(R.color.white, blueDate);
			caldroidFragment.setTextColorForDate(R.color.white, greenDate);
		}
	}

	/**
	 * Save current states of the Caldroid here
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

		if (caldroidFragment != null) {
			caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
		}

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.fragment_calendar, menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch (id) {
		case R.id.action_calendar_today:
//			Calendar cal = Calendar.getInstance();
//			cal.add(Calendar.DATE, 0);
//			Date today = cal.getTime();
			Date today = new Date();
			if (currentDate != null && currentDate != today) {
				caldroidFragment.setBackgroundResourceForDate(R.color.white, currentDate);
				caldroidFragment.setTextColorForDate(R.color.black, currentDate);
				caldroidFragment.clearSelectedDates();
				caldroidFragment.clearDisableDates();
				caldroidFragment.refreshView();
				caldroidFragment.setBackgroundResourceForDate(R.drawable.red_border, new Date());
				caldroidFragment.setTextColorForDate(R.color.black, new Date());
			}			
			caldroidFragment.setCalendarDate(today);
			caldroidFragment.setBackgroundResourceForDate(R.color.blue,
					today);
			caldroidFragment.setTextColorForDate(R.color.white, today);
			currentDate = today;
			break;
			
		case R.id.action_calendar_add:
			// create a new event
			Intent createNote = new Intent(getActivity().getApplicationContext(),
					NoteActivity.class);
			createNote.putExtra("TYPE_NOTE", "CREATE_NEW");
			startActivity(createNote);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
