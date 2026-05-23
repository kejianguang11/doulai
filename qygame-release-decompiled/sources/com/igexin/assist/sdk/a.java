package com.igexin.assist.sdk;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.igexin.assist.control.AbstractPushManager;
import com.igexin.assist.util.AssistUtils;
import com.igexin.c.a.c.a.d;
import com.igexin.push.core.e;
import com.igexin.push.core.e.f;
import com.igexin.push.g.o;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public static final String a = "AssistMangerFactory";
    private static final String c = "com.igexin.assist.control.fcm.ManufacturePushManager";
    private static a d;
    private static final String[] e = {"com.igexin.assist.control.xiaomi.MiuiPushManager", "com.igexin.assist.control.meizu.FlymePushManager", "com.igexin.assist.control.huawei.HmsPushManager", "com.igexin.assist.control.oppo.OppoPushManager", "com.igexin.assist.control.vivo.VivoPushManager", "com.igexin.assist.control.st.SmartisanPushManager", "com.igexin.assist.control.fcm.FcmPushManager"};
    public AbstractPushManager b;

    public static a a() {
        if (d == null) {
            synchronized (AbstractPushManager.class) {
                if (d == null) {
                    d = new a();
                }
            }
        }
        return d;
    }

    private void b(Context context) {
        if (this.b != null && this.b.isSupport()) {
            if (this.b.getBrandCode().equals(AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_XM)) {
                try {
                    Class.forName("com.xiaomi.mipush.sdk.MiPushClient").getDeclaredMethod("clearNotification", Context.class).invoke(null, context);
                } catch (Throwable th) {
                    com.igexin.c.a.c.a.a(th);
                    com.igexin.c.a.c.a.a("AssistMangerFactory | cancelAllAssistNotification() err " + th.toString(), new Object[0]);
                }
                com.igexin.c.a.c.a.b(a, " cancelAllAssistNotification() XM ");
                return;
            }
            if (this.b.getBrandCode().equals(AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_MZ)) {
                try {
                    Class.forName("com.meizu.cloud.pushsdk.PushManager").getDeclaredMethod("clearNotification", Context.class).invoke(null, context);
                } catch (Throwable th2) {
                    com.igexin.c.a.c.a.a(th2);
                    com.igexin.c.a.c.a.a("AssistMangerFactory | cancelAllAssistNotification() err " + th2.toString(), new Object[0]);
                }
                com.igexin.c.a.c.a.b(a, " cancelAllAssistNotification() MZ ");
            }
        }
    }

    private static void c(Context context) {
        try {
            Class.forName("com.xiaomi.mipush.sdk.MiPushClient").getDeclaredMethod("clearNotification", Context.class).invoke(null, context);
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            com.igexin.c.a.c.a.a("AssistMangerFactory | cancelAllAssistNotification() err " + th.toString(), new Object[0]);
        }
        com.igexin.c.a.c.a.b(a, " cancelAllAssistNotification() XM ");
    }

    private static void d() {
        for (String str : e) {
            try {
                Class.forName(str);
                d.a().a("UnSupport plugin [" + str + "]. Please change plugin to 3.0.");
                return;
            } catch (Throwable th) {
                th.getMessage();
            }
        }
    }

    private static void d(Context context) {
        try {
            Class.forName("com.meizu.cloud.pushsdk.PushManager").getDeclaredMethod("clearNotification", Context.class).invoke(null, context);
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            com.igexin.c.a.c.a.a("AssistMangerFactory | cancelAllAssistNotification() err " + th.toString(), new Object[0]);
        }
        com.igexin.c.a.c.a.b(a, " cancelAllAssistNotification() MZ ");
    }

    private String e() {
        return this.b == null ? "" : this.b.getBrandCode();
    }

    private String f() {
        String str;
        Object objInvoke;
        if (this.b == null) {
            return "";
        }
        String name = this.b.getClass().getName();
        String str2 = "";
        try {
            if (!name.contains("fcm")) {
                if (name.contains(AssistUtils.BRAND_XIAOMI)) {
                    Field declaredField = this.b.getClass().getDeclaredField("XIAOMI_VERSION");
                    boolean zIsAccessible = declaredField.isAccessible();
                    declaredField.setAccessible(true);
                    str = (String) declaredField.get(this.b.getClass());
                    try {
                        declaredField.setAccessible(zIsAccessible);
                        return str;
                    } catch (Throwable th) {
                        th = th;
                        com.igexin.c.a.c.a.a(th);
                        return str;
                    }
                }
                if (name.contains(AssistUtils.BRAND_HW)) {
                    return ((String) o.b(e.l).metaData.get("com.huawei.hms.client.service.name:push")).split(":")[1];
                }
                if (name.contains(AssistUtils.BRAND_OPPO)) {
                    Class<?> cls = Class.forName("com.heytap.msp.push.HeytapPushManager");
                    objInvoke = cls.getDeclaredMethod("getSDKVersionName", new Class[0]).invoke(cls, new Object[0]);
                } else if (name.contains(AssistUtils.BRAND_STP)) {
                    Class<?> cls2 = Class.forName("com.gtups.sdk.PushManager");
                    objInvoke = cls2.getDeclaredMethod("getVersion", Context.class).invoke(cls2.getDeclaredMethod("getInstance", new Class[0]).invoke(cls2, new Object[0]), e.l);
                } else {
                    if (name.contains(AssistUtils.BRAND_VIVO)) {
                        ApplicationInfo applicationInfoB = o.b(e.l);
                        StringBuilder sb = new StringBuilder();
                        sb.append(applicationInfoB.metaData.getInt("sdk_version_vivo"));
                        return sb.toString();
                    }
                    if (name.contains(AssistUtils.BRAND_MZ)) {
                        for (Field field : Class.forName("com.meizu.cloud.pushsdk.PushManager").getDeclaredFields()) {
                            if (Modifier.isFinal(field.getModifiers()) && "TAG".equals(field.getName())) {
                                str2 = (String) field.get(null);
                            }
                        }
                    }
                }
                return (String) objInvoke;
            }
            return str2;
        } catch (Throwable th2) {
            th = th2;
            str = "";
        }
    }

    public final AbstractPushManager a(Context context) {
        String lowerCase = AssistUtils.getDeviceBrand().toLowerCase();
        if (com.igexin.push.config.d.M.contains(lowerCase)) {
            com.igexin.c.a.c.a.a("AssistMangerFactory|getPushManager = null, setToken = false", new Object[0]);
            f.a().b("false");
            return null;
        }
        try {
            this.b = (AbstractPushManager) Class.forName("com.igexin.assist.control." + lowerCase + ".ManufacturePushManager").getConstructor(Context.class).newInstance(context);
        } catch (Throwable th) {
            d.a().a(lowerCase + " PushManager = null " + th.toString());
        }
        if (this.b == null) {
            try {
                com.igexin.c.a.c.a.a("AssistMangerFactory|try init fcm push", new Object[0]);
                this.b = (AbstractPushManager) Class.forName(c).getConstructor(Context.class).newInstance(context);
                if (!this.b.isSupport()) {
                    this.b = null;
                }
            } catch (Throwable th2) {
                d.a().a(lowerCase + " Fcm PushManager = null");
                StringBuilder sb = new StringBuilder("|Fcm ManufacturePushManager = null ");
                sb.append(th2.toString());
                com.igexin.c.a.c.a.b(a, sb.toString());
                if (!e.b().booleanValue()) {
                    f.a().b("false");
                }
                if (th2 instanceof ClassNotFoundException) {
                    d();
                }
            }
        }
        if (this.b == null && !e.b().booleanValue()) {
            f.a().b("false");
        }
        StringBuilder sb2 = new StringBuilder("AssistMangerFactory|ManufacturePushManager is null = ");
        sb2.append(this.b == null);
        com.igexin.c.a.c.a.a(sb2.toString(), new Object[0]);
        return this.b;
    }

    public final String[] b() {
        String str;
        String strF;
        if (this.b == null) {
            return new String[]{"", ""};
        }
        try {
            Field declaredField = this.b.getClass().getDeclaredField("PLUGIN_VERSION");
            boolean zIsAccessible = declaredField.isAccessible();
            declaredField.setAccessible(true);
            str = (String) declaredField.get(this.b.getClass());
            try {
                declaredField.setAccessible(zIsAccessible);
            } catch (Throwable th) {
                th = th;
                com.igexin.c.a.c.a.a(th);
            }
        } catch (Throwable th2) {
            th = th2;
            str = "";
        }
        try {
            strF = (String) this.b.getClass().getDeclaredMethod("getBrandSdkVersion", new Class[0]).invoke(this.b, new Object[0]);
        } catch (Throwable th3) {
            com.igexin.c.a.c.a.a(a, th3.getMessage());
            strF = f();
        }
        return new String[]{str, strF};
    }

    public final boolean c() {
        if (com.igexin.push.config.d.M.contains(AssistUtils.getDeviceBrand().toLowerCase()) || this.b == null) {
            return false;
        }
        return this.b.isSupport();
    }
}
