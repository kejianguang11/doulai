package com.getui.gtc.dim.e;

import com.getui.gtc.base.GtcProvider;
import com.getui.gtc.base.log.Logger;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    private final Logger a;

    static class a {
        private static final b a = new b(0);
    }

    private b() {
        this.a = new Logger(GtcProvider.context());
        this.a.setGlobalTag("gtc.dim");
        this.a.setFileEnableProperty("dim.fileLog");
        this.a.setLogcatEnable(false);
        this.a.setLogFileNameSuffix("gtc");
        this.a.setStackOffset(1);
    }

    /* synthetic */ b(byte b) {
        this();
    }

    public static void a(String str) {
        a.a.a.d(str);
    }

    public static void a(String str, Throwable th) {
        a.a.a.e(str, th);
    }

    public static void a(Throwable th) {
        a.a.a.w(th);
    }

    public static void b(String str) {
        a.a.a.w(str);
    }

    public static void b(Throwable th) {
        a.a.a.e(th);
    }
}
