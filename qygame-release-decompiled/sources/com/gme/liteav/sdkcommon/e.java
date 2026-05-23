package com.gme.liteav.sdkcommon;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class e implements Runnable {
    private final DashboardManager a;
    private final String b;
    private final String c;

    private e(DashboardManager dashboardManager, String str, String str2) {
        this.a = dashboardManager;
        this.b = str;
        this.c = str2;
    }

    public static Runnable a(DashboardManager dashboardManager, String str, String str2) {
        return new e(dashboardManager, str, str2);
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.setStatusInternal(this.b, this.c);
    }
}
