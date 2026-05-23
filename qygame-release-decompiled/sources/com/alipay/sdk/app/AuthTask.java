package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.sdk.util.e;
import com.alipay.sdk.util.l;
import com.mobile.auth.gatewayauth.Constant;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class AuthTask {
    static final Object a = com.alipay.sdk.util.e.class;
    private static final int b = 73;
    private Activity c;
    private com.alipay.sdk.widget.a d;

    public AuthTask(Activity activity) {
        this.c = activity;
        com.alipay.sdk.sys.b bVarA = com.alipay.sdk.sys.b.a();
        Activity activity2 = this.c;
        com.alipay.sdk.data.c.a();
        bVarA.a(activity2);
        com.alipay.sdk.app.statistic.a.a(activity);
        this.d = new com.alipay.sdk.widget.a(activity, com.alipay.sdk.widget.a.c);
    }

    private e.a a() {
        return new a(this);
    }

    private String a(Activity activity, String str) {
        String strA = new com.alipay.sdk.sys.a(this.c).a(str);
        if (!a(activity)) {
            return b(activity, strA);
        }
        String strA2 = new com.alipay.sdk.util.e(activity, new a(this)).a(strA);
        return TextUtils.equals(strA2, com.alipay.sdk.util.e.b) ? b(activity, strA) : TextUtils.isEmpty(strA2) ? i.a() : strA2;
    }

    private String a(com.alipay.sdk.protocol.b bVar) {
        String[] strArr = bVar.b;
        Bundle bundle = new Bundle();
        bundle.putString(Constant.PROTOCOL_WEB_VIEW_URL, strArr[0]);
        Intent intent = new Intent(this.c, (Class<?>) H5AuthActivity.class);
        intent.putExtras(bundle);
        this.c.startActivity(intent);
        synchronized (a) {
            try {
                a.wait();
            } catch (InterruptedException unused) {
                return i.a();
            }
        }
        String str = i.a;
        return TextUtils.isEmpty(str) ? i.a() : str;
    }

    private static boolean a(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(l.a(), 128);
            if (packageInfo == null) {
                return false;
            }
            return packageInfo.versionCode >= 73;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    private String b(Activity activity, String str) {
        j jVarA;
        b();
        try {
            try {
                List<com.alipay.sdk.protocol.b> listA = com.alipay.sdk.protocol.b.a(new com.alipay.sdk.packet.impl.a().a(activity, str).a().optJSONObject(com.alipay.sdk.cons.c.c).optJSONObject(com.alipay.sdk.cons.c.d));
                c();
                for (int i = 0; i < listA.size(); i++) {
                    if (listA.get(i).a == com.alipay.sdk.protocol.a.WapPay) {
                        return a(listA.get(i));
                    }
                }
            } catch (IOException e) {
                j jVarA2 = j.a(j.NETWORK_ERROR.h);
                com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.a, e);
                c();
                jVarA = jVarA2;
            } catch (Throwable th) {
                com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.b, com.alipay.sdk.app.statistic.c.s, th);
            }
            c();
            jVarA = null;
            if (jVarA == null) {
                jVarA = j.a(j.FAILED.h);
            }
            return i.a(jVarA.h, jVarA.i, "");
        } finally {
            c();
        }
    }

    private void b() {
        if (this.d != null) {
            this.d.a();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        if (this.d != null) {
            this.d.b();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0050 A[Catch: all -> 0x0067, Exception -> 0x007a, TRY_LEAVE, TryCatch #3 {Exception -> 0x007a, all -> 0x0067, blocks: (B:8:0x001a, B:10:0x002d, B:12:0x0043, B:14:0x0049, B:16:0x0050), top: B:30:0x001a, outer: #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized String auth(String str, boolean z) {
        String strA;
        Activity activity;
        String strB;
        if (z) {
            try {
                b();
            } catch (Throwable th) {
                throw th;
            }
        }
        com.alipay.sdk.sys.b bVarA = com.alipay.sdk.sys.b.a();
        Activity activity2 = this.c;
        com.alipay.sdk.data.c.a();
        bVarA.a(activity2);
        strA = i.a();
        try {
            Activity activity3 = this.c;
            String strA2 = new com.alipay.sdk.sys.a(this.c).a(str);
            if (a(activity3)) {
                String strA3 = new com.alipay.sdk.util.e(activity3, new a(this)).a(strA2);
                if (TextUtils.equals(strA3, com.alipay.sdk.util.e.b)) {
                    strB = b(activity3, strA2);
                } else if (TextUtils.isEmpty(strA3)) {
                    strB = i.a();
                } else {
                    strA = strA3;
                    com.alipay.sdk.data.a.b().a(this.c);
                    c();
                    activity = this.c;
                }
                strA = strB;
                com.alipay.sdk.data.a.b().a(this.c);
                c();
                activity = this.c;
            }
        } catch (Exception unused) {
            com.alipay.sdk.data.a.b().a(this.c);
            c();
            activity = this.c;
        } catch (Throwable th2) {
            com.alipay.sdk.data.a.b().a(this.c);
            c();
            com.alipay.sdk.app.statistic.a.a(this.c, str);
            throw th2;
        }
        com.alipay.sdk.app.statistic.a.a(activity, str);
        return strA;
    }

    public synchronized Map<String, String> authV2(String str, boolean z) {
        return com.alipay.sdk.util.j.a(auth(str, z));
    }
}
