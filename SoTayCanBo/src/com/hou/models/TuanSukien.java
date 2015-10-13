package com.hou.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TuanSukien implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String day;
	private List<SuKien> itemlist = new ArrayList<SuKien>();
	public TuanSukien(String day, List<SuKien> itemlist) {
		super();
		this.day = day;
		this.itemlist = itemlist;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public List<SuKien> getItemlist() {
		return itemlist;
	}
	public void setItemlist(List<SuKien> itemlist) {
		this.itemlist = itemlist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
