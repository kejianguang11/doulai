package androidx.appcompat.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.LocaleList;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.inputmethod.EditorInfoCompat;
import androidx.core.widget.AutoSizeableTextView;
import androidx.core.widget.TextViewCompat;
import java.lang.ref.WeakReference;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
class AppCompatTextHelper {
    private static final int MONOSPACE = 3;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int TEXT_FONT_WEIGHT_UNSPECIFIED = -1;
    private boolean mAsyncFontPending;

    @NonNull
    private final AppCompatTextViewAutoSizeHelper mAutoSizeTextHelper;
    private TintInfo mDrawableBottomTint;
    private TintInfo mDrawableEndTint;
    private TintInfo mDrawableLeftTint;
    private TintInfo mDrawableRightTint;
    private TintInfo mDrawableStartTint;
    private TintInfo mDrawableTint;
    private TintInfo mDrawableTopTint;
    private Typeface mFontTypeface;

    @NonNull
    private final TextView mView;
    private int mStyle = 0;
    private int mFontWeight = -1;

    AppCompatTextHelper(@NonNull TextView textView) {
        this.mView = textView;
        this.mAutoSizeTextHelper = new AppCompatTextViewAutoSizeHelper(this.mView);
    }

    private void applyCompoundDrawableTint(Drawable drawable, TintInfo tintInfo) {
        if (drawable == null || tintInfo == null) {
            return;
        }
        AppCompatDrawableManager.a(drawable, tintInfo, this.mView.getDrawableState());
    }

    private static TintInfo createTintInfo(Context context, AppCompatDrawableManager appCompatDrawableManager, int i) {
        ColorStateList colorStateListA = appCompatDrawableManager.a(context, i);
        if (colorStateListA == null) {
            return null;
        }
        TintInfo tintInfo = new TintInfo();
        tintInfo.mHasTintList = true;
        tintInfo.mTintList = colorStateListA;
        return tintInfo;
    }

    private void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5, Drawable drawable6) {
        if (Build.VERSION.SDK_INT >= 17 && (drawable5 != null || drawable6 != null)) {
            Drawable[] compoundDrawablesRelative = this.mView.getCompoundDrawablesRelative();
            TextView textView = this.mView;
            if (drawable5 == null) {
                drawable5 = compoundDrawablesRelative[0];
            }
            if (drawable2 == null) {
                drawable2 = compoundDrawablesRelative[1];
            }
            if (drawable6 == null) {
                drawable6 = compoundDrawablesRelative[2];
            }
            if (drawable4 == null) {
                drawable4 = compoundDrawablesRelative[3];
            }
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable5, drawable2, drawable6, drawable4);
            return;
        }
        if (drawable == null && drawable2 == null && drawable3 == null && drawable4 == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 17) {
            Drawable[] compoundDrawablesRelative2 = this.mView.getCompoundDrawablesRelative();
            if (compoundDrawablesRelative2[0] != null || compoundDrawablesRelative2[2] != null) {
                TextView textView2 = this.mView;
                Drawable drawable7 = compoundDrawablesRelative2[0];
                if (drawable2 == null) {
                    drawable2 = compoundDrawablesRelative2[1];
                }
                Drawable drawable8 = compoundDrawablesRelative2[2];
                if (drawable4 == null) {
                    drawable4 = compoundDrawablesRelative2[3];
                }
                textView2.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable7, drawable2, drawable8, drawable4);
                return;
            }
        }
        Drawable[] compoundDrawables = this.mView.getCompoundDrawables();
        TextView textView3 = this.mView;
        if (drawable == null) {
            drawable = compoundDrawables[0];
        }
        if (drawable2 == null) {
            drawable2 = compoundDrawables[1];
        }
        if (drawable3 == null) {
            drawable3 = compoundDrawables[2];
        }
        if (drawable4 == null) {
            drawable4 = compoundDrawables[3];
        }
        textView3.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
    }

    private void setCompoundTints() {
        this.mDrawableLeftTint = this.mDrawableTint;
        this.mDrawableTopTint = this.mDrawableTint;
        this.mDrawableRightTint = this.mDrawableTint;
        this.mDrawableBottomTint = this.mDrawableTint;
        this.mDrawableStartTint = this.mDrawableTint;
        this.mDrawableEndTint = this.mDrawableTint;
    }

    private void setTextSizeInternal(int i, float f) {
        this.mAutoSizeTextHelper.a(i, f);
    }

    private void updateTypefaceAndStyle(Context context, TintTypedArray tintTypedArray) {
        String string;
        Typeface typefaceCreate;
        Typeface typeface;
        this.mStyle = tintTypedArray.getInt(R.styleable.TextAppearance_android_textStyle, this.mStyle);
        if (Build.VERSION.SDK_INT >= 28) {
            this.mFontWeight = tintTypedArray.getInt(R.styleable.TextAppearance_android_textFontWeight, -1);
            if (this.mFontWeight != -1) {
                this.mStyle = (this.mStyle & 2) | 0;
            }
        }
        if (!tintTypedArray.hasValue(R.styleable.TextAppearance_android_fontFamily) && !tintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily)) {
            if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_typeface)) {
                this.mAsyncFontPending = false;
                switch (tintTypedArray.getInt(R.styleable.TextAppearance_android_typeface, 1)) {
                    case 1:
                        typeface = Typeface.SANS_SERIF;
                        break;
                    case 2:
                        typeface = Typeface.SERIF;
                        break;
                    case 3:
                        typeface = Typeface.MONOSPACE;
                        break;
                    default:
                        return;
                }
                this.mFontTypeface = typeface;
                return;
            }
            return;
        }
        this.mFontTypeface = null;
        int i = tintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily) ? R.styleable.TextAppearance_fontFamily : R.styleable.TextAppearance_android_fontFamily;
        final int i2 = this.mFontWeight;
        final int i3 = this.mStyle;
        if (!context.isRestricted()) {
            final WeakReference weakReference = new WeakReference(this.mView);
            try {
                Typeface font = tintTypedArray.getFont(i, this.mStyle, new ResourcesCompat.FontCallback() { // from class: androidx.appcompat.widget.AppCompatTextHelper.1
                    @Override // androidx.core.content.res.ResourcesCompat.FontCallback
                    public void onFontRetrievalFailed(int i4) {
                    }

                    @Override // androidx.core.content.res.ResourcesCompat.FontCallback
                    public void onFontRetrieved(@NonNull Typeface typeface2) {
                        if (Build.VERSION.SDK_INT >= 28 && i2 != -1) {
                            typeface2 = Typeface.create(typeface2, i2, (i3 & 2) != 0);
                        }
                        AppCompatTextHelper.this.a(weakReference, typeface2);
                    }
                });
                if (font != null) {
                    if (Build.VERSION.SDK_INT >= 28 && this.mFontWeight != -1) {
                        font = Typeface.create(Typeface.create(font, 0), this.mFontWeight, (this.mStyle & 2) != 0);
                    }
                    this.mFontTypeface = font;
                }
                this.mAsyncFontPending = this.mFontTypeface == null;
            } catch (Resources.NotFoundException | UnsupportedOperationException unused) {
            }
        }
        if (this.mFontTypeface != null || (string = tintTypedArray.getString(i)) == null) {
            return;
        }
        if (Build.VERSION.SDK_INT < 28 || this.mFontWeight == -1) {
            typefaceCreate = Typeface.create(string, this.mStyle);
        } else {
            typefaceCreate = Typeface.create(Typeface.create(string, 0), this.mFontWeight, (this.mStyle & 2) != 0);
        }
        this.mFontTypeface = typefaceCreate;
    }

    void a() {
        b();
    }

    void a(int i) {
        this.mAutoSizeTextHelper.a(i);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    void a(int i, float f) {
        if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE || d()) {
            return;
        }
        setTextSizeInternal(i, f);
    }

    void a(int i, int i2, int i3, int i4) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.a(i, i2, i3, i4);
    }

    void a(Context context, int i) {
        String string;
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        ColorStateList colorStateList3;
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, i, R.styleable.TextAppearance);
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextAppearance_textAllCaps)) {
            a(tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.TextAppearance_textAllCaps, false));
        }
        if (Build.VERSION.SDK_INT < 23) {
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textColor) && (colorStateList3 = tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.TextAppearance_android_textColor)) != null) {
                this.mView.setTextColor(colorStateList3);
            }
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textColorLink) && (colorStateList2 = tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.TextAppearance_android_textColorLink)) != null) {
                this.mView.setLinkTextColor(colorStateList2);
            }
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textColorHint) && (colorStateList = tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.TextAppearance_android_textColorHint)) != null) {
                this.mView.setHintTextColor(colorStateList);
            }
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textSize) && tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, -1) == 0) {
            this.mView.setTextSize(0, 0.0f);
        }
        updateTypefaceAndStyle(context, tintTypedArrayObtainStyledAttributes);
        if (Build.VERSION.SDK_INT >= 26 && tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextAppearance_fontVariationSettings) && (string = tintTypedArrayObtainStyledAttributes.getString(R.styleable.TextAppearance_fontVariationSettings)) != null) {
            this.mView.setFontVariationSettings(string);
        }
        tintTypedArrayObtainStyledAttributes.recycle();
        if (this.mFontTypeface != null) {
            this.mView.setTypeface(this.mFontTypeface, this.mStyle);
        }
    }

    void a(@Nullable ColorStateList colorStateList) {
        if (this.mDrawableTint == null) {
            this.mDrawableTint = new TintInfo();
        }
        this.mDrawableTint.mTintList = colorStateList;
        this.mDrawableTint.mHasTintList = colorStateList != null;
        setCompoundTints();
    }

    void a(@Nullable PorterDuff.Mode mode) {
        if (this.mDrawableTint == null) {
            this.mDrawableTint = new TintInfo();
        }
        this.mDrawableTint.mTintMode = mode;
        this.mDrawableTint.mHasTintMode = mode != null;
        setCompoundTints();
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0141  */
    @SuppressLint({"NewApi"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void a(@Nullable AttributeSet attributeSet, int i) {
        boolean z;
        boolean z2;
        ColorStateList colorStateList;
        String string;
        ColorStateList colorStateList2;
        String string2;
        ColorStateList colorStateList3;
        boolean z3;
        int i2;
        Context context = this.mView.getContext();
        AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.get();
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.AppCompatTextHelper, i, 0);
        ViewCompat.saveAttributeDataForStyleable(this.mView, this.mView.getContext(), R.styleable.AppCompatTextHelper, attributeSet, tintTypedArrayObtainStyledAttributes.getWrappedTypeArray(), i, 0);
        int resourceId = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_textAppearance, -1);
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableLeft)) {
            this.mDrawableLeftTint = createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableLeft, 0));
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableTop)) {
            this.mDrawableTopTint = createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableTop, 0));
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableRight)) {
            this.mDrawableRightTint = createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableRight, 0));
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableBottom)) {
            this.mDrawableBottomTint = createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableBottom, 0));
        }
        if (Build.VERSION.SDK_INT >= 17) {
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableStart)) {
                this.mDrawableStartTint = createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableStart, 0));
            }
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableEnd)) {
                this.mDrawableEndTint = createTintInfo(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableEnd, 0));
            }
        }
        tintTypedArrayObtainStyledAttributes.recycle();
        boolean z4 = this.mView.getTransformationMethod() instanceof PasswordTransformationMethod;
        if (resourceId != -1) {
            TintTypedArray tintTypedArrayObtainStyledAttributes2 = TintTypedArray.obtainStyledAttributes(context, resourceId, R.styleable.TextAppearance);
            if (z4 || !tintTypedArrayObtainStyledAttributes2.hasValue(R.styleable.TextAppearance_textAllCaps)) {
                z = false;
                z2 = false;
            } else {
                z2 = tintTypedArrayObtainStyledAttributes2.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
                z = true;
            }
            updateTypefaceAndStyle(context, tintTypedArrayObtainStyledAttributes2);
            if (Build.VERSION.SDK_INT < 23) {
                ColorStateList colorStateList4 = tintTypedArrayObtainStyledAttributes2.hasValue(R.styleable.TextAppearance_android_textColor) ? tintTypedArrayObtainStyledAttributes2.getColorStateList(R.styleable.TextAppearance_android_textColor) : null;
                colorStateList = tintTypedArrayObtainStyledAttributes2.hasValue(R.styleable.TextAppearance_android_textColorHint) ? tintTypedArrayObtainStyledAttributes2.getColorStateList(R.styleable.TextAppearance_android_textColorHint) : null;
                if (tintTypedArrayObtainStyledAttributes2.hasValue(R.styleable.TextAppearance_android_textColorLink)) {
                    ColorStateList colorStateList5 = colorStateList4;
                    colorStateList3 = tintTypedArrayObtainStyledAttributes2.getColorStateList(R.styleable.TextAppearance_android_textColorLink);
                    colorStateList2 = colorStateList5;
                    string2 = !tintTypedArrayObtainStyledAttributes2.hasValue(R.styleable.TextAppearance_textLocale) ? tintTypedArrayObtainStyledAttributes2.getString(R.styleable.TextAppearance_textLocale) : null;
                    string = (Build.VERSION.SDK_INT >= 26 || !tintTypedArrayObtainStyledAttributes2.hasValue(R.styleable.TextAppearance_fontVariationSettings)) ? null : tintTypedArrayObtainStyledAttributes2.getString(R.styleable.TextAppearance_fontVariationSettings);
                    tintTypedArrayObtainStyledAttributes2.recycle();
                } else {
                    colorStateList2 = colorStateList4;
                }
            } else {
                colorStateList = null;
                colorStateList2 = null;
            }
            colorStateList3 = null;
            if (!tintTypedArrayObtainStyledAttributes2.hasValue(R.styleable.TextAppearance_textLocale)) {
            }
            if (Build.VERSION.SDK_INT >= 26) {
                tintTypedArrayObtainStyledAttributes2.recycle();
            }
        } else {
            z = false;
            z2 = false;
            colorStateList = null;
            string = null;
            colorStateList2 = null;
            string2 = null;
            colorStateList3 = null;
        }
        TintTypedArray tintTypedArrayObtainStyledAttributes3 = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.TextAppearance, i, 0);
        if (z4 || !tintTypedArrayObtainStyledAttributes3.hasValue(R.styleable.TextAppearance_textAllCaps)) {
            z3 = z;
        } else {
            z2 = tintTypedArrayObtainStyledAttributes3.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
            z3 = true;
        }
        if (Build.VERSION.SDK_INT < 23) {
            if (tintTypedArrayObtainStyledAttributes3.hasValue(R.styleable.TextAppearance_android_textColor)) {
                colorStateList2 = tintTypedArrayObtainStyledAttributes3.getColorStateList(R.styleable.TextAppearance_android_textColor);
            }
            if (tintTypedArrayObtainStyledAttributes3.hasValue(R.styleable.TextAppearance_android_textColorHint)) {
                colorStateList = tintTypedArrayObtainStyledAttributes3.getColorStateList(R.styleable.TextAppearance_android_textColorHint);
            }
            if (tintTypedArrayObtainStyledAttributes3.hasValue(R.styleable.TextAppearance_android_textColorLink)) {
                colorStateList3 = tintTypedArrayObtainStyledAttributes3.getColorStateList(R.styleable.TextAppearance_android_textColorLink);
            }
        }
        if (tintTypedArrayObtainStyledAttributes3.hasValue(R.styleable.TextAppearance_textLocale)) {
            string2 = tintTypedArrayObtainStyledAttributes3.getString(R.styleable.TextAppearance_textLocale);
        }
        if (Build.VERSION.SDK_INT >= 26 && tintTypedArrayObtainStyledAttributes3.hasValue(R.styleable.TextAppearance_fontVariationSettings)) {
            string = tintTypedArrayObtainStyledAttributes3.getString(R.styleable.TextAppearance_fontVariationSettings);
        }
        if (Build.VERSION.SDK_INT >= 28 && tintTypedArrayObtainStyledAttributes3.hasValue(R.styleable.TextAppearance_android_textSize) && tintTypedArrayObtainStyledAttributes3.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, -1) == 0) {
            this.mView.setTextSize(0, 0.0f);
        }
        updateTypefaceAndStyle(context, tintTypedArrayObtainStyledAttributes3);
        tintTypedArrayObtainStyledAttributes3.recycle();
        if (colorStateList2 != null) {
            this.mView.setTextColor(colorStateList2);
        }
        if (colorStateList != null) {
            this.mView.setHintTextColor(colorStateList);
        }
        if (colorStateList3 != null) {
            this.mView.setLinkTextColor(colorStateList3);
        }
        if (!z4 && z3) {
            a(z2);
        }
        if (this.mFontTypeface != null) {
            if (this.mFontWeight == -1) {
                this.mView.setTypeface(this.mFontTypeface, this.mStyle);
            } else {
                this.mView.setTypeface(this.mFontTypeface);
            }
        }
        if (string != null) {
            this.mView.setFontVariationSettings(string);
        }
        if (string2 != null) {
            if (Build.VERSION.SDK_INT >= 24) {
                this.mView.setTextLocales(LocaleList.forLanguageTags(string2));
            } else if (Build.VERSION.SDK_INT >= 21) {
                this.mView.setTextLocale(Locale.forLanguageTag(string2.substring(0, string2.indexOf(44))));
            }
        }
        this.mAutoSizeTextHelper.a(attributeSet, i);
        if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && this.mAutoSizeTextHelper.a() != 0) {
            int[] iArrE = this.mAutoSizeTextHelper.e();
            if (iArrE.length > 0) {
                if (this.mView.getAutoSizeStepGranularity() != -1.0f) {
                    this.mView.setAutoSizeTextTypeUniformWithConfiguration(this.mAutoSizeTextHelper.c(), this.mAutoSizeTextHelper.d(), this.mAutoSizeTextHelper.b(), 0);
                } else {
                    this.mView.setAutoSizeTextTypeUniformWithPresetSizes(iArrE, 0);
                }
            }
        }
        TintTypedArray tintTypedArrayObtainStyledAttributes4 = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.AppCompatTextView);
        int resourceId2 = tintTypedArrayObtainStyledAttributes4.getResourceId(R.styleable.AppCompatTextView_drawableLeftCompat, -1);
        Drawable drawable = resourceId2 != -1 ? appCompatDrawableManager.getDrawable(context, resourceId2) : null;
        int resourceId3 = tintTypedArrayObtainStyledAttributes4.getResourceId(R.styleable.AppCompatTextView_drawableTopCompat, -1);
        Drawable drawable2 = resourceId3 != -1 ? appCompatDrawableManager.getDrawable(context, resourceId3) : null;
        int resourceId4 = tintTypedArrayObtainStyledAttributes4.getResourceId(R.styleable.AppCompatTextView_drawableRightCompat, -1);
        Drawable drawable3 = resourceId4 != -1 ? appCompatDrawableManager.getDrawable(context, resourceId4) : null;
        int resourceId5 = tintTypedArrayObtainStyledAttributes4.getResourceId(R.styleable.AppCompatTextView_drawableBottomCompat, -1);
        Drawable drawable4 = resourceId5 != -1 ? appCompatDrawableManager.getDrawable(context, resourceId5) : null;
        int resourceId6 = tintTypedArrayObtainStyledAttributes4.getResourceId(R.styleable.AppCompatTextView_drawableStartCompat, -1);
        Drawable drawable5 = resourceId6 != -1 ? appCompatDrawableManager.getDrawable(context, resourceId6) : null;
        int resourceId7 = tintTypedArrayObtainStyledAttributes4.getResourceId(R.styleable.AppCompatTextView_drawableEndCompat, -1);
        setCompoundDrawables(drawable, drawable2, drawable3, drawable4, drawable5, resourceId7 != -1 ? appCompatDrawableManager.getDrawable(context, resourceId7) : null);
        if (tintTypedArrayObtainStyledAttributes4.hasValue(R.styleable.AppCompatTextView_drawableTint)) {
            TextViewCompat.setCompoundDrawableTintList(this.mView, tintTypedArrayObtainStyledAttributes4.getColorStateList(R.styleable.AppCompatTextView_drawableTint));
        }
        if (tintTypedArrayObtainStyledAttributes4.hasValue(R.styleable.AppCompatTextView_drawableTintMode)) {
            i2 = -1;
            TextViewCompat.setCompoundDrawableTintMode(this.mView, DrawableUtils.parseTintMode(tintTypedArrayObtainStyledAttributes4.getInt(R.styleable.AppCompatTextView_drawableTintMode, -1), null));
        } else {
            i2 = -1;
        }
        int dimensionPixelSize = tintTypedArrayObtainStyledAttributes4.getDimensionPixelSize(R.styleable.AppCompatTextView_firstBaselineToTopHeight, i2);
        int dimensionPixelSize2 = tintTypedArrayObtainStyledAttributes4.getDimensionPixelSize(R.styleable.AppCompatTextView_lastBaselineToBottomHeight, i2);
        int dimensionPixelSize3 = tintTypedArrayObtainStyledAttributes4.getDimensionPixelSize(R.styleable.AppCompatTextView_lineHeight, i2);
        tintTypedArrayObtainStyledAttributes4.recycle();
        if (dimensionPixelSize != i2) {
            TextViewCompat.setFirstBaselineToTopHeight(this.mView, dimensionPixelSize);
        }
        if (dimensionPixelSize2 != i2) {
            TextViewCompat.setLastBaselineToBottomHeight(this.mView, dimensionPixelSize2);
        }
        if (dimensionPixelSize3 != i2) {
            TextViewCompat.setLineHeight(this.mView, dimensionPixelSize3);
        }
    }

    void a(@NonNull TextView textView, @Nullable InputConnection inputConnection, @NonNull EditorInfo editorInfo) {
        if (Build.VERSION.SDK_INT >= 30 || inputConnection == null) {
            return;
        }
        EditorInfoCompat.setInitialSurroundingText(editorInfo, textView.getText());
    }

    void a(WeakReference<TextView> weakReference, final Typeface typeface) {
        if (this.mAsyncFontPending) {
            this.mFontTypeface = typeface;
            final TextView textView = weakReference.get();
            if (textView != null) {
                if (!ViewCompat.isAttachedToWindow(textView)) {
                    textView.setTypeface(typeface, this.mStyle);
                } else {
                    final int i = this.mStyle;
                    textView.post(new Runnable() { // from class: androidx.appcompat.widget.AppCompatTextHelper.2
                        @Override // java.lang.Runnable
                        public void run() {
                            textView.setTypeface(typeface, i);
                        }
                    });
                }
            }
        }
    }

    void a(boolean z) {
        this.mView.setAllCaps(z);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    void a(boolean z, int i, int i2, int i3, int i4) {
        if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) {
            return;
        }
        c();
    }

    void a(@NonNull int[] iArr, int i) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.a(iArr, i);
    }

    void b() {
        if (this.mDrawableLeftTint != null || this.mDrawableTopTint != null || this.mDrawableRightTint != null || this.mDrawableBottomTint != null) {
            Drawable[] compoundDrawables = this.mView.getCompoundDrawables();
            applyCompoundDrawableTint(compoundDrawables[0], this.mDrawableLeftTint);
            applyCompoundDrawableTint(compoundDrawables[1], this.mDrawableTopTint);
            applyCompoundDrawableTint(compoundDrawables[2], this.mDrawableRightTint);
            applyCompoundDrawableTint(compoundDrawables[3], this.mDrawableBottomTint);
        }
        if (Build.VERSION.SDK_INT >= 17) {
            if (this.mDrawableStartTint == null && this.mDrawableEndTint == null) {
                return;
            }
            Drawable[] compoundDrawablesRelative = this.mView.getCompoundDrawablesRelative();
            applyCompoundDrawableTint(compoundDrawablesRelative[0], this.mDrawableStartTint);
            applyCompoundDrawableTint(compoundDrawablesRelative[2], this.mDrawableEndTint);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    void c() {
        this.mAutoSizeTextHelper.f();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    boolean d() {
        return this.mAutoSizeTextHelper.g();
    }

    int e() {
        return this.mAutoSizeTextHelper.a();
    }

    int f() {
        return this.mAutoSizeTextHelper.b();
    }

    int g() {
        return this.mAutoSizeTextHelper.c();
    }

    int h() {
        return this.mAutoSizeTextHelper.d();
    }

    int[] i() {
        return this.mAutoSizeTextHelper.e();
    }

    @Nullable
    ColorStateList j() {
        if (this.mDrawableTint != null) {
            return this.mDrawableTint.mTintList;
        }
        return null;
    }

    @Nullable
    PorterDuff.Mode k() {
        if (this.mDrawableTint != null) {
            return this.mDrawableTint.mTintMode;
        }
        return null;
    }
}
