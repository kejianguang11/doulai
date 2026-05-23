package com.gme.liteav.audio2.route;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import com.alipay.sdk.packet.d;
import com.gme.liteav.base.Log;
import com.gme.liteav.base.system.LiteavSystemInfo;

/* JADX INFO: loaded from: classes.dex */
public final class a extends BroadcastReceiver {
    final Context a;
    private final InterfaceC0025a b;

    /* JADX INFO: renamed from: com.gme.liteav.audio2.route.a$a, reason: collision with other inner class name */
    public interface InterfaceC0025a {
        void onBluetoothConnectionChanged(boolean z);

        void onBluetoothScoConnected(boolean z);

        void onSystemVolumeChanged();

        void onUsbConnectionChanged(String str, boolean z);

        void onWiredHeadsetConnectionChanged(boolean z);
    }

    public a(Context context, InterfaceC0025a interfaceC0025a) {
        this.a = context;
        this.b = interfaceC0025a;
    }

    private static int a(Intent intent, String str, int i) {
        try {
            return intent.getIntExtra(str, i);
        } catch (Exception e) {
            Log.e("AudioEventBroadcastReceiver", "getIntentIntExtra ".concat(String.valueOf(e)), new Object[0]);
            return i;
        }
    }

    private static String a(int i) {
        switch (i) {
            case 10:
                return "STATE_OFF";
            case 11:
                return "STATE_TURNING_ON";
            case 12:
                return "STATE_ON";
            case 13:
                return "STATE_TURNING_OFF";
            default:
                return "unknown";
        }
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String str;
        if (intent == null || context == null) {
            Log.e("AudioEventBroadcastReceiver", "Receive intent or context is null", new Object[0]);
            return;
        }
        String action = intent.getAction();
        if (action == null) {
        }
        switch (action) {
            case "android.intent.action.HEADSET_PLUG":
                int iA = a(intent, "state", -1);
                Log.i("AudioEventBroadcastReceiver", "Receive ACTION_HEADSET_PLUG, EXTRA_STATE:".concat(String.valueOf(iA)), new Object[0]);
                if (iA == -1) {
                    Log.e("AudioEventBroadcastReceiver", "Unknown headset state, ignore...", new Object[0]);
                    break;
                } else {
                    this.b.onWiredHeadsetConnectionChanged(iA != 0);
                    break;
                }
                break;
            case "android.bluetooth.adapter.action.STATE_CHANGED":
                int iA2 = a(intent, "android.bluetooth.adapter.extra.STATE", 0);
                Log.i("AudioEventBroadcastReceiver", "Receive ACTION_STATE_CHANGED, EXTRA_STATE:" + a(iA2) + " EXTRA_PREVIOUS_STATE: " + a(a(intent, "android.bluetooth.adapter.extra.PREVIOUS_STATE", 0)), new Object[0]);
                if (iA2 == 10) {
                    this.b.onBluetoothConnectionChanged(false);
                    break;
                }
                break;
            case "android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED":
                int iA3 = a(intent, "android.bluetooth.profile.extra.STATE", -1);
                Object[] objArr = new Object[1];
                switch (iA3) {
                    case 0:
                        str = "STATE_DISCONNECTED";
                        break;
                    case 1:
                        str = "STATE_CONNECTING";
                        break;
                    case 2:
                        str = "STATE_CONNECTED";
                        break;
                    case 3:
                        str = "STATE_DISCONNECTING";
                        break;
                    default:
                        str = "unknown";
                        break;
                }
                objArr[0] = str;
                Log.i("AudioEventBroadcastReceiver", "Receive bluetooth headset connection state changed: %s", objArr);
                if (iA3 == 0) {
                    this.b.onBluetoothConnectionChanged(false);
                    break;
                } else if (iA3 == 2) {
                    this.b.onBluetoothConnectionChanged(true);
                    break;
                }
                break;
            case "android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED":
                int iA4 = a(intent, "android.bluetooth.profile.extra.STATE", 10);
                if (iA4 == 12) {
                    Log.i("AudioEventBroadcastReceiver", "Receive bluetooth audio state changed to STATE_AUDIO_CONNECTED", new Object[0]);
                    this.b.onBluetoothScoConnected(true);
                    break;
                } else {
                    if (iA4 == 10) {
                        Log.i("AudioEventBroadcastReceiver", "Receive bluetooth audio state changed to STATE_AUDIO_DISCONNECTED", new Object[0]);
                        this.b.onBluetoothScoConnected(false);
                    }
                    break;
                }
                break;
            case "android.hardware.usb.action.USB_DEVICE_ATTACHED":
            case "android.hardware.usb.action.USB_DEVICE_DETACHED":
                UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra(d.n);
                if (usbDevice != null) {
                    String productName = "";
                    if (LiteavSystemInfo.getSystemOSVersionInt() >= 21) {
                        productName = usbDevice.getProductName();
                        Log.i("AudioEventBroadcastReceiver", "Usb device attached " + productName + " manufacture " + usbDevice.getManufacturerName(), new Object[0]);
                    }
                    if (!AudioDeviceProperty.isUsbHeadsetDevice(usbDevice)) {
                        Log.i("AudioEventBroadcastReceiver", "The attached usb device doesn't seem to support audio, ignore it", new Object[0]);
                    } else if ("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(intent.getAction())) {
                        this.b.onUsbConnectionChanged(productName, true);
                    } else if (!"android.hardware.usb.action.USB_DEVICE_DETACHED".equals(intent.getAction())) {
                        Log.i("AudioEventBroadcastReceiver", "Unknown action, ignore it " + intent.getAction(), new Object[0]);
                    } else {
                        this.b.onUsbConnectionChanged(productName, false);
                    }
                    break;
                }
                break;
            case "android.media.VOLUME_CHANGED_ACTION":
                if (this.b != null) {
                    this.b.onSystemVolumeChanged();
                    break;
                }
                break;
            default:
                Log.w("AudioEventBroadcastReceiver", "Ignore unknown Action:".concat(String.valueOf(action)), new Object[0]);
                break;
        }
    }
}
