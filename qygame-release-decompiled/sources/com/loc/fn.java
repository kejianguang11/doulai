package com.loc;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.amap.api.location.AMapLocation;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class fn {
    private static fn f;
    private static long i;
    private File d;
    private String e;
    private Context g;
    private boolean h;
    private LinkedHashMap<String, Long> c = new LinkedHashMap<>();
    String a = "";
    String b = null;

    private fn(Context context) {
        this.e = null;
        this.g = context.getApplicationContext();
        String path = this.g.getFilesDir().getPath();
        if (this.e == null) {
            this.e = fq.l(this.g);
        }
        try {
            this.d = new File(path, "reportRecorder");
        } catch (Throwable th) {
            eb.a(th);
        }
        c();
    }

    public static synchronized fn a(Context context) {
        if (f == null) {
            f = new fn(context);
        }
        return f;
    }

    private boolean b(Context context) {
        if (this.b == null) {
            this.b = fp.a(context, "pref", "lastavedate", "0");
        }
        if (this.b.equals(this.a)) {
            return false;
        }
        SharedPreferences.Editor editorA = fp.a(context, "pref");
        fp.a(editorA, "lastavedate", this.a);
        fp.a(editorA);
        this.b = this.a;
        return true;
    }

    private synchronized void c() {
        if (this.c == null || this.c.size() <= 0) {
            try {
                this.a = new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis()));
                Iterator<String> it = fq.a(this.d).iterator();
                while (it.hasNext()) {
                    try {
                        String[] strArrSplit = new String(ey.b(p.b(it.next()), this.e), com.alipay.sdk.sys.a.m).split(com.igexin.push.core.b.an);
                        if (strArrSplit != null && strArrSplit.length > 1) {
                            this.c.put(strArrSplit[0], Long.valueOf(Long.parseLong(strArrSplit[1])));
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
    }

    private void d() {
        try {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Long> entry : this.c.entrySet()) {
                try {
                    sb.append(p.b(ey.a((entry.getKey() + com.igexin.push.core.b.an + entry.getValue()).getBytes(com.alipay.sdk.sys.a.m), this.e)) + "\n");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            String string = sb.toString();
            if (TextUtils.isEmpty(string)) {
                return;
            }
            fq.a(this.d, string);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public final synchronized void a() {
        if (this.h) {
            d();
            this.h = false;
        }
    }

    public final synchronized void a(AMapLocation aMapLocation) {
        try {
            if ((!this.c.containsKey(this.a) && this.c.size() >= 8) || (this.c.containsKey(this.a) && this.c.size() >= 9)) {
                ArrayList arrayList = new ArrayList();
                Iterator<Map.Entry<String, Long>> it = this.c.entrySet().iterator();
                while (it.hasNext()) {
                    try {
                        arrayList.add(it.next().getKey());
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                    if (arrayList.size() == this.c.size() - 7) {
                        break;
                    }
                }
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    this.c.remove((String) it2.next());
                }
            }
            if (aMapLocation.getErrorCode() != 0) {
                return;
            }
            if (aMapLocation.getLocationType() != 6 && aMapLocation.getLocationType() != 5) {
                if (this.c.containsKey(this.a)) {
                    long jLongValue = this.c.get(this.a).longValue() + 1;
                    i = jLongValue;
                    this.c.put(this.a, Long.valueOf(jLongValue));
                } else {
                    this.c.put(this.a, 1L);
                    i = 1L;
                }
                if (i != 0 && i % 100 == 0) {
                    a();
                }
                this.h = true;
                return;
            }
            return;
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
    }

    public final synchronized void b() {
        try {
            if (b(this.g)) {
                for (Map.Entry<String, Long> entry : this.c.entrySet()) {
                    try {
                        if (!this.a.equals(entry.getKey())) {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("param_long_first", entry.getKey());
                            jSONObject.put("param_long_second", entry.getValue());
                            fo.a(this.g, "O023", jSONObject);
                        }
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }
            }
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
    }
}
