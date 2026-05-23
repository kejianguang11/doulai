package com.igexin.assist.sdk;

import android.content.Context;
import com.igexin.assist.control.AbstractPushManager;
import com.igexin.assist.util.AssistUtils;
import com.igexin.push.config.d;
import com.igexin.push.core.e;
import com.igexin.push.core.e.f;
import com.igexin.push.g.b;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public class AssistPushManager {
    private static final String a = "Assist_OtherPushManager";
    private AbstractPushManager b;
    private AtomicBoolean c;

    static class a {
        private static final AssistPushManager a = new AssistPushManager(0);

        private a() {
        }
    }

    private AssistPushManager() {
        this.c = new AtomicBoolean(false);
    }

    /* synthetic */ AssistPushManager(byte b) {
        this();
    }

    public static boolean checkSupportDevice(Context context) {
        return (d.U && b.a(context.getApplicationContext(), AssistUtils.BRAND_HON)) || b.a(context.getApplicationContext(), AssistUtils.BRAND_HW) || b.a(context.getApplicationContext(), AssistUtils.BRAND_XIAOMI) || b.a(context.getApplicationContext(), AssistUtils.BRAND_OPPO) || b.a(context.getApplicationContext(), AssistUtils.BRAND_MZ) || b.a(context.getApplicationContext(), AssistUtils.BRAND_VIVO) || b.a(context);
    }

    public static AssistPushManager getInstance() {
        return a.a;
    }

    public static String getToken() {
        return e.I;
    }

    public void initialize(Context context) {
        this.b = com.igexin.assist.sdk.a.a().a(context);
    }

    public void register(Context context) {
        if (this.b != null) {
            this.b.register(context);
        }
    }

    public void saveToken(String str) {
        f.a().b(str);
    }

    public void setSilentTime(Context context, int i, int i2) {
        if (this.b != null) {
            this.b.setSilentTime(context, i, i2);
        }
    }

    public void turnOffPush(Context context) {
        if (this.b != null) {
            this.b.turnOffPush(context);
        }
    }

    public void turnOnPush(Context context) {
        if (this.b != null) {
            this.b.turnOnPush(context);
        }
    }

    public void unregister(Context context) {
        if (this.b != null) {
            this.b.unregister(context);
        }
    }
}
