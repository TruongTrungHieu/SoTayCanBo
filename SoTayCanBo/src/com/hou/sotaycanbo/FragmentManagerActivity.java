package com.hou.sotaycanbo;

import com.hou.fragment.SotayFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import it.neokree.materialnavigationdrawer.elements.listeners.MaterialAccountListener;

public class FragmentManagerActivity extends MaterialNavigationDrawer implements
		MaterialAccountListener {

	private MaterialAccount account;
	private MaterialSection<Fragment> SoTay;
	private MaterialSection<Fragment> GhiChu;
	private MaterialSection<Fragment> Lich;
	private MaterialSection<Fragment> LichTuan;
	private MaterialSection<Fragment> LienHe;
	private MaterialSection<Fragment> DongBo;
	private MaterialSection<Fragment> CaiDat;
	private MaterialSection<Fragment> ThongTin;

	public static FragmentManagerActivity sInstance;

	@Override
	public void init(Bundle savedInstanceState) {
		sInstance = this;
		this.disableLearningPattern();

		account = new MaterialAccount(this.getResources(), "Mai Lan", "",
				R.drawable.account_ava, R.drawable.account_bg);
		this.addAccount(account);
		
		this.setAccountListener(this);
		this.setDrawerBackgroundColor(this.getResources().getColor(
				R.color.white));

		SoTay = newSection("Sô Tay", R.drawable.menu_sotay_material, new SotayFragment());
		GhiChu = newSection("Ghi Chú", R.drawable.menu_sotay_material, new SotayFragment());
		Lich = newSection("Lịch", R.drawable.menu_sotay_material, new SotayFragment());
		LichTuan = newSection("Lịch Tuần", R.drawable.menu_sotay_material, new SotayFragment());
		LienHe = newSection("Liên Hệ", R.drawable.menu_sotay_material, new SotayFragment());
		DongBo = newSection("Đồng Bộ", R.drawable.menu_sotay_material, new SotayFragment());
		CaiDat = newSection("Cài Đặt", R.drawable.menu_sotay_material, new SotayFragment());
		ThongTin = newSection("Thông Tin", R.drawable.menu_sotay_material, new SotayFragment());

		this.addSection(SoTay);
		this.addSection(GhiChu);
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
