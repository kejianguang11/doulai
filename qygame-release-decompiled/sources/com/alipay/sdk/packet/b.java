package com.alipay.sdk.packet;

import android.text.TextUtils;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    String a;
    public String b;

    public b(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    private void a(String str) {
        this.a = str;
    }

    private String b() {
        return this.a;
    }

    private void b(String str) {
        this.b = str;
    }

    private String c() {
        return this.b;
    }

    public final JSONObject a() {
        if (TextUtils.isEmpty(this.b)) {
            return null;
        }
        try {
            return new JSONObject(this.b);
        } catch (Exception unused) {
            return null;
        }
    }

    public final String toString() {
        return "\nenvelop:" + this.a + "\nbody:" + this.b;
    }
}
