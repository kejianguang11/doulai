package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.sdk.util.H5PayResultModel;
import com.alipay.sdk.util.e;
import com.alipay.sdk.util.l;
import com.igexin.push.g.q;
import com.igexin.push.g.s;
import com.mobile.auth.gatewayauth.Constant;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class PayTask {
    static final Object a = com.alipay.sdk.util.e.class;
    private static final long h = 3000;
    private static long i = -1;
    private Activity b;
    private com.alipay.sdk.widget.a c;
    private String d = "wappaygw.alipay.com/service/rest.htm";
    private String e = "mclient.alipay.com/service/rest.htm";
    private String f = "mclient.alipay.com/home/exterfaceAssign.htm";
    private Map<String, a> g = new HashMap();

    private class a {
        String a;
        String b;

        private a() {
            this.a = "";
            this.b = "";
        }

        /* synthetic */ a(PayTask payTask, byte b) {
            this();
        }

        private String a() {
            return this.a;
        }

        private void a(String str) {
            this.a = str;
        }

        private String b() {
            return this.b;
        }

        private void b(String str) {
            this.b = str;
        }
    }

    public PayTask(Activity activity) {
        this.b = activity;
        com.alipay.sdk.sys.b bVarA = com.alipay.sdk.sys.b.a();
        Activity activity2 = this.b;
        com.alipay.sdk.data.c.a();
        bVarA.a(activity2);
        com.alipay.sdk.app.statistic.a.a(activity);
        this.c = new com.alipay.sdk.widget.a(activity, com.alipay.sdk.widget.a.b);
    }

    private e.a a() {
        return new h(this);
    }

    private String a(com.alipay.sdk.protocol.b bVar) {
        String[] strArr = bVar.b;
        Intent intent = new Intent(this.b, (Class<?>) H5PayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.PROTOCOL_WEB_VIEW_URL, strArr[0]);
        if (strArr.length == 2) {
            bundle.putString("cookie", strArr[1]);
        }
        intent.putExtras(bundle);
        this.b.startActivity(intent);
        synchronized (a) {
            try {
                a.wait();
            } catch (InterruptedException unused) {
                return i.a();
            }
        }
        String str = i.a;
        return TextUtils.isEmpty(str) ? i.a() : str;
    }

    private String a(String str) {
        String strA = new com.alipay.sdk.sys.a(this.b).a(str);
        if (!strA.contains("paymethod=\"expressGateway\"") && l.c(this.b)) {
            com.alipay.sdk.util.e eVar = new com.alipay.sdk.util.e(this.b, new h(this));
            String strA2 = eVar.a(strA);
            eVar.a = null;
            return TextUtils.equals(strA2, com.alipay.sdk.util.e.b) ? b(strA) : TextUtils.isEmpty(strA2) ? i.a() : strA2;
        }
        return b(strA);
    }

    private static String a(String str, String str2) {
        String str3 = str2 + "={";
        return str.substring(str.indexOf(str3) + str3.length(), str.lastIndexOf(com.alipay.sdk.util.h.d));
    }

    private static boolean a(boolean z, boolean z2, String str, StringBuilder sb, Map<String, String> map, String... strArr) {
        String str2 = "";
        int length = strArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            String str3 = strArr[i2];
            if (!TextUtils.isEmpty(map.get(str3))) {
                str2 = map.get(str3);
                break;
            }
            i2++;
        }
        if (TextUtils.isEmpty(str2)) {
            return !z2;
        }
        if (z) {
            sb.append(com.alipay.sdk.sys.a.b);
        }
        sb.append(str);
        sb.append("=\"");
        sb.append(str2);
        sb.append("\"");
        return true;
    }

    private String b(String str) {
        j jVarA;
        b();
        try {
            try {
                try {
                    List<com.alipay.sdk.protocol.b> listA = com.alipay.sdk.protocol.b.a(new com.alipay.sdk.packet.impl.d().a(this.b.getApplicationContext(), str).a().optJSONObject(com.alipay.sdk.cons.c.c).optJSONObject(com.alipay.sdk.cons.c.d));
                    for (int i2 = 0; i2 < listA.size(); i2++) {
                        if (listA.get(i2).a == com.alipay.sdk.protocol.a.Update) {
                            String[] strArr = listA.get(i2).b;
                            if (strArr.length == 3 && TextUtils.equals(com.alipay.sdk.cons.b.c, strArr[0])) {
                                Context context = com.alipay.sdk.sys.b.a().a;
                                com.alipay.sdk.tid.b bVarA = com.alipay.sdk.tid.b.a();
                                if (!TextUtils.isEmpty(strArr[1]) && !TextUtils.isEmpty(strArr[2])) {
                                    bVarA.a = strArr[1];
                                    bVarA.b = strArr[2];
                                    com.alipay.sdk.tid.a aVar = new com.alipay.sdk.tid.a(context);
                                    try {
                                        aVar.a(com.alipay.sdk.util.a.a(context).a(), com.alipay.sdk.util.a.a(context).b(), bVarA.a, bVarA.b);
                                    } catch (Exception unused) {
                                    } catch (Throwable th) {
                                        aVar.close();
                                        throw th;
                                    }
                                    aVar.close();
                                }
                            }
                        }
                    }
                    c();
                    for (int i3 = 0; i3 < listA.size(); i3++) {
                        if (listA.get(i3).a == com.alipay.sdk.protocol.a.WapPay) {
                            return a(listA.get(i3));
                        }
                    }
                } catch (IOException e) {
                    j jVarA2 = j.a(j.NETWORK_ERROR.h);
                    com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.a, e);
                    c();
                    jVarA = jVarA2;
                }
            } catch (Throwable th2) {
                com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.b, com.alipay.sdk.app.statistic.c.r, th2);
            }
            c();
            jVarA = null;
            if (jVarA == null) {
                jVarA = j.a(j.FAILED.h);
            }
            return i.a(jVarA.h, jVarA.i, "");
        } finally {
            c();
        }
    }

    private void b() {
        if (this.c != null) {
            this.c.a();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        if (this.c != null) {
            this.c.b();
            this.c = null;
        }
    }

    private static boolean d() {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (jElapsedRealtime - i < h) {
            return true;
        }
        i = jElapsedRealtime;
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x00c8 A[Catch: all -> 0x0333, Throwable -> 0x0335, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:7:0x0021, B:14:0x009c, B:16:0x00b2, B:23:0x012d, B:25:0x0143, B:36:0x01c0, B:38:0x01d1, B:40:0x01df, B:42:0x01fc, B:44:0x0227, B:54:0x025c, B:60:0x0290, B:47:0x0238, B:49:0x023e, B:51:0x024c, B:63:0x02ee, B:65:0x02f6, B:67:0x02fc, B:69:0x0304, B:27:0x0159, B:29:0x0161, B:31:0x0169, B:33:0x018e, B:18:0x00c8, B:20:0x00ed, B:9:0x0037, B:11:0x005c), top: B:81:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0159 A[Catch: all -> 0x0333, Throwable -> 0x0335, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:7:0x0021, B:14:0x009c, B:16:0x00b2, B:23:0x012d, B:25:0x0143, B:36:0x01c0, B:38:0x01d1, B:40:0x01df, B:42:0x01fc, B:44:0x0227, B:54:0x025c, B:60:0x0290, B:47:0x0238, B:49:0x023e, B:51:0x024c, B:63:0x02ee, B:65:0x02f6, B:67:0x02fc, B:69:0x0304, B:27:0x0159, B:29:0x0161, B:31:0x0169, B:33:0x018e, B:18:0x00c8, B:20:0x00ed, B:9:0x0037, B:11:0x005c), top: B:81:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0037 A[Catch: all -> 0x0333, Throwable -> 0x0335, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0007, B:7:0x0021, B:14:0x009c, B:16:0x00b2, B:23:0x012d, B:25:0x0143, B:36:0x01c0, B:38:0x01d1, B:40:0x01df, B:42:0x01fc, B:44:0x0227, B:54:0x025c, B:60:0x0290, B:47:0x0238, B:49:0x023e, B:51:0x024c, B:63:0x02ee, B:65:0x02f6, B:67:0x02fc, B:69:0x0304, B:27:0x0159, B:29:0x0161, B:31:0x0169, B:33:0x018e, B:18:0x00c8, B:20:0x00ed, B:9:0x0037, B:11:0x005c), top: B:81:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized String fetchOrderInfoFromH5PayUrl(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                String strTrim = str.trim();
                if (!strTrim.startsWith("https://" + this.d)) {
                    if (strTrim.startsWith("http://" + this.d)) {
                        String strTrim2 = strTrim.replaceFirst("(http|https)://" + this.d + "\\?", "").trim();
                        if (!TextUtils.isEmpty(strTrim2)) {
                            return "_input_charset=\"utf-8\"&ordertoken=\"" + l.a("<request_token>", "</request_token>", l.a(strTrim2).get("req_data")) + "\"&pay_channel_id=\"alipay_sdk\"&bizcontext=\"" + new com.alipay.sdk.sys.a(this.b).a(q.e, "h5tonative") + "\"";
                        }
                    }
                    if (!strTrim.startsWith("https://" + this.e)) {
                        if (strTrim.startsWith("http://" + this.e)) {
                            String strTrim3 = strTrim.replaceFirst("(http|https)://" + this.e + "\\?", "").trim();
                            if (!TextUtils.isEmpty(strTrim3)) {
                                return "_input_charset=\"utf-8\"&ordertoken=\"" + l.a("<request_token>", "</request_token>", l.a(strTrim3).get("req_data")) + "\"&pay_channel_id=\"alipay_sdk\"&bizcontext=\"" + new com.alipay.sdk.sys.a(this.b).a(q.e, "h5tonative") + "\"";
                            }
                        }
                        if (!strTrim.startsWith("https://" + this.f)) {
                            if (strTrim.startsWith("http://" + this.f)) {
                                if (strTrim.contains("alipay.wap.create.direct.pay.by.user") || strTrim.contains("create_forex_trade_wap")) {
                                    if (!TextUtils.isEmpty(strTrim.replaceFirst("(http|https)://" + this.f + "\\?", "").trim())) {
                                        try {
                                            JSONObject jSONObject = new JSONObject();
                                            jSONObject.put(Constant.PROTOCOL_WEB_VIEW_URL, str);
                                            jSONObject.put("bizcontext", new com.alipay.sdk.sys.a(this.b).a(q.e, "h5tonative"));
                                            return "new_external_info==" + jSONObject.toString();
                                        } catch (Throwable unused) {
                                        }
                                    }
                                }
                            }
                            byte b = 0;
                            if (Pattern.compile("^(http|https)://(maliprod\\.alipay\\.com\\/w\\/trade_pay\\.do.?|mali\\.alipay\\.com\\/w\\/trade_pay\\.do.?|mclient\\.alipay\\.com\\/w\\/trade_pay\\.do.?)").matcher(str).find()) {
                                String strA = l.a("?", "", str);
                                if (!TextUtils.isEmpty(strA)) {
                                    Map<String, String> mapA = l.a(strA);
                                    StringBuilder sb = new StringBuilder();
                                    if (a(false, true, com.alipay.sdk.app.statistic.c.H, sb, mapA, com.alipay.sdk.app.statistic.c.H, "alipay_trade_no")) {
                                        a(true, false, "pay_phase_id", sb, mapA, "payPhaseId", "pay_phase_id", "out_relation_id");
                                        sb.append("&biz_sub_type=\"TRADE\"");
                                        sb.append("&biz_type=\"trade\"");
                                        String str2 = mapA.get("app_name");
                                        if (TextUtils.isEmpty(str2) && !TextUtils.isEmpty(mapA.get("cid"))) {
                                            str2 = "ali1688";
                                        } else if (TextUtils.isEmpty(str2) && (!TextUtils.isEmpty(mapA.get("sid")) || !TextUtils.isEmpty(mapA.get("s_id")))) {
                                            str2 = "tb";
                                        }
                                        sb.append("&app_name=\"" + str2 + "\"");
                                        if (!a(true, true, "extern_token", sb, mapA, "extern_token", "cid", "sid", "s_id")) {
                                            return "";
                                        }
                                        a(true, false, "appenv", sb, mapA, "appenv");
                                        sb.append("&pay_channel_id=\"alipay_sdk\"");
                                        a aVar = new a(this, b);
                                        aVar.a = mapA.get("return_url");
                                        aVar.b = mapA.get("pay_order_id");
                                        String str3 = sb.toString() + "&bizcontext=\"" + new com.alipay.sdk.sys.a(this.b).a(q.e, "h5tonative") + "\"";
                                        this.g.put(str3, aVar);
                                        return str3;
                                    }
                                }
                            }
                            if (strTrim.contains("mclient.alipay.com/cashier/mobilepay.htm") || (EnvUtils.isSandBox() && strTrim.contains("mobileclientgw.alipaydev.com/cashier/mobilepay.htm"))) {
                                String strA2 = new com.alipay.sdk.sys.a(this.b).a(q.e, "h5tonative");
                                JSONObject jSONObject2 = new JSONObject();
                                jSONObject2.put(Constant.PROTOCOL_WEB_VIEW_URL, strTrim);
                                jSONObject2.put("bizcontext", strA2);
                                return String.format("new_external_info==%s", jSONObject2.toString());
                            }
                        }
                    }
                }
            }
        } catch (Throwable unused2) {
        }
        return "";
    }

    public synchronized String fetchTradeToken() {
        return com.alipay.sdk.util.i.b(this.b.getApplicationContext(), com.alipay.sdk.util.h.a, "");
    }

    public String getVersion() {
        return com.alipay.sdk.cons.a.f;
    }

    public synchronized H5PayResultModel h5Pay(String str, boolean z) {
        HashMap map;
        String strA;
        H5PayResultModel h5PayResultModel = new H5PayResultModel();
        try {
            str.trim();
            String[] strArrSplit = pay(str, z).split(com.alipay.sdk.util.h.b);
            map = new HashMap();
            for (String str2 : strArrSplit) {
                String strSubstring = str2.substring(0, str2.indexOf("={"));
                String str3 = strSubstring + "={";
                map.put(strSubstring, str2.substring(str2.indexOf(str3) + str3.length(), str2.lastIndexOf(com.alipay.sdk.util.h.d)));
            }
            if (map.containsKey(com.alipay.sdk.util.j.a)) {
                h5PayResultModel.setResultCode((String) map.get(com.alipay.sdk.util.j.a));
            }
        } catch (Throwable unused) {
        }
        if (!map.containsKey("callBackUrl")) {
            if (map.containsKey(com.alipay.sdk.util.j.c)) {
                String str4 = (String) map.get(com.alipay.sdk.util.j.c);
                if (str4.length() > 15) {
                    a aVar = this.g.get(str);
                    if (aVar != null) {
                        h5PayResultModel.setReturnUrl(TextUtils.isEmpty(aVar.b) ? aVar.a : com.alipay.sdk.data.a.b().j.replace("$OrderId$", aVar.b));
                        this.g.remove(str);
                        return h5PayResultModel;
                    }
                    strA = l.a("&callBackUrl=\"", "\"", str4);
                    if (TextUtils.isEmpty(strA)) {
                        strA = l.a("&call_back_url=\"", "\"", str4);
                        if (TextUtils.isEmpty(strA)) {
                            strA = l.a(com.alipay.sdk.cons.a.o, "\"", str4);
                            if (TextUtils.isEmpty(strA)) {
                                strA = URLDecoder.decode(l.a(com.alipay.sdk.cons.a.p, com.alipay.sdk.sys.a.b, str4), s.b);
                                if (TextUtils.isEmpty(strA)) {
                                    strA = URLDecoder.decode(l.a("&callBackUrl=", com.alipay.sdk.sys.a.b, str4), s.b);
                                }
                            }
                        }
                    }
                    if (TextUtils.isEmpty(strA) && !TextUtils.isEmpty(str4) && str4.contains("call_back_url")) {
                        strA = l.b("call_back_url=\"", "\"", str4);
                    }
                    if (TextUtils.isEmpty(strA)) {
                        strA = com.alipay.sdk.data.a.b().j;
                    }
                } else {
                    a aVar2 = this.g.get(str);
                    if (aVar2 != null) {
                        h5PayResultModel.setReturnUrl(aVar2.a);
                        this.g.remove(str);
                        return h5PayResultModel;
                    }
                }
            }
            return h5PayResultModel;
        }
        strA = (String) map.get("callBackUrl");
        h5PayResultModel.setReturnUrl(strA);
        return h5PayResultModel;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(18:(1:12)|13|(1:15)|16|(2:18|(1:20)(2:21|(1:23)))|(2:80|24)|(12:28|(2:30|(1:32))|34|81|35|(1:37)(4:38|(4:41|(1:87)(2:45|(2:46|(1:IC)(2:48|(2:53|(3:92|55|90)(1:56))(3:91|52|89))))|57|39)|85|58)|59|(1:61)|65|66|73|74)|33|34|81|35|(0)(0)|59|(0)|65|66|73|74) */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x013e, code lost:
    
        r12 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x013f, code lost:
    
        com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.b, com.alipay.sdk.app.statistic.c.y, r12);
     */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00bc A[Catch: Throwable -> 0x013e, all -> 0x0160, TryCatch #0 {all -> 0x0160, blocks: (B:24:0x0069, B:26:0x007d, B:28:0x0085, B:30:0x009f, B:32:0x00a5, B:34:0x00ae, B:35:0x00b4, B:59:0x0132, B:61:0x0138, B:38:0x00bc, B:39:0x00c4, B:41:0x00c7, B:43:0x00d1, B:45:0x00db, B:46:0x00f1, B:48:0x00f4, B:50:0x00fe, B:52:0x0108, B:53:0x0118, B:55:0x0122, B:56:0x012b, B:57:0x012e, B:64:0x013f, B:33:0x00aa, B:70:0x0162), top: B:80:0x0069, outer: #3, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0138 A[Catch: Throwable -> 0x013e, all -> 0x0160, TRY_LEAVE, TryCatch #0 {all -> 0x0160, blocks: (B:24:0x0069, B:26:0x007d, B:28:0x0085, B:30:0x009f, B:32:0x00a5, B:34:0x00ae, B:35:0x00b4, B:59:0x0132, B:61:0x0138, B:38:0x00bc, B:39:0x00c4, B:41:0x00c7, B:43:0x00d1, B:45:0x00db, B:46:0x00f1, B:48:0x00f4, B:50:0x00fe, B:52:0x0108, B:53:0x0118, B:55:0x0122, B:56:0x012b, B:57:0x012e, B:64:0x013f, B:33:0x00aa, B:70:0x0162), top: B:80:0x0069, outer: #3, inners: #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized String pay(String str, boolean z) {
        boolean z2;
        String strA;
        Context applicationContext;
        String strA2;
        String str2;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (jElapsedRealtime - i >= h) {
            i = jElapsedRealtime;
            z2 = false;
        } else {
            z2 = true;
        }
        if (z2) {
            j jVarA = j.a(j.DOUBLE_REQUEST.h);
            return i.a(jVarA.h, jVarA.i, "");
        }
        if (z) {
            b();
        }
        if (str.contains(com.alipay.sdk.cons.a.q)) {
            com.alipay.sdk.cons.a.r = true;
        }
        if (com.alipay.sdk.cons.a.r) {
            if (str.startsWith(com.alipay.sdk.cons.a.s)) {
                str = str.substring(str.indexOf(com.alipay.sdk.cons.a.s) + 53);
            } else if (str.startsWith(com.alipay.sdk.cons.a.t)) {
                str = str.substring(str.indexOf(com.alipay.sdk.cons.a.t) + 52);
            }
        }
        try {
            try {
                strA2 = new com.alipay.sdk.sys.a(this.b).a(str);
                str2 = null;
            } catch (Throwable th) {
                com.alipay.sdk.data.a.b().a(this.b.getApplicationContext());
                c();
                com.alipay.sdk.app.statistic.a.a(this.b.getApplicationContext(), str);
                throw th;
            }
        } catch (Throwable unused) {
            strA = i.a();
            com.alipay.sdk.data.a.b().a(this.b.getApplicationContext());
            c();
            applicationContext = this.b.getApplicationContext();
        }
        if (!strA2.contains("paymethod=\"expressGateway\"") && l.c(this.b)) {
            com.alipay.sdk.util.e eVar = new com.alipay.sdk.util.e(this.b, new h(this));
            strA = eVar.a(strA2);
            eVar.a = null;
            if (!TextUtils.equals(strA, com.alipay.sdk.util.e.b)) {
                if (TextUtils.isEmpty(strA)) {
                    strA = i.a();
                }
            }
            Context applicationContext2 = this.b.getApplicationContext();
            if (TextUtils.isEmpty(strA)) {
                String[] strArrSplit = strA.split(com.alipay.sdk.util.h.b);
                String strSubstring = null;
                for (int i2 = 0; i2 < strArrSplit.length; i2++) {
                    if (strArrSplit[i2].startsWith(com.alipay.sdk.util.h.c) && strArrSplit[i2].endsWith(com.alipay.sdk.util.h.d)) {
                        String[] strArrSplit2 = strArrSplit[i2].substring(8, strArrSplit[i2].length() - 1).split(com.alipay.sdk.sys.a.b);
                        int i3 = 0;
                        while (true) {
                            if (i3 >= strArrSplit2.length) {
                                break;
                            }
                            if (strArrSplit2[i3].startsWith(com.alipay.sdk.util.h.e) && strArrSplit2[i3].endsWith("\"")) {
                                strSubstring = strArrSplit2[i3].substring(13, strArrSplit2[i3].length() - 1);
                                break;
                            }
                            if (strArrSplit2[i3].startsWith(com.alipay.sdk.util.h.g)) {
                                strSubstring = strArrSplit2[i3].substring(12);
                                break;
                            }
                            i3++;
                        }
                    }
                }
                str2 = strSubstring;
            }
            if (!TextUtils.isEmpty(str2)) {
                com.alipay.sdk.util.i.a(applicationContext2, com.alipay.sdk.util.h.a, str2);
            }
            com.alipay.sdk.data.a.b().a(this.b.getApplicationContext());
            c();
            applicationContext = this.b.getApplicationContext();
            com.alipay.sdk.app.statistic.a.a(applicationContext, str);
            return strA;
        }
        strA = b(strA2);
        Context applicationContext22 = this.b.getApplicationContext();
        if (TextUtils.isEmpty(strA)) {
        }
        if (!TextUtils.isEmpty(str2)) {
        }
        com.alipay.sdk.data.a.b().a(this.b.getApplicationContext());
        c();
        applicationContext = this.b.getApplicationContext();
        com.alipay.sdk.app.statistic.a.a(applicationContext, str);
        return strA;
    }

    public synchronized boolean payInterceptorWithUrl(String str, boolean z, H5PayCallback h5PayCallback) {
        String strFetchOrderInfoFromH5PayUrl;
        strFetchOrderInfoFromH5PayUrl = fetchOrderInfoFromH5PayUrl(str);
        if (!TextUtils.isEmpty(strFetchOrderInfoFromH5PayUrl)) {
            new Thread(new g(this, strFetchOrderInfoFromH5PayUrl, z, h5PayCallback)).start();
        }
        return !TextUtils.isEmpty(strFetchOrderInfoFromH5PayUrl);
    }

    public synchronized Map<String, String> payV2(String str, boolean z) {
        return com.alipay.sdk.util.j.a(pay(str, z));
    }
}
