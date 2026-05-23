package com.gme.trtc.hardwareearmonitor.honor;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.gme.trtc.hardwareearmonitor.honor.IHonorAudioPlayService;

/* JADX INFO: loaded from: classes.dex */
public class HonorAudioPlayClient extends HonorAudioFeaturesKit {
    private static final String ENGINE_CLASS_NAME = "com.hihonor.android.magicx.media.audioengine.HnAudioPlayServiceImpl";
    private static final String TAG = "HnAudioEngine.HnAudioPlayClient";
    private Context mContext;
    private HonorFeatureKitManager mFeatureKitManager;
    private IHonorAudioPlayService mHnAudioPlayService;
    private boolean mIsServiceConnected = false;
    private IBinder mService = null;
    private ServiceConnection mConnection = new ServiceConnection() { // from class: com.gme.trtc.hardwareearmonitor.honor.HonorAudioPlayClient.1
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            HonorLogUtils.info(HonorAudioPlayClient.TAG, "onServiceConnected");
            HonorAudioPlayClient.this.mHnAudioPlayService = IHonorAudioPlayService.Stub.asInterface(iBinder);
            if (HonorAudioPlayClient.this.mHnAudioPlayService != null) {
                HonorAudioPlayClient.this.mIsServiceConnected = true;
                HonorAudioPlayClient.this.serviceInit(HonorAudioPlayClient.this.mContext.getPackageName());
                HonorAudioPlayClient.this.serviceLinkToDeath(iBinder);
                HonorAudioPlayClient.this.mFeatureKitManager.onCallBack(3000);
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            HonorLogUtils.info(HonorAudioPlayClient.TAG, "onServiceDisconnected");
            HonorAudioPlayClient.this.mIsServiceConnected = false;
            if (HonorAudioPlayClient.this.mFeatureKitManager != null) {
                HonorAudioPlayClient.this.mFeatureKitManager.onCallBack(HonorResultCode.AUDIO_PLAY_SERVICE_DISCONNECTED);
            }
        }
    };
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() { // from class: com.gme.trtc.hardwareearmonitor.honor.HonorAudioPlayClient.2
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            HonorAudioPlayClient.this.mService.unlinkToDeath(HonorAudioPlayClient.this.mDeathRecipient, 0);
            HonorAudioPlayClient.this.mFeatureKitManager.onCallBack(HonorResultCode.AUDIO_PLAY_SERVICE_DIED);
            HonorLogUtils.error(HonorAudioPlayClient.TAG, "service binder died");
            HonorAudioPlayClient.this.mService = null;
        }
    };

    public HonorAudioPlayClient(Context context) {
        this.mFeatureKitManager = null;
        this.mFeatureKitManager = HonorFeatureKitManager.getInstance();
        this.mContext = context;
    }

    private void bindService(Context context) {
        HonorLogUtils.info(TAG, "bindService");
        if (this.mFeatureKitManager == null || this.mIsServiceConnected) {
            return;
        }
        this.mFeatureKitManager.bindService(context, this.mConnection, ENGINE_CLASS_NAME);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void serviceInit(String str) {
        HonorLogUtils.info(TAG, "HnAudioPlayClient serviceInit");
        try {
            if (this.mHnAudioPlayService == null || !this.mIsServiceConnected) {
                return;
            }
            this.mHnAudioPlayService.init(str);
        } catch (Throwable th) {
            HonorLogUtils.error(TAG, "HnAudioPlayClient isSupported,RemoteException ex :" + th.getMessage());
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
            this.mFeatureKitManager.onCallBack(HonorResultCode.AUDIO_PLAY_SERVICE_LINKFAILED);
            HonorLogUtils.error(TAG, "serviceLinkToDeath, RemoteException");
        }
    }

    @Override // com.gme.trtc.hardwareearmonitor.honor.HonorAudioFeaturesKit
    public void destroy() {
        super.destroy();
        HonorLogUtils.info(TAG, "destroy, mIsServiceConnected = " + this.mIsServiceConnected);
        if (this.mIsServiceConnected) {
            this.mIsServiceConnected = false;
            this.mFeatureKitManager.unbindService(this.mContext, this.mConnection);
        }
    }

    public void enableHighSampleRatePlay(boolean z) {
        if (HonorFeatureKitManager.mMinVersion < 1000001) {
            HonorLogUtils.error(TAG, "not support high sample rate play service. The mix version is " + HonorFeatureKitManager.mMinVersion);
            this.mFeatureKitManager.onCallBack(HonorResultCode.HIGHSAMPLERATE_PLAY_SERVICE_UNSUPPORTED);
            return;
        }
        HonorLogUtils.info(TAG, "enableHighSampleRatePlay, enable = ".concat(String.valueOf(z)));
        try {
            if (this.mHnAudioPlayService == null || !this.mIsServiceConnected) {
                return;
            }
            this.mHnAudioPlayService.enableHighSampleRatePlay(z);
        } catch (Throwable th) {
            HonorLogUtils.error(TAG, "enableHighSampleRatePlay,RemoteException ex : " + th.getMessage());
        }
    }

    public void initialize(Context context) {
        String str;
        String str2;
        if (context == null) {
            str = TAG;
            str2 = "initialize, context is null";
        } else if (HonorFeatureKitManager.isAudioKitSupport(context)) {
            bindService(context);
            return;
        } else {
            this.mFeatureKitManager.onCallBack(2);
            str = TAG;
            str2 = "initialize, not install AudioEngine";
        }
        HonorLogUtils.info(str, str2);
    }
}
