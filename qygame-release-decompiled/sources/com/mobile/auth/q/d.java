package com.mobile.auth.q;

import android.content.Context;
import android.text.TextUtils;
import com.igexin.assist.sdk.AssistPushConsts;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.manager.RequestCallback;
import com.mobile.auth.gatewayauth.manager.a;
import com.mobile.auth.gatewayauth.model.MonitorStruct;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class d extends com.mobile.auth.gatewayauth.manager.a {
    private a i;

    public d(Context context, com.mobile.auth.gatewayauth.manager.d dVar) {
        super(context, dVar, Constant.VENDOR_CMCC, null);
        this.i = a.a(this.d);
    }

    static /* synthetic */ a a(d dVar) {
        try {
            return dVar.i;
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

    static /* synthetic */ void a(d dVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            dVar.a((RequestCallback<a.C0044a, com.mobile.auth.gatewayauth.manager.base.b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void a(d dVar, String str, String str2, String str3, boolean z, String str4, MonitorStruct monitorStruct) {
        try {
            dVar.a(str, str2, str3, z, str4, monitorStruct);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ com.mobile.auth.o.a b(d dVar) {
        try {
            return dVar.h;
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

    static /* synthetic */ void b(d dVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            dVar.a((RequestCallback<a.C0044a, com.mobile.auth.gatewayauth.manager.base.b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void b(d dVar, String str, String str2, String str3, boolean z, String str4, MonitorStruct monitorStruct) {
        try {
            dVar.a(str, str2, str3, z, str4, monitorStruct);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void c(d dVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            dVar.a((RequestCallback<a.C0044a, com.mobile.auth.gatewayauth.manager.base.b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void c(d dVar, String str, String str2, String str3, boolean z, String str4, MonitorStruct monitorStruct) {
        try {
            dVar.a(str, str2, str3, z, str4, monitorStruct);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.manager.a
    public void a(long j) {
        try {
            super.a(j);
            this.i.a(this.c);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.manager.a
    public void a(String str, String str2) {
        try {
            super.a(str, str2);
            this.i.a(str);
            this.i.b(str2);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.manager.a
    public void a(boolean z) {
        try {
            com.mobile.auth.e.a.a(z);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.manager.a
    protected String b(String str, String str2) {
        try {
            if (TextUtils.isEmpty(str)) {
                return str;
            }
            try {
                Integer numValueOf = Integer.valueOf(Integer.parseInt(str));
                if (numValueOf.intValue() >= 10000) {
                    if (numValueOf.intValue() <= 40000) {
                        return str2;
                    }
                }
            } catch (Exception unused) {
            }
            switch (str) {
                case "102203":
                    return "600025";
                case "102101":
                case "102103":
                case "200027":
                case "200022":
                    return Constant.CODE_ERROR_NO_MOBILE_NETWORK_FAIL;
                case "102507":
                case "200023":
                case "200024":
                    return ResultCode.CODE_ERROR_FUNCTION_TIME_OUT;
                default:
                    return str2;
            }
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

    @Override // com.mobile.auth.gatewayauth.manager.a
    public void c() {
        try {
            this.i.c();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.manager.a
    public synchronized void d(final RequestCallback<a.C0044a, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, a.b bVar) {
        final MonitorStruct monitorStruct;
        try {
            monitorStruct = new MonitorStruct();
            monitorStruct.putApiParam("timeout", String.valueOf(this.c));
            monitorStruct.setSessionId(bVar.c());
            monitorStruct.setRequestId(bVar.b());
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setAction(Constant.ACTION_CMCC_LOGIN_CODE);
            monitorStruct.setNetworkType(com.mobile.auth.gatewayauth.utils.c.a(this.d, true));
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
        if (!TextUtils.isEmpty(this.a) && !TextUtils.isEmpty(this.b)) {
            this.i.a(new com.mobile.auth.e.b() { // from class: com.mobile.auth.q.d.1
                /* JADX WARN: Removed duplicated region for block: B:18:0x0055 A[Catch: Throwable -> 0x009a, TryCatch #0 {Throwable -> 0x009a, blocks: (B:2:0x0000, B:4:0x000b, B:5:0x0010, B:8:0x0022, B:16:0x003e, B:18:0x0055, B:19:0x0088, B:10:0x0028, B:13:0x0033), top: B:27:0x0000 }] */
                /* JADX WARN: Removed duplicated region for block: B:19:0x0088 A[Catch: Throwable -> 0x009a, TRY_LEAVE, TryCatch #0 {Throwable -> 0x009a, blocks: (B:2:0x0000, B:4:0x000b, B:5:0x0010, B:8:0x0022, B:16:0x003e, B:18:0x0055, B:19:0x0088, B:10:0x0028, B:13:0x0033), top: B:27:0x0000 }] */
                @Override // com.mobile.auth.e.b
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public void a(int i, JSONObject jSONObject) {
                    String str;
                    String strOptString;
                    try {
                        monitorStruct.setCarrierReturnTime(System.currentTimeMillis());
                        if (jSONObject == null) {
                            jSONObject = new JSONObject();
                        }
                        String strOptString2 = jSONObject.optString("resultCode");
                        String strOptString3 = "";
                        if (jSONObject.has("resultDesc")) {
                            str = "resultDesc";
                        } else {
                            if (!jSONObject.has("desc")) {
                                if (jSONObject.has("resultString")) {
                                    str = "resultString";
                                }
                                String str2 = strOptString3;
                                String strOptString4 = jSONObject.optString("traceId");
                                strOptString = jSONObject.optString("securityphone");
                                monitorStruct.setCarrierTraceId(strOptString4);
                                if (!TextUtils.isEmpty(strOptString)) {
                                    d.a(d.this, requestCallback, strOptString2, str2, jSONObject.toString(), Constant.VENDOR_CMCC, monitorStruct, ResultCode.CODE_GET_MASK_FAIL);
                                    return;
                                }
                                requestCallback.onSuccess(a.C0044a.a().a(strOptString).c(Constant.CMCC_PROTOCOL).d(Constant.CMCC_PROTOCOL_URL).a());
                                monitorStruct.setPhoneNumber(strOptString);
                                d.a(d.this, strOptString2, "", "", true, Constant.VENDOR_CMCC, monitorStruct);
                                return;
                            }
                            str = "desc";
                        }
                        strOptString3 = jSONObject.optString(str);
                        String str22 = strOptString3;
                        String strOptString42 = jSONObject.optString("traceId");
                        strOptString = jSONObject.optString("securityphone");
                        monitorStruct.setCarrierTraceId(strOptString42);
                        if (!TextUtils.isEmpty(strOptString)) {
                        }
                    } catch (Throwable th3) {
                        try {
                            ExceptionProcessor.processException(th3);
                        } catch (Throwable th4) {
                            ExceptionProcessor.processException(th4);
                        }
                    }
                }
            });
            return;
        }
        a(requestCallback, ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO, "", Constant.VENDOR_CMCC, monitorStruct, ResultCode.CODE_GET_MASK_FAIL);
    }

    @Override // com.mobile.auth.gatewayauth.manager.a
    public synchronized void e(final RequestCallback<a.C0044a, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, a.b bVar) {
        try {
            final MonitorStruct monitorStruct = new MonitorStruct();
            monitorStruct.putApiParam("timeout", String.valueOf(this.c));
            monitorStruct.setSessionId(bVar.c());
            monitorStruct.setRequestId(bVar.b());
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setAction(Constant.ACTION_CMCC_LOGIN_TOKEN);
            monitorStruct.setNetworkType(com.mobile.auth.gatewayauth.utils.c.a(this.d, true));
            this.i.c(new com.mobile.auth.e.b() { // from class: com.mobile.auth.q.d.2
                /* JADX WARN: Removed duplicated region for block: B:18:0x0055 A[Catch: Throwable -> 0x00af, TryCatch #0 {Throwable -> 0x00af, blocks: (B:2:0x0000, B:4:0x000b, B:5:0x0010, B:8:0x0022, B:16:0x003e, B:18:0x0055, B:19:0x009d, B:10:0x0028, B:13:0x0033), top: B:27:0x0000 }] */
                /* JADX WARN: Removed duplicated region for block: B:19:0x009d A[Catch: Throwable -> 0x00af, TRY_LEAVE, TryCatch #0 {Throwable -> 0x00af, blocks: (B:2:0x0000, B:4:0x000b, B:5:0x0010, B:8:0x0022, B:16:0x003e, B:18:0x0055, B:19:0x009d, B:10:0x0028, B:13:0x0033), top: B:27:0x0000 }] */
                @Override // com.mobile.auth.e.b
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public void a(int i, JSONObject jSONObject) {
                    String str;
                    String strOptString;
                    try {
                        monitorStruct.setCarrierReturnTime(System.currentTimeMillis());
                        if (jSONObject == null) {
                            jSONObject = new JSONObject();
                        }
                        String strOptString2 = jSONObject.optString("resultCode");
                        String strOptString3 = "";
                        if (jSONObject.has("resultDesc")) {
                            str = "resultDesc";
                        } else {
                            if (!jSONObject.has("desc")) {
                                if (jSONObject.has("resultString")) {
                                    str = "resultString";
                                }
                                String str2 = strOptString3;
                                String strOptString4 = jSONObject.optString("traceId");
                                strOptString = jSONObject.optString(AssistPushConsts.MSG_TYPE_TOKEN);
                                monitorStruct.setCarrierTraceId(strOptString4);
                                if (!TextUtils.isEmpty(strOptString)) {
                                    d.b(d.this, requestCallback, strOptString2, str2, jSONObject.toString(), Constant.VENDOR_CMCC, monitorStruct, ResultCode.CODE_GET_TOKEN_FAIL);
                                    return;
                                }
                                monitorStruct.setAccessCode(jSONObject.optString(AssistPushConsts.MSG_TYPE_TOKEN));
                                d.a(d.this).a();
                                d.b(d.this, strOptString2, "", "", true, Constant.VENDOR_CMCC, monitorStruct);
                                requestCallback.onSuccess(a.C0044a.a().b(jSONObject.optString(AssistPushConsts.MSG_TYPE_TOKEN)).a(System.currentTimeMillis() + com.igexin.push.config.c.l).a());
                                return;
                            }
                            str = "desc";
                        }
                        strOptString3 = jSONObject.optString(str);
                        String str22 = strOptString3;
                        String strOptString42 = jSONObject.optString("traceId");
                        strOptString = jSONObject.optString(AssistPushConsts.MSG_TYPE_TOKEN);
                        monitorStruct.setCarrierTraceId(strOptString42);
                        if (!TextUtils.isEmpty(strOptString)) {
                        }
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            });
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.manager.a
    public synchronized void f(final RequestCallback<a.C0044a, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, a.b bVar) {
        final MonitorStruct monitorStruct;
        try {
            monitorStruct = new MonitorStruct();
            monitorStruct.putApiParam("timeout", String.valueOf(this.c));
            monitorStruct.setSessionId(bVar.c());
            monitorStruct.setRequestId(bVar.b());
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setAction(Constant.ACTION_CMCC_AUTH_TOKEN);
            monitorStruct.setNetworkType(com.mobile.auth.gatewayauth.utils.c.a(this.d, true));
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
        if (!TextUtils.isEmpty(this.a) && !TextUtils.isEmpty(this.b)) {
            this.i.b(new com.mobile.auth.e.b() { // from class: com.mobile.auth.q.d.3
                /* JADX WARN: Removed duplicated region for block: B:18:0x0050 A[Catch: Throwable -> 0x00b7, TryCatch #1 {Throwable -> 0x00b7, blocks: (B:2:0x0000, B:4:0x000b, B:5:0x0010, B:8:0x0022, B:16:0x003e, B:18:0x0050, B:19:0x00a5, B:10:0x0028, B:13:0x0033), top: B:29:0x0000 }] */
                /* JADX WARN: Removed duplicated region for block: B:19:0x00a5 A[Catch: Throwable -> 0x00b7, TRY_LEAVE, TryCatch #1 {Throwable -> 0x00b7, blocks: (B:2:0x0000, B:4:0x000b, B:5:0x0010, B:8:0x0022, B:16:0x003e, B:18:0x0050, B:19:0x00a5, B:10:0x0028, B:13:0x0033), top: B:29:0x0000 }] */
                @Override // com.mobile.auth.e.b
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public void a(int i, JSONObject jSONObject) {
                    String str;
                    String strOptString;
                    try {
                        monitorStruct.setCarrierReturnTime(System.currentTimeMillis());
                        if (jSONObject == null) {
                            jSONObject = new JSONObject();
                        }
                        String strOptString2 = jSONObject.optString("resultCode");
                        String strOptString3 = "";
                        if (jSONObject.has("resultDesc")) {
                            str = "resultDesc";
                        } else {
                            if (!jSONObject.has("desc")) {
                                if (jSONObject.has("resultString")) {
                                    str = "resultString";
                                }
                                String str2 = strOptString3;
                                String strOptString4 = jSONObject.optString("traceId");
                                strOptString = jSONObject.optString(AssistPushConsts.MSG_TYPE_TOKEN);
                                if (!TextUtils.isEmpty(strOptString)) {
                                    d.c(d.this, requestCallback, strOptString2, str2, jSONObject.toString(), Constant.VENDOR_CMCC, monitorStruct, ResultCode.CODE_GET_TOKEN_FAIL);
                                    return;
                                }
                                d.b(d.this).a("cmcc：", "getAccessCode:", jSONObject.toString());
                                monitorStruct.setCarrierTraceId(strOptString4);
                                monitorStruct.setAccessCode(strOptString);
                                d.c(d.this, strOptString2, "", "", true, Constant.VENDOR_CMCC, monitorStruct);
                                requestCallback.onSuccess(a.C0044a.a().b(strOptString).a(System.currentTimeMillis() + com.igexin.push.config.c.l).a());
                                return;
                            }
                            str = "desc";
                        }
                        strOptString3 = jSONObject.optString(str);
                        String str22 = strOptString3;
                        String strOptString42 = jSONObject.optString("traceId");
                        strOptString = jSONObject.optString(AssistPushConsts.MSG_TYPE_TOKEN);
                        if (!TextUtils.isEmpty(strOptString)) {
                        }
                    } catch (Throwable th3) {
                        try {
                            ExceptionProcessor.processException(th3);
                        } catch (Throwable th4) {
                            ExceptionProcessor.processException(th4);
                        }
                    }
                }
            });
            return;
        }
        a(requestCallback, ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO, "", Constant.VENDOR_CMCC, monitorStruct, ResultCode.CODE_GET_TOKEN_FAIL);
    }
}
