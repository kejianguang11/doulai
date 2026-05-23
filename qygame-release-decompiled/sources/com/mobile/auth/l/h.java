package com.mobile.auth.l;

import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.l.k;
import com.mobile.auth.l.n;

/* JADX INFO: loaded from: classes.dex */
public class h {
    private static String a;
    private static String b;
    private static long c;

    private static int a(String str) {
        String strB;
        if (TextUtils.isEmpty(b)) {
            strB = k.b("pre_sim_key", "");
            b = strB;
        } else {
            strB = b;
        }
        if (TextUtils.isEmpty(strB)) {
            return 0;
        }
        return strB.equals(str) ? 1 : 2;
    }

    public static long a() {
        long jA;
        long j;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (TextUtils.isEmpty(a)) {
            String strB = k.b("phonescripcache", "");
            jA = k.a("phonescripstarttime", 0L);
            if (TextUtils.isEmpty(strB)) {
                j = 0;
            }
            return Math.max(j / 1000, 0L);
        }
        c.b("PhoneScripUtils", b + " " + c);
        jA = c;
        j = (jA - jCurrentTimeMillis) - com.igexin.push.config.c.i;
        return Math.max(j / 1000, 0L);
    }

    public static String a(Context context) {
        if (!TextUtils.isEmpty(a)) {
            return a;
        }
        String strB = k.b("phonescripcache", "");
        if (TextUtils.isEmpty(strB)) {
            c.a("PhoneScripUtils", "null");
            return null;
        }
        c = k.a("phonescripstarttime", 0L);
        b = k.b("pre_sim_key", "");
        String strB2 = b.b(context, strB);
        a = strB2;
        return strB2;
    }

    public static void a(final Context context, final String str, long j, final String str2, String str3) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || j <= 0) {
            return;
        }
        c.b("PhoneScripUtils", "save phone scrip simKey = " + str2);
        a = str;
        long j2 = j * 1000;
        c = System.currentTimeMillis() + j2;
        c.b("sLifeTime", c + "");
        b = str2;
        if (!"operator".equals(str3)) {
            n.a(new n.a() { // from class: com.mobile.auth.l.h.1
                @Override // com.mobile.auth.l.n.a
                protected void a() {
                    c.b("PhoneScripUtils", "start save scrip to sp in sub thread");
                    h.b(context, str, h.c, str2);
                }
            });
        } else if (j2 > 3600000) {
            c = System.currentTimeMillis() + 3600000;
        } else {
            c = System.currentTimeMillis() + j2;
        }
    }

    public static void a(boolean z, boolean z2) {
        k.a aVarA = k.a();
        aVarA.a("phonescripstarttime");
        aVarA.a("phonescripcache");
        aVarA.a("pre_sim_key");
        if (z2) {
            aVarA.a();
        } else {
            aVarA.b();
        }
        if (z) {
            a = null;
            b = null;
            c = 0L;
        }
    }

    private static boolean a(long j) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        c.b("PhoneScripUtils", j + "");
        c.b("PhoneScripUtils", jCurrentTimeMillis + "");
        return j - jCurrentTimeMillis > com.igexin.push.config.c.i;
    }

    public static boolean a(com.cmic.sso.sdk.a aVar) {
        int iA = a(aVar.b("scripKey"));
        aVar.a("imsiState", iA + "");
        c.b("PhoneScripUtils", "simState = " + iA);
        if (k.a("phonescripversion", 0) != 1 && iA != 0) {
            a(true, false);
            b.a();
            c.b("PhoneScripUtils", "phoneScriptVersion change");
            return false;
        }
        if (iA == 1) {
            return c();
        }
        if (iA == 2) {
            a(true, false);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context, String str, long j, String str2) {
        String strA = b.a(context, str);
        if (TextUtils.isEmpty(strA)) {
            return;
        }
        k.a aVarA = k.a();
        aVarA.a("phonescripcache", strA);
        aVarA.a("phonescripstarttime", j);
        aVarA.a("phonescripversion", 1);
        aVarA.a("pre_sim_key", str2);
        aVarA.b();
    }

    private static boolean c() {
        if (TextUtils.isEmpty(a)) {
            return !TextUtils.isEmpty(k.b("phonescripcache", "")) && a(k.a("phonescripstarttime", 0L));
        }
        c.b("PhoneScripUtils", b + " " + c);
        return a(c);
    }
}
