package com.gme.av.impl;

import com.gme.TMG.ITMGAudioCtrl;
import com.gme.TMG.ITMGType;
import com.gme.av.jni.GMESDKAudioJni;

/* JADX INFO: loaded from: classes.dex */
public class TMGAudioCtrl extends ITMGAudioCtrl {
    public static final String TAG = "TMGAudioCtrl";
    private final GMESDKAudioJni mNativeAudioJni;

    TMGAudioCtrl(GMESDKAudioJni gMESDKAudioJni) {
        this.mNativeAudioJni = gMESDKAudioJni;
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int AddAudioBlackList(String str) {
        return this.mNativeAudioJni.addAudioBlackList(str);
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int EnableAudioCaptureDevice(boolean z) {
        return this.mNativeAudioJni.enableAudioCaptureDevice(z);
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int EnableAudioPlayDevice(boolean z) {
        return this.mNativeAudioJni.enableAudioPlayDevice(z);
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int EnableAudioRecv(boolean z) {
        return this.mNativeAudioJni.enableAudioRecv(z);
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int EnableAudioSend(boolean z) {
        return this.mNativeAudioJni.enableAudioSend(z);
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int EnableCustomAudioCapture(boolean z) {
        return this.mNativeAudioJni.EnableCustomAudioCapture(z);
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int EnableCustomAudioRendering(boolean z) {
        return this.mNativeAudioJni.EnableCustomAudioRendering(z);
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int EnableLoopBack(boolean z) {
        return this.mNativeAudioJni.enableLoopBack(z);
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int EnableMic(boolean z) {
        int iEnableAudioCaptureDevice = EnableAudioCaptureDevice(z);
        int iEnableAudioSend = EnableAudioSend(z);
        if (iEnableAudioCaptureDevice == 0 && iEnableAudioSend == 0) {
            return 0;
        }
        return iEnableAudioCaptureDevice != 0 ? iEnableAudioCaptureDevice : iEnableAudioSend;
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int EnableSpatializer(boolean z) {
        return this.mNativeAudioJni.EnableSpatializer(z);
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int EnableSpeaker(boolean z) {
        int iEnableAudioPlayDevice = EnableAudioPlayDevice(z);
        int iEnableAudioRecv = EnableAudioRecv(z);
        if (iEnableAudioPlayDevice == 0 && iEnableAudioRecv == 0) {
            return 0;
        }
        return iEnableAudioPlayDevice != 0 ? iEnableAudioPlayDevice : iEnableAudioRecv;
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int GetCustomAudioRenderingFrame(ITMGType.TMGAudioFrame tMGAudioFrame) {
        return this.mNativeAudioJni.GetCustomAudioRenderingFrame(tMGAudioFrame);
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int GetMicLevel() {
        return this.mNativeAudioJni.GetMicLevel();
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int GetMicState() {
        return (IsAudioCaptureDeviceEnabled() && IsAudioSendEnabled()) ? 1 : 0;
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int GetMicVolume() {
        return this.mNativeAudioJni.getMicVolume();
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int GetRecvStreamLevel(String str) {
        return this.mNativeAudioJni.GetRecvStreamLevel(str);
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int GetSendStreamLevel() {
        return this.mNativeAudioJni.GetSendStreamLevel();
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int GetSpeakerLevel() {
        return this.mNativeAudioJni.GetSpeakerLevel();
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int GetSpeakerState() {
        return (IsAudioPlayDeviceEnabled() && IsAudioRecvEnabled()) ? 1 : 0;
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int GetSpeakerVolume() {
        return this.mNativeAudioJni.getSpeakerVolume();
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int GetSpeakerVolumeByUserID(String str) {
        return this.mNativeAudioJni.getSpeakerVolumeByUserID(str);
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int InitSpatializer(String str) {
        return this.mNativeAudioJni.InitSpatializer(str);
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public boolean IsAudioCaptureDeviceEnabled() {
        return this.mNativeAudioJni.isAudioCaptureDeviceEnabled();
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public boolean IsAudioPlayDeviceEnabled() {
        return this.mNativeAudioJni.isAudioPlayDeviceEnabled();
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public boolean IsAudioRecvEnabled() {
        return this.mNativeAudioJni.isAudioRecvEnabled();
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public boolean IsAudioSendEnabled() {
        return this.mNativeAudioJni.isAudioSendEnabled();
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public boolean IsEnableSpatializer() {
        return this.mNativeAudioJni.IsEnableSpatializer();
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public boolean IsUserIDInAudioBlackList(String str) {
        return this.mNativeAudioJni.isUserIDInAudioBlackList(str);
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int RemoveAudioBlackList(String str) {
        return this.mNativeAudioJni.removeAudioBlackList(str);
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int SendCustomAudioData(ITMGType.TMGAudioFrame tMGAudioFrame) {
        return this.mNativeAudioJni.SendCustomAudioData(tMGAudioFrame);
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public void SetAudioFrameCallback(ITMGAudioCtrl.ITMGAudioFrameCallback iTMGAudioFrameCallback) {
        this.mNativeAudioJni.setAudioFrameCallback(iTMGAudioFrameCallback);
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int SetAudioRoute(ITMGType.ITMG_AUDIO_ROUTE itmg_audio_route) {
        return this.mNativeAudioJni.SetAudioRoute(itmg_audio_route.getNativeValue());
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int SetLoopBackVolume(int i) {
        return this.mNativeAudioJni.setLoopBackVolume(i);
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int SetMicVolume(int i) {
        return this.mNativeAudioJni.setMicVolume(i);
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int SetSpeakerVolume(int i) {
        return this.mNativeAudioJni.setSpeakerVolume(i);
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int SetSpeakerVolumeByUserID(String str, int i) {
        return this.mNativeAudioJni.setSpeakerVolumeByUserID(str, i);
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int StopTrackingVolume() {
        return this.mNativeAudioJni.StopTrackingVolume();
    }

    @Override // com.gme.TMG.ITMGAudioCtrl
    public int TrackingVolume(float f) {
        return this.mNativeAudioJni.TrackingVolume(f);
    }
}
