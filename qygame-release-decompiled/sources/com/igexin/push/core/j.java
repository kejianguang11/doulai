package com.igexin.push.core;

import com.igexin.push.core.d;

/* JADX INFO: loaded from: classes.dex */
public final class j {
    private static final String b = "HeartBeatGenerator";
    private static j e;
    public long a = 240000;
    private int c = b.a;
    private long d = 0;

    /* JADX INFO: renamed from: com.igexin.push.core.j$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b = new int[a.a().length];

        static {
            try {
                b[a.a - 1] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                b[a.b - 1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                b[a.c - 1] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                b[a.d - 1] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            a = new int[b.a().length];
            try {
                a[b.a - 1] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[b.b - 1] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[b.c - 1] = 3;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static final class a {
        public static final int a = 1;
        public static final int b = 2;
        public static final int c = 3;
        public static final int d = 4;
        private static final /* synthetic */ int[] e = {a, b, c, d};

        private a(String str, int i) {
        }

        public static int[] a() {
            return (int[]) e.clone();
        }
    }

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    static final class b {
        public static final int a = 1;
        public static final int b = 2;
        public static final int c = 3;
        private static final /* synthetic */ int[] d = {a, b, c};

        private b(String str, int i) {
        }

        public static int[] a() {
            return (int[]) d.clone();
        }
    }

    private j() {
    }

    public static j a() {
        if (e == null) {
            e = new j();
        }
        return e;
    }

    private void a(long j) {
        this.a = j;
    }

    private void b(int i) {
        int i2;
        switch (AnonymousClass1.b[i - 1]) {
            case 1:
                this.a = Math.min(this.a + 60000, com.igexin.push.config.c.m);
                i2 = b.a;
                break;
            case 2:
            case 3:
                this.d++;
                if (this.d < 2) {
                    return;
                }
                this.a = Math.max(this.a - 60000, 240000L);
                i2 = b.b;
                break;
            case 4:
                this.a = 240000L;
                e(b.a);
                return;
            default:
                return;
        }
        e(i2);
    }

    private void c(int i) {
        int i2;
        switch (AnonymousClass1.b[i - 1]) {
            case 1:
                i2 = b.b;
                break;
            case 2:
            case 3:
                this.a = Math.max(this.a - 60000, 240000L);
                this.d++;
                if (this.d < 2) {
                    return;
                }
                this.a = 240000L;
                i2 = b.c;
                break;
            case 4:
                this.a = 240000L;
                e(b.a);
                return;
            default:
                return;
        }
        e(i2);
    }

    private void d(int i) {
        int i2;
        switch (AnonymousClass1.b[i - 1]) {
            case 1:
                this.a = 240000L;
                i2 = b.a;
                break;
            case 2:
            case 3:
                i2 = b.c;
                break;
            case 4:
                this.a = 240000L;
                e(b.a);
                return;
            default:
                return;
        }
        e(i2);
    }

    private void e(int i) {
        this.c = i;
        this.d = 0L;
    }

    public final void a(int i) {
        int i2;
        switch (AnonymousClass1.a[this.c - 1]) {
            case 1:
                switch (AnonymousClass1.b[i - 1]) {
                    case 1:
                        this.a = Math.min(this.a + 60000, com.igexin.push.config.c.m);
                        i2 = b.a;
                        e(i2);
                        break;
                    case 2:
                    case 3:
                        this.d++;
                        if (this.d >= 2) {
                            this.a = Math.max(this.a - 60000, 240000L);
                            i2 = b.b;
                            e(i2);
                        }
                        break;
                    case 4:
                        this.a = 240000L;
                        e(b.a);
                        break;
                }
                break;
            case 2:
                switch (AnonymousClass1.b[i - 1]) {
                    case 1:
                        i2 = b.b;
                        e(i2);
                        break;
                    case 2:
                    case 3:
                        this.a = Math.max(this.a - 60000, 240000L);
                        this.d++;
                        if (this.d >= 2) {
                            this.a = 240000L;
                        }
                        break;
                    case 4:
                        this.a = 240000L;
                        e(b.a);
                        break;
                }
                i2 = b.c;
                e(i2);
                break;
            case 3:
                switch (AnonymousClass1.b[i - 1]) {
                    case 1:
                        this.a = 240000L;
                        break;
                    case 2:
                    case 3:
                        i2 = b.c;
                        e(i2);
                        break;
                    case 4:
                        this.a = 240000L;
                        e(b.a);
                        break;
                }
                i2 = b.a;
                e(i2);
                break;
        }
    }

    public final long b() {
        long j = this.a;
        if (com.igexin.push.config.d.e > 0) {
            j = com.igexin.push.config.d.e * 1000;
        }
        if (e.n && e.u && d.a.a.h.b) {
            return j;
        }
        return 3600000L;
    }
}
