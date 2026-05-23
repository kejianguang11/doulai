package com.zx.a.I8b7;

import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class a2 extends JSONArray {
    public a2() {
    }

    public a2(String str) throws JSONException {
        super(str);
    }

    public synchronized int a(String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        for (int i = 0; i < length(); i++) {
            synchronized (this) {
                if (!super.getString(i).startsWith(str)) {
                }
            }
            return i;
        }
        return -1;
    }

    public synchronized void a(int i) {
        for (int i2 = 0; i2 < i; i2++) {
            super.remove(0);
            v2.b("removeM " + i2);
        }
    }

    @Override // org.json.JSONArray
    public synchronized String getString(int i) throws JSONException {
        return super.getString(i);
    }

    @Override // org.json.JSONArray
    public synchronized JSONArray put(int i, Object obj) throws JSONException {
        return super.put(i, obj);
    }

    @Override // org.json.JSONArray
    public synchronized JSONArray put(Object obj) {
        return super.put(obj);
    }

    @Override // org.json.JSONArray
    public synchronized JSONArray put(boolean z) {
        return super.put(z);
    }

    @Override // org.json.JSONArray
    public synchronized Object remove(int i) {
        return super.remove(i);
    }

    @Override // org.json.JSONArray
    public synchronized String toString() {
        return super.toString();
    }
}
