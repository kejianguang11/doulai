package com.mobile.auth.q;

import android.content.Context;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.utils.i;
import com.mobile.auth.l.j;

/* JADX INFO: loaded from: classes.dex */
public class e {
    public static int a(Context context, int i) {
        try {
            try {
                return Integer.parseInt(com.mobile.auth.e.a.a(context).c(context).optString("operatortype"));
            } catch (Exception e) {
                i.a(e);
                return i;
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
    }

    public static String a(Context context) {
        try {
            try {
                b.a(context);
                j.a(context);
                return j.a().b();
            } catch (Exception e) {
                e.toString();
                return null;
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }
}
