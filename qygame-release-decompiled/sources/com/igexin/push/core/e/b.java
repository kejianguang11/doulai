package com.igexin.push.core.e;

import android.database.sqlite.SQLiteDatabase;
import com.igexin.push.g.r;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class b implements a {
    private static b b;
    private Map<String, byte[]> a = new HashMap();

    private b() {
    }

    private static b a() {
        if (b == null) {
            b = new b();
        }
        return b;
    }

    private String a(byte[] bArr) {
        String strA;
        do {
            strA = r.a();
        } while (this.a.containsKey(strA));
        this.a.put(strA, bArr);
        return strA;
    }

    private synchronized byte[] a(String str) {
        byte[] bArr;
        bArr = this.a.get(str);
        if (bArr != null) {
            this.a.remove(str);
        }
        return bArr;
    }

    @Override // com.igexin.push.core.e.a
    public final void a(SQLiteDatabase sQLiteDatabase) {
    }

    @Override // com.igexin.push.core.e.a
    public final void b(SQLiteDatabase sQLiteDatabase) {
    }

    @Override // com.igexin.push.core.e.a
    public final void c(SQLiteDatabase sQLiteDatabase) {
    }
}
