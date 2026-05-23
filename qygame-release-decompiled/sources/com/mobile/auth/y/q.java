package com.mobile.auth.y;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.text.TextUtils;
import com.gme.trtc.hardwareearmonitor.honor.HonorResultCode;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
public class q {
    private static q f;
    private Network a = null;
    private ConnectivityManager.NetworkCallback b = null;
    private ConnectivityManager c = null;
    private List<a> d = new ArrayList();
    private Timer e = null;

    public interface a {
        void a(boolean z, Object obj);
    }

    private q() {
    }

    static /* synthetic */ Network a(q qVar) {
        try {
            return qVar.a;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    static /* synthetic */ Network a(q qVar, Network network) {
        try {
            qVar.a = network;
            return network;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static q a() {
        try {
            if (f == null) {
                synchronized (q.class) {
                    if (f == null) {
                        f = new q();
                    }
                }
            }
            return f;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    private synchronized void a(a aVar) {
        try {
            try {
                this.d.add(aVar);
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        } catch (Exception unused) {
            t.b();
        }
    }

    static /* synthetic */ void a(q qVar, boolean z, Network network) {
        try {
            qVar.a(z, network);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    private synchronized void a(boolean z, Network network) {
        try {
            try {
                if (this.e != null) {
                    this.e.cancel();
                    this.e = null;
                }
                Iterator<a> it = this.d.iterator();
                while (it.hasNext()) {
                    it.next().a(z, network);
                }
                this.d.clear();
            } catch (Exception unused) {
                t.b();
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    static /* synthetic */ ConnectivityManager b(q qVar) {
        try {
            return qVar.c;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @TargetApi(21)
    public final synchronized void a(Context context, a aVar) {
        try {
            if (this.a != null) {
                aVar.a(true, this.a);
                return;
            }
            a(aVar);
            if (this.b == null || this.d.size() < 2) {
                try {
                    this.c = (ConnectivityManager) context.getSystemService("connectivity");
                    NetworkRequest.Builder builder = new NetworkRequest.Builder();
                    builder.addTransportType(0);
                    builder.addCapability(12);
                    NetworkRequest networkRequestBuild = builder.build();
                    this.b = new ConnectivityManager.NetworkCallback() { // from class: com.mobile.auth.y.q.1
                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public final void onAvailable(Network network) {
                            try {
                                super.onAvailable(network);
                                t.c("Network onAvailable");
                                q.a(q.this, network);
                                q.a(q.this, true, network);
                                try {
                                    String extraInfo = q.b(q.this).getNetworkInfo(q.a(q.this)).getExtraInfo();
                                    if (TextUtils.isEmpty(extraInfo)) {
                                        return;
                                    }
                                    u.d(extraInfo);
                                } catch (Exception unused) {
                                    t.b();
                                }
                            } catch (Throwable th) {
                                ExceptionProcessor.processException(th);
                            }
                        }

                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public final void onLost(Network network) {
                            try {
                                super.onLost(network);
                                t.c("Network onLost");
                                q.this.b();
                            } catch (Throwable th) {
                                ExceptionProcessor.processException(th);
                            }
                        }

                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public final void onUnavailable() {
                            try {
                                super.onUnavailable();
                                t.c("Network onUnavailable");
                                q.a(q.this, false, null);
                                q.this.b();
                            } catch (Throwable th) {
                                ExceptionProcessor.processException(th);
                            }
                        }
                    };
                    int i = 3000;
                    if (u.g() < 3000) {
                        i = HonorResultCode.ADVANCED_RECORD_SUCCESS;
                    }
                    if (Build.VERSION.SDK_INT >= 26) {
                        this.c.requestNetwork(networkRequestBuild, this.b, i);
                        return;
                    }
                    this.e = new Timer();
                    this.e.schedule(new TimerTask() { // from class: com.mobile.auth.y.q.2
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public final void run() {
                            try {
                                q.a(q.this, false, null);
                            } catch (Throwable th) {
                                ExceptionProcessor.processException(th);
                            }
                        }
                    }, i);
                    this.c.requestNetwork(networkRequestBuild, this.b);
                } catch (Exception unused) {
                    t.b();
                    a(false, (Network) null);
                }
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public final synchronized void b() {
        try {
            try {
                if (this.e != null) {
                    this.e.cancel();
                    this.e = null;
                }
                if (Build.VERSION.SDK_INT >= 21 && this.c != null && this.b != null) {
                    this.c.unregisterNetworkCallback(this.b);
                }
                this.c = null;
                this.b = null;
                this.a = null;
                this.d.clear();
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
            }
        } catch (Exception unused) {
            t.b();
        }
    }
}
