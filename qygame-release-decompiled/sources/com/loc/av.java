package com.loc;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class av {
    static byte[] a;
    static byte[] b;
    private String c;

    public av(String str) {
        this.c = s.a(TextUtils.isDigitsOnly(str) ? "SPUtil" : str);
    }

    public static int a(Context context, String str, String str2, int i) {
        try {
            return context.getSharedPreferences(str, 0).getInt(str2, i);
        } catch (Throwable th) {
            an.b(th, "csp", "giv");
            return i;
        }
    }

    public static SharedPreferences.Editor a(Context context, String str) {
        if (context != null) {
            try {
                if (!TextUtils.isEmpty(str)) {
                    return context.getSharedPreferences(str, 0).edit();
                }
            } catch (Throwable th) {
                ak.a(th, "sp", "ge");
            }
        }
        return null;
    }

    public static String a(Context context, String str, String str2) {
        if (context == null) {
            return "";
        }
        try {
            return x.a(b(context, x.d(context.getSharedPreferences(str, 0).getString(str2, ""))));
        } catch (Throwable unused) {
            return "";
        }
    }

    public static void a(Context context, String str, String str2, String str3) {
        if (context == null || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            return;
        }
        try {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences(str, 0).edit();
            editorEdit.putString(str2, x.g(a(context, x.a(str3))));
            a(editorEdit);
        } catch (Throwable unused) {
        }
    }

    public static void a(SharedPreferences.Editor editor) {
        if (editor == null) {
            return;
        }
        try {
            editor.apply();
        } catch (Throwable th) {
            ak.a(th, "sp", "cm");
        }
    }

    public static void a(SharedPreferences.Editor editor, String str) {
        if (editor != null) {
            try {
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                editor.remove(str);
            } catch (Throwable th) {
                ak.a(th, "sp", "rk");
            }
        }
    }

    public static void a(SharedPreferences.Editor editor, String str, int i) {
        try {
            editor.putInt(str, i);
        } catch (Throwable th) {
            an.b(th, "csp", "putPrefsInt");
        }
    }

    public static void a(SharedPreferences.Editor editor, String str, long j) {
        if (editor == null || TextUtils.isEmpty(str)) {
            return;
        }
        try {
            editor.putLong(str, j);
        } catch (Throwable th) {
            an.b(th, "csp", "plv");
        }
    }

    public static void a(SharedPreferences.Editor editor, String str, String str2) {
        if (editor != null) {
            try {
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                    editor.putString(str, str2);
                }
            } catch (Throwable th) {
                ak.a(th, "sp", "ps");
            }
        }
    }

    public static void a(SharedPreferences.Editor editor, String str, boolean z) {
        try {
            editor.putBoolean(str, z);
        } catch (Throwable th) {
            an.b(th, "csp", "setPrefsStr");
        }
    }

    public static boolean a(Context context, String str, String str2, boolean z) {
        try {
            return context.getSharedPreferences(str, 0).getBoolean(str2, z);
        } catch (Throwable th) {
            an.b(th, "csp", "gbv");
            return z;
        }
    }

    private static byte[] a(Context context) {
        if (context == null) {
            return new byte[0];
        }
        if (a != null && a.length > 0) {
            return a;
        }
        byte[] bytes = l.f(context).getBytes();
        a = bytes;
        return bytes;
    }

    public static byte[] a(Context context, byte[] bArr) {
        try {
            return p.b(a(context), bArr, b(context));
        } catch (Throwable unused) {
            return new byte[0];
        }
    }

    public static long b(Context context, String str, String str2) {
        try {
            return context.getSharedPreferences(str, 0).getLong(str2, 0L);
        } catch (Throwable th) {
            an.b(th, "csp", "glv");
            return 0L;
        }
    }

    public static String b(Context context, String str, String str2, String str3) {
        if (context == null) {
            return null;
        }
        try {
            return context.getSharedPreferences(str, 0).getString(str2, str3);
        } catch (Throwable th) {
            an.b(th, "csp", "gsv");
            return str3;
        }
    }

    private static byte[] b(Context context) {
        if (b != null && b.length > 0) {
            return b;
        }
        if (Build.VERSION.SDK_INT >= 9) {
            b = Arrays.copyOfRange(a(context), 0, a(context).length / 2);
        } else {
            b = new byte[a(context).length / 2];
            for (int i = 0; i < b.length; i++) {
                b[i] = a(context)[i];
            }
        }
        return b;
    }

    public static byte[] b(Context context, byte[] bArr) {
        try {
            return p.a(a(context), bArr, b(context));
        } catch (Exception unused) {
            return new byte[0];
        }
    }
}
