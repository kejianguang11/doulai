package com.loc;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Xml;
import android.view.WindowManager;
import com.igexin.assist.util.AssistUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import org.xmlpull.v1.XmlPullParser;

/* JADX INFO: loaded from: classes.dex */
public final class o {
    private static String A = "";
    private static String B = "";
    private static boolean C = false;
    private static boolean D = false;
    private static String E = "";
    private static boolean F = false;
    private static boolean G = false;
    private static long H = 0;
    private static int I = 0;
    private static String J = null;
    private static String K = "";
    private static boolean L = true;
    private static boolean M = false;
    private static String N = "";
    private static boolean O = false;
    private static int P = -1;
    private static boolean Q = false;
    private static int R = -1;
    private static boolean S = false;
    private static volatile b T = null;
    static String a = "";
    static String b = "";
    static volatile boolean c = true;
    public static boolean d = false;
    static String e = "";
    static boolean f = false;
    public static a g = null;
    static int h = -1;
    static String i = "";
    static String j = "";
    private static String k = null;
    private static boolean l = false;
    private static volatile boolean m = false;
    private static String n = "";
    private static boolean o = false;
    private static boolean p = false;
    private static boolean q = false;
    private static String r = "";
    private static String s = "";
    private static boolean t = false;
    private static boolean u = false;
    private static String v = "";
    private static boolean w = false;
    private static String x = "";
    private static boolean y = false;
    private static String z = "";

    public interface a {
        bl a(byte[] bArr, Map<String, String> map);

        String a();

        String a(Context context, String str);

        String a(String str, String str2, String str3, String str4);

        Map<String, String> b();
    }

    public static class b {
        private static Context a;
        private static BroadcastReceiver b;
        private static ConnectivityManager c;
        private static NetworkRequest d;
        private static ConnectivityManager.NetworkCallback e;

        @SuppressLint({"WrongConstant"})
        public final void a(Context context) {
            if (Build.VERSION.SDK_INT < 24) {
                if (context == null || b != null) {
                    return;
                }
                b = new BroadcastReceiver() { // from class: com.loc.o.b.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if (x.c("WYW5kcm9pZC5uZXQuY29ubi5DT05ORUNUSVZJVFlfQ0hBTkdF").equals(intent.getAction())) {
                            o.h();
                        }
                    }
                };
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(x.c("WYW5kcm9pZC5uZXQuY29ubi5DT05ORUNUSVZJVFlfQ0hBTkdF"));
                context.registerReceiver(b, intentFilter);
                return;
            }
            if (o.c(context, x.c("AYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19ORVRXT1JLX1NUQVRF")) && context != null && c == null) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                c = connectivityManager;
                if (connectivityManager != null) {
                    d = new NetworkRequest.Builder().addCapability(0).addCapability(1).build();
                    e = new ConnectivityManager.NetworkCallback() { // from class: com.loc.o.b.2
                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public final void onAvailable(Network network) {
                            super.onAvailable(network);
                            o.h();
                        }

                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public final void onLost(Network network) {
                            super.onLost(network);
                            o.h();
                        }
                    };
                    c.registerNetworkCallback(d, e);
                    a = context;
                }
            }
        }
    }

    static class c implements ServiceConnection {
        private static String a;
        private Context b;
        private int c;

        c(Context context, int i) {
            this.b = context;
            this.c = i;
        }

        private String a() {
            try {
                if (!TextUtils.isEmpty(a)) {
                    return a;
                }
                byte[] bArrDigest = MessageDigest.getInstance(x.c("IU0hBMQ")).digest(this.b.getPackageManager().getPackageInfo(this.b.getPackageName(), 64).signatures[0].toByteArray());
                StringBuffer stringBuffer = new StringBuffer();
                for (byte b : bArrDigest) {
                    stringBuffer.append(Integer.toHexString((b & 255) | 256).substring(1, 3));
                }
                String string = stringBuffer.toString();
                if (!TextUtils.isEmpty(string)) {
                    a = string;
                }
                return string;
            } catch (Throwable unused) {
                return "";
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x004b A[Catch: all -> 0x005f, Throwable -> 0x0061, Merged into TryCatch #1 {all -> 0x005f, Throwable -> 0x0061, blocks: (B:3:0x0008, B:5:0x000f, B:13:0x004b, B:7:0x0014, B:8:0x0037, B:9:0x003d, B:10:0x0041, B:19:0x0062), top: B:24:0x0008 }, TRY_LEAVE] */
        @Override // android.content.ServiceConnection
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            String strC;
            boolean z;
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                int i = this.c;
                if (i != 2) {
                    switch (i) {
                        case 4:
                            strC = x.c("UY29tLnNhbXN1bmcuYW5kcm9pZC5kZXZpY2VpZHNlcnZpY2UuSURldmljZUlkU2VydmljZQ");
                            break;
                        case 5:
                            parcelObtain.writeInterfaceToken(x.c("KY29tLmhleXRhcC5vcGVuaWQuSU9wZW5JRA"));
                            parcelObtain.writeString(this.b.getPackageName());
                            parcelObtain.writeString(a());
                            parcelObtain.writeString(x.c("IT1VJRA"));
                            z = true;
                            break;
                        default:
                            z = false;
                            break;
                    }
                    if (z) {
                        iBinder.transact(1, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        String unused = o.n = parcelObtain2.readString();
                    }
                }
                strC = x.c("UY29tLnVvZGlzLm9wZW5kZXZpY2UuYWlkbC5PcGVuRGV2aWNlSWRlbnRpZmllclNlcnZpY2U");
                parcelObtain.writeInterfaceToken(strC);
                z = true;
                if (z) {
                }
            } catch (Throwable th) {
                ak.a(th, "oac", String.valueOf(this.c));
            } finally {
                parcelObtain2.recycle();
                parcelObtain.recycle();
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
        }
    }

    static String A(Context context) {
        try {
            return L(context);
        } catch (Throwable unused) {
            return "";
        }
    }

    private static String C(Context context) {
        try {
            String strB = av.b(context, "Alvin2", "UTDID2", "");
            return TextUtils.isEmpty(strB) ? av.b(context, "Alvin2", "UTDID", "") : strB;
        } catch (Throwable unused) {
            return "";
        }
    }

    private static String D(Context context) throws Throwable {
        boolean z2;
        FileInputStream fileInputStream = null;
        try {
        } catch (Throwable th) {
            th = th;
        }
        try {
            if (x.a(context, "android.permission.READ_EXTERNAL_STORAGE") && "mounted".equals(Environment.getExternalStorageState())) {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/.UTSystemConfig/Global/Alvin2.xml");
                XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
                FileInputStream fileInputStream2 = new FileInputStream(file);
                try {
                    xmlPullParserNewPullParser.setInput(fileInputStream2, com.igexin.push.g.s.b);
                    z2 = false;
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream = fileInputStream2;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (Throwable unused) {
                        }
                    }
                    throw th;
                }
                for (int eventType = xmlPullParserNewPullParser.getEventType(); 1 != eventType; eventType = xmlPullParserNewPullParser.next()) {
                    if (eventType != 0) {
                        switch (eventType) {
                            case 2:
                                if (xmlPullParserNewPullParser.getAttributeCount() > 0) {
                                    int attributeCount = xmlPullParserNewPullParser.getAttributeCount();
                                    boolean z3 = z2;
                                    for (int i2 = 0; i2 < attributeCount; i2++) {
                                        String attributeValue = xmlPullParserNewPullParser.getAttributeValue(i2);
                                        if ("UTDID2".equals(attributeValue) || "UTDID".equals(attributeValue)) {
                                            z3 = true;
                                        }
                                    }
                                    z2 = z3;
                                }
                                break;
                            case 3:
                                z2 = false;
                                break;
                            case 4:
                                if (z2) {
                                    String text = xmlPullParserNewPullParser.getText();
                                    try {
                                        fileInputStream2.close();
                                        break;
                                    } catch (Throwable unused2) {
                                    }
                                    return text;
                                }
                                break;
                                fileInputStream.close();
                                return "";
                        }
                    }
                }
                fileInputStream = fileInputStream2;
            }
            fileInputStream.close();
            return "";
        } catch (Throwable unused3) {
            return "";
        }
        if (fileInputStream == null) {
            return "";
        }
    }

    private static String E(Context context) {
        try {
            Class<?> cls = Class.forName(x.c("WY29tLmFuZHJvaWQuaWQuaW1wbC5JZFByb3ZpZGVySW1wbA"));
            Object objInvoke = cls.getMethod(x.c("MZ2V0T0FJRA"), Context.class).invoke(cls.newInstance(), context);
            if (objInvoke != null) {
                String str = (String) objInvoke;
                n = str;
                return str;
            }
        } catch (Throwable th) {
            ak.a(th, "oa", "xm");
            o = true;
        }
        return n;
    }

    private static String F(Context context) {
        try {
            Cursor cursorQuery = context.getContentResolver().query(Uri.parse(x.c("QY29udGVudDovL2NvbS52aXZvLnZtcy5JZFByb3ZpZGVyL0lkZW50aWZpZXJJZC9PQUlE")), null, null, null, null);
            if (cursorQuery != null) {
                while (cursorQuery.moveToNext()) {
                    int columnCount = cursorQuery.getColumnCount();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= columnCount) {
                            break;
                        }
                        if (x.c("IdmFsdWU").equals(cursorQuery.getColumnName(i2))) {
                            n = cursorQuery.getString(i2);
                            break;
                        }
                        i2++;
                    }
                }
                cursorQuery.close();
            }
        } catch (Throwable th) {
            o = true;
            ak.a(th, "oa", AssistUtils.BRAND_VIVO);
        }
        return n;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String G(Context context) {
        if (x.c("IeGlhb21p").equalsIgnoreCase(Build.MANUFACTURER) || x.c("IeGlhb21p").equalsIgnoreCase(Build.BRAND) || x.c("IUkVETUk=").equalsIgnoreCase(Build.MANUFACTURER) || x.c("IUkVETUk=").equalsIgnoreCase(Build.BRAND)) {
            return E(context);
        }
        if (x.c("Idml2bw").equalsIgnoreCase(Build.MANUFACTURER) || x.c("Idml2bw").equalsIgnoreCase(Build.BRAND)) {
            return F(context);
        }
        if (x.c("IaHVhd2Vp").equalsIgnoreCase(Build.MANUFACTURER) || x.c("IaHVhd2Vp").equalsIgnoreCase(Build.BRAND) || x.c("ISE9OT1I=").equalsIgnoreCase(Build.MANUFACTURER)) {
            return a(context, 2);
        }
        if (x.c("Mc2Ftc3VuZw").equalsIgnoreCase(Build.MANUFACTURER) || x.c("Mc2Ftc3VuZw").equalsIgnoreCase(Build.BRAND)) {
            return a(context, 4);
        }
        if (x.c("IT1BQTw").equalsIgnoreCase(Build.MANUFACTURER) || x.c("IT1BQTw").equalsIgnoreCase(Build.BRAND) || x.c("MT25lUGx1cw").equalsIgnoreCase(Build.MANUFACTURER) || x.c("MT25lUGx1cw").equalsIgnoreCase(Build.BRAND) || x.c("IUkVBTE1F").equalsIgnoreCase(Build.BRAND)) {
            return a(context, 5);
        }
        o = true;
        return n;
    }

    private static String H(Context context) {
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.getName().equalsIgnoreCase("wlan0")) {
                    byte[] hardwareAddress = null;
                    if (Build.VERSION.SDK_INT >= 9 && (Build.VERSION.SDK_INT < 30 || context.getApplicationInfo().targetSdkVersion < 30)) {
                        hardwareAddress = networkInterface.getHardwareAddress();
                    }
                    if (hardwareAddress == null) {
                        return "";
                    }
                    StringBuilder sb = new StringBuilder();
                    for (byte b2 : hardwareAddress) {
                        String upperCase = Integer.toHexString(b2 & 255).toUpperCase();
                        if (upperCase.length() == 1) {
                            sb.append("0");
                        }
                        sb.append(upperCase);
                        sb.append(":");
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString();
                }
            }
            return "";
        } catch (Exception unused) {
            return "";
        }
    }

    private static String I(Context context) {
        if (!TextUtils.isEmpty(E)) {
            return E;
        }
        try {
            String strB = av.b(context, "open_common", "a1", "");
            if (TextUtils.isEmpty(strB)) {
                E = "amap" + UUID.randomUUID().toString().replace("_", "").toLowerCase();
                SharedPreferences.Editor editorA = av.a(context, "open_common");
                av.a(editorA, "a1", x.b(E));
                av.a(editorA);
            } else {
                E = x.c(strB);
            }
        } catch (Throwable unused) {
        }
        return E;
    }

    private static boolean J(Context context) {
        TelephonyManager telephonyManagerP = P(context);
        if (telephonyManagerP == null) {
            return false;
        }
        switch (telephonyManagerP.getSimState()) {
            case 0:
            case 1:
                return false;
            default:
                return true;
        }
    }

    private static String K(Context context) throws IllegalAccessException, InvocationTargetException {
        if (Build.VERSION.SDK_INT >= 29) {
            return "";
        }
        boolean zJ = J(context);
        if (L != zJ) {
            if (zJ) {
                K = "";
                M = false;
            }
            L = zJ;
        }
        if ((K == null || "".equals(K)) && !M) {
            if (!L) {
                return "";
            }
            if (!c(context, x.c("WYW5kcm9pZC5wZXJtaXNzaW9uLlJFQURfUEhPTkVfU1RBVEU="))) {
                return K;
            }
            TelephonyManager telephonyManagerP = P(context);
            if (telephonyManagerP == null) {
                return "";
            }
            Method methodA = x.a(telephonyManagerP.getClass(), "UZ2V0U3Vic2NyaWJlcklk", (Class<?>[]) new Class[0]);
            if (methodA != null) {
                K = (String) methodA.invoke(telephonyManagerP, new Object[0]);
            }
            if (K == null) {
                K = "";
            }
            M = true;
            return K;
        }
        return K;
    }

    private static String L(Context context) {
        TelephonyManager telephonyManagerP;
        if (O) {
            return N;
        }
        U(context);
        if (c(context, x.c("WYW5kcm9pZC5wZXJtaXNzaW9uLlJFQURfUEhPTkVfU1RBVEU=")) && (telephonyManagerP = P(context)) != null) {
            String simOperatorName = telephonyManagerP.getSimOperatorName();
            N = simOperatorName;
            if (TextUtils.isEmpty(simOperatorName)) {
                N = telephonyManagerP.getNetworkOperatorName();
            }
            O = true;
            return N;
        }
        return N;
    }

    private static int M(Context context) {
        if (Q) {
            return P;
        }
        U(context);
        if (context == null || !c(context, x.c("AYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19ORVRXT1JLX1NUQVRF"))) {
            return P;
        }
        ConnectivityManager connectivityManagerN = N(context);
        if (connectivityManagerN == null) {
            return P;
        }
        NetworkInfo activeNetworkInfo = connectivityManagerN.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            P = activeNetworkInfo.getType();
        }
        Q = true;
        return P;
    }

    private static ConnectivityManager N(Context context) {
        return (ConnectivityManager) context.getSystemService("connectivity");
    }

    private static int O(Context context) {
        TelephonyManager telephonyManagerP;
        if (S) {
            return R;
        }
        U(context);
        if (c(context, x.c("WYW5kcm9pZC5wZXJtaXNzaW9uLlJFQURfUEhPTkVfU1RBVEU=")) && (telephonyManagerP = P(context)) != null) {
            R = telephonyManagerP.getNetworkType();
            S = true;
            return R;
        }
        return R;
    }

    private static TelephonyManager P(Context context) {
        return (TelephonyManager) context.getSystemService("phone");
    }

    private static String Q(Context context) {
        String strR;
        if (!c) {
            return "";
        }
        try {
            strR = R(context);
        } catch (Throwable unused) {
            strR = null;
        }
        if (!TextUtils.isEmpty(strR)) {
            try {
                return new String(p.a(x.c("HYW1hcGFkaXVhbWFwYWRpdWFtYXBhZGl1YW1hcGFkaXU").getBytes(com.alipay.sdk.sys.a.m), p.b(strR), x.c("MAAAAAAAAAAAAAAAAAAAAAA").getBytes(com.alipay.sdk.sys.a.m)), com.alipay.sdk.sys.a.m);
            } catch (Throwable unused2) {
            }
        }
        c = false;
        return "";
    }

    private static String R(Context context) throws Throwable {
        String strS = "";
        try {
            strS = S(context);
        } catch (Throwable unused) {
        }
        return !TextUtils.isEmpty(strS) ? strS : context == null ? "" : context.getSharedPreferences(x.c("SU2hhcmVkUHJlZmVyZW5jZUFkaXU"), 0).getString(s.a(x.c("RYW1hcF9kZXZpY2VfYWRpdQ")), "");
    }

    private static String S(Context context) throws Throwable {
        RandomAccessFile randomAccessFile;
        ByteArrayOutputStream byteArrayOutputStream;
        Throwable th;
        String str;
        String[] strArrSplit;
        if (Build.VERSION.SDK_INT >= 19 && !c(context, x.c("EYW5kcm9pZC5wZXJtaXNzaW9uLlJFQURfRVhURVJOQUxfU1RPUkFHRQ=="))) {
            return "";
        }
        String strA = s.a(x.c("LYW1hcF9kZXZpY2VfYWRpdQ"));
        String strT = T(context);
        if (TextUtils.isEmpty(strT)) {
            return "";
        }
        File file = new File(strT + File.separator + x.c("KYmFja3Vwcw"), x.c("MLmFkaXU"));
        if (!file.exists() || !file.canRead()) {
            return "";
        }
        if (file.length() == 0) {
            file.delete();
            return "";
        }
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            try {
                byte[] bArr = new byte[1024];
                byteArrayOutputStream = new ByteArrayOutputStream();
                while (true) {
                    try {
                        int i2 = randomAccessFile.read(bArr);
                        if (i2 == -1) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr, 0, i2);
                    } catch (Throwable unused) {
                        byteArrayOutputStream2 = byteArrayOutputStream;
                        a(byteArrayOutputStream2);
                    }
                }
                str = new String(byteArrayOutputStream.toByteArray(), com.alipay.sdk.sys.a.m);
            } catch (Throwable unused2) {
            }
        } catch (Throwable unused3) {
            randomAccessFile = null;
        }
        if (TextUtils.isEmpty(str) || !str.contains(x.c("SIw")) || (strArrSplit = str.split(x.c("SIw"))) == null || strArrSplit.length != 2 || !TextUtils.equals(strA, strArrSplit[0])) {
            a(byteArrayOutputStream);
            a(randomAccessFile);
            return "";
        }
        String str2 = strArrSplit[1];
        a(byteArrayOutputStream);
        a(randomAccessFile);
        return str2;
    }

    private static String T(Context context) {
        if (Build.VERSION.SDK_INT < 9) {
            return null;
        }
        try {
            StorageManager storageManager = (StorageManager) context.getSystemService("storage");
            Class<?> cls = Class.forName(x.c("SYW5kcm9pZC5vcy5zdG9yYWdlLlN0b3JhZ2VWb2x1bWU"));
            Method method = storageManager.getClass().getMethod(x.c("MZ2V0Vm9sdW1lTGlzdA"), new Class[0]);
            Method method2 = cls.getMethod(x.c("FZ2V0UGF0aA"), new Class[0]);
            Method method3 = cls.getMethod(x.c("DaXNSZW1vdmFibGU"), new Class[0]);
            Object objInvoke = method.invoke(storageManager, new Object[0]);
            int length = Array.getLength(objInvoke);
            for (int i2 = 0; i2 < length; i2++) {
                Object obj = Array.get(objInvoke, i2);
                String str = (String) method2.invoke(obj, new Object[0]);
                if (!((Boolean) method3.invoke(obj, new Object[0])).booleanValue()) {
                    return str;
                }
            }
        } catch (Throwable unused) {
        }
        return null;
    }

    private static synchronized b U(Context context) {
        if (T == null) {
            if (context == null) {
                return null;
            }
            b bVar = new b();
            T = bVar;
            bVar.a(context.getApplicationContext());
        }
        return T;
    }

    public static String a() {
        return k;
    }

    public static String a(final Context context) {
        try {
            if (!TextUtils.isEmpty(b)) {
                return b;
            }
            if (context == null) {
                return "";
            }
            String strQ = Q(context);
            b = strQ;
            if (!TextUtils.isEmpty(strQ)) {
                return b;
            }
            if (c() == null || m) {
                return "";
            }
            m = true;
            cj.a().b(new ck() { // from class: com.loc.o.1
                @Override // com.loc.ck
                public final void a() {
                    try {
                        Map<String, String> mapB = o.g.b();
                        String strA = o.g.a(o.h(context), "", "", o.y(context));
                        if (TextUtils.isEmpty(strA)) {
                            return;
                        }
                        bg.a();
                        String strA2 = o.g.a(context, new String(bg.a(o.g.a(strA.getBytes(), mapB)).a));
                        if (TextUtils.isEmpty(strA2)) {
                            return;
                        }
                        o.b = strA2;
                    } catch (Throwable unused) {
                    }
                }
            });
            return "";
        } catch (Throwable unused) {
            return "";
        }
    }

    private static String a(Context context, int i2) {
        try {
            Intent intent = new Intent();
            if (i2 != 2) {
                switch (i2) {
                    case 4:
                        intent.setClassName(x.c("WY29tLnNhbXN1bmcuYW5kcm9pZC5kZXZpY2VpZHNlcnZpY2U"), x.c("QY29tLnNhbXN1bmcuYW5kcm9pZC5kZXZpY2VpZHNlcnZpY2UuRGV2aWNlSWRTZXJ2aWNl"));
                        break;
                    case 5:
                        intent.setClassName(x.c("YY29tLmhleXRhcC5vcGVuaWQ"), x.c("SY29tLmhleXRhcC5vcGVuaWQuSWRlbnRpZnlTZXJ2aWNl"));
                        intent.setAction(x.c("EYWN0aW9uLmNvbS5oZXl0YXAub3BlbmlkLk9QRU5fSURfU0VSVklDRQ"));
                        break;
                    default:
                        o = true;
                        return n;
                }
            } else {
                intent.setAction(x.c("WY29tLnVvZGlzLm9wZW5kZXZpY2UuT1BFTklEU19TRVJWSUNF"));
                intent.setPackage(x.c("UY29tLmh1YXdlaS5od2lk"));
            }
            c cVar = new c(context, i2);
            if (context.bindService(intent, cVar, 1)) {
                int i3 = 0;
                while (i3 < 100 && TextUtils.isEmpty(n)) {
                    i3++;
                    Thread.sleep(15L);
                }
                context.unbindService(cVar);
            }
            return n;
        } catch (Throwable th) {
            ak.a(th, "oa", String.valueOf(i2));
            o = true;
            return n;
        }
    }

    public static String a(Context context, String str) {
        if (Build.VERSION.SDK_INT >= 29) {
            return "";
        }
        try {
            if (Build.VERSION.SDK_INT < 21) {
                return "";
            }
            if (TextUtils.isEmpty(i) && !D) {
                TelephonyManager telephonyManagerP = P(context);
                if (h == -1) {
                    Method methodA = x.a(TelephonyManager.class, "UZ2V0UGhvbmVDb3VudA=", (Class<?>[]) new Class[0]);
                    if (methodA != null) {
                        try {
                            h = ((Integer) methodA.invoke(telephonyManagerP, new Object[0])).intValue();
                        } catch (Throwable unused) {
                            h = 0;
                        }
                    } else {
                        h = 0;
                    }
                }
                Method methodA2 = x.a(TelephonyManager.class, "MZ2V0SW1laQ=", (Class<?>[]) new Class[]{Integer.TYPE});
                if (methodA2 == null) {
                    h = 0;
                    D = true;
                    return "";
                }
                StringBuilder sb = new StringBuilder();
                for (int i2 = 0; i2 < h; i2++) {
                    try {
                        sb.append((String) methodA2.invoke(telephonyManagerP, Integer.valueOf(i2)));
                        sb.append(str);
                    } catch (Throwable unused2) {
                    }
                }
                String string = sb.toString();
                if (string.length() == 0) {
                    h = 0;
                    D = true;
                    return "";
                }
                i = string.substring(0, string.length() - 1);
                D = true;
                return i;
            }
            return i;
        } catch (Throwable unused3) {
            return "";
        }
    }

    public static void a(a aVar) {
        if (g == null) {
            g = aVar;
        }
    }

    private static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable unused) {
            }
        }
    }

    public static void a(String str) {
        k = str;
    }

    public static String b() {
        try {
            return !TextUtils.isEmpty(e) ? e : g == null ? "" : g.a();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String b(Context context) {
        try {
            return L(context);
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    public static a c() {
        return g;
    }

    public static String c(Context context) {
        if (Build.VERSION.SDK_INT >= 29) {
            return "";
        }
        try {
            String strY = y(context);
            if (strY != null && strY.length() >= 5) {
                return strY.substring(3, 5);
            }
            return "";
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean c(Context context, String str) {
        return context != null && context.checkCallingOrSelfPermission(str) == 0;
    }

    public static int d(Context context) {
        try {
            return O(context);
        } catch (Throwable th) {
            th.printStackTrace();
            return -1;
        }
    }

    static String[] d() {
        return new String[]{"", ""};
    }

    public static int e(Context context) {
        try {
            return M(context);
        } catch (Throwable th) {
            th.printStackTrace();
            return -1;
        }
    }

    public static void e() {
        try {
            ai.a();
        } catch (Throwable unused) {
        }
    }

    public static long f() {
        long blockCount;
        long blockCount2;
        if (H != 0) {
            return H;
        }
        try {
            StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
            StatFs statFs2 = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
            if (Build.VERSION.SDK_INT >= 18) {
                blockCount = (statFs.getBlockCountLong() * statFs.getBlockSizeLong()) / 1048576;
                blockCount2 = (statFs2.getBlockCountLong() * statFs2.getBlockSizeLong()) / 1048576;
            } else {
                blockCount = (((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / 1048576;
                blockCount2 = (((long) statFs2.getBlockCount()) * ((long) statFs2.getBlockSize())) / 1048576;
            }
            H = blockCount + blockCount2;
        } catch (Throwable unused) {
        }
        return H;
    }

    public static String f(Context context) {
        try {
            return K(context);
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    public static String g() {
        if (!TextUtils.isEmpty(J)) {
            return J;
        }
        String property = System.getProperty("os.arch");
        J = property;
        return property;
    }

    public static String g(final Context context) {
        if (o) {
            return "";
        }
        if (TextUtils.isEmpty(n) && !p) {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                cj.a().b(new ck() { // from class: com.loc.o.2
                    @Override // com.loc.ck
                    public final void a() {
                        o.G(context);
                        o.i();
                    }
                });
                return n;
            }
            p = true;
            return G(context);
        }
        return n;
    }

    public static String h(Context context) {
        if (q) {
            return a == null ? "" : a;
        }
        if (a != null && !"".equals(a)) {
            return a;
        }
        if (c(context, x.c("WYW5kcm9pZC5wZXJtaXNzaW9uLldSSVRFX1NFVFRJTkdT"))) {
            a = Settings.System.getString(context.getContentResolver(), "mqBRboGZkQPcAkyk");
        }
        if (!TextUtils.isEmpty(a)) {
            q = true;
            return a;
        }
        try {
            String strC = C(context);
            a = strC;
            if (!TextUtils.isEmpty(strC)) {
                q = true;
                return a;
            }
        } catch (Throwable unused) {
        }
        try {
            a = D(context);
            q = true;
        } catch (Throwable unused2) {
        }
        return a == null ? "" : a;
    }

    public static void h() {
        P = -1;
        Q = false;
        R = -1;
        S = false;
        N = "";
        O = false;
        x = "";
        y = false;
    }

    public static String i(Context context) {
        if (Build.VERSION.SDK_INT >= 29) {
            return "";
        }
        if (TextUtils.isEmpty(s) && !t) {
            if (!c(context, x.c("WYW5kcm9pZC5wZXJtaXNzaW9uLlJFQURfUEhPTkVfU1RBVEU="))) {
                return "";
            }
            if (Build.VERSION.SDK_INT >= 26) {
                String str = (String) x.a(Build.class, "MZ2V0U2VyaWFs", (Class<?>[]) new Class[0]).invoke(Build.class, new Object[0]);
                t = true;
                return str;
            }
            if (Build.VERSION.SDK_INT >= 9) {
                s = Build.SERIAL;
                t = true;
            }
            return s == null ? "" : s;
        }
        return s;
    }

    static /* synthetic */ boolean i() {
        p = true;
        return true;
    }

    public static String j(Context context) {
        if (TextUtils.isEmpty(r) && !u) {
            try {
                r = Settings.Secure.getString(context.getContentResolver(), x.c(new String(ag.a(13))));
                u = true;
                return r == null ? "" : r;
            } catch (Throwable unused) {
                return r;
            }
        }
        return r;
    }

    public static String k(Context context) {
        if (Build.VERSION.SDK_INT < 30 || context.getApplicationInfo().targetSdkVersion < 30) {
            try {
                if ((v == null || "".equals(v)) && !w && c(context, x.c("WYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19XSUZJX1NUQVRF"))) {
                    WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                    if (wifiManager == null) {
                        return "";
                    }
                    v = wifiManager.getConnectionInfo().getMacAddress();
                    if (x.c("YMDI6MDA6MDA6MDA6MDA6MDA").equals(v) || x.c("YMDA6MDA6MDA6MDA6MDA6MDA").equals(v)) {
                        v = H(context);
                    }
                    w = true;
                }
                return v;
            } catch (Throwable unused) {
            }
        }
        return v;
    }

    static String l(Context context) {
        try {
            TelephonyManager telephonyManagerP = P(context);
            if (telephonyManagerP == null) {
                return "";
            }
            String networkOperator = telephonyManagerP.getNetworkOperator();
            if (!TextUtils.isEmpty(networkOperator) && networkOperator.length() >= 3) {
                return networkOperator.substring(0, 3);
            }
            return "";
        } catch (Throwable unused) {
            return "";
        }
    }

    static String m(Context context) {
        TelephonyManager telephonyManagerP;
        if (y) {
            return x;
        }
        try {
            U(context);
            telephonyManagerP = P(context);
        } catch (Throwable unused) {
        }
        if (telephonyManagerP == null) {
            return x;
        }
        String networkOperator = telephonyManagerP.getNetworkOperator();
        if (!TextUtils.isEmpty(networkOperator) && networkOperator.length() >= 3) {
            x = networkOperator.substring(3);
            y = true;
            return x;
        }
        y = true;
        return x;
    }

    public static int n(Context context) {
        try {
            return O(context);
        } catch (Throwable unused) {
            return -1;
        }
    }

    public static int o(Context context) {
        try {
            return M(context);
        } catch (Throwable unused) {
            return -1;
        }
    }

    public static NetworkInfo p(Context context) {
        ConnectivityManager connectivityManagerN;
        if (c(context, x.c("AYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19ORVRXT1JLX1NUQVRF")) && (connectivityManagerN = N(context)) != null) {
            return connectivityManagerN.getActiveNetworkInfo();
        }
        return null;
    }

    static String q(Context context) {
        try {
            NetworkInfo networkInfoP = p(context);
            if (networkInfoP == null) {
                return null;
            }
            return networkInfoP.getExtraInfo();
        } catch (Throwable unused) {
            return null;
        }
    }

    static String r(Context context) {
        StringBuilder sb;
        if (z != null && !"".equals(z)) {
            return z;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager == null) {
            return "";
        }
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int i2 = displayMetrics.widthPixels;
        int i3 = displayMetrics.heightPixels;
        if (i3 > i2) {
            sb = new StringBuilder();
            sb.append(i2);
            sb.append("*");
            sb.append(i3);
        } else {
            sb = new StringBuilder();
            sb.append(i3);
            sb.append("*");
            sb.append(i2);
        }
        z = sb.toString();
        return z;
    }

    public static String s(Context context) {
        try {
            if (!c(context, x.c("WYW5kcm9pZC5wZXJtaXNzaW9uLlJFQURfUEhPTkVfU1RBVEU="))) {
                return K;
            }
            TelephonyManager telephonyManagerP = P(context);
            return telephonyManagerP == null ? "" : telephonyManagerP.getNetworkOperatorName();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String t(Context context) {
        ConnectivityManager connectivityManagerN;
        NetworkInfo activeNetworkInfo;
        try {
            return (!c(context, x.c("AYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19ORVRXT1JLX1NUQVRF")) || (connectivityManagerN = N(context)) == null || (activeNetworkInfo = connectivityManagerN.getActiveNetworkInfo()) == null) ? "" : activeNetworkInfo.getTypeName();
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String u(Context context) {
        String strV;
        String strA = "";
        try {
            strV = v(context);
        } catch (Throwable unused) {
        }
        try {
            strA = TextUtils.isEmpty(strV) ? a(context) : strV;
            if (TextUtils.isEmpty(strA)) {
                strA = h(context);
            }
            if (TextUtils.isEmpty(strA)) {
                strA = g(context);
            }
            if (TextUtils.isEmpty(strA)) {
                strA = j(context);
            }
            if (TextUtils.isEmpty(strA)) {
                return I(context);
            }
            return strA;
        } catch (Throwable unused2) {
            return strV;
        }
    }

    public static String v(Context context) {
        if (Build.VERSION.SDK_INT >= 29) {
            return "";
        }
        if ((A == null || "".equals(A)) && !F && c(context, x.c("WYW5kcm9pZC5wZXJtaXNzaW9uLlJFQURfUEhPTkVfU1RBVEU="))) {
            TelephonyManager telephonyManagerP = P(context);
            if (telephonyManagerP == null) {
                return "";
            }
            Method methodA = x.a(telephonyManagerP.getClass(), "QZ2V0RGV2aWNlSWQ", (Class<?>[]) new Class[0]);
            if (Build.VERSION.SDK_INT >= 26) {
                methodA = x.a(telephonyManagerP.getClass(), "QZ2V0SW1laQ==", (Class<?>[]) new Class[0]);
            }
            if (methodA != null) {
                A = (String) methodA.invoke(telephonyManagerP, new Object[0]);
            }
            if (A == null) {
                A = "";
            }
            F = true;
            return A;
        }
        return A;
    }

    public static String w(Context context) {
        return v(context) + "#" + a(context) + "#" + u(context);
    }

    public static String x(Context context) {
        if (Build.VERSION.SDK_INT >= 29) {
            return "";
        }
        if ((B == null || "".equals(B)) && c(context, x.c("WYW5kcm9pZC5wZXJtaXNzaW9uLlJFQURfUEhPTkVfU1RBVEU="))) {
            TelephonyManager telephonyManagerP = P(context);
            if (telephonyManagerP == null) {
                return "";
            }
            if (G) {
                return B;
            }
            if (Build.VERSION.SDK_INT >= 26) {
                Method methodA = x.a(telephonyManagerP.getClass(), "QZ2V0TWVpZA==", (Class<?>[]) new Class[0]);
                if (methodA != null) {
                    B = (String) methodA.invoke(telephonyManagerP, new Object[0]);
                }
                if (B == null) {
                    B = "";
                }
                G = true;
            }
            return B;
        }
        return B;
    }

    public static String y(Context context) {
        try {
            return K(context);
        } catch (Throwable unused) {
            return "";
        }
    }

    public static int z(Context context) throws Throwable {
        if (I != 0) {
            return I;
        }
        int i2 = 0;
        if (Build.VERSION.SDK_INT >= 16) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            if (activityManager == null) {
                return 0;
            }
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
            i2 = (int) (memoryInfo.totalMem / ConstantsAPI.AppSupportContentFlag.MMAPP_SUPPORT_XLS);
        } else {
            BufferedReader bufferedReader = null;
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new FileReader(new File("/proc/meminfo")));
                try {
                    int iIntValue = Integer.valueOf(bufferedReader2.readLine().split("\\s+")[1]).intValue();
                    try {
                        bufferedReader2.close();
                    } catch (IOException unused) {
                    }
                    i2 = iIntValue;
                } catch (Throwable unused2) {
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException unused3) {
                        }
                    }
                }
            } catch (Throwable th) {
                th = th;
            }
        }
        int i3 = i2 / 1024;
        I = i3;
        return i3;
    }
}
