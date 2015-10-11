package com.hou.models;

import java.io.Serializable;

public class DinhKem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String maDinhkem;
	private String maGhichu;
	private String loaifile;
	private String url;
	private String filename;
	
	public DinhKem() {
		
	}

	public DinhKem(String maDinhkem, String maGhichu, String loaifile,
			String url, String filename) {
		super();
		this.maDinhkem = maDinhkem;
		this.maGhichu = maGhichu;
		this.loaifile = loaifile;
		this.url = url;
		this.filename = filename;
	}

	public String getMaDinhkem() {
		return maDinhkem;
	}

	public void setMaDinhkem(String maDinhkem) {
		this.maDinhkem = maDinhkem;
	}

	public String getMaGhichu() {
		return maGhichu;
	}

	public void setMaGhichu(String maGhichu) {
		this.maGhichu = maGhichu;
	}

	public String getLoaifile() {
		return loaifile;
	}

	public void setLoaifile(String loaifile) {
		this.loaifile = loaifile;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
