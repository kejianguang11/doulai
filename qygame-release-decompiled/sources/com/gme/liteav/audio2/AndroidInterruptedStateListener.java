package com.gme.liteav.audio2;

import android.media.AudioManager;
import android.media.AudioRecordingConfiguration;
import android.os.Build;
import com.gme.liteav.audio2.d;
import com.gme.liteav.audio2.e;
import com.gme.liteav.base.Log;
import com.gme.liteav.base.ThreadUtils;
import com.gme.liteav.base.annotations.JNINamespace;
import com.gme.liteav.base.system.LiteavSystemInfo;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav::audio")
public class AndroidInterruptedStateListener implements d.a, e.b {
    private static final int RECORDING_CONFIGS_LIMIT = 10;
    public static final String TAG = "AndroidInterruptedStateListener";
    private static d mRecordingCallback;
    private final long mNativeRecordingConfigListener;
    private volatile boolean mNeedNotify = false;
    private Object mObject = new Object();
    private e mPhoneStateManager;

    static class RecordingConfig {
        int a = 0;
        boolean b = false;

        public int getSessionId() {
            return this.a;
        }

        public boolean isSilenced() {
            return this.b;
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 24) {
            mRecordingCallback = new d();
        }
    }

    public AndroidInterruptedStateListener(long j) {
        this.mNativeRecordingConfigListener = j;
        Log.d(TAG, "new AndroidInterruptedStateListener" + hashCode(), new Object[0]);
    }

    static /* synthetic */ void a(AndroidInterruptedStateListener androidInterruptedStateListener) {
        String str;
        StringBuilder sb;
        if (androidInterruptedStateListener.mPhoneStateManager != null) {
            e eVar = androidInterruptedStateListener.mPhoneStateManager;
            if (e.b()) {
                try {
                    if (eVar.a != null) {
                        eVar.a.listen(eVar, 0);
                    }
                    eVar.g = 0;
                    return;
                } catch (Throwable th) {
                    th = th;
                    str = "PhoneStateManager";
                    sb = new StringBuilder("stop listen phone state failed, ");
                }
            } else {
                if (Build.VERSION.SDK_INT < 31) {
                    e.c();
                    return;
                }
                try {
                    if (eVar.d == null || eVar.e == null) {
                        return;
                    }
                    AudioManager.class.getMethod("removeOnModeChangedListener", eVar.d).invoke(eVar.b, eVar.e);
                    return;
                } catch (Throwable th2) {
                    th = th2;
                    str = "PhoneStateManager";
                    sb = new StringBuilder("remove mode changed listener failed, ");
                }
            }
            sb.append(th.getMessage());
            Log.e(str, sb.toString(), new Object[0]);
        }
    }

    static /* synthetic */ void b(AndroidInterruptedStateListener androidInterruptedStateListener) {
        String str;
        StringBuilder sb;
        if (androidInterruptedStateListener.mPhoneStateManager == null) {
            androidInterruptedStateListener.mPhoneStateManager = new e(androidInterruptedStateListener);
        }
        e eVar = androidInterruptedStateListener.mPhoneStateManager;
        if (e.b()) {
            try {
                if (eVar.a != null) {
                    eVar.a.listen(eVar, 32);
                } else {
                    Log.w("PhoneStateManager", "TelephonyManager is null, start listen phone state failed.", new Object[0]);
                }
            } catch (Throwable th) {
                th = th;
                str = "PhoneStateManager";
                sb = new StringBuilder("start listen phone state failed, ");
                sb.append(th.getMessage());
                Log.e(str, sb.toString(), new Object[0]);
            }
        } else if (Build.VERSION.SDK_INT >= 31) {
            try {
                if (eVar.d == null) {
                    eVar.d = Class.forName("android.media.AudioManager$OnModeChangedListener");
                }
                if (eVar.e == null) {
                    eVar.e = Proxy.newProxyInstance(eVar.d.getClassLoader(), new Class[]{eVar.d}, new e.a(eVar));
                }
                AudioManager.class.getMethod("addOnModeChangedListener", Executor.class, eVar.d).invoke(eVar.b, h.a(eVar), eVar.e);
            } catch (Throwable th2) {
                th = th2;
                str = "PhoneStateManager";
                sb = new StringBuilder("add mode changed listener failed, ");
                sb.append(th.getMessage());
                Log.e(str, sb.toString(), new Object[0]);
            }
        } else if (Build.VERSION.SDK_INT >= 26 && e.c != null) {
            Log.i("PhoneStateManager", "register audio playback callback.", new Object[0]);
            e.c.a = eVar;
        }
        eVar.f.a(f.a(eVar));
    }

    private static native void nativeNotifyAudioRecordingConfigChangedFromJava(long j, RecordingConfig[] recordingConfigArr);

    private static native void nativeNotifyInterruptedByPhoneCallFromJava(long j);

    private static native void nativeNotifyResumedByPhoneCallFromJava(long j);

    @Override // com.gme.liteav.audio2.d.a
    public void OnRecordingConfigChanged(List<AudioRecordingConfiguration> list) {
        if (list == null) {
            return;
        }
        int iMin = Math.min(list.size(), 10);
        RecordingConfig[] recordingConfigArr = new RecordingConfig[iMin];
        for (int i = 0; i < iMin; i++) {
            recordingConfigArr[i] = new RecordingConfig();
            AudioRecordingConfiguration audioRecordingConfiguration = list.get(i);
            recordingConfigArr[i].a = audioRecordingConfiguration.getClientAudioSessionId();
            if (LiteavSystemInfo.getSystemOSVersionInt() < 29) {
                recordingConfigArr[i].b = false;
            } else if (Build.VERSION.SDK_INT >= 29) {
                recordingConfigArr[i].b = audioRecordingConfiguration.isClientSilenced();
            }
        }
        synchronized (this.mObject) {
            if (this.mNeedNotify) {
                nativeNotifyAudioRecordingConfigChangedFromJava(this.mNativeRecordingConfigListener, recordingConfigArr);
            }
        }
    }

    @Override // com.gme.liteav.audio2.e.b
    public void onInterruptedByPhoneCall() {
        synchronized (this.mObject) {
            if (this.mNeedNotify) {
                nativeNotifyInterruptedByPhoneCallFromJava(this.mNativeRecordingConfigListener);
            }
        }
    }

    @Override // com.gme.liteav.audio2.e.b
    public void onResumedByPhoneCall() {
        synchronized (this.mObject) {
            if (this.mNeedNotify) {
                nativeNotifyResumedByPhoneCallFromJava(this.mNativeRecordingConfigListener);
            }
        }
    }

    public void registerAudioRecordingCallback() {
        if (LiteavSystemInfo.getSystemOSVersionInt() < 24) {
            return;
        }
        if (mRecordingCallback != null) {
            mRecordingCallback.a = this;
        }
        ThreadUtils.getUiThreadHandler().post(a.a(this));
        this.mNeedNotify = true;
    }

    public void unregisterAudioRecordingCallback() {
        if (LiteavSystemInfo.getSystemOSVersionInt() >= 24 && mRecordingCallback != null) {
            synchronized (this.mObject) {
                this.mNeedNotify = false;
                mRecordingCallback.a = null;
                ThreadUtils.getUiThreadHandler().post(b.a(this));
            }
        }
    }
}
