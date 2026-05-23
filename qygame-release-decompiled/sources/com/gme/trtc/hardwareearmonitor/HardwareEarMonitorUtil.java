package com.gme.trtc.hardwareearmonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.alipay.sdk.packet.d;
import com.gme.liteav.base.ContextUtils;
import com.gme.liteav.base.annotations.JNINamespace;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav::extensions")
public class HardwareEarMonitorUtil extends BroadcastReceiver {
    private IntentFilter mFilter;
    private long mNativeHardwareEarMonitorHandle;
    private int mHeadsetState = -1;
    private int mHasMicrophone = -1;
    private String mDeviceName = "NotDefine";
    private String mPortName = "NotDefine";
    private String mDeviceAddress = "NotDefine";
    private Object mLock = new Object();
    private Context mContext = ContextUtils.getApplicationContext();

    public HardwareEarMonitorUtil(long j) {
        this.mNativeHardwareEarMonitorHandle = 0L;
        this.mNativeHardwareEarMonitorHandle = j;
        try {
            this.mFilter = new IntentFilter("android.intent.action.HEADSET_PLUG");
            this.mContext.registerReceiver(this, this.mFilter);
        } catch (Throwable unused) {
        }
    }

    public static HardwareEarMonitorUtil create(long j) {
        return new HardwareEarMonitorUtil(j);
    }

    private static native void nativeHeadsetDescChanged(long j, int i, int i2, String str, String str2, String str3);

    public void destroy() {
        if (this.mContext != null) {
            this.mContext.unregisterReceiver(this);
        }
        if (this.mFilter != null) {
            this.mFilter = null;
        }
        synchronized (this.mLock) {
            this.mNativeHardwareEarMonitorHandle = 0L;
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent != null && "android.intent.action.HEADSET_PLUG".equals(intent.getAction())) {
            synchronized (this.mLock) {
                this.mHeadsetState = intent.getIntExtra("state", -1);
                this.mHasMicrophone = intent.getIntExtra("microphone", -1);
                this.mDeviceName = intent.getStringExtra(d.n);
                this.mPortName = intent.getStringExtra("portName");
                this.mDeviceAddress = intent.getStringExtra("address");
                nativeHeadsetDescChanged(this.mNativeHardwareEarMonitorHandle, this.mHeadsetState, this.mHasMicrophone, this.mDeviceName == null ? "" : this.mDeviceName, this.mPortName == null ? "" : this.mPortName, this.mDeviceAddress == null ? "" : this.mDeviceAddress);
            }
        }
    }
}
