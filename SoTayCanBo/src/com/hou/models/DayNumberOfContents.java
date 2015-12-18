package com.hou.models;

import java.util.ArrayList;

public class DayNumberOfContents {
	
	private String Dayname;
	private int number;
	private ArrayList<SuKien> listChild = new ArrayList<SuKien>();

	public ArrayList<SuKien> getListChild() {
		return listChild;
	}

	public void setListChild(ArrayList<SuKien> listChild) {
		this.listChild = listChild;
	}

	public String getDayname() {
		return Dayname;
	}

	public void setDayname(String dayname) {
		Dayname = dayname;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public DayNumberOfContents() {
	}

	public DayNumberOfContents(String day, ArrayList<SuKien> listChild) {
		this.Dayname = day;
		this.number = listChild.size();
		this.listChild = listChild;
	}

}
