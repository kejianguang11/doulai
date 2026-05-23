package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;

/* JADX INFO: loaded from: classes.dex */
public class WXChannelJumpUrlInfo extends WXChannelBaseJumpInfo {
    private static final int LENGTH_LIMIT = 10240;
    private static final String TAG = "MicroMsg.SDK.WXChannelJumpUrlInfo";
    public String url;

    @Override // com.tencent.mm.opensdk.modelbiz.WXChannelBaseJumpInfo, com.tencent.mm.opensdk.modelbiz.IWXChannelJumpInfo
    public boolean checkArgs() {
        String str;
        String str2;
        String str3 = this.url;
        if (str3 == null || str3.length() <= 0) {
            str = TAG;
            str2 = "checkArgs fail, url is null";
        } else {
            if (this.url.length() < LENGTH_LIMIT) {
                return true;
            }
            str = TAG;
            str2 = "checkArgs fail, url is invalid";
        }
        Log.e(str, str2);
        return false;
    }

    @Override // com.tencent.mm.opensdk.modelbiz.WXChannelBaseJumpInfo, com.tencent.mm.opensdk.modelbiz.IWXChannelJumpInfo
    public void serialize(Bundle bundle) {
        super.serialize(bundle);
        bundle.putString("wx_channel_jump_url", this.url);
    }

    @Override // com.tencent.mm.opensdk.modelbiz.IWXChannelJumpInfo
    public int type() {
        return 2;
    }

    @Override // com.tencent.mm.opensdk.modelbiz.WXChannelBaseJumpInfo, com.tencent.mm.opensdk.modelbiz.IWXChannelJumpInfo
    public void unserialize(Bundle bundle) {
        super.unserialize(bundle);
        this.url = bundle.getString("wx_channel_jump_url");
    }
}
