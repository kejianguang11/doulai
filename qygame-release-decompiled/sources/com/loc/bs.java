package com.loc;

import android.content.Context;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public class bs {
    static boolean a = false;
    static int b = 20;
    private static int c = 20;
    private static WeakReference<bn> d;
    private static int e;

    static class a extends ck {
        static int a = 1;
        static int b = 2;
        static int c = 3;
        private Context d;
        private br f;
        private int g;
        private List<br> h;

        a(Context context, int i) {
            this.d = context;
            this.g = i;
        }

        a(Context context, int i, br brVar) {
            this(context, i);
            this.f = brVar;
        }

        a(Context context, int i, List<br> list) {
            this(context, i);
            this.h = list;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r5v3 */
        /* JADX WARN: Type inference failed for: r5v4, types: [java.io.ByteArrayOutputStream] */
        /* JADX WARN: Type inference failed for: r5v7 */
        @Override // com.loc.ck
        public final void a() {
            String str;
            String str2;
            Throwable th;
            ?? r5;
            ByteArrayOutputStream byteArrayOutputStream;
            if (this.g == 1) {
                try {
                    if (this.d != null && this.f != null) {
                        synchronized (bs.class) {
                            if (this.d != null && this.f != null) {
                                bs.a(this.d, this.f.a());
                                return;
                            }
                            return;
                        }
                    }
                    return;
                } catch (Throwable th2) {
                    th = th2;
                    str = "stm";
                    str2 = "as";
                }
            } else {
                if (this.g != 2) {
                    if (this.g == 3) {
                        try {
                            if (this.d == null) {
                                return;
                            }
                            bn bnVarA = bt.a(bs.d);
                            bt.a(this.d, bnVarA, al.h, 1000, 307200, com.igexin.push.config.c.H);
                            if (bnVarA.g == null) {
                                bnVarA.g = new bu(new by(this.d, new bv(new bz(new cb()))));
                            }
                            bnVarA.h = 3600000;
                            if (TextUtils.isEmpty(bnVarA.i)) {
                                bnVarA.i = "cKey";
                            }
                            if (bnVarA.f == null) {
                                bnVarA.f = new cf(this.d, bnVarA.h, bnVarA.i, new cc(bnVarA.a, new cd(this.d, bs.a, bs.c * 1024, bs.b * 1024, "staticUpdate", bs.e * 1024)));
                            }
                            bo.a(bnVarA);
                            return;
                        } catch (Throwable th3) {
                            an.b(th3, "stm", "usd");
                            return;
                        }
                    }
                    return;
                }
                try {
                    synchronized (bs.class) {
                        if (this.h != null && this.d != null) {
                            byte[] byteArray = null;
                            ByteArrayOutputStream byteArrayOutputStream2 = null;
                            byte[] bArr = new byte[0];
                            try {
                                try {
                                    byteArrayOutputStream = new ByteArrayOutputStream();
                                } catch (Throwable th4) {
                                    th = th4;
                                    r5 = byteArray;
                                }
                            } catch (Throwable th5) {
                                th = th5;
                            }
                            try {
                                for (br brVar : this.h) {
                                    if (brVar != null) {
                                        byteArrayOutputStream.write(brVar.a());
                                    }
                                }
                                byteArray = byteArrayOutputStream.toByteArray();
                                try {
                                    byteArrayOutputStream.close();
                                } catch (Throwable th6) {
                                    th6.printStackTrace();
                                }
                            } catch (Throwable th7) {
                                th = th7;
                                r5 = byteArrayOutputStream;
                                if (r5 == 0) {
                                    throw th;
                                }
                                try {
                                    r5.close();
                                    throw th;
                                } catch (Throwable th8) {
                                    th8.printStackTrace();
                                    throw th;
                                }
                            }
                            bs.a(this.d, byteArray);
                            return;
                        }
                        return;
                    }
                } catch (Throwable th9) {
                    th = th9;
                    str = "stm";
                    str2 = "apb";
                }
            }
            an.b(th, str, str2);
        }
    }

    public static void a(Context context) {
        cj.a().b(new a(context, a.c));
    }

    static /* synthetic */ void a(Context context, byte[] bArr) throws IOException {
        bn bnVarA = bt.a(d);
        bt.a(context, bnVarA, al.h, 1000, 307200, com.igexin.push.config.c.H);
        if (bnVarA.e == null) {
            bnVarA.e = new aw();
        }
        try {
            bo.a(Integer.toString(new Random().nextInt(100)) + Long.toString(System.nanoTime()), bArr, bnVarA);
        } catch (Throwable th) {
            an.b(th, "stm", "wts");
        }
    }

    public static synchronized void a(br brVar, Context context) {
        cj.a().b(new a(context, a.a, brVar));
    }

    public static synchronized void a(List<br> list, Context context) {
        if (list != null) {
            try {
                if (list.size() != 0) {
                    cj.a().b(new a(context, a.b, list));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static synchronized void a(boolean z, int i) {
        a = z;
        e = Math.max(0, i);
    }

    public static synchronized void b(List<br> list, Context context) {
        try {
            List<br> listB = bi.b();
            if (listB != null && listB.size() > 0) {
                list.addAll(listB);
            }
        } catch (Throwable unused) {
        }
        a(list, context);
    }
}
