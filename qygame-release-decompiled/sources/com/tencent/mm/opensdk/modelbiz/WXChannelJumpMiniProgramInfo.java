package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;

/* JADX INFO: loaded from: classes.dex */
public class WXChannelJumpMiniProgramInfo extends WXChannelBaseJumpInfo {
    private static final int LENGTH_LIMIT = 10240;
    private static final String TAG = "MicroMsg.SDK.WXChannelJumpMiniProgramInfo";
    public String path;
    public String username;

    @Override // com.tencent.mm.opensdk.modelbiz.WXChannelBaseJumpInfo, com.tencent.mm.opensdk.modelbiz.IWXChannelJumpInfo
    public boolean checkArgs() {
        String str;
        String str2;
        String str3 = this.username;
        if (str3 == null || str3.length() <= 0) {
            str = TAG;
            str2 = "checkArgs fail, username is null";
        } else {
            String str4 = this.path;
            if (str4 == null || str4.length() < LENGTH_LIMIT) {
                return super.checkArgs();
            }
            str = TAG;
            str2 = "checkArgs fail, path is invalid";
        }
        Log.e(str, str2);
        return false;
    }

    @Override // com.tencent.mm.opensdk.modelbiz.WXChannelBaseJumpInfo, com.tencent.mm.opensdk.modelbiz.IWXChannelJumpInfo
    public void serialize(Bundle bundle) {
        super.serialize(bundle);
        bundle.putString("wx_channel_jump_mini_program_username", this.username);
        bundle.putString("wx_channel_jump_mini_program_path", this.path);
    }

    @Override // com.tencent.mm.opensdk.modelbiz.IWXChannelJumpInfo
    public int type() {
        return 1;
    }

    @Override // com.tencent.mm.opensdk.modelbiz.WXChannelBaseJumpInfo, com.tencent.mm.opensdk.modelbiz.IWXChannelJumpInfo
    public void unserialize(Bundle bundle) {
        super.unserialize(bundle);
        this.username = bundle.getString("wx_channel_jump_mini_program_username");
        this.path = bundle.getString("wx_channel_jump_mini_program_path");
    }
}
