package com.zx.a.I8b7;

import android.text.TextUtils;
import android.util.Log;
import com.zx.a.I8b7.r3;
import com.zx.module.base.Callback;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class l2 {
    public static final Set<String> b;
    public t3 a;

    static {
        HashSet hashSet = new HashSet();
        b = hashSet;
        q3.c = "core-n";
        q3.d = "3.3.5.47903";
        hashSet.add("fd39c63f1732f201");
        hashSet.add("182215c3273d3c96");
        hashSet.add("30c3b906fa3a6c10");
        hashSet.add("83e1f70a049353e0");
        hashSet.add("a14a9b473d09b4a4");
        hashSet.add("c5d0f5289411bfb1");
        hashSet.add("888db8aca12678cf");
        hashSet.add("4d34408b292920ff");
        Set<String> set = q3.J;
        set.add("fd39c63f1732f201");
        set.add("182215c3273d3c96");
        set.add("30c3b906fa3a6c10");
        set.add("83e1f70a049353e0");
        set.add("888db8aca12678cf");
    }

    public final String a(String str, int i) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(com.alipay.sdk.packet.d.k, str);
        jSONObject.put("code", i);
        return jSONObject.toString();
    }

    public String f182215c3273d3c96(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(com.alipay.sdk.packet.d.k, false);
        jSONObject.put("code", 0);
        return jSONObject.toString();
    }

    public String f30c3b906fa3a6c10(String str) throws JSONException {
        try {
            boolean z = new JSONObject(str).getBoolean("isDebug");
            Log.d("setDebug", "isDebug: " + z);
            t.a = z;
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("code", 0);
            return jSONObject.toString();
        } catch (Throwable th) {
            Log.e("ZXCoreModule setDebug", th.getMessage());
            return a(th.getMessage(), 1);
        }
    }

    public void f4d34408b292920ff(String str, Callback callback) {
        v2.a("getAuthToken:" + str + "with cb");
        try {
            r3.b.a.b(new JSONObject(str), callback, 1);
        } catch (Throwable th) {
            v2.b("getAuthToken error:" + th);
            if (callback != null) {
                try {
                    callback.callback(a(th.getMessage(), 1));
                } catch (Throwable th2) {
                    v2.a(th2);
                }
            }
        }
    }

    public String f83e1f70a049353e0(String str) throws JSONException {
        q3.b = new JSONObject(str).getString("version");
        return a("", 0);
    }

    public String f888db8aca12678cf(String str) throws JSONException {
        return a("lib not work", 1);
    }

    public void fa14a9b473d09b4a4(String str, Callback callback) {
        v2.a("getUAID:" + str + "with cb");
        try {
            r3.b.a.b(new JSONObject(str), callback, 0);
        } catch (Throwable th) {
            v2.b("getUAID error:" + th);
            if (callback != null) {
                try {
                    callback.callback(a(th.getMessage(), 1));
                } catch (Throwable th2) {
                    v2.a(th2);
                }
            }
        }
    }

    public void fc5d0f5289411bfb1(String str, Callback callback) {
        v2.a("getTag:" + str + "with cb");
        try {
            callback.callback(a(e2.c(), 0));
        } catch (Throwable th) {
            v2.b("getTag error:" + th);
            if (callback != null) {
                try {
                    callback.callback(a(th.getMessage(), 1));
                } catch (Throwable th2) {
                    v2.a(th2);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String ffd39c63f1732f201(String str) throws JSONException {
        String strA;
        boolean z = new JSONObject(str).getBoolean("allowExpired");
        ((y3) this.a).getClass();
        if (z) {
            strA = q3.a();
        } else {
            String str2 = q3.i;
            boolean z2 = true;
            if (!TextUtils.isEmpty(str2)) {
                try {
                    if (System.currentTimeMillis() < Long.parseLong(str2.split("-")[1]) * 1000) {
                        z2 = false;
                    }
                } catch (Exception e) {
                    v2.b("zid判断过期异常:" + str2 + ", err :" + e.getMessage());
                }
            }
            if (z2) {
                strA = null;
            }
        }
        return a(strA, 0);
    }
}
