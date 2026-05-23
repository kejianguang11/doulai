package com.zx.a.I8b7;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.text.TextUtils;
import android.util.Base64;
import java.security.MessageDigest;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class c1 {
    public static final a a;
    public static volatile c1 b = null;
    public static Context c = null;
    public static boolean d = false;

    public interface a {
        boolean a(Context context);

        String b(Context context);

        boolean c(Context context);
    }

    public static class b implements a {
        public static String f = null;
        public static boolean g = false;
        public static boolean h = false;
        public static final CountDownLatch i = new CountDownLatch(1);
        public final String a;
        public final String b;
        public final String c;
        public final String d;
        public e e;

        public b(String str, String str2, String str3, String str4) {
            this.a = str;
            this.b = str2;
            this.c = str3;
            this.d = str4;
        }

        public int a() {
            return 1;
        }

        @Override // com.zx.a.I8b7.c1.a
        public boolean a(Context context) {
            if (context == null || TextUtils.isEmpty(this.a)) {
                return false;
            }
            if (this.e == null) {
                this.e = new e(this.d, i);
            }
            Intent intent = new Intent();
            if (TextUtils.isEmpty(this.b)) {
                intent.setPackage(this.a);
            } else {
                intent.setComponent(new ComponentName(this.a, this.b));
            }
            if (!TextUtils.isEmpty(this.c)) {
                intent.setAction(this.c);
            }
            return this.e.a(context, intent);
        }

        public String b() {
            return null;
        }

        @Override // com.zx.a.I8b7.c1.a
        public String b(Context context) {
            e eVar;
            d dVar;
            e eVar2;
            if (!TextUtils.isEmpty(f) || (eVar = this.e) == null || (dVar = eVar.a) == null) {
                return f;
            }
            try {
                String strA = dVar.a(d(context), e(context), b(), a());
                f = strA;
                if (!TextUtils.isEmpty(strA) && (eVar2 = this.e) != null) {
                    context.unbindService(eVar2);
                }
            } catch (Throwable unused) {
            }
            return f;
        }

        @Override // com.zx.a.I8b7.c1.a
        public boolean c(Context context) {
            if (h) {
                return g;
            }
            boolean z = false;
            if (context != null && !TextUtils.isEmpty(this.a)) {
                try {
                    PackageInfo packageInfoA = m3.a(this.a, 0);
                    if (Build.VERSION.SDK_INT >= 28) {
                        if (packageInfoA != null) {
                            if (packageInfoA.getLongVersionCode() >= 1) {
                                return true;
                            }
                        }
                        return false;
                    }
                    if (packageInfoA != null && packageInfoA.versionCode >= 1) {
                        z = true;
                    }
                } catch (Throwable unused) {
                    return false;
                }
            }
            g = z;
            h = true;
            return g;
        }

        public String d(Context context) {
            return null;
        }

        public String e(Context context) {
            return null;
        }
    }

    public static class c implements a {
        public static String e = null;
        public static boolean f = false;
        public String a;
        public String b;
        public String[] c;
        public boolean d = false;

        public c(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        @Override // com.zx.a.I8b7.c1.a
        public boolean a(Context context) {
            return true;
        }

        @Override // com.zx.a.I8b7.c1.a
        public String b(Context context) {
            Cursor cursorQuery;
            if (TextUtils.isEmpty(e)) {
                StringBuilder sbA = j3.a("content://");
                sbA.append(this.a);
                sbA.append("/");
                sbA.append(this.b);
                try {
                    cursorQuery = context.getContentResolver().query(Uri.parse(sbA.toString()), null, null, this.c, null);
                    if (cursorQuery != null) {
                        try {
                            cursorQuery.moveToFirst();
                            e = cursorQuery.getString(cursorQuery.getColumnIndex("value"));
                        } catch (Throwable unused) {
                            try {
                                e = null;
                                return e;
                            } finally {
                                if (cursorQuery != null) {
                                    cursorQuery.close();
                                }
                            }
                        }
                    }
                } catch (Throwable unused2) {
                    cursorQuery = null;
                }
                if (cursorQuery != null) {
                }
            }
            return e;
        }

        @Override // com.zx.a.I8b7.c1.a
        public boolean c(Context context) {
            if (this.d) {
                return f;
            }
            if (context == null) {
                return false;
            }
            try {
                PackageManager packageManagerD = b4.d(context);
                f = (packageManagerD == null || packageManagerD.resolveContentProvider(this.a, 0) == null) ? false : true;
            } catch (Throwable unused) {
                f = false;
            }
            this.d = true;
            return f;
        }
    }

    public static class d implements IInterface {
        public IBinder a;
        public String b;

        public d(IBinder iBinder, String str) {
            this.a = iBinder;
            this.b = str;
        }

        public String a(String str, String str2, String str3, int i) {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken(this.b);
                if (!TextUtils.isEmpty(str)) {
                    parcelObtain.writeString(str);
                }
                if (!TextUtils.isEmpty(str2)) {
                    parcelObtain.writeString(str2);
                }
                if (!TextUtils.isEmpty(str3)) {
                    parcelObtain.writeString(str3);
                }
                this.a.transact(i, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                return parcelObtain2.readString();
            } catch (Throwable unused) {
                try {
                    parcelObtain.recycle();
                    parcelObtain2.recycle();
                    return "";
                } catch (Exception unused2) {
                    return "";
                }
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this.a;
        }
    }

    public static class e implements ServiceConnection {
        public d a;
        public String b;
        public CountDownLatch c;
        public IBinder d;

        public e(String str, CountDownLatch countDownLatch) {
            this.b = str;
            this.c = countDownLatch;
        }

        public boolean a(Context context, Intent intent) {
            d dVar;
            if (this.a != null) {
                return true;
            }
            try {
                boolean zBindService = context.bindService(intent, this, 1);
                this.c.await(1L, TimeUnit.SECONDS);
                IBinder iBinder = this.d;
                String str = this.b;
                if (iBinder == null) {
                    dVar = null;
                } else {
                    IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(str);
                    dVar = iInterfaceQueryLocalInterface instanceof d ? (d) iInterfaceQueryLocalInterface : new d(iBinder, str);
                }
                this.a = dVar;
                return zBindService;
            } catch (Throwable unused) {
                return false;
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.d = iBinder;
                this.c.countDown();
            } catch (Throwable unused) {
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            this.a = null;
            this.d = null;
        }
    }

    public static class f extends b {
        public f() {
            super(a("Y29tLmFzdXMubXNhLlN1cHBsZW1lbnRhcnlESUQ="), a("Y29tLmFzdXMubXNhLlN1cHBsZW1lbnRhcnlESUQuU3VwcGxlbWVudGFyeURJRFNlcnZpY2U="), a("Y29tLmFzdXMubXNhLmFjdGlvbi5BQ0NFU1NfRElE"), a("Y29tLmFzdXMubXNhLlN1cHBsZW1lbnRhcnlESUQuSURpZEFpZGxJbnRlcmZhY2U="));
        }

        public static String a(String str) {
            return new String(Base64.decode(str, 0));
        }

        @Override // com.zx.a.I8b7.c1.b
        public int a() {
            return 2;
        }
    }

    public static class g extends b {
        public g() {
            super("com.coolpad.deviceidsupport", "com.coolpad.deviceidsupport.DeviceIdService", null, "com.coolpad.deviceidsupport.IDeviceIdManager");
        }

        @Override // com.zx.a.I8b7.c1.b
        public int a() {
            return 2;
        }
    }

    public static class h extends b {
        public h() {
            super("com.huawei.hwid", null, "com.uodis.opendevice.OPENIDS_SERVICE", "com.uodis.opendevice.aidl.OpenDeviceIdentifierService");
        }
    }

    public static class i extends c {
        public i() {
            super("com.meizu.flyme.openidsdk", "");
        }

        @Override // com.zx.a.I8b7.c1.c, com.zx.a.I8b7.c1.a
        public String b(Context context) {
            this.c = new String[]{"oaid"};
            return super.b(context);
        }

        @Override // com.zx.a.I8b7.c1.c, com.zx.a.I8b7.c1.a
        public boolean c(Context context) {
            if (super.c(context)) {
                c.f = true;
            } else {
                try {
                    Cursor cursorQuery = context.getContentResolver().query(Uri.parse("content://com.meizu.flyme.openidsdk/"), null, null, new String[]{"support"}, null);
                    if (cursorQuery == null) {
                        return false;
                    }
                    cursorQuery.moveToFirst();
                    int columnIndex = cursorQuery.getColumnIndex("value");
                    if (columnIndex >= 0) {
                        String string = cursorQuery.getString(columnIndex);
                        if (TextUtils.isEmpty(string)) {
                            return false;
                        }
                        c.f = "0".equals(string);
                    } else {
                        c.f = false;
                    }
                } catch (Throwable unused) {
                    c.f = false;
                    return false;
                }
            }
            this.d = true;
            return c.f;
        }
    }

    public static class j implements a {
        public boolean a = false;
        public boolean b = false;
        public String c = null;

        @Override // com.zx.a.I8b7.c1.a
        public boolean a(Context context) {
            return true;
        }

        @Override // com.zx.a.I8b7.c1.a
        public String b(Context context) {
            Bundle bundleCall;
            try {
                if (TextUtils.isEmpty(this.c)) {
                    Uri uri = Uri.parse("content://cn.nubia.identity/identity");
                    int i = Build.VERSION.SDK_INT;
                    if (i >= 17) {
                        ContentProviderClient contentProviderClientAcquireContentProviderClient = context.getContentResolver().acquireContentProviderClient(uri);
                        if (contentProviderClientAcquireContentProviderClient != null) {
                            Bundle bundleCall2 = contentProviderClientAcquireContentProviderClient.call("getOAID", null, null);
                            try {
                                if (i >= 24) {
                                    Class.forName("android.content.ContentProviderClient").getMethod("close", new Class[0]).invoke(contentProviderClientAcquireContentProviderClient, new Object[0]);
                                } else {
                                    contentProviderClientAcquireContentProviderClient.release();
                                }
                            } catch (Throwable unused) {
                            }
                            bundleCall = bundleCall2;
                        } else {
                            bundleCall = null;
                        }
                    } else {
                        bundleCall = context.getContentResolver().call(uri, "getOAID", (String) null, (Bundle) null);
                    }
                    if (bundleCall == null) {
                        return this.c;
                    }
                    if (bundleCall.getInt("code", -1) == 0) {
                        this.c = bundleCall.getString(com.igexin.push.core.b.C);
                    }
                }
            } catch (Throwable unused2) {
                this.c = null;
            }
            return this.c;
        }

        @Override // com.zx.a.I8b7.c1.a
        public boolean c(Context context) {
            if (this.b) {
                return this.a;
            }
            if (context == null) {
                return false;
            }
            try {
                PackageManager packageManagerD = b4.d(context);
                this.a = (packageManagerD == null || packageManagerD.resolveContentProvider("cn.nubia.identity", 0) == null) ? false : true;
            } catch (Throwable unused) {
                this.a = false;
            }
            this.b = true;
            return this.a;
        }
    }

    public static class k extends b {
        public String j;
        public String k;

        public k() {
            super("com.heytap.openid", "com.heytap.openid.IdentifyService", "action.com.heytap.openid.OPEN_ID_SERVICE", "com.heytap.openid.IOpenID");
        }

        @Override // com.zx.a.I8b7.c1.b
        public String b() {
            return "OUID";
        }

        @Override // com.zx.a.I8b7.c1.b
        public String d(Context context) {
            if (TextUtils.isEmpty(this.k)) {
                this.k = context.getPackageName();
            }
            return this.k;
        }

        @Override // com.zx.a.I8b7.c1.b
        @SuppressLint({"PackageManagerGetSignatures"})
        public String e(Context context) {
            if (TextUtils.isEmpty(this.j)) {
                try {
                    if (TextUtils.isEmpty(this.k)) {
                        this.k = context.getPackageName();
                    }
                    String str = this.k;
                    this.k = str;
                    Signature[] signatureArr = m3.a(str, 64).signatures;
                    if (signatureArr != null && signatureArr.length > 0) {
                        byte[] bArrDigest = MessageDigest.getInstance("SHA1").digest(signatureArr[0].toByteArray());
                        StringBuilder sb = new StringBuilder();
                        for (byte b : bArrDigest) {
                            sb.append(Integer.toHexString((b & 255) | 256).substring(1, 3));
                        }
                        this.j = sb.toString();
                    }
                } catch (Throwable unused) {
                }
            }
            return this.j;
        }
    }

    public static class l extends b {
        public l() {
            super("com.samsung.android.deviceidservice", "com.samsung.android.deviceidservice.DeviceIdService", null, "com.samsung.android.deviceidservice.IDeviceIdService");
        }
    }

    public static class m extends c {
        public m() {
            super("com.vivo.vms.IdProvider", "IdentifierId/OAID");
        }
    }

    public static class n implements a {
        public static String b;
        public Class<?> a = null;

        @Override // com.zx.a.I8b7.c1.a
        public boolean a(Context context) {
            return true;
        }

        @Override // com.zx.a.I8b7.c1.a
        public String b(Context context) {
            if (TextUtils.isEmpty(b)) {
                try {
                    b = String.valueOf(this.a.getMethod("getOAID", Context.class).invoke(this.a.newInstance(), context));
                } catch (Throwable unused) {
                    b = null;
                }
            }
            return b;
        }

        @Override // com.zx.a.I8b7.c1.a
        @SuppressLint({"PrivateApi"})
        public boolean c(Context context) {
            try {
                this.a = Class.forName("com.android.id.impl.IdProviderImpl");
                return true;
            } catch (Throwable unused) {
                return false;
            }
        }
    }

    public static class o extends b {
        public o() {
            super("com.zui.deviceidservice", "com.zui.deviceidservice.DeviceidService", null, "com.zui.deviceidservice.IDeviceidInterface");
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0112  */
    static {
        byte b2;
        a oVar;
        String upperCase = Build.MANUFACTURER.toUpperCase();
        upperCase.getClass();
        switch (upperCase.hashCode()) {
            case -2053026509:
                b2 = !upperCase.equals("LENOVO") ? (byte) -1 : (byte) 0;
                break;
            case -1881642058:
                if (upperCase.equals("REALME")) {
                    b2 = 1;
                    break;
                }
                break;
            case -1712043046:
                if (upperCase.equals("SAMSUNG")) {
                    b2 = 2;
                    break;
                }
                break;
            case -1706170181:
                if (upperCase.equals("XIAOMI")) {
                    b2 = 3;
                    break;
                }
                break;
            case -1134767290:
                if (upperCase.equals("BLACKSHARK")) {
                    b2 = 4;
                    break;
                }
                break;
            case -602397472:
                if (upperCase.equals("ONEPLUS")) {
                    b2 = 5;
                    break;
                }
                break;
            case 89198:
                if (upperCase.equals("ZUI")) {
                    b2 = 6;
                    break;
                }
                break;
            case 89200:
                if (upperCase.equals("ZUK")) {
                    b2 = 7;
                    break;
                }
                break;
            case 2018896:
                if (upperCase.equals("ASUS")) {
                    b2 = 8;
                    break;
                }
                break;
            case 2255112:
                if (upperCase.equals("IQOO")) {
                    b2 = 9;
                    break;
                }
                break;
            case 2432928:
                if (upperCase.equals("OPPO")) {
                    b2 = 10;
                    break;
                }
                break;
            case 2634924:
                if (upperCase.equals("VIVO")) {
                    b2 = com.igexin.push.core.b.n.l;
                    break;
                }
                break;
            case 68924490:
                if (upperCase.equals("HONOR")) {
                    b2 = 12;
                    break;
                }
                break;
            case 73239724:
                if (upperCase.equals("MEIZU")) {
                    b2 = 13;
                    break;
                }
                break;
            case 74632627:
                if (upperCase.equals("NUBIA")) {
                    b2 = 14;
                    break;
                }
                break;
            case 77852109:
                if (upperCase.equals("REDMI")) {
                    b2 = 15;
                    break;
                }
                break;
            case 630905871:
                if (upperCase.equals("MOTOLORA")) {
                    b2 = 16;
                    break;
                }
                break;
            case 1670208650:
                if (upperCase.equals("COOLPAD")) {
                    b2 = 17;
                    break;
                }
                break;
            case 1972178256:
                if (upperCase.equals("HUA_WEI")) {
                    b2 = 18;
                    break;
                }
                break;
            case 2141820391:
                if (upperCase.equals("HUAWEI")) {
                    b2 = 19;
                    break;
                }
                break;
        }
        switch (b2) {
            case 0:
            case 6:
            case 7:
            case 16:
                oVar = new o();
                break;
            case 1:
            case 5:
            case 10:
                oVar = new k();
                break;
            case 2:
                oVar = new l();
                break;
            case 3:
            case 4:
            case 15:
                oVar = new n();
                break;
            case 8:
                oVar = new f();
                break;
            case 9:
            case 11:
                oVar = new m();
                break;
            case 12:
            case 18:
            case 19:
                oVar = new h();
                break;
            case 13:
                oVar = new i();
                break;
            case 14:
                oVar = new j();
                break;
            case 17:
                oVar = new g();
                break;
            default:
                oVar = null;
                break;
        }
        a = oVar;
    }

    public static c1 a() {
        if (b == null) {
            synchronized (c1.class) {
                if (b == null) {
                    b = new c1();
                }
            }
        }
        return b;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0018  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String a(Context context) {
        a aVar;
        boolean zC;
        a aVar2 = a;
        if (aVar2 != null && context != null) {
            Context applicationContext = context.getApplicationContext();
            c = applicationContext;
            if (applicationContext == null) {
                zC = false;
                if (zC) {
                    d = a.a(c);
                }
            } else {
                try {
                    zC = aVar2.c(applicationContext);
                } catch (Throwable unused) {
                    zC = false;
                }
                if (zC) {
                }
            }
        }
        boolean z = d;
        if (!z) {
            return null;
        }
        try {
            Context context2 = c;
            if (context2 != null && (aVar = a) != null && z) {
                return aVar.b(context2);
            }
        } catch (Throwable unused2) {
        }
        return null;
    }
}
