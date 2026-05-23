package com.zx.a.I8b7;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.zx.module.annotation.Java2C;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class d2 {
    public static int a = Integer.MIN_VALUE;
    public static Object b = null;
    public static Method c = null;
    public static Method d = null;
    public static volatile String e = "";

    public class a implements Runnable {
        @Override // java.lang.Runnable
        public void run() {
            try {
                v2.a("tb2 start!");
                d2.a(q3.a);
            } catch (Throwable th) {
                v2.a(th);
            }
        }
    }

    @Java2C.Method2C
    private static native int a();

    @Java2C.Method2C
    private static native PackageInfo a(int i, int i2);

    @Java2C.Method2C
    private static native PackageInfo a(String str, int i) throws PackageManager.NameNotFoundException;

    @Java2C.Method2C
    private static native PackageInfo a(String str, int i, int i2);

    @Java2C.Method2C
    public static native String a(Context context);

    @Java2C.Method2C
    private static native PackageInfo b(String str, int i);

    @Java2C.Method2C
    public static native void b();
}
