package com.gme.liteav.sdkcommon;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class c implements Runnable {
    private final DashboardManager a;
    private final String b;

    private c(DashboardManager dashboardManager, String str) {
        this.a = dashboardManager;
        this.b = str;
    }

    public static Runnable a(DashboardManager dashboardManager, String str) {
        return new c(dashboardManager, str);
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.removeDashboardInternal(this.b);
    }
}
