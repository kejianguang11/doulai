package com.loc;

import android.content.Context;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public final class cf extends cg {
    protected int a;
    protected long b;
    private String d;
    private Context e;

    public cf(Context context, int i, String str, cg cgVar) {
        super(cgVar);
        this.a = i;
        this.d = str;
        this.e = context;
    }

    @Override // com.loc.cg
    public final void a(boolean z) {
        super.a(z);
        if (z) {
            String str = this.d;
            long jCurrentTimeMillis = System.currentTimeMillis();
            this.b = jCurrentTimeMillis;
            al.a(this.e, str, String.valueOf(jCurrentTimeMillis));
        }
    }

    @Override // com.loc.cg
    protected final boolean a() {
        if (this.b == 0) {
            String strA = al.a(this.e, this.d);
            this.b = TextUtils.isEmpty(strA) ? 0L : Long.parseLong(strA);
        }
        return System.currentTimeMillis() - this.b >= ((long) this.a);
    }
}
