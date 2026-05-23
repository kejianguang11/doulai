package com.mobile.auth.y;

import android.util.Log;
import com.mobile.auth.gatewayauth.ExceptionProcessor;

/* JADX INFO: loaded from: classes.dex */
public final class t {
    private static boolean a = false;
    private static int b = 2;
    private static long c;
    private static int d;

    public static void a() {
        try {
            d = 0;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void a(String str) {
        try {
            b("\n" + str + "\n");
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void a(boolean z) {
        try {
            a = z;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void b() {
    }

    public static void b(String str) {
        try {
            StringBuilder sb = new StringBuilder("【");
            int i = d;
            d = i + 1;
            sb.append(i);
            sb.append("】\n时间戳:");
            sb.append(System.currentTimeMillis());
            sb.append("\n时间差:");
            sb.append(System.currentTimeMillis() - c);
            sb.append("\n数据:\n");
            sb.append(str);
            sb.append("\n\n");
            c = System.currentTimeMillis();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void c(String str) {
        try {
            if (a) {
                Log.d("UniAccount", p.b() + " " + str);
                b(str);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void d(String str) {
        try {
            if (a) {
                Log.e("UniAccount", p.b() + " " + str);
                b(str);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void e(String str) {
        try {
            Log.e("UniAccount", p.b() + " " + str);
            b(str);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }
}
