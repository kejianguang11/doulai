package com.gme.liteav.base.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import com.gme.liteav.base.ContextUtils;
import com.gme.liteav.base.Log;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class e implements Application.ActivityLifecycleCallbacks {
    private static final k<Boolean> b = new k<>(f.a());
    public volatile boolean a;
    private volatile WeakReference<Activity> c;
    private volatile Boolean d;
    private volatile a e;
    private final Set<Integer> f;
    private final Set<Integer> g;

    public interface a {
        void a(boolean z);
    }

    static class b {
        private static final e a = new e(0);
    }

    private e() {
        this.c = null;
        this.d = null;
        this.f = new HashSet();
        this.g = new HashSet();
        this.a = false;
        Context applicationContext = ContextUtils.getApplicationContext();
        if (applicationContext == null) {
            Log.e("ProcessLifecycleOwner", "ProcessStateOwner init failed. Context is null", new Object[0]);
        } else {
            ((Application) applicationContext.getApplicationContext()).registerActivityLifecycleCallbacks(this);
        }
    }

    /* synthetic */ e(byte b2) {
        this();
    }

    public static e a() {
        return b.a;
    }

    public static void a(boolean z) {
        b.a(Boolean.valueOf(z));
    }

    private static boolean a(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            if (activityManager == null) {
                Log.e("ProcessLifecycleOwner", "activityManager is null.", new Object[0]);
                return false;
            }
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            if (runningAppProcesses == null) {
                Log.e("ProcessLifecycleOwner", "processInfoList is null.", new Object[0]);
                return false;
            }
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.importance == 100 && context.getPackageName().equals(runningAppProcessInfo.processName)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            Log.e("ProcessLifecycleOwner", "Get App background state failed. ".concat(String.valueOf(e)), new Object[0]);
            return false;
        }
    }

    private void b(Activity activity) {
        this.f.add(Integer.valueOf(activity.hashCode()));
        this.c = new WeakReference<>(activity);
        b(false);
        if (this.a) {
            Log.i("ProcessLifecycleOwner", "update activity to ".concat(String.valueOf(activity)), new Object[0]);
        }
    }

    private synchronized void b(boolean z) {
        if (this.d == null || this.d.booleanValue() != z) {
            this.d = Boolean.valueOf(z);
            b.a(Boolean.valueOf(z));
            if (this.e != null && this.a) {
                this.e.a(this.d.booleanValue());
            }
        }
    }

    public final synchronized void a(Activity activity) {
        if (activity == null) {
            return;
        }
        if (c() != null) {
            Log.i("ProcessLifecycleOwner", "activity is exists, don't need activity from user", new Object[0]);
            return;
        }
        this.c = new WeakReference<>(activity);
        Log.i("ProcessLifecycleOwner", "update activity to " + activity + " from user", new Object[0]);
    }

    public final synchronized void a(a aVar) {
        this.e = aVar;
    }

    public final synchronized boolean b() {
        if (this.d == null) {
            this.d = b.a();
        }
        return this.d.booleanValue();
    }

    public final Activity c() {
        WeakReference<Activity> weakReference = this.c;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final synchronized void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final synchronized void onActivityDestroyed(Activity activity) {
        if (this.a) {
            StringBuilder sb = new StringBuilder("onActivityDestroyed, activity=");
            sb.append(activity);
            sb.append(" mActivity=");
            sb.append(this.c != null ? this.c.get() : "null");
            Log.i("ProcessLifecycleOwner", sb.toString(), new Object[0]);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final synchronized void onActivityPaused(Activity activity) {
        this.g.add(Integer.valueOf(activity.hashCode()));
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final synchronized void onActivityResumed(Activity activity) {
        b(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final synchronized void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final synchronized void onActivityStarted(Activity activity) {
        b(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final synchronized void onActivityStopped(Activity activity) {
        if (this.a) {
            StringBuilder sb = new StringBuilder("onActivityStopped, activity=");
            sb.append(activity);
            sb.append(" mActivity=");
            sb.append(this.c != null ? this.c.get() : "null");
            Log.i("ProcessLifecycleOwner", sb.toString(), new Object[0]);
        }
        int iHashCode = activity.hashCode();
        if (this.f.contains(Integer.valueOf(iHashCode))) {
            this.f.remove(Integer.valueOf(iHashCode));
            b(this.f.size() == 0);
            if (this.c != null && this.c.get() == activity) {
                this.c = null;
            }
        } else if (this.f.size() != 0) {
            b(false);
        } else if (this.g.contains(Integer.valueOf(iHashCode))) {
            b(true);
        }
        this.g.remove(Integer.valueOf(iHashCode));
    }
}
