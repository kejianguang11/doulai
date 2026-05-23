package com.igexin.push.g;

import android.os.Build;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public final class p {
    public static Class<?> a(String str) throws Throwable {
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                return (Class) Class.class.getDeclaredMethod("forName", String.class).invoke(null, str);
            } catch (Throwable unused) {
            }
        }
        return Class.forName(str);
    }

    private static Field a(Class<?> cls, String str) throws Throwable {
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                return (Field) Class.class.getDeclaredMethod("getDeclaredField", String.class).invoke(cls, str);
            } catch (Throwable unused) {
            }
        }
        return cls.getDeclaredField(str);
    }

    public static Method a(Class<?> cls, String str, Class<?>... clsArr) throws Throwable {
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                return (Method) Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class).invoke(cls, str, clsArr);
            } catch (Throwable unused) {
            }
        }
        return cls.getDeclaredMethod(str, clsArr);
    }

    private static Field b(Class<?> cls, String str) throws Throwable {
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                return (Field) Class.class.getDeclaredMethod("getField", String.class).invoke(cls, str);
            } catch (Throwable unused) {
            }
        }
        return cls.getDeclaredField(str);
    }
}
