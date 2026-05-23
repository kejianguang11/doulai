package com.mobile.auth.c;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.net.InetAddress;

/* JADX INFO: loaded from: classes.dex */
public class j {
    private static final String a = "j";
    private a e;
    private boolean b = false;
    private ConnectivityManager c = null;
    private ConnectivityManager.NetworkCallback d = null;
    private long f = 0;
    private long g = 0;

    public interface a {
        void a();

        void a(int i, String str, long j);

        void a(Network network, long j);
    }

    public static int a(String str) {
        try {
            try {
                byte[] address = InetAddress.getByName(str).getAddress();
                return (address[0] & 255) | ((address[3] & 255) << 24) | ((address[2] & 255) << 16) | ((address[1] & 255) << 8);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return -1;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return -1;
                }
            }
        } catch (Throwable th3) {
            com.mobile.auth.a.a.a(a, "When InetAddress.getByName(),throws exception", th3);
            return -1;
        }
    }

    static /* synthetic */ long a(j jVar, long j) {
        try {
            jVar.f = j;
            return j;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1L;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1L;
            }
        }
    }

    static /* synthetic */ ConnectivityManager a(j jVar, ConnectivityManager connectivityManager) {
        try {
            jVar.c = connectivityManager;
            return connectivityManager;
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

    @TargetApi(21)
    private void a(Context context) {
        try {
            this.f = 0L;
            this.c = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
            this.g = System.currentTimeMillis();
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            builder.addCapability(12);
            builder.addTransportType(0);
            NetworkRequest networkRequestBuild = builder.build();
            this.d = new ConnectivityManager.NetworkCallback() { // from class: com.mobile.auth.c.j.2
                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onAvailable(Network network) {
                    try {
                        j.a(j.this, System.currentTimeMillis() - j.d(j.this));
                        j.a(j.this, true);
                        if (j.b(j.this) != null) {
                            j.b(j.this).a(network, j.e(j.this));
                        }
                        if (j.f(j.this) != null) {
                            try {
                                j.f(j.this).unregisterNetworkCallback(this);
                                j.a(j.this, (ConnectivityManager) null);
                            } catch (Throwable th) {
                                com.mobile.auth.a.a.a(j.a(), "switchToMobileForAboveL", th);
                            }
                        }
                    } catch (Throwable th2) {
                        try {
                            ExceptionProcessor.processException(th2);
                        } catch (Throwable th3) {
                            ExceptionProcessor.processException(th3);
                        }
                    }
                }
            };
            this.c.requestNetwork(networkRequestBuild, this.d);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ boolean a(j jVar) {
        try {
            return jVar.b;
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

    static /* synthetic */ boolean a(j jVar, boolean z) {
        try {
            jVar.b = z;
            return z;
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

    static /* synthetic */ a b(j jVar) {
        try {
            return jVar.e;
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

    public static String b(String str) {
        try {
            int iIndexOf = str.indexOf("://");
            if (iIndexOf > 0) {
                str = str.substring(iIndexOf + 3);
            }
            int iIndexOf2 = str.indexOf(58);
            if (iIndexOf2 >= 0) {
                str = str.substring(0, iIndexOf2);
            }
            int iIndexOf3 = str.indexOf(47);
            if (iIndexOf3 >= 0) {
                str = str.substring(0, iIndexOf3);
            }
            int iIndexOf4 = str.indexOf(63);
            return iIndexOf4 >= 0 ? str.substring(0, iIndexOf4) : str;
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

    private void b() {
        try {
            if (Build.VERSION.SDK_INT < 21 || this.c == null || this.d == null) {
                return;
            }
            try {
                this.c.unregisterNetworkCallback(this.d);
            } catch (Throwable th) {
                com.mobile.auth.a.a.a(a, "unregisterNetworkCallback", th);
            }
            this.c = null;
        } catch (Throwable th2) {
            try {
                ExceptionProcessor.processException(th2);
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
            }
        }
    }

    private boolean b(Context context, String str) {
        boolean zBooleanValue;
        try {
            Class<?> cls = Class.forName("android.net.ConnectivityManager");
            this.f = 0L;
            this.g = System.currentTimeMillis();
            this.c = (ConnectivityManager) context.getSystemService("connectivity");
            if (this.c.getNetworkInfo(5).getState().compareTo(NetworkInfo.State.CONNECTED) != 0) {
                cls.getMethod("startUsingNetworkFeature", Integer.TYPE, String.class).invoke(this.c, 0, "enableHIPRI");
                for (int i = 0; i < 5; i++) {
                    try {
                        if (this.c.getNetworkInfo(5).getState().compareTo(NetworkInfo.State.CONNECTED) == 0) {
                            break;
                        }
                        Thread.sleep(500L);
                    } catch (Throwable th) {
                        com.mobile.auth.a.a.a(a, "switchToMobileForUnderL", th);
                    }
                }
            }
            zBooleanValue = ((Boolean) cls.getMethod("requestRouteToHost", Integer.TYPE, Integer.TYPE).invoke(this.c, 5, Integer.valueOf(a(b(str))))).booleanValue();
            try {
                this.f = System.currentTimeMillis() - this.g;
                com.mobile.auth.a.a.a(a, "Switch network result ： " + zBooleanValue + " (4.x) , expendTime ：" + this.f);
            } catch (Throwable th2) {
                th = th2;
                try {
                    com.mobile.auth.a.a.a(a, "4.x网络切换异常", th);
                } catch (Throwable th3) {
                    try {
                        ExceptionProcessor.processException(th3);
                        return false;
                    } catch (Throwable th4) {
                        ExceptionProcessor.processException(th4);
                        return false;
                    }
                }
            }
        } catch (Throwable th5) {
            th = th5;
            zBooleanValue = false;
            com.mobile.auth.a.a.a(a, "4.x网络切换异常", th);
            return zBooleanValue;
        }
        return zBooleanValue;
    }

    static /* synthetic */ void c(j jVar) {
        try {
            jVar.b();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ long d(j jVar) {
        try {
            return jVar.g;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1L;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1L;
            }
        }
    }

    static /* synthetic */ long e(j jVar) {
        try {
            return jVar.f;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1L;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1L;
            }
        }
    }

    static /* synthetic */ ConnectivityManager f(j jVar) {
        try {
            return jVar.c;
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

    public void a(final int i) {
        try {
            r.a().a(new Runnable() { // from class: com.mobile.auth.c.j.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (i > 2500) {
                            try {
                                Thread.sleep(2500L);
                            } catch (Throwable th) {
                                com.mobile.auth.a.a.a(j.a(), "timeoutCheckRunnable exception!", th);
                            }
                            if (!j.a(j.this)) {
                                if (j.b(j.this) != null) {
                                    j.b(j.this).a(80800, "WIFI切换超时", 2500L);
                                }
                                com.mobile.auth.a.a.a(j.a(), "切换网络超时(L)");
                                j.c(j.this);
                                return;
                            }
                        }
                        try {
                            Thread.sleep(i <= 2500 ? i : i - com.igexin.push.c.b.b);
                        } catch (Throwable th2) {
                            com.mobile.auth.a.a.a(j.a(), "timeoutCheckRunnable exception!", th2);
                        }
                        if (j.b(j.this) != null) {
                            if (j.a(j.this)) {
                                j.b(j.this).a();
                            } else {
                                j.b(j.this).a(80800, "WIFI切换超时", 2500L);
                                j.c(j.this);
                            }
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

    public void a(Context context, a aVar) {
        try {
            this.e = aVar;
            try {
                a(context);
            } catch (Throwable th) {
                com.mobile.auth.a.a.a(a, "switchToMobileForAboveL", th);
                if (this.e != null) {
                    this.e.a(80801, "WIFI切换异常", -1L);
                }
            }
        } catch (Throwable th2) {
            try {
                ExceptionProcessor.processException(th2);
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
            }
        }
    }

    public boolean a(Context context, String str) {
        try {
            return b(context, str);
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
}
