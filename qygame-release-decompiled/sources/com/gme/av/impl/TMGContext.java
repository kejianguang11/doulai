package com.gme.av.impl;

import android.app.Activity;
import android.content.Context;
import com.gme.TMG.ITMGAudioCtrl;
import com.gme.TMG.ITMGAudioEffectCtrl;
import com.gme.TMG.ITMGContext;
import com.gme.TMG.ITMGPTT;
import com.gme.TMG.ITMGRoom;
import com.gme.TMG.ITMGType;
import com.gme.av.jni.GMESDKJni;
import com.gme.av.utils.QLog;
import com.gme.liteav.base.ContextUtils;
import com.gme.liteav.base.util.e;

/* JADX INFO: loaded from: classes.dex */
public class TMGContext extends ITMGContext {
    public static final String TAG = "TMGContext";
    private TMGAudioCtrl mAudioCtrl;
    private TMGAudioEffectCtrl mAudioEffectCtrl;
    private Context mContext;
    private ITMGContext.ITMGDelegate mDelegate;
    private GMESDKJni mNativeSDKJni;
    private TMGPTT mPTT;
    private TMGRoom mRoom;

    public TMGContext(Context context) {
        this.mContext = context == null ? null : context.getApplicationContext();
        ContextUtils.initApplicationContext(this.mContext);
        ContextUtils.setDataDirectorySuffix("gme");
        initCloud();
        if (context instanceof Activity) {
            e.a().a((Activity) context);
        }
    }

    private void initCloud() {
        this.mNativeSDKJni = new GMESDKJni();
        this.mRoom = new TMGRoom(this.mNativeSDKJni.getRoomJni());
        this.mAudioCtrl = new TMGAudioCtrl(this.mNativeSDKJni.getAudioJni());
        this.mAudioEffectCtrl = new TMGAudioEffectCtrl(this.mNativeSDKJni.getAudioEffectJni());
        this.mPTT = new TMGPTT(this.mNativeSDKJni.getPTTJni());
    }

    @Override // com.gme.TMG.ITMGContext
    public ITMGType.ITMG_MIC_PERMISSION CheckMicPermission() {
        return ITMGType.ITMG_MIC_PERMISSION.values()[this.mNativeSDKJni.checkMicPermission()];
    }

    @Override // com.gme.TMG.ITMGContext
    public int EnterRoom(String str, ITMGType.ITMG_ROOM_TYPE itmg_room_type, byte[] bArr) {
        return this.mNativeSDKJni.enterRoom(str, itmg_room_type.getNativeValue(), bArr);
    }

    @Override // com.gme.TMG.ITMGContext
    public int ExitRoom() {
        return this.mNativeSDKJni.exitRoom();
    }

    @Override // com.gme.TMG.ITMGContext
    public String GetAdvanceParams(String str) {
        return this.mNativeSDKJni.getAdvanceParam(str);
    }

    @Override // com.gme.TMG.ITMGContext
    public ITMGAudioCtrl GetAudioCtrl() {
        return this.mAudioCtrl;
    }

    @Override // com.gme.TMG.ITMGContext
    public ITMGAudioEffectCtrl GetAudioEffectCtrl() {
        return this.mAudioEffectCtrl;
    }

    @Override // com.gme.TMG.ITMGContext
    public String GetLogPath() {
        return this.mNativeSDKJni.getLogPath();
    }

    @Override // com.gme.TMG.ITMGContext
    public ITMGPTT GetPTT() {
        return this.mPTT;
    }

    @Override // com.gme.TMG.ITMGContext
    public ITMGRoom GetRoom() {
        return this.mRoom;
    }

    @Override // com.gme.TMG.ITMGContext
    public String GetSDKVersion() {
        return this.mNativeSDKJni.getSDKVersion();
    }

    @Override // com.gme.TMG.ITMGContext
    public int Init(String str, String str2) {
        return this.mNativeSDKJni.init(str, str2);
    }

    @Override // com.gme.TMG.ITMGContext
    public boolean IsRoomEntered() {
        return this.mNativeSDKJni.isRoomEntered();
    }

    @Override // com.gme.TMG.ITMGContext
    public int Pause() {
        return this.mNativeSDKJni.pause();
    }

    @Override // com.gme.TMG.ITMGContext
    public int Poll() {
        return this.mNativeSDKJni.poll();
    }

    @Override // com.gme.TMG.ITMGContext
    public int Resume() {
        return this.mNativeSDKJni.resume();
    }

    @Override // com.gme.TMG.ITMGContext
    public int SetAdvanceParams(String str, String str2) {
        return this.mNativeSDKJni.setAdvanceParams(str, str2);
    }

    @Override // com.gme.TMG.ITMGContext
    public void SetAppVersion(String str) {
        this.mNativeSDKJni.setAppVersion(str);
    }

    @Override // com.gme.TMG.ITMGContext
    public int SetAudioRole(ITMGType.ITMG_AUDIO_MEMBER_ROLE itmg_audio_member_role) {
        return this.mNativeSDKJni.setAudioRole(itmg_audio_member_role.getNativeValue());
    }

    @Override // com.gme.TMG.ITMGContext
    public int SetLogLevel(ITMGType.ITMG_LOG_LEVEL itmg_log_level, ITMGType.ITMG_LOG_LEVEL itmg_log_level2) {
        return this.mNativeSDKJni.setLogLevel(itmg_log_level.getNativeValue(), itmg_log_level2.getNativeValue());
    }

    @Override // com.gme.TMG.ITMGContext
    public int SetLogPath(String str) {
        return this.mNativeSDKJni.setLogPath(str);
    }

    @Override // com.gme.TMG.ITMGContext
    public int SetRangeAudioMode(ITMGType.ITMG_RANGE_AUDIO_MODE itmg_range_audio_mode) {
        return this.mNativeSDKJni.setRangeAudioMode(itmg_range_audio_mode.getNativeValue());
    }

    @Override // com.gme.TMG.ITMGContext
    public int SetRangeAudioTeamID(int i) {
        return this.mNativeSDKJni.setRangeAudioTeamID(i);
    }

    @Override // com.gme.TMG.ITMGContext
    public int SetRecvMixStreamCount(int i) {
        return this.mNativeSDKJni.setRecvMixStreamCount(i);
    }

    @Override // com.gme.TMG.ITMGContext
    public void SetRegion(String str) {
        this.mNativeSDKJni.setRegion(str);
    }

    @Override // com.gme.TMG.ITMGContext
    public int SetScene(ITMGType.ITMG_APP_SCENE itmg_app_scene) {
        return this.mNativeSDKJni.setScene(itmg_app_scene.getNativeValue());
    }

    @Override // com.gme.TMG.ITMGContext
    public int SetTMGDelegate(ITMGContext.ITMGDelegate iTMGDelegate) {
        QLog.i(TAG, String.format("SetTMGDelegate.delegate=%s", iTMGDelegate));
        this.mDelegate = iTMGDelegate;
        if (this.mNativeSDKJni != null) {
            this.mNativeSDKJni.setDelegate(this.mDelegate);
        }
        return 0;
    }

    @Override // com.gme.TMG.ITMGContext
    public int ShowDebugView(boolean z) {
        return this.mNativeSDKJni.showDebugView(z);
    }

    @Override // com.gme.TMG.ITMGContext
    public int Uninit() {
        return this.mNativeSDKJni.unInit();
    }
}
