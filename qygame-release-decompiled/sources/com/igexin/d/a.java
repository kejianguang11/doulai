package com.igexin.d;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import com.igexin.push.core.b;
import com.igexin.push.g.c;
import com.igexin.push.g.d;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushService;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class a implements InvocationHandler {
    private static String b = "ZxExecutor";
    private static volatile a c;
    public Context a;

    /* JADX INFO: renamed from: com.igexin.d.a$1, reason: invalid class name */
    public class AnonymousClass1 implements Runnable {
        public AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                if (a.c(a.this.a)) {
                    a.a(a.this, a.this.a);
                }
            } catch (Throwable th) {
                com.igexin.c.a.c.a.a(th);
            }
        }
    }

    /* JADX INFO: renamed from: com.igexin.d.a$2, reason: invalid class name */
    final class AnonymousClass2 implements Runnable {
        final /* synthetic */ Context a;
        final /* synthetic */ String b;
        final /* synthetic */ String c;

        AnonymousClass2(Context context, String str, String str2) {
            this.a = context;
            this.b = str;
            this.c = str2;
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                Class cls = (Class) d.a(this.a, PushService.class).second;
                if (cls != null) {
                    Intent intent = new Intent(this.a, (Class<?>) cls);
                    intent.putExtra("action", PushConsts.ACTION_BROADCAST_UPLOAD_TYPE253);
                    intent.putExtra(b.C, this.b);
                    intent.putExtra("aid", this.c);
                    c.a(this.a, intent);
                }
            } catch (Throwable th) {
                com.igexin.c.a.c.a.a(th);
            }
        }
    }

    private a() {
    }

    public static a a() {
        if (c == null) {
            synchronized (a.class) {
                if (c == null) {
                    a aVar = new a();
                    c = aVar;
                    return aVar;
                }
            }
        }
        return c;
    }

    static /* synthetic */ void a(a aVar, Context context) {
        try {
            Class<?> cls = Class.forName("com.zx.sdk.api.ZXManager");
            Object objInvoke = cls.getDeclaredMethod("newSDK", String.class).invoke(cls, com.igexin.push.a.r);
            Method declaredMethod = objInvoke.getClass().getDeclaredMethod("init", Context.class);
            Method declaredMethod2 = objInvoke.getClass().getDeclaredMethod("allowPermissionDialog", Boolean.TYPE);
            declaredMethod.invoke(objInvoke, context);
            declaredMethod2.invoke(objInvoke, Boolean.FALSE);
            Class<?> cls2 = Class.forName("com.zx.sdk.api.ZXIDListener");
            objInvoke.getClass().getDeclaredMethod("getZXID", cls2).invoke(objInvoke, Proxy.newProxyInstance(a.class.getClassLoader(), new Class[]{cls2}, aVar));
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
    }

    private void a(String str, String str2, Context context) {
        com.igexin.b.a.a().a.execute(new AnonymousClass2(context, str, str2));
    }

    private void b(Context context) {
        this.a = context.getApplicationContext();
        com.igexin.b.a.a().b().schedule(new AnonymousClass1(), 2000L, TimeUnit.MILLISECONDS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean c(Context context) throws Throwable {
        com.igexin.push.b.b bVar;
        Cursor cursor = null;
        try {
            bVar = new com.igexin.push.b.b(context);
            try {
                Cursor cursorA = bVar.a(b.X, new String[]{"value"}, "id = 79");
                if (cursorA != null) {
                    try {
                        if (cursorA.moveToFirst()) {
                            boolean z = Boolean.parseBoolean(cursorA.getString(0));
                            if (cursorA != null) {
                                try {
                                    cursorA.close();
                                } catch (Throwable th) {
                                    com.igexin.c.a.c.a.a(th);
                                }
                            }
                            bVar.close();
                            return z;
                        }
                    } catch (Throwable th2) {
                        cursor = cursorA;
                        th = th2;
                        if (cursor != null) {
                            try {
                                cursor.close();
                            } catch (Throwable th3) {
                                com.igexin.c.a.c.a.a(th3);
                                throw th;
                            }
                        }
                        if (bVar != null) {
                            bVar.close();
                        }
                        throw th;
                    }
                }
                if (cursorA != null) {
                    try {
                        cursorA.close();
                    } catch (Throwable th4) {
                        com.igexin.c.a.c.a.a(th4);
                    }
                }
                bVar.close();
                return false;
            } catch (Throwable th5) {
                th = th5;
            }
        } catch (Throwable th6) {
            th = th6;
            bVar = null;
        }
    }

    private void d(Context context) {
        try {
            Class<?> cls = Class.forName("com.zx.sdk.api.ZXManager");
            Object objInvoke = cls.getDeclaredMethod("newSDK", String.class).invoke(cls, com.igexin.push.a.r);
            Method declaredMethod = objInvoke.getClass().getDeclaredMethod("init", Context.class);
            Method declaredMethod2 = objInvoke.getClass().getDeclaredMethod("allowPermissionDialog", Boolean.TYPE);
            declaredMethod.invoke(objInvoke, context);
            declaredMethod2.invoke(objInvoke, Boolean.FALSE);
            Class<?> cls2 = Class.forName("com.zx.sdk.api.ZXIDListener");
            objInvoke.getClass().getDeclaredMethod("getZXID", cls2).invoke(objInvoke, Proxy.newProxyInstance(a.class.getClassLoader(), new Class[]{cls2}, this));
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) {
        try {
            String name = method.getName();
            byte b2 = -1;
            int iHashCode = name.hashCode();
            if (iHashCode != -530890460) {
                if (iHashCode == 1116433148 && name.equals("onFailed")) {
                    b2 = 1;
                }
            } else if (name.equals("onSuccess")) {
                b2 = 0;
            }
            switch (b2) {
                case 0:
                    Object obj2 = objArr[0];
                    com.igexin.c.a.c.a.b(b, " get zxid success ".concat(String.valueOf(obj2)));
                    String strOptString = "";
                    JSONObject jSONObject = (JSONObject) Class.forName("com.zx.sdk.api.ZXID").getDeclaredMethod("getAids", new Class[0]).invoke(obj2, new Object[0]);
                    if (jSONObject instanceof JSONObject) {
                        strOptString = jSONObject.optString("venderAid", "");
                        com.igexin.c.a.c.a.b(b, " get aid success ".concat(String.valueOf(strOptString)));
                    }
                    com.igexin.b.a.a().a.execute(new AnonymousClass2(this.a, obj2.toString(), strOptString));
                    break;
                case 1:
                    StringBuilder sb = new StringBuilder();
                    for (Object obj3 : objArr) {
                        sb.append(obj3);
                        sb.append(b.an);
                    }
                    com.igexin.c.a.c.a.a("ZxExecutor | ", " get zxid failed code  msg = ".concat(String.valueOf(sb)));
                    break;
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }
}
