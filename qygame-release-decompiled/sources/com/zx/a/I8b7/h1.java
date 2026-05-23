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
import android.util.Base64;
import com.zx.a.I8b7.s1;
import com.zx.a.I8b7.s2;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class h1 {
    public static final byte[] a = {-95, -8, -49, 34, 91, -116, -29, -2, -106, 39, -56, 39, -121, 112, -22, 21};
    public static final byte[] b = {-84, -2, -56, -72, -90, 65, -76, -48, -92, 30, -27, -64, -102, 101, 95, 24};

    @SuppressLint({"NewApi"})
    public class a extends ConnectivityManager.NetworkCallback {
        public ConnectivityManager a;
        public TimerTask c;
        public final AtomicBoolean d = new AtomicBoolean(false);
        public Timer b = new Timer();

        /* JADX INFO: renamed from: com.zx.a.I8b7.h1$a$a, reason: collision with other inner class name */
        public class C0059a extends TimerTask {
            public C0059a(a aVar, h1 h1Var) {
            }

            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
            }
        }

        public a(ConnectivityManager connectivityManager) {
            this.a = connectivityManager;
            C0059a c0059a = new C0059a(this, h1.this);
            this.c = c0059a;
            this.b.schedule(c0059a, 7000L);
        }

        public final void a(Network network) throws Exception {
            StringBuilder sbA = j3.a(h1.this.c());
            sbA.append(URLEncoder.encode(q3.i, com.alipay.sdk.sys.a.m));
            HttpURLConnection httpURLConnection = (HttpURLConnection) network.openConnection(new URL(sbA.toString()));
            httpURLConnection.setConnectTimeout(7000);
            httpURLConnection.setReadTimeout(7000);
            httpURLConnection.connect();
            w1 w1VarA = w1.a(z0.b("text/json; charset=utf-8"), httpURLConnection.getContentLength(), httpURLConnection.getResponseCode() == 200 ? httpURLConnection.getInputStream() : httpURLConnection.getErrorStream());
            StringBuilder sbA2 = j3.a("cmd 8 suc!");
            sbA2.append(w1VarA.b());
            v2.a(sbA2.toString());
            httpURLConnection.disconnect();
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(Network network) {
            super.onAvailable(network);
            try {
                if (this.d.getAndSet(true)) {
                    return;
                }
                this.c.cancel();
                this.b.cancel();
                a(network);
                this.a.unregisterNetworkCallback(this);
            } catch (Throwable unused) {
            }
        }
    }

    public static final class b {
        public static final h1 a = new h1();
    }

    public h1() {
        b();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void a() throws Throwable {
        boolean zBooleanValue;
        if (TextUtils.isEmpty(q3.i)) {
            return;
        }
        boolean zA = b4.a(q3.a, "android.permission.ACCESS_WIFI_STATE", false);
        boolean zA2 = b4.a(q3.a, "android.permission.CHANGE_NETWORK_STATE", false);
        int i = Build.VERSION.SDK_INT;
        if (i >= 21 && zA && zA2) {
            Context context = q3.a;
            boolean z = true;
            if (i < 24) {
                zBooleanValue = true;
                if (zBooleanValue) {
                    return;
                }
                Context context2 = q3.a;
                if (((WifiManager) context2.getSystemService("wifi")).isWifiEnabled()) {
                    WifiManager wifiManager = (WifiManager) context2.getSystemService("wifi");
                    WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                    if (!(wifiManager.isWifiEnabled() && (connectionInfo == null ? 0 : connectionInfo.getIpAddress()) != 0)) {
                    }
                    if (z) {
                    }
                } else {
                    z = false;
                    if (z) {
                        if (Build.VERSION.SDK_INT >= 21) {
                            ConnectivityManager connectivityManager = (ConnectivityManager) q3.a.getSystemService("connectivity");
                            NetworkRequest.Builder builder = new NetworkRequest.Builder();
                            builder.addCapability(12);
                            builder.addTransportType(0);
                            connectivityManager.requestNetwork(builder.build(), new a(connectivityManager));
                            return;
                        }
                        return;
                    }
                }
            } else {
                try {
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                    zBooleanValue = ((Boolean) telephonyManager.getClass().getDeclaredMethod("getDataEnabled", new Class[0]).invoke(telephonyManager, new Object[0])).booleanValue();
                } catch (Exception unused) {
                    zBooleanValue = true;
                }
                if (zBooleanValue) {
                }
            }
        }
        d();
    }

    public final String[] b() {
        return new String[]{"https://nisportal.10010.com:9001", "1073741824000"};
    }

    public final String c() throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, new SecretKeySpec(a, "AES"), new IvParameterSpec(b));
        return new String(cipher.doFinal(Base64.decode("xxXbFehPQ+Zs/VtAtd6DL3ogem3eY/0zoVy6qWtcMuI5NPlB0yUsMmvUu+oK7CzNKOAIJmt5N8/SdP04E4O7Tw==", 2)), com.alipay.sdk.sys.a.m);
    }

    public final void d() {
        try {
            s2 s2Var = new s2(new s2.a());
            s1.a aVar = new s1.a();
            s1.a aVarA = aVar.a(c() + URLEncoder.encode(q3.i, com.alipay.sdk.sys.a.m));
            aVarA.b = "GET";
            aVarA.e = "unicom uaid nisportal api";
            new k1(s2Var, new s1(aVar)).a();
            v2.a("l t s u c!");
        } catch (Throwable unused) {
        }
    }
}
