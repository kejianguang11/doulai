package androidx.core.util;

import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class DebugUtils {
    private DebugUtils() {
    }

    public static void buildShortClassTag(Object obj, StringBuilder sb) {
        int iLastIndexOf;
        String hexString;
        if (obj == null) {
            hexString = "null";
        } else {
            String simpleName = obj.getClass().getSimpleName();
            if ((simpleName == null || simpleName.length() <= 0) && (iLastIndexOf = (simpleName = obj.getClass().getName()).lastIndexOf(46)) > 0) {
                simpleName = simpleName.substring(iLastIndexOf + 1);
            }
            sb.append(simpleName);
            sb.append('{');
            hexString = Integer.toHexString(System.identityHashCode(obj));
        }
        sb.append(hexString);
    }
}
