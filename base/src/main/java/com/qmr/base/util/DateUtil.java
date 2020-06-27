package com.qmr.base.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtil {

    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    public static String YYYYMMDD(long time) {
        String date = df.format(time);
        return date;
    }
}
