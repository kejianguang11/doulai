package com.getui.gtc.e;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Base64;
import com.getui.gtc.base.crypt.SecureCryptTools;
import com.getui.gtc.base.db.AbstractTable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class d extends AbstractTable {
    public String a;
    public String b;
    public long c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;
    public String k;
    public String l;
    public String m;
    public long n;
    public long o;
    public long p;
    public long q;
    public final Set<String> r = new HashSet();

    /* JADX WARN: Removed duplicated region for block: B:26:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String a(int i) throws Throwable {
        Cursor cursor = null;
        try {
            try {
                Cursor cursorQuery = getReadableDatabase().query("r", new String[]{"a", "b"}, "a=?", new String[]{String.valueOf(i)}, null, null, null);
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.moveToNext()) {
                            String string = cursorQuery.getString(1);
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            return string;
                        }
                    } catch (Throwable th) {
                        th = th;
                        cursor = cursorQuery;
                        com.getui.gtc.i.c.a.b(th);
                        if (cursor == null) {
                            return "";
                        }
                        cursor.close();
                        return "";
                        if (cursor != null) {
                        }
                        throw th;
                    }
                }
                if (cursorQuery == null) {
                    return "";
                }
                cursorQuery.close();
                return "";
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public final JSONObject a() {
        try {
            String strA = a(18);
            if (TextUtils.isEmpty(strA)) {
                return null;
            }
            return new JSONObject(new String(SecureCryptTools.getInstance().decrypt(Base64.decode(strA, 0))));
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.c(th);
            return null;
        }
    }

    public final void a(String str) {
        if (a(23, str)) {
            this.g = str;
        }
    }

    public final void a(Collection<String> collection) {
        if (collection.size() <= 0) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.r);
        arrayList.addAll(collection);
        StringBuilder sb = new StringBuilder();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            sb.append((String) arrayList.get(i));
            if (i < size - 1) {
                sb.append(com.igexin.push.core.b.an);
            }
        }
        if (a(8, sb.toString())) {
            this.r.addAll(collection);
        }
    }

    public final void a(JSONObject jSONObject) {
        if (jSONObject != null) {
            try {
                if (jSONObject.length() != 0) {
                    a(18, Base64.encodeToString(SecureCryptTools.getInstance().encrypt(jSONObject.toString().getBytes()), 0));
                    return;
                }
            } catch (Throwable th) {
                com.getui.gtc.i.c.a.c(th);
                return;
            }
        }
        a(18, "");
    }

    public final boolean a(int i, long j) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("a", Integer.valueOf(i));
        contentValues.put("b", Long.valueOf(j));
        return replace(null, contentValues) != -1;
    }

    public final boolean a(int i, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("a", Integer.valueOf(i));
        contentValues.put("b", str);
        return replace(null, contentValues) != -1;
    }

    public final JSONObject b() {
        try {
            String strA = a(17);
            if (!TextUtils.isEmpty(strA)) {
                return new JSONObject(new String(SecureCryptTools.getInstance().decrypt(Base64.decode(strA, 0))));
            }
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.c(th);
        }
        return new JSONObject();
    }

    public final void b(String str) {
        if (a(24, str)) {
            this.h = str;
        }
    }

    public final void b(JSONObject jSONObject) {
        try {
            a(17, Base64.encodeToString(SecureCryptTools.getInstance().encrypt(jSONObject.toString().getBytes()), 0));
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.c(th);
        }
    }

    public final void c(String str) {
        if (a(10, str)) {
            this.j = str;
        }
    }

    @Override // com.getui.gtc.base.db.AbstractTable
    public String createSql() {
        return "CREATE TABLE IF NOT EXISTS r (a TEXT PRIMARY KEY, b TEXT)";
    }

    public final void d(String str) {
        try {
            if (a(11, Base64.encodeToString(SecureCryptTools.getInstance().encrypt(str.getBytes()), 0))) {
                this.k = str;
            }
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.c(th);
        }
    }

    public final void e(String str) {
        try {
            if (a(12, Base64.encodeToString(SecureCryptTools.getInstance().encrypt(str.getBytes()), 0))) {
                this.l = str;
            }
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.c(th);
        }
    }

    public final void f(String str) {
        if (a(7, str)) {
            this.a = str;
        }
    }

    public final void g(String str) {
        if (a(20, str)) {
            this.b = str;
        }
    }

    @Override // com.getui.gtc.base.db.AbstractTable
    public String getTableName() {
        return "r";
    }

    public final void h(String str) {
        if (TextUtils.isEmpty(str) || !this.r.contains(str)) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.r);
        arrayList.remove(str);
        StringBuilder sb = new StringBuilder();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            sb.append((String) arrayList.get(i));
            if (i < size - 1) {
                sb.append(com.igexin.push.core.b.an);
            }
        }
        if (a(8, sb.toString())) {
            this.r.remove(str);
        }
    }

    @Override // com.getui.gtc.base.db.AbstractTable
    public void initCache() throws Throwable {
        Cursor cursorQuery;
        Cursor cursor = null;
        try {
            try {
                cursorQuery = getReadableDatabase().query("r", new String[]{"a", "b"}, null, null, null, null, null);
                if (cursorQuery != null) {
                    while (cursorQuery.moveToNext()) {
                        try {
                            switch (cursorQuery.getInt(0)) {
                                case 4:
                                    this.d = cursorQuery.getString(1);
                                    break;
                                case 6:
                                    this.c = cursorQuery.getLong(1);
                                    break;
                                case 7:
                                    this.a = cursorQuery.getString(1);
                                    break;
                                case 8:
                                    String string = cursorQuery.getString(1);
                                    if (!TextUtils.isEmpty(string)) {
                                        this.r.addAll(Arrays.asList(string.split(com.igexin.push.core.b.an)));
                                    }
                                    break;
                                case 9:
                                    this.i = cursorQuery.getString(1);
                                    break;
                                case 10:
                                    this.j = cursorQuery.getString(1);
                                    break;
                                case 11:
                                    try {
                                        String string2 = cursorQuery.getString(1);
                                        if (!TextUtils.isEmpty(string2)) {
                                            this.k = new String(SecureCryptTools.getInstance().decrypt(Base64.decode(string2, 0)));
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        com.getui.gtc.i.c.a.c(th);
                                    }
                                    break;
                                case 12:
                                    try {
                                        String string3 = cursorQuery.getString(1);
                                        if (!TextUtils.isEmpty(string3)) {
                                            this.l = new String(SecureCryptTools.getInstance().decrypt(Base64.decode(string3, 0)));
                                        }
                                    } catch (Throwable th2) {
                                        th = th2;
                                        com.getui.gtc.i.c.a.c(th);
                                    }
                                    break;
                                case 13:
                                    try {
                                        String string4 = cursorQuery.getString(1);
                                        if (!TextUtils.isEmpty(string4)) {
                                            this.m = new String(SecureCryptTools.getInstance().decrypt(Base64.decode(string4, 0)));
                                        }
                                    } catch (Throwable th3) {
                                        th = th3;
                                        com.getui.gtc.i.c.a.c(th);
                                    }
                                    break;
                                case 14:
                                    this.n = cursorQuery.getLong(1);
                                    break;
                                case 15:
                                    this.o = cursorQuery.getLong(1);
                                    break;
                                case 16:
                                    this.p = cursorQuery.getLong(1);
                                    break;
                                case 19:
                                    this.q = cursorQuery.getLong(1);
                                    this.p = cursorQuery.getLong(1);
                                    break;
                                case 20:
                                    this.b = cursorQuery.getString(1);
                                    break;
                                case 21:
                                    this.e = cursorQuery.getString(1);
                                    break;
                                case 22:
                                    this.f = cursorQuery.getString(1);
                                    break;
                                case 23:
                                    this.g = cursorQuery.getString(1);
                                    break;
                                case 24:
                                    this.h = cursorQuery.getString(1);
                                    break;
                            }
                        } catch (Exception e) {
                            e = e;
                            cursor = cursorQuery;
                            com.getui.gtc.i.c.a.b(e);
                            if (cursor != null) {
                                cursor.close();
                                return;
                            }
                            return;
                        } catch (Throwable th4) {
                            th = th4;
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            throw th;
                        }
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th5) {
            th = th5;
            cursorQuery = cursor;
        }
    }
}
