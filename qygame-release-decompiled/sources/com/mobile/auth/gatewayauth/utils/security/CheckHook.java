package com.mobile.auth.gatewayauth.utils.security;

import com.ali.security.MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;

/* JADX INFO: loaded from: classes.dex */
public class CheckHook {
    private static int a;
    private static int b;

    static {
        MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963.SLoad("pns-2.13.12-LogOnlineStandardCuumRelease_alijtca_plus");
        a = -1;
        b = -1;
    }

    @SafeProtector
    public static native synchronized boolean isHookByJar();

    @SafeProtector
    public static native synchronized boolean isHookByStack();
}
