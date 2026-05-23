package com.loc;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class y {
    private static y a;
    private final Context b;
    private final String c = af.a(x.c("RYW1hcF9kZXZpY2VfYWRpdQ"));

    private y(Context context) {
        this.b = context.getApplicationContext();
    }

    public static y a(Context context) {
        if (a == null) {
            synchronized (y.class) {
                if (a == null) {
                    a = new y(context);
                }
            }
        }
        return a;
    }

    public final synchronized void a() {
        try {
            if (o.c() == null) {
                o.a(ac.a());
            }
        } catch (Throwable unused) {
        }
    }

    public final void a(String str) {
        z.a(this.b).a(this.c);
        z.a(this.b).b(str);
    }
}
