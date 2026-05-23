package com.igexin.push.g;

import android.R;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Pair;
import com.igexin.assist.util.AssistUtils;
import com.mobile.auth.gatewayauth.Constant;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class d {
    static long a = 0;
    static HashMap<String, Object> b = new HashMap<>();
    private static final String c = "ro.miui.ui.version.name";
    private static final String d = "ro.miui.ui.version.code";
    private static final String e = "GT";
    private static volatile Boolean f;
    private static String g;
    private static PackageInfo h;

    /* JADX WARN: Removed duplicated region for block: B:160:0x02bb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Intent a(String str) throws URISyntaxException {
        int iLastIndexOf;
        boolean zStartsWith;
        String strSubstring;
        StringBuilder sb;
        String str2;
        int i;
        try {
            zStartsWith = str.startsWith("android-app:");
            iLastIndexOf = str.lastIndexOf("#");
        } catch (IndexOutOfBoundsException unused) {
            iLastIndexOf = 0;
        }
        try {
            if (iLastIndexOf == -1) {
                if (!zStartsWith) {
                    return new Intent("android.intent.action.VIEW", Uri.parse(str));
                }
            } else if (!str.startsWith("#Intent;", iLastIndexOf)) {
                if (!zStartsWith) {
                    return d(str);
                }
                iLastIndexOf = -1;
            }
            Intent intent = new Intent("android.intent.action.VIEW");
            if (iLastIndexOf >= 0) {
                strSubstring = str.substring(0, iLastIndexOf);
                iLastIndexOf += 8;
            } else {
                strSubstring = str;
            }
            if (intent.getExtras() == null) {
                intent.putExtras(new Bundle());
            }
            Bundle extras = intent.getExtras();
            String strSubstring2 = null;
            boolean z = false;
            boolean z2 = false;
            Intent intent2 = intent;
            String strSubstring3 = null;
            while (iLastIndexOf >= 0 && !str.startsWith("end", iLastIndexOf)) {
                int iIndexOf = str.indexOf(61, iLastIndexOf);
                if (iIndexOf < 0) {
                    iIndexOf = iLastIndexOf - 1;
                }
                int iIndexOf2 = str.indexOf(59, iLastIndexOf);
                String strDecode = iIndexOf < iIndexOf2 ? Uri.decode(str.substring(iIndexOf + 1, iIndexOf2)) : "";
                if (str.startsWith("action=", iLastIndexOf)) {
                    intent2.setAction(strDecode);
                    if (!z) {
                        z2 = true;
                    }
                } else if (str.startsWith("category=", iLastIndexOf)) {
                    intent2.addCategory(strDecode);
                } else if (str.startsWith("type=", iLastIndexOf)) {
                    intent2.setType(strDecode);
                } else if (str.startsWith("launchFlags=", iLastIndexOf)) {
                    intent2.setFlags(Integer.decode(strDecode).intValue());
                    int i2 = Build.VERSION.SDK_INT >= 19 ? 67 : 3;
                    if (Build.VERSION.SDK_INT >= 21) {
                        i2 |= 128;
                    }
                    intent2.setFlags((~i2) & intent2.getFlags());
                } else if (str.startsWith("package=", iLastIndexOf)) {
                    intent2.setPackage(strDecode);
                } else if (str.startsWith("component=", iLastIndexOf)) {
                    intent2.setComponent(ComponentName.unflattenFromString(strDecode));
                } else if (str.startsWith("scheme=", iLastIndexOf)) {
                    if (z) {
                        intent2.setData(Uri.parse(strDecode + ":"));
                    } else {
                        strSubstring3 = strDecode;
                    }
                } else if (str.startsWith("sourceBounds=", iLastIndexOf)) {
                    intent2.setSourceBounds(Rect.unflattenFromString(strDecode));
                } else if (iIndexOf2 == iLastIndexOf + 3 && str.startsWith("SEL", iLastIndexOf)) {
                    intent2 = new Intent();
                    z = true;
                } else {
                    String strDecode2 = Uri.decode(str.substring(iLastIndexOf + 2, iIndexOf));
                    if (str.startsWith("S.", iLastIndexOf)) {
                        extras.putString(strDecode2, strDecode);
                    } else if (str.startsWith("B.", iLastIndexOf)) {
                        extras.putBoolean(strDecode2, Boolean.parseBoolean(strDecode));
                    } else if (str.startsWith("b.", iLastIndexOf)) {
                        extras.putByte(strDecode2, Byte.parseByte(strDecode));
                    } else if (str.startsWith("c.", iLastIndexOf)) {
                        extras.putChar(strDecode2, strDecode.charAt(0));
                    } else if (str.startsWith("d.", iLastIndexOf)) {
                        extras.putDouble(strDecode2, Double.parseDouble(strDecode));
                    } else if (str.startsWith("f.", iLastIndexOf)) {
                        extras.putFloat(strDecode2, Float.parseFloat(strDecode));
                    } else if (str.startsWith("i.", iLastIndexOf)) {
                        extras.putInt(strDecode2, Integer.parseInt(strDecode));
                    } else if (str.startsWith("l.", iLastIndexOf)) {
                        extras.putLong(strDecode2, Long.parseLong(strDecode));
                    } else {
                        if (!str.startsWith("s.", iLastIndexOf)) {
                            throw new URISyntaxException(str, "unknown EXTRA type", iLastIndexOf);
                        }
                        extras.putShort(strDecode2, Short.parseShort(strDecode));
                    }
                }
                iLastIndexOf = iIndexOf2 + 1;
            }
            intent2.putExtras(extras);
            if (!z) {
                intent = intent2;
            } else if (intent.getPackage() == null && Build.VERSION.SDK_INT >= 15) {
                intent.setSelector(intent2);
            }
            if (strSubstring != null) {
                if (strSubstring.startsWith("intent:")) {
                    strSubstring = strSubstring.substring(7);
                    if (strSubstring3 != null) {
                        sb = new StringBuilder();
                        sb.append(strSubstring3);
                        sb.append(':');
                        sb.append(strSubstring);
                        strSubstring = sb.toString();
                    }
                    if (strSubstring.length() > 0) {
                        try {
                            intent.setData(Uri.parse(strSubstring));
                        } catch (IllegalArgumentException e2) {
                            throw new URISyntaxException(str, e2.getMessage());
                        }
                    }
                } else {
                    if (strSubstring.startsWith("android-app:")) {
                        if (strSubstring.charAt(12) == '/' && strSubstring.charAt(13) == '/') {
                            int iIndexOf3 = strSubstring.indexOf(47, 14);
                            if (iIndexOf3 < 0) {
                                intent.setPackage(strSubstring.substring(14));
                                if (!z2) {
                                    str2 = "android.intent.action.MAIN";
                                    intent.setAction(str2);
                                }
                            } else {
                                intent.setPackage(strSubstring.substring(14, iIndexOf3));
                                int i3 = iIndexOf3 + 1;
                                if (i3 < strSubstring.length()) {
                                    int iIndexOf4 = strSubstring.indexOf(47, i3);
                                    if (iIndexOf4 >= 0) {
                                        strSubstring3 = strSubstring.substring(i3, iIndexOf4);
                                        if (iIndexOf4 >= strSubstring.length() || (iIndexOf3 = strSubstring.indexOf(47, (i = iIndexOf4 + 1))) < 0) {
                                            iIndexOf3 = iIndexOf4;
                                        } else {
                                            strSubstring2 = strSubstring.substring(i, iIndexOf3);
                                        }
                                    } else {
                                        strSubstring3 = strSubstring.substring(i3);
                                    }
                                }
                                if (strSubstring3 != null) {
                                    if (strSubstring2 == null) {
                                        sb = new StringBuilder();
                                        sb.append(strSubstring3);
                                        strSubstring = ":";
                                    } else {
                                        sb = new StringBuilder();
                                        sb.append(strSubstring3);
                                        sb.append("://");
                                        sb.append(strSubstring2);
                                        strSubstring = strSubstring.substring(iIndexOf3);
                                    }
                                    sb.append(strSubstring);
                                    strSubstring = sb.toString();
                                } else if (!z2) {
                                    str2 = "android.intent.action.MAIN";
                                    intent.setAction(str2);
                                }
                            }
                        }
                        strSubstring = "";
                    }
                    if (strSubstring.length() > 0) {
                    }
                }
            }
            return intent;
        } catch (IndexOutOfBoundsException unused2) {
            throw new URISyntaxException(str, "illegal Intent URI format", iLastIndexOf);
        }
    }

    public static Pair<ServiceInfo, Class> a(Context context, Class cls) {
        try {
            if (h == null) {
                h = context.getPackageManager().getPackageInfo(context.getPackageName(), Build.VERSION.SDK_INT >= 24 ? 516 : 4);
            }
            ServiceInfo[] serviceInfoArr = h.services;
            if (serviceInfoArr != null && serviceInfoArr.length > 0) {
                for (ServiceInfo serviceInfo : serviceInfoArr) {
                    try {
                        Class<?> cls2 = Class.forName(serviceInfo.name);
                        if (cls2 != cls && cls.isAssignableFrom(cls2)) {
                            com.igexin.c.a.c.a.b("GT", cls.getSimpleName() + " child is " + cls2.getSimpleName());
                            return Pair.create(serviceInfo, cls2);
                        }
                    } catch (Throwable th) {
                        com.igexin.c.a.c.a.a(th);
                    }
                }
            }
        } catch (Throwable th2) {
            com.igexin.c.a.c.a.a(th2);
            com.igexin.c.a.c.a.c.a().a(" findGtImplClassInManifest error = " + th2.toString());
        }
        return Pair.create(null, null);
    }

    private static String a(Context context) {
        try {
            Intent launchIntentForPackage = context.getApplicationContext().getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            return (launchIntentForPackage == null || launchIntentForPackage.getComponent() == null) ? "" : launchIntentForPackage.getComponent().getClassName();
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            return "";
        }
    }

    public static String a(ApplicationInfo applicationInfo) {
        try {
            String string = applicationInfo.metaData.getString(com.igexin.push.core.b.a);
            if (TextUtils.isEmpty(string)) {
                string = applicationInfo.packageName;
            }
            Class<?> cls = Class.forName(string + ".BuildConfig");
            return (String) cls.getField("GETUI_APPID").get(cls);
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a("get cf error|" + e2.toString(), new Object[0]);
            return "";
        }
    }

    public static HashMap<String, Object> a() {
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - a < 2000) {
                return b;
            }
            b.put("isPause", Boolean.FALSE);
            b.put("isTranslucent", Boolean.FALSE);
            a = jCurrentTimeMillis;
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Activity activity = null;
            Object objInvoke = cls.getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
            Field declaredField = cls.getDeclaredField("mActivities");
            declaredField.setAccessible(true);
            Map map = Build.VERSION.SDK_INT < 19 ? (HashMap) declaredField.get(objInvoke) : (ArrayMap) declaredField.get(objInvoke);
            if (map.size() <= 0) {
                return b;
            }
            Boolean boolValueOf = null;
            for (Object obj : map.values()) {
                Class<?> cls2 = obj.getClass();
                Field declaredField2 = cls2.getDeclaredField("activity");
                declaredField2.setAccessible(true);
                Activity activity2 = (Activity) declaredField2.get(obj);
                Field declaredField3 = cls2.getDeclaredField("paused");
                declaredField3.setAccessible(true);
                boolean z = declaredField3.getBoolean(obj);
                boolValueOf = Boolean.valueOf(boolValueOf == null ? z : boolValueOf.booleanValue() && z);
                if (!z) {
                    activity = activity2;
                }
            }
            boolean z2 = activity != null ? activity.getTheme().obtainStyledAttributes(new int[]{R.attr.windowIsTranslucent}).getBoolean(0, false) : false;
            b.put("isPause", Boolean.valueOf(Boolean.TRUE.equals(boolValueOf)));
            b.put("isTranslucent", Boolean.valueOf(z2));
            return b;
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            return b;
        }
    }

    public static synchronized boolean a(int i, boolean z) {
        try {
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
        if (com.igexin.push.core.e.l == null) {
            return false;
        }
        String str = com.igexin.push.core.e.G;
        if (AssistUtils.BRAND_HW.equalsIgnoreCase(str) || AssistUtils.BRAND_HON.equalsIgnoreCase(str)) {
            int iIntValue = ((Integer) q.b(com.igexin.push.core.e.l, q.h, 0)).intValue();
            if (!z) {
                i += iIntValue;
            }
            q.a(com.igexin.push.core.e.l, q.h, Integer.valueOf(i));
            Bundle bundle = new Bundle();
            bundle.putString("package", com.igexin.push.core.e.g);
            bundle.putString("class", a(com.igexin.push.core.e.l));
            bundle.putInt("badgenumber", i);
            Uri uri = Uri.parse("content://com.huawei.android.launcher.settings/badge/");
            Uri uri2 = Uri.parse("content://com.hihonor.android.launcher.settings/badge/");
            if (TextUtils.isEmpty(com.igexin.push.core.e.l.getContentResolver().getType(uri))) {
                uri = uri2;
            }
            com.igexin.push.core.e.l.getContentResolver().call(uri, "change_badge", (String) null, bundle);
            return true;
        }
        return false;
    }

    public static boolean a(String... strArr) {
        for (int i = 0; i < 5; i++) {
            if (TextUtils.isEmpty(strArr[i])) {
                return true;
            }
        }
        return false;
    }

    private static boolean b() {
        try {
            if (f != null) {
                return f.booleanValue();
            }
            Boolean boolValueOf = Boolean.valueOf((!"Xiaomi".equalsIgnoreCase(com.igexin.push.core.e.G) && TextUtils.isEmpty(c(c)) && TextUtils.isEmpty(c(d))) ? false : true);
            f = boolValueOf;
            return boolValueOf.booleanValue();
        } catch (Throwable unused) {
            return false;
        }
    }

    public static boolean b(int i, boolean z) {
        try {
            if (com.igexin.push.core.e.l == null || !AssistUtils.BRAND_VIVO.equalsIgnoreCase(com.igexin.push.core.e.G)) {
                return false;
            }
            Intent intent = new Intent();
            intent.setAction("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
            intent.putExtra("packageName", com.igexin.push.core.e.l.getPackageName());
            Intent launchIntentForPackage = com.igexin.push.core.e.l.getPackageManager().getLaunchIntentForPackage(com.igexin.push.core.e.l.getPackageName());
            if (launchIntentForPackage == null || launchIntentForPackage.getComponent() == null) {
                return false;
            }
            int iIntValue = ((Integer) q.b(com.igexin.push.core.e.l, q.i, 0)).intValue();
            if (!z) {
                i += iIntValue;
            }
            q.a(com.igexin.push.core.e.l, q.i, Integer.valueOf(i));
            intent.putExtra("className", launchIntentForPackage.getComponent().getClassName());
            intent.putExtra("notificationNum", i);
            intent.addFlags(16777216);
            com.igexin.push.core.e.l.sendBroadcast(intent);
            return true;
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            return false;
        }
    }

    public static boolean b(String str) {
        try {
            if (TextUtils.isEmpty(g)) {
                g = c();
                com.igexin.c.a.c.a.b("GT", " gtcVersion = " + g);
            }
            String[] strArrSplit = g.split("\\.");
            String[] strArrSplit2 = str.split("\\.");
            if (strArrSplit.length == 4 && strArrSplit2.length == 4) {
                for (int i = 0; i < 3; i++) {
                    int i2 = Integer.parseInt(strArrSplit2[i]);
                    int i3 = Integer.parseInt(strArrSplit[i]);
                    if (i3 != i2) {
                        return i3 < i2;
                    }
                }
            }
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
        return false;
    }

    private static String c() {
        try {
            Field declaredField = Class.forName("com.getui.gtc.BuildConfig").getDeclaredField("VERSION_NAME");
            declaredField.setAccessible(true);
            return ((String) declaredField.get(null)).substring(4);
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            return "";
        }
    }

    private static String c(String str) throws Throwable {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("getprop ".concat(String.valueOf(str))).getInputStream()), 1024);
            try {
                String line = bufferedReader.readLine();
                try {
                    bufferedReader.close();
                } catch (IOException e2) {
                    com.igexin.c.a.c.a.a(e2);
                }
                return line;
            } catch (Exception unused) {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e3) {
                        com.igexin.c.a.c.a.a(e3);
                    }
                }
                return null;
            } catch (Throwable th) {
                th = th;
                bufferedReader2 = bufferedReader;
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (IOException e4) {
                        com.igexin.c.a.c.a.a(e4);
                    }
                }
                throw th;
            }
        } catch (Exception unused2) {
            bufferedReader = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static boolean c(int i, boolean z) {
        try {
            if (com.igexin.push.core.e.l == null || !AssistUtils.BRAND_OPPO.equalsIgnoreCase(com.igexin.push.core.e.G)) {
                return false;
            }
            int iIntValue = ((Integer) q.b(com.igexin.push.core.e.l, q.j, 0)).intValue();
            if (!z) {
                i += iIntValue;
            }
            q.a(com.igexin.push.core.e.l, q.j, Integer.valueOf(i));
            Intent intent = new Intent("com.oppo.unsettledevent");
            intent.putExtra("packageName", com.igexin.push.core.e.l.getPackageName());
            intent.putExtra(Constant.LOGIN_ACTIVITY_NUMBER, i);
            intent.putExtra("upgradeNumber", i);
            List<ResolveInfo> listQueryBroadcastReceivers = com.igexin.push.core.e.l.getPackageManager().queryBroadcastReceivers(intent, 0);
            if (listQueryBroadcastReceivers != null && listQueryBroadcastReceivers.size() > 0) {
                com.igexin.push.core.e.l.sendBroadcast(intent);
                return true;
            }
            Bundle bundle = new Bundle();
            bundle.putInt("app_badge_count", i);
            com.igexin.push.core.e.l.getContentResolver().call(Uri.parse("content://com.android.badge/badge"), "setAppBadgeCount", (String) null, bundle);
            return true;
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
            return false;
        }
    }

    private static Intent d(String str) throws URISyntaxException {
        boolean z;
        String strSubstring = str;
        int iLastIndexOf = strSubstring.lastIndexOf(35);
        if (iLastIndexOf < 0) {
            return new Intent("android.intent.action.VIEW", Uri.parse(str));
        }
        String str2 = null;
        int i = iLastIndexOf + 1;
        if (strSubstring.regionMatches(i, "action(", 0, 7)) {
            int i2 = i + 7;
            int iIndexOf = strSubstring.indexOf(41, i2);
            String strSubstring2 = strSubstring.substring(i2, iIndexOf);
            z = true;
            i = iIndexOf + 1;
            str2 = strSubstring2;
        } else {
            z = false;
        }
        Intent intent = new Intent(str2);
        if (strSubstring.regionMatches(i, "categories(", 0, 11)) {
            int i3 = i + 11;
            int iIndexOf2 = strSubstring.indexOf(41, i3);
            while (i3 < iIndexOf2) {
                int iIndexOf3 = strSubstring.indexOf(33, i3);
                if (iIndexOf3 < 0 || iIndexOf3 > iIndexOf2) {
                    iIndexOf3 = iIndexOf2;
                }
                if (i3 < iIndexOf3) {
                    intent.addCategory(strSubstring.substring(i3, iIndexOf3));
                }
                i3 = iIndexOf3 + 1;
            }
            i = iIndexOf2 + 1;
            z = true;
        }
        if (strSubstring.regionMatches(i, "type(", 0, 5)) {
            int i4 = i + 5;
            int iIndexOf4 = strSubstring.indexOf(41, i4);
            intent.setType(strSubstring.substring(i4, iIndexOf4));
            i = iIndexOf4 + 1;
            z = true;
        }
        if (strSubstring.regionMatches(i, "launchFlags(", 0, 12)) {
            int i5 = i + 12;
            int iIndexOf5 = strSubstring.indexOf(41, i5);
            intent.setFlags(Integer.decode(strSubstring.substring(i5, iIndexOf5)).intValue());
            int i6 = Build.VERSION.SDK_INT >= 19 ? 67 : 3;
            if (Build.VERSION.SDK_INT >= 21) {
                i6 |= 128;
            }
            intent.setFlags((~i6) & intent.getFlags());
            i = iIndexOf5 + 1;
            z = true;
        }
        if (strSubstring.regionMatches(i, "component(", 0, 10)) {
            int i7 = i + 10;
            int iIndexOf6 = strSubstring.indexOf(41, i7);
            int iIndexOf7 = strSubstring.indexOf(33, i7);
            if (iIndexOf7 >= 0 && iIndexOf7 < iIndexOf6) {
                intent.setComponent(new ComponentName(strSubstring.substring(i7, iIndexOf7), strSubstring.substring(iIndexOf7 + 1, iIndexOf6)));
            }
            i = iIndexOf6 + 1;
            z = true;
        }
        if (strSubstring.regionMatches(i, "extras(", 0, 7)) {
            int i8 = i + 7;
            int iIndexOf8 = strSubstring.indexOf(41, i8);
            if (iIndexOf8 == -1) {
                throw new URISyntaxException(strSubstring, "EXTRA missing trailing ')'", i8);
            }
            if (intent.getExtras() == null) {
                intent.putExtras(new Bundle());
            }
            Bundle extras = intent.getExtras();
            while (i8 < iIndexOf8) {
                int iIndexOf9 = strSubstring.indexOf(61, i8);
                int i9 = i8 + 1;
                if (iIndexOf9 <= i9 || i8 >= iIndexOf8) {
                    throw new URISyntaxException(strSubstring, "EXTRA missing '='", i8);
                }
                char cCharAt = strSubstring.charAt(i8);
                String strSubstring3 = strSubstring.substring(i9, iIndexOf9);
                int i10 = iIndexOf9 + 1;
                int iIndexOf10 = strSubstring.indexOf(33, i10);
                if (iIndexOf10 == -1 || iIndexOf10 >= iIndexOf8) {
                    iIndexOf10 = iIndexOf8;
                }
                if (i10 >= iIndexOf10) {
                    throw new URISyntaxException(strSubstring, "EXTRA missing '!'", i10);
                }
                String strSubstring4 = strSubstring.substring(i10, iIndexOf10);
                if (cCharAt == 'B') {
                    extras.putBoolean(strSubstring3, Boolean.parseBoolean(strSubstring4));
                } else if (cCharAt == 'S') {
                    extras.putString(strSubstring3, Uri.decode(strSubstring4));
                } else if (cCharAt == 'f') {
                    extras.putFloat(strSubstring3, Float.parseFloat(strSubstring4));
                } else if (cCharAt == 'i') {
                    extras.putInt(strSubstring3, Integer.parseInt(strSubstring4));
                } else if (cCharAt == 'l') {
                    extras.putLong(strSubstring3, Long.parseLong(strSubstring4));
                } else {
                    if (cCharAt != 's') {
                        switch (cCharAt) {
                            case 'b':
                                extras.putByte(strSubstring3, Byte.parseByte(strSubstring4));
                                break;
                            case 'c':
                                extras.putChar(strSubstring3, Uri.decode(strSubstring4).charAt(0));
                                break;
                            case 'd':
                                try {
                                    extras.putDouble(strSubstring3, Double.parseDouble(strSubstring4));
                                } catch (NumberFormatException unused) {
                                    throw new URISyntaxException(strSubstring, "EXTRA value can't be parsed", iIndexOf10);
                                }
                                break;
                            default:
                                throw new URISyntaxException(strSubstring, "EXTRA has unknown type", iIndexOf10);
                        }
                        throw new URISyntaxException(strSubstring, "EXTRA value can't be parsed", iIndexOf10);
                    }
                    extras.putShort(strSubstring3, Short.parseShort(strSubstring4));
                }
                char cCharAt2 = strSubstring.charAt(iIndexOf10);
                if (cCharAt2 == ')') {
                    intent.putExtras(extras);
                    z = true;
                } else {
                    if (cCharAt2 != '!') {
                        throw new URISyntaxException(strSubstring, "EXTRA missing '!'", iIndexOf10);
                    }
                    i8 = iIndexOf10 + 1;
                }
            }
            intent.putExtras(extras);
            z = true;
        }
        if (z) {
            strSubstring = strSubstring.substring(0, iLastIndexOf);
        }
        intent.setData(Uri.parse(strSubstring));
        if (intent.getAction() != null) {
            return intent;
        }
        intent.setAction("android.intent.action.VIEW");
        return intent;
    }
}
