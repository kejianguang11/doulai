package com.loc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public final class bz extends ca {
    public bz() {
    }

    public bz(ca caVar) {
        super(caVar);
    }

    @Override // com.loc.ca
    protected final byte[] a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date()));
        stringBuffer.append(" ");
        stringBuffer.append(UUID.randomUUID().toString());
        stringBuffer.append(" ");
        if (stringBuffer.length() != 53) {
            return new byte[0];
        }
        byte[] bArrA = x.a(stringBuffer.toString());
        byte[] bArr2 = new byte[bArrA.length + bArr.length];
        System.arraycopy(bArrA, 0, bArr2, 0, bArrA.length);
        System.arraycopy(bArr, 0, bArr2, bArrA.length, bArr.length);
        return bArr2;
    }
}
