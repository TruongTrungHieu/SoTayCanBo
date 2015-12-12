package com.hou.models;

import java.io.Serializable;

public class Event implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String maEvent;
	private String tenEvent;
	private String diadiem;
	private String thoigianbatdau;
	private String maLaplai;
	private String maLoinhac;
	private String ngay_event;
	private String maCanbo;
	private String mota;
	private String maSukientuan;
	
	public Event() {
		
	}

	public Event(String maEvent, String tenEvent, String diadiem,
			String thoigianbatdau, String maLaplai, String maLoinhac,
			String ngay_event, String maCanbo, String mota, String maSukientuan) {
		super();
		this.maEvent = maEvent;
		this.tenEvent = tenEvent;
		this.diadiem = diadiem;
		this.thoigianbatdau = thoigianbatdau;
		this.maLaplai = maLaplai;
		this.maLoinhac = maLoinhac;
		this.ngay_event = ngay_event;
		this.maCanbo = maCanbo;
		this.mota = mota;
		this.maSukientuan = maSukientuan;
	}

	public String getMaEvent() {
		return maEvent;
	}

	public void setMaEvent(String maEvent) {
		this.maEvent = maEvent;
	}

	public String getTenEvent() {
		return tenEvent;
	}

	public void setTenEvent(String tenEvent) {
		this.tenEvent = tenEvent;
	}

	public String getDiadiem() {
		return diadiem;
	}

	public void setDiadiem(String diadiem) {
		this.diadiem = diadiem;
	}

	public String getThoigianbatdau() {
		return thoigianbatdau;
	}

	public void setThoigianbatdau(String thoigianbatdau) {
		this.thoigianbatdau = thoigianbatdau;
	}

	public String getMaLaplai() {
		return maLaplai;
	}

	public void setMaLaplai(String maLaplai) {
		this.maLaplai = maLaplai;
	}

	public String getMaLoinhac() {
		return maLoinhac;
	}

	public void setMaLoinhac(String maLoinhac) {
		this.maLoinhac = maLoinhac;
	}

	public String getNgay_event() {
		return ngay_event;
	}

	public void setNgay_event(String ngay_event) {
		this.ngay_event = ngay_event;
	}

	public String getMaCanbo() {
		return maCanbo;
	}

	public void setMaCanbo(String maCanbo) {
		this.maCanbo = maCanbo;
	}

	public String getMota() {
		return mota;
	}

	public void setMota(String mota) {
		this.mota = mota;
	}
	
	public String getMaSukientuan() {
		return maSukientuan;
	}
	
	public void setMaSukientuan(String maSukientuan) {
		this.maSukientuan = maSukientuan;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
