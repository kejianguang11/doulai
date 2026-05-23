package com.mobile.auth.gatewayauth.utils.security;

import android.content.Context;
import com.ali.security.MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;

/* JADX INFO: loaded from: classes.dex */
@SafeProtector
public class EmulatorDetector {
    private static final String TAG = "EmulatorDetector";
    private static int rating;

    static {
        MinosSecurityLoad_fb60a2d6833db14c01f3c6e9647b5963.SLoad("pns-2.13.12-LogOnlineStandardCuumRelease_alijtca_plus");
        rating = -1;
    }

    private static final native String getProp(Context context, String str);

    public static native boolean isEmulator(Context context);

    private static native boolean isEmulatorAbsoluly(Context context);

    private static final native boolean mayOnEmulatorViaQEMU(Context context);
}
