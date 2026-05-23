package androidx.core.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.alibaba.fastjson.asm.Opcodes;

/* JADX INFO: loaded from: classes.dex */
public abstract class RoundedBitmapDrawable extends Drawable {
    private static final int DEFAULT_PAINT_FLAGS = 3;
    final Bitmap a;
    private int mBitmapHeight;
    private final BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private float mCornerRadius;
    private boolean mIsCircular;
    private int mTargetDensity;
    private int mGravity = 119;
    private final Paint mPaint = new Paint(3);
    private final Matrix mShaderMatrix = new Matrix();
    final Rect b = new Rect();
    private final RectF mDstRectF = new RectF();
    private boolean mApplyGravity = true;

    RoundedBitmapDrawable(Resources resources, Bitmap bitmap) {
        BitmapShader bitmapShader;
        this.mTargetDensity = Opcodes.IF_ICMPNE;
        if (resources != null) {
            this.mTargetDensity = resources.getDisplayMetrics().densityDpi;
        }
        this.a = bitmap;
        if (this.a != null) {
            computeBitmapSize();
            bitmapShader = new BitmapShader(this.a, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        } else {
            this.mBitmapHeight = -1;
            this.mBitmapWidth = -1;
            bitmapShader = null;
        }
        this.mBitmapShader = bitmapShader;
    }

    private void computeBitmapSize() {
        this.mBitmapWidth = this.a.getScaledWidth(this.mTargetDensity);
        this.mBitmapHeight = this.a.getScaledHeight(this.mTargetDensity);
    }

    private static boolean isGreaterThanZero(float f) {
        return f > 0.05f;
    }

    private void updateCircularCornerRadius() {
        this.mCornerRadius = Math.min(this.mBitmapHeight, this.mBitmapWidth) / 2;
    }

    void a() {
        if (this.mApplyGravity) {
            if (this.mIsCircular) {
                int iMin = Math.min(this.mBitmapWidth, this.mBitmapHeight);
                a(this.mGravity, iMin, iMin, getBounds(), this.b);
                int iMin2 = Math.min(this.b.width(), this.b.height());
                this.b.inset(Math.max(0, (this.b.width() - iMin2) / 2), Math.max(0, (this.b.height() - iMin2) / 2));
                this.mCornerRadius = iMin2 * 0.5f;
            } else {
                a(this.mGravity, this.mBitmapWidth, this.mBitmapHeight, getBounds(), this.b);
            }
            this.mDstRectF.set(this.b);
            if (this.mBitmapShader != null) {
                this.mShaderMatrix.setTranslate(this.mDstRectF.left, this.mDstRectF.top);
                this.mShaderMatrix.preScale(this.mDstRectF.width() / this.a.getWidth(), this.mDstRectF.height() / this.a.getHeight());
                this.mBitmapShader.setLocalMatrix(this.mShaderMatrix);
                this.mPaint.setShader(this.mBitmapShader);
            }
            this.mApplyGravity = false;
        }
    }

    void a(int i, int i2, int i3, Rect rect, Rect rect2) {
        throw new UnsupportedOperationException();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        Bitmap bitmap = this.a;
        if (bitmap == null) {
            return;
        }
        a();
        if (this.mPaint.getShader() == null) {
            canvas.drawBitmap(bitmap, (Rect) null, this.b, this.mPaint);
        } else {
            canvas.drawRoundRect(this.mDstRectF, this.mCornerRadius, this.mCornerRadius, this.mPaint);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    @Nullable
    public final Bitmap getBitmap() {
        return this.a;
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    public float getCornerRadius() {
        return this.mCornerRadius;
    }

    public int getGravity() {
        return this.mGravity;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mBitmapHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mBitmapWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        Bitmap bitmap;
        return (this.mGravity != 119 || this.mIsCircular || (bitmap = this.a) == null || bitmap.hasAlpha() || this.mPaint.getAlpha() < 255 || isGreaterThanZero(this.mCornerRadius)) ? -3 : -1;
    }

    @NonNull
    public final Paint getPaint() {
        return this.mPaint;
    }

    public boolean hasAntiAlias() {
        return this.mPaint.isAntiAlias();
    }

    public boolean hasMipMap() {
        throw new UnsupportedOperationException();
    }

    public boolean isCircular() {
        return this.mIsCircular;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        if (this.mIsCircular) {
            updateCircularCornerRadius();
        }
        this.mApplyGravity = true;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i != this.mPaint.getAlpha()) {
            this.mPaint.setAlpha(i);
            invalidateSelf();
        }
    }

    public void setAntiAlias(boolean z) {
        this.mPaint.setAntiAlias(z);
        invalidateSelf();
    }

    public void setCircular(boolean z) {
        this.mIsCircular = z;
        this.mApplyGravity = true;
        if (!z) {
            setCornerRadius(0.0f);
            return;
        }
        updateCircularCornerRadius();
        this.mPaint.setShader(this.mBitmapShader);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public void setCornerRadius(float f) {
        Paint paint;
        BitmapShader bitmapShader;
        if (this.mCornerRadius == f) {
            return;
        }
        this.mIsCircular = false;
        if (isGreaterThanZero(f)) {
            paint = this.mPaint;
            bitmapShader = this.mBitmapShader;
        } else {
            paint = this.mPaint;
            bitmapShader = null;
        }
        paint.setShader(bitmapShader);
        this.mCornerRadius = f;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        this.mPaint.setDither(z);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z) {
        this.mPaint.setFilterBitmap(z);
        invalidateSelf();
    }

    public void setGravity(int i) {
        if (this.mGravity != i) {
            this.mGravity = i;
            this.mApplyGravity = true;
            invalidateSelf();
        }
    }

    public void setMipMap(boolean z) {
        throw new UnsupportedOperationException();
    }

    public void setTargetDensity(int i) {
        if (this.mTargetDensity != i) {
            if (i == 0) {
                i = Opcodes.IF_ICMPNE;
            }
            this.mTargetDensity = i;
            if (this.a != null) {
                computeBitmapSize();
            }
            invalidateSelf();
        }
    }

    public void setTargetDensity(@NonNull Canvas canvas) {
        setTargetDensity(canvas.getDensity());
    }

    public void setTargetDensity(@NonNull DisplayMetrics displayMetrics) {
        setTargetDensity(displayMetrics.densityDpi);
    }
}
