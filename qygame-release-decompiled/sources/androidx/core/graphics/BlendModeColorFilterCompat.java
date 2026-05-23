package androidx.core.graphics;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class BlendModeColorFilterCompat {
    private BlendModeColorFilterCompat() {
    }

    @Nullable
    public static ColorFilter createBlendModeColorFilterCompat(int i, @NonNull BlendModeCompat blendModeCompat) {
        if (Build.VERSION.SDK_INT >= 29) {
            BlendMode blendModeA = BlendModeUtils.a(blendModeCompat);
            if (blendModeA != null) {
                return new BlendModeColorFilter(i, blendModeA);
            }
            return null;
        }
        PorterDuff.Mode modeB = BlendModeUtils.b(blendModeCompat);
        if (modeB != null) {
            return new PorterDuffColorFilter(i, modeB);
        }
        return null;
    }
}
