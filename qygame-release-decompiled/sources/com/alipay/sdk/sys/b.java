package com.alipay.sdk.sys;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.data.c;
import com.ta.utdid2.device.UTDevice;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    private static b b;
    public Context a;

    private b() {
    }

    public static b a() {
        if (b == null) {
            b = new b();
        }
        return b;
    }

    private static String a(String[] strArr) throws Throwable {
        Process processStart;
        String line;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(strArr);
            processBuilder.redirectErrorStream(false);
            processStart = processBuilder.start();
            try {
                try {
                    DataOutputStream dataOutputStream = new DataOutputStream(processStart.getOutputStream());
                    line = new DataInputStream(processStart.getInputStream()).readLine();
                    try {
                        dataOutputStream.writeBytes("exit\n");
                        dataOutputStream.flush();
                        processStart.waitFor();
                    } catch (Throwable unused) {
                    }
                } catch (Throwable th) {
                    th = th;
                    try {
                        processStart.destroy();
                    } catch (Exception unused2) {
                    }
                    throw th;
                }
            } catch (Throwable unused3) {
                line = "";
            }
        } catch (Throwable th2) {
            th = th2;
            processStart = null;
        }
        try {
            processStart.destroy();
        } catch (Exception unused4) {
        }
        return line;
    }

    public static boolean b() {
        String[] strArr = {"/system/xbin/", "/system/bin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        for (int i = 0; i < 5; i++) {
            try {
                String str = strArr[i] + "su";
                if (new File(str).exists()) {
                    String strA = a(new String[]{"ls", "-l", str});
                    if (!TextUtils.isEmpty(strA)) {
                        if (strA.indexOf("root") != strA.lastIndexOf("root")) {
                            return true;
                        }
                    }
                    return false;
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    private Context d() {
        return this.a;
    }

    private static c e() {
        return c.a();
    }

    public final void a(Context context) {
        this.a = context.getApplicationContext();
    }

    public final String c() {
        try {
            return UTDevice.getUtdid(this.a);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.e, com.alipay.sdk.app.statistic.c.j, th);
            return "";
        }
    }
}
