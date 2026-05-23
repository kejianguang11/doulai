package com.loc;

import java.lang.Thread;

/* JADX INFO: loaded from: classes.dex */
public class ak {
    protected static ak a;
    protected Thread.UncaughtExceptionHandler b;
    protected boolean c = true;

    public static void a(Throwable th, String str, String str2) {
        if (a != null) {
            a.a(th, 1, str, str2);
        }
    }

    protected void a() {
    }

    protected void a(w wVar, String str, String str2) {
    }

    protected void a(w wVar, boolean z) {
    }

    protected void a(Throwable th, int i, String str, String str2) {
    }
}
