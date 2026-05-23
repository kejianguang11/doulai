package com.gme.trtc.hardwareearmonitor.oplus;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import androidx.core.provider.FontsContractCompat;
import com.alipay.sdk.app.statistic.c;
import com.alipay.sdk.authjs.a;
import com.gme.liteav.base.ContextUtils;
import com.gme.liteav.base.annotations.JNINamespace;
import com.gme.liteav.base.util.LiteavLog;
import com.gme.liteav.base.util.g;
import com.gme.trtc.hardwareearmonitor.oplus.OplusKaraokeServiceAidlInterface;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav::extensions")
public class HardwareEarMonitorOplus {
    private static final int AUTHENTICATE_FAIL = 1002;
    private static final String AUTH_URI = "content://com.oplus.ocs.out.OpenCapabilityThirdProvider/oplus";
    private static final int KARAOKE_MESSAGE_CODE = 3000;
    private static final int KARAOKE_SUCCESS = 1000;
    private static final int ON_BIND_EXCEPTION = 1012;
    private static final int OPEN_CAPABILITY_THIRD_PROVIDER_NOT_FOUND = 1013;
    private static final String TAG = "HardwareEarMonitorOplus";
    private OplusKaraokeServiceAidlInterface mKaraokeServiceInterface;
    private long mNativeHardwareEarMonitorHandle;
    private Intent mServiceIntent;
    private Object mLock = new Object();
    private AudioManager mAudioManager = (AudioManager) ContextUtils.getApplicationContext().getSystemService("audio");
    private Context mContext = ContextUtils.getApplicationContext();
    private KaraokeServiceConnection mConnection = new KaraokeServiceConnection();
    private g mTaskRunner = new g();

    public class KaraokeServiceConnection implements ServiceConnection {
        private String mDescName;

        public KaraokeServiceConnection() {
        }

        void a(String str) {
            this.mDescName = str;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            HardwareEarMonitorOplus hardwareEarMonitorOplus;
            int i;
            OplusKaraokeServiceAidlInterface.Stub.setDESCRIPTOR(this.mDescName);
            HardwareEarMonitorOplus.this.mKaraokeServiceInterface = OplusKaraokeServiceAidlInterface.Stub.asInterface(iBinder);
            if (HardwareEarMonitorOplus.this.mKaraokeServiceInterface != null) {
                LiteavLog.i(HardwareEarMonitorOplus.TAG, "mKaraokeServiceInterface Create Success");
                hardwareEarMonitorOplus = HardwareEarMonitorOplus.this;
                i = 1000;
            } else {
                LiteavLog.i(HardwareEarMonitorOplus.TAG, "mKaraokeServiceInterface Create Failed");
                hardwareEarMonitorOplus = HardwareEarMonitorOplus.this;
                i = 1012;
            }
            hardwareEarMonitorOplus.notifyResult(i);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            HardwareEarMonitorOplus.this.mKaraokeServiceInterface = null;
            LiteavLog.i(HardwareEarMonitorOplus.TAG, "onServiceDisconnected");
            HardwareEarMonitorOplus.this.notifyResult(1012);
        }
    }

    public HardwareEarMonitorOplus(long j) {
        this.mNativeHardwareEarMonitorHandle = 0L;
        this.mNativeHardwareEarMonitorHandle = j;
    }

    public static HardwareEarMonitorOplus create(long j) {
        return new HardwareEarMonitorOplus(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeHandleResult(long j, int i);

    public void authCheck(String str) {
        Bundle bundleCall;
        Uri uri = Uri.parse(AUTH_URI);
        Messenger messenger = new Messenger(new Handler(this.mContext.getMainLooper()) { // from class: com.gme.trtc.hardwareearmonitor.oplus.HardwareEarMonitorOplus.2
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                try {
                    super.handleMessage(message);
                    HardwareEarMonitorOplus.this.notifyResult(message.getData().getInt(FontsContractCompat.Columns.RESULT_CODE));
                } catch (Throwable th) {
                    LiteavLog.i(HardwareEarMonitorOplus.TAG, "authCheck ex:" + th.getMessage());
                    HardwareEarMonitorOplus.this.notifyResult(1002);
                }
            }
        });
        Bundle bundle = new Bundle();
        bundle.putBinder(a.c, messenger.getBinder());
        try {
            bundleCall = this.mContext.getContentResolver().call(uri, c.d, str, bundle);
        } catch (Throwable th) {
            LiteavLog.e(TAG, "component is not exist or not visible ex:" + th.getMessage());
            notifyResult(1013);
            bundleCall = null;
        }
        if (bundleCall == null) {
            LiteavLog.e(TAG, "retBundle is null");
            notifyResult(1013);
        }
    }

    public void bindKaraokeService(String str, String str2, String str3) {
        this.mServiceIntent = new Intent();
        LiteavLog.i(TAG, "bindKaraokeService service package name:" + str + " service name:" + str2 + " android os:" + Build.VERSION.SDK_INT + " oplus os:" + getOplusOSVersionInt());
        this.mServiceIntent.setPackage(str);
        this.mServiceIntent.setAction(str2);
        this.mConnection.a(str3);
        try {
            this.mContext.bindService(this.mServiceIntent, this.mConnection, 1);
        } catch (Throwable th) {
            LiteavLog.i(TAG, "bindKaraokeService ex:" + th.getMessage());
            notifyResult(1012);
        }
    }

    public void destroy() {
        synchronized (this.mLock) {
            this.mNativeHardwareEarMonitorHandle = 0L;
        }
        try {
            this.mContext.unbindService(this.mConnection);
        } catch (Throwable th) {
            LiteavLog.i(TAG, "destroy ex:" + th.getMessage());
        }
        this.mConnection = null;
        this.mKaraokeServiceInterface = null;
    }

    public String getManifestMeta(String str) {
        try {
            return this.mContext.getPackageManager().getApplicationInfo(this.mContext.getPackageName(), 128).metaData.getString(str);
        } catch (Throwable th) {
            LiteavLog.i(TAG, "getManifestMeta ex:" + th.getMessage());
            return "";
        }
    }

    public int getOplusOSVersionInt() {
        try {
            Class<?> cls = Class.forName("com.oplus.os.OplusBuild");
            return ((Integer) cls.getDeclaredMethod("getOplusOSVERSION", new Class[0]).invoke(cls, new Object[0])).intValue();
        } catch (Throwable th) {
            LiteavLog.i(TAG, "getOplusOSVersionInt ex:" + th.getMessage());
            return 0;
        }
    }

    public int getUidFromPackage() {
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                return this.mContext.getPackageManager().getPackageUid(this.mContext.getPackageName(), 0);
            }
            return 0;
        } catch (Throwable unused) {
            return 0;
        }
    }

    public void notifyResult(final int i) {
        this.mTaskRunner.a(new Runnable() { // from class: com.gme.trtc.hardwareearmonitor.oplus.HardwareEarMonitorOplus.1
            @Override // java.lang.Runnable
            public final void run() {
                synchronized (HardwareEarMonitorOplus.this.mLock) {
                    HardwareEarMonitorOplus.nativeHandleResult(HardwareEarMonitorOplus.this.mNativeHardwareEarMonitorHandle, i);
                }
            }
        });
    }

    public void setActiveClient(String str) {
        if (this.mKaraokeServiceInterface != null) {
            try {
                this.mKaraokeServiceInterface.setActiveClient(str);
            } catch (Throwable th) {
                LiteavLog.i(TAG, "setActiveClient ex:" + th.getMessage());
            }
        }
    }

    public boolean setAudioParams(String str) {
        try {
            this.mAudioManager.setParameters(str);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public boolean systemFeatureSupported(String str) {
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                return this.mContext.getPackageManager().hasSystemFeature(str);
            }
            return false;
        } catch (Throwable unused) {
            return false;
        }
    }
}
