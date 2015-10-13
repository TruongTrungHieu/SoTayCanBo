package com.hou.models;

public class DayNumberOfContents {
	private String Dayname;
	private int number;

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

	public DayNumberOfContents(String day, int num) {
		this.Dayname = day;
		this.number = num;
	}

}
