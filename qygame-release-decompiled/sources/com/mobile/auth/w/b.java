package com.mobile.auth.w;

import android.content.Context;
import com.alipay.sdk.util.e;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.model.pns_vendor_query.UploadRB;
import com.mobile.auth.gatewayauth.network.RequestState;
import com.mobile.auth.gatewayauth.network.RequestUtil;
import com.mobile.auth.gatewayauth.sdktools.upload.pns.model.PnsUploadRequest;
import com.mobile.auth.gatewayauth.sdktools.upload.pns.model.UploadMonitorDTO;
import com.mobile.auth.gatewayauth.utils.i;
import com.nirvana.tools.core.ExecutorManager;
import com.nirvana.tools.jsoner.JSONUtils;
import com.nirvana.tools.jsoner.JsonType;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class b implements com.mobile.auth.gatewayauth.b {
    @Override // com.mobile.auth.gatewayauth.b
    public boolean a(String str) {
        try {
            if (!i.b()) {
                ExecutorManager.getInstance().scheduleFutureDelay(new Runnable() { // from class: com.mobile.auth.w.b.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            com.mobile.auth.o.a.a((Context) null).d();
                        } catch (Throwable th) {
                            try {
                                ExceptionProcessor.processException(th);
                            } catch (Throwable th2) {
                                ExceptionProcessor.processException(th2);
                            }
                        }
                    }
                }, 1500L);
                return true;
            }
            if (!RequestState.getInstance().checkTokenValied(1)) {
                ExecutorManager.getInstance().scheduleFutureDelay(new Runnable() { // from class: com.mobile.auth.w.b.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            com.mobile.auth.o.a.a((Context) null).d();
                        } catch (Throwable th) {
                            try {
                                ExceptionProcessor.processException(th);
                            } catch (Throwable th2) {
                                ExceptionProcessor.processException(th2);
                            }
                        }
                    }
                }, 1500L);
                return true;
            }
            PnsUploadRequest pnsUploadRequest = new PnsUploadRequest();
            try {
                pnsUploadRequest.setUploadMonitorDTOList(JSONUtils.parseJsonArray2JsonerList(str, new JsonType<UploadMonitorDTO>() { // from class: com.mobile.auth.w.b.3
                }));
                JSONObject json = pnsUploadRequest.toJson();
                UploadRB uploadRBFromJson = UploadRB.fromJson(RequestUtil.uploadUserTrackInfoByPop(RequestState.getInstance().getKeyRespone().getSk(), json.toString()));
                com.mobile.auth.o.a aVarA = com.mobile.auth.o.a.a((Context) null);
                if (aVarA != null) {
                    aVarA.a(uploadRBFromJson);
                }
                ExecutorManager.getInstance().scheduleFutureDelay(new Runnable() { // from class: com.mobile.auth.w.b.4
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            com.mobile.auth.o.a.a((Context) null).d();
                        } catch (Throwable th) {
                            try {
                                ExceptionProcessor.processException(th);
                            } catch (Throwable th2) {
                                ExceptionProcessor.processException(th2);
                            }
                        }
                    }
                }, 1500L);
                if (uploadRBFromJson == null || uploadRBFromJson.getAlibaba_aliqin_psc_info_upload_response() == null || uploadRBFromJson.getAlibaba_aliqin_psc_info_upload_response().getResult() == null || !"OK".equals(uploadRBFromJson.getAlibaba_aliqin_psc_info_upload_response().getResult().getCode())) {
                    json.put("uploadResult2_0", e.b);
                    return false;
                }
                json.put("uploadResult2_0", "success");
                return true;
            } catch (Exception e) {
                i.a(e);
                ExecutorManager.getInstance().scheduleFutureDelay(new Runnable() { // from class: com.mobile.auth.w.b.5
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            com.mobile.auth.o.a.a((Context) null).d();
                        } catch (Throwable th) {
                            try {
                                ExceptionProcessor.processException(th);
                            } catch (Throwable th2) {
                                ExceptionProcessor.processException(th2);
                            }
                        }
                    }
                }, 1500L);
                return false;
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }
}
