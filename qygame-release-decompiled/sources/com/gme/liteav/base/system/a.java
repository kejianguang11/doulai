package com.gme.liteav.base.system;

import android.content.Context;
import android.content.pm.PackageInfo;
import com.gme.liteav.base.ContextUtils;

/* JADX INFO: loaded from: classes.dex */
final class a {
    private static final com.gme.liteav.base.util.k<PackageInfo> a = new com.gme.liteav.base.util.k<>(b.a());

    public static String a() {
        PackageInfo packageInfoA = a.a();
        return packageInfoA == null ? "" : packageInfoA.packageName;
    }

    public static String b() {
        PackageInfo packageInfoA;
        Context applicationContext = ContextUtils.getApplicationContext();
        return (applicationContext == null || (packageInfoA = a.a()) == null) ? "" : applicationContext.getPackageManager().getApplicationLabel(packageInfoA.applicationInfo).toString();
    }

    public static String c() {
        PackageInfo packageInfoA = a.a();
        return packageInfoA == null ? "" : packageInfoA.versionName;
    }

    static /* synthetic */ PackageInfo d() throws Exception {
        Context applicationContext = ContextUtils.getApplicationContext();
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 0);
    }
}
