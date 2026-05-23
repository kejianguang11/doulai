package org.cocos2dx.lib;

import android.util.Log;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes.dex */
public class Cocos2dxReflectionHelper {
    public static <T> T getConstantValue(Class cls, String str) {
        String str2;
        StringBuilder sb;
        String str3;
        try {
            return (T) cls.getDeclaredField(str).get(null);
        } catch (IllegalAccessException unused) {
            str2 = "error";
            sb = new StringBuilder();
            sb.append(str);
            str = " is not accessable";
            sb.append(str);
            Log.e(str2, sb.toString());
            return null;
        } catch (IllegalArgumentException unused2) {
            str2 = "error";
            sb = new StringBuilder();
            str3 = "arguments error when get ";
            sb.append(str3);
            sb.append(str);
            Log.e(str2, sb.toString());
            return null;
        } catch (NoSuchFieldException unused3) {
            Log.e("error", "can not find " + str + " in " + cls.getName());
            return null;
        } catch (Exception unused4) {
            str2 = "error";
            sb = new StringBuilder();
            str3 = "can not get constant";
            sb.append(str3);
            sb.append(str);
            Log.e(str2, sb.toString());
            return null;
        }
    }

    public static <T> T invokeInstanceMethod(Object obj, String str, Class[] clsArr, Object[] objArr) {
        String str2;
        StringBuilder sb;
        String str3;
        Class<?> cls = obj.getClass();
        try {
            return (T) cls.getMethod(str, clsArr).invoke(obj, objArr);
        } catch (IllegalAccessException unused) {
            str2 = "error";
            sb = new StringBuilder();
            sb.append(str);
            str = " is not accessible";
            sb.append(str);
            Log.e(str2, sb.toString());
            return null;
        } catch (IllegalArgumentException unused2) {
            str2 = "error";
            sb = new StringBuilder();
            str3 = "arguments are error when invoking ";
            sb.append(str3);
            sb.append(str);
            Log.e(str2, sb.toString());
            return null;
        } catch (NoSuchMethodException unused3) {
            str2 = "error";
            sb = new StringBuilder();
            sb.append("can not find ");
            sb.append(str);
            sb.append(" in ");
            str = cls.getName();
            sb.append(str);
            Log.e(str2, sb.toString());
            return null;
        } catch (InvocationTargetException unused4) {
            str2 = "error";
            sb = new StringBuilder();
            str3 = "an exception was thrown by the invoked method when invoking ";
            sb.append(str3);
            sb.append(str);
            Log.e(str2, sb.toString());
            return null;
        }
    }
}
