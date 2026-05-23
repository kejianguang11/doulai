package com.igexin.push.c;

import android.annotation.SuppressLint;
import android.os.Build;
import com.igexin.push.c.b;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes.dex */
public final class e {
    private static final String e = "DT_DetectRunTask";
    private static final long f = 60;
    Future<d> a;
    d b;
    i c;
    boolean d;

    /* JADX INFO: renamed from: com.igexin.push.c.e$1, reason: invalid class name */
    final class AnonymousClass1 implements Callable<d> {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        @Override // java.util.concurrent.Callable
        @SuppressLint({"NewApi"})
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public d call() throws Throwable {
            Socket socket;
            Thread.currentThread().hashCode();
            com.igexin.b.a.a().a.getActiveCount();
            if (!Thread.currentThread().isInterrupted()) {
                Socket socket2 = null;
                try {
                    try {
                    } catch (Exception e) {
                        e = e;
                    }
                    if (Thread.currentThread().isInterrupted()) {
                        com.igexin.c.a.c.a.a(e.e, Thread.currentThread().getName() + " is interrupted ######");
                        Thread.currentThread().hashCode();
                        com.igexin.b.a.a().a.getActiveCount();
                        return null;
                    }
                    synchronized (i.class) {
                    }
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    String[] strArrA = com.igexin.c.a.b.g.a(e.this.b.a());
                    socket = new Socket();
                    try {
                        socket.connect(new InetSocketAddress(strArrA[1], e.this.b.b), b.b);
                        long jCurrentTimeMillis2 = System.currentTimeMillis();
                        String strA = e.a(socket.getInetAddress());
                        e.this.b.a("socket://" + strA + ":" + e.this.b.b, jCurrentTimeMillis2 - jCurrentTimeMillis, jCurrentTimeMillis2);
                        e.this.c();
                        com.igexin.c.a.c.a.a("DT_DetectRunTask|detect " + e.this.c() + "|time = " + e.this.b.c(), new Object[0]);
                        synchronized (i.class) {
                            if (e.this.c != null && !Thread.currentThread().isInterrupted()) {
                                e.this.c.a(b.a.a, e.this.b);
                            }
                            if (!socket.isClosed()) {
                                try {
                                    socket.close();
                                } catch (Exception e2) {
                                    e = e2;
                                    com.igexin.c.a.c.a.a(e);
                                }
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                        socket2 = socket;
                        com.igexin.c.a.c.a.b(e.e, "|detect " + e.this.c() + "thread -->" + e.getMessage());
                        synchronized (i.class) {
                            if (e.this.c != null) {
                                e.this.b.b();
                                e.this.c.a(b.a.c, e.this.b);
                            }
                            if (socket2 != null && !socket2.isClosed()) {
                                try {
                                    socket2.close();
                                } catch (Exception e4) {
                                    e = e4;
                                    com.igexin.c.a.c.a.a(e);
                                }
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (socket != null) {
                            try {
                                socket.close();
                            } catch (Exception e5) {
                                com.igexin.c.a.c.a.a(e5);
                            }
                        }
                        Thread.currentThread().hashCode();
                        com.igexin.b.a.a().a.getActiveCount();
                        throw th;
                    }
                    Thread.currentThread().hashCode();
                    com.igexin.b.a.a().a.getActiveCount();
                } catch (Throwable th2) {
                    th = th2;
                    socket = socket2;
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                    Thread.currentThread().hashCode();
                    com.igexin.b.a.a().a.getActiveCount();
                    throw th;
                }
            }
            return e.this.b;
        }
    }

    public static String a(InetAddress inetAddress) throws NoSuchMethodException {
        Class<?> cls;
        try {
            cls = Class.forName("java.net.InetAddress");
        } catch (Throwable unused) {
        }
        if (Build.VERSION.SDK_INT >= 23) {
            Method declaredMethod = cls.getDeclaredMethod("holder", new Class[0]);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(inetAddress, new Object[0]);
            Field declaredField = objInvoke.getClass().getDeclaredField("address");
            declaredField.setAccessible(true);
            int iIntValue = ((Integer) declaredField.get(objInvoke)).intValue();
            String str = ((iIntValue >>> 24) & 255) + "." + ((iIntValue >>> 16) & 255) + "." + ((iIntValue >>> 8) & 255) + "." + (iIntValue & 255);
            com.igexin.c.a.c.a.b(e, "i new Str: ".concat(String.valueOf(str)));
            return str;
        }
        Field declaredField2 = cls.getDeclaredField("ipaddress");
        declaredField2.setAccessible(true);
        byte[] bArr = (byte[]) declaredField2.get(inetAddress);
        if (bArr.length >= 4) {
            String str2 = (bArr[3] & 255) + "." + (bArr[2] & 255) + "." + (bArr[1] & 255) + "." + (bArr[0] & 255);
            com.igexin.c.a.c.a.b(e, "i old Str: ".concat(String.valueOf(str2)));
            return str2;
        }
        if (!com.igexin.push.config.d.Z) {
            throw new NoSuchMethodException("can't get ad by new method");
        }
        com.igexin.c.a.c.a.b(e, "get ad by original method");
        return inetAddress.getHostAddress();
    }

    private void a(d dVar) {
        this.b = dVar;
    }

    private void a(boolean z) {
        this.d = z;
    }

    private d d() {
        return this.b;
    }

    private void e() {
        synchronized (i.class) {
            if (this.c != null) {
                this.a = com.igexin.b.a.a().a.submit(new AnonymousClass1());
            }
        }
    }

    private void f() {
        this.a = com.igexin.b.a.a().a.submit(new AnonymousClass1());
    }

    private void g() {
        try {
            if (this.a == null || this.a.isCancelled() || this.a.isDone()) {
                return;
            }
            this.a.cancel(true);
            this.a = null;
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
        }
    }

    public final void a() {
        c();
        com.igexin.c.a.c.a.a("DT_DetectRunTask|stop " + c() + " task", new Object[0]);
        g();
    }

    public final void a(i iVar) {
        synchronized (i.class) {
            this.c = iVar;
        }
    }

    public final void b() {
        a((i) null);
        g();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String c() {
        return this.b.a() + "|" + this.b.a;
    }
}
