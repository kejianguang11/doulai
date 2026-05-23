package com.igexin.push.core.e;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.text.TextUtils;
import com.igexin.assist.sdk.AssistPushConsts;
import com.igexin.push.core.ServiceManager;
import com.igexin.push.d.c;
import com.igexin.push.f.b.d;
import com.igexin.push.g.g;
import com.igexin.push.g.k;
import java.util.UUID;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class f implements a {
    private static final int A = 48;
    private static final int B = 49;
    private static final int C = 50;
    private static final int D = 51;
    private static final int E = 53;
    private static final int F = 54;
    private static final int G = 60;
    private static final int H = 61;
    private static final int I = 63;
    private static final int J = 64;
    private static final int K = 65;
    private static final int L = 66;
    private static final int M = 67;
    private static final int N = 68;
    private static final int O = 69;
    private static volatile f P = null;
    public static final String a = "com.igexin.push.core.e.f";
    private static final int c = 1;
    private static final int d = 2;
    private static final int e = 3;
    private static final int f = 4;
    private static final int g = 6;
    private static final int h = 8;
    private static final int i = 12;
    private static final int j = 13;
    private static final int k = 14;
    private static final int l = 15;
    private static final int m = 16;
    private static final int n = 17;
    private static final int o = 18;
    private static final int p = 20;
    private static final int q = 21;
    private static final int r = 22;
    private static final int s = 23;
    private static final int t = 25;
    private static final int u = 30;
    private static final int v = 31;
    private static final int w = 32;
    private static final int x = 40;
    private static final int y = 46;
    private static final int z = 47;
    public boolean b;

    /* JADX INFO: renamed from: com.igexin.push.core.e.f$11, reason: invalid class name */
    public class AnonymousClass11 extends com.igexin.push.b.d {
        public AnonymousClass11() {
        }

        @Override // com.igexin.push.b.d
        public final void a_() {
            f.a();
            f.b(this.d, 16, String.valueOf(com.igexin.push.core.e.X));
        }
    }

    /* JADX INFO: renamed from: com.igexin.push.core.e.f$19, reason: invalid class name */
    public class AnonymousClass19 extends com.igexin.push.b.d {
        final /* synthetic */ String a;

        public AnonymousClass19(String str) {
            this.a = str;
        }

        @Override // com.igexin.push.b.d
        public final void a_() throws Exception {
            f.a();
            f.b(this.d, 3, this.a);
        }
    }

    /* JADX INFO: renamed from: com.igexin.push.core.e.f$24, reason: invalid class name */
    public class AnonymousClass24 extends com.igexin.push.b.d {
        public AnonymousClass24() {
        }

        @Override // com.igexin.push.b.d
        public final void a_() {
            f.a();
            f.b(this.d, 67, String.valueOf(com.igexin.push.core.e.J));
        }
    }

    /* JADX INFO: renamed from: com.igexin.push.core.e.f$27, reason: invalid class name */
    public class AnonymousClass27 extends com.igexin.push.b.d {
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        public AnonymousClass27(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        @Override // com.igexin.push.b.d
        public final void a_() throws Exception {
            if (!TextUtils.isEmpty(this.a)) {
                f.a();
                f.b(this.d, 53, this.a);
            }
            if (TextUtils.isEmpty(this.b)) {
                return;
            }
            f.a();
            f.b(this.d, 54, this.b);
        }
    }

    /* JADX INFO: renamed from: com.igexin.push.core.e.f$28, reason: invalid class name */
    public class AnonymousClass28 extends com.igexin.push.b.d {
        public AnonymousClass28() {
        }

        @Override // com.igexin.push.b.d
        public final void a_() throws Exception {
            f.a();
            f.b(this.d, 60, String.valueOf(com.igexin.push.core.e.c));
        }
    }

    /* JADX INFO: renamed from: com.igexin.push.core.e.f$31, reason: invalid class name */
    public class AnonymousClass31 extends com.igexin.push.b.d {
        public AnonymousClass31() {
        }

        @Override // com.igexin.push.b.d
        public final void a_() throws Exception {
            f.a();
            f.b(this.d, 64, String.valueOf(com.igexin.push.core.e.ax));
        }
    }

    /* JADX INFO: renamed from: com.igexin.push.core.e.f$35, reason: invalid class name */
    public class AnonymousClass35 extends com.igexin.push.b.d {
        final /* synthetic */ String a;

        public AnonymousClass35(String str) {
            this.a = str;
        }

        @Override // com.igexin.push.b.d
        public final void a_() throws Exception {
            f.a();
            f.b(this.d, 69, this.a);
        }
    }

    /* JADX INFO: renamed from: com.igexin.push.core.e.f$38, reason: invalid class name */
    public class AnonymousClass38 extends com.igexin.push.b.d {
        public AnonymousClass38() {
        }

        @Override // com.igexin.push.b.d
        public final void a_() throws Throwable {
            f.this.c(this.d);
            k.b();
        }
    }

    /* JADX INFO: renamed from: com.igexin.push.core.e.f$39, reason: invalid class name */
    public class AnonymousClass39 extends com.igexin.push.b.d {
        public AnonymousClass39() {
        }

        @Override // com.igexin.push.b.d
        public final void a_() {
            f.a();
            f.b(this.d, 8, String.valueOf(com.igexin.push.core.e.R));
        }
    }

    /* JADX INFO: renamed from: com.igexin.push.core.e.f$4, reason: invalid class name */
    public class AnonymousClass4 extends com.igexin.push.b.d {
        public AnonymousClass4() {
        }

        @Override // com.igexin.push.b.d
        public final void a_() {
            f.a();
            f.b(this.d, 51, com.igexin.push.core.e.C);
        }
    }

    /* JADX INFO: renamed from: com.igexin.push.core.e.f$9, reason: invalid class name */
    public class AnonymousClass9 extends com.igexin.push.b.d {
        public AnonymousClass9() {
        }

        @Override // com.igexin.push.b.d
        public final void a_() {
            f.a();
            f.b(this.d, 13, com.igexin.push.core.e.V);
        }
    }

    private f() {
    }

    public static f a() {
        if (P == null) {
            synchronized (f.class) {
                if (P == null) {
                    P = new f();
                }
            }
        }
        return P;
    }

    private boolean a(String str, String str2) {
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new AnonymousClass27(str, str2), false, true);
    }

    private boolean a(String str, String str2, long j2) {
        com.igexin.push.core.e.z = j2;
        if (TextUtils.isEmpty(com.igexin.push.core.e.H)) {
            com.igexin.push.core.e.H = str2;
        }
        com.igexin.push.core.e.A = str;
        return c();
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0049 A[PHI: r9
      0x0049: PHI (r9v6 android.database.Cursor) = (r9v5 android.database.Cursor), (r9v7 android.database.Cursor) binds: [B:20:0x0047, B:13:0x003c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static byte[] a(SQLiteDatabase sQLiteDatabase, int i2) throws Throwable {
        Cursor cursorQuery;
        try {
            cursorQuery = sQLiteDatabase.query(com.igexin.push.core.b.Y, new String[]{"value"}, "id=".concat(String.valueOf(i2)), null, null, null, null);
            if (cursorQuery != null) {
                try {
                    try {
                        if (cursorQuery.moveToFirst()) {
                            byte[] bArrA = com.igexin.c.a.a.a.a(cursorQuery.getBlob(cursorQuery.getColumnIndex("value")), com.igexin.push.core.e.M);
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            return bArrA;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        com.igexin.c.a.c.a.a(e);
                        if (cursorQuery != null) {
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    throw th;
                }
            }
        } catch (Exception e3) {
            e = e3;
            cursorQuery = null;
        } catch (Throwable th2) {
            th = th2;
            cursorQuery = null;
            if (cursorQuery != null) {
            }
            throw th;
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0042 A[PHI: r9
      0x0042: PHI (r9v3 android.database.Cursor) = (r9v2 android.database.Cursor), (r9v4 android.database.Cursor) binds: [B:20:0x0040, B:13:0x0036] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004a  */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r0v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String b(SQLiteDatabase sQLiteDatabase, int i2) throws Throwable {
        Cursor cursorQuery;
        ?? r0 = 0;
        try {
            try {
                cursorQuery = sQLiteDatabase.query(com.igexin.push.core.b.Y, new String[]{"value"}, "id=".concat(String.valueOf(i2)), null, null, null, null);
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.moveToFirst()) {
                            String string = cursorQuery.getString(cursorQuery.getColumnIndex("value"));
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            return string;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        com.igexin.c.a.c.a.a(e);
                        if (cursorQuery != null) {
                        }
                    }
                }
            } catch (Throwable th) {
                th = th;
                r0 = sQLiteDatabase;
                if (r0 != 0) {
                    r0.close();
                }
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            cursorQuery = null;
        } catch (Throwable th2) {
            th = th2;
            if (r0 != 0) {
            }
            throw th;
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(SQLiteDatabase sQLiteDatabase, int i2, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(com.igexin.push.core.b.C, Integer.valueOf(i2));
        contentValues.put("value", str);
        sQLiteDatabase.replace(com.igexin.push.core.b.Y, null, contentValues);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(SQLiteDatabase sQLiteDatabase, int i2, byte[] bArr) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(com.igexin.push.core.b.C, Integer.valueOf(i2));
        contentValues.put("value", bArr);
        sQLiteDatabase.replace(com.igexin.push.core.b.Y, null, contentValues);
    }

    private boolean c(int i2) {
        if (com.igexin.push.core.e.J == i2) {
            return false;
        }
        com.igexin.push.core.e.J = i2;
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new AnonymousClass24(), false, true);
    }

    public static void d() {
        try {
            String string = com.igexin.push.core.e.D;
            UUID uuidRandomUUID = UUID.randomUUID();
            if (TextUtils.isEmpty(string) || string.length() <= 8) {
                StringBuilder sb = new StringBuilder("V");
                sb.append(com.igexin.c.b.a.b(com.igexin.push.core.e.g + uuidRandomUUID));
                string = sb.toString();
            }
            StringBuilder sb2 = new StringBuilder("A-");
            sb2.append(string);
            sb2.append("-");
            sb2.append(com.igexin.c.b.a.b(System.currentTimeMillis() + uuidRandomUUID + com.igexin.push.core.e.g));
            String string2 = sb2.toString();
            com.igexin.push.core.e.L = string2;
            if (string2.length() >= 64) {
                com.igexin.push.core.e.L = com.igexin.push.core.e.L.substring(0, 62);
            }
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            StringBuilder sb3 = new StringBuilder("A-V");
            sb3.append(com.igexin.push.core.e.g);
            sb3.append("-");
            sb3.append(com.igexin.c.b.a.b(System.currentTimeMillis() + com.igexin.push.core.e.g));
            com.igexin.push.core.e.L = sb3.toString();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0054 A[PHI: r10
      0x0054: PHI (r10v7 android.database.Cursor) = (r10v6 android.database.Cursor), (r10v16 android.database.Cursor) binds: [B:18:0x0052, B:11:0x0043] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x008c  */
    /* JADX WARN: Type inference failed for: r10v0, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v5, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void d(SQLiteDatabase sQLiteDatabase) throws Throwable {
        Throwable th;
        Exception e2;
        Cursor cursorQuery;
        try {
            try {
                cursorQuery = sQLiteDatabase.query(com.igexin.push.core.b.Y, new String[]{"value"}, "id=?", new String[]{"25"}, null, null, null);
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.moveToFirst()) {
                            com.igexin.push.core.e.M = new String(com.igexin.c.a.a.a.a(cursorQuery.getBlob(cursorQuery.getColumnIndex("value")), com.igexin.c.b.a.b(ServiceManager.b.getPackageName())));
                        }
                    } catch (Exception e3) {
                        e2 = e3;
                        com.igexin.c.a.c.a.a(e2);
                        if (cursorQuery != null) {
                        }
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (sQLiteDatabase != 0) {
                    sQLiteDatabase.close();
                }
                throw th;
            }
        } catch (Exception e4) {
            e2 = e4;
            cursorQuery = null;
        } catch (Throwable th3) {
            th = th3;
            sQLiteDatabase = 0;
            if (sQLiteDatabase != 0) {
            }
            throw th;
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        if (com.igexin.push.core.e.M == null) {
            com.igexin.push.core.e.M = com.igexin.c.b.a.b(com.igexin.push.core.e.D == null ? com.igexin.push.core.b.al : com.igexin.push.core.e.D);
        }
        com.igexin.c.a.c.a.a(a + "|storageKey = " + com.igexin.push.core.e.M, new Object[0]);
    }

    private void e(SQLiteDatabase sQLiteDatabase) throws Throwable {
        this.b = true;
        d(sQLiteDatabase);
        byte[] bArrA = a(sQLiteDatabase, 1);
        if (bArrA != null) {
            try {
                String str = new String(bArrA);
                com.igexin.push.core.e.z = str.equals("null") ? 0L : Long.parseLong(str);
            } catch (Exception e2) {
                com.igexin.c.a.c.a.a(e2);
            }
            com.igexin.c.a.c.a.a(a + "|db version changed, save session = " + com.igexin.push.core.e.z, new Object[0]);
        }
        byte[] bArrA2 = a(sQLiteDatabase, 20);
        if (bArrA2 != null) {
            String str2 = new String(bArrA2);
            if (str2.equals("null")) {
                str2 = null;
            }
            com.igexin.push.core.e.B = str2;
            com.igexin.push.core.e.A = str2;
            com.igexin.c.a.c.a.a(a + "|db version changed, save cid = " + str2, new Object[0]);
        }
        String strB = b(sQLiteDatabase, 3);
        if (!TextUtils.isEmpty(strB)) {
            if (strB.equals("null")) {
                strB = null;
            }
            com.igexin.push.core.e.L = strB;
        }
        String str3 = com.igexin.push.core.e.L;
        String strB2 = b(sQLiteDatabase, 2);
        if (!TextUtils.isEmpty(strB2)) {
            if (strB2.equals("null")) {
                strB2 = null;
            }
            com.igexin.push.core.e.H = strB2;
        }
        String strB3 = b(sQLiteDatabase, 46);
        if (!TextUtils.isEmpty(strB3)) {
            if (strB3.equals("null")) {
                strB3 = null;
            }
            com.igexin.push.core.e.I = strB3;
        }
        String strB4 = b(sQLiteDatabase, 48);
        if (!TextUtils.isEmpty(strB4)) {
            if (strB4.equals("null")) {
                strB4 = null;
            }
            com.igexin.push.core.e.K = strB4;
        }
        String strB5 = b(sQLiteDatabase, 51);
        if (TextUtils.isEmpty(strB5) || strB5.length() == 13) {
            return;
        }
        if (strB5.equals("null")) {
            strB5 = null;
        }
        com.igexin.push.core.e.C = strB5;
    }

    static /* synthetic */ void f() throws Throwable {
        k.b();
        String strD = k.d();
        if (strD == null || strD.length() <= 5) {
            k.f();
        }
    }

    private static void f(SQLiteDatabase sQLiteDatabase) throws Throwable {
        String strB = b(sQLiteDatabase, 2);
        if (TextUtils.isEmpty(strB)) {
            return;
        }
        if (strB.equals("null")) {
            strB = null;
        }
        com.igexin.push.core.e.H = strB;
    }

    private boolean f(long j2) {
        if (j2 == com.igexin.push.core.e.R) {
            return false;
        }
        com.igexin.push.core.e.R = j2;
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new AnonymousClass39(), false, true);
    }

    private void g() {
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new AnonymousClass38(), false, true);
    }

    private static void g(SQLiteDatabase sQLiteDatabase) throws Throwable {
        String strB = b(sQLiteDatabase, 51);
        if (TextUtils.isEmpty(strB) || strB.length() == 13) {
            return;
        }
        if (strB.equals("null")) {
            strB = null;
        }
        com.igexin.push.core.e.C = strB;
    }

    private boolean g(long j2) {
        if (com.igexin.push.core.e.T == j2) {
            return false;
        }
        com.igexin.push.core.e.T = j2;
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.7
            @Override // com.igexin.push.b.d
            public final void a_() {
                f.a();
                f.b(this.d, 12, String.valueOf(com.igexin.push.core.e.T));
            }
        }, false, true);
        return true;
    }

    static /* synthetic */ byte[] g(String str) {
        return g.a(str.getBytes());
    }

    private static void h() throws Throwable {
        k.b();
        String strD = k.d();
        if (strD == null || strD.length() <= 5) {
            k.f();
        }
    }

    private static void h(SQLiteDatabase sQLiteDatabase) throws Throwable {
        String strB = b(sQLiteDatabase, 46);
        if (TextUtils.isEmpty(strB)) {
            return;
        }
        if (strB.equals("null")) {
            strB = null;
        }
        com.igexin.push.core.e.I = strB;
    }

    private boolean h(long j2) {
        if (com.igexin.push.core.e.X == j2) {
            return false;
        }
        com.igexin.push.core.e.X = j2;
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new AnonymousClass11(), false, true);
    }

    private static byte[] h(String str) {
        return g.a(str.getBytes());
    }

    private static void i() {
        String str = com.igexin.push.core.e.A;
        com.igexin.c.a.c.a.a(a + "| found a duplicate cid " + com.igexin.push.core.e.A, new Object[0]);
        com.igexin.push.core.e.L = null;
        d();
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) a().new AnonymousClass19(com.igexin.push.core.e.L), false, true);
        a().b();
        com.igexin.push.core.e.r = 0;
        com.igexin.push.f.b.e.g().a = SystemClock.elapsedRealtime();
    }

    private static void i(SQLiteDatabase sQLiteDatabase) throws Throwable {
        String strB = b(sQLiteDatabase, 48);
        if (TextUtils.isEmpty(strB)) {
            return;
        }
        if (strB.equals("null")) {
            strB = null;
        }
        com.igexin.push.core.e.K = strB;
    }

    private boolean i(long j2) {
        if (com.igexin.push.core.e.U == j2) {
            return false;
        }
        com.igexin.push.core.e.U = j2;
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.20
            @Override // com.igexin.push.b.d
            public final void a_() {
                f.a();
                f.b(this.d, 32, String.valueOf(com.igexin.push.core.e.U));
            }
        }, false, true);
    }

    private boolean i(String str) {
        com.igexin.push.core.e.C = str;
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new AnonymousClass4(), false, true);
    }

    private void j() {
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new AnonymousClass31(), false, true);
    }

    private static void j(SQLiteDatabase sQLiteDatabase) throws Throwable {
        String strB = b(sQLiteDatabase, 3);
        if (!TextUtils.isEmpty(strB)) {
            if (strB.equals("null")) {
                strB = null;
            }
            com.igexin.push.core.e.L = strB;
        }
        String str = com.igexin.push.core.e.L;
    }

    private boolean j(long j2) {
        if (com.igexin.push.core.e.c == j2) {
            return false;
        }
        com.igexin.push.core.e.c = j2;
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new AnonymousClass28(), false, true);
    }

    private boolean j(String str) {
        if (str.equals(com.igexin.push.core.e.V)) {
            return false;
        }
        com.igexin.push.core.e.V = str;
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new AnonymousClass9(), false, true);
        return true;
    }

    private static void k(SQLiteDatabase sQLiteDatabase) throws Throwable {
        byte[] bArrA = a(sQLiteDatabase, 1);
        if (bArrA != null) {
            try {
                String str = new String(bArrA);
                com.igexin.push.core.e.z = str.equals("null") ? 0L : Long.parseLong(str);
            } catch (Exception e2) {
                com.igexin.c.a.c.a.a(e2);
            }
            com.igexin.c.a.c.a.a(a + "|db version changed, save session = " + com.igexin.push.core.e.z, new Object[0]);
        }
    }

    private boolean k(String str) {
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new AnonymousClass19(str), false, true);
    }

    private static void l(SQLiteDatabase sQLiteDatabase) throws Throwable {
        byte[] bArrA = a(sQLiteDatabase, 20);
        if (bArrA != null) {
            String str = new String(bArrA);
            if (str.equals("null")) {
                str = null;
            }
            com.igexin.push.core.e.B = str;
            com.igexin.push.core.e.A = str;
            com.igexin.c.a.c.a.a(a + "|db version changed, save cid = " + str, new Object[0]);
        }
    }

    private void l(String str) {
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new AnonymousClass35(str), false, true);
    }

    @Override // com.igexin.push.core.e.a
    public final void a(SQLiteDatabase sQLiteDatabase) {
    }

    public final void a(boolean z2) {
        com.igexin.push.core.e.W = z2;
        com.igexin.c.a.c.a.a(z2);
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.10
            @Override // com.igexin.push.b.d
            public final void a_() {
                f.a();
                f.b(this.d, 15, String.valueOf(com.igexin.push.core.e.W));
            }
        }, false, true);
    }

    public final boolean a(int i2) {
        com.igexin.push.core.e.ab = i2;
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.14
            @Override // com.igexin.push.b.d
            public final void a_() {
                f.a();
                f.b(this.d, 18, String.valueOf(com.igexin.push.core.e.ab));
            }
        }, false, true);
    }

    public final boolean a(long j2) {
        com.igexin.push.core.e.a(j2);
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.2
            @Override // com.igexin.push.b.d
            public final void a_() throws Throwable {
                f.a();
                f.b(this.d, 1, g.a(String.valueOf(com.igexin.push.core.e.z).getBytes()));
                f.a();
                f.b(this.d, 20, f.g(com.igexin.push.core.e.A));
                k.b();
            }
        }, false, true);
    }

    public final boolean a(String str) {
        com.igexin.push.core.e.H = str;
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.3
            @Override // com.igexin.push.b.d
            public final void a_() throws Throwable {
                f.a();
                f.b(this.d, 2, com.igexin.push.core.e.H);
                String strD = k.d();
                if (strD == null || strD.length() <= 5) {
                    k.f();
                }
            }
        }, false, true);
    }

    public final boolean a(final String str, boolean z2) {
        com.igexin.c.a.b.e eVarA;
        com.igexin.push.b.d dVar;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (z2) {
            if (!str.equals(com.igexin.push.core.e.ar)) {
                com.igexin.push.core.e.ar = str.equals("null") ? null : str;
                eVarA = com.igexin.c.a.b.e.a();
                dVar = new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.15
                    @Override // com.igexin.push.b.d
                    public final void a_() throws Exception {
                        f.a();
                        f.b(this.d, 31, f.g(str));
                    }
                };
                return eVarA.a((com.igexin.c.a.d.f) dVar, false, true);
            }
            return false;
        }
        if (!str.equals(com.igexin.push.core.e.as)) {
            com.igexin.push.core.e.as = str.equals("null") ? null : str;
            eVarA = com.igexin.c.a.b.e.a();
            dVar = new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.16
                @Override // com.igexin.push.b.d
                public final void a_() throws Exception {
                    f.a();
                    f.b(this.d, 30, f.g(str));
                }
            };
            return eVarA.a((com.igexin.c.a.d.f) dVar, false, true);
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:224:0x03cf A[PHI: r8 r14
      0x03cf: PHI (r8v5 android.database.Cursor) = (r8v4 android.database.Cursor), (r8v7 android.database.Cursor) binds: [B:223:0x03cd, B:213:0x03b5] A[DONT_GENERATE, DONT_INLINE]
      0x03cf: PHI (r14v3 boolean) = (r14v2 boolean), (r14v5 boolean) binds: [B:223:0x03cd, B:213:0x03b5] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:227:0x03da  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x044e  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x0456  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x0467  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x0484  */
    /* JADX WARN: Removed duplicated region for block: B:265:0x04b9  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x04ec  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x04f5  */
    /* JADX WARN: Removed duplicated region for block: B:278:0x0506  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x007b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:325:0x007e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:326:0x008f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:327:0x00a1 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:328:0x00b4 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:329:0x00c7 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:330:0x00d9 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:331:0x00ec A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:332:0x00f9 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:333:0x0106 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:334:0x011c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:335:0x014b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:336:0x0176 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:337:0x0183 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:338:0x0195 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:339:0x01a2 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:340:0x01d3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:341:0x01e6 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:342:0x0212 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:343:0x023e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:344:0x026a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:345:0x0296 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:346:0x02a9 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:347:0x02bd A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:348:0x02cf A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:349:0x02dc A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:350:0x02ef A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:351:0x0301 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:352:0x030a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:353:0x0317 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:354:0x032a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:355:0x033d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:356:0x0350 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:357:0x0366 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:358:0x0373 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:359:0x0380 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:378:0x0029 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:414:? A[RETURN, SYNTHETIC] */
    @Override // com.igexin.push.core.e.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void b(SQLiteDatabase sQLiteDatabase) throws Throwable {
        boolean z2;
        Cursor cursorQuery;
        String str;
        String strD;
        JSONObject jSONObjectC;
        String strOptString;
        String strC;
        byte[] blob;
        String string;
        String str2;
        Object[] objArr;
        d(sQLiteDatabase);
        Cursor cursor = null;
        try {
        } catch (Throwable th) {
            th = th;
        }
        try {
            try {
                z2 = false;
            } catch (Exception e2) {
                e = e2;
                z2 = false;
            }
            try {
                cursorQuery = sQLiteDatabase.query(com.igexin.push.core.b.Y, new String[]{com.igexin.push.core.b.C, "value"}, null, null, null, null, com.igexin.push.core.b.C);
                if (cursorQuery != null) {
                    while (cursorQuery.moveToNext()) {
                        try {
                            int i2 = cursorQuery.getInt(0);
                            if (i2 != 66) {
                                if (i2 == 1 || i2 == 14 || i2 == 20 || i2 == 25 || i2 == 22 || i2 == 23 || i2 == 31 || i2 == 30 || i2 == 49 || i2 == 50 || i2 == 61) {
                                    blob = cursorQuery.getBlob(1);
                                    if (blob != null) {
                                        blob = com.igexin.c.a.a.a.a(blob, com.igexin.push.core.e.M);
                                    }
                                    string = null;
                                    if (blob == null || string != null) {
                                        switch (i2) {
                                            case 1:
                                                String str3 = new String(blob);
                                                try {
                                                    com.igexin.push.core.e.z = str3.equals("null") ? 0L : Long.parseLong(str3);
                                                    continue;
                                                    continue;
                                                } catch (Exception unused) {
                                                    com.igexin.c.a.c.a.a(a, "session formate error! session : ".concat(String.valueOf(str3)));
                                                    com.igexin.push.core.e.z = 0L;
                                                }
                                                break;
                                            case 2:
                                                if (string.equals("null")) {
                                                    string = null;
                                                }
                                                com.igexin.push.core.e.H = string;
                                                continue;
                                                continue;
                                            case 3:
                                                if (string.equals("null")) {
                                                    string = null;
                                                }
                                                com.igexin.push.core.e.L = string;
                                                continue;
                                                continue;
                                            case 4:
                                                com.igexin.push.core.e.t = string.equals("null") || Boolean.parseBoolean(string);
                                                continue;
                                                continue;
                                            case 6:
                                                com.igexin.push.core.e.Q = string.equals("null") ? 0L : Long.parseLong(string);
                                                continue;
                                                continue;
                                            case 8:
                                                com.igexin.push.core.e.R = string.equals("null") ? 0L : Long.parseLong(string);
                                                continue;
                                                continue;
                                            case 12:
                                                com.igexin.push.core.e.T = string.equals("null") ? 0L : Long.parseLong(string);
                                                continue;
                                                continue;
                                            case 13:
                                                if (string.equals("null")) {
                                                    string = null;
                                                }
                                                com.igexin.push.core.e.V = string;
                                                continue;
                                                continue;
                                            case 14:
                                                com.igexin.push.core.e.an = new String(blob);
                                                continue;
                                                continue;
                                            case 15:
                                                if (!string.equals("null")) {
                                                    com.igexin.push.core.e.W = Boolean.parseBoolean(string);
                                                    boolean z3 = com.igexin.push.core.e.t;
                                                } else {
                                                    continue;
                                                    continue;
                                                }
                                                break;
                                            case 16:
                                                com.igexin.push.core.e.X = string.equals("null") ? 0L : Long.parseLong(string);
                                                continue;
                                                continue;
                                            case 17:
                                                if (string.equals("null")) {
                                                    string = null;
                                                }
                                                com.igexin.push.core.e.Z = string;
                                                continue;
                                                continue;
                                            case 18:
                                                com.igexin.push.core.e.ab = string.equals("null") ? 0 : Integer.parseInt(string);
                                                continue;
                                                continue;
                                            case 20:
                                                String str4 = new String(blob);
                                                if (str4.equals("null")) {
                                                    str4 = null;
                                                }
                                                com.igexin.push.core.e.B = str4;
                                                com.igexin.push.core.e.A = str4;
                                                continue;
                                                continue;
                                            case 21:
                                                com.igexin.push.core.e.ao = string.equals("null") ? 0L : Long.parseLong(string);
                                                continue;
                                                continue;
                                            case 22:
                                                String str5 = new String(blob);
                                                if (str5.equals("null")) {
                                                    str5 = null;
                                                }
                                                com.igexin.push.core.e.aq = str5;
                                                str2 = a + "|read last wf result = " + com.igexin.push.core.e.aq;
                                                objArr = new Object[0];
                                                break;
                                            case 23:
                                                String str6 = new String(blob);
                                                if (str6.equals("null")) {
                                                    str6 = null;
                                                }
                                                com.igexin.push.core.e.ap = str6;
                                                str2 = a + "|read last mobile result = " + com.igexin.push.core.e.ap;
                                                objArr = new Object[0];
                                                break;
                                            case 30:
                                                String str7 = new String(blob);
                                                if (str7.equals("null")) {
                                                    str7 = null;
                                                }
                                                com.igexin.push.core.e.as = str7;
                                                str2 = a + "|read last domainWfStatus = " + com.igexin.push.core.e.as;
                                                objArr = new Object[0];
                                                break;
                                            case 31:
                                                String str8 = new String(blob);
                                                if (str8.equals("null")) {
                                                    str8 = null;
                                                }
                                                com.igexin.push.core.e.ar = str8;
                                                str2 = a + "|read last domainMobileStatus = " + com.igexin.push.core.e.ar;
                                                objArr = new Object[0];
                                                break;
                                            case 32:
                                                com.igexin.push.core.e.U = string.equals("null") ? 0L : Long.parseLong(string);
                                                continue;
                                                continue;
                                            case 40:
                                                boolean z4 = !string.equals("null") && Boolean.parseBoolean(string);
                                                c.b.a.b = z4;
                                                com.igexin.c.a.c.a.a("ConnectModelCoordinator|init, current is polling mdl = ".concat(String.valueOf(z4)), new Object[0]);
                                                if (!z4) {
                                                    continue;
                                                    continue;
                                                } else {
                                                    d.a.a.g();
                                                }
                                                break;
                                            case 46:
                                                if (string.equals("null")) {
                                                    string = null;
                                                }
                                                com.igexin.push.core.e.I = string;
                                                continue;
                                                continue;
                                            case 47:
                                                com.igexin.push.core.e.aA = string.equals("null") ? 0 : Integer.parseInt(string);
                                                continue;
                                                continue;
                                            case 48:
                                                if (string.equals("null")) {
                                                    string = null;
                                                }
                                                com.igexin.push.core.e.K = string;
                                                continue;
                                                continue;
                                            case 49:
                                                String str9 = new String(blob);
                                                if (str9.equals("null")) {
                                                    str9 = null;
                                                }
                                                com.igexin.push.core.e.at = str9;
                                                str2 = a + "|read last wfRedirectCmList = " + com.igexin.push.core.e.at;
                                                objArr = new Object[0];
                                                break;
                                            case 50:
                                                String str10 = new String(blob);
                                                if (str10.equals("null")) {
                                                    str10 = null;
                                                }
                                                com.igexin.push.core.e.au = str10;
                                                str2 = a + "|read last mobileRedirectCmList = " + com.igexin.push.core.e.au;
                                                objArr = new Object[0];
                                                break;
                                            case 51:
                                                if (!string.equals("null") && string.length() != 13) {
                                                    com.igexin.push.core.e.C = string;
                                                }
                                                String str11 = com.igexin.push.core.e.C;
                                                continue;
                                                continue;
                                            case 53:
                                                if ("null".equals(string)) {
                                                    string = null;
                                                }
                                                com.igexin.push.core.e.aK = string;
                                                continue;
                                                continue;
                                            case 54:
                                                if ("null".equals(string)) {
                                                    string = null;
                                                }
                                                com.igexin.push.core.e.aL = string;
                                                continue;
                                                continue;
                                            case 60:
                                                com.igexin.push.core.e.c = string.equals("null") ? 0L : Long.parseLong(string);
                                                continue;
                                                continue;
                                            case 61:
                                                String str12 = new String(blob);
                                                if (str12.equals("null")) {
                                                    str12 = null;
                                                }
                                                com.igexin.push.core.e.d = str12;
                                                continue;
                                                continue;
                                            case 63:
                                                com.igexin.push.core.e.aw = "null".equals(string) ? 0L : Long.parseLong(string);
                                                continue;
                                                continue;
                                            case 64:
                                                com.igexin.push.core.e.ax = "null".equals(string) ? 0L : Long.parseLong(string);
                                                continue;
                                                continue;
                                            case 65:
                                                com.igexin.push.core.e.ay = "null".equals(string) ? 0L : Long.parseLong(string);
                                                continue;
                                                continue;
                                            case 67:
                                                com.igexin.push.core.e.J = string.equals("null") ? -1 : Integer.parseInt(string);
                                                continue;
                                                continue;
                                            case 68:
                                                com.igexin.push.core.e.aN = string;
                                                continue;
                                                continue;
                                        }
                                        com.igexin.c.a.c.a.a(str2, objArr);
                                    }
                                } else {
                                    try {
                                        string = cursorQuery.getString(1);
                                        blob = null;
                                        if (blob == null) {
                                        }
                                        switch (i2) {
                                        }
                                        com.igexin.c.a.c.a.a(str2, objArr);
                                    } catch (Throwable th2) {
                                        com.igexin.c.a.c.a.a(th2);
                                    }
                                }
                            }
                        } catch (Exception e3) {
                            e = e3;
                            com.igexin.c.a.c.a.a(e);
                            com.igexin.c.a.c.a.a(a, e.toString());
                            if (cursorQuery != null) {
                            }
                        }
                    }
                }
            } catch (Exception e4) {
                e = e4;
                cursorQuery = null;
                com.igexin.c.a.c.a.a(e);
                com.igexin.c.a.c.a.a(a, e.toString());
                if (cursorQuery != null) {
                }
                if (com.igexin.push.core.e.z == 0) {
                }
                if (com.igexin.push.core.e.A == null) {
                    com.igexin.push.core.e.B = strC;
                    com.igexin.push.core.e.A = strC;
                    com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.12
                        @Override // com.igexin.push.b.d
                        public final void a_() throws Exception {
                            f.b(this.d, 20, g.a(com.igexin.push.core.e.A.getBytes()));
                        }
                    }, z2, true);
                }
                if (com.igexin.push.core.e.A == null) {
                    com.igexin.push.core.e.B = com.igexin.c.b.a.b(String.valueOf(com.igexin.push.core.e.z));
                    com.igexin.push.core.e.a(com.igexin.push.core.e.z);
                    com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.23
                        @Override // com.igexin.push.b.d
                        public final void a_() throws Exception {
                            f.b(this.d, 20, g.a(com.igexin.push.core.e.A.getBytes()));
                        }
                    }, z2, true);
                }
                if ("cfcd208495d565ef66e7dff9f98764da".equals(com.igexin.push.core.e.A)) {
                    if (com.igexin.push.core.e.z == 0) {
                    }
                }
                if (!TextUtils.isEmpty(com.igexin.push.core.e.an)) {
                    com.igexin.push.core.e.an = com.igexin.c.b.a.a();
                    com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.34
                        @Override // com.igexin.push.b.d
                        public final void a_() throws Exception {
                            f.b(this.d, 14, g.a(com.igexin.push.core.e.an.getBytes()));
                        }
                    }, z2, true);
                }
                strD = k.d();
                if (com.igexin.push.core.e.H == null) {
                    com.igexin.push.core.e.H = strD;
                    com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.36
                        @Override // com.igexin.push.b.d
                        public final void a_() throws Exception {
                            f.b(this.d, 2, com.igexin.push.core.e.H);
                        }
                    }, z2, true);
                }
                if (com.igexin.push.core.e.L == null) {
                }
                d dVarA = d.a(com.igexin.push.core.e.l);
                jSONObjectC = dVarA.c();
                strOptString = jSONObjectC.optString(AssistPushConsts.MSG_TYPE_TOKEN, str);
                if (strOptString != null) {
                    com.igexin.push.core.e.I = strOptString;
                    if (jSONObjectC.optBoolean("isForce")) {
                    }
                    if (com.igexin.push.core.e.I != null) {
                    }
                }
                if (this.b) {
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            if (com.igexin.push.core.e.z == 0) {
                long jE = k.e();
                if (jE != 0) {
                    com.igexin.push.core.e.z = jE;
                    com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.1
                        @Override // com.igexin.push.b.d
                        public final void a_() throws Exception {
                            f.b(this.d, 1, g.a(String.valueOf(com.igexin.push.core.e.z).getBytes()));
                        }
                    }, z2, true);
                }
            }
            if (com.igexin.push.core.e.A == null && (strC = k.c()) != null) {
                com.igexin.push.core.e.B = strC;
                com.igexin.push.core.e.A = strC;
                com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.12
                    @Override // com.igexin.push.b.d
                    public final void a_() throws Exception {
                        f.b(this.d, 20, g.a(com.igexin.push.core.e.A.getBytes()));
                    }
                }, z2, true);
            }
            if (com.igexin.push.core.e.A == null && com.igexin.push.core.e.z != 0) {
                com.igexin.push.core.e.B = com.igexin.c.b.a.b(String.valueOf(com.igexin.push.core.e.z));
                com.igexin.push.core.e.a(com.igexin.push.core.e.z);
                com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.23
                    @Override // com.igexin.push.b.d
                    public final void a_() throws Exception {
                        f.b(this.d, 20, g.a(com.igexin.push.core.e.A.getBytes()));
                    }
                }, z2, true);
            }
            if ("cfcd208495d565ef66e7dff9f98764da".equals(com.igexin.push.core.e.A) && (com.igexin.push.core.e.A == null || com.igexin.push.core.e.A.matches("([a-f]|[0-9]){32}"))) {
                str = null;
            } else if (com.igexin.push.core.e.z == 0) {
                a().a(com.igexin.push.core.e.z);
                com.igexin.push.core.e.B = com.igexin.push.core.e.A;
                k.g();
                str = null;
            } else {
                str = null;
                com.igexin.push.core.e.B = null;
                com.igexin.push.core.e.A = "null";
                com.igexin.push.core.e.z = 0L;
            }
            if (!TextUtils.isEmpty(com.igexin.push.core.e.an) || "null".equals(com.igexin.push.core.e.an)) {
                com.igexin.push.core.e.an = com.igexin.c.b.a.a();
                com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.34
                    @Override // com.igexin.push.b.d
                    public final void a_() throws Exception {
                        f.b(this.d, 14, g.a(com.igexin.push.core.e.an.getBytes()));
                    }
                }, z2, true);
            }
            strD = k.d();
            if (com.igexin.push.core.e.H == null && strD != null && strD.length() > 5) {
                com.igexin.push.core.e.H = strD;
                com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.36
                    @Override // com.igexin.push.b.d
                    public final void a_() throws Exception {
                        f.b(this.d, 2, com.igexin.push.core.e.H);
                    }
                }, z2, true);
            }
            if (com.igexin.push.core.e.L == null) {
                d();
                com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.37
                    @Override // com.igexin.push.b.d
                    public final void a_() throws Exception {
                        f.b(this.d, 3, com.igexin.push.core.e.L);
                        String unused2 = f.a;
                        String str13 = com.igexin.push.core.e.L;
                    }
                }, z2, true);
            }
            d dVarA2 = d.a(com.igexin.push.core.e.l);
            jSONObjectC = dVarA2.c();
            strOptString = jSONObjectC.optString(AssistPushConsts.MSG_TYPE_TOKEN, str);
            if (strOptString != null && !strOptString.equals(com.igexin.push.core.e.I)) {
                com.igexin.push.core.e.I = strOptString;
                if (jSONObjectC.optBoolean("isForce")) {
                    c("");
                }
                if (com.igexin.push.core.e.I != null) {
                    b(com.igexin.push.core.e.I);
                    dVarA2.a(new JSONObject());
                }
            }
            if (this.b) {
                return;
            }
            this.b = z2;
            if (!TextUtils.isEmpty(com.igexin.push.core.e.M)) {
                b(sQLiteDatabase, 25, com.igexin.c.a.a.a.b(com.igexin.push.core.e.M.getBytes(), com.igexin.c.b.a.b(com.igexin.push.core.e.l.getPackageName())));
            }
            if (com.igexin.push.core.e.z != 0) {
                b(sQLiteDatabase, 1, g.a(String.valueOf(com.igexin.push.core.e.z).getBytes()));
            }
            if (!TextUtils.isEmpty(com.igexin.push.core.e.A)) {
                b(sQLiteDatabase, 20, g.a(com.igexin.push.core.e.A.getBytes()));
            }
            if (!TextUtils.isEmpty(com.igexin.push.core.e.H) && com.igexin.push.core.e.H.length() > 5) {
                b(sQLiteDatabase, 2, com.igexin.push.core.e.H);
            }
            if (!TextUtils.isEmpty(com.igexin.push.core.e.L)) {
                b(sQLiteDatabase, 3, com.igexin.push.core.e.L);
            }
            if (!TextUtils.isEmpty(com.igexin.push.core.e.I)) {
                b(sQLiteDatabase, 46, com.igexin.push.core.e.I);
            }
            if (!TextUtils.isEmpty(com.igexin.push.core.e.C)) {
                b(sQLiteDatabase, 51, com.igexin.push.core.e.C);
            }
            if (TextUtils.isEmpty(com.igexin.push.core.e.K)) {
                return;
            }
            b(sQLiteDatabase, 48, com.igexin.push.core.e.K);
        } catch (Throwable th3) {
            th = th3;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public final boolean b() {
        com.igexin.push.core.e.z = 0L;
        com.igexin.push.core.e.A = "null";
        d();
        return c();
    }

    public final boolean b(int i2) {
        if (com.igexin.push.core.e.aA == i2) {
            return false;
        }
        com.igexin.push.core.e.aA = i2;
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.22
            @Override // com.igexin.push.b.d
            public final void a_() {
                f.a();
                f.b(this.d, 47, String.valueOf(com.igexin.push.core.e.aA));
            }
        }, false, true);
    }

    public final boolean b(final long j2) {
        com.igexin.push.core.e.ao = j2;
        com.igexin.c.a.c.a.a(a + "|save idc config failed time : " + j2, new Object[0]);
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.8
            @Override // com.igexin.push.b.d
            public final void a_() {
                f.a();
                f.b(this.d, 21, String.valueOf(j2));
            }
        }, false, true);
    }

    public final boolean b(String str) {
        com.igexin.push.core.e.I = str;
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.5
            @Override // com.igexin.push.b.d
            public final void a_() {
                f.a();
                f.b(this.d, 46, com.igexin.push.core.e.I);
            }
        }, false, true);
    }

    public final boolean b(final String str, boolean z2) {
        com.igexin.c.a.b.e eVarA;
        com.igexin.push.b.d dVar;
        if (str == null) {
            return false;
        }
        if (z2) {
            if (!str.equals(com.igexin.push.core.e.ap)) {
                com.igexin.push.core.e.ap = str.equals("null") ? null : str;
                eVarA = com.igexin.c.a.b.e.a();
                dVar = new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.17
                    @Override // com.igexin.push.b.d
                    public final void a_() throws Exception {
                        f.a();
                        f.b(this.d, 23, f.g(str));
                    }
                };
                return eVarA.a((com.igexin.c.a.d.f) dVar, false, true);
            }
            return false;
        }
        if (!str.equals(com.igexin.push.core.e.aq)) {
            com.igexin.push.core.e.aq = str.equals("null") ? null : str;
            eVarA = com.igexin.c.a.b.e.a();
            dVar = new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.18
                @Override // com.igexin.push.b.d
                public final void a_() throws Exception {
                    f.a();
                    f.b(this.d, 22, f.g(str));
                }
            };
            return eVarA.a((com.igexin.c.a.d.f) dVar, false, true);
        }
        return false;
    }

    public final boolean b(final boolean z2) {
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.25
            @Override // com.igexin.push.b.d
            public final void a_() {
                f.a();
                f.b(this.d, 40, String.valueOf(z2));
            }
        }, false, true);
    }

    @Override // com.igexin.push.core.e.a
    public final void c(SQLiteDatabase sQLiteDatabase) {
        byte[] bArrB = com.igexin.c.a.a.a.b(String.valueOf(com.igexin.push.core.e.z).getBytes(), com.igexin.push.core.e.M);
        long j2 = com.igexin.push.core.e.z;
        b(sQLiteDatabase, 1, bArrB);
        b(sQLiteDatabase, 4, String.valueOf(com.igexin.push.core.e.t));
        b(sQLiteDatabase, 8, String.valueOf(com.igexin.push.core.e.R));
        b(sQLiteDatabase, 32, String.valueOf(com.igexin.push.core.e.U));
        b(sQLiteDatabase, 3, com.igexin.push.core.e.L);
        b(sQLiteDatabase, 12, String.valueOf(com.igexin.push.core.e.T));
        b(sQLiteDatabase, 20, com.igexin.c.a.a.a.b(com.igexin.push.core.e.A.getBytes(), com.igexin.push.core.e.M));
        b(sQLiteDatabase, 2, com.igexin.push.core.e.H);
        b(sQLiteDatabase, 25, com.igexin.c.a.a.a.b(com.igexin.push.core.e.M.getBytes(), com.igexin.c.b.a.b(com.igexin.push.core.e.l.getPackageName())));
    }

    public final boolean c() {
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.40
            @Override // com.igexin.push.b.d
            public final void a_() throws Throwable {
                f.a();
                f.b(this.d, 2, com.igexin.push.core.e.H);
                f.b(this.d, 1, f.g(String.valueOf(com.igexin.push.core.e.z)));
                f.b(this.d, 20, f.g(com.igexin.push.core.e.A));
                f.b(this.d, 3, com.igexin.push.core.e.L);
                f.f();
            }
        }, false, true);
    }

    public final boolean c(long j2) {
        if (com.igexin.push.core.e.Q == j2) {
            return false;
        }
        com.igexin.push.core.e.Q = j2;
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.21
            @Override // com.igexin.push.b.d
            public final void a_() {
                if (this.d != null) {
                    f.a();
                    f.b(this.d, 6, String.valueOf(com.igexin.push.core.e.Q));
                }
            }
        }, false, true);
    }

    public final boolean c(String str) {
        com.igexin.push.core.e.K = str;
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.6
            @Override // com.igexin.push.b.d
            public final void a_() {
                f.a();
                f.b(this.d, 48, com.igexin.push.core.e.K);
            }
        }, false, true);
    }

    public final boolean c(final String str, final boolean z2) {
        if (str == null) {
            return false;
        }
        String str2 = str.equals("null") ? null : str;
        if (z2 && !TextUtils.equals(com.igexin.push.core.e.au, str)) {
            com.igexin.push.core.e.au = str2;
        } else {
            if (z2 || TextUtils.equals(com.igexin.push.core.e.at, str)) {
                return false;
            }
            com.igexin.push.core.e.at = str2;
        }
        com.igexin.c.a.c.a.a(a + "|saveLastRedirectCmList isMobile = " + z2 + ", lastRedirectCmList = " + str, new Object[0]);
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.26
            @Override // com.igexin.push.b.d
            public final void a_() throws Exception {
                f.a();
                f.b(this.d, z2 ? 50 : 49, f.g(str));
            }
        }, false, true);
    }

    public final boolean d(long j2) {
        if (com.igexin.push.core.e.aw == j2) {
            return false;
        }
        com.igexin.push.core.e.aw = j2;
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.30
            @Override // com.igexin.push.b.d
            public final void a_() throws Exception {
                f.a();
                f.b(this.d, 63, String.valueOf(com.igexin.push.core.e.aw));
            }
        }, false, true);
    }

    public final boolean d(String str) {
        if (str.equals(com.igexin.push.core.e.Z)) {
            return false;
        }
        com.igexin.push.core.e.Z = str;
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.13
            @Override // com.igexin.push.b.d
            public final void a_() {
                f.a();
                f.b(this.d, 17, String.valueOf(com.igexin.push.core.e.Z));
            }
        }, false, true);
    }

    public final void e(long j2) {
        if (com.igexin.push.core.e.ay != j2) {
            com.igexin.push.core.e.ay = j2;
            com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.32
                @Override // com.igexin.push.b.d
                public final void a_() throws Exception {
                    f.a();
                    f.b(this.d, 65, String.valueOf(com.igexin.push.core.e.ay));
                }
            }, false, true);
        }
    }

    public final boolean e(final String str) {
        com.igexin.push.core.e.d = str.equals("null") ? null : str;
        return com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.29
            @Override // com.igexin.push.b.d
            public final void a_() throws Exception {
                f.a();
                f.b(this.d, 61, g.a(str.getBytes()));
            }
        }, false, true);
    }

    public final void f(String str) {
        if (str.equals(com.igexin.push.core.e.aN)) {
            return;
        }
        com.igexin.push.core.e.aN = str;
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.e.f.33
            @Override // com.igexin.push.b.d
            public final void a_() throws Exception {
                f.a();
                f.b(this.d, 68, com.igexin.push.core.e.aN);
                f.a();
                f.b(this.d, 69, com.igexin.push.core.e.aN);
            }
        }, false, true);
    }
}
