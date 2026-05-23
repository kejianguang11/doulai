package com.loc;

import android.content.Context;
import android.text.TextUtils;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/* JADX INFO: loaded from: classes.dex */
public final class bw extends ca {
    private Context a;
    private String b;
    private az e;
    private Object[] f;

    public bw(Context context, ca caVar, az azVar, String str, Object... objArr) {
        super(caVar);
        this.a = context;
        this.b = str;
        this.e = azVar;
        this.f = objArr;
    }

    private String b() {
        try {
            return String.format(x.c(this.b), this.f);
        } catch (Throwable th) {
            th.printStackTrace();
            an.b(th, "ofm", "gpj");
            return "";
        }
    }

    @Override // com.loc.ca
    protected final byte[] a(byte[] bArr) throws BadPaddingException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, InvalidKeyException, CertificateException {
        String strA = x.a(bArr);
        if (TextUtils.isEmpty(strA)) {
            return null;
        }
        return x.a("{\"pinfo\":\"" + x.a(this.e.b(x.a(b()))) + "\",\"els\":[" + strA + "]}");
    }
}
