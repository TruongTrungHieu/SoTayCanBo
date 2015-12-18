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
import android.content.ContextWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("InflateParams")
public class LichNgayAdapter extends BaseExpandableListAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	private ArrayList<DayNumberOfContents> listParent;
	private ExecuteQuery exeQ;

	public LichNgayAdapter(Context context,
			ArrayList<DayNumberOfContents> listParent) {
		this.mContext = context;
		this.listParent = listParent;
	}

	public void setInflater(LayoutInflater inflater, Context context) {
		this.inflater = inflater;
		this.mContext = context;
		exeQ = new ExecuteQuery(context);
		exeQ.createDatabase();
		exeQ.open();
	}

	/*
	 *	Child
	 */

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		ArrayList<SuKien> listChild = listParent.get(groupPosition)
				.getListChild();
		return listChild.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		ArrayList<SuKien> listChild = listParent.get(groupPosition)
				.getListChild();
		return listChild.size();
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final SuKien sk = (SuKien) getChild(groupPosition, childPosition);

		TextView tvName, tvTime;
		ImageView ivBuoi = null;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_lich_tuan_item, null);
		}

		tvName = (TextView) convertView.findViewById(R.id.sukienTitle);
		tvName.setText(sk.getTenSukien().trim());
		tvTime = (TextView) convertView.findViewById(R.id.sukienTime);
		tvTime.setText(sk.getThoigianbatdau().trim());
		ivBuoi = (ImageView) convertView.findViewById(R.id.ivBuoi);

		String thoigian = sk.getThoigianbatdau().toString().trim();
		if (thoigian.compareToIgnoreCase("12:00") < 0) {
			ivBuoi.setBackgroundResource(R.drawable.ic_morning);
		} else {
			ivBuoi.setBackgroundResource(R.drawable.ic_afternoon);
		}

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				sukienDetailDialog(mContext, sk);
			}
		});

		return convertView;
	}

	@SuppressLint("InflateParams")
	public void sukienDetailDialog(final Context c, SuKien skd) {
		View alertLayout = inflater.inflate(R.layout.dialog_chitiet_sukien,
				null);

		AlertDialog.Builder alert = new AlertDialog.Builder(c);
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

		// ten su kien
		tvTensukien.setText(skd.getTenSukien());
		// thoi gian
		tvThoigian.setText(skd.getThoigianbatdau() + " - " + skd.getNgay());
		// dia diem
		String phong = skd.getPhong().trim();
		String diadiem = skd.getDiadiem().trim();
		if (phong.equals(mContext.getString(R.string.compare_khong))) {
			phong = "";
		}
		if (!phong.equalsIgnoreCase("")) {
			tvDiadiem.setText(phong + "\n" + diadiem);
		} else {
			tvDiadiem.setText(diadiem);
		}
		// thanh phan tham gia
		String[] tpThamgia = skd.getTp_thamgia().split("##");
		String chinh = "";
		String phu = "";
		if (tpThamgia.length == 1) {
			chinh = tpThamgia[0];
		}
		if (tpThamgia.length == 2) {
			phu = tpThamgia[1];
		}
		if (chinh.equals(mContext.getString(R.string.compare_khong))
				|| chinh.length() == 0) {
			if (phu.equals(mContext.getString(R.string.compare_khong))
					|| phu.length() == 0) {
				tvThanhphan.setText("");
			} else {
				tvThanhphan.setText(phu);
			}
			tvThanhphan_khac.setText("");
		} else {
			tvThanhphan.setText(chinh);
			if (phu.equals(mContext.getString(R.string.compare_khong))
					|| phu.length() == 0) {
				tvThanhphan_khac.setText("");
			} else {
				tvThanhphan_khac.setText(phu);
			}
		}

		// noi dung
		tvNoidung.setText(skd.getNoidung());

		final ImageView btnAddEvent = (ImageView) alertLayout
				.findViewById(R.id.btnAddEvent);
		final SuKien sukienTuan = skd;
		btnAddEvent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Event e = new Event();
				e.setMaEvent(Global.getMaCanBo((Activity) mContext) + "_E_"
						+ Global.getCurrentDateTime());
				e.setTenEvent(sukienTuan.getTenSukien());
				e.setDiadiem(sukienTuan.getDiadiem() + " - "
						+ sukienTuan.getPhong());
				e.setThoigianbatdau(sukienTuan.getThoigianbatdau());
				e.setMaLaplai("");
				e.setMaLoinhac("0");
				e.setNgay_event(sukienTuan.getNgay());
				e.setMaCanbo(Global.getMaCanBo((Activity) mContext));
				e.setMota(sukienTuan.getNoidung());
				e.setMaSukientuan(sukienTuan.getMaSukien());

				if (exeQ.insert_tblEvent_from_lichtuan(e)) {
					Toast.makeText(
							((ContextWrapper) mContext).getBaseContext(),
							mContext.getString(R.string.dialog_lichtuan_insert_success),
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(
							((ContextWrapper) mContext).getBaseContext(),
							mContext.getString(R.string.dialog_lichtuan_insert_false),
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

	/*
	 * Parent
	 */

	@Override
	public Object getGroup(int groupPosition) {
		return listParent.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return listParent.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
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
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		DayNumberOfContents parentItem = (DayNumberOfContents) getGroup(groupPosition);

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.day_group_layout, null);
		}

		TextView tvName = (TextView) convertView.findViewById(R.id.dayName);
		tvName.setText(parentItem.getDayname());

		TextView tvNumber = (TextView) convertView
				.findViewById(R.id.numberOfContents);
		tvNumber.setText("(" + parentItem.getNumber() + ")");

		return convertView;
	}

}
