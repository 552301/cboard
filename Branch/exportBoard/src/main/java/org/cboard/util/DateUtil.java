package org.cboard.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
	public static String getFirstDay() {
		
		// 获取当月第一天和最后一天  
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        //String firstday, lastday;  
        // 获取前月的第一天  
        Calendar cale = Calendar.getInstance();  
        cale.add(Calendar.MONTH, 0);  
        cale.set(Calendar.DAY_OF_MONTH, 1);  
        return format.format(cale.getTime());  
	}
	
public static String getLastDay() {
		
		// 获取当月第一天和最后一天  
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        //String firstday, lastday;  
        // 获取前月的第一天  
        Calendar cale = Calendar.getInstance();  
        cale.add(Calendar.MONTH, 1);  
        cale.set(Calendar.DAY_OF_MONTH, 0);  
        return format.format(cale.getTime());  
	}

}
