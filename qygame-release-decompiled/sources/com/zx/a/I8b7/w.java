package com.zx.a.I8b7;

import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class w implements Runnable {
    public final /* synthetic */ File a;
    public final /* synthetic */ String b;
    public final /* synthetic */ File c;
    public final /* synthetic */ File d;

    public w(x xVar, File file, String str, File file2, File file3) {
        this.a = file;
        this.b = str;
        this.c = file2;
        this.d = file3;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            Thread.sleep(1000L);
            this.a.delete();
            new File(this.b).delete();
            this.c.delete();
            this.d.delete();
            o0.a(this.d);
        } catch (Throwable th) {
            v2.a(th);
        }
    }
}
