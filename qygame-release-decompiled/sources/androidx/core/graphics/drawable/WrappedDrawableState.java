package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
final class WrappedDrawableState extends Drawable.ConstantState {
    int a;
    Drawable.ConstantState b;
    ColorStateList c;
    PorterDuff.Mode d;

    WrappedDrawableState(@Nullable WrappedDrawableState wrappedDrawableState) {
        this.c = null;
        this.d = WrappedDrawableApi14.a;
        if (wrappedDrawableState != null) {
            this.a = wrappedDrawableState.a;
            this.b = wrappedDrawableState.b;
            this.c = wrappedDrawableState.c;
            this.d = wrappedDrawableState.d;
        }
    }

    boolean a() {
        return this.b != null;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public int getChangingConfigurations() {
        return (this.b != null ? this.b.getChangingConfigurations() : 0) | this.a;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    @NonNull
    public Drawable newDrawable() {
        return newDrawable(null);
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    @NonNull
    public Drawable newDrawable(@Nullable Resources resources) {
        return Build.VERSION.SDK_INT >= 21 ? new WrappedDrawableApi21(this, resources) : new WrappedDrawableApi14(this, resources);
    }
}
