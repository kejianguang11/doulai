package com.mobile.auth.c;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import com.mobile.auth.c.j;
import com.mobile.auth.c.r;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    private static final String a = "b";

    static /* synthetic */ String a() {
        try {
            return a;
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

    public static String a(Context context, String str, String str2, Network network) {
        try {
            return c(context, i.a(context, str, network), str2, network);
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

    private String a(Context context, String str, String str2, String str3, Network network, String str4, String str5) {
        try {
            new n();
            String strB = b();
            String strA = l.a(context, str, str2, str3, strB);
            com.mobile.auth.a.a.a(a, "request params : " + strA);
            n nVarA = i.a(context, k.j(context), strA, network, str4, str5, true);
            if (nVarA.d) {
                nVarA = i.a(context, nVarA.e.equals(com.igexin.push.config.c.H) ? "https://card.e.189.cn/auth/preauth.do" : "https://id6.me/auth/preauth.do", strA, network, str4, str5, false);
            }
            com.mobile.auth.a.a.a(a, "request result : " + nVarA.b);
            nVarA.b = b(context, nVarA.b, strB, network);
            if (TextUtils.isEmpty(nVarA.b)) {
                nVarA.b = "{\"result\":80001,\"msg\":\"请求异常\"}";
                return nVarA.b;
            }
            com.mobile.auth.b.e.a(str5, nVarA.b, strA);
            return nVarA.b;
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

    private static String a(Context context, List<String> list, String str, Network network) {
        String strA;
        JSONObject jSONObject;
        for (int i = 0; i < list.size(); i++) {
            try {
                try {
                    String str2 = list.get(i);
                    if (!TextUtils.isEmpty(list.get(i)) && context != null && Build.VERSION.SDK_INT < 21) {
                        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                        if (connectivityManager.getNetworkInfo(5).getState().compareTo(NetworkInfo.State.CONNECTED) == 0) {
                            ((Boolean) Class.forName("android.net.ConnectivityManager").getMethod("requestRouteToHost", Integer.TYPE, Integer.TYPE).invoke(connectivityManager, 5, Integer.valueOf(j.a(j.b(str2))))).booleanValue();
                        }
                    }
                    strA = a(context, list.get(i), str, network);
                    try {
                        jSONObject = !TextUtils.isEmpty(strA) ? new JSONObject(strA) : null;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (jSONObject != null && jSONObject.getInt(com.alipay.sdk.util.j.c) == 0) {
                    return strA;
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
        return null;
    }

    static /* synthetic */ String a(b bVar, Context context, String str, String str2, String str3, Network network, String str4, String str5) {
        try {
            return bVar.a(context, str, str2, str3, network, str4, str5);
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

    public static String a(String str, String str2) {
        try {
            return f.b(str, str2);
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

    private void a(final Context context, final String str, final r.a aVar, final int i, final com.mobile.auth.a.b bVar) {
        try {
            final Future futureB = r.a().b(aVar);
            r.a().a(new Runnable() { // from class: com.mobile.auth.c.b.4
                @Override // java.lang.Runnable
                public void run() {
                    Context context2;
                    String str2;
                    String str3;
                    com.mobile.auth.a.b bVar2;
                    Future future;
                    try {
                        try {
                            try {
                                futureB.get(i, TimeUnit.MILLISECONDS);
                            } catch (Throwable th) {
                                aVar.a(true);
                                if (th instanceof TimeoutException) {
                                    com.mobile.auth.b.e.a(str, "{\"result\":80000,\"msg\":\"请求超时\"}", "");
                                    com.mobile.auth.b.e.a(str).h("submitOnTimeoutInterrupted()");
                                    context2 = context;
                                    str2 = "{\"result\":80000,\"msg\":\"请求超时\"}";
                                    str3 = str;
                                    bVar2 = bVar;
                                } else {
                                    com.mobile.auth.b.e.a(str, "{\"result\":80001,\"msg\":\"请求异常\"}", "");
                                    com.mobile.auth.b.e.a(str).h("submitOnTimeoutInterrupted other exception : " + th.getMessage());
                                    com.mobile.auth.a.a.a(b.a(), "submitOnTimeoutInterrupted other exception", th);
                                    context2 = context;
                                    str2 = "{\"result\":80001,\"msg\":\"请求异常\"}";
                                    str3 = str;
                                    bVar2 = bVar;
                                }
                                com.mobile.auth.a.a.b(context2, str2, str3, bVar2);
                                if (futureB == null || futureB.isDone()) {
                                    return;
                                } else {
                                    future = futureB;
                                }
                            }
                            if (futureB == null || futureB.isDone()) {
                                return;
                            }
                            future = futureB;
                            future.cancel(true);
                        } catch (Throwable th2) {
                            if (futureB != null && !futureB.isDone()) {
                                futureB.cancel(true);
                            }
                            throw th2;
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
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private String b() {
        String str = "";
        try {
            String string = UUID.randomUUID().toString();
            if (TextUtils.isEmpty(string)) {
                return "";
            }
            String strReplace = string.replace("-", "");
            try {
                return strReplace.length() >= 16 ? strReplace.substring(0, 16) : strReplace;
            } catch (Throwable th) {
                th = th;
                str = strReplace;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            com.mobile.auth.a.a.a(a, "generateAesKey error", th);
            return str;
        } catch (Throwable th3) {
            try {
                ExceptionProcessor.processException(th3);
                return null;
            } catch (Throwable th4) {
                ExceptionProcessor.processException(th4);
                return null;
            }
        }
    }

    private String b(Context context, String str, String str2, Network network) {
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                int iOptInt = jSONObject.optInt(com.alipay.sdk.util.j.c);
                String strOptString = jSONObject.optString(com.alipay.sdk.packet.d.k);
                if ((iOptInt == 0 || iOptInt == 30002) && !TextUtils.isEmpty(strOptString)) {
                    String strA = a(strOptString, str2);
                    if (!TextUtils.isEmpty(strA)) {
                        try {
                            jSONObject.put(com.alipay.sdk.packet.d.k, new JSONObject(strA));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            jSONObject.put(com.alipay.sdk.packet.d.k, strA);
                        }
                        if (iOptInt != 30002) {
                            return jSONObject.toString();
                        }
                        JSONObject jSONObject2 = (JSONObject) jSONObject.opt(com.alipay.sdk.packet.d.k);
                        ArrayList arrayList = new ArrayList();
                        JSONArray jSONArrayOptJSONArray = jSONObject2.optJSONArray("urls");
                        if (jSONArrayOptJSONArray != null) {
                            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                                arrayList.add(jSONArrayOptJSONArray.getString(i));
                            }
                        }
                        if (arrayList.isEmpty()) {
                            return null;
                        }
                        return a(context, arrayList, str2, network);
                    }
                }
                return jSONObject.toString();
            } catch (Throwable th) {
                com.mobile.auth.a.a.a(a, "decryptResult error", th);
                return null;
            }
        } catch (Throwable th2) {
            try {
                ExceptionProcessor.processException(th2);
                return null;
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
                return null;
            }
        }
    }

    private static String c(Context context, String str, String str2, Network network) {
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                int iOptInt = jSONObject.optInt(com.alipay.sdk.util.j.c);
                String strOptString = jSONObject.optString(com.alipay.sdk.packet.d.k);
                if (iOptInt == 0 && !TextUtils.isEmpty(strOptString)) {
                    String strA = a(strOptString, str2);
                    if (!TextUtils.isEmpty(strA)) {
                        try {
                            jSONObject.put(com.alipay.sdk.packet.d.k, new JSONObject(strA));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            jSONObject.put(com.alipay.sdk.packet.d.k, strA);
                        }
                    }
                }
                return jSONObject.toString();
            } catch (Throwable th) {
                com.mobile.auth.a.a.a(a, "decryptResult error", th);
                return null;
            }
        } catch (Throwable th2) {
            try {
                ExceptionProcessor.processException(th2);
                return null;
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
                return null;
            }
        }
    }

    public void a(final Context context, final String str, final String str2, final String str3, final com.mobile.auth.a.b bVar) {
        try {
            int i = com.mobile.auth.a.a.b <= 0 ? com.igexin.push.config.c.d : com.mobile.auth.a.a.b;
            final String strA = e.a();
            final String strA2 = e.a(context);
            com.mobile.auth.b.e.a(strA).a(str).b(strA2).d("preauth").c(k.f(context)).i(context.getPackageName());
            a(context, strA, new r.a() { // from class: com.mobile.auth.c.b.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        String strA3 = b.a(b.this, context, str, str2, str3, null, strA2, strA);
                        if (a()) {
                            return;
                        }
                        com.mobile.auth.a.a.b(context, strA3, strA, bVar);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            }, i, bVar);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void b(final Context context, final String str, final String str2, final String str3, final com.mobile.auth.a.b bVar) {
        try {
            int i = com.mobile.auth.a.a.b <= 0 ? com.igexin.push.config.c.d : com.mobile.auth.a.a.b;
            final String strA = e.a();
            final String strA2 = e.a(context);
            com.mobile.auth.b.e.a(strA).a(str).b(strA2).d("preauth").c(k.f(context)).i(context.getPackageName());
            if (Build.VERSION.SDK_INT < 21) {
                a(context, strA, new r.a() { // from class: com.mobile.auth.c.b.3
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            if (new j().a(context, "https://id6.me/auth/preauth.do")) {
                                if (a()) {
                                    return;
                                }
                                String strA3 = b.a(b.this, context, str, str2, str3, null, strA2, strA);
                                if (a()) {
                                } else {
                                    com.mobile.auth.a.a.b(context, strA3, strA, bVar);
                                }
                            } else if (a()) {
                            } else {
                                com.mobile.auth.a.a.b(context, o.a(80800, "WIFI切换超时"), strA, bVar);
                            }
                        } catch (Throwable th) {
                            try {
                                ExceptionProcessor.processException(th);
                            } catch (Throwable th2) {
                                ExceptionProcessor.processException(th2);
                            }
                        }
                    }
                }, i, bVar);
                return;
            }
            j jVar = new j();
            jVar.a(context, new j.a() { // from class: com.mobile.auth.c.b.2
                private boolean i = false;
                private boolean j = false;

                @Override // com.mobile.auth.c.j.a
                public synchronized void a() {
                    try {
                        this.i = true;
                        if (!this.j) {
                            com.mobile.auth.b.e.a(strA, "{\"result\":80000,\"msg\":\"请求超时\"}", "");
                            com.mobile.auth.a.a.b(context, "{\"result\":80000,\"msg\":\"请求超时\"}", strA, bVar);
                        }
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.c.j.a
                public synchronized void a(int i2, String str4, long j) {
                    try {
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                    if (!this.i && !this.j) {
                        this.j = true;
                        com.mobile.auth.b.e.a(strA).h("switchToMobile_L  onFail()  expendTime : " + j).a(i2).f(str4).b(j);
                        com.mobile.auth.a.a.b(context, o.a(i2, str4), strA, bVar);
                        com.mobile.auth.a.a.a(b.a(), "Switching network failed (L), errorMsg :" + str4 + " , expendTime ：" + j);
                    }
                }

                @Override // com.mobile.auth.c.j.a
                public void a(Network network, long j) {
                    try {
                        com.mobile.auth.a.a.a(b.a(), "Switching network successfully (L) , expendTime ：" + j);
                        if (!this.i && !this.j) {
                            com.mobile.auth.b.e.a(strA).b(j);
                            String strA3 = b.a(b.this, context, str, str2, str3, network, strA2, strA);
                            synchronized (this) {
                                if (!this.i && !this.j) {
                                    this.j = true;
                                    com.mobile.auth.a.a.b(context, strA3, strA, bVar);
                                }
                            }
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
            jVar.a(i);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
