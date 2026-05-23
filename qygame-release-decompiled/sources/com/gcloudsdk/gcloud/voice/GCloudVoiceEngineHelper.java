package com.gcloudsdk.gcloud.voice;

/* JADX INFO: loaded from: classes.dex */
public class GCloudVoiceEngineHelper {
    public static native int APITrace(String str, String str2);

    public static native void AndroidInit();

    public static native int ApplyMessageKey(int i);

    public static native int ApplyMessageKey(String str, int i, int i2);

    public static native int AuditionFileForMagicType(String str, String str2);

    public static native int ChangeRole(int i, String str);

    public static native int CloseMic();

    public static native int CloseSpeaker();

    public static native int DownloadRecordedFile(String str, String str2, int i);

    public static native int DownloadRecordedFile(String str, String str2, int i, boolean z);

    public static native int EnableAccFilePlay(boolean z);

    public static native void EnableBluetoothSCO(boolean z);

    public static native int EnableCivilFile(boolean z);

    public static native int EnableCivilVoice(boolean z);

    public static native int EnableEarBack(boolean z);

    public static native int EnableKeyWordsDetect(boolean z);

    public static native int EnableLog(boolean z);

    public static native int EnableMagicVoice(String str, boolean z);

    public static native int EnableMultiRoom(boolean z);

    public static native int EnableNativeBGMPlay(boolean z);

    public static native int EnableRecvMagicVoice(boolean z);

    public static native int EnableReportALL(boolean z);

    public static native int EnableReportALLAbroad(boolean z);

    public static native int EnableReportForAbroad(boolean z);

    public static native int EnableRoomMicrophone(String str, boolean z);

    public static native int EnableRoomSpeaker(String str, boolean z);

    public static native int EnableSpeakerOn(boolean z);

    public static native int EnableTranslate(String str, boolean z, int i, int i2);

    public static native int EngineJniInstance();

    public static native int ForbidMemberVoice(int i, boolean z, String str);

    public static native int ForbidUseIDVoice(String str, boolean z, String str2);

    public static native int GetAccFileTotalTimeByMs();

    public static native int GetAccPlayTimeByMs();

    public static native int GetBGMLevel();

    public static native int GetBGMPlayState();

    public static native int GetCurPlayTimeForPreview();

    public static native int GetFileParam(String str, Integer num, Float f);

    public static native int GetMicLevel();

    public static native int GetMicState();

    public static native int GetMuteResult();

    public static native int GetRecordKaraokeTotalTime();

    public static native int GetRoomMembers(String str, String[] strArr, int[] iArr, int[] iArr2);

    public static native int GetSpeakerLevel();

    public static native int GetSpeakerState();

    public static native boolean GetUseID(String str, int i, String str2);

    public static native int Init();

    public static native int Invoke(int i, int i2, int i3, int[] iArr);

    public static native int IsRecordComplete(boolean z);

    public static native int IsSaveMagicFile(boolean z);

    public static native int IsSpeaking();

    public static native boolean IsUseIDBeForbidVoice(String str, String str2);

    public static native int JoinNationalRoom(String str, int i, int i2);

    public static native int JoinNationalRoom(String str, int i, String str2, int i2, int i3);

    public static native int JoinNationalRoomByScenes(String str, String str2, int i, int i2);

    public static native int JoinRangeRoom(String str, int i);

    public static native int JoinRangeRoomByScenes(String str, String str2, int i);

    public static native int JoinTeamRoom(String str, int i);

    public static native int JoinTeamRoom(String str, String str2, int i, int i2);

    public static native int JoinTeamRoomByScenes(String str, String str2, int i);

    public static native int OpenMic();

    public static native int OpenSpeaker();

    public static native int Pause();

    public static native int PauseBGMPlay();

    public static native int PauseKaraoke();

    public static native int PlayRecordedFile(String str);

    public static native int Poll();

    public static native int QuitRoom(String str, int i);

    public static native int QuitRoomByScenes(String str, int i);

    public static native int RSTSSpeechToSpeech(int i, int[] iArr, int i2, String str, int i3, float f, float f2, int i4, String str2);

    public static native int RSTSSpeechToText(int i, int[] iArr, int i2, int i3, String str);

    public static native int RSTSStartRecording(int i, int[] iArr, int i2, int i3, int i4, String str);

    public static native int RSTSStopRecording();

    public static native int ReportFileForAbroad(String str, boolean z, boolean z2, int i);

    public static native int ReportPlayer(String[] strArr, int i, String str);

    public static native int Resume();

    public static native int ResumeBGMPlay();

    public static native int ResumeKaraoke();

    public static native int RoomGeneralDataChannel(String str, String str2);

    public static native int SeekTimeMsForAcc(int i);

    public static native int SeekTimeMsForPreview(int i);

    public static native int SetAppInfo(String str, String str2, String str3);

    public static native int SetAudience(int[] iArr, int i, String str);

    public static native int SetBGMPath(String str);

    public static native int SetBGMVol(int i);

    public static native int SetBitRate(int i);

    public static native int SetCivilBinPath(String str);

    public static native int SetDataFree(boolean z);

    public static native int SetKaraokeAccVol(int i);

    public static native int SetKaraokeVoiceDelay(int i);

    public static native int SetKaraokeVoiceVol(int i);

    public static native int SetMagicVoiceMsgType(String str);

    public static native int SetMicVolume(int i);

    public static native int SetMode(int i);

    public static native int SetNotify(IGCloudVoiceNotify iGCloudVoiceNotify);

    public static native int SetPlayerForUseID(String[] strArr, int i, String str);

    public static native int SetPlayerInfoAbroad(String[] strArr, int[] iArr, int[] iArr2, int i);

    public static native int SetRSTTServerInfo(String str, String str2, String str3);

    public static native int SetReportBufferTime(int i);

    public static native int SetReportedPlayerInfo(String[] strArr, int[] iArr, int i);

    public static native int SetServerInfo(String str);

    public static native int SetSpeakerVolume(int i);

    public static native int SetVoiceEffects(int i);

    public static native int SetVolumeForRoom(String str, int i);

    public static native int SetVolumeForUseID(String str, String str2, int i);

    public static native int SpeechFileToText(String str, int i, int i2, int i3);

    public static native int SpeechFileTranslate(String str, int i, int i2, int i3, float f, float f2, int i4);

    public static native int SpeechToText(String str, int i, int i2);

    public static native int SpeechToText(String str, String str2, int i, int i2, int i3);

    public static native int SpeechTranslate(String str, int i, int i2, int i3, int i4);

    public static native int StartBGMPlay();

    public static native int StartKaraokeRecording(String str, String str2, String str3);

    public static native int StartPreview();

    public static native int StartRecording(String str);

    public static native int StopBGMPlay();

    public static native int StopKaraokeRecording();

    public static native int StopPlayFile();

    public static native int StopPreview();

    public static native int StopRecording();

    public static native int TestMic();

    public static native int TextToSpeech(String str, int i, int i2, int i3, String str2);

    public static native int TextToSpeechFile(String str, int i, String str2, int i2, float f, float f2, int i3, String str3);

    public static native int TextToStreamSpeechStart(String str, String str2, int i, String str3, String str4);

    public static native int TextToStreamSpeechStop();

    public static native int TextTranslate(String str, int i, int i2, int i3);

    public static native int UpdateCoordinate(String str, long j, long j2, long j3, long j4);

    public static native int UploadRecordedFile(String str, int i);

    public static native int UploadRecordedFile(String str, int i, boolean z);
}
