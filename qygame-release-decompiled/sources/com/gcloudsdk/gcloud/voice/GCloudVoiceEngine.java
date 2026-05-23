package com.gcloudsdk.gcloud.voice;

import android.app.Activity;
import android.content.Context;
import com.gcloudsdk.apollo.ApolloVoiceDeviceMgr;

/* JADX INFO: loaded from: classes.dex */
public class GCloudVoiceEngine extends GCloudVoiceEngineExtension {
    private static volatile GCloudVoiceEngine _instance;
    private Context appContext = null;
    private GCloudVoiceEngineHelper JNIHelper = new GCloudVoiceEngineHelper();

    public class TaskID {
        public int ID = -1;

        public TaskID() {
        }
    }

    static {
        try {
            System.loadLibrary("GCloudVoice");
        } catch (UnsatisfiedLinkError unused) {
            System.err.println("Load library GCloudVoice failed!!!");
            System.exit(1);
        }
    }

    private GCloudVoiceEngine() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        GCloudVoiceEngineHelper.EngineJniInstance();
    }

    public static GCloudVoiceEngine getInstance() {
        if (_instance == null) {
            synchronized (GCloudVoiceEngine.class) {
                if (_instance == null) {
                    _instance = new GCloudVoiceEngine();
                }
            }
        }
        return _instance;
    }

    public int ApplyMessageKey(int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.ApplyMessageKey(i);
    }

    public int ChangeRole(int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.ChangeRole(i, "");
    }

    public int ChangeRole(int i, String str) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.ChangeRole(i, str);
    }

    public int CloseMic() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.CloseMic();
    }

    public int CloseSpeaker() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.CloseSpeaker();
    }

    public int DownloadRecordedFile(String str, String str2, int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.DownloadRecordedFile(str, str2, i);
    }

    public int Init() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.Init();
    }

    public int JoinNationalRoom(String str, int i, int i2) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.JoinNationalRoom(str, i, i2);
    }

    public int JoinRangeRoom(String str, int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.JoinRangeRoom(str, i);
    }

    public int JoinTeamRoom(String str, int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.JoinTeamRoom(str, i);
    }

    public int OpenMic() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.OpenMic();
    }

    public int OpenSpeaker() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.OpenSpeaker();
    }

    public int Pause() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.Pause();
    }

    public int PlayRecordedFile(String str) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.PlayRecordedFile(str);
    }

    public int Poll() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.Poll();
    }

    public int QuitRoom(String str, int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.QuitRoom(str, i);
    }

    public int Resume() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.Resume();
    }

    public int SetAppInfo(String str, String str2, String str3) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.SetAppInfo(str, str2, str3);
    }

    public int SetMode(int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.SetMode(i);
    }

    public int SetNotify(IGCloudVoiceNotify iGCloudVoiceNotify) {
        ApolloVoiceDeviceMgr.setGCloudVoiceNotify(iGCloudVoiceNotify);
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.SetNotify(iGCloudVoiceNotify);
    }

    public int SpeechToText(String str, int i, int i2) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.SpeechToText(str, i, i2);
    }

    public int StartRecording(String str) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.StartRecording(str);
    }

    public int StopPlayFile() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.StopPlayFile();
    }

    public int StopRecording() {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.StopRecording();
    }

    public int UpdateCoordinate(String str, long j, long j2, long j3, long j4) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.UpdateCoordinate(str, j, j2, j3, j4);
    }

    public int UploadRecordedFile(String str, int i) {
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        return GCloudVoiceEngineHelper.UploadRecordedFile(str, i);
    }

    public int init(Context context, Activity activity) {
        ApolloVoiceDeviceMgr.ApolloVoiceDeviceInit(context, activity);
        if (context == null) {
            return 0;
        }
        GCloudVoiceEngineHelper gCloudVoiceEngineHelper = this.JNIHelper;
        GCloudVoiceEngineHelper.AndroidInit();
        return 0;
    }
}
