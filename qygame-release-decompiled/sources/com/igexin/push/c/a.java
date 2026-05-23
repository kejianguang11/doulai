package com.igexin.push.c;

import android.text.TextUtils;
import com.igexin.push.config.SDKUrlConfig;
import com.igexin.push.core.d;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class a {
    static final String a = com.igexin.push.c.b.a + a.class.getName();
    private static final int q = 10;
    int b;
    protected int g;
    protected volatile long h;
    protected volatile long i;
    boolean j;
    private int l;
    private int m;
    private d n;
    final List<d> c = new ArrayList();
    private final List<b> o = new ArrayList();
    final Object d = new Object();
    private final Object p = new Object();
    public volatile EnumC0031a e = EnumC0031a.NORMAL;
    private int r = 0;
    public AtomicBoolean f = new AtomicBoolean(false);
    final Comparator<d> k = new Comparator<d>() { // from class: com.igexin.push.c.a.1
        private static int a(d dVar, d dVar2) {
            return (int) (dVar.c() - dVar2.c());
        }

        @Override // java.util.Comparator
        public final /* synthetic */ int compare(d dVar, d dVar2) {
            return (int) (dVar.c() - dVar2.c());
        }
    };

    /* JADX INFO: renamed from: com.igexin.push.c.a$a, reason: collision with other inner class name */
    protected enum EnumC0031a {
        NORMAL(0),
        BACKUP(1),
        TRY_NORMAL(2);

        int d;

        EnumC0031a(int i) {
            this.d = -1;
            this.d = i;
        }

        private int a() {
            return this.d;
        }

        public static EnumC0031a a(int i) {
            for (EnumC0031a enumC0031a : values()) {
                if (enumC0031a.d == i) {
                    return enumC0031a;
                }
            }
            return null;
        }
    }

    public static final class b {
        public String a;
        public long b;

        public final b a(JSONObject jSONObject) {
            if (jSONObject == null) {
                return this;
            }
            try {
                this.a = jSONObject.getString("address");
                this.b = jSONObject.getLong("outdateTime");
            } catch (Exception e) {
                com.igexin.c.a.c.a.a(e);
            }
            return this;
        }

        public final JSONObject a() {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("address", this.a);
                jSONObject.put("outdateTime", this.b);
                return jSONObject;
            } catch (Exception e) {
                com.igexin.c.a.c.a.a(e);
                return null;
            }
        }

        public final String toString() {
            return "ServerAddress{address='" + this.a + "', outdateTime=" + this.b + '}';
        }
    }

    private String a(boolean z) {
        try {
            synchronized (this.p) {
                String str = this.j ? com.igexin.push.core.e.at : com.igexin.push.core.e.au;
                if (this.o.isEmpty() && TextUtils.isEmpty(str)) {
                    com.igexin.c.a.c.a.a(a + "cm list size = 0", new Object[0]);
                    this.m = 0;
                    this.l = 0;
                    return null;
                }
                if (this.o.isEmpty() && !TextUtils.isEmpty(str)) {
                    a(str);
                }
                com.igexin.c.a.c.a.a(a + "cm try = " + this.m + " times", new Object[0]);
                if (this.m >= this.o.size() * 1) {
                    com.igexin.c.a.c.a.a(a + "cm invalid", new Object[0]);
                    this.m = 0;
                    this.l = 0;
                    this.o.clear();
                    return null;
                }
                long jCurrentTimeMillis = System.currentTimeMillis();
                Iterator<b> it = this.o.iterator();
                while (it.hasNext()) {
                    b next = it.next();
                    if (next.b < jCurrentTimeMillis) {
                        com.igexin.c.a.c.a.a(a + "|add[" + next.a + "] outDate", new Object[0]);
                        it.remove();
                    }
                }
                h();
                if (this.o.isEmpty()) {
                    return null;
                }
                if (z) {
                    this.m++;
                }
                this.l = this.l >= this.o.size() ? 0 : this.l;
                String str2 = this.o.get(this.l).a;
                this.l++;
                return str2;
            }
        } catch (Exception e) {
            com.igexin.c.a.c.a.a(a, e.toString());
            com.igexin.c.a.c.a.a(a + "|" + e.toString(), new Object[0]);
            return null;
        }
    }

    private void a(String str) {
        try {
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                this.o.add(new b().a(jSONArray.getJSONObject(i)));
            }
            com.igexin.c.a.c.a.a(a + "|get cm from cache, isWf = " + this.j + ", lastCmList = " + str, new Object[0]);
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
    }

    private String b(boolean z) {
        String strA;
        synchronized (this.d) {
            this.b = this.b >= this.c.size() ? 0 : this.b;
            this.n = this.c.get(this.b);
            strA = this.n.a(z);
        }
        return strA;
    }

    private void c(boolean z) {
        this.j = z;
    }

    private List<b> g() {
        return this.o;
    }

    private void h() {
        JSONArray jSONArray = new JSONArray();
        Iterator<b> it = this.o.iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next().a());
        }
        com.igexin.push.core.e.f.a().c(jSONArray.length() == 0 ? "null" : jSONArray.toString(), !this.j);
    }

    private void i() {
        synchronized (this.d) {
            this.b = 0;
            Collections.sort(this.c, this.k);
        }
    }

    private void j() {
        EnumC0031a enumC0031a = this.e;
        com.igexin.c.a.c.a.a(a + "|detect success, current type = " + this.e, new Object[0]);
        if (this.e == EnumC0031a.BACKUP) {
            a(EnumC0031a.TRY_NORMAL);
            com.igexin.push.core.d unused = d.a.a;
            com.igexin.push.e.a.a(true);
        }
    }

    private void k() {
        EnumC0031a enumC0031a = this.e;
        com.igexin.c.a.c.a.a(a + "|before disconnect, type = " + this.e, new Object[0]);
        switch (this.e) {
            case NORMAL:
                if (System.currentTimeMillis() - this.i > com.igexin.push.core.b.J && this.g > com.igexin.push.config.d.t) {
                    a(EnumC0031a.BACKUP);
                    break;
                }
                break;
            case BACKUP:
                if (System.currentTimeMillis() - this.h > com.igexin.push.config.d.r) {
                    a(EnumC0031a.TRY_NORMAL);
                }
                break;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0083 A[Catch: all -> 0x00b7, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0021, B:7:0x0025, B:8:0x0029, B:9:0x0032, B:23:0x00a8, B:11:0x0036, B:13:0x003a, B:14:0x003d, B:16:0x0046, B:17:0x004c, B:18:0x0072, B:19:0x0076, B:21:0x0083, B:22:0x0088), top: B:29:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final synchronized void a(EnumC0031a enumC0031a) {
        String str;
        Object[] objArr;
        com.igexin.c.a.c.a.a(a + "|set domain type = " + enumC0031a, new Object[0]);
        if (com.igexin.push.config.d.g) {
            if (this.e != enumC0031a) {
                a((List<b>) null);
            }
            switch (enumC0031a) {
                case NORMAL:
                    this.b = 0;
                    SDKUrlConfig.setConnectAddress(b(true));
                    if (enumC0031a == EnumC0031a.NORMAL) {
                        this.f.set(false);
                    }
                    SDKUrlConfig.getConnectAddress();
                    str = a + "|set domain type normal cm = " + SDKUrlConfig.getConnectAddress();
                    objArr = new Object[0];
                    com.igexin.c.a.c.a.a(str, objArr);
                    break;
                case BACKUP:
                    this.f.set(true);
                    if (this.e != enumC0031a) {
                        this.h = System.currentTimeMillis();
                    }
                    SDKUrlConfig.setConnectAddress(SDKUrlConfig.XFR_ADDRESS_BAK[0]);
                    SDKUrlConfig.getConnectAddress();
                    str = a + "|set domain type backup cm = " + SDKUrlConfig.getConnectAddress();
                    objArr = new Object[0];
                    com.igexin.c.a.c.a.a(str, objArr);
                    break;
                case TRY_NORMAL:
                    if (this.e != enumC0031a) {
                        this.r = 0;
                    }
                    this.b = 0;
                    SDKUrlConfig.setConnectAddress(b(true));
                    if (enumC0031a == EnumC0031a.NORMAL) {
                    }
                    SDKUrlConfig.getConnectAddress();
                    str = a + "|set domain type normal cm = " + SDKUrlConfig.getConnectAddress();
                    objArr = new Object[0];
                    com.igexin.c.a.c.a.a(str, objArr);
                    break;
            }
            this.e = enumC0031a;
            c.a().f().n();
        }
    }

    public final void a(List<b> list) {
        synchronized (this.p) {
            this.l = 0;
            this.m = 0;
            this.o.clear();
            if (list != null) {
                this.o.addAll(list);
                com.igexin.c.a.c.a.a(a + "|set cm list: " + list.toString(), new Object[0]);
            }
            h();
        }
    }

    public final boolean a() {
        boolean z;
        String strA;
        try {
            com.igexin.push.core.d unused = d.a.a;
            z = true;
            boolean z2 = !com.igexin.push.e.a.d();
            strA = a(z2);
            com.igexin.c.a.c.a.a(a + "|get from cm = " + strA, new Object[0]);
            if (strA == null) {
                if (com.igexin.push.config.d.g && this.e == EnumC0031a.BACKUP) {
                    this.b = this.b >= SDKUrlConfig.XFR_ADDRESS_BAK.length ? 0 : this.b;
                    strA = SDKUrlConfig.XFR_ADDRESS_BAK[this.b];
                    this.b++;
                } else {
                    if (this.n != null && !this.n.d()) {
                        this.b++;
                    }
                    strA = b(z2);
                }
                z = false;
            }
        } catch (Exception e) {
            e = e;
            z = false;
        }
        try {
            if (!SDKUrlConfig.getConnectAddress().equals(strA)) {
                SDKUrlConfig.getConnectAddress();
                com.igexin.c.a.c.a.a(a + "|address changed : form [" + SDKUrlConfig.getConnectAddress() + "] to [" + strA + "]", new Object[0]);
            }
            SDKUrlConfig.setConnectAddress(strA);
        } catch (Exception e2) {
            e = e2;
            com.igexin.c.a.c.a.a(e);
            com.igexin.c.a.c.a.a(a, e.toString());
            com.igexin.c.a.c.a.a(a + "|switch address|" + e.toString(), new Object[0]);
        }
        return z;
    }

    public final synchronized void b() {
        this.m = 0;
        if (this.n != null) {
            this.n.e();
        }
    }

    public final void b(List<d> list) {
        synchronized (this.d) {
            this.c.clear();
            this.c.addAll(list);
            Collections.sort(this.c, this.k);
        }
    }

    public final synchronized void c() {
        this.g++;
        com.igexin.c.a.c.a.a(a + "|loginFailedCnt = " + this.g, new Object[0]);
    }

    public final void d() {
        if (AnonymousClass2.a[this.e.ordinal()] == 2 && System.currentTimeMillis() - this.h > com.igexin.push.config.d.r) {
            a(EnumC0031a.TRY_NORMAL);
        }
    }

    public final void e() {
        if (this.e != EnumC0031a.BACKUP) {
            this.g = 0;
        }
        switch (this.e) {
            case NORMAL:
                this.i = System.currentTimeMillis();
                c.a().f().n();
                this.f.set(false);
                break;
            case TRY_NORMAL:
                a(EnumC0031a.NORMAL);
                this.f.set(false);
                break;
        }
    }

    public final void f() {
        EnumC0031a enumC0031a;
        EnumC0031a enumC0031a2 = this.e;
        com.igexin.c.a.c.a.a(a + "|before disconnect, type = " + this.e, new Object[0]);
        switch (this.e) {
            case NORMAL:
                if (System.currentTimeMillis() - this.i > com.igexin.push.core.b.J && this.g > com.igexin.push.config.d.t) {
                    enumC0031a = EnumC0031a.BACKUP;
                    a(enumC0031a);
                }
                break;
            case BACKUP:
                if (System.currentTimeMillis() - this.h > com.igexin.push.config.d.r) {
                    enumC0031a = EnumC0031a.TRY_NORMAL;
                    a(enumC0031a);
                }
                break;
        }
        if (com.igexin.push.core.e.u && this.e != EnumC0031a.BACKUP) {
            this.i = System.currentTimeMillis();
            c.a().f().n();
        }
        switch (this.e) {
            case TRY_NORMAL:
                int i = this.r + 1;
                this.r = i;
                if (i >= 10) {
                    this.g = 0;
                    this.h = System.currentTimeMillis();
                    a(EnumC0031a.BACKUP);
                }
                break;
        }
    }
}
