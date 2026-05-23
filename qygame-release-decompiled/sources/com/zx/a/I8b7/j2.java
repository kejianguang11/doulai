package com.zx.a.I8b7;

import android.R;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.gme.liteav.TXLiteAVCode;
import com.zx.a.I8b7.a4;
import com.zx.a.I8b7.p2;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class j2 {
    public static JSONArray b = new JSONArray();
    public final AtomicBoolean a = new AtomicBoolean(false);

    public class a implements Application.ActivityLifecycleCallbacks {

        /* JADX INFO: renamed from: com.zx.a.I8b7.j2$a$a, reason: collision with other inner class name */
        public class RunnableC0061a implements Runnable {
            public RunnableC0061a(a aVar) {
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    p2.a.a.a.b(j2.b.toString());
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
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
            try {
                if (j2.this.a() && j2.b.length() < 200) {
                    String strA = j2.a((ViewGroup) activity.findViewById(R.id.content));
                    if (TextUtils.isEmpty(strA)) {
                        return;
                    }
                    j2.b.put(activity.getClass().getName() + "###" + strA + "###" + (System.currentTimeMillis() / 1000) + "###" + b4.a("6") + "###" + b4.a("7"));
                    a4.f.a.a.execute(new RunnableC0061a(this));
                }
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
        public static final j2 a = new j2();
    }

    public static String a(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        if (childCount >= 10) {
            childCount = 10;
        }
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof WebView) {
                return ((WebView) childAt).getUrl();
            }
            if (childAt instanceof ViewGroup) {
                String strA = a((ViewGroup) childAt);
                if (!TextUtils.isEmpty(strA)) {
                    return strA;
                }
            }
        }
        return null;
    }

    public boolean a() {
        try {
            return new JSONObject(q3.m).has("zxc5");
        } catch (Throwable unused) {
            return false;
        }
    }

    public void b() {
        try {
            if (a() && !this.a.getAndSet(true)) {
                v2.a("zx vb start");
                if (q3.a.getApplicationContext() instanceof Application) {
                    Application application = (Application) q3.a.getApplicationContext();
                    try {
                        b = new JSONArray(p2.a.a.a.a(TXLiteAVCode.WARNING_IGNORE_UPSTREAM_FOR_AUDIENCE));
                    } catch (Throwable unused) {
                        b = new JSONArray();
                    }
                    application.registerActivityLifecycleCallbacks(new a());
                }
            }
        } catch (Throwable unused2) {
        }
    }
}
