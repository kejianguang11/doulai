package org.cocos2dx.javascript.gme;

import android.content.Intent;
import com.alipay.sdk.util.j;

/* JADX INFO: loaded from: classes.dex */
public class TMGCallbackHelper {
    private static Params2 params2 = new Params2();
    private static ParamsUerInfo paramsUerInfo = new ParamsUerInfo();
    private static ParamsAudioDeviceInfo paramsAudioDeviceInfo = new ParamsAudioDeviceInfo();

    static class Params2 {
        int a;
        String b;

        Params2() {
        }
    }

    static class ParamsAudioDeviceInfo {
        boolean a;
        int b;

        ParamsAudioDeviceInfo() {
        }
    }

    static class ParamsUerInfo {
        int a;
        String[] b;

        ParamsUerInfo() {
        }
    }

    static ParamsAudioDeviceInfo ParseAudioDeviceInfoIntent(Intent intent) {
        paramsAudioDeviceInfo.a = intent.getBooleanExtra("audio_state", false);
        paramsAudioDeviceInfo.b = intent.getIntExtra("audio_errcode", 0);
        return paramsAudioDeviceInfo;
    }

    public static Params2 ParseIntentParams2(Intent intent) {
        params2.a = intent.getIntExtra(j.c, -1);
        params2.b = intent.getStringExtra("error_info");
        return params2;
    }

    static ParamsUerInfo ParseUserInfoUpdateInfoIntent(Intent intent) {
        paramsUerInfo.a = intent.getIntExtra("event_id", 0);
        paramsUerInfo.b = intent.getStringArrayExtra("user_list");
        return paramsUerInfo;
    }
}
