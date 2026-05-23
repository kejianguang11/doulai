package com.gcloudsdk.gcloud.voice;

import com.gcloudsdk.apollo.ApolloVoiceLog;

/* JADX INFO: loaded from: classes.dex */
public class GCloudVoiceEngineExtension {
    private static GCloudVoiceEngineHelper JNIHelper;

    protected GCloudVoiceEngineExtension() {
        JNIHelper = new GCloudVoiceEngineHelper();
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        GCloudVoiceEngineHelper.EngineJniInstance();
    }

    public int APITrace(String str, String str2) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.APITrace(str, str2);
    }

    public int ApplyMessageKey_Token(String str, int i, int i2) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.ApplyMessageKey(str, i, i2);
    }

    public int AuditionFileForMagicType(String str, String str2) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.AuditionFileForMagicType(str, str2);
    }

    public int DownloadRecordedFile(String str, String str2, int i, boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.DownloadRecordedFile(str, str2, i, z);
    }

    public int EnableAccFilePlay(boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.EnableAccFilePlay(z);
    }

    public void EnableBluetoothSCO(boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        GCloudVoiceEngineHelper.EnableBluetoothSCO(z);
    }

    public int EnableCivilFile(boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.EnableCivilFile(z);
    }

    public int EnableCivilVoice(boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.EnableCivilVoice(z);
    }

    public int EnableEarBack(boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.EnableEarBack(z);
    }

    public int EnableKeyWordsDetect(boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.EnableKeyWordsDetect(z);
    }

    public int EnableLog(boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.EnableLog(z);
    }

    public int EnableMagicVoice(String str, boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.EnableMagicVoice(str, z);
    }

    public int EnableMultiRoom(boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.EnableMultiRoom(z);
    }

    public int EnableNativeBGMPlay(boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.EnableNativeBGMPlay(z);
    }

    public int EnableRecvMagicVoice(boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.EnableRecvMagicVoice(z);
    }

    public int EnableReportALL(boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.EnableReportALL(z);
    }

    public int EnableReportALLAbroad(boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.EnableReportALLAbroad(z);
    }

    public int EnableReportForAbroad(boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.EnableReportForAbroad(z);
    }

    public int EnableRoomMicrophone(String str, boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.EnableRoomMicrophone(str, z);
    }

    public int EnableRoomSpeaker(String str, boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.EnableRoomSpeaker(str, z);
    }

    public int EnableSpeakerOn(boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.EnableSpeakerOn(z);
    }

    public int EnableTranslate(String str, boolean z, int i, int i2) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.EnableTranslate(str, z, i, i2);
    }

    public int ForbidMemberVoice(int i, boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.ForbidMemberVoice(i, z, "");
    }

    public int ForbidMemberVoice(int i, boolean z, String str) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.ForbidMemberVoice(i, z, str);
    }

    public int ForbidUseIDVoice(String str, boolean z, String str2) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.ForbidUseIDVoice(str, z, str2);
    }

    public int GetAccFileTotalTimeByMs() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.GetAccFileTotalTimeByMs();
    }

    public int GetAccPlayTimeByMs() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.GetAccPlayTimeByMs();
    }

    public int GetBGMFileTime() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.GetAccFileTotalTimeByMs();
    }

    public int GetBGMLevel() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.GetBGMLevel();
    }

    public int GetBGMPlayState() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.GetBGMPlayState();
    }

    public int GetBGMPlayTime() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.GetAccPlayTimeByMs();
    }

    public int GetCurPlayTimeForPreview() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.GetCurPlayTimeForPreview();
    }

    public int GetFileParam(String str, Integer num, Float f) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.GetFileParam(str, num, f);
    }

    public int GetMicLevel() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.GetMicLevel();
    }

    public int GetMicState() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.GetMicState();
    }

    public int GetMuteResult() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.GetMuteResult();
    }

    public int GetRecordKaraokeTotalTime() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.GetRecordKaraokeTotalTime();
    }

    public int GetRoomMembers(String str, RoomMember[] roomMemberArr) {
        if (roomMemberArr == null || roomMemberArr.length == 0) {
            GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
            return GCloudVoiceEngineHelper.GetRoomMembers(str, null, null, null);
        }
        int length = roomMemberArr.length;
        String[] strArr = new String[length];
        int[] iArr = new int[length];
        int[] iArr2 = new int[length];
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper2 = JNIHelper;
        int iGetRoomMembers = GCloudVoiceEngineHelper.GetRoomMembers(str, strArr, iArr, iArr2);
        if (iGetRoomMembers > 0) {
            for (int i = 0; i < iGetRoomMembers && i < roomMemberArr.length; i++) {
                if (roomMemberArr[i] == null) {
                    ApolloVoiceLog.LogI("getroommbers ret=" + iGetRoomMembers + " but roommembers is null,maybe you need new it");
                    return 0;
                }
                roomMemberArr[i].openid = strArr[i];
                roomMemberArr[i].memberid = iArr[i];
                roomMemberArr[i].status = iArr2[i];
            }
        }
        return iGetRoomMembers;
    }

    public int GetSpeakerLevel() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.GetSpeakerLevel();
    }

    public int GetSpeakerState() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.GetSpeakerState();
    }

    public boolean GetUseID(String str, int i, String str2) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.GetUseID(str, i, str2);
    }

    public int Invoke(int i, int i2, int i3, int[] iArr) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.Invoke(i, i2, i3, iArr);
    }

    public int IsRecordComplete(boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.IsRecordComplete(z);
    }

    public int IsSaveMagicFile(boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.IsSaveMagicFile(z);
    }

    public int IsSpeaking() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.IsSpeaking();
    }

    public boolean IsUseIDBeForbidVoice(String str, String str2) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.IsUseIDBeForbidVoice(str, str2);
    }

    public int JoinNationalRoom_Scenes(String str, String str2, int i, int i2) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.JoinNationalRoomByScenes(str, str2, i, i2);
    }

    public int JoinNationalRoom_Token(String str, int i, String str2, int i2, int i3) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.JoinNationalRoom(str, i, str2, i2, i3);
    }

    public int JoinRangeRoom_Scenes(String str, String str2, int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.JoinRangeRoomByScenes(str, str2, i);
    }

    public int JoinTeamRoom_Scenes(String str, String str2, int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.JoinTeamRoomByScenes(str, str2, i);
    }

    public int JoinTeamRoom_Token(String str, String str2, int i, int i2) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.JoinTeamRoom(str, str2, i, i2);
    }

    public int PauseBGMPlay() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.PauseBGMPlay();
    }

    public int PauseKaraoke() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.PauseKaraoke();
    }

    public int QuitRoom_Scenes(String str, int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.QuitRoomByScenes(str, i);
    }

    public int RSTSSpeechToSpeech(int i, int[] iArr, int i2, String str, int i3, float f, float f2, int i4, String str2) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.RSTSSpeechToSpeech(i, iArr, i2, str, i3, f, f2, i4, str2);
    }

    public int RSTSSpeechToText(int i, int[] iArr, int i2, int i3, String str) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.RSTSSpeechToText(i, iArr, i2, i3, str);
    }

    public int RSTSStartRecording(int i, int[] iArr, int i2, int i3, int i4, String str) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.RSTSStartRecording(i, iArr, i2, i3, i4, str);
    }

    public int RSTSStopRecording() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.RSTSStopRecording();
    }

    public int ReportFileForAbroad(String str, boolean z, boolean z2, int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.ReportFileForAbroad(str, z, z2, i);
    }

    public int ReportPlayer(String[] strArr, int i, String str) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.ReportPlayer(strArr, i, str);
    }

    public int ResumeBGMPlay() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.ResumeBGMPlay();
    }

    public int ResumeKaraoke() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.ResumeKaraoke();
    }

    public int RoomGeneralDataChannel(String str, String str2) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.RoomGeneralDataChannel(str, str2);
    }

    public int SeekTimeMsForAcc(int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SeekTimeMsForAcc(i);
    }

    public int SeekTimeMsForPreview(int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SeekTimeMsForPreview(i);
    }

    public int SetAudience(int[] iArr, String str) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SetAudience(iArr, iArr.length, str);
    }

    public int SetBGMPath(String str) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SetBGMPath(str);
    }

    public int SetBGMPlayTime(int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SeekTimeMsForAcc(i);
    }

    public int SetBGMVol(int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SetBGMVol(i);
    }

    public int SetBitRate(int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SetBitRate(i);
    }

    public int SetCivilBinPath(String str) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SetCivilBinPath(str);
    }

    public int SetDataFree(boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SetDataFree(z);
    }

    public int SetKaraokeAccVol(int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SetKaraokeAccVol(i);
    }

    public int SetKaraokeVoiceDelay(int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SetKaraokeVoiceDelay(i);
    }

    public int SetKaraokeVoiceVol(int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SetKaraokeVoiceVol(i);
    }

    public int SetMagicVoiceMsgType(String str) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SetMagicVoiceMsgType(str);
    }

    public int SetMicVolume(int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SetMicVolume(i);
    }

    public int SetPlayerForUseID(String[] strArr, int i, String str) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SetPlayerForUseID(strArr, i, str);
    }

    public int SetPlayerInfoAbroad(String[] strArr, int[] iArr, int[] iArr2, int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SetPlayerInfoAbroad(strArr, iArr, iArr2, i);
    }

    public int SetRSTTServerInfo(String str, String str2, String str3) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SetRSTTServerInfo(str, str2, str3);
    }

    public int SetReportBufferTime(int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SetReportBufferTime(i);
    }

    public int SetReportedPlayerInfo(String[] strArr, int[] iArr, int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SetReportedPlayerInfo(strArr, iArr, i);
    }

    public int SetServerInfo(String str) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SetServerInfo(str);
    }

    public int SetSpeakerVolume(int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SetSpeakerVolume(i);
    }

    public int SetVoiceEffects(int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SetVoiceEffects(i);
    }

    public int SetVolumeForRoom(String str, int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SetVolumeForRoom(str, i);
    }

    public int SetVolumeForUseID(String str, String str2, int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SetVolumeForUseID(str, str2, i);
    }

    public int SpeechFileToText(String str, int i, int i2, int i3) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SpeechFileToText(str, i, i2, i3);
    }

    public int SpeechFileTranslate(String str, int i, int i2, int i3, float f, float f2, int i4) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SpeechFileTranslate(str, i, i2, i3, f, f2, i4);
    }

    public int SpeechToText_Token(String str, String str2, int i, int i2, int i3) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SpeechToText(str, str2, i, i2, i3);
    }

    public int SpeechTranslate(String str, int i, int i2, int i3, int i4) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.SpeechTranslate(str, i, i2, i3, i4);
    }

    public int StartBGMPlay() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.StartBGMPlay();
    }

    public int StartKaraokeRecording(String str, String str2, String str3) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.StartKaraokeRecording(str, str2, str3);
    }

    public int StartPreview() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.StartPreview();
    }

    public int StopBGMPlay() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.StopBGMPlay();
    }

    public int StopKaraokeRecording() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.StopKaraokeRecording();
    }

    public int StopPreview() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.StopPreview();
    }

    public int TestMic() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.TestMic();
    }

    public int TextToSpeech(String str, int i, int i2, int i3, String str2) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.TextToSpeech(str, i, i2, i3, str2);
    }

    public int TextToSpeechFile(String str, int i, String str2, int i2, float f, float f2, int i3, String str3) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.TextToSpeechFile(str, i, str2, i2, f, f2, i3, str3);
    }

    public int TextToStreamSpeechStart(String str, String str2, int i, String str3, String str4) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.TextToStreamSpeechStart(str, str2, i, str3, str4);
    }

    public int TextToStreamSpeechStop() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.TextToStreamSpeechStop();
    }

    public int TextTranslate(String str, int i, int i2, int i3) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.TextTranslate(str, i, i2, i3);
    }

    public int UploadRecordedFile(String str, int i, boolean z) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = JNIHelper;
        return GCloudVoiceEngineHelper.UploadRecordedFile(str, i, z);
    }
}
