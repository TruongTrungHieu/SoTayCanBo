package com.hou.sotaycanbo;

import com.hou.fragment.CalendarFragment;
import com.hou.fragment.GhichuFragment;
import com.hou.fragment.LichTuanFragment;
import com.hou.fragment.LienheFragment;
import com.hou.fragment.SotayFragment;

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

	public static FragmentManagerActivity sInstance;

	@Override
	public void init(Bundle savedInstanceState) {
		sInstance = this;
		this.disableLearningPattern();

		account = new MaterialAccount(this.getResources(), "Nguyễn Mai Lan", "Khoa Công nghệ Thông Tin",
				R.drawable.account_ava, R.drawable.account_bg);
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
				R.drawable.menu_lichtuan_material, new LichTuanFragment());

		LienHe = newSection(getResources().getString(R.string.manager_lienhe),
				R.drawable.menu_lienhe_material, new LienheFragment());

		DongBo = newSection(getResources().getString(R.string.manager_dongbo),
				R.drawable.menu_dongbo_material, new SotayFragment());

		CaiDat = newSection(getResources().getString(R.string.manager_caidat),
				R.drawable.menu_caidat_material, new SotayFragment());

		ThongTin = newSection(
				getResources().getString(R.string.manager_thongtin),
				R.drawable.menu_thongtin_material, new SotayFragment());

		this.addSection(GhiChu);
		this.addSection(SoTay);
		this.addSection(Lich);
		this.addSection(LichTuan);
		this.addSection(LienHe);
		this.addSection(DongBo);
		this.addSection(CaiDat);
		this.addSection(ThongTin);

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
