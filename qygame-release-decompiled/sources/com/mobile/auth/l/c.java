package com.mobile.auth.l;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class c {
    private static final c a = new c();
    private static boolean b = false;

    public static void a(String str, String str2) {
        if (b) {
            Log.e("CMCC-SDK:" + str, "" + str2);
        }
    }

    public static void a(boolean z) {
        b = z;
    }

    public static void b(String str, String str2) {
        if (b) {
            Log.d("CMCC-SDK:" + str, "" + str2);
        }
    }
}
