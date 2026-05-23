package com.igexin.c.a.b.a.a;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.igexin.c.a.b.a.a.d.AnonymousClass1;
import com.igexin.push.core.d;
import java.net.Socket;

/* JADX INFO: loaded from: classes.dex */
public final class c extends Handler {

    /* JADX INFO: renamed from: com.igexin.c.a.b.a.a.c$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[j.a().length];

        static {
            try {
                a[j.d - 1] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[j.e - 1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[j.c - 1] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[j.f - 1] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[j.g - 1] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[j.a - 1] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[j.h - 1] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public c(Looper looper) {
        super(looper);
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        try {
            switch (AnonymousClass1.a[j.a()[message.what] - 1]) {
                case 1:
                    d dVarA = d.a();
                    boolean z = (dVarA.a == null || dVarA.a.isClosed()) ? false : true;
                    if (!z && dVarA.d == null) {
                        com.igexin.c.a.c.a.a("GS-M|disconnect = true, reconnect", new Object[0]);
                        dVarA.d = new b(dVarA.new AnonymousClass1());
                        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) dVarA.d, true);
                    }
                    com.igexin.c.a.c.a.a("GS-Mstart connect, isConnected = " + z + ", ctask = " + dVarA.d, new Object[0]);
                    break;
                case 3:
                    d.a().a((Socket) message.obj);
                    break;
                case 4:
                    d dVarA2 = d.a();
                    if (dVarA2.i() && !dVarA2.f) {
                        dVarA2.b();
                        dVarA2.f = true;
                        break;
                    }
                    break;
                case 5:
                    d dVarA3 = d.a();
                    dVarA3.j();
                    if ((dVarA3.d == null && dVarA3.c == null && dVarA3.b == null) || dVarA3.i()) {
                        dVarA3.b();
                    } else {
                        dVarA3.h();
                    }
                    break;
                case 6:
                    d.a();
                    com.igexin.push.core.d unused = d.a.a;
                    com.igexin.push.e.a.a(j.a);
                    break;
                case 7:
                    d dVarA4 = d.a();
                    com.igexin.c.a.c.a.b("GS-M", ((String) message.obj) + " write task response timeout");
                    dVarA4.c();
                    break;
            }
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
    }
}
