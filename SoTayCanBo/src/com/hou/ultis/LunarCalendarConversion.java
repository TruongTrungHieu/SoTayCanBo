package com.hou.ultis;

public class LunarCalendarConversion {

	@SuppressWarnings("unused")
	private String solarDay;
	private int dd;
	private int mm;
	private int yyyy;

	public LunarCalendarConversion() {

	}

	public LunarCalendarConversion(String solarDay) {
		this.solarDay = solarDay;
		String[] a = solarDay.split("/");
		dd = Integer.parseInt(a[0]);
		mm = Integer.parseInt(a[1]);
		yyyy = Integer.parseInt(a[2]);
	}

	public double jdFromDate(int day, int month, int year) {
		int a = (int) ((14 - mm) / 12);
		int y = year + 4800 - a;
		int m = month + 12 * a - 3;
		double jd = dd + (int)((153 * m + 2) / 5) + 365 * y + (int)(y / 4) - (int)(y / 100)
				+ (int)(y / 400) - 32045;
		if (jd < 2299161) {
			jd = dd + (int)((153 * m + 2) / 5) + 365 * y + (int)(y / 4) - 32083;
		}
		return jd;
	}

	public double getNewMoonDay(int k, int timeZone) {
		double T, T2, T3, dr, Jd1, M, Mpr, F, C1, deltat, JdNew;
		T = k / 1236.85; // Time in Julian centuries from 1900 January 0.5
		T2 = T * T;
		T3 = T2 * T;
		dr = Math.PI / 180;
		Jd1 = 2415020.75933 + 29.53058868 * k + 0.0001178 * T2 - 0.000000155
				* T3;
		Jd1 = Jd1 + 0.00033
				* Math.sin((166.56 + 132.87 * T - 0.009173 * T2) * dr); 
		M = 359.2242 + 29.10535608 * k - 0.0000333 * T2 - 0.00000347 * T3;
		Mpr = 306.0253 + 385.81691806 * k + 0.0107306 * T2 + 0.00001236 * T3;
		F = 21.2964 + 390.67050646 * k - 0.0016528 * T2 - 0.00000239 * T3;
		C1 = (0.1734 - 0.000393 * T) * Math.sin(M * dr) + 0.0021
				* Math.sin(2 * dr * M);
		C1 = C1 - 0.4068 * Math.sin(Mpr * dr) + 0.0161 * Math.sin(dr * 2 * Mpr);
		C1 = C1 - 0.0004 * Math.sin(dr * 3 * Mpr);
		C1 = C1 + 0.0104 * Math.sin(dr * 2 * F) - 0.0051
				* Math.sin(dr * (M + Mpr));
		C1 = C1 - 0.0074 * Math.sin(dr * (M - Mpr)) + 0.0004
				* Math.sin(dr * (2 * F + M));
		C1 = C1 - 0.0004 * Math.sin(dr * (2 * F - M)) - 0.0006
				* Math.sin(dr * (2 * F + Mpr));
		C1 = C1 + 0.0010 * Math.sin(dr * (2 * F - Mpr)) + 0.0005
				* Math.sin(dr * (2 * Mpr + M));
		if (T < -11) {
			deltat = 0.001 + 0.000839 * T + 0.0002261 * T2 - 0.00000845 * T3
					- 0.000000081 * T * T3;
		} else {
			deltat = -0.000278 + 0.000265 * T + 0.000262 * T2;
		}
		JdNew = Jd1 + C1 - deltat;
		return (JdNew + 0.5 + timeZone / 24);
	}

	public int getSunLongitude(double jdn, double timeZone) {
		double T, T2, dr, M, L0, DL, L;
		T = (jdn - 2451545.5 - timeZone / 24) / 36525;
		T2 = T * T;
		dr = Math.PI / 180;
		M = 357.52910 + 35999.05030 * T - 0.0001559 * T2 - 0.00000048 * T * T2; 
		L0 = 280.46645 + 36000.76983 * T + 0.0003032 * T2; 
		DL = (1.914600 - 0.004817 * T - 0.000014 * T2) * Math.sin(dr * M);
		DL = DL + (0.019993 - 0.000101 * T) * Math.sin(dr * 2 * M) + 0.000290
				* Math.sin(dr * 3 * M);
		L = L0 + DL; 
		L = L * dr;
		L = L - Math.PI * 2 * ((int)(L / (Math.PI * 2)));
		return (int)(L / Math.PI * 6);
	}

	public double getLunarMonth11(int yy, int timeZone) {
		double off, nm, sunLong;
		int k;
		off = jdFromDate(31, 12, yy) - 2415021;
		k = (int)(off / 29.530588853);
		nm = getNewMoonDay(k, timeZone);
		sunLong = getSunLongitude(nm, timeZone);
		if (sunLong >= 9) {
			nm = getNewMoonDay(k - 1, timeZone);
		}
		return nm;
	}

	public int getLeapMonthOffset(int a11, int timeZone) {

		double last, arc;
		int k = (int)((a11 - 2415021.076998695) / 29.530588853 + 0.5);
		last = 0;
		int i = 1; // We start with the month following lunar month 11
		arc = getSunLongitude(getNewMoonDay(k + i, timeZone), timeZone);
		do {
			last = arc;
			i++;
			arc = getSunLongitude(getNewMoonDay(k + i, timeZone), timeZone);
		} while (arc != last && i < 14);
		return i - 1;
	}

	@SuppressWarnings("unused")
	public String convertSolar2Lunar(int timeZone) {
		String result = "";

		double dayNumber, monthStart, a11, b11, lunarDay, lunarMonth, lunarYear, lunarLeap;
		int k;
		dayNumber = jdFromDate(dd, mm, yyyy);
		k = (int) ((dayNumber - 2415021.076998695) / 29.530588853);
		monthStart = getNewMoonDay(k + 1, timeZone);
		if (monthStart > dayNumber) {
			monthStart = getNewMoonDay(k, timeZone);
		}
		a11 = getLunarMonth11(yyyy, timeZone);
		b11 = a11;
		if (a11 >= monthStart) {
			lunarYear = yyyy;
			a11 = getLunarMonth11(yyyy - 1, timeZone);
		} else {
			lunarYear = yyyy + 1;
			b11 = getLunarMonth11(yyyy + 1, timeZone);
		}
		lunarDay = dayNumber - monthStart + 1;
		double diff = (double) ((monthStart - a11) / 29);
		lunarLeap = 0;
		lunarMonth = diff + 11;
		if (b11 - a11 > 365) {
			int leapMonthDiff = getLeapMonthOffset((int)a11, timeZone);
			if (diff >= leapMonthDiff) {
				lunarMonth = diff + 10;
				if (diff == leapMonthDiff) {
					lunarLeap = 1;
				}
			}
		}
		if (lunarMonth > 12) {
			lunarMonth = lunarMonth - 12 - 1;
		}
		if (lunarMonth >= 11 && diff < 4) {
			lunarYear -= 1;
		}
		result = (int)lunarDay + "/" + (int)lunarMonth + "/" + (int)lunarYear;
		return result;
	}

}
