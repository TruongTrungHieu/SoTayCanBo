package com.hou.adapters;

import java.util.ArrayList;

import com.hou.models.DayNumberOfContents;
import com.hou.models.SuKien;
import com.hou.sotaycanbo.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
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

	public LichNgayAdapter(ArrayList<DayNumberOfContents> parents, ArrayList<Object> childern) {
		this.parentItems = parents;
		this.childtems = childern;
	}

	public void setInflater(LayoutInflater inflater, Activity activity) {
		this.inflater = inflater;
		this.activity = activity;
	}

	@SuppressWarnings("unchecked")
	@SuppressLint("InflateParams") @Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {

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
		
		if(child.get(childPosition).getBuoi().toString().equalsIgnoreCase("Sáng")){
			ivBuoi.setBackgroundResource(R.drawable.ic_morning);
		}
		else{
			ivBuoi.setBackgroundResource(R.drawable.ic_afternoon);
		}
		
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				/*
				 * Toast.makeText(activity,
				 * child.get(childPosition).getMaSukien(),
				 * Toast.LENGTH_SHORT).show();
				 */
				sukienDetailDialog(activity, child.get(childPosition));
			}
		});

		return convertView;
	}

	public void sukienDetailDialog(final Context c, SuKien sk) {
		final Dialog dialog = new Dialog(c);
		dialog.setContentView(R.layout.dialog_chitiet_sukien);
		dialog.setTitle(R.string.dialogSukien);
		dialog.setCancelable(true);
		TextView tvName, tvDate, tvRoom, tvTimeBegin, tvPlace, tvNote, tvContens;
		tvName = (TextView) dialog.findViewById(R.id.sukienName);
		tvDate = (TextView) dialog.findViewById(R.id.sukienDate);
		tvRoom = (TextView) dialog.findViewById(R.id.sukienRoom);
		tvTimeBegin = (TextView) dialog.findViewById(R.id.sukienTimeBegin);
		tvPlace = (TextView) dialog.findViewById(R.id.sukienPlace);
		tvNote = (TextView) dialog.findViewById(R.id.sukienNote);
		tvContens = (TextView) dialog.findViewById(R.id.sukienContents);

		tvName.setText(sk.getTenSukien());
		tvDate.setText("" + sk.getNgay());
		tvRoom.setText(sk.getPhong());
		tvTimeBegin.setText(sk.getThoigianbatdau());
		tvPlace.setText(sk.getDiadiem());
		tvNote.setText(sk.getGhichu());
		tvContens.setText(sk.getNoidung());

		final ImageView btnAddEvent = (ImageView) dialog.findViewById(R.id.btnAddEvent);
		btnAddEvent.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});

		Button btnOK = (Button) dialog.findViewById(R.id.btnDismiss);
		btnOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	@SuppressLint("InflateParams") @Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.day_group_layout, null);
		}

		TextView tvName = (TextView) convertView.findViewById(R.id.dayName);
		tvName.setText("" + parentItems.get(groupPosition).getDayname());

		TextView tvNumber = (TextView) convertView.findViewById(R.id.numberOfContents);
		tvNumber.setText("(" + parentItems.get(groupPosition).getNumber() +")");

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
