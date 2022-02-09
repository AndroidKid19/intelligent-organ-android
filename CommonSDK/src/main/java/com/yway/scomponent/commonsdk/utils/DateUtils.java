package com.yway.scomponent.commonsdk.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @ProjectName: intelligent-organ-android
 * @Package: com.yway.scomponent.commonsdk.utils
 * @ClassName: DateUtils
 * @Description:
 * @Author: Yuan
 * @CreateDate: 2021/12/21 11:01
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/12/21 11:01
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DateUtils {
    /**
     * 获取自定义格式时间
     *
     * @param format 格式：yyyy年、MM月、dd日、HH小时、mm分钟、ss秒
     * @return
     */
    public static String getCustomTime(String format) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(cal.getTime());
    }

    /**
     * 得到当前时间 - 小时
     *
     * @return HH:mm:ss
     */
    public static String getTimeOfHour() {
        return getCustomTime("HH");
    }

    /**
     * 得到当前时间 - 分钟
     *
     * @return HH:mm:ss
     */
    public static String getTimeOfMinutes() {
        return getCustomTime("mm");
    }


    /**
     * <p>
     * 方法名称：获取指定开始时间与结束时间之间的所有时间，以分钟为 单位
     * </p >
     * <p>
     * @param strDate 开始时间 HH:mm:ss
     * @param endDate 结束时间 HH:mm:ss
     * @param miNute 需要获取的间隔分钟 例如：间隔30分钟的
     * </p >
     *
     * @return
     * @autho zhangyadong
     * @time 2021年12月22日 下午15:36:15
     */
    public static List<String> getTimeInterval(String strDate, String endDate, Integer miNute) throws ParseException {

        List<String> strDateList = new LinkedList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(strDate));

        for (long d = cal.getTimeInMillis(); d <= sdf.parse(endDate).getTime(); d = get_D_Plaus(cal, miNute)) {
            strDateList.add(sdf.format(d));
        }
        return strDateList;

    }

    public static long get_D_Plaus(Calendar c, Integer miNute) {
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + miNute);
        return c.getTimeInMillis();
    }
}
