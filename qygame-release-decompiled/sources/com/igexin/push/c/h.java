package com.igexin.push.c;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.text.TextUtils;
import com.igexin.push.c.a;
import com.igexin.push.c.b;
import com.igexin.push.c.e.AnonymousClass1;
import com.igexin.push.config.SDKUrlConfig;
import com.igexin.push.core.d;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public abstract class h {
    private static final String e = b.a + h.class.getName();
    protected long a;
    private Handler i;
    protected final Map<String, e> b = new LinkedHashMap();
    protected final Map<String, d> c = new HashMap();
    private final Object f = new Object();
    private final Object g = new Object();
    protected a d = new a();
    private final Comparator<Map.Entry<String, d>> h = new Comparator<Map.Entry<String, d>>() { // from class: com.igexin.push.c.h.1
        private static int a(Map.Entry<String, d> entry, Map.Entry<String, d> entry2) {
            return (int) (entry.getValue().c() - entry2.getValue().c());
        }

        @Override // java.util.Comparator
        public final /* synthetic */ int compare(Map.Entry<String, d> entry, Map.Entry<String, d> entry2) {
            return (int) (entry.getValue().c() - entry2.getValue().c());
        }
    };

    /* JADX WARN: Removed duplicated region for block: B:6:0x003a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public h(String str, String str2) {
        JSONObject jSONObject;
        JSONArray jSONArray;
        JSONObject jSONObject2;
        if (!SDKUrlConfig.hasMultipleXfr()) {
            a();
            return;
        }
        if (TextUtils.isEmpty(str)) {
            a();
        } else {
            try {
                jSONObject = new JSONObject(str);
            } catch (JSONException e2) {
                com.igexin.c.a.c.a.a(e2);
                jSONObject = null;
            }
            if (jSONObject != null && jSONObject.length() != 0) {
                if (jSONObject.has("lastDetectTime")) {
                    try {
                        this.a = jSONObject.getLong("lastDetectTime");
                    } catch (JSONException e3) {
                        com.igexin.c.a.c.a.a(e3);
                    }
                }
                if (Math.abs(System.currentTimeMillis() - this.a) < b.c) {
                    if (jSONObject.has("list")) {
                        try {
                            jSONArray = jSONObject.getJSONArray("list");
                        } catch (JSONException e4) {
                            com.igexin.c.a.c.a.a(e4);
                            jSONArray = null;
                        }
                        if (jSONArray == null && jSONArray.length() != 0) {
                            List<String> listA = a(jSONArray);
                            if (!listA.isEmpty()) {
                                List<String> defaultXfrList = SDKUrlConfig.getDefaultXfrList();
                                ArrayList arrayList = new ArrayList(defaultXfrList);
                                arrayList.retainAll(listA);
                                if (arrayList.size() != listA.size()) {
                                    com.igexin.c.a.c.a.a(e, "db cache xfr != default, use default");
                                    com.igexin.c.a.c.a.a(e + " | db cache xfr != default, use default", new Object[0]);
                                    arrayList.clear();
                                    defaultXfrList.clear();
                                    listA.clear();
                                    a();
                                } else {
                                    com.igexin.c.a.c.a.a(e, "db cache xfr == default, use cache");
                                    com.igexin.c.a.c.a.a(e + " | db cache xfr == default, use cache", new Object[0]);
                                    b(jSONArray);
                                }
                            }
                        }
                    } else {
                        jSONArray = null;
                        if (jSONArray == null) {
                            a();
                        }
                    }
                }
            }
        }
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        try {
            jSONObject2 = new JSONObject(str2);
        } catch (JSONException e5) {
            com.igexin.c.a.c.a.a(e5);
            jSONObject2 = null;
        }
        if (jSONObject2 == null || jSONObject2.length() == 0) {
            return;
        }
        if (jSONObject2.has("loginFailedlCnt")) {
            try {
                this.d.g = jSONObject2.getInt("loginFailedlCnt");
            } catch (JSONException e6) {
                com.igexin.c.a.c.a.a(e6);
            }
        }
        if (jSONObject2.has("lastChange2BackupTime")) {
            try {
                this.d.h = jSONObject2.getLong("lastChange2BackupTime");
            } catch (JSONException e7) {
                com.igexin.c.a.c.a.a(e7);
            }
        }
        if (jSONObject2.has("lastOfflineTime")) {
            try {
                this.d.i = jSONObject2.getLong("lastOfflineTime");
            } catch (JSONException e8) {
                com.igexin.c.a.c.a.a(e8);
            }
        }
        if (jSONObject2.has("domainType")) {
            try {
                this.d.e = a.EnumC0031a.a(jSONObject2.getInt("domainType"));
                if (this.d.e == a.EnumC0031a.BACKUP) {
                    this.d.f.set(true);
                }
            } catch (JSONException e9) {
                com.igexin.c.a.c.a.a(e9);
            }
        }
    }

    private static d a(JSONObject jSONObject) throws Exception {
        if (!jSONObject.has("domain")) {
            return null;
        }
        d dVar = new d();
        dVar.a(jSONObject.getString("domain"));
        if (jSONObject.has("port")) {
            dVar.b = jSONObject.getInt("port");
        }
        if (jSONObject.has("ip")) {
            dVar.a = jSONObject.getString("ip");
        }
        if (jSONObject.has("consumeTime")) {
            dVar.c = jSONObject.getLong("consumeTime");
        }
        if (jSONObject.has("detectSuccessTime")) {
            dVar.d = jSONObject.getLong("detectSuccessTime");
        }
        if (jSONObject.has("isDomain")) {
            dVar.e = jSONObject.getBoolean("isDomain");
        }
        return dVar;
    }

    private static List<String> a(JSONArray jSONArray) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                arrayList.add(jSONArray.getJSONObject(i).getString("domain"));
            } catch (Exception e2) {
                com.igexin.c.a.c.a.a(e2);
            }
        }
        return arrayList;
    }

    private void a() {
        this.a = 0L;
        if (r()) {
            if (com.igexin.push.core.e.ap != null) {
                com.igexin.push.core.e.f.a().b("null", true);
            }
        } else if (com.igexin.push.core.e.aq != null) {
            com.igexin.push.core.e.f.a().b("null", false);
        }
        List<String> defaultXfrList = SDKUrlConfig.getDefaultXfrList();
        ArrayList arrayList = new ArrayList();
        for (String str : defaultXfrList) {
            d dVar = new d(str, Integer.parseInt(com.igexin.c.a.b.g.a(str)[2]));
            if (defaultXfrList.size() > 1) {
                b(dVar);
            }
            arrayList.add(dVar);
        }
        this.d.b(arrayList);
        defaultXfrList.clear();
    }

    private static List<String> b() {
        return SDKUrlConfig.getDefaultXfrList();
    }

    private void b(d dVar) {
        e eVar = new e();
        eVar.d = c() == b.EnumC0032b.a;
        eVar.a(d());
        eVar.b = dVar;
        synchronized (this.g) {
            this.b.put(dVar.a(), eVar);
        }
    }

    private void b(String str) {
        JSONObject jSONObject;
        JSONArray jSONArray;
        if (TextUtils.isEmpty(str)) {
            a();
            return;
        }
        try {
            jSONObject = new JSONObject(str);
        } catch (JSONException e2) {
            com.igexin.c.a.c.a.a(e2);
            jSONObject = null;
        }
        if (jSONObject == null || jSONObject.length() == 0) {
            a();
            return;
        }
        if (jSONObject.has("lastDetectTime")) {
            try {
                this.a = jSONObject.getLong("lastDetectTime");
            } catch (JSONException e3) {
                com.igexin.c.a.c.a.a(e3);
            }
        }
        if (Math.abs(System.currentTimeMillis() - this.a) >= b.c) {
            a();
            return;
        }
        if (jSONObject.has("list")) {
            try {
                jSONArray = jSONObject.getJSONArray("list");
            } catch (JSONException e4) {
                com.igexin.c.a.c.a.a(e4);
                jSONArray = null;
            }
        } else {
            jSONArray = null;
        }
        if (jSONArray == null || jSONArray.length() == 0) {
            a();
            return;
        }
        List<String> listA = a(jSONArray);
        if (listA.isEmpty()) {
            a();
            return;
        }
        List<String> defaultXfrList = SDKUrlConfig.getDefaultXfrList();
        ArrayList arrayList = new ArrayList(defaultXfrList);
        arrayList.retainAll(listA);
        if (arrayList.size() == listA.size()) {
            com.igexin.c.a.c.a.a(e, "db cache xfr == default, use cache");
            com.igexin.c.a.c.a.a(e + " | db cache xfr == default, use cache", new Object[0]);
            b(jSONArray);
            return;
        }
        com.igexin.c.a.c.a.a(e, "db cache xfr != default, use default");
        com.igexin.c.a.c.a.a(e + " | db cache xfr != default, use default", new Object[0]);
        arrayList.clear();
        defaultXfrList.clear();
        listA.clear();
        a();
    }

    private void b(JSONArray jSONArray) {
        d dVar;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (jSONObject.has("domain")) {
                    dVar = new d();
                    dVar.a(jSONObject.getString("domain"));
                    if (jSONObject.has("port")) {
                        dVar.b = jSONObject.getInt("port");
                    }
                    if (jSONObject.has("ip")) {
                        dVar.a = jSONObject.getString("ip");
                    }
                    if (jSONObject.has("consumeTime")) {
                        dVar.c = jSONObject.getLong("consumeTime");
                    }
                    if (jSONObject.has("detectSuccessTime")) {
                        dVar.d = jSONObject.getLong("detectSuccessTime");
                    }
                    if (jSONObject.has("isDomain")) {
                        dVar.e = jSONObject.getBoolean("isDomain");
                    }
                } else {
                    dVar = null;
                }
                if (dVar != null) {
                    this.c.put(dVar.a(), dVar);
                } else {
                    try {
                        dVar = d(jSONObject.getString("domain"));
                    } catch (Exception e2) {
                        com.igexin.c.a.c.a.a(e2);
                        com.igexin.c.a.c.a.a(e + "|initWithCacheData exception " + e2.toString(), new Object[0]);
                        this.c.clear();
                        a();
                        return;
                    }
                }
                if (dVar != null) {
                    b(dVar);
                    arrayList.add(dVar);
                }
            } catch (Exception e3) {
                com.igexin.c.a.c.a.a(e3);
                com.igexin.c.a.c.a.a(e + "|initWithCacheData exception " + e3.toString(), new Object[0]);
                return;
            }
        }
        this.d.b(arrayList);
    }

    private void c(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        JSONObject jSONObject = null;
        try {
            jSONObject = new JSONObject(str);
        } catch (JSONException e2) {
            com.igexin.c.a.c.a.a(e2);
        }
        if (jSONObject == null || jSONObject.length() == 0) {
            return;
        }
        if (jSONObject.has("loginFailedlCnt")) {
            try {
                this.d.g = jSONObject.getInt("loginFailedlCnt");
            } catch (JSONException e3) {
                com.igexin.c.a.c.a.a(e3);
            }
        }
        if (jSONObject.has("lastChange2BackupTime")) {
            try {
                this.d.h = jSONObject.getLong("lastChange2BackupTime");
            } catch (JSONException e4) {
                com.igexin.c.a.c.a.a(e4);
            }
        }
        if (jSONObject.has("lastOfflineTime")) {
            try {
                this.d.i = jSONObject.getLong("lastOfflineTime");
            } catch (JSONException e5) {
                com.igexin.c.a.c.a.a(e5);
            }
        }
        if (jSONObject.has("domainType")) {
            try {
                this.d.e = a.EnumC0031a.a(jSONObject.getInt("domainType"));
                if (this.d.e == a.EnumC0031a.BACKUP) {
                    this.d.f.set(true);
                }
            } catch (JSONException e6) {
                com.igexin.c.a.c.a.a(e6);
            }
        }
    }

    private static d d(String str) {
        d dVar = new d();
        String[] strArrA = com.igexin.c.a.b.g.a(str);
        dVar.a(str);
        dVar.b = Integer.parseInt(strArrA[2]);
        return dVar;
    }

    protected static void k() {
        com.igexin.push.core.e.f.a().b("null", true);
        com.igexin.push.core.e.f.a().b("null", false);
    }

    private void p() {
        synchronized (this.f) {
            this.c.clear();
        }
    }

    private boolean q() {
        long jAbs = Math.abs(System.currentTimeMillis() - this.a);
        if (jAbs >= (b.c * 2) - 3600) {
            long j = b.c;
            com.igexin.c.a.c.a.a(e + "|current time - last detect time > " + (b.c / 1000) + " s, detect = true", new Object[0]);
            f.a.set(true);
            return true;
        }
        if (!f.a.getAndSet(true)) {
            long jAbs2 = Math.abs(b.c - jAbs);
            f.g().a(jAbs2, TimeUnit.MILLISECONDS);
            com.igexin.c.a.c.a.a(e + "|set next detect time = " + jAbs2, new Object[0]);
        }
        return false;
    }

    private boolean r() {
        return c() == b.EnumC0032b.b;
    }

    protected final e a(String str) {
        synchronized (this.g) {
            for (Map.Entry<String, e> entry : this.b.entrySet()) {
                if (entry.getKey().equals(str)) {
                    return entry.getValue();
                }
            }
            return null;
        }
    }

    protected final void a(d dVar) {
        synchronized (this.f) {
            this.c.put(dVar.a(), dVar);
        }
        a aVar = this.d;
        synchronized (aVar.d) {
            aVar.b = 0;
            Collections.sort(aVar.c, aVar.k);
        }
    }

    public abstract int c();

    public abstract i d();

    public final void e() {
        long jAbs = Math.abs(System.currentTimeMillis() - this.a);
        boolean z = true;
        if (jAbs >= (b.c * 2) - 3600) {
            long j = b.c;
            com.igexin.c.a.c.a.a(e + "|current time - last detect time > " + (b.c / 1000) + " s, detect = true", new Object[0]);
            f.a.set(true);
        } else {
            if (!f.a.getAndSet(true)) {
                long jAbs2 = Math.abs(b.c - jAbs);
                f.g().a(jAbs2, TimeUnit.MILLISECONDS);
                com.igexin.c.a.c.a.a(e + "|set next detect time = " + jAbs2, new Object[0]);
            }
            z = false;
        }
        if (!z) {
            com.igexin.c.a.c.a.a(e + "|startDetect detect = false, return !!!", new Object[0]);
            return;
        }
        com.igexin.c.a.c.a.a(e + "|startDetect detect = true, start detect !!!", new Object[0]);
        i();
    }

    public final void f() {
        synchronized (this.g) {
            for (Map.Entry<String, e> entry : this.b.entrySet()) {
                entry.getValue().a((i) null);
                entry.getValue().a();
            }
        }
    }

    public final void g() {
        f();
        p();
        List<String> defaultXfrList = SDKUrlConfig.getDefaultXfrList();
        synchronized (this.g) {
            int size = this.b.size();
            if (defaultXfrList.size() < size) {
                int size2 = size - defaultXfrList.size();
                Iterator<Map.Entry<String, e>> it = this.b.entrySet().iterator();
                for (int i = 0; it.hasNext() && i < size2; i++) {
                    it.next().getValue().b();
                    it.remove();
                }
            }
            ArrayList arrayList = new ArrayList(this.b.values());
            this.b.clear();
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < defaultXfrList.size(); i2++) {
                d dVar = new d();
                String[] strArrA = com.igexin.c.a.b.g.a(defaultXfrList.get(i2));
                dVar.a(defaultXfrList.get(i2));
                dVar.b = Integer.parseInt(strArrA[2]);
                if (i2 < size) {
                    e eVar = (e) arrayList.get(i2);
                    eVar.b = dVar;
                    this.b.put(dVar.a(), eVar);
                } else {
                    b(dVar);
                }
                arrayList2.add(dVar);
            }
            this.d.b(arrayList2);
        }
    }

    public final void h() {
        f();
        p();
        List<String> defaultXfrList = SDKUrlConfig.getDefaultXfrList();
        synchronized (this.g) {
            Iterator<Map.Entry<String, e>> it = this.b.entrySet().iterator();
            while (it.hasNext()) {
                it.next().getValue().b();
            }
            this.b.clear();
            ArrayList arrayList = new ArrayList();
            d dVar = new d();
            String[] strArrA = com.igexin.c.a.b.g.a(defaultXfrList.get(0));
            dVar.a(defaultXfrList.get(0));
            dVar.b = Integer.parseInt(strArrA[2]);
            arrayList.add(dVar);
            this.d.b(arrayList);
            arrayList.clear();
        }
    }

    public final void i() {
        this.a = System.currentTimeMillis();
        synchronized (this.g) {
            for (Map.Entry<String, e> entry : this.b.entrySet()) {
                entry.getValue();
                entry.getValue().a(d());
                if (entry.getValue().b != null) {
                    entry.getValue().b.b();
                }
                e value = entry.getValue();
                synchronized (i.class) {
                    if (value.c != null) {
                        value.a = com.igexin.b.a.a().a.submit(value.new AnonymousClass1());
                    }
                }
            }
        }
    }

    public final synchronized void j() {
        this.a = System.currentTimeMillis();
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        synchronized (this.g) {
            try {
                jSONObject.put("lastDetectTime", this.a);
                jSONObject.put("list", jSONArray);
                Iterator<Map.Entry<String, e>> it = this.b.entrySet().iterator();
                while (it.hasNext()) {
                    JSONObject jSONObjectF = it.next().getValue().b.f();
                    if (jSONObjectF != null) {
                        jSONArray.put(jSONObjectF);
                    }
                }
            } catch (Exception e2) {
                com.igexin.c.a.c.a.a(e2);
            }
        }
        if (jSONObject.length() > 0) {
            if (r()) {
                com.igexin.push.core.e.f.a().b(jSONObject.toString(), true);
                return;
            }
            com.igexin.push.core.e.f.a().b(jSONObject.toString(), false);
        }
    }

    protected final synchronized void l() {
        a aVar = this.d;
        a.EnumC0031a enumC0031a = aVar.e;
        com.igexin.c.a.c.a.a(a.a + "|detect success, current type = " + aVar.e, new Object[0]);
        if (aVar.e == a.EnumC0031a.BACKUP) {
            aVar.a(a.EnumC0031a.TRY_NORMAL);
            com.igexin.push.core.d unused = d.a.a;
            com.igexin.push.e.a.a(true);
        }
    }

    public final void m() {
        synchronized (h.class) {
            if (this.i == null) {
                HandlerThread handlerThread = new HandlerThread("NetDetect-T");
                handlerThread.start();
                this.i = new Handler(handlerThread.getLooper());
            }
        }
        this.i.removeCallbacksAndMessages("detToken");
        this.i.postAtTime(new Runnable() { // from class: com.igexin.push.c.h.2
            @Override // java.lang.Runnable
            public final void run() {
                String unused = h.e;
                try {
                    h.this.j();
                } catch (Throwable th) {
                    com.igexin.c.a.c.a.a(th);
                }
            }
        }, "detToken", SystemClock.uptimeMillis() + com.igexin.push.config.c.s);
    }

    protected final synchronized void n() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("loginFailedlCnt", this.d.g);
            jSONObject.put("lastChange2BackupTime", this.d.h);
            jSONObject.put("lastOfflineTime", this.d.i);
            jSONObject.put("domainType", this.d.e.d);
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
        }
        if (jSONObject.length() > 0) {
            if (r()) {
                com.igexin.push.core.e.f.a().a(jSONObject.toString(), true);
                return;
            }
            com.igexin.push.core.e.f.a().a(jSONObject.toString(), false);
        }
    }
}
