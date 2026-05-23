package com.alipay.sdk.tid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class a extends SQLiteOpenHelper {
    private static final String a = "msp.db";
    private static final int b = 1;
    private WeakReference<Context> c;

    public a(Context context) {
        super(context, a, (SQLiteDatabase.CursorFactory) null, 1);
        this.c = new WeakReference<>(context);
    }

    private List<String> a() throws Throwable {
        SQLiteDatabase readableDatabase;
        Cursor cursorRawQuery;
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            readableDatabase = getReadableDatabase();
            try {
                cursorRawQuery = readableDatabase.rawQuery("select tid from tb_tid", null);
                while (cursorRawQuery.moveToNext()) {
                    try {
                        String string = cursorRawQuery.getString(0);
                        if (!TextUtils.isEmpty(string)) {
                            arrayList.add(com.alipay.sdk.encrypt.b.a(2, string, com.alipay.sdk.util.a.c(this.c.get())));
                        }
                    } catch (Exception unused) {
                        cursor = cursorRawQuery;
                        if (cursor != null) {
                            cursor.close();
                        }
                        if (readableDatabase != null && readableDatabase.isOpen()) {
                        }
                        return arrayList;
                    } catch (Throwable th) {
                        th = th;
                        if (cursorRawQuery != null) {
                            cursorRawQuery.close();
                        }
                        if (readableDatabase != null && readableDatabase.isOpen()) {
                            readableDatabase.close();
                        }
                        throw th;
                    }
                }
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
            } catch (Exception unused2) {
            } catch (Throwable th2) {
                th = th2;
                cursorRawQuery = null;
            }
        } catch (Exception unused3) {
            readableDatabase = null;
        } catch (Throwable th3) {
            th = th3;
            readableDatabase = null;
            cursorRawQuery = null;
        }
        if (readableDatabase != null && readableDatabase.isOpen()) {
            readableDatabase.close();
        }
        return arrayList;
    }

    private static void a(SQLiteDatabase sQLiteDatabase) {
        Cursor cursorRawQuery = sQLiteDatabase.rawQuery("select name from tb_tid where tid!='' order by dt asc", null);
        if (cursorRawQuery.getCount() <= 14) {
            cursorRawQuery.close();
            return;
        }
        int count = cursorRawQuery.getCount() - 14;
        String[] strArr = new String[count];
        if (cursorRawQuery.moveToFirst()) {
            int i = 0;
            do {
                strArr[i] = cursorRawQuery.getString(0);
                i++;
                if (!cursorRawQuery.moveToNext()) {
                    break;
                }
            } while (count > i);
        }
        cursorRawQuery.close();
        for (int i2 = 0; i2 < count; i2++) {
            if (!TextUtils.isEmpty(strArr[i2])) {
                a(sQLiteDatabase, strArr[i2]);
            }
        }
    }

    static void a(SQLiteDatabase sQLiteDatabase, String str) {
        try {
            sQLiteDatabase.delete("tb_tid", "name=?", new String[]{str});
        } catch (Exception unused) {
        }
    }

    private static boolean a(SQLiteDatabase sQLiteDatabase, String str, String str2) throws Throwable {
        Cursor cursorRawQuery;
        int i;
        Cursor cursor = null;
        try {
            cursorRawQuery = sQLiteDatabase.rawQuery("select count(*) from tb_tid where name=?", new String[]{c(str, str2)});
            try {
                i = cursorRawQuery.moveToFirst() ? cursorRawQuery.getInt(0) : 0;
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
            } catch (Exception unused) {
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                i = 0;
            } catch (Throwable th) {
                th = th;
                cursor = cursorRawQuery;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Exception unused2) {
            cursorRawQuery = null;
        } catch (Throwable th2) {
            th = th2;
        }
        return i > 0;
    }

    private void b(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, String str4) {
        String strA = com.alipay.sdk.encrypt.b.a(1, str3, com.alipay.sdk.util.a.c(this.c.get()));
        String strC = c(str, str2);
        sQLiteDatabase.execSQL("insert into tb_tid (name, tid, key_tid, dt) values (?, ?, ?, datetime('now', 'localtime'))", new Object[]{strC, strA, str4});
        Cursor cursorRawQuery = sQLiteDatabase.rawQuery("select name from tb_tid where tid!='' order by dt asc", null);
        if (cursorRawQuery.getCount() <= 14) {
            cursorRawQuery.close();
            return;
        }
        int count = cursorRawQuery.getCount() - 14;
        String[] strArr = new String[count];
        if (cursorRawQuery.moveToFirst()) {
            int i = 0;
            do {
                strArr[i] = cursorRawQuery.getString(0);
                i++;
                if (!cursorRawQuery.moveToNext()) {
                    break;
                }
            } while (count > i);
        }
        cursorRawQuery.close();
        for (int i2 = 0; i2 < count; i2++) {
            if (!TextUtils.isEmpty(strArr[i2])) {
                a(sQLiteDatabase, strArr[i2]);
            }
        }
    }

    static String c(String str, String str2) {
        return str + str2;
    }

    private void d(String str, String str2) throws Throwable {
        SQLiteDatabase writableDatabase;
        SQLiteDatabase sQLiteDatabase = null;
        try {
            writableDatabase = getWritableDatabase();
            try {
                a(writableDatabase, str, str2, "", "");
                a(writableDatabase, c(str, str2));
                if (writableDatabase == null || !writableDatabase.isOpen()) {
                    return;
                }
                writableDatabase.close();
            } catch (Exception unused) {
                sQLiteDatabase = writableDatabase;
                if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
                    return;
                }
                sQLiteDatabase.close();
            } catch (Throwable th) {
                th = th;
                if (writableDatabase != null && writableDatabase.isOpen()) {
                    writableDatabase.close();
                }
                throw th;
            }
        } catch (Exception unused2) {
        } catch (Throwable th2) {
            th = th2;
            writableDatabase = null;
        }
    }

    private long e(String str, String str2) throws Throwable {
        SQLiteDatabase readableDatabase;
        Cursor cursorRawQuery;
        Cursor cursor = null;
        try {
            readableDatabase = getReadableDatabase();
            try {
                cursorRawQuery = readableDatabase.rawQuery("select dt from tb_tid where name=?", new String[]{c(str, str2)});
                try {
                    time = cursorRawQuery.moveToFirst() ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(cursorRawQuery.getString(0)).getTime() : 0L;
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                } catch (Exception unused) {
                    cursor = cursorRawQuery;
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (readableDatabase != null && readableDatabase.isOpen()) {
                    }
                    return time;
                } catch (Throwable th) {
                    th = th;
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    if (readableDatabase != null && readableDatabase.isOpen()) {
                        readableDatabase.close();
                    }
                    throw th;
                }
            } catch (Exception unused2) {
            } catch (Throwable th2) {
                th = th2;
                cursorRawQuery = null;
            }
        } catch (Exception unused3) {
            readableDatabase = null;
        } catch (Throwable th3) {
            th = th3;
            readableDatabase = null;
            cursorRawQuery = null;
        }
        if (readableDatabase != null && readableDatabase.isOpen()) {
            readableDatabase.close();
        }
        return time;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:48:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String a(String str, String str2) throws Throwable {
        SQLiteDatabase readableDatabase;
        Cursor cursorRawQuery;
        Cursor cursor = null;
        cursor = null;
        string = null;
        string = null;
        String string = null;
        try {
            readableDatabase = getReadableDatabase();
            try {
                cursorRawQuery = readableDatabase.rawQuery("select tid from tb_tid where name=?", new String[]{c(str, str2)});
                try {
                    string = cursorRawQuery.moveToFirst() ? cursorRawQuery.getString(0) : null;
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                } catch (Exception unused) {
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    if (readableDatabase != null && readableDatabase.isOpen()) {
                    }
                    if (TextUtils.isEmpty(string)) {
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = cursorRawQuery;
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (readableDatabase != null && readableDatabase.isOpen()) {
                        readableDatabase.close();
                    }
                    throw th;
                }
            } catch (Exception unused2) {
                cursorRawQuery = null;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception unused3) {
            cursorRawQuery = null;
            readableDatabase = null;
        } catch (Throwable th3) {
            th = th3;
            readableDatabase = null;
        }
        if (readableDatabase != null && readableDatabase.isOpen()) {
            readableDatabase.close();
        }
        return TextUtils.isEmpty(string) ? com.alipay.sdk.encrypt.b.a(2, string, com.alipay.sdk.util.a.c(this.c.get())) : string;
    }

    final void a(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, String str4) {
        sQLiteDatabase.execSQL("update tb_tid set tid=?, key_tid=?, dt=datetime('now', 'localtime') where name=?", new Object[]{com.alipay.sdk.encrypt.b.a(1, str3, com.alipay.sdk.util.a.c(this.c.get())), str4, c(str, str2)});
    }

    public final void a(String str, String str2, String str3, String str4) throws Throwable {
        SQLiteDatabase writableDatabase;
        SQLiteDatabase sQLiteDatabase = null;
        try {
            writableDatabase = getWritableDatabase();
            try {
                if (a(writableDatabase, str, str2)) {
                    a(writableDatabase, str, str2, str3, str4);
                } else {
                    String strA = com.alipay.sdk.encrypt.b.a(1, str3, com.alipay.sdk.util.a.c(this.c.get()));
                    String strC = c(str, str2);
                    writableDatabase.execSQL("insert into tb_tid (name, tid, key_tid, dt) values (?, ?, ?, datetime('now', 'localtime'))", new Object[]{strC, strA, str4});
                    Cursor cursorRawQuery = writableDatabase.rawQuery("select name from tb_tid where tid!='' order by dt asc", null);
                    if (cursorRawQuery.getCount() <= 14) {
                        cursorRawQuery.close();
                    } else {
                        int count = cursorRawQuery.getCount() - 14;
                        String[] strArr = new String[count];
                        if (cursorRawQuery.moveToFirst()) {
                            int i = 0;
                            do {
                                strArr[i] = cursorRawQuery.getString(0);
                                i++;
                                if (!cursorRawQuery.moveToNext()) {
                                    break;
                                }
                            } while (count > i);
                        }
                        cursorRawQuery.close();
                        for (int i2 = 0; i2 < count; i2++) {
                            if (!TextUtils.isEmpty(strArr[i2])) {
                                a(writableDatabase, strArr[i2]);
                            }
                        }
                    }
                }
                if (writableDatabase == null || !writableDatabase.isOpen()) {
                    return;
                }
                writableDatabase.close();
            } catch (Exception unused) {
                sQLiteDatabase = writableDatabase;
                if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
                    return;
                }
                sQLiteDatabase.close();
            } catch (Throwable th) {
                th = th;
                if (writableDatabase != null && writableDatabase.isOpen()) {
                    writableDatabase.close();
                }
                throw th;
            }
        } catch (Exception unused2) {
        } catch (Throwable th2) {
            th = th2;
            writableDatabase = null;
        }
    }

    public final String b(String str, String str2) throws Throwable {
        SQLiteDatabase readableDatabase;
        Cursor cursorRawQuery;
        Cursor cursor = null;
        cursor = null;
        string = null;
        string = null;
        String string = null;
        try {
            readableDatabase = getReadableDatabase();
            try {
                cursorRawQuery = readableDatabase.rawQuery("select key_tid from tb_tid where name=?", new String[]{c(str, str2)});
                try {
                    string = cursorRawQuery.moveToFirst() ? cursorRawQuery.getString(0) : null;
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                } catch (Exception unused) {
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    if (readableDatabase != null && readableDatabase.isOpen()) {
                    }
                    return string;
                } catch (Throwable th) {
                    th = th;
                    cursor = cursorRawQuery;
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (readableDatabase != null && readableDatabase.isOpen()) {
                        readableDatabase.close();
                    }
                    throw th;
                }
            } catch (Exception unused2) {
                cursorRawQuery = null;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception unused3) {
            readableDatabase = null;
            cursorRawQuery = null;
        } catch (Throwable th3) {
            th = th3;
            readableDatabase = null;
        }
        if (readableDatabase != null && readableDatabase.isOpen()) {
            readableDatabase.close();
        }
        return string;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("create table if not exists tb_tid (name text primary key, tid text, key_tid text, dt datetime);");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("drop table if exists tb_tid");
        onCreate(sQLiteDatabase);
    }
}
