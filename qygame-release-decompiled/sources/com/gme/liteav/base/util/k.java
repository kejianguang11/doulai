package com.gme.liteav.base.util;

import android.text.TextUtils;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
public final class k<T> {
    private T a;
    private Callable<T> b;

    public k(Callable<T> callable) {
        this.b = callable;
    }

    public final T a() {
        if (this.a instanceof String) {
            if (!TextUtils.isEmpty((CharSequence) this.a)) {
                return this.a;
            }
        } else if (this.a != null) {
            return this.a;
        }
        synchronized (this) {
            if (this.a instanceof String) {
                if (!TextUtils.isEmpty((CharSequence) this.a)) {
                    return this.a;
                }
            } else if (this.a != null) {
                return this.a;
            }
            try {
                this.a = this.b.call();
            } catch (Exception e) {
                e.printStackTrace();
                LiteavLog.e("Stash", "Get value failed. msg:" + e.getMessage());
            }
            return this.a;
        }
    }

    public final void a(T t) {
        synchronized (this) {
            this.a = t;
        }
    }
}
