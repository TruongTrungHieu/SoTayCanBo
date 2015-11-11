package com.hou.models;

import java.io.Serializable;

public class SuKien implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String maSukien;
	private String thu;
	private String buoi;
	private String ngay;
	private String tenSukien;
	private String phong;
	private String diadiem;
	private String tp_thamgia;
	private String thoigianbatdau;
	private int trangthai;
	private String ghichu;
	private String noidung;
	
	public SuKien() {
		
	}

	public SuKien(String maSukien, String thu, String buoi, String ngay,
			String tenSukien, String phong, String diadiem, String tp_thamgia,
			String thoigianbatdau, int trangthai, String ghichu, String noidung) {
		super();
		this.maSukien = maSukien;
		this.thu = thu;
		this.buoi = buoi;
		this.ngay = ngay;
		this.tenSukien = tenSukien;
		this.phong = phong;
		this.diadiem = diadiem;
		this.tp_thamgia = tp_thamgia;
		this.thoigianbatdau = thoigianbatdau;
		this.trangthai = trangthai;
		this.ghichu = ghichu;
		this.noidung = noidung;
	}

	public String getMaSukien() {
		return maSukien;
	}

	public void setMaSukien(String maSukien) {
		this.maSukien = maSukien;
	}

	public String getThu() {
		return thu;
	}

	public void setThu(String thu) {
		this.thu = thu;
	}

	public String getBuoi() {
		return buoi;
	}

	public void setBuoi(String buoi) {
		this.buoi = buoi;
	}

	public String getNgay() {
		return ngay;
	}

	public void setNgay(String ngay) {
		this.ngay = ngay;
	}

	public String getTenSukien() {
		return tenSukien;
	}

	public void setTenSukien(String tenSukien) {
		this.tenSukien = tenSukien;
	}

	public String getPhong() {
		return phong;
	}

	public void setPhong(String phong) {
		this.phong = phong;
	}

	public String getDiadiem() {
		return diadiem;
	}

	public void setDiadiem(String diadiem) {
		this.diadiem = diadiem;
	}

	public String getTp_thamgia() {
		return tp_thamgia;
	}

	public void setTp_thamgia(String tp_thamgia) {
		this.tp_thamgia = tp_thamgia;
	}

	public String getThoigianbatdau() {
		return thoigianbatdau;
	}

	public void setThoigianbatdau(String thoigianbatdau) {
		this.thoigianbatdau = thoigianbatdau;
	}

	public int getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(int trangthai) {
		this.trangthai = trangthai;
	}

	public String getGhichu() {
		return ghichu;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}

	public String getNoidung() {
		return noidung;
	}

	public void setNoidung(String noidung) {
		this.noidung = noidung;
	}
	
	
}
