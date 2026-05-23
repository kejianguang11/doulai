package com.zx.a.I8b7;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class u {
    public static float a;

    public static int a(Context context, float f) {
        if (a == 0.0f) {
            a = context.getResources().getDisplayMetrics().density;
        }
        return (int) ((f * a) + 0.5f);
    }
}
