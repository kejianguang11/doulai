package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.utils.Log;

/* JADX INFO: loaded from: classes.dex */
public class WXStateSceneDataObject implements SendMessageToWX.IWXSceneDataObject {
    private static final int LENGTH_LIMIT = 10240;
    private static final String TAG = "MicroMsg.SDK.WXStateSceneDataObject";
    private static final String WX_STATE_JUMP_INFO_KEY_IDENTIFIER = "_wxapi_scene_data_state_jump_info_identifier";
    public String stateId;
    public IWXStateJumpInfo stateJumpInfo;
    public String stateTitle;
    public String token;

    public interface IWXStateJumpInfo {
        public static final int WX_STATE_JUMP_TYPE_CHANNEL_PROFILE = 3;
        public static final int WX_STATE_JUMP_TYPE_MINI_PROGRAM = 2;
        public static final int WX_STATE_JUMP_TYPE_UNKNOWN = 0;
        public static final int WX_STATE_JUMP_TYPE_URL = 1;

        boolean checkArgs();

        void serialize(Bundle bundle);

        int type();

        void unserialize(Bundle bundle);
    }

    @Override // com.tencent.mm.opensdk.modelmsg.SendMessageToWX.IWXSceneDataObject
    public boolean checkArgs() {
        String str;
        String str2;
        String str3;
        String str4;
        String str5 = this.stateId;
        if ((str5 == null || str5.length() <= LENGTH_LIMIT) && (((str = this.stateTitle) == null || str.length() <= LENGTH_LIMIT) && ((str2 = this.token) == null || str2.length() <= LENGTH_LIMIT))) {
            IWXStateJumpInfo iWXStateJumpInfo = this.stateJumpInfo;
            if (iWXStateJumpInfo != null) {
                return iWXStateJumpInfo.checkArgs();
            }
            str3 = TAG;
            str4 = "checkArgs fail, statsJumpInfo is null";
        } else {
            str3 = TAG;
            str4 = "checkArgs fail, stateId is invalid";
        }
        Log.e(str3, str4);
        return false;
    }

    @Override // com.tencent.mm.opensdk.modelmsg.SendMessageToWX.IWXSceneDataObject
    public int getJumpType() {
        IWXStateJumpInfo iWXStateJumpInfo = this.stateJumpInfo;
        if (iWXStateJumpInfo != null) {
            return iWXStateJumpInfo.type();
        }
        return 0;
    }

    @Override // com.tencent.mm.opensdk.modelmsg.SendMessageToWX.IWXSceneDataObject
    public void serialize(Bundle bundle) {
        bundle.putString("_wxapi_scene_data_state_id", this.stateId);
        bundle.putString("_wxapi_scene_data_state_title", this.stateTitle);
        bundle.putString("_wxapi_scene_data_state_token", this.token);
        IWXStateJumpInfo iWXStateJumpInfo = this.stateJumpInfo;
        if (iWXStateJumpInfo != null) {
            bundle.putString(WX_STATE_JUMP_INFO_KEY_IDENTIFIER, iWXStateJumpInfo.getClass().getName());
            this.stateJumpInfo.serialize(bundle);
        }
    }

    @Override // com.tencent.mm.opensdk.modelmsg.SendMessageToWX.IWXSceneDataObject
    public void unserialize(Bundle bundle) {
        this.stateId = bundle.getString("_wxapi_scene_data_state_id");
        this.stateTitle = bundle.getString("_wxapi_scene_data_state_title");
        this.token = bundle.getString("_wxapi_scene_data_state_token");
        String string = bundle.getString(WX_STATE_JUMP_INFO_KEY_IDENTIFIER);
        if (string != null) {
            try {
                IWXStateJumpInfo iWXStateJumpInfo = (IWXStateJumpInfo) Class.forName(string).newInstance();
                this.stateJumpInfo = iWXStateJumpInfo;
                iWXStateJumpInfo.unserialize(bundle);
            } catch (Exception e) {
                Log.e(TAG, "get WXSceneDataObject from bundle failed: unknown ident " + string + ", ex = " + e.getMessage());
            }
        }
    }
}
