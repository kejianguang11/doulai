package com.igexin.push.f;

/* JADX INFO: loaded from: classes.dex */
public final class f implements com.igexin.push.f.b.c {
    private static final String a = "SilentTask";
    private static f b;
    private boolean c = com.igexin.push.g.c.a(System.currentTimeMillis());

    private f() {
    }

    public static f a() {
        if (b == null) {
            b = new f();
        }
        return b;
    }

    @Override // com.igexin.push.f.b.c
    public final void a(long j) {
    }

    @Override // com.igexin.push.f.b.c
    public final void b() {
        c();
    }

    public final void c() {
        boolean z = this.c;
        this.c = com.igexin.push.g.c.a(System.currentTimeMillis());
        if (!z || this.c) {
            return;
        }
        com.igexin.c.a.c.a.b(a, "out silence time");
        a.a().a(false);
    }

    @Override // com.igexin.push.f.b.c
    public final boolean d() {
        return com.igexin.push.config.d.c != 0;
    }
}
