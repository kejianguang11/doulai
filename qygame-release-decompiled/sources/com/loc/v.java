package com.loc;

import android.content.Context;
import android.database.Cursor;
import android.net.Proxy;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class v {
    private static String a() {
        String defaultHost;
        try {
            defaultHost = Proxy.getDefaultHost();
        } catch (Throwable th) {
            an.b(th, "pu", "gdh");
            defaultHost = null;
        }
        return defaultHost == null ? "null" : defaultHost;
    }

    private static String a(String str) {
        return x.c(str);
    }

    public static java.net.Proxy a(Context context) {
        try {
            return Build.VERSION.SDK_INT >= 11 ? a(context, new URI("http://restsdk.amap.com")) : b(context);
        } catch (Throwable th) {
            an.b(th, "pu", "gp");
            return null;
        }
    }

    private static java.net.Proxy a(Context context, URI uri) {
        java.net.Proxy proxy;
        if (c(context)) {
            try {
                List<java.net.Proxy> listSelect = ProxySelector.getDefault().select(uri);
                if (listSelect == null || listSelect.isEmpty() || (proxy = listSelect.get(0)) == null) {
                    return null;
                }
                if (proxy.type() == Proxy.Type.DIRECT) {
                    return null;
                }
                return proxy;
            } catch (Throwable th) {
                an.b(th, "pu", "gpsc");
            }
        }
        return null;
    }

    private static int b() {
        try {
            return android.net.Proxy.getDefaultPort();
        } catch (Throwable th) {
            an.b(th, "pu", "gdp");
            return -1;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x014a A[PHI: r4
      0x014a: PHI (r4v5 ??) = (r4v16 ??), (r4v17 ??) binds: [B:76:0x00f0, B:91:0x012d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0161 A[Catch: Throwable -> 0x015d, TRY_LEAVE, TryCatch #2 {Throwable -> 0x015d, blocks: (B:105:0x0153, B:112:0x0161), top: B:123:0x0153 }] */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0153 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0072 A[PHI: r3 r4
      0x0072: PHI (r3v41 ??) = (r3v37 ??), (r3v46 ??) binds: [B:51:0x00ac, B:30:0x006f] A[DONT_GENERATE, DONT_INLINE]
      0x0072: PHI (r4v10 int) = (r4v9 int), (r4v13 int) binds: [B:51:0x00ac, B:30:0x006f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00b9 A[Catch: Throwable -> 0x00be, TRY_ENTER, TRY_LEAVE, TryCatch #6 {Throwable -> 0x00be, blocks: (B:59:0x00b9, B:70:0x00dc, B:103:0x014e), top: B:128:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00dc A[Catch: Throwable -> 0x00be, TRY_ENTER, TRY_LEAVE, TryCatch #6 {Throwable -> 0x00be, blocks: (B:59:0x00b9, B:70:0x00dc, B:103:0x014e), top: B:128:0x001a }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00f2 A[Catch: all -> 0x0178, TryCatch #8 {all -> 0x0178, blocks: (B:7:0x0020, B:9:0x0026, B:11:0x0032, B:13:0x003a, B:15:0x0042, B:16:0x004a, B:18:0x0050, B:23:0x005f, B:68:0x00d0, B:75:0x00e5, B:77:0x00f2, B:79:0x0108, B:81:0x010e, B:86:0x011c, B:90:0x0127, B:92:0x012f, B:94:0x0135, B:99:0x0143, B:38:0x007f, B:40:0x0087, B:41:0x008f, B:43:0x0095, B:48:0x00a4), top: B:130:0x001a }] */
    /* JADX WARN: Type inference failed for: r0v10, types: [int] */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v18 */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v20 */
    /* JADX WARN: Type inference failed for: r0v21 */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v24 */
    /* JADX WARN: Type inference failed for: r0v25 */
    /* JADX WARN: Type inference failed for: r0v26 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.content.ContentResolver] */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.net.Uri] */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v18, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v19, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v28 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v30 */
    /* JADX WARN: Type inference failed for: r3v36 */
    /* JADX WARN: Type inference failed for: r3v37 */
    /* JADX WARN: Type inference failed for: r3v41 */
    /* JADX WARN: Type inference failed for: r3v45 */
    /* JADX WARN: Type inference failed for: r3v46 */
    /* JADX WARN: Type inference failed for: r3v51 */
    /* JADX WARN: Type inference failed for: r3v52 */
    /* JADX WARN: Type inference failed for: r3v53 */
    /* JADX WARN: Type inference failed for: r3v54 */
    /* JADX WARN: Type inference failed for: r3v55 */
    /* JADX WARN: Type inference failed for: r3v58 */
    /* JADX WARN: Type inference failed for: r3v59 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v60 */
    /* JADX WARN: Type inference failed for: r3v61 */
    /* JADX WARN: Type inference failed for: r3v62 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v16 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static java.net.Proxy b(Context context) throws Throwable {
        Cursor cursorQuery;
        Object obj;
        String strQ;
        boolean z;
        boolean z2;
        int iB;
        String str;
        boolean z3;
        String str2;
        boolean z4;
        if (c(context)) {
            ?? A = Uri.parse("content://telephony/carriers/preferapn");
            ?? contentResolver = context.getContentResolver();
            ?? r4 = 0;
            Object obj2 = null;
            ?? r0 = 80;
            r0 = 80;
            r0 = 80;
            r0 = 80;
            r0 = 80;
            r0 = 80;
            boolean z5 = false;
            try {
            } catch (Throwable th) {
                th = th;
            }
            try {
                try {
                    cursorQuery = contentResolver.query(A, null, null, null, null);
                } catch (Throwable th2) {
                    an.b(th2, "pu", "gPx2");
                }
            } catch (SecurityException e) {
                e = e;
                cursorQuery = null;
                obj2 = null;
            } catch (Throwable th3) {
                th = th3;
                cursorQuery = null;
                obj = null;
            }
            if (cursorQuery != null) {
                try {
                } catch (SecurityException e2) {
                    e = e2;
                    obj2 = null;
                    r4 = -1;
                    an.b(e, "pu", "ghp");
                    strQ = o.q(context);
                    ?? r42 = r4;
                    if (strQ != null) {
                        String lowerCase = strQ.toLowerCase(Locale.US);
                        String strA = a();
                        int iB2 = b();
                        if (lowerCase.indexOf("ctwap") != -1) {
                            if (TextUtils.isEmpty(strA) || strA.equals("null")) {
                                A = obj2;
                                z2 = false;
                            } else {
                                z2 = true;
                                A = strA;
                            }
                            if (!z2) {
                                A = a("QMTAuMC4wLjIwMA==");
                            }
                            if (iB2 != -1) {
                                r0 = iB2;
                            }
                        } else {
                            r42 = iB2;
                            if (lowerCase.indexOf("wap") != -1) {
                                if (TextUtils.isEmpty(strA) || strA.equals("null")) {
                                    A = obj2;
                                    z = false;
                                } else {
                                    z = true;
                                    A = strA;
                                }
                                if (!z) {
                                    A = a("QMTAuMC4wLjE3Mg==");
                                }
                            } else {
                                r0 = r42;
                                A = obj2;
                            }
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                            r0 = r0;
                            A = A;
                        }
                        if (A != 0) {
                        }
                        if (z5) {
                        }
                    }
                    return null;
                } catch (Throwable th4) {
                    th = th4;
                    obj = null;
                    r0 = -1;
                    A = obj;
                    an.b(th, "pu", "gPx1");
                    th.printStackTrace();
                    if (cursorQuery != null) {
                        cursorQuery.close();
                        r0 = r0;
                        A = A;
                    }
                    if (A != 0) {
                    }
                    if (z5) {
                    }
                    return null;
                }
                if (cursorQuery.moveToFirst()) {
                    String string = cursorQuery.getString(cursorQuery.getColumnIndex("apn"));
                    String lowerCase2 = string;
                    if (string != null) {
                        Locale locale = Locale.US;
                        lowerCase2 = string.toLowerCase(locale);
                        r4 = locale;
                    }
                    try {
                        try {
                        } catch (SecurityException e3) {
                            e = e3;
                            obj2 = null;
                            an.b(e, "pu", "ghp");
                            strQ = o.q(context);
                            ?? r422 = r4;
                            if (strQ != null) {
                            }
                            return null;
                        } catch (Throwable th5) {
                            th = th5;
                            A = 0;
                            r0 = r4;
                            an.b(th, "pu", "gPx1");
                            th.printStackTrace();
                            if (cursorQuery != null) {
                            }
                            if (A != 0) {
                            }
                            if (z5) {
                            }
                            return null;
                        }
                    } catch (SecurityException e4) {
                        e = e4;
                        an.b(e, "pu", "ghp");
                        strQ = o.q(context);
                        ?? r4222 = r4;
                        if (strQ != null) {
                        }
                        return null;
                    } catch (Throwable th6) {
                        th = th6;
                        r0 = r4;
                        A = 0;
                        an.b(th, "pu", "gPx1");
                        th.printStackTrace();
                        if (cursorQuery != null) {
                        }
                        if (A != 0) {
                        }
                        if (z5) {
                        }
                        return null;
                    }
                    if (lowerCase2 == null || !lowerCase2.contains("ctwap")) {
                        if (lowerCase2 == null || !lowerCase2.contains("wap")) {
                            A = 0;
                            r0 = -1;
                        } else {
                            String strA2 = a();
                            iB = b();
                            if (TextUtils.isEmpty(strA2) || strA2.equals("null")) {
                                str = null;
                                z3 = false;
                            } else {
                                str = strA2;
                                z3 = true;
                            }
                            A = !z3 ? a("QMTAuMC4wLjE3Mg==") : str;
                            if (iB != -1) {
                                r0 = iB;
                            }
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                            r0 = r0;
                            A = A;
                        }
                        if (A != 0) {
                            try {
                                if (A.length() > 0 && r0 != -1) {
                                    z5 = true;
                                }
                            } catch (Throwable th7) {
                                ak.a(th7, "pu", "gp2");
                                th7.printStackTrace();
                            }
                        }
                        if (z5) {
                            return new java.net.Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(A, r0));
                        }
                    } else {
                        String strA3 = a();
                        iB = b();
                        if (TextUtils.isEmpty(strA3) || strA3.equals("null")) {
                            str2 = null;
                            z4 = false;
                        } else {
                            str2 = strA3;
                            z4 = true;
                        }
                        A = !z4 ? a("QMTAuMC4wLjIwMA==") : str2;
                        if (iB != -1) {
                        }
                        if (cursorQuery != null) {
                        }
                        if (A != 0) {
                        }
                        if (z5) {
                        }
                    }
                }
            }
        }
        return null;
    }

    private static boolean c(Context context) {
        return o.o(context) == 0;
    }
}
