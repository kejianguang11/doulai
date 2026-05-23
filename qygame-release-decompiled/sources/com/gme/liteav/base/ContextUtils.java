package com.gme.liteav.base;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Process;
import android.preference.PreferenceManager;
import com.gme.liteav.base.annotations.JNINamespace;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("base::android")
public class ContextUtils {
    private static final String TAG = "ContextUtils";
    static final /* synthetic */ boolean a = !ContextUtils.class.desiredAssertionStatus();
    private static Context sApplicationContext;

    static class a {
        private static SharedPreferences a = ContextUtils.fetchAppSharedPreferences();
    }

    public static Activity activityFromContext(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static SharedPreferences fetchAppSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(sApplicationContext);
    }

    public static SharedPreferences getAppSharedPreferences() {
        return a.a;
    }

    public static AssetManager getApplicationAssets() {
        Context applicationContext = getApplicationContext();
        while (applicationContext instanceof ContextWrapper) {
            applicationContext = ((ContextWrapper) applicationContext).getBaseContext();
        }
        return applicationContext.getAssets();
    }

    public static Context getApplicationContext() {
        return sApplicationContext;
    }

    public static void initApplicationContext(Context context) {
        if (!a && sApplicationContext != null && sApplicationContext != context && ((ContextWrapper) sApplicationContext).getBaseContext() != context) {
            throw new AssertionError();
        }
        initJavaSideApplicationContext(context);
    }

    public static void initApplicationContextForTests(Context context) {
        initJavaSideApplicationContext(context);
        SharedPreferences unused = a.a = fetchAppSharedPreferences();
    }

    public static void initContextFromNative(String str) {
        try {
            Method method = Class.forName("android.app.ActivityThread").getMethod("currentActivityThread", new Class[0]);
            method.setAccessible(true);
            Object objInvoke = method.invoke(null, new Object[0]);
            Object objInvoke2 = objInvoke.getClass().getMethod("getApplication", new Class[0]).invoke(objInvoke, new Object[0]);
            if (objInvoke2 instanceof Context) {
                initApplicationContext((Context) objInvoke2);
                setDataDirectorySuffix(str);
            }
        } catch (Exception unused) {
        }
    }

    private static void initJavaSideApplicationContext(Context context) {
        if (!a && context == null) {
            throw new AssertionError();
        }
        sApplicationContext = context;
    }

    public static boolean isIsolatedProcess() {
        return Process.isIsolated();
    }

    public static void setDataDirectorySuffix(String str) {
        PathUtils.setPrivateDataDirectorySuffix(str, null);
    }
}
