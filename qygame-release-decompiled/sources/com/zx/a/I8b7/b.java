package com.zx.a.I8b7;

import android.database.sqlite.SQLiteOpenHelper;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public abstract class b {
    public SQLiteOpenHelper b;
    public Map<Class<? extends c>, c> a = new HashMap();
    public AtomicBoolean c = new AtomicBoolean(false);

    public abstract String a();

    public final SQLiteOpenHelper b() {
        SQLiteOpenHelper sQLiteOpenHelper = this.b;
        if (sQLiteOpenHelper != null) {
            return sQLiteOpenHelper;
        }
        StringBuilder sbA = j3.a("db ");
        sbA.append(a());
        sbA.append(" has not been initialized");
        throw new RuntimeException(sbA.toString());
    }

    public abstract int c();
}
