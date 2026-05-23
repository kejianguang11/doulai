package com.alipay.sdk.util;

import android.app.Activity;
import android.content.Intent;
import android.content.ServiceConnection;
import com.alipay.android.app.IAlixPay;
import com.alipay.android.app.IRemoteServiceCallback;
import com.alipay.sdk.util.l;

/* JADX INFO: loaded from: classes.dex */
public class e {
    public static final String b = "failed";
    public Activity a;
    private IAlixPay c;
    private boolean e;
    private a f;
    private final Object d = IAlixPay.class;
    private ServiceConnection g = new f(this);
    private IRemoteServiceCallback h = new g(this);

    public interface a {
        void a();

        void b();
    }

    public e(Activity activity, a aVar) {
        this.a = activity;
        this.f = aVar;
    }

    private void a() {
        this.a = null;
    }

    private void a(l.a aVar) throws InterruptedException {
        if (aVar == null || aVar.b <= 78) {
            return;
        }
        String strA = l.a();
        Intent intent = new Intent();
        intent.setClassName(strA, "com.alipay.android.app.TransProcessPayActivity");
        this.a.startActivity(intent);
        Thread.sleep(200L);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private String b(String str) {
        String strA;
        Intent intent = new Intent();
        String strA2 = l.a();
        intent.setPackage(strA2);
        intent.setAction(strA2 + ".IAlixPay");
        String strJ = l.j(this.a);
        try {
            if (!this.a.getApplicationContext().bindService(intent, this.g, 1)) {
                throw new Throwable("bindService fail");
            }
            synchronized (this.d) {
                if (this.c == null) {
                    try {
                        this.d.wait(com.alipay.sdk.data.a.b().a());
                    } catch (InterruptedException e) {
                        com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.b, com.alipay.sdk.app.statistic.c.A, e);
                    }
                }
            }
            try {
                try {
                } catch (Throwable th) {
                    com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.b, com.alipay.sdk.app.statistic.c.x, th);
                    strA = com.alipay.sdk.app.i.a();
                    try {
                        this.c.unregisterCallback(this.h);
                    } catch (Throwable unused) {
                    }
                    try {
                        this.a.getApplicationContext().unbindService(this.g);
                    } catch (Throwable unused2) {
                    }
                    this.f = null;
                    this.h = null;
                    this.g = null;
                    this.c = null;
                    if (this.e && this.a != null) {
                    }
                }
                if (this.c != null) {
                    if (this.a.getRequestedOrientation() == 0) {
                        this.a.setRequestedOrientation(1);
                        this.e = true;
                    }
                    this.c.registerCallback(this.h);
                    strA = this.c.Pay(str);
                    try {
                        this.c.unregisterCallback(this.h);
                    } catch (Throwable unused3) {
                    }
                    try {
                        this.a.getApplicationContext().unbindService(this.g);
                    } catch (Throwable unused4) {
                    }
                    this.f = null;
                    this.h = null;
                    this.g = null;
                    this.c = null;
                    if (this.e && this.a != null) {
                        this.a.setRequestedOrientation(0);
                        this.e = false;
                    }
                    return strA;
                }
                com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.b, com.alipay.sdk.app.statistic.c.u, strJ + "|" + l.j(this.a) + "|" + l.k(this.a));
                try {
                    this.c.unregisterCallback(this.h);
                } catch (Throwable unused5) {
                }
                try {
                    this.a.getApplicationContext().unbindService(this.g);
                } catch (Throwable unused6) {
                }
                this.f = null;
                this.h = null;
                this.g = null;
                this.c = null;
                if (this.e && this.a != null) {
                    this.a.setRequestedOrientation(0);
                    this.e = false;
                }
                return b;
            } finally {
            }
        } catch (Throwable th2) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.b, com.alipay.sdk.app.statistic.c.z, th2);
            return b;
        }
    }

    public final String a(String str) {
        if (l.d(this.a)) {
            return b;
        }
        try {
            l.a aVarA = l.a(this.a);
            if (aVarA.a()) {
                return b;
            }
            if (aVarA != null && aVarA.b > 78) {
                String strA = l.a();
                Intent intent = new Intent();
                intent.setClassName(strA, "com.alipay.android.app.TransProcessPayActivity");
                this.a.startActivity(intent);
                Thread.sleep(200L);
            }
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.b, com.alipay.sdk.app.statistic.c.C, th);
        }
        return b(str);
    }
}
