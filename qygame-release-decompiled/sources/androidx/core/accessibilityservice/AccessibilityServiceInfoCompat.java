package androidx.core.accessibilityservice;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public final class AccessibilityServiceInfoCompat {
    public static final int CAPABILITY_CAN_FILTER_KEY_EVENTS = 8;
    public static final int CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 4;
    public static final int CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION = 2;
    public static final int CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT = 1;
    public static final int FEEDBACK_ALL_MASK = -1;
    public static final int FEEDBACK_BRAILLE = 32;
    public static final int FLAG_INCLUDE_NOT_IMPORTANT_VIEWS = 2;
    public static final int FLAG_REPORT_VIEW_IDS = 16;
    public static final int FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 8;
    public static final int FLAG_REQUEST_FILTER_KEY_EVENTS = 32;
    public static final int FLAG_REQUEST_TOUCH_EXPLORATION_MODE = 4;

    private AccessibilityServiceInfoCompat() {
    }

    @NonNull
    public static String capabilityToString(int i) {
        if (i == 4) {
            return "CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
        }
        if (i == 8) {
            return "CAPABILITY_CAN_FILTER_KEY_EVENTS";
        }
        switch (i) {
            case 1:
                return "CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT";
            case 2:
                return "CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION";
            default:
                return "UNKNOWN";
        }
    }

    @NonNull
    public static String feedbackTypeToString(int i) {
        StringBuilder sb = new StringBuilder();
        String str = "[";
        while (true) {
            sb.append(str);
            while (i > 0) {
                int iNumberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(i);
                i &= ~iNumberOfTrailingZeros;
                if (sb.length() > 1) {
                    sb.append(", ");
                }
                if (iNumberOfTrailingZeros == 4) {
                    str = "FEEDBACK_AUDIBLE";
                } else if (iNumberOfTrailingZeros == 8) {
                    str = "FEEDBACK_VISUAL";
                } else if (iNumberOfTrailingZeros != 16) {
                    switch (iNumberOfTrailingZeros) {
                        case 1:
                            str = "FEEDBACK_SPOKEN";
                            break;
                        case 2:
                            str = "FEEDBACK_HAPTIC";
                            break;
                    }
                } else {
                    str = "FEEDBACK_GENERIC";
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }

    @Nullable
    public static String flagToString(int i) {
        if (i == 4) {
            return "FLAG_REQUEST_TOUCH_EXPLORATION_MODE";
        }
        if (i == 8) {
            return "FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
        }
        if (i == 16) {
            return "FLAG_REPORT_VIEW_IDS";
        }
        if (i == 32) {
            return "FLAG_REQUEST_FILTER_KEY_EVENTS";
        }
        switch (i) {
            case 1:
                return "DEFAULT";
            case 2:
                return "FLAG_INCLUDE_NOT_IMPORTANT_VIEWS";
            default:
                return null;
        }
    }

    public static int getCapabilities(@NonNull AccessibilityServiceInfo accessibilityServiceInfo) {
        return Build.VERSION.SDK_INT >= 18 ? accessibilityServiceInfo.getCapabilities() : accessibilityServiceInfo.getCanRetrieveWindowContent() ? 1 : 0;
    }

    @Nullable
    public static String loadDescription(@NonNull AccessibilityServiceInfo accessibilityServiceInfo, @NonNull PackageManager packageManager) {
        return Build.VERSION.SDK_INT >= 16 ? accessibilityServiceInfo.loadDescription(packageManager) : accessibilityServiceInfo.getDescription();
    }
}
