package com.hou.models;

import java.io.Serializable;

public class DM_Nhomdonvi implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String maNhomdonvi;
	private String tenNhomdonvi;
	private String ghichu;
	
	public DM_Nhomdonvi() {
		
	}

	public DM_Nhomdonvi(String maNhomdonvi, String tenNhomdonvi, String ghichu) {
		super();
		this.maNhomdonvi = maNhomdonvi;
		this.tenNhomdonvi = tenNhomdonvi;
		this.ghichu = ghichu;
	}

	public String getMaNhomdonvi() {
		return maNhomdonvi;
	}

	public void setMaNhomdonvi(String maNhomdonvi) {
		this.maNhomdonvi = maNhomdonvi;
	}

	public String getTenNhomdonvi() {
		return tenNhomdonvi;
	}

	public void setTenNhomdonvi(String tenNhomdonvi) {
		this.tenNhomdonvi = tenNhomdonvi;
	}

	public String getGhichu() {
		return ghichu;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
