package com.gme.liteav.audio2;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.os.Process;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.gme.liteav.audio2.c;
import com.gme.liteav.base.ContextUtils;
import com.gme.liteav.base.Log;
import com.gme.liteav.base.util.g;
import com.gme.liteav.base.util.g.a;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public final class e extends PhoneStateListener implements c.a {
    static c c;
    Class<?> d;
    Object e;
    private b h;
    int g = 0;
    private boolean i = false;
    TelephonyManager a = (TelephonyManager) ContextUtils.getApplicationContext().getSystemService("phone");
    AudioManager b = (AudioManager) ContextUtils.getApplicationContext().getSystemService("audio");
    com.gme.liteav.base.util.g f = new com.gme.liteav.base.util.g("PhoneStateManager");

    static class a implements InvocationHandler {
        private final WeakReference<e> a;

        a(e eVar) {
            this.a = new WeakReference<>(eVar);
        }

        @Override // java.lang.reflect.InvocationHandler
        public final Object invoke(Object obj, Method method, Object[] objArr) {
            b bVar;
            try {
                if ("onModeChanged".equals(method.getName())) {
                    int iIntValue = ((Integer) objArr[0]).intValue();
                    e eVar = this.a.get();
                    if (eVar != null && (bVar = eVar.h) != null) {
                        if (iIntValue == 2) {
                            eVar.i = true;
                            bVar.onInterruptedByPhoneCall();
                        } else if (eVar.i) {
                            eVar.i = false;
                            bVar.onResumedByPhoneCall();
                        }
                    }
                }
            } catch (Throwable th) {
                Log.e("PhoneStateManager", "notify mode changed failed, " + th.getMessage(), new Object[0]);
            }
            return obj;
        }
    }

    public interface b {
        void onInterruptedByPhoneCall();

        void onResumedByPhoneCall();
    }

    static {
        if (Build.VERSION.SDK_INT >= 26) {
            c = new c();
        }
    }

    public e(b bVar) {
        this.h = bVar;
    }

    static boolean b() {
        Context applicationContext = ContextUtils.getApplicationContext();
        if (applicationContext == null) {
            return false;
        }
        try {
            return applicationContext.checkPermission("android.permission.READ_PHONE_STATE", Process.myPid(), Process.myUid()) == 0;
        } catch (Throwable th) {
            Log.e("PhoneStateManager", "check permission exception, " + th.getMessage(), new Object[0]);
            return true;
        }
    }

    static void c() {
        if (Build.VERSION.SDK_INT >= 26 && c != null) {
            Log.i("PhoneStateManager", "unregister audio playback callback.", new Object[0]);
            c.a = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        b bVar = this.h;
        if (bVar == null) {
            return;
        }
        try {
            if (this.b.getMode() == 2) {
                this.i = true;
                bVar.onInterruptedByPhoneCall();
            } else if (this.i) {
                this.i = false;
                bVar.onResumedByPhoneCall();
            }
        } catch (Throwable th) {
            Log.e("PhoneStateManager", "get Mode exception, " + th.getMessage(), new Object[0]);
        }
    }

    @Override // com.gme.liteav.audio2.c.a
    public final void a() {
        com.gme.liteav.base.util.g gVar = this.f;
        g.a aVar = gVar.new a(g.a(this));
        synchronized (gVar) {
            gVar.c.add(aVar);
        }
        com.gme.liteav.base.util.g.this.b.postDelayed(aVar.b, aVar.c);
    }

    @Override // android.telephony.PhoneStateListener
    public final void onCallStateChanged(int i, String str) {
        b bVar = this.h;
        if (bVar == null || this.g == i) {
            return;
        }
        this.g = i;
        if (this.g == 2) {
            bVar.onInterruptedByPhoneCall();
        } else if (this.g == 0) {
            bVar.onResumedByPhoneCall();
        }
    }
}
