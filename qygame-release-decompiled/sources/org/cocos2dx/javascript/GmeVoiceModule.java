package org.cocos2dx.javascript;

import android.content.Intent;
import android.util.Log;
import androidx.core.provider.FontsContractCompat;
import com.alipay.sdk.util.j;
import com.gme.TMG.ITMGContext;
import com.gme.TMG.ITMGType;
import org.cocos2dx.javascript.gme.EnginePollHelper;
import org.cocos2dx.javascript.gme.GMEAuthBufferHelper;
import org.cocos2dx.javascript.gme.TMGCallbackDispatcher;
import org.cocos2dx.javascript.gme.TMGDispatcherBase;

/* JADX INFO: loaded from: classes.dex */
public class GmeVoiceModule implements TMGDispatcherBase {
    private static GmeVoiceModule mInstance;
    private boolean mIsPlaying;
    private boolean mIsRecording;
    private ITMGContext mTmgContext;
    private AppActivity mAppActivity = null;
    private int mFileIndex = 1;
    private String mSavePath = "";
    private String mCacheRecordFile = "";
    private String mPlayingFile = "";
    private String appSecret = "";

    public static void CancelRecording() {
        GmeVoiceModule gmeVoiceModule = getInstance();
        gmeVoiceModule.mIsRecording = false;
        Log.i("GmeVoiceModule", "Cancel Record");
        ITMGContext.GetInstance(gmeVoiceModule.mAppActivity).GetPTT().CancelRecording();
    }

    public static void DownloadRecordedFile(String str, String str2) {
        ITMGContext.GetInstance(getInstance().mAppActivity).GetPTT().DownloadRecordedFile(str, str2);
    }

    public static void Init(final String str, final String str2, String str3, final String str4) {
        GmeVoiceModule gmeVoiceModule = getInstance();
        gmeVoiceModule.appSecret = str3;
        gmeVoiceModule.mAppActivity.runOnUiThread(new Runnable() { // from class: org.cocos2dx.javascript.GmeVoiceModule.1
            @Override // java.lang.Runnable
            public void run() {
                GmeVoiceModule.this.initSdk(str, str2, str4);
            }
        });
    }

    public static int PlayRecordedFile(String str) {
        GmeVoiceModule gmeVoiceModule = getInstance();
        if (gmeVoiceModule.mIsPlaying) {
            return -1;
        }
        if (gmeVoiceModule.mIsRecording) {
            return -2;
        }
        gmeVoiceModule.mIsPlaying = true;
        Log.i("GmeVoiceModule", "Preview record file:" + str);
        gmeVoiceModule.mPlayingFile = str;
        ITMGContext.GetInstance(gmeVoiceModule.mAppActivity).GetPTT().PlayRecordedFile(str);
        return 0;
    }

    public static boolean StartRecording() {
        GmeVoiceModule gmeVoiceModule = getInstance();
        if (gmeVoiceModule.mIsRecording) {
            Log.i("GmeVoiceModule", "is Recording, please start later");
            return false;
        }
        Log.i("GmeVoiceModule", "Start Recording");
        StringBuilder sb = new StringBuilder();
        sb.append(gmeVoiceModule.mSavePath);
        sb.append("test_");
        int i = gmeVoiceModule.mFileIndex;
        gmeVoiceModule.mFileIndex = i + 1;
        sb.append(i);
        sb.append(".ogg");
        String string = sb.toString();
        gmeVoiceModule.mCacheRecordFile = string;
        if (ITMGContext.GetInstance(gmeVoiceModule.mAppActivity).GetPTT().StartRecording(string) != 0) {
            return false;
        }
        gmeVoiceModule.doEventCallback(GmeModuleCode.StartRecordSuccess, string);
        gmeVoiceModule.mIsRecording = true;
        return true;
    }

    public static void StopPlayFile() {
        GmeVoiceModule gmeVoiceModule = getInstance();
        gmeVoiceModule.mIsPlaying = false;
        ITMGContext.GetInstance(gmeVoiceModule.mAppActivity).GetPTT().StopPlayFile();
    }

    public static void StopRecording() {
        GmeVoiceModule gmeVoiceModule = getInstance();
        gmeVoiceModule.mIsRecording = true;
        Log.i("GmeVoiceModule", "Stop Record");
        ITMGContext.GetInstance(gmeVoiceModule.mAppActivity).GetPTT().StopRecording();
    }

    private void doEventCallback(GmeModuleCode gmeModuleCode, String str) {
        Log.i("GmeVoiceModule", "doEventCallback code:" + gmeModuleCode + ", msg:" + str);
        DeviceModule.runJsCode((("gg.gvoice.onGvoiceCallback(" + gmeModuleCode.code + ",'") + str) + "');");
    }

    public static GmeVoiceModule getInstance() {
        if (mInstance == null) {
            synchronized (GmeVoiceModule.class) {
                if (mInstance == null) {
                    mInstance = new GmeVoiceModule();
                }
            }
        }
        return mInstance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initSdk(String str, String str2, String str3) {
        this.mTmgContext.Uninit();
        this.mSavePath = str3;
        int iInit = this.mTmgContext.Init(str, str2);
        if (iInit == 0) {
            Log.i("GmeVoiceModule", "Init success");
            GMEAuthBufferHelper gMEAuthBufferHelper = GMEAuthBufferHelper.getInstance();
            gMEAuthBufferHelper.setGMEParams(str, this.appSecret, str2);
            EnginePollHelper.createEnginePollHelper();
            byte[] bArrCreateAuthBuffer = gMEAuthBufferHelper.createAuthBuffer("0");
            if (bArrCreateAuthBuffer.length == 0) {
                doEventCallback(GmeModuleCode.ApplyKeyFailed, "authBuffer is empty");
            }
            Log.i("GmeVoiceModule", "Init ApplyPTTAuthbuffer ret: " + this.mTmgContext.GetPTT().ApplyPTTAuthbuffer(bArrCreateAuthBuffer));
            return;
        }
        if (iInit == 1003) {
            Log.i("GmeVoiceModule", "Init success");
            return;
        }
        Log.i("GmeVoiceModule", "Init error errorCode:" + iInit);
        doEventCallback(GmeModuleCode.ApplyKeyFailed, "errorCode:" + iInit);
    }

    @Override // org.cocos2dx.javascript.gme.TMGDispatcherBase
    public void OnEvent(ITMGType.ITMG_MAIN_EVENT_TYPE itmg_main_event_type, Intent intent) {
        GmeModuleCode gmeModuleCode;
        StringBuilder sb;
        String str;
        String stringExtra;
        GmeModuleCode gmeModuleCode2;
        String string;
        int intExtra = intent.getIntExtra(j.c, -2);
        if (itmg_main_event_type != ITMGType.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_PTT_RECORD_COMPLETE) {
            if (itmg_main_event_type == ITMGType.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_PTT_UPLOAD_COMPLETE) {
                if (intExtra != 0) {
                    doEventCallback(GmeModuleCode.UpLoadFailed, "nErrCode: " + intExtra);
                    Log.i("GmeVoiceModule", "Upload file fail errCode:" + intExtra);
                    return;
                }
                stringExtra = intent.getStringExtra(FontsContractCompat.Columns.FILE_ID);
                Log.i("GmeVoiceModule", stringExtra);
                Log.i("GmeVoiceModule", "Upload file success url:" + stringExtra);
                gmeModuleCode2 = GmeModuleCode.UpLoadSuccess;
            } else if (itmg_main_event_type == ITMGType.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_PTT_PLAY_COMPLETE) {
                this.mIsPlaying = false;
                if (intExtra == 0) {
                    Log.i("GmeVoiceModule", "Play record file success");
                    gmeModuleCode = GmeModuleCode.PlayRecordFinish;
                    string = this.mPlayingFile;
                    doEventCallback(gmeModuleCode, string);
                }
                Log.i("GmeVoiceModule", "Play record file fail errCode:" + intExtra);
                gmeModuleCode = GmeModuleCode.PlayRecordFailed;
                sb = new StringBuilder();
                sb.append(this.mPlayingFile);
            } else {
                if (itmg_main_event_type != ITMGType.ITMG_MAIN_EVENT_TYPE.ITMG_MAIN_EVENT_TYPE_PTT_DOWNLOAD_COMPLETE) {
                    return;
                }
                if (intExtra != 0) {
                    Log.i("GmeVoiceModule", "Download record file fail errCode:" + intExtra);
                    gmeModuleCode = GmeModuleCode.DownLoadFailed;
                    sb = new StringBuilder();
                    str = "code: ";
                    sb.append(str);
                    sb.append(intExtra);
                    string = sb.toString();
                    doEventCallback(gmeModuleCode, string);
                }
                stringExtra = intent.getStringExtra("file_path");
                gmeModuleCode2 = GmeModuleCode.DownLoadSuccess;
            }
            doEventCallback(gmeModuleCode2, stringExtra);
            return;
        }
        this.mIsRecording = false;
        if (intExtra == 0) {
            String stringExtra2 = intent.getStringExtra("file_path");
            Log.i("GmeVoiceModule", stringExtra2);
            Log.i("GmeVoiceModule", "Record Success local filepath:" + stringExtra2);
            ITMGContext.GetInstance(this.mAppActivity).GetPTT().UploadRecordedFile(this.mCacheRecordFile);
            return;
        }
        Log.i("GmeVoiceModule", "Record fail errorCode:" + intExtra);
        gmeModuleCode = GmeModuleCode.StartRecordFailed;
        sb = new StringBuilder();
        str = "nErrCode:";
        sb.append(str);
        sb.append(intExtra);
        string = sb.toString();
        doEventCallback(gmeModuleCode, string);
    }

    public void setContext(AppActivity appActivity) {
        this.mAppActivity = appActivity;
        this.mTmgContext = ITMGContext.GetInstance(appActivity);
        TMGCallbackDispatcher.getInstance();
        TMGCallbackDispatcher tMGCallbackDispatcher = TMGCallbackDispatcher.getInstance();
        this.mTmgContext.SetTMGDelegate(tMGCallbackDispatcher);
        this.mSavePath = appActivity.getFilesDir().getAbsolutePath();
        tMGCallbackDispatcher.registerSubDispatcher(this);
    }
}
