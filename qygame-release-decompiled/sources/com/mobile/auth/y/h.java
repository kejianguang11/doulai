package com.mobile.auth.y;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import com.mobile.auth.gatewayauth.ExceptionProcessor;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"ApplySharedPref"})
public final class h {
    public static String a(Context context, String str) {
        try {
            try {
                return context.getSharedPreferences("cuAuthCacheName", 0).getString(str, "");
            } catch (Exception e) {
                t.d(e.getMessage());
                return "";
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static void a(Context context, String str, Long l) {
        try {
            try {
                SharedPreferences.Editor editorEdit = context.getSharedPreferences("cuAuthCacheName", 0).edit();
                editorEdit.putLong(str, l.longValue());
                editorEdit.commit();
            } catch (Exception e) {
                t.d(e.getMessage());
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static boolean a(Context context, String str, String str2) {
        try {
            try {
                SharedPreferences.Editor editorEdit = context.getSharedPreferences("cuAuthCacheName", 0).edit();
                editorEdit.putString(str, str2);
                return editorEdit.commit();
            } catch (Exception e) {
                t.d(e.getMessage());
                return false;
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public static Long b(Context context, String str) {
        long j;
        try {
            try {
                j = context.getSharedPreferences("cuAuthCacheName", 0).getLong(str, 0L);
            } catch (Exception e) {
                t.d(e.getMessage());
                j = 0;
            }
            return Long.valueOf(j);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }
}
