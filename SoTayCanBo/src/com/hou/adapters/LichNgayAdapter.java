package com.hou.adapters;

import java.util.ArrayList;

import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.hou.models.DayNumberOfContents;
import com.hou.models.Event;
import com.hou.models.SuKien;
import com.hou.sotaycanbo.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LichNgayAdapter extends BaseExpandableListAdapter {

	private Activity activity;
	private ArrayList<Object> childtems;
	private LayoutInflater inflater;
	private ArrayList<DayNumberOfContents> parentItems;
	private ArrayList<SuKien> child;
	private ExecuteQuery exeQ;

	public LichNgayAdapter(ArrayList<DayNumberOfContents> parents,
			ArrayList<Object> childern) {
		this.parentItems = parents;
		this.childtems = childern;
	}

	public void setInflater(LayoutInflater inflater, Activity activity) {
		this.inflater = inflater;
		this.activity = activity;
		exeQ = new ExecuteQuery(activity);
		exeQ.createDatabase();
		exeQ.open();
	}

	@SuppressWarnings("unchecked")
	@SuppressLint("InflateParams")
	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		child = (ArrayList<SuKien>) childtems.get(groupPosition);

		TextView tvName, tvTime;
		ImageView ivBuoi = null;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_lich_tuan_item, null);
		}

		tvName = (TextView) convertView.findViewById(R.id.sukienTitle);
		tvName.setText(child.get(childPosition).getTenSukien());
		tvTime = (TextView) convertView.findViewById(R.id.sukienTime);
		tvTime.setText(child.get(childPosition).getThoigianbatdau());
		ivBuoi = (ImageView) convertView.findViewById(R.id.ivBuoi);

		String buoi = child.get(childPosition).getBuoi().toString().trim();
		if (buoi.equalsIgnoreCase(this.activity.getResources().getString(
				R.string.lichtuan_sang))) {
			ivBuoi.setBackgroundResource(R.drawable.ic_morning);
		} else {
			ivBuoi.setBackgroundResource(R.drawable.ic_afternoon);
		}

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				sukienDetailDialog(activity, child.get(childPosition));
			}
		});

		return convertView;
	}

	@SuppressLint("InflateParams")
	public void sukienDetailDialog(final Context c, SuKien sk) {
		LayoutInflater inflater = this.activity.getLayoutInflater();
		View alertLayout = inflater.inflate(R.layout.dialog_chitiet_sukien,
				null);

		AlertDialog.Builder alert = new AlertDialog.Builder(this.activity);
		alert.setView(alertLayout);
		final AlertDialog dialog = alert.create();

		TextView tvTensukien, tvThoigian, tvDiadiem, tvThanhphan, tvThanhphan_khac, tvNoidung;
		tvTensukien = (TextView) alertLayout.findViewById(R.id.tvTensukien);
		tvThoigian = (TextView) alertLayout.findViewById(R.id.tvThoigian);
		tvDiadiem = (TextView) alertLayout.findViewById(R.id.tvDiadiem);
		tvThanhphan = (TextView) alertLayout.findViewById(R.id.tvThanhphan);
		tvThanhphan_khac = (TextView) alertLayout
				.findViewById(R.id.tvThanhphan_khac);
		tvNoidung = (TextView) alertLayout.findViewById(R.id.tvNoidung);

		tvTensukien.setText(sk.getTenSukien());
		tvThoigian.setText(sk.getThoigianbatdau() + " - " + sk.getNgay());
		if (!sk.getPhong().trim().equalsIgnoreCase("")) {
			tvDiadiem.setText(sk.getPhong() + "\n" + sk.getDiadiem());
		} else {
			tvDiadiem.setText(sk.getDiadiem());
		}
		String[] tpThamgia = sk.getTp_thamgia().split("##");
		tvThanhphan.setText(tpThamgia[0]);
		tvThanhphan_khac.setText(tpThamgia[1]);
		tvNoidung.setText(sk.getNoidung());

		final ImageView btnAddEvent = (ImageView) alertLayout
				.findViewById(R.id.btnAddEvent);
		final SuKien sukienTuan = sk;
		btnAddEvent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Event e = new Event();
				e.setMaEvent(Global.getMaCanBo(activity) + "_E_"
						+ Global.getCurrentDateTime());
				e.setTenEvent(sukienTuan.getTenSukien());
				e.setDiadiem(sukienTuan.getDiadiem() + " - "
						+ sukienTuan.getPhong());
				e.setThoigianbatdau(sukienTuan.getThoigianbatdau());
				e.setMaLaplai("");
				e.setMaLoinhac("0");
				e.setNgay_event(sukienTuan.getNgay());
				e.setMaCanbo(Global.getMaCanBo(activity));
				e.setMota(sukienTuan.getNoidung());
				if (exeQ.insert_tblEvent_single(e)) {
					Toast.makeText(
							activity,
							activity.getString(R.string.dialog_lichtuan_insert_success),
							Toast.LENGTH_LONG).show();
				}
			}
		});

		Button btnOK = (Button) alertLayout.findViewById(R.id.btnDismiss);
		btnOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	@SuppressLint("InflateParams")
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.day_group_layout, null);
		}

		TextView tvName = (TextView) convertView.findViewById(R.id.dayName);
		tvName.setText("" + parentItems.get(groupPosition).getDayname());

		TextView tvNumber = (TextView) convertView
				.findViewById(R.id.numberOfContents);
		tvNumber.setText("(" + parentItems.get(groupPosition).getNumber() + ")");

		return convertView;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getChildrenCount(int groupPosition) {
		return ((ArrayList<String>) childtems.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return parentItems.size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}
