package com.bby.youlianwallet.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fanyy on 2018/4/8.
 */

public class TimeUtil {
    private static SimpleDateFormat format1=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
    private static SimpleDateFormat format2=new SimpleDateFormat("yy/MM/dd HH:mm");
    private static SimpleDateFormat format3=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//等价于now.toLocaleString()
    private static SimpleDateFormat format4=new SimpleDateFormat("dd/MM/yyyy");

    /**
     * 将format3格式的字符串装换为format4格式的字符串
     * @param timeString
     * @return
     */
    public static String getFormatTime(String timeString){
        try {
            Date date = format3.parse(timeString);
            return format4.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "日期格式异常";
    }
}
