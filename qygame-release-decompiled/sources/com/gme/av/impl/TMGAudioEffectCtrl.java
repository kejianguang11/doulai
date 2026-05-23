package com.gme.av.impl;

import com.gme.TMG.ITMGAudioEffectCtrl;
import com.gme.TMG.ITMGType;
import com.gme.av.jni.GMESDKAudioEffectJni;

/* JADX INFO: loaded from: classes.dex */
public class TMGAudioEffectCtrl extends ITMGAudioEffectCtrl {
    public static final String TAG = "TMGAudioEffectCtrl";
    private final GMESDKAudioEffectJni mAudioEffectJni;

    TMGAudioEffectCtrl(GMESDKAudioEffectJni gMESDKAudioEffectJni) {
        this.mAudioEffectJni = gMESDKAudioEffectJni;
    }

    @Override // com.gme.TMG.ITMGAudioEffectCtrl
    public int EnableMusicPlayout(long j, boolean z) {
        return this.mAudioEffectJni.enableMusicPlayout(j, z);
    }

    @Override // com.gme.TMG.ITMGAudioEffectCtrl
    public int EnableMusicPublish(long j, boolean z) {
        return this.mAudioEffectJni.enableMusicPublish(j, z);
    }

    @Override // com.gme.TMG.ITMGAudioEffectCtrl
    public int GetMusicCurrentPosInMS(long j) {
        return this.mAudioEffectJni.getMusicCurrentPosInMS(j);
    }

    @Override // com.gme.TMG.ITMGAudioEffectCtrl
    public int GetMusicDurationInMS(long j) {
        return this.mAudioEffectJni.getMusicDurationInMS(j);
    }

    @Override // com.gme.TMG.ITMGAudioEffectCtrl
    public int GetMusicPlayoutVolume(long j) {
        return this.mAudioEffectJni.getMusicPlayoutVolume(j);
    }

    @Override // com.gme.TMG.ITMGAudioEffectCtrl
    public int GetMusicPublishVolume(long j) {
        return this.mAudioEffectJni.getMusicPublishVolume(j);
    }

    @Override // com.gme.TMG.ITMGAudioEffectCtrl
    public boolean IsMusicPlayEnd(long j) {
        return this.mAudioEffectJni.isMusicPlayEnd(j);
    }

    @Override // com.gme.TMG.ITMGAudioEffectCtrl
    public int PausePlayMusic(long j) {
        return this.mAudioEffectJni.pausePlayMusic(j);
    }

    @Override // com.gme.TMG.ITMGAudioEffectCtrl
    public int ResumePlayMusic(long j) {
        return this.mAudioEffectJni.resumePlayMusic(j);
    }

    @Override // com.gme.TMG.ITMGAudioEffectCtrl
    public int SeekMusicToPosInTime(long j, int i) {
        return this.mAudioEffectJni.seekMusicToPosInTime(j, i);
    }

    @Override // com.gme.TMG.ITMGAudioEffectCtrl
    public int SetAllMusicVolume(int i) {
        return this.mAudioEffectJni.setAllMusicVolume(i);
    }

    @Override // com.gme.TMG.ITMGAudioEffectCtrl
    public int SetKaraokeType(ITMGAudioEffectCtrl.ITMG_KARAOKE_TYPE itmg_karaoke_type) {
        return this.mAudioEffectJni.setKaraokeType(itmg_karaoke_type.getNativeValue());
    }

    @Override // com.gme.TMG.ITMGAudioEffectCtrl
    public int SetMusicPitch(long j, float f) {
        return this.mAudioEffectJni.setMusicPitch(j, f);
    }

    @Override // com.gme.TMG.ITMGAudioEffectCtrl
    public int SetMusicPlayoutVolume(long j, int i) {
        return this.mAudioEffectJni.setMusicPlayoutVolume(j, i);
    }

    @Override // com.gme.TMG.ITMGAudioEffectCtrl
    public int SetMusicPublishVolume(long j, int i) {
        return this.mAudioEffectJni.setMusicPublishVolume(j, i);
    }

    @Override // com.gme.TMG.ITMGAudioEffectCtrl
    public int SetVoiceType(ITMGType.ITMG_VOICE_TYPE itmg_voice_type) {
        return this.mAudioEffectJni.setVoiceType(itmg_voice_type.getNativeValue());
    }

    @Override // com.gme.TMG.ITMGAudioEffectCtrl
    public int StartPlayMusic(long j, String str, int i) {
        return this.mAudioEffectJni.startPlayMusic(j, str, i);
    }

    @Override // com.gme.TMG.ITMGAudioEffectCtrl
    public int StartRecord(String str, ITMGAudioEffectCtrl.ITMG_AUDIO_RECORDING_CONTENT itmg_audio_recording_content) {
        return this.mAudioEffectJni.startRecord(str, itmg_audio_recording_content.ordinal());
    }

    @Override // com.gme.TMG.ITMGAudioEffectCtrl
    public int StopPlayMusic(long j) {
        return this.mAudioEffectJni.stopPlayMusic(j);
    }

    @Override // com.gme.TMG.ITMGAudioEffectCtrl
    public int StopRecord() {
        return this.mAudioEffectJni.stopRecord();
    }
}
