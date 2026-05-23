package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.R;
import androidx.core.view.ViewCompat;

/* JADX INFO: loaded from: classes.dex */
class AppCompatBackgroundHelper {
    private TintInfo mBackgroundTint;
    private TintInfo mInternalBackgroundTint;
    private TintInfo mTmpInfo;

    @NonNull
    private final View mView;
    private int mBackgroundResId = -1;
    private final AppCompatDrawableManager mDrawableManager = AppCompatDrawableManager.get();

    AppCompatBackgroundHelper(@NonNull View view) {
        this.mView = view;
    }

    private boolean applyFrameworkTintUsingColorFilter(@NonNull Drawable drawable) {
        if (this.mTmpInfo == null) {
            this.mTmpInfo = new TintInfo();
        }
        TintInfo tintInfo = this.mTmpInfo;
        tintInfo.a();
        ColorStateList backgroundTintList = ViewCompat.getBackgroundTintList(this.mView);
        if (backgroundTintList != null) {
            tintInfo.mHasTintList = true;
            tintInfo.mTintList = backgroundTintList;
        }
        PorterDuff.Mode backgroundTintMode = ViewCompat.getBackgroundTintMode(this.mView);
        if (backgroundTintMode != null) {
            tintInfo.mHasTintMode = true;
            tintInfo.mTintMode = backgroundTintMode;
        }
        if (!tintInfo.mHasTintList && !tintInfo.mHasTintMode) {
            return false;
        }
        AppCompatDrawableManager.a(drawable, tintInfo, this.mView.getDrawableState());
        return true;
    }

    private boolean shouldApplyFrameworkTintUsingColorFilter() {
        int i = Build.VERSION.SDK_INT;
        return i > 21 ? this.mInternalBackgroundTint != null : i == 21;
    }

    ColorStateList a() {
        if (this.mBackgroundTint != null) {
            return this.mBackgroundTint.mTintList;
        }
        return null;
    }

    void a(int i) {
        this.mBackgroundResId = i;
        b(this.mDrawableManager != null ? this.mDrawableManager.a(this.mView.getContext(), i) : null);
        c();
    }

    void a(ColorStateList colorStateList) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        this.mBackgroundTint.mTintList = colorStateList;
        this.mBackgroundTint.mHasTintList = true;
        c();
    }

    void a(PorterDuff.Mode mode) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        this.mBackgroundTint.mTintMode = mode;
        this.mBackgroundTint.mHasTintMode = true;
        c();
    }

    void a(Drawable drawable) {
        this.mBackgroundResId = -1;
        b(null);
        c();
    }

    void a(@Nullable AttributeSet attributeSet, int i) {
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), attributeSet, R.styleable.ViewBackgroundHelper, i, 0);
        ViewCompat.saveAttributeDataForStyleable(this.mView, this.mView.getContext(), R.styleable.ViewBackgroundHelper, attributeSet, tintTypedArrayObtainStyledAttributes.getWrappedTypeArray(), i, 0);
        try {
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.ViewBackgroundHelper_android_background)) {
                this.mBackgroundResId = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.ViewBackgroundHelper_android_background, -1);
                ColorStateList colorStateListA = this.mDrawableManager.a(this.mView.getContext(), this.mBackgroundResId);
                if (colorStateListA != null) {
                    b(colorStateListA);
                }
            }
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.ViewBackgroundHelper_backgroundTint)) {
                ViewCompat.setBackgroundTintList(this.mView, tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.ViewBackgroundHelper_backgroundTint));
            }
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.ViewBackgroundHelper_backgroundTintMode)) {
                ViewCompat.setBackgroundTintMode(this.mView, DrawableUtils.parseTintMode(tintTypedArrayObtainStyledAttributes.getInt(R.styleable.ViewBackgroundHelper_backgroundTintMode, -1), null));
            }
        } finally {
            tintTypedArrayObtainStyledAttributes.recycle();
        }
    }

    PorterDuff.Mode b() {
        if (this.mBackgroundTint != null) {
            return this.mBackgroundTint.mTintMode;
        }
        return null;
    }

    void b(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (this.mInternalBackgroundTint == null) {
                this.mInternalBackgroundTint = new TintInfo();
            }
            this.mInternalBackgroundTint.mTintList = colorStateList;
            this.mInternalBackgroundTint.mHasTintList = true;
        } else {
            this.mInternalBackgroundTint = null;
        }
        c();
    }

    void c() {
        TintInfo tintInfo;
        Drawable background = this.mView.getBackground();
        if (background != null) {
            if (shouldApplyFrameworkTintUsingColorFilter() && applyFrameworkTintUsingColorFilter(background)) {
                return;
            }
            if (this.mBackgroundTint != null) {
                tintInfo = this.mBackgroundTint;
            } else if (this.mInternalBackgroundTint == null) {
                return;
            } else {
                tintInfo = this.mInternalBackgroundTint;
            }
            AppCompatDrawableManager.a(background, tintInfo, this.mView.getDrawableState());
        }
    }
}
