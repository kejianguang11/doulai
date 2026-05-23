package com.igexin.push.core.i.a;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: classes.dex */
public abstract class b<T extends Drawable> implements l, m<T> {
    protected final T a;

    public b(T t) {
        this.a = (T) k.a(t);
    }

    @Override // com.igexin.push.core.i.a.m
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final T c() {
        Drawable.ConstantState constantState = this.a.getConstantState();
        return constantState == null ? this.a : (T) constantState.newDrawable();
    }

    @Override // com.igexin.push.core.i.a.l
    public void b() {
        if (this.a instanceof BitmapDrawable) {
            ((BitmapDrawable) this.a).getBitmap().prepareToDraw();
        } else if (this.a instanceof e) {
            ((e) this.a).a().prepareToDraw();
        }
    }
}
