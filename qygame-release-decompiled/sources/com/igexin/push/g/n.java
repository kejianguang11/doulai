package com.igexin.push.g;

import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public final class n {
    public static final int a = 5;

    private static Method a(Class<?> cls, String str, Class<?>... clsArr) throws Exception {
        Method method;
        while (true) {
            Method[] declaredMethods = cls.getDeclaredMethods();
            if (str == null) {
                throw new NullPointerException("Method name must not be null.");
            }
            int length = declaredMethods.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    method = null;
                    break;
                }
                method = declaredMethods[i];
                if (method.getName().equals(str) && a(method.getParameterTypes(), clsArr)) {
                    break;
                }
                i++;
            }
            if (method != null) {
                method.setAccessible(true);
                return method;
            }
            if (cls.getSuperclass() == null) {
                throw new NoSuchMethodException();
            }
            cls = cls.getSuperclass();
        }
    }

    private static Method a(Method[] methodArr, String str, Class<?>[] clsArr) {
        if (str == null) {
            throw new NullPointerException("Method name must not be null.");
        }
        for (Method method : methodArr) {
            if (method.getName().equals(str) && a(method.getParameterTypes(), clsArr)) {
                return method;
            }
        }
        return null;
    }

    private static boolean a(Class<?>[] clsArr, Class<?>[] clsArr2) {
        if (clsArr == null) {
            return clsArr2 == null || clsArr2.length == 0;
        }
        if (clsArr2 == null) {
            return clsArr.length == 0;
        }
        if (clsArr.length != clsArr2.length) {
            return false;
        }
        for (int i = 0; i < clsArr.length; i++) {
            if (clsArr2[i] != null && !clsArr[i].isAssignableFrom(clsArr2[i])) {
                return false;
            }
        }
        return true;
    }
}
