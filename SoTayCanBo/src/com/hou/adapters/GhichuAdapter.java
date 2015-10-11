package com.hou.adapters;

import java.util.ArrayList;
import java.util.List;

import com.hou.app.Global;
import com.hou.models.GhiChu;
import com.hou.sotaycanbo.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GhichuAdapter extends ArrayAdapter<GhiChu> {

	private Context context;
	@SuppressWarnings("unused")
	private int layoutId;
	private ArrayList<GhiChu> listGhichu;
	
	private SparseBooleanArray mSelectedItemsIds;
	
	public GhichuAdapter(Context context, int resource, List<GhiChu> listGhichu) {
		super(context, resource, listGhichu);
		this.context = context;
		this.layoutId = resource;
		this.listGhichu = (ArrayList<GhiChu>)listGhichu;
		mSelectedItemsIds = new SparseBooleanArray();
	}
	
	@SuppressLint("ViewHolder") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
		
		View rowView = inf.inflate(R.layout.itemlist_note, parent, false);
		
		TextView tvTenghichu = (TextView) rowView.findViewById(R.id.tvTenGhichu);
		TextView tvThoigiansua = (TextView) rowView.findViewById(R.id.tvThoigiansua);
		TextView tvNoidung = (TextView) rowView.findViewById(R.id.tvNoidung);
		ImageView imgBookmark = (ImageView) rowView.findViewById(R.id.imgBookmark);
		
		
		String Tenghichu = listGhichu.get(position).getTenGhiChu();
		String Noidung = listGhichu.get(position).getNoidung();
		
		long timeUpd = listGhichu.get(position).getNgaysua();
		String Thoigiansua = Global.getFormattedDateTime(timeUpd);
		
		tvTenghichu.setText(Tenghichu);
		tvNoidung.setText(Noidung);
		tvThoigiansua.setText(Thoigiansua);
		
		if (listGhichu.get(position).getBookmark() == 1) {
			imgBookmark.setVisibility(View.VISIBLE);
		} else {
			imgBookmark.setVisibility(View.GONE);
		}
		
		return rowView;
	}
	
	@Override
	public void remove(GhiChu object) {
		listGhichu.remove(object);
		notifyDataSetChanged();
	}
	
	public ArrayList<GhiChu> getGhiChu() {
		return listGhichu;
	}
	
	public void toggleSelection(int position) {
		selectView(position, !mSelectedItemsIds.get(position));
	}
 
	public void removeSelection() {
		mSelectedItemsIds = new SparseBooleanArray();
		notifyDataSetChanged();
	}
	
	public void selectView(int position, boolean value) {
		if (value)
			mSelectedItemsIds.put(position, value);
		else
			mSelectedItemsIds.delete(position);
		notifyDataSetChanged();
	}
	
	public int getSelectedCount() {
		return mSelectedItemsIds.size();
	}

	public SparseBooleanArray getSelectedIds() {
		return mSelectedItemsIds;
	}
}
