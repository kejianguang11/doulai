package androidx.appcompat.widget;

import android.R;
import android.annotation.SuppressLint;
import android.graphics.Insets;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.graphics.drawable.WrappedDrawable;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"RestrictedAPI"})
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class DrawableUtils {
    private static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    private static final int[] EMPTY_STATE_SET = new int[0];
    public static final Rect INSETS_NONE = new Rect();
    private static final String TAG = "DrawableUtils";
    private static final String VECTOR_DRAWABLE_CLAZZ_NAME = "android.graphics.drawable.VectorDrawable";
    private static Class<?> sInsetsClazz;

    static {
        if (Build.VERSION.SDK_INT >= 18) {
            try {
                sInsetsClazz = Class.forName("android.graphics.Insets");
            } catch (ClassNotFoundException unused) {
            }
        }
    }

    private DrawableUtils() {
    }

    static void a(@NonNull Drawable drawable) {
        if (Build.VERSION.SDK_INT == 21 && VECTOR_DRAWABLE_CLAZZ_NAME.equals(drawable.getClass().getName())) {
            fixVectorDrawableTinting(drawable);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean canSafelyMutateDrawable(@NonNull Drawable drawable) {
        Drawable drawable2;
        if (Build.VERSION.SDK_INT < 15 && (drawable instanceof InsetDrawable)) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 15 && (drawable instanceof GradientDrawable)) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 17 && (drawable instanceof LayerDrawable)) {
            return false;
        }
        if (!(drawable instanceof DrawableContainer)) {
            if (drawable instanceof WrappedDrawable) {
                drawable2 = ((WrappedDrawable) drawable).getWrappedDrawable();
            } else if (drawable instanceof DrawableWrapper) {
                drawable2 = ((DrawableWrapper) drawable).getWrappedDrawable();
            } else {
                if (!(drawable instanceof ScaleDrawable)) {
                    return true;
                }
                drawable2 = ((ScaleDrawable) drawable).getDrawable();
            }
            return canSafelyMutateDrawable(drawable2);
        }
        Drawable.ConstantState constantState = drawable.getConstantState();
        if (!(constantState instanceof DrawableContainer.DrawableContainerState)) {
            return true;
        }
        for (Drawable drawable3 : ((DrawableContainer.DrawableContainerState) constantState).getChildren()) {
            if (!canSafelyMutateDrawable(drawable3)) {
                return false;
            }
        }
        return true;
    }

    private static void fixVectorDrawableTinting(Drawable drawable) {
        int[] state = drawable.getState();
        drawable.setState((state == null || state.length == 0) ? CHECKED_STATE_SET : EMPTY_STATE_SET);
        drawable.setState(state);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0094  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Rect getOpticalBounds(Drawable drawable) {
        byte b;
        if (Build.VERSION.SDK_INT >= 29) {
            Insets opticalInsets = drawable.getOpticalInsets();
            Rect rect = new Rect();
            rect.left = opticalInsets.left;
            rect.right = opticalInsets.right;
            rect.top = opticalInsets.top;
            rect.bottom = opticalInsets.bottom;
            return rect;
        }
        if (sInsetsClazz != null) {
            try {
                Drawable drawableUnwrap = DrawableCompat.unwrap(drawable);
                Object objInvoke = drawableUnwrap.getClass().getMethod("getOpticalInsets", new Class[0]).invoke(drawableUnwrap, new Object[0]);
                if (objInvoke != null) {
                    Rect rect2 = new Rect();
                    for (Field field : sInsetsClazz.getFields()) {
                        String name = field.getName();
                        int iHashCode = name.hashCode();
                        if (iHashCode != -1383228885) {
                            if (iHashCode != 115029) {
                                if (iHashCode != 3317767) {
                                    b = (iHashCode == 108511772 && name.equals("right")) ? (byte) 2 : (byte) -1;
                                } else if (name.equals("left")) {
                                    b = 0;
                                }
                            } else if (name.equals("top")) {
                                b = 1;
                            }
                        } else if (name.equals("bottom")) {
                            b = 3;
                        }
                        switch (b) {
                            case 0:
                                rect2.left = field.getInt(objInvoke);
                                break;
                            case 1:
                                rect2.top = field.getInt(objInvoke);
                                break;
                            case 2:
                                rect2.right = field.getInt(objInvoke);
                                break;
                            case 3:
                                rect2.bottom = field.getInt(objInvoke);
                                break;
                        }
                    }
                    return rect2;
                }
            } catch (Exception unused) {
                Log.e(TAG, "Couldn't obtain the optical insets. Ignoring.");
            }
        }
        return INSETS_NONE;
    }

    public static PorterDuff.Mode parseTintMode(int i, PorterDuff.Mode mode) {
        if (i == 3) {
            return PorterDuff.Mode.SRC_OVER;
        }
        if (i == 5) {
            return PorterDuff.Mode.SRC_IN;
        }
        if (i == 9) {
            return PorterDuff.Mode.SRC_ATOP;
        }
        switch (i) {
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return mode;
        }
    }
}
