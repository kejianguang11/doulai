package androidx.core.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.Rect;
import android.view.Gravity;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* JADX INFO: loaded from: classes.dex */
@RequiresApi(21)
class RoundedBitmapDrawable21 extends RoundedBitmapDrawable {
    protected RoundedBitmapDrawable21(Resources resources, Bitmap bitmap) {
        super(resources, bitmap);
    }

    @Override // androidx.core.graphics.drawable.RoundedBitmapDrawable
    void a(int i, int i2, int i3, Rect rect, Rect rect2) {
        Gravity.apply(i, i2, i3, rect, rect2, 0);
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(@NonNull Outline outline) {
        a();
        outline.setRoundRect(this.b, getCornerRadius());
    }

    @Override // androidx.core.graphics.drawable.RoundedBitmapDrawable
    public boolean hasMipMap() {
        return this.a != null && this.a.hasMipMap();
    }

    @Override // androidx.core.graphics.drawable.RoundedBitmapDrawable
    public void setMipMap(boolean z) {
        if (this.a != null) {
            this.a.setHasMipMap(z);
            invalidateSelf();
        }
    }
}
