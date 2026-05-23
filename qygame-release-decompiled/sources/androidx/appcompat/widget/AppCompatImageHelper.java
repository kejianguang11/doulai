package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ImageViewCompat;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class AppCompatImageHelper {
    private TintInfo mImageTint;
    private TintInfo mInternalImageTint;
    private TintInfo mTmpInfo;

    @NonNull
    private final ImageView mView;

    public AppCompatImageHelper(@NonNull ImageView imageView) {
        this.mView = imageView;
    }

    private boolean applyFrameworkTintUsingColorFilter(@NonNull Drawable drawable) {
        if (this.mTmpInfo == null) {
            this.mTmpInfo = new TintInfo();
        }
        TintInfo tintInfo = this.mTmpInfo;
        tintInfo.a();
        ColorStateList imageTintList = ImageViewCompat.getImageTintList(this.mView);
        if (imageTintList != null) {
            tintInfo.mHasTintList = true;
            tintInfo.mTintList = imageTintList;
        }
        PorterDuff.Mode imageTintMode = ImageViewCompat.getImageTintMode(this.mView);
        if (imageTintMode != null) {
            tintInfo.mHasTintMode = true;
            tintInfo.mTintMode = imageTintMode;
        }
        if (!tintInfo.mHasTintList && !tintInfo.mHasTintMode) {
            return false;
        }
        AppCompatDrawableManager.a(drawable, tintInfo, this.mView.getDrawableState());
        return true;
    }

    private boolean shouldApplyFrameworkTintUsingColorFilter() {
        int i = Build.VERSION.SDK_INT;
        return i > 21 ? this.mInternalImageTint != null : i == 21;
    }

    void a(ColorStateList colorStateList) {
        if (this.mImageTint == null) {
            this.mImageTint = new TintInfo();
        }
        this.mImageTint.mTintList = colorStateList;
        this.mImageTint.mHasTintList = true;
        d();
    }

    void a(PorterDuff.Mode mode) {
        if (this.mImageTint == null) {
            this.mImageTint = new TintInfo();
        }
        this.mImageTint.mTintMode = mode;
        this.mImageTint.mHasTintMode = true;
        d();
    }

    boolean a() {
        return Build.VERSION.SDK_INT < 21 || !(this.mView.getBackground() instanceof RippleDrawable);
    }

    ColorStateList b() {
        if (this.mImageTint != null) {
            return this.mImageTint.mTintList;
        }
        return null;
    }

    PorterDuff.Mode c() {
        if (this.mImageTint != null) {
            return this.mImageTint.mTintMode;
        }
        return null;
    }

    void d() {
        TintInfo tintInfo;
        Drawable drawable = this.mView.getDrawable();
        if (drawable != null) {
            DrawableUtils.a(drawable);
        }
        if (drawable != null) {
            if (shouldApplyFrameworkTintUsingColorFilter() && applyFrameworkTintUsingColorFilter(drawable)) {
                return;
            }
            if (this.mImageTint != null) {
                tintInfo = this.mImageTint;
            } else if (this.mInternalImageTint == null) {
                return;
            } else {
                tintInfo = this.mInternalImageTint;
            }
            AppCompatDrawableManager.a(drawable, tintInfo, this.mView.getDrawableState());
        }
    }

    public void loadFromAttributes(AttributeSet attributeSet, int i) {
        int resourceId;
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), attributeSet, R.styleable.AppCompatImageView, i, 0);
        ViewCompat.saveAttributeDataForStyleable(this.mView, this.mView.getContext(), R.styleable.AppCompatImageView, attributeSet, tintTypedArrayObtainStyledAttributes.getWrappedTypeArray(), i, 0);
        try {
            Drawable drawable = this.mView.getDrawable();
            if (drawable == null && (resourceId = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatImageView_srcCompat, -1)) != -1 && (drawable = AppCompatResources.getDrawable(this.mView.getContext(), resourceId)) != null) {
                this.mView.setImageDrawable(drawable);
            }
            if (drawable != null) {
                DrawableUtils.a(drawable);
            }
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatImageView_tint)) {
                ImageViewCompat.setImageTintList(this.mView, tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.AppCompatImageView_tint));
            }
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatImageView_tintMode)) {
                ImageViewCompat.setImageTintMode(this.mView, DrawableUtils.parseTintMode(tintTypedArrayObtainStyledAttributes.getInt(R.styleable.AppCompatImageView_tintMode, -1), null));
            }
        } finally {
            tintTypedArrayObtainStyledAttributes.recycle();
        }
    }

    public void setImageResource(int i) {
        if (i != 0) {
            Drawable drawable = AppCompatResources.getDrawable(this.mView.getContext(), i);
            if (drawable != null) {
                DrawableUtils.a(drawable);
            }
            this.mView.setImageDrawable(drawable);
        } else {
            this.mView.setImageDrawable(null);
        }
        d();
    }
}
