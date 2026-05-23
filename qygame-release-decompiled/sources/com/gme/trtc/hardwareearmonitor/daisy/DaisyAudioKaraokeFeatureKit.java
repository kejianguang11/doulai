package com.gme.trtc.hardwareearmonitor.daisy;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.gme.liteav.base.util.LiteavLog;
import com.gme.trtc.hardwareearmonitor.daisy.IDaisyAudioKaraokeFeature;

/* JADX INFO: loaded from: classes.dex */
public class DaisyAudioKaraokeFeatureKit extends DaisyAudioFeaturesKit {
    private static final String ENGINE_CLASS_NAME = "com.huawei.multimedia.audioengine.HwAudioKaraokeFeatureService";
    private static final String TAG = "DaisyAudioKit.DaisyAudioKaraokeFeatureKit";
    private Context mContext;
    private DaisyFeatureKitManager mFeatureKitManager;
    private IDaisyAudioKaraokeFeature mIHwAudioKaraokeFeatureAidl;
    private boolean mIsServiceConnected = false;
    private IBinder mService = null;
    private ServiceConnection mConnection = new ServiceConnection() { // from class: com.gme.trtc.hardwareearmonitor.daisy.DaisyAudioKaraokeFeatureKit.1
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            DaisyAudioKaraokeFeatureKit.this.mIHwAudioKaraokeFeatureAidl = IDaisyAudioKaraokeFeature.Stub.asInterface(iBinder);
            if (DaisyAudioKaraokeFeatureKit.this.mIHwAudioKaraokeFeatureAidl != null) {
                DaisyAudioKaraokeFeatureKit.this.mIsServiceConnected = true;
                if (!DaisyAudioKaraokeFeatureKit.this.serviceInit(DaisyAudioKaraokeFeatureKit.this.mContext.getPackageName())) {
                    DaisyAudioKaraokeFeatureKit.this.mFeatureKitManager.a(1002);
                } else {
                    DaisyAudioKaraokeFeatureKit.this.mFeatureKitManager.a(1000);
                    DaisyAudioKaraokeFeatureKit.this.serviceLinkToDeath(iBinder);
                }
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            DaisyAudioKaraokeFeatureKit.this.mIsServiceConnected = false;
            if (DaisyAudioKaraokeFeatureKit.this.mFeatureKitManager != null) {
                DaisyAudioKaraokeFeatureKit.this.mFeatureKitManager.a(1001);
            }
        }
    };
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() { // from class: com.gme.trtc.hardwareearmonitor.daisy.DaisyAudioKaraokeFeatureKit.2
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            LiteavLog.e(DaisyAudioKaraokeFeatureKit.TAG, "binderDied");
            DaisyAudioKaraokeFeatureKit.this.mService.unlinkToDeath(DaisyAudioKaraokeFeatureKit.this.mDeathRecipient, 0);
            DaisyAudioKaraokeFeatureKit.this.mFeatureKitManager.a(1003);
            DaisyAudioKaraokeFeatureKit.this.mService = null;
        }
    };

    public enum ParameName {
        CMD_SET_AUDIO_EFFECT_MODE_BASE("Karaoke_reverb_mode="),
        CMD_SET_VOCAL_VOLUME_BASE("Karaoke_volume="),
        CMD_SET_VOCAL_EQUALIZER_MODE("Karaoke_eq_mode=");

        private String mParameName;

        ParameName(String str) {
            this.mParameName = str;
        }

        public final String getParameName() {
            return this.mParameName;
        }
    }

    protected DaisyAudioKaraokeFeatureKit(Context context) {
        this.mFeatureKitManager = null;
        this.mFeatureKitManager = DaisyFeatureKitManager.a();
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
    public boolean serviceInit(String str) {
        try {
            if (this.mIHwAudioKaraokeFeatureAidl != null && this.mIsServiceConnected) {
                this.mIHwAudioKaraokeFeatureAidl.init(str);
            }
            return true;
        } catch (Throwable th) {
            LiteavLog.e(TAG, "isFeatureSupported,RemoteException ex : %s", th.getMessage());
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void serviceLinkToDeath(IBinder iBinder) {
        this.mService = iBinder;
        if (iBinder != null) {
            try {
                iBinder.linkToDeath(this.mDeathRecipient, 0);
            } catch (Throwable unused) {
                this.mFeatureKitManager.a(1002);
                LiteavLog.e(TAG, "serviceLinkToDeath, RemoteException");
            }
        }
    }

    protected void a(Context context) {
        if (context == null) {
            return;
        }
        if (DaisyFeatureKitManager.isAudioKitSupport(context)) {
            bindService(context);
        } else {
            this.mFeatureKitManager.a(2);
        }
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

    public int enableKaraokeFeature(boolean z) {
        try {
            if (this.mIHwAudioKaraokeFeatureAidl == null || !this.mIsServiceConnected) {
                return -2;
            }
            return this.mIHwAudioKaraokeFeatureAidl.enableKaraokeFeature(z);
        } catch (Throwable th) {
            LiteavLog.e(TAG, "enableKaraokeFeature,RemoteException ex : %s", th.getMessage());
            return -2;
        }
    }

    public int getKaraokeLatency() {
        try {
            if (this.mIHwAudioKaraokeFeatureAidl == null || !this.mIsServiceConnected) {
                return -1;
            }
            return this.mIHwAudioKaraokeFeatureAidl.getKaraokeLatency();
        } catch (Throwable th) {
            LiteavLog.e(TAG, "getKaraokeLatency,RemoteException ex : %s", th.getMessage());
            return -1;
        }
    }

    public boolean isKaraokeFeatureSupport() {
        try {
            if (this.mIHwAudioKaraokeFeatureAidl != null && this.mIsServiceConnected) {
                return this.mIHwAudioKaraokeFeatureAidl.isKaraokeFeatureSupport();
            }
        } catch (Throwable th) {
            LiteavLog.e(TAG, "isFeatureSupported,RemoteException ex : %s", th.getMessage());
        }
        return false;
    }

    public int setParameter(ParameName parameName, int i) {
        if (parameName == null) {
            return 1807;
        }
        try {
            if (this.mIHwAudioKaraokeFeatureAidl == null || !this.mIsServiceConnected) {
                return -2;
            }
            return this.mIHwAudioKaraokeFeatureAidl.setParameter(parameName.getParameName(), i);
        } catch (Throwable th) {
            LiteavLog.e(TAG, "setParameter,RemoteException ex : %s", th.getMessage());
            return -2;
        }
    }
}
