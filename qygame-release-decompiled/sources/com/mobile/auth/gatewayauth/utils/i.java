package com.mobile.auth.gatewayauth.utils;

import android.text.TextUtils;
import android.util.Log;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.core.ExecutorManager;

/* JADX INFO: loaded from: classes.dex */
public class i {
    private static boolean a = false;
    private static boolean b = true;

    public static void a(String str) {
        try {
            if (!a || TextUtils.isEmpty(str)) {
                return;
            }
            Log.d("AuthSDK", str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void a(Throwable th) {
        try {
            if (!a || th == null) {
                return;
            }
            Log.e("AuthSDK", ExecutorManager.getErrorInfoFromException(th));
        } catch (Throwable th2) {
            try {
                ExceptionProcessor.processException(th2);
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
            }
        }
    }

    public static void a(boolean z) {
        try {
            a = z;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static boolean a() {
        try {
            return a;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public static void b(String str) {
        try {
            if (!a || TextUtils.isEmpty(str)) {
                return;
            }
            Log.w("AuthSDK", str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void b(boolean z) {
        try {
            b = z;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static boolean b() {
        try {
            return b;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public static void c(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            Log.e("AuthSDK", str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void d(String str) {
        try {
            if (!a || TextUtils.isEmpty(str)) {
                return;
            }
            Log.i("AuthSDK", str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
