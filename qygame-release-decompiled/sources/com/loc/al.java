package com.loc;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.text.TextUtils;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.RejectedExecutionException;

/* JADX INFO: loaded from: classes.dex */
public final class al {
    public static final String a = "/a/";
    static final String b = "b";
    static final String c = "c";
    static final String d = "d";
    public static String e = "s";
    public static final String f = "g";
    public static final String g = "h";
    public static final String h = "e";
    public static final String i = "f";
    public static final String j = "j";
    public static final String k = "k";
    private static long l;
    private static Vector<w> m = new Vector<>();

    public static String a(Context context, String str) {
        return context.getSharedPreferences("AMSKLG_CFG", 0).getString(str, "");
    }

    static List<w> a() {
        Vector<w> vector;
        try {
            synchronized (Looper.getMainLooper()) {
                vector = m;
            }
            return vector;
        } catch (Throwable th) {
            th.printStackTrace();
            return m;
        }
    }

    public static void a(final Context context) {
        try {
            if (System.currentTimeMillis() - l < 60000) {
                return;
            }
            l = System.currentTimeMillis();
            cj.a().b(new ck() { // from class: com.loc.al.1
                @Override // com.loc.ck
                public final void a() {
                    try {
                        ao.b(context);
                        ao.d(context);
                        ao.c(context);
                        bs.a(context);
                        bq.a(context);
                    } catch (RejectedExecutionException unused) {
                    } catch (Throwable th) {
                        an.b(th, "Lg", "proL");
                    }
                }
            });
        } catch (Throwable th) {
            an.b(th, "Lg", "proL");
        }
    }

    @TargetApi(9)
    public static void a(Context context, String str, String str2) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("AMSKLG_CFG", 0).edit();
        editorEdit.putString(str, str2);
        editorEdit.apply();
    }

    public static void a(w wVar) {
        try {
            synchronized (Looper.getMainLooper()) {
                try {
                    if (wVar == null) {
                        return;
                    }
                    if (m.contains(wVar)) {
                        return;
                    }
                    m.add(wVar);
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (Throwable unused) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0035, code lost:
    
        r1 = r7.length;
        r2 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0037, code lost:
    
        if (r2 >= r1) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0043, code lost:
    
        if (b(r6, r7[r2].trim()) == false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0045, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0046, code lost:
    
        r2 = r2 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static boolean a(String[] strArr, String str) {
        if (strArr != null && str != null) {
            try {
                String[] strArrSplit = str.split("\n");
                int length = strArrSplit.length;
                int i2 = 0;
                while (true) {
                    boolean z = true;
                    if (i2 >= length) {
                        break;
                    }
                    String strTrim = strArrSplit[i2].trim();
                    if (TextUtils.isEmpty(strTrim) || !strTrim.startsWith("at ") || !strTrim.contains("uncaughtException")) {
                        z = false;
                    }
                    if (z) {
                        return false;
                    }
                    i2++;
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return false;
    }

    public static void b(Context context, String str) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("AMSKLG_CFG", 0).edit();
        editorEdit.remove(str);
        editorEdit.apply();
    }

    static boolean b(String[] strArr, String str) {
        if (strArr != null && str != null) {
            try {
                String strTrim = str;
                for (String str2 : strArr) {
                    strTrim = strTrim.trim();
                    if (strTrim.startsWith("at ")) {
                        if (strTrim.contains(str2 + ".") && strTrim.endsWith(")") && !strTrim.contains("uncaughtException")) {
                            return true;
                        }
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return false;
    }

    public static String c(Context context, String str) {
        return context.getFilesDir().getAbsolutePath() + a + str;
    }
}
