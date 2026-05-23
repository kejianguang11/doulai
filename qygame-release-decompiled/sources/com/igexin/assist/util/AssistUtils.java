package com.igexin.assist.util;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.igexin.push.config.d;
import com.igexin.push.core.ServiceManager;
import com.igexin.push.g.b;
import com.igexin.sdk.PushManager;

/* JADX INFO: loaded from: classes.dex */
public class AssistUtils {
    public static final String BRAND_HON = "honor";
    public static final String BRAND_HW = "huawei";
    public static final String BRAND_MZ = "meizu";
    public static final String BRAND_OPPO = "oppo";
    public static final String BRAND_STP = "stp";
    public static final String BRAND_VIVO = "vivo";
    public static final String BRAND_XIAOMI = "xiaomi";
    static String a = "";

    public static String getDeviceBrand() {
        if (!TextUtils.isEmpty(a)) {
            return a;
        }
        a = (d.U && b.a(ServiceManager.b.getApplicationContext(), BRAND_HON)) ? BRAND_HON : b.a(ServiceManager.b.getApplicationContext(), BRAND_HW) ? BRAND_HW : b.a(ServiceManager.b.getApplicationContext(), BRAND_XIAOMI) ? BRAND_XIAOMI : b.a(ServiceManager.b.getApplicationContext(), BRAND_OPPO) ? BRAND_OPPO : b.a(ServiceManager.b.getApplicationContext(), BRAND_MZ) ? BRAND_MZ : b.a(ServiceManager.b.getApplicationContext(), BRAND_VIVO) ? BRAND_VIVO : b.a(ServiceManager.b) ? BRAND_STP : Build.BRAND;
        return a.toLowerCase();
    }

    public static void startGetuiService(Context context) {
        if (context != null) {
            try {
                PushManager.getInstance().initialize(context);
            } catch (Throwable th) {
                com.igexin.c.a.c.a.a(th);
            }
        }
    }
}
