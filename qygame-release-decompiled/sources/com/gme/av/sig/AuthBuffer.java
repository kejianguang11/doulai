package com.gme.av.sig;

import com.gme.av.jni.GMESDKJni;
import com.gme.av.utils.GMELibLoader;

/* JADX INFO: loaded from: classes.dex */
public class AuthBuffer {
    private static boolean mIsSoLoaded = false;
    private static AuthBuffer sAuthBuffer;

    private AuthBuffer() {
    }

    public static AuthBuffer getInstance() {
        AuthBuffer authBuffer;
        synchronized (AuthBuffer.class) {
            if (sAuthBuffer == null) {
                loadSo();
                if (mIsSoLoaded) {
                    sAuthBuffer = new AuthBuffer();
                }
            }
            authBuffer = sAuthBuffer;
        }
        return authBuffer;
    }

    private static void loadSo() {
        mIsSoLoaded = GMELibLoader.loadSdkLibrary() == 0;
    }

    public byte[] genAuthBuffer(int i, String str, String str2, String str3) {
        return GMESDKJni.genAuthBuffer(i, str, str2, str3);
    }
}
