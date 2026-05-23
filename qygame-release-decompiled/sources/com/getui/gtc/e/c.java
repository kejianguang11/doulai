package com.getui.gtc.e;

import com.getui.gtc.base.GtcProvider;
import com.getui.gtc.base.db.DbManager;

/* JADX INFO: loaded from: classes.dex */
public final class c {
    public d a;
    public com.getui.gtc.e.a b;
    private e c;

    public static class a {
        private static final c a = new c(0);
    }

    private c() {
        try {
            DbManager.init(GtcProvider.context(), b.class, com.getui.gtc.e.a.class, d.class, e.class);
            this.a = (d) DbManager.getTable(b.class, d.class);
            this.c = (e) DbManager.getTable(b.class, e.class);
            this.b = (com.getui.gtc.e.a) DbManager.getTable(b.class, com.getui.gtc.e.a.class);
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.c(th);
        }
    }

    /* synthetic */ c(byte b) {
        this();
    }
}
