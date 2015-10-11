package com.hou.models;

import java.io.Serializable;

public class SoTay implements Serializable{
	/**
	 * Class SoTay
	 */
	private static final long serialVersionUID = 1L;
	
	private String maSoTay;
	private String tenSoTay;
	private long ngayTao;
	private int soGhiChu;
	private String maCanBo;
	
	public SoTay() {
		
	}

	public SoTay(String maSoTay, String tenSoTay, long ngayTao, int soGhiChu,
			String maCanBo) {
		super();
		this.maSoTay = maSoTay;
		this.tenSoTay = tenSoTay;
		this.ngayTao = ngayTao;
		this.soGhiChu = soGhiChu;
		this.maCanBo = maCanBo;
	}

	public String getMaSoTay() {
		return maSoTay;
	}

	public void setMaSoTay(String maSoTay) {
		this.maSoTay = maSoTay;
	}

	public String getTenSoTay() {
		return tenSoTay;
	}

	public void setTenSoTay(String tenSoTay) {
		this.tenSoTay = tenSoTay;
	}

	public long getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(long ngayTao) {
		this.ngayTao = ngayTao;
	}

	public int getSoGhiChu() {
		return soGhiChu;
	}

	public void setSoGhiChu(int soGhiChu) {
		this.soGhiChu = soGhiChu;
	}

	public String getMaCanBo() {
		return maCanBo;
	}

	public void setMaCanBo(String maCanBo) {
		this.maCanBo = maCanBo;
	}
	
}
