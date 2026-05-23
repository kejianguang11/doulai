package com.mobile.auth.gatewayauth.detectnet;

import android.content.Context;
import android.net.Network;
import android.os.Build;
import android.text.TextUtils;
import com.mobile.auth.c.j;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.utils.i;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public class e {
    private static volatile e a;
    private String g;
    private String h;
    private AtomicBoolean c = new AtomicBoolean(false);
    private int e = 3;
    private int f = 2;
    private ExecutorService b = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue(3), new ThreadPoolExecutor.CallerRunsPolicy());
    private ConcurrentHashMap<String, String> d = new ConcurrentHashMap<>();

    public e() {
        this.h = "eco.taobao.com";
        try {
            this.h = new URL("https://dypnsapi.aliyuncs.com/?").getHost();
        } catch (MalformedURLException unused) {
        }
    }

    static /* synthetic */ DetectResult a(e eVar, String str, DetectResult detectResult) {
        try {
            return eVar.b(str, detectResult);
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

    private DetectResult a(String str, DetectResult detectResult) {
        try {
            i.a("pingNet： ping检测top默认");
            c cVarA = a(str, this.h);
            detectResult.setTopConnected(cVarA.a());
            if (cVarA.a()) {
                detectResult.setTopWholeMS(cVarA.b());
            } else {
                detectResult.setTopWholeMS(0L);
            }
            return detectResult;
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

    public static e a() {
        try {
            if (a == null) {
                synchronized (e.class) {
                    if (a == null) {
                        a = new e();
                    }
                }
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

    static /* synthetic */ AtomicBoolean a(e eVar) {
        try {
            return eVar.c;
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

    /* JADX WARN: Removed duplicated region for block: B:19:0x0089 A[Catch: Throwable -> 0x008d, TRY_LEAVE, TryCatch #0 {Throwable -> 0x008d, blocks: (B:2:0x0000, B:4:0x002b, B:5:0x0034, B:7:0x0053, B:9:0x0061, B:11:0x0069, B:12:0x006d, B:17:0x0082, B:19:0x0089, B:13:0x0071, B:15:0x0079, B:16:0x007d, B:8:0x005a), top: B:27:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void a(Context context, final String str, final a aVar) {
        try {
            i.a("pingNet：检测 默认网络和蜂窝网络");
            final DetectResult detectResult = new DetectResult();
            detectResult.setRequestIds(this.d.keySet()).setSessionIds(this.d.values());
            a(str, detectResult);
            j jVar = new j();
            if (Build.VERSION.SDK_INT >= 21) {
                jVar.a(context, new j.a() { // from class: com.mobile.auth.gatewayauth.detectnet.e.2
                    @Override // com.mobile.auth.c.j.a
                    public void a() {
                        try {
                            i.a("pingNet：切换蜂窝网络超时！");
                            detectResult.setTopCellularConnected(false).setTopCellularWholeMS(0L).setCuCellularConnected(false).setCuCellularWholeMS(0L);
                            e.a(e.this).set(false);
                            if (aVar != null) {
                                aVar.a(detectResult);
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
                    public void a(int i, String str2, long j) {
                        try {
                            i.a("pingNet：切换蜂窝网络失败！");
                            detectResult.setTopCellularConnected(false).setTopCellularWholeMS(0L).setCuCellularConnected(false).setCuCellularWholeMS(0L);
                            e.a(e.this).set(false);
                            if (aVar != null) {
                                aVar.a(detectResult);
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
                    public void a(Network network, long j) {
                        try {
                            i.a("pingNet：切换蜂窝网络成功！");
                            e.a(e.this, str, detectResult);
                            e.b(e.this, str, detectResult);
                            e.a(e.this).set(false);
                            if (aVar != null) {
                                aVar.a(detectResult);
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
                return;
            }
            boolean zA = jVar.a(context, this.h);
            i.a("pingNet：系统5.0以下切换蜂窝网络是否成功：" + zA);
            if (zA) {
                b(str, detectResult);
                c(str, detectResult);
            } else {
                detectResult.setTopCellularConnected(false).setTopCellularWholeMS(0L);
            }
            if (!TextUtils.isEmpty(this.g) && jVar.a(context, this.g)) {
                c(str, detectResult);
                this.c.set(false);
                if (aVar == null) {
                    aVar.a(detectResult);
                    return;
                }
                return;
            }
            DetectResult cuCellularConnected = detectResult.setCuCellularConnected(false);
            cuCellularConnected.setCuCellularWholeMS(0L);
            this.c.set(false);
            if (aVar == null) {
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ void a(e eVar, Context context, String str, a aVar) {
        try {
            eVar.a(context, str, aVar);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    static /* synthetic */ DetectResult b(e eVar, String str, DetectResult detectResult) {
        try {
            return eVar.c(str, detectResult);
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

    private DetectResult b(String str, DetectResult detectResult) {
        try {
            i.a("pingNet： ping检测top蜂窝");
            c cVarA = a(str, this.h);
            detectResult.setTopCellularConnected(cVarA.a());
            if (cVarA.a()) {
                detectResult.setTopCellularWholeMS(cVarA.b());
            } else {
                detectResult.setTopCellularWholeMS(0L);
            }
            return detectResult;
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

    static /* synthetic */ ConcurrentHashMap b(e eVar) {
        try {
            return eVar.d;
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

    static /* synthetic */ DetectResult c(e eVar, String str, DetectResult detectResult) {
        try {
            return eVar.a(str, detectResult);
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

    private DetectResult c(String str, DetectResult detectResult) {
        try {
            i.a("pingNet： ping检测蜂窝");
            if (TextUtils.isEmpty(this.g)) {
                detectResult.setCuCellularConnected(false).setCuCellularWholeMS(0L);
            } else {
                c cVarA = a(str, this.g);
                detectResult.setCuCellularConnected(cVarA.a());
                if (cVarA.a()) {
                    detectResult.setCuCellularWholeMS(cVarA.b());
                } else {
                    detectResult.setCuCellularWholeMS(0L);
                }
            }
            return detectResult;
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

    public c a(String str, String str2) {
        try {
            i.a("pingNet：" + str + " ping start");
            c cVar = new c();
            int i = 0;
            while (i < this.f) {
                cVar = d.a(str2, this.e);
                i.a("pingNet：第" + i + "次 result：" + cVar.toString());
                i++;
                if (cVar.a()) {
                    break;
                }
            }
            i.a("pingNet：" + str + " ping stop");
            i.a("pingNet：" + str + " 总共 ping了:" + i + "次");
            return cVar;
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

    public e a(String str) {
        try {
            this.g = str;
            return this;
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

    public void a(final Context context, final String str, final String str2, final a aVar) {
        try {
            i.a("pingNet：" + str + " startDetect");
            this.b.execute(new Runnable() { // from class: com.mobile.auth.gatewayauth.detectnet.e.1
                @Override // java.lang.Runnable
                public void run() {
                    DetectResult detectResult;
                    a aVar2;
                    try {
                        if (!e.a(e.this).compareAndSet(false, true)) {
                            i.a("pingNet：" + str + " 已经有检测在实施：" + e.b(e.this).toString());
                            e.b(e.this).put(str, str2);
                            return;
                        }
                        e.b(e.this).clear();
                        e.b(e.this).put(str, str2);
                        if (com.mobile.auth.gatewayauth.utils.c.i(context)) {
                            i.a("pingNet：纯蜂窝网络连接");
                            DetectResult detectResult2 = new DetectResult();
                            detectResult2.setRequestIds(e.b(e.this).keySet()).setSessionIds(e.b(e.this).values());
                            detectResult = e.b(e.this, str, e.a(e.this, str, detectResult2));
                            detectResult.setTopConnected(false).setTopWholeMS(0L);
                            e.a(e.this).set(false);
                            if (aVar == null) {
                                return;
                            } else {
                                aVar2 = aVar;
                            }
                        } else {
                            if (com.mobile.auth.gatewayauth.utils.c.e(context)) {
                                i.a("pingNet：wifi+蜂窝网络");
                                e.a(e.this, context, str, aVar);
                                return;
                            }
                            i.a("pingNet：纯wifi");
                            detectResult = new DetectResult();
                            detectResult.setRequestIds(e.b(e.this).keySet()).setSessionIds(e.b(e.this).values());
                            e.c(e.this, str, detectResult);
                            detectResult.setTopCellularConnected(false).setTopCellularWholeMS(0L).setCuCellularConnected(false).setCuCellularWholeMS(0L);
                            e.a(e.this).set(false);
                            if (aVar == null) {
                                return;
                            } else {
                                aVar2 = aVar;
                            }
                        }
                        aVar2.a(detectResult);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
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
}
