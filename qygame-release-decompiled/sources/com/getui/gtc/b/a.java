package com.getui.gtc.b;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.igexin.push.core.d.d;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    private static C0008a a;

    /* JADX INFO: renamed from: com.getui.gtc.b.a$a, reason: collision with other inner class name */
    static class C0008a extends SQLiteOpenHelper {
        C0008a(Context context) {
            super(context, "gtc.db", (SQLiteDatabase.CursorFactory) null, 5);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 2, insn: 0x0086: MOVE (r1 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:42:0x0086 */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0081 A[PHI: r0
      0x0081: PHI (r0v9 android.database.sqlite.SQLiteDatabase) = (r0v8 android.database.sqlite.SQLiteDatabase), (r0v11 android.database.sqlite.SQLiteDatabase) binds: [B:38:0x007f, B:25:0x0069] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String a(Context context) throws Throwable {
        Cursor cursorQuery;
        Cursor cursor;
        byte[] blob;
        Cursor cursor2 = null;
        if (!context.getDatabasePath("gtc.db").exists()) {
            return null;
        }
        C0008a c0008a = a;
        SQLiteDatabase readableDatabase = c0008a;
        if (c0008a == null) {
            C0008a c0008a2 = new C0008a(context);
            a = c0008a2;
            readableDatabase = c0008a2;
        }
        try {
            try {
                readableDatabase = a.getReadableDatabase();
            } catch (Throwable th) {
                th = th;
                cursor2 = cursor;
            }
        } catch (Exception e) {
            e = e;
            readableDatabase = 0;
            cursorQuery = null;
        } catch (Throwable th2) {
            th = th2;
            readableDatabase = 0;
        }
        try {
            cursorQuery = readableDatabase.query(d.e, new String[]{"b"}, "a=?", new String[]{"100"}, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToNext() && (blob = cursorQuery.getBlob(0)) != null) {
                        String str = new String(com.getui.gtc.i.a.b.a(blob, com.getui.gtc.i.a.a.a(context.getPackageName())));
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        if (readableDatabase != 0) {
                            readableDatabase.close();
                        }
                        return str;
                    }
                } catch (Exception e2) {
                    e = e2;
                    com.getui.gtc.i.c.a.b(e);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    if (readableDatabase != 0) {
                    }
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        } catch (Exception e3) {
            e = e3;
            cursorQuery = null;
        } catch (Throwable th3) {
            th = th3;
            if (cursor2 != null) {
                cursor2.close();
            }
            if (readableDatabase != 0) {
                readableDatabase.close();
            }
            throw th;
        }
        if (readableDatabase != 0) {
            readableDatabase.close();
        }
        return null;
    }
}
