package com.loc;

import androidx.appcompat.widget.ActivityChooserView;

/* JADX INFO: loaded from: classes.dex */
public abstract class cg {
    cg c;

    public cg() {
    }

    public cg(cg cgVar) {
        this.c = cgVar;
    }

    public void a(int i) {
        if (this.c != null) {
            this.c.a(i);
        }
    }

    public void a(boolean z) {
        if (this.c != null) {
            this.c.a(z);
        }
    }

    protected abstract boolean a();

    public int b() {
        return Math.min(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, this.c != null ? this.c.b() : Integer.MAX_VALUE);
    }

    public final boolean c() {
        if (this.c != null ? this.c.c() : true) {
            return a();
        }
        return false;
    }
}
