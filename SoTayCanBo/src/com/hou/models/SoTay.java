package com.hou.models;

import java.io.Serializable;

public class SoTay implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tenSoTay;
	private int soGhiChu;
	
	public SoTay() {
		
	}
	
	public SoTay(String ten, int soNote) {
		this.tenSoTay = ten;
		this.soGhiChu = soNote;
	}

	public String getTenSoTay() {
		return tenSoTay;
	}

	public void setTenSoTay(String tenSoTay) {
		this.tenSoTay = tenSoTay;
	}

	public int getSoGhiChu() {
		return soGhiChu;
	}

	public void setSoGhiChu(int soGhiChu) {
		this.soGhiChu = soGhiChu;
	}
	
}
