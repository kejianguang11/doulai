package com.zx.a.I8b7;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.zx.module.annotation.Java2C;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
public class z1 {
    public static String a(Context context) throws Exception {
        String strA;
        if (!TextUtils.isEmpty(q3.f)) {
            return q3.f.trim();
        }
        if (context == null) {
            throw new RuntimeException("context not provided, cannot be null");
        }
        try {
            strA = b(context).getString("ZX_APPID");
        } catch (Exception e) {
            v2.a(e);
            strA = null;
        }
        if (TextUtils.isEmpty(strA)) {
            if (q3.e == null) {
                c(context);
            }
            if (!(!TextUtils.isEmpty(q3.e))) {
                throw new IllegalStateException("ZX_APPID not found");
            }
            if (TextUtils.isEmpty(q3.g)) {
                q3.g = context.getPackageName();
            }
            strA = a(q3.g);
        }
        return strA.trim();
    }

    @Java2C.Method2C
    public static native String a(String str) throws NoSuchAlgorithmException, InvalidKeyException;

    public static JSONArray a() {
        JSONArray jSONArray = new JSONArray();
        try {
            Bundle bundleB = b(q3.a);
            if (bundleB == null) {
                return jSONArray;
            }
            for (String str : bundleB.keySet()) {
                if (str.startsWith("ZX_APPID_")) {
                    String string = bundleB.getString(str);
                    if (!TextUtils.isEmpty(string)) {
                        jSONArray.put(string);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            v2.a(e);
        }
        return jSONArray;
    }

    public static Bundle b(Context context) throws PackageManager.NameNotFoundException {
        if (q3.L == null) {
            PackageManager packageManagerD = b4.d(context.getApplicationContext());
            if (TextUtils.isEmpty(q3.g)) {
                q3.g = context.getPackageName();
            }
            q3.L = packageManagerD.getApplicationInfo(q3.g, 128).metaData;
        }
        return q3.L;
    }

    @Java2C.Method2C
    private static native String b();

    public static void c(Context context) {
        try {
            q3.e = b(context).getString("ZX_CHANNEL_ID");
            v2.a("initChannelId: , channelId = '" + q3.e + "'");
        } catch (Exception e) {
            v2.a(e);
        }
    }
}
