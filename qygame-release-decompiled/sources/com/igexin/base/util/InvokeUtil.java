package com.igexin.base.util;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class InvokeUtil {
    private static Context appContext;

    public static Context findAppContext() {
        if (appContext != null) {
            return appContext;
        }
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Context context = (Context) cls.getMethod("getApplication", new Class[0]).invoke(cls.getDeclaredMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]), new Object[0]);
            appContext = context;
            return context;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }
}
