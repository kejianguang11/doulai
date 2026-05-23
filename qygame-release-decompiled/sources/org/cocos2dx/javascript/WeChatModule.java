package org.cocos2dx.javascript;

import android.content.Intent;
import com.qygame.qzdlyx.wxapi.WXEntryActivity;
import com.qygame.qzdlyx.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxJavascriptJavaBridge;

/* JADX INFO: loaded from: classes.dex */
public class WeChatModule {
    private static AppActivity app = null;
    public static String appId = "";
    public static String appSecret = "";

    public static void initWx(String str, String str2) {
        appId = str;
        appSecret = str2;
    }

    public static boolean isInstallWx() {
        return WXAPIFactory.createWXAPI(app, appId, true).isWXAppInstalled();
    }

    public static void linkApplet(String str, int i) {
        Intent intent = new Intent(Cocos2dxActivity.getContext(), (Class<?>) WXEntryActivity.class);
        intent.putExtra(WXEntryActivity.linkApplet, "linkApplet");
        intent.putExtra("path", str);
        intent.putExtra("type", i);
        Cocos2dxActivity.getContext().startActivity(intent);
    }

    public static void loginWx() {
        Intent intent = new Intent(Cocos2dxActivity.getContext(), (Class<?>) WXEntryActivity.class);
        intent.putExtra(WXEntryActivity.ReqWxLogin, "wxlogin");
        Cocos2dxActivity.getContext().startActivity(intent);
    }

    public static void payWx(String str) {
        Intent intent = new Intent(Cocos2dxActivity.getContext(), (Class<?>) WXPayEntryActivity.class);
        intent.putExtra(WXPayEntryActivity.ReqWXPay, "ReqWXPay");
        intent.putExtra("PayContent", str);
        Cocos2dxActivity.getContext().startActivity(intent);
    }

    public static void runJsCode(final String str) {
        app.runOnGLThread(new Runnable() { // from class: org.cocos2dx.javascript.WeChatModule.1
            @Override // java.lang.Runnable
            public void run() {
                Cocos2dxJavascriptJavaBridge.evalString(str);
            }
        });
    }

    public static void setContext(AppActivity appActivity) {
        app = appActivity;
    }

    public static void shareImageWx(String str, int i) {
        Intent intent = new Intent(Cocos2dxActivity.getContext(), (Class<?>) WXEntryActivity.class);
        intent.putExtra(WXEntryActivity.ReqWxShareImg, "ReqWxShareImg");
        intent.putExtra("ImgPath", str);
        intent.putExtra("ShareType", i);
        Cocos2dxActivity.getContext().startActivity(intent);
    }

    public static void shareTextWx(String str, int i) {
        Intent intent = new Intent(Cocos2dxActivity.getContext(), (Class<?>) WXEntryActivity.class);
        intent.putExtra(WXEntryActivity.ReqWxShareTxt, "ReqWxShareTxt");
        intent.putExtra("ShareText", str);
        intent.putExtra("ShareType", i);
        Cocos2dxActivity.getContext().startActivity(intent);
    }

    public static void shareUrlWx(String str, String str2, String str3, int i) {
        Intent intent = new Intent(Cocos2dxActivity.getContext(), (Class<?>) WXEntryActivity.class);
        intent.putExtra(WXEntryActivity.ReqWxShareUrl, "ReqWxShareUrl");
        intent.putExtra("ShareUrl", str);
        intent.putExtra("ShareTitle", str2);
        intent.putExtra("ShareDesc", str3);
        intent.putExtra("ShareType", i);
        Cocos2dxActivity.getContext().startActivity(intent);
    }

    public static void wxLoginResultCallback(boolean z, String str) {
        String str2;
        StringBuilder sb;
        if (z) {
            str2 = "gg.wechat.onWxLoginResultCallback(true, '" + str;
            sb = new StringBuilder();
        } else {
            str2 = "gg.wechat.onWxLoginResultCallback(false, '" + str;
            sb = new StringBuilder();
        }
        sb.append(str2);
        sb.append("');");
        runJsCode(sb.toString());
    }

    public static void wxPayResultCallBack(boolean z, String str) {
        String str2;
        StringBuilder sb;
        if (z) {
            str2 = "gg.wechat.onWxPayResultCallback(true, '" + str;
            sb = new StringBuilder();
        } else {
            str2 = "gg.wechat.onWxPayResultCallback(false, '" + str;
            sb = new StringBuilder();
        }
        sb.append(str2);
        sb.append("');");
        runJsCode(sb.toString());
    }

    public static void wxShareResultCallback(boolean z, String str) {
        String str2;
        StringBuilder sb;
        if (z) {
            str2 = "gg.wechat.onWxShareResultCallback(true, '" + str;
            sb = new StringBuilder();
        } else {
            str2 = "gg.wechat.onWxShareResultCallback(true, '" + str;
            sb = new StringBuilder();
        }
        sb.append(str2);
        sb.append("');");
        runJsCode(sb.toString());
    }
}
