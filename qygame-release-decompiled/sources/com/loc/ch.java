package com.loc;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public final class ch extends cg {
    private Context a;
    private boolean b;

    public ch(Context context) {
        this.b = false;
        this.a = context;
        this.b = false;
    }

    @Override // com.loc.cg
    protected final boolean a() {
        return o.o(this.a) == 1 || this.b;
    }
}
