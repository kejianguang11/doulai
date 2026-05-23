package com.gcloudsdk.apollo;

import android.app.Activity;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import com.alipay.sdk.sys.a;
import com.gcloudsdk.apollo.apollovoice.httpclient.AudioDeviceListener;
import com.gcloudsdk.apollo.apollovoice.httpclient.HttpsUtils;
import com.gcloudsdk.gcloud.voice.GCloudVoiceVersion;
import com.gcloudsdk.gcloud.voice.IGCloudVoiceNotify;
import com.igexin.push.core.b;
import com.igexin.push.g.q;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class ApolloVoiceDeviceMgr {
    private static final int MODE_RESET = -2;
    private static final int MODE_SET_AUTO = -1;
    private static final int MODE_SET_ONLY = -3;
    private static final int SCO_CHECK_INTERL = 2000;
    private static final int SCO_CHECK_TIME_MAX = 2;
    protected static boolean a = true;
    private static String appname = "";
    private static boolean bAudioFocus = false;
    private static boolean bEmulate = false;
    private static boolean bFocusPause = false;
    private static boolean bGvoiceDsp = false;
    private static boolean bNeedSetFocus = true;
    private static boolean bPermissionOK = false;
    private static String checkDsp = null;
    private static String dataPath = null;
    private static Activity mActivity = null;
    private static AudioDeviceListener mAudioDeviceListener = null;
    private static AudioFocusChangeListener mAudioFocusChangeListener = null;
    private static AudioManager mAudioManager = null;
    private static int mAudioStatusEvent = 0;
    private static BluetoothAdapter mBluetoothAdapter = null;
    private static boolean mBluetoothSCO = false;
    private static boolean mBluetoothSCOEnable = false;
    private static int mBluetoothState = -100;
    private static boolean mCheckDeviceFlag = false;
    private static Context mContext = null;
    private static boolean mCurrVoipState = false;
    private static IGCloudVoiceNotify mGCloudVoiceNotify = null;
    private static BroadcastReceiver mHeadSetReceiver = null;
    private static boolean mIsBluetoothConnected = false;
    private static boolean mIsHeadsetConnected = false;
    private static boolean mIsMicOpen = false;
    private static boolean mIsMultiDeviceConnected = false;
    private static int mMode = -1;
    private static int mSCOReConnecteTimes = 0;
    private static boolean mScoThreadRunning = false;
    private static boolean m_bFirstBluePermission = false;
    private static int maxVolCall = 0;
    private static int maxVolMusic = 0;
    private static int nGvoiceDspApiVersion = -1;
    private static int nGvoiceDspSet = 0;
    private static int nLangType = -1;
    private static int taragetSdkVersion = 23;

    private static class AudioFocusChangeListener implements AudioManager.OnAudioFocusChangeListener {
        private AudioFocusChangeListener() {
        }

        @Override // android.media.AudioManager.OnAudioFocusChangeListener
        public void onAudioFocusChange(int i) {
            ApolloVoiceLog.LogI("mAudioStatusEvent focusChange:" + i);
            boolean zIsBackground = ApolloVoiceDeviceMgr.isBackground();
            ApolloVoiceEngine.APITrace("AudioFocusChange", "focusChange:" + i + ",background:" + zIsBackground);
            StringBuilder sb = new StringBuilder();
            sb.append("is background  ");
            sb.append(zIsBackground);
            ApolloVoiceLog.LogI(sb.toString());
            if (i == -2 || i == -3) {
                if (!ApolloVoiceEngine.IsPause()) {
                    if (ApolloVoiceDeviceMgr.isBackground()) {
                        return;
                    }
                    boolean unused = ApolloVoiceDeviceMgr.bNeedSetFocus = false;
                    ApolloVoiceEngine.Pause();
                    boolean unused2 = ApolloVoiceDeviceMgr.bFocusPause = true;
                    boolean unused3 = ApolloVoiceDeviceMgr.bNeedSetFocus = true;
                    return;
                }
            } else {
                if (i != 1 || !ApolloVoiceDeviceMgr.bFocusPause) {
                    return;
                }
                if (!ApolloVoiceDeviceMgr.isBackground()) {
                    boolean unused4 = ApolloVoiceDeviceMgr.bNeedSetFocus = false;
                    ApolloVoiceEngine.Resume();
                    boolean unused5 = ApolloVoiceDeviceMgr.bNeedSetFocus = true;
                }
            }
            boolean unused6 = ApolloVoiceDeviceMgr.bFocusPause = false;
        }
    }

    static {
        try {
            System.loadLibrary("GCloudVoice");
        } catch (UnsatisfiedLinkError unused) {
            System.err.println("load library failed!!!");
        }
        mHeadSetReceiver = new BroadcastReceiver() { // from class: com.gcloudsdk.apollo.ApolloVoiceDeviceMgr.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String str = "";
                try {
                    if (intent.getAction().equals("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED")) {
                        int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
                        int intExtra2 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
                        if (intExtra == 2 || intExtra2 == 2) {
                            ApolloVoiceLog.LogI("bluetooth connect ,cur state is " + intExtra);
                            if (!ApolloVoiceDeviceMgr.mAudioManager.isBluetoothScoAvailableOffCall()) {
                                ApolloVoiceLog.LogI("bluetooth connect, isBluetoothScoAvailableOffCall is " + ApolloVoiceDeviceMgr.mAudioManager.isBluetoothScoAvailableOffCall());
                                return;
                            }
                            boolean unused2 = ApolloVoiceDeviceMgr.mIsBluetoothConnected = true;
                            str = "bluetooth headset connect";
                            ApolloVoiceEngine.SetBluetoothState(true);
                            int unused3 = ApolloVoiceDeviceMgr.mAudioStatusEvent = 21;
                        } else if (intExtra == 0 || intExtra2 == 2) {
                            ApolloVoiceLog.LogI("bluetooth disconnect,cur state is " + intExtra);
                            boolean unused4 = ApolloVoiceDeviceMgr.mIsBluetoothConnected = false;
                            str = "bluetooth headset disconnect";
                            ApolloVoiceEngine.SetBluetoothState(false);
                            if (ApolloVoiceDeviceMgr.mIsHeadsetConnected) {
                                int unused5 = ApolloVoiceDeviceMgr.mAudioStatusEvent = 11;
                            } else {
                                int unused6 = ApolloVoiceDeviceMgr.mAudioStatusEvent = 20;
                            }
                        } else if (intExtra == 1 || intExtra2 == 1) {
                            ApolloVoiceLog.LogI("bluetoothHeadset connecting...");
                            return;
                        }
                    } else if (intent.getAction().equals("android.intent.action.HEADSET_PLUG")) {
                        if (intent.hasExtra("state")) {
                            int intExtra3 = intent.getIntExtra("state", -1);
                            switch (intExtra3) {
                                case 0:
                                    ApolloVoiceLog.LogI("headset disconnect ,cur state is " + intExtra3);
                                    boolean unused7 = ApolloVoiceDeviceMgr.mIsHeadsetConnected = false;
                                    ApolloVoiceEngine.SetHeadSetState(false);
                                    if (!ApolloVoiceDeviceMgr.mIsBluetoothConnected) {
                                        int unused8 = ApolloVoiceDeviceMgr.mAudioStatusEvent = 10;
                                    } else {
                                        int unused9 = ApolloVoiceDeviceMgr.mAudioStatusEvent = 21;
                                    }
                                    break;
                                case 1:
                                    ApolloVoiceLog.LogI("headset connect ,cur state is " + intExtra3);
                                    boolean unused10 = ApolloVoiceDeviceMgr.mIsHeadsetConnected = true;
                                    boolean unused11 = ApolloVoiceDeviceMgr.mIsBluetoothConnected = false;
                                    int unused12 = ApolloVoiceDeviceMgr.mAudioStatusEvent = 11;
                                    str = "headset connect";
                                    ApolloVoiceEngine.SetHeadSetState(true);
                                    break;
                            }
                            ApolloVoiceLog.LogE("BroadcastReceiver ACTION_HEADSET_PLUG onReceive bSetValue=" + ApolloVoiceDeviceMgr.a);
                        }
                    } else if (intent.getAction().equals("android.media.ACTION_SCO_AUDIO_STATE_UPDATED")) {
                        int intExtra4 = intent.getIntExtra("android.media.extra.SCO_AUDIO_STATE", 0);
                        ApolloVoiceLog.LogI("ApolloVoiceDeviceManager ::SCO cur state is " + intExtra4);
                        int unused13 = ApolloVoiceDeviceMgr.mBluetoothState = intExtra4;
                        if (intExtra4 == 1) {
                            boolean unused14 = ApolloVoiceDeviceMgr.mBluetoothSCO = true;
                            ApolloVoiceDeviceMgr.mAudioManager.setBluetoothScoOn(true);
                        } else if (intExtra4 == 0) {
                            boolean unused15 = ApolloVoiceDeviceMgr.mBluetoothSCO = false;
                            ApolloVoiceDeviceMgr.mAudioManager.setBluetoothScoOn(false);
                            ApolloVoiceDeviceMgr.ApolloVoiceDeviceSetMode(0);
                        }
                    } else if (intent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                        int intExtra5 = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 0);
                        if (intExtra5 == 12) {
                            ApolloVoiceLog.LogI("ApolloVoiceDeviceManger::Open bluetooth.\n");
                            ApolloVoiceDeviceMgr.RequestBluePerssion(true);
                        } else if (intExtra5 == 10) {
                            ApolloVoiceLog.LogI("ApolloVoiceDeviceManger::Close Bluetooth.\n");
                            boolean unused16 = ApolloVoiceDeviceMgr.mIsBluetoothConnected = false;
                            ApolloVoiceEngine.SetBluetoothState(false);
                        }
                    }
                    if (ApolloVoiceDeviceMgr.mAudioStatusEvent != 0) {
                        ApolloVoiceDeviceMgr.mAudioDeviceListener.onStatus(ApolloVoiceDeviceMgr.mAudioStatusEvent, str);
                        int unused17 = ApolloVoiceDeviceMgr.mAudioStatusEvent = 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        mAudioDeviceListener = new AudioDeviceListener() { // from class: com.gcloudsdk.apollo.ApolloVoiceDeviceMgr.2
            @Override // com.gcloudsdk.apollo.apollovoice.httpclient.AudioDeviceListener
            public synchronized void onStatus(int i, String str) {
                ApolloVoiceLog.LogI("--mAudioStatusEvent---:" + i);
                ApolloVoiceEngine.OnEvent(i, str);
                if (ApolloVoiceDeviceMgr.mGCloudVoiceNotify != null) {
                    ApolloVoiceDeviceMgr.mGCloudVoiceNotify.OnEvent(i, str);
                }
            }
        };
    }

    public static int ApolloVoiceCheckMode() {
        ApolloVoiceLog.LogI("ApolloVoiceCheckMode mode:" + mMode);
        if (!checkAudioManagerIsInit()) {
            return -1;
        }
        try {
            int mode = mAudioManager.getMode();
            if (mode == mMode || mode != 2) {
                ApolloVoiceSetSpeakerOn(true);
                return 0;
            }
            mAudioManager.setMode(0);
            mAudioManager.setMode(mMode);
            ApolloVoiceSetSpeakerOn(true);
            if (mAudioManager.getMode() == mMode) {
                ApolloVoiceLog.LogI("Check mode succ.");
                return 1;
            }
            ApolloVoiceLog.LogI("cur mode:" + mAudioManager.getMode() + " ,want mode:" + mMode);
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void ApolloVoiceDeviceInit(Context context, Activity activity) {
        String str;
        ApolloVoiceLog.LogI("GCloudVoice Version in java code: " + GCloudVoiceVersion.Version());
        ApolloVoiceLog.LogI("ApolloVoiceDeviceInit");
        if (mContext != null) {
            return;
        }
        mContext = context;
        if (!CheckManifestPermission("android.permission.MODIFY_AUDIO_SETTINGS") || (!CheckManifestPermission("android.permission.BLUETOOTH") && !CheckManifestPermission("android.permission.BLUETOOTH_CONNECT"))) {
            ApolloVoiceLog.LogE("Check the permissions GVoice needed!");
            return;
        }
        mActivity = activity;
        NetInterfaceHelper.init(activity);
        if (checkAudioManagerIsInit()) {
            CheckSysLang();
            bEmulate = CheckDeviceInfo();
            GetTargetSdkVer();
            ApolloVoiceLog.LogI("GVOICE_DSP:getParameters begin");
            try {
                ApolloVoiceLog.LogI("GVOICE_DSP:try getParameters version 1");
                checkDsp = mAudioManager.getParameters("vivo_adsp_support_gvoice");
                ApolloVoiceLog.LogI(checkDsp);
                if (checkDsp.equals("vivo_adsp_support_gvoice=true")) {
                    ApolloVoiceLog.LogI("GVOICE_DSP:APP supports gvoice dsp api(v1). NS will run on adsp");
                    nGvoiceDspApiVersion = 1;
                    nGvoiceDspSet = 1;
                    bGvoiceDsp = true;
                } else {
                    if (checkDsp.equals("vivo_adsp_support_gvoice=false")) {
                        ApolloVoiceLog.LogI("GVOICE_DSP:APP does not support gvoice dsp(v1).No gvoice module runs on adsp");
                        nGvoiceDspApiVersion = 1;
                        nGvoiceDspSet = 0;
                    } else {
                        ApolloVoiceLog.LogI("GVOICE_DSP:try getParameters version 2");
                        checkDsp = mAudioManager.getParameters("adsp_support_gvoice");
                        ApolloVoiceLog.LogI(checkDsp);
                        if (checkDsp != null) {
                            if (checkDsp.equals("adsp_support_gvoice=0001")) {
                                nGvoiceDspSet = 1;
                                bGvoiceDsp = true;
                                nGvoiceDspApiVersion = 2;
                                str = "GVOICE_DSP:APP supports gvoice dsp(v2).nGvoiceDspSet=" + nGvoiceDspSet;
                            } else if (checkDsp.equals("adsp_support_gvoice=0010")) {
                                nGvoiceDspSet = 2;
                                bGvoiceDsp = true;
                                nGvoiceDspApiVersion = 2;
                                str = "GVOICE_DSP:APP supports gvoice dsp(v2).nGvoiceDspSet=" + nGvoiceDspSet;
                            } else if (checkDsp.equals("adsp_support_gvoice=0011")) {
                                nGvoiceDspSet = 3;
                                bGvoiceDsp = true;
                                nGvoiceDspApiVersion = 2;
                                str = "GVOICE_DSP:APP supports gvoice dsp(v2).nGvoiceDspSet=" + nGvoiceDspSet;
                            } else {
                                ApolloVoiceLog.LogI("GVOICE_DSP:reset nGvoiceDspSet to 0,reset bGvoiceDsp to false");
                                nGvoiceDspSet = 0;
                            }
                            ApolloVoiceLog.LogI(str);
                        } else {
                            ApolloVoiceLog.LogE("GVOICE_DSP:getParameters(adsp_support_gvoice)  returns null");
                        }
                    }
                    bGvoiceDsp = false;
                }
                SetGVoiceDsp(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ApolloVoiceLog.LogI("GVOICE_DSP:getParameters done");
            try {
                mAudioManager.setMode(0);
            } catch (IllegalArgumentException e2) {
                ApolloVoiceLog.LogE(e2.getMessage());
            }
            int audioDeviceConnectionState = getAudioDeviceConnectionState();
            if (audioDeviceConnectionState == 2 || audioDeviceConnectionState == 3) {
                ApolloVoiceEngine.SetBluetoothState(true);
                mIsBluetoothConnected = true;
            } else {
                mIsBluetoothConnected = false;
            }
            ApolloVoiceLog.LogI("apollovoicemanager:: getMode: " + mAudioManager.getMode());
            if (mAudioManager != null) {
                if (audioDeviceConnectionState == 0) {
                    try {
                        mAudioManager.setSpeakerphoneOn(true);
                        a = true;
                    } catch (Exception e3) {
                        ApolloVoiceLog.LogI("Init failed!!! The exception is " + e3.toString());
                    }
                }
                AudioManager audioManager = mAudioManager;
                AudioManager audioManager2 = mAudioManager;
                maxVolMusic = audioManager.getStreamMaxVolume(3);
                AudioManager audioManager3 = mAudioManager;
                AudioManager audioManager4 = mAudioManager;
                maxVolCall = audioManager3.getStreamMaxVolume(0);
                ApolloVoiceLog.LogI("GCloudVoice::max music " + maxVolMusic + "max call =  " + maxVolCall);
            }
            registerHeadsetPlugReceiver();
            ApolloVoiceConfig.SetContext(context);
            ApolloVoiceUDID.SetContext(context);
            ApolloVoiceNetStatus.SetContext(context);
            HttpsUtils.setContext(context);
            mAudioFocusChangeListener = new AudioFocusChangeListener();
            EnableAudioFocus(true);
        }
    }

    public static boolean ApolloVoiceDeviceSetMode(int i) {
        ApolloVoiceLog.LogI("ApolloVoiceDeviceSetMode mode:" + i);
        if (!checkAudioManagerIsInit()) {
            return false;
        }
        mMode = i;
        try {
            ApolloVoiceLog.LogI("ApolloVoiceDeviceSetMode curmode:" + mAudioManager.getMode());
            mAudioManager.setMode(i);
            ApolloVoiceSetSpeakerOn(true);
            if (mAudioManager.getMode() != i) {
                ApolloVoiceLog.LogI("cur mode:" + mAudioManager.getMode() + " ,want mode:" + i);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void ApolloVoiceDeviceUninit() {
        mActivity = null;
        if (mContext != null) {
            unregisterHeadsetPlugReceiver();
            mAudioManager.setMode(0);
            mAudioManager = null;
            mContext = null;
        }
    }

    public static int ApolloVoiceGetCurrMode() {
        if (mAudioManager != null) {
            return mAudioManager.getMode();
        }
        return -2;
    }

    public static void ApolloVoiceSetSpeakerOn(boolean z) {
        ApolloVoiceLog.LogI("apolloVoiceDevice::SetSpeakerOn is " + z);
        if (checkAudioManagerIsInit()) {
            try {
                if (mIsBluetoothConnected || mIsHeadsetConnected) {
                    mAudioManager.setSpeakerphoneOn(false);
                } else {
                    mAudioManager.setSpeakerphoneOn(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            a = z;
        }
    }

    public static void CheckBluePermission() {
        ApolloVoiceLog.LogI("ApolloVoiceDevice check blue permission.\n");
        try {
            if (mBluetoothAdapter == null) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            }
            if (mBluetoothAdapter == null || mBluetoothAdapter.getState() != 12) {
                return;
            }
            RequestBluePerssion(true);
        } catch (IllegalArgumentException e) {
            ApolloVoiceLog.LogE(e.getMessage());
        }
    }

    private static boolean CheckDeviceInfo() {
        try {
            byte[] bytes = ApolloVoiceUDID.Brand().getBytes();
            byte[] bytes2 = ApolloVoiceUDID.FingerID().getBytes();
            String strBytesToHexString = bytesToHexString(bytes);
            String strBytesToHexString2 = bytesToHexString(bytes2);
            if (!ApolloVoiceUDID.FingerID().startsWith("generic") && !ApolloVoiceUDID.Model().contains("google_sdk") && !ApolloVoiceUDID.Model().toLowerCase().contains("droid4x") && !ApolloVoiceUDID.Model().contains("Emulator") && !ApolloVoiceUDID.Model().contains("Android SDK built for x86") && !ApolloVoiceUDID.ManuID().contains("Genymotion") && !ApolloVoiceUDID.ProductID().equals("sdk") && !ApolloVoiceUDID.ProductID().equals("google_sdk") && !ApolloVoiceUDID.ProductID().equals("sdk_x86") && !ApolloVoiceUDID.ProductID().equals("vbox86p") && !strBytesToHexString.contains("74656e63656e74")) {
                if (!strBytesToHexString2.contains("74656e63656e74")) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean CheckManifestPermission(String str) {
        if (mContext == null) {
            return false;
        }
        try {
            for (String str2 : mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 4096).requestedPermissions) {
                if (str2.equals(str)) {
                    ApolloVoiceLog.LogI("Check permission ok.");
                    bPermissionOK = true;
                    return true;
                }
                ApolloVoiceLog.LogE(str2);
            }
            return false;
        } catch (Exception unused) {
            ApolloVoiceLog.LogE("getPackageName throw an exception!");
            return true;
        }
    }

    public static boolean CheckPermiss(String str) {
        try {
            return PermissionChecker.checkSelfPermission(mContext, str) == 0;
        } catch (Exception unused) {
            ApolloVoiceLog.LogW("CheckPermiss get an exception !");
            return false;
        }
    }

    public static int CheckSysLang() {
        try {
            Locale locale = Locale.getDefault();
            if (locale.getLanguage().equals(new Locale("zh").getLanguage())) {
                if (locale.getCountry().equals("CN")) {
                    nLangType = 0;
                } else {
                    nLangType = 15;
                }
            } else if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
                nLangType = 1;
            } else if (locale.getLanguage().equals(new Locale("ja").getLanguage())) {
                nLangType = 2;
            } else if (locale.getLanguage().equals(new Locale("ko").getLanguage())) {
                nLangType = 3;
            } else if (locale.getLanguage().equals(new Locale("de").getLanguage())) {
                nLangType = 4;
            } else if (locale.getLanguage().equals(new Locale("fr").getLanguage())) {
                nLangType = 5;
            } else if (locale.getLanguage().equals(new Locale("es").getLanguage())) {
                nLangType = 6;
            } else if (locale.getLanguage().equals(new Locale(q.f).getLanguage())) {
                nLangType = 7;
            } else if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
                nLangType = 8;
            } else if (locale.getLanguage().equals(new Locale("ru").getLanguage())) {
                nLangType = 9;
            } else if (locale.getLanguage().equals(new Locale("pt").getLanguage())) {
                nLangType = 10;
            } else if (locale.getLanguage().equals(new Locale("vi").getLanguage())) {
                nLangType = 11;
            } else if (locale.getLanguage().equals(new Locale(b.C).getLanguage())) {
                nLangType = 12;
            } else if (locale.getLanguage().equals(new Locale("ms").getLanguage())) {
                nLangType = 13;
            } else if (locale.getLanguage().equals(new Locale("th").getLanguage())) {
                nLangType = 14;
            } else if (locale.getLanguage().equals(new Locale("af").getLanguage())) {
                nLangType = 16;
            } else if (locale.getLanguage().equals(new Locale("sq").getLanguage())) {
                nLangType = 17;
            } else if (locale.getLanguage().equals(new Locale("am").getLanguage())) {
                nLangType = 18;
            } else if (locale.getLanguage().equals(new Locale("ar").getLanguage())) {
                nLangType = 19;
            } else if (locale.getLanguage().equals(new Locale("hy").getLanguage())) {
                nLangType = 20;
            } else if (locale.getLanguage().equals(new Locale("az").getLanguage())) {
                nLangType = 21;
            } else if (locale.getLanguage().equals(new Locale("eu").getLanguage())) {
                nLangType = 22;
            } else if (locale.getLanguage().equals(new Locale("bn").getLanguage())) {
                nLangType = 23;
            } else if (locale.getLanguage().equals(new Locale("bs").getLanguage())) {
                nLangType = 24;
            } else if (locale.getLanguage().equals(new Locale("bg").getLanguage())) {
                nLangType = 25;
            } else if (locale.getLanguage().equals(new Locale("my").getLanguage())) {
                nLangType = 26;
            } else if (locale.getLanguage().equals(new Locale(b.ab).getLanguage())) {
                nLangType = 27;
            } else if (locale.getLanguage().equals(new Locale("hr").getLanguage())) {
                nLangType = 28;
            } else if (locale.getLanguage().equals(new Locale("cs").getLanguage())) {
                nLangType = 29;
            } else if (locale.getLanguage().equals(new Locale("da").getLanguage())) {
                nLangType = 30;
            } else if (locale.getLanguage().equals(new Locale("nl").getLanguage())) {
                nLangType = 31;
            } else if (locale.getLanguage().equals(new Locale("et").getLanguage())) {
                nLangType = 32;
            } else if (locale.getLanguage().equals(new Locale("fil").getLanguage())) {
                nLangType = 33;
            } else if (locale.getLanguage().equals(new Locale("fi").getLanguage())) {
                nLangType = 34;
            } else if (locale.getLanguage().equals(new Locale("gl").getLanguage())) {
                nLangType = 35;
            } else if (locale.getLanguage().equals(new Locale("ka").getLanguage())) {
                nLangType = 36;
            } else if (locale.getLanguage().equals(new Locale("el").getLanguage())) {
                nLangType = 37;
            } else if (locale.getLanguage().equals(new Locale("gu").getLanguage())) {
                nLangType = 38;
            } else if (locale.getLanguage().equals(new Locale("iw").getLanguage())) {
                nLangType = 39;
            } else if (locale.getLanguage().equals(new Locale("hi").getLanguage())) {
                nLangType = 40;
            } else if (locale.getLanguage().equals(new Locale("hu").getLanguage())) {
                nLangType = 41;
            } else if (locale.getLanguage().equals(new Locale("is").getLanguage())) {
                nLangType = 42;
            } else if (locale.getLanguage().equals(new Locale("jv").getLanguage())) {
                nLangType = 43;
            } else if (locale.getLanguage().equals(new Locale("kn").getLanguage())) {
                nLangType = 44;
            } else if (locale.getLanguage().equals(new Locale("kk").getLanguage())) {
                nLangType = 45;
            } else if (locale.getLanguage().equals(new Locale("km").getLanguage())) {
                nLangType = 46;
            } else if (locale.getLanguage().equals(new Locale("lo").getLanguage())) {
                nLangType = 47;
            } else if (locale.getLanguage().equals(new Locale("lv").getLanguage())) {
                nLangType = 48;
            } else if (locale.getLanguage().equals(new Locale("lt").getLanguage())) {
                nLangType = 49;
            } else if (locale.getLanguage().equals(new Locale("mk").getLanguage())) {
                nLangType = 50;
            } else if (locale.getLanguage().equals(new Locale("ml").getLanguage())) {
                nLangType = 51;
            } else if (locale.getLanguage().equals(new Locale("mr").getLanguage())) {
                nLangType = 52;
            } else if (locale.getLanguage().equals(new Locale("mn").getLanguage())) {
                nLangType = 53;
            } else if (locale.getLanguage().equals(new Locale("ne").getLanguage())) {
                nLangType = 54;
            } else if (locale.getLanguage().equals(new Locale("no").getLanguage())) {
                nLangType = 55;
            } else if (locale.getLanguage().equals(new Locale("fa").getLanguage())) {
                nLangType = 56;
            } else if (locale.getLanguage().equals(new Locale("pl").getLanguage())) {
                nLangType = 57;
            } else if (locale.getLanguage().equals(new Locale(com.alipay.sdk.cons.b.k).getLanguage())) {
                nLangType = 58;
            } else if (locale.getLanguage().equals(new Locale("ro").getLanguage())) {
                nLangType = 59;
            } else if (locale.getLanguage().equals(new Locale("sr").getLanguage())) {
                nLangType = 60;
            } else if (locale.getLanguage().equals(new Locale("si").getLanguage())) {
                nLangType = 61;
            } else if (locale.getLanguage().equals(new Locale("sk").getLanguage())) {
                nLangType = 62;
            } else if (locale.getLanguage().equals(new Locale("sl").getLanguage())) {
                nLangType = 63;
            } else if (locale.getLanguage().equals(new Locale("su").getLanguage())) {
                nLangType = 64;
            } else if (locale.getLanguage().equals(new Locale("sw").getLanguage())) {
                nLangType = 65;
            } else if (locale.getLanguage().equals(new Locale(a.h).getLanguage())) {
                nLangType = 66;
            } else if (locale.getLanguage().equals(new Locale("ta").getLanguage())) {
                nLangType = 67;
            } else if (locale.getLanguage().equals(new Locale("te").getLanguage())) {
                nLangType = 68;
            } else if (locale.getLanguage().equals(new Locale("uk").getLanguage())) {
                nLangType = 69;
            } else if (locale.getLanguage().equals(new Locale("ur").getLanguage())) {
                nLangType = 70;
            } else if (locale.getLanguage().equals(new Locale("uz").getLanguage())) {
                nLangType = 71;
            } else if (locale.getLanguage().equals(new Locale("zu").getLanguage())) {
                nLangType = 72;
            }
        } catch (Exception unused) {
            ApolloVoiceLog.LogI("CheckSysLang fail.\n");
            nLangType = -1;
        }
        return nLangType;
    }

    public static int EnableAudioFocus(boolean z) {
        String str;
        if (z == bAudioFocus) {
            return 0;
        }
        ApolloVoiceLog.LogI("EnableAudioFocus: " + z);
        try {
            if (z) {
                if (mAudioManager.requestAudioFocus(mAudioFocusChangeListener, 3, 1) == 1) {
                    ApolloVoiceLog.LogI("own requestAudioFocus  successfully.");
                    bAudioFocus = true;
                } else {
                    bAudioFocus = false;
                    str = "own requestAudioFocus  failed.";
                    ApolloVoiceLog.LogE(str);
                }
            } else if (mAudioManager.abandonAudioFocus(mAudioFocusChangeListener) == 1) {
                ApolloVoiceLog.LogI("own abandonAudioFocus  successfully.");
                bAudioFocus = false;
            } else {
                bAudioFocus = true;
                str = "own abandonAudioFocus  failed.";
                ApolloVoiceLog.LogE(str);
            }
        } catch (Exception unused) {
            ApolloVoiceLog.LogE("requestAudioFocus exception.");
        }
        return 0;
    }

    public static int GetLangType() {
        return nLangType;
    }

    public static int GetSupportGVoiceDspSet() {
        ApolloVoiceLog.LogI("GVOICE_DSP:GetSupportGVoiceDspSet return nGvoiceDspSet=" + nGvoiceDspSet);
        return nGvoiceDspSet;
    }

    public static void GetTargetSdkVer() {
        try {
            taragetSdkVersion = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).applicationInfo.targetSdkVersion;
            ApolloVoiceLog.LogI("targetSdkVersion = " + taragetSdkVersion);
        } catch (PackageManager.NameNotFoundException unused) {
            ApolloVoiceLog.LogE("Can't find package : " + mContext.getPackageName());
        }
    }

    public static boolean HaveMicrophonePermission(boolean z) {
        if (mContext == null || !bPermissionOK) {
            ApolloVoiceLog.LogE(bPermissionOK ? "bPermissionOK" : "not bPermissionOK");
            return false;
        }
        if (taragetSdkVersion >= 23 || CheckPermiss("android.permission.RECORD_AUDIO")) {
            ApolloVoiceLog.LogI("buildVersion = " + Build.VERSION.SDK_INT);
            if (Build.VERSION.SDK_INT >= 23) {
                if (ContextCompat.checkSelfPermission(mContext, "android.permission.RECORD_AUDIO") == 0) {
                    ApolloVoiceLog.LogI("Have microphone permission");
                    return true;
                }
                ApolloVoiceLog.LogE("Don't have microphone permission");
                if (z) {
                    ActivityCompat.requestPermissions(mActivity, new String[]{"android.permission.RECORD_AUDIO"}, 100);
                }
                return false;
            }
            if (CheckPermiss("android.permission.RECORD_AUDIO")) {
                return true;
            }
        }
        ApolloVoiceLog.LogE("Don't have microphone permission");
        return false;
    }

    public static boolean IsDeviceEmu() {
        ApolloVoiceLog.LogI("Device is Emu " + bEmulate);
        return bEmulate;
    }

    private static boolean IsHeadSet() {
        if (mAudioManager != null) {
            return mAudioManager.isWiredHeadsetOn();
        }
        return false;
    }

    public static boolean IsSupportGVoiceDsp() {
        return bGvoiceDsp;
    }

    public static boolean RequestBluePerssion(boolean z) {
        if (Build.VERSION.SDK_INT >= 31) {
            ApolloVoiceLog.LogI("ApolloVoiceDevice request blue permission.\n");
            try {
                if (CheckManifestPermission("android.permission.BLUETOOTH_CONNECT")) {
                    if (ContextCompat.checkSelfPermission(mContext, "android.permission.BLUETOOTH_CONNECT") == 0) {
                        ApolloVoiceLog.LogI("Have blue permission");
                        return true;
                    }
                    ApolloVoiceLog.LogE("Don't have blue permission");
                    if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, "android.permission.BLUETOOTH_CONNECT")) {
                        if (z) {
                            ActivityCompat.requestPermissions(mActivity, new String[]{"android.permission.BLUETOOTH_CONNECT"}, 100);
                        }
                        ApolloVoiceLog.LogI("request blue permissions.\n");
                        return true;
                    }
                    if (m_bFirstBluePermission) {
                        ApolloVoiceLog.LogI("user forbidden blue permissions.\n");
                    } else if (z) {
                        ActivityCompat.requestPermissions(mActivity, new String[]{"android.permission.BLUETOOTH_CONNECT"}, 100);
                        if (!m_bFirstBluePermission) {
                            m_bFirstBluePermission = true;
                        }
                    }
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static void SetBluetoothSCOEnable(boolean z) {
        ApolloVoiceLog.LogI("bluetoothSCOEnable:" + z);
        if (checkAudioManagerIsInit()) {
            try {
                if (!z) {
                    if (mAudioManager.isBluetoothScoOn()) {
                        mAudioManager.stopBluetoothSco();
                    }
                } else if (mAudioManager.isBluetoothScoAvailableOffCall()) {
                    if (ApolloVoiceGetCurrMode() != 3) {
                        ApolloVoiceDeviceSetMode(3);
                    }
                    mAudioManager.startBluetoothSco();
                }
            } catch (Exception unused) {
                ApolloVoiceLog.LogI("SetBluetoothSCOEnable set fail.\n");
            }
        }
    }

    public static void SetGVoiceDsp(boolean z) {
        AudioManager audioManager;
        String str;
        if (mAudioManager == null) {
            return;
        }
        try {
            ApolloVoiceLog.LogI("GVOICE_DSP:SetGVoiceDsp(bGvoiceDsp=" + bGvoiceDsp + ",nGvoiceDspApiVersion=" + nGvoiceDspApiVersion + ")");
            if (bGvoiceDsp) {
                if (1 == nGvoiceDspApiVersion) {
                    if (z) {
                        ApolloVoiceLog.LogI("GVOICE_DSP:enable gvoice dsp.");
                        audioManager = mAudioManager;
                        str = "vivo_adsp_gvoice_mode=true";
                    } else {
                        ApolloVoiceLog.LogI("GVOICE_DSP:disable gvoice dsp.");
                        audioManager = mAudioManager;
                        str = "vivo_adsp_gvoice_mode=false";
                    }
                } else if (z) {
                    ApolloVoiceLog.LogI("GVOICE_DSP:enable gvoice dsp.");
                    audioManager = mAudioManager;
                    str = "adsp_gvoice_mode=true";
                } else {
                    ApolloVoiceLog.LogI("GVOICE_DSP:disable gvoice dsp.");
                    audioManager = mAudioManager;
                    str = "adsp_gvoice_mode=false";
                }
                audioManager.setParameters(str);
            }
        } catch (Exception unused) {
            ApolloVoiceLog.LogI("GVOICE_DSP:SetGVoiceDsp set fail.\n");
        }
    }

    public static void SetpreVoipMode(int i) {
        mCurrVoipState = i == 1;
    }

    private static String bytesToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder("");
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString);
        }
        return sb.toString().toLowerCase();
    }

    public static boolean checkAudioManagerIsInit() {
        String str;
        if (mAudioManager != null) {
            return true;
        }
        if (mContext != null) {
            mAudioManager = (AudioManager) mContext.getSystemService("audio");
            if (mAudioManager != null) {
                return true;
            }
            str = "apolloVoiceDevice::get AudioManager null....\n";
        } else {
            str = "apolloVoiceDevice::context is null....\n";
        }
        ApolloVoiceLog.LogI(str);
        return false;
    }

    public static int getAudioDeviceConnectionState() {
        int i;
        int i2;
        boolean z;
        if (!checkAudioManagerIsInit()) {
            return 0;
        }
        if (mAudioManager.isWiredHeadsetOn()) {
            mIsHeadsetConnected = true;
            i = 1;
        } else {
            mIsHeadsetConnected = false;
            i = 0;
        }
        int i3 = i;
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
            int profileConnectionState = mBluetoothAdapter.getProfileConnectionState(2);
            int profileConnectionState2 = mBluetoothAdapter.getProfileConnectionState(1);
            int profileConnectionState3 = mBluetoothAdapter.getProfileConnectionState(3);
            if (profileConnectionState2 == 2) {
                z = true;
                i2 = 2;
            } else {
                i2 = i3;
                z = false;
            }
            if (i == 0 || !z) {
                mIsMultiDeviceConnected = false;
                i3 = i2;
            } else {
                mIsMultiDeviceConnected = true;
                if (mIsHeadsetConnected && !mIsBluetoothConnected) {
                    ApolloVoiceLog.LogW("getHeadsetDeviceStatus: wiredheadset actually!");
                    i3 = 1;
                } else if (mIsHeadsetConnected || !mIsBluetoothConnected) {
                    i3 = 3;
                } else {
                    ApolloVoiceLog.LogW("getHeadsetDeviceStatus: bluetooth actually!");
                    i3 = 2;
                }
            }
            ApolloVoiceLog.LogI("getHeadsetDeviceStatus state:" + i3 + " a2dp:" + profileConnectionState + " headset:" + profileConnectionState2 + " health:" + profileConnectionState3 + " mIsHeadsetConnected:" + mIsHeadsetConnected + " mIsBluetoothConnected:" + mIsBluetoothConnected);
        }
        return i3;
    }

    public static boolean hasRecordPermission() {
        byte[] bArr = new byte[512];
        AudioRecord audioRecord = null;
        try {
            try {
                AudioRecord audioRecord2 = new AudioRecord(0, 44100, 16, 2, AudioRecord.getMinBufferSize(44100, 16, 2));
                try {
                    audioRecord2.startRecording();
                    if (audioRecord2.getRecordingState() != 3) {
                        ApolloVoiceLog.LogI("startRecording but not in recording state");
                        audioRecord2.stop();
                        audioRecord2.release();
                        return false;
                    }
                    int i = audioRecord2.read(bArr, 0, 512);
                    ApolloVoiceLog.LogI("hasRecordPermission is recording readsize " + i);
                    if (i < 0) {
                        audioRecord2.stop();
                        audioRecord2.release();
                        return false;
                    }
                    audioRecord2.stop();
                    audioRecord2.release();
                    return true;
                } catch (IllegalStateException unused) {
                    audioRecord = audioRecord2;
                    ApolloVoiceLog.LogE("IllegalStateException when startRecording, which maybe do not have RECORD_AUDIO permission in actual");
                    if (audioRecord != null) {
                        audioRecord.release();
                    }
                    return false;
                }
            } catch (IllegalArgumentException unused2) {
                ApolloVoiceLog.LogW("IllegalArgumentException when create AudioRecord");
                return false;
            }
        } catch (IllegalStateException unused3) {
        }
    }

    public static boolean isBackground() {
        ApolloVoiceLog.LogI("is background call.\n");
        if (Build.VERSION.SDK_INT <= 21) {
            return true;
        }
        try {
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
            ActivityManager.getMyMemoryState(runningAppProcessInfo);
            if (runningAppProcessInfo.importance != 100 && runningAppProcessInfo.importance != 200) {
                return true;
            }
            ApolloVoiceLog.LogI("is not background .\n");
            return false;
        } catch (Exception unused) {
            ApolloVoiceLog.LogI("getMyMemoryState fail.\n");
            return true;
        }
    }

    private static boolean isEnableFocus() {
        ApolloVoiceLog.LogI("Need EnableAudioFocus: " + bNeedSetFocus);
        return bNeedSetFocus;
    }

    private static void registerHeadsetPlugReceiver() {
        if (mContext == null) {
            return;
        }
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.HEADSET_PLUG");
            intentFilter.addAction("android.media.ACTION_SCO_AUDIO_STATE_UPDATED");
            intentFilter.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED");
            mContext.registerReceiver(mHeadSetReceiver, intentFilter);
        } catch (Exception e) {
            ApolloVoiceLog.LogI("Registe headset failed!!! The exception is " + e.toString());
        }
    }

    public static void setGCloudVoiceNotify(IGCloudVoiceNotify iGCloudVoiceNotify) {
        mGCloudVoiceNotify = iGCloudVoiceNotify;
    }

    private static void unregisterHeadsetPlugReceiver() {
        if (mContext == null) {
            return;
        }
        try {
            mContext.unregisterReceiver(mHeadSetReceiver);
        } catch (Exception e) {
            ApolloVoiceLog.LogI("Registe headset failed!!! The exception is " + e.toString());
        }
    }
}
