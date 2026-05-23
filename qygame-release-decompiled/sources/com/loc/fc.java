package com.loc;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.loc.bl;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class fc {
    public static int a = 1;
    public static int b = 2;
    private static fc e;
    private Context j;
    private String k;
    private long c = 0;
    private boolean d = false;
    private ArrayList<String> f = new ArrayList<>();
    private el g = new el();
    private el h = new el();
    private long i = com.igexin.push.config.c.l;
    private boolean l = false;

    private fc(Context context) {
        this.j = context;
    }

    public static synchronized fc a(Context context) {
        if (e == null) {
            e = new fc(context);
        }
        return e;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public el b(int i) {
        return i == b ? this.h : this.g;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x001a A[Catch: all -> 0x000f, TRY_LEAVE, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0009, B:12:0x0012, B:14:0x001a, B:19:0x002b, B:24:0x0038, B:26:0x0052, B:27:0x0087), top: B:32:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0052 A[Catch: all -> 0x000f, LOOP:0: B:25:0x0050->B:26:0x0052, LOOP_END, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0009, B:12:0x0012, B:14:0x001a, B:19:0x002b, B:24:0x0038, B:26:0x0052, B:27:0x0087), top: B:32:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private synchronized void b(boolean z, final int i) {
        if (z) {
            if (this.c != 0) {
            }
            this.c = System.currentTimeMillis();
            this.l = true;
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            StringBuffer stringBuffer = new StringBuffer();
            while (i < r1) {
            }
            cj.a().b(new ck() { // from class: com.loc.fc.1
                @Override // com.loc.ck
                public final void a() {
                    int i2;
                    StringBuilder sb = new StringBuilder("http://");
                    sb.append(fi.q());
                    sb.append("?host=dualstack-a.apilocate.amap.com&query=");
                    sb.append(i == fc.b ? 6 : 4);
                    String string = sb.toString();
                    fd fdVar = new fd();
                    fdVar.b(string);
                    fdVar.c(string);
                    fdVar.a(bl.a.SINGLE);
                    fdVar.a(bl.c.HTTP);
                    try {
                        bg.a();
                        JSONObject jSONObject = new JSONObject(new String(bg.a(fdVar).a));
                        String[] strArrB = fc.b(jSONObject.optJSONArray("ips"), fc.a);
                        if (strArrB != null && strArrB.length > 0 && !fc.b(strArrB, fc.this.b(fc.a).a())) {
                            fc.this.b(fc.a).a(strArrB);
                            fc.this.f(fc.a);
                        }
                        String[] strArrB2 = fc.b(jSONObject.optJSONArray("ipsv6"), fc.b);
                        if (strArrB2 != null && strArrB2.length > 0 && !fc.b(strArrB2, fc.this.b(fc.b).a())) {
                            fc.this.b(fc.b).a(strArrB2);
                            fc.this.f(fc.b);
                        }
                        if ((jSONObject.has("ips") || jSONObject.has("ipsv6")) && jSONObject.has("ttl") && (i2 = jSONObject.getInt("ttl")) > 30) {
                            fc.this.i = i2 * 1000;
                        }
                    } catch (Throwable th) {
                        JSONObject jSONObject2 = new JSONObject();
                        try {
                            jSONObject2.put("key", "dnsError");
                            jSONObject2.put("reason", th.getMessage());
                        } catch (Throwable unused) {
                        }
                        fo.a(fc.this.j, "O018", jSONObject2);
                    }
                }
            });
            return;
        }
        if (!fi.o() && this.l) {
            return;
        }
        if (this.c != 0) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - this.c < this.i) {
                return;
            }
            if (jCurrentTimeMillis - this.c < 60000) {
                return;
            }
        }
        this.c = System.currentTimeMillis();
        this.l = true;
        StackTraceElement[] stackTrace2 = Thread.currentThread().getStackTrace();
        StringBuffer stringBuffer2 = new StringBuffer();
        for (StackTraceElement stackTraceElement : stackTrace2) {
            stringBuffer2.append(stackTraceElement.getClassName() + "(" + stackTraceElement.getMethodName() + ":" + stackTraceElement.getLineNumber() + "),");
        }
        cj.a().b(new ck() { // from class: com.loc.fc.1
            @Override // com.loc.ck
            public final void a() {
                int i2;
                StringBuilder sb = new StringBuilder("http://");
                sb.append(fi.q());
                sb.append("?host=dualstack-a.apilocate.amap.com&query=");
                sb.append(i == fc.b ? 6 : 4);
                String string = sb.toString();
                fd fdVar = new fd();
                fdVar.b(string);
                fdVar.c(string);
                fdVar.a(bl.a.SINGLE);
                fdVar.a(bl.c.HTTP);
                try {
                    bg.a();
                    JSONObject jSONObject = new JSONObject(new String(bg.a(fdVar).a));
                    String[] strArrB = fc.b(jSONObject.optJSONArray("ips"), fc.a);
                    if (strArrB != null && strArrB.length > 0 && !fc.b(strArrB, fc.this.b(fc.a).a())) {
                        fc.this.b(fc.a).a(strArrB);
                        fc.this.f(fc.a);
                    }
                    String[] strArrB2 = fc.b(jSONObject.optJSONArray("ipsv6"), fc.b);
                    if (strArrB2 != null && strArrB2.length > 0 && !fc.b(strArrB2, fc.this.b(fc.b).a())) {
                        fc.this.b(fc.b).a(strArrB2);
                        fc.this.f(fc.b);
                    }
                    if ((jSONObject.has("ips") || jSONObject.has("ipsv6")) && jSONObject.has("ttl") && (i2 = jSONObject.getInt("ttl")) > 30) {
                        fc.this.i = i2 * 1000;
                    }
                } catch (Throwable th) {
                    JSONObject jSONObject2 = new JSONObject();
                    try {
                        jSONObject2.put("key", "dnsError");
                        jSONObject2.put("reason", th.getMessage());
                    } catch (Throwable unused) {
                    }
                    fo.a(fc.this.j, "O018", jSONObject2);
                }
            }
        });
        return;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(String[] strArr, String[] strArr2) {
        if (strArr == null || strArr.length == 0 || strArr2 == null || strArr2.length == 0 || strArr.length != strArr2.length) {
            return false;
        }
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            if (!strArr[i].equals(strArr2[i])) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String[] b(JSONArray jSONArray, int i) throws JSONException {
        if (jSONArray == null || jSONArray.length() == 0) {
            return new String[0];
        }
        int length = jSONArray.length();
        String[] strArr = new String[length];
        for (int i2 = 0; i2 < length; i2++) {
            String string = jSONArray.getString(i2);
            if (!TextUtils.isEmpty(string)) {
                if (i == b) {
                    string = "[" + string + "]";
                }
                strArr[i2] = string;
            }
        }
        return strArr;
    }

    private static String c(int i) {
        return i == b ? "last_ip_6" : "last_ip_4";
    }

    private void d(int i) {
        if (b(i).d()) {
            SharedPreferences.Editor editorA = fp.a(this.j, "cbG9jaXA");
            fp.a(editorA, c(i));
            fp.a(editorA);
            b(i).a(false);
        }
    }

    private String e(int i) {
        String str;
        int i2 = 0;
        b(false, i);
        String[] strArrA = b(i).a();
        if (strArrA == null || strArrA.length <= 0) {
            g(i);
            return b(i).b();
        }
        int length = strArrA.length;
        while (true) {
            if (i2 >= length) {
                str = null;
                break;
            }
            str = strArrA[i2];
            if (!this.f.contains(str)) {
                break;
            }
            i2++;
        }
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        b(i).a(str);
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f(int i) {
        if (b(i).a() == null || b(i).a().length <= 0) {
            return;
        }
        String str = b(i).a()[0];
        if (str.equals(this.k) || this.f.contains(str)) {
            return;
        }
        this.k = str;
        SharedPreferences.Editor editorA = fp.a(this.j, "cbG9jaXA");
        fp.a(editorA, c(i), str);
        fp.a(editorA);
    }

    private void g(int i) {
        String strA = fp.a(this.j, "cbG9jaXA", c(i), (String) null);
        if (TextUtils.isEmpty(strA) || this.f.contains(strA)) {
            return;
        }
        b(i).a(strA);
        b(i).b(strA);
        b(i).a(true);
    }

    public final String a(ff ffVar, int i) {
        try {
            if (fi.p() && ffVar != null) {
                String strB = ffVar.b();
                String host = new URL(strB).getHost();
                if (!"http://abroad.apilocate.amap.com/mobile/binary".equals(strB) && !"abroad.apilocate.amap.com".equals(host)) {
                    String str = "apilocate.amap.com".equalsIgnoreCase(host) ? "httpdns.apilocate.amap.com" : host;
                    if (!m.d(str)) {
                        return null;
                    }
                    String strE = e(i);
                    if (!TextUtils.isEmpty(strE)) {
                        ffVar.d(strB.replace(host, strE));
                        ffVar.a().put(com.alipay.sdk.cons.c.f, str);
                        ffVar.e(str);
                        ffVar.a(i == b);
                        return strE;
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return null;
    }

    public final void a(int i) {
        if (b(i).e()) {
            d(i);
            return;
        }
        this.f.add(b(i).b());
        d(i);
        b(true, i);
    }

    public final void a(boolean z, int i) {
        b(i).b(z);
        if (z) {
            String strC = b(i).c();
            String strB = b(i).b();
            if (TextUtils.isEmpty(strB) || strB.equals(strC)) {
                return;
            }
            SharedPreferences.Editor editorA = fp.a(this.j, "cbG9jaXA");
            fp.a(editorA, c(i), strB);
            fp.a(editorA);
        }
    }
}
