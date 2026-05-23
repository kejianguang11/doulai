package com.loc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* JADX INFO: loaded from: classes.dex */
public final class au extends SQLiteOpenHelper {
    private static boolean b = true;
    private static boolean c = false;
    private aq a;

    public au(Context context, String str, aq aqVar) {
        super(context, str, (SQLiteDatabase.CursorFactory) null, 1);
        this.a = aqVar;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        this.a.a(sQLiteDatabase);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}
