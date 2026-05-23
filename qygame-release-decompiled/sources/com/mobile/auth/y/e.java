package com.mobile.auth.y;

import android.content.Context;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class e {
    private static volatile e b;
    public Context a;
    private ExecutorService c = Executors.newSingleThreadExecutor();

    private e() {
    }

    public static e a() {
        try {
            if (b == null) {
                synchronized (e.class) {
                    if (b == null) {
                        b = new e();
                    }
                }
            }
            return b;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static String a(Context context, String str, String str2) {
        try {
            return f.a(context, str, str2);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static void a(int i, d dVar, String str) {
        try {
            t.e("type:" + i + "\nmsg:" + str);
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("resultCode", 1);
                jSONObject.put("resultMsg", str);
                jSONObject.put("resultData", "");
                jSONObject.put("seq", "");
                dVar.onResult(jSONObject.toString());
            } catch (Exception unused) {
                t.b();
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void a(boolean z) {
        try {
            t.a(z);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static boolean a(Context context) {
        try {
            if (g.a(context)) {
                if (g.c(context)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public static boolean a(Context context, String str, String str2, String str3) {
        try {
            return f.a(context, str, str2, str3);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public static String b() {
        try {
            return p.b();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static void b(Context context) {
        try {
            g.b(context);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static boolean b(String str) {
        try {
            if (str.equalsIgnoreCase("ali.wosms.cn") || str.equalsIgnoreCase("m.zzx.cnklog.com") || str.equalsIgnoreCase("msv6.wosms.cn") || str.equalsIgnoreCase("test.wosms.cn")) {
                p.c = str;
                return true;
            }
            p.c = "msv6.wosms.cn";
            return false;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public static String c() {
        return "phoneinfo";
    }

    public static void c(Context context) {
        try {
            g.d(context);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static String d() {
        try {
            String str = p.c;
            if (((str == null || str.length() == 0 || str.trim().length() == 0 || "null".equals(str)) ? Boolean.TRUE : Boolean.FALSE).booleanValue()) {
                p.c = "msv6.wosms.cn";
            }
            return p.c;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static boolean d(Context context) {
        try {
            int iB = v.b(context);
            return iB == 0 || iB == 1;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public static String e() {
        return "auth.wosms.cn";
    }

    public static void e(Context context) {
        try {
            f.a(context);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void f() {
        try {
            n.a = false;
            n.b = false;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void g() {
        try {
            q.a().b();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public final String a(String str) {
        try {
            if (this.a == null) {
                return "sdk 未初始化, context 为空";
            }
            try {
                switch (str.toLowerCase()) {
                    case "appid":
                        return u.c();
                    case "packagename":
                        return this.a.getApplicationContext().getPackageName();
                    case "md5":
                    case "sha1":
                    case "sha256":
                        return v.a(this.a, this.a.getPackageName(), str.toLowerCase());
                    case "sdkversion":
                        return p.b();
                    default:
                        throw new Exception("no info");
                }
            } catch (Exception e) {
                return "no info:" + e.toString();
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }
}
