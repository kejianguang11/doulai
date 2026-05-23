package com.igexin.push.core;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Pair;
import androidx.core.view.MotionEventCompat;
import com.igexin.push.core.d;
import com.igexin.push.g.o;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public final class k {
    public static final int a = -1;
    public static final int b = 0;
    public static final int c = 1;
    private static final String d = "LoginInteractor";
    private static k e;

    public static k a() {
        if (e == null) {
            e = new k();
        }
        return e;
    }

    private static void a(List<com.igexin.push.d.c.j> list) {
        if (ServiceManager.getInstance().initType == null) {
            return;
        }
        int iIntValue = ((Integer) ServiceManager.getInstance().initType.first).intValue();
        String strValueOf = String.valueOf(iIntValue);
        if (iIntValue == 1) {
            strValueOf = strValueOf + "#" + ((String) ServiceManager.getInstance().initType.second);
        }
        com.igexin.push.d.c.j jVar = new com.igexin.push.d.c.j();
        jVar.a = (byte) 5;
        jVar.b = strValueOf;
        list.add(jVar);
    }

    public static int b() {
        if (!e.s || !com.igexin.push.g.c.a()) {
            com.igexin.c.a.c.a.a(d, "keyNegotiate stop ++++++++++");
            com.igexin.c.a.c.a.a("LoginInteractor|keyNegotiate stop ++++++++++", new Object[0]);
            return -1;
        }
        com.igexin.push.d.c.g gVar = new com.igexin.push.d.c.g();
        gVar.b = e.a;
        int iA = d.a.a.h.a("K-", gVar, true);
        com.igexin.c.a.c.a.a("LoginInteractor|keyNegotiate result=".concat(String.valueOf(iA)), new Object[0]);
        return iA < 0 ? 0 : 1;
    }

    public static void c() {
        com.igexin.c.a.c.a.d.a().a("[LoginInteractor] Start login appid = " + e.a);
        if (e.t) {
            e.t = false;
            e.T = System.currentTimeMillis() + (((long) Math.abs(new Random().nextInt() % 24)) * 3600000);
        }
        com.igexin.push.c.c.a().d().d();
        if (e.z == 0) {
            com.igexin.c.a.c.a.a("registerReq #####", new Object[0]);
            com.igexin.push.d.c.d dVar = new com.igexin.push.d.c.d(e.D, e.E, e.L, e.a);
            String str = e.D;
            String str2 = e.E;
            String str3 = e.L;
            com.igexin.push.e.a aVar = d.a.a.h;
            StringBuilder sb = new StringBuilder("R-");
            sb.append(e.L);
            com.igexin.c.a.c.a.a("registerReq|" + (aVar.a(sb.toString(), dVar, true) >= 0) + "|" + e.L, new Object[0]);
            return;
        }
        long j = e.z;
        com.igexin.push.d.c.i iVarD = d();
        com.igexin.c.a.c.a.a("loginReqBefore|" + iVarD.b, new Object[0]);
        com.igexin.push.e.a aVar2 = d.a.a.h;
        StringBuilder sb2 = new StringBuilder("S-");
        sb2.append(e.z);
        if (aVar2.a(sb2.toString(), iVarD, true) >= 0) {
            String str4 = e.A;
            com.igexin.c.a.c.a.a("LoginInteractor|loginReq|" + e.A, new Object[0]);
        }
    }

    public static com.igexin.push.d.c.i d() {
        int type;
        Throwable th;
        Pair<String, String> pairB;
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        com.igexin.push.d.c.i iVar = new com.igexin.push.d.c.i();
        iVar.b = e.z;
        iVar.c = (byte) 0;
        iVar.d = MotionEventCompat.ACTION_POINTER_INDEX_MASK;
        iVar.e = e.a;
        int i = -1;
        try {
            ArrayList<com.igexin.push.d.c.j> arrayList = new ArrayList();
            try {
                connectivityManager = (ConnectivityManager) e.l.getSystemService("connectivity");
            } catch (Throwable th2) {
                type = -1;
                th = th2;
            }
            if (connectivityManager != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null) {
                type = activeNetworkInfo.getType();
                try {
                    com.igexin.push.d.c.j jVar = new com.igexin.push.d.c.j();
                    jVar.a = (byte) 2;
                    jVar.b = String.valueOf(type);
                    arrayList.add(jVar);
                } catch (Throwable th3) {
                    th = th3;
                    com.igexin.c.a.c.a.a(th);
                }
                i = type;
            }
            if (i == 1 && (pairB = o.b()) != null) {
                String str = (String) pairB.first;
                String str2 = (String) pairB.second;
                if (str != null) {
                    com.igexin.push.d.c.j jVar2 = new com.igexin.push.d.c.j();
                    jVar2.a = (byte) 1;
                    jVar2.b = str;
                    arrayList.add(jVar2);
                }
                if (str2 != null) {
                    com.igexin.push.d.c.j jVar3 = new com.igexin.push.d.c.j();
                    jVar3.a = (byte) 4;
                    jVar3.b = str2;
                    arrayList.add(jVar3);
                }
            }
            String strP = o.p();
            if (!TextUtils.isEmpty(strP)) {
                String[] strArrSplit = strP.split("#");
                if (strArrSplit.length >= 3 && !TextUtils.isEmpty(strArrSplit[2])) {
                    com.igexin.push.d.c.j jVar4 = new com.igexin.push.d.c.j();
                    jVar4.a = (byte) 6;
                    jVar4.b = strArrSplit[2];
                    arrayList.add(jVar4);
                }
            }
            if (com.igexin.push.config.d.ae) {
                String strR = o.r();
                if (!TextUtils.isEmpty(strR)) {
                    com.igexin.push.d.c.j jVar5 = new com.igexin.push.d.c.j();
                    jVar5.a = (byte) 7;
                    jVar5.b = strR;
                    arrayList.add(jVar5);
                }
            }
            if (ServiceManager.getInstance().initType != null) {
                int iIntValue = ((Integer) ServiceManager.getInstance().initType.first).intValue();
                String strValueOf = String.valueOf(iIntValue);
                if (iIntValue == 1) {
                    strValueOf = strValueOf + "#" + ((String) ServiceManager.getInstance().initType.second);
                }
                com.igexin.push.d.c.j jVar6 = new com.igexin.push.d.c.j();
                jVar6.a = (byte) 5;
                jVar6.b = strValueOf;
                arrayList.add(jVar6);
            }
            try {
                StringBuilder sb = new StringBuilder();
                for (com.igexin.push.d.c.j jVar7 : arrayList) {
                    sb.append((int) jVar7.a);
                    sb.append(":");
                    sb.append((String) jVar7.b);
                    sb.append("|");
                }
                com.igexin.c.a.c.a.a("LoginInteractor| ".concat(String.valueOf(sb)), new Object[0]);
            } catch (Throwable th4) {
                com.igexin.c.a.c.a.a(th4);
            }
            if (!arrayList.isEmpty()) {
                iVar.g = arrayList;
            }
        } catch (Throwable th5) {
            com.igexin.c.a.c.a.a(th5);
        }
        return iVar;
    }
}
