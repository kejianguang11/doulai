package com.igexin.push.d.c;

/* JADX INFO: loaded from: classes.dex */
public class n extends c {
    public static final int a = 26;
    private static final String j = "com.igexin.push.d.c.n";
    public int b;
    public int c;
    public long d;
    public String e;
    public Object f;
    public Object g;
    public String h;
    public String i = com.alipay.sdk.sys.a.m;

    public n() {
        this.m = 26;
    }

    private boolean e() {
        return this.c == 64;
    }

    private boolean f() {
        return this.c == 192;
    }

    private void g() {
        this.c = 128;
    }

    private void h() {
        this.c = 64;
    }

    private void i() {
        this.c = 192;
    }

    @Override // com.igexin.push.d.c.c, com.igexin.c.a.d.a.a
    public final void a() {
        this.e = null;
        this.g = null;
        this.f = null;
        this.i = null;
    }

    @Override // com.igexin.push.d.c.c
    public final void a(byte[] bArr) {
        int i;
        int i2;
        this.b = com.igexin.c.a.b.g.b(bArr, 0);
        this.c = bArr[2] & 192;
        this.i = a(bArr[2]);
        this.d = com.igexin.c.a.b.g.d(bArr, 3);
        int i3 = bArr[11] & 255;
        try {
            this.e = new String(bArr, 12, i3, this.i);
        } catch (Exception e) {
            this.e = "";
            com.igexin.c.a.c.a.a(e);
        }
        int i4 = i3 + 12;
        int i5 = 0;
        while (true) {
            i = i5 | (bArr[i4] & 127);
            if ((bArr[i4] & 128) == 0) {
                break;
            }
            i5 = i << 7;
            i4++;
        }
        int i6 = i4 + 1;
        if (i > 0) {
            if (this.c == 192) {
                this.f = new byte[i];
                System.arraycopy(bArr, i6, this.f, 0, i);
            } else {
                try {
                    this.f = new String(bArr, i6, i, this.i);
                } catch (Exception e2) {
                    com.igexin.c.a.c.a.a(e2);
                }
            }
        }
        int i7 = i6 + i;
        int i8 = 0;
        while (true) {
            i2 = i8 | (bArr[i7] & 127);
            if ((bArr[i7] & 128) == 0) {
                break;
            }
            i8 = i2 << 7;
            i7++;
        }
        int i9 = i7 + 1;
        if (i2 > 0) {
            this.g = new byte[i2];
            System.arraycopy(bArr, i9, this.g, 0, i2);
        }
        int i10 = i9 + i2;
        if (bArr.length > i10) {
            try {
                this.h = new String(bArr, i10 + 1, bArr[i10] & 255, this.i);
            } catch (Exception e3) {
                com.igexin.c.a.c.a.a(e3);
            }
        }
    }

    @Override // com.igexin.push.d.c.c
    public final byte[] b() {
        byte[] bArr;
        byte[] bytes;
        byte[] bytes2;
        byte[] bytes3;
        byte[] bArr2;
        int length;
        int length2;
        byte[] bArrA;
        byte[] bArrA2;
        try {
            bytes = this.e.getBytes(this.i);
            bytes2 = this.h.getBytes(this.i);
            bytes3 = !"".equals(this.f) ? this.c == 192 ? (byte[]) this.f : ((String) this.f).getBytes(this.i) : null;
            bArr2 = this.g != null ? (byte[]) this.g : null;
            length = bytes3 == null ? 0 : bytes3.length;
            length2 = bArr2 == null ? 0 : bArr2.length;
            bArrA = com.igexin.c.a.b.g.a(length);
            bArrA2 = com.igexin.c.a.b.g.a(length2);
            bArr = new byte[bytes.length + 13 + bArrA.length + length + bArrA2.length + length2 + bytes2.length];
        } catch (Exception e) {
            e = e;
            bArr = null;
        }
        try {
            com.igexin.c.a.b.g.b(this.b, bArr, 0);
            bArr[2] = (byte) (this.c | a(this.i));
            com.igexin.c.a.b.g.a(this.d, bArr, 3);
            bArr[11] = (byte) bytes.length;
            int iA = com.igexin.c.a.b.g.a(bytes, bArr, 12, bytes.length) + 12;
            int iA2 = iA + com.igexin.c.a.b.g.a(bArrA, bArr, iA, bArrA.length);
            if (length > 0) {
                iA2 += com.igexin.c.a.b.g.a(bytes3, bArr, iA2, length);
            }
            int iA3 = iA2 + com.igexin.c.a.b.g.a(bArrA2, bArr, iA2, bArrA2.length);
            if (length2 > 0) {
                iA3 += com.igexin.c.a.b.g.a(bArr2, bArr, iA3, length2);
            }
            bArr[iA3] = (byte) bytes2.length;
            com.igexin.c.a.b.g.a(bytes2, bArr, iA3 + 1, bytes2.length);
        } catch (Exception e2) {
            e = e2;
            com.igexin.c.a.c.a.a(e);
        }
        return bArr;
    }

    public final boolean d() {
        return this.c == 128;
    }

    public String toString() {
        return "{\"msgID\":" + this.b + ", \"msgType\":" + this.c + ", \"msgDate\":" + this.d + ", \"msgAddress\":\"" + this.e + "\", \"msgContent\":" + this.f + ", \"msgExtra\":" + this.g + ", \"msgCID\":\"" + this.h + "\", \"charset\":\"" + this.i + "\", \"command\":" + this.m + ", \"property\":" + ((int) this.n) + ", \"expandProperty\":" + ((int) this.o) + ", \"tag\":\"" + this.y + "\"}";
    }
}
