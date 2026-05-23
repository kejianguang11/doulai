package com.igexin.push.core.c;

import android.app.AppOpsManager;
import android.content.ContentValues;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.text.TextUtils;
import com.igexin.assist.util.AssistUtils;
import com.igexin.push.config.SDKUrlConfig;
import com.igexin.push.config.d;
import com.igexin.push.core.b;
import com.igexin.push.core.b.v;
import com.igexin.push.core.d;
import com.igexin.push.core.e;
import com.igexin.push.core.e.f;
import com.igexin.push.core.e.f.AnonymousClass39;
import com.igexin.push.g.c;
import com.igexin.push.g.l;
import com.igexin.push.g.o;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class a implements com.igexin.push.core.e.a {
    private static final String a = "BIDataManager";
    private static a b;

    /* JADX INFO: renamed from: com.igexin.push.core.c.a$2, reason: invalid class name */
    final class AnonymousClass2 implements Comparator<v> {
        AnonymousClass2() {
        }

        private static int a(v vVar, v vVar2) {
            if (vVar.c.equals(vVar2.c)) {
                return 0;
            }
            return vVar.c.compareTo(vVar2.c);
        }

        @Override // java.util.Comparator
        public final /* synthetic */ int compare(v vVar, v vVar2) {
            v vVar3 = vVar;
            v vVar4 = vVar2;
            if (vVar3.c.equals(vVar4.c)) {
                return 0;
            }
            return vVar3.c.compareTo(vVar4.c);
        }
    }

    private static int a(ApplicationInfo applicationInfo, AppOpsManager appOpsManager, PackageManager packageManager) {
        try {
            if (applicationInfo.packageName.equals(e.g)) {
                return c.b(e.l) ? 1 : 0;
            }
            if (Build.VERSION.SDK_INT < 31 || !d.aa) {
                return -1;
            }
            if (Build.VERSION.SDK_INT >= 33 && applicationInfo.targetSdkVersion >= 33) {
                try {
                    return packageManager.checkPermission("android.permission.POST_NOTIFICATIONS", applicationInfo.packageName) == 0 ? 1 : 0;
                } catch (Throwable unused) {
                    String[] strArr = l.a(applicationInfo.packageName, 4096).requestedPermissions;
                    if (strArr == null || !new HashSet(Arrays.asList(strArr)).contains("android.permission.POST_NOTIFICATIONS")) {
                        return 0;
                    }
                }
            }
            Class<?> cls = Class.forName(AppOpsManager.class.getName());
            return ((Integer) cls.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class).invoke(appOpsManager, Integer.valueOf(((Integer) cls.getDeclaredField("OP_POST_NOTIFICATION").get(Integer.class)).intValue()), Integer.valueOf(applicationInfo.uid), applicationInfo.packageName)).intValue() != 0 ? 0 : 1;
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            return -1;
        }
    }

    private static long a(String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", (Integer) 10);
        contentValues.put(com.alipay.sdk.packet.d.k, str);
        contentValues.put("time", Long.valueOf(System.currentTimeMillis()));
        return d.a.a.i.a(b.ae, contentValues);
    }

    public static a a() {
        if (b == null) {
            b = new a();
        }
        return b;
    }

    static /* synthetic */ void a(a aVar, List list) {
        AnonymousClass2 anonymousClass2 = aVar.new AnonymousClass2();
        AppOpsManager appOpsManager = (AppOpsManager) e.l.getSystemService("appops");
        PackageManager packageManager = e.l.getPackageManager();
        List<PackageInfo> listA = o.a();
        for (int i = 0; i < listA.size(); i++) {
            try {
                PackageInfo packageInfo = listA.get(i);
                ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                if ((applicationInfo.flags & 1) <= 0) {
                    v vVar = new v();
                    vVar.a = applicationInfo.loadLabel(packageManager).toString();
                    vVar.c = applicationInfo.packageName;
                    vVar.b = String.valueOf(packageInfo.versionCode);
                    vVar.d = packageInfo.versionName;
                    vVar.e = a(applicationInfo, appOpsManager, packageManager);
                    list.add(vVar);
                }
            } catch (Exception e) {
                com.igexin.c.a.c.a.a(e);
            }
        }
        Collections.sort(list, anonymousClass2);
    }

    public static void a(String str, String str2) {
        try {
            if (TextUtils.isEmpty(str) || "null".equals(str) || !com.igexin.push.config.d.R.booleanValue()) {
                return;
            }
            if ((System.currentTimeMillis() - e.aw) - (com.igexin.push.config.d.S * 1000) < 0) {
                com.igexin.c.a.c.a.b(a, "type253 in Interval = " + com.igexin.push.config.d.S);
                return;
            }
            Boolean bool = com.igexin.push.config.d.R;
            long j = com.igexin.push.config.d.S;
            long j2 = com.igexin.push.config.d.S;
            com.igexin.c.a.c.a.a(a, "start up id type253Enable = " + com.igexin.push.config.d.R + " ，type253Interval = " + com.igexin.push.config.d.S);
            String strM = o.m();
            String strH = o.h();
            String str3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Long.valueOf(System.currentTimeMillis()));
            StringBuilder sb = new StringBuilder();
            sb.append(str3);
            sb.append("|");
            sb.append(e.C);
            sb.append("|");
            sb.append(e.a);
            sb.append("|");
            sb.append(str);
            sb.append("|");
            if (strH == null || "null".equals(strH)) {
                strH = "";
            }
            sb.append(strH);
            sb.append("|");
            if (strM == null || "null".equals(strM)) {
                strM = "";
            }
            sb.append(strM);
            sb.append("|");
            sb.append(str2);
            sb.append("||ANDROID");
            String string = sb.toString();
            SDKUrlConfig.getBiUploadServiceUrl();
            com.igexin.c.a.c.a.a("BIDataManager| upload253 = ".concat(String.valueOf(string)), new Object[0]);
            byte[] bytes = string.getBytes();
            f.a().d(System.currentTimeMillis());
            com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.f.a.a(new com.igexin.push.core.h.e(SDKUrlConfig.getBiUploadServiceUrl(), bytes, com.igexin.push.config.c.D)), false, true);
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
    }

    private static void a(ArrayList<String> arrayList) {
        d.a.a.i.a(b.ae, new String[]{b.C}, (String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    private void a(List<v> list) {
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        AppOpsManager appOpsManager = (AppOpsManager) e.l.getSystemService("appops");
        PackageManager packageManager = e.l.getPackageManager();
        List<PackageInfo> listA = o.a();
        for (int i = 0; i < listA.size(); i++) {
            try {
                PackageInfo packageInfo = listA.get(i);
                ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                if ((applicationInfo.flags & 1) <= 0) {
                    v vVar = new v();
                    vVar.a = applicationInfo.loadLabel(packageManager).toString();
                    vVar.c = applicationInfo.packageName;
                    vVar.b = String.valueOf(packageInfo.versionCode);
                    vVar.d = packageInfo.versionName;
                    vVar.e = a(applicationInfo, appOpsManager, packageManager);
                    list.add(vVar);
                }
            } catch (Exception e) {
                com.igexin.c.a.c.a.a(e);
            }
        }
        Collections.sort(list, anonymousClass2);
    }

    public static void b() {
        String str;
        String str2;
        if (!c.e()) {
            str = a;
            str2 = " upload type144 network false";
        } else if (com.igexin.push.config.d.V) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if ((jCurrentTimeMillis - e.ay) - (com.igexin.push.config.d.W * 1000) >= 0) {
                try {
                    String[] strArrB = com.igexin.assist.sdk.a.a().b();
                    StringBuilder sb = new StringBuilder();
                    sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
                    sb.append("|");
                    sb.append(e.A);
                    sb.append("|");
                    sb.append(e.a);
                    sb.append("|");
                    sb.append(e.C);
                    sb.append("|");
                    sb.append(com.igexin.push.config.d.T ? o.q() : "");
                    sb.append("|");
                    sb.append(o.d());
                    sb.append("|");
                    sb.append(AssistUtils.getDeviceBrand().toLowerCase());
                    sb.append("|");
                    sb.append(strArrB[0]);
                    sb.append("|");
                    sb.append(strArrB[1]);
                    sb.append("|");
                    sb.append(o.n());
                    sb.append("|");
                    sb.append(o.o());
                    f.a().e(jCurrentTimeMillis);
                    com.igexin.c.a.c.a.b("UploadBITask", "upload type144 data = " + sb.toString());
                    com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.f.a.a(new com.igexin.push.core.h.e(SDKUrlConfig.getBiUploadServiceUrl(), sb.toString().getBytes(), com.igexin.push.config.c.E)), false, true);
                    return;
                } catch (Throwable th) {
                    com.igexin.c.a.c.a.a(th);
                    return;
                }
            }
            str = a;
            str2 = "type144 in Interval = " + com.igexin.push.config.d.W;
        } else {
            str = a;
            str2 = " isUpload type144 Enable false";
        }
        com.igexin.c.a.c.a.b(str, str2);
    }

    private static void b(ArrayList<String> arrayList) {
        d.a.a.i.a(b.ae, new String[]{"rowid"}, (String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v2, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r1v3, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8, types: [boolean] */
    public static List<com.igexin.push.core.b.c> c() throws Throwable {
        ArrayList arrayList = new ArrayList();
        ?? MoveToNext = 0;
        MoveToNext = 0;
        MoveToNext = 0;
        try {
            try {
                Cursor cursorA = d.a.a.i.a(b.ae, new String[]{"type"}, new String[]{"10"}, null, "");
                if (cursorA != null) {
                    while (true) {
                        try {
                            MoveToNext = cursorA.moveToNext();
                            if (MoveToNext == 0) {
                                break;
                            }
                            arrayList.add(new com.igexin.push.core.b.c(cursorA.getInt(0), cursorA.getString(1), cursorA.getInt(2), cursorA.getLong(3)));
                        } catch (Throwable th) {
                            th = th;
                            MoveToNext = cursorA;
                            if (MoveToNext != 0) {
                                MoveToNext.close();
                            }
                            throw th;
                        }
                    }
                }
                if (cursorA != null) {
                    cursorA.close();
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0085  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void d() throws Throwable {
        Cursor cursorA;
        Throwable th;
        Throwable th2;
        Cursor cursor = null;
        try {
            try {
                cursorA = d.a.a.i.a(b.ae, null, null, new String[]{"COUNT(*)"}, null);
                if (cursorA == null) {
                    if (cursorA != null) {
                        cursorA.close();
                        return;
                    }
                    return;
                }
                try {
                    cursorA.moveToNext();
                    long j = cursorA.getLong(0);
                    cursorA.close();
                    long j2 = j - 200;
                    if (j2 > 0) {
                        d.a.a.i.a(b.ae, "id IN(SELECT id FROM bidata ORDER BY time ASC LIMIT " + j2 + ")");
                        com.igexin.c.a.c.a.b(a, "delete bidata " + j2 + " old expired data");
                    }
                    if (cursorA != null) {
                        cursorA.close();
                    }
                } catch (Throwable th3) {
                    th = th3;
                    com.igexin.c.a.c.a.a(th);
                    if (cursorA != null) {
                        cursorA.close();
                    }
                }
            } catch (Throwable th4) {
                th2 = th4;
                if (0 != 0) {
                    cursor.close();
                }
                throw th2;
            }
        } catch (Throwable th5) {
            cursorA = null;
            th = th5;
        }
    }

    @Override // com.igexin.push.core.e.a
    public final void a(SQLiteDatabase sQLiteDatabase) {
    }

    public final void a(boolean z) {
        long jCurrentTimeMillis = System.currentTimeMillis() - e.R;
        if (!z || jCurrentTimeMillis - b.J >= 0) {
            StringBuilder sb = new StringBuilder(a);
            sb.append(z ? ", over 24h, start upload AL" : "data change start upload AL");
            com.igexin.c.a.c.a.a(sb.toString(), new Object[0]);
            com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.f.d() { // from class: com.igexin.push.core.c.a.1
                @Override // com.igexin.push.f.d
                public final void b() {
                    try {
                        f fVarA = f.a();
                        long jCurrentTimeMillis2 = System.currentTimeMillis();
                        if (jCurrentTimeMillis2 != e.R) {
                            e.R = jCurrentTimeMillis2;
                            com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) fVarA.new AnonymousClass39(), false, true);
                        }
                        ArrayList arrayList = new ArrayList();
                        a.a(a.this, arrayList);
                        if (arrayList.isEmpty()) {
                            return;
                        }
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put("action", "reportapplist");
                            jSONObject.put("session_last", e.z);
                            JSONArray jSONArray = new JSONArray();
                            int size = arrayList.size();
                            for (int i = 0; i < size; i++) {
                                JSONObject jSONObject2 = new JSONObject();
                                jSONObject2.put("appid", ((v) arrayList.get(i)).c);
                                jSONObject2.put("name", ((v) arrayList.get(i)).a);
                                jSONObject2.put("version", ((v) arrayList.get(i)).b);
                                jSONObject2.put("versionName", ((v) arrayList.get(i)).d);
                                jSONObject2.put("notificationEnabled", ((v) arrayList.get(i)).e);
                                jSONArray.put(jSONObject2);
                            }
                            jSONObject.put("applist", jSONArray);
                        } catch (Exception e) {
                            com.igexin.c.a.c.a.a(e);
                        }
                        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.f.a.a(new com.igexin.push.core.h.a(SDKUrlConfig.getBiUploadServiceUrl(), jSONObject.toString().getBytes())), false, true);
                        com.igexin.c.a.c.a.a("reportAL", new Object[0]);
                        if (com.igexin.push.config.d.ac) {
                            com.igexin.c.a.c.a.a("reportAL = " + jSONObject.toString(), new Object[0]);
                        }
                    } catch (Throwable th) {
                        com.igexin.c.a.c.a.a(th);
                    }
                }
            }, false, true);
        }
    }

    @Override // com.igexin.push.core.e.a
    public final void b(SQLiteDatabase sQLiteDatabase) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0085  */
    /* JADX WARN: Type inference failed for: r8v0, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5, types: [android.database.Cursor] */
    @Override // com.igexin.push.core.e.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void c(SQLiteDatabase sQLiteDatabase) throws Throwable {
        Cursor cursorA;
        Throwable th;
        Throwable th2;
        try {
            try {
                cursorA = d.a.a.i.a(b.ae, null, null, new String[]{"COUNT(*)"}, null);
                if (cursorA == null) {
                    if (cursorA != null) {
                        cursorA.close();
                        return;
                    }
                    return;
                }
                try {
                    cursorA.moveToNext();
                    long j = cursorA.getLong(0);
                    cursorA.close();
                    long j2 = j - 200;
                    if (j2 > 0) {
                        d.a.a.i.a(b.ae, "id IN(SELECT id FROM bidata ORDER BY time ASC LIMIT " + j2 + ")");
                        com.igexin.c.a.c.a.b(a, "delete bidata " + j2 + " old expired data");
                    }
                    if (cursorA != null) {
                        cursorA.close();
                    }
                } catch (Throwable th3) {
                    th = th3;
                    com.igexin.c.a.c.a.a(th);
                    if (cursorA != null) {
                        cursorA.close();
                    }
                }
            } catch (Throwable th4) {
                th2 = th4;
                if (sQLiteDatabase != 0) {
                    sQLiteDatabase.close();
                }
                throw th2;
            }
        } catch (Throwable th5) {
            cursorA = null;
            th = th5;
        }
    }
}
