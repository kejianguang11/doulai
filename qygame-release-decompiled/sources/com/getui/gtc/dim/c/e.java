package com.getui.gtc.dim.c;

import android.annotation.SuppressLint;
import android.net.wifi.WifiInfo;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class e {
    private static Object a(Parcel parcel) {
        try {
            int iDataPosition = parcel.dataPosition();
            int i = parcel.readInt();
            parcel.setDataPosition(iDataPosition);
            if (i > 100) {
                return null;
            }
            Class<?> cls = Class.forName("android.net.wifi.WifiSsid");
            return ((Parcelable.Creator) cls.getDeclaredField("CREATOR").get(cls)).createFromParcel(parcel);
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a("getWifiSsid", th);
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x008e, code lost:
    
        throw new java.lang.UnsupportedOperationException("cannot read wifiInfo,API>33");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String a(WifiInfo wifiInfo) throws JSONException {
        Object objA;
        JSONObject jSONObject;
        int i = Build.VERSION.SDK_INT;
        if (i < 28) {
            jSONObject = b(wifiInfo);
        } else {
            Parcel parcelObtain = Parcel.obtain();
            try {
                wifiInfo.writeToParcel(parcelObtain, 0);
                boolean z = false;
                while (true) {
                    if (i >= 28 && i <= 33) {
                        parcelObtain.setDataPosition(0);
                        parcelObtain.readInt();
                        parcelObtain.readInt();
                        parcelObtain.readInt();
                        if (i >= 29) {
                            parcelObtain.readInt();
                            parcelObtain.readInt();
                        }
                        if (z) {
                            parcelObtain.readInt();
                        }
                        parcelObtain.readInt();
                        if (parcelObtain.readByte() == 1) {
                            parcelObtain.createByteArray();
                        }
                        objA = parcelObtain.readInt() == 1 ? a(parcelObtain) : null;
                        if (z || objA != null) {
                            break;
                        }
                        z = true;
                    } else {
                        break;
                    }
                }
                jSONObject = new JSONObject();
                if (objA != null) {
                    jSONObject.put("BSSID", parcelObtain.readString());
                }
                jSONObject.put("SSID", a(objA));
            } finally {
                parcelObtain.recycle();
            }
        }
        jSONObject.put("rssi", wifiInfo.getRssi());
        jSONObject.put("toString", wifiInfo.toString());
        return jSONObject.toString();
    }

    private static String a(Object obj) {
        if (obj == null) {
            return "<unknown ssid>";
        }
        String string = obj.toString();
        if (TextUtils.isEmpty(string)) {
            String strB = b(obj);
            return strB != null ? strB : "<unknown ssid>";
        }
        return "\"" + string + "\"";
    }

    private static String b(Object obj) {
        Class<?> cls = obj.getClass();
        try {
            if (Build.VERSION.SDK_INT < 28) {
                Method declaredMethod = cls.getDeclaredMethod("getHexString", new Class[0]);
                declaredMethod.setAccessible(true);
                return (String) declaredMethod.invoke(obj, new Object[0]);
            }
            Method declaredMethod2 = cls.getDeclaredMethod("getOctets", new Class[0]);
            declaredMethod2.setAccessible(true);
            byte[] bArr = (byte[]) declaredMethod2.invoke(obj, new Object[0]);
            String str = "0x";
            for (byte b : bArr) {
                str = str + String.format(Locale.US, "%02x", Byte.valueOf(b));
            }
            return bArr.length > 0 ? str : "<unknown ssid>";
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a("getHexString ", th);
            return "<unknown ssid>";
        }
    }

    @SuppressLint({"SoonBlockedPrivateApi", "DiscouragedPrivateApi"})
    private static JSONObject b(WifiInfo wifiInfo) {
        JSONObject jSONObject = new JSONObject();
        try {
            Field declaredField = WifiInfo.class.getDeclaredField("mBSSID");
            declaredField.setAccessible(true);
            Field declaredField2 = WifiInfo.class.getDeclaredField("mWifiSsid");
            declaredField2.setAccessible(true);
            jSONObject.put("BSSID", declaredField.get(wifiInfo));
            jSONObject.put("SSID", a(declaredField2.get(wifiInfo)));
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a("wifiInfo getBelow28", th);
        }
        return jSONObject;
    }
}
