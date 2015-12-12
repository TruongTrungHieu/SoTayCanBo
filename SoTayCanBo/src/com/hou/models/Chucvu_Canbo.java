package com.hou.models;

import java.io.Serializable;

public class Chucvu_Canbo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String maCanbo;
	private String maDonvi;
	private String tenChucvu;
	private String uutien;
	
	public Chucvu_Canbo() {
		
	}

	public Chucvu_Canbo(String maCanbo, String maDonvi, String tenChucvu,
			String uutien) {
		super();
		this.maCanbo = maCanbo;
		this.maDonvi = maDonvi;
		this.tenChucvu = tenChucvu;
		this.uutien = uutien;
	}

	public String getMaCanbo() {
		return maCanbo;
	}

	public void setMaCanbo(String maCanbo) {
		this.maCanbo = maCanbo;
	}

	public String getMaDonvi() {
		return maDonvi;
	}

	public void setMaDonvi(String maDonvi) {
		this.maDonvi = maDonvi;
	}

	public String getTenChucvu() {
		return tenChucvu;
	}

	public void setTenChucvu(String tenChucvu) {
		this.tenChucvu = tenChucvu;
	}

	public String getUutien() {
		return uutien;
	}

	public void setUutien(String uutien) {
		this.uutien = uutien;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
