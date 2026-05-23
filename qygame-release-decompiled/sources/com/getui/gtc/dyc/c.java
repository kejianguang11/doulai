package com.getui.gtc.dyc;

import android.content.ContentValues;
import android.database.Cursor;
import com.getui.gtc.base.GtcProvider;
import com.getui.gtc.base.db.AbstractDb;
import com.getui.gtc.base.db.AbstractTable;
import com.getui.gtc.base.db.DbManager;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
class c {

    public static class a extends AbstractDb {
        @Override // com.getui.gtc.base.db.AbstractDb
        public String getDbName() {
            return "cg.db";
        }

        @Override // com.getui.gtc.base.db.AbstractDb
        public int getVersion() {
            return 1;
        }
    }

    /* JADX INFO: renamed from: com.getui.gtc.dyc.c$c, reason: collision with other inner class name */
    static class C0019c {
        private static c a = new c();
    }

    public static class d extends AbstractTable {
        @Override // com.getui.gtc.base.db.AbstractTable
        public String createSql() {
            return "CREATE TABLE IF NOT EXISTS sct (v TEXT PRIMARY KEY, c TEXT)";
        }

        @Override // com.getui.gtc.base.db.AbstractTable
        public String getTableName() {
            return "sct";
        }
    }

    private c() {
        try {
            DbManager.init(GtcProvider.context(), a.class, d.class);
        } catch (Throwable th) {
            com.getui.gtc.dyc.a.a.a.c(th);
        }
    }

    static c a() {
        return C0019c.a;
    }

    h a(String str) throws Throwable {
        Cursor cursorQuery = ((d) DbManager.getTable(a.class, d.class)).query(new String[]{com.igexin.push.core.d.d.d}, "v=?", new String[]{str});
        h hVarE = null;
        if (cursorQuery == null) {
            return null;
        }
        if (cursorQuery.moveToNext()) {
            try {
                hVarE = h.e(cursorQuery.getString(cursorQuery.getColumnIndex(com.igexin.push.core.d.d.d)));
            } catch (Throwable th) {
                com.getui.gtc.dyc.a.a.a.c(th);
            }
        }
        cursorQuery.close();
        return hVarE;
    }

    boolean a(String str, h hVar) {
        try {
            String strG = hVar.g();
            ContentValues contentValues = new ContentValues();
            contentValues.put("v", str);
            contentValues.put(com.igexin.push.core.d.d.d, strG);
            return ((d) DbManager.getTable(a.class, d.class)).replace(null, contentValues) != -1;
        } catch (Throwable th) {
            com.getui.gtc.dyc.a.a.a.c(th);
            return false;
        }
    }

    HashMap<String, h> c() {
        Cursor cursorQuery = ((d) DbManager.getTable(a.class, d.class)).query(new String[]{"v", com.igexin.push.core.d.d.d}, null, null);
        if (cursorQuery == null) {
            return null;
        }
        HashMap<String, h> map = new HashMap<>();
        while (cursorQuery.moveToNext()) {
            try {
                map.put(cursorQuery.getString(cursorQuery.getColumnIndex("v")), h.e(cursorQuery.getString(cursorQuery.getColumnIndex(com.igexin.push.core.d.d.d))));
            } catch (Throwable th) {
                com.getui.gtc.dyc.a.a.a.c(th);
            }
        }
        cursorQuery.close();
        return map;
    }
}
