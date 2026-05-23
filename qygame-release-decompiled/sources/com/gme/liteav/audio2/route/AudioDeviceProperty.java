package com.gme.liteav.audio2.route;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.media.AudioDeviceCallback;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import com.gme.liteav.audio2.route.a;
import com.gme.liteav.base.ContextUtils;
import com.gme.liteav.base.Log;
import com.gme.liteav.base.annotations.JNINamespace;
import com.gme.liteav.base.system.LiteavSystemInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav::audio")
public class AudioDeviceProperty implements a.InterfaceC0025a {
    private static final String TAG = "AudioDeviceProperty";
    private AudioDeviceCallback mAudioDeviceCallback;
    private a mAudioEventBroadcastReceiver;
    private b mBluetoothHeadsetListener;
    private long mNativeAudioDeviceProperty;
    private boolean mAudioDeviceCallbackAvailable = false;
    private boolean mUseBluetoothSco = false;
    private final Context mContext = ContextUtils.getApplicationContext();
    private final AudioManager mAudioManager = (AudioManager) this.mContext.getSystemService("audio");

    public static class UsbAudioDeviceInfo {
        public String a = "";
        public String b = "";

        public String getName() {
            return this.a;
        }

        public String getVidPid() {
            return this.b;
        }
    }

    public AudioDeviceProperty(long j) {
        this.mNativeAudioDeviceProperty = j;
    }

    private void buildAudioDeviceCallback() {
        if (this.mAudioDeviceCallback != null) {
            return;
        }
        this.mAudioDeviceCallback = new AudioDeviceCallback() { // from class: com.gme.liteav.audio2.route.AudioDeviceProperty.1
            @Override // android.media.AudioDeviceCallback
            public final void onAudioDevicesAdded(AudioDeviceInfo[] audioDeviceInfoArr) {
                if (audioDeviceInfoArr.length == 0) {
                    return;
                }
                Log.i(AudioDeviceProperty.TAG, "add device size " + audioDeviceInfoArr.length, new Object[0]);
                AudioDeviceProperty.this.mAudioDeviceCallbackAvailable = true;
                for (AudioDeviceInfo audioDeviceInfo : audioDeviceInfoArr) {
                    Log.i(AudioDeviceProperty.TAG, "added device type is " + audioDeviceInfo.getType() + " sink: " + audioDeviceInfo.isSink() + " product name: " + ((Object) audioDeviceInfo.getProductName()), new Object[0]);
                    if (audioDeviceInfo.getType() == 8 || audioDeviceInfo.getType() == 26) {
                        AudioDeviceProperty.nativeNotifyBluetoothConnectionChangedFromJava(AudioDeviceProperty.this.mNativeAudioDeviceProperty, true);
                    } else if (audioDeviceInfo.getType() == 11 || audioDeviceInfo.getType() == 12 || audioDeviceInfo.getType() == 22) {
                        AudioDeviceProperty.nativeNotifyUsbConnectionChangedFromJava(AudioDeviceProperty.this.mNativeAudioDeviceProperty, audioDeviceInfo.getProductName().toString(), AudioDeviceProperty.this.isUsbHeadsetAvailable());
                    } else if (audioDeviceInfo.getType() == 3 || audioDeviceInfo.getType() == 4) {
                        AudioDeviceProperty.nativeNotifyWiredHeadsetConnectionChangedFromJava(AudioDeviceProperty.this.mNativeAudioDeviceProperty, true);
                    }
                }
            }

            @Override // android.media.AudioDeviceCallback
            public final void onAudioDevicesRemoved(AudioDeviceInfo[] audioDeviceInfoArr) {
                if (audioDeviceInfoArr.length == 0) {
                    return;
                }
                Log.i(AudioDeviceProperty.TAG, "remove device size " + audioDeviceInfoArr.length, new Object[0]);
                int length = audioDeviceInfoArr.length;
                for (int i = 0; i < length; i++) {
                    AudioDeviceInfo audioDeviceInfo = audioDeviceInfoArr[i];
                    Log.i(AudioDeviceProperty.TAG, "removed device type is " + audioDeviceInfo.getType() + " sink: " + audioDeviceInfo.isSink() + " product name: " + ((Object) audioDeviceInfo.getProductName()), new Object[0]);
                    if ((audioDeviceInfo.getType() == 8 || audioDeviceInfo.getType() == 7 || audioDeviceInfo.getType() == 26) && !AudioDeviceProperty.this.isBluetoothHeadsetConnected()) {
                        AudioDeviceProperty.nativeNotifyBluetoothConnectionChangedFromJava(AudioDeviceProperty.this.mNativeAudioDeviceProperty, false);
                    } else if (audioDeviceInfo.getType() == 11 || audioDeviceInfo.getType() == 12 || audioDeviceInfo.getType() == 22) {
                        AudioDeviceProperty.nativeNotifyUsbConnectionChangedFromJava(AudioDeviceProperty.this.mNativeAudioDeviceProperty, audioDeviceInfo.getProductName().toString(), AudioDeviceProperty.this.isUsbHeadsetAvailable());
                    } else if (audioDeviceInfo.getType() == 3 || audioDeviceInfo.getType() == 4) {
                        AudioDeviceProperty.nativeNotifyWiredHeadsetConnectionChangedFromJava(AudioDeviceProperty.this.mNativeAudioDeviceProperty, false);
                    }
                }
            }
        };
    }

    private boolean isCommunicationDeviceConnected(int i) {
        try {
            AudioDeviceInfo audioDeviceInfo = (AudioDeviceInfo) AudioManager.class.getMethod("getCommunicationDevice", new Class[0]).invoke(this.mAudioManager, new Object[0]);
            if (audioDeviceInfo == null) {
                return false;
            }
            return audioDeviceInfo.getType() == i;
        } catch (Throwable th) {
            Log.i(TAG, "get communication device failed. ".concat(String.valueOf(th)), new Object[0]);
            return false;
        }
    }

    public static boolean isUsbHeadsetDevice(UsbDevice usbDevice) {
        if (usbDevice == null) {
            return false;
        }
        for (int i = 0; i < usbDevice.getInterfaceCount(); i++) {
            try {
                if (usbDevice.getInterface(i).getInterfaceClass() == 1) {
                    return true;
                }
            } catch (Throwable th) {
                Log.e(TAG, "Get interface exception " + th.getMessage(), new Object[0]);
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeNotifyBluetoothConnectionChangedFromJava(long j, boolean z);

    private static native void nativeNotifyBluetoothScoConnectedFromJava(long j, boolean z);

    private static native void nativeNotifySystemVolumeChangedFromJava(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeNotifyUsbConnectionChangedFromJava(long j, String str, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeNotifyWiredHeadsetConnectionChangedFromJava(long j, boolean z);

    private void registerAudioDeviceCallback() {
        if (LiteavSystemInfo.getSystemOSVersionInt() < 23) {
            return;
        }
        if (this.mAudioDeviceCallback == null) {
            buildAudioDeviceCallback();
        }
        if (this.mAudioDeviceCallback == null) {
            return;
        }
        try {
            this.mAudioManager.registerAudioDeviceCallback(this.mAudioDeviceCallback, null);
            Log.i(TAG, "register audio device callback", new Object[0]);
        } catch (Throwable th) {
            Log.e(TAG, "registerAudioDeviceCallback exception " + th.getMessage(), new Object[0]);
        }
    }

    private void setCommunicationDevice(AudioDeviceInfo audioDeviceInfo) {
        try {
            boolean zBooleanValue = ((Boolean) AudioManager.class.getMethod("setCommunicationDevice", AudioDeviceInfo.class).invoke(this.mAudioManager, audioDeviceInfo)).booleanValue();
            if (!zBooleanValue) {
                AudioManager.class.getMethod("clearCommunicationDevice", new Class[0]).invoke(this.mAudioManager, new Object[0]);
            }
            Log.i(TAG, "setCommunicationDevice: " + zBooleanValue + ", type: " + audioDeviceInfo.getType() + ", product name: " + ((Object) audioDeviceInfo.getProductName()), new Object[0]);
        } catch (Throwable th) {
            Log.i(TAG, "set communication device failed. ".concat(String.valueOf(th)), new Object[0]);
        }
    }

    private void unregisterAudioDeviceCallback() {
        if (LiteavSystemInfo.getSystemOSVersionInt() >= 23 && this.mAudioDeviceCallback != null) {
            try {
                this.mAudioManager.unregisterAudioDeviceCallback(this.mAudioDeviceCallback);
                Log.i(TAG, "unregister audio device callback", new Object[0]);
            } catch (Throwable th) {
                Log.e(TAG, "unregisterAudioDeviceCallback exception " + th.getMessage(), new Object[0]);
            }
        }
    }

    public UsbAudioDeviceInfo GetUsbAudioDeviceInfo(String str) {
        UsbManager usbManager;
        UsbAudioDeviceInfo usbAudioDeviceInfo = new UsbAudioDeviceInfo();
        try {
            usbManager = (UsbManager) this.mContext.getSystemService("usb");
        } catch (Throwable th) {
            Log.i(TAG, "getDeviceList exception " + th.getMessage(), new Object[0]);
        }
        if (usbManager != null && LiteavSystemInfo.getSystemOSVersionInt() >= 21) {
            for (UsbDevice usbDevice : usbManager.getDeviceList().values()) {
                if (str.contains(usbDevice.getProductName()) || isUsbHeadsetDevice(usbDevice)) {
                    usbAudioDeviceInfo.a = usbDevice.getProductName();
                    usbAudioDeviceInfo.b = String.valueOf(usbDevice.getVendorId()) + usbDevice.getProductId();
                }
            }
            return usbAudioDeviceInfo;
        }
        return usbAudioDeviceInfo;
    }

    public boolean checkBluetoothPermission() {
        return b.a(this.mContext);
    }

    public void connectBluetooth() {
        try {
            if (LiteavSystemInfo.getSystemOSVersionInt() < 35) {
                this.mUseBluetoothSco = true;
                this.mAudioManager.startBluetoothSco();
                Log.i(TAG, "startBluetoothSco", new Object[0]);
                return;
            }
            List<AudioDeviceInfo> list = (List) AudioManager.class.getMethod("getAvailableCommunicationDevices", new Class[0]).invoke(this.mAudioManager, new Object[0]);
            if (list != null && !list.isEmpty()) {
                for (AudioDeviceInfo audioDeviceInfo : list) {
                    if (audioDeviceInfo.getType() == 7 || audioDeviceInfo.getType() == 26) {
                        setCommunicationDevice(audioDeviceInfo);
                        return;
                    }
                }
                Log.w(TAG, "not found available communication devices, try to startBluetoothSco", new Object[0]);
                this.mUseBluetoothSco = true;
                this.mAudioManager.startBluetoothSco();
            }
        } catch (Throwable th) {
            Log.i(TAG, "startBluetooth exception " + th.getMessage(), new Object[0]);
        }
    }

    public void disconnectBluetooth() {
        try {
            if (LiteavSystemInfo.getSystemOSVersionInt() >= 35 && !this.mUseBluetoothSco) {
                AudioManager.class.getMethod("clearCommunicationDevice", new Class[0]).invoke(this.mAudioManager, new Object[0]);
                Log.i(TAG, "clearCommunicationDevice", new Object[0]);
                return;
            }
            this.mUseBluetoothSco = false;
            this.mAudioManager.stopBluetoothSco();
            Log.i(TAG, "stopBluetoothSco", new Object[0]);
        } catch (Throwable th) {
            Log.i(TAG, "stopBluetooth exception " + th.getMessage(), new Object[0]);
        }
    }

    public int getMode() {
        try {
            return this.mAudioManager.getMode();
        } catch (Throwable th) {
            Log.i(TAG, "Get mode exception " + th.getMessage(), new Object[0]);
            return 0;
        }
    }

    public int getSystemVolume() {
        try {
            int streamMaxVolume = this.mAudioManager.getStreamMaxVolume(this.mAudioManager.getMode() == 0 ? 3 : 0);
            if (streamMaxVolume <= 0) {
                return -1;
            }
            return (int) ((this.mAudioManager.getStreamVolume(r2) / streamMaxVolume) * 100.0f);
        } catch (Throwable th) {
            Log.e(TAG, "getStreamVolume exception " + th.getMessage(), new Object[0]);
            return -1;
        }
    }

    public boolean isBluetoothConnected() {
        try {
            if (LiteavSystemInfo.getSystemOSVersionInt() >= 35) {
                return isCommunicationDeviceConnected(7) || isCommunicationDeviceConnected(26);
            }
            Intent intentRegisterReceiver = ContextUtils.getApplicationContext().registerReceiver(null, new IntentFilter("android.media.ACTION_SCO_AUDIO_STATE_UPDATED"));
            return intentRegisterReceiver != null && intentRegisterReceiver.getIntExtra("android.media.extra.SCO_AUDIO_STATE", 0) == 1;
        } catch (Throwable th) {
            Log.i(TAG, "isBluetoothConnected exception " + th.getMessage(), new Object[0]);
            return false;
        }
    }

    public boolean isBluetoothHeadsetConnected() {
        if (this.mBluetoothHeadsetListener != null) {
            return this.mBluetoothHeadsetListener.a();
        }
        Log.e(TAG, "mBluetoothHeadsetListener is null", new Object[0]);
        return false;
    }

    public boolean isBluetoothOn() {
        try {
            return LiteavSystemInfo.getSystemOSVersionInt() < 35 ? this.mAudioManager.isBluetoothScoOn() : isCommunicationDeviceConnected(7) || isCommunicationDeviceConnected(26);
        } catch (Throwable th) {
            Log.i(TAG, "isBluetoothOn exception " + th.getMessage(), new Object[0]);
            return false;
        }
    }

    public boolean isBluetoothScoAvailable() {
        if (LiteavSystemInfo.getSystemOSVersionInt() < 23) {
            return true;
        }
        ArrayList arrayList = new ArrayList();
        try {
            for (AudioDeviceInfo audioDeviceInfo : this.mAudioManager.getDevices(2)) {
                arrayList.add(Integer.valueOf(audioDeviceInfo.getType()));
            }
        } catch (Throwable th) {
            Log.i(TAG, "isBluetoothScoAvailable exception " + th.getMessage(), new Object[0]);
        }
        return arrayList.isEmpty() || !arrayList.contains(8) || arrayList.contains(7);
    }

    public boolean isSpeakerphoneOn() {
        try {
            return this.mAudioManager.isSpeakerphoneOn();
        } catch (Throwable th) {
            Log.i(TAG, "isSpeakerphoneOn exception " + th.getMessage(), new Object[0]);
            return false;
        }
    }

    public boolean isUsbHeadsetAvailable() {
        try {
            UsbManager usbManager = (UsbManager) this.mContext.getSystemService("usb");
            if (usbManager == null) {
                return false;
            }
            Iterator<UsbDevice> it = usbManager.getDeviceList().values().iterator();
            while (it.hasNext()) {
                if (isUsbHeadsetDevice(it.next())) {
                    return true;
                }
            }
        } catch (Throwable th) {
            Log.i(TAG, "getDeviceList exception " + th.getMessage(), new Object[0]);
        }
        return false;
    }

    public boolean isWiredHeadsetOn() {
        try {
            return this.mAudioManager.isWiredHeadsetOn();
        } catch (Throwable th) {
            Log.i(TAG, "isWiredHeadsetOn exception " + th.getMessage(), new Object[0]);
            return false;
        }
    }

    @Override // com.gme.liteav.audio2.route.a.InterfaceC0025a
    public void onBluetoothConnectionChanged(boolean z) {
        if (z || !isBluetoothHeadsetConnected()) {
            nativeNotifyBluetoothConnectionChangedFromJava(this.mNativeAudioDeviceProperty, z);
        }
    }

    @Override // com.gme.liteav.audio2.route.a.InterfaceC0025a
    public void onBluetoothScoConnected(boolean z) {
        nativeNotifyBluetoothScoConnectedFromJava(this.mNativeAudioDeviceProperty, z);
    }

    @Override // com.gme.liteav.audio2.route.a.InterfaceC0025a
    public void onSystemVolumeChanged() {
        nativeNotifySystemVolumeChangedFromJava(this.mNativeAudioDeviceProperty);
    }

    @Override // com.gme.liteav.audio2.route.a.InterfaceC0025a
    public void onUsbConnectionChanged(String str, boolean z) {
        if (this.mAudioDeviceCallbackAvailable) {
            return;
        }
        nativeNotifyUsbConnectionChangedFromJava(this.mNativeAudioDeviceProperty, str, z);
    }

    @Override // com.gme.liteav.audio2.route.a.InterfaceC0025a
    public void onWiredHeadsetConnectionChanged(boolean z) {
        if (this.mAudioDeviceCallbackAvailable) {
            return;
        }
        nativeNotifyWiredHeadsetConnectionChangedFromJava(this.mNativeAudioDeviceProperty, z);
    }

    public void setBluetoothOn(boolean z) {
        try {
            if (LiteavSystemInfo.getSystemOSVersionInt() < 35) {
                this.mAudioManager.setBluetoothScoOn(z);
                Log.i(TAG, "setBluetoothScoOn ".concat(String.valueOf(z)), new Object[0]);
            }
        } catch (Throwable th) {
            Log.i(TAG, "setBluetoothOn exception " + th.getMessage(), new Object[0]);
        }
    }

    public void setSpeakerphoneOn(boolean z) {
        try {
            this.mAudioManager.setSpeakerphoneOn(z);
            Log.i(TAG, "setSpeakerphoneOn ".concat(String.valueOf(z)), new Object[0]);
        } catch (Throwable th) {
            Log.i(TAG, "setSpeakerphoneOn exception " + th.getMessage(), new Object[0]);
        }
    }

    public void setVoip(boolean z) {
        int i = z ? 3 : 0;
        try {
            this.mAudioManager.setMode(i);
            Log.i(TAG, "setMode ".concat(String.valueOf(i)), new Object[0]);
        } catch (Throwable th) {
            Log.i(TAG, "Set mode exception " + th.getMessage(), new Object[0]);
        }
    }

    public void setWiredHeadsetOn(boolean z) {
        try {
            this.mAudioManager.setWiredHeadsetOn(z);
            Log.i(TAG, "setWiredHeadsetOn ".concat(String.valueOf(z)), new Object[0]);
        } catch (Throwable th) {
            Log.i(TAG, "setWiredHeadsetOn exception " + th.getMessage(), new Object[0]);
        }
    }

    public void start() {
        registerAudioDeviceCallback();
        this.mAudioEventBroadcastReceiver = new a(this.mContext, this);
        a aVar = this.mAudioEventBroadcastReceiver;
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.HEADSET_PLUG");
            intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
            intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
            intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
            intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
            aVar.a.registerReceiver(aVar, intentFilter);
        } catch (Throwable unused) {
            Log.e("AudioEventBroadcastReceiver", "register broadcast exception", new Object[0]);
        }
        this.mBluetoothHeadsetListener = new b(this.mContext);
    }

    public void stop() {
        if (this.mAudioEventBroadcastReceiver != null) {
            a aVar = this.mAudioEventBroadcastReceiver;
            if (aVar.a != null) {
                try {
                    aVar.a.unregisterReceiver(aVar);
                } catch (Exception unused) {
                }
            }
        }
        this.mAudioEventBroadcastReceiver = null;
        if (this.mBluetoothHeadsetListener != null) {
            b bVar = this.mBluetoothHeadsetListener;
            synchronized (bVar.c) {
                if (bVar.a != null && bVar.b != null) {
                    bVar.b();
                    bVar.b = null;
                }
            }
        }
        this.mBluetoothHeadsetListener = null;
        unregisterAudioDeviceCallback();
    }
}
