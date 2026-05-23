package com.getui.gtc.dim.a;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.sdk.util.e;
import com.getui.gtc.base.GtcProvider;
import com.getui.gtc.base.crypt.CryptTools;
import com.getui.gtc.base.crypt.SecureCryptTools;
import com.getui.gtc.base.db.AbstractTable;
import com.getui.gtc.dim.b.h;
import com.getui.gtc.dim.e.c;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class b extends AbstractTable {
    public final h a(String str) throws Throwable {
        Cursor cursorQuery;
        h hVar;
        Cursor cursor = null;
        hVar = null;
        h hVar2 = null;
        cursor = null;
        try {
            try {
                cursorQuery = getReadableDatabase().query("d", new String[]{"t", "b"}, "a=?", new String[]{String.valueOf(str)}, null, null, null);
                if (cursorQuery != null) {
                    try {
                        try {
                            if (cursorQuery.moveToNext()) {
                                String string = cursorQuery.getString(cursorQuery.getColumnIndex("b"));
                                hVar = new h(null, cursorQuery.getLong(cursorQuery.getColumnIndex("t")));
                                try {
                                    if (!TextUtils.isEmpty(string)) {
                                        if (string.equals("-1")) {
                                            File file = new File(GtcProvider.context().getCacheDir(), CryptTools.digestToHexString("MD5", (str + ".db").getBytes()));
                                            if (file.exists()) {
                                                string = new String(c.a(file));
                                            }
                                        }
                                        hVar.a = c.a(SecureCryptTools.getInstance().decrypt(Base64.decode(string, 0)));
                                    }
                                    hVar2 = hVar;
                                } catch (Throwable th) {
                                    th = th;
                                    cursor = cursorQuery;
                                    com.getui.gtc.dim.e.b.a(th);
                                    if (cursor != null) {
                                        cursor.close();
                                    }
                                    return hVar;
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            hVar = null;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        throw th;
                    }
                }
                if (cursorQuery == null) {
                    return hVar2;
                }
                cursorQuery.close();
                return hVar2;
            } catch (Throwable th4) {
                th = th4;
                hVar = null;
            }
        } catch (Throwable th5) {
            th = th5;
            cursorQuery = cursor;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0060  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void a() throws Throwable {
        Cursor cursorQuery;
        String string;
        String str = Build.BRAND + "#" + Build.VERSION.SDK_INT + "-" + Build.VERSION.RELEASE;
        Cursor cursor = null;
        try {
            try {
                cursorQuery = query(new String[]{"a", "t", "b"}, "a=?", new String[]{"dim-key-android-rom-version"});
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.moveToNext()) {
                            string = cursorQuery.getString(cursorQuery.getColumnIndex("b"));
                            com.getui.gtc.dim.e.b.a("dim storage query dbRomVersion = ".concat(String.valueOf(string)));
                        } else {
                            string = null;
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        throw th;
                    }
                }
                if (str.equals(string)) {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                        return;
                    }
                    return;
                }
                com.getui.gtc.dim.e.b.a("dim storage will delete parcelable data");
                String[] strArr = {"dim-2-1-18-1", "dim-2-1-18-2", "dim-2-1-17-1", "dim-2-1-17-2", "dim-2-1-21-2", "dim-2-1-21-3", "dim-2-1-21-5", "dim-2-1-21-1"};
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 8; i++) {
                    sb.append("a=?");
                    if (i != 7) {
                        sb.append(" or ");
                    }
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("b", "");
                update(contentValues, sb.toString(), strArr);
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("a", "dim-key-android-rom-version");
                contentValues2.put("t", Long.valueOf(System.currentTimeMillis()));
                contentValues2.put("b", str);
                long jReplace = replace(null, contentValues2);
                StringBuilder sb2 = new StringBuilder("dim storage update dbRomVersion ");
                sb2.append(jReplace != -1 ? "success" : e.b);
                sb2.append(",currentRomVersion = ");
                sb2.append(str);
                com.getui.gtc.dim.e.b.a(sb2.toString());
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

    public final boolean a(String str, Object obj) {
        String strEncodeToString;
        try {
            try {
                strEncodeToString = Base64.encodeToString(SecureCryptTools.getInstance().encrypt(c.b(obj)), 0);
                if (strEncodeToString.length() > 5120) {
                    c.a(strEncodeToString.getBytes(), new File(GtcProvider.context().getCacheDir(), CryptTools.digestToHexString("MD5", (str + ".db").getBytes())));
                    strEncodeToString = "-1";
                }
            } catch (Throwable unused) {
                strEncodeToString = "";
                com.getui.gtc.dim.e.b.b("dim storage save failed: ".concat(String.valueOf(obj)));
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("a", str);
            contentValues.put("t", Long.valueOf(System.currentTimeMillis()));
            contentValues.put("b", strEncodeToString);
            com.getui.gtc.dim.e.b.a(str + " update dim storage cache = " + strEncodeToString);
            return replace(null, contentValues) != -1;
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
            return false;
        }
    }

    @Override // com.getui.gtc.base.db.AbstractTable
    public String createSql() {
        return "CREATE TABLE IF NOT EXISTS d (a TEXT PRIMARY KEY, t TEXT, b TEXT)";
    }

    @Override // com.getui.gtc.base.db.AbstractTable
    public String getTableName() {
        return "d";
    }
}
