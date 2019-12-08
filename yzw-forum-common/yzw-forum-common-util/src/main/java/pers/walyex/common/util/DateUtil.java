package pers.walyex.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期公用类
 *
 * @author Waldron Ye
 * @date 2019/5/30 21:56
 */
public class DateUtil {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String DEFAULT_DATE_FORMAT = YYYY_MM_DD;

    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyyMMddHHmmssSSS";

    /**
     * 字符串按默认格式转日期
     *
     * @param strDate 日期字符串
     * @return 日期
     * @throws ParseException 转换异常
     */
    public static Date parse(String strDate) throws ParseException {
        return parse(strDate, DEFAULT_DATE_FORMAT);
    }

    /**
     * 字符串按指定格式转日期
     *
     * @param strDate 日期字符串
     * @param pattern 指定的日期转换格式
     * @return 日期
     * @throws ParseException
     */
    public static Date parse(String strDate, String pattern) throws ParseException {
        return createDateFormat(pattern).parse(strDate);
    }

    /**
     * 日期按默认格式转字符串
     *
     * @param date 这个日期值将格式化为一个日期字符串
     * @return 日期字符串
     */
    public static String format(Date date) {
        return format(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * 日期按指定格式转字符串
     *
     * @param date    这个日期值将格式化为一个日期字符串
     * @param pattern 指定的日期转换格式
     * @return 日期字符串
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else {
            return createDateFormat(pattern).format(date);
        }
    }

    /**
     * 创建日期格式化实现类
     *
     * @param pattern 指定的日期转换格式
     * @return 日期格式化实现类
     */
    private static DateFormat createDateFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 指定日期增加（年）
     *
     * @param date   指定的一个原始日期
     * @param amount 数值增量
     * @return 新日期
     */
    public static Date addYear(Date date, int amount) {
        return add(date, Calendar.DAY_OF_YEAR, amount);
    }

    /**
     * 指定日期增加（月）
     *
     * @param date   指定的一个原始日期
     * @param amount 数值增量
     * @return 新日期
     */
    public static Date addMonth(Date date, int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    /**
     * 指定日期增加（周）
     *
     * @param date   指定的一个原始日期
     * @param amount 数值增量
     * @return 新日期
     */
    public static Date addWeek(Date date, int amount) {
        return add(date, Calendar.WEEK_OF_YEAR, amount);
    }

    /**
     * 指定日期增加（天）
     *
     * @param date   指定的一个原始日期
     * @param amount 数值增量
     * @return 新日期
     */
    public static Date addDay(Date date, int amount) {
        return add(date, Calendar.DATE, amount);
    }

    /**
     * 指定日期减少（天）
     *
     * @param date   指定的一个原始日期
     * @param amount 数值减量
     * @return 新日期
     */
    public static Date reduceDay(Date date, int amount) {
        return add(date, Calendar.DATE, -amount);
    }

    /**
     * 指定日期增加数量（年，月，日，小时，分钟，秒，毫秒）
     *
     * @param date   指定的一个原始日期
     * @param field  日历类Calendar字段
     * @param amount 数值增量
     * @return
     */
    public static Date add(Date date, int field, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else {
            Calendar c = createCalendar(date);
            c.add(field, amount);
            return c.getTime();
        }
    }

    /**
     * 获取日期是一年中的第几周
     *
     * @param date 指定的一个原始日期
     * @return
     */
    public static int getWeekOfYear(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else {
            Calendar calendar = createCalendar(date);
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            return calendar.get(Calendar.WEEK_OF_YEAR);
        }
    }

    /**
     * 获取日期是星期几(0~6,0为星期日)
     *
     * @param date 指定的一个原始日期
     * @return 星期几
     */
    public static int getWeekOfDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else {
            return createCalendar(date).get(Calendar.DAY_OF_WEEK) - 1;
        }
    }

    /**
     * 创建日历类
     *
     * @param date 指定日期
     * @return
     */
    private static Calendar createCalendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }
}
