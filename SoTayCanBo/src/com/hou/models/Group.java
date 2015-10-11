package com.hou.models;

import java.io.Serializable;

public class Group implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String maGroup;
	private String tenGroup;
	private String maCanbo_admin;
	
	public Group() {
		
	}

	public Group(String maGroup, String tenGroup, String maCanbo_admin) {
		super();
		this.maGroup = maGroup;
		this.tenGroup = tenGroup;
		this.maCanbo_admin = maCanbo_admin;
	}

	public String getMaGroup() {
		return maGroup;
	}

	public void setMaGroup(String maGroup) {
		this.maGroup = maGroup;
	}

	public String getTenGroup() {
		return tenGroup;
	}

	public void setTenGroup(String tenGroup) {
		this.tenGroup = tenGroup;
	}

	public String getMaCanbo_admin() {
		return maCanbo_admin;
	}

	public void setMaCanbo_admin(String maCanbo_admin) {
		this.maCanbo_admin = maCanbo_admin;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
