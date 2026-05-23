package com.loc;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.text.TextUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.gme.liteav.TXLiteAVCode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class e {
    static boolean g = false;
    Context e;
    private List<Messenger> w;
    private boolean o = false;
    private boolean p = false;
    String a = null;
    b b = null;
    private long q = 0;
    private long r = 0;
    private eo s = null;
    AMapLocation c = null;
    private long t = 0;
    private int u = 0;
    a d = null;
    private j v = null;
    ej f = null;
    HashMap<Messenger, Long> h = new HashMap<>();
    fo i = null;
    long j = 0;
    long k = 0;
    String l = null;
    private boolean x = true;
    private String y = "";
    AMapLocationClientOption m = null;
    AMapLocationClientOption n = new AMapLocationClientOption();

    public class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Messenger messenger;
            Throwable th;
            Bundle data;
            e eVar;
            String str;
            try {
                data = message.getData();
                try {
                    messenger = message.replyTo;
                    if (data != null) {
                        try {
                            if (!data.isEmpty()) {
                                if (!e.this.a(data.getString(com.igexin.push.core.d.d.d))) {
                                    if (message.what == 1) {
                                        fo.a((String) null, TXLiteAVCode.WARNING_AUDIO_FRAME_DECODE_FAIL);
                                        eo eoVarB = e.b("invalid handlder scode!!!#1002");
                                        ei eiVar = new ei();
                                        eiVar.f("#1002");
                                        eiVar.e("conitue");
                                        e.this.a(messenger, eoVarB, eoVarB.k(), eiVar);
                                        return;
                                    }
                                    return;
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            try {
                                fj.a(th, "ApsServiceCore", "ActionHandler handlerMessage");
                            } catch (Throwable th3) {
                                fj.a(th3, "actionHandler", "handleMessage");
                                return;
                            }
                        }
                    }
                } catch (Throwable th4) {
                    messenger = null;
                    th = th4;
                }
            } catch (Throwable th5) {
                messenger = null;
                th = th5;
                data = null;
            }
            int i = message.what;
            switch (i) {
                case 0:
                    e.this.a(data);
                    e.this.a(messenger, data);
                    break;
                case 1:
                    e.this.a(data);
                    e.this.b(messenger, data);
                    break;
                default:
                    switch (i) {
                        case 9:
                            e.this.a(data);
                            e.this.f();
                            break;
                        case 10:
                            e.this.a(data);
                            eVar = e.this;
                            str = "FINE_LOC";
                            eVar.a(messenger, data, str);
                            break;
                        case 11:
                            e.this.c();
                            break;
                        case 12:
                            e.this.a(messenger);
                            break;
                        case 13:
                            Messenger messenger2 = message.replyTo;
                            if (messenger2 != null && e.this.w != null && !e.this.w.contains(messenger2)) {
                                e.this.w.add(messenger2);
                                if (e.this.w.size() == 1) {
                                    e.this.e();
                                }
                            }
                            break;
                        case 14:
                            Messenger messenger3 = message.replyTo;
                            if (messenger3 != null && e.this.w != null && e.this.w.contains(messenger3)) {
                                e.this.w.remove(messenger3);
                            }
                            if (e.this.w != null && e.this.w.size() == 0) {
                                e.this.f.h();
                            }
                            break;
                        case 15:
                            e.this.a(data);
                            eVar = e.this;
                            str = "COARSE_LOC";
                            eVar.a(messenger, data, str);
                            break;
                    }
                    break;
            }
            super.handleMessage(message);
        }
    }

    class b extends HandlerThread {
        public b(String str) {
            super(str);
        }

        @Override // android.os.HandlerThread
        protected final void onLooperPrepared() {
            try {
                try {
                    e.this.v = new j(e.this.e);
                } catch (Throwable th) {
                    fj.a(th, "APSManager$ActionThread", "init 2");
                }
                try {
                    fi.b(e.this.e);
                    fi.a(e.this.e);
                } catch (Throwable th2) {
                    fj.a(th2, "APSManager$ActionThread", "init 3");
                }
                e.this.f = new ej(false);
                super.onLooperPrepared();
            } catch (Throwable th3) {
                fj.a(th3, "APSManager$ActionThread", "onLooperPrepared");
            }
        }

        @Override // android.os.HandlerThread, java.lang.Thread, java.lang.Runnable
        public final void run() {
            try {
                super.run();
            } catch (Throwable th) {
                fj.a(th, "APSManager$ActionThread", "run");
            }
        }
    }

    public e(Context context) {
        this.e = null;
        this.e = context;
    }

    private static eo a(int i, String str) {
        try {
            eo eoVar = new eo("");
            eoVar.setErrorCode(i);
            eoVar.setLocationDetail(str);
            return eoVar;
        } catch (Throwable th) {
            fj.a(th, "ApsServiceCore", "newInstanceAMapLoc");
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Bundle bundle) {
        try {
            if (this.o) {
                if (this.f != null) {
                    this.f.a();
                    return;
                }
                return;
            }
            fj.a(this.e);
            if (bundle != null) {
                this.n = fj.a(bundle.getBundle("optBundle"));
            }
            this.f.a(this.e);
            this.f.b();
            a(this.n);
            this.f.c();
            this.o = true;
            this.x = true;
            this.y = "";
            if (this.w == null || this.w.size() <= 0) {
                return;
            }
            e();
        } catch (Throwable th) {
            this.x = false;
            th.printStackTrace();
            this.y = th.getMessage();
            fj.a(th, "ApsServiceCore", "init");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Messenger messenger) {
        this.h.remove(messenger);
    }

    private static void a(Messenger messenger, int i, Bundle bundle) {
        if (messenger != null) {
            try {
                Message messageObtain = Message.obtain();
                messageObtain.setData(bundle);
                messageObtain.what = i;
                messenger.send(messageObtain);
            } catch (Throwable th) {
                fj.a(th, "ApsServiceCore", "sendMessage");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Messenger messenger, Bundle bundle) {
        if (bundle != null) {
            try {
                if (bundle.isEmpty() || this.p) {
                    return;
                }
                this.p = true;
                b(messenger);
            } catch (Throwable th) {
                fj.a(th, "ApsServiceCore", "doInitAuth");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Messenger messenger, AMapLocation aMapLocation, String str, ei eiVar) {
        Bundle bundle = new Bundle();
        bundle.setClassLoader(AMapLocation.class.getClassLoader());
        bundle.putParcelable("loc", aMapLocation);
        bundle.putString("nb", str);
        bundle.putParcelable("statics", eiVar);
        this.h.put(messenger, Long.valueOf(fq.b()));
        a(messenger, 1, bundle);
    }

    private void a(Messenger messenger, String str) {
        Bundle bundle = new Bundle();
        bundle.setClassLoader(AMapLocation.class.getClassLoader());
        bundle.putInt("I_MAX_GEO_DIS", fi.i() * 3);
        bundle.putInt("I_MIN_GEO_DIS", fi.i());
        bundle.putParcelable("loc", this.c);
        a(messenger, "COARSE_LOC".equals(str) ? 103 : 6, bundle);
    }

    private void a(AMapLocationClientOption aMapLocationClientOption) {
        try {
            if (this.f != null) {
                this.f.a(aMapLocationClientOption);
            }
            if (aMapLocationClientOption != null) {
                g = aMapLocationClientOption.isKillProcess();
                if (this.m != null) {
                    if (aMapLocationClientOption.isOffset() != this.m.isOffset() || aMapLocationClientOption.isNeedAddress() != this.m.isNeedAddress() || aMapLocationClientOption.isLocationCacheEnable() != this.m.isLocationCacheEnable() || this.m.getGeoLanguage() != aMapLocationClientOption.getGeoLanguage()) {
                        this.r = 0L;
                    }
                    if (aMapLocationClientOption.isOffset() != this.m.isOffset() || this.m.getGeoLanguage() != aMapLocationClientOption.getGeoLanguage()) {
                        this.c = null;
                    }
                }
                this.m = aMapLocationClientOption;
            }
        } catch (Throwable th) {
            fj.a(th, "ApsServiceCore", "setExtra");
        }
    }

    private static AMapLocationClientOption b(Bundle bundle) {
        AMapLocationClientOption aMapLocationClientOptionA;
        try {
            aMapLocationClientOptionA = fj.a(bundle.getBundle("optBundle"));
            try {
                String string = bundle.getString("d");
                if (!TextUtils.isEmpty(string)) {
                    o.a(string);
                }
            } catch (Throwable th) {
                try {
                    fj.a(th, "APSManager", "doLocation setUmidToken");
                } catch (Throwable th2) {
                    th = th2;
                    fj.a(th, "APSManager", "parseBundle");
                }
            }
        } catch (Throwable th3) {
            th = th3;
            aMapLocationClientOptionA = null;
        }
        return aMapLocationClientOptionA;
    }

    static /* synthetic */ eo b(String str) {
        return a(10, str);
    }

    private void b(Messenger messenger) {
        try {
            this.f.f();
            if (fi.k()) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("installMockApp", true);
                a(messenger, 9, bundle);
            }
        } catch (Throwable th) {
            fj.a(th, "ApsServiceCore", "initAuth");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Messenger messenger, Bundle bundle) {
        AMapLocation aMapLocationA;
        if (bundle != null) {
            try {
                if (bundle.isEmpty()) {
                    return;
                }
                ei eiVar = new ei();
                eiVar.e("conitue");
                AMapLocationClientOption aMapLocationClientOptionB = b(bundle);
                a(aMapLocationClientOptionB);
                if (this.h.containsKey(messenger) && !aMapLocationClientOptionB.isOnceLocation()) {
                    if (fq.b() - this.h.get(messenger).longValue() < 800) {
                        return;
                    }
                }
                String strK = null;
                if (!this.x) {
                    this.s = a(9, "init error : " + this.y + "#0901");
                    eiVar.f("#0901");
                    a(messenger, this.s, this.s.k(), eiVar);
                    fo.a((String) null, 2091);
                    return;
                }
                long jB = fq.b();
                if (fq.a(this.s) && jB - this.r < 600) {
                    a(messenger, this.s, this.s.k(), eiVar);
                    this.f.a(this.s, 3);
                    return;
                }
                eiVar.c(fq.b());
                try {
                    this.s = this.f.a(eiVar);
                    if (this.s.getLocationType() == 6 || this.s.getLocationType() == 5) {
                        this.f.a(this.s, 2);
                    } else if (this.s.getLocationType() == 2) {
                        this.f.a(this.s, 3);
                    } else if (this.s.getLocationType() == 4) {
                        this.f.a(this.s, 4);
                    }
                    String[] strArr = new String[0];
                    this.s = this.f.a(this.s);
                } catch (Throwable th) {
                    fo.a((String) null, 2081);
                    eiVar.f("#0801");
                    this.s = a(8, "loc error : " + th.getMessage() + "#0801");
                    fj.a(th, "ApsServiceCore", "run part2");
                }
                if (fq.a(this.s)) {
                    this.r = fq.b();
                }
                if (this.s == null) {
                    this.s = a(8, "loc is null#0801");
                    eiVar.f("#0801");
                }
                if (this.s != null) {
                    strK = this.s.k();
                    aMapLocationA = this.s.m6clone();
                } else {
                    aMapLocationA = null;
                }
                try {
                    if (aMapLocationClientOptionB.isLocationCacheEnable() && this.v != null) {
                        aMapLocationA = this.v.a(aMapLocationA, strK, aMapLocationClientOptionB.getLastLocationLifeCycle());
                    }
                } catch (Throwable th2) {
                    fj.a(th2, "ApsServiceCore", "fixLastLocation");
                }
                a(messenger, aMapLocationA, strK, eiVar);
            } catch (Throwable th3) {
                fj.a(th3, "ApsServiceCore", "doLocation");
            }
        }
    }

    public static void d() {
        g = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        if (fq.m(this.e)) {
            new Object[1][0] = "Cl | dl -- sc_cpo";
            return;
        }
        try {
            if (this.f == null || this.f == null) {
                return;
            }
            this.f.a(this.d);
            this.f.g();
        } catch (Throwable th) {
            fj.a(th, "ApsServiceCore", "startColl");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        try {
            fi.c(this.e);
        } catch (Throwable th) {
            fj.a(th, "ApsServiceCore", "doCallOtherSer");
        }
    }

    public final void a() {
        try {
            this.i = new fo();
            this.b = new b("amapLocCoreThread");
            this.b.setPriority(5);
            this.b.start();
            this.d = new a(this.b.getLooper());
            this.w = new ArrayList();
        } catch (Throwable th) {
            fj.a(th, "ApsServiceCore", "onCreate");
        }
    }

    public final void a(Intent intent) {
        if (!"true".equals(intent.getStringExtra("as")) || this.d == null) {
            return;
        }
        this.d.sendEmptyMessageDelayed(9, 100L);
    }

    final void a(Messenger messenger, Bundle bundle, String str) {
        AMapLocationClientOption aMapLocationClientOptionB;
        float fA;
        if (bundle != null) {
            try {
                if (bundle.isEmpty()) {
                    return;
                }
                double d = bundle.getDouble("lat");
                double d2 = bundle.getDouble("lon");
                float f = bundle.getFloat("radius");
                long j = bundle.getLong("time");
                if ("FINE_LOC".equals(str)) {
                    AMapLocation aMapLocation = new AMapLocation("gps");
                    aMapLocation.setLatitude(d);
                    aMapLocation.setLocationType(1);
                    aMapLocation.setLongitude(d2);
                    aMapLocation.setAccuracy(f);
                    aMapLocation.setTime(j);
                    this.f.a(aMapLocation);
                }
                if (fi.h() && (aMapLocationClientOptionB = b(bundle)) != null && aMapLocationClientOptionB.isNeedAddress()) {
                    a(aMapLocationClientOptionB);
                    if (this.c != null) {
                        fA = fq.a(new double[]{d, d2, this.c.getLatitude(), this.c.getLongitude()});
                        if (fA < fi.i() * 3) {
                            a(messenger, str);
                        }
                    } else {
                        fA = -1.0f;
                    }
                    if (fA == -1.0f || fA > fi.i()) {
                        a(bundle);
                        this.c = this.f.a(d, d2);
                        if (this.c == null || TextUtils.isEmpty(this.c.getAdCode())) {
                            return;
                        }
                        a(messenger, str);
                    }
                }
            } catch (Throwable th) {
                fj.a(th, "ApsServiceCore", "doLocationGeo");
            }
        }
    }

    public final boolean a(String str) {
        if (TextUtils.isEmpty(this.l)) {
            this.l = fj.b(this.e);
        }
        return !TextUtils.isEmpty(str) && str.equals(this.l);
    }

    public final Handler b() {
        return this.d;
    }

    public final void b(Intent intent) {
        String stringExtra = intent.getStringExtra("a");
        if (!TextUtils.isEmpty(stringExtra)) {
            m.a(this.e, stringExtra);
        }
        this.a = intent.getStringExtra("b");
        l.a(this.a);
        String stringExtra2 = intent.getStringExtra("d");
        if (TextUtils.isEmpty(stringExtra2)) {
            return;
        }
        o.a(stringExtra2);
    }

    public final void c() {
        b bVar;
        try {
            if (this.h != null) {
                this.h.clear();
                this.h = null;
            }
            try {
                if (this.w != null) {
                    this.w.clear();
                }
            } catch (Throwable th) {
                fj.a(th, "apm", "des1");
            }
            if (this.v != null) {
                this.v.c();
                this.v = null;
            }
            this.o = false;
            this.p = false;
            this.f.e();
            if (this.d != null) {
                this.d.removeCallbacksAndMessages(null);
            }
            this.d = null;
            if (this.b != null) {
                if (Build.VERSION.SDK_INT >= 18) {
                    try {
                        fm.a(this.b, (Class<?>) HandlerThread.class, "quitSafely", new Object[0]);
                    } catch (Throwable unused) {
                        bVar = this.b;
                        bVar.quit();
                    }
                } else {
                    bVar = this.b;
                }
                bVar.quit();
            }
            this.b = null;
            if (this.i != null && this.j != 0 && this.k != 0) {
                long jB = fq.b() - this.j;
                fo.a(this.e, this.i.c(this.e), this.i.d(this.e), this.k, jB);
                this.i.e(this.e);
            }
            fo.a(this.e);
            an.b();
            if (g) {
                Process.killProcess(Process.myPid());
            }
        } catch (Throwable th2) {
            fj.a(th2, "apm", "tdest");
        }
    }
}
