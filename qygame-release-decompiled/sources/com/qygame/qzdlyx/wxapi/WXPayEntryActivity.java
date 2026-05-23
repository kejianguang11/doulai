package com.qygame.qzdlyx.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import org.cocos2dx.javascript.WeChatModule;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    public static String ReqWXPay = "ReqWXPay";
    private static final String TAG = "WXPayEntryActivity";
    private IWXAPI api;

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        this.api = WXAPIFactory.createWXAPI(this, WeChatModule.appId);
        this.api.handleIntent(intent, this);
        if (intent.hasExtra(ReqWXPay)) {
            try {
                reqWXPay(intent.getStringExtra("PayContent"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        finish();
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        this.api.handleIntent(intent, this);
    }

    @Override // com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
    public void onReq(BaseReq baseReq) {
    }

    @Override // com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
    public void onResp(BaseResp baseResp) {
        String str;
        Log.d(TAG, "onPayFinish, errCode = " + baseResp.errCode);
        if (baseResp.getType() == 5) {
            int i = baseResp.errCode;
            if (i == -4) {
                str = "微信验证失败";
            } else if (i == -2) {
                str = "支付取消";
            } else {
                if (i == 0) {
                    WeChatModule.wxPayResultCallBack(true, "支付成功");
                    return;
                }
                str = "支付失败";
            }
            WeChatModule.wxPayResultCallBack(false, str);
        }
    }

    public void reqWXPay(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        PayReq payReq = new PayReq();
        payReq.appId = jSONObject.getString("appid");
        payReq.partnerId = jSONObject.getString("partnerid");
        payReq.prepayId = jSONObject.getString("prepayid");
        payReq.nonceStr = jSONObject.getString("noncestr");
        payReq.timeStamp = jSONObject.getString(DBHelpTool.RecordEntry.COLUMN_NAME_TIMESTAMP);
        payReq.packageValue = jSONObject.getString("package");
        payReq.sign = jSONObject.getString("sign");
        payReq.extData = "app data";
        this.api.sendReq(payReq);
    }
}
