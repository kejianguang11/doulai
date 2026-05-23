package com.igexin.push.d;

/* JADX INFO: loaded from: classes.dex */
public final class d implements b {
    public static final String a = "NormalModel";

    @Override // com.igexin.push.d.b
    public final long a() {
        String strConcat;
        long j;
        long j2;
        long j3;
        boolean zA = com.igexin.push.g.c.a();
        com.igexin.push.core.e.n = com.igexin.push.g.c.e();
        boolean z = com.igexin.push.core.e.p;
        boolean z2 = com.igexin.push.core.e.s;
        boolean z3 = com.igexin.push.core.e.n;
        com.igexin.c.a.c.a.a("NormalModel|isSdkOn = " + com.igexin.push.core.e.p + " isPushOn = " + com.igexin.push.core.e.s + " isBlockEndTime = " + zA + " isNetworkAvailable = " + com.igexin.push.core.e.n, new Object[0]);
        boolean z4 = com.igexin.push.core.e.n;
        long j4 = com.igexin.push.config.c.g;
        if (z4 && com.igexin.push.core.e.p && com.igexin.push.core.e.s && zA) {
            if (com.igexin.push.core.e.O <= 0) {
                j3 = 1;
            } else {
                if (com.igexin.push.core.e.O <= 300) {
                    j = com.igexin.push.core.e.O;
                    j2 = 150;
                } else if (com.igexin.push.core.e.O <= com.igexin.push.config.c.i) {
                    j = com.igexin.push.core.e.O;
                    j2 = 500;
                } else if (com.igexin.push.core.e.O <= com.igexin.push.config.c.k) {
                    j = com.igexin.push.core.e.O;
                    j2 = 1500;
                } else {
                    j = com.igexin.push.core.e.O;
                    j2 = com.igexin.push.config.c.l;
                }
                j3 = j + j2;
            }
            com.igexin.push.core.e.O = j3;
            if (com.igexin.push.core.e.O > com.igexin.push.config.c.g) {
                com.igexin.push.core.e.O = com.igexin.push.config.c.g;
            }
            j4 = com.igexin.push.core.e.O;
            strConcat = "NormalModel|after add auto reconnect delay time = ".concat(String.valueOf(j4));
        } else {
            com.igexin.c.a.c.a.a(a, "reconnect stop, interval= 3min ++++++");
            strConcat = "NormalModel|reconnect stop, interval= 3min ++++";
        }
        com.igexin.c.a.c.a.a(strConcat, new Object[0]);
        return j4;
    }
}
