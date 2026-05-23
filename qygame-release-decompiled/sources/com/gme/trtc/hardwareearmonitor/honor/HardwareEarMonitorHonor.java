package com.gme.trtc.hardwareearmonitor.honor;

import android.content.Context;
import android.media.AudioManager;
import com.gme.liteav.base.ContextUtils;
import com.gme.liteav.base.annotations.JNINamespace;
import com.gme.liteav.base.util.g;
import com.gme.trtc.hardwareearmonitor.honor.HonorAudioClient;
import com.gme.trtc.hardwareearmonitor.honor.HonorEarReturnClient;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav::extensions")
public class HardwareEarMonitorHonor implements IHonorAudioServiceCallback {
    private static final String TAG = "HardwareEarMonitorHonor";
    private HonorEarReturnClient mHnEarReturnClient;
    private long mNativeHardwareEarMonitorHandle;
    private Object mLock = new Object();
    private AudioManager mAudioManager = (AudioManager) ContextUtils.getApplicationContext().getSystemService("audio");
    private Context mContext = ContextUtils.getApplicationContext();
    private g mTaskRunner = new g();
    private HonorAudioClient mHnAudioClient = new HonorAudioClient(this.mContext, this);

    public HardwareEarMonitorHonor(long j) {
        this.mNativeHardwareEarMonitorHandle = 0L;
        this.mNativeHardwareEarMonitorHandle = j;
        this.mHnAudioClient.initialize();
    }

    public static HardwareEarMonitorHonor create(long j) {
        return new HardwareEarMonitorHonor(j);
    }

    public static boolean isAudioKitSupport() {
        return HonorAudioClient.isDeviceSupported(ContextUtils.getApplicationContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeHandleResult(long j, int i);

    public void createKaraokeService() {
        this.mHnEarReturnClient = (HonorEarReturnClient) this.mHnAudioClient.createService(HonorAudioClient.ServiceType.HNAUDIO_SERVICE_EARRETURN);
    }

    public void destroy() {
        synchronized (this.mLock) {
            this.mNativeHardwareEarMonitorHandle = 0L;
        }
        if (this.mHnAudioClient != null) {
            this.mHnAudioClient.destroy();
            this.mHnAudioClient = null;
        }
        if (this.mHnEarReturnClient != null) {
            this.mHnEarReturnClient.destroy();
            this.mHnEarReturnClient = null;
        }
    }

    public int enableKaraoke(boolean z) {
        if (this.mHnEarReturnClient == null) {
            return 1806;
        }
        return this.mHnEarReturnClient.enableEarReturn(z);
    }

    public int getKaraokeLatency() {
        if (this.mHnEarReturnClient == null) {
            return -1;
        }
        try {
            return this.mHnEarReturnClient.getEarReturnLatency();
        } catch (Throwable unused) {
            return -1;
        }
    }

    public int[] getKaraokeSupportedServices() {
        List<Integer> supportedServices = this.mHnAudioClient.getSupportedServices();
        if (supportedServices == null) {
            return null;
        }
        int[] iArr = new int[supportedServices.size()];
        for (int i = 0; i < supportedServices.size(); i++) {
            iArr[i] = supportedServices.get(i).intValue();
        }
        return iArr;
    }

    public boolean isHardwareEarMonitorSupported() {
        if (this.mHnEarReturnClient == null) {
            return false;
        }
        return this.mHnEarReturnClient.isServiceSupported();
    }

    public boolean isKaraokeServiceSupport() {
        return this.mHnAudioClient.isServiceSupported(HonorAudioClient.ServiceType.HNAUDIO_SERVICE_EARRETURN);
    }

    @Override // com.gme.trtc.hardwareearmonitor.honor.IHonorAudioServiceCallback
    public void onResult(final int i) {
        this.mTaskRunner.a(new Runnable() { // from class: com.gme.trtc.hardwareearmonitor.honor.HardwareEarMonitorHonor.1
            @Override // java.lang.Runnable
            public final void run() {
                synchronized (HardwareEarMonitorHonor.this.mLock) {
                    HardwareEarMonitorHonor.nativeHandleResult(HardwareEarMonitorHonor.this.mNativeHardwareEarMonitorHandle, i);
                }
            }
        });
    }

    public boolean setAudioParams(String str) {
        try {
            this.mAudioManager.setParameters(str);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public int setEqualizer(int i) {
        if (this.mHnEarReturnClient == null) {
            return 1806;
        }
        return this.mHnEarReturnClient.setParameter(HonorEarReturnClient.ParameName.CMD_SET_VOCAL_EQUALIZER_MODE, i);
    }

    public int setReverberation(int i) {
        if (this.mHnEarReturnClient == null) {
            return 1806;
        }
        return this.mHnEarReturnClient.setParameter(HonorEarReturnClient.ParameName.CMD_SET_AUDIO_EFFECT_MODE_BASE, i);
    }

    public int setVolume(int i) {
        if (this.mHnEarReturnClient == null) {
            return 1806;
        }
        return this.mHnEarReturnClient.setParameter(HonorEarReturnClient.ParameName.CMD_SET_VOCAL_VOLUME_BASE, i);
    }
}
