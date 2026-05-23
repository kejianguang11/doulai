package org.cocos2dx.javascript;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.sdk.app.PayTask;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.qygame.qzdlyx.mobile.ModifyImageActivity;
import com.qygame.qzdlyx.mobile.QuickMobileModule;
import java.util.Map;
import org.cocos2dx.javascript.alipay.OrderInfoUtil2_0;
import org.cocos2dx.javascript.alipay.PayResult;
import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxJavascriptJavaBridge;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class JSBridgeHelper {
    private static final int ALIPAY_FLAG = 1;

    @SuppressLint({"HandlerLeak"})
    private static Handler AlipayHander = new Handler() { // from class: org.cocos2dx.javascript.JSBridgeHelper.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            PayResult payResult = new PayResult((Map) message.obj);
            payResult.getResult();
            if (TextUtils.equals(payResult.getResultStatus(), "9000")) {
                JSBridgeHelper.alipayCallBack(true, "");
            } else {
                JSBridgeHelper.alipayCallBack(false, "支付失败");
            }
        }
    };
    private static AppActivity app;

    public static void alipayCallBack(boolean z, String str) {
        String str2;
        StringBuilder sb;
        if (z) {
            str2 = "gg.jsbHelper.alipayCallback(true, '" + str;
            sb = new StringBuilder();
        } else {
            str2 = "gg.jsbHelper.alipayCallback(false, '" + str;
            sb = new StringBuilder();
        }
        sb.append(str2);
        sb.append("');");
        runJsCode(sb.toString());
    }

    public static void changeHeadImage(String str) {
        Intent intent = new Intent(Cocos2dxActivity.getContext(), (Class<?>) ModifyImageActivity.class);
        intent.putExtra(ModifyImageActivity.chooseHeadImage, "chooseHeadImage");
        intent.putExtra("imageName", str);
        Cocos2dxActivity.getContext().startActivity(intent);
    }

    public static void changeHeadImageCallback(boolean z, String str) {
        String str2;
        if (z) {
            str2 = ("gg.jsbHelper.changeHeadImageCallback(true, '" + str) + "');";
        } else {
            str2 = "gg.jsbHelper.changeHeadImageCallback(false, '')";
        }
        runJsCode(str2);
    }

    public static boolean checkLocationAuth() {
        return app.getPackageManager().checkPermission("android.permission.ACCESS_COARSE_LOCATION", Cocos2dxActivity.getContext().getPackageName()) == 0;
    }

    public static String getDistance(String str, String str2, String str3, String str4) {
        return AmapModule.getDistance(str, str2, str3, str4);
    }

    public static String getLocation() {
        return AmapModule.getLocationInfo();
    }

    public static void initLocation() {
        AmapModule.initLocation(app);
    }

    public static void initNotify() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 26) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", Cocos2dxActivity.getContext().getPackageName());
        } else if (Build.VERSION.SDK_INT >= 21) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", Cocos2dxActivity.getContext().getPackageName());
            intent.putExtra("app_uid", app.getApplicationInfo().uid);
        } else {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", Cocos2dxActivity.getContext().getPackageName(), null));
        }
        Cocos2dxActivity.getContext().startActivity(intent);
    }

    public static void initPrivacyCompliance(boolean z) {
        AmapModule.privacyCompliance(app, z);
    }

    public static boolean isSupportMobileLogin() {
        return QuickMobileModule.isSupport();
    }

    public static boolean loginMobile() {
        QuickMobileModule.mobileLogin();
        return true;
    }

    public static void mobileLoginCallback(boolean z, String str) {
        String str2;
        StringBuilder sb;
        if (z) {
            str2 = "gg.jsbHelper.mobileLoginCallback(true, '" + str;
            sb = new StringBuilder();
        } else {
            str2 = "gg.jsbHelper.mobileLoginCallback(false, '" + str;
            sb = new StringBuilder();
        }
        sb.append(str2);
        sb.append("');");
        runJsCode(sb.toString());
    }

    public static void onSelectPhotoCallback(boolean z, String str) {
        String str2;
        if (z) {
            str2 = ("gg.jsbHelper.onSelectPhotoCallback(true, '" + str) + "');";
        } else {
            str2 = "gg.jsbHelper.onSelectPhotoCallback(false, '')";
        }
        runJsCode(str2);
    }

    public static void openSettingView() {
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", Cocos2dxActivity.getContext().getPackageName(), null));
        Cocos2dxActivity.getContext().startActivity(intent);
    }

    public static void payAlipay(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        final String strBuildOrderParam = OrderInfoUtil2_0.buildOrderParam(OrderInfoUtil2_0.buildOrderParamMap(jSONObject.getString("app_id"), jSONObject.getString("notify_url"), jSONObject.getString(DBHelpTool.RecordEntry.COLUMN_NAME_TIMESTAMP), jSONObject.getString("biz_content"), jSONObject.getString("sign")));
        new Thread(new Runnable() { // from class: org.cocos2dx.javascript.JSBridgeHelper.3
            @Override // java.lang.Runnable
            public void run() {
                Map<String, String> mapPayV2 = new PayTask(JSBridgeHelper.app).payV2(strBuildOrderParam, true);
                Message message = new Message();
                message.what = 1;
                message.obj = mapPayV2;
                JSBridgeHelper.AlipayHander.sendMessage(message);
            }
        }).start();
    }

    public static void runJsCode(final String str) {
        app.runOnGLThread(new Runnable() { // from class: org.cocos2dx.javascript.JSBridgeHelper.2
            @Override // java.lang.Runnable
            public void run() {
                Log.i("JSBridgeHelper", "runJsCode: " + str);
                Cocos2dxJavascriptJavaBridge.evalString(str);
            }
        });
    }

    public static void selectPhoto(String str) {
        Intent intent = new Intent(Cocos2dxActivity.getContext(), (Class<?>) ModifyImageActivity.class);
        intent.putExtra(ModifyImageActivity.selectPhoto, "selectPhoto");
        intent.putExtra("imageName", str);
        Cocos2dxActivity.getContext().startActivity(intent);
    }

    public static void setContext(AppActivity appActivity) {
        app = appActivity;
    }

    public static boolean startLocation() {
        AmapModule.startLocation();
        return true;
    }
}
