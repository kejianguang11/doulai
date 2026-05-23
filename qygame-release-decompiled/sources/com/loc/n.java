package com.loc;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/* JADX INFO: loaded from: classes.dex */
public final class n {

    private static class a {
        String a;
        String b;
        String c;
        String d;
        String e;
        String f;
        String g;
        String h;
        String i;
        String j;
        String k;
        String l;
        String m;
        String n;
        String o;
        String p;
        String q;
        String r;
        String s;
        String t;
        String u;
        String v;
        String w;
        String x;
        String y;
        String z;

        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }
    }

    public static String a() {
        String strValueOf;
        Throwable th;
        try {
            strValueOf = String.valueOf(System.currentTimeMillis());
            try {
                String str = l.a() ? "1" : "0";
                int length = strValueOf.length();
                return strValueOf.substring(0, length - 2) + str + strValueOf.substring(length - 1);
            } catch (Throwable th2) {
                th = th2;
                ak.a(th, "CI", "TS");
                return strValueOf;
            }
        } catch (Throwable th3) {
            strValueOf = null;
            th = th3;
        }
    }

    public static String a(Context context) {
        return b(context);
    }

    public static String a(Context context, String str, String str2) {
        try {
            return s.a(l.e(context) + ":" + str.substring(0, str.length() - 3) + ":" + str2);
        } catch (Throwable th) {
            ak.a(th, "CI", "Sco");
            return null;
        }
    }

    private static String a(a aVar) {
        return p.b(b(aVar));
    }

    private static void a(ByteArrayOutputStream byteArrayOutputStream, String str) {
        if (TextUtils.isEmpty(str)) {
            x.a(byteArrayOutputStream, (byte) 0, new byte[0]);
        } else {
            x.a(byteArrayOutputStream, str.getBytes().length > 255 ? (byte) -1 : (byte) str.getBytes().length, x.a(str));
        }
    }

    public static byte[] a(Context context, boolean z, boolean z2) {
        try {
            return b(b(context, z, z2));
        } catch (Throwable th) {
            ak.a(th, "CI", "gz");
            return null;
        }
    }

    public static byte[] a(byte[] bArr) throws BadPaddingException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, InvalidKeyException, CertificateException, NullPointerException {
        return p.a(bArr);
    }

    private static a b(Context context, boolean z, boolean z2) {
        String str;
        a aVar = new a((byte) 0);
        aVar.a = o.v(context);
        aVar.b = o.k(context);
        String strH = o.h(context);
        if (strH == null) {
            strH = "";
        }
        aVar.c = strH;
        aVar.d = l.c(context);
        aVar.e = Build.MODEL;
        aVar.f = Build.MANUFACTURER;
        aVar.g = Build.DEVICE;
        aVar.h = l.b(context);
        aVar.i = l.d(context);
        aVar.j = String.valueOf(Build.VERSION.SDK_INT);
        aVar.k = o.y(context);
        aVar.l = o.r(context);
        StringBuilder sb = new StringBuilder();
        sb.append(o.o(context));
        aVar.m = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(o.n(context));
        aVar.n = sb2.toString();
        aVar.o = o.A(context);
        aVar.p = o.m(context);
        aVar.q = "";
        aVar.r = "";
        if (z) {
            aVar.s = "";
            str = "";
        } else {
            String[] strArrD = o.d();
            aVar.s = strArrD[0];
            str = strArrD[1];
        }
        aVar.t = str;
        aVar.w = o.a();
        String strA = o.a(context);
        if (TextUtils.isEmpty(strA)) {
            strA = "";
        }
        aVar.x = strA;
        aVar.y = "aid=" + o.j(context);
        if ((z2 && ag.d) || ag.e) {
            String strG = o.g(context);
            if (!TextUtils.isEmpty(strG)) {
                aVar.y += "|oaid=" + strG;
            }
        }
        String strA2 = o.a(context, com.igexin.push.core.b.an);
        if (!TextUtils.isEmpty(strA2)) {
            aVar.y += "|multiImeis=" + strA2;
        }
        String strX = o.x(context);
        if (!TextUtils.isEmpty(strX)) {
            aVar.y += "|meid=" + strX;
        }
        aVar.y += "|serial=" + o.i(context);
        String strB = o.b();
        if (!TextUtils.isEmpty(strB)) {
            aVar.y += "|adiuExtras=" + strB;
        }
        aVar.y += "|storage=" + o.f() + "|ram=" + o.z(context) + "|arch=" + o.g();
        String strB2 = ai.a().b();
        if (TextUtils.isEmpty(strB2)) {
            strB2 = "";
        }
        aVar.z = strB2;
        return aVar;
    }

    private static String b(Context context) {
        try {
            return a(b(context, false, false));
        } catch (Throwable th) {
            ak.a(th, "CI", "gCXi");
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00b9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static byte[] b(a aVar) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                try {
                    a(byteArrayOutputStream, aVar.a);
                    a(byteArrayOutputStream, aVar.b);
                    a(byteArrayOutputStream, aVar.c);
                    a(byteArrayOutputStream, aVar.d);
                    a(byteArrayOutputStream, aVar.e);
                    a(byteArrayOutputStream, aVar.f);
                    a(byteArrayOutputStream, aVar.g);
                    a(byteArrayOutputStream, aVar.h);
                    a(byteArrayOutputStream, aVar.i);
                    a(byteArrayOutputStream, aVar.j);
                    a(byteArrayOutputStream, aVar.k);
                    a(byteArrayOutputStream, aVar.l);
                    a(byteArrayOutputStream, aVar.m);
                    a(byteArrayOutputStream, aVar.n);
                    a(byteArrayOutputStream, aVar.o);
                    a(byteArrayOutputStream, aVar.p);
                    a(byteArrayOutputStream, aVar.q);
                    a(byteArrayOutputStream, aVar.r);
                    a(byteArrayOutputStream, aVar.s);
                    a(byteArrayOutputStream, aVar.t);
                    a(byteArrayOutputStream, aVar.u);
                    a(byteArrayOutputStream, aVar.v);
                    a(byteArrayOutputStream, aVar.w);
                    a(byteArrayOutputStream, aVar.x);
                    a(byteArrayOutputStream, aVar.y);
                    a(byteArrayOutputStream, aVar.z);
                    byte[] bArrB = b(x.b(byteArrayOutputStream.toByteArray()));
                    try {
                        byteArrayOutputStream.close();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                    return bArrB;
                } catch (Throwable th2) {
                    th = th2;
                    ak.a(th, "CI", "gzx");
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (Throwable th3) {
                            th3.printStackTrace();
                        }
                    }
                    return null;
                }
            } catch (Throwable th4) {
                th = th4;
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Throwable th5) {
                        th5.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Throwable th6) {
            th = th6;
            byteArrayOutputStream = null;
            if (byteArrayOutputStream != null) {
            }
            throw th;
        }
    }

    private static byte[] b(byte[] bArr) throws Throwable {
        PublicKey publicKeyD = x.d();
        if (bArr.length <= 117) {
            return p.a(bArr, publicKeyD);
        }
        byte[] bArr2 = new byte[117];
        System.arraycopy(bArr, 0, bArr2, 0, 117);
        byte[] bArrA = p.a(bArr2, publicKeyD);
        byte[] bArr3 = new byte[(bArr.length + 128) - 117];
        System.arraycopy(bArrA, 0, bArr3, 0, 128);
        System.arraycopy(bArr, 117, bArr3, 128, bArr.length - 117);
        return bArr3;
    }
}
