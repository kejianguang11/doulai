package com.mobile.auth.s;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.manager.RequestCallback;
import com.mobile.auth.gatewayauth.manager.a;
import com.mobile.auth.gatewayauth.manager.base.b;
import com.mobile.auth.gatewayauth.manager.d;
import com.mobile.auth.gatewayauth.model.MonitorStruct;
import com.mobile.auth.gatewayauth.utils.c;
import com.mobile.auth.gatewayauth.utils.i;
import com.unicom.online.account.shield.ResultListener;
import com.unicom.online.account.shield.UniAccountHelper;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class a extends com.mobile.auth.gatewayauth.manager.a {
    public a(Context context, d dVar) {
        super(context, dVar, Constant.VENDOR_CUCC, null);
    }

    private synchronized void a(final RequestCallback<a.C0044a, b> requestCallback, final MonitorStruct monitorStruct, final String str) {
        try {
            UniAccountHelper.getInstance().cuGetToken((int) this.c, new ResultListener() { // from class: com.mobile.auth.s.a.1
                @Override // com.unicom.online.account.shield.ResultListener
                public void onResult(String str2) {
                    try {
                        try {
                            if (!TextUtils.isEmpty("")) {
                                Log.i("cuzx login result:", str2);
                            }
                            JSONObject jSONObject = new JSONObject(str2);
                            int iOptInt = jSONObject.optInt("resultCode");
                            String strOptString = jSONObject.optString("resultMsg");
                            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("resultData");
                            if (iOptInt != 100) {
                                if ("1202".equals(Integer.valueOf(iOptInt))) {
                                    a.c(a.this, requestCallback, String.valueOf(iOptInt), ResultCode.MSG_ERROR_ANALYZE_SDK_BLACKLIST_INFO, str2, Constant.VENDOR_CUCC, monitorStruct, str);
                                    return;
                                } else {
                                    a.d(a.this, requestCallback, String.valueOf(iOptInt), strOptString, str2, Constant.VENDOR_CUCC, monitorStruct, str);
                                    return;
                                }
                            }
                            if (jSONObjectOptJSONObject != null) {
                                String strOptString2 = jSONObjectOptJSONObject.optString("fakeMobile");
                                String strOptString3 = jSONObjectOptJSONObject.optString("accessCode");
                                long jOptLong = jSONObjectOptJSONObject.optLong("exp");
                                if (TextUtils.isEmpty(strOptString2) || TextUtils.isEmpty(strOptString3)) {
                                    if ("1202".equals(Integer.valueOf(iOptInt))) {
                                        a.a(a.this, requestCallback, String.valueOf(iOptInt), ResultCode.MSG_ERROR_ANALYZE_SDK_BLACKLIST_INFO, str2, Constant.VENDOR_CUCC, monitorStruct, str);
                                        return;
                                    } else {
                                        a.b(a.this, requestCallback, String.valueOf(iOptInt), strOptString, str2, Constant.VENDOR_CUCC, monitorStruct, str);
                                        return;
                                    }
                                }
                                requestCallback.onSuccess(a.C0044a.a().a(strOptString2).c(Constant.CUCC_WOPROTOCOL).d(Constant.CUCC_WOPROTOCOL_URL).b(strOptString3).a(jOptLong).a());
                                monitorStruct.setAccessCode(strOptString3);
                                monitorStruct.setPhoneNumber(strOptString2);
                                a.a(a.this, String.valueOf(iOptInt), "", "", true, Constant.VENDOR_CUCC, monitorStruct);
                            }
                        } catch (Throwable th) {
                            try {
                                ExceptionProcessor.processException(th);
                            } catch (Throwable th2) {
                                ExceptionProcessor.processException(th2);
                            }
                        }
                    } catch (Exception e) {
                        a.e(a.this, requestCallback, Constant.CODE_ERROR_UNKNOWN_FAIL, "JSON转换失败", e.toString(), Constant.VENDOR_CUCC, monitorStruct, str);
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

    @Override // com.mobile.auth.gatewayauth.manager.a
    public void a(String str, String str2) {
        try {
            super.a(str, str2);
            UniAccountHelper.getInstance().setLogEnable(i.b());
            UniAccountHelper.getInstance().init(this.d, str);
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
            switch (str) {
                case "410000":
                    return ResultCode.CODE_ERROR_FUNCTION_TIME_OUT;
                case "410001":
                case "410002":
                    return "600025";
                case "410003":
                case "410004":
                case "410005":
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
            monitorStruct.setAction(Constant.ACTION_CUCC_LOGIN_CODE);
            monitorStruct.setUrgency(1);
            monitorStruct.setNetworkType(c.a(this.d, true));
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
    public synchronized void e(final RequestCallback<a.C0044a, b> requestCallback, a.b bVar) {
        try {
            MonitorStruct monitorStruct = new MonitorStruct();
            monitorStruct.putApiParam("timeout", String.valueOf(this.c));
            monitorStruct.setSessionId(bVar.c());
            monitorStruct.setRequestId(bVar.b());
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setAction(Constant.ACTION_CUCC_LOGIN_TOKEN);
            monitorStruct.setNetworkType(c.a(this.d, true));
            a(new RequestCallback<a.C0044a, b>() { // from class: com.mobile.auth.s.a.2
                public void a(a.C0044a c0044a) {
                    try {
                        requestCallback.onSuccess(c0044a);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                public void a(b bVar2) {
                    try {
                        requestCallback.onError(bVar2);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onError(b bVar2) {
                    try {
                        a(bVar2);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onSuccess(a.C0044a c0044a) {
                    try {
                        a(c0044a);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            }, monitorStruct, ResultCode.CODE_GET_TOKEN_FAIL);
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
            MonitorStruct monitorStruct = new MonitorStruct();
            monitorStruct.putApiParam("timeout", String.valueOf(this.c));
            monitorStruct.setSessionId(bVar.c());
            monitorStruct.setRequestId(bVar.b());
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setAction(Constant.ACTION_CUCC_AUTH_TOKEN);
            monitorStruct.setNetworkType(c.a(this.d, true));
            a(new RequestCallback<a.C0044a, b>() { // from class: com.mobile.auth.s.a.3
                public void a(a.C0044a c0044a) {
                    try {
                        requestCallback.onSuccess(c0044a);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                public void a(b bVar2) {
                    try {
                        requestCallback.onError(bVar2);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onError(b bVar2) {
                    try {
                        a(bVar2);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onSuccess(a.C0044a c0044a) {
                    try {
                        a(c0044a);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            }, monitorStruct, ResultCode.CODE_GET_TOKEN_FAIL);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
