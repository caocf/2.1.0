package com.mysteel.banksteel.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	/**
	 * 获取两个给定日期之前相差的天数,小时,分钟
	 * @param startDate 起始时间
	 * @param endDate 结束时间
	 */
	public static long[] dateDiffEx(Date startDate, Date endDate)
	{
		long[] diffTime = new long[3];

		long minuteDiff = 0L;
		long hourDiff = 0L;
		long dayDiff = 0L;

		long diff = dateDiff(12, startDate, endDate);

		if (diff > 0L)
		{
			minuteDiff = diff % 60L;
			diff /= 60L;
		}

		if (diff > 0L)
		{
			hourDiff = diff % 24L;
			diff /= 24L;
		}

		if (diff > 0L)
		{
			dayDiff = diff;
		}

		diffTime[0] = dayDiff;
		diffTime[1] = hourDiff;
		diffTime[2] = minuteDiff;

		return diffTime;
	}
	
	
	/**
	 * 根据规则得到两个给定日期之前相差的时间
	 * @param iField 日历的规则(1为多少年,2为多少月,3为多少天,10为多少小时,11为一天中的小时,12为分,13为秒)
	 * @param startDate 起始时间
	 * @param endDate 结束时间
	 */
	public static long dateDiff(int iField, Date startDate, Date endDate)
	{
		if(null==startDate||null==endDate){
			return 0;
		}
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();

		int startYear = Integer.parseInt(getFormatDate("yyyy", startDate));
		int endYear = Integer.parseInt(getFormatDate("yyyy", endDate));
		int startMonth = Integer.parseInt(getFormatDate("MM", startDate)) - 1;
		int endMonth = Integer.parseInt(getFormatDate("MM", endDate)) - 1;
		int startDay = Integer.parseInt(getFormatDate("dd", startDate));
		int endDay = Integer.parseInt(getFormatDate("dd", endDate));
		int startHour = Integer.parseInt(getFormatDate("HH", startDate));
		int endHour = Integer.parseInt(getFormatDate("HH", endDate));
		int startMinute = Integer.parseInt(getFormatDate("mm", startDate));
		int endMinute = Integer.parseInt(getFormatDate("mm", endDate));
		int startSecond = Integer.parseInt(getFormatDate("ss", startDate));
		int endSecond = Integer.parseInt(getFormatDate("ss", endDate));
		switch (iField) { case 1:
			return (endYear - startYear);
		case 2:
			long yearDiff = endYear - startYear;
			long monthDiff = endMonth - startMonth;
			return (yearDiff * 12L + monthDiff);
		case 3:
			start.set(startYear, startMonth, startDay, 0, 0, 0);
			end.set(endYear, endMonth, endDay, 0, 0, 0);
			return ((end.getTimeInMillis() - start.getTimeInMillis()) / 
					86400000L);
		case 10:
			start.set(startYear, startMonth, startDay, startHour, 0, 0);
			end.set(endYear, endMonth, endDay, endHour, 0, 0);
			return ((end.getTimeInMillis() - start.getTimeInMillis()) / 
					3600000L);
		case 11:
			start.set(startYear, startMonth, startDay, startHour, 0, 0);
			end.set(endYear, endMonth, endDay, endHour, 0, 0);
			return ((end.getTimeInMillis() - start.getTimeInMillis()) / 
					3600000L);
		case 12:
			start.set(startYear, startMonth, startDay, startHour, startMinute, 
					0);
			end.set(endYear, endMonth, endDay, endHour, endMinute, 0);
			return ((end.getTimeInMillis() - start.getTimeInMillis()) / 
					60000L);
		case 13:
			start.set(startYear, startMonth, startDay, startHour, startMinute, 
					startSecond);
			end.set(endYear, endMonth, endDay, endHour, endMinute, endSecond);
			return ((end.getTimeInMillis() - start.getTimeInMillis()) / 
					1000L);
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9: } return (end.getTimeInMillis() - start.getTimeInMillis());
	}
	
	/**
	 * 根据格式和日期获取按格式显示的日期字符串
	 * @param sFormat 格式
	 * @param date    日期
	 */
	public static String getFormatDate(String sFormat, Date date)
	{
		if (date == null)
			return null;

		if ((sFormat == "yy") || (sFormat == "yyyy") || (sFormat == "yyyy-MM")||(sFormat == "yy.MM.dd")||(sFormat == "yyyy.MM.dd")||
				(sFormat == "MM") || (sFormat == "dd") || (sFormat == "MM-dd") || 
				(sFormat == "MM.dd")||(sFormat == "M/dd")||(sFormat == "MM/dd") || (sFormat == "yyyyMM") || 
				(sFormat == "yyyyMMdd") || (sFormat == "yyyy/MM") || 
				(sFormat == "yy/MM/dd") || 
				(sFormat == "yyyy/MM/dd") || 
				(sFormat == "yyyy-MM-dd") || (sFormat == "yyyy-MM-dd HH:mm:ss") || 
				(sFormat == "yyyy/MM/dd HH:mm:ss") || 
				(sFormat == "yyyyMMddHHmmss") || (sFormat == "yyyyMMddHHmmssSSS") ||
				(sFormat == "yyyy年MM月dd日") ||(sFormat == "yyyy年M月d日") || (sFormat == "yyyy年") ||  (sFormat == "yyyy-M-d H") ||
				(sFormat == "yyyy年MM月") || (sFormat == "MM月dd日") ||(sFormat == "M月d日") ||  
				(sFormat == "dd日") || (sFormat == "HH") || (sFormat == "H") || 
				(sFormat == "mm") || (sFormat == "ss")|| (sFormat == "SSS") || 
				(sFormat == "yyyy/MM/dd HH:mm")||(sFormat == "yyyy.MM.dd HH:mm")||(sFormat == "yyyy年M月d日 HH:mm")) {
			SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
			return formatter.format(date);
		}else{
			if ((sFormat == "HH:mm")) {
				sFormat = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
				return formatter.format(date).substring(11, 16);
			}
		}
		return null;
	}
	
	/**
	 * 根据格式和日期获取按格式显示的日期
	 * @param sFormat 格式
	 * @param date    日期
	 */
	public static Date getDate(String sFormat, String date)
	{
		if ((date == null) || ("".equals(date)))
			return null;

		if ((sFormat == "yy") || (sFormat == "yyyy") || 
				(sFormat == "MM") || (sFormat == "dd") || 
				(sFormat == "MM/dd") || (sFormat == "yyyyMM") || 
				(sFormat == "yyyyMMdd") || (sFormat == "yyyy/MM") || 
				(sFormat == "yy/MM/dd") || 
				(sFormat == "yyyy/MM/dd") || 
				(sFormat == "yyyy-MM-dd") || 
				(sFormat == "yyyy/MM/dd HH:mm:ss") || (sFormat == "yyyy-MM-dd HH:mm:ss") ||
				(sFormat == "yyyyMMddHHmm") || 
				(sFormat == "yyyyMMddHHmmss") || 
				(sFormat == "yyyyMMdd-HHmmss") || (sFormat == "yyyyMMddHHmmssSSS") || (sFormat == "yyyy-M-d H") ||
				(sFormat == "yyyy年MM月dd日") || 
				(sFormat == "yyyy年MM月") || (sFormat == "MM月dd日") || 
				(sFormat == "dd日") || (sFormat == "HH") ||
				(sFormat == "mm")) {
			SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
			try {
				return formatter.parse(date);
			}
			catch (Exception e) {
			}
		}else{
				if (sFormat == "HH:mm") {
					sFormat = "yyyy-MM-dd HH:mm:ss";
					SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
					try {
						return formatter.parse("1970-01-01 "+date+":00");
					}
					catch (Exception e) {
					}
				}
		}
		return null;
	}


	/**
	 *  获取指定日期的年份
	 * @param date
	 * @return int 例如2013
	 */
	public static int getYear(Date date) {
		Calendar g = Calendar.getInstance();
		g.setTime(date);
		return g.get(Calendar.YEAR);//获得年份
	}

	/**
	 *  获取当前月份
	 * @param date
	 * @return int 例如10
	 */
	public static int getMonth() {
		Calendar g = Calendar.getInstance();
		return g.get(Calendar.MONTH)+1;//获得当前月份
	}

	/**
	 *  获取当前日期   多少号
	 * @param date
	 * @return int 例如21
	 */
	public static int getDay() {
		Calendar g = Calendar.getInstance();
		return g.get(Calendar.DAY_OF_MONTH);//获得当前日期
	}

	/**
	 * 获得当前小时
	 * @return
	 */
	public static int getHour() {
		Calendar g = Calendar.getInstance();
		return g.get(Calendar.HOUR_OF_DAY);//获得当前日期
	}


	/**
	 * 根据日期所在的月有多少天
	 * @param date    日期
	 */
	public static long getMonthDayCount(Date date)
	{
		Date start = getMonthFirstDay(date);
		Date end = getMonthEndDay(date);
		return (dateDiff(3, start, end) + 1L);
	}

	/**
	 * 获取date所在的月的第一天的日期
	 * @param date 日期
	 */
	public static Date getMonthFirstDay(Date date)
	{
		String month = getFormatDate("yyyyMM", date) + "01";
		Date firstday = null;

		firstday = getDate("yyyyMMdd", month);

		return firstday;
	}

	/**
	 * 获取date所在的月的最后一天的日期
	 * @param date 日期
	 */
	public static Date getMonthEndDay(Date date)
	{
		Date endday = dateAdd(3, -1, dateAdd(
				2, 1, getMonthFirstDay(date)));
		return endday;
	}

	/**
	 * 为给定的日期添加或减去指定的时间量
	 * @param iField 日历的规则(1为多少年,2为多少月,3为多少天,10为多少小时,11为一天中的小时,12为分,13为秒)
	 * @param iValue 时间量
	 * @param date 日期
	 */
	public static Date dateAdd(int iField, int iValue, Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		switch (iField) { case 1:
			cal.add(cal.YEAR, iValue);
			break;
			case 2:
				cal.add(cal.MONTH, iValue);
				break;
			case 3:
				cal.add(cal.DATE, iValue);
				break;
			case 10:
				cal.add(cal.HOUR, iValue);
				break;
			case 11:
				cal.add(cal.HOUR_OF_DAY, iValue);
				break;
			case 12:
				cal.add(cal.MINUTE, iValue);
				break;
			case 13:
				cal.add(cal.SECOND, iValue);
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9: } return cal.getTime();
	}

}
