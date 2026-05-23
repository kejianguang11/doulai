package com.mobile.auth.gatewayauth.utils;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class j {
    private static Pattern a = Pattern.compile("^[-\\+]?[\\d]*$");

    public static String a(int i) {
        try {
            return Integer.toHexString(i);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public static String a(long j) {
        try {
            try {
                return new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss.SSS").format(new Date(j));
            } catch (Exception unused) {
                return String.valueOf(j);
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public static String a(Long l, int i) {
        try {
            return String.format("%0" + i + "x", Long.valueOf(l.longValue()));
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }
}
