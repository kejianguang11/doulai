package com.igexin.push.d.c;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class i extends c {
    public static final int a = 4;
    public long b;
    public byte c;
    public int d;
    public String e;
    public short f = (short) com.igexin.push.config.d.e;
    public List<j> g;

    public i() {
        this.m = 4;
        this.n = (byte) 20;
    }

    private static String a(byte[] bArr, int i, int i2) {
        try {
            return new String(bArr, i, i2, com.alipay.sdk.sys.a.m);
        } catch (Exception unused) {
            return "";
        }
    }

    @Override // com.igexin.push.d.c.c
    public final void a(byte[] bArr) {
        this.b = com.igexin.c.a.b.g.d(bArr, 0);
        this.c = bArr[8];
        this.d = com.igexin.c.a.b.g.c(bArr, 9) & (-1);
        int i = 14;
        if (bArr.length > 13) {
            int i2 = bArr[13] & 255;
            if (i2 > 0) {
                this.g = new ArrayList();
                int i3 = i2 + 14;
                while (i < i3) {
                    j jVar = new j();
                    this.g.add(jVar);
                    int i4 = i + 1;
                    int i5 = bArr[i] & 255 & 255;
                    int i6 = i4 + 1;
                    int i7 = bArr[i4] & 255 & 255;
                    jVar.a = (byte) i5;
                    if ((i5 == 1 || i5 == 4) && i7 > 0) {
                        try {
                            jVar.b = new String(bArr, i6, i7, com.alipay.sdk.sys.a.m);
                        } catch (Exception e) {
                            com.igexin.c.a.c.a.a(e);
                        }
                    }
                    i = i6 + i7;
                }
            }
        } else {
            i = 13;
        }
        if (bArr.length > i) {
            this.e = a(bArr, i + 1, bArr[i] & 255);
        }
    }

    @Override // com.igexin.push.d.c.c
    public final byte[] b() {
        int length;
        int i;
        byte[] byteArray = null;
        if (this.g != null && this.g.size() > 0) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Iterator<j> it = this.g.iterator();
            while (it.hasNext()) {
                try {
                    byteArrayOutputStream.write(it.next().b());
                    byteArray = byteArrayOutputStream.toByteArray();
                } catch (IOException e) {
                    com.igexin.c.a.c.a.a(e);
                }
            }
            try {
                byteArrayOutputStream.close();
            } catch (IOException e2) {
                com.igexin.c.a.c.a.a(e2);
            }
        }
        if (byteArray != null) {
            length = byteArray.length;
            i = length + 1;
        } else {
            length = 0;
            i = 1;
        }
        byte[] bArr = new byte[i + 12 + this.e.getBytes().length + 1 + 2];
        com.igexin.c.a.b.g.a(this.b, bArr, 0);
        com.igexin.c.a.b.g.a(((this.c & 255) << 24) | this.d, bArr, 8);
        bArr[12] = (byte) length;
        int iA = length > 0 ? 13 + com.igexin.c.a.b.g.a(byteArray, bArr, 13, length) : 13;
        byte[] bytes = this.e.getBytes();
        int i2 = iA + 1;
        bArr[iA] = (byte) bytes.length;
        System.arraycopy(bytes, 0, bArr, i2, bytes.length);
        com.igexin.c.a.b.g.b(this.f, bArr, i2 + bytes.length);
        return bArr;
    }
}
