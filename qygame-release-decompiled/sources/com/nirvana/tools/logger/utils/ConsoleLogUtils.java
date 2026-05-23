package com.nirvana.tools.logger.utils;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class ConsoleLogUtils {
    private static boolean loggerEnable = true;

    public static boolean isDebug() {
        return false;
    }

    public static void logcatD(String str, String str2) {
        if (loggerEnable) {
            Log.d(str, str2);
        }
    }

    public static void logcatE(String str, String str2) {
        if (loggerEnable) {
            Log.e(str, str2);
        }
    }

    public static void logcatI(String str, String str2) {
        if (loggerEnable) {
            Log.i(str, str2);
        }
    }

    public static void logcatV(String str, String str2) {
        if (loggerEnable) {
            Log.v(str, str2);
        }
    }

    public static void logcatW(String str, String str2) {
        if (loggerEnable) {
            Log.w(str, str2);
        }
    }

    public static void setLoggerEnable(boolean z) {
        loggerEnable = z;
    }
}
