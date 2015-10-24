package com.hou.sotaycanbo;

import com.hou.app.Const;
import com.hou.app.Global;
import com.hou.database_handler.ExecuteQuery;
import com.hou.fragment.CalendarFragment;
import com.hou.fragment.GhichuFragment;
import com.hou.fragment.LienHeFragment;
import com.hou.fragment.LienheFragment;
import com.hou.fragment.SotayFragment;
import com.hou.models.CanBo;
import com.hou.fragment.ThongTinCanBoFragment;

import android.os.Bundle;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;

public class FragmentManagerActivity extends MaterialNavigationDrawer<Object>
		implements MaterialAccountListener {

	private MaterialAccount account;

	private MaterialSection<?> SoTay;
	private MaterialSection<?> GhiChu;
	private MaterialSection<?> Lich;
	private MaterialSection<?> LichTuan;
	private MaterialSection<?> LienHe;
	private MaterialSection<?> DongBo;
	private MaterialSection<?> CaiDat;
	private MaterialSection<?> ThongTin;
	private MaterialSection<?> ThongTinCanBo;

	public static FragmentManagerActivity sInstance;

	private ExecuteQuery exeQ;

	@Override
	public void init(Bundle savedInstanceState) {
		sInstance = this;
		this.disableLearningPattern();

		exeQ = new ExecuteQuery(getApplicationContext());
		exeQ.createDatabase();
		exeQ.open();

		saveInfoCanbo();
		String tenDonvi = exeQ.getTendonviByMadonvi(Global.getPreference(
				getApplicationContext(), Const.USER_MADONVI));
		String tenCanbo = Global.getPreference(
				getApplicationContext(), Const.USER_HOTEN);
		String anh = Global.getPreference(getApplicationContext(), Const.USER_ANH);
		
		account = new MaterialAccount(this.getResources(), tenCanbo,
				tenDonvi, R.drawable.account_ava,
				R.drawable.bg);
		this.addAccount(account);

		this.setAccountListener(this);
		this.setDrawerBackgroundColor(this.getResources().getColor(
				R.color.white));

		GhiChu = newSection(getResources().getString(R.string.manager_ghichu),
				R.drawable.menu_ghichu_material, new GhichuFragment());

		SoTay = newSection(getResources().getString(R.string.manager_sotay),
				R.drawable.menu_sotay_material, new SotayFragment());

		Lich = newSection(getResources().getString(R.string.manager_lich),
				R.drawable.menu_lich_material, new CalendarFragment());

		LichTuan = newSection(
				getResources().getString(R.string.manager_lichtuan),
				R.drawable.menu_lichtuan_material, new CalendarFragment());

		LienHe = newSection(getResources().getString(R.string.manager_lienhe),
				R.drawable.menu_lienhe_material, new LienHeFragment());

		DongBo = newSection(getResources().getString(R.string.manager_dongbo),
				R.drawable.menu_dongbo_material, new SotayFragment());

		CaiDat = newSection(getResources().getString(R.string.manager_caidat),
				R.drawable.menu_caidat_material, new SotayFragment());

		ThongTin = newSection(
				getResources().getString(R.string.manager_thongtin),
				R.drawable.menu_thongtin_material, new SotayFragment());
		
		ThongTinCanBo = newSection(
				getResources().getString(R.string.manager_thongtincanbo),
				R.drawable.menu_thongtin_material, new ThongTinCanBoFragment());

		this.addSection(GhiChu);
		this.addSection(SoTay);
		this.addSection(Lich);
		this.addSection(LichTuan);
		this.addSection(LienHe);
		this.addSection(DongBo);
		this.addSection(CaiDat);
		this.addSection(ThongTin);
		this.addSection(ThongTinCanBo);

	}

	private void saveInfoCanbo() {
		CanBo cb;
		cb = (CanBo) getIntent().getSerializableExtra("canbo");
		if (cb != null) {
			Global.savePreference(getApplicationContext(), Const.USER_MACANBO,
					cb.getMaCanbo());
			Global.savePreference(getApplicationContext(), Const.USER_HOTEN,
					cb.getTenCanbo());
			Global.savePreference(getApplicationContext(), Const.USER_DIACHI,
					cb.getDiachi());
			Global.savePreference(getApplicationContext(), Const.USER_SDT,
					cb.getSdt());
			Global.savePreference(getApplicationContext(), Const.USER_EMAIL,
					cb.getEmail());
			Global.savePreference(getApplicationContext(), Const.USER_CMND,
					cb.getCmnd());
			Global.savePreference(getApplicationContext(), Const.USER_HOCVI,
					cb.getHocvi());
			Global.savePreference(getApplicationContext(), Const.USER_HOCHAM,
					cb.getHocham());
			Global.savePreference(getApplicationContext(), Const.USER_ANH,
					cb.getAvatar());
			Global.savePreference(getApplicationContext(), Const.USER_MADONVI,
					cb.getMaDonvi());
		}
	}

	@Override
	public void onAccountOpening(MaterialAccount account) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChangeAccount(MaterialAccount newAccount) {
		// TODO Auto-generated method stub

	}

}
