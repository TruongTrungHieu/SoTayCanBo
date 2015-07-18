package com.hou.models;

import java.io.Serializable;

public class GhiChu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String idGhiChu;
	private String tenGhiChu;
	private String noidung;
	private long thoigiantao;
	private String idSotay;
	private long thoigiansua;
	private boolean bookmark;
	
	public GhiChu() {
		
	}
	
	public GhiChu(String idGhiChu, String tenGhiChu, String noidung,
			long thoigiantao, String idSotay, long thoigiansua, boolean bookmark) {
		super();
		this.idGhiChu = idGhiChu;
		this.tenGhiChu = tenGhiChu;
		this.noidung = noidung;
		this.thoigiantao = thoigiantao;
		this.idSotay = idSotay;
		this.thoigiansua = thoigiansua;
		this.bookmark = bookmark;
	}

	public String getIdGhiChu() {
		return idGhiChu;
	}

	public boolean isBookmark() {
		return bookmark;
	}

	public void setBookmark(boolean bookmark) {
		this.bookmark = bookmark;
	}

	public void setIdGhiChu(String idGhiChu) {
		this.idGhiChu = idGhiChu;
	}

	public String getTenGhiChu() {
		return tenGhiChu;
	}

	public void setTenGhiChu(String tenGhiChu) {
		this.tenGhiChu = tenGhiChu;
	}

	public String getNoidung() {
		return noidung;
	}

	public void setNoidung(String noidung) {
		this.noidung = noidung;
	}

	public long getThoigiantao() {
		return thoigiantao;
	}

	public void setThoigiantao(long thoigiantao) {
		this.thoigiantao = thoigiantao;
	}

	public String getIdSotay() {
		return idSotay;
	}

	public void setIdSotay(String idSotay) {
		this.idSotay = idSotay;
	}

	public long getThoigiansua() {
		return thoigiansua;
	}

	public void setThoigiansua(long thoigiansua) {
		this.thoigiansua = thoigiansua;
	}
	
	
}
