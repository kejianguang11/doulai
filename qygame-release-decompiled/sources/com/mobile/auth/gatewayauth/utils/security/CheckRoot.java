package com.mobile.auth.gatewayauth.utils.security;

import com.ali.security.MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;

/* JADX INFO: loaded from: classes.dex */
@SafeProtector
public class CheckRoot {
    private static String LOG_TAG;

    static {
        MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963.SLoad("pns-2.13.12-LogOnlineStandardCuumRelease_alijtca_plus");
        LOG_TAG = "CheckRoot";
    }

    private static native boolean checkDeviceDebuggable();

    private static native boolean checkRootPathSU();

    private static native boolean checkSuperuserApk();

    public static native String isDeviceRooted();
}
