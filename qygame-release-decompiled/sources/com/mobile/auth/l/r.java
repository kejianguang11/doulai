package com.mobile.auth.l;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class r {
    private static r a;
    private ConnectivityManager b;
    private Network c;
    private ConnectivityManager.NetworkCallback d;
    private boolean e;

    public interface a {
        void a(Network network);
    }

    private r(Context context) {
        try {
            this.b = (ConnectivityManager) context.getSystemService("connectivity");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static r a(Context context) {
        if (a == null) {
            synchronized (r.class) {
                if (a == null) {
                    a = new r(context);
                }
            }
        }
        return a;
    }

    @TargetApi(21)
    public synchronized void a(final a aVar) {
        NetworkInfo networkInfo;
        if (this.b == null) {
            c.a("WifiNetworkUtils", "mConnectivityManager 为空");
            aVar.a(null);
            return;
        }
        if (this.c != null && !this.e && (networkInfo = this.b.getNetworkInfo(this.c)) != null && networkInfo.isAvailable()) {
            Log.e("HttpUtils", "reuse network: ");
            aVar.a(this.c);
            return;
        }
        if (this.d == null) {
            NetworkRequest networkRequestBuild = new NetworkRequest.Builder().addCapability(12).addTransportType(0).build();
            this.d = new ConnectivityManager.NetworkCallback() { // from class: com.mobile.auth.l.r.1
                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onAvailable(Network network) {
                    try {
                        if (r.this.b.getNetworkCapabilities(network).hasTransport(0)) {
                            r.this.c = network;
                            aVar.a(network);
                            r.this.e = false;
                        } else {
                            c.a("WifiNetworkUtils", "切换失败，未开启数据网络");
                            r.this.c = null;
                            aVar.a(null);
                            r.this.b.unregisterNetworkCallback(r.this.d);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        r.this.c = null;
                        aVar.a(null);
                    }
                }

                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onLost(Network network) {
                    r.this.e = true;
                }
            };
            this.b.requestNetwork(networkRequestBuild, this.d);
            return;
        }
        try {
            this.b.unregisterNetworkCallback(this.d);
        } catch (Exception e) {
            e.printStackTrace();
            this.d = null;
        }
        Log.e("HttpUtils", "clear: ");
        NetworkRequest networkRequestBuild2 = new NetworkRequest.Builder().addCapability(12).addTransportType(0).build();
        this.d = new ConnectivityManager.NetworkCallback() { // from class: com.mobile.auth.l.r.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                try {
                    if (r.this.b.getNetworkCapabilities(network).hasTransport(0)) {
                        r.this.c = network;
                        aVar.a(network);
                        r.this.e = false;
                    } else {
                        c.a("WifiNetworkUtils", "切换失败，未开启数据网络");
                        r.this.c = null;
                        aVar.a(null);
                        r.this.b.unregisterNetworkCallback(r.this.d);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    r.this.c = null;
                    aVar.a(null);
                }
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
                r.this.e = true;
            }
        };
        try {
            this.b.requestNetwork(networkRequestBuild2, this.d);
        } catch (Exception e2) {
            e2.printStackTrace();
            aVar.a(null);
        }
        return;
    }

    public boolean a() {
        return Build.VERSION.SDK_INT >= 21 && this.c != null;
    }

    public void b() {
        if (this.b == null) {
            return;
        }
        try {
            if (Build.VERSION.SDK_INT < 21 || this.d == null) {
                return;
            }
            this.b.unregisterNetworkCallback(this.d);
            this.d = null;
            this.c = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
