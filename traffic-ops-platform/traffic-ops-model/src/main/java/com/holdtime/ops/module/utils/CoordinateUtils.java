package com.holdtime.ops.module.utils;

import java.text.SimpleDateFormat;
import java.util.*;

public class CoordinateUtils {
    /**
     * 生成X轴坐标
     */
    public static Map<String, Integer> generateLineData(Date startDate, Date endDate) {
        Map<String, Integer> lineDataMap = new LinkedHashMap<>();
        if (endDate != null && startDate != null) {
            int interval = getMonthInterval(endDate, startDate);
            for (int i = 0; i <= interval; i++) {
                String x = dateFormat(startDate, i);
                lineDataMap.put(x, 0);
            }
        } else if (endDate != null) {
            for (int i = 12; i >= 1; i--) {
                int n = (-1 * i);
                String x = dateFormat(endDate, n);
                lineDataMap.put(x, 0);
            }
        } else if (startDate != null) {
            for (int i = 1; i <= 12; i++) {
                String x = dateFormat(startDate, i);
                lineDataMap.put(x, 0);
            }
        } else {
            Date date = new Date();
            for (int i = 12; i >= 1; i--) {
                int n = (-1 * i);
                String x = dateFormat(date, n);
                lineDataMap.put(x, 0);
            }
        }
        return lineDataMap;
    }

	/**
     * 生成X轴坐标
     */
	public static List<String> generateXList(Date startDate, Date endDate) {
        List<String> xList = new ArrayList<>();
        if (endDate != null && startDate != null) {
            int interval = getMonthInterval(endDate, startDate);
            for (int i = 0; i <= interval; i++) {
                String x = dateFormat(startDate, i);
                xList.add(x);
            }
        } else if (endDate != null) {
            for (int i = 12; i >= 1; i--) {
                int n = (-1 * i);
                String x = dateFormat(endDate, n);
                xList.add(x);
            }
        } else if (startDate != null) {
            for (int i = 1; i <= 12; i++) {
                String x = dateFormat(startDate, i);
                xList.add(x);
            }
        } else {
            Date date = new Date();
            for (int i = 12; i >= 1; i--) {
                int n = (-1 * i);
                String x = dateFormat(date, n);
                xList.add(x);
            }
        }
        return xList;
    }
    
    /**
     * 计算两个日期的间隔月份
     * @param date1 日期1
     * @param date2 日期2
     * @return 间隔月份
     */
    public static int getMonthInterval(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);

        int year = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        int month = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        int result = year * 12 + month;
        
        return result == 0 ? 1 : Math.abs(result);
    }
    
    /**
     * 日期格式化
     * @param date 日期
     * @param minuendNum 差值
     * @return 返回格式化后日期
     */
    public static String dateFormat(Date date, int minuendNum) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.MONTH, minuendNum);
        date = cl.getTime();
        return sdf.format(date);
    }

    private CoordinateUtils() {
    }
}
