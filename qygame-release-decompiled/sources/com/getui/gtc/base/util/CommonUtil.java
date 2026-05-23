package com.getui.gtc.base.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.os.Process;
import android.text.TextUtils;
import android.util.ArrayMap;
import com.getui.gtc.base.GtcProvider;
import com.getui.gtc.base.ProcessSwitchContract;
import com.getui.gtc.base.publish.Broker;
import com.getui.gtc.base.publish.Subscriber;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class CommonUtil {
    private static File externalFilesDir;
    private static Boolean isAppDebug;

    static class CommonUtilSubscriber implements Subscriber {
        private static final CommonUtilSubscriber INSTANCE = new CommonUtilSubscriber();
        private static String getInstanceMethodName;
        private ApplicationInfo applicationInfo;
        private PackageInfo packageInfo;

        private CommonUtilSubscriber() {
        }

        private Bundle createBundle() {
            Bundle bundle = new Bundle();
            bundle.putString(ProcessSwitchContract.CLASS_NAME, getClass().getName());
            bundle.putString(ProcessSwitchContract.GET_INSTANCE, getInstanceMethodName);
            return bundle;
        }

        public static CommonUtilSubscriber getInstance() {
            getInstanceMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();
            return INSTANCE;
        }

        ApplicationInfo getAppInfoForSelf(Context context) {
            try {
                if (!CommonUtil.isGtcProcess()) {
                    GtcProvider.setContext(context);
                    Bundle bundleCreateBundle = createBundle();
                    bundleCreateBundle.putString(ProcessSwitchContract.METHOD_NAME, "base-1-3-1");
                    return (ApplicationInfo) Broker.getInstance().subscribe(bundleCreateBundle).getParcelable(ProcessSwitchContract.METHOD_RETURN);
                }
                if (this.applicationInfo == null) {
                    if (context == null) {
                        context = CommonUtil.findAppContext();
                    }
                    this.applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
                }
                return this.applicationInfo;
            } catch (Throwable th) {
                th.printStackTrace();
                return null;
            }
        }

        PackageInfo getPackageInfoForSelf(Context context) {
            try {
                if (!CommonUtil.isGtcProcess()) {
                    GtcProvider.setContext(context);
                    Bundle bundleCreateBundle = createBundle();
                    bundleCreateBundle.putString(ProcessSwitchContract.METHOD_NAME, "base-1-2-1");
                    return (PackageInfo) Broker.getInstance().subscribe(bundleCreateBundle).getParcelable(ProcessSwitchContract.METHOD_RETURN);
                }
                if (this.packageInfo == null) {
                    if (context == null) {
                        context = CommonUtil.findAppContext();
                    }
                    this.packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 79);
                }
                return this.packageInfo;
            } catch (Throwable th) {
                th.printStackTrace();
                return null;
            }
        }

        public boolean isAppForeground() {
            try {
                if (CommonUtil.isGtcProcess()) {
                    return GtcProvider.isForeground();
                }
                Bundle bundleCreateBundle = createBundle();
                bundleCreateBundle.putString(ProcessSwitchContract.METHOD_NAME, "base-1-1-1");
                return Broker.getInstance().subscribe(bundleCreateBundle).getBoolean(ProcessSwitchContract.METHOD_RETURN);
            } catch (Throwable unused) {
                return false;
            }
        }

        @Override // com.getui.gtc.base.publish.Subscriber
        public void receive(Bundle bundle, Bundle bundle2) {
            Parcelable packageInfoForSelf;
            String str;
            ArrayList arrayList = new ArrayList();
            try {
                try {
                    Throwable th = (Throwable) bundle2.getSerializable(ProcessSwitchContract.METHOD_EXCEPTION);
                    if (th != null) {
                        arrayList.add(th);
                    }
                    String string = bundle.getString(ProcessSwitchContract.METHOD_NAME);
                    if (TextUtils.isEmpty(string)) {
                        throw new RuntimeException("methodName missed");
                    }
                    byte b = -1;
                    int iHashCode = string.hashCode();
                    if (iHashCode != -1969640451) {
                        if (iHashCode != -1969639490) {
                            if (iHashCode == -1969638529 && string.equals("base-1-3-1")) {
                                b = 2;
                            }
                        } else if (string.equals("base-1-2-1")) {
                            b = 1;
                        }
                    } else if (string.equals("base-1-1-1")) {
                        b = 0;
                    }
                    switch (b) {
                        case 0:
                            bundle2.putBoolean(ProcessSwitchContract.METHOD_RETURN, isAppForeground());
                            break;
                        case 1:
                            packageInfoForSelf = getPackageInfoForSelf(GtcProvider.context());
                            str = ProcessSwitchContract.METHOD_RETURN;
                            bundle2.putParcelable(str, packageInfoForSelf);
                            break;
                        case 2:
                            packageInfoForSelf = getAppInfoForSelf(GtcProvider.context());
                            str = ProcessSwitchContract.METHOD_RETURN;
                            bundle2.putParcelable(str, packageInfoForSelf);
                            break;
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ((Throwable) it.next()).printStackTrace();
                    }
                } catch (Throwable th2) {
                    arrayList.add(th2);
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        ((Throwable) it2.next()).printStackTrace();
                    }
                }
            } catch (Throwable th3) {
                Iterator it3 = arrayList.iterator();
                while (it3.hasNext()) {
                    ((Throwable) it3.next()).printStackTrace();
                }
                throw th3;
            }
        }
    }

    public static void checkRuntimePermission(Context context, String str, boolean z) throws Throwable {
        if (hasPermission(context, str, z)) {
            return;
        }
        throw new IllegalStateException("permission " + str + " not granted");
    }

    public static Context findAppContext() {
        try {
            Method declaredMethod = Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication", new Class[0]);
            declaredMethod.setAccessible(true);
            return (Context) declaredMethod.invoke(null, new Object[0]);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public static ApplicationInfo getAppInfoForSelf(Context context) throws PackageManager.NameNotFoundException {
        ApplicationInfo appInfoForSelf = CommonUtilSubscriber.getInstance().getAppInfoForSelf(context);
        if (appInfoForSelf != null) {
            return appInfoForSelf;
        }
        throw new PackageManager.NameNotFoundException();
    }

    public static File getExternalFilesDir(Context context) {
        if (externalFilesDir == null) {
            if (context == null) {
                context = findAppContext();
            }
            externalFilesDir = context.getExternalFilesDir(null);
        }
        return externalFilesDir;
    }

    public static PackageInfo getPackageInfoForSelf(Context context) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfoForSelf = CommonUtilSubscriber.getInstance().getPackageInfoForSelf(context);
        if (packageInfoForSelf != null) {
            return packageInfoForSelf;
        }
        throw new PackageManager.NameNotFoundException();
    }

    public static String getProcessName() throws Throwable {
        String processName;
        try {
            if (Build.VERSION.SDK_INT >= 28) {
                processName = Application.getProcessName();
            } else {
                Method declaredMethod = Class.forName("android.app.ActivityThread").getDeclaredMethod("currentProcessName", new Class[0]);
                declaredMethod.setAccessible(true);
                processName = (String) declaredMethod.invoke(null, new Object[0]);
            }
            return processName;
        } catch (Throwable th) {
            th.printStackTrace();
            String processNameByPid = getProcessNameByPid(Process.myPid());
            return !TextUtils.isEmpty(processNameByPid) ? processNameByPid : "unknown.process";
        }
    }

    /* JADX WARN: Not initialized variable reg: 1, insn: 0x004a: MOVE (r0 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]), block:B:26:0x004a */
    /* JADX WARN: Removed duplicated region for block: B:35:0x004d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static String getProcessNameByPid(int i) throws Throwable {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        BufferedReader bufferedReader3 = null;
        try {
            try {
                bufferedReader = new BufferedReader(new FileReader("/proc/" + i + "/cmdline"));
                try {
                    String line = bufferedReader.readLine();
                    if (!TextUtils.isEmpty(line)) {
                        line = line.trim();
                    }
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return line;
                } catch (Throwable th) {
                    th = th;
                    th.printStackTrace();
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    return null;
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedReader3 = bufferedReader2;
                if (bufferedReader3 != null) {
                    try {
                        bufferedReader3.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
        }
    }

    public static Activity getTopActivity() {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Object objInvoke = cls.getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
            Field declaredField = cls.getDeclaredField("mActivities");
            declaredField.setAccessible(true);
            Map map = Build.VERSION.SDK_INT < 19 ? (HashMap) declaredField.get(objInvoke) : (ArrayMap) declaredField.get(objInvoke);
            if (map.size() <= 0) {
                return null;
            }
            for (Object obj : map.values()) {
                Class<?> cls2 = obj.getClass();
                Field declaredField2 = cls2.getDeclaredField("paused");
                declaredField2.setAccessible(true);
                if (!declaredField2.getBoolean(obj)) {
                    Field declaredField3 = cls2.getDeclaredField("activity");
                    declaredField3.setAccessible(true);
                    return (Activity) declaredField3.get(obj);
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return null;
    }

    public static boolean hasPermission(Context context, String str, boolean z) {
        try {
            return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
        } catch (Throwable unused) {
            return z;
        }
    }

    public static boolean isAppDebugEnable() {
        if (GtcProvider.context() == null) {
            return false;
        }
        if (isAppDebug == null) {
            try {
                isAppDebug = Boolean.valueOf((GtcProvider.context().getApplicationInfo().flags & 2) != 0);
            } catch (Throwable unused) {
                return false;
            }
        }
        return isAppDebug.booleanValue();
    }

    public static boolean isAppForeground() {
        return CommonUtilSubscriber.getInstance().isAppForeground();
    }

    public static boolean isGtcProcess() {
        return Process.myPid() == GtcProvider.gtcPid();
    }

    public static boolean isMainProcess() {
        return isMainProcess(GtcProvider.context());
    }

    public static boolean isMainProcess(Context context) {
        if (context == null) {
            try {
                context = findAppContext();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        if (context != null) {
            String str = context.getApplicationInfo().processName;
            String processName = getProcessName();
            if (str != null) {
                if (str.equals(processName)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
