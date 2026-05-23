package com.zx.a.I8b7;

import com.zx.module.base.Listener;

/* JADX INFO: loaded from: classes.dex */
public class u3 implements Listener {
    public final /* synthetic */ Listener a;
    public final /* synthetic */ y3 b;

    public u3(y3 y3Var, Listener listener) {
        this.b = y3Var;
        this.a = listener;
    }

    @Override // com.zx.module.base.Listener
    public void onMessage(String str, String str2) {
        if (str.equals("zxid") || str.equals("MESSAGE_ON_ZXID_RECEIVED")) {
            this.b.a.set(false);
        }
        this.a.onMessage(str, str2);
    }
}
