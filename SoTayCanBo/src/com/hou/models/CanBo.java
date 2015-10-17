package com.hou.models;

import java.io.Serializable;

public class CanBo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String maCanbo;
	private String maDonvi;
	private String tenCanbo;
	private String diachi;
	private String email;
	private String cmnd;
	private String hocham;
	private String hocvi;
	private String avatar;
	private String sdt;
	
	public CanBo() {
		
	}

	public CanBo(String maCanbo, String maDonvi,
			String tenCanbo, String diachi, String email, String cmnd,
			String hocham, String hocvi, String avatar, String sdt) {
		super();
		this.maCanbo = maCanbo;
		this.maDonvi = maDonvi;
		this.tenCanbo = tenCanbo;
		this.diachi = diachi;
		this.email = email;
		this.cmnd = cmnd;
		this.hocham = hocham;
		this.hocvi = hocvi;
		this.avatar = avatar;
		this.sdt = sdt;
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

	public String getTenCanbo() {
		return tenCanbo;
	}

	public void setTenCanbo(String tenCanbo) {
		this.tenCanbo = tenCanbo;
	}

	public String getDiachi() {
		return diachi;
	}

	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCmnd() {
		return cmnd;
	}

	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}

	public String getHocham() {
		return hocham;
	}

	public void setHocham(String hocham) {
		this.hocham = hocham;
	}

	public String getHocvi() {
		return hocvi;
	}

	public void setHocvi(String hocvi) {
		this.hocvi = hocvi;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
