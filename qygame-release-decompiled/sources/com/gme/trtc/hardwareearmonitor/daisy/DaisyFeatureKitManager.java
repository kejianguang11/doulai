package com.gme.trtc.hardwareearmonitor.daisy;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import com.gme.liteav.base.util.LiteavLog;

/* JADX INFO: loaded from: classes.dex */
public class DaisyFeatureKitManager {
    private static final String ENGINE_PACKAGE_NAME = "com.huawei.multimedia.audioengine";
    private static final int PACKAGE_INFO_FLAG = 0;
    private static final String TAG = "DaisyAudioKit.DaisyFeatureKitManager";
    private IDaisyAudioKitCallback mCallBack = null;
    private static final Object SET_CALL_BACK_LOCK = new Object();
    private static final Object NEW_FEATUREMANAGER_LOCK = new Object();
    private static final Object BIND_SERVICE_LOCK = new Object();
    private static final Object UNBIND_SERVICE_LOCK = new Object();
    private static DaisyFeatureKitManager sInstance = null;

    protected static DaisyFeatureKitManager a() {
        DaisyFeatureKitManager daisyFeatureKitManager;
        synchronized (NEW_FEATUREMANAGER_LOCK) {
            if (sInstance == null) {
                sInstance = new DaisyFeatureKitManager();
            }
            daisyFeatureKitManager = sInstance;
        }
        return daisyFeatureKitManager;
    }

    public static boolean isAudioKitSupport(Context context) {
        if (context == null) {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return true;
        }
        try {
            return packageManager.getPackageInfo(ENGINE_PACKAGE_NAME, 0) != null;
        } catch (PackageManager.NameNotFoundException unused) {
            LiteavLog.e(TAG, "isAudioKitSupport ,NameNotFoundException");
            return false;
        }
    }

    protected <T extends DaisyAudioFeaturesKit> T a(int i, Context context) {
        if (context == null || i != 1) {
            return null;
        }
        DaisyAudioKaraokeFeatureKit daisyAudioKaraokeFeatureKit = new DaisyAudioKaraokeFeatureKit(context);
        daisyAudioKaraokeFeatureKit.a(context);
        return daisyAudioKaraokeFeatureKit;
    }

    protected void a(int i) {
        synchronized (SET_CALL_BACK_LOCK) {
            if (b() != null) {
                b().onResult(i);
            }
        }
    }

    protected void a(Context context, ServiceConnection serviceConnection) {
        synchronized (UNBIND_SERVICE_LOCK) {
            if (context != null) {
                try {
                    context.unbindService(serviceConnection);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    protected void a(Context context, ServiceConnection serviceConnection, String str) {
        synchronized (BIND_SERVICE_LOCK) {
            try {
                if (context == null) {
                    return;
                }
                Intent intent = new Intent();
                intent.setClassName(ENGINE_PACKAGE_NAME, str);
                try {
                    context.bindService(intent, serviceConnection, 1);
                } catch (SecurityException e) {
                    LiteavLog.e(TAG, "bindService, SecurityException, %s", e.getMessage());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    protected void a(IDaisyAudioKitCallback iDaisyAudioKitCallback) {
        this.mCallBack = iDaisyAudioKitCallback;
    }

    protected IDaisyAudioKitCallback b() {
        return this.mCallBack;
    }
}
