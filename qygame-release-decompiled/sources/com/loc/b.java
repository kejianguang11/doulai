package com.loc;

import android.content.Context;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    bg a;

    public b(Context context) {
        this.a = null;
        try {
            q.a().a(context);
        } catch (Throwable unused) {
        }
        this.a = bg.a();
    }

    private String a(Context context, String str, Map<String, String> map) {
        try {
            HashMap map2 = new HashMap(16);
            fd fdVar = new fd();
            map2.clear();
            map2.put("Content-Type", "application/x-www-form-urlencoded");
            map2.put("Connection", "Keep-Alive");
            map2.put("User-Agent", "AMAP_Location_SDK_Android 6.1.0");
            String strA = n.a();
            String strA2 = n.a(context, strA, x.b(map));
            map.put("ts", strA);
            map.put("scode", strA2);
            fdVar.b(map);
            fdVar.a((Map<String, String>) map2);
            fdVar.b(str);
            fdVar.a(v.a(context));
            fdVar.a(fj.i);
            fdVar.b(fj.i);
            try {
                return new String(bg.a(fdVar).a, com.igexin.push.g.s.b);
            } catch (Throwable th) {
                fj.a(th, "GeoFenceNetManager", "post");
                return null;
            }
        } catch (Throwable unused) {
        }
    }

    private static Map<String, String> b(Context context, String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        HashMap map = new HashMap(16);
        map.put("key", l.f(context));
        if (!TextUtils.isEmpty(str)) {
            map.put("keywords", str);
        }
        if (!TextUtils.isEmpty(str2)) {
            map.put("types", str2);
        }
        if (!TextUtils.isEmpty(str5) && !TextUtils.isEmpty(str6)) {
            map.put("location", str6 + com.igexin.push.core.b.an + str5);
        }
        if (!TextUtils.isEmpty(str3)) {
            map.put("city", str3);
        }
        if (!TextUtils.isEmpty(str4)) {
            map.put("offset", str4);
        }
        if (!TextUtils.isEmpty(str7)) {
            map.put("radius", str7);
        }
        return map;
    }

    public final String a(Context context, String str, String str2) {
        Map<String, String> mapB = b(context, str2, null, null, null, null, null, null);
        mapB.put("extensions", "all");
        mapB.put("subdistrict", "0");
        return a(context, str, mapB);
    }

    public final String a(Context context, String str, String str2, String str3, String str4, String str5) {
        Map<String, String> mapB = b(context, str2, str3, str4, str5, null, null, null);
        mapB.put("children", "1");
        mapB.put("page", "1");
        mapB.put("extensions", "base");
        return a(context, str, mapB);
    }

    public final String a(Context context, String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        Map<String, String> mapB = b(context, str2, str3, null, str4, str5, str6, str7);
        mapB.put("children", "1");
        mapB.put("page", "1");
        mapB.put("extensions", "base");
        return a(context, str, mapB);
    }
}
