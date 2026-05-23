package com.mobile.auth.p;

import android.content.Context;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.manager.VendorSdkInfoManager;
import com.mobile.auth.gatewayauth.model.psc_info_upload.AllRBInfo;
import com.mobile.auth.gatewayauth.model.psc_info_upload.PnsVendorQueryResponse;
import com.mobile.auth.gatewayauth.model.psc_info_upload.Result;
import com.mobile.auth.gatewayauth.network.RequestState;
import com.mobile.auth.gatewayauth.network.RequestUtil;
import com.mobile.auth.gatewayauth.utils.EncryptUtils;

/* JADX INFO: loaded from: classes.dex */
public class b extends a<com.mobile.auth.u.b> {
    private com.mobile.auth.o.a a;
    private VendorSdkInfoManager b;

    public b(Context context, VendorSdkInfoManager vendorSdkInfoManager, com.mobile.auth.gatewayauth.manager.b bVar, com.mobile.auth.o.a aVar) {
        super(context, bVar);
        this.a = aVar;
        this.b = vendorSdkInfoManager;
    }

    @Override // com.mobile.auth.p.a
    public /* synthetic */ com.mobile.auth.u.a a() {
        try {
            return d();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    @Override // com.mobile.auth.p.a
    public /* synthetic */ com.mobile.auth.u.a a(String str) {
        try {
            return b(str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public com.mobile.auth.u.b b(String str) {
        String vendorListByPop;
        vendorListByPop = "";
        try {
            try {
                vendorListByPop = RequestState.getInstance().checkTokenValied(1) ? RequestUtil.getVendorListByPop(RequestState.getInstance().getKeyRespone().getSk(), EncryptUtils.encryptToken(c(), null, null, null, this.b.c(), str, null, null, null, false, null)) : "";
                this.a.a("PopRequest-GetVendorList:" + vendorListByPop);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return null;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return null;
                }
            }
        } catch (Exception unused) {
        }
        return new com.mobile.auth.u.b(false, vendorListByPop, str);
    }

    public com.mobile.auth.u.b d() {
        try {
            AllRBInfo allRBInfo = new AllRBInfo();
            PnsVendorQueryResponse pnsVendorQueryResponse = new PnsVendorQueryResponse();
            Result result = new Result();
            result.setCode(ResultCode.CODE_ERROR_FUNCTION_LIMIT);
            result.setMessage("GetVendorList Limited");
            pnsVendorQueryResponse.setResult(result);
            allRBInfo.setResponse(pnsVendorQueryResponse);
            return new com.mobile.auth.u.b(false, allRBInfo.toJson().toString(), "");
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public com.mobile.auth.u.a e() {
        try {
            return new com.mobile.auth.u.b(true, "{}", "");
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    @Override // com.nirvana.tools.requestqueue.TimeoutCallable
    public /* synthetic */ com.mobile.auth.u.a onTimeout() {
        try {
            return e();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }
}
