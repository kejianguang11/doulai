package com.mobile.auth.a;

import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.b.e;
import com.mobile.auth.c.k;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static c a = null;
    public static int b = 0;
    public static int c = 0;
    public static int d = 0;
    private static final String e = "a";

    public static void a(int i, int i2, int i3, c cVar) {
        try {
            c = i;
            d = i2;
            b = i3;
            a = cVar;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void a(Context context, String str, String str2, b bVar) {
        try {
            a(e, "called requestPreAuthCode()   appId：" + str + ",appSecret:" + str2);
            a(context, str, str2, "qhx", bVar);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private static void a(Context context, String str, String str2, String str3, b bVar) {
        try {
            if (bVar == null) {
                a = null;
                return;
            }
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                if (!k.b(context)) {
                    bVar.a("{\"result\":80003,\"msg\":\"网络无连接\"}");
                    a = null;
                    return;
                } else if (k.c(context)) {
                    new com.mobile.auth.c.b().a(context, str, str2, str3, bVar);
                    return;
                } else if (k.d(context)) {
                    new com.mobile.auth.c.b().b(context, str, str2, str3, bVar);
                    return;
                } else {
                    bVar.a("{\"result\":80004,\"msg\":\"移动网络未开启\"}");
                    a = null;
                    return;
                }
            }
            bVar.a("{\"result\":80106,\"msg\":\"请求参数异常\"}");
            a = null;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void a(String str, String str2) {
        try {
            if (a != null) {
                try {
                    a.a("CT_" + str, str2);
                } catch (Throwable unused) {
                }
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void a(String str, String str2, Throwable th) {
        try {
            if (a != null) {
                try {
                    a.a("CT_" + str, str2, th);
                } catch (Throwable unused) {
                }
            }
        } catch (Throwable th2) {
            try {
                ExceptionProcessor.processException(th2);
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
            }
        }
    }

    public static void b(Context context, String str, String str2, b bVar) {
        if (bVar != null) {
            try {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    jSONObject.put("reqId", str2);
                    bVar.a(jSONObject.toString());
                    a(e, "callback result : " + jSONObject.toString());
                } catch (Exception unused) {
                    bVar.a(str);
                    a(e, "Exception callback result : " + str);
                }
                a = null;
                e.a(context, str2);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }
}
