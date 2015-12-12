package com.hou.adapters;

import java.util.ArrayList;
import java.util.List;

import com.hou.models.DonVi;
import com.hou.sotaycanbo.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DonviAdapter extends ArrayAdapter<DonVi> {

	private Context mContext;
	private ArrayList<DonVi> listDonvi;
	@SuppressWarnings("unused")
	private int layoutId;

	public DonviAdapter(Context context, int resource, List<DonVi> listDV) {
		super(context, resource, listDV);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.layoutId = resource;
		this.listDonvi = (ArrayList<DonVi>) listDV;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listDonvi.size();
	}

	@Override
	public DonVi getItem(int position) {
		// TODO Auto-generated method stub
		return listDonvi.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint({ "InflateParams", "ViewHolder" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		LayoutInflater inf = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View v = inf.inflate(R.layout.itemlist_donvi, parent, false);

		TextView tvTenDonvi = (TextView) v.findViewById(R.id.tvTenDonvi);
		TextView tvSodienthoai = (TextView) v.findViewById(R.id.tvSodienthoai);
		TextView tvEmail = (TextView) v.findViewById(R.id.tvEmail);
		TextView tvDiachi = (TextView) v.findViewById(R.id.tvDiachi);

		DonVi dv = listDonvi.get(position);
		tvTenDonvi.setText(dv.getTenDonvi().toString());
		tvSodienthoai.setText(dv.getSodienthoai().toString());
		if (dv.getEmail().trim().toString().length() > 0) {
			tvEmail.setText(dv.getEmail().toString());
		} else {
			tvEmail.setText("");
		}
		tvDiachi.setText(dv.getDiachi().toString());

		return v;
	}
}
