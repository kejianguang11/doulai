package com.gcloudsdk.apollo;

import android.content.Context;
import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
public class ApolloVoiceUDID {
    private static Context mainContext;
    private static String sAppVersion;
    private static String sBrand;
    private static String sBundleId;
    private static String sFinger;
    private static String sManu;
    private static String sModel;
    private static String sOsVersion;
    private static String sProduct;

    public static String AppVersion() {
        if (sAppVersion != null) {
            return sAppVersion;
        }
        if (mainContext != null) {
            try {
                sAppVersion = mainContext.getPackageManager().getPackageInfo(mainContext.getPackageName(), 1).versionName;
            } catch (Exception e) {
                ApolloVoiceLog.LogE("GetAppVersion Exception: " + e);
            }
        }
        if (sAppVersion == null) {
            sAppVersion = "Unknown";
        }
        return sAppVersion;
    }

    public static String Brand() {
        if (sBrand != null) {
            return sBrand;
        }
        sBrand = ApolloVoiceEngine.GetDeviceBrand();
        ApolloVoiceLog.LogI("Get brand : " + sBrand);
        if (sBrand == null) {
            sBrand = "Emulator";
        }
        return sBrand;
    }

    public static String BundleID() {
        if (sBundleId != null) {
            return sBundleId;
        }
        if (mainContext != null) {
            try {
                sBundleId = mainContext.getPackageName();
            } catch (Exception e) {
                ApolloVoiceLog.LogE("GetBundleId Exception: " + e);
            }
        }
        if (sBundleId == null) {
            sBundleId = "Unknown";
        }
        return sBundleId;
    }

    public static String FingerID() {
        if (sFinger != null) {
            return sFinger;
        }
        sFinger = Build.FINGERPRINT;
        if (sFinger == null) {
            sFinger = "Emulator";
        }
        ApolloVoiceLog.LogI("Get finger : " + sFinger);
        return sFinger;
    }

    public static String ManuID() {
        if (sManu != null) {
            return sManu;
        }
        sManu = Build.MANUFACTURER;
        if (sManu == null) {
            sManu = "Emulator";
        }
        ApolloVoiceLog.LogI("Get PRODUCT : " + sManu);
        return sManu;
    }

    public static String Model() {
        if (sModel != null) {
            return sModel;
        }
        sModel = ApolloVoiceEngine.GetDeviceModel();
        ApolloVoiceLog.LogI("Get model : " + sModel);
        if (sModel == null) {
            sModel = "Emulator";
        }
        return sModel;
    }

    public static String OSVersion() {
        if (sOsVersion != null) {
            return sOsVersion;
        }
        sOsVersion = Build.VERSION.RELEASE;
        if (sOsVersion == null) {
            sOsVersion = "Unknown";
        }
        return sOsVersion;
    }

    public static String ProductID() {
        if (sProduct != null) {
            return sProduct;
        }
        sProduct = Build.PRODUCT;
        if (sProduct == null) {
            sProduct = "Emulator";
        }
        ApolloVoiceLog.LogI("Get PRODUCT : " + sProduct);
        return sProduct;
    }

    public static void SetContext(Context context) {
        mainContext = context;
    }
}
