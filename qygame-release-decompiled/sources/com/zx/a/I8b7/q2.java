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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public class q2 {

    @SuppressLint({"NewApi"})
    public class a extends ConnectivityManager.NetworkCallback {
        public ConnectivityManager a;
        public b b;
        public TimerTask d;
        public final AtomicBoolean e = new AtomicBoolean(false);
        public Timer c = new Timer();

        /* JADX INFO: renamed from: com.zx.a.I8b7.q2$a$a, reason: collision with other inner class name */
        public class C0062a extends TimerTask {
            public C0062a(q2 q2Var) {
            }

            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                try {
                    b bVar = a.this.b;
                    if (bVar != null) {
                        bVar.a(1, "time out 7s!");
                    }
                } catch (Throwable th) {
                    v2.a(th);
                }
            }
        }

        public a(q2 q2Var, ConnectivityManager connectivityManager, b bVar) {
            this.a = connectivityManager;
            this.b = bVar;
            C0062a c0062a = new C0062a(q2Var);
            this.d = c0062a;
            this.c.schedule(c0062a, 7000L);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(Network network) {
            super.onAvailable(network);
            try {
                if (this.e.getAndSet(true)) {
                    return;
                }
                this.d.cancel();
                this.c.cancel();
                this.b.a(network);
                this.a.unregisterNetworkCallback(this);
            } catch (Throwable th) {
                v2.a(th);
                b bVar = this.b;
                if (bVar != null) {
                    try {
                        bVar.a(1, th.getMessage());
                        this.a.unregisterNetworkCallback(this);
                    } catch (Throwable th2) {
                        v2.a(th2);
                    }
                }
            }
        }
    }

    public interface b {
        void a();

        void a(int i, String str);

        void a(Network network);
    }

    public static final class c {
        public static final q2 a = new q2();
    }

    public void a(b bVar) throws Throwable {
        boolean zA = b4.a(q3.a, "android.permission.ACCESS_WIFI_STATE", false);
        boolean zA2 = b4.a(q3.a, "android.permission.CHANGE_NETWORK_STATE", false);
        if (Build.VERSION.SDK_INT >= 21 && zA && zA2 && a(q3.a)) {
            if (!b(q3.a)) {
                bVar.a();
                return;
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) q3.a.getSystemService("connectivity");
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            builder.addCapability(12);
            builder.addTransportType(0);
            connectivityManager.requestNetwork(builder.build(), new a(this, connectivityManager, bVar));
        }
    }

    public boolean a(Context context) {
        try {
            if (Build.VERSION.SDK_INT < 24) {
                return true;
            }
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            return ((Boolean) telephonyManager.getClass().getDeclaredMethod("getDataEnabled", new Class[0]).invoke(telephonyManager, new Object[0])).booleanValue();
        } catch (Exception unused) {
            return true;
        }
    }

    public boolean b(Context context) {
        if (((WifiManager) context.getSystemService("wifi")).isWifiEnabled()) {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (wifiManager.isWifiEnabled() && (connectionInfo == null ? 0 : connectionInfo.getIpAddress()) != 0) {
                return true;
            }
        }
        return false;
    }
}
