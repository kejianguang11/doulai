package com.igexin.c.a.b.a.a;

import android.text.TextUtils;
import com.igexin.c.a.b.a.a.a;
import com.igexin.push.config.SDKUrlConfig;
import java.net.InetSocketAddress;
import java.net.Socket;

/* JADX INFO: loaded from: classes.dex */
public final class b extends a {
    public static final int a = -2037;
    private static final String j = "GS-C";
    private static final int k = 10000;
    private Socket P;
    private com.igexin.c.a.b.a.a.a.d l;

    public b(com.igexin.c.a.b.a.a.a.d dVar) {
        super(-2037, null);
        this.l = dVar;
    }

    @Override // com.igexin.c.a.b.f, com.igexin.c.a.d.f, com.igexin.c.a.d.a.a
    public final void a() {
        super.a();
        com.igexin.c.a.c.a.a("GS-C|sc dispose", new Object[0]);
        if (this.l != null) {
            if (this.g == a.EnumC0029a.c) {
                this.l.a();
            } else if (this.g == a.EnumC0029a.b) {
                if (!TextUtils.isEmpty(this.h)) {
                    this.l.a(new Exception(this.h));
                }
            } else if (this.g == a.EnumC0029a.a && this.P != null) {
                this.l.a(this.P);
            }
        }
        this.l = null;
    }

    @Override // com.igexin.c.a.d.f, com.igexin.c.a.d.a.f
    public final void b_() throws Exception {
        super.b_();
        com.igexin.push.c.c.a().d().a();
        String connectAddress = SDKUrlConfig.getConnectAddress();
        try {
            String[] strArrA = com.igexin.c.a.b.g.a(connectAddress);
            String str = strArrA[1];
            int i = Integer.parseInt(strArrA[2]);
            com.igexin.c.a.c.a.a("GS-C|start connect :  " + connectAddress + " *********", new Object[0]);
            if (this.l != null) {
                this.l.b();
            }
            this.P = new Socket();
            try {
                this.P.connect(new InetSocketAddress(str, i), 10000);
                com.igexin.c.a.c.a.a("GS-C|connected :  " + connectAddress + " #########", new Object[0]);
                com.igexin.c.a.c.a.a("GS-C|local-" + connectAddress + " port:" + i, new Object[0]);
                if (this.g != a.EnumC0029a.c) {
                    this.g = a.EnumC0029a.a;
                }
            } catch (Exception e) {
                if (this.g != a.EnumC0029a.c) {
                    this.g = a.EnumC0029a.b;
                    this.h = e.toString();
                }
            }
            this.f = true;
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
            com.igexin.c.a.c.a.a("GS-C|ips invalid, " + e2.toString(), new Object[0]);
            throw e2;
        }
    }

    @Override // com.igexin.c.a.d.a.e
    public final int c() {
        return -2037;
    }

    @Override // com.igexin.c.a.b.a.a.a
    public final void c_() {
        boolean z = this.i;
        boolean z2 = this.f;
        this.g = a.EnumC0029a.c;
    }
}
