package com.mobile.auth.o;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.PnsLoggerHandler;
import com.mobile.auth.gatewayauth.model.ConfigRule;
import com.mobile.auth.gatewayauth.model.DowngradeConfig;
import com.mobile.auth.gatewayauth.model.DowngradeInfoList;
import com.mobile.auth.gatewayauth.model.pns_vendor_query.LimitedInfo;
import com.mobile.auth.gatewayauth.model.pns_vendor_query.UploadRB;
import com.mobile.auth.gatewayauth.utils.i;
import com.mobile.auth.w.b;
import com.mobile.auth.w.c;
import com.nirvana.tools.logger.ACMMonitor;
import com.nirvana.tools.logger.UaidTracker;
import com.nirvana.tools.logger.model.ACMLimitConfig;
import com.nirvana.tools.logger.model.ACMMonitorRecord;
import com.nirvana.tools.logger.utils.ConsoleLogUtils;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private static volatile a a;
    private ACMMonitor b;
    private boolean c;
    private boolean d;
    private com.mobile.auth.w.a e;
    private HandlerThread f;
    private Handler g;
    private volatile PnsLoggerHandler h;
    private List<ACMMonitorRecord> i;

    private a() {
        this.c = false;
        this.d = false;
        this.f = null;
        this.g = null;
        this.i = new ArrayList();
        this.f = new HandlerThread("PnsLoggerThread");
        this.f.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: com.mobile.auth.o.a.1
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public void uncaughtException(Thread thread, Throwable th) {
            }
        });
        this.f.start();
        this.g = new Handler(this.f.getLooper());
    }

    public a(final Context context) {
        this();
        this.g.post(new Runnable() { // from class: com.mobile.auth.o.a.9
            @Override // java.lang.Runnable
            public void run() {
                try {
                    a.f();
                    b bVar = new b();
                    a.a(a.this, new c());
                    a.a(a.this).a(bVar);
                    a.a(a.this, new ACMMonitor(context, a.a(a.this)));
                    a.b(a.this).setUploadType(1);
                    a.b(a.this).setMaxUploadRetry(1L);
                    a.b(a.this).setMaxUploadSize(100);
                    a.b(a.this).setRetryCount(0);
                    ConsoleLogUtils.setLoggerEnable(true);
                } catch (Throwable th) {
                    try {
                        ExceptionProcessor.processException(th);
                    } catch (Throwable th2) {
                        ExceptionProcessor.processException(th2);
                    }
                }
            }
        });
    }

    public static a a(Context context) {
        try {
            if (a == null) {
                synchronized (a.class) {
                    if (a == null && context != null) {
                        a = new a(context);
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

    static /* synthetic */ com.mobile.auth.w.a a(a aVar) {
        try {
            return aVar.e;
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

    static /* synthetic */ com.mobile.auth.w.a a(a aVar, com.mobile.auth.w.a aVar2) {
        try {
            aVar.e = aVar2;
            return aVar2;
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

    static /* synthetic */ ACMMonitor a(a aVar, ACMMonitor aCMMonitor) {
        try {
            aVar.b = aCMMonitor;
            return aCMMonitor;
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

    static /* synthetic */ String a(a aVar, String[] strArr) {
        try {
            return aVar.f(strArr);
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

    static /* synthetic */ List a(a aVar, List list) {
        try {
            aVar.i = list;
            return list;
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

    static /* synthetic */ boolean a(a aVar, boolean z) {
        try {
            aVar.d = z;
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

    static /* synthetic */ ACMMonitor b(a aVar) {
        try {
            return aVar.b;
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

    static /* synthetic */ boolean b(a aVar, boolean z) {
        try {
            aVar.c = z;
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

    static /* synthetic */ PnsLoggerHandler c(a aVar) {
        try {
            return aVar.h;
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

    static /* synthetic */ boolean d(a aVar) {
        try {
            return aVar.c;
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

    static /* synthetic */ List e(a aVar) {
        try {
            return aVar.i;
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

    private String f(String... strArr) {
        if (strArr != null) {
            try {
                if (strArr.length != 0) {
                    if (strArr.length == 1) {
                        return strArr[0];
                    }
                    StringBuilder sb = new StringBuilder();
                    for (String str : strArr) {
                        sb.append(str);
                    }
                    return sb.toString();
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return null;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
        return null;
    }

    static /* synthetic */ boolean f() {
        try {
            return g();
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

    private static boolean g() {
        try {
            return Class.forName("com.nirvana.tools.logger.ACMMonitor") != null;
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

    public void a(PnsLoggerHandler pnsLoggerHandler) {
        try {
            this.h = pnsLoggerHandler;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(final ConfigRule configRule) {
        try {
            this.g.post(new Runnable() { // from class: com.mobile.auth.o.a.3
                @Override // java.lang.Runnable
                public void run() {
                    UaidTracker uaidTracker;
                    try {
                        if (configRule != null && configRule.getSls() != null) {
                            ACMLimitConfig aCMLimitConfigBuild = ACMLimitConfig.newACMLimitConfig().isLimited("true".equals(configRule.getSls().getIs_limited())).limitCount(configRule.getSls().getLimit_count()).limitHours(configRule.getSls().getLimit_time_hour()).build();
                            DowngradeInfoList downgradeInfoList = configRule.getDowngradeInfoList();
                            if (downgradeInfoList == null) {
                                a.a(a.this, false);
                                if (a.b(a.this) != null) {
                                    uaidTracker = UaidTracker.getInstance();
                                    uaidTracker.setEnable(false);
                                }
                            } else {
                                List<DowngradeConfig> downgradeInfo = downgradeInfoList.getDowngradeInfo();
                                if (downgradeInfo == null || downgradeInfo.isEmpty()) {
                                    a.a(a.this, false);
                                    if (a.b(a.this) != null) {
                                        uaidTracker = UaidTracker.getInstance();
                                        uaidTracker.setEnable(false);
                                    }
                                } else {
                                    a.a(a.this, false);
                                    if (a.b(a.this) != null) {
                                        UaidTracker.getInstance().setEnable(false);
                                    }
                                    for (DowngradeConfig downgradeConfig : downgradeInfo) {
                                        if ("is_network_test_opened".equals(downgradeConfig.getDowngrade_name()) && downgradeConfig.isDowngrade()) {
                                            a.a(a.this, true);
                                        }
                                        if ("is_uaid_tracker_opened".equals(downgradeConfig.getDowngrade_name()) && downgradeConfig.isDowngrade() && a.b(a.this) != null) {
                                            UaidTracker.getInstance().setEnable(true);
                                        }
                                    }
                                }
                            }
                            if (a.b(a.this) != null) {
                                if ("true".equals(configRule.getIs_sls_demoted()) || "true".equals(configRule.getIs_demoted())) {
                                    a.b(a.this, true);
                                } else {
                                    a.b(a.this, false);
                                }
                                if (a.d(a.this)) {
                                    a.b(a.this).setUploadEnabled(false);
                                } else {
                                    a.b(a.this).setUploadEnabled(true);
                                }
                                a.b(a.this).setLimitConfig(aCMLimitConfigBuild);
                            }
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
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(final UploadRB uploadRB) {
        try {
            this.g.post(new Runnable() { // from class: com.mobile.auth.o.a.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (uploadRB == null || uploadRB.getAlibaba_aliqin_psc_info_upload_response() == null || uploadRB.getAlibaba_aliqin_psc_info_upload_response().getResult() == null || uploadRB.getAlibaba_aliqin_psc_info_upload_response().getResult().getModule() == null || uploadRB.getAlibaba_aliqin_psc_info_upload_response().getResult().getModule().getLimited_info() == null || a.b(a.this) == null) {
                            return;
                        }
                        LimitedInfo limited_info = uploadRB.getAlibaba_aliqin_psc_info_upload_response().getResult().getModule().getLimited_info();
                        a.b(a.this).setLimitConfig(ACMLimitConfig.newACMLimitConfig().isLimited("true".equals(limited_info.getIs_limited())).limitCount(limited_info.getLimit_count()).limitHours(limited_info.getLimit_time_hour()).build());
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

    public void a(final String str, final int i) {
        try {
            this.g.post(new Runnable() { // from class: com.mobile.auth.o.a.8
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        PnsLoggerHandler pnsLoggerHandlerC = a.c(a.this);
                        if (pnsLoggerHandlerC != null) {
                            pnsLoggerHandlerC.monitor(str);
                        }
                        a.this.b("CacheMonitor:", str, "\n Urgency ", String.valueOf(i));
                        if (a.d(a.this) || a.b(a.this) == null) {
                            return;
                        }
                        if (a.e(a.this) == null) {
                            a.a(a.this, new ArrayList());
                        }
                        ACMMonitorRecord aCMMonitorRecord = new ACMMonitorRecord(i);
                        aCMMonitorRecord.setContent(str);
                        a.e(a.this).add(aCMMonitorRecord);
                        if (a.e(a.this).size() >= 5) {
                            a.b(a.this).monitorRecords(a.e(a.this));
                            a.e(a.this).clear();
                            Log.d("CacheMonitor", "cache and clear");
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
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(final String... strArr) {
        try {
            this.g.post(new Runnable() { // from class: com.mobile.auth.o.a.10
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        String strA = a.a(a.this, strArr);
                        PnsLoggerHandler pnsLoggerHandlerC = a.c(a.this);
                        if (pnsLoggerHandlerC != null) {
                            pnsLoggerHandlerC.info(strA);
                        }
                        i.d(strA);
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

    public boolean a() {
        try {
            return this.d;
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

    public void b() {
        try {
            this.g.post(new Runnable() { // from class: com.mobile.auth.o.a.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (a.d(a.this) || a.b(a.this) == null) {
                            return;
                        }
                        if (a.e(a.this) != null && a.e(a.this).size() > 0) {
                            a.b(a.this).monitorRecords(a.e(a.this));
                            a.e(a.this).clear();
                            a.this.b("CacheMonitor:", "uploadMonitor and clear");
                        }
                        a.b(a.this).uploadManual();
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

    public void b(final String... strArr) {
        try {
            this.g.post(new Runnable() { // from class: com.mobile.auth.o.a.11
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        String strA = a.a(a.this, strArr);
                        PnsLoggerHandler pnsLoggerHandlerC = a.c(a.this);
                        if (pnsLoggerHandlerC != null) {
                            pnsLoggerHandlerC.debug(strA);
                        }
                        i.a(strA);
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

    public void c() {
        try {
            this.g.post(new Runnable() { // from class: com.mobile.auth.o.a.5
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (a.d(a.this) || a.b(a.this) == null) {
                            return;
                        }
                        if (a.e(a.this) != null && a.e(a.this).size() > 0) {
                            a.b(a.this).monitorRecords(a.e(a.this));
                            a.e(a.this).clear();
                            a.this.b("CacheMonitor:", "uploadFailedMonitor and clear");
                        }
                        a.b(a.this).uploadFailed();
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

    public void c(final String... strArr) {
        try {
            this.g.post(new Runnable() { // from class: com.mobile.auth.o.a.12
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        String strA = a.a(a.this, strArr);
                        PnsLoggerHandler pnsLoggerHandlerC = a.c(a.this);
                        if (pnsLoggerHandlerC != null) {
                            pnsLoggerHandlerC.warning(strA);
                        }
                        i.b(strA);
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

    public void d() {
        try {
            this.g.post(new Runnable() { // from class: com.mobile.auth.o.a.6
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        a.this.b("deleteMonitor:", "delete unupload Monitor");
                        if (a.b(a.this) != null) {
                            a.b(a.this).deleteRecordsByFlag(2);
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
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void d(final String... strArr) {
        try {
            this.g.post(new Runnable() { // from class: com.mobile.auth.o.a.13
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        i.b(a.a(a.this, strArr));
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

    public void e() {
        try {
            this.g.post(new Runnable() { // from class: com.mobile.auth.o.a.7
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        a.this.b("deleteMonitor:", "delete unupload Monitor");
                        if (a.b(a.this) != null) {
                            a.b(a.this).deleteRecordsByFlag(-1);
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
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void e(final String... strArr) {
        try {
            this.g.post(new Runnable() { // from class: com.mobile.auth.o.a.14
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        String strA = a.a(a.this, strArr);
                        PnsLoggerHandler pnsLoggerHandlerC = a.c(a.this);
                        if (pnsLoggerHandlerC != null) {
                            pnsLoggerHandlerC.error(strA);
                        }
                        i.c(strA);
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
