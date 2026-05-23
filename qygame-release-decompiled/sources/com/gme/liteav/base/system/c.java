package com.gme.liteav.base.system;

import com.gme.liteav.base.util.e;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class c implements e.a {
    private static final c a = new c();

    private c() {
    }

    public static e.a a() {
        return a;
    }

    @Override // com.gme.liteav.base.util.e.a
    public final void a(boolean z) {
        LiteavSystemInfo.onAppBackgroundStateChanged(z);
    }
}
