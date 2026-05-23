package androidx.appcompat.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.ColorUtils;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY})
public class ThemeUtils {
    private static final String TAG = "ThemeUtils";
    private static final ThreadLocal<TypedValue> TL_TYPED_VALUE = new ThreadLocal<>();
    static final int[] a = {-16842910};
    static final int[] b = {R.attr.state_focused};
    static final int[] c = {R.attr.state_activated};
    static final int[] d = {R.attr.state_pressed};
    static final int[] e = {R.attr.state_checked};
    static final int[] f = {R.attr.state_selected};
    static final int[] g = {-16842919, -16842908};
    static final int[] h = new int[0];
    private static final int[] TEMP_ARRAY = new int[1];

    private ThemeUtils() {
    }

    static int a(@NonNull Context context, int i, float f2) {
        return ColorUtils.setAlphaComponent(getThemeAttrColor(context, i), Math.round(Color.alpha(r0) * f2));
    }

    public static void checkAppCompatTheme(@NonNull View view, @NonNull Context context) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(androidx.appcompat.R.styleable.AppCompatTheme);
        try {
            if (!typedArrayObtainStyledAttributes.hasValue(androidx.appcompat.R.styleable.AppCompatTheme_windowActionBar)) {
                Log.e(TAG, "View " + view.getClass() + " is an AppCompat widget that can only be used with a Theme.AppCompat theme (or descendant).");
            }
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    @NonNull
    public static ColorStateList createDisabledStateList(int i, int i2) {
        return new ColorStateList(new int[][]{a, h}, new int[]{i2, i});
    }

    public static int getDisabledThemeAttrColor(@NonNull Context context, int i) {
        ColorStateList themeAttrColorStateList = getThemeAttrColorStateList(context, i);
        if (themeAttrColorStateList != null && themeAttrColorStateList.isStateful()) {
            return themeAttrColorStateList.getColorForState(a, themeAttrColorStateList.getDefaultColor());
        }
        TypedValue typedValue = getTypedValue();
        context.getTheme().resolveAttribute(R.attr.disabledAlpha, typedValue, true);
        return a(context, i, typedValue.getFloat());
    }

    public static int getThemeAttrColor(@NonNull Context context, int i) {
        TEMP_ARRAY[0] = i;
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, (AttributeSet) null, TEMP_ARRAY);
        try {
            return tintTypedArrayObtainStyledAttributes.getColor(0, 0);
        } finally {
            tintTypedArrayObtainStyledAttributes.recycle();
        }
    }

    @Nullable
    public static ColorStateList getThemeAttrColorStateList(@NonNull Context context, int i) {
        TEMP_ARRAY[0] = i;
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, (AttributeSet) null, TEMP_ARRAY);
        try {
            return tintTypedArrayObtainStyledAttributes.getColorStateList(0);
        } finally {
            tintTypedArrayObtainStyledAttributes.recycle();
        }
    }

    private static TypedValue getTypedValue() {
        TypedValue typedValue = TL_TYPED_VALUE.get();
        if (typedValue != null) {
            return typedValue;
        }
        TypedValue typedValue2 = new TypedValue();
        TL_TYPED_VALUE.set(typedValue2);
        return typedValue2;
    }
}
