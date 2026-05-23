package com.loc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class bu extends ca {
    ByteArrayOutputStream a;

    public bu() {
        this.a = new ByteArrayOutputStream();
    }

    public bu(ca caVar) {
        super(caVar);
        this.a = new ByteArrayOutputStream();
    }

    @Override // com.loc.ca
    protected final byte[] a(byte[] bArr) {
        byte[] byteArray = this.a.toByteArray();
        try {
            this.a.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.a = new ByteArrayOutputStream();
        return byteArray;
    }

    @Override // com.loc.ca
    public final void b(byte[] bArr) {
        try {
            this.a.write(bArr);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
