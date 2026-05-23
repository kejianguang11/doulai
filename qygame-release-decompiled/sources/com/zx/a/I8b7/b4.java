package com.zx.a.I8b7;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import com.zx.a.I8b7.v2;
import com.zx.module.annotation.Java2C;
import java.util.HashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class b4 {
    public static PackageManager a;
    public static c2 b;
    public static HashMap<String, String> c = new HashMap<>();
    public static final HashMap<String, Boolean> d;

    static {
        HashMap<String, Boolean> map = new HashMap<>();
        Boolean bool = Boolean.TRUE;
        map.put("00000000-0000-0000-0000-000000000000", bool);
        map.put("00000000000000000000000000000000", bool);
        map.put("null", bool);
        map.put("0000000000000000000000000000000000000000000000000000000000000000", bool);
        map.put("", bool);
        map.put(null, bool);
        d = map;
    }

    @TargetApi(26)
    @Java2C.Method2C
    public static native String a();

    @Java2C.Method2C
    public static native String a(Context context);

    @Java2C.Method2C
    public static native String a(String str);

    public static String a(HashMap<String, String> map, String str) {
        return map.containsKey(str) ? map.get(str) : "";
    }

    public static boolean a(Context context, String str, boolean z) {
        try {
            return d(context).checkPermission(str, context.getPackageName()) == 0;
        } catch (Throwable th) {
            try {
                v2.a.a.a.a.a(4, null, null, th);
                return z;
            } catch (Throwable th2) {
                th2.printStackTrace();
                return z;
            }
        }
    }

    @SuppressLint({"MissingPermission"})
    public static boolean a(Context context, boolean z) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (activeNetworkInfo.isAvailable()) {
                    return true;
                }
            }
            return false;
        } catch (Throwable unused) {
            return z;
        }
    }

    public static String b() {
        try {
            String str = Build.BRAND;
            return (TextUtils.isEmpty(str) || str.equals("unknown")) ? Build.MANUFACTURER : str;
        } catch (Throwable th) {
            v2.a(th);
            return "";
        }
    }

    @Java2C.Method2C
    public static native String b(Context context);

    @Java2C.Method2C
    public static native boolean b(String str);

    @Java2C.Method2C
    public static native int c(Context context);

    public static PackageInfo c(String str) {
        try {
            return m3.a(str, 0);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String c() {
        String str = "";
        String[] strArr = new String[0];
        if (Build.VERSION.SDK_INT >= 21) {
            strArr = Build.SUPPORTED_ABIS;
        }
        if (strArr == null || strArr.length <= 0) {
            return "";
        }
        for (String str2 : strArr) {
            str = str + str2 + com.igexin.push.core.b.an;
        }
        return str;
    }

    public static PackageManager d(Context context) {
        if (a == null) {
            a = context.getPackageManager();
        }
        return a;
    }

    @Java2C.Method2C
    public static native String d();

    @Java2C.Method2C
    public static native c2 e(Context context);

    @Java2C.Method2C
    public static native String e();

    @Java2C.Method2C
    public static native String f();

    @Java2C.Method2C
    private static native boolean f(Context context);

    public static boolean g() throws Throwable {
        try {
            return new JSONObject(q3.m).has("zxc4");
        } catch (Throwable unused) {
            return false;
        }
    }

    public static boolean h() {
        try {
            return (q3.a.getApplicationInfo().flags & 2) != 0;
        } catch (Throwable th) {
            v2.a("b4", th);
            return true;
        }
    }
}
