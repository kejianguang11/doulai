package com.nirvana.tools.logger.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class CommonUtils {
    public static String getLimitIntervalIndex(int i) {
        if (i == 0) {
            return getTodayData();
        }
        return getTodayData() + "-" + i + "-" + (Calendar.getInstance().get(11) / i);
    }

    public static String getTodayData() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}
