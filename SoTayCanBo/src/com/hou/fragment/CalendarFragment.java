package com.hou.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hou.database_handler.ExecuteQuery;
import com.hou.models.GhiChu;
import com.hou.sotaycanbo.NoteActivity;
import com.hou.sotaycanbo.R;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

public class CalendarFragment extends Fragment {

	CaldroidFragment caldroidFragment;
	private Calendar cal;
	Date currentDate;
	private final SimpleDateFormat formatter = new SimpleDateFormat(
			"dd MM yyyy");
	private ExecuteQuery exeQ;
	List<GhiChu> listGC;
	ListView listEvent;
	boolean isFirstTimeSelect = true;

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		cal = Calendar.getInstance();
		caldroidFragment = new CaldroidFragment();
		exeQ = new ExecuteQuery(getActivity());
		exeQ.createDatabase();
		exeQ.open();
		listGC = exeQ.getAllGhichu();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_main, container, false);
		listEvent = (ListView) view.findViewById(R.id.listEvent);
		// If Activity is created after rotation
		if (savedInstanceState != null) {
			caldroidFragment.restoreStatesFromKey(savedInstanceState,
					"CALDROID_SAVED_STATE");
		}
		// If activity is created from fresh
		else {
			Bundle args = new Bundle();
			// Calendar cal = Calendar.getInstance();
			args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
			args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
			args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
			args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

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
				if (isFirstTimeSelect) {
					currentDate = date;
					Log.d("First", currentDate.getTime() + "");
					formatter.format(currentDate);
				}
				if (!isDateEqual(currentDate, date)) {
					caldroidFragment.setBackgroundResourceForDate(
							R.color.white, currentDate);
					caldroidFragment.setTextColorForDate(R.color.black,
							currentDate);
					caldroidFragment.clearSelectedDates();
					caldroidFragment.clearDisableDates();
					caldroidFragment.refreshView();
					if (isDateEqual(currentDate, new Date())) {
						caldroidFragment.setBackgroundResourceForDate(
								R.drawable.red_border, currentDate);
						if (isEventDate(currentDate)) {
							caldroidFragment
									.setBackgroundResourceForDate(
											R.drawable.red_border_green_bg,
											currentDate);
							caldroidFragment.setTextColorForDate(R.color.black,
									currentDate);
						}
					}
					if (isEventDate(currentDate)) {
						caldroidFragment.setBackgroundResourceForDate(
								R.color.green, currentDate);
						caldroidFragment.setTextColorForDate(R.color.black,
								currentDate);
					}
					caldroidFragment.setTextColorForDate(R.color.black,
							new Date());
				}
				setListEvent(date);

				// Toast.makeText(getActivity().getApplicationContext(),
				// formatter.format(date), Toast.LENGTH_SHORT).show();
				isFirstTimeSelect = false;
				currentDate = date;
				caldroidFragment.setBackgroundResourceForDate(R.color.blue,
						date);
				caldroidFragment.setTextColorForDate(R.color.white, date);
				caldroidFragment.setCalendarDate(date);
			}

			@Override
			public void onChangeMonth(int month, int year) {

			}

			@Override
			public void onLongClickDate(Date date, View view) {

			}

			@Override
			public void onCaldroidViewCreated() {

			}

		};

		// Setup Caldroid
		caldroidFragment.setCaldroidListener(listener);
		setHasOptionsMenu(true);
		return view;
	}

	public boolean isDateEqual(Date date1, Date date2) {
		String d1 = formatter.format(date1);
		String d2 = formatter.format(date2);
		if (d1.equals(d2)) {
			return true;
		}
		return false;
	}

	private void setCustomResourceForDates() {
		if (caldroidFragment != null) {
			for (GhiChu ghiChu : listGC) {
				Date date = new Date(ghiChu.getNgaythuchien());
				if (date.compareTo(new Date()) == 0) {
					caldroidFragment.setBackgroundResourceForDate(
							R.drawable.red_border_green_bg, date);
				} else
					caldroidFragment.setBackgroundResourceForDate(
							R.color.green, date);
				caldroidFragment.setTextColorForDate(R.color.white, date);
			}
		}
	}

	private boolean isEventDate(Date date) {
		boolean isEvent = false;
		for (GhiChu ghiChu : listGC) {
			Date eventDate = new Date(ghiChu.getNgaythuchien());
			if (isDateEqual(date, eventDate)) {
				isEvent = true;
				break;
			}
		}
		return isEvent;
	}

	private List<String> getListEvent(Date date) {
		List<String> getListByDate = new ArrayList<String>();
		for (GhiChu ghiChu : listGC) {
			if (isDateEqual(date, new Date(ghiChu.getNgaythuchien()))) {
				getListByDate.add(formatter.format(new Date(ghiChu
						.getNgaythuchien())) + " - " + ghiChu.getNoidung());
			}
		}
		return getListByDate;
	}

	private void setListEvent(final Date date) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, getListEvent(date));
		listEvent.setAdapter(adapter);
		listEvent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent note = new Intent(getActivity(), NoteActivity.class);
				note.putExtra("TYPE_NOTE", "READ");
				note.putExtra("NOTE", getListEvent(date).get(arg2));
				startActivity(note);
			}
		});
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
			// Calendar cal = Calendar.getInstance();
			// cal.add(Calendar.DATE, 0);
			// Date today = cal.getTime();
			Date today = new Date();
			if (currentDate != null && currentDate != today) {
				caldroidFragment.setBackgroundResourceForDate(R.color.white,
						currentDate);
				caldroidFragment
						.setTextColorForDate(R.color.black, currentDate);
				caldroidFragment.clearSelectedDates();
				caldroidFragment.clearDisableDates();
				caldroidFragment.refreshView();
				caldroidFragment.setBackgroundResourceForDate(
						R.drawable.red_border, new Date());
				caldroidFragment.setTextColorForDate(R.color.black, new Date());
			}
			caldroidFragment.setCalendarDate(today);
			caldroidFragment.setBackgroundResourceForDate(R.color.blue, today);
			caldroidFragment.setTextColorForDate(R.color.white, today);
			currentDate = today;
			break;

		case R.id.action_calendar_add:
			// create a new event
			Intent createNote = new Intent(getActivity()
					.getApplicationContext(), NoteActivity.class);
			createNote.putExtra("TYPE_NOTE", "CREATE_NEW");
			startActivity(createNote);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
