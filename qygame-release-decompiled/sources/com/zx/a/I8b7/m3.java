package com.zx.a.I8b7;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.zx.module.annotation.Java2C;

/* JADX INFO: loaded from: classes.dex */
public class m3 {
    public static int a = Integer.MIN_VALUE;
    public static Object b;

    @Java2C.Method2C
    private static native int a();

    @Java2C.Method2C
    public static native PackageInfo a(String str, int i) throws PackageManager.NameNotFoundException;

    @Java2C.Method2C
    private static native PackageInfo a(String str, int i, int i2);

    @Java2C.Method2C
    private static native PackageInfo b(String str, int i);
}
