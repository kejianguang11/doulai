package com.loc;

import android.content.Context;
import android.os.Handler;

/* JADX INFO: loaded from: classes.dex */
public final class er extends ep<es> {
    public er(Context context, String str, Handler handler) {
        super(context, str, handler);
    }

    private static String a(es esVar) {
        return esVar == null ? "" : esVar.b();
    }

    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
    private static void a2(es esVar, long j) {
        if (esVar != null) {
            esVar.t = j;
        }
    }

    /* JADX INFO: renamed from: b, reason: avoid collision after fix types in other method */
    private static int b2(es esVar) {
        if (esVar == null) {
            return 99;
        }
        return esVar.s;
    }

    /* JADX INFO: renamed from: c, reason: avoid collision after fix types in other method */
    private static long c2(es esVar) {
        if (esVar == null) {
            return 0L;
        }
        return esVar.t;
    }

    @Override // com.loc.ep
    final /* bridge */ /* synthetic */ void a(es esVar, long j) {
        a2(esVar, j);
    }

    @Override // com.loc.ep
    final long b() {
        return em.g;
    }

    @Override // com.loc.ep
    public final /* synthetic */ String b(es esVar) {
        return a(esVar);
    }

    @Override // com.loc.ep
    final /* synthetic */ int c(es esVar) {
        return b2(esVar);
    }

    @Override // com.loc.ep
    final long c() {
        return em.h;
    }

    @Override // com.loc.ep
    final /* synthetic */ long d(es esVar) {
        return c2(esVar);
    }
}
