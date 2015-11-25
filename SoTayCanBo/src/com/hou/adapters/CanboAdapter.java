package com.hou.adapters;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.hou.app.Global;
import com.hou.models.CanBo;
import com.hou.sotaycanbo.R;
import com.hou.ultis.CircularImageView;
import com.hou.ultis.ImageUltiFunctions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CanboAdapter extends ArrayAdapter<CanBo> {

	private Context mContext;
	private ArrayList<CanBo> listCanbo;
	@SuppressWarnings("unused")
	private int layoutId;

	public CanboAdapter(Context context, int resource, List<CanBo> listCB) {
		super(context, resource, listCB);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.layoutId = resource;
		this.listCanbo = (ArrayList<CanBo>) listCB;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listCanbo.size();
	}

	@Override
	public CanBo getItem(int position) {
		// TODO Auto-generated method stub
		return listCanbo.get(position);
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

		View v = inf.inflate(R.layout.itemlist_canbo, parent, false);

		CircularImageView ava = (CircularImageView) v
				.findViewById(R.id.imgAvatar);
		TextView tvTenCanbo = (TextView) v.findViewById(R.id.tvTencanbo);
		TextView tvEmail = (TextView) v.findViewById(R.id.tvEmail);
		TextView tvSodienthoai = (TextView) v.findViewById(R.id.tvSodienthoai);

		CanBo cb = listCanbo.get(position);
		String tenCanbo = cb.getTenCanbo().toString(); 
		if (tenCanbo != null && !tenCanbo.equals("null")) {
			tvTenCanbo.setText(tenCanbo);
		} else {
			tvTenCanbo.setText("");
		}
		String email = cb.getEmail().toString(); 
		if (email != null && !email.equals("null")) {
			tvEmail.setText(email);
		} else {
			tvEmail.setText("");
		}
		String sdt = cb.getSdt().toString();
		if (sdt != null && !sdt.equals("null")) {
			tvSodienthoai.setText(sdt);
		} else {
			tvSodienthoai.setText("");
		}

		File f = ImageUltiFunctions
				.getFileFromUri(Global.getURI(cb.getAvatar()));
		if (f != null) {
			Bitmap b = ImageUltiFunctions.decodeSampledBitmapFromFile(f, 500,
					500);
			ava.setImageBitmap(b);
		} else {
			ava.setImageResource(R.drawable.test1);
		}
		return v;
	}
}
