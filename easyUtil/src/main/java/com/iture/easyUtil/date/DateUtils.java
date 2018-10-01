package com.iture.easyUtil.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	/**
	 * 对日期加减一定天数
	 * @param date
	 * @param day 加减的天数
	 * @return
	 */
	public static Date addDay(Date date,int day) {
		 Calendar c = Calendar.getInstance();
		 c.setTime(date);
		 c.add(Calendar.DAY_OF_YEAR, day);
		 return c.getTime();
	}
	public static String formatDate(Date date,String format) {
		 SimpleDateFormat sdf=new SimpleDateFormat(format);
		 return sdf.format(date);
	}
	public static int getYyyyMMdd(Date date) {
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		 return Integer.parseInt(sdf.format(date));
	}
	public static int getYyyyMMdd(long ms) {
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		 return Integer.parseInt(sdf.format(ms));
	}
	public static Date parseDay(String dateStr,String format) {
		 SimpleDateFormat sdf=new SimpleDateFormat(format);
		 try {
			Date date = sdf.parse(dateStr);
			return date;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	public static Date parseYyyyMMdd(String yyyyMMdd) {
		 return parseDay(yyyyMMdd,"yyyyMMdd");
	}
} 
