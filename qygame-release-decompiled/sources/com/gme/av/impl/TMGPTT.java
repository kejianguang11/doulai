package com.gme.av.impl;

import com.gme.TMG.ITMGPTT;
import com.gme.TMG.ITMGType;
import com.gme.av.jni.GMESDKPTTJni;

/* JADX INFO: loaded from: classes.dex */
public class TMGPTT extends ITMGPTT {
    public static final String TAG = TMGAudioCtrl.class.getSimpleName();
    private final GMESDKPTTJni mNativePttJni;

    TMGPTT(GMESDKPTTJni gMESDKPTTJni) {
        this.mNativePttJni = gMESDKPTTJni;
    }

    @Override // com.gme.TMG.ITMGPTT
    public int ApplyPTTAuthbuffer(byte[] bArr) {
        return this.mNativePttJni.applyPTTAuthbuffer(bArr);
    }

    @Override // com.gme.TMG.ITMGPTT
    public int CancelRecording() {
        return this.mNativePttJni.cancelRecording();
    }

    @Override // com.gme.TMG.ITMGPTT
    public int DownloadRecordedFile(String str, String str2) {
        return this.mNativePttJni.downloadRecordedFile(str, str2);
    }

    @Override // com.gme.TMG.ITMGPTT
    public int GetFileSize(String str) {
        return this.mNativePttJni.getFileSize(str);
    }

    @Override // com.gme.TMG.ITMGPTT
    public int GetVoiceFileDuration(String str) {
        return this.mNativePttJni.getVoiceFileDuration(str);
    }

    @Override // com.gme.TMG.ITMGPTT
    public int PlayRecordedFile(String str) {
        return this.mNativePttJni.playRecordedFile(str, ITMGType.ITMG_VOICE_TYPE.ITMG_VOICE_TYPE_ORIGINAL_SOUND.getNativeValue());
    }

    @Override // com.gme.TMG.ITMGPTT
    public int PlayRecordedFile(String str, ITMGType.ITMG_VOICE_TYPE itmg_voice_type) {
        return this.mNativePttJni.playRecordedFile(str, itmg_voice_type.getNativeValue());
    }

    @Override // com.gme.TMG.ITMGPTT
    public int SetMaxMessageLength(int i) {
        return this.mNativePttJni.setMaxMessageLength(i);
    }

    @Override // com.gme.TMG.ITMGPTT
    public int SetPTTSourceLanguage(String str) {
        return this.mNativePttJni.setPTTSourceLanguage(str);
    }

    @Override // com.gme.TMG.ITMGPTT
    public int SpeechToText(String str) {
        return this.mNativePttJni.speechToText(str);
    }

    @Override // com.gme.TMG.ITMGPTT
    public int SpeechToText(String str, String str2) {
        return this.mNativePttJni.speechToText(str, str2);
    }

    @Override // com.gme.TMG.ITMGPTT
    public int SpeechToText(String str, String str2, String str3) {
        return this.mNativePttJni.speechToText(str, str2, str3);
    }

    @Override // com.gme.TMG.ITMGPTT
    public int StartRecording(String str) {
        return this.mNativePttJni.startRecording(str);
    }

    @Override // com.gme.TMG.ITMGPTT
    public int StartRecordingWithStreamingRecognition(String str) {
        return this.mNativePttJni.startRecordingWithStreamingRecognition(str);
    }

    @Override // com.gme.TMG.ITMGPTT
    public int StartRecordingWithStreamingRecognition(String str, String str2) {
        return this.mNativePttJni.startRecordingWithStreamingRecognition(str, str2);
    }

    @Override // com.gme.TMG.ITMGPTT
    public int StartRecordingWithStreamingRecognition(String str, String str2, String str3) {
        return this.mNativePttJni.startRecordingWithStreamingRecognition(str, str2, str3);
    }

    @Override // com.gme.TMG.ITMGPTT
    public int StopPlayFile() {
        return this.mNativePttJni.stopPlayFile();
    }

    @Override // com.gme.TMG.ITMGPTT
    public int StopRecording() {
        return this.mNativePttJni.stopRecording();
    }

    @Override // com.gme.TMG.ITMGPTT
    public int TextToSpeech(int i, String str, String str2, String str3, float f) {
        return this.mNativePttJni.textToSpeech(i, str, str2, str3, f);
    }

    @Override // com.gme.TMG.ITMGPTT
    public int TranslateText(String str, String str2, String str3) {
        return this.mNativePttJni.translateText(str, str2, str3);
    }

    @Override // com.gme.TMG.ITMGPTT
    public int UploadRecordedFile(String str) {
        return this.mNativePttJni.uploadRecordedFile(str);
    }
}
