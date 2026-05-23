package com.loc;

import android.app.Application;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.PointerIconCompat;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.amap.api.location.APSService;
import com.amap.api.location.UmidtokenInfo;
import com.gme.av.sdk.AVError;
import com.gme.liteav.TXLiteAVCode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public final class d {
    private static boolean G = true;
    private static boolean I = false;
    private static AtomicBoolean J = new AtomicBoolean(false);
    public static volatile boolean g = false;
    private Context C;
    private g D;
    eo a;
    public c c;
    j j;
    Intent m;
    AMapLocationClientOption b = new AMapLocationClientOption();
    h d = null;
    private boolean E = false;
    private volatile boolean F = false;
    ArrayList<AMapLocationListener> e = new ArrayList<>();
    boolean f = false;
    public boolean h = true;
    public boolean i = true;
    Messenger k = null;
    Messenger l = null;
    int n = 0;
    private boolean H = true;
    b o = null;
    boolean p = false;
    AMapLocationClientOption.AMapLocationMode q = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;
    Object r = new Object();
    fo s = null;
    boolean t = false;
    e u = null;
    private AMapLocationClientOption K = new AMapLocationClientOption();
    private i L = null;
    String v = null;
    private ServiceConnection M = new ServiceConnection() { // from class: com.loc.d.2
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                d.this.k = new Messenger(iBinder);
                d.this.E = true;
                d.this.t = true;
            } catch (Throwable th) {
                fj.a(th, "ALManager", "onServiceConnected");
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            d.this.k = null;
            d.this.E = false;
        }
    };
    AMapLocationQualityReport w = null;
    boolean x = false;
    boolean y = false;
    private volatile boolean N = false;
    a z = null;
    String A = null;
    boolean B = false;

    public class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v0, types: [int] */
        /* JADX WARN: Type inference failed for: r6v5 */
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            try {
                super.handleMessage(message);
                ?? r1 = message.what;
                try {
                    switch (r1) {
                        case 11:
                            d.this.a(message.getData());
                            return;
                        case 12:
                            d.this.b(message);
                            return;
                        default:
                            try {
                                switch (r1) {
                                    case 1002:
                                        d.this.c((AMapLocationListener) message.obj);
                                        break;
                                    case 1003:
                                        d.this.j();
                                        d.this.a(13, (Bundle) null);
                                        break;
                                    case 1004:
                                        d.this.l();
                                        d.this.a(14, (Bundle) null);
                                        break;
                                    case AVError.AV_ERR_TIMEOUT /* 1005 */:
                                        d.this.d((AMapLocationListener) message.obj);
                                        break;
                                    case 1006:
                                    case 1007:
                                        break;
                                    default:
                                        switch (r1) {
                                            case PointerIconCompat.TYPE_ALIAS /* 1010 */:
                                                break;
                                            case PointerIconCompat.TYPE_COPY /* 1011 */:
                                                d.this.a(14, (Bundle) null);
                                                d.this.g();
                                                break;
                                            default:
                                                switch (r1) {
                                                    case PointerIconCompat.TYPE_HORIZONTAL_DOUBLE_ARROW /* 1014 */:
                                                        d.this.a(message);
                                                        break;
                                                    case PointerIconCompat.TYPE_VERTICAL_DOUBLE_ARROW /* 1015 */:
                                                        d.this.d.a(d.this.b);
                                                        d.this.a(InputDeviceCompat.SOURCE_GAMEPAD, (Object) null, 300000L);
                                                        break;
                                                    case PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW /* 1016 */:
                                                        if (fq.m(d.this.C)) {
                                                            new Object[1][0] = "coarse permission lbs location";
                                                            d.this.r();
                                                        } else if (!d.this.d.b()) {
                                                            d.this.n();
                                                        } else {
                                                            d.this.a(PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW, (Object) null, 1000L);
                                                        }
                                                        break;
                                                    case PointerIconCompat.TYPE_TOP_LEFT_DIAGONAL_DOUBLE_ARROW /* 1017 */:
                                                        d.this.d.a();
                                                        d.this.a(InputDeviceCompat.SOURCE_GAMEPAD);
                                                        break;
                                                    case PointerIconCompat.TYPE_ZOOM_IN /* 1018 */:
                                                        d.this.b = (AMapLocationClientOption) message.obj;
                                                        if (d.this.b != null) {
                                                            d.this.s();
                                                        }
                                                        break;
                                                    case 1023:
                                                        d.this.c(message);
                                                        break;
                                                    case 1024:
                                                        d.this.d(message);
                                                        break;
                                                    case InputDeviceCompat.SOURCE_GAMEPAD /* 1025 */:
                                                        if (d.this.d.f()) {
                                                            d.this.d.a();
                                                            d.this.d.a(d.this.b);
                                                        }
                                                        d.this.a(InputDeviceCompat.SOURCE_GAMEPAD, (Object) null, 300000L);
                                                        break;
                                                    case 1026:
                                                        d.this.D.a(d.this.b);
                                                        break;
                                                    case 1027:
                                                        d.this.D.a();
                                                        break;
                                                    case 1028:
                                                        d.this.f((AMapLocation) message.obj);
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                }
                                return;
                            } catch (Throwable th) {
                                th = th;
                            }
                            break;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    message = r1;
                }
            } catch (Throwable th3) {
                th = th3;
                message = 0;
            }
            if (message == 0) {
                message = "handleMessage";
            }
            fj.a(th, "AMapLocationManage$MHandlerr", message);
        }
    }

    static class b extends HandlerThread {
        d a;

        public b(String str, d dVar) {
            super(str);
            this.a = null;
            this.a = dVar;
        }

        @Override // android.os.HandlerThread
        protected final void onLooperPrepared() {
            try {
                this.a.j.a();
                fn.a(this.a.C);
                this.a.p();
                if (this.a != null && this.a.C != null) {
                    fi.b(this.a.C);
                    fi.a(this.a.C);
                }
                super.onLooperPrepared();
            } catch (Throwable unused) {
            }
        }

        @Override // android.os.HandlerThread, java.lang.Thread, java.lang.Runnable
        public final void run() {
            try {
                super.run();
            } catch (Throwable unused) {
            }
        }
    }

    public class c extends Handler {
        public c() {
        }

        public c(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v0 */
        /* JADX WARN: Type inference failed for: r1v13 */
        /* JADX WARN: Type inference failed for: r1v19 */
        /* JADX WARN: Type inference failed for: r1v6, types: [int] */
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            String str;
            try {
                super.handleMessage(message);
            } catch (Throwable th) {
                th = th;
                str = 0;
            }
            if (d.this.p) {
                return;
            }
            str = message.what;
            if (str != 13) {
                try {
                    switch (str) {
                        case 1:
                            Message messageObtainMessage = d.this.z.obtainMessage();
                            messageObtainMessage.what = 11;
                            messageObtainMessage.setData(message.getData());
                            d.this.z.sendMessage(messageObtainMessage);
                            break;
                        case 2:
                            Message messageObtain = Message.obtain();
                            messageObtain.what = 12;
                            messageObtain.obj = message.obj;
                            d.this.z.sendMessage(messageObtain);
                            if (d.this.K == null && d.this.K.getCacheCallBack() && d.this.c != null) {
                                d.this.c.removeMessages(13);
                                break;
                            }
                            break;
                        case 3:
                            break;
                        default:
                            switch (str) {
                                case 5:
                                    Bundle data = message.getData();
                                    data.putBundle("optBundle", fj.a(d.this.b));
                                    d.this.a(10, data);
                                    break;
                                case 6:
                                    Bundle data2 = message.getData();
                                    if (d.this.d != null) {
                                        d.this.d.a(data2);
                                    }
                                    break;
                                case 7:
                                    d.this.H = message.getData().getBoolean("ngpsAble");
                                    break;
                                case 8:
                                    fo.a((String) null, 2141);
                                    Message messageObtain2 = Message.obtain();
                                    messageObtain2.what = 12;
                                    messageObtain2.obj = message.obj;
                                    d.this.z.sendMessage(messageObtain2);
                                    if (d.this.K == null) {
                                    }
                                    break;
                                case 9:
                                    try {
                                        boolean unused = d.I = message.getData().getBoolean("installMockApp");
                                    } catch (Throwable th2) {
                                        str = "handleMessage RESULT_INSTALLED_MOCK_APP";
                                        th = th2;
                                    }
                                    break;
                                case 10:
                                    d.this.a((AMapLocation) message.obj);
                                    break;
                                default:
                                    switch (str) {
                                        case 100:
                                            fo.a((String) null, 2155);
                                            break;
                                        case 102:
                                            Bundle data3 = message.getData();
                                            data3.putBundle("optBundle", fj.a(d.this.b));
                                            d.this.a(15, data3);
                                            break;
                                        case 103:
                                            Bundle data4 = message.getData();
                                            if (d.this.D != null) {
                                                d.this.D.a(data4);
                                            }
                                            break;
                                    }
                                    Message messageObtain3 = Message.obtain();
                                    messageObtain3.what = 1028;
                                    messageObtain3.obj = message.obj;
                                    d.this.z.sendMessage(messageObtain3);
                                    if (d.this.K != null && d.this.K.getCacheCallBack() && d.this.c != null) {
                                        d.this.c.removeMessages(13);
                                        break;
                                    }
                                    break;
                            }
                            break;
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
                return;
            }
            try {
                if (d.this.a != null) {
                    d.this.a(d.this.a);
                    return;
                }
                AMapLocation aMapLocation = new AMapLocation("LBS");
                aMapLocation.setErrorCode(33);
                d.this.a(aMapLocation);
                return;
            } catch (Throwable th4) {
                th = th4;
                str = "handleMessage RESULT_CACHE_CALLBACK";
            }
            if (str == 0) {
                str = "handleMessage";
            }
            fj.a(th, "AmapLocationManager$MainHandler", str);
        }
    }

    public d(Context context, Intent intent, Looper looper) {
        this.m = null;
        this.C = context;
        this.m = intent;
        b(looper);
    }

    private a a(Looper looper) {
        a aVar;
        synchronized (this.r) {
            this.z = new a(looper);
            aVar = this.z;
        }
        return aVar;
    }

    private eo a(ej ejVar, boolean z) {
        if (!this.b.isLocationCacheEnable()) {
            return null;
        }
        try {
            return ejVar.a(z);
        } catch (Throwable th) {
            fj.a(th, "ALManager", "doFirstCacheLoc");
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i) {
        synchronized (this.r) {
            if (this.z != null) {
                this.z.removeMessages(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, Bundle bundle) {
        if (bundle == null) {
            try {
                bundle = new Bundle();
            } catch (Throwable th) {
                boolean z = (th instanceof IllegalStateException) && th.getMessage().contains("sending message to a Handler on a dead thread");
                if ((th instanceof RemoteException) || z) {
                    this.k = null;
                    this.E = false;
                }
                fj.a(th, "ALManager", "sendLocMessage");
                return;
            }
        }
        if (TextUtils.isEmpty(this.v)) {
            this.v = fj.b(this.C);
        }
        bundle.putString(com.igexin.push.core.d.d.d, this.v);
        Message messageObtain = Message.obtain();
        messageObtain.what = i;
        messageObtain.setData(bundle);
        messageObtain.replyTo = this.l;
        if (this.k != null) {
            this.k.send(messageObtain);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i, Object obj, long j) {
        synchronized (this.r) {
            if (this.z != null) {
                Message messageObtain = Message.obtain();
                messageObtain.what = i;
                if (obj instanceof Bundle) {
                    messageObtain.setData((Bundle) obj);
                } else {
                    messageObtain.obj = obj;
                }
                this.z.sendMessageDelayed(messageObtain, j);
            }
        }
    }

    private static void a(final Context context) {
        if (J.compareAndSet(false, true)) {
            cj.a().b(new ck() { // from class: com.loc.d.1
                @Override // com.loc.ck
                public final void a() {
                    o.e();
                    o.a(context);
                    o.h(context);
                }
            });
        }
    }

    private void a(Intent intent) {
        try {
            this.C.bindService(intent, this.M, 1);
        } catch (Throwable th) {
            fj.a(th, "ALManager", "startServiceImpl");
        }
    }

    private void a(Intent intent, boolean z) {
        if (this.C != null) {
            if (Build.VERSION.SDK_INT < 26 || !z) {
                this.C.startService(intent);
            } else if (!t()) {
                Log.e("amapapi", "-------------调用后台定位服务，缺少权限：android.permission.FOREGROUND_SERVICE--------------");
                return;
            } else {
                try {
                    this.C.getClass().getMethod("startForegroundService", Intent.class).invoke(this.C, intent);
                } catch (Throwable unused) {
                    this.C.startService(intent);
                }
            }
            this.B = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Bundle bundle) {
        ei eiVar;
        AMapLocation aMapLocationA;
        AMapLocation aMapLocation = null;
        if (bundle != null) {
            try {
                bundle.setClassLoader(AMapLocation.class.getClassLoader());
                aMapLocationA = (AMapLocation) bundle.getParcelable("loc");
                this.A = bundle.getString("nb");
                eiVar = (ei) bundle.getParcelable("statics");
                if (aMapLocationA != null) {
                    try {
                        if (aMapLocationA.getErrorCode() == 0 && this.d != null) {
                            this.d.c();
                            if (!TextUtils.isEmpty(aMapLocationA.getAdCode())) {
                                h.y = aMapLocationA;
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        fj.a(th, "AmapLocationManager", "resultLbsLocationSuccess");
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                eiVar = null;
                fj.a(th, "AmapLocationManager", "resultLbsLocationSuccess");
            }
        } else {
            eiVar = null;
            aMapLocationA = null;
        }
        if (this.d != null) {
            aMapLocationA = this.d.a(aMapLocationA, this.A);
        }
        aMapLocation = aMapLocationA;
        a(aMapLocation, eiVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Message message) {
        try {
            Bundle data = message.getData();
            AMapLocation aMapLocation = (AMapLocation) data.getParcelable("loc");
            String string = data.getString("lastLocNb");
            e(aMapLocation);
            if (this.j.a(aMapLocation, string)) {
                this.j.d();
            }
        } catch (Throwable th) {
            fj.a(th, "ALManager", "doSaveLastLocation");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AMapLocation aMapLocation) {
        try {
            if (aMapLocation.getErrorCode() != 0) {
                aMapLocation.setLocationType(0);
            }
            if (aMapLocation.getErrorCode() == 0) {
                double latitude = aMapLocation.getLatitude();
                double longitude = aMapLocation.getLongitude();
                if ((latitude == 0.0d && longitude == 0.0d) || latitude < -90.0d || latitude > 90.0d || longitude < -180.0d || longitude > 180.0d) {
                    fo.a("errorLatLng", aMapLocation.toStr());
                    aMapLocation.setLocationType(0);
                    aMapLocation.setErrorCode(8);
                    aMapLocation.setLocationDetail("LatLng is error#0802");
                }
            }
            if ("gps".equalsIgnoreCase(aMapLocation.getProvider()) || !this.d.b()) {
                aMapLocation.setAltitude(fq.c(aMapLocation.getAltitude()));
                aMapLocation.setBearing(fq.a(aMapLocation.getBearing()));
                aMapLocation.setSpeed(fq.a(aMapLocation.getSpeed()));
                b(aMapLocation);
                Iterator<AMapLocationListener> it = this.e.iterator();
                while (it.hasNext()) {
                    try {
                        it.next().onLocationChanged(aMapLocation);
                    } catch (Throwable unused) {
                    }
                }
            }
        } catch (Throwable unused2) {
        }
    }

    private synchronized void a(AMapLocation aMapLocation, ei eiVar) {
        try {
            if (aMapLocation == null) {
                try {
                    aMapLocation = new AMapLocation("");
                    aMapLocation.setErrorCode(8);
                    aMapLocation.setLocationDetail("amapLocation is null#0801");
                } catch (Throwable th) {
                    fj.a(th, "ALManager", "handlerLocation part3");
                    return;
                }
            }
            if (!"gps".equalsIgnoreCase(aMapLocation.getProvider())) {
                aMapLocation.setProvider("lbs");
            }
            if (this.w == null) {
                this.w = new AMapLocationQualityReport();
            }
            this.w.setLocationMode(this.b.getLocationMode());
            if (this.d != null) {
                this.w.setGPSSatellites(this.d.e());
                this.w.setGpsStatus(this.d.d());
            }
            this.w.setWifiAble(fq.g(this.C));
            this.w.setNetworkType(fq.h(this.C));
            if (aMapLocation.getLocationType() == 1 || "gps".equalsIgnoreCase(aMapLocation.getProvider())) {
                this.w.setNetUseTime(0L);
            }
            if (eiVar != null) {
                this.w.setNetUseTime(eiVar.a());
            }
            this.w.setInstallHighDangerMockApp(I);
            aMapLocation.setLocationQualityReport(this.w);
            try {
                if (this.F) {
                    a(aMapLocation, this.A);
                    if (eiVar != null) {
                        eiVar.d(fq.b());
                    }
                    fo.a(this.C, aMapLocation, eiVar);
                    fo.a(this.C, aMapLocation);
                    c(aMapLocation.m6clone());
                    fn.a(this.C).a(aMapLocation);
                    fn.a(this.C).b();
                }
            } catch (Throwable th2) {
                fj.a(th2, "ALManager", "handlerLocation part2");
            }
            if (this.p) {
                return;
            }
            if (this.b.isOnceLocation()) {
                l();
                a(14, (Bundle) null);
            }
        } catch (Throwable th3) {
            throw th3;
        }
    }

    private void a(AMapLocation aMapLocation, String str) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("loc", aMapLocation);
        bundle.putString("lastLocNb", str);
        a(PointerIconCompat.TYPE_HORIZONTAL_DOUBLE_ARROW, bundle, 0L);
    }

    private static void a(ej ejVar) {
        try {
            ejVar.d();
            ejVar.a(new AMapLocationClientOption().setNeedAddress(false));
            ejVar.a(true, new ei());
        } catch (Throwable th) {
            fj.a(th, "ALManager", "apsLocation:doFirstNetLocate 2");
        }
    }

    private void a(ej ejVar, ei eiVar) {
        try {
            ejVar.a(this.C);
            ejVar.a(this.b);
            ejVar.b(eiVar);
        } catch (Throwable th) {
            fj.a(th, "ALManager", "initApsBase");
        }
    }

    private static void a(ej ejVar, eo eoVar) {
        if (eoVar != null) {
            try {
                if (eoVar.getErrorCode() == 0) {
                    ejVar.b(eoVar);
                }
            } catch (Throwable th) {
                fj.a(th, "ALManager", "apsLocation:doFirstAddCache");
            }
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(23:0|2|(12:107|3|101|4|(1:6)|114|10|(1:12)|16|17|120|18)|(5:20|(1:36)(2:22|(3:25|(2:27|(1:31))|36)(1:24))|108|85|93)(1:32)|37|(5:39|118|40|(3:112|42|(1:44))|47)(1:51)|(2:53|54)(1:57)|105|58|(2:62|63)|99|66|(1:70)|103|71|(1:73)|74|(1:76)|(1:84)|108|85|93|(2:(0)|(1:121))) */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00f0, code lost:
    
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00f1, code lost:
    
        com.loc.fj.a(r7, "ALManager", "fixLastLocation");
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x012e, code lost:
    
        r12 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x012f, code lost:
    
        com.loc.fj.a(r12, "ALManager", "apsLocation:callback");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private eo b(ej ejVar) {
        eo eoVarA;
        boolean zL;
        boolean z;
        eo eoVarA2;
        boolean z2;
        String strK;
        Throwable th;
        AMapLocation aMapLocationA = null;
        this.a = null;
        ei eiVar = new ei();
        try {
            try {
                eiVar.c(fq.b());
                try {
                    String apikey = AMapLocationClientOption.getAPIKEY();
                    if (!TextUtils.isEmpty(apikey)) {
                        m.a(this.C, apikey);
                    }
                } catch (Throwable th2) {
                    fj.a(th2, "ALManager", "apsLocation setAuthKey");
                }
                try {
                    String umidtoken = UmidtokenInfo.getUmidtoken();
                    if (!TextUtils.isEmpty(umidtoken)) {
                        o.a(umidtoken);
                    }
                } catch (Throwable th3) {
                    fj.a(th3, "ALManager", "apsLocation setUmidToken");
                }
                a(ejVar, eiVar);
                zL = fi.l();
                z = false;
                try {
                } catch (Throwable th4) {
                    fj.a(th4, "ALManager", "apscach");
                }
            } finally {
                try {
                    ejVar.e();
                } catch (Throwable unused) {
                }
            }
        } catch (Throwable th5) {
            th = th5;
            eoVarA = null;
        }
        if (this.K.getCacheCallBack()) {
            eoVarA2 = a(ejVar, this.K.getCacheCallBack());
            if (eoVarA2 == null) {
                eoVarA2 = null;
            } else if (!fi.a(eoVarA2.getTime())) {
                if (this.K.getCacheCallBack()) {
                    int cacheTimeOut = this.K.getCacheTimeOut();
                    long jA = fq.a() - eoVarA2.getTime();
                    if (jA > 0 && jA < cacheTimeOut) {
                        this.a = eoVarA2;
                        this.a.setLocationType(10);
                    }
                }
                eoVarA2 = null;
            }
            return eoVarA;
        }
        eoVarA2 = a(ejVar, false);
        if (eoVarA2 == null) {
            try {
                eoVarA = ejVar.a(!zL, eiVar);
                if (eoVarA != null) {
                    try {
                        if (eoVarA.getErrorCode() == 0) {
                            z = true;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        try {
                            fj.a(th, "ALManager", "apsLocation:doFirstNetLocate");
                        } catch (Throwable th7) {
                            th = th7;
                            fj.a(th, "ALManager", "apsLocation");
                        }
                    }
                }
            } catch (Throwable th8) {
                eoVarA = eoVarA2;
                th = th8;
            }
            z2 = true;
        } else {
            eoVarA = eoVarA2;
            z2 = false;
        }
        if (eoVarA != null) {
            strK = eoVarA.k();
            aMapLocationA = eoVarA.m6clone();
        } else {
            strK = null;
        }
        if (this.b.isLocationCacheEnable() && this.j != null) {
            aMapLocationA = this.j.a(aMapLocationA, strK, this.b.getLastLocationLifeCycle());
        }
        if (this.K.getCacheCallBack() && this.c != null) {
            this.c.removeMessages(13);
        }
        Bundle bundle = new Bundle();
        if (aMapLocationA != null) {
            bundle.putParcelable("loc", aMapLocationA);
            bundle.putString("nb", eoVarA.k());
            bundle.putParcelable("statics", eiVar);
        }
        a(bundle);
        if (z) {
            a(ejVar, eoVarA);
        }
        if (z2 && zL && !g) {
            g = true;
            a(ejVar);
        }
        return eoVarA;
    }

    private void b(Looper looper) {
        try {
            if (looper == null) {
                this.c = Looper.myLooper() == null ? new c(this.C.getMainLooper()) : new c();
            } else {
                this.c = new c(looper);
            }
        } catch (Throwable th) {
            fj.a(th, "ALManager", "init 1");
        }
        try {
            try {
                this.j = new j(this.C);
            } catch (Throwable th2) {
                fj.a(th2, "ALManager", "init 2");
            }
            this.o = new b("amapLocManagerThread", this);
            this.o.setPriority(5);
            this.o.start();
            this.z = a(this.o.getLooper());
        } catch (Throwable th3) {
            fj.a(th3, "ALManager", "init 5");
        }
        try {
            this.d = new h(this.C, this.c);
            this.D = new g(this.C, this.c);
        } catch (Throwable th4) {
            fj.a(th4, "ALManager", "init 3");
        }
        if (this.s == null) {
            this.s = new fo();
        }
        a(this.C);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Message message) {
        try {
            AMapLocation aMapLocation = (AMapLocation) message.obj;
            if (this.h && this.k != null) {
                Bundle bundle = new Bundle();
                bundle.putBundle("optBundle", fj.a(this.b));
                a(0, bundle);
                if (this.F) {
                    a(13, (Bundle) null);
                }
                this.h = false;
            }
            a(aMapLocation, (ei) null);
            a(InputDeviceCompat.SOURCE_GAMEPAD);
            a(InputDeviceCompat.SOURCE_GAMEPAD, (Object) null, 300000L);
        } catch (Throwable th) {
            fj.a(th, "ALManager", "resultGpsLocationSuccess");
        }
    }

    private void b(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            try {
                String locationDetail = aMapLocation.getLocationDetail();
                StringBuilder sb = TextUtils.isEmpty(locationDetail) ? new StringBuilder() : new StringBuilder(locationDetail);
                boolean zC = fq.c(this.C, "EYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19XSUZJX1NUQVRF");
                boolean zC2 = fq.c(this.C, "WYW5kcm9pZC5wZXJtaXNzaW9uLkNIQU5HRV9XSUZJX1NUQVRF");
                boolean zC3 = fq.c(this.C, "WYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19MT0NBVElPTl9FWFRSQV9DT01NQU5EUw==");
                boolean zC4 = fq.c(this.C, "EYW5kcm9pZC5wZXJtaXNzaW9uLlJFQURfUEhPTkVfU1RBVEU=");
                boolean zC5 = fq.c(this.C, "EYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19GSU5FX0xPQ0FUSU9O");
                boolean zC6 = fq.c(this.C, "EYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19DT0FSU0VfTE9DQVRJT04=");
                sb.append(zC ? "#pm1" : "#pm0");
                sb.append(zC2 ? "1" : "0");
                sb.append(zC3 ? "1" : "0");
                sb.append(zC4 ? "1" : "0");
                sb.append(zC5 ? "1" : "0");
                sb.append(zC6 ? "1" : "0");
                aMapLocation.setLocationDetail(sb.toString());
            } catch (Throwable unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Message message) {
        if (message == null) {
            return;
        }
        try {
            Bundle data = message.getData();
            if (data == null) {
                return;
            }
            int i = data.getInt(com.igexin.push.core.d.d.e, 0);
            Notification notification = (Notification) data.getParcelable(al.g);
            Intent intentQ = q();
            intentQ.putExtra(com.igexin.push.core.d.d.e, i);
            intentQ.putExtra(al.g, notification);
            intentQ.putExtra(al.f, 1);
            a(intentQ, true);
        } catch (Throwable th) {
            fj.a(th, "ALManager", "doEnableBackgroundLocation");
        }
    }

    private void c(AMapLocation aMapLocation) {
        Message messageObtainMessage = this.c.obtainMessage();
        messageObtainMessage.what = 10;
        messageObtainMessage.obj = aMapLocation;
        this.c.sendMessage(messageObtainMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(AMapLocationListener aMapLocationListener) {
        if (aMapLocationListener == null) {
            throw new IllegalArgumentException("listener参数不能为null");
        }
        if (this.e == null) {
            this.e = new ArrayList<>();
        }
        if (this.e.contains(aMapLocationListener)) {
            return;
        }
        this.e.add(aMapLocationListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(Message message) {
        if (message == null) {
            return;
        }
        try {
            Bundle data = message.getData();
            if (data == null) {
                return;
            }
            boolean z = data.getBoolean(al.j, true);
            Intent intentQ = q();
            intentQ.putExtra(al.j, z);
            intentQ.putExtra(al.f, 2);
            a(intentQ, false);
        } catch (Throwable th) {
            fj.a(th, "ALManager", "doDisableBackgroundLocation");
        }
    }

    private synchronized void d(AMapLocation aMapLocation) {
        try {
            if (aMapLocation == null) {
                try {
                    aMapLocation = new AMapLocation("");
                    aMapLocation.setErrorCode(8);
                    aMapLocation.setLocationDetail("coarse amapLocation is null#2005");
                } catch (Throwable th) {
                    fj.a(th, "ALManager", "handlerCoarseLocation part2");
                    return;
                }
            }
            if (this.w == null) {
                this.w = new AMapLocationQualityReport();
            }
            this.w.setLocationMode(this.b.getLocationMode());
            if (this.D != null) {
                this.w.setGPSSatellites(aMapLocation.getSatellites());
                this.w.setGpsStatus(this.D.b());
            }
            this.w.setWifiAble(fq.g(this.C));
            this.w.setNetworkType(fq.h(this.C));
            this.w.setNetUseTime(0L);
            this.w.setInstallHighDangerMockApp(I);
            aMapLocation.setLocationQualityReport(this.w);
            try {
                if (this.F) {
                    fo.a(this.C, aMapLocation);
                    c(aMapLocation.m6clone());
                    fn.a(this.C).a(aMapLocation);
                    fn.a(this.C).b();
                }
            } catch (Throwable th2) {
                fj.a(th2, "ALManager", "handlerCoarseLocation part");
            }
            if (this.p) {
                return;
            }
            if (this.D != null) {
                l();
            }
            a(14, (Bundle) null);
        } catch (Throwable th3) {
            throw th3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(AMapLocationListener aMapLocationListener) {
        if (!this.e.isEmpty() && this.e.contains(aMapLocationListener)) {
            this.e.remove(aMapLocationListener);
        }
        if (this.e.isEmpty()) {
            l();
        }
    }

    private void e(AMapLocation aMapLocation) {
        if (aMapLocation == null) {
            return;
        }
        AMapLocation aMapLocationA = null;
        try {
            if (j.b != null) {
                aMapLocationA = j.b.a();
            } else if (this.j != null) {
                aMapLocationA = this.j.b();
            }
            fo.a(aMapLocationA, aMapLocation);
        } catch (Throwable unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f(AMapLocation aMapLocation) {
        try {
            if (this.i && this.k != null) {
                Bundle bundle = new Bundle();
                bundle.putBundle("optBundle", fj.a(this.b));
                a(0, bundle);
                if (this.F) {
                    a(13, (Bundle) null);
                }
                this.i = false;
            }
            d(aMapLocation);
        } catch (Throwable th) {
            fj.a(th, "ALManager", "resultGpsLocationSuccess");
        }
    }

    private void h() {
        synchronized (this.r) {
            if (this.z != null) {
                this.z.removeCallbacksAndMessages(null);
            }
            this.z = null;
        }
    }

    private boolean i() {
        boolean z = false;
        int i = 0;
        while (this.k == null) {
            try {
                Thread.sleep(100L);
                i++;
                if (i >= 50) {
                    break;
                }
            } catch (Throwable th) {
                fj.a(th, "ALManager", "checkAPSManager");
            }
        }
        if (this.k == null) {
            Message messageObtain = Message.obtain();
            Bundle bundle = new Bundle();
            AMapLocation aMapLocation = new AMapLocation("");
            aMapLocation.setErrorCode(10);
            aMapLocation.setLocationDetail(!fq.k(this.C.getApplicationContext()) ? "请检查配置文件是否配置服务，并且manifest中service标签是否配置在application标签内#1003" : "启动ApsServcie失败#1001");
            bundle.putParcelable("loc", aMapLocation);
            messageObtain.setData(bundle);
            messageObtain.what = 1;
            this.c.sendMessage(messageObtain);
        } else {
            z = true;
        }
        if (!z) {
            fo.a((String) null, !fq.k(this.C.getApplicationContext()) ? 2103 : TXLiteAVCode.WARNING_VIDEO_FRAME_DECODE_FAIL);
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void j() {
        if ((Build.VERSION.SDK_INT < 29 && Build.VERSION.SDK_INT >= 23 && !fq.c(this.C, "EYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19DT0FSU0VfTE9DQVRJT04=") && !fq.c(this.C, "EYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19GSU5FX0xPQ0FUSU9O")) || ((Build.VERSION.SDK_INT < 31 && Build.VERSION.SDK_INT >= 29 && !fq.c(this.C, "EYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19GSU5FX0xPQ0FUSU9O")) || (Build.VERSION.SDK_INT >= 31 && !fq.c(this.C, "EYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19DT0FSU0VfTE9DQVRJT04=") && !fq.c(this.C, "EYW5kcm9pZC5wZXJtaXNzaW9uLkFDQ0VTU19GSU5FX0xPQ0FUSU9O")))) {
            k();
            return;
        }
        if (this.b == null) {
            this.b = new AMapLocationClientOption();
        }
        if (this.F) {
            return;
        }
        this.F = true;
        long gpsFirstTimeout = 0;
        switch (this.b.getLocationMode()) {
            case Battery_Saving:
                a(1027, (Object) null, 0L);
                a(PointerIconCompat.TYPE_TOP_LEFT_DIAGONAL_DOUBLE_ARROW, (Object) null, 0L);
                a(PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW, (Object) null, 0L);
                return;
            case Device_Sensors:
                if (fq.m(this.C)) {
                    a(PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW);
                    a(PointerIconCompat.TYPE_TOP_LEFT_DIAGONAL_DOUBLE_ARROW, (Object) null, 0L);
                    a(1026, (Object) null, 0L);
                    return;
                } else {
                    a(PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW);
                    a(1027, (Object) null, 0L);
                    a(PointerIconCompat.TYPE_VERTICAL_DOUBLE_ARROW, (Object) null, 0L);
                    return;
                }
            case Hight_Accuracy:
                if (fq.m(this.C)) {
                    a(PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW);
                    a(PointerIconCompat.TYPE_TOP_LEFT_DIAGONAL_DOUBLE_ARROW, (Object) null, 0L);
                    a(1026, (Object) null, 0L);
                    return;
                } else {
                    a(1027, (Object) null, 0L);
                    a(PointerIconCompat.TYPE_VERTICAL_DOUBLE_ARROW, (Object) null, 0L);
                    if (this.b.isGpsFirst() && this.b.isOnceLocation()) {
                        gpsFirstTimeout = this.b.getGpsFirstTimeout();
                    }
                    a(PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW, (Object) null, gpsFirstTimeout);
                }
                break;
        }
    }

    private void k() {
        AMapLocation aMapLocation = new AMapLocation("");
        aMapLocation.setErrorCode(12);
        aMapLocation.setLocationDetail("定位权限被禁用,请授予应用定位权限 #1201");
        if (this.w == null) {
            this.w = new AMapLocationQualityReport();
        }
        this.w = new AMapLocationQualityReport();
        this.w.setGpsStatus(4);
        this.w.setGPSSatellites(0);
        this.w.setLocationMode(this.b.getLocationMode());
        this.w.setWifiAble(fq.g(this.C));
        this.w.setNetworkType(fq.h(this.C));
        this.w.setNetUseTime(0L);
        aMapLocation.setLocationQualityReport(this.w);
        fo.a((String) null, 2121);
        c(aMapLocation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void l() {
        try {
            a(InputDeviceCompat.SOURCE_GAMEPAD);
            if (this.d != null) {
                this.d.a();
            }
            if (this.D != null) {
                this.D.a();
            }
            a(PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW);
            this.F = false;
            this.n = 0;
        } catch (Throwable th) {
            fj.a(th, "ALManager", "stopLocation");
        }
    }

    private void m() {
        eo eoVarB = b(new ej(true));
        if (i()) {
            Bundle bundle = new Bundle();
            String str = "0";
            if (eoVarB != null && (eoVarB.getLocationType() == 2 || eoVarB.getLocationType() == 4)) {
                str = "1";
            }
            bundle.putBundle("optBundle", fj.a(this.b));
            bundle.putString("isCacheLoc", str);
            a(0, bundle);
            if (this.F) {
                a(13, (Bundle) null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void n() {
        try {
            try {
                if (G || !(this.t || this.N)) {
                    G = false;
                    this.N = true;
                    m();
                } else {
                    try {
                        if (this.t && !a() && !this.y) {
                            this.y = true;
                            p();
                        }
                    } catch (Throwable th) {
                        this.y = true;
                        fj.a(th, "ALManager", "doLBSLocation reStartService");
                    }
                    if (i()) {
                        this.y = false;
                        Bundle bundle = new Bundle();
                        bundle.putBundle("optBundle", fj.a(this.b));
                        bundle.putString("d", UmidtokenInfo.getUmidtoken());
                        if (!this.d.b()) {
                            a(1, bundle);
                        }
                    }
                }
            } catch (Throwable th2) {
                fj.a(th2, "ALManager", "doLBSLocation");
                try {
                    if (this.b.isOnceLocation()) {
                        return;
                    }
                    o();
                } catch (Throwable unused) {
                }
            }
        } finally {
            try {
                if (!this.b.isOnceLocation()) {
                    o();
                }
            } catch (Throwable unused2) {
            }
        }
    }

    private void o() {
        if (this.b.getLocationMode() != AMapLocationClientOption.AMapLocationMode.Device_Sensors) {
            a(PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW, (Object) null, this.b.getInterval() >= 1000 ? this.b.getInterval() : 1000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void p() {
        try {
            if (this.l == null) {
                this.l = new Messenger(this.c);
            }
            a(q());
        } catch (Throwable unused) {
        }
    }

    private Intent q() {
        String apikey;
        if (this.m == null) {
            this.m = new Intent(this.C, (Class<?>) APSService.class);
        }
        try {
            apikey = !TextUtils.isEmpty(AMapLocationClientOption.getAPIKEY()) ? AMapLocationClientOption.getAPIKEY() : l.f(this.C);
        } catch (Throwable th) {
            fj.a(th, "ALManager", "startServiceImpl p2");
            apikey = "";
        }
        this.m.putExtra("a", apikey);
        this.m.putExtra("b", l.c(this.C));
        this.m.putExtra("d", UmidtokenInfo.getUmidtoken());
        return this.m;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void r() {
        try {
            StringBuilder sb = new StringBuilder();
            new ei().f("#2001");
            sb.append("模糊权限下不支持低功耗定位#2001");
            fo.a((String) null, 2153);
            eo eoVar = new eo("");
            eoVar.setErrorCode(20);
            eoVar.setLocationDetail(sb.toString());
            f(eoVar);
        } catch (Throwable th) {
            fj.a(th, "ALManager", "apsLocation:callback");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void s() {
        fo foVar;
        Context context;
        int i;
        this.d.b(this.b);
        this.D.b(this.b);
        if (this.F && !this.b.getLocationMode().equals(this.q)) {
            l();
            j();
        }
        this.q = this.b.getLocationMode();
        if (this.s != null) {
            if (this.b.isOnceLocation()) {
                foVar = this.s;
                context = this.C;
                i = 0;
            } else {
                foVar = this.s;
                context = this.C;
                i = 1;
            }
            foVar.a(context, i);
            this.s.a(this.C, this.b);
        }
    }

    private boolean t() {
        int iB;
        if (fq.j(this.C)) {
            try {
                iB = fm.b(((Application) this.C.getApplicationContext()).getBaseContext(), "checkSelfPermission", "android.permission.FOREGROUND_SERVICE");
            } catch (Throwable unused) {
                iB = -1;
            }
            if (iB != 0) {
                return false;
            }
        }
        return true;
    }

    public final void a(int i, Notification notification) {
        if (i == 0 || notification == null) {
            return;
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putInt(com.igexin.push.core.d.d.e, i);
            bundle.putParcelable(al.g, notification);
            a(1023, bundle, 0L);
        } catch (Throwable th) {
            fj.a(th, "ALManager", "disableBackgroundLocation");
        }
    }

    public final void a(WebView webView) {
        if (this.L == null) {
            this.L = new i(this.C, webView);
        }
        this.L.a();
    }

    public final void a(AMapLocationClientOption aMapLocationClientOption) {
        try {
            this.K = aMapLocationClientOption.m7clone();
            a(PointerIconCompat.TYPE_ZOOM_IN, aMapLocationClientOption.m7clone(), 0L);
        } catch (Throwable th) {
            fj.a(th, "ALManager", "setLocationOption");
        }
    }

    public final void a(AMapLocationListener aMapLocationListener) {
        try {
            a(1002, aMapLocationListener, 0L);
        } catch (Throwable th) {
            fj.a(th, "ALManager", "setLocationListener");
        }
    }

    public final void a(boolean z) {
        try {
            Bundle bundle = new Bundle();
            bundle.putBoolean(al.j, z);
            a(1024, bundle, 0L);
        } catch (Throwable th) {
            fj.a(th, "ALManager", "disableBackgroundLocation");
        }
    }

    public final boolean a() {
        return this.E;
    }

    public final void b() {
        try {
            if (this.K.getCacheCallBack() && this.c != null) {
                this.c.sendEmptyMessageDelayed(13, this.K.getCacheCallBackTime());
            }
        } catch (Throwable unused) {
        }
        try {
            a(1003, (Object) null, 0L);
        } catch (Throwable th) {
            fj.a(th, "ALManager", "startLocation");
        }
    }

    public final void b(AMapLocationListener aMapLocationListener) {
        try {
            a(AVError.AV_ERR_TIMEOUT, aMapLocationListener, 0L);
        } catch (Throwable th) {
            fj.a(th, "ALManager", "unRegisterLocationListener");
        }
    }

    public final void c() {
        try {
            a(1004, (Object) null, 0L);
        } catch (Throwable th) {
            fj.a(th, "ALManager", "stopLocation");
        }
    }

    public final void d() {
        try {
            if (this.L != null) {
                this.L.b();
                this.L = null;
            }
            a(PointerIconCompat.TYPE_COPY, (Object) null, 0L);
            this.p = true;
        } catch (Throwable th) {
            fj.a(th, "ALManager", "onDestroy");
        }
    }

    public final AMapLocation e() {
        AMapLocation aMapLocation = null;
        try {
            if (this.j != null) {
                AMapLocation aMapLocationB = this.j.b();
                if (aMapLocationB == null) {
                    return aMapLocationB;
                }
                try {
                    aMapLocationB.setTrustedLevel(3);
                    return aMapLocationB;
                } catch (Throwable th) {
                    aMapLocation = aMapLocationB;
                    th = th;
                    fj.a(th, "ALManager", "getLastKnownLocation");
                    return aMapLocation;
                }
            }
        } catch (Throwable th2) {
            th = th2;
        }
        return aMapLocation;
    }

    public final void f() {
        try {
            if (this.L != null) {
                this.L.b();
                this.L = null;
            }
        } catch (Throwable th) {
            fj.a(th, "ALManager", "stopAssistantLocation");
        }
    }

    final void g() {
        a(12, (Bundle) null);
        this.h = true;
        this.i = true;
        this.E = false;
        this.t = false;
        l();
        if (this.s != null) {
            this.s.b(this.C);
        }
        fn.a(this.C).a();
        fo.a(this.C);
        if (this.u != null) {
            this.u.b().sendEmptyMessage(11);
        } else if (this.M != null) {
            this.C.unbindService(this.M);
        }
        try {
            if (this.B) {
                this.C.stopService(q());
            }
        } catch (Throwable unused) {
        }
        this.B = false;
        if (this.e != null) {
            this.e.clear();
            this.e = null;
        }
        this.M = null;
        h();
        if (this.o != null) {
            if (Build.VERSION.SDK_INT >= 18) {
                try {
                    fm.a(this.o, (Class<?>) HandlerThread.class, "quitSafely", new Object[0]);
                } catch (Throwable unused2) {
                    this.o.quit();
                }
            } else {
                this.o.quit();
            }
        }
        this.o = null;
        if (this.c != null) {
            this.c.removeCallbacksAndMessages(null);
        }
        if (this.j != null) {
            this.j.c();
            this.j = null;
        }
    }
}
