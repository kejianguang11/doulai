package com.loc;

import android.content.Context;
import android.text.TextUtils;
import java.io.ByteArrayOutputStream;

/* JADX INFO: loaded from: classes.dex */
public final class br {
    private Context a;
    private String b;
    private String c;
    private String d;
    private String e;

    public br(Context context, String str, String str2, String str3) throws k {
        if (TextUtils.isEmpty(str3) || str3.length() > 256) {
            throw new k("无效的参数 - IllegalArgumentException");
        }
        this.a = context.getApplicationContext();
        this.c = str;
        this.d = str2;
        this.b = str3;
    }

    private static byte[] a(int i) {
        return new byte[]{(byte) ((i >> 24) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 8) & 255), (byte) (i & 255)};
    }

    private byte[] b(String str) {
        byte[] bArrA;
        if (!TextUtils.isEmpty(str) && (bArrA = x.a(this.e)) != null) {
            return x.a(bArrA.length);
        }
        return new byte[]{0, 0};
    }

    public final void a(String str) throws k {
        if (TextUtils.isEmpty(str) || str.length() > 65536) {
            throw new k("无效的参数 - IllegalArgumentException");
        }
        this.e = str;
    }

    public final byte[] a() throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        int iCurrentTimeMillis = 0;
        byte[] bArr = new byte[0];
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        try {
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
            } catch (Throwable th) {
                th = th;
            }
            try {
                try {
                    x.a(byteArrayOutputStream, this.c);
                    x.a(byteArrayOutputStream, this.d);
                    x.a(byteArrayOutputStream, this.b);
                    x.a(byteArrayOutputStream, String.valueOf(o.o(this.a)));
                    try {
                        iCurrentTimeMillis = (int) (System.currentTimeMillis() / 1000);
                    } catch (Throwable unused) {
                    }
                    byteArrayOutputStream.write(a(iCurrentTimeMillis));
                    byteArrayOutputStream.write(b(this.e));
                    byteArrayOutputStream.write(x.a(this.e));
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    try {
                        byteArrayOutputStream.close();
                        return byteArray;
                    } catch (Throwable th2) {
                        th2.printStackTrace();
                        return byteArray;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (Throwable th4) {
                            th4.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th5) {
                th = th5;
                byteArrayOutputStream2 = byteArrayOutputStream;
                an.b(th, "se", "tds");
                if (byteArrayOutputStream2 != null) {
                    try {
                        byteArrayOutputStream2.close();
                    } catch (Throwable th6) {
                        th6.printStackTrace();
                    }
                }
                return bArr;
            }
        } catch (Throwable th7) {
            th = th7;
            byteArrayOutputStream = byteArrayOutputStream2;
        }
    }
}
