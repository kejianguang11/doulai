package com.loc;

import android.content.Context;
import android.os.Build;
import java.io.ByteArrayOutputStream;

/* JADX INFO: loaded from: classes.dex */
public final class by extends ca {
    public static int a = 13;
    public static int b = 6;
    private Context e;

    public by(Context context, ca caVar) {
        super(caVar);
        this.e = context;
    }

    private static byte[] a(Context context) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[0];
        try {
            try {
                x.a(byteArrayOutputStream, "1.2." + a + "." + b);
                x.a(byteArrayOutputStream, "Android");
                x.a(byteArrayOutputStream, o.v(context));
                x.a(byteArrayOutputStream, o.k(context));
                x.a(byteArrayOutputStream, o.h(context));
                x.a(byteArrayOutputStream, Build.MANUFACTURER);
                x.a(byteArrayOutputStream, Build.MODEL);
                x.a(byteArrayOutputStream, Build.DEVICE);
                x.a(byteArrayOutputStream, o.y(context));
                x.a(byteArrayOutputStream, l.c(context));
                x.a(byteArrayOutputStream, l.d(context));
                x.a(byteArrayOutputStream, l.f(context));
                byteArrayOutputStream.write(new byte[]{0});
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                try {
                    byteArrayOutputStream.close();
                    return byteArray;
                } catch (Throwable th) {
                    th.printStackTrace();
                    return byteArray;
                }
            } catch (Throwable th2) {
                an.b(th2, "sm", "gh");
                return bArr;
            }
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th3) {
                th3.printStackTrace();
            }
        }
    }

    @Override // com.loc.ca
    protected final byte[] a(byte[] bArr) {
        byte[] bArrA = a(this.e);
        byte[] bArr2 = new byte[bArrA.length + bArr.length];
        System.arraycopy(bArrA, 0, bArr2, 0, bArrA.length);
        System.arraycopy(bArr, 0, bArr2, bArrA.length, bArr.length);
        return bArr2;
    }
}
