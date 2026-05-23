package com.igexin.push.core.e;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.igexin.push.core.b.k;
import com.igexin.push.core.d;
import java.util.ArrayList;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class c implements a {
    private static c c;
    private ArrayList<k> d;
    private boolean e;
    private String b = "MessageDataManager";
    public int a = -1;

    /* JADX INFO: renamed from: com.igexin.push.core.e.c$1, reason: invalid class name */
    public class AnonymousClass1 extends com.igexin.push.b.d {
        public AnonymousClass1() {
        }

        @Override // com.igexin.push.b.d
        public final void a_() throws Exception {
            Cursor cursorA;
            Cursor cursor = null;
            try {
                try {
                    com.igexin.push.b.b bVar = d.a.a.i;
                    cursorA = bVar.a(com.igexin.push.core.b.Z, new String[]{"status"}, new String[]{"0"}, null, null);
                    if (cursorA != null) {
                        while (cursorA.moveToNext()) {
                            try {
                                try {
                                    byte[] blob = cursorA.getBlob(cursorA.getColumnIndex("info"));
                                    long j = cursorA.getLong(cursorA.getColumnIndex("createtime"));
                                    try {
                                        JSONObject jSONObject = new JSONObject(new String(com.igexin.c.b.a.c(blob)));
                                        String string = jSONObject.getString("taskid");
                                        if (jSONObject.has("condition") && !c.b(jSONObject) && System.currentTimeMillis() - j > 259200000) {
                                            String unused = c.this.b;
                                            com.igexin.c.a.c.a.a(c.this.b + "|del condition taskid = " + string, new Object[0]);
                                            bVar.a(com.igexin.push.core.b.Z, new String[]{"taskid"}, new String[]{string});
                                        }
                                    } catch (Throwable th) {
                                        com.igexin.c.a.c.a.a(th);
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    if (cursorA != null) {
                                        cursorA.close();
                                    }
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                cursor = cursorA;
                                com.igexin.c.a.c.a.a(th);
                                if (cursor != null) {
                                    cursor.close();
                                    return;
                                }
                                return;
                            }
                        }
                    }
                    if (cursorA != null) {
                        cursorA.close();
                    }
                } catch (Throwable th4) {
                    th = th4;
                }
            } catch (Throwable th5) {
                th = th5;
                cursorA = cursor;
            }
        }
    }

    /* JADX INFO: renamed from: com.igexin.push.core.e.c$2, reason: invalid class name */
    public class AnonymousClass2 extends com.igexin.push.b.d {
        public AnonymousClass2() {
        }

        @Override // com.igexin.push.b.d
        public final void a_() throws Exception {
            d.a.a.i.a(com.igexin.push.core.b.Z, "createtime <= ".concat(String.valueOf(System.currentTimeMillis() - com.igexin.push.f.b.d.b)));
        }
    }

    public static c a() {
        if (c == null) {
            synchronized (c.class) {
                if (c == null) {
                    c = new c();
                }
            }
        }
        return c;
    }

    public static void a(int i, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", Integer.valueOf(i));
        d.a.a.i.a(com.igexin.push.core.b.Z, contentValues, new String[]{"taskid"}, new String[]{str});
    }

    private void a(ContentValues contentValues) {
        try {
            if (this.a == -1) {
                this.a = b();
            }
            if (this.a < 1000) {
                if (d.a.a.i.a(com.igexin.push.core.b.Z, contentValues) != -1) {
                    this.a++;
                    return;
                }
                return;
            }
            int iA = d.a.a.i.a(com.igexin.push.core.b.Z, "id IN (SELECT id from message where status IS NULL or status=1 or status=2 order by id asc limit 250)");
            this.a -= iA;
            if (iA < 250) {
                this.a -= d.a.a.i.a(com.igexin.push.core.b.Z, "id IN (SELECT id from message where status=0 order by id asc limit " + (com.igexin.push.core.b.av - iA) + ")");
            }
            if (d.a.a.i.a(com.igexin.push.core.b.Z, contentValues) != -1) {
                this.a++;
            }
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
    }

    private static void a(SQLiteDatabase sQLiteDatabase, ArrayList<k> arrayList) {
        try {
            for (k kVar : arrayList) {
                byte[] bArr = kVar.e;
                ContentValues contentValues = new ContentValues();
                contentValues.put(com.igexin.push.core.b.C, Long.valueOf(kVar.a));
                contentValues.put("messageid", kVar.b);
                contentValues.put("taskid", kVar.c);
                contentValues.put("appid", kVar.d);
                if (bArr != null) {
                    contentValues.put("info", bArr);
                }
                if (kVar.f != null) {
                    contentValues.put("msgextra", kVar.f);
                }
                contentValues.put("key", kVar.g);
                contentValues.put("status", Integer.valueOf(kVar.h));
                contentValues.put("createtime", Long.valueOf(kVar.i));
                sQLiteDatabase.insert(com.igexin.push.core.b.Z, null, contentValues);
            }
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
        arrayList.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00c6 A[Catch: Throwable -> 0x00c4, all -> 0x0102, TryCatch #2 {all -> 0x0102, blocks: (B:23:0x00ab, B:25:0x00b1, B:27:0x00b7, B:31:0x00cd, B:30:0x00c6, B:39:0x00df), top: B:63:0x0080 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:73:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.content.ContentValues] */
    /* JADX WARN: Type inference failed for: r0v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.igexin.push.b.b] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void a(String str, int i, int i2) throws Throwable {
        Cursor cursorA;
        com.igexin.push.f.e eVarA;
        Cursor cursorA2;
        Throwable th;
        if (i2 == 0) {
            return;
        }
        Cursor cursor = null;
        try {
            try {
                ?? contentValues = new ContentValues();
                contentValues.put("notify_status", Integer.valueOf(i));
                if (i == com.igexin.push.core.b.ai) {
                    cursorA = d.a.a.i.a(com.igexin.push.core.b.Z, new String[]{"taskId"}, new String[]{str}, new String[]{"redisplay_num", "redisplay_duration", "redisplay_freq"}, null);
                    if (cursorA != null) {
                        try {
                            if (cursorA.getCount() != 0) {
                                if (cursorA.moveToFirst()) {
                                    int i3 = cursorA.getInt(cursorA.getColumnIndex("redisplay_num"));
                                    long j = cursorA.getLong(cursorA.getColumnIndex("redisplay_duration"));
                                    contentValues.put("redisplay_num", Integer.valueOf(i3 + 1));
                                    contentValues.put("expect_redisplay_time", Long.valueOf((System.currentTimeMillis() / 1000) + j));
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (cursorA != null) {
                                cursorA.close();
                            }
                            throw th;
                        }
                    }
                    if (cursorA != null) {
                        cursorA.close();
                        return;
                    }
                    return;
                }
                cursorA = null;
                try {
                    d.a.a.i.a(com.igexin.push.core.b.Z, contentValues, new String[]{"taskid"}, new String[]{str});
                    eVarA = com.igexin.push.f.e.a();
                } catch (Throwable th3) {
                    th = th3;
                }
                try {
                    cursorA2 = d.a.a.i.a(com.igexin.push.core.b.Z, new String[0], "status = '1' and notify_status = '1' and redisplay_freq != '0' and redisplay_num <= redisplay_freq  order by expect_redisplay_time asc limit 1");
                } catch (Throwable th4) {
                    th = th4;
                    contentValues = 0;
                    if (contentValues != 0) {
                        contentValues.close();
                    }
                    throw th;
                }
                if (cursorA2 != null) {
                    try {
                        if (cursorA2.getCount() == 1 && cursorA2.moveToFirst()) {
                            eVarA.b = cursorA2.getLong(cursorA2.getColumnIndex("expect_redisplay_time"));
                        } else {
                            eVarA.b = Long.MAX_VALUE;
                        }
                        long j2 = eVarA.b;
                        System.currentTimeMillis();
                    } catch (Throwable th5) {
                        th = th5;
                        com.igexin.c.a.c.a.a(th);
                        com.igexin.c.a.c.a.b(eVarA.a, " get next redisplay message fail" + th.toString());
                        if (cursorA2 != null) {
                        }
                        if (cursorA == null) {
                        }
                    }
                    if (cursorA2 != null) {
                        cursorA2.close();
                    }
                }
                if (cursorA == null) {
                    cursorA.close();
                }
            } catch (Throwable th6) {
                th = th6;
            }
        } catch (Throwable th7) {
            th = th7;
            cursorA = cursor;
        }
    }

    public static boolean a(String str) throws Throwable {
        boolean z = false;
        Cursor cursor = null;
        try {
            try {
                Cursor cursorA = d.a.a.i.a(com.igexin.push.core.b.Z, new String[]{"taskid"}, new String[]{str}, null, null);
                if (cursorA != null) {
                    try {
                        if (cursorA.getCount() > 0) {
                            z = true;
                        }
                    } catch (Throwable th) {
                        cursor = cursorA;
                        th = th;
                        com.igexin.c.a.c.a.a(th);
                        if (cursor != null) {
                            cursor.close();
                        }
                        return false;
                    }
                }
                if (cursorA != null) {
                    cursorA.close();
                }
                return z;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static int b() throws Throwable {
        Cursor cursor = null;
        try {
            try {
                Cursor cursorA = d.a.a.i.a(com.igexin.push.core.b.Z, null, null, null, null);
                if (cursorA == null) {
                    if (cursorA == null) {
                        return 0;
                    }
                    cursorA.close();
                    return 0;
                }
                try {
                    int count = cursorA.getCount();
                    if (cursorA != null) {
                        cursorA.close();
                    }
                    return count;
                } catch (Throwable th) {
                    th = th;
                    cursor = cursorA;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("condition");
            if (jSONObject2.has("wifi") || jSONObject2.has("screenOn") || jSONObject2.has("ssid") || jSONObject2.has("duration")) {
                return false;
            }
            return !jSONObject2.has("netConnected");
        } catch (Exception e) {
            com.igexin.c.a.c.a.a(e);
            return true;
        }
    }

    private void c() {
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new AnonymousClass1(), false, true);
    }

    private void d() {
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new AnonymousClass2(), false, true);
    }

    private static void e() {
    }

    private void e(SQLiteDatabase sQLiteDatabase) throws Throwable {
        Cursor cursorQuery;
        Cursor cursor = null;
        try {
            try {
                cursorQuery = sQLiteDatabase.query(com.igexin.push.core.b.Z, null, "status =?", new String[]{"0"}, null, null, null, null);
                if (cursorQuery != null) {
                    try {
                        ArrayList<k> arrayList = new ArrayList<>();
                        while (cursorQuery.moveToNext()) {
                            arrayList.add(new k(cursorQuery.getInt(cursorQuery.getColumnIndex(com.igexin.push.core.b.C)), cursorQuery.getString(cursorQuery.getColumnIndex("messageid")), cursorQuery.getString(cursorQuery.getColumnIndex("taskid")), cursorQuery.getString(cursorQuery.getColumnIndex("appid")), cursorQuery.getBlob(cursorQuery.getColumnIndexOrThrow("info")), cursorQuery.getBlob(cursorQuery.getColumnIndex("msgextra")), cursorQuery.getString(cursorQuery.getColumnIndex("key")), cursorQuery.getInt(cursorQuery.getColumnIndex("status")), cursorQuery.getLong(cursorQuery.getColumnIndex("createtime"))));
                        }
                        this.d = arrayList;
                        arrayList.size();
                    } catch (Throwable th) {
                        th = th;
                        cursor = cursorQuery;
                        com.igexin.c.a.c.a.a(th);
                        if (cursor != null) {
                            cursor.close();
                            return;
                        }
                        return;
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            cursorQuery = cursor;
        }
    }

    @Override // com.igexin.push.core.e.a
    public final void a(SQLiteDatabase sQLiteDatabase) {
        if (!this.e || this.d == null || this.d.size() <= 0) {
            return;
        }
        ArrayList<k> arrayList = this.d;
        try {
            for (k kVar : arrayList) {
                byte[] bArr = kVar.e;
                ContentValues contentValues = new ContentValues();
                contentValues.put(com.igexin.push.core.b.C, Long.valueOf(kVar.a));
                contentValues.put("messageid", kVar.b);
                contentValues.put("taskid", kVar.c);
                contentValues.put("appid", kVar.d);
                if (bArr != null) {
                    contentValues.put("info", bArr);
                }
                if (kVar.f != null) {
                    contentValues.put("msgextra", kVar.f);
                }
                contentValues.put("key", kVar.g);
                contentValues.put("status", Integer.valueOf(kVar.h));
                contentValues.put("createtime", Long.valueOf(kVar.i));
                sQLiteDatabase.insert(com.igexin.push.core.b.Z, null, contentValues);
            }
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
        arrayList.clear();
    }

    @Override // com.igexin.push.core.e.a
    public final void b(SQLiteDatabase sQLiteDatabase) {
    }

    @Override // com.igexin.push.core.e.a
    public final void c(SQLiteDatabase sQLiteDatabase) {
    }

    public final void d(SQLiteDatabase sQLiteDatabase) throws Throwable {
        Cursor cursorQuery;
        this.e = true;
        Cursor cursor = null;
        try {
            try {
                cursorQuery = sQLiteDatabase.query(com.igexin.push.core.b.Z, null, "status =?", new String[]{"0"}, null, null, null, null);
                if (cursorQuery != null) {
                    try {
                        ArrayList<k> arrayList = new ArrayList<>();
                        while (cursorQuery.moveToNext()) {
                            arrayList.add(new k(cursorQuery.getInt(cursorQuery.getColumnIndex(com.igexin.push.core.b.C)), cursorQuery.getString(cursorQuery.getColumnIndex("messageid")), cursorQuery.getString(cursorQuery.getColumnIndex("taskid")), cursorQuery.getString(cursorQuery.getColumnIndex("appid")), cursorQuery.getBlob(cursorQuery.getColumnIndexOrThrow("info")), cursorQuery.getBlob(cursorQuery.getColumnIndex("msgextra")), cursorQuery.getString(cursorQuery.getColumnIndex("key")), cursorQuery.getInt(cursorQuery.getColumnIndex("status")), cursorQuery.getLong(cursorQuery.getColumnIndex("createtime"))));
                        }
                        this.d = arrayList;
                        arrayList.size();
                    } catch (Throwable th) {
                        th = th;
                        cursor = cursorQuery;
                        com.igexin.c.a.c.a.a(th);
                        if (cursor != null) {
                            cursor.close();
                            return;
                        }
                        return;
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            cursorQuery = cursor;
        }
    }
}
