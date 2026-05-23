package com.igexin.push.g;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.text.TextUtils;
import com.igexin.push.config.SDKUrlConfig;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class h {
    private static final int a = 10000;
    private static final String b = "ErrorReport";

    /* JADX INFO: renamed from: com.igexin.push.g.h$1, reason: invalid class name */
    public static class AnonymousClass1 implements Runnable {
        final /* synthetic */ Context a;
        final /* synthetic */ a b;

        public AnonymousClass1(Context context, a aVar) {
            this.a = context;
            this.b = aVar;
        }

        @Override // java.lang.Runnable
        public final void run() {
            boolean z = false;
            try {
                if (h.a()) {
                    com.igexin.push.core.d.d.a().a(com.igexin.push.core.d.d.g, Long.valueOf(System.currentTimeMillis()));
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("action", "upload_BI");
                    jSONObject.put("BIType", "25");
                    jSONObject.put("cid", "0");
                    jSONObject.put("BIData", new String(com.igexin.c.a.b.g.c(h.a(this.a).getBytes()), com.alipay.sdk.sys.a.m));
                    byte[] bArrA = s.a(SDKUrlConfig.getBiUploadServiceUrl(), com.igexin.c.b.a.b(jSONObject.toString().getBytes()));
                    if (bArrA != null) {
                        new String(bArrA);
                    }
                    z = true;
                }
            } catch (Throwable th) {
                com.igexin.c.a.c.a.a(th);
                com.igexin.c.a.c.a.a("ErrorReport|report 25 ex = " + th.toString(), new Object[0]);
            }
            if (this.b != null) {
                this.b.a(z);
            }
        }
    }

    public interface a {
        void a(boolean z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String a(Context context) {
        String packageName = context.getPackageName();
        String string = null;
        try {
            ApplicationInfo applicationInfoB = o.b(context);
            if (applicationInfoB != null && applicationInfoB.metaData != null) {
                String strA = d.a(applicationInfoB);
                try {
                    string = TextUtils.isEmpty(strA) ? applicationInfoB.metaData.getString(com.igexin.push.core.b.b) : strA;
                    if (TextUtils.isEmpty(string)) {
                        string = applicationInfoB.metaData.getString("GETUI_APPID");
                    }
                } catch (Exception e) {
                    e = e;
                    string = strA;
                    com.igexin.c.a.c.a.a(e);
                }
            }
        } catch (Exception e2) {
            e = e2;
        }
        String str = Build.VERSION.SDK;
        String str2 = Build.VERSION.RELEASE;
        StringBuilder sb = new StringBuilder();
        sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
        sb.append("|");
        sb.append(string);
        sb.append("|");
        sb.append("3.3.10.0");
        sb.append("|");
        sb.append(true);
        sb.append("|");
        sb.append(o.g() == null ? "" : o.g());
        sb.append("|");
        sb.append(o.e());
        sb.append("|");
        sb.append(str);
        sb.append("|");
        sb.append(str2);
        sb.append("|");
        sb.append(o.a(context));
        sb.append("|");
        sb.append(o.k());
        sb.append("|");
        sb.append(packageName);
        if (g.d != null) {
            sb.append("|");
            sb.append(g.d);
        }
        com.igexin.c.a.c.a.a("ErrorReport|" + sb.toString(), new Object[0]);
        return sb.toString();
    }

    private static void a(a aVar, Context context) {
        com.igexin.b.a.a().a.execute(new AnonymousClass1(context, aVar));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a() {
        try {
            return System.currentTimeMillis() - com.igexin.push.core.d.d.a().a(com.igexin.push.core.d.d.g, new long[0]) > com.igexin.push.core.b.J;
        } catch (Exception e) {
            com.igexin.c.a.c.a.a(e);
            return false;
        }
    }
}
