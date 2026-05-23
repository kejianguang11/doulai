package com.igexin.push.d.c;

/* JADX INFO: loaded from: classes.dex */
public final class j extends c {
    public byte a;
    public Object b;

    @Override // com.igexin.push.d.c.c
    public final void a(byte[] bArr) {
    }

    @Override // com.igexin.push.d.c.c
    public final byte[] b() {
        byte[] bytes = (this.a == 1 || this.a == 2 || (this.a != 3 && (this.a == 4 || this.a == 5 || this.a == 6 || this.a == 7))) ? ((String) this.b).getBytes() : null;
        if (bytes == null) {
            return null;
        }
        byte[] bArr = new byte[bytes.length + 2];
        bArr[0] = this.a;
        bArr[1] = (byte) bytes.length;
        System.arraycopy(bytes, 0, bArr, 2, bytes.length);
        return bArr;
    }
}
