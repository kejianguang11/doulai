package com.loc;

/* JADX INFO: loaded from: classes.dex */
public final class bx extends ca {
    private StringBuilder a;
    private boolean b;

    public bx() {
        this.a = new StringBuilder();
        this.b = true;
    }

    public bx(ca caVar) {
        super(caVar);
        this.a = new StringBuilder();
        this.b = true;
    }

    @Override // com.loc.ca
    protected final byte[] a(byte[] bArr) {
        byte[] bArrA = x.a(this.a.toString());
        this.d = bArrA;
        this.b = true;
        this.a.delete(0, this.a.length());
        return bArrA;
    }

    @Override // com.loc.ca
    public final void b(byte[] bArr) {
        String strA = x.a(bArr);
        if (this.b) {
            this.b = false;
        } else {
            this.a.append(com.igexin.push.core.b.an);
        }
        StringBuilder sb = this.a;
        sb.append("{\"log\":\"");
        sb.append(strA);
        sb.append("\"}");
    }
}
