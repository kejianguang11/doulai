package com.nirvana.tools.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

/* JADX INFO: loaded from: classes.dex */
public class SupportJarUtils {
    public static int checkSelfPermission(Context context, String str) {
        if (checkXSupportIsAvable()) {
            return checkSelfPermissionWithX(context, str);
        }
        return 10;
    }

    private static int checkSelfPermissionWithX(Context context, String str) {
        try {
            Object objInvoke = Class.forName("androidx.core.content.ContextCompat").getDeclaredMethod("checkSelfPermission", Context.class, String.class).invoke(null, context, str);
            if (objInvoke != null) {
                return Integer.parseInt(objInvoke.toString());
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static boolean checkXSupportIsAvable() {
        try {
            return Class.forName("androidx.core.content.ContextCompat") != null;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public static void startActivityForResult(Activity activity, Intent intent, int i, String str, String str2) {
        ActivityCompat.startActivityForResult(activity, intent, i, (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) ? null : ActivityOptionsCompat.makeCustomAnimation(activity, AppUtils.getAnimResID(activity, str), AppUtils.getAnimResID(activity, str2)).toBundle());
    }
}
