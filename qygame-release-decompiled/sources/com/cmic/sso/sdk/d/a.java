package com.cmic.sso.sdk.d;

import android.text.TextUtils;
import com.mobile.auth.l.o;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private static C0004a<String, String> a = new C0004a<>();

    /* JADX INFO: renamed from: com.cmic.sso.sdk.d.a$a, reason: collision with other inner class name */
    private static class C0004a<K, V> extends HashMap<K, V> {
        private C0004a() {
        }
    }

    public static void a(String str) {
        try {
            String str2 = a.get(str);
            a.put(str, String.valueOf((TextUtils.isEmpty(str2) ? 0 : Integer.parseInt(str2)) + 1));
            a.put(str + "Time", o.a());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void a(String str, String str2) {
        a.put(str, str2);
    }
}
