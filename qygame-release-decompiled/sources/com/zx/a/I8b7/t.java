package com.zx.a.I8b7;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class t {
    public static boolean a = true;

    public static void a(String str) {
        if (a) {
            StringBuilder sbA = j3.a("--- ");
            sbA.append(str == null ? "null" : str);
            sbA.append(" ---");
            Log.d("zx-DebugMode", sbA.toString());
        }
        if (str == null) {
            str = "null";
        }
        v2.a(str);
    }

    public static void b(String str) {
        if (a) {
            StringBuilder sbA = j3.a("--- ");
            if (str == null) {
                str = "null";
            }
            sbA.append(str);
            sbA.append(" ---");
            Log.e("zx-DebugMode", sbA.toString());
        }
    }
}
