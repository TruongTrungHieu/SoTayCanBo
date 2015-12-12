package com.hou.adapters;

import java.util.ArrayList;
import java.util.List;

import com.hou.models.SoTay;
import com.hou.sotaycanbo.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SotayAdapter extends ArrayAdapter<SoTay> {
	
	private Context context;
	@SuppressWarnings("unused")
	private int layoutId;
	private ArrayList<SoTay> listSotay;
	
	public SotayAdapter(Context context, int resource, List<SoTay> listSotay) {
		super(context, resource, listSotay);
		this.context = context;
		this.layoutId = resource;
		this.listSotay = (ArrayList<SoTay>)listSotay;
	}
	
	@SuppressLint("ViewHolder") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		
		View rowView = inf.inflate(R.layout.itemlist_sotay, parent, false);
		
		TextView tvTenSotay = (TextView) rowView.findViewById(R.id.tvTenSotay);
		TextView tvSoghichu = (TextView) rowView.findViewById(R.id.tvSoghichu);
		ImageView ivMore = (ImageView) rowView.findViewById(R.id.ivMore);
		
		
		String tenSotay = listSotay.get(position).getTenSoTay();
		String soGhichu = listSotay.get(position).getSoGhiChu() + " Ghi chú";
		
		tvTenSotay.setText(tenSotay);
		tvSoghichu.setText(soGhichu);
		
		ivMore.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialogMoreNote();
			}
		});
		
		return rowView;
	}
	
	public void showDialogMoreNote() {
		
	}
}
