package org.cocos2dx.lib;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class Cocos2dxLocalStorage {
    private static String DATABASE_NAME = "jsb.sqlite";
    private static final int DATABASE_VERSION = 1;
    private static String TABLE_NAME = "data";
    private static final String TAG = "Cocos2dxLocalStorage";
    private static SQLiteDatabase mDatabase;
    private static DBOpenHelper mDatabaseOpenHelper;

    private static class DBOpenHelper extends SQLiteOpenHelper {
        DBOpenHelper(Context context) {
            super(context, Cocos2dxLocalStorage.DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + Cocos2dxLocalStorage.TABLE_NAME + "(key TEXT PRIMARY KEY,value TEXT);");
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            Log.w(Cocos2dxLocalStorage.TAG, "Upgrading database from version " + i + " to " + i2 + ", which will destroy all old data");
        }
    }

    public static void clear() {
        try {
            mDatabase.execSQL("delete from " + TABLE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void destroy() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    public static String getItem(String str) {
        String string = null;
        try {
            Cursor cursorRawQuery = mDatabase.rawQuery("select value from " + TABLE_NAME + " where key=?", new String[]{str});
            while (true) {
                if (!cursorRawQuery.moveToNext()) {
                    break;
                }
                if (string != null) {
                    Log.e(TAG, "The key contains more than one value.");
                    break;
                }
                string = cursorRawQuery.getString(cursorRawQuery.getColumnIndex("value"));
            }
            cursorRawQuery.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    public static String getKey(int i) {
        Cursor cursorRawQuery;
        int i2 = 0;
        String string = null;
        try {
            cursorRawQuery = mDatabase.rawQuery("select key from " + TABLE_NAME + " order by rowid asc", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (i >= 0 && i < cursorRawQuery.getCount()) {
            while (true) {
                if (!cursorRawQuery.moveToNext()) {
                    break;
                }
                if (i2 == i) {
                    string = cursorRawQuery.getString(cursorRawQuery.getColumnIndex("key"));
                    break;
                }
                i2++;
            }
            cursorRawQuery.close();
            return string;
        }
        return null;
    }

    public static int getLength() {
        try {
            Cursor cursorRawQuery = mDatabase.rawQuery("select count(*) as nums from " + TABLE_NAME, null);
            i = cursorRawQuery.moveToNext() ? cursorRawQuery.getInt(cursorRawQuery.getColumnIndex("nums")) : 0;
            cursorRawQuery.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public static boolean init(String str, String str2) {
        if (Cocos2dxActivity.getContext() == null) {
            return false;
        }
        DATABASE_NAME = str;
        TABLE_NAME = str2;
        mDatabaseOpenHelper = new DBOpenHelper(Cocos2dxActivity.getContext());
        mDatabase = mDatabaseOpenHelper.getWritableDatabase();
        return true;
    }

    public static void removeItem(String str) {
        try {
            mDatabase.execSQL("delete from " + TABLE_NAME + " where key=?", new Object[]{str});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setItem(String str, String str2) {
        try {
            mDatabase.execSQL("replace into " + TABLE_NAME + "(key,value)values(?,?)", new Object[]{str, str2});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
