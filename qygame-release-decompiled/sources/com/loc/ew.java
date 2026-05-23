package com.loc;

import android.content.Context;
import android.os.Handler;

/* JADX INFO: loaded from: classes.dex */
public final class ew extends ep<dy> {
    public ew(Context context, String str, Handler handler) {
        super(context, str, handler);
    }

    private static String a(dy dyVar) {
        return dyVar == null ? "" : dyVar.a();
    }

    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
    private static void a2(dy dyVar, long j) {
        if (dyVar != null) {
            dyVar.f = j;
        }
    }

    /* JADX INFO: renamed from: b, reason: avoid collision after fix types in other method */
    private static int b2(dy dyVar) {
        if (dyVar == null) {
            return -113;
        }
        return dyVar.c;
    }

    /* JADX INFO: renamed from: c, reason: avoid collision after fix types in other method */
    private static long c2(dy dyVar) {
        if (dyVar == null) {
            return 0L;
        }
        return dyVar.f;
    }

    @Override // com.loc.ep
    final /* bridge */ /* synthetic */ void a(dy dyVar, long j) {
        a2(dyVar, j);
    }

    @Override // com.loc.ep
    final long b() {
        return em.e;
    }

    @Override // com.loc.ep
    public final /* synthetic */ String b(dy dyVar) {
        return a(dyVar);
    }

    @Override // com.loc.ep
    final /* synthetic */ int c(dy dyVar) {
        return b2(dyVar);
    }

    @Override // com.loc.ep
    final long c() {
        return em.f;
    }

    @Override // com.loc.ep
    final /* synthetic */ long d(dy dyVar) {
        return c2(dyVar);
    }
}
