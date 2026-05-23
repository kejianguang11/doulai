package com.igexin.push.d.c;

/* JADX INFO: loaded from: classes.dex */
public final class m extends c {
    public static final int a = 37;
    public boolean b;
    public boolean c;
    public String d;
    public String e;
    public long f;

    public m() {
        this.m = 37;
    }

    @Override // com.igexin.push.d.c.c, com.igexin.c.a.d.a.a
    public final void a() {
        this.e = null;
        this.d = null;
        this.f = 0L;
    }

    @Override // com.igexin.push.d.c.c
    public final void a(byte[] bArr) {
        byte b = bArr[0];
        int i = 1;
        this.b = (b & 64) != 0;
        this.c = (b & 128) != 0;
        if (this.c) {
            this.d = a(b);
            int iB = com.igexin.c.a.b.g.b(bArr, 1);
            i = 1 + iB + 2;
            try {
                this.e = new String(bArr, 3, iB, this.d);
            } catch (Exception e) {
                com.igexin.c.a.c.a.a(e);
            }
        }
        if (bArr.length > i) {
            this.f = com.igexin.c.a.b.g.d(bArr, i);
        }
    }

    @Override // com.igexin.push.d.c.c
    public final byte[] b() {
        int i;
        int i2;
        byte[] bytes;
        byte bA = this.b ? (byte) 64 : (byte) 0;
        int iB = 1;
        byte[] bArr = null;
        if (this.c) {
            byte b = (byte) (bA | 128);
            try {
                bytes = this.e.getBytes(this.d);
            } catch (Exception e) {
                e = e;
            }
            try {
                int length = bytes.length;
                int i3 = length + 3;
                bArr = bytes;
                i2 = i3;
                i = length;
            } catch (Exception e2) {
                bArr = bytes;
                e = e2;
                com.igexin.c.a.c.a.a(e);
                i2 = 3;
                i = 0;
            }
            bA = (byte) (b | a(this.d));
        } else {
            i = 0;
            i2 = 1;
        }
        byte[] bArr2 = new byte[i2 + 8];
        bArr2[0] = bA;
        if (this.c) {
            iB = com.igexin.c.a.b.g.b(i, bArr2, 1);
            if (bArr != null) {
                iB = com.igexin.c.a.b.g.a(bArr, bArr2, 2, i) + 2;
            }
        }
        com.igexin.c.a.b.g.a(this.f, bArr2, iB);
        return bArr2;
    }
}
