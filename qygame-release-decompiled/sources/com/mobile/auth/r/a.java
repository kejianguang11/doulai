package com.mobile.auth.r;

import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.a.c;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.manager.RequestCallback;
import com.mobile.auth.gatewayauth.manager.a;
import com.mobile.auth.gatewayauth.manager.base.b;
import com.mobile.auth.gatewayauth.manager.d;
import com.mobile.auth.gatewayauth.model.MonitorStruct;
import com.mobile.auth.gatewayauth.model.ctcctoken.CTCCTokenRet;
import com.mobile.auth.gatewayauth.model.ctcctoken.Data;
import com.nirvana.tools.core.ExecutorManager;

/* JADX INFO: loaded from: classes.dex */
public class a extends com.mobile.auth.gatewayauth.manager.a {
    private c i;

    public a(Context context, d dVar) {
        super(context, dVar, Constant.VENDOR_CTCC, null);
        this.i = new c() { // from class: com.mobile.auth.r.a.1
            @Override // com.mobile.auth.a.c
            public void a(String str, String str2) {
                try {
                    a.a(a.this).a(str, str2);
                } catch (Throwable th) {
                    try {
                        ExceptionProcessor.processException(th);
                    } catch (Throwable th2) {
                        ExceptionProcessor.processException(th2);
                    }
                }
            }

            @Override // com.mobile.auth.a.c
            public void a(String str, String str2, Throwable th) {
                try {
                    if (th != null) {
                        a.b(a.this).c(str, str2, ExecutorManager.getErrorInfoFromException(th));
                    } else {
                        a.c(a.this).c(str, str2);
                    }
                } catch (Throwable th2) {
                    try {
                        ExceptionProcessor.processException(th2);
                    } catch (Throwable th3) {
                        ExceptionProcessor.processException(th3);
                    }
                }
            }
        };
    }

    static /* synthetic */ com.mobile.auth.o.a a(a aVar) {
        try {
            return aVar.h;
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

    private synchronized void a(final RequestCallback<a.C0044a, b> requestCallback, final MonitorStruct monitorStruct, final String str) {
        try {
            com.mobile.auth.a.a.a((int) this.c, (int) this.c, (int) this.c, this.i);
            com.mobile.auth.a.a.a(this.d, this.a, this.b, new com.mobile.auth.a.b() { // from class: com.mobile.auth.r.a.2
                @Override // com.mobile.auth.a.b
                public void a(String str2) {
                    try {
                        monitorStruct.setCarrierReturnTime(System.currentTimeMillis());
                        if (TextUtils.isEmpty(str2)) {
                            a.a(a.this, requestCallback, Constant.CODE_ERROR_UNKNOWN_FAIL, "CTCC 获得的手机授权码结果为空", "", Constant.VENDOR_CTCC, monitorStruct, str);
                            return;
                        }
                        try {
                            CTCCTokenRet cTCCTokenRetFromJson = CTCCTokenRet.fromJson(str2);
                            if (cTCCTokenRetFromJson != null) {
                                monitorStruct.setCarrierTraceId(cTCCTokenRetFromJson.getReqId());
                                if (cTCCTokenRetFromJson.getResult() != 0 || cTCCTokenRetFromJson.getData() == null) {
                                    a.d(a.this, requestCallback, String.valueOf(cTCCTokenRetFromJson.getResult()), cTCCTokenRetFromJson.getMsg(), str2, Constant.VENDOR_CTCC, monitorStruct, str);
                                    return;
                                }
                                Data data = cTCCTokenRetFromJson.getData();
                                String number = data.getNumber();
                                String accessCode = data.getAccessCode();
                                if (TextUtils.isEmpty(number)) {
                                    a.c(a.this, requestCallback, String.valueOf(cTCCTokenRetFromJson.getResult()), cTCCTokenRetFromJson.getMsg(), str2, Constant.VENDOR_CTCC, monitorStruct, str);
                                    return;
                                }
                                requestCallback.onSuccess(a.C0044a.a().a(number).c(Constant.CTCC_PROTOCOL).d(Constant.CTCC_PROTOCOL_URL).b(accessCode).a(System.currentTimeMillis() + ((long) (data.getExpiredTime() * 1000))).a());
                                monitorStruct.setPhoneNumber(number);
                                monitorStruct.setAccessCode(accessCode);
                                a.a(a.this, String.valueOf(cTCCTokenRetFromJson.getResult()), "", "", true, Constant.VENDOR_CTCC, monitorStruct);
                            }
                        } catch (Exception e) {
                            a.d(a.this).e("CTCCValidManager init exception:", ExecutorManager.getErrorInfoFromException(e));
                            a.b(a.this, requestCallback, Constant.CODE_ERROR_UNKNOWN_FAIL, "JSON转换失败", str2, Constant.VENDOR_CTCC, monitorStruct, str);
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

    static /* synthetic */ void a(a aVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            aVar.a((RequestCallback<a.C0044a, b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void a(a aVar, String str, String str2, String str3, boolean z, String str4, MonitorStruct monitorStruct) {
        try {
            aVar.a(str, str2, str3, z, str4, monitorStruct);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ com.mobile.auth.o.a b(a aVar) {
        try {
            return aVar.h;
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

    static /* synthetic */ void b(a aVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            aVar.a((RequestCallback<a.C0044a, b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void b(a aVar, String str, String str2, String str3, boolean z, String str4, MonitorStruct monitorStruct) {
        try {
            aVar.a(str, str2, str3, z, str4, monitorStruct);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ com.mobile.auth.o.a c(a aVar) {
        try {
            return aVar.h;
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

    static /* synthetic */ void c(a aVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            aVar.a((RequestCallback<a.C0044a, b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ com.mobile.auth.o.a d(a aVar) {
        try {
            return aVar.h;
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

    static /* synthetic */ void d(a aVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            aVar.a((RequestCallback<a.C0044a, b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ com.mobile.auth.o.a e(a aVar) {
        try {
            return aVar.h;
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

    static /* synthetic */ void e(a aVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            aVar.a((RequestCallback<a.C0044a, b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void f(a aVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            aVar.a((RequestCallback<a.C0044a, b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void g(a aVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            aVar.a((RequestCallback<a.C0044a, b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void h(a aVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            aVar.a((RequestCallback<a.C0044a, b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
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
                case "-10002":
                    return "600025";
                case "80000":
                case "80005":
                case "80800":
                case "-8003":
                case "-720002":
                    return ResultCode.CODE_ERROR_FUNCTION_TIME_OUT;
                case "-8100":
                case "80003":
                case "80004":
                case "-8004":
                    return Constant.CODE_ERROR_NO_MOBILE_NETWORK_FAIL;
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
    }

    @Override // com.mobile.auth.gatewayauth.manager.a
    public synchronized void d(RequestCallback<a.C0044a, b> requestCallback, a.b bVar) {
        try {
            MonitorStruct monitorStruct = new MonitorStruct();
            monitorStruct.putApiParam("timeout", String.valueOf(this.c));
            monitorStruct.setSessionId(bVar.c());
            monitorStruct.setRequestId(bVar.b());
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setAction(Constant.ACTION_CTCC_LOGIN_CODE);
            monitorStruct.setNetworkType(com.mobile.auth.gatewayauth.utils.c.a(this.d, true));
            a(requestCallback, monitorStruct, ResultCode.CODE_GET_MASK_FAIL);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.manager.a
    public synchronized void e(RequestCallback<a.C0044a, b> requestCallback, a.b bVar) {
        try {
            MonitorStruct monitorStruct = new MonitorStruct();
            monitorStruct.putApiParam("timeout", String.valueOf(this.c));
            monitorStruct.setSessionId(bVar.c());
            monitorStruct.setRequestId(bVar.b());
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setAction(Constant.ACTION_CTCC_LOGIN_TOKEN);
            monitorStruct.setNetworkType(com.mobile.auth.gatewayauth.utils.c.a(this.d, true));
            a(requestCallback, monitorStruct, ResultCode.CODE_GET_TOKEN_FAIL);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.manager.a
    public synchronized void f(final RequestCallback<a.C0044a, b> requestCallback, a.b bVar) {
        try {
            final MonitorStruct monitorStruct = new MonitorStruct();
            monitorStruct.putApiParam("timeout", String.valueOf(this.c));
            monitorStruct.setSessionId(bVar.c());
            monitorStruct.setRequestId(bVar.b());
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setAction(Constant.ACTION_CTCC_AUTH_TOKEN);
            monitorStruct.setNetworkType(com.mobile.auth.gatewayauth.utils.c.a(this.d, true));
            com.mobile.auth.a.a.a((int) this.c, (int) this.c, (int) this.c, this.i);
            com.mobile.auth.a.a.a(this.d, this.a, this.b, new com.mobile.auth.a.b() { // from class: com.mobile.auth.r.a.3
                @Override // com.mobile.auth.a.b
                public void a(String str) {
                    try {
                        monitorStruct.setCarrierReturnTime(System.currentTimeMillis());
                        if (TextUtils.isEmpty(str)) {
                            a.e(a.this, requestCallback, Constant.CODE_ERROR_UNKNOWN_FAIL, "CTCC 获得认证Token结果为空", "", Constant.VENDOR_CTCC, monitorStruct, ResultCode.CODE_GET_TOKEN_FAIL);
                            return;
                        }
                        try {
                            CTCCTokenRet cTCCTokenRetFromJson = CTCCTokenRet.fromJson(str);
                            if (cTCCTokenRetFromJson != null) {
                                monitorStruct.setCarrierTraceId(cTCCTokenRetFromJson.getReqId());
                                if (cTCCTokenRetFromJson.getResult() != 0 || cTCCTokenRetFromJson.getData() == null) {
                                    a.h(a.this, requestCallback, String.valueOf(cTCCTokenRetFromJson.getResult()), cTCCTokenRetFromJson.getMsg(), str, Constant.VENDOR_CTCC, monitorStruct, ResultCode.CODE_GET_TOKEN_FAIL);
                                    return;
                                }
                                Data data = cTCCTokenRetFromJson.getData();
                                String accessCode = data.getAccessCode();
                                if (TextUtils.isEmpty(accessCode)) {
                                    a.g(a.this, requestCallback, String.valueOf(cTCCTokenRetFromJson.getResult()), cTCCTokenRetFromJson.getMsg(), str, Constant.VENDOR_CTCC, monitorStruct, ResultCode.CODE_GET_TOKEN_FAIL);
                                    return;
                                }
                                monitorStruct.setAccessCode(accessCode);
                                a.b(a.this, String.valueOf(cTCCTokenRetFromJson.getResult()), cTCCTokenRetFromJson.getMsg(), "", true, Constant.VENDOR_CTCC, monitorStruct);
                                requestCallback.onSuccess(a.C0044a.a().b(accessCode).a(System.currentTimeMillis() + ((long) (data.getExpiredTime() * 1000))).a());
                            }
                        } catch (Exception e) {
                            a.e(a.this).e("CTCCValidManager init exception:", ExecutorManager.getErrorInfoFromException(e));
                            a.f(a.this, requestCallback, Constant.CODE_ERROR_UNKNOWN_FAIL, "JSON转换失败", str, Constant.VENDOR_CTCC, monitorStruct, ResultCode.CODE_GET_TOKEN_FAIL);
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
}
