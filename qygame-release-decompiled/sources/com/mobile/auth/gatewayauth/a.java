package com.mobile.auth.gatewayauth;

import android.content.res.Configuration;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes.dex */
public class a {
    public static float a() {
        try {
            try {
                try {
                    Configuration configuration = new Configuration();
                    Class<?> cls = Class.forName("android.app.ActivityManagerNative");
                    Object objInvoke = cls.getMethod("getDefault", new Class[0]).invoke(cls, new Object[0]);
                    configuration.updateFrom((Configuration) objInvoke.getClass().getMethod("getConfiguration", new Class[0]).invoke(objInvoke, new Object[0]));
                    return configuration.fontScale;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return 1.0f;
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                    return 1.0f;
                }
            } catch (NoSuchMethodException e3) {
                e3.printStackTrace();
                return 1.0f;
            } catch (InvocationTargetException e4) {
                e4.printStackTrace();
                return 1.0f;
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1.0f;
        }
    }
}
