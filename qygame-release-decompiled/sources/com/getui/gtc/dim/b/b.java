package com.getui.gtc.dim.b;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Process;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import com.getui.gtc.base.GtcProvider;
import com.getui.gtc.base.util.CommonUtil;
import com.getui.gtc.dim.b.d;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    private static final b i = new b();
    private long j = 0;
    long a = 10;
    long b = -1;
    long c = 3600;
    long d = -1;
    long e = -1;
    private long l = -1;
    boolean f = false;
    boolean g = false;
    boolean h = true;
    private boolean m = true;
    private final Map<String, a> k = new HashMap(4);

    public static class a {
        public String a;
        public long b;
        public String c;
        public String d;
        public int e;
        public int f;
        public int g;
        public int h;

        public final String toString() {
            return "Config{key='" + this.a + "', sdkTotalRuntimeCondition=" + this.b + ", timeRangeStart='" + this.c + "', timeRangeEnd='" + this.d + "', timeRangeState=" + this.e + ", weekState=" + this.f + ", sdkAccessCountCondition=" + this.g + ", installDurationDayCondition=" + this.h + '}';
        }
    }

    /* JADX INFO: renamed from: com.getui.gtc.dim.b.b$b, reason: collision with other inner class name */
    static class C0013b {
        static Calendar a(long j, String str) {
            String[] strArrSplit = str.split(":");
            int i = Integer.parseInt(strArrSplit[0]);
            int i2 = Integer.parseInt(strArrSplit[1]);
            int i3 = Integer.parseInt(strArrSplit[2]);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(j);
            calendar.set(11, i);
            calendar.set(12, i2);
            calendar.set(13, i3);
            calendar.set(14, 0);
            return calendar;
        }

        public static boolean a(long j) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(j);
            int i = calendar.get(7);
            return i == 1 || i == 7;
        }
    }

    private b() {
    }

    public static b a() {
        return i;
    }

    private static boolean a(Context context) {
        try {
            if (!CommonUtil.isAppForeground()) {
                Intent intentRegisterReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
                return intentRegisterReceiver == null || intentRegisterReceiver.getExtras() == null || intentRegisterReceiver.getExtras().getInt("plugged") == 2;
            }
            boolean z = Settings.Secure.getInt(context.getContentResolver(), "adb_enabled", 0) > 0;
            Intent intentRegisterReceiver2 = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            if (intentRegisterReceiver2 != null && intentRegisterReceiver2.getExtras() != null) {
                return z && intentRegisterReceiver2.getExtras().getInt("plugged") == 2;
            }
            return true;
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.b(th);
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0091  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static boolean a(boolean z, boolean z2) {
        boolean z3;
        if (z) {
            try {
                if (CommonUtil.isAppDebugEnable()) {
                    com.getui.gtc.dim.e.b.b("check safe f: debuggable");
                    return false;
                }
            } catch (Throwable th) {
                com.getui.gtc.dim.e.b.b(th);
                return false;
            }
        }
        if (z && a(GtcProvider.context())) {
            com.getui.gtc.dim.e.b.b("check safe f: u-model");
            return false;
        }
        if (z2 && c()) {
            com.getui.gtc.dim.e.b.b("check safe f: xp");
            return false;
        }
        String str = Build.FINGERPRINT;
        if (str.contains("generic") || str.contains("unknown") || str.contains("generic_x86") || str.contains("vbox")) {
            z3 = true;
        } else {
            String str2 = Build.MODEL;
            if (!str2.contains("google_sdk") && !str2.contains("Emulator") && !str2.contains("Android SDK built for x86") && !Build.MANUFACTURER.contains("Genymotion")) {
                String str3 = Build.HARDWARE;
                if (!"goldfish".equals(str3) && !"ranchu".equals(str3)) {
                    z3 = false;
                }
            }
        }
        if (z3) {
            com.getui.gtc.dim.e.b.b("check safe f: emulator");
            return false;
        }
        com.getui.gtc.dim.e.b.a("check safe s");
        return true;
    }

    private long b() {
        long j;
        synchronized (this) {
            if (this.l < 0) {
                this.l = b("dim-key-sdk-sync-install-time");
            }
            j = this.l;
        }
        return j;
    }

    static long b(String str) {
        try {
            d unused = d.a.a;
            h hVarA = d.a(str);
            Object obj = hVarA != null ? hVarA.a : null;
            j = obj instanceof String ? Long.parseLong((String) obj) : -1L;
            com.getui.gtc.dim.e.b.a("dim sys get " + str + " from db: " + j);
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.b(th);
        }
        if (j < 0) {
            return 0L;
        }
        return j;
    }

    private static boolean c() {
        HashSet<String> hashSet;
        FileReader fileReader;
        BufferedReader bufferedReader;
        if (e(f("ZGUucm9idi5hbmRyb2lkLnhwb3NlZC5pbnN0YWxsZXI=")) || e(f("Y29tLnNhdXJpay5zdWJzdHJhdGU="))) {
            return true;
        }
        try {
            ClassLoader.getSystemClassLoader().loadClass(f("ZGUucm9idi5hbmRyb2lkLnhwb3NlZC5YcG9zZWRIZWxwZXJz"));
            return true;
        } catch (Exception e) {
            String strF = f("ZGUucm9idi5hbmRyb2lkLnhwb3NlZC5YcG9zZWRCcmlkZ2U=");
            String strF2 = f("bWFpbg==");
            String strF3 = f("aGFuZGxlSG9va2VkTWV0aG9k");
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                if (stackTraceElement.getClassName().equals(strF) && (stackTraceElement.getMethodName().equals(strF2) || stackTraceElement.getMethodName().equals(strF3))) {
                    return true;
                }
            }
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter((Writer) stringWriter, true);
            e.printStackTrace(printWriter);
            printWriter.flush();
            stringWriter.flush();
            if (stringWriter.toString().contains(f("eHBvc2Vk"))) {
                return true;
            }
            try {
                hashSet = new HashSet();
                fileReader = new FileReader("/proc/" + Process.myPid() + "/maps");
                bufferedReader = new BufferedReader(fileReader);
            } catch (Throwable th) {
                com.getui.gtc.dim.e.b.b(th);
                return false;
            }
            while (true) {
                try {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        if (line.endsWith(".so") || line.endsWith(".jar")) {
                            hashSet.add(line.substring(line.lastIndexOf(" ") + 1));
                        }
                    } catch (Throwable th2) {
                        com.getui.gtc.dim.e.b.b(th2);
                        fileReader.close();
                    }
                    bufferedReader.close();
                    return false;
                } finally {
                    fileReader.close();
                    bufferedReader.close();
                }
            }
            String strF4 = f("Y29tLnNhdXJpay5zdWJzdHJhdGU=");
            String strF5 = f("WHBvc2VkQnJpZGdlLmphcg==");
            for (String str : hashSet) {
                if (str.contains(strF4)) {
                    return true;
                }
                if (str.contains(strF5)) {
                    return true;
                }
            }
            fileReader.close();
            bufferedReader.close();
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00af  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static List<a> d(String str) {
        String[] strArr;
        int i2;
        ArrayList arrayList = new ArrayList();
        try {
            String[] strArrSplit = str.split(com.igexin.push.core.b.an);
            int length = strArrSplit.length;
            char c = 0;
            int i3 = 0;
            while (i3 < length) {
                String str2 = strArrSplit[i3];
                if (!TextUtils.isEmpty(str2)) {
                    String[] strArrSplit2 = str2.split("\\|");
                    if (strArrSplit2.length == 6) {
                        try {
                            String str3 = strArrSplit2[c];
                            long j = Long.parseLong(strArrSplit2[1]);
                            String str4 = strArrSplit2[2];
                            if (Pattern.compile("^\\d{2}:\\d{2}:\\d{2}-\\d{2}:\\d{2}:\\d{2}#[0-3]$").matcher(str4).matches()) {
                                String[] strArrSplit3 = str4.split("#");
                                String[] strArrSplit4 = strArrSplit3[c].split("-");
                                String str5 = strArrSplit4[c];
                                String str6 = strArrSplit4[1];
                                int i4 = Integer.parseInt(strArrSplit3[1]);
                                int i5 = Integer.parseInt(strArrSplit2[3]);
                                int i6 = Integer.parseInt(strArrSplit2[4]);
                                int i7 = Integer.parseInt(strArrSplit2[5]);
                                long j2 = i6;
                                strArr = strArrSplit;
                                i2 = length;
                                try {
                                    if (j2 > i.a) {
                                        i.a = j2;
                                    }
                                    if (j > i.c) {
                                        i.c = j;
                                    }
                                    a aVar = new a();
                                    aVar.a = str3;
                                    aVar.b = j;
                                    aVar.c = str5;
                                    aVar.d = str6;
                                    aVar.e = i4;
                                    aVar.f = i5;
                                    aVar.g = i6;
                                    aVar.h = i7;
                                    arrayList.add(aVar);
                                } catch (Throwable th) {
                                    th = th;
                                    com.getui.gtc.dim.e.b.b(th);
                                }
                            } else {
                                strArr = strArrSplit;
                                i2 = length;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            strArr = strArrSplit;
                            i2 = length;
                        }
                    } else {
                        strArr = strArrSplit;
                        i2 = length;
                    }
                }
                i3++;
                strArrSplit = strArr;
                length = i2;
                c = 0;
            }
        } catch (Throwable th3) {
            com.getui.gtc.dim.e.b.b(th3);
        }
        return arrayList;
    }

    private static boolean e(String str) {
        try {
            com.getui.gtc.dim.e.d.a(str, 0);
            com.getui.gtc.dim.e.b.a("specific " + str + " p info hit success");
            return true;
        } catch (Throwable unused) {
            com.getui.gtc.dim.e.b.a("specific " + str + " p info hit failed");
            return false;
        }
    }

    private static String f(String str) {
        return new String(Base64.decode(str, 0));
    }

    public final void a(long j) {
        if (this.m) {
            this.j = j;
            synchronized (this) {
                if (b() == 0) {
                    try {
                        this.l = System.currentTimeMillis() + j;
                        d unused = d.a.a;
                        d.a("dim-key-sdk-sync-install-time", (Object) String.valueOf(this.l));
                        com.getui.gtc.dim.e.b.a("dim sys server install time set: " + this.l);
                    } catch (Throwable th) {
                        com.getui.gtc.dim.e.b.b(th);
                    }
                }
            }
        }
        com.getui.gtc.dim.e.b.a("dim sys syncTime set: " + j + ", syncTime: " + this.m);
    }

    public final void a(String str) {
        try {
            for (String str2 : str.split(com.igexin.push.core.b.an)) {
                String[] strArrSplit = str2.split(":");
                String str3 = strArrSplit[0];
                boolean z = true;
                if (Integer.parseInt(strArrSplit[1]) != 1) {
                    z = false;
                }
                if ("dim".equals(str3)) {
                    this.f = z;
                } else if ("xp".equals(str3)) {
                    this.g = z;
                } else if ("du".equals(str3)) {
                    this.h = z;
                } else if (com.igexin.push.core.b.ad.equals(str3)) {
                    this.m = z;
                }
            }
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.b(th);
        }
        com.getui.gtc.dim.e.b.a("dim sys globalHC policy set: ".concat(String.valueOf(str)));
    }

    public final void a(String str, a aVar) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.k.put(str, aVar);
        com.getui.gtc.dim.e.b.a("dim sys globalHC set: " + str + " : " + aVar);
    }

    final boolean c(String str) {
        boolean z;
        try {
            a aVar = this.k.get(str);
            if (aVar == null) {
                aVar = this.k.get("dim-2-2-0-1");
            }
            if (aVar == null) {
                com.getui.gtc.dim.e.b.a("check filed condition : " + str + " , config is null");
                return false;
            }
            long jCurrentTimeMillis = System.currentTimeMillis() + this.j;
            if (aVar.b > 0 && this.d < aVar.b * 1000) {
                com.getui.gtc.dim.e.b.a("check filed condition : " + str + " , 1 not passed, " + this.d);
                return false;
            }
            if (aVar.e == 1) {
                String str2 = aVar.c;
                String str3 = aVar.d;
                Calendar calendarA = C0013b.a(jCurrentTimeMillis, str2);
                Calendar calendarA2 = C0013b.a(jCurrentTimeMillis, str3);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(jCurrentTimeMillis);
                if (calendarA2.before(calendarA)) {
                    z = calendar.after(calendarA) || calendar.before(calendarA2);
                } else {
                    if (calendar.after(calendarA) && calendar.before(calendarA2)) {
                    }
                }
                if (z) {
                    if (aVar.f > 0 && aVar.f <= 3 && ((aVar.f != 1 || !C0013b.a(jCurrentTimeMillis)) && (aVar.f != 2 || C0013b.a(jCurrentTimeMillis)))) {
                        if (aVar.g > 0 && this.b < aVar.g) {
                            com.getui.gtc.dim.e.b.a("check filed condition : " + str + " , 4 not passed, " + this.b);
                            return false;
                        }
                        long jB = b();
                        if (jB == 0) {
                            jB = com.getui.gtc.dim.e.d.a(GtcProvider.context().getPackageName(), 0).firstInstallTime;
                        }
                        if (aVar.h <= 0 || jCurrentTimeMillis - jB >= aVar.h * 86400000) {
                            com.getui.gtc.dim.e.b.a("check filed condition : " + str + " , passed");
                            return true;
                        }
                        com.getui.gtc.dim.e.b.a("check filed condition : " + str + " , 5 not passed, " + jB);
                        return false;
                    }
                    com.getui.gtc.dim.e.b.a("check filed condition : " + str + " , 3 not passed");
                    return false;
                }
            }
            com.getui.gtc.dim.e.b.a("check filed condition : " + str + " , 2 not passed");
            return false;
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a("check filed condition : " + str + " , catch exception, not passed", th);
            return false;
        }
    }
}
