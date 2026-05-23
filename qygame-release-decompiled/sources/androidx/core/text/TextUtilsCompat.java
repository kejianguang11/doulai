package androidx.core.text;

import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class TextUtilsCompat {
    private static final String ARAB_SCRIPT_SUBTAG = "Arab";
    private static final String HEBR_SCRIPT_SUBTAG = "Hebr";
    private static final Locale ROOT = new Locale("", "");

    private TextUtilsCompat() {
    }

    private static int getLayoutDirectionFromFirstChar(@NonNull Locale locale) {
        switch (Character.getDirectionality(locale.getDisplayName(locale).charAt(0))) {
            case 1:
            case 2:
                return 1;
            default:
                return 0;
        }
    }

    public static int getLayoutDirectionFromLocale(@Nullable Locale locale) {
        if (Build.VERSION.SDK_INT >= 17) {
            return TextUtils.getLayoutDirectionFromLocale(locale);
        }
        if (locale == null || locale.equals(ROOT)) {
            return 0;
        }
        String strMaximizeAndGetScript = ICUCompat.maximizeAndGetScript(locale);
        return strMaximizeAndGetScript == null ? getLayoutDirectionFromFirstChar(locale) : (strMaximizeAndGetScript.equalsIgnoreCase(ARAB_SCRIPT_SUBTAG) || strMaximizeAndGetScript.equalsIgnoreCase(HEBR_SCRIPT_SUBTAG)) ? 1 : 0;
    }

    @NonNull
    public static String htmlEncode(@NonNull String str) {
        String str2;
        if (Build.VERSION.SDK_INT >= 17) {
            return TextUtils.htmlEncode(str);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\"') {
                str2 = "&quot;";
            } else if (cCharAt == '<') {
                str2 = "&lt;";
            } else if (cCharAt != '>') {
                switch (cCharAt) {
                    case '&':
                        str2 = "&amp;";
                        break;
                    case '\'':
                        str2 = "&#39;";
                        break;
                    default:
                        sb.append(cCharAt);
                        continue;
                        break;
                }
            } else {
                str2 = "&gt;";
            }
            sb.append(str2);
        }
        return sb.toString();
    }
}
