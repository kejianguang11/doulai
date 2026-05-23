package com.loc;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.view.MotionEventCompat;
import com.loc.bd;
import com.loc.cs;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.crypto.KeyGenerator;

/* JADX INFO: loaded from: classes.dex */
public final class ek implements ee {
    private static long k;
    Context a;
    Cdo d;
    private Handler g;
    private LocationManager h;
    private a i;
    private ArrayList<cu> f = new ArrayList<>();
    ex b = null;
    et c = null;
    private volatile boolean j = false;
    bn e = new bn();

    static class a implements LocationListener {
        private ek a;

        a(ek ekVar) {
            this.a = ekVar;
        }

        final void a() {
            this.a = null;
        }

        final void a(ek ekVar) {
            this.a = ekVar;
        }

        @Override // android.location.LocationListener
        public final void onLocationChanged(Location location) {
            try {
                if (this.a != null) {
                    this.a.a(location);
                }
            } catch (Throwable unused) {
            }
        }

        @Override // android.location.LocationListener
        public final void onProviderDisabled(String str) {
        }

        @Override // android.location.LocationListener
        public final void onProviderEnabled(String str) {
        }

        @Override // android.location.LocationListener
        public final void onStatusChanged(String str, int i, Bundle bundle) {
        }
    }

    class b extends ck {
        private int b;
        private Location c;

        b(int i) {
            this.b = 0;
            this.b = i;
        }

        b(ek ekVar, Location location) {
            this(1);
            this.c = location;
        }

        private void b() {
            try {
                new Object[1][0] = "Cl | dl -- dc";
                if (this.c != null && ek.this.j) {
                    if (fq.m(ek.this.a)) {
                        new Object[1][0] = "Cl | dl -- c_perm_dc";
                        return;
                    }
                    Bundle extras = this.c.getExtras();
                    int i = extras != null ? extras.getInt("satellites") : 0;
                    if (fq.a(this.c, i)) {
                        return;
                    }
                    if (ek.this.b != null && !ek.this.b.s) {
                        ek.this.b.f();
                    }
                    ArrayList<dy> arrayListA = ek.this.b.a();
                    List<dr> listA = ek.this.c.a();
                    cs.a aVar = new cs.a();
                    dx dxVar = new dx();
                    dxVar.i = this.c.getAccuracy();
                    dxVar.f = this.c.getAltitude();
                    dxVar.d = this.c.getLatitude();
                    dxVar.h = this.c.getBearing();
                    dxVar.e = this.c.getLongitude();
                    dxVar.j = this.c.isFromMockProvider();
                    dxVar.a = this.c.getProvider();
                    dxVar.g = this.c.getSpeed();
                    dxVar.l = (byte) i;
                    dxVar.b = System.currentTimeMillis();
                    dxVar.c = this.c.getTime();
                    dxVar.k = this.c.getTime();
                    aVar.a = dxVar;
                    aVar.b = arrayListA;
                    WifiInfo wifiInfoC = ek.this.b.c();
                    if (wifiInfoC != null) {
                        aVar.c = dy.a(wifiInfoC.getBSSID());
                    }
                    aVar.d = ex.A;
                    aVar.f = this.c.getTime();
                    aVar.g = (byte) o.n(ek.this.a);
                    aVar.h = o.s(ek.this.a);
                    aVar.e = ek.this.b.k();
                    aVar.j = fq.a(ek.this.a);
                    aVar.i = listA;
                    cu cuVarA = Cdo.a(aVar);
                    if (cuVarA == null) {
                        return;
                    }
                    synchronized (ek.this.f) {
                        ek.this.f.add(cuVarA);
                        if (ek.this.f.size() >= 5) {
                            ek.this.e();
                        }
                    }
                    ek.this.d();
                }
            } catch (Throwable th) {
                fj.a(th, "cl", "coll");
            }
        }

        private void c() throws Throwable {
            bd bdVarA;
            new Object[1][0] = "Cl | dl -- duts";
            if (fq.m(ek.this.a)) {
                new Object[1][0] = "Cl | dl -- c_perm_duts";
                return;
            }
            bd bdVar = null;
            try {
                try {
                    long unused = ek.k = System.currentTimeMillis();
                    if (ek.this.e.f.c()) {
                        bdVarA = bd.a(new File(ek.this.e.a), ek.this.e.b);
                        try {
                            ArrayList arrayList = new ArrayList();
                            byte[] bArrF = ek.f();
                            if (bArrF == null) {
                                try {
                                    bdVarA.close();
                                    return;
                                } catch (Throwable unused2) {
                                    return;
                                }
                            }
                            List listB = ek.b(bdVarA, ek.this.e, arrayList, bArrF);
                            if (listB != null && listB.size() != 0) {
                                ek.this.e.f.a(true);
                                if (Cdo.a(x.b(Cdo.a(ey.a(bArrF), p.b(bArrF, Cdo.a(), x.c()), listB)))) {
                                    ek.b(bdVarA, arrayList);
                                }
                                bdVar = bdVarA;
                            }
                            try {
                                bdVarA.close();
                                return;
                            } catch (Throwable unused3) {
                                return;
                            }
                        } catch (Throwable th) {
                            th = th;
                            bdVar = bdVarA;
                            an.b(th, "leg", "uts");
                            if (bdVar != null) {
                                try {
                                    bdVar.close();
                                    return;
                                } catch (Throwable unused4) {
                                    return;
                                }
                            }
                            return;
                        }
                    }
                    if (bdVar != null) {
                        try {
                            bdVar.close();
                        } catch (Throwable unused5) {
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                bdVarA = bdVar;
            }
        }

        @Override // com.loc.ck
        public final void a() throws Throwable {
            if (this.b == 1) {
                b();
            } else if (this.b == 2) {
                c();
            } else if (this.b == 3) {
                ek.this.g();
            }
        }
    }

    ek(Context context) {
        this.a = null;
        this.a = context;
        bt.a(this.a, this.e, al.k, 100, 1024000, "0");
        bn bnVar = this.e;
        int i = fi.g;
        boolean z = fi.e;
        int i2 = fi.f;
        bnVar.f = new cf(context, i, "kKey", new cd(context, z, i2, i2 * 10, "carrierLocKey"));
        this.e.e = new aw();
    }

    private static int a(byte[] bArr) {
        return ((bArr[0] & 255) << 24) | (bArr[3] & 255) | ((bArr[2] & 255) << 8) | ((bArr[1] & 255) << 16);
    }

    private static byte[] a(int i) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            if (keyGenerator == null) {
                return null;
            }
            keyGenerator.init(i);
            return keyGenerator.generateKey().getEncoded();
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00e0, code lost:
    
        if (r10 == null) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00e2, code lost:
    
        r10.close();
     */
    /* JADX WARN: Removed duplicated region for block: B:107:0x011b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:111:0x003b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:119:0x00d8 A[EXC_TOP_SPLITTER, PHI: r5 r9 r16
      0x00d8: PHI (r5v3 int) = (r5v4 int), (r5v6 int) binds: [B:81:0x011e, B:55:0x00d6] A[DONT_GENERATE, DONT_INLINE]
      0x00d8: PHI (r9v3 com.loc.bd$b) = (r9v4 com.loc.bd$b), (r9v5 com.loc.bd$b) binds: [B:81:0x011e, B:55:0x00d6] A[DONT_GENERATE, DONT_INLINE]
      0x00d8: PHI (r16v3 java.lang.String[]) = (r16v4 java.lang.String[]), (r16v8 java.lang.String[]) binds: [B:81:0x011e, B:55:0x00d6] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:147:0x012b A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static List<cu> b(bd bdVar, bn bnVar, List<String> list, byte[] bArr) {
        String[] list2;
        String[] strArr;
        bd.b bVarA;
        InputStream inputStreamA;
        String str;
        int iB;
        byte[] bArrA;
        byte[] bArr2;
        ArrayList arrayList = new ArrayList();
        try {
            File fileB = bdVar.b();
            if (fileB == null || !fileB.exists() || (list2 = fileB.list()) == null) {
                return arrayList;
            }
            int length = list2.length;
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                String str2 = list2[i2];
                if (str2.contains(".0")) {
                    InputStream inputStream = null;
                    try {
                        try {
                            str = str2.split("\\.")[i];
                        } catch (Throwable th) {
                            th = th;
                            bVarA = null;
                            inputStreamA = null;
                        }
                    } catch (Throwable unused) {
                    }
                    try {
                        bVarA = bdVar.a(str);
                    } catch (Throwable unused2) {
                        strArr = list2;
                        bVarA = null;
                        if (inputStream != null) {
                        }
                        if (bVarA != null) {
                        }
                        i2++;
                        list2 = strArr;
                        i = 0;
                    }
                    if (bVarA != null) {
                        try {
                            inputStreamA = bVarA.a();
                        } catch (Throwable unused3) {
                            strArr = list2;
                        }
                        if (inputStreamA == null) {
                            if (inputStreamA != null) {
                                try {
                                    inputStreamA.close();
                                } catch (Throwable unused4) {
                                }
                            }
                            if (bVarA != null) {
                            }
                        } else {
                            try {
                                try {
                                    byte[] bArr3 = new byte[2];
                                    inputStreamA.read(bArr3);
                                    iB = fq.b(bArr3);
                                } catch (Throwable th2) {
                                    th = th2;
                                    if (inputStreamA != null) {
                                        try {
                                            inputStreamA.close();
                                        } catch (Throwable unused5) {
                                        }
                                    }
                                    if (bVarA == null) {
                                        throw th;
                                    }
                                    try {
                                        bVarA.close();
                                        throw th;
                                    } catch (Throwable unused6) {
                                        throw th;
                                    }
                                    return arrayList;
                                }
                            } catch (Throwable unused7) {
                                strArr = list2;
                            }
                            if (iB == 0 || iB > 65535) {
                                break;
                            }
                            byte[] bArr4 = new byte[iB];
                            inputStreamA.read(bArr4);
                            byte[] bArr5 = new byte[2];
                            int length2 = i;
                            while (inputStreamA.read(bArr5) >= 0) {
                                try {
                                    byte[] bArr6 = new byte[fq.b(bArr5)];
                                    inputStreamA.read(bArr6);
                                    bArrA = p.a(bArr4, bArr6, x.c());
                                    length2 += bArrA.length;
                                    bArr2 = new byte[4];
                                    inputStreamA.read(bArr2);
                                    strArr = list2;
                                } catch (Throwable unused8) {
                                    strArr = list2;
                                }
                                try {
                                    arrayList.add(new cu(a(bArr2), p.b(bArr, x.b(bArrA), x.c())));
                                    list2 = strArr;
                                } catch (Throwable unused9) {
                                    inputStream = inputStreamA;
                                    if (inputStream != null) {
                                        try {
                                            inputStream.close();
                                        } catch (Throwable unused10) {
                                        }
                                    }
                                    if (bVarA != null) {
                                    }
                                    i2++;
                                    list2 = strArr;
                                    i = 0;
                                }
                            }
                            strArr = list2;
                            i3 += length2;
                            try {
                                list.add(str);
                            } catch (Throwable unused11) {
                            }
                            try {
                            } catch (Throwable unused12) {
                                inputStream = inputStreamA;
                                if (inputStream != null) {
                                }
                                if (bVarA != null) {
                                }
                            }
                            if (i3 > bnVar.f.b()) {
                                if (inputStreamA != null) {
                                    try {
                                        inputStreamA.close();
                                    } catch (Throwable unused13) {
                                    }
                                }
                                if (bVarA != null) {
                                }
                            } else {
                                if (inputStreamA != null) {
                                    try {
                                        inputStreamA.close();
                                    } catch (Throwable unused14) {
                                    }
                                }
                                if (bVarA != null) {
                                    try {
                                        bVarA.close();
                                    } catch (Throwable unused15) {
                                    }
                                }
                            }
                        }
                    } else if (bVarA != null) {
                        try {
                            bVarA.close();
                        } catch (Throwable unused16) {
                        }
                    }
                    i2++;
                    list2 = strArr;
                    i = 0;
                }
                strArr = list2;
                i2++;
                list2 = strArr;
                i = 0;
            }
            try {
                bVarA.close();
            } catch (Throwable unused17) {
            }
            return arrayList;
        } catch (Throwable th3) {
            an.b(th3, "aps", "upc");
        }
        return arrayList;
        if (bVarA != null) {
            bVarA.close();
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(bd bdVar, List<String> list) {
        if (bdVar != null) {
            try {
                Iterator<String> it = list.iterator();
                while (it.hasNext()) {
                    bdVar.c(it.next());
                }
                bdVar.close();
            } catch (Throwable th) {
                an.b(th, "aps", "dlo");
            }
        }
    }

    private static byte[] b(int i) {
        return new byte[]{(byte) ((i >> 24) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 8) & 255), (byte) (i & 255)};
    }

    private static byte[] c(int i) {
        return new byte[]{(byte) ((i & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8), (byte) (i & 255)};
    }

    static /* synthetic */ byte[] f() {
        return a(128);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        try {
            new Object[1][0] = "Cl | dl -- wtd";
            if (fq.m(this.a)) {
                new Object[1][0] = "Cl | dl -- c_perm_wtd";
                return;
            }
            if (this.f != null && this.f.size() != 0) {
                ArrayList<cu> arrayList = new ArrayList();
                synchronized (this.f) {
                    arrayList.addAll(this.f);
                    this.f.clear();
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArrA = a(256);
                if (bArrA == null) {
                    return;
                }
                byteArrayOutputStream.write(c(bArrA.length));
                byteArrayOutputStream.write(bArrA);
                for (cu cuVar : arrayList) {
                    byte[] bArrB = cuVar.b();
                    if (bArrB.length >= 10 && bArrB.length <= 65535) {
                        byte[] bArrB2 = p.b(bArrA, bArrB, x.c());
                        byteArrayOutputStream.write(c(bArrB2.length));
                        byteArrayOutputStream.write(bArrB2);
                        byteArrayOutputStream.write(b(cuVar.a()));
                    }
                }
                bo.a(Long.toString(System.currentTimeMillis()), byteArrayOutputStream.toByteArray(), this.e);
            }
        } catch (Throwable th) {
            fj.a(th, "clm", "wtD");
        }
    }

    @Override // com.loc.ee
    public final ed a(ec ecVar) {
        try {
            fd fdVar = new fd();
            fdVar.a(ecVar.b);
            fdVar.b(ecVar.a);
            fdVar.a(ecVar.d);
            bg.a();
            bm bmVarA = bg.a(fdVar);
            ed edVar = new ed();
            edVar.c = bmVarA.a;
            edVar.b = bmVarA.b;
            edVar.a = 200;
            return edVar;
        } catch (Throwable unused) {
            return null;
        }
    }

    final void a() {
        if (fq.m(this.a)) {
            new Object[1][0] = "Cl | dl -- c_perm_spc";
            return;
        }
        try {
            if (this.i != null && this.h != null) {
                this.h.removeUpdates(this.i);
            }
            if (this.i != null) {
                this.i.a();
            }
            if (this.j) {
                g();
                this.b.a((ek) null);
                this.c.a((ek) null);
                this.c = null;
                this.b = null;
                this.g = null;
                this.j = false;
            }
        } catch (Throwable th) {
            fj.a(th, "clm", "stc");
        }
    }

    public final void a(Location location) {
        try {
            if (this.g != null) {
                this.g.post(new b(this, location));
            }
        } catch (Throwable th) {
            an.b(th, "cl", "olcc");
        }
    }

    public final void a(et etVar, ex exVar, Handler handler) {
        new Object[1][0] = "Cl | dl -- sc";
        if (this.j || etVar == null || exVar == null || handler == null) {
            return;
        }
        if (fq.m(this.a)) {
            new Object[1][0] = "Cl | dl -- c_perm_sc";
            return;
        }
        this.j = true;
        this.c = etVar;
        this.b = exVar;
        this.b.a(this);
        this.c.a(this);
        this.g = handler;
        try {
            if (this.h == null && this.g != null) {
                this.h = (LocationManager) this.a.getSystemService("location");
            }
            if (this.i == null) {
                this.i = new a(this);
            }
            this.i.a(this);
            if (this.i != null && this.h != null) {
                this.h.requestLocationUpdates("passive", 1000L, -1.0f, this.i);
            }
            if (this.d == null) {
                this.d = new Cdo("6.1.0", l.f(this.a), "S128DF1572465B890OE3F7A13167KLEI", l.c(this.a), this);
                this.d.a(o.v(this.a)).b(o.h(this.a)).c(o.a(this.a)).d(o.g(this.a)).e(o.y(this.a)).f(o.i(this.a)).g(Build.MODEL).h(Build.MANUFACTURER).i(Build.BRAND).a(Build.VERSION.SDK_INT).j(Build.VERSION.RELEASE).a(dy.a(o.k(this.a))).k(o.k(this.a));
                Cdo.b();
            }
        } catch (Throwable th) {
            fj.a(th, "col", "init");
        }
    }

    public final void b() {
        try {
            new Object[1][0] = "Cl | dl -- uwf";
            if (this.g != null) {
                this.g.post(new Runnable() { // from class: com.loc.ek.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        try {
                            if (ek.this.d == null || ek.this.b == null) {
                                return;
                            }
                            Cdo.b(ek.this.b.a());
                        } catch (Throwable th) {
                            fj.a(th, "cl", "upwr");
                        }
                    }
                });
            }
        } catch (Throwable th) {
            fj.a(th, "cl", "upw");
        }
    }

    public final void c() {
        try {
            new Object[1][0] = "Cl | dl -- uc";
            if (this.d == null || this.c == null) {
                return;
            }
            Cdo.a(this.c.a());
        } catch (Throwable th) {
            fj.a(th, "cl", "upc");
        }
    }

    public final void d() {
        try {
            if (fq.m(this.a)) {
                new Object[1][0] = "Cl | dl -- c_perm_uts";
            } else {
                if (System.currentTimeMillis() - k < 60000) {
                    return;
                }
                cj.a().b(new b(2));
            }
        } catch (Throwable unused) {
        }
    }

    public final void e() {
        try {
            cj.a().b(new b(3));
        } catch (Throwable unused) {
        }
    }
}
