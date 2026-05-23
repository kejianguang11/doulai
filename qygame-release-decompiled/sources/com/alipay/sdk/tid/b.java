package com.alipay.sdk.tid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    private static b c;
    public String a;
    public String b;

    private b() {
    }

    public static synchronized b a() {
        if (c == null) {
            c = new b();
            Context context = com.alipay.sdk.sys.b.a().a;
            a aVar = new a(context);
            String strA = com.alipay.sdk.util.a.a(context).a();
            String strB = com.alipay.sdk.util.a.a(context).b();
            c.a = aVar.a(strA, strB);
            c.b = aVar.b(strA, strB);
            if (TextUtils.isEmpty(c.b)) {
                b bVar = c;
                String hexString = Long.toHexString(System.currentTimeMillis());
                if (hexString.length() > 10) {
                    hexString = hexString.substring(hexString.length() - 10);
                }
                bVar.b = hexString;
            }
            aVar.a(strA, strB, c.a, c.b);
        }
        return c;
    }

    private void a(Context context) {
        a aVar = new a(context);
        try {
            aVar.a(com.alipay.sdk.util.a.a(context).a(), com.alipay.sdk.util.a.a(context).b(), this.a, this.b);
        } catch (Exception unused) {
        } finally {
            aVar.close();
        }
    }

    private void a(String str) {
        this.a = str;
    }

    private String b() {
        return this.a;
    }

    private void b(String str) {
        this.b = str;
    }

    private String c() {
        return this.b;
    }

    private boolean d() {
        return TextUtils.isEmpty(this.a);
    }

    private static void e() throws Throwable {
        SQLiteDatabase writableDatabase;
        Throwable th;
        Context context = com.alipay.sdk.sys.b.a().a;
        String strA = com.alipay.sdk.util.a.a(context).a();
        String strB = com.alipay.sdk.util.a.a(context).b();
        a aVar = new a(context);
        SQLiteDatabase sQLiteDatabase = null;
        try {
            writableDatabase = aVar.getWritableDatabase();
            try {
                aVar.a(writableDatabase, strA, strB, "", "");
                a.a(writableDatabase, a.c(strA, strB));
                if (writableDatabase != null && writableDatabase.isOpen()) {
                    writableDatabase.close();
                }
            } catch (Exception unused) {
                sQLiteDatabase = writableDatabase;
                if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                    sQLiteDatabase.close();
                }
            } catch (Throwable th2) {
                th = th2;
                if (writableDatabase != null && writableDatabase.isOpen()) {
                    writableDatabase.close();
                }
                throw th;
            }
        } catch (Exception unused2) {
        } catch (Throwable th3) {
            writableDatabase = null;
            th = th3;
        }
        aVar.close();
    }

    private static String f() {
        String hexString = Long.toHexString(System.currentTimeMillis());
        return hexString.length() > 10 ? hexString.substring(hexString.length() - 10) : hexString;
    }
}
