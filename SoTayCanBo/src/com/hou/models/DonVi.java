package com.hou.models;

import java.io.Serializable;

public class DonVi implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String maDonvi;
	private String tenDonvi;
	private String sodienthoai;
	private String email;
	private String website;
	private String fax;
	private String diachi;
	private String maNhomdonvi;
	
	public DonVi() {
		
	}

	public DonVi(String maDonvi, String tenDonvi, String sodienthoai,
			String email, String website, String fax, String diachi,
			String maNhomdonvi) {
		super();
		this.maDonvi = maDonvi;
		this.tenDonvi = tenDonvi;
		this.sodienthoai = sodienthoai;
		this.email = email;
		this.website = website;
		this.fax = fax;
		this.diachi = diachi;
		this.maNhomdonvi = maNhomdonvi;
	}

	public String getMaDonvi() {
		return maDonvi;
	}

	public void setMaDonvi(String maDonvi) {
		this.maDonvi = maDonvi;
	}

	public String getTenDonvi() {
		return tenDonvi;
	}

	public void setTenDonvi(String tenDonvi) {
		this.tenDonvi = tenDonvi;
	}

	public String getSodienthoai() {
		return sodienthoai;
	}

	public void setSodienthoai(String sodienthoai) {
		this.sodienthoai = sodienthoai;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getDiachi() {
		return diachi;
	}

	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}

	public String getMaNhomdonvi() {
		return maNhomdonvi;
	}

	public void setMaNhomdonvi(String maNhomdonvi) {
		this.maNhomdonvi = maNhomdonvi;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
