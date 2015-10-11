package com.hou.models;

import java.io.Serializable;

public class GhiChu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String maGhiChu;
	private String tenGhiChu;
	private long ngaytao;
	private String noidung;
	private long ngaysua;
	private int trangthai;
	private int dinhkem;
	private long ngaythuchien;
	private int bookmark;
	private String maSotay;
	
	public GhiChu() {
		
	}

	public GhiChu(String maGhiChu, String tenGhiChu, long ngaytao,
			String noidung, long ngaysua, int trangthai, int dinhkem,
			long ngaythuchien, int bookmark, String maSotay) {
		super();
		this.maGhiChu = maGhiChu;
		this.tenGhiChu = tenGhiChu;
		this.ngaytao = ngaytao;
		this.noidung = noidung;
		this.ngaysua = ngaysua;
		this.trangthai = trangthai;
		this.dinhkem = dinhkem;
		this.ngaythuchien = ngaythuchien;
		this.bookmark = bookmark;
		this.maSotay = maSotay;
	}

	public String getMaGhiChu() {
		return maGhiChu;
	}

	public void setMaGhiChu(String maGhiChu) {
		this.maGhiChu = maGhiChu;
	}

	public String getTenGhiChu() {
		return tenGhiChu;
	}

	public void setTenGhiChu(String tenGhiChu) {
		this.tenGhiChu = tenGhiChu;
	}

	public long getNgaytao() {
		return ngaytao;
	}

	public void setNgaytao(long ngaytao) {
		this.ngaytao = ngaytao;
	}

	public String getNoidung() {
		return noidung;
	}

	public void setNoidung(String noidung) {
		this.noidung = noidung;
	}

	public long getNgaysua() {
		return ngaysua;
	}

	public void setNgaysua(long ngaysua) {
		this.ngaysua = ngaysua;
	}

	public int getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(int trangthai) {
		this.trangthai = trangthai;
	}

	public int getDinhkem() {
		return dinhkem;
	}

	public void setDinhkem(int dinhkem) {
		this.dinhkem = dinhkem;
	}

	public long getNgaythuchien() {
		return ngaythuchien;
	}

	public void setNgaythuchien(long ngaythuchien) {
		this.ngaythuchien = ngaythuchien;
	}

	public int getBookmark() {
		return bookmark;
	}

	public void setBookmark(int bookmark) {
		this.bookmark = bookmark;
	}

	public String getMaSotay() {
		return maSotay;
	}

	public void setMaSotay(String maSotay) {
		this.maSotay = maSotay;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
