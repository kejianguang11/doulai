package com.igexin.c.a.c;

import com.getui.gtc.base.GtcProvider;
import com.getui.gtc.base.log.Logger;
import com.igexin.c.a.d.g;
import com.igexin.push.config.e;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private static volatile a b;
    private static final List<String> c = new ArrayList();
    public volatile Logger a;

    private a() {
        try {
            this.a = new Logger(GtcProvider.context());
            this.a.setGlobalTag("gtsdk");
            this.a.setLogcatEnable(false);
            this.a.setLogFileNameSuffix("GTSDK");
            this.a.setStackOffset(1);
            this.a.setFileEnableProperty("sdk.debug");
            c.add(g.h);
            c.add("ScheduleQueue");
        } catch (Throwable unused) {
        }
    }

    public static a a() {
        if (b == null) {
            synchronized (a.class) {
                if (b == null) {
                    b = new a();
                }
            }
        }
        return b;
    }

    public static void a(String str, String str2) {
        try {
            if (a().a == null || str == null || c.contains(str)) {
                return;
            }
            a().a.e(str + "|" + str2);
        } catch (Throwable unused) {
        }
    }

    public static void a(String str, Object... objArr) {
        try {
            if (a().a != null) {
                if (objArr.length > 0) {
                    str = String.format(str, objArr);
                }
                a().a.filelog(1, null, str, null);
            }
        } catch (Throwable unused) {
        }
    }

    public static void a(Throwable th) {
        try {
            if (a().a != null) {
                a().a.e(th);
            }
        } catch (Throwable unused) {
        }
    }

    public static void a(boolean z) {
        try {
            e.a(Boolean.valueOf(z));
            if (a().a != null) {
                a().a.setLogcatEnable(false);
                a().a.setFileEnableProperty("sdk.debug");
            }
        } catch (Throwable unused) {
        }
    }

    private static Logger b() {
        return a().a;
    }

    public static void b(String str, String str2) {
        try {
            if (a().a == null || str == null || c.contains(str)) {
                return;
            }
            a().a.d(str + "|" + str2);
        } catch (Throwable unused) {
        }
    }

    private static void c(String str, String str2) {
        try {
            if (a().a == null || str == null || c.contains(str)) {
                return;
            }
            a().a.logcat(2, null, str2, null);
        } catch (Throwable unused) {
        }
    }

    private static void d(String str, String str2) {
        try {
            if (a().a == null || str == null || c.contains(str)) {
                return;
            }
            a().a.logcat(3, null, str2, null);
        } catch (Throwable unused) {
        }
    }

    private static void e(String str, String str2) {
        try {
            if (a().a == null || str == null || c.contains(str)) {
                return;
            }
            a().a.logcat(4, null, str2, null);
        } catch (Throwable unused) {
        }
    }
}
