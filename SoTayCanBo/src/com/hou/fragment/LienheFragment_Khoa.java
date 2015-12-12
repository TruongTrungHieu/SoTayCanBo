package com.hou.fragment;

import java.util.ArrayList;

import com.hou.adapters.DonviAdapter;
import com.hou.app.Global;
import com.hou.models.DonVi;
import com.hou.sotaycanbo.DanhSachCanBoActivity;
import com.hou.sotaycanbo.R;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LienheFragment_Khoa extends Fragment {

	private ListView lvDonviKhoa;
	private DonviAdapter adapter;
	private ArrayList<DonVi> list;

	public LienheFragment_Khoa(ArrayList<DonVi> list) {
		super();
		this.list = list;
	}

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_lienhe_khoa, container,
				false);

		lvDonviKhoa = (ListView) view.findViewById(R.id.lvDonvi_Khoa);
		adapter = new DonviAdapter(getActivity(), R.layout.itemlist_donvi, list);
		lvDonviKhoa.setAdapter(adapter);
		lvDonviKhoa
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						new ActionDialog(getActivity(),
								R.layout.dialog_action_donvi, list
										.get(position)).show();
					}
				});
		lvDonviKhoa
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						return false;
					}
				});

		return view;
	}

	public void onResume() {
		super.onResume();
	}

	public class ActionDialog extends Dialog implements View.OnClickListener {
		private final TextView tvDonviDetail;
		private final TextView tvDonviCall;
		private final TextView tvDonviEmail;
		Context mContext;
		private DonVi mDonvi;

		public ActionDialog(Context context, int resource, DonVi dv) {
			super(context);
			this.mContext = context;
			this.mDonvi = dv;
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setCancelable(true);
			setContentView(resource);
			tvDonviDetail = (TextView) findViewById(R.id.tvDonviDetails);
			tvDonviCall = (TextView) findViewById(R.id.tvDonviCall);
			tvDonviEmail = (TextView) findViewById(R.id.tvDonviEmail);
			tvDonviDetail.setOnClickListener(this);
			tvDonviCall.setOnClickListener(this);
			tvDonviEmail.setOnClickListener(this);

			WindowManager.LayoutParams wmlp = getWindow().getAttributes();
			wmlp.gravity = Gravity.CENTER;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tvDonviDetails:
				Intent i = new Intent(getActivity(),
						DanhSachCanBoActivity.class);
				i.putExtra("donvi", mDonvi);
				startActivity(i);
				getActivity().overridePendingTransition(R.anim.slide_in_right,
						R.anim.fade_out);
				this.dismiss();
				break;
			case R.id.tvDonviCall:
				String sdt = mDonvi.getSodienthoai();
				if (sdt.length() > 0) {
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:" + sdt));
					startActivity(callIntent);
				}
				this.dismiss();
				break;
			case R.id.tvDonviEmail:
				String email = mDonvi.getEmail();
				if (email.length() > 0) {
					if (Global.hasNetworkConnection(getActivity()
							.getApplicationContext())) {
						Intent intent = new Intent(Intent.ACTION_SEND);
						intent.setType("text/html");
						intent.putExtra(Intent.EXTRA_EMAIL, email);
						startActivity(Intent.createChooser(intent,
								getString(R.string.title_share)));
					} else {
						Toast.makeText(getActivity().getBaseContext(),
								getString(R.string.no_internet),
								Toast.LENGTH_SHORT).show();
					}
				}
				this.dismiss();
				break;
			default:
			}
		}
	}
}
