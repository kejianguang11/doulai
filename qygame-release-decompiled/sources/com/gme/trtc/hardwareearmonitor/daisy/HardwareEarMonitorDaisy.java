package com.gme.trtc.hardwareearmonitor.daisy;

import android.content.Context;
import android.media.AudioManager;
import com.gme.liteav.base.ContextUtils;
import com.gme.liteav.base.annotations.JNINamespace;
import com.gme.liteav.base.util.g;
import com.gme.trtc.hardwareearmonitor.daisy.DaisyAudioKaraokeFeatureKit;
import com.gme.trtc.hardwareearmonitor.daisy.DaisyAudioKit;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav::extensions")
public class HardwareEarMonitorDaisy implements IDaisyAudioKitCallback {
    private static final String TAG = "HardwareEarMonitorDaisy";
    private DaisyAudioKaraokeFeatureKit mDaisyKaraokeKit;
    private long mNativeHardwareEarMonitorHandle;
    private Object mLock = new Object();
    private AudioManager mAudioManager = (AudioManager) ContextUtils.getApplicationContext().getSystemService("audio");
    private Context mContext = ContextUtils.getApplicationContext();
    private g mTaskRunner = new g();
    private DaisyAudioKit mDaisyAudioKit = new DaisyAudioKit(ContextUtils.getApplicationContext(), this);

    public HardwareEarMonitorDaisy(long j) {
        this.mNativeHardwareEarMonitorHandle = 0L;
        this.mNativeHardwareEarMonitorHandle = j;
        this.mDaisyAudioKit.initialize();
    }

    public static HardwareEarMonitorDaisy create(long j) {
        return new HardwareEarMonitorDaisy(j);
    }

    public static boolean isAudioKitSupport() {
        return DaisyFeatureKitManager.isAudioKitSupport(ContextUtils.getApplicationContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeHandleResult(long j, int i);

    private int setEqualizer(int i) {
        if (this.mDaisyKaraokeKit == null) {
            return 1806;
        }
        return this.mDaisyKaraokeKit.setParameter(DaisyAudioKaraokeFeatureKit.ParameName.CMD_SET_VOCAL_EQUALIZER_MODE, i);
    }

    private int setVolume(int i) {
        if (this.mDaisyKaraokeKit == null) {
            return 1806;
        }
        return this.mDaisyKaraokeKit.setParameter(DaisyAudioKaraokeFeatureKit.ParameName.CMD_SET_VOCAL_VOLUME_BASE, i);
    }

    public void createKaraokeService() {
        this.mDaisyKaraokeKit = (DaisyAudioKaraokeFeatureKit) this.mDaisyAudioKit.createFeature(DaisyAudioKit.FeatureType.HWAUDIO_FEATURE_KARAOKE);
    }

    public void destroy() {
        synchronized (this.mLock) {
            this.mNativeHardwareEarMonitorHandle = 0L;
        }
        if (this.mDaisyAudioKit != null) {
            this.mDaisyAudioKit.destroy();
            this.mDaisyAudioKit = null;
        }
        if (this.mDaisyKaraokeKit != null) {
            this.mDaisyKaraokeKit.destroy();
            this.mDaisyKaraokeKit = null;
        }
    }

    public int enableKaraoke(boolean z) {
        if (this.mDaisyKaraokeKit == null) {
            return 1806;
        }
        return this.mDaisyKaraokeKit.enableKaraokeFeature(z);
    }

    public int getKaraokeLatency() {
        if (this.mDaisyKaraokeKit == null) {
            return -1;
        }
        try {
            return this.mDaisyKaraokeKit.getKaraokeLatency();
        } catch (Throwable unused) {
            return -1;
        }
    }

    public int[] getKaraokeSupportedServices() {
        List<Integer> supportedFeatures = this.mDaisyAudioKit.getSupportedFeatures();
        if (supportedFeatures == null) {
            return null;
        }
        int[] iArr = new int[supportedFeatures.size()];
        for (int i = 0; i < supportedFeatures.size(); i++) {
            iArr[i] = supportedFeatures.get(i).intValue();
        }
        return iArr;
    }

    public boolean isHardwareEarMonitorSupported() {
        if (this.mDaisyAudioKit == null) {
            return false;
        }
        return this.mDaisyAudioKit.isFeatureSupported(DaisyAudioKit.FeatureType.HWAUDIO_FEATURE_KARAOKE);
    }

    public boolean isKaraokeServiceSupport() {
        return this.mDaisyAudioKit.isFeatureSupported(DaisyAudioKit.FeatureType.HWAUDIO_FEATURE_KARAOKE);
    }

    @Override // com.gme.trtc.hardwareearmonitor.daisy.IDaisyAudioKitCallback
    public void onResult(final int i) {
        this.mTaskRunner.a(new Runnable() { // from class: com.gme.trtc.hardwareearmonitor.daisy.HardwareEarMonitorDaisy.1
            @Override // java.lang.Runnable
            public final void run() {
                synchronized (HardwareEarMonitorDaisy.this.mLock) {
                    HardwareEarMonitorDaisy.nativeHandleResult(HardwareEarMonitorDaisy.this.mNativeHardwareEarMonitorHandle, i);
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

    public int setReverberation(int i) {
        if (this.mDaisyKaraokeKit == null) {
            return 1806;
        }
        return this.mDaisyKaraokeKit.setParameter(DaisyAudioKaraokeFeatureKit.ParameName.CMD_SET_AUDIO_EFFECT_MODE_BASE, i);
    }
}
