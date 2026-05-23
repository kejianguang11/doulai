package com.mobile.auth.gatewayauth.manager;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.mobile.auth.BuildConfig;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.PnsReporter;
import com.mobile.auth.gatewayauth.model.MonitorStruct;
import com.mobile.auth.gatewayauth.model.UStruct;
import com.mobile.auth.gatewayauth.utils.AESUtils;
import com.mobile.auth.gatewayauth.utils.security.PackageUtils;
import com.nirvana.tools.logger.storage.LoggerIdManager;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class d {
    public static String a;
    public static String b;
    public static String c;
    private Context d;
    private String h;
    private String i;
    private String j;
    private Map<String, String> o;
    private Map<String, String> p;
    private Map<String, String> q;
    private Map<String, String> r;
    private com.mobile.auth.o.a s;
    private final String e = "c78623c22e2f6513";
    private String f = UUID.randomUUID().toString();
    private boolean g = true;
    private String k = "";
    private String l = "";
    private com.mobile.auth.n.a m = null;
    private LoggerIdManager n = null;

    public d(Context context) {
        this.d = context.getApplicationContext();
        o();
    }

    private String a(Context context) {
        try {
            try {
                try {
                    Object objInvoke = Class.forName("com.nirvana.tools.logger.utils.LocalDeviceUtil").getDeclaredMethod("getUmaaId", Context.class).invoke(null, context);
                    return objInvoke != null ? objInvoke.toString() : "";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "";
                }
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
            ExceptionProcessor.processException(th);
            return null;
        } catch (Throwable th2) {
            ExceptionProcessor.processException(th2);
            return null;
        }
    }

    private String a(JSONObject jSONObject, String str, String str2, String str3, String str4) {
        try {
            try {
                jSONObject.put(com.igexin.push.core.d.d.d, new JSONObject(a(this.d, str2, str4)));
                jSONObject.put("action", str);
                jSONObject.put("apiLevel", str3);
                jSONObject.put("osType", "Android");
                if (this.r != null && !this.r.isEmpty()) {
                    for (String str5 : this.r.keySet()) {
                        jSONObject.put(str5, this.r.get(str5));
                    }
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
        } catch (JSONException e) {
            this.s.e("AssembleMonitorInfoError!", Log.getStackTraceString(e));
        }
        return jSONObject.toString();
    }

    private String b(Context context) {
        try {
            try {
                try {
                    Object objInvoke = Class.forName("com.nirvana.tools.logger.utils.LocalDeviceUtil").getDeclaredMethod("getDeviceId", Context.class).invoke(null, context);
                    return objInvoke != null ? objInvoke.toString() : "";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "";
                }
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
            ExceptionProcessor.processException(th);
            return null;
        } catch (Throwable th2) {
            ExceptionProcessor.processException(th2);
            return null;
        }
    }

    private void o() {
        try {
            this.s = com.mobile.auth.o.a.a(this.d);
            this.m = new com.mobile.auth.n.a(a(), this);
            r();
            this.n = new LoggerIdManager(this.d);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private String p() {
        try {
            return q() ? a(this.d) : "";
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

    private boolean q() {
        try {
            return Class.forName("com.nirvana.tools.logger.utils.LocalDeviceUtil") != null;
        } catch (ClassNotFoundException unused) {
            return false;
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

    private boolean r() {
        try {
            return Class.forName("com.nirvana.tools.logger.storage.LoggerIdManager") != null;
        } catch (ClassNotFoundException unused) {
            return false;
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

    public com.mobile.auth.o.a a() {
        try {
            return this.s;
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

    public String a(MonitorStruct monitorStruct) {
        try {
            return a(monitorStruct.getVendorKey(), monitorStruct.getAction(), new UStruct(monitorStruct), monitorStruct.getApiLevel(), monitorStruct.getPhoneNumber());
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

    public String a(String str, String str2, UStruct uStruct, String str3) {
        try {
            return a(str, str2, uStruct, str3, "");
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

    public String a(String str, String str2, UStruct uStruct, String str3, String str4) {
        try {
            JSONObject jSONObject = new JSONObject();
            if (!TextUtils.isEmpty(this.k)) {
                uStruct.setEt(this.k);
            }
            try {
                JSONObject json = uStruct.toJson();
                if (this.o != null && !this.o.isEmpty()) {
                    for (String str5 : this.o.keySet()) {
                        json.put(str5, this.o.get(str5));
                    }
                }
                jSONObject.put("u", json);
                jSONObject.put("u", uStruct.toJson());
                if (TextUtils.isEmpty(str4)) {
                    Map<String, String> mapB = b(uStruct.getPrivateIp());
                    if (this.q != null && !this.q.isEmpty()) {
                        mapB.putAll(this.q);
                    }
                    jSONObject.put(com.igexin.push.core.d.d.g, AESUtils.encrypt(new JSONObject(mapB).toString(), "c78623c22e2f6513"));
                } else {
                    HashMap map = new HashMap();
                    map.put("phoneNumber", str4);
                    map.putAll(b(uStruct.getPrivateIp()));
                    if (this.q != null && !this.q.isEmpty()) {
                        map.putAll(this.q);
                    }
                    jSONObject.put(com.igexin.push.core.d.d.g, AESUtils.encrypt(new JSONObject(map).toString(), "c78623c22e2f6513"));
                }
            } catch (Exception e) {
                this.s.e("BuildMonitorError!", Log.getStackTraceString(e));
            }
            return a(jSONObject, str2, str, str3, uStruct.getNetworkType());
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

    public Map<String, Object> a(Context context, String str, String str2) {
        String str3;
        String str4;
        try {
            HashMap map = new HashMap();
            map.put("createTime", Long.valueOf(System.currentTimeMillis()));
            map.put("osVersion", com.mobile.auth.gatewayauth.utils.f.b());
            map.put("deviceName", com.mobile.auth.gatewayauth.utils.f.c());
            map.put("deviceBrand", com.mobile.auth.gatewayauth.utils.f.a());
            map.put("packageName", PackageUtils.getPackageName(context));
            map.put("appVersion", PackageUtils.getVersionName(context));
            map.put("signature", PackageUtils.getSign(context));
            if (Constant.VENDOR_CUCC.equals(str)) {
                map.put("vendorKey", Constant.VENDOR_CUXZ);
            } else {
                map.put("vendorKey", str);
            }
            map.put("sdkVersion", BuildConfig.VERSION_NAME);
            map.put("networkType", str2);
            map.put("monitorVersion", "2.1");
            map.put(com.alipay.sdk.cons.b.g, l());
            map.put("um_aaid", m());
            map.put("uniqueId", k());
            map.put("traceId", this.f);
            map.put("archiveName", BuildConfig.FLAVOR);
            if (Constant.VENDOR_CMCC.equals(str)) {
                str3 = "carrierSdkVersion";
                str4 = BuildConfig.CMCC_SDK_VERSION;
            } else if (Constant.VENDOR_CUCC.equals(str)) {
                str3 = "carrierSdkVersion";
                str4 = BuildConfig.CUZX_SDK_VERSION;
            } else if (Constant.VENDOR_CTCC.equals(str)) {
                str3 = "carrierSdkVersion";
                str4 = BuildConfig.CTCC_SDK_VERSION;
            } else if (Constant.VENDOR_CUXZ.equals(str)) {
                str3 = "carrierSdkVersion";
                str4 = BuildConfig.CUZX_SDK_VERSION;
            } else {
                str3 = "carrierSdkVersion";
                str4 = "";
            }
            map.put(str3, str4);
            if (this.p != null && !this.p.isEmpty()) {
                map.putAll(this.p);
            }
            return map;
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

    public void a(f fVar) {
        try {
            this.m.a(fVar);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(String str) {
        try {
            this.l = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public PnsReporter b() {
        try {
            return this.m;
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

    public String b(String str, String str2, UStruct uStruct, String str3) {
        String str4;
        String strEncrypt;
        try {
            JSONObject jSONObject = new JSONObject();
            if (!TextUtils.isEmpty(this.k)) {
                uStruct.setEt(this.k);
            }
            try {
                JSONObject json = uStruct.toJson();
                if (this.o != null && !this.o.isEmpty()) {
                    for (String str5 : this.o.keySet()) {
                        json.put(str5, this.o.get(str5));
                    }
                }
                jSONObject.put("u", json);
                if (this.q == null || this.q.isEmpty()) {
                    str4 = com.igexin.push.core.d.d.g;
                    strEncrypt = "";
                } else {
                    this.q.putAll(this.q);
                    str4 = com.igexin.push.core.d.d.g;
                    strEncrypt = AESUtils.encrypt(new JSONObject(this.q).toString(), "c78623c22e2f6513");
                }
                jSONObject.put(str4, strEncrypt);
            } catch (Exception e) {
                this.s.e("BuildMonitorNoSError!", Log.getStackTraceString(e));
            }
            return a(jSONObject, str2, str, str3, uStruct.getNetworkType());
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

    public Map<String, String> b(String str) {
        try {
            HashMap map = new HashMap();
            if (TextUtils.isEmpty(str)) {
                map.put("innerIP", str);
            }
            map.put("sceneCode", this.l);
            return map;
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

    public synchronized String c() {
        try {
            if (TextUtils.isEmpty(this.h)) {
                return f();
            }
            return this.h;
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

    public void c(String str) {
        if (str != null) {
            try {
                this.k = str;
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    public synchronized String d() {
        try {
            if (TextUtils.isEmpty(this.i)) {
                return g();
            }
            return this.i;
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

    public synchronized String e() {
        try {
            if (TextUtils.isEmpty(this.j)) {
                return j();
            }
            return this.j;
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

    public synchronized String f() {
        try {
            this.h = UUID.randomUUID().toString();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        return this.h;
    }

    public synchronized String g() {
        try {
            this.i = UUID.randomUUID().toString();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        return this.i;
    }

    public synchronized void h() {
        try {
            this.h = null;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public synchronized void i() {
        try {
            this.i = null;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public synchronized String j() {
        try {
            this.j = UUID.randomUUID().toString();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        return this.j;
    }

    public String k() {
        try {
            if (a == null && this.n != null) {
                a = this.n.getUniqueId();
            }
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

    public String l() {
        try {
            if (!this.g) {
                return null;
            }
            if (b == null && q()) {
                b = b(this.d);
            }
            return b;
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

    public String m() {
        try {
            if (c == null) {
                c = p();
            }
            return c;
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

    public void n() {
        try {
            this.g = false;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
