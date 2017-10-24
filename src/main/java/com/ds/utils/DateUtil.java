package com.ds.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd"; 
	public static final String DEFAULT_FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_FORMAT_HOUR = "yyyy-MM-dd HH";
	public static final char[] DEFAULT_FORMAT_TIME_CHAR = DEFAULT_FORMAT_TIME.toCharArray();
	private static String INCOMFORMAT = "yyyyMMddHHmmss";
	public static Date string2Date(String dateString, String dateFormat) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		try {
			return simpleDateFormat.parse(dateString);
		} catch (java.text.ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	public static Date string2Date(String dateString) {
		return string2Date(dateString,DEFAULT_FORMAT);
	}
	public static Date string2TimeDate(String dateString) {
		StringBuilder sb = new StringBuilder();
		char[] cs = dateString.trim().toCharArray();
		if(cs.length < 4)return null;
		int i = 0;
		while(i<cs.length){
			if(i==4 || i==7 || i==10 || i==13 || i==16)sb.append(cs[i]);
			else sb.append(DEFAULT_FORMAT_TIME_CHAR[i]);
			i++;
		}
		return string2Date(dateString,sb.toString());
	}
	
	public static Date string2DatePower(String dateString) {
		StringBuilder sb = new StringBuilder();
		char[] cs = dateString.trim().toCharArray();

		for(char c : cs){
			if(c >= 48 && c <= 57){
				sb.append(c);
			}
		}
		String toTime = sb.toString();
        int strlen = toTime.length();
        if(strlen > 14){
            return null;
        }
//        if(!toTime.startsWith("19") && !toTime.startsWith("20")){
//            return null;
//        }
		return string2Date(toTime, INCOMFORMAT.substring(0,strlen));
	}
    public static String formatString(String dateString) {
        Date date = string2DatePower(dateString);
        return date2TimeString(date);
    }

    public static boolean isTimeString(String str){
        Date d = string2DatePower(str);
        return (d != null);
    }
	public static Date shortenDate(Date date) {
		return DateUtil.string2Date(DateUtil.date2String(date));
	}
	/**
	 * get the max time of the day
	 * @param date
	 * @return
	 */
	public static Date maxDate(Date date) {
		String dateStr = date2String(date,"yyyyMMdd");
		dateStr+="235959";
		return DateUtil.string2Date(dateStr, "yyyyMMddHHmmss");
	}
	
	/**
	 * format a date to a string with specific format
	 * Example: formatDate(new Date(), "yyyy-MM-dd")
	 * @param date date to be formated
	 * @param dateFormat date format
	 * @return
	 */
	public static String date2String(Date date, String dateFormat) {
		if (date == null)
			return "";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		return simpleDateFormat.format(date);
		
	}
	public static String date2String(Date date) {
		return date2String(date,DEFAULT_FORMAT);
	}
	public static String date2TimeString(Date date) {
		return date2String(date,DEFAULT_FORMAT_TIME);
	}
	public static Date sqldate2Utildate(java.sql.Date sqldate){
		if(sqldate==null)return null;
		return new Date(sqldate.getTime());
	}
	public static Date timestamp2Utildate(java.sql.Timestamp timestamp){
		if(timestamp==null)return null;
		return new Date(timestamp.getTime());
	}
	public static boolean isSameDate(Date date1 , Date date2){
		if(date2String(date1).equals(date2String(date2)))return true;
		return false;
	}
	public static Date addDays(Date date, int days){
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		calendar.add(GregorianCalendar.DAY_OF_MONTH,days);
		return calendar.getTime();
	}
	public static Date addMonths(Date date, int months){
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		calendar.add(GregorianCalendar.MONTH,months);
		return calendar.getTime();
	}
	public static Date addSeconds(Date date, int seconds){
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		calendar.add(GregorianCalendar.SECOND,seconds);
		return calendar.getTime();
	}
	public static int compareDate(Date date1,Date date2){
		Calendar calendar1 = new GregorianCalendar();
		calendar1.setTime(date1);
		Calendar calendar2 = new GregorianCalendar();
		calendar2.setTime(date2);
		return calendar1.compareTo(calendar2);
	}
	public static long diffTimeInSecond(Date date1,Date date2){
		long diffl = date1.getTime() - date2.getTime();
		long diff = diffl/1000;
		return diff;
	}
	public static long diffTimeInMin(Date date1,Date date2){
		long diffl = date1.getTime() - date2.getTime();
		long diff = diffl/60000;
		return diff;
	}
	public static int diffTimeInYear(Date date1,Date date2){
		Calendar calendar1 = new GregorianCalendar();
		calendar1.setTime(date1);
		Calendar calendar2 = new GregorianCalendar();
		calendar2.setTime(date2);
		return calendar1.get(Calendar.YEAR)-calendar2.get(Calendar.YEAR);
	}
	public static Date extractMonthDayFromDate(Date date){
		Calendar calendar1 = new GregorianCalendar();
		calendar1.setTime(date);
		Calendar now = new GregorianCalendar();
		calendar1.set(Calendar.YEAR, now.get(Calendar.YEAR));
		return calendar1.getTime();
	}
	public static void log(Object s){
		System.out.println(s);
	}
	
    private static boolean checkYear(String year) {
        if (StringUtils.isEmpty(year)) {
            return false;
        }
        if (year.length() != 4) {
            return false;
        }
        try {
            Integer.parseInt(year);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    private static boolean checkMonth(String month) {
        if (StringUtils.isEmpty(month)) {
            return false;
        }
        if (month.length() > 2) {
            return false;
        }
        try {
            int m = Integer.parseInt(month);
            if (m > 12) {
                return false;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
    
    private static boolean checkDay(String day) {
        if (StringUtils.isEmpty(day)) {
            return false;
        }
        if (day.length() > 2) {
            return false;
        }
        try {
            int d = Integer.parseInt(day);
            if (d > 31) {
                return false;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
    
    private static boolean checkHour(String hour) {
        if (StringUtils.isEmpty(hour)) {
            return false;
        }
        if (hour.length() > 2) {
            return false;
        }
        try {
            int h = Integer.parseInt(hour);
            if (h > 24) {
                return false;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
    
    private static boolean checkMin(String min) {
        if (StringUtils.isEmpty(min)) {
            return false;
        }
        if (min.length() > 2) {
            return false;
        }
        try {
            int m = Integer.parseInt(min);
            if (m > 59) {
                return false;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
    
    private static boolean checkSec(String sec) {
        if (StringUtils.isEmpty(sec)) {
            return false;
        }
        if (sec.length() > 2) {
            return false;
        }
        try {
            int s = Integer.parseInt(sec);
            if (s > 59) {
                return false;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static boolean isDate(String str) {
        char[] cs = str.toCharArray();
        boolean hasSpace = false;
        int dashCnt = 0;
        int colonCnt = 0;
        StringBuilder sb = new StringBuilder();
        String year = null, month = null, day = null, hour = null, min = null, sec = null;
        for (char c : cs) {
            if (c == '-' || c == '/') {
                if (dashCnt == 0) {
                    year = sb.toString();
                    if (!checkYear(year)) {
                        return false;
                    }
                    sb.setLength(0);
                } else if (dashCnt == 1) {
                    month = sb.toString();
                    if (!checkMonth(month)) {
                        return false;
                    }
                    sb.setLength(0);
                } else {
                    return false;
                }
                dashCnt++;
            } else if (c == ' ') {
                if (!hasSpace && dashCnt == 2) {
                    hasSpace = true;
                    day = sb.toString();
                    if (!checkDay(day)) {
                        return false;
                    }
                    sb.setLength(0);
                }

            } else if (c == ':') {
                if (colonCnt == 0) {
                    hour = sb.toString();
                    if (!checkHour(hour)) {
                        return false;
                    }
                    sb.setLength(0);
                } else if (colonCnt == 1) {
                    min = sb.toString();
                    min = sb.toString();
                    if (!checkMin(min)) {
                        return false;
                    }
                    sb.setLength(0);
                } else {
                    return false;
                }
                colonCnt++;

            } else {
                sb.append(c);
            }
        }

        if (day == null) {
            day = sb.toString();
        } else if (hour == null) {
            hour = sb.toString();
            if (!checkHour(hour)) {
                return false;
            }
        } else if (min == null) {
            min = sb.toString();
            if (!checkMin(min)) {
                return false;
            }
        } else {
            sec = sb.toString();
            if (!checkSec(sec)) {
                return false;
            }
        }

        return true;
    }

}
