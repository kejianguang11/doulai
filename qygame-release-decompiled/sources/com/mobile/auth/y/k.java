package com.mobile.auth.y;

import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.crypto.Cipher;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class k {
    public l b;
    private ExecutorService c = Executors.newSingleThreadExecutor();
    public ScheduledExecutorService a = Executors.newScheduledThreadPool(1);

    static String a(Context context) {
        String string;
        String str = "";
        try {
            try {
                String strC = u.c();
                StringBuilder sb = new StringBuilder();
                sb.append(System.currentTimeMillis());
                String string2 = sb.toString();
                String strB = p.b();
                String packageName = context.getPackageName();
                String strG = v.g();
                String strA = v.a();
                String strA2 = v.a(strG, strA.substring(0, 16), strA.substring(16, 32));
                PublicKey publicKeyGeneratePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(j.b("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVc1ecjpc5k7TkabF935iQONDZ0/E5XWPVv9FEsI59XTRW0+BCMK1MODRSWMvHFrPMh9ZilnRr7qXuAKCBEynQEghmpIVvMYhFu48FAI9bKfkI5lKuQK+tc4X0+zTbNrpedNoKXK4C7dDjTETBH6prwWE9j5WsAf0gbjUbIs3FxwIDAQAB")));
                Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                cipher.init(1, publicKeyGeneratePublic);
                String strA3 = j.a(cipher.doFinal(strA.getBytes()));
                String strA4 = v.a(context, context.getPackageName(), p.b);
                String strB2 = u.b();
                if (!TextUtils.isEmpty(strB2)) {
                    strB2 = "0";
                }
                String str2 = strA4 + "\n" + strC + "\n2.1\njson\n" + strB2 + "\n" + packageName + "\n" + strA2 + "\n" + strB + "\n" + strA3 + "\n" + string2;
                String strA5 = v.a(str2.replaceAll("\n", ""));
                t.a("unSignDebugInfo=".concat(String.valueOf(str2)));
                String strA6 = j.a(strA2);
                String strA7 = j.a(strA3);
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("androidMd5", strA4);
                jSONObject.put("apiKey", strC);
                jSONObject.put(com.alipay.sdk.cons.c.m, "2.1");
                jSONObject.put("format", "json");
                jSONObject.put("operator", strB2);
                jSONObject.put("packName", packageName);
                jSONObject.put("privateIp", strA6);
                jSONObject.put("sdkVersion", strB);
                jSONObject.put("secretKey", strA7);
                jSONObject.put("timeStamp", string2);
                jSONObject.put("sign", strA5);
                string = jSONObject.toString();
            } catch (Exception e) {
                e = e;
            }
            try {
                t.c("getPreCheckParam_CU_Oath: param ok  \n");
                return string;
            } catch (Exception e2) {
                str = string;
                e = e2;
                e.printStackTrace();
                return str;
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    static /* synthetic */ void a(k kVar) {
        try {
            try {
                if (kVar.a != null) {
                    kVar.a.shutdownNow();
                    kVar.a = null;
                    return;
                }
                return;
            } catch (Exception unused) {
                t.b();
                return;
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
        ExceptionProcessor.processException(th);
    }

    public final void a(final Context context, final int i, final Object obj, final m mVar) {
        try {
            synchronized (this) {
                try {
                    this.c.submit(new Runnable() { // from class: com.mobile.auth.y.k.4
                        @Override // java.lang.Runnable
                        public final void run() {
                            try {
                                try {
                                    String str = "";
                                    if (i != 2) {
                                        mVar.a(i, 410009, "410009no this type");
                                    } else {
                                        str = u.a() + s.a(k.a(context), com.alipay.sdk.sys.a.b);
                                    }
                                    n nVar = new n();
                                    Context context2 = context;
                                    HashMap<String, String> map = new HashMap<>();
                                    map.put("user-agent", "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 %sSafari/533.1");
                                    map.put("netType", com.igexin.push.config.c.H);
                                    map.put("os", "android");
                                    map.put("Accept", "*/*");
                                    String strA = nVar.a(context2, str, map, obj);
                                    if (u.h() == 1) {
                                        try {
                                            q.a().b();
                                            t.c("\n  WIFI + 流量 \n call releaseNetwork() \n");
                                        } catch (Exception unused) {
                                            t.b();
                                        }
                                    }
                                    if (TextUtils.isEmpty(strA)) {
                                        mVar.a(i, 410002, "网络请求响应为空");
                                    } else {
                                        mVar.a(i, 1, strA);
                                    }
                                } catch (Throwable th) {
                                    ExceptionProcessor.processException(th);
                                }
                            } catch (Exception unused2) {
                                t.b();
                            }
                        }
                    });
                } catch (Exception e) {
                    mVar.a(i, 410009, "410009" + e.getMessage());
                }
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }
}
