package com.loc;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class bq {
    static int a = 1000;
    static boolean b = false;
    static int c = 20;
    static int d = 0;
    private static WeakReference<bn> e = null;
    private static int f = 10;

    static class a extends ck {
        private int a;
        private Context b;
        private bp c;

        a(Context context, int i) {
            this.b = context;
            this.a = i;
        }

        a(Context context, bp bpVar) {
            this(context, 1);
            this.c = bpVar;
        }

        @Override // com.loc.ck
        public final void a() {
            if (this.a == 1) {
                try {
                    synchronized (bq.class) {
                        String string = Long.toString(System.currentTimeMillis());
                        bn bnVarA = bt.a(bq.e);
                        bt.a(this.b, bnVarA, al.i, bq.a, 2097152, "6");
                        if (bnVarA.e == null) {
                            bnVarA.e = new ay(new ba(new bb(new ba())));
                        }
                        bo.a(string, this.c.a(), bnVarA);
                    }
                    return;
                } catch (Throwable th) {
                    an.b(th, "ofm", "aple");
                    return;
                }
            }
            if (this.a == 2) {
                try {
                    bn bnVarA2 = bt.a(bq.e);
                    bt.a(this.b, bnVarA2, al.i, bq.a, 2097152, "6");
                    bnVarA2.h = 14400000;
                    if (bnVarA2.g == null) {
                        bnVarA2.g = new bx(new bw(this.b, new cb(), new ay(new ba(new bb())), new String(ag.a(10)), l.f(this.b), o.v(this.b), o.k(this.b), o.h(this.b), o.a(), Build.MANUFACTURER, Build.DEVICE, o.y(this.b), l.c(this.b), Build.MODEL, l.d(this.b), l.b(this.b), o.g(this.b), o.a(this.b), String.valueOf(Build.VERSION.SDK_INT)));
                    }
                    if (TextUtils.isEmpty(bnVarA2.i)) {
                        bnVarA2.i = "fKey";
                    }
                    bnVarA2.f = new cf(this.b, bnVarA2.h, bnVarA2.i, new cd(this.b, bq.b, bq.f * 1024, bq.c * 1024, "offLocKey", bq.d * 1024));
                    bo.a(bnVarA2);
                } catch (Throwable th2) {
                    an.b(th2, "ofm", "uold");
                }
            }
        }
    }

    public static synchronized void a(int i, boolean z, int i2, int i3) {
        a = i;
        b = z;
        if (i2 < 10 || i2 > 100) {
            i2 = 20;
        }
        c = i2;
        if (i2 / 5 > f) {
            f = c / 5;
        }
        d = i3;
    }

    public static void a(Context context) {
        cj.a().b(new a(context, 2));
    }

    public static synchronized void a(bp bpVar, Context context) {
        cj.a().b(new a(context, bpVar));
    }
}
