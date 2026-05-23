package com.qygame.qzdlyx.mobile;

import android.content.Context;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.asm.Opcodes;
import com.mobile.auth.gatewayauth.AuthUIConfig;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.PhoneNumberAuthHelper;
import com.mobile.auth.gatewayauth.PreLoginResultListener;
import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.TokenResultListener;
import com.mobile.auth.gatewayauth.model.TokenRet;
import org.cocos2dx.javascript.AppActivity;
import org.cocos2dx.javascript.Constants;
import org.cocos2dx.javascript.JSBridgeHelper;

/* JADX INFO: loaded from: classes.dex */
public class QuickMobileModule {
    private static final int SERVICE_TYPE_LOGIN = 2;
    private static AppActivity app = null;
    private static Context context = null;
    private static PhoneNumberAuthHelper mAlicomAuthHelper = null;
    private static int mScreenHeightDp = 0;
    private static TokenResultListener mTokenListener = null;
    private static boolean supportFlag = false;

    public static void checkEnable() {
        if (supportFlag) {
            return;
        }
        mAlicomAuthHelper.checkEnvAvailable(2);
    }

    private static void configLoginTokenLand() {
        mAlicomAuthHelper.setAuthUIConfig(new AuthUIConfig.Builder().setAppPrivacyColor(-7829368, -16776961).setPrivacyState(false).setSwitchAccHidden(true).setSloganHidden(true).setNavHidden(true).setStatusBarHidden(true).setCheckboxHidden(true).setLogoOffsetY(55).setLogoImgPath("phone").setLogoWidth(50).setLogoHeight(50).setNumFieldOffsetY(120).setLogBtnOffsetY(Opcodes.IF_ACMPNE).setLogBtnMarginLeftAndRight((mScreenHeightDp - 339) / 2).setPrivacyMargin(115).setLogBtnWidth(339).setAuthPageActIn("in_activity", "out_activity").setAuthPageActOut("in_activity", "out_activity").setVendorPrivacyPrefix("《").setVendorPrivacySuffix("》").setPageBackgroundPath("page_background_color").setScreenOrientation(3).create());
    }

    public static void init(Context context2, AppActivity appActivity) {
        context = context2;
        app = appActivity;
        mTokenListener = new TokenResultListener() { // from class: com.qygame.qzdlyx.mobile.QuickMobileModule.1
            @Override // com.mobile.auth.gatewayauth.TokenResultListener
            public void onTokenFailed(final String str) {
                QuickMobileModule.app.runOnUiThread(new Runnable() { // from class: com.qygame.qzdlyx.mobile.QuickMobileModule.1.2
                    @Override // java.lang.Runnable
                    public void run() {
                        TokenRet tokenRet = (TokenRet) JSON.parseObject(str, TokenRet.class);
                        Log.e("QuickMobileModule", tokenRet.getMsg());
                        QuickMobileModule.mAlicomAuthHelper.hideLoginLoading();
                        JSBridgeHelper.mobileLoginCallback(false, "获取token失败," + tokenRet.getMsg() + ",code:" + tokenRet.getCode());
                    }
                });
            }

            @Override // com.mobile.auth.gatewayauth.TokenResultListener
            public void onTokenSuccess(final String str) {
                QuickMobileModule.app.runOnGLThread(new Runnable() { // from class: com.qygame.qzdlyx.mobile.QuickMobileModule.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TokenRet tokenRet;
                        try {
                            tokenRet = (TokenRet) JSON.parseObject(str, TokenRet.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                            tokenRet = null;
                        }
                        if (tokenRet != null && ResultCode.CODE_ERROR_ENV_CHECK_SUCCESS.equals(tokenRet.getCode())) {
                            boolean unused = QuickMobileModule.supportFlag = true;
                        }
                        if (tokenRet != null && ResultCode.CODE_START_AUTHPAGE_SUCCESS.equals(tokenRet.getCode())) {
                            Log.e("QuickMobileModule", "唤起授权页成功:" + str);
                        }
                        if (tokenRet == null || !"600000".equals(tokenRet.getCode())) {
                            return;
                        }
                        String token = tokenRet.getToken();
                        QuickMobileModule.mAlicomAuthHelper.quitLoginPage();
                        Log.e("QuickMobileModule", "获取token成功:" + str);
                        JSBridgeHelper.mobileLoginCallback(true, token);
                    }
                });
            }
        };
        mAlicomAuthHelper = PhoneNumberAuthHelper.getInstance(context2, mTokenListener);
        mAlicomAuthHelper.setAuthListener(mTokenListener);
        mAlicomAuthHelper.setAuthSDKInfo(Constants.MobileSecret);
        mScreenHeightDp = AppUtils.px2dp(context2, AppUtils.getPhoneHeightPixels(appActivity));
        checkEnable();
    }

    public static boolean isSupport() {
        checkEnable();
        return supportFlag;
    }

    public static void mobileLogin() {
        configLoginTokenLand();
        mAlicomAuthHelper.getLoginToken(app, Constant.DEFAULT_TIMEOUT);
    }

    public void getMobileNum() {
        mAlicomAuthHelper.accelerateLoginPage(Constant.DEFAULT_TIMEOUT, new PreLoginResultListener() { // from class: com.qygame.qzdlyx.mobile.QuickMobileModule.2
            @Override // com.mobile.auth.gatewayauth.PreLoginResultListener
            public void onTokenFailed(final String str, final String str2) {
                QuickMobileModule.app.runOnUiThread(new Runnable() { // from class: com.qygame.qzdlyx.mobile.QuickMobileModule.2.2
                    @Override // java.lang.Runnable
                    public void run() {
                        Log.e("QuickMobileModule", str + "预取号失败:\n" + str2);
                    }
                });
            }

            @Override // com.mobile.auth.gatewayauth.PreLoginResultListener
            public void onTokenSuccess(final String str) {
                QuickMobileModule.app.runOnUiThread(new Runnable() { // from class: com.qygame.qzdlyx.mobile.QuickMobileModule.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Log.e("QuickMobileModule", str + "预取号成功！");
                    }
                });
            }
        });
    }
}
