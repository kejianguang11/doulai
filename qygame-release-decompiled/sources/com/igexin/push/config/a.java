package com.igexin.push.config;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.igexin.push.core.e.f.AnonymousClass9;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
public class a implements com.igexin.push.core.e.a {
    public static final String a = "com.igexin.push.config.a";
    public static final int b = 63;
    public static final int c = 65;
    public static final int d = 67;
    public static final int e = 68;
    public static final int f = 79;
    public static final int g = 82;
    private static final int h = 1;
    private static final int i = 2;
    private static final int j = 3;
    private static final int k = 15;
    private static final int l = 16;
    private static final int m = 24;
    private static final int n = 26;
    private static final int o = 28;
    private static final int p = 46;
    private static final int q = 47;
    private static final int r = 48;
    private static final int s = 49;
    private static final int t = 60;
    private static final int u = 61;
    private static final int v = 62;
    private static final int w = 69;
    private static final int x = 70;
    private static final int y = 74;
    private static volatile a z;

    /* JADX INFO: renamed from: com.igexin.push.config.a$1, reason: invalid class name */
    public class AnonymousClass1 extends com.igexin.push.b.d {
        final /* synthetic */ String a;

        public AnonymousClass1(String str) {
            this.a = str;
        }

        @Override // com.igexin.push.b.d
        public final void a_() {
            a.b(this.d, 63, this.a);
        }
    }

    /* JADX INFO: renamed from: com.igexin.push.config.a$3, reason: invalid class name */
    public class AnonymousClass3 extends com.igexin.push.b.d {
        public AnonymousClass3() {
        }

        @Override // com.igexin.push.b.d
        public final void a_() {
            a.b(this.d, 15, String.valueOf(d.e));
        }
    }

    /* JADX INFO: renamed from: com.igexin.push.config.a$4, reason: invalid class name */
    public class AnonymousClass4 extends com.igexin.push.b.d {
        public AnonymousClass4() {
        }

        @Override // com.igexin.push.b.d
        public final void a_() {
            a.b(this.d, 16, String.valueOf(d.f));
        }
    }

    /* JADX INFO: renamed from: com.igexin.push.config.a$5, reason: invalid class name */
    public class AnonymousClass5 extends com.igexin.push.b.d {
        public AnonymousClass5() {
        }

        @Override // com.igexin.push.b.d
        public final void a_() {
            a.b(this.d, 3, String.valueOf(d.d));
        }
    }

    /* JADX INFO: renamed from: com.igexin.push.config.a$6, reason: invalid class name */
    public class AnonymousClass6 extends com.igexin.push.b.d {
        final /* synthetic */ String a;

        public AnonymousClass6(String str) {
            this.a = str;
        }

        @Override // com.igexin.push.b.d
        public final void a_() {
            a.a(this.d, 26, com.igexin.c.a.a.a.b(this.a.getBytes(), com.igexin.push.core.e.M));
        }
    }

    /* JADX INFO: renamed from: com.igexin.push.config.a$7, reason: invalid class name */
    public class AnonymousClass7 extends com.igexin.push.b.d {
        final /* synthetic */ String a;

        public AnonymousClass7(String str) {
            this.a = str;
        }

        @Override // com.igexin.push.b.d
        public final void a_() {
            a.a(this.d, 24, com.igexin.c.a.a.a.b(this.a.getBytes(), com.igexin.push.core.e.M));
        }
    }

    /* JADX INFO: renamed from: com.igexin.push.config.a$9, reason: invalid class name */
    public class AnonymousClass9 extends com.igexin.push.b.d {
        final /* synthetic */ boolean a;

        public AnonymousClass9(boolean z) {
            this.a = z;
        }

        @Override // com.igexin.push.b.d
        public final void a_() {
            a.b(this.d, 79, String.valueOf(this.a));
        }
    }

    private a() {
    }

    public static a a() {
        if (z == null) {
            synchronized (a.class) {
                if (z == null) {
                    z = new a();
                }
            }
        }
        return z;
    }

    private void a(final long j2) {
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.config.a.8
            @Override // com.igexin.push.b.d
            public final void a_() {
                com.igexin.push.core.e.aH = j2;
                a.b(this.d, 65, String.valueOf(j2));
            }
        }, true, false);
    }

    private static void a(SQLiteDatabase sQLiteDatabase, int i2) {
        sQLiteDatabase.delete(com.igexin.push.core.b.X, "id = ?", new String[]{String.valueOf(i2)});
    }

    static /* synthetic */ void a(SQLiteDatabase sQLiteDatabase, int i2, byte[] bArr) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(com.igexin.push.core.b.C, Integer.valueOf(i2));
        contentValues.put("value", bArr);
        sQLiteDatabase.replace(com.igexin.push.core.b.X, null, contentValues);
    }

    private void a(boolean z2) {
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new AnonymousClass9(z2), true, false);
    }

    private boolean a(String str) {
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new AnonymousClass1(str), false, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(SQLiteDatabase sQLiteDatabase, int i2, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(com.igexin.push.core.b.C, Integer.valueOf(i2));
        contentValues.put("value", str);
        sQLiteDatabase.replace(com.igexin.push.core.b.X, null, contentValues);
    }

    private static void b(SQLiteDatabase sQLiteDatabase, int i2, byte[] bArr) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(com.igexin.push.core.b.C, Integer.valueOf(i2));
        contentValues.put("value", bArr);
        sQLiteDatabase.replace(com.igexin.push.core.b.X, null, contentValues);
    }

    private void b(String str) {
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new AnonymousClass6(str), true, false);
    }

    private void c() {
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new AnonymousClass3(), false, true);
    }

    private void c(String str) {
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new AnonymousClass7(str), true, false);
    }

    private void d() {
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new AnonymousClass4(), false, true);
    }

    private void e() {
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new AnonymousClass5(), false, true);
    }

    @Override // com.igexin.push.core.e.a
    public final void a(SQLiteDatabase sQLiteDatabase) {
    }

    public final void b() {
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.config.a.2
            @Override // com.igexin.push.b.d
            public final void a_() {
                a.b(this.d, 1, String.valueOf(d.b));
                a.b(this.d, 2, String.valueOf(d.c));
            }
        }, false, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:88:0x01c0 A[PHI: r12
      0x01c0: PHI (r12v7 android.database.Cursor) = (r12v6 android.database.Cursor), (r12v14 android.database.Cursor) binds: [B:87:0x01be, B:80:0x01b1] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x020c  */
    @Override // com.igexin.push.core.e.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void b(SQLiteDatabase sQLiteDatabase) throws Throwable {
        Throwable th;
        Cursor cursorQuery;
        byte[] blob;
        String string;
        Throwable e2;
        try {
            cursorQuery = sQLiteDatabase.query(com.igexin.push.core.b.X, new String[]{com.igexin.push.core.b.C, "value"}, null, null, null, null, com.igexin.push.core.b.C);
            if (cursorQuery != null) {
                while (cursorQuery.moveToNext()) {
                    try {
                        try {
                            int i2 = cursorQuery.getInt(0);
                            if (i2 == 24 || i2 == 26) {
                                blob = cursorQuery.getBlob(1);
                                if (blob != null) {
                                    blob = com.igexin.c.a.a.a.a(blob, com.igexin.push.core.e.M);
                                }
                                string = null;
                            } else {
                                try {
                                    string = cursorQuery.getString(1);
                                    blob = null;
                                } catch (Throwable th2) {
                                    e2 = th2;
                                }
                            }
                            if (blob != null || string != null) {
                                switch (i2) {
                                    case 1:
                                        if (string.equals("null")) {
                                            continue;
                                        } else {
                                            d.b = Integer.valueOf(string).intValue();
                                        }
                                        break;
                                    case 2:
                                        if (string.equals("null")) {
                                            continue;
                                        } else {
                                            d.c = Integer.valueOf(string).intValue();
                                        }
                                        break;
                                    case 3:
                                        if (string.equals("null")) {
                                            continue;
                                        } else {
                                            d.d = Long.valueOf(string).longValue();
                                        }
                                        break;
                                    case 15:
                                        if (string.equals("null")) {
                                            continue;
                                        } else {
                                            d.e = Integer.valueOf(string).intValue();
                                        }
                                        break;
                                    case 16:
                                        if (string.equals("null")) {
                                            continue;
                                        } else {
                                            d.f = Integer.valueOf(string).intValue();
                                        }
                                        break;
                                    case 24:
                                        try {
                                            g.a(new String(blob), false);
                                            continue;
                                        } catch (Exception e3) {
                                            e2 = e3;
                                            com.igexin.c.a.c.a.a(e2);
                                        }
                                        break;
                                    case 26:
                                        try {
                                            SDKUrlConfig.setIdcConfigUrl(com.igexin.push.core.a.b.h.a(new JSONArray(new String(blob))));
                                            continue;
                                        } catch (Exception e4) {
                                            e2 = e4;
                                            com.igexin.c.a.c.a.a(e2);
                                        }
                                        break;
                                    case 28:
                                        if (string.equals("null")) {
                                            continue;
                                        } else {
                                            d.w = string;
                                        }
                                        break;
                                    case 46:
                                        if (string.equals("null")) {
                                            continue;
                                        } else {
                                            d.H = Boolean.valueOf(string).booleanValue();
                                        }
                                        break;
                                    case 47:
                                        if (string.equals("null")) {
                                            continue;
                                        } else {
                                            d.I = Boolean.valueOf(string).booleanValue();
                                        }
                                        break;
                                    case 48:
                                        if (string.equals("null")) {
                                            continue;
                                        } else {
                                            d.J = Boolean.valueOf(string).booleanValue();
                                        }
                                        break;
                                    case 49:
                                        if (string.equals("null")) {
                                            continue;
                                        } else {
                                            d.K = Boolean.valueOf(string).booleanValue();
                                        }
                                        break;
                                    case 61:
                                        com.igexin.push.core.e.aD = Integer.valueOf(string).intValue();
                                        continue;
                                    case 63:
                                        com.igexin.push.core.e.aC = string;
                                        continue;
                                    case 65:
                                        com.igexin.push.core.e.aH = Long.valueOf(string).longValue();
                                        continue;
                                    case 67:
                                        com.igexin.push.core.e.aF = Long.valueOf(string).longValue();
                                        continue;
                                    case 68:
                                        com.igexin.push.core.e.aG = Long.valueOf(string).longValue();
                                        continue;
                                    case 69:
                                        com.igexin.push.core.e.aI = string;
                                        continue;
                                    case 70:
                                        com.igexin.push.core.e.aJ = Integer.valueOf(string).intValue();
                                        continue;
                                    case 74:
                                        if (string.equals("null")) {
                                            continue;
                                        } else {
                                            d.D = Boolean.valueOf(string).booleanValue();
                                        }
                                        break;
                                    case 79:
                                        com.igexin.c.a.c.a.a(a + "| read from db  initZxEnable = " + string, new Object[0]);
                                        d.Q = Boolean.parseBoolean(string);
                                        continue;
                                    case 82:
                                        d.U = Boolean.parseBoolean(string);
                                        continue;
                                }
                                com.igexin.c.a.c.a.a(e2);
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            com.igexin.c.a.c.a.a(th);
                            if (cursorQuery != null) {
                            }
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        throw th;
                    }
                }
            }
        } catch (Throwable th5) {
            th = th5;
            cursorQuery = null;
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        String str = com.igexin.push.core.e.V;
        com.igexin.c.a.c.a.a(a + "|current ver = 3.3.10.0, last ver = " + com.igexin.push.core.e.V, new Object[0]);
        if ("3.3.10.0".equals(com.igexin.push.core.e.V)) {
            return;
        }
        com.igexin.push.core.e.f fVarA = com.igexin.push.core.e.f.a();
        if ("3.3.10.0".equals(com.igexin.push.core.e.V)) {
            return;
        }
        com.igexin.push.core.e.V = "3.3.10.0";
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) fVarA.new AnonymousClass9(), false, true);
    }

    @Override // com.igexin.push.core.e.a
    public final void c(SQLiteDatabase sQLiteDatabase) {
        b(sQLiteDatabase, 1, String.valueOf(d.b));
        b(sQLiteDatabase, 2, String.valueOf(d.c));
        b(sQLiteDatabase, 3, String.valueOf(d.d));
        b(sQLiteDatabase, 15, String.valueOf(d.e));
        b(sQLiteDatabase, 3, String.valueOf(d.d));
    }
}
