package com.gme.trtc.hardwareearmonitor.honor;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import com.gme.trtc.hardwareearmonitor.honor.HonorAudioClient;
import com.gme.trtc.hardwareearmonitor.honor.IHonorAdvancedRecordService;

/* JADX INFO: loaded from: classes.dex */
public class HonorAdvancedRecordClient extends HonorAudioFeaturesKit {
    private static final String ENGINE_CLASS_NAME = "com.hihonor.android.magicx.media.audioengine.HnAdvancedRecordServiceImpl";
    private static final String TAG = "HnAudioService.HnAdvancedRecordClient";
    private Context mContext;
    private HonorFeatureKitManager mFeatureKitManager;
    private boolean mIsServiceConnected = false;
    private IHonorAdvancedRecordService mHnAdvancedRecordService = null;
    private IBinder mService = null;
    private final IBinder mClientBinder = new Binder();
    private ServiceConnection mConnection = new ServiceConnection() { // from class: com.gme.trtc.hardwareearmonitor.honor.HonorAdvancedRecordClient.1
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            HonorAdvancedRecordClient.this.mHnAdvancedRecordService = IHonorAdvancedRecordService.Stub.asInterface(iBinder);
            HonorLogUtils.info(HonorAdvancedRecordClient.TAG, "HnAdvancedRecordClient onServiceConnected");
            if (HonorAdvancedRecordClient.this.mHnAdvancedRecordService != null) {
                HonorAdvancedRecordClient.this.mIsServiceConnected = true;
                HonorLogUtils.info(HonorAdvancedRecordClient.TAG, "HnAdvancedRecordClient onServiceConnected, mIHnAdvancedRecordService is not null");
                HonorAdvancedRecordClient.this.mFeatureKitManager.onCallBack(HonorResultCode.ADVANCED_RECORD_SUCCESS);
                HonorAdvancedRecordClient.this.serviceInit(HonorAdvancedRecordClient.this.mContext.getPackageName());
                HonorAdvancedRecordClient.this.serviceLinkToDeath(iBinder);
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            HonorLogUtils.info(HonorAdvancedRecordClient.TAG, "HnAdvancedRecordClient onServiceDisconnected");
            HonorAdvancedRecordClient.this.mHnAdvancedRecordService = null;
            HonorAdvancedRecordClient.this.mIsServiceConnected = false;
            HonorAdvancedRecordClient.this.mFeatureKitManager.onCallBack(HonorResultCode.ADVANCED_RECORD_DISCONNECTED);
        }
    };
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() { // from class: com.gme.trtc.hardwareearmonitor.honor.HonorAdvancedRecordClient.2
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            HonorAdvancedRecordClient.this.mService.unlinkToDeath(HonorAdvancedRecordClient.this.mDeathRecipient, 0);
            HonorAdvancedRecordClient.this.mFeatureKitManager.onCallBack(6);
            HonorLogUtils.error(HonorAdvancedRecordClient.TAG, "service binder died");
            HonorAdvancedRecordClient.this.mService = null;
        }
    };

    public enum DenoiseLevel {
        DENOISE_DEFAULT_LEVEL(1);

        private final int mDenoiseLevel;

        DenoiseLevel(int i) {
            this.mDenoiseLevel = i;
        }

        public final int getLevel() {
            return this.mDenoiseLevel;
        }
    }

    public enum DenoiseMode {
        DENOISE_NN_MODE(1);

        private final int mDenoiseMode;

        DenoiseMode(int i) {
            this.mDenoiseMode = i;
        }

        public final int getMode() {
            return this.mDenoiseMode;
        }
    }

    public enum DenoiseScene {
        DENOISE_SPEAK_SCENE(1);

        private final int mDenoiseScene;

        DenoiseScene(int i) {
            this.mDenoiseScene = i;
        }

        public final int getScene() {
            return this.mDenoiseScene;
        }
    }

    public HonorAdvancedRecordClient(Context context) {
        this.mFeatureKitManager = null;
        this.mFeatureKitManager = HonorFeatureKitManager.getInstance();
        this.mContext = context;
    }

    private void bindService(Context context) {
        HonorLogUtils.info(TAG, "HnAdvancedRecordClient bindService");
        if (this.mFeatureKitManager == null || this.mIsServiceConnected) {
            return;
        }
        this.mFeatureKitManager.bindService(context, this.mConnection, ENGINE_CLASS_NAME);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void serviceInit(String str) {
        HonorLogUtils.info(TAG, "HnAdvancedRecordClient serviceInit");
        try {
            if (this.mHnAdvancedRecordService == null || !this.mIsServiceConnected) {
                return;
            }
            this.mHnAdvancedRecordService.init(str);
        } catch (Throwable th) {
            HonorLogUtils.error(TAG, "HnAdvancedRecordClient isSupported,RemoteException ex :" + th.getMessage());
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
            this.mFeatureKitManager.onCallBack(HonorResultCode.ADVANCED_RECORD_SERVICE_LINKFAIL);
            HonorLogUtils.error(TAG, "serviceLinkToDeath, RemoteException");
        }
    }

    @Override // com.gme.trtc.hardwareearmonitor.honor.HonorAudioFeaturesKit
    public void destroy() {
        super.destroy();
        HonorLogUtils.info(TAG, "destroy, HnAdvancedRecordClient mIsServiceConnected = " + this.mIsServiceConnected);
        if (this.mIsServiceConnected) {
            this.mIsServiceConnected = false;
            this.mFeatureKitManager.unbindService(this.mContext, this.mConnection);
        }
    }

    public boolean disableAdvancedRecord(Context context) {
        HonorLogUtils.info(TAG, "HnAdvancedRecordClient disableAdvancedRecord mIsServiceConnected=" + this.mIsServiceConnected);
        try {
            if (this.mHnAdvancedRecordService == null || !this.mIsServiceConnected) {
                return false;
            }
            return this.mHnAdvancedRecordService.disableAdvancedRecord();
        } catch (Throwable th) {
            HonorLogUtils.error(TAG, "disableAdvancedRecord failed, RemoteException ex : " + th.getMessage());
            return false;
        }
    }

    public boolean enableAdvancedRecord(Context context) {
        HonorLogUtils.info(TAG, "HnAdvancedRecordClient enableAdvancedRecord");
        try {
            if (this.mHnAdvancedRecordService == null || !this.mIsServiceConnected) {
                return false;
            }
            return this.mHnAdvancedRecordService.enableAdvancedRecord();
        } catch (Throwable th) {
            HonorLogUtils.error(TAG, "enableAdvancedRecord failed, RemoteException ex : " + th.getMessage());
            return false;
        }
    }

    public int enableRecordDenoise(boolean z, DenoiseMode denoiseMode, DenoiseScene denoiseScene, DenoiseLevel denoiseLevel) {
        if (HonorFeatureKitManager.mMinVersion < 1000001) {
            HonorLogUtils.error(TAG, "enable record denoise fail, mix version is " + HonorFeatureKitManager.mMinVersion);
            return HonorResultCode.RECORD_DENOISE_SERVICE_UNSUPPORTED;
        }
        try {
            if (this.mHnAdvancedRecordService == null || !this.mIsServiceConnected) {
                return -2;
            }
            return this.mHnAdvancedRecordService.enableRecordDenoise(z, denoiseMode.getMode(), denoiseScene.getScene(), denoiseLevel.getLevel(), this.mClientBinder);
        } catch (Throwable th) {
            HonorLogUtils.error(TAG, "enableRecordDenoise,RemoteException ex : " + th.getMessage());
            return -2;
        }
    }

    public void initialize(Context context) {
        String str;
        String str2;
        HonorLogUtils.info(TAG, "HnAdvancedRecordClient initialize");
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

    @Override // com.gme.trtc.hardwareearmonitor.honor.HonorAudioFeaturesKit
    public boolean isServiceSupported() {
        HonorLogUtils.info(TAG, "HnAdvancedRecordClient isSupported, type = " + HonorAudioClient.ServiceType.HNAUDIO_SERVICE_ADVANCEDRECORD.getServiceType() + ",mIsServiceConnected=" + this.mIsServiceConnected);
        try {
            if (this.mHnAdvancedRecordService != null && this.mIsServiceConnected) {
                return this.mHnAdvancedRecordService.isSupported(HonorAudioClient.ServiceType.HNAUDIO_SERVICE_ADVANCEDRECORD.getServiceType());
            }
        } catch (Throwable th) {
            HonorLogUtils.error(TAG, "isSupported,RemoteException ex : " + th.getMessage());
        }
        return super.isServiceSupported();
    }
}
