package com.zx.a.I8b7;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.igexin.assist.sdk.AssistPushConsts;
import com.zx.module.annotation.Java2C;
import com.zx.module.base.Callback;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class r3 {
    public final String[] a = f();
    public final String[] b = d();
    public final String[] c = c();

    @SuppressLint({"NewApi"})
    public class a extends ConnectivityManager.NetworkCallback {
        public ConnectivityManager a;
        public Callback b;
        public TimerTask d;
        public JSONObject e;
        public int f;
        public final AtomicBoolean g = new AtomicBoolean(false);
        public Timer c = new Timer();

        /* JADX INFO: renamed from: com.zx.a.I8b7.r3$a$a, reason: collision with other inner class name */
        public class C0063a extends TimerTask {
            public C0063a(r3 r3Var) {
            }

            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                try {
                    a aVar = a.this;
                    Callback callback = aVar.b;
                    if (callback != null) {
                        callback.callback(r3.this.a("wifi 情况下切换数据网络超时, 检查是否打开数据网络!", 1));
                    }
                } catch (JSONException e) {
                    v2.a(e);
                }
            }
        }

        public a(JSONObject jSONObject, ConnectivityManager connectivityManager, Callback callback, int i) {
            this.e = jSONObject;
            this.f = i;
            this.a = connectivityManager;
            this.b = callback;
            C0063a c0063a = new C0063a(r3.this);
            this.d = c0063a;
            this.c.schedule(c0063a, 7000L);
        }

        @Java2C.Method2C
        private native void a(Network network, String str);

        public final void a(Network network) throws Throwable {
            HttpURLConnection httpURLConnection = (HttpURLConnection) network.openConnection(new URL(r3.this.b[0]));
            httpURLConnection.setConnectTimeout(7000);
            httpURLConnection.setReadTimeout(7000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();
            JSONObject jSONObjectA = r3.this.a();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), com.alipay.sdk.sys.a.m));
            bufferedWriter.write(jSONObjectA.toString());
            bufferedWriter.close();
            JSONObject jSONObject = new JSONObject(w1.a(z0.b("text/json; charset=utf-8"), httpURLConnection.getContentLength(), httpURLConnection.getResponseCode() == 200 ? httpURLConnection.getInputStream() : httpURLConnection.getErrorStream()).b()).getJSONObject("body");
            String string = jSONObject.getString("resultCode");
            jSONObject.getString("resultDesc");
            httpURLConnection.disconnect();
            if ("103000".equals(string)) {
                r3.this.a(this.e, this.b, this.f, "cmcc", jSONObject.getString(AssistPushConsts.MSG_TYPE_TOKEN), null);
            } else {
                this.b.callback(r3.this.b(jSONObject.toString()));
            }
        }

        public final void b(Network network) throws Exception {
            HttpURLConnection httpURLConnection = (HttpURLConnection) network.openConnection(new URL(r3.this.c[0]));
            httpURLConnection.setConnectTimeout(7000);
            httpURLConnection.setReadTimeout(7000);
            httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpURLConnection.setRequestProperty("Charset", com.alipay.sdk.sys.a.m);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();
            String strSubstring = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
            String strA = r3.this.a(strSubstring);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), com.alipay.sdk.sys.a.m));
            bufferedWriter.write(strA);
            bufferedWriter.close();
            JSONObject jSONObject = new JSONObject(w1.a(z0.b(""), httpURLConnection.getContentLength(), httpURLConnection.getResponseCode() == 200 ? httpURLConnection.getInputStream() : httpURLConnection.getErrorStream()).b());
            httpURLConnection.disconnect();
            jSONObject.getString("msg");
            int i = jSONObject.getInt(com.alipay.sdk.util.j.c);
            String strOptString = jSONObject.optString(com.alipay.sdk.packet.d.k);
            if (i != 0 || TextUtils.isEmpty(strOptString)) {
                this.b.callback(r3.this.b(jSONObject.toString()));
            } else {
                r3.this.a(this.e, this.b, this.f, "ct", r3.this.a(strSubstring, strOptString), null);
            }
        }

        public final void c(Network network) throws Throwable {
            r3 r3Var = r3.this;
            String strC = r3Var.c(r3Var.a[0]);
            v2.a("unicomUAIDNisportalUrl: " + strC);
            HttpURLConnection httpURLConnection = (HttpURLConnection) network.openConnection(new URL(strC));
            httpURLConnection.setConnectTimeout(7000);
            httpURLConnection.setReadTimeout(7000);
            httpURLConnection.connect();
            JSONObject jSONObject = new JSONObject(w1.a(z0.b("text/json; charset=utf-8"), httpURLConnection.getContentLength(), httpURLConnection.getResponseCode() == 200 ? httpURLConnection.getInputStream() : httpURLConnection.getErrorStream()).b());
            String strOptString = jSONObject.optString("authurl");
            if (TextUtils.isEmpty(strOptString)) {
                this.b.callback(r3.this.b(jSONObject.toString()));
                return;
            }
            String strC2 = r3.this.c(strOptString);
            v2.a("unicomUAIDAuthUrl: " + strC2);
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) network.openConnection(new URL(strC2));
            httpURLConnection2.setConnectTimeout(7000);
            httpURLConnection2.setReadTimeout(7000);
            httpURLConnection2.connect();
            JSONObject jSONObject2 = new JSONObject(w1.a(z0.b("text/json; charset=utf-8"), httpURLConnection2.getContentLength(), httpURLConnection2.getResponseCode() == 200 ? httpURLConnection2.getInputStream() : httpURLConnection2.getErrorStream()).b());
            String strOptString2 = jSONObject2.optString("code");
            if (TextUtils.isEmpty(strOptString2)) {
                this.b.callback(r3.this.b(jSONObject2.toString()));
                return;
            }
            v2.a("unicomUAID code: " + strOptString2);
            a(network, strOptString2);
            httpURLConnection.disconnect();
            httpURLConnection2.disconnect();
            r3.this.a(this.e, this.b, this.f, "unicom", strOptString2, jSONObject2.optString("province", null));
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(Network network) {
            super.onAvailable(network);
            try {
                if (this.g.getAndSet(true)) {
                    return;
                }
                v2.a("zx 网络切换: 使用移动网络访问");
                this.d.cancel();
                this.c.cancel();
                String strB = b4.b(q3.a);
                if ("中国联通".equals(strB)) {
                    c(network);
                } else if ("中国移动".equals(strB)) {
                    a(network);
                } else if ("中国电信".equals(strB)) {
                    b(network);
                } else {
                    this.b.callback(r3.this.a("暂不支持该运营商", 1));
                }
                this.a.unregisterNetworkCallback(this);
            } catch (Throwable th) {
                v2.a(th);
                Callback callback = this.b;
                if (callback != null) {
                    try {
                        callback.callback(r3.this.a(th.getMessage(), 1));
                        this.a.unregisterNetworkCallback(this);
                    } catch (JSONException e) {
                        v2.a(e);
                    }
                }
            }
        }
    }

    public static final class b {
        public static final r3 a = new r3();
    }

    @Java2C.Method2C
    private native String a(Callback callback) throws Exception;

    /* JADX INFO: Access modifiers changed from: private */
    @Java2C.Method2C
    public native String a(String str) throws Exception;

    /* JADX INFO: Access modifiers changed from: private */
    @Java2C.Method2C
    public native String a(String str, String str2) throws BadPaddingException, JSONException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException;

    /* JADX INFO: Access modifiers changed from: private */
    @Java2C.Method2C
    public native JSONObject a() throws JSONException;

    @Java2C.Method2C
    private native void a(JSONObject jSONObject, Callback callback, int i) throws Throwable;

    @Java2C.Method2C
    private native String b(Callback callback) throws Exception;

    @Java2C.Method2C
    private final native String[] b();

    /* JADX INFO: Access modifiers changed from: private */
    @Java2C.Method2C
    public native String c(String str);

    @Java2C.Method2C
    private native JSONObject c(Callback callback) throws Throwable;

    @Java2C.Method2C
    private final native String[] c();

    @Java2C.Method2C
    private final native String[] d();

    @Java2C.Method2C
    private native String e();

    @Java2C.Method2C
    private final native String[] f();

    public final String a(String str, int i) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(com.alipay.sdk.packet.d.k, str);
        jSONObject.put("code", i);
        return jSONObject.toString();
    }

    public final void a(JSONObject jSONObject, Callback callback, int i, String str, String str2, String str3) throws Throwable {
        JSONObject jSONObject2;
        String string;
        if (i == 0) {
            try {
                callback.callback(a(y1.b(jSONObject, str, str2, str3), 0));
                return;
            } catch (Throwable th) {
                String message = th.getMessage();
                jSONObject2 = new JSONObject();
                jSONObject2.put("msg", message);
                jSONObject2.put("code", 10011);
            }
        } else {
            if (i == 1) {
                String string2 = jSONObject.getString("callerId");
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put(com.alipay.sdk.packet.d.k, r.d(string2 + "|" + q3.g + "|" + q3.a(q3.h) + "|" + str + "|" + str2, b()[0]));
                jSONObject3.put("code", 0);
                string = jSONObject3.toString();
                callback.callback(string);
            }
            if (i != 2) {
                return;
            }
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("code", str2);
            jSONObject4.put("type", str);
            jSONObject2 = new JSONObject();
            jSONObject2.put(com.alipay.sdk.packet.d.k, jSONObject4);
            jSONObject2.put("code", 0);
        }
        string = jSONObject2.toString();
        callback.callback(string);
    }

    public final String b(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("msg", str);
        jSONObject.put("code", 10010);
        return jSONObject.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00b7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void b(JSONObject jSONObject, Callback callback, int i) throws Throwable {
        String str;
        boolean zBooleanValue;
        String str2;
        boolean z;
        boolean zA = b4.a(q3.a, "android.permission.ACCESS_WIFI_STATE", false);
        boolean zA2 = b4.a(q3.a, "android.permission.CHANGE_NETWORK_STATE", false);
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 21 && zA && zA2) {
            Context context = q3.a;
            v2.a("getUAID:forceSendRequestByMobileData with cb");
            if (i2 >= 24) {
                try {
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                    zBooleanValue = ((Boolean) telephonyManager.getClass().getDeclaredMethod("getDataEnabled", new Class[0]).invoke(telephonyManager, new Object[0])).booleanValue();
                } catch (Exception unused) {
                    zBooleanValue = true;
                }
                if (zBooleanValue) {
                }
                callback.callback(a(str2, 1));
                return;
            }
            zBooleanValue = true;
            if (zBooleanValue) {
                v2.a("zx 网络切换: 移动网络不可用，提示请打开移动网络");
                str2 = "zx 网络切换: 移动网络不可用，提示请打开移动网络";
            } else {
                if (((WifiManager) context.getSystemService("wifi")).isWifiEnabled()) {
                    WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                    WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                    if (wifiManager.isWifiEnabled() && (connectionInfo == null ? 0 : connectionInfo.getIpAddress()) != 0) {
                        z = true;
                    }
                    if (z) {
                        str = "zx 网络切换: 直接可以使用移动网络访问";
                    } else {
                        if (Build.VERSION.SDK_INT >= 21) {
                            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                            NetworkRequest.Builder builder = new NetworkRequest.Builder();
                            builder.addCapability(12);
                            builder.addTransportType(0);
                            connectivityManager.requestNetwork(builder.build(), new a(jSONObject, connectivityManager, callback, i));
                            return;
                        }
                        v2.a("zx 网络切换: 请关闭WIFI");
                        str2 = "zx 网络切换: 请关闭WIFI";
                    }
                }
                z = false;
                if (z) {
                }
            }
            callback.callback(a(str2, 1));
            return;
        }
        str = "开始执行getUAID333";
        v2.a(str);
        a(jSONObject, callback, i);
    }
}
