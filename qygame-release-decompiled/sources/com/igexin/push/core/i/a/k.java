package com.igexin.push.core.i.a;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
public final class k {
    private k() {
    }

    @TargetApi(19)
    public static int a(Bitmap bitmap) {
        if (!bitmap.isRecycled()) {
            if (Build.VERSION.SDK_INT >= 19) {
                try {
                    return bitmap.getAllocationByteCount();
                } catch (NullPointerException unused) {
                }
            }
            return bitmap.getHeight() * bitmap.getRowBytes();
        }
        throw new IllegalStateException("Cannot obtain size for recycled Bitmap: " + bitmap + "[" + bitmap.getWidth() + "x" + bitmap.getHeight() + "] " + bitmap.getConfig());
    }

    public static <T> T a(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }

    public static void a(boolean z, String str) {
        if (!z) {
            throw new IllegalArgumentException(String.valueOf(str));
        }
    }
}
