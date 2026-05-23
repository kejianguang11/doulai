package com.getui.gtc.dyc.a.a;

import com.getui.gtc.base.GtcProvider;
import com.getui.gtc.base.log.Logger;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private Logger a;

    /* JADX INFO: renamed from: com.getui.gtc.dyc.a.a.a$a, reason: collision with other inner class name */
    static class C0018a {
        private static a a = new a();
    }

    private a() {
        this.a = new Logger(GtcProvider.context());
        this.a.setGlobalTag("gtc.dyc");
        this.a.setFileEnableProperty("dyc.fileLog");
        this.a.setLogcatEnable(false);
        this.a.setLogFileNameSuffix("gtc");
        this.a.setStackOffset(1);
    }

    public static Logger a() {
        return C0018a.a.a;
    }

    public static void a(String str) {
        C0018a.a.a.e(str);
    }

    public static void a(Throwable th) {
        C0018a.a.a.w(th);
    }

    public static void c(Throwable th) {
        C0018a.a.a.e(th);
    }
}
