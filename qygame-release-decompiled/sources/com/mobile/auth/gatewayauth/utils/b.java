package com.mobile.auth.gatewayauth.utils;

import android.content.Context;
import android.graphics.Typeface;
import com.mobile.auth.gatewayauth.ExceptionProcessor;

/* JADX INFO: loaded from: classes.dex */
public class b {
    public static Typeface a(Context context, String str) {
        try {
            return Typeface.createFromAsset(context.getAssets(), str);
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
