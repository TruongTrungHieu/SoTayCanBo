package com.hou.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TuanSukien implements Serializable {
	private String day;
	private List<SuKien> itemlist = new ArrayList<SuKien>();
}
