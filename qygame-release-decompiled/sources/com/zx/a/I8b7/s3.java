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
import com.zx.a.I8b7.h;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class s3 {
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

        /* JADX INFO: renamed from: com.zx.a.I8b7.s3$a$a, reason: collision with other inner class name */
        public class C0064a extends TimerTask {
            public C0064a(s3 s3Var) {
            }

            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                try {
                    a aVar = a.this;
                    Callback callback = aVar.b;
                    if (callback != null) {
                        callback.callback(s3.this.a("wifi 情况下切换数据网络超时, 检查是否打开数据网络!", 1));
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
            C0064a c0064a = new C0064a(s3.this);
            this.d = c0064a;
            this.c.schedule(c0064a, 7000L);
        }

        @Java2C.Method2C
        private native void a(Network network, String str);

        public final b a(Network network) throws Throwable {
            HttpURLConnection httpURLConnection = (HttpURLConnection) network.openConnection(new URL(s3.this.b[0]));
            httpURLConnection.setConnectTimeout(7000);
            httpURLConnection.setReadTimeout(7000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();
            JSONObject jSONObjectA = s3.this.a();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), com.alipay.sdk.sys.a.m));
            bufferedWriter.write(jSONObjectA.toString());
            bufferedWriter.close();
            JSONObject jSONObject = new JSONObject(w1.a(z0.b("text/json; charset=utf-8"), httpURLConnection.getContentLength(), httpURLConnection.getResponseCode() == 200 ? httpURLConnection.getInputStream() : httpURLConnection.getErrorStream()).b()).getJSONObject("body");
            String string = jSONObject.getString("resultCode");
            jSONObject.getString("resultDesc");
            httpURLConnection.disconnect();
            if (!"103000".equals(string)) {
                return new b(false, s3.this.a(jSONObject.toString(), "cmcc"));
            }
            String string2 = jSONObject.getString(AssistPushConsts.MSG_TYPE_TOKEN);
            s3.this.a(this.e, this.b, this.f, "cmcc", string2, null);
            return new b(true, string2);
        }

        public final b b(Network network) throws Exception {
            HttpURLConnection httpURLConnection = (HttpURLConnection) network.openConnection(new URL(s3.this.c[0]));
            httpURLConnection.setConnectTimeout(7000);
            httpURLConnection.setReadTimeout(7000);
            httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpURLConnection.setRequestProperty("Charset", com.alipay.sdk.sys.a.m);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();
            String strSubstring = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
            String strA = s3.this.a(strSubstring);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), com.alipay.sdk.sys.a.m));
            bufferedWriter.write(strA);
            bufferedWriter.close();
            JSONObject jSONObject = new JSONObject(w1.a(z0.b(""), httpURLConnection.getContentLength(), httpURLConnection.getResponseCode() == 200 ? httpURLConnection.getInputStream() : httpURLConnection.getErrorStream()).b());
            httpURLConnection.disconnect();
            jSONObject.getString("msg");
            int i = jSONObject.getInt(com.alipay.sdk.util.j.c);
            String strOptString = jSONObject.optString(com.alipay.sdk.packet.d.k);
            if (i != 0 || TextUtils.isEmpty(strOptString)) {
                return new b(false, s3.this.a(jSONObject.toString(), "ct"));
            }
            String strB = s3.this.b(strSubstring, strOptString);
            s3.this.a(this.e, this.b, this.f, "ct", strB, null);
            return new b(true, strB);
        }

        public final b c(Network network) throws Throwable {
            s3 s3Var = s3.this;
            String strB = s3Var.b(s3Var.a[0]);
            v2.a("unicomUAIDNisportalUrl: " + strB);
            HttpURLConnection httpURLConnection = (HttpURLConnection) network.openConnection(new URL(strB));
            httpURLConnection.setConnectTimeout(7000);
            httpURLConnection.setReadTimeout(7000);
            httpURLConnection.connect();
            JSONObject jSONObject = new JSONObject(w1.a(z0.b("text/json; charset=utf-8"), httpURLConnection.getContentLength(), httpURLConnection.getResponseCode() == 200 ? httpURLConnection.getInputStream() : httpURLConnection.getErrorStream()).b());
            String strOptString = jSONObject.optString("authurl");
            if (TextUtils.isEmpty(strOptString)) {
                return new b(false, s3.this.a(jSONObject.toString(), "unicom"));
            }
            String strB2 = s3.this.b(strOptString);
            v2.a("unicomUAIDAuthUrl: " + strB2);
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) network.openConnection(new URL(strB2));
            httpURLConnection2.setConnectTimeout(7000);
            httpURLConnection2.setReadTimeout(7000);
            httpURLConnection2.connect();
            JSONObject jSONObject2 = new JSONObject(w1.a(z0.b("text/json; charset=utf-8"), httpURLConnection2.getContentLength(), httpURLConnection2.getResponseCode() == 200 ? httpURLConnection2.getInputStream() : httpURLConnection2.getErrorStream()).b());
            String strOptString2 = jSONObject2.optString("code");
            if (TextUtils.isEmpty(strOptString2)) {
                return new b(false, s3.this.a(jSONObject2.toString(), "unicom"));
            }
            v2.a("unicomUAID code: " + strOptString2);
            a(network, strOptString2);
            httpURLConnection.disconnect();
            httpURLConnection2.disconnect();
            s3.this.a(this.e, this.b, this.f, "unicom", strOptString2, jSONObject2.optString("province", null));
            return new b(true, strOptString2);
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
                JSONArray jSONArray = new JSONArray();
                b bVarA = a(network);
                if (!bVarA.a) {
                    jSONArray.put(bVarA.b);
                    b bVarC = c(network);
                    if (!bVarC.a) {
                        jSONArray.put(bVarC.b);
                        b bVarB = b(network);
                        if (!bVarB.a) {
                            jSONArray.put(bVarB.b);
                            this.b.callback(s3.this.a(jSONArray.toString(), 1));
                        }
                    }
                }
                this.a.unregisterNetworkCallback(this);
            } catch (Throwable th) {
                v2.a(th);
                Callback callback = this.b;
                if (callback != null) {
                    try {
                        callback.callback(s3.this.a(th.getMessage(), 1));
                        this.a.unregisterNetworkCallback(this);
                    } catch (JSONException e) {
                        v2.a(e);
                    }
                }
            }
        }
    }

    public static class b {
        public boolean a;
        public String b;

        public b(boolean z, String str) {
            this.a = z;
            this.b = str;
        }
    }

    public static final class c {
        public static final s3 a = new s3();
    }

    @Java2C.Method2C
    private native b a(JSONObject jSONObject, Callback callback, int i) throws Throwable;

    /* JADX INFO: Access modifiers changed from: private */
    @Java2C.Method2C
    public native String a(String str) throws Exception;

    /* JADX INFO: Access modifiers changed from: private */
    @Java2C.Method2C
    public native JSONObject a() throws JSONException;

    @Java2C.Method2C
    private native b b(JSONObject jSONObject, Callback callback, int i) throws Throwable;

    /* JADX INFO: Access modifiers changed from: private */
    @Java2C.Method2C
    public native String b(String str);

    /* JADX INFO: Access modifiers changed from: private */
    @Java2C.Method2C
    public native String b(String str, String str2) throws BadPaddingException, JSONException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException;

    @Java2C.Method2C
    private final native String[] b();

    @Java2C.Method2C
    private native void c(JSONObject jSONObject, Callback callback, int i) throws Throwable;

    @Java2C.Method2C
    private final native String[] c();

    @Java2C.Method2C
    private native b d(JSONObject jSONObject, Callback callback, int i) throws Throwable;

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

    public final String a(String str, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("msg", str);
        jSONObject.put("code", 10010);
        jSONObject.put("isp", str2);
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

    /* JADX WARN: Removed duplicated region for block: B:13:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00b7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void e(JSONObject jSONObject, Callback callback, int i) throws Throwable {
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
                ((h.a) callback).callback(a(str2, 1));
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
            ((h.a) callback).callback(a(str2, 1));
            return;
        }
        str = "开始执行getUAID333";
        v2.a(str);
        c(jSONObject, callback, i);
    }
}
