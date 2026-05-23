package com.tencent.mm.opensdk.openapi;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import com.android.vending.expansion.zipfile.APEZProvider;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.a;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
class MMSharedPreferences implements SharedPreferences {
    private static final String TAG = "MicroMsg.SDK.SharedPreferences";
    private final ContentResolver cr;
    private final String[] columns = {APEZProvider.FILEID, "key", "type", "value"};
    private final HashMap<String, Object> values = new HashMap<>();
    private REditor editor = null;

    private static class REditor implements SharedPreferences.Editor {
        private ContentResolver cr;
        private Map<String, Object> values = new HashMap();
        private Set<String> remove = new HashSet();
        private boolean clear = false;

        public REditor(ContentResolver contentResolver) {
            this.cr = contentResolver;
        }

        @Override // android.content.SharedPreferences.Editor
        public void apply() {
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor clear() {
            this.clear = true;
            return this;
        }

        /* JADX WARN: Removed duplicated region for block: B:36:0x009f  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x00a1  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x00b6 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:45:0x003f A[SYNTHETIC] */
        @Override // android.content.SharedPreferences.Editor
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean commit() {
            int i;
            boolean z;
            ContentValues contentValues = new ContentValues();
            if (this.clear) {
                this.cr.delete(a.a, null, null);
                this.clear = false;
            }
            Iterator<String> it = this.remove.iterator();
            while (it.hasNext()) {
                this.cr.delete(a.a, "key = ?", new String[]{it.next()});
            }
            for (Map.Entry<String, Object> entry : this.values.entrySet()) {
                Object value = entry.getValue();
                if (value == null) {
                    Log.e("MicroMsg.SDK.PluginProvider.Resolver", "unresolve failed, null value");
                } else {
                    if (value instanceof Integer) {
                        i = 1;
                    } else if (value instanceof Long) {
                        i = 2;
                    } else if (value instanceof String) {
                        i = 3;
                    } else if (value instanceof Boolean) {
                        i = 4;
                    } else if (value instanceof Float) {
                        i = 5;
                    } else if (value instanceof Double) {
                        i = 6;
                    } else {
                        Log.e("MicroMsg.SDK.PluginProvider.Resolver", "unresolve failed, unknown type=" + value.getClass().toString());
                    }
                    if (i != 0) {
                        z = false;
                    } else {
                        contentValues.put("type", Integer.valueOf(i));
                        contentValues.put("value", value.toString());
                        z = true;
                    }
                    if (!z) {
                        this.cr.update(a.a, contentValues, "key = ?", new String[]{entry.getKey()});
                    }
                }
                i = 0;
                if (i != 0) {
                }
                if (!z) {
                }
            }
            return true;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putBoolean(String str, boolean z) {
            this.values.put(str, Boolean.valueOf(z));
            this.remove.remove(str);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putFloat(String str, float f) {
            this.values.put(str, Float.valueOf(f));
            this.remove.remove(str);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putInt(String str, int i) {
            this.values.put(str, Integer.valueOf(i));
            this.remove.remove(str);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putLong(String str, long j) {
            this.values.put(str, Long.valueOf(j));
            this.remove.remove(str);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putString(String str, String str2) {
            this.values.put(str, str2);
            this.remove.remove(str);
            return this;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor putStringSet(String str, Set<String> set) {
            return null;
        }

        @Override // android.content.SharedPreferences.Editor
        public SharedPreferences.Editor remove(String str) {
            this.remove.add(str);
            return this;
        }
    }

    public MMSharedPreferences(Context context) {
        this.cr = context.getContentResolver();
    }

    private Object getValue(String str) {
        try {
            Cursor cursorQuery = this.cr.query(a.a, this.columns, "key = ?", new String[]{str}, null);
            if (cursorQuery == null) {
                return null;
            }
            Object objA = cursorQuery.moveToFirst() ? com.tencent.mm.opensdk.channel.a.a.a(cursorQuery.getInt(cursorQuery.getColumnIndex("type")), cursorQuery.getString(cursorQuery.getColumnIndex("value"))) : null;
            cursorQuery.close();
            return objA;
        } catch (Exception e) {
            Log.e(TAG, "getValue exception:" + e.getMessage());
            return null;
        }
    }

    @Override // android.content.SharedPreferences
    public boolean contains(String str) {
        return getValue(str) != null;
    }

    @Override // android.content.SharedPreferences
    public SharedPreferences.Editor edit() {
        if (this.editor == null) {
            this.editor = new REditor(this.cr);
        }
        return this.editor;
    }

    @Override // android.content.SharedPreferences
    public Map<String, ?> getAll() {
        try {
            Cursor cursorQuery = this.cr.query(a.a, this.columns, null, null, null);
            if (cursorQuery == null) {
                return null;
            }
            int columnIndex = cursorQuery.getColumnIndex("key");
            int columnIndex2 = cursorQuery.getColumnIndex("type");
            int columnIndex3 = cursorQuery.getColumnIndex("value");
            while (cursorQuery.moveToNext()) {
                this.values.put(cursorQuery.getString(columnIndex), com.tencent.mm.opensdk.channel.a.a.a(cursorQuery.getInt(columnIndex2), cursorQuery.getString(columnIndex3)));
            }
            cursorQuery.close();
            return this.values;
        } catch (Exception e) {
            Log.e(TAG, "getAll exception:" + e.getMessage());
            return this.values;
        }
    }

    @Override // android.content.SharedPreferences
    public boolean getBoolean(String str, boolean z) {
        Object value = getValue(str);
        return (value == null || !(value instanceof Boolean)) ? z : ((Boolean) value).booleanValue();
    }

    @Override // android.content.SharedPreferences
    public float getFloat(String str, float f) {
        Object value = getValue(str);
        return (value == null || !(value instanceof Float)) ? f : ((Float) value).floatValue();
    }

    @Override // android.content.SharedPreferences
    public int getInt(String str, int i) {
        Object value = getValue(str);
        return (value == null || !(value instanceof Integer)) ? i : ((Integer) value).intValue();
    }

    @Override // android.content.SharedPreferences
    public long getLong(String str, long j) {
        Object value = getValue(str);
        return (value == null || !(value instanceof Long)) ? j : ((Long) value).longValue();
    }

    @Override // android.content.SharedPreferences
    public String getString(String str, String str2) {
        Object value = getValue(str);
        return (value == null || !(value instanceof String)) ? str2 : (String) value;
    }

    @Override // android.content.SharedPreferences
    public Set<String> getStringSet(String str, Set<String> set) {
        return null;
    }

    @Override // android.content.SharedPreferences
    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
    }

    @Override // android.content.SharedPreferences
    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
    }
}
