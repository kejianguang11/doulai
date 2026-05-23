package com.igexin.c.a.b.a.a;

/* JADX INFO: loaded from: classes.dex */
public abstract class a extends com.igexin.c.a.b.f {
    protected volatile boolean f;
    protected volatile int g;
    protected String h;
    protected volatile boolean i;

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX INFO: renamed from: com.igexin.c.a.b.a.a.a$a, reason: collision with other inner class name */
    protected static final class EnumC0029a {
        public static final int a = 1;
        public static final int b = 2;
        public static final int c = 3;
        private static final /* synthetic */ int[] d = {a, b, c};

        private EnumC0029a(String str, int i) {
        }

        private static int[] a() {
            return (int[]) d.clone();
        }
    }

    public a(int i, com.igexin.c.a.b.d dVar) {
        super(i, null, dVar);
        this.g = EnumC0029a.a;
        this.i = true;
    }

    public abstract void c_();

    @Override // com.igexin.c.a.d.f, com.igexin.c.a.d.a.f
    public final void d() {
        super.d();
        this.o = true;
    }

    @Override // com.igexin.c.a.d.f
    public final void e() {
        Thread thread = this.K;
        if (!thread.isAlive() || thread.isInterrupted()) {
            return;
        }
        thread.interrupt();
    }

    @Override // com.igexin.c.a.d.f
    public final void f() {
    }

    protected final boolean g() {
        return this.g == EnumC0029a.c;
    }
}
