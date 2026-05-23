package com.nirvana.tools.core;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class UTSharedPreferencesHelper {
    public static synchronized void clearInfo(Context context, String str, String str2) {
        try {
            context.getSharedPreferences(str, 0).edit().remove(str2).commit();
        } catch (Exception unused) {
        }
    }

    public static boolean contains(Context context, String str, String str2) {
        return context.getSharedPreferences(str, 0).contains(str2);
    }

    public static <T> T get(Context context, String str, String str2, T t) {
        try {
            if (contains(context, str, str2)) {
                String strDecode = EncodeUtil.decode(context.getSharedPreferences(str, 0).getString(str2, ""));
                if (t instanceof Integer) {
                    return (T) Integer.valueOf(strDecode);
                }
                if (t instanceof Boolean) {
                    return (T) Boolean.valueOf(strDecode);
                }
                if (t instanceof Long) {
                    return (T) Long.valueOf(strDecode);
                }
                if (t instanceof String) {
                    return (T) String.valueOf(strDecode);
                }
                throw new Exception("unsupported type");
            }
        } catch (Exception unused) {
        }
        return t;
    }

    public static <T> void put(Context context, String str, String str2, T t) {
        try {
            context.getSharedPreferences(str, 0).edit().putString(str2, EncodeUtil.encode(t.toString())).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
