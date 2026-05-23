package com.loc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityNr;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoNr;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.CellSignalStrengthNr;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.core.internal.view.SupportMenu;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"NewApi"})
public final class et {
    TelephonyManager b;
    SignalStrength d;
    private Context h;
    private er m;

    @SuppressLint({"NewApi"})
    private TelephonyManager.CellInfoCallback q;
    private ek u;
    private boolean i = false;
    private boolean j = false;
    ArrayList<es> a = new ArrayList<>();
    private String k = null;
    private ArrayList<es> l = new ArrayList<>();
    private long n = 0;
    PhoneStateListener c = null;
    private boolean o = false;
    private Object p = new Object();
    private boolean r = false;
    boolean e = false;
    StringBuilder f = null;
    private String s = null;
    private String t = null;
    String g = null;

    @SuppressLint({"NewApi"})
    class a extends TelephonyManager.CellInfoCallback {
        a() {
        }

        @Override // android.telephony.TelephonyManager.CellInfoCallback
        public final void onCellInfo(List<CellInfo> list) {
            try {
                if (fq.b() - et.this.n < 500) {
                    return;
                }
                et.b(et.this);
                et.this.a(et.this.t());
                et.this.a(list);
                et.this.n = fq.b();
            } catch (SecurityException e) {
                et.this.g = e.getMessage();
            } catch (Throwable th) {
                fj.a(th, "Cgi", "cellInfo");
            }
        }
    }

    class b extends PhoneStateListener {
        b() {
        }

        @Override // android.telephony.PhoneStateListener
        public final void onCellInfoChanged(List<CellInfo> list) {
            try {
                if (et.this.u != null) {
                    et.this.u.c();
                }
                if (fq.b() - et.this.n < 500) {
                    return;
                }
                et.this.a(et.this.t());
                et.this.a(list);
                et.this.n = fq.b();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }

        @Override // android.telephony.PhoneStateListener
        public final void onCellLocationChanged(CellLocation cellLocation) {
            if (fq.b() - et.this.n < 500) {
                return;
            }
            try {
                et.this.a(cellLocation);
                et.this.a(et.this.u());
                et.this.n = fq.b();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }

        @Override // android.telephony.PhoneStateListener
        public final void onDataConnectionStateChanged(int i) {
            super.onDataConnectionStateChanged(i);
        }

        @Override // android.telephony.PhoneStateListener
        public final void onServiceStateChanged(ServiceState serviceState) {
            try {
                switch (serviceState.getState()) {
                    case 0:
                        et.this.a(false, false);
                        break;
                    case 1:
                        et.this.j();
                        break;
                }
            } catch (Throwable unused) {
            }
        }

        @Override // android.telephony.PhoneStateListener
        public final void onSignalStrengthChanged(int i) {
            super.onSignalStrengthChanged(i);
        }

        @Override // android.telephony.PhoneStateListener
        public final void onSignalStrengthsChanged(SignalStrength signalStrength) {
            if (signalStrength == null) {
                return;
            }
            et.this.d = signalStrength;
            try {
                if (et.this.u != null) {
                    et.this.u.c();
                }
            } catch (Throwable unused) {
            }
        }
    }

    public et(Context context, Handler handler) {
        this.b = null;
        this.m = null;
        this.h = context;
        if (this.b == null) {
            this.b = (TelephonyManager) fq.a(this.h, "phone");
        }
        o();
        this.m = new er(context, "cellAge", handler);
        this.m.a();
    }

    private static es a(int i, boolean z, int i2, int i3, int i4, int i5, int i6) {
        es esVar = new es(i, z);
        esVar.a = i2;
        esVar.b = i3;
        esVar.c = i4;
        esVar.d = i5;
        esVar.k = i6;
        return esVar;
    }

    private es a(CellInfoCdma cellInfoCdma, boolean z) {
        int i;
        int i2;
        if (cellInfoCdma != null && cellInfoCdma.getCellIdentity() != null) {
            CellIdentityCdma cellIdentity = cellInfoCdma.getCellIdentity();
            if (cellIdentity.getSystemId() > 0 && cellIdentity.getNetworkId() >= 0 && cellIdentity.getBasestationId() >= 0) {
                CellIdentityCdma cellIdentity2 = cellInfoCdma.getCellIdentity();
                String[] strArrA = fq.a(this.b);
                try {
                    i = Integer.parseInt(strArrA[0]);
                    try {
                        i2 = Integer.parseInt(strArrA[1]);
                    } catch (Throwable unused) {
                        i2 = 0;
                    }
                } catch (Throwable unused2) {
                    i = 0;
                }
                es esVarA = a(2, z, i, i2, 0, 0, cellInfoCdma.getCellSignalStrength().getCdmaDbm());
                esVarA.h = cellIdentity2.getSystemId();
                esVarA.i = cellIdentity2.getNetworkId();
                esVarA.j = cellIdentity2.getBasestationId();
                esVarA.f = cellIdentity2.getLatitude();
                esVarA.g = cellIdentity2.getLongitude();
                esVarA.s = cellInfoCdma.getCellSignalStrength().getCdmaDbm();
                return esVarA;
            }
        }
        return null;
    }

    @SuppressLint({"NewApi"})
    private static es a(CellInfoGsm cellInfoGsm, boolean z) {
        if (cellInfoGsm == null || cellInfoGsm.getCellIdentity() == null) {
            return null;
        }
        CellIdentityGsm cellIdentity = cellInfoGsm.getCellIdentity();
        es esVarA = a(1, z, cellIdentity.getMcc(), cellIdentity.getMnc(), cellIdentity.getLac(), cellIdentity.getCid(), cellInfoGsm.getCellSignalStrength().getDbm());
        esVarA.o = cellInfoGsm.getCellIdentity().getBsic();
        esVarA.p = cellInfoGsm.getCellIdentity().getArfcn();
        esVarA.q = cellInfoGsm.getCellSignalStrength().getTimingAdvance();
        esVarA.s = cellInfoGsm.getCellSignalStrength().getDbm();
        return esVarA;
    }

    private static es a(CellInfoLte cellInfoLte, boolean z) {
        if (cellInfoLte == null || cellInfoLte.getCellIdentity() == null) {
            return null;
        }
        CellIdentityLte cellIdentity = cellInfoLte.getCellIdentity();
        es esVarA = a(3, z, cellIdentity.getMcc(), cellIdentity.getMnc(), cellIdentity.getTac(), cellIdentity.getCi(), cellInfoLte.getCellSignalStrength().getDbm());
        esVarA.o = cellIdentity.getPci();
        if (Build.VERSION.SDK_INT >= 24) {
            esVarA.p = cellIdentity.getEarfcn();
        }
        esVarA.q = cellInfoLte.getCellSignalStrength().getTimingAdvance();
        esVarA.s = cellInfoLte.getCellSignalStrength().getDbm();
        return esVarA;
    }

    private static es a(CellInfoNr cellInfoNr, boolean z) throws Exception {
        int i;
        int i2;
        int i3;
        if (cellInfoNr == null || cellInfoNr.getCellIdentity() == null) {
            return null;
        }
        CellIdentityNr cellIdentityNr = (CellIdentityNr) cellInfoNr.getCellIdentity();
        int tac = cellIdentityNr.getTac();
        if (tac == Integer.MAX_VALUE && "HUAWEI".equals(Build.MANUFACTURER)) {
            try {
                tac = fm.b(cellIdentityNr, "getHwTac", new Object[0]);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        long nci = cellIdentityNr.getNci();
        try {
            i = Integer.parseInt(cellIdentityNr.getMccString());
        } catch (Throwable th2) {
            th = th2;
            i = 0;
        }
        try {
            i2 = i;
            i3 = Integer.parseInt(cellIdentityNr.getMncString());
        } catch (Throwable th3) {
            th = th3;
            th.printStackTrace();
            i2 = i;
            i3 = 0;
        }
        es esVarA = a(5, z, i2, i3, cellIdentityNr.getTac(), 0, ((CellSignalStrengthNr) cellInfoNr.getCellSignalStrength()).getSsRsrp());
        esVarA.e = nci;
        if (tac > 16777215) {
            esVarA.c = SupportMenu.USER_MASK;
        } else if (tac > 65535) {
            esVarA.c = SupportMenu.USER_MASK;
            esVarA.q = tac;
        } else {
            esVarA.c = tac;
        }
        esVarA.o = cellIdentityNr.getPci();
        esVarA.p = cellIdentityNr.getNrarfcn();
        esVarA.s = cellInfoNr.getCellSignalStrength().getDbm();
        return esVarA;
    }

    private static es a(CellInfoWcdma cellInfoWcdma, boolean z) {
        if (cellInfoWcdma == null || cellInfoWcdma.getCellIdentity() == null) {
            return null;
        }
        CellIdentityWcdma cellIdentity = cellInfoWcdma.getCellIdentity();
        es esVarA = a(4, z, cellIdentity.getMcc(), cellIdentity.getMnc(), cellIdentity.getLac(), cellIdentity.getCid(), cellInfoWcdma.getCellSignalStrength().getDbm());
        esVarA.o = cellIdentity.getPsc();
        esVarA.p = cellInfoWcdma.getCellIdentity().getUarfcn();
        esVarA.s = cellInfoWcdma.getCellSignalStrength().getDbm();
        return esVarA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(CellLocation cellLocation) {
        String[] strArrA = fq.a(this.b);
        this.a.clear();
        if (cellLocation instanceof GsmCellLocation) {
            GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
            es esVar = new es(1, true);
            esVar.a = fq.e(strArrA[0]);
            esVar.b = fq.e(strArrA[1]);
            esVar.c = gsmCellLocation.getLac();
            esVar.d = gsmCellLocation.getCid();
            if (this.d != null) {
                int gsmSignalStrength = this.d.getGsmSignalStrength();
                esVar.s = gsmSignalStrength == 99 ? ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED : b(gsmSignalStrength);
            }
            esVar.r = false;
            this.m.a(esVar);
            this.a.add(esVar);
            return;
        }
        if (cellLocation instanceof CdmaCellLocation) {
            CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
            es esVar2 = new es(2, true);
            esVar2.a = Integer.parseInt(strArrA[0]);
            esVar2.b = Integer.parseInt(strArrA[1]);
            esVar2.f = cdmaCellLocation.getBaseStationLatitude();
            esVar2.g = cdmaCellLocation.getBaseStationLongitude();
            esVar2.h = cdmaCellLocation.getSystemId();
            esVar2.i = cdmaCellLocation.getNetworkId();
            esVar2.j = cdmaCellLocation.getBaseStationId();
            if (this.d != null) {
                esVar2.s = this.d.getCdmaDbm();
            }
            esVar2.r = false;
            this.m.a(esVar2);
            this.a.add(esVar2);
        }
    }

    public static boolean a(int i) {
        return i > 0 && i <= 15;
    }

    private static int b(int i) {
        return (i * 2) - 113;
    }

    @SuppressLint({"NewApi"})
    private void b(boolean z, boolean z2) {
        if (!this.e && this.b != null && Build.VERSION.SDK_INT >= 29 && this.h.getApplicationInfo().targetSdkVersion >= 29) {
            if (this.q == null) {
                this.q = new a();
            }
            this.b.requestCellInfoUpdate(cj.a().b(), this.q);
            if (z2 || z) {
                for (int i = 0; !this.r && i < 20; i++) {
                    try {
                        Thread.sleep(5L);
                    } catch (Throwable unused) {
                    }
                }
            }
        }
        this.j = false;
        if (this.b != null) {
            this.k = this.b.getNetworkOperator();
            if (!TextUtils.isEmpty(this.k)) {
                this.j = true;
            }
        }
        this.n = fq.b();
    }

    static /* synthetic */ boolean b(et etVar) {
        etVar.r = true;
        return true;
    }

    private void o() {
        if (this.b == null) {
            return;
        }
        p();
    }

    private void p() {
        try {
            if (this.c == null) {
                this.c = new b();
            }
            int i = 336;
            if (Build.VERSION.SDK_INT >= 31) {
                if (this.h.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0) {
                    this.t = "hasFineLocPerm";
                } else {
                    this.t = "hasNoFineLocPerm";
                    i = 320;
                }
            }
            if (Build.VERSION.SDK_INT >= 17) {
                if (Build.VERSION.SDK_INT >= 31) {
                    boolean z = this.h.checkSelfPermission("android.permission.READ_PHONE_STATE") == 0;
                    boolean z2 = this.h.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0;
                    if (z && z2) {
                        i |= 1024;
                    }
                    this.s = z ? "hasReadPhoneStatePerm" : "hasNoReadPhoneStatePerm";
                    this.t = z2 ? "hasFineLocPerm" : "hasNoFineLocPerm";
                    StringBuilder sb = new StringBuilder("CgiManager | mLFLPerm = ");
                    sb.append(this.t);
                    sb.append(";mLRPSPerm = ");
                    sb.append(this.s);
                } else {
                    i |= 1024;
                }
            }
            if (this.c != null) {
                this.b.listen(this.c, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int q() {
        es esVarE = e();
        if (esVarE != null) {
            return esVarE.l;
        }
        return 0;
    }

    private CellLocation r() {
        if (this.b != null) {
            try {
                CellLocation cellLocation = this.b.getCellLocation();
                this.g = null;
                return cellLocation;
            } catch (SecurityException e) {
                this.g = e.getMessage();
            } catch (Throwable th) {
                this.g = null;
                fj.a(th, "CgiManager", "getCellLocation");
            }
        }
        return null;
    }

    private boolean s() {
        return !this.e && fq.b() - this.n >= 45000;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CellLocation t() {
        if (this.b == null) {
            return null;
        }
        return r();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"NewApi"})
    public List<CellInfo> u() {
        List<CellInfo> allCellInfo;
        try {
            if (fq.c() < 18 || this.b == null) {
                return null;
            }
            try {
                allCellInfo = this.b.getAllCellInfo();
                try {
                    this.g = null;
                } catch (SecurityException e) {
                    e = e;
                    this.g = e.getMessage();
                }
            } catch (SecurityException e2) {
                e = e2;
                allCellInfo = null;
            }
            return allCellInfo;
        } catch (Throwable th) {
            fj.a(th, "Cgi", "getNewCells");
            return null;
        }
    }

    public final List<dr> a() {
        Object obj;
        Object obj2;
        ArrayList arrayList = new ArrayList();
        List<CellInfo> allCellInfo = this.b.getAllCellInfo();
        if (Build.VERSION.SDK_INT >= 17 && allCellInfo != null) {
            for (CellInfo cellInfo : allCellInfo) {
                if (cellInfo instanceof CellInfoCdma) {
                    CellInfoCdma cellInfoCdma = (CellInfoCdma) cellInfo;
                    CellIdentityCdma cellIdentity = cellInfoCdma.getCellIdentity();
                    ds dsVar = new ds(cellInfo.isRegistered(), true);
                    dsVar.m = cellIdentity.getLatitude();
                    dsVar.n = cellIdentity.getLongitude();
                    dsVar.j = cellIdentity.getSystemId();
                    dsVar.k = cellIdentity.getNetworkId();
                    dsVar.l = cellIdentity.getBasestationId();
                    dsVar.d = cellInfoCdma.getCellSignalStrength().getAsuLevel();
                    dsVar.c = cellInfoCdma.getCellSignalStrength().getCdmaDbm();
                    obj = dsVar;
                } else {
                    if (cellInfo instanceof CellInfoGsm) {
                        CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfo;
                        CellIdentityGsm cellIdentity2 = cellInfoGsm.getCellIdentity();
                        dt dtVar = new dt(cellInfo.isRegistered(), true);
                        dtVar.a = String.valueOf(cellIdentity2.getMcc());
                        dtVar.b = String.valueOf(cellIdentity2.getMnc());
                        dtVar.j = cellIdentity2.getLac();
                        dtVar.k = cellIdentity2.getCid();
                        dtVar.c = cellInfoGsm.getCellSignalStrength().getDbm();
                        dtVar.d = cellInfoGsm.getCellSignalStrength().getAsuLevel();
                        obj2 = dtVar;
                        if (Build.VERSION.SDK_INT >= 24) {
                            dtVar.m = cellIdentity2.getArfcn();
                            dtVar.n = cellIdentity2.getBsic();
                            obj2 = dtVar;
                        }
                    } else if (cellInfo instanceof CellInfoLte) {
                        CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
                        CellIdentityLte cellIdentity3 = cellInfoLte.getCellIdentity();
                        du duVar = new du(cellInfo.isRegistered());
                        duVar.a = String.valueOf(cellIdentity3.getMcc());
                        duVar.b = String.valueOf(cellIdentity3.getMnc());
                        duVar.l = cellIdentity3.getPci();
                        duVar.d = cellInfoLte.getCellSignalStrength().getAsuLevel();
                        duVar.k = cellIdentity3.getCi();
                        duVar.j = cellIdentity3.getTac();
                        duVar.n = cellInfoLte.getCellSignalStrength().getTimingAdvance();
                        duVar.c = cellInfoLte.getCellSignalStrength().getDbm();
                        obj = duVar;
                        if (Build.VERSION.SDK_INT >= 24) {
                            duVar.m = cellIdentity3.getEarfcn();
                            obj = duVar;
                        }
                    } else if (Build.VERSION.SDK_INT >= 18 && (cellInfo instanceof CellInfoWcdma)) {
                        CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) cellInfo;
                        CellIdentityWcdma cellIdentity4 = cellInfoWcdma.getCellIdentity();
                        dv dvVar = new dv(cellInfo.isRegistered(), true);
                        dvVar.a = String.valueOf(cellIdentity4.getMcc());
                        dvVar.b = String.valueOf(cellIdentity4.getMnc());
                        dvVar.j = cellIdentity4.getLac();
                        dvVar.k = cellIdentity4.getCid();
                        dvVar.l = cellIdentity4.getPsc();
                        dvVar.d = cellInfoWcdma.getCellSignalStrength().getAsuLevel();
                        dvVar.c = cellInfoWcdma.getCellSignalStrength().getDbm();
                        obj2 = dvVar;
                        if (Build.VERSION.SDK_INT >= 24) {
                            dvVar.m = cellIdentity4.getUarfcn();
                            obj2 = dvVar;
                        }
                    }
                    arrayList.add(obj2);
                }
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public final void a(ek ekVar) {
        this.u = ekVar;
    }

    final synchronized void a(List<CellInfo> list) {
        if (this.l != null) {
            this.l.clear();
        }
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                CellInfo cellInfo = list.get(i);
                if (cellInfo != null) {
                    es esVarA = null;
                    boolean zIsRegistered = cellInfo.isRegistered();
                    if (cellInfo instanceof CellInfoCdma) {
                        esVarA = a((CellInfoCdma) cellInfo, zIsRegistered);
                    } else if (cellInfo instanceof CellInfoGsm) {
                        esVarA = a((CellInfoGsm) cellInfo, zIsRegistered);
                    } else if (cellInfo instanceof CellInfoWcdma) {
                        esVarA = a((CellInfoWcdma) cellInfo, zIsRegistered);
                    } else if (cellInfo instanceof CellInfoLte) {
                        esVarA = a((CellInfoLte) cellInfo, zIsRegistered);
                    } else if (Build.VERSION.SDK_INT >= 29 && (cellInfo instanceof CellInfoNr)) {
                        esVarA = a((CellInfoNr) cellInfo, zIsRegistered);
                    }
                    if (esVarA != null) {
                        this.m.a(esVarA);
                        esVarA.m = (short) Math.min(65535L, this.m.e(esVarA));
                        esVarA.r = true;
                        this.l.add(esVarA);
                    }
                }
            }
            this.i = false;
            if (this.l != null && this.l.size() > 0) {
                this.i = true;
            }
        }
    }

    public final void a(boolean z) {
        this.m.a(z);
        this.n = 0L;
        synchronized (this.p) {
            this.o = true;
        }
        if (this.b != null && this.c != null) {
            try {
                this.b.listen(this.c, 0);
            } catch (Throwable th) {
                fj.a(th, "CgiManager", "destroy");
            }
        }
        this.c = null;
        this.d = null;
        this.b = null;
    }

    public final void a(boolean z, boolean z2) {
        try {
            this.e = fq.a(this.h);
            if (s()) {
                b(z, z2);
                a(t());
                a(u());
            }
            if (this.e) {
                j();
            }
        } catch (SecurityException e) {
            this.g = e.getMessage();
        } catch (Throwable th) {
            fj.a(th, "CgiManager", "refresh");
        }
    }

    public final void b() {
        boolean z = false;
        try {
            if (Build.VERSION.SDK_INT >= 31) {
                String str = this.h.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0 ? "hasFineLocPerm" : "hasNoFineLocPerm";
                String str2 = this.h.checkSelfPermission("android.permission.READ_PHONE_STATE") == 0 ? "hasReadPhoneStatePerm" : "hasNoReadPhoneStatePerm";
                if (!TextUtils.isEmpty(this.t) && !this.t.equals(str)) {
                    z = true;
                }
                if (!TextUtils.isEmpty(this.s) && !this.s.equals(str2)) {
                    z = true;
                }
                if (z) {
                    p();
                }
            }
        } catch (Throwable unused) {
        }
    }

    public final synchronized ArrayList<es> c() {
        ArrayList<es> arrayList;
        arrayList = new ArrayList<>();
        if (this.a != null) {
            Iterator<es> it = this.a.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().clone());
            }
        }
        return arrayList;
    }

    public final synchronized ArrayList<es> d() {
        ArrayList<es> arrayList;
        arrayList = new ArrayList<>();
        if (this.l != null) {
            Iterator<es> it = this.l.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().clone());
            }
        }
        return arrayList;
    }

    public final synchronized es e() {
        if (this.e) {
            return null;
        }
        ArrayList<es> arrayList = this.a;
        if (arrayList.size() <= 0) {
            return null;
        }
        return arrayList.get(0).clone();
    }

    public final synchronized es f() {
        if (this.e) {
            return null;
        }
        ArrayList<es> arrayList = this.l;
        if (arrayList.size() <= 0) {
            return null;
        }
        for (es esVar : arrayList) {
            if (esVar.n) {
                return esVar.clone();
            }
        }
        return arrayList.get(0).clone();
    }

    public final int g() {
        return q() | (this.i ? 4 : 0) | (this.j ? 8 : 0);
    }

    public final int h() {
        return q() & 3;
    }

    public final TelephonyManager i() {
        return this.b;
    }

    final synchronized void j() {
        this.g = null;
        this.a.clear();
        this.l.clear();
        this.i = false;
        this.j = false;
    }

    public final String k() {
        return this.g;
    }

    public final String l() {
        return this.k;
    }

    public final synchronized String m() {
        if (this.e) {
            j();
        }
        if (this.f == null) {
            this.f = new StringBuilder();
        } else {
            this.f.delete(0, this.f.length());
        }
        if (h() == 1) {
            for (int i = 1; i < this.a.size(); i++) {
                StringBuilder sb = this.f;
                sb.append("#");
                sb.append(this.a.get(i).b);
                StringBuilder sb2 = this.f;
                sb2.append("|");
                sb2.append(this.a.get(i).c);
                StringBuilder sb3 = this.f;
                sb3.append("|");
                sb3.append(this.a.get(i).d);
            }
        }
        for (int i2 = 1; i2 < this.l.size(); i2++) {
            es esVar = this.l.get(i2);
            if (esVar.l == 1 || esVar.l == 3 || esVar.l == 4 || esVar.l == 5) {
                StringBuilder sb4 = this.f;
                sb4.append("#");
                sb4.append(esVar.l);
                StringBuilder sb5 = this.f;
                sb5.append("|");
                sb5.append(esVar.a);
                StringBuilder sb6 = this.f;
                sb6.append("|");
                sb6.append(esVar.b);
                StringBuilder sb7 = this.f;
                sb7.append("|");
                sb7.append(esVar.c);
                StringBuilder sb8 = this.f;
                sb8.append("|");
                sb8.append(esVar.a());
            } else if (esVar.l == 2) {
                StringBuilder sb9 = this.f;
                sb9.append("#");
                sb9.append(esVar.l);
                StringBuilder sb10 = this.f;
                sb10.append("|");
                sb10.append(esVar.a);
                StringBuilder sb11 = this.f;
                sb11.append("|");
                sb11.append(esVar.h);
                StringBuilder sb12 = this.f;
                sb12.append("|");
                sb12.append(esVar.i);
                StringBuilder sb13 = this.f;
                sb13.append("|");
                sb13.append(esVar.j);
            }
        }
        if (this.f.length() > 0) {
            this.f.deleteCharAt(0);
        }
        return this.f.toString();
    }

    public final boolean n() {
        try {
            if (this.b != null) {
                if (!TextUtils.isEmpty(this.b.getSimOperator())) {
                    return true;
                }
                if (!TextUtils.isEmpty(this.b.getSimCountryIso())) {
                    return true;
                }
            }
        } catch (Throwable unused) {
        }
        try {
            int iA = fq.a(fq.c(this.h));
            return iA == 0 || iA == 4 || iA == 2 || iA == 5 || iA == 3;
        } catch (Throwable unused2) {
            return false;
        }
    }
}
