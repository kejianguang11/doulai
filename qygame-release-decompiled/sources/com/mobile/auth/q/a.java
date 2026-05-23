package com.mobile.auth.q;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import com.igexin.assist.sdk.AssistPushConsts;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.l.h;
import com.mobile.auth.l.n;
import com.mobile.auth.l.q;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private static volatile a b;
    private com.mobile.auth.e.a a;
    private Context c;
    private com.mobile.auth.e.c d;
    private com.cmic.sso.sdk.a e;
    private String f = "";
    private Handler g;
    private String h;
    private String i;

    /* JADX INFO: renamed from: com.mobile.auth.q.a$a, reason: collision with other inner class name */
    private class RunnableC0048a implements Runnable {
        private com.cmic.sso.sdk.a b;
        private volatile boolean c = false;

        RunnableC0048a(com.cmic.sso.sdk.a aVar) {
            this.b = aVar;
        }

        private synchronized boolean a() {
            boolean z;
            try {
                z = this.c;
                this.c = true;
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return false;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return false;
                }
            }
            return !z;
        }

        static /* synthetic */ boolean a(RunnableC0048a runnableC0048a) {
            try {
                return runnableC0048a.a();
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return false;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return false;
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (a()) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("resultCode", "200023");
                        jSONObject.put("resultString", "登录超时");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    a.a(a.this).a("200023", "登录超时", this.b, jSONObject);
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    public a(Context context) {
        this.a = com.mobile.auth.e.a.a(context);
        this.c = context.getApplicationContext();
        this.g = new Handler(this.c.getMainLooper());
    }

    static /* synthetic */ com.mobile.auth.e.a a(a aVar) {
        try {
            return aVar.a;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public static a a(Context context) {
        try {
            if (b == null) {
                synchronized (a.class) {
                    if (b == null) {
                        b = new a(context);
                    }
                }
            }
            return b;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    static /* synthetic */ String a(a aVar, String str) {
        try {
            aVar.f = str;
            return str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private void a(com.mobile.auth.e.c cVar, com.cmic.sso.sdk.a aVar, com.mobile.auth.e.d dVar) {
        Method declaredMethod;
        Method method = null;
        try {
            try {
                try {
                    declaredMethod = com.mobile.auth.e.c.class.getDeclaredMethod("a", com.cmic.sso.sdk.a.class, com.mobile.auth.e.d.class);
                } catch (Throwable th) {
                    try {
                        ExceptionProcessor.processException(th);
                        return;
                    } catch (Throwable th2) {
                        ExceptionProcessor.processException(th2);
                        return;
                    }
                }
            } catch (IllegalAccessException e) {
                e = e;
            } catch (NoSuchMethodException e2) {
                e = e2;
            } catch (InvocationTargetException e3) {
                e = e3;
            }
        } catch (Throwable th3) {
            th = th3;
        }
        try {
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(cVar, aVar, dVar);
            declaredMethod.setAccessible(false);
        } catch (IllegalAccessException e4) {
            e = e4;
            method = declaredMethod;
            e.printStackTrace();
            method.setAccessible(false);
        } catch (NoSuchMethodException e5) {
            e = e5;
            method = declaredMethod;
            e.printStackTrace();
            method.setAccessible(false);
        } catch (InvocationTargetException e6) {
            e = e6;
            method = declaredMethod;
            e.printStackTrace();
            method.setAccessible(false);
        } catch (Throwable th4) {
            th = th4;
            method = declaredMethod;
            method.setAccessible(false);
            throw th;
        }
    }

    static /* synthetic */ void a(a aVar, String str, com.cmic.sso.sdk.a aVar2, c cVar) {
        try {
            aVar.a(str, aVar2, cVar);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private void a(String str, com.cmic.sso.sdk.a aVar, final c cVar) {
        try {
            final RunnableC0048a runnableC0048a = new RunnableC0048a(aVar);
            this.g.postDelayed(runnableC0048a, b());
            aVar.b("authTypeInput", str);
            a(d(), aVar, new com.mobile.auth.e.d() { // from class: com.mobile.auth.q.a.3
                @Override // com.mobile.auth.e.d
                public void a(String str2, String str3, com.cmic.sso.sdk.a aVar2, JSONObject jSONObject) {
                    try {
                        if (RunnableC0048a.a(runnableC0048a)) {
                            a.f(a.this).removeCallbacks(runnableC0048a);
                            cVar.a().put("securityphone", aVar2.b("securityphone", ""));
                            if (1 != aVar2.c("logintype") || !"显示登录取号成功".equals(str3) || com.mobile.auth.l.e.a(aVar2.b("traceId"))) {
                                a.a(a.this).a(str2, str3, aVar2, jSONObject);
                                return;
                            }
                            String strB = aVar2.b("traceId");
                            a.a(a.this, strB);
                            com.mobile.auth.l.e.a(strB, aVar2);
                            JSONObject jSONObject2 = new JSONObject();
                            try {
                                jSONObject2.put("resultCode", str2);
                                jSONObject2.put("authType", aVar2.b("authType", ""));
                                jSONObject2.put("authTypeDes", aVar2.b("authTypeDes", ""));
                                jSONObject2.put("openId", aVar2.b("openId", ""));
                                jSONObject2.put(AssistPushConsts.MSG_TYPE_TOKEN, aVar2.b(AssistPushConsts.MSG_TYPE_TOKEN, ""));
                                jSONObject2.put("traceId", aVar2.b("traceId", ""));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            com.mobile.auth.e.b bVarC = com.mobile.auth.l.e.c(strB);
                            if (bVarC != null) {
                                bVarC.a(aVar2.c("SDKRequestCode"), jSONObject2);
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
            });
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0064 A[Catch: Throwable -> 0x007d, PHI: r1
      0x0064: PHI (r1v5 java.lang.reflect.Method) = (r1v3 java.lang.reflect.Method), (r1v4 java.lang.reflect.Method), (r1v6 java.lang.reflect.Method) binds: [B:20:0x0062, B:29:0x0073, B:26:0x006e] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TRY_LEAVE, TryCatch #3 {Throwable -> 0x007d, blocks: (B:6:0x004c, B:33:0x0079, B:36:0x007f, B:21:0x0064), top: B:42:0x0002 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean a(com.mobile.auth.e.a aVar, com.cmic.sso.sdk.a aVar2, String str, String str2, String str3, int i, com.mobile.auth.e.b bVar) {
        Method declaredMethod;
        Method method = null;
        try {
            try {
                try {
                    declaredMethod = com.mobile.auth.e.e.class.getDeclaredMethod("a", com.cmic.sso.sdk.a.class, String.class, String.class, String.class, Integer.TYPE, com.mobile.auth.e.b.class);
                } catch (Throwable th) {
                    try {
                        ExceptionProcessor.processException(th);
                        return false;
                    } catch (Throwable th2) {
                        ExceptionProcessor.processException(th2);
                        return false;
                    }
                }
            } catch (IllegalAccessException e) {
                e = e;
            } catch (NoSuchMethodException e2) {
                e = e2;
            } catch (InvocationTargetException e3) {
                e = e3;
            }
        } catch (Throwable th3) {
            th = th3;
        }
        try {
            declaredMethod.setAccessible(true);
            boolean zBooleanValue = ((Boolean) declaredMethod.invoke(aVar, aVar2, str, str2, str3, Integer.valueOf(i), bVar)).booleanValue();
            if (declaredMethod != null) {
                declaredMethod.setAccessible(false);
            }
            return zBooleanValue;
        } catch (IllegalAccessException e4) {
            e = e4;
            method = declaredMethod;
            e.printStackTrace();
            if (method != null) {
                method.setAccessible(false);
            }
            return false;
        } catch (NoSuchMethodException e5) {
            e = e5;
            method = declaredMethod;
            e.printStackTrace();
            if (method != null) {
                method.setAccessible(false);
            }
            return false;
        } catch (InvocationTargetException e6) {
            e = e6;
            method = declaredMethod;
            e.printStackTrace();
            if (method != null) {
            }
            return false;
        } catch (Throwable th4) {
            th = th4;
            method = declaredMethod;
            if (method != null) {
                method.setAccessible(false);
            }
            throw th;
        }
    }

    static /* synthetic */ boolean a(a aVar, com.mobile.auth.e.a aVar2, com.cmic.sso.sdk.a aVar3, String str, String str2, String str3, int i, com.mobile.auth.e.b bVar) {
        try {
            return aVar.a(aVar2, aVar3, str, str2, str3, i, bVar);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    static /* synthetic */ com.cmic.sso.sdk.a b(a aVar) {
        try {
            return aVar.e;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    static /* synthetic */ String c(a aVar) {
        try {
            return aVar.h;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x003c A[Catch: Throwable -> 0x0043, TRY_ENTER, TryCatch #2 {Throwable -> 0x0043, blocks: (B:3:0x0001, B:10:0x001e, B:30:0x003c, B:31:0x003f, B:32:0x0040), top: B:40:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private com.mobile.auth.e.c d() {
        Field declaredField;
        try {
            if (this.d == null) {
                try {
                    try {
                        declaredField = com.mobile.auth.e.e.class.getDeclaredField("a");
                        try {
                            declaredField.setAccessible(true);
                            this.d = (com.mobile.auth.e.c) declaredField.get(this.a);
                        } catch (IllegalAccessException e) {
                            e = e;
                            e.printStackTrace();
                            if (declaredField != null) {
                            }
                            return this.d;
                        } catch (NoSuchFieldException e2) {
                            e = e2;
                            e.printStackTrace();
                            if (declaredField != null) {
                            }
                            return this.d;
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (declaredField != null) {
                            declaredField.setAccessible(false);
                        }
                        throw th;
                    }
                } catch (IllegalAccessException e3) {
                    e = e3;
                    declaredField = null;
                } catch (NoSuchFieldException e4) {
                    e = e4;
                    declaredField = null;
                } catch (Throwable th2) {
                    th = th2;
                    declaredField = null;
                    if (declaredField != null) {
                    }
                    throw th;
                }
                if (declaredField != null) {
                    declaredField.setAccessible(false);
                }
            }
            return this.d;
        } catch (Throwable th3) {
            try {
                ExceptionProcessor.processException(th3);
                return null;
            } catch (Throwable th4) {
                ExceptionProcessor.processException(th4);
                return null;
            }
        }
    }

    static /* synthetic */ String d(a aVar) {
        try {
            return aVar.i;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    static /* synthetic */ Context e(a aVar) {
        try {
            return aVar.c;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    static /* synthetic */ Handler f(a aVar) {
        try {
            return aVar.g;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public void a() {
        try {
            this.a.b();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(long j) {
        try {
            this.a.a(j);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(com.mobile.auth.e.b bVar) {
        try {
            final c cVar = new c(bVar);
            this.e = new com.cmic.sso.sdk.a(64);
            String strC = q.c();
            this.e.a(new com.cmic.sso.sdk.d.b());
            this.e.a("traceId", strC);
            com.mobile.auth.l.c.a("traceId", strC);
            com.mobile.auth.l.e.a(strC, cVar);
            this.e.a("SDKRequestCode", -1);
            n.a(new n.a(this.c, this.e) { // from class: com.mobile.auth.q.a.1
                @Override // com.mobile.auth.l.n.a
                protected void a() {
                    try {
                        if (a.a(a.this, a.a(a.this), a.b(a.this), a.c(a.this), a.d(a.this), "preGetMobile", 3, cVar)) {
                            a.a(a.this, String.valueOf(3), a.b(a.this), cVar);
                        }
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            });
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(String str) {
        try {
            this.h = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public long b() {
        try {
            return this.a.c();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1L;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1L;
            }
        }
    }

    public void b(com.mobile.auth.e.b bVar) {
        try {
            this.a.a(this.h, this.i, new c(bVar));
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void b(String str) {
        try {
            this.i = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void c() {
        try {
            this.a.d();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void c(com.mobile.auth.e.b bVar) {
        try {
            final c cVar = new c(bVar);
            this.e = new com.cmic.sso.sdk.a(64);
            String strC = q.c();
            this.e.a(new com.cmic.sso.sdk.d.b());
            this.e.a("traceId", strC);
            com.mobile.auth.l.c.a("traceId", strC);
            com.mobile.auth.l.e.a(strC, cVar);
            this.e.a("SDKRequestCode", -1);
            n.a(new n.a(this.c, this.e) { // from class: com.mobile.auth.q.a.2
                @Override // com.mobile.auth.l.n.a
                protected void a() {
                    try {
                        if (a.a(a.this, a.a(a.this), a.b(a.this), a.c(a.this), a.d(a.this), "loginAuth", 1, cVar)) {
                            String strA = h.a(a.e(a.this));
                            if (!TextUtils.isEmpty(strA)) {
                                a.b(a.this).a("phonescrip", strA);
                            }
                            h.a(true, false);
                            a.a(a.this, String.valueOf(3), a.b(a.this), cVar);
                        }
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            });
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
