package com.gme.liteav.base;

/* JADX INFO: loaded from: classes.dex */
public class JNIUtils {
    static final /* synthetic */ boolean a = !JNIUtils.class.desiredAssertionStatus();
    private static ClassLoader sJniClassLoader;
    private static Boolean sSelectiveJniRegistrationEnabled;

    public static void enableSelectiveJniRegistration() {
        if (!a && sSelectiveJniRegistrationEnabled != null) {
            throw new AssertionError();
        }
        sSelectiveJniRegistrationEnabled = Boolean.TRUE;
    }

    public static Object getClassLoader() {
        return sJniClassLoader == null ? JNIUtils.class.getClassLoader() : sJniClassLoader;
    }

    public static boolean isSelectiveJniRegistrationEnabled() {
        if (sSelectiveJniRegistrationEnabled == null) {
            sSelectiveJniRegistrationEnabled = Boolean.FALSE;
        }
        return sSelectiveJniRegistrationEnabled.booleanValue();
    }

    public static void setClassLoader(ClassLoader classLoader) {
        sJniClassLoader = classLoader;
    }
}
