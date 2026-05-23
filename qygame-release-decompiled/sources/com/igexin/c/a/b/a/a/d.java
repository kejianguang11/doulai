package com.igexin.c.a.b.a.a;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.igexin.push.core.d;
import com.igexin.push.d.c;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public final class d {
    static final Object j = new Object();
    private static final String n = "GS-M";
    Socket a;
    e b;
    g c;
    b d;
    final Handler e;
    boolean f;
    protected Lock g;
    protected Condition h;
    final List<f> i;
    protected ConcurrentLinkedQueue<f> k;
    long l;
    final Comparator<f> m;
    private com.igexin.c.a.b.d o;
    private final AtomicBoolean p;

    /* JADX INFO: renamed from: com.igexin.c.a.b.a.a.d$1, reason: invalid class name */
    final class AnonymousClass1 implements com.igexin.c.a.b.a.a.a.d {
        AnonymousClass1() {
        }

        @Override // com.igexin.c.a.b.a.a.a.a
        public final void a() {
            com.igexin.c.a.c.a.a(d.n, "connect hand INTERRUPT_SUCCESS");
            d.this.e.sendEmptyMessage(j.f - 1);
        }

        @Override // com.igexin.c.a.b.a.a.a.d
        public final void a(Exception exc) {
            com.igexin.c.a.c.a.a(d.n, "connect exception = " + exc.toString());
            com.igexin.c.a.c.a.a("GS-M|c ex = " + exc.toString(), new Object[0]);
            d.this.c();
        }

        @Override // com.igexin.c.a.b.a.a.a.d
        public final void a(Socket socket) {
            Message messageObtain = Message.obtain();
            messageObtain.obj = socket;
            messageObtain.what = j.c - 1;
            d.this.e.sendMessage(messageObtain);
        }

        @Override // com.igexin.c.a.b.a.a.a.d
        public final void b() {
            d.this.e.sendEmptyMessage(j.e - 1);
        }
    }

    /* JADX INFO: renamed from: com.igexin.c.a.b.a.a.d$2, reason: invalid class name */
    final class AnonymousClass2 implements com.igexin.c.a.b.a.a.a.b {
        AnonymousClass2() {
        }

        @Override // com.igexin.c.a.b.a.a.a.a
        public final void a() {
            d.this.e.sendEmptyMessage(j.f - 1);
        }

        @Override // com.igexin.c.a.b.a.a.a.b
        public final void a(Exception exc) {
            com.igexin.c.a.c.a.a(d.n, "readTask exception = " + exc.toString());
            com.igexin.c.a.c.a.a("GS-M|r ex = " + exc.toString(), new Object[0]);
            if (exc.getMessage() == null || !exc.getMessage().equals("end of stream")) {
                d.this.c();
            } else {
                c.b.a.c();
            }
        }

        @Override // com.igexin.c.a.b.a.a.a.b
        public final void b() {
        }
    }

    /* JADX INFO: renamed from: com.igexin.c.a.b.a.a.d$3, reason: invalid class name */
    final class AnonymousClass3 implements com.igexin.c.a.b.a.a.a.c {
        AnonymousClass3() {
        }

        @Override // com.igexin.c.a.b.a.a.a.a
        public final void a() {
            d.this.e.sendEmptyMessage(j.f - 1);
        }

        @Override // com.igexin.c.a.b.a.a.a.c
        public final void a(f fVar) {
            d dVar = d.this;
            if (fVar.B <= 0 || fVar.G == null) {
                fVar.l();
                return;
            }
            fVar.z = System.currentTimeMillis();
            synchronized (d.j) {
                dVar.i.add(fVar);
                Collections.sort(dVar.i, dVar.m);
                fVar.d.getClass().getSimpleName();
                dVar.l = TimeUnit.SECONDS.toMillis(dVar.i.get(0).B);
                if (dVar.l > 0 && dVar.i.size() == 1) {
                    fVar.d.getClass().getSimpleName();
                    com.igexin.c.a.c.a.a("GS-M|add : " + fVar.toString() + " --- " + fVar.d.getClass().getName() + " set response timeout delay = " + dVar.l, new Object[0]);
                    Message messageObtain = Message.obtain();
                    messageObtain.what = j.h - 1;
                    messageObtain.obj = fVar.d.getClass().getSimpleName();
                    dVar.e.sendMessageDelayed(messageObtain, dVar.l);
                }
                dVar.i.size();
            }
        }

        @Override // com.igexin.c.a.b.a.a.a.c
        public final void a(Exception exc) {
            com.igexin.c.a.c.a.a(d.n, "writeTask exception = " + exc.toString());
            com.igexin.c.a.c.a.a("GS-M|w ex = " + exc.toString(), new Object[0]);
            d.this.c();
        }
    }

    static class a {
        private static final d a = new d(0);

        private a() {
        }
    }

    private d() {
        this.p = new AtomicBoolean(false);
        this.g = new ReentrantLock();
        this.h = this.g.newCondition();
        this.i = new ArrayList();
        this.k = new ConcurrentLinkedQueue<>();
        this.m = new Comparator<f>() { // from class: com.igexin.c.a.b.a.a.d.4
            private static int a(f fVar, f fVar2) {
                if (fVar == null) {
                    return 1;
                }
                if (fVar2 == null) {
                    return -1;
                }
                if (Build.VERSION.SDK_INT >= 19) {
                    return Long.compare(((long) fVar.B) + fVar.z, ((long) fVar2.B) + fVar2.z);
                }
                if (((long) fVar.B) + fVar.z > ((long) fVar2.B) + fVar2.z) {
                    return 1;
                }
                return ((long) fVar.B) + fVar.z < ((long) fVar2.B) + fVar2.z ? -1 : 0;
            }

            @Override // java.util.Comparator
            public final /* bridge */ /* synthetic */ int compare(f fVar, f fVar2) {
                f fVar3 = fVar;
                f fVar4 = fVar2;
                if (fVar3 == null) {
                    return 1;
                }
                if (fVar4 == null) {
                    return -1;
                }
                if (Build.VERSION.SDK_INT >= 19) {
                    return Long.compare(((long) fVar3.B) + fVar3.z, ((long) fVar4.B) + fVar4.z);
                }
                if (((long) fVar3.B) + fVar3.z > ((long) fVar4.B) + fVar4.z) {
                    return 1;
                }
                return ((long) fVar3.B) + fVar3.z < ((long) fVar4.B) + fVar4.z ? -1 : 0;
            }
        };
        this.e = d.a.a.e;
    }

    /* synthetic */ d(byte b) {
        this();
    }

    public static d a() {
        return a.a;
    }

    private void a(Object obj) {
        com.igexin.c.a.c.a.b(n, ((String) obj) + " write task response timeout");
        c();
    }

    private void b(f fVar) {
        if (fVar.B <= 0 || fVar.G == null) {
            fVar.l();
            return;
        }
        fVar.z = System.currentTimeMillis();
        synchronized (j) {
            this.i.add(fVar);
            Collections.sort(this.i, this.m);
            fVar.d.getClass().getSimpleName();
            this.l = TimeUnit.SECONDS.toMillis(this.i.get(0).B);
            if (this.l > 0 && this.i.size() == 1) {
                fVar.d.getClass().getSimpleName();
                com.igexin.c.a.c.a.a("GS-M|add : " + fVar.toString() + " --- " + fVar.d.getClass().getName() + " set response timeout delay = " + this.l, new Object[0]);
                Message messageObtain = Message.obtain();
                messageObtain.what = j.h - 1;
                messageObtain.obj = fVar.d.getClass().getSimpleName();
                this.e.sendMessageDelayed(messageObtain, this.l);
            }
            this.i.size();
        }
    }

    private void b(Socket socket) throws Exception {
        this.b = new e(new h(socket.getInputStream()), this.o);
        this.b.k = new AnonymousClass2();
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) this.b, true);
    }

    private void c(Socket socket) throws Exception {
        this.c = new g(new i(socket.getOutputStream()), this.o);
        this.c.j = new AnonymousClass3();
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) this.c, true);
    }

    private static void k() {
        com.igexin.push.core.d unused = d.a.a;
        com.igexin.push.e.a.a(j.a);
    }

    private void l() {
        j();
        if ((this.d == null && this.c == null && this.b == null) || i()) {
            b();
        } else {
            h();
        }
    }

    private void m() {
        boolean z = (this.a == null || this.a.isClosed()) ? false : true;
        if (!z && this.d == null) {
            com.igexin.c.a.c.a.a("GS-M|disconnect = true, reconnect", new Object[0]);
            this.d = new b(new AnonymousClass1());
            com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) this.d, true);
        } else {
            com.igexin.c.a.c.a.a("GS-Mstart connect, isConnected = " + z + ", ctask = " + this.d, new Object[0]);
        }
    }

    private void n() {
        if (this.c != null) {
            this.c.l = null;
            this.c = null;
        }
        if (this.b != null) {
            this.b.j = null;
            this.b = null;
        }
        this.d = null;
        this.a = null;
    }

    private void o() {
        if (!i() || this.f) {
            return;
        }
        b();
        this.f = true;
    }

    private boolean p() {
        return (this.a == null || this.a.isClosed()) ? false : true;
    }

    final void a(f fVar) {
        try {
            try {
                this.g.lock();
                this.k.offer(fVar);
                this.h.signalAll();
            } catch (Exception e) {
                com.igexin.c.a.c.a.a(e);
                try {
                    this.g.unlock();
                } catch (Exception e2) {
                    com.igexin.c.a.c.a.a(e2);
                }
            }
        } finally {
            try {
                this.g.unlock();
            } catch (Exception e3) {
                com.igexin.c.a.c.a.a(e3);
            }
        }
    }

    public final void a(com.igexin.c.a.b.d dVar) {
        this.o = dVar;
        if (this.b != null) {
            this.b.l = dVar;
        }
        if (this.c != null) {
            this.c.k = dVar;
        }
    }

    public final void a(String str) {
        boolean z;
        long jCurrentTimeMillis = System.currentTimeMillis();
        synchronized (j) {
            com.igexin.c.a.c.a.a("GS-M|" + str + " -- resp,no timeout", new Object[0]);
            Iterator<f> it = this.i.iterator();
            String simpleName = null;
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                f next = it.next();
                if (next.G.a(jCurrentTimeMillis, next)) {
                    next.l();
                    next.G.c();
                    it.remove();
                    z = true;
                    break;
                }
                long jB = next.G.b(jCurrentTimeMillis, next);
                if (this.l < 0 || this.l > jB) {
                    this.l = jB;
                    simpleName = next.d.getClass().getSimpleName();
                }
            }
            this.e.removeMessages(j.h - 1);
            if (z) {
                com.igexin.c.a.c.a.a("GS-M|timeout", new Object[0]);
                f();
                return;
            }
            if (this.i.size() > 0) {
                f fVar = this.i.get(0);
                fVar.l();
                com.igexin.c.a.b.e.a().a((Object) fVar);
                this.i.remove(fVar);
                fVar.d.getClass().getSimpleName();
                com.igexin.c.a.c.a.a("GS-M|remove : " + fVar.toString() + " -- " + fVar.d.getClass().getSimpleName(), new Object[0]);
            }
            int size = this.i.size();
            com.igexin.c.a.c.a.a("GS-M|r, size = ".concat(String.valueOf(size)), new Object[0]);
            if (size > 0 && this.l > 0 && !TextUtils.isEmpty(simpleName)) {
                com.igexin.c.a.c.a.a("GS-M|" + simpleName + " , set  response timeout = " + this.l, new Object[0]);
                Message messageObtain = Message.obtain();
                messageObtain.what = j.h - 1;
                messageObtain.obj = simpleName;
                this.e.sendMessageDelayed(messageObtain, this.l);
            }
        }
    }

    final void a(Socket socket) {
        try {
            if (this.d.g()) {
                return;
            }
            this.a = socket;
            this.b = new e(new h(socket.getInputStream()), this.o);
            this.b.k = new AnonymousClass2();
            com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) this.b, true);
            this.c = new g(new i(socket.getOutputStream()), this.o);
            this.c.j = new AnonymousClass3();
            com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) this.c, true);
        } catch (Exception e) {
            com.igexin.c.a.c.a.a("GS-M|" + e.toString(), new Object[0]);
            com.igexin.c.a.c.a.a(n, "tcpConnect exception =" + e.toString());
            c();
        }
    }

    public final void b() {
        this.p.set(false);
        com.igexin.push.core.d unused = d.a.a;
        com.igexin.push.e.a.a(j.b);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void c() {
        if (this.p.getAndSet(true)) {
            return;
        }
        this.e.sendEmptyMessage(j.a - 1);
    }

    public final synchronized void d() {
        com.igexin.c.a.c.a.a(n, "disConnect, hand TCP_DISCONNECT");
        this.e.sendEmptyMessage(j.g - 1);
    }

    final synchronized void e() {
        this.e.sendEmptyMessage(j.d - 1);
        this.f = false;
    }

    public final synchronized void f() {
        com.igexin.c.a.c.a.a(n, "alarm timeout disconnect");
        com.igexin.c.a.c.a.a("GS-M|alarm timeout disconnect", new Object[0]);
        c();
    }

    public final synchronized void g() {
        com.igexin.c.a.c.a.a(n, "redirect disconnect");
        com.igexin.c.a.c.a.a("GS-M|redirect disconnect", new Object[0]);
        c();
    }

    final void h() {
        com.igexin.c.a.c.a.a(n, "disconnect");
        com.igexin.c.a.c.a.a("GS-M|disconnect", new Object[0]);
        if (this.d != null) {
            this.d.c_();
        }
        if (this.c != null) {
            this.c.c_();
        }
        if (this.b != null) {
            this.b.c_();
        }
        if (this.a != null) {
            try {
                if (this.a.isClosed()) {
                    return;
                }
                this.a.close();
            } catch (Exception e) {
                com.igexin.c.a.c.a.a(e);
            }
        }
    }

    final boolean i() {
        if (this.d != null && !this.d.f) {
            return false;
        }
        if (this.b != null && !this.b.f) {
            return false;
        }
        if (this.c != null && !this.c.f) {
            return false;
        }
        n();
        return true;
    }

    final void j() {
        this.e.removeMessages(j.h - 1);
        com.igexin.c.a.b.e.a().d();
        com.igexin.c.a.c.a.a("GS-M|cancel alarm", new Object[0]);
        synchronized (j) {
            if (!this.i.isEmpty()) {
                Iterator<f> it = this.i.iterator();
                while (it.hasNext()) {
                    it.next().l();
                }
                this.i.clear();
            }
        }
        if (this.k.isEmpty()) {
            return;
        }
        Iterator<f> it2 = this.k.iterator();
        while (it2.hasNext()) {
            it2.next().l();
        }
        this.k.clear();
    }
}
