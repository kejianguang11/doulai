package com.zx.a.I8b7;

import android.os.Handler;
import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
public class o3 {
    public static final Handler a = new Handler(Looper.getMainLooper());

    public static final class a {
        public static final o3 a = new o3();
    }

    public boolean a() {
        return (q3.u == 1 && q3.v == 1 && q3.t == 1) || (q3.u == 0 && q3.t == 1);
    }

    public boolean b() {
        return q3.u == 1 && q3.v == -1 && q3.t == 1;
    }
}
