package com.hou.adapters;

import java.util.ArrayList;
import java.util.List;

import com.hou.app.Const;
import com.hou.models.DinhKem;
import com.hou.sotaycanbo.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AttachmentAdapter extends ArrayAdapter<DinhKem> {

	private Context context;
	@SuppressWarnings("unused")
	private int layoutId;
	private ArrayList<DinhKem> listAttachment;

	private SparseBooleanArray mSelectedItemsIds;

	public AttachmentAdapter(Context context, int resource,
			List<DinhKem> listAttachment) {
		super(context, resource, listAttachment);
		this.context = context;
		this.layoutId = resource;
		this.listAttachment = (ArrayList<DinhKem>) listAttachment;
		mSelectedItemsIds = new SparseBooleanArray();
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		LayoutInflater inf = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		final View rowView = inf.inflate(R.layout.itemlist_attachment, parent,
				false);

		TextView tvFilename = (TextView) rowView.findViewById(R.id.tv_filename);
		ImageView imgFileStyle = (ImageView) rowView
				.findViewById(R.id.img_icon_filestyle);
		ImageView imgDeleteFile = (ImageView) rowView
				.findViewById(R.id.img_delete_file);

		tvFilename.setText(listAttachment.get(position).getFilename());
		String filestyle = listAttachment.get(position).getLoaifile()
				.toString();
		if (filestyle.equals(Const.ATTACHMENT_FILE)) {
			imgFileStyle.setImageResource(R.drawable.file_attachment);
		} else if (filestyle.equals(Const.ATTACHMENT_IMAGE)) {
			imgFileStyle.setImageResource(R.drawable.image_attachment);
		} else if (filestyle.equals(Const.ATTACHMENT_VOICE)) {
			imgFileStyle.setImageResource(R.drawable.audio_attachment);
		}

		imgDeleteFile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Animation fadeOut = new AlphaAnimation(1, 0);
				fadeOut.setInterpolator(new AccelerateInterpolator());
				fadeOut.setDuration(500);

				fadeOut.setAnimationListener(new Animation.AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						// adapter.pointItems.remove(position);
						// adapter.notifyDataSetChanged();
						remove(listAttachment.get(position));
					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}
				});

				rowView.startAnimation(fadeOut);
				// remove(listAttachment.get(position));
			}
		});

		return rowView;
	}

	@Override
	public void remove(DinhKem object) {
		listAttachment.remove(object);
		notifyDataSetChanged();
	}

	public ArrayList<DinhKem> getDinhkem() {
		return listAttachment;
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
