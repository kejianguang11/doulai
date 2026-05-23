package com.loc;

import android.content.Context;
import android.os.Build;
import com.igexin.sdk.PushConsts;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ao {
    private static WeakReference<bn> a = null;
    private static boolean b = true;
    private static WeakReference<cg> c;
    private static WeakReference<cg> d;
    private static String[] e = new String[10];
    private static int f = 0;
    private static boolean g = false;
    private static int h = 0;
    private static w i;

    private static w a(String str) {
        List<w> listA = al.a();
        if (listA == null) {
            listA = new ArrayList();
        }
        if (str != null && !"".equals(str)) {
            for (w wVar : listA) {
                if (al.a(wVar.f(), str)) {
                    return wVar;
                }
            }
            if (str.contains("com.amap.api.col")) {
                try {
                    return x.a();
                } catch (k e2) {
                    e2.printStackTrace();
                }
            }
            if (str.contains("com.amap.co") || str.contains("com.amap.opensdk.co") || str.contains("com.amap.location")) {
                try {
                    w wVarB = x.b();
                    wVarB.a(true);
                    return wVarB;
                } catch (k e3) {
                    e3.printStackTrace();
                }
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:93:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0125 A[RETURN] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:52:0x00b6 -> B:113:0x011c). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static String a(List<w> list) throws Throwable {
        be beVar;
        FileInputStream fileInputStream;
        AutoCloseable autoCloseable;
        File file;
        try {
            try {
                try {
                    file = new File("/data/anr/traces.txt");
                } catch (Throwable th) {
                    th = th;
                }
            } catch (FileNotFoundException unused) {
                beVar = null;
                fileInputStream = null;
            } catch (Throwable th2) {
                th = th2;
                beVar = null;
                fileInputStream = null;
            }
        } catch (Throwable th3) {
            an.b(th3, "alg", "getA");
        }
        if (!file.exists()) {
            return null;
        }
        fileInputStream = new FileInputStream(file);
        try {
            if (fileInputStream.available() > 1024000) {
                fileInputStream.skip(r1 - 1024000);
            }
            beVar = new be(fileInputStream, bd.b);
            boolean z = false;
            while (true) {
                try {
                    try {
                        String strTrim = beVar.a().trim();
                        if (strTrim.contains(PushConsts.KEY_SERVICE_PIT)) {
                            while (!strTrim.startsWith("\"main\"")) {
                                strTrim = beVar.a();
                            }
                            z = true;
                        }
                        if (!strTrim.equals("") || !z) {
                            if (z) {
                                try {
                                    if (f > 9) {
                                        f = 0;
                                    }
                                    e[f] = strTrim;
                                    f++;
                                } catch (Throwable th4) {
                                    an.b(th4, "alg", "aDa");
                                }
                                if (h == 5) {
                                    break;
                                }
                                if (g) {
                                    h++;
                                } else {
                                    Iterator<w> it = list.iterator();
                                    while (true) {
                                        if (it.hasNext()) {
                                            w next = it.next();
                                            boolean zB = al.b(next.f(), strTrim);
                                            g = zB;
                                            if (zB) {
                                                i = next;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        an.b(th, "alg", "getA");
                        if (beVar != null) {
                            try {
                                beVar.close();
                            } catch (Throwable th6) {
                                an.b(th6, "alg", "getA");
                            }
                        }
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        if (g) {
                        }
                    }
                } catch (EOFException unused2) {
                } catch (FileNotFoundException unused3) {
                }
            }
            try {
                beVar.close();
            } catch (Throwable th7) {
                an.b(th7, "alg", "getA");
            }
            fileInputStream.close();
            break;
        } catch (FileNotFoundException unused4) {
            beVar = null;
        } catch (Throwable th8) {
            th = th8;
            beVar = null;
        }
        if (g) {
            return b();
        }
        return null;
        if (beVar != null) {
            try {
                beVar.close();
            } catch (Throwable th9) {
                an.b(th9, "alg", "getA");
            }
        }
        if (fileInputStream != null) {
            fileInputStream.close();
        }
        if (g) {
        }
    }

    static void a(Context context) {
        String strA;
        List<w> listA = al.a();
        if (listA == null || listA.size() == 0 || (strA = a(listA)) == null || "".equals(strA) || i == null) {
            return;
        }
        a(context, i, 2, "ANR", strA);
    }

    private static void a(final Context context, final cg cgVar, final String str) {
        cj.a().b(new ck() { // from class: com.loc.ao.1
            @Override // com.loc.ck
            public final void a() {
                try {
                    synchronized (ao.class) {
                        bn bnVarA = bt.a(ao.a);
                        bt.a(context, bnVarA, str, 1000, 4096000, "1");
                        bnVarA.f = cgVar;
                        if (bnVarA.g == null) {
                            bnVarA.g = new bx(new bw(context, new cb(), new ay(new ba(new bb())), "QImtleSI6IiVzIiwicGxhdGZvcm0iOiJhbmRyb2lkIiwiZGl1IjoiJXMiLCJhZGl1IjoiJXMiLCJwa2ciOiIlcyIsIm1vZGVsIjoiJXMiLCJhcHBuYW1lIjoiJXMiLCJhcHB2ZXJzaW9uIjoiJXMiLCJzeXN2ZXJzaW9uIjoiJXMi", l.f(context), o.v(context), o.u(context), l.c(context), Build.MODEL, l.b(context), l.d(context), Build.VERSION.RELEASE));
                        }
                        bnVarA.h = 3600000;
                        bo.a(bnVarA);
                    }
                } catch (Throwable th) {
                    an.b(th, "lg", "pul");
                }
            }
        });
    }

    private static void a(Context context, w wVar, int i2, String str, String str2) {
        String str3;
        String strA = bt.a();
        String strA2 = bt.a(context, wVar);
        l.a(context);
        String strA3 = bt.a(strA2, strA, i2, str, str2);
        if (strA3 == null || "".equals(strA3)) {
            return;
        }
        String strB = s.b(str2);
        if (i2 == 1) {
            str3 = al.b;
        } else if (i2 == 2) {
            str3 = al.d;
        } else if (i2 != 0) {
            return;
        } else {
            str3 = al.c;
        }
        String str4 = str3;
        bn bnVarA = bt.a(a);
        bt.a(context, bnVarA, str4, 1000, 4096000, "1");
        if (bnVarA.e == null) {
            bnVarA.e = new ax(new ay(new ba(new bb())));
        }
        try {
            bo.a(strB, x.a(strA3.replaceAll("\n", "<br/>")), bnVarA);
        } catch (Throwable unused) {
        }
    }

    public static void a(Context context, Throwable th, int i2, String str, String str2) {
        String strA = x.a(th);
        w wVarA = a(strA);
        if (a(wVarA)) {
            String strReplaceAll = strA.replaceAll("\n", "<br/>");
            String string = th.toString();
            if (string == null || "".equals(string)) {
                return;
            }
            StringBuilder sb = new StringBuilder();
            if (str != null) {
                sb.append("class:");
                sb.append(str);
            }
            if (str2 != null) {
                sb.append(" method:");
                sb.append(str2);
                sb.append("$<br/>");
            }
            sb.append(strReplaceAll);
            a(context, wVarA, i2, string, sb.toString());
        }
    }

    static void a(w wVar, Context context, String str, String str2) {
        if (!a(wVar) || str == null || "".equals(str)) {
            return;
        }
        a(context, wVar, 1, str, str2);
    }

    private static boolean a(w wVar) {
        return wVar != null && wVar.e();
    }

    private static String b() {
        StringBuilder sb = new StringBuilder();
        try {
            for (int i2 = f; i2 < 10 && i2 <= 9; i2++) {
                sb.append(e[i2]);
            }
            for (int i3 = 0; i3 < f; i3++) {
                sb.append(e[i3]);
            }
        } catch (Throwable th) {
            an.b(th, "alg", "gLI");
        }
        return sb.toString();
    }

    static void b(Context context) {
        ce ceVar = new ce(b);
        b = false;
        a(context, ceVar, al.c);
    }

    static void c(Context context) {
        if (c == null || c.get() == null) {
            c = new WeakReference<>(new cf(context, 3600000, "hKey", new ch(context)));
        }
        a(context, c.get(), al.d);
    }

    static void d(Context context) {
        if (d == null || d.get() == null) {
            d = new WeakReference<>(new cf(context, 3600000, "gKey", new ch(context)));
        }
        a(context, d.get(), al.b);
    }
}
