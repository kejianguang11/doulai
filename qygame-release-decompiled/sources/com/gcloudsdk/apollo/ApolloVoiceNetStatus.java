package com.gcloudsdk.apollo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/* JADX INFO: loaded from: classes.dex */
public class ApolloVoiceNetStatus {
    private static String UNKNOWN = "Unknown";
    private static Context mainContext;

    public static String Net() {
        String str = UNKNOWN;
        if (mainContext == null) {
            ApolloVoiceLog.LogE("mainContext is null. May init java first");
            return str;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) mainContext.getSystemService("connectivity");
        if (connectivityManager == null) {
            return UNKNOWN;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            str = UNKNOWN;
        } else if (activeNetworkInfo.getType() == 1) {
            str = "WiFi";
        } else if (activeNetworkInfo.getType() == 0) {
            int subtype = activeNetworkInfo.getSubtype();
            if (subtype == 4 || subtype == 1 || subtype == 2 || subtype == 1) {
                str = "2G";
            } else if (subtype == 3 || subtype == 8 || subtype == 6 || subtype == 5 || subtype == 12) {
                str = "3G";
            } else if (subtype == 13) {
                str = "4G";
            }
        }
        ApolloVoiceLog.LogI("Android Java Get Net status: " + str);
        return str;
    }

    public static void SetContext(Context context) {
        mainContext = context;
    }
}
