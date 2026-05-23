package com.nirvana.tools.logger.cache.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class DBHelper extends SQLiteOpenHelper {
    private String mCreateSql;
    private String mDeleteSql;
    private String mIndexSql;

    public DBHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i, String str2, String str3, String str4) {
        super(context, str, cursorFactory, i);
        this.mCreateSql = str2;
        this.mDeleteSql = str3;
        this.mIndexSql = str4;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL(this.mCreateSql);
            sQLiteDatabase.execSQL(this.mIndexSql);
            sQLiteDatabase.execSQL("PRAGMA auto_vacuum = FULL");
        } catch (Exception e) {
            Log.e("DbHelper", "Database onCreate Exception", e);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        try {
            sQLiteDatabase.execSQL(this.mDeleteSql);
            onCreate(sQLiteDatabase);
        } catch (Exception e) {
            Log.e("DbHelper", "Database onUpgrade Exception", e);
        }
    }
}
