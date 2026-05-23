package com.gme.trtc.hardwareearmonitor.honor;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.gme.trtc.hardwareearmonitor.honor.HonorAudioClient;
import com.gme.trtc.hardwareearmonitor.honor.IHonorEarReturnService;

/* JADX INFO: loaded from: classes.dex */
public class HonorEarReturnClient extends HonorAudioFeaturesKit {
    private static final String ENGINE_CLASS_NAME = "com.hihonor.android.magicx.media.audioengine.HnEarReturnServiceImpl";
    private static final String TAG = "HnAudioEngine.HnEarReturnClient";
    private Context mContext;
    private HonorFeatureKitManager mFeatureKitManager;
    private IHonorEarReturnService mHnEarReturnService;
    private boolean mIsServiceConnected = false;
    private IBinder mService = null;
    private ServiceConnection mConnection = new ServiceConnection() { // from class: com.gme.trtc.hardwareearmonitor.honor.HonorEarReturnClient.1
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            HonorLogUtils.info(HonorEarReturnClient.TAG, "onServiceConnected");
            HonorEarReturnClient.this.mHnEarReturnService = IHonorEarReturnService.Stub.asInterface(iBinder);
            if (HonorEarReturnClient.this.mHnEarReturnService != null) {
                HonorEarReturnClient.this.mIsServiceConnected = true;
                if (!HonorEarReturnClient.this.serviceInit(HonorEarReturnClient.this.mContext.getPackageName())) {
                    HonorEarReturnClient.this.mFeatureKitManager.onCallBack(1002);
                } else {
                    HonorEarReturnClient.this.serviceLinkToDeath(iBinder);
                    HonorEarReturnClient.this.mFeatureKitManager.onCallBack(1000);
                }
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            HonorLogUtils.info(HonorEarReturnClient.TAG, "onServiceDisconnected");
            HonorEarReturnClient.this.mIsServiceConnected = false;
            if (HonorEarReturnClient.this.mFeatureKitManager != null) {
                HonorEarReturnClient.this.mFeatureKitManager.onCallBack(1001);
            }
        }
    };
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() { // from class: com.gme.trtc.hardwareearmonitor.honor.HonorEarReturnClient.2
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            HonorLogUtils.error(HonorEarReturnClient.TAG, "binderDied");
            HonorEarReturnClient.this.mService.unlinkToDeath(HonorEarReturnClient.this.mDeathRecipient, 0);
            HonorEarReturnClient.this.mFeatureKitManager.onCallBack(1003);
            HonorEarReturnClient.this.mService = null;
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

    public HonorEarReturnClient(Context context) {
        this.mFeatureKitManager = null;
        this.mFeatureKitManager = HonorFeatureKitManager.getInstance();
        this.mContext = context;
    }

    private void bindService(Context context) {
        HonorLogUtils.info(TAG, "bindService");
        try {
            if (this.mFeatureKitManager == null || this.mIsServiceConnected) {
                return;
            }
            this.mFeatureKitManager.bindService(context, this.mConnection, ENGINE_CLASS_NAME);
        } catch (Throwable th) {
            HonorLogUtils.error(TAG, "bindService,RemoteException ex : " + th.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean serviceInit(String str) {
        try {
            if (this.mHnEarReturnService != null && this.mIsServiceConnected) {
                this.mHnEarReturnService.init(str);
            }
            return true;
        } catch (Throwable th) {
            HonorLogUtils.error(TAG, "isSupported,RemoteException ex :" + th.getMessage());
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
                this.mFeatureKitManager.onCallBack(1002);
                HonorLogUtils.error(TAG, "serviceLinkToDeath, RemoteException");
            }
        }
    }

    @Override // com.gme.trtc.hardwareearmonitor.honor.HonorAudioFeaturesKit
    public void destroy() {
        try {
            super.destroy();
            HonorLogUtils.info(TAG, "destroy, mIsServiceConnected = " + this.mIsServiceConnected);
            if (this.mIsServiceConnected) {
                this.mIsServiceConnected = false;
                this.mFeatureKitManager.unbindService(this.mContext, this.mConnection);
            }
        } catch (Throwable th) {
            HonorLogUtils.error(TAG, "bindService,RemoteException ex : " + th.getMessage());
        }
    }

    public int enableEarReturn(boolean z) {
        HonorLogUtils.info(TAG, "enableEarReturn, enable = ".concat(String.valueOf(z)));
        try {
            if (this.mHnEarReturnService == null || !this.mIsServiceConnected) {
                return -2;
            }
            return this.mHnEarReturnService.enableEarReturn(z);
        } catch (Throwable th) {
            HonorLogUtils.error(TAG, "enableEarReturn,RemoteException ex : " + th.getMessage());
            return -2;
        }
    }

    public int getEarReturnLatency() {
        HonorLogUtils.info(TAG, "getEarReturnLatency");
        try {
            if (this.mHnEarReturnService == null || !this.mIsServiceConnected) {
                return -1;
            }
            return this.mHnEarReturnService.getEarReturnLatency();
        } catch (Throwable th) {
            HonorLogUtils.error(TAG, "getEarReturnLatency,RemoteException ex : " + th.getMessage());
            return -1;
        }
    }

    public void initialize(Context context) {
        String str;
        String str2;
        HonorLogUtils.info(TAG, "initialize");
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
        HonorLogUtils.info(TAG, "isSupported, type = " + HonorAudioClient.ServiceType.HNAUDIO_SERVICE_EARRETURN.getServiceType());
        try {
            if (this.mHnEarReturnService != null && this.mIsServiceConnected) {
                return this.mHnEarReturnService.isSupported(HonorAudioClient.ServiceType.HNAUDIO_SERVICE_EARRETURN.getServiceType());
            }
        } catch (Throwable th) {
            HonorLogUtils.error(TAG, "isSupported,RemoteException ex : " + th.getMessage());
        }
        return super.isServiceSupported();
    }

    public int setParameter(ParameName parameName, int i) {
        if (parameName == null) {
            return 1807;
        }
        try {
            HonorLogUtils.info(TAG, "parameValue =" + i + ", parame.getParameName() =" + parameName.getParameName());
            if (this.mHnEarReturnService == null || !this.mIsServiceConnected) {
                return -2;
            }
            return this.mHnEarReturnService.setParameter(parameName.getParameName(), i);
        } catch (Throwable th) {
            HonorLogUtils.error(TAG, "setParameter,RemoteException ex : " + th.getMessage());
            return -2;
        }
    }
}
