package org.cboard.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static String getFirstDay() {

		// 获取当月第一天和最后一天
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// String firstday, lastday;
		// 获取前月的第一天
		Calendar cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		return format.format(cale.getTime());
	}

	public static String getLastDay() {

		// 获取当月第一天和最后一天
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// String firstday, lastday;
		// 获取前月的第一天
		Calendar cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		return format.format(cale.getTime());
	}
	
	public static String convertDate2Str(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
	/**
	 	* 给定yyyy-MM-dd hh:mm:ss格式的字符串，返回Date
	 	* @param dateString
	 	* @return
     */
    public static Date convertStr2Date(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     	* 给定yyyy-MM-dd hh:mm:ss格式的字符串，返回Date
     	* @param date
     	* @return
     */
    public static Date formatDate(Date date,String dateFormatStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormatStr);
            String date1 = sdf.format(date);
            date = sdf.parse(date1);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
                  *  功能描述:
                  * 〈增加多少分钟--几分钟后（减-用负数）〉
     * @param date  时间
     * @param addTime 加几分钟
     * @return:
     * @since:  1.0.0
     * @Author: 周金明
     * @Date:   2019/4/11 12:29
     */
    public static Date DateAddMinute(Date date,long addTime){
        Date now = date;
        long time = 60*1000*addTime;
        Date afterDate = new Date(now .getTime() + time);
        return afterDate;
    }

}
