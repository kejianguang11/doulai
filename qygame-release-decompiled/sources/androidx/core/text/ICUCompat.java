package androidx.core.text;

import android.icu.util.ULocale;
import android.os.Build;
import android.util.Log;
import androidx.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class ICUCompat {
    private static final String TAG = "ICUCompat";
    private static Method sAddLikelySubtagsMethod;
    private static Method sGetScriptMethod;

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            if (Build.VERSION.SDK_INT < 24) {
                try {
                    sAddLikelySubtagsMethod = Class.forName("libcore.icu.ICU").getMethod("addLikelySubtags", Locale.class);
                    return;
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
            return;
        }
        try {
            Class<?> cls = Class.forName("libcore.icu.ICU");
            if (cls != null) {
                sGetScriptMethod = cls.getMethod("getScript", String.class);
                sAddLikelySubtagsMethod = cls.getMethod("addLikelySubtags", String.class);
            }
        } catch (Exception e2) {
            sGetScriptMethod = null;
            sAddLikelySubtagsMethod = null;
            Log.w(TAG, e2);
        }
    }

    private ICUCompat() {
    }

    private static String addLikelySubtags(Locale locale) {
        String string = locale.toString();
        try {
            if (sAddLikelySubtagsMethod != null) {
                return (String) sAddLikelySubtagsMethod.invoke(null, string);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            Log.w(TAG, e);
        }
        return string;
    }

    private static String getScript(String str) {
        try {
            if (sGetScriptMethod != null) {
                return (String) sGetScriptMethod.invoke(null, str);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            Log.w(TAG, e);
        }
        return null;
    }

    @Nullable
    public static String maximizeAndGetScript(Locale locale) {
        if (Build.VERSION.SDK_INT >= 24) {
            return ULocale.addLikelySubtags(ULocale.forLocale(locale)).getScript();
        }
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                return ((Locale) sAddLikelySubtagsMethod.invoke(null, locale)).getScript();
            } catch (IllegalAccessException | InvocationTargetException e) {
                Log.w(TAG, e);
                return locale.getScript();
            }
        }
        String strAddLikelySubtags = addLikelySubtags(locale);
        if (strAddLikelySubtags != null) {
            return getScript(strAddLikelySubtags);
        }
        return null;
    }
}
