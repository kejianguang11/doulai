package com.mobile.auth.gatewayauth.utils.security;

import android.content.Context;
import com.ali.security.MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;

/* JADX INFO: loaded from: classes.dex */
public class PackageUtils {
    private static String a;
    private static String b;
    private static String c;
    private static String d;
    private static final char[] e;

    static {
        MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963.SLoad("pns-2.13.12-LogOnlineStandardCuumRelease_alijtca_plus");
        a = null;
        b = null;
        c = null;
        d = null;
        e = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    }

    @SafeProtector
    public static native synchronized String getPackageName(Context context);

    @SafeProtector
    public static native synchronized String getSign(Context context);

    @SafeProtector
    public static native synchronized String getVersionName(Context context);

    @SafeProtector
    public static native String hexdigest(byte[] bArr);

    @SafeProtector
    private static native void setupAppInfo(Context context);
}
