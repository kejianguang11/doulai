package com.zx.a.I8b7;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.zx.a.I8b7.a4;
import com.zx.a.I8b7.p2;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class j1 {
    public static volatile a2 i;
    public static volatile a2 j;
    public volatile int a;
    public volatile int b;
    public final AtomicBoolean c = new AtomicBoolean(false);
    public volatile String d;
    public volatile long e;
    public volatile long f;
    public volatile long g;
    public b2 h;

    public class a implements Application.ActivityLifecycleCallbacks {

        /* JADX INFO: renamed from: com.zx.a.I8b7.j1$a$a, reason: collision with other inner class name */
        public class RunnableC0060a implements Runnable {
            public RunnableC0060a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    j1.a(j1.this);
                } catch (Throwable unused) {
                }
            }
        }

        public a() {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
            try {
                a4.f.a.e.execute(new RunnableC0060a());
            } catch (Throwable unused) {
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            try {
                j1 j1Var = j1.this;
                j1Var.e = j1Var.a();
                j1 j1Var2 = j1.this;
                j1Var2.f = j1Var2.a();
                j1.this.d = activity.getClass().getName();
            } catch (Throwable unused) {
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
        }
    }

    public static final class b {
        public static final j1 a = new j1();
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00b6 A[Catch: all -> 0x00ee, TryCatch #0 {all -> 0x00ee, blocks: (B:3:0x0003, B:6:0x0009, B:9:0x001d, B:13:0x0035, B:15:0x0039, B:17:0x0055, B:21:0x00a5, B:23:0x00b6, B:24:0x00bb, B:27:0x00ce, B:18:0x006f, B:19:0x0085, B:20:0x0089), top: B:32:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00ce A[Catch: all -> 0x00ee, TRY_LEAVE, TryCatch #0 {all -> 0x00ee, blocks: (B:3:0x0003, B:6:0x0009, B:9:0x001d, B:13:0x0035, B:15:0x0039, B:17:0x0055, B:21:0x00a5, B:23:0x00b6, B:24:0x00bb, B:27:0x00ce, B:18:0x006f, B:19:0x0085, B:20:0x0089), top: B:32:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void a(j1 j1Var) throws Throwable {
        a2 a2Var;
        String str;
        int length;
        a2 a2Var2;
        j1Var.getClass();
        try {
            if (j != null && j.toString().getBytes(StandardCharsets.UTF_8).length <= 512000) {
                Object objOpt = j1Var.h.opt(j1Var.d);
                long j2 = j1Var.f - j1Var.e;
                if (j2 <= 0) {
                    j2 = 1;
                }
                if (objOpt == null || !(objOpt instanceof a2)) {
                    a2Var = new a2();
                    str = j1Var.e + "#" + j2;
                } else {
                    a2Var = (a2) objOpt;
                    int iA = a2Var.a(j1Var.e + "");
                    if (iA != -1) {
                        a2Var.put(iA, j1Var.e + "#" + j2);
                        j1Var.h.put(j1Var.d, a2Var);
                        length = j.length() - 1;
                        if (length >= 0) {
                            j.remove(length);
                        }
                        j.put(j1Var.h);
                        p2 p2Var = p2.a.a;
                        z3 z3Var = p2Var.a;
                        a2Var2 = j;
                        z3Var.getClass();
                        if (a2Var2 != null) {
                            return;
                        }
                        p2Var.a.a(62, a2Var2.toString(), true);
                        v2.a("TABRt had changed refresh:" + a2Var2);
                        return;
                    }
                    str = j1Var.e + "#" + j2;
                }
                a2Var.put(str);
                j1Var.h.put(j1Var.d, a2Var);
                length = j.length() - 1;
                if (length >= 0) {
                }
                j.put(j1Var.h);
                p2 p2Var2 = p2.a.a;
                z3 z3Var2 = p2Var2.a;
                a2Var2 = j;
                z3Var2.getClass();
                if (a2Var2 != null) {
                }
            }
        } catch (Throwable th) {
            StringBuilder sbA = j3.a("dealTabRT ex:");
            sbA.append(th.getMessage());
            v2.b(sbA.toString());
        }
    }

    public static void a(j1 j1Var, long j2, long j3) throws Throwable {
        j1Var.getClass();
        long j4 = j3 - j2;
        try {
            int length = i.length() - 1;
            if (length >= 0) {
                i.put(length, j2 + "#" + j4);
            }
            p2 p2Var = p2.a.a;
            z3 z3Var = p2Var.a;
            a2 a2Var = i;
            z3Var.getClass();
            if (a2Var == null) {
                return;
            }
            p2Var.a.a(61, a2Var.toString(), true);
            v2.a("appRt had changed refresh:" + a2Var);
        } catch (Throwable th) {
            StringBuilder sbA = j3.a("dealAppRT ex:");
            sbA.append(th.getMessage());
            v2.b(sbA.toString());
        }
    }

    public final long a() {
        return System.currentTimeMillis() / 1000;
    }

    public final void a(long j2) throws JSONException {
        String strA = p2.a.a.a.a(61);
        if (TextUtils.isEmpty(strA)) {
            i = new a2();
        } else {
            i = new a2(strA);
        }
        StringBuilder sbA = j3.a("read appRt = ");
        sbA.append(i);
        v2.a(sbA.toString());
        i.put(j2 + "#0");
        this.a = i.length();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void b() throws JSONException {
        String str;
        List<ResolveInfo> listQueryIntentActivities;
        if (q3.a.getApplicationContext() instanceof Application) {
            Application application = (Application) q3.a.getApplicationContext();
            this.h = new b2();
            this.e = a();
            this.f = a();
            try {
                PackageManager packageManagerD = b4.d(q3.a);
                Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
                intent.addCategory("android.intent.category.LAUNCHER");
                intent.setPackage(q3.g);
                listQueryIntentActivities = packageManagerD.queryIntentActivities(intent, 0);
            } catch (Exception e) {
                v2.a(e);
            }
            if (listQueryIntentActivities == null || listQueryIntentActivities.isEmpty()) {
                str = "ZX_MainActivity";
            } else {
                Iterator<ResolveInfo> it = listQueryIntentActivities.iterator();
                if (it.hasNext()) {
                    str = it.next().activityInfo.name;
                }
            }
            this.d = str;
            String strA = p2.a.a.a.a(62);
            if (TextUtils.isEmpty(strA)) {
                j = new a2();
            } else {
                j = new a2(strA);
            }
            StringBuilder sbA = j3.a("read tabRT = ");
            sbA.append(j);
            v2.a(sbA.toString());
            j.put(this.h);
            this.b = j.length();
            application.registerActivityLifecycleCallbacks(new a());
        }
    }
}
