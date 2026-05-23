package com.igexin.push.core.i.a;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class h {
    final com.igexin.push.core.i.a.d a;
    final List<b> b;
    public boolean c;
    public boolean d;
    a e;
    boolean f;
    a g;
    Bitmap h;
    public a i;
    int j;
    int k;
    int l;
    private final Handler m;
    private boolean n;
    private d o;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static class a {
        final int a;
        Bitmap b;
        private final Handler c;
        private final long d;

        a(Handler handler, int i, long j) {
            this.c = handler;
            this.a = i;
            this.d = j;
        }

        private Bitmap a() {
            return this.b;
        }

        private void b() {
            this.b = null;
        }

        public final void a(Bitmap bitmap) {
            this.b = bitmap;
            this.c.sendMessageAtTime(this.c.obtainMessage(1, this), this.d);
        }
    }

    public interface b {
        void b();
    }

    class c implements Handler.Callback {
        static final int a = 1;
        static final int b = 2;

        c() {
        }

        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            if (message.what != 1) {
                int i = message.what;
                return false;
            }
            try {
                h.this.a((a) message.obj);
            } catch (Throwable th) {
                com.igexin.c.a.c.a.a(th);
            }
            return true;
        }
    }

    interface d {
        void a();
    }

    h(com.igexin.push.core.i.a.d dVar, Bitmap bitmap) {
        this(dVar, bitmap, (byte) 0);
    }

    private h(com.igexin.push.core.i.a.d dVar, Bitmap bitmap, byte b2) {
        this.b = new ArrayList();
        this.m = new Handler(Looper.getMainLooper(), new c());
        this.a = dVar;
        this.h = (Bitmap) k.a(bitmap);
        this.j = k.a(bitmap);
        this.k = bitmap.getWidth();
        this.l = bitmap.getHeight();
    }

    private void a(Bitmap bitmap) {
        this.h = (Bitmap) k.a(bitmap);
        this.j = k.a(bitmap);
        this.k = bitmap.getWidth();
        this.l = bitmap.getHeight();
    }

    private Bitmap c() {
        return this.h;
    }

    private int d() {
        return this.k;
    }

    private int e() {
        return this.l;
    }

    private int f() {
        return this.a.m() + this.j;
    }

    private int g() {
        if (this.e != null) {
            return this.e.a;
        }
        return -1;
    }

    private ByteBuffer h() {
        return this.a.c().asReadOnlyBuffer();
    }

    private int i() {
        return this.a.l();
    }

    private void j() {
        if (this.c) {
            return;
        }
        this.c = true;
        this.f = false;
        n();
    }

    private void k() {
        this.c = false;
    }

    private void l() {
        this.b.clear();
        b();
        this.c = false;
        if (this.e != null) {
            this.e = null;
        }
        if (this.g != null) {
            this.g = null;
        }
        if (this.i != null) {
            this.i = null;
        }
        this.a.o();
        this.f = true;
    }

    private Bitmap m() {
        return this.e != null ? this.e.b : this.h;
    }

    private void n() {
        if (!this.c || this.n) {
            return;
        }
        if (this.d) {
            k.a(this.i == null, "Pending target must be null when starting from the first frame");
            this.a.i();
            this.d = false;
        }
        if (this.i != null) {
            a aVar = this.i;
            this.i = null;
            a(aVar);
            return;
        }
        this.n = true;
        long jUptimeMillis = SystemClock.uptimeMillis() + ((long) this.a.f());
        this.a.e();
        this.g = new a(this.m, this.a.h(), jUptimeMillis);
        Bitmap bitmapN = this.a.n();
        k.a(bitmapN != null, "nextFrame is null");
        this.g.a(bitmapN);
    }

    private void o() {
        k.a(!this.c, "Can't restart a running animation");
        this.d = true;
        if (this.i != null) {
            this.i = null;
        }
    }

    final int a() {
        return this.a.g();
    }

    final void a(a aVar) {
        this.n = false;
        if (!this.f) {
            if (this.c) {
                if (aVar.b != null) {
                    b();
                    a aVar2 = this.e;
                    this.e = aVar;
                    for (int size = this.b.size() - 1; size >= 0; size--) {
                        this.b.get(size).b();
                    }
                    if (aVar2 != null) {
                        this.m.obtainMessage(2, aVar2).sendToTarget();
                    }
                }
                n();
                return;
            }
            if (!this.d) {
                this.i = aVar;
                return;
            }
        }
        this.m.obtainMessage(2, aVar).sendToTarget();
    }

    final void a(b bVar) {
        if (this.f) {
            throw new IllegalStateException("Cannot subscribe to a cleared frame loader");
        }
        if (this.b.contains(bVar)) {
            throw new IllegalStateException("Cannot subscribe twice in a row");
        }
        boolean zIsEmpty = this.b.isEmpty();
        this.b.add(bVar);
        if (zIsEmpty) {
            j();
        }
    }

    final void b() {
        if (this.h != null) {
            this.h = null;
        }
    }

    final void b(b bVar) {
        this.b.remove(bVar);
        if (this.b.isEmpty()) {
            this.c = false;
        }
    }
}
