package com.hou.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
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
import com.hou.models.Event;
import com.hou.sotaycanbo.CreateEventActivity;
import com.hou.sotaycanbo.R;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

public class CalendarFragment extends Fragment {

	private CaldroidFragment caldroidFragment;
	private Calendar cal;
	private Date currentDate;
	private final SimpleDateFormat formatter = new SimpleDateFormat(
			"dd/MM/yyyy");
	private ExecuteQuery exeQ;
	private List<Event> listEvent;
	private ListView lvEvent;
	boolean isFirstTimeSelect = true;

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		cal = Calendar.getInstance();
		currentDate = new Date();
		caldroidFragment = new CaldroidFragment();

		exeQ = new ExecuteQuery(getActivity());
		exeQ.createDatabase();
		exeQ.open();
		listEvent = exeQ.getAllEvent();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_main, container, false);
		lvEvent = (ListView) view.findViewById(R.id.lvEvent);
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

		try {
			setCustomResourceForDates();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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
						try {
							if (isEventDate(currentDate)) {
								caldroidFragment
										.setBackgroundResourceForDate(
												R.drawable.red_border_green_bg,
												currentDate);
								caldroidFragment.setTextColorForDate(R.color.black,
										currentDate);
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					try {
						if (isEventDate(currentDate)) {
							caldroidFragment.setBackgroundResourceForDate(
									R.color.green, currentDate);
							caldroidFragment.setTextColorForDate(R.color.black,
									currentDate);
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					caldroidFragment.setTextColorForDate(R.color.black,
							new Date());
				}
				try {
					setListEvent(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

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

	private void setCustomResourceForDates() throws ParseException {
		if (caldroidFragment != null) {
			for (Event event : listEvent) {
				Date date = formatter.parse(event.getNgay_event());
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

	private boolean isEventDate(Date date) throws ParseException {
		boolean isEvent = false;
		for (Event e : listEvent) {
			Date eventDate = formatter.parse(e.getNgay_event());
			if (isDateEqual(date, eventDate)) {
				isEvent = true;
				break;
			}
		}
		return isEvent;
	}

	private List<String> getListEvent(Date date) throws ParseException {
		List<String> getListByDate = new ArrayList<String>();
		for (Event e : listEvent) {
			if (isDateEqual(date, formatter.parse(e.getNgay_event()))) {
				getListByDate.add("  " + e.getThoigianbatdau() + "      " + e.getTenEvent());
			}
		}
		return getListByDate;
	}

	private void setListEvent(final Date date) throws ParseException {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, getListEvent(date));
		lvEvent.setAdapter(adapter);
		lvEvent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
			}
		});
		lvEvent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				final int pos = position;
				AlertDialog.Builder buider = new Builder(getActivity());
				buider.setMessage(getActivity()
						.getString(R.string.calendar_delete_confirm));
				buider.setNegativeButton(getString(R.string.forgotpass_cancel), new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				buider.setNeutralButton(getString(R.string.forgotpass_ok), new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						exeQ.delete_tblEvent_byMaEvent(listEvent.get(pos).getMaEvent());
						try {
							setListEvent(formatter.parse(listEvent.get(pos).getNgay_event()));
							setCustomResourceForDates();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				buider.create().show();
				
				return false;
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
			try {
				setListEvent(currentDate);
				setCustomResourceForDates();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case R.id.action_calendar_add:
			// create a new event
			Intent createEvent = new Intent(getActivity()
					.getApplicationContext(), CreateEventActivity.class);
			createEvent.putExtra("currentDay", formatter.format(currentDate));
			startActivity(createEvent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onResume() {
		super.onResume();
		try {
			setCustomResourceForDates();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
