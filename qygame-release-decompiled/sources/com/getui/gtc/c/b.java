package com.getui.gtc.c;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.getui.gtc.api.GtcIdCallback;
import com.getui.gtc.base.GtcProvider;
import com.getui.gtc.base.util.CommonUtil;
import com.getui.gtc.e.c;
import com.getui.gtc.e.d;
import com.getui.gtc.h.c;
import com.getui.gtc.server.ServerManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    public static String b;
    public static String c;
    public static volatile String d;
    public static String e;
    public static String f;
    public static String g;
    public static String a = GtcProvider.context().getPackageName();
    private static final List<GtcIdCallback> j = new ArrayList();
    public static String h = com.igexin.push.a.k;
    public static String i = com.igexin.push.a.j;

    public static void a() {
        String[] strArrSplit;
        String[] strArrSplit2;
        String[] strArrSplit3;
        String str;
        try {
            Bundle bundle = CommonUtil.getAppInfoForSelf(GtcProvider.context()).metaData;
            if (bundle != null) {
                String string = bundle.getString("GTC_C");
                if (!TextUtils.isEmpty(string)) {
                    e = string;
                }
                String string2 = bundle.getString("GTC_B");
                if (!TextUtils.isEmpty(string2)) {
                    f = string2;
                }
                String string3 = bundle.getString("GTC_A");
                if (!TextUtils.isEmpty(string3)) {
                    g = string3;
                }
                String string4 = bundle.getString("GTC_P");
                if (!TextUtils.isEmpty(string4)) {
                    h = string4;
                }
                String string5 = bundle.getString("GTC_K");
                if (!TextUtils.isEmpty(string5)) {
                    i = string5;
                }
                String string6 = bundle.getString("GETUI_APPID");
                if (TextUtils.isEmpty(string6)) {
                    string6 = bundle.getString("GETUI_APP_ID");
                    if (TextUtils.isEmpty(string6)) {
                        string6 = bundle.getString(com.igexin.push.core.b.b);
                        if (TextUtils.isEmpty(string6)) {
                            string6 = bundle.getString("GI_APPID");
                            if (TextUtils.isEmpty(string6)) {
                                string6 = bundle.getString("GI_APP_ID");
                                if (TextUtils.isEmpty(string6)) {
                                    string6 = bundle.getString("GS_APPID");
                                    if (TextUtils.isEmpty(string6)) {
                                        string6 = bundle.getString("GS_APP_ID");
                                        if (TextUtils.isEmpty(string6)) {
                                            string6 = bundle.getString("GY_APPID");
                                            if (TextUtils.isEmpty(string6)) {
                                                string6 = bundle.getString("GY_APP_ID");
                                                if (TextUtils.isEmpty(string6)) {
                                                    String string7 = bundle.getString("com.sdk.plus.appid");
                                                    if (!TextUtils.isEmpty(string7)) {
                                                        a = string7;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    a = string6;
                } else {
                    a = string6;
                }
            } else {
                com.getui.gtc.i.c.a.b("metaData==null");
            }
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.c(th);
        }
        HashMap map = new HashMap();
        String[] strArr = new String[0];
        try {
            try {
                strArrSplit = e.split(com.igexin.push.core.b.an);
            } catch (Throwable unused) {
                strArrSplit = "https://c-gtc.getui.net,https://c-gtc.gepush.com".split(com.igexin.push.core.b.an);
            }
            map.put("gtc.cs", Arrays.asList(strArrSplit));
            String[] strArr2 = new String[0];
            try {
                try {
                    strArrSplit2 = g.split(com.igexin.push.core.b.an);
                } catch (Throwable unused2) {
                    strArrSplit2 = "https://gtc.getui.net,https://gtc.gepush.com".split(com.igexin.push.core.b.an);
                }
                map.put("gtc.as", Arrays.asList(strArrSplit2));
                String[] strArr3 = new String[0];
                try {
                    try {
                        strArrSplit3 = f.split(com.igexin.push.core.b.an);
                    } catch (Throwable unused3) {
                        strArrSplit3 = "https://b-gtc.getui.net,https://b-gtc.gepush.com".split(com.igexin.push.core.b.an);
                    }
                    map.put("gtc.bs", Arrays.asList(strArrSplit3));
                    ServerManager.addBuildInServerMap(map);
                    if (TextUtils.isEmpty(d)) {
                        d = c.a.a.a.d;
                    }
                    if (TextUtils.isEmpty(d)) {
                        if (TextUtils.isEmpty(c)) {
                            c = c.a.a.a.i;
                        }
                        if (TextUtils.isEmpty(c)) {
                            if (TextUtils.isEmpty(b)) {
                                Context context = GtcProvider.context();
                                String strA = com.getui.gtc.b.a.a(context);
                                if (TextUtils.isEmpty(strA)) {
                                    strA = context.getSharedPreferences("GINSIGHT-SDK-PREFERENCE", 0).getString("gicid", null);
                                }
                                b = strA;
                            }
                            if (TextUtils.isEmpty(b)) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("gtc_");
                                String strA2 = com.getui.gtc.i.a.a.a(UUID.randomUUID().toString() + "-" + System.currentTimeMillis() + "-" + GtcProvider.context().getPackageName());
                                sb.append(strA2);
                                char cCharAt = strA2.charAt(strA2.length() + (-1));
                                if (cCharAt < 16) {
                                    sb.append("0");
                                }
                                sb.append(Integer.toHexString(cCharAt));
                                c = sb.toString();
                                d dVar = c.a.a.a;
                                String str2 = c;
                                if (dVar.a(9, str2)) {
                                    dVar.i = str2;
                                }
                                str = c;
                                d = str;
                                a(str);
                            } else {
                                str = b;
                                d = str;
                                a(str);
                            }
                        } else {
                            str = c;
                            d = str;
                            a(str);
                        }
                    }
                    ServerManager.updateConfigServerMap();
                    Log.d("GTC", "gtcid is " + d);
                } catch (Throwable th2) {
                    map.put("gtc.bs", Arrays.asList(strArr3));
                    throw th2;
                }
            } catch (Throwable th3) {
                map.put("gtc.as", Arrays.asList(strArr2));
                throw th3;
            }
        } catch (Throwable th4) {
            map.put("gtc.cs", Arrays.asList(strArr));
            throw th4;
        }
    }

    public static void a(GtcIdCallback gtcIdCallback) throws RemoteException {
        if (gtcIdCallback != null) {
            gtcIdCallback.onSuccess(d);
            j.add(gtcIdCallback);
        }
    }

    private static void a(final String str) {
        com.getui.gtc.h.c.a(str, new c.a() { // from class: com.getui.gtc.c.b.1
            @Override // com.getui.gtc.h.c.a
            public final void a(String str2) {
                b.d = str2;
                if (!TextUtils.equals(str, str2)) {
                    try {
                        Log.d("GTC", "gtcid changed to " + b.d);
                        Iterator it = b.j.iterator();
                        while (it.hasNext()) {
                            ((GtcIdCallback) it.next()).onSuccess(b.d);
                        }
                    } catch (Throwable th) {
                        com.getui.gtc.i.c.a.b(th);
                    }
                }
                b.j.clear();
                d dVar = c.a.a.a;
                String str3 = b.d;
                if (dVar.a(4, str3)) {
                    dVar.d = str3;
                }
            }
        });
    }
}
