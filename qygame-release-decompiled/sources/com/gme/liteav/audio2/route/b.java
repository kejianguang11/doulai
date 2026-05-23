package com.gme.liteav.audio2.route;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Process;
import com.gme.liteav.base.Log;
import com.gme.liteav.base.system.LiteavSystemInfo;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class b implements BluetoothProfile.ServiceListener {
    private final Context d;
    private AudioManager e;
    BluetoothProfile b = null;
    final Object c = new Object();
    final BluetoothAdapter a = c();

    public b(Context context) {
        this.d = context;
        if (this.a != null) {
            try {
                this.a.getProfileProxy(context, this, 1);
            } catch (Throwable th) {
                Log.w("BluetoothHeadsetListener", "Get profile proxy exception " + th.getMessage(), new Object[0]);
            }
        } else {
            Log.i("BluetoothHeadsetListener", "Bluetooth adapter is null", new Object[0]);
        }
        this.e = (AudioManager) this.d.getSystemService("audio");
    }

    public static boolean a(Context context) {
        if (context == null || LiteavSystemInfo.getSystemOSVersionInt() < 31) {
            return true;
        }
        try {
            return context.checkPermission("android.permission.BLUETOOTH_CONNECT", Process.myPid(), Process.myUid()) == 0;
        } catch (Throwable th) {
            Log.w("BluetoothHeadsetListener", "checkPermission exception " + th.getMessage(), new Object[0]);
            return true;
        }
    }

    private static BluetoothAdapter c() {
        try {
            return BluetoothAdapter.getDefaultAdapter();
        } catch (Throwable th) {
            Log.w("BluetoothHeadsetListener", "Get default adapter exception " + th.getMessage(), new Object[0]);
            return null;
        }
    }

    private List<BluetoothDevice> d() {
        try {
            return this.b.getConnectedDevices();
        } catch (Throwable th) {
            Log.w("BluetoothHeadsetListener", "Get connected devices exception " + th.getMessage(), new Object[0]);
            return null;
        }
    }

    private boolean e() {
        try {
            return this.a.isEnabled();
        } catch (Throwable th) {
            Log.w("BluetoothHeadsetListener", "Get bluetooth adapter status exception " + th.getMessage(), new Object[0]);
            return false;
        }
    }

    private boolean f() {
        try {
            return ((Integer) BluetoothAdapter.class.getMethod("isLeAudioSupported", new Class[0]).invoke(this.a, new Object[0])).intValue() == 10;
        } catch (Throwable th) {
            Log.w("BluetoothHeadsetListener", "get le audio supported failed. ".concat(String.valueOf(th)), new Object[0]);
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x005d A[Catch: Throwable -> 0x009a, all -> 0x00d8, TryCatch #0 {Throwable -> 0x009a, blocks: (B:22:0x0055, B:24:0x005d, B:26:0x0068, B:28:0x0072, B:30:0x0079, B:33:0x0082, B:34:0x0085, B:36:0x008d, B:38:0x0093), top: B:51:0x0055, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0085 A[Catch: Throwable -> 0x009a, all -> 0x00d8, TryCatch #0 {Throwable -> 0x009a, blocks: (B:22:0x0055, B:24:0x005d, B:26:0x0068, B:28:0x0072, B:30:0x0079, B:33:0x0082, B:34:0x0085, B:36:0x008d, B:38:0x0093), top: B:51:0x0055, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean a() {
        String str;
        String str2;
        Object[] objArr;
        boolean z;
        List<BluetoothDevice> listD;
        if (this.a == null || !e()) {
            return false;
        }
        synchronized (this.c) {
            if (this.b == null) {
                try {
                    Log.i("BluetoothHeadsetListener", "mBluetoothHeadsetProfile is null ,wait for 1000ms", new Object[0]);
                    this.c.wait(1000L);
                } catch (Throwable th) {
                    Log.w("BluetoothHeadsetListener", "Wait exception " + th.getMessage(), new Object[0]);
                }
                if (this.b == null) {
                    str = "BluetoothHeadsetListener";
                    str2 = "mBluetoothHeadsetProfile is still null";
                    objArr = new Object[0];
                } else {
                    str = "BluetoothHeadsetListener";
                    str2 = "mBluetoothHeadsetProfile service is connected now";
                    objArr = new Object[0];
                }
                Log.i(str, str2, objArr);
                z = true;
                try {
                } catch (Throwable th2) {
                    Log.e("BluetoothHeadsetListener", "get connected bluetooth devices failed." + th2.getMessage(), new Object[0]);
                }
                if (LiteavSystemInfo.getSystemOSVersionInt() < 23) {
                    for (AudioDeviceInfo audioDeviceInfo : this.e.getDevices(2)) {
                        if (audioDeviceInfo.getType() == 8 || audioDeviceInfo.getType() == 7 || audioDeviceInfo.getType() == 26) {
                            break;
                        }
                    }
                    z = false;
                } else if (!a(this.d) || (listD = d()) == null || listD.size() <= 0) {
                    z = false;
                }
            } else {
                z = true;
                if (LiteavSystemInfo.getSystemOSVersionInt() < 23) {
                }
            }
        }
        Log.i("BluetoothHeadsetListener", "find bluetooth device " + z + ", le audio supported is " + f(), new Object[0]);
        return z;
    }

    final void b() {
        try {
            this.a.closeProfileProxy(1, this.b);
        } catch (Throwable th) {
            Log.w("BluetoothHeadsetListener", "Close profile proxy exception " + th.getMessage(), new Object[0]);
        }
    }

    @Override // android.bluetooth.BluetoothProfile.ServiceListener
    public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
        if (i != 1) {
            return;
        }
        synchronized (this.c) {
            if (this.a != null && this.b != null) {
                Log.i("BluetoothHeadsetListener", "Bluetooth Headset proxy changed from %s to %s", this.b, bluetoothProfile);
                b();
            }
            this.b = bluetoothProfile;
            this.c.notifyAll();
        }
    }

    @Override // android.bluetooth.BluetoothProfile.ServiceListener
    public final void onServiceDisconnected(int i) {
        if (i != 1) {
            return;
        }
        synchronized (this.c) {
            if (this.a != null && this.b != null) {
                b();
                this.b = null;
            }
        }
    }
}
