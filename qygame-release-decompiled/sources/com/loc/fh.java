package com.loc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import androidx.core.internal.view.SupportMenu;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.CRC32;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"NewApi"})
public final class fh {
    protected static String I;
    protected static String K;
    public String a = "1";
    protected short b = 0;
    protected String c = null;
    protected String d = null;
    protected String e = null;
    protected String f = null;
    protected String g = null;
    public String h = null;
    public String i = null;
    protected String j = null;
    protected String k = null;
    protected String l = null;
    protected String m = null;
    protected String n = null;
    protected String o = null;
    protected String p = null;
    protected String q = null;
    protected String r = null;
    protected String s = null;
    protected String t = null;
    protected String u = null;
    protected String v = null;
    protected String w = null;
    protected String x = null;
    protected String y = null;
    protected int z = 0;
    protected ArrayList<es> A = new ArrayList<>();
    protected ArrayList<es> B = new ArrayList<>();
    protected String C = null;
    protected String D = null;
    protected ArrayList<dy> E = new ArrayList<>();
    protected String F = null;
    protected String G = null;
    protected byte[] H = null;
    private byte[] Q = null;
    private int R = 0;
    protected String J = null;
    protected String L = null;
    protected String M = null;
    protected String N = null;
    protected int O = 0;
    private List<eu> S = null;
    private List<es> T = Collections.synchronizedList(new ArrayList());
    final int P = 3;

    private static int a(String str, byte[] bArr, int i) {
        try {
        } catch (Throwable th) {
            fj.a(th, "Req", "copyContentWithByteLen");
            bArr[i] = 0;
        }
        if (TextUtils.isEmpty(str)) {
            bArr[i] = 0;
            return i + 1;
        }
        byte[] bytes = str.getBytes("GBK");
        int length = bytes.length;
        if (length > 127) {
            length = 127;
        }
        bArr[i] = (byte) length;
        int i2 = i + 1;
        System.arraycopy(bytes, 0, bArr, i2, length);
        return i2 + length;
    }

    private static void a(es esVar, List<es> list) {
        if (esVar == null || list == null) {
            return;
        }
        int size = list.size();
        if (size == 0) {
            list.add(esVar);
            return;
        }
        long jMin = Long.MAX_VALUE;
        int i = 0;
        int i2 = -1;
        int i3 = -1;
        while (true) {
            if (i >= size) {
                i2 = i3;
                break;
            }
            es esVar2 = list.get(i);
            if (esVar.c() == null || !esVar.c().equals(esVar2.c())) {
                jMin = Math.min(jMin, esVar2.t);
                if (jMin == esVar2.t) {
                    i3 = i;
                }
                i++;
            } else if (esVar.s != esVar2.s) {
                esVar2.t = esVar.t;
                esVar2.s = esVar.s;
            }
        }
        if (i2 >= 0) {
            if (size < 3) {
                list.add(esVar);
            } else {
                if (esVar.t <= jMin || i2 >= size) {
                    return;
                }
                list.remove(i2);
                list.add(esVar);
            }
        }
    }

    private void a(ArrayList<es> arrayList, ArrayList<es> arrayList2) {
        if (arrayList2 != null && arrayList2.size() > 0) {
            for (es esVar : arrayList2) {
                if (esVar.r && esVar.n) {
                    a(esVar, this.T);
                    return;
                }
            }
        }
        if (arrayList == null || arrayList.size() <= 0) {
            return;
        }
        a(arrayList.get(0), this.T);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0017 A[Catch: Throwable -> 0x0010, LOOP:0: B:10:0x0015->B:11:0x0017, LOOP_END, TryCatch #0 {Throwable -> 0x0010, blocks: (B:4:0x000c, B:13:0x001f, B:15:0x0022, B:17:0x002b, B:18:0x0033, B:9:0x0012, B:11:0x0017), top: B:22:0x000c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private byte[] a(String str) {
        int i;
        String[] strArrSplit = str.split(":");
        byte[] bArr = new byte[6];
        if (strArrSplit != null) {
            try {
                if (strArrSplit.length != 6) {
                    strArrSplit = new String[6];
                    for (i = 0; i < 6; i++) {
                        strArrSplit[i] = "0";
                    }
                }
            } catch (Throwable th) {
                fj.a(th, "Req", "getMacBa ".concat(String.valueOf(str)));
                return a("00:00:00:00:00:00");
            }
        } else {
            strArrSplit = new String[6];
            while (i < 6) {
            }
        }
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            if (strArrSplit[i2].length() > 2) {
                strArrSplit[i2] = strArrSplit[i2].substring(0, 2);
            }
            bArr[i2] = (byte) Integer.parseInt(strArrSplit[i2], 16);
        }
        return bArr;
    }

    private void b() {
        String[] strArr = new String[27];
        strArr[0] = this.a;
        strArr[1] = this.c;
        strArr[2] = this.d;
        strArr[3] = this.e;
        strArr[4] = this.f;
        strArr[5] = this.g;
        strArr[6] = this.h;
        strArr[7] = this.i;
        strArr[8] = this.l;
        strArr[9] = this.m;
        strArr[10] = this.n;
        strArr[11] = this.o;
        strArr[12] = this.p;
        strArr[13] = this.q;
        strArr[14] = this.r;
        strArr[15] = this.s;
        strArr[16] = this.t;
        strArr[17] = this.u;
        strArr[18] = this.v;
        strArr[19] = this.w;
        strArr[20] = this.x;
        strArr[21] = this.D;
        strArr[22] = this.F;
        strArr[23] = this.G;
        strArr[24] = I;
        strArr[25] = this.M;
        strArr[26] = this.N;
        for (int i = 0; i < 27; i++) {
            if (TextUtils.isEmpty(strArr[i])) {
                strArr[i] = "";
            }
        }
        if (TextUtils.isEmpty(this.j) || (!"0".equals(this.j) && !com.igexin.push.config.c.H.equals(this.j))) {
            this.j = "0";
        }
        if (TextUtils.isEmpty(this.k) || (!"0".equals(this.k) && !"1".equals(this.k))) {
            this.k = "0";
        }
        if (TextUtils.isEmpty(this.y) || (!"1".equals(this.y) && !com.igexin.push.config.c.H.equals(this.y))) {
            this.y = "0";
        }
        if (!et.a(this.z)) {
            this.z = 0;
        }
        if (this.H == null) {
            this.H = new byte[0];
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:119:0x008c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void a(Context context, boolean z, boolean z2, et etVar, ex exVar, ConnectivityManager connectivityManager, String str, ev evVar) {
        String str2;
        NetworkInfo activeNetworkInfo;
        String strA;
        String str3;
        int length;
        String strF = l.f(context);
        int iD = fq.d();
        this.J = str;
        this.S = null;
        String str4 = "api_serverSDK_130905";
        String str5 = "S128DF1572465B890OE3F7A13167KLEI";
        if (!z2) {
            str4 = "UC_nlp_20131029";
            str5 = "BKZCHMBBSSUK7U8GLUKHBB56CCFF78U";
        }
        StringBuilder sb = new StringBuilder();
        int iG = etVar.g();
        int iH = etVar.h();
        TelephonyManager telephonyManagerI = etVar.i();
        ArrayList<es> arrayListC = etVar.c();
        ArrayList<es> arrayListD = etVar.d();
        ArrayList<dy> arrayListE = exVar.e();
        String str6 = iH == 2 ? "1" : "0";
        if (telephonyManagerI == null) {
            str2 = strF;
        } else if (TextUtils.isEmpty(fj.g)) {
            try {
                fj.g = o.v(context);
                str2 = strF;
            } catch (Throwable th) {
                str2 = strF;
                fj.a(th, "Aps", "getApsReq part4");
            }
            if (TextUtils.isEmpty(fj.g) && Build.VERSION.SDK_INT < 29) {
                fj.g = "888888888888888";
            }
            if (TextUtils.isEmpty(fj.h)) {
                try {
                    fj.h = o.y(context);
                } catch (SecurityException unused) {
                } catch (Throwable th2) {
                    fj.a(th2, "Aps", "getApsReq part2");
                }
            }
            if (TextUtils.isEmpty(fj.h) && Build.VERSION.SDK_INT < 29) {
                fj.h = "888888888888888";
            }
        } else {
            str2 = strF;
            if (TextUtils.isEmpty(fj.g)) {
                fj.g = "888888888888888";
            }
            if (TextUtils.isEmpty(fj.h)) {
            }
            if (TextUtils.isEmpty(fj.h)) {
                fj.h = "888888888888888";
            }
        }
        try {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        } catch (Throwable th3) {
            fj.a(th3, "Aps", "getApsReq part");
            activeNetworkInfo = null;
        }
        boolean zA = exVar.a(connectivityManager);
        if (fq.a(activeNetworkInfo) != -1) {
            strA = fq.a(context, telephonyManagerI);
            str3 = zA ? com.igexin.push.config.c.H : "1";
        } else {
            strA = "";
            str3 = "";
        }
        if ((iG & 4) != 4 || arrayListD.isEmpty()) {
            this.B.clear();
        } else {
            this.B.clear();
            this.B.addAll(arrayListD);
        }
        this.A.clear();
        this.A.addAll(arrayListC);
        StringBuilder sb2 = new StringBuilder();
        if (exVar.k()) {
            if (zA) {
                WifiInfo wifiInfoM = exVar.m();
                if (ex.a(wifiInfoM)) {
                    sb2.append(wifiInfoM.getBSSID());
                    sb2.append(com.igexin.push.core.b.an);
                    int rssi = wifiInfoM.getRssi();
                    if (rssi < -128 || rssi > 127) {
                        rssi = 0;
                    }
                    sb2.append(rssi);
                    sb2.append(com.igexin.push.core.b.an);
                    String ssid = wifiInfoM.getSSID();
                    try {
                        length = wifiInfoM.getSSID().getBytes(com.alipay.sdk.sys.a.m).length;
                    } catch (Exception unused2) {
                        length = 32;
                    }
                    if (length >= 32) {
                        ssid = "unkwn";
                    }
                    sb2.append(ssid.replace("*", "."));
                }
            }
            if (arrayListE != null && this.E != null) {
                this.E.clear();
                this.E.addAll(arrayListE);
            }
        } else {
            exVar.g();
            if (this.E != null) {
                this.E.clear();
            }
        }
        this.b = (short) 0;
        if (!z) {
            this.b = (short) (this.b | 2);
        }
        this.c = str4;
        this.d = str5;
        this.f = Build.MODEL;
        this.g = "android" + Build.VERSION.RELEASE;
        this.h = fq.b(context);
        this.i = str6;
        this.j = "0";
        this.k = "0";
        this.l = "0";
        this.m = "0";
        this.n = "0";
        this.o = str2;
        this.p = fj.g;
        this.q = fj.h;
        this.s = String.valueOf(iD);
        this.t = fq.i(context);
        this.v = "6.1.0";
        this.w = null;
        this.u = "";
        this.x = strA;
        this.y = str3;
        this.z = iG;
        this.C = etVar.l();
        this.F = ex.p();
        this.D = sb2.toString();
        this.O = (int) ((fq.b() - exVar.q()) / 1000);
        try {
            if (TextUtils.isEmpty(I)) {
                I = o.h(context);
            }
        } catch (Throwable unused3) {
        }
        try {
            if (TextUtils.isEmpty(K)) {
                K = o.a(context);
            }
        } catch (Throwable unused4) {
        }
        try {
            if (TextUtils.isEmpty(this.M)) {
                this.M = o.i(context);
            }
        } catch (Throwable unused5) {
        }
        try {
            if (TextUtils.isEmpty(this.N)) {
                this.N = o.g(context);
            }
        } catch (Throwable unused6) {
        }
        try {
            this.S = evVar.a(this.B, this.E);
            a(this.A, this.B);
        } catch (Throwable th4) {
            th4.printStackTrace();
        }
        sb.delete(0, sb.length());
        sb2.delete(0, sb2.length());
    }

    /* JADX WARN: Can't wrap try/catch for region: R(18:16|21|22|(8:27|(1:29)(1:30)|31|(7:33|(1:35)(1:36)|37|(1:39)(1:40)|41|(1:43)(1:44)|45)(12:(11:48|(1:50)(1:51)|52|(1:54)(1:55)|56|(1:58)(1:59)|60|(1:62)(1:63)|64|(1:66)(1:67)|68)|69|(1:71)(1:72)|(1:74)|77|(1:79)(1:80)|81|(1:83)|84|(1:86)(1:87)|88|(2:90|(2:92|26)(4:93|(11:95|(1:97)(1:98)|99|(1:101)(1:102)|103|(1:105)(1:107)|106|(1:109)|113|(8:115|(1:117)(1:118)|119|(1:121)|122|(1:124)(1:125)|126|398)(2:127|397)|128)|396|129))(2:130|(1:132)))|46|69|(0)(0)|(9:74|77|(0)(0)|81|(0)|84|(0)(0)|88|(0)(0))(0))(1:26)|133|(1:139)(3:376|137|138)|140|(1:205)(4:143|(8:145|(3:164|(1:166)|167)(2:152|(3:154|(1:156)|157)(7:158|(3:160|(1:162)|163)|169|(1:171)|175|(2:193|(1:387)(5:195|(1:197)|(1:199)|200|(3:202|192|386)(1:388)))(2:182|(1:385)(6:186|(1:188)|(1:190)|191|192|386))|203))|168|169|(2:172|171)(0)|175|(3:177|193|(0)(0))(0)|203)|383|204)|206|(1:234)(21:211|366|212|213|379|214|(1:216)|217|218|221|(1:223)|227|(2:229|230)|231|236|(1:238)(7:239|(1:241)(1:242)|(1:244)|245|(10:247|368|248|249|251|(1:256)|257|(1:261)|(2:263|394)(1:395)|264)|393|265)|266|365|267|(1:269)|(26:271|275|276|363|277|(1:279)|280|281|(3:374|283|284)|381|286|287|372|288|289|(1:291)(1:292)|293|(1:295)|296|(4:298|(1:300)(1:301)|302|(5:370|304|(6:307|(3:326|(1:328)|329)(2:314|(3:316|(1:318)|319)(4:320|(3:322|(1:324)|325)|331|332))|330|331|332|305)|389|335)(2:334|335))|336|(4:338|(1:340)(1:341)|342|(3:344|(6:347|(1:349)|350|(2:352|391)(1:392)|353|345)|390))|354|(1:356)|357|358)(26:272|273|276|363|277|(0)|280|281|(0)|381|286|287|372|288|289|(0)(0)|293|(0)|296|(0)|336|(0)|354|(0)|357|358))|235|236|(0)(0)|266|365|267|(0)|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:274:0x06eb, code lost:
    
        r15[r11] = 0;
        r4 = 1;
     */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0342  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x036d A[PHI: r0
      0x036d: PHI (r0v48 int) = (r0v47 int), (r0v47 int), (r0v140 int) binds: [B:134:0x034f, B:136:0x0354, B:362:0x036d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0484  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x04fd  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x050d  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0558  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x060b  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x061f  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x0624  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x06d7  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x06da A[Catch: Throwable -> 0x06eb, TryCatch #1 {Throwable -> 0x06eb, blocks: (B:267:0x06ca, B:271:0x06da, B:272:0x06de), top: B:365:0x06ca }] */
    /* JADX WARN: Removed duplicated region for block: B:272:0x06de A[Catch: Throwable -> 0x06eb, TRY_LEAVE, TryCatch #1 {Throwable -> 0x06eb, blocks: (B:267:0x06ca, B:271:0x06da, B:272:0x06de), top: B:365:0x06ca }] */
    /* JADX WARN: Removed duplicated region for block: B:279:0x06fc A[Catch: Throwable -> 0x071b, TryCatch #0 {Throwable -> 0x071b, blocks: (B:277:0x06f4, B:279:0x06fc, B:280:0x0706), top: B:363:0x06f4 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x0731  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x0735  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x0743  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x075f  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:338:0x085d  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x0904  */
    /* JADX WARN: Removed duplicated region for block: B:374:0x070c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:387:0x054a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0219  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x021e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x022d  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0247  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x024e  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0251  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x025e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final byte[] a() {
        byte[] bArr;
        int length;
        int i;
        int i2;
        int length2;
        int length3;
        int i3;
        long jB;
        byte[] bArr2;
        long jB2;
        byte[] bArr3;
        byte[] bArr4;
        long j;
        long jB3;
        String str;
        int i4;
        byte b;
        int length4;
        int length5;
        int iMin;
        int length6;
        int i5;
        int length7;
        int i6;
        int length8;
        byte[] bArrA;
        int length9;
        int length10;
        int length11;
        int length12;
        int length13;
        boolean zIsEmpty;
        byte[] bytes;
        int i7;
        int length14;
        int length15;
        int i8;
        int length16;
        int length17;
        b();
        byte[] bArr5 = new byte[2];
        byte[] bArr6 = new byte[4];
        int i9 = 1;
        int length18 = this.H != null ? this.H.length + 1 + 4096 : 4096;
        if (this.Q == null || length18 > this.R) {
            bArr = new byte[length18];
            this.Q = bArr;
            this.R = length18;
        } else {
            bArr = this.Q;
        }
        bArr[0] = fq.g(this.a);
        byte[] bArrA2 = fq.a(this.b, (byte[]) null);
        System.arraycopy(bArrA2, 0, bArr, 1, bArrA2.length);
        int iA = a(this.q, bArr, a(this.p, bArr, a(this.h, bArr, a(this.u, bArr, a(this.g, bArr, a(this.f, bArr, a(this.e, bArr, a(this.o, bArr, a(this.d, bArr, a(this.c, bArr, bArrA2.length + 1))))))))));
        try {
        } catch (Throwable th) {
            fj.a(th, "Req", "buildV4Dot219");
            bArr[iA] = 0;
        }
        if (TextUtils.isEmpty(this.t)) {
            bArr[iA] = 0;
            length = iA + 1;
            int iA2 = a(this.x, bArr, a(K, bArr, a(I, bArr, a(this.w, bArr, a(this.v, bArr, length)))));
            bArr[iA2] = Byte.parseByte(this.y);
            int i10 = iA2 + 1;
            bArr[i10] = Byte.parseByte(this.j);
            int i11 = i10 + 1;
            i = this.z & 3;
            bArr[i11] = (byte) this.z;
            i2 = i11 + 1;
            if (i != 1 || i == 2) {
                byte[] bArrA3 = fq.a(this.A.size() <= 0 ? this.A.get(0).a : 0, (byte[]) null);
                System.arraycopy(bArrA3, 0, bArr, i2, bArrA3.length);
                int length19 = i2 + bArrA3.length;
                if (i != 1) {
                    byte[] bArrA4 = fq.a(this.A.size() > 0 ? this.A.get(0).b : 0, (byte[]) null);
                    System.arraycopy(bArrA4, 0, bArr, length19, bArrA4.length);
                    int length20 = length19 + bArrA4.length;
                    byte[] bArrA5 = fq.a(this.A.size() > 0 ? this.A.get(0).c : 0, (byte[]) null);
                    System.arraycopy(bArrA5, 0, bArr, length20, bArrA5.length);
                    length2 = length20 + bArrA5.length;
                    byte[] bArrB = fq.b(this.A.size() > 0 ? this.A.get(0).d : 0, (byte[]) null);
                    System.arraycopy(bArrB, 0, bArr, length2, bArrB.length);
                    length3 = bArrB.length;
                } else {
                    if (i == 2) {
                        byte[] bArrA6 = fq.a(this.A.size() > 0 ? this.A.get(0).h : 0, (byte[]) null);
                        System.arraycopy(bArrA6, 0, bArr, length19, bArrA6.length);
                        int length21 = length19 + bArrA6.length;
                        byte[] bArrA7 = fq.a(this.A.size() > 0 ? this.A.get(0).i : 0, (byte[]) null);
                        System.arraycopy(bArrA7, 0, bArr, length21, bArrA7.length);
                        int length22 = length21 + bArrA7.length;
                        byte[] bArrA8 = fq.a(this.A.size() > 0 ? this.A.get(0).j : 0, (byte[]) null);
                        System.arraycopy(bArrA8, 0, bArr, length22, bArrA8.length);
                        int length23 = length22 + bArrA8.length;
                        byte[] bArrB2 = fq.b(this.A.size() > 0 ? this.A.get(0).g : 0, (byte[]) null);
                        System.arraycopy(bArrB2, 0, bArr, length23, bArrB2.length);
                        length2 = length23 + bArrB2.length;
                        byte[] bArrB3 = fq.b(this.A.size() > 0 ? this.A.get(0).f : 0, (byte[]) null);
                        System.arraycopy(bArrB3, 0, bArr, length2, bArrB3.length);
                        length3 = bArrB3.length;
                    }
                    i3 = this.A.size() > 0 ? this.A.get(0).k : 0;
                    if (i3 > 127 || i3 < -128) {
                        i3 = 0;
                    }
                    bArr[length19] = (byte) i3;
                    int i12 = length19 + 1;
                    jB = this.A.size() <= 0 ? (fq.b() - this.A.get(0).t) / 1000 : 0L;
                    if (jB > 65535) {
                        jB = 65535;
                    }
                    byte[] bArrA9 = fq.a((int) (jB >= 0 ? 0L : jB), bArr5);
                    System.arraycopy(bArrA9, 0, bArr, i12, bArrA9.length);
                    i2 = i12 + 2;
                    if (i == 1) {
                        bArr2 = bArr;
                        jB2 = 0;
                        if (i == 2) {
                            bArr2[i2] = 0;
                            i2++;
                        }
                    } else if (this.A.size() == 0) {
                        bArr[i2] = 0;
                        i2++;
                        bArr2 = bArr;
                        jB2 = 0;
                    } else {
                        int size = this.A.size();
                        bArr[i2] = (byte) size;
                        int length24 = i2 + 1;
                        int i13 = 0;
                        while (i13 < size) {
                            byte[] bArrA10 = fq.a(this.A.size() > 0 ? this.A.get(i13).c : 0, (byte[]) null);
                            System.arraycopy(bArrA10, 0, bArr, length24, bArrA10.length);
                            int length25 = length24 + bArrA10.length;
                            byte[] bArrB4 = fq.b(this.A.size() > 0 ? this.A.get(i13).d : 0, (byte[]) null);
                            System.arraycopy(bArrB4, 0, bArr, length25, bArrB4.length);
                            int length26 = length25 + bArrB4.length;
                            int i14 = this.A.size() > 0 ? this.A.get(i13).k : 0;
                            if (i14 > 127 || i14 < -128) {
                                i14 = 0;
                            }
                            bArr[length26] = (byte) i14;
                            length24 = length26 + i9;
                            if (Double.valueOf(fj.a).doubleValue() >= 5.2d) {
                                if (this.A.size() > 0) {
                                    bArr4 = bArr;
                                    jB3 = (fq.b() - this.A.get(0).t) / 1000;
                                    j = 65535;
                                } else {
                                    bArr4 = bArr;
                                    j = 65535;
                                    jB3 = 0;
                                }
                                if (jB3 > j) {
                                    jB3 = j;
                                }
                                byte[] bArrA11 = fq.a((int) (jB3 < 0 ? 0L : jB3), bArr5);
                                bArr3 = bArr4;
                                System.arraycopy(bArrA11, 0, bArr3, length24, bArrA11.length);
                                length24 += bArrA11.length;
                            } else {
                                bArr3 = bArr;
                            }
                            i13++;
                            bArr = bArr3;
                            i9 = 1;
                        }
                        bArr2 = bArr;
                        jB2 = 0;
                        i2 = length24;
                    }
                }
                length19 = length2 + length3;
                if (this.A.size() > 0) {
                }
                if (i3 > 127) {
                    i3 = 0;
                    bArr[length19] = (byte) i3;
                    int i122 = length19 + 1;
                    if (this.A.size() <= 0) {
                    }
                    if (jB > 65535) {
                    }
                    byte[] bArrA92 = fq.a((int) (jB >= 0 ? 0L : jB), bArr5);
                    System.arraycopy(bArrA92, 0, bArr, i122, bArrA92.length);
                    i2 = i122 + 2;
                    if (i == 1) {
                    }
                }
            } else {
                bArr2 = bArr;
                jB2 = 0;
            }
            str = this.C;
            int i15 = 8;
            if (str == null && (this.z & 8) == 8) {
                try {
                    byte[] bytes2 = str.getBytes("GBK");
                    int iMin2 = Math.min(bytes2.length, 60);
                    bArr2[i2] = (byte) iMin2;
                    i2++;
                    System.arraycopy(bytes2, 0, bArr2, i2, iMin2);
                    i4 = i2 + iMin2;
                } catch (Exception unused) {
                    bArr2[i2] = 0;
                    i4 = i2 + 1;
                }
            } else {
                bArr2[i2] = 0;
                i4 = i2 + 1;
            }
            ArrayList<es> arrayList = this.B;
            int size2 = arrayList.size();
            if ((this.z & 4) == 4 || size2 <= 0) {
                bArr2[i4] = 0;
                b = 1;
                length4 = i4 + 1;
            } else {
                arrayList.get(0);
                bArr2[i4] = (byte) size2;
                byte b2 = 1;
                length4 = i4 + 1;
                int i16 = 0;
                while (i16 < size2) {
                    es esVar = arrayList.get(i16);
                    if (esVar.l == b2 || esVar.l == 3 || esVar.l == 4) {
                        byte b3 = (byte) esVar.l;
                        if (esVar.n) {
                            b3 = (byte) (b3 | 8);
                        }
                        bArr2[length4] = b3;
                        int i17 = length4 + 1;
                        byte[] bArrA12 = fq.a(esVar.a, bArr5);
                        System.arraycopy(bArrA12, 0, bArr2, i17, bArrA12.length);
                        int length27 = i17 + bArrA12.length;
                        byte[] bArrA13 = fq.a(esVar.b, bArr5);
                        System.arraycopy(bArrA13, 0, bArr2, length27, bArrA13.length);
                        int length28 = length27 + bArrA13.length;
                        byte[] bArrA14 = fq.a(esVar.c, bArr5);
                        System.arraycopy(bArrA14, 0, bArr2, length28, bArrA14.length);
                        length14 = length28 + bArrA14.length;
                        byte[] bArrB5 = fq.b(esVar.d, bArr6);
                        System.arraycopy(bArrB5, 0, bArr2, length14, bArrB5.length);
                        length15 = bArrB5.length;
                    } else if (esVar.l == 2) {
                        byte b4 = (byte) esVar.l;
                        if (esVar.n) {
                            b4 = (byte) (b4 | 8);
                        }
                        bArr2[length4] = b4;
                        int i18 = length4 + 1;
                        byte[] bArrA15 = fq.a(esVar.a, bArr5);
                        System.arraycopy(bArrA15, 0, bArr2, i18, bArrA15.length);
                        int length29 = i18 + bArrA15.length;
                        byte[] bArrA16 = fq.a(esVar.h, bArr5);
                        System.arraycopy(bArrA16, 0, bArr2, length29, bArrA16.length);
                        int length30 = length29 + bArrA16.length;
                        byte[] bArrA17 = fq.a(esVar.i, bArr5);
                        System.arraycopy(bArrA17, 0, bArr2, length30, bArrA17.length);
                        int length31 = length30 + bArrA17.length;
                        byte[] bArrA18 = fq.a(esVar.j, bArr5);
                        System.arraycopy(bArrA18, 0, bArr2, length31, bArrA18.length);
                        int length32 = length31 + bArrA18.length;
                        byte[] bArrB6 = fq.b(esVar.g, bArr6);
                        System.arraycopy(bArrB6, 0, bArr2, length32, bArrB6.length);
                        length14 = length32 + bArrB6.length;
                        byte[] bArrB7 = fq.b(esVar.f, bArr6);
                        System.arraycopy(bArrB7, 0, bArr2, length14, bArrB7.length);
                        length15 = bArrB7.length;
                    } else {
                        if (esVar.l == 5) {
                            byte b5 = (byte) esVar.l;
                            if (esVar.n) {
                                b5 = (byte) (b5 | 8);
                            }
                            bArr2[length4] = b5;
                            int i19 = length4 + 1;
                            byte[] bArrA19 = fq.a(esVar.a, bArr5);
                            System.arraycopy(bArrA19, 0, bArr2, i19, bArrA19.length);
                            int length33 = i19 + bArrA19.length;
                            byte[] bArrA20 = fq.a(esVar.b, bArr5);
                            System.arraycopy(bArrA20, 0, bArr2, length33, bArrA20.length);
                            int length34 = length33 + bArrA20.length;
                            byte[] bArrA21 = fq.a(esVar.c, bArr5);
                            System.arraycopy(bArrA21, 0, bArr2, length34, bArrA21.length);
                            int length35 = length34 + bArrA21.length;
                            System.arraycopy(fq.a(esVar.e), 0, bArr2, length35, i15);
                            length4 = length35 + i15;
                        }
                        i8 = esVar.k;
                        if (i8 <= 127 || i8 < -128) {
                            i8 = 99;
                        }
                        bArr2[length4] = (byte) i8;
                        int i20 = length4 + 1;
                        int i21 = size2;
                        byte[] bArrA22 = fq.a((short) ((fq.b() - esVar.t) / 1000), bArr5);
                        System.arraycopy(bArrA22, 0, bArr2, i20, bArrA22.length);
                        length4 = i20 + bArrA22.length;
                        if (esVar.l != 3 || esVar.l == 4 || esVar.l == 5) {
                            if (Double.valueOf(fj.a).doubleValue() >= 5.0d) {
                                int i22 = esVar.o;
                                if (i22 > 32767) {
                                    i22 = 32767;
                                }
                                if (i22 < 0) {
                                    i22 = 32767;
                                }
                                byte[] bArrA23 = fq.a(i22, bArr5);
                                System.arraycopy(bArrA23, 0, bArr2, length4, bArrA23.length);
                                length4 += bArrA23.length;
                                if (Double.valueOf(fj.a).doubleValue() >= 5.3d) {
                                    byte[] bArrB8 = fq.b(esVar.p, bArr6);
                                    System.arraycopy(bArrB8, 0, bArr2, length4, bArrB8.length);
                                    length16 = length4 + bArrB8.length;
                                    byte[] bArrB9 = fq.b(esVar.q, bArr6);
                                    System.arraycopy(bArrB9, 0, bArr2, length16, bArrB9.length);
                                    length17 = bArrB9.length;
                                    length4 = length16 + length17;
                                }
                            }
                        } else if (esVar.l == 1 && Double.valueOf(fj.a).doubleValue() >= 5.3d) {
                            int i23 = esVar.o;
                            if (i23 > 32767) {
                                i23 = 32767;
                            }
                            if (i23 < 0) {
                                i23 = 32767;
                            }
                            byte[] bArrA24 = fq.a(i23, bArr5);
                            System.arraycopy(bArrA24, 0, bArr2, length4, bArrA24.length);
                            int length36 = length4 + bArrA24.length;
                            byte[] bArrB10 = fq.b(esVar.p, bArr6);
                            System.arraycopy(bArrB10, 0, bArr2, length36, bArrB10.length);
                            length16 = length36 + bArrB10.length;
                            byte[] bArrB11 = fq.b(esVar.q, bArr6);
                            System.arraycopy(bArrB11, 0, bArr2, length16, bArrB11.length);
                            length17 = bArrB11.length;
                            length4 = length16 + length17;
                        }
                        i16++;
                        size2 = i21;
                        i15 = 8;
                        b2 = 1;
                    }
                    length4 = length14 + length15;
                    i8 = esVar.k;
                    if (i8 <= 127) {
                        i8 = 99;
                    }
                    bArr2[length4] = (byte) i8;
                    int i202 = length4 + 1;
                    int i212 = size2;
                    byte[] bArrA222 = fq.a((short) ((fq.b() - esVar.t) / 1000), bArr5);
                    System.arraycopy(bArrA222, 0, bArr2, i202, bArrA222.length);
                    length4 = i202 + bArrA222.length;
                    if (esVar.l != 3) {
                        if (Double.valueOf(fj.a).doubleValue() >= 5.0d) {
                        }
                    }
                    i16++;
                    size2 = i212;
                    i15 = 8;
                    b2 = 1;
                }
                b = b2;
            }
            if (TextUtils.isEmpty(this.D) && this.D.length() != 0) {
                bArr2[length4] = b;
                length5 = length4 + b;
                try {
                    String[] strArrSplit = this.D.split(com.igexin.push.core.b.an);
                    byte[] bArrA25 = a(strArrSplit[0]);
                    System.arraycopy(bArrA25, 0, bArr2, length5, bArrA25.length);
                    length5 += bArrA25.length;
                    try {
                        byte[] bytes3 = strArrSplit[2].getBytes("GBK");
                        int length37 = bytes3.length;
                        if (length37 > 127) {
                            length37 = 127;
                        }
                        bArr2[length5] = (byte) length37;
                        length5++;
                        System.arraycopy(bytes3, 0, bArr2, length5, length37);
                        i7 = length5 + length37;
                    } catch (Throwable th2) {
                        fj.a(th2, "Req", "buildV4Dot214");
                        bArr2[length5] = 0;
                        i7 = length5 + 1;
                    }
                    int i24 = Integer.parseInt(strArrSplit[1]);
                    if (i24 > 127 || i24 < -128) {
                        i24 = 0;
                    }
                    bArr2[i7] = Byte.parseByte(String.valueOf(i24));
                    length5 = i7 + 1;
                    if (Double.valueOf(fj.a).doubleValue() >= 5.2d) {
                        byte[] bArrA26 = fq.a(this.O, bArr5);
                        System.arraycopy(bArrA26, 0, bArr2, length5, bArrA26.length);
                        length5 += bArrA26.length;
                    }
                    b = 1;
                } catch (Throwable th3) {
                    fj.a(th3, "Req", "buildV4Dot216");
                    byte[] bArrA27 = a("00:00:00:00:00:00");
                    System.arraycopy(bArrA27, 0, bArr2, length5, bArrA27.length);
                    int length38 = length5 + bArrA27.length;
                    bArr2[length38] = 0;
                    b = 1;
                    length4 = length38 + 1;
                    bArr2[length4] = Byte.parseByte("0");
                    length5 = length4 + b;
                }
                ArrayList<dy> arrayList2 = this.E;
                iMin = Math.min(arrayList2.size(), 25);
                if (iMin == 0) {
                    bArr2[length5] = 0;
                    length6 = length5 + b;
                } else {
                    bArr2[length5] = (byte) iMin;
                    int length39 = length5 + b;
                    boolean z = fq.c() >= 17;
                    if (z) {
                        jB2 = fq.b() / 1000;
                    }
                    for (int i25 = 0; i25 < iMin; i25++) {
                        dy dyVar = arrayList2.get(i25);
                        byte[] bArrA28 = a(dy.a(dyVar.a));
                        System.arraycopy(bArrA28, 0, bArr2, length39, bArrA28.length);
                        int length40 = length39 + bArrA28.length;
                        try {
                            byte[] bytes4 = dyVar.b.getBytes("GBK");
                            bArr2[length40] = (byte) bytes4.length;
                            length40++;
                            System.arraycopy(bytes4, 0, bArr2, length40, bytes4.length);
                            length7 = length40 + bytes4.length;
                            i5 = 1;
                        } catch (Exception unused2) {
                            bArr2[length40] = 0;
                            i5 = 1;
                            length7 = length40 + 1;
                        }
                        int i26 = dyVar.c;
                        if (i26 > 127 || i26 < -128) {
                            i26 = 0;
                        }
                        bArr2[length7] = Byte.parseByte(String.valueOf(i26));
                        int i27 = length7 + i5;
                        if (!z || (i6 = (int) (jB2 - (dyVar.f / 1000))) < 0) {
                            i6 = 0;
                        }
                        if (i6 > 65535) {
                            i6 = 65535;
                        }
                        byte[] bArrA29 = fq.a(i6, bArr5);
                        System.arraycopy(bArrA29, 0, bArr2, i27, bArrA29.length);
                        int length41 = i27 + bArrA29.length;
                        byte[] bArrA30 = fq.a(dyVar.d, bArr5);
                        System.arraycopy(bArrA30, 0, bArr2, length41, bArrA30.length);
                        length39 = length41 + bArrA30.length;
                    }
                    byte[] bArrA31 = fq.a(Integer.parseInt(this.F), bArr5);
                    System.arraycopy(bArrA31, 0, bArr2, length39, bArrA31.length);
                    length6 = length39 + bArrA31.length;
                }
                bArr2[length6] = 0;
                int i28 = length6 + 1;
                bytes = this.G.getBytes("GBK");
                if (bytes.length > 127) {
                    bytes = null;
                }
                if (bytes != null) {
                    bArr2[i28] = (byte) bytes.length;
                    int i29 = i28 + 1;
                    System.arraycopy(bytes, 0, bArr2, i29, bytes.length);
                    length8 = i29 + bytes.length;
                    bArrA = new byte[]{0, 0};
                    zIsEmpty = TextUtils.isEmpty(this.J);
                    if (!zIsEmpty) {
                    }
                    System.arraycopy(bArrA, 0, bArr2, length8, 2);
                    length9 = length8 + 2;
                    if (!zIsEmpty) {
                    }
                    System.arraycopy(fq.a(0, bArr5), 0, bArr2, length9, 2);
                    int i30 = length9 + 2;
                    System.arraycopy(new byte[]{0, 0}, 0, bArr2, i30, 2);
                    int i31 = i30 + 2;
                    if (this.H == null) {
                    }
                    byte[] bArrA32 = fq.a(length10, (byte[]) null);
                    System.arraycopy(bArrA32, 0, bArr2, i31, bArrA32.length);
                    int length42 = i31 + bArrA32.length;
                    if (length10 > 0) {
                    }
                    if (Double.valueOf(fj.a).doubleValue() >= 5.0d) {
                    }
                    if (Double.valueOf(fj.a).doubleValue() >= 5.2d) {
                    }
                    if (Double.valueOf(fj.a).doubleValue() >= 5.3d) {
                    }
                    byte[] bArr7 = new byte[length42];
                    System.arraycopy(bArr2, 0, bArr7, 0, length42);
                    CRC32 crc32 = new CRC32();
                    crc32.update(bArr7);
                    byte[] bArrA33 = fq.a(crc32.getValue());
                    byte[] bArr8 = new byte[length42 + 8];
                    System.arraycopy(bArr7, 0, bArr8, 0, length42);
                    System.arraycopy(bArrA33, 0, bArr8, length42, 8);
                    return bArr8;
                }
                bArr2[i28] = 0;
                int i32 = 1;
                length8 = i28 + i32;
                bArrA = new byte[]{0, 0};
                try {
                    zIsEmpty = TextUtils.isEmpty(this.J);
                    if (!zIsEmpty) {
                        bArrA = fq.a(this.J.length(), bArr5);
                    }
                    System.arraycopy(bArrA, 0, bArr2, length8, 2);
                    length9 = length8 + 2;
                    if (!zIsEmpty) {
                        try {
                            byte[] bytes5 = this.J.getBytes("GBK");
                            System.arraycopy(bytes5, 0, bArr2, length9, bytes5.length);
                            length9 += bytes5.length;
                        } catch (Throwable unused3) {
                        }
                    }
                } catch (Throwable unused4) {
                    length9 = length8 + 2;
                }
                try {
                    System.arraycopy(fq.a(0, bArr5), 0, bArr2, length9, 2);
                } catch (Throwable unused5) {
                }
                int i302 = length9 + 2;
                try {
                    System.arraycopy(new byte[]{0, 0}, 0, bArr2, i302, 2);
                } catch (Throwable unused6) {
                }
                int i312 = i302 + 2;
                length10 = this.H == null ? this.H.length : 0;
                byte[] bArrA322 = fq.a(length10, (byte[]) null);
                System.arraycopy(bArrA322, 0, bArr2, i312, bArrA322.length);
                int length422 = i312 + bArrA322.length;
                if (length10 > 0) {
                    System.arraycopy(this.H, 0, bArr2, length422, this.H.length);
                    length422 += this.H.length;
                }
                if (Double.valueOf(fj.a).doubleValue() >= 5.0d) {
                    int size3 = this.T != null ? this.T.size() : 0;
                    bArr2[length422] = (byte) size3;
                    int i33 = length422 + 1;
                    byte[] bArr9 = new byte[i33];
                    System.arraycopy(bArr2, 0, bArr9, 0, i33);
                    if (size3 > 0) {
                        try {
                            length11 = i33;
                            for (es esVar2 : this.T) {
                                if (esVar2.l == 1 || esVar2.l == 3 || esVar2.l == 4) {
                                    byte b6 = (byte) esVar2.l;
                                    if (esVar2.n) {
                                        b6 = (byte) (b6 | 8);
                                    }
                                    bArr2[length11] = b6;
                                    int i34 = length11 + 1;
                                    byte[] bArrA34 = fq.a(esVar2.c, bArr5);
                                    System.arraycopy(bArrA34, 0, bArr2, i34, bArrA34.length);
                                    length12 = i34 + bArrA34.length;
                                    byte[] bArrB12 = fq.b(esVar2.d, bArr6);
                                    System.arraycopy(bArrB12, 0, bArr2, length12, bArrB12.length);
                                    length13 = bArrB12.length;
                                } else if (esVar2.l == 2) {
                                    byte b7 = (byte) esVar2.l;
                                    if (esVar2.n) {
                                        b7 = (byte) (b7 | 8);
                                    }
                                    bArr2[length11] = b7;
                                    int i35 = length11 + 1;
                                    byte[] bArrA35 = fq.a(esVar2.h, bArr5);
                                    System.arraycopy(bArrA35, 0, bArr2, i35, bArrA35.length);
                                    int length43 = i35 + bArrA35.length;
                                    byte[] bArrA36 = fq.a(esVar2.i, bArr5);
                                    System.arraycopy(bArrA36, 0, bArr2, length43, bArrA36.length);
                                    length12 = length43 + bArrA36.length;
                                    byte[] bArrA37 = fq.a(esVar2.j, bArr5);
                                    System.arraycopy(bArrA37, 0, bArr2, length12, bArrA37.length);
                                    length13 = bArrA37.length;
                                } else {
                                    if (esVar2.l == 5) {
                                        byte b8 = (byte) esVar2.l;
                                        if (esVar2.n) {
                                            b8 = (byte) (b8 | 8);
                                        }
                                        bArr2[length11] = b8;
                                        int i36 = length11 + 1;
                                        byte[] bArrA38 = fq.a(esVar2.c, bArr5);
                                        System.arraycopy(bArrA38, 0, bArr2, i36, bArrA38.length);
                                        int length44 = i36 + bArrA38.length;
                                        System.arraycopy(fq.a(esVar2.e), 0, bArr2, length44, 8);
                                        length11 = length44 + 8;
                                    }
                                    byte[] bArrA39 = fq.a((short) ((fq.b() - esVar2.t) / 1000), bArr5);
                                    System.arraycopy(bArrA39, 0, bArr2, length11, bArrA39.length);
                                    length11 += bArrA39.length;
                                }
                                length11 = length12 + length13;
                                byte[] bArrA392 = fq.a((short) ((fq.b() - esVar2.t) / 1000), bArr5);
                                System.arraycopy(bArrA392, 0, bArr2, length11, bArrA392.length);
                                length11 += bArrA392.length;
                            }
                        } catch (Throwable unused7) {
                            System.arraycopy(bArr9, 0, bArr2, 0, i33);
                            bArr2[i33 - 1] = 0;
                            length11 = i33;
                        }
                        length422 = a(this.M, bArr2, length11);
                    } else {
                        length11 = i33;
                        length422 = a(this.M, bArr2, length11);
                    }
                }
                if (Double.valueOf(fj.a).doubleValue() >= 5.2d) {
                    int size4 = this.S == null ? 0 : this.S.size();
                    bArr2[length422] = (byte) size4;
                    length422++;
                    if (size4 > 0) {
                        for (eu euVar : this.S) {
                            int iCurrentTimeMillis = ((int) (System.currentTimeMillis() - euVar.d)) / 1000;
                            if (iCurrentTimeMillis > 65535) {
                                iCurrentTimeMillis = 65535;
                            }
                            System.arraycopy(fq.a(iCurrentTimeMillis, bArr5), 0, bArr2, length422, 2);
                            int i37 = length422 + 2;
                            System.arraycopy(fq.b((int) Math.round(euVar.c * 1.0E7d), bArr6), 0, bArr2, i37, 4);
                            int i38 = i37 + 4;
                            System.arraycopy(fq.b((int) Math.round(euVar.b * 1.0E7d), bArr6), 0, bArr2, i38, 4);
                            int i39 = i38 + 4;
                            float f = euVar.e;
                            if (f > 65535.0f) {
                                f = 65535.0f;
                            }
                            System.arraycopy(fq.a((int) f, bArr5), 0, bArr2, i39, 2);
                            int i40 = i39 + 2;
                            System.arraycopy(fq.a((short) ((euVar.h | (euVar.a << 13) | (euVar.g << 6)) & SupportMenu.USER_MASK), bArr5), 0, bArr2, i40, 2);
                            length422 = i40 + 2;
                        }
                    }
                }
                if (Double.valueOf(fj.a).doubleValue() >= 5.3d) {
                    length422 = a(this.N, bArr2, length422);
                }
                byte[] bArr72 = new byte[length422];
                System.arraycopy(bArr2, 0, bArr72, 0, length422);
                CRC32 crc322 = new CRC32();
                crc322.update(bArr72);
                byte[] bArrA332 = fq.a(crc322.getValue());
                byte[] bArr82 = new byte[length422 + 8];
                System.arraycopy(bArr72, 0, bArr82, 0, length422);
                System.arraycopy(bArrA332, 0, bArr82, length422, 8);
                return bArr82;
            }
            bArr2[length4] = 0;
            length5 = length4 + b;
            ArrayList<dy> arrayList22 = this.E;
            iMin = Math.min(arrayList22.size(), 25);
            if (iMin == 0) {
            }
            bArr2[length6] = 0;
            int i282 = length6 + 1;
            bytes = this.G.getBytes("GBK");
            if (bytes.length > 127) {
            }
            if (bytes != null) {
            }
        } else {
            byte[] bArrA40 = a(this.t);
            bArr[iA] = (byte) bArrA40.length;
            int i41 = iA + 1;
            System.arraycopy(bArrA40, 0, bArr, i41, bArrA40.length);
            length = i41 + bArrA40.length;
            int iA22 = a(this.x, bArr, a(K, bArr, a(I, bArr, a(this.w, bArr, a(this.v, bArr, length)))));
            bArr[iA22] = Byte.parseByte(this.y);
            int i102 = iA22 + 1;
            bArr[i102] = Byte.parseByte(this.j);
            int i112 = i102 + 1;
            i = this.z & 3;
            bArr[i112] = (byte) this.z;
            i2 = i112 + 1;
            if (i != 1) {
                byte[] bArrA310 = fq.a(this.A.size() <= 0 ? this.A.get(0).a : 0, (byte[]) null);
                System.arraycopy(bArrA310, 0, bArr, i2, bArrA310.length);
                int length192 = i2 + bArrA310.length;
                if (i != 1) {
                }
                length192 = length2 + length3;
                if (this.A.size() > 0) {
                }
                if (i3 > 127) {
                }
            }
            str = this.C;
            int i152 = 8;
            if (str == null) {
                bArr2[i2] = 0;
                i4 = i2 + 1;
            }
            ArrayList<es> arrayList3 = this.B;
            int size22 = arrayList3.size();
            if ((this.z & 4) == 4) {
                bArr2[i4] = 0;
                b = 1;
                length4 = i4 + 1;
            }
            if (TextUtils.isEmpty(this.D)) {
                bArr2[length4] = 0;
                length5 = length4 + b;
            }
            ArrayList<dy> arrayList222 = this.E;
            iMin = Math.min(arrayList222.size(), 25);
            if (iMin == 0) {
            }
            bArr2[length6] = 0;
            int i2822 = length6 + 1;
            bytes = this.G.getBytes("GBK");
            if (bytes.length > 127) {
            }
            if (bytes != null) {
            }
        }
    }
}
