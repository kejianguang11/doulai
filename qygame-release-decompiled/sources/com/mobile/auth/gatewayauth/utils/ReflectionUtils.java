package com.mobile.auth.gatewayauth.utils;

import android.app.Activity;
import android.app.Application;
import com.ali.security.MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;

/* JADX INFO: loaded from: classes.dex */
@SafeProtector
public class ReflectionUtils {
    private static volatile Application sApplication;

    static {
        MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963.SLoad("pns-2.13.12-LogOnlineStandardCuumRelease_alijtca_plus");
        sApplication = null;
    }

    public static native Activity getActivity();

    public static native Application getApplication();
}
