package com.hou.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.hou.adapters.SotayAdapter;
import com.hou.app.Const;
import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.hou.models.SoTay;
import com.hou.sotaycanbo.ListNoteActivity;
import com.hou.sotaycanbo.R;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

public class SotayFragment extends Fragment {

	private ListView lvSotay;
	private ArrayList<SoTay> listSotay = null;
	private SotayAdapter adapter = null;

	private static final int SORT_TITLE = 1;
	private static final int SORT_NONOTE = 2;
	private static final int SORT_TIME = 3;

	private ExecuteQuery exeQ;
	private FrameLayout flNoSotay;

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);

		exeQ = new ExecuteQuery(getActivity().getApplicationContext());
		exeQ.createDatabase();
		exeQ.open();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_sotay, container, false);

		lvSotay = (ListView) view.findViewById(R.id.lvSotay);

		listSotay = new ArrayList<SoTay>();
		listSotay = exeQ.getAllSotay();

		adapter = new SotayAdapter(getActivity().getApplicationContext(),
				R.layout.itemlist_sotay, listSotay);
		lvSotay.setAdapter(adapter);

		lvSotay.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent dsSotay = new Intent(getActivity(),
						ListNoteActivity.class);
				dsSotay.putExtra(Const.KEY_SOTAY, listSotay.get(position));
				startActivity(dsSotay);
			}
		});
		flNoSotay = (FrameLayout) view.findViewById(R.id.flNoSotay);
		checkViewNoSotay();

		return view;
	}

	private void checkViewNoSotay() {
		if (listSotay.size() > 0) {
			flNoSotay.setVisibility(View.GONE);
		} else {
			flNoSotay.setVisibility(View.VISIBLE);
		}
	}
	
	public void showDialogNewSotay() {
		final Dialog dialogAddnew = new Dialog(getActivity());
		dialogAddnew.setTitle(getResources().getString(
				R.string.dialog_sotay_addnew_title));
		dialogAddnew.setContentView(R.layout.dialog_sotay_addnew);

		final EditText edtTenSotay;
		edtTenSotay = (EditText) dialogAddnew.findViewById(R.id.edtTenSotay);

		Button btnCancel, btnOK;
		btnCancel = (Button) dialogAddnew.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialogAddnew.dismiss();
			}
		});

		btnOK = (Button) dialogAddnew.findViewById(R.id.btnOK);
		btnOK.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String ten = edtTenSotay.getText().toString().trim();
				if (ten.length() > 0) {
					for (SoTay nb : listSotay) {
						if (nb.getTenSoTay().equalsIgnoreCase(ten)) {
							Toast.makeText(
									getActivity(),
									getResources().getString(
											R.string.dialog_sotay_addnew_notif),
									Toast.LENGTH_SHORT).show();
							dialogAddnew.dismiss();
							return;
						}
					}
					String pk = Global.getPreference(getActivity(),
							Const.USER_MACANBO)
							+ "_"
							+ Global.getCurrentDateTime();
					SoTay st = new SoTay(pk, ten, Global.getCurrentDateTime(),
							0, Global.getPreference(getActivity(),
									Const.USER_EMAIL));
					listSotay.add(st);
					exeQ.insert_tblSotay_single(st);
					checkViewNoSotay();
					adapter.notifyDataSetChanged();
					dialogAddnew.dismiss();
				}
			}
		});

		dialogAddnew.show();
	}

	public void showDialogSortSotay() {
		final Dialog dialogSort = new Dialog(getActivity());
		dialogSort.setTitle(getResources().getString(
				R.string.dialog_sotay_sort_titledialog));
		dialogSort.setContentView(R.layout.dialog_sotay_sort);

		ListView lvSortSotay = (ListView) dialogSort
				.findViewById(R.id.lvSortSotay);

		ArrayList<String> listSortSotay = new ArrayList<String>();
		listSortSotay.add(getResources().getString(
				R.string.dialog_sotay_sort_title));
		listSortSotay.add(getResources().getString(
				R.string.dialog_sotay_sort_nonote));
		listSortSotay.add(getResources().getString(
				R.string.dialog_sotay_sort_time));

		ArrayAdapter<String> adapterSortSotay = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_1,
				listSortSotay);

		lvSortSotay.setAdapter(adapterSortSotay);

		lvSortSotay.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				sortListSotay(position + 1);
				adapter.notifyDataSetChanged();
				dialogSort.dismiss();
			}
		});

		dialogSort.show();
	}

	public void sortListSotay(int type) {
		switch (type) {
		case SORT_TITLE:
			Collections.sort(listSotay, new Comparator<SoTay>() {
				@Override
				public int compare(SoTay st1, SoTay st2) {

					return (st1.getTenSoTay().toLowerCase()).compareTo(st2
							.getTenSoTay().toLowerCase());
				}
			});
			break;

		case SORT_NONOTE:
			Collections.sort(listSotay, new Comparator<SoTay>() {
				@Override
				public int compare(SoTay st1, SoTay st2) {

					return (st2.getSoGhiChu() + "").compareTo(st1.getSoGhiChu()
							+ "");
				}
			});
			break;

		case SORT_TIME:
			Collections.sort(listSotay, new Comparator<SoTay>() {
				@Override
				public int compare(SoTay st1, SoTay st2) {

					return (st2.getNgayTao() + "").compareTo(st1.getNgayTao()
							+ "");
				}
			});
			break;

		default:
			break;
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.fragment_sotay, menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch (id) {
		case R.id.action_sotay_addnew:
			showDialogNewSotay();
			break;
		case R.id.action_sotay_sort:
			showDialogSortSotay();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
