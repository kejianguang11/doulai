package com.alipay.sdk.packet;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.widget.TextView;
import com.alipay.sdk.util.h;
import com.alipay.sdk.util.k;
import com.alipay.sdk.util.l;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public abstract class d {
    public static final String a = "msp-gzip";
    public static final String b = "Msp-Param";
    public static final String c = "Operation-Type";
    public static final String d = "content-type";
    public static final String e = "Version";
    public static final String f = "AppId";
    public static final String g = "des-mode";
    public static final String h = "namespace";
    public static final String i = "api_name";
    public static final String j = "api_version";
    public static final String k = "data";
    public static final String l = "params";
    public static final String m = "public_key";
    public static final String n = "device";
    public static final String o = "action";
    public static final String p = "type";
    public static final String q = "method";
    private static com.alipay.sdk.net.a t;
    protected boolean r = true;
    protected boolean s = true;

    private b a(Context context) throws Throwable {
        return a(context, "", k.a(context), true);
    }

    private b a(Context context, String str, String str2) throws Throwable {
        return a(context, str, str2, true);
    }

    public static String a(HashMap<String, String> map, HashMap<String, String> map2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            jSONObject2.put(entry.getKey(), entry.getValue());
        }
        JSONObject jSONObject3 = new JSONObject();
        for (Map.Entry<String, String> entry2 : map2.entrySet()) {
            jSONObject3.put(entry2.getKey(), entry2.getValue());
        }
        jSONObject2.put("params", jSONObject3);
        jSONObject.put(k, jSONObject2);
        return jSONObject.toString();
    }

    private static String a(HttpResponse httpResponse, String str) {
        Header[] allHeaders;
        String name;
        if (httpResponse == null || (allHeaders = httpResponse.getAllHeaders()) == null || allHeaders.length <= 0) {
            return null;
        }
        for (Header header : allHeaders) {
            if (header != null && (name = header.getName()) != null && name.equalsIgnoreCase(str)) {
                return header.getValue();
            }
        }
        return null;
    }

    public static JSONObject a(String str, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("type", str);
        jSONObject2.put(q, str2);
        jSONObject.put("action", jSONObject2);
        return jSONObject;
    }

    private static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(str).getJSONObject(k);
            if (!jSONObject.has("params")) {
                return false;
            }
            String strOptString = jSONObject.getJSONObject("params").optString(m, null);
            if (TextUtils.isEmpty(strOptString)) {
                return false;
            }
            com.alipay.sdk.sys.b.a();
            com.alipay.sdk.data.c.a().a(strOptString);
            return true;
        } catch (JSONException unused) {
            return false;
        }
    }

    private static boolean a(HttpResponse httpResponse) {
        Header[] allHeaders;
        String name;
        String value = null;
        if (httpResponse != null && (allHeaders = httpResponse.getAllHeaders()) != null && allHeaders.length > 0) {
            int length = allHeaders.length;
            int i2 = 0;
            while (true) {
                if (i2 < length) {
                    Header header = allHeaders[i2];
                    if (header != null && (name = header.getName()) != null && name.equalsIgnoreCase(a)) {
                        value = header.getValue();
                        break;
                    }
                    i2++;
                } else {
                    break;
                }
            }
        }
        return Boolean.valueOf(value).booleanValue();
    }

    private static com.alipay.sdk.net.a b(Context context, String str) {
        if (t == null) {
            t = new com.alipay.sdk.net.a(context, str);
        } else if (!TextUtils.equals(str, t.b)) {
            t.b = str;
        }
        return t;
    }

    private static byte[] b(HttpResponse httpResponse) throws Throwable {
        InputStream content;
        byte[] bArr = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            content = httpResponse.getEntity().getContent();
            try {
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                while (true) {
                    try {
                        int i2 = content.read(bArr);
                        if (i2 == -1) {
                            break;
                        }
                        byteArrayOutputStream2.write(bArr, 0, i2);
                    } catch (Throwable th) {
                        th = th;
                        byteArrayOutputStream = byteArrayOutputStream2;
                        if (content != null) {
                            try {
                                content.close();
                            } catch (Exception unused) {
                            }
                        }
                        if (byteArrayOutputStream == null) {
                            throw th;
                        }
                        try {
                            byteArrayOutputStream.close();
                            throw th;
                        } catch (Exception unused2) {
                            throw th;
                        }
                    }
                }
                byte[] byteArray = byteArrayOutputStream2.toByteArray();
                if (content != null) {
                    try {
                        content.close();
                    } catch (Exception unused3) {
                    }
                }
                try {
                    byteArrayOutputStream2.close();
                } catch (Exception unused4) {
                }
                return byteArray;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            content = null;
        }
    }

    public b a(Context context, String str) throws Throwable {
        return a(context, str, k.a(context), true);
    }

    public final b a(Context context, String str, String str2, boolean z) throws Throwable {
        Header[] allHeaders;
        String name;
        e eVar = new e(this.s);
        c cVarA = eVar.a(new b(c(), a(str, a())), this.r);
        if (t == null) {
            t = new com.alipay.sdk.net.a(context, str2);
        } else if (!TextUtils.equals(str2, t.b)) {
            t.b = str2;
        }
        HttpResponse httpResponseA = t.a(cVarA.b, a(cVarA.a, str));
        String value = null;
        if (httpResponseA != null && (allHeaders = httpResponseA.getAllHeaders()) != null && allHeaders.length > 0) {
            int length = allHeaders.length;
            int i2 = 0;
            while (true) {
                if (i2 < length) {
                    Header header = allHeaders[i2];
                    if (header != null && (name = header.getName()) != null && name.equalsIgnoreCase(a)) {
                        value = header.getValue();
                        break;
                    }
                    i2++;
                } else {
                    break;
                }
            }
        }
        b bVarA = eVar.a(new c(Boolean.valueOf(value).booleanValue(), b(httpResponseA)));
        return (bVarA != null && a(bVarA.a) && z) ? a(context, str, str2, false) : bVarA;
    }

    public String a(String str, JSONObject jSONObject) {
        JSONObject jSONObject2;
        com.alipay.sdk.tid.b bVar;
        String strA;
        com.alipay.sdk.sys.b bVarA = com.alipay.sdk.sys.b.a();
        com.alipay.sdk.tid.b bVarA2 = com.alipay.sdk.tid.b.a();
        JSONObject jSONObjectA = com.alipay.sdk.util.b.a(new JSONObject(), jSONObject);
        try {
            jSONObjectA.put(com.alipay.sdk.cons.b.c, bVarA2.a);
            com.alipay.sdk.data.c cVarA = com.alipay.sdk.data.c.a();
            Context context = com.alipay.sdk.sys.b.a().a;
            com.alipay.sdk.util.a aVarA = com.alipay.sdk.util.a.a(context);
            if (TextUtils.isEmpty(cVarA.a)) {
                cVarA.a = "Msp/15.5.5 (" + l.b() + h.b + l.c() + h.b + l.g(context) + h.b + l.i(context) + h.b + l.h(context) + h.b + Float.toString(new TextView(context).getTextSize());
            }
            String str2 = com.alipay.sdk.util.a.b(context).p;
            String strD = l.d();
            String strA2 = aVarA.a();
            String strB = aVarA.b();
            Context context2 = com.alipay.sdk.sys.b.a().a;
            SharedPreferences sharedPreferences = context2.getSharedPreferences("virtualImeiAndImsi", 0);
            String string = sharedPreferences.getString("virtual_imsi", null);
            if (TextUtils.isEmpty(string)) {
                if (TextUtils.isEmpty(com.alipay.sdk.tid.b.a().a)) {
                    String strC = com.alipay.sdk.sys.b.a().c();
                    strA = TextUtils.isEmpty(strC) ? com.alipay.sdk.data.c.b() : strC.substring(3, 18);
                } else {
                    strA = com.alipay.sdk.util.a.a(context2).a();
                }
                string = strA;
                sharedPreferences.edit().putString("virtual_imsi", string).commit();
            }
            Context context3 = com.alipay.sdk.sys.b.a().a;
            SharedPreferences sharedPreferences2 = context3.getSharedPreferences("virtualImeiAndImsi", 0);
            String string2 = sharedPreferences2.getString("virtual_imei", null);
            if (TextUtils.isEmpty(string2)) {
                string2 = TextUtils.isEmpty(com.alipay.sdk.tid.b.a().a) ? com.alipay.sdk.data.c.b() : com.alipay.sdk.util.a.a(context3).b();
                sharedPreferences2.edit().putString("virtual_imei", string2).commit();
            }
            if (bVarA2 != null) {
                cVarA.c = bVarA2.b;
            }
            String strReplace = Build.MANUFACTURER.replace(h.b, " ");
            try {
                String strReplace2 = Build.MODEL.replace(h.b, " ");
                boolean zB = com.alipay.sdk.sys.b.b();
                String str3 = aVarA.a;
                WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
                String ssid = connectionInfo != null ? connectionInfo.getSSID() : "-1";
                WifiInfo connectionInfo2 = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
                String bssid = connectionInfo2 != null ? connectionInfo2.getBSSID() : "00";
                StringBuilder sb = new StringBuilder();
                sb.append(cVarA.a);
                sb.append(h.b);
                sb.append(str2);
                sb.append(h.b);
                sb.append(strD);
                sb.append(h.b);
                sb.append("1");
                sb.append(h.b);
                sb.append(strA2);
                sb.append(h.b);
                sb.append(strB);
                sb.append(h.b);
                sb.append(cVarA.c);
                sb.append(h.b);
                sb.append(strReplace);
                sb.append(h.b);
                sb.append(strReplace2);
                sb.append(h.b);
                sb.append(zB);
                sb.append(h.b);
                sb.append(str3);
                sb.append(";-1;-1;");
                sb.append(cVarA.b);
                sb.append(h.b);
                sb.append(string);
                sb.append(h.b);
                sb.append(string2);
                sb.append(h.b);
                sb.append(ssid);
                sb.append(h.b);
                sb.append(bssid);
                if (bVarA2 != null) {
                    HashMap<String, String> map = new HashMap<>();
                    bVar = bVarA2;
                    map.put(com.alipay.sdk.cons.b.c, bVar.a);
                    map.put(com.alipay.sdk.cons.b.g, com.alipay.sdk.sys.b.a().c());
                    String strB2 = cVarA.b(context, map);
                    if (!TextUtils.isEmpty(strB2)) {
                        sb.append(h.b);
                        sb.append(strB2);
                    }
                } else {
                    bVar = bVarA2;
                }
                sb.append(")");
                jSONObject2 = jSONObjectA;
                try {
                    jSONObject2.put(com.alipay.sdk.cons.b.b, sb.toString());
                    jSONObject2.put(com.alipay.sdk.cons.b.e, l.c(bVarA.a));
                    jSONObject2.put(com.alipay.sdk.cons.b.f, l.b(bVarA.a));
                    jSONObject2.put(com.alipay.sdk.cons.b.d, str);
                    jSONObject2.put(com.alipay.sdk.cons.b.h, com.alipay.sdk.cons.a.d);
                    jSONObject2.put(com.alipay.sdk.cons.b.g, bVarA.c());
                    jSONObject2.put(com.alipay.sdk.cons.b.j, bVar.b);
                    com.alipay.sdk.data.c.a();
                    jSONObject2.put(com.alipay.sdk.cons.b.k, com.alipay.sdk.data.c.a(bVarA.a));
                } catch (Throwable unused) {
                }
            } catch (Throwable unused2) {
                jSONObject2 = jSONObjectA;
            }
        } catch (Throwable unused3) {
            jSONObject2 = jSONObjectA;
        }
        return jSONObject2.toString();
    }

    public List<Header> a(boolean z, String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new BasicHeader(a, String.valueOf(z)));
        arrayList.add(new BasicHeader(c, "alipay.msp.cashier.dispatch.bytes"));
        arrayList.add(new BasicHeader(d, "application/octet-stream"));
        arrayList.add(new BasicHeader(e, "2.0"));
        arrayList.add(new BasicHeader(f, "TAOBAO"));
        arrayList.add(new BasicHeader(b, a.a(str)));
        arrayList.add(new BasicHeader(g, "CBC"));
        return arrayList;
    }

    public abstract JSONObject a() throws JSONException;

    public String b() {
        return "4.9.0";
    }

    public String c() throws JSONException {
        HashMap map = new HashMap();
        map.put(n, Build.MODEL);
        map.put("namespace", "com.alipay.mobilecashier");
        map.put(i, "com.alipay.mcpay");
        map.put(j, b());
        return a((HashMap<String, String>) map, (HashMap<String, String>) new HashMap());
    }
}
