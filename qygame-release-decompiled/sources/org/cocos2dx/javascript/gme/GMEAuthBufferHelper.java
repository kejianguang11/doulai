package org.cocos2dx.javascript.gme;

import android.text.TextUtils;
import com.gme.av.sig.AuthBuffer;

/* JADX INFO: loaded from: classes.dex */
public class GMEAuthBufferHelper {
    private static GMEAuthBufferHelper mInstance;
    private String mAppId;
    private String mKey;
    private String mUserID;

    private GMEAuthBufferHelper() {
    }

    public static GMEAuthBufferHelper getInstance() {
        if (mInstance == null) {
            synchronized (GMEAuthBufferHelper.class) {
                if (mInstance == null) {
                    mInstance = new GMEAuthBufferHelper();
                }
            }
        }
        return mInstance;
    }

    public byte[] createAuthBuffer(String str) {
        return TextUtils.isEmpty(str) ? AuthBuffer.getInstance().genAuthBuffer(Integer.parseInt(this.mAppId), "0", this.mUserID, this.mKey) : AuthBuffer.getInstance().genAuthBuffer(Integer.parseInt(this.mAppId), str, this.mUserID, this.mKey);
    }

    public void setGMEParams(String str, String str2, String str3) {
        this.mAppId = str;
        this.mKey = str2;
        this.mUserID = str3;
    }
}
