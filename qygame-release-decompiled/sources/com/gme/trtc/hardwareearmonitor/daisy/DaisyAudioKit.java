package com.gme.trtc.hardwareearmonitor.daisy;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.gme.liteav.base.util.LiteavLog;
import com.gme.trtc.hardwareearmonitor.daisy.IDaisyAudioEngine;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DaisyAudioKit {
    private static final List<Integer> DEFAULT_FEATURE_LIST = new ArrayList(0);
    private static final String ENGINE_CLASS_NAME = "com.huawei.multimedia.audioengine.HwAudioEngineService";
    private static final String TAG = "DaisyAudioKit.DaisyAudioKit";
    private Context mContext;
    private IDaisyAudioEngine mIHwAudioEngine = null;
    private boolean mIsServiceConnected = false;
    private IBinder mService = null;
    private ServiceConnection mConnection = new ServiceConnection() { // from class: com.gme.trtc.hardwareearmonitor.daisy.DaisyAudioKit.1
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            DaisyAudioKit.this.mIHwAudioEngine = IDaisyAudioEngine.Stub.asInterface(iBinder);
            if (DaisyAudioKit.this.mIHwAudioEngine != null) {
                DaisyAudioKit.this.mIsServiceConnected = true;
                DaisyAudioKit.this.mFeatureKitManager.a(0);
                DaisyAudioKit.this.serviceInit(DaisyAudioKit.this.mContext.getPackageName(), "1.0.1");
                DaisyAudioKit.this.serviceLinkToDeath(iBinder);
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            DaisyAudioKit.this.mIHwAudioEngine = null;
            DaisyAudioKit.this.mIsServiceConnected = false;
            DaisyAudioKit.this.mFeatureKitManager.a(4);
        }
    };
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() { // from class: com.gme.trtc.hardwareearmonitor.daisy.DaisyAudioKit.2
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            DaisyAudioKit.this.mService.unlinkToDeath(DaisyAudioKit.this.mDeathRecipient, 0);
            DaisyAudioKit.this.mFeatureKitManager.a(6);
            LiteavLog.e(DaisyAudioKit.TAG, "service binder died");
            DaisyAudioKit.this.mService = null;
        }
    };
    private DaisyFeatureKitManager mFeatureKitManager = DaisyFeatureKitManager.a();

    public enum FeatureType {
        HWAUDIO_FEATURE_KARAOKE(1);

        private int mFeatureType;

        FeatureType(int i) {
            this.mFeatureType = i;
        }

        public final int getFeatureType() {
            return this.mFeatureType;
        }
    }

    public DaisyAudioKit(Context context, IDaisyAudioKitCallback iDaisyAudioKitCallback) {
        this.mContext = null;
        this.mFeatureKitManager.a(iDaisyAudioKitCallback);
        this.mContext = context;
    }

    private void bindService(Context context) {
        try {
            if (this.mFeatureKitManager == null || this.mIsServiceConnected) {
                return;
            }
            this.mFeatureKitManager.a(context, this.mConnection, ENGINE_CLASS_NAME);
        } catch (Throwable th) {
            LiteavLog.e(TAG, "bindService,RemoteException ex : %s", th.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void serviceInit(String str, String str2) {
        try {
            if (this.mIHwAudioEngine == null || !this.mIsServiceConnected) {
                return;
            }
            this.mIHwAudioEngine.init(str, str2);
        } catch (Throwable th) {
            LiteavLog.e(TAG, "isFeatureSupported,RemoteException ex : %s", th.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void serviceLinkToDeath(IBinder iBinder) {
        this.mService = iBinder;
        try {
            if (this.mService != null) {
                this.mService.linkToDeath(this.mDeathRecipient, 0);
            }
        } catch (Throwable unused) {
            this.mFeatureKitManager.a(5);
            LiteavLog.e(TAG, "serviceLinkToDeath, RemoteException");
        }
    }

    public <T extends DaisyAudioFeaturesKit> T createFeature(FeatureType featureType) {
        if (this.mFeatureKitManager == null || featureType == null) {
            return null;
        }
        return (T) this.mFeatureKitManager.a(featureType.getFeatureType(), this.mContext);
    }

    public void destroy() {
        try {
            if (this.mIsServiceConnected) {
                this.mIsServiceConnected = false;
                this.mFeatureKitManager.a(this.mContext, this.mConnection);
            }
        } catch (Throwable th) {
            LiteavLog.e(TAG, "unbindService,RemoteException ex : %s", th.getMessage());
        }
    }

    public List<Integer> getSupportedFeatures() {
        try {
            if (this.mIHwAudioEngine != null && this.mIsServiceConnected) {
                return this.mIHwAudioEngine.getSupportedFeatures();
            }
        } catch (Throwable unused) {
            LiteavLog.e(TAG, "getSupportedFeatures, createFeature,wait bind service fail");
        }
        return DEFAULT_FEATURE_LIST;
    }

    public void initialize() {
        if (this.mContext == null) {
            this.mFeatureKitManager.a(7);
        } else if (DaisyFeatureKitManager.isAudioKitSupport(this.mContext)) {
            bindService(this.mContext);
        } else {
            this.mFeatureKitManager.a(2);
        }
    }

    public boolean isFeatureSupported(FeatureType featureType) {
        if (featureType == null) {
            return false;
        }
        try {
            if (this.mIHwAudioEngine != null && this.mIsServiceConnected) {
                return this.mIHwAudioEngine.isFeatureSupported(featureType.getFeatureType());
            }
        } catch (Throwable th) {
            LiteavLog.e(TAG, "isFeatureSupported,RemoteException ex : %s", th.getMessage());
        }
        return false;
    }
}
