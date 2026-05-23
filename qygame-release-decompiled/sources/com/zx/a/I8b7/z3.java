package com.zx.a.I8b7;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Base64;
import com.gme.liteav.TXLiteAVCode;
import com.zx.a.I8b7.p2;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class z3 extends c {
    public SQLiteDatabase b = null;

    @Override // com.zx.a.I8b7.c
    public String a() {
        return "CREATE TABLE IF NOT EXISTS zx_table (key integer primary key, value text)";
    }

    public String a(int i) {
        Cursor cursorQuery;
        String str = "";
        Cursor cursor = null;
        try {
            cursorQuery = b().query(c(), new String[]{"key", "value"}, "key=" + i, null, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToNext()) {
                        String string = cursorQuery.getString(cursorQuery.getColumnIndex("value"));
                        try {
                            str = new String(r.a("AES/CBC/PKCS5Padding", q3.x, q3.y, Base64.decode(string, 0)), StandardCharsets.UTF_8);
                        } catch (Throwable th) {
                            cursor = cursorQuery;
                            th = th;
                            str = string;
                            try {
                                v2.a(th);
                                if (cursor != null) {
                                    cursorQuery = cursor;
                                    cursorQuery.close();
                                }
                            } catch (Throwable th2) {
                                if (cursor != null) {
                                    cursor.close();
                                }
                                throw th2;
                            }
                        }
                    }
                } catch (Throwable th3) {
                    cursor = cursorQuery;
                    th = th3;
                }
            }
        } catch (Throwable th4) {
            th = th4;
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return str;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0052 A[DONT_GENERATE, PHI: r11
      0x0052: PHI (r11v3 android.database.Cursor) = (r11v2 android.database.Cursor), (r11v4 android.database.Cursor) binds: [B:17:0x0050, B:12:0x0048] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public JSONObject a(IvParameterSpec ivParameterSpec, SecretKey secretKey) {
        Cursor cursorQuery;
        try {
            cursorQuery = b().query("zx_table", new String[]{"key", "value"}, "key=18", null, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToNext()) {
                        JSONObject jSONObject = new JSONObject(new String(r.a("AES/CBC/PKCS5Padding", secretKey, ivParameterSpec, Base64.decode(cursorQuery.getString(cursorQuery.getColumnIndex("value")), 0)), StandardCharsets.UTF_8));
                        cursorQuery.close();
                        return jSONObject;
                    }
                } catch (Throwable th) {
                    th = th;
                    try {
                        v2.a(th);
                    } finally {
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                    }
                }
            }
        } catch (Throwable th2) {
            th = th2;
            cursorQuery = null;
        }
        if (cursorQuery != null) {
        }
        return null;
    }

    public final void a(int i, String str, boolean z) {
        String str2;
        if (this.b == null) {
            this.b = d();
        }
        if (z) {
            try {
                str2 = new String(Base64.encode(r.b("AES/CBC/PKCS5Padding", q3.x, q3.y, str.getBytes()), 0), StandardCharsets.UTF_8);
            } catch (Exception e) {
                v2.b("ZXID updateDBValue valueID:" + i + ",value:" + str + ",error:" + e.toString());
                return;
            }
        } else {
            str2 = str;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("key", Integer.valueOf(i));
        contentValues.put("value", str2);
        v2.a("replace resultId = " + this.b.replace("zx_table", null, contentValues));
    }

    @Override // com.zx.a.I8b7.c
    public void a(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        v2.a("ZXID数据库升级, drop zx_table表");
        try {
            sQLiteDatabase.execSQL("drop table if exists zx_table");
        } catch (Exception e) {
            v2.a(e);
        }
        sQLiteDatabase.beginTransaction();
        try {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS zx_table (key integer primary key, value text)");
            sQLiteDatabase.setTransactionSuccessful();
        } finally {
            sQLiteDatabase.endTransaction();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void a(String str) {
        Cursor cursorQuery;
        IvParameterSpec ivParameterSpecG;
        SecretKey secretKeyI;
        JSONObject jSONObjectA;
        try {
            ivParameterSpecG = g();
            secretKeyI = i();
            jSONObjectA = a(ivParameterSpecG, secretKeyI);
        } catch (Throwable th) {
            th = th;
            cursorQuery = null;
        }
        try {
            if (jSONObjectA == null) {
                return;
            }
            String string = jSONObjectA.getString("mainVersion");
            String string2 = jSONObjectA.getString("checksum");
            if (TextUtils.equals(string, str)) {
                cursorQuery = b().query("zx_table", new String[]{"key", "value"}, "key=17", null, null, null, null);
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.moveToNext()) {
                            byte[] bArrA = r.a("AES/CBC/PKCS5Padding", secretKeyI, ivParameterSpecG, Base64.decode(cursorQuery.getString(cursorQuery.getColumnIndex("value")), 0));
                            if (!TextUtils.equals(string2, r.a("SHA256", bArrA))) {
                                throw new IOException("zx checksum1 exception");
                            }
                            a(str, bArrA);
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        v2.a(th);
                    }
                }
                if (cursorQuery == null) {
                    return;
                }
            }
            return;
            v2.a(th);
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    public final void a(String str, byte[] bArr) throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        File file = new File(q3.a.getFilesDir().getAbsolutePath() + File.separator + "zx-core-" + str + ".zip");
        if (!file.createNewFile()) {
            throw new IOException("zx createNewFile exception");
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        byte[] bArr2 = new byte[2048];
        while (true) {
            int i = byteArrayInputStream.read(bArr2);
            if (i == -1) {
                fileOutputStream.close();
                byteArrayInputStream.close();
                return;
            }
            fileOutputStream.write(bArr2, 0, i);
        }
    }

    public void a(JSONObject jSONObject) {
        q3.m = jSONObject.toString();
        p2.a.a.a.a(30, q3.m, true);
        v2.a("zxc had changed refresh:" + q3.m);
    }

    public void a(byte[] bArr) {
        String str = new String(Base64.encode(bArr, 0), StandardCharsets.UTF_8);
        a(9, str + "", false);
        v2.a("ZXID saveSecretKey secretStr:" + str);
    }

    public void b(int i) {
        if (i != q3.t) {
            q3.t = i;
            q3.v = -1;
            a(14, q3.t + "", false);
            a(7, q3.v + "", false);
        }
    }

    public void b(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        p2.a.a.a.a(TXLiteAVCode.WARNING_IGNORE_UPSTREAM_FOR_AUDIENCE, str, true);
        v2.a("vb had changed refresh:" + str);
    }

    @Override // com.zx.a.I8b7.c
    public String c() {
        return "zx_table";
    }

    public void c(int i) {
        if (i != q3.v) {
            q3.v = i;
            a(7, q3.v + "", false);
        }
    }

    public void d(int i) {
        if (i != q3.p) {
            q3.p = i;
            p2.a.a.a.a(3, q3.p + "", false);
            v2.a("syncId had changed refresh:" + i);
        }
    }

    public int e() {
        Cursor cursorQuery;
        Cursor cursor = null;
        try {
            cursorQuery = b().query(c(), new String[]{"key", "value"}, "key=20", null, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToNext()) {
                        q3.u = Integer.parseInt(cursorQuery.getString(cursorQuery.getColumnIndex("value")));
                    }
                } catch (Throwable th) {
                    cursor = cursorQuery;
                    th = th;
                    try {
                        v2.a(th);
                        if (cursor != null) {
                            cursorQuery = cursor;
                            cursorQuery.close();
                        }
                    } catch (Throwable th2) {
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th2;
                    }
                }
            }
        } catch (Throwable th3) {
            th = th3;
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return q3.u;
    }

    public int f() {
        Cursor cursorQuery;
        Cursor cursor = null;
        try {
            cursorQuery = b().query(c(), new String[]{"key", "value"}, "key=14", null, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToNext()) {
                        q3.t = Integer.parseInt(cursorQuery.getString(cursorQuery.getColumnIndex("value")));
                    }
                } catch (Throwable th) {
                    cursor = cursorQuery;
                    th = th;
                    try {
                        v2.a(th);
                        if (cursor != null) {
                            cursorQuery = cursor;
                            cursorQuery.close();
                        }
                    } catch (Throwable th2) {
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th2;
                    }
                }
            }
        } catch (Throwable th3) {
            th = th3;
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return q3.t;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0045 A[DONT_GENERATE, PHI: r0 r11
      0x0045: PHI (r0v2 javax.crypto.spec.IvParameterSpec) = (r0v0 javax.crypto.spec.IvParameterSpec), (r0v5 javax.crypto.spec.IvParameterSpec) binds: [B:16:0x0043, B:11:0x003b] A[DONT_GENERATE, DONT_INLINE]
      0x0045: PHI (r11v3 android.database.Cursor) = (r11v2 android.database.Cursor), (r11v4 android.database.Cursor) binds: [B:16:0x0043, B:11:0x003b] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public IvParameterSpec g() {
        Cursor cursorQuery;
        IvParameterSpec ivParameterSpec = null;
        try {
            cursorQuery = b().query(c(), new String[]{"key", "value"}, "key=10", null, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToNext()) {
                        ivParameterSpec = new IvParameterSpec(Base64.decode(cursorQuery.getString(cursorQuery.getColumnIndex("value")), 0));
                    }
                } catch (Throwable th) {
                    th = th;
                    try {
                        v2.a(th);
                    } finally {
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                    }
                }
            }
        } catch (Throwable th2) {
            th = th2;
            cursorQuery = null;
        }
        if (cursorQuery != null) {
        }
        return ivParameterSpec;
    }

    public int h() {
        Cursor cursorQuery;
        Cursor cursor = null;
        try {
            cursorQuery = b().query(c(), new String[]{"key", "value"}, "key=7", null, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToNext()) {
                        q3.v = Integer.parseInt(cursorQuery.getString(cursorQuery.getColumnIndex("value")));
                    }
                } catch (Throwable th) {
                    cursor = cursorQuery;
                    th = th;
                    try {
                        v2.a(th);
                        if (cursor != null) {
                            cursorQuery = cursor;
                            cursorQuery.close();
                        }
                    } catch (Throwable th2) {
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th2;
                    }
                }
            }
        } catch (Throwable th3) {
            th = th3;
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return q3.v;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0049 A[DONT_GENERATE, PHI: r0 r11
      0x0049: PHI (r0v2 javax.crypto.spec.SecretKeySpec) = (r0v0 javax.crypto.spec.SecretKeySpec), (r0v5 javax.crypto.spec.SecretKeySpec) binds: [B:16:0x0047, B:11:0x003f] A[DONT_GENERATE, DONT_INLINE]
      0x0049: PHI (r11v3 android.database.Cursor) = (r11v2 android.database.Cursor), (r11v4 android.database.Cursor) binds: [B:16:0x0047, B:11:0x003f] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SecretKey i() {
        Cursor cursorQuery;
        SecretKeySpec secretKeySpec = null;
        try {
            cursorQuery = b().query(c(), new String[]{"key", "value"}, "key=9", null, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToNext()) {
                        byte[] bArrDecode = Base64.decode(cursorQuery.getString(cursorQuery.getColumnIndex("value")), 0);
                        SecureRandom secureRandom = r.a;
                        secretKeySpec = new SecretKeySpec(bArrDecode, "AES");
                    }
                } catch (Throwable th) {
                    th = th;
                    try {
                        v2.a(th);
                    } finally {
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                    }
                }
            }
        } catch (Throwable th2) {
            th = th2;
            cursorQuery = null;
        }
        if (cursorQuery != null) {
        }
        return secretKeySpec;
    }
}
