package com.qygame.qzdlyx.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import com.qygame.qzdlyx.R;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import java.io.File;
import org.cocos2dx.javascript.Constants;
import org.cocos2dx.javascript.Util;
import org.cocos2dx.javascript.WeChatModule;

/* JADX INFO: loaded from: classes.dex */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    public static String ReqWxLogin = "ReqWxLogin";
    public static String ReqWxShareImg = "ReqWxShareImg";
    public static String ReqWxShareTxt = "ReqWxShareTxt";
    public static String ReqWxShareUrl = "ReqWxShareUrl";
    private static final int SceneSession = 0;
    private static final int SceneTimeline = 1;
    private static final int THUMB_SIZE = 120;
    public static String Tag = "WXEntryActivity";
    public static IWXAPI api = null;
    public static boolean bLogonWX = false;
    public static String linkApplet = "linkApplet";
    private static WXEntryActivity sContext;

    private String buildTransaction(String str) {
        if (str == null) {
            return String.valueOf(System.currentTimeMillis());
        }
        return str + System.currentTimeMillis();
    }

    public void linkApplet(String str, int i) {
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = Constants.APPLET_ID;
        req.path = str + "?id=" + i;
        req.miniprogramType = 0;
        api.sendReq(req);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        sContext = this;
        Log.d(Tag, "onCreate");
        Intent intent = getIntent();
        api = WXAPIFactory.createWXAPI(this, WeChatModule.appId, true);
        api.registerApp(WeChatModule.appId);
        api.handleIntent(intent, this);
        if (intent.hasExtra(ReqWxLogin)) {
            reqLogin();
        } else if (intent.hasExtra(ReqWxShareImg)) {
            reqShareImg(intent.getStringExtra("ImgPath"), intent.getIntExtra("ShareType", 0));
        } else if (intent.hasExtra(ReqWxShareTxt)) {
            reqShareTxt(intent.getStringExtra("ShareText"), intent.getIntExtra("ShareType", 0));
        } else if (intent.hasExtra(ReqWxShareUrl)) {
            reqShareUrl(intent.getStringExtra("ShareUrl"), intent.getStringExtra("ShareTitle"), intent.getStringExtra("ShareDesc"), intent.getIntExtra("ShareType", 0));
        } else if (intent.hasExtra(linkApplet)) {
            linkApplet(intent.getStringExtra("path"), intent.getIntExtra("type", 0));
        }
        finish();
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override // com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
    public void onReq(BaseReq baseReq) {
        String str;
        String str2;
        Log.d(Tag, "public void onReq(BaseReq req) !!!!!!!");
        switch (baseReq.getType()) {
            case 3:
                str = Tag;
                str2 = "onReq:COMMAND_GETMESSAGE_FROM_WX";
                break;
            case 4:
                str = Tag;
                str2 = "onReq:COMMAND_SHOWMESSAGE_FROM_WX";
                break;
            default:
                Log.d(Tag, "onReq:" + baseReq.getType());
        }
        Log.d(str, str2);
        Log.d(Tag, "onReq:" + baseReq.getType());
    }

    @Override // com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
    public void onResp(BaseResp baseResp) {
        String str;
        String str2;
        if (baseResp.getType() == 2) {
            int i = baseResp.errCode;
            if (i == -4) {
                str2 = "权限验证失败";
                WeChatModule.wxShareResultCallback(false, str2);
            } else if (i == -2) {
                WeChatModule.wxShareResultCallback(false, "取消分享");
                str2 = "权限验证失败";
                WeChatModule.wxShareResultCallback(false, str2);
            } else if (i != 0) {
                str2 = "分享失败";
                WeChatModule.wxShareResultCallback(false, str2);
            } else {
                WeChatModule.wxShareResultCallback(true, "分享成功");
            }
        }
        if (baseResp.getType() == 1) {
            int i2 = baseResp.errCode;
            if (i2 == -4) {
                str = "微信验证失败";
            } else if (i2 == -2) {
                str = "取消微信登录";
            } else {
                if (i2 == 0) {
                    SendAuth.Resp resp = (SendAuth.Resp) baseResp;
                    if (resp.state.toString().equals("qygame_qzdlyx")) {
                        WeChatModule.wxLoginResultCallback(true, resp.code);
                        return;
                    }
                    return;
                }
                str = "微信登录失败";
            }
            WeChatModule.wxLoginResultCallback(false, str);
        }
    }

    public void reqLogin() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "qygame_qzdlyx";
        api.sendReq(req);
        Log.d(Tag, "reqLogin!!!!");
    }

    public void reqShareImg(String str, int i) {
        String str2;
        StringBuilder sb;
        String str3;
        if (new File(str).exists()) {
            Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str);
            WXImageObject wXImageObject = new WXImageObject(bitmapDecodeFile);
            WXMediaMessage wXMediaMessage = new WXMediaMessage();
            wXMediaMessage.mediaObject = wXImageObject;
            Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapDecodeFile, 120, 120, true);
            bitmapDecodeFile.recycle();
            wXMediaMessage.thumbData = Util.bmpToByteArray(bitmapCreateScaledBitmap, true);
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("img");
            req.message = wXMediaMessage;
            if (i == 1) {
                req.scene = 1;
            } else if (i == 0) {
                req.scene = 0;
            }
            api.sendReq(req);
            str2 = Tag;
            sb = new StringBuilder();
            str3 = "reqShare Ok:";
        } else {
            str2 = Tag;
            sb = new StringBuilder();
            str3 = "reqShare file not exists:";
        }
        sb.append(str3);
        sb.append(str);
        Log.d(str2, sb.toString());
    }

    public void reqShareTxt(String str, int i) {
        WXTextObject wXTextObject = new WXTextObject();
        wXTextObject.text = str;
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.mediaObject = wXTextObject;
        wXMediaMessage.description = str;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = wXMediaMessage;
        int i2 = 1;
        if (i == 1) {
            req.scene = i2;
        } else if (i == 0) {
            i2 = 0;
            req.scene = i2;
        }
        api.sendReq(req);
        Log.d(Tag, "reqShareTxt Ok:" + str);
    }

    public void reqShareTxtCB(String str, int i) {
        WXAppExtendObject wXAppExtendObject = new WXAppExtendObject("lallalallallal", "filePath");
        if (!wXAppExtendObject.checkArgs()) {
            Log.d(Tag, "reqShareTxtCB Error:" + str);
        }
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.title = "11";
        wXMediaMessage.description = "22";
        wXMediaMessage.mediaObject = wXAppExtendObject;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("appdata");
        req.message = wXMediaMessage;
        int i2 = 1;
        if (i == 1) {
            req.scene = i2;
        } else if (i == 0) {
            i2 = 0;
            req.scene = i2;
        }
        api.sendReq(req);
        Log.d(Tag, "reqShareTxtCB Ok:" + str);
    }

    public void reqShareUrl(String str, String str2, String str3, int i) {
        WXWebpageObject wXWebpageObject = new WXWebpageObject();
        wXWebpageObject.webpageUrl = str;
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.mediaObject = wXWebpageObject;
        wXMediaMessage.title = str2;
        wXMediaMessage.description = str3;
        wXMediaMessage.thumbData = Util.bmpToByteArray(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(sContext.getResources(), R.mipmap.ic_launcher), 120, 120, true), true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = wXMediaMessage;
        if (i == 1) {
            req.scene = 1;
        } else if (i == 0) {
            req.scene = 0;
        }
        api.sendReq(req);
    }
}
