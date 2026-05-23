package androidx.appcompat.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes.dex */
class ActionBarBackgroundDrawable extends Drawable {
    final ActionBarContainer a;

    public ActionBarBackgroundDrawable(ActionBarContainer actionBarContainer) {
        this.a = actionBarContainer;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Drawable drawable;
        if (!this.a.d) {
            if (this.a.a != null) {
                this.a.a.draw(canvas);
            }
            if (this.a.b == null || !this.a.e) {
                return;
            } else {
                drawable = this.a.b;
            }
        } else if (this.a.c == null) {
            return;
        } else {
            drawable = this.a.c;
        }
        drawable.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    @RequiresApi(21)
    public void getOutline(@NonNull Outline outline) {
        Drawable drawable;
        if (this.a.d) {
            if (this.a.c == null) {
                return;
            } else {
                drawable = this.a.c;
            }
        } else if (this.a.a == null) {
            return;
        } else {
            drawable = this.a.a;
        }
        drawable.getOutline(outline);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }
}
