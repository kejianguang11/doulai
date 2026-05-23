package com.qygame.qzdlyx.mobile;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/* JADX INFO: loaded from: classes.dex */
public class AppUtils {
    public static int dp2px(Context context, float f) {
        try {
            return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
        } catch (Exception unused) {
            return (int) f;
        }
    }

    public static int getPhoneHeightPixels(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics.heightPixels;
    }

    public static int getPhoneWidthPixels(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics.widthPixels;
    }

    public static int px2dp(Context context, float f) {
        try {
            return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
        } catch (Exception unused) {
            return (int) f;
        }
    }
}
