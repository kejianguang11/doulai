package com.getui.gtc.dim.b;

import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import com.getui.gtc.dim.AppDataProvider;
import com.getui.gtc.dim.Caller;
import com.getui.gtc.dim.DimSource;
import com.getui.gtc.dim.b.d;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
abstract class f {
    static final List<String> a = Arrays.asList("dim-2-1-21-5", "dim-2-1-21-3", "dim-2-1-21-1");
    AppDataProvider e;
    String f;
    private volatile int k;
    private volatile String r;
    int c = 0;
    int d = 1;
    private int m = 3;
    private final int[] n = {-1, 33};
    private volatile boolean s = true;
    final Map<String, Integer> b = new HashMap();
    private final Map<String, Integer> g = new HashMap();
    private final List<String> i = new ArrayList();
    private final List<String> j = new ArrayList();
    private final Map<String, String> h = new HashMap();
    private final Map<String, Boolean> o = new HashMap(4);
    private final List<String> p = new ArrayList();
    private final List<String> q = new ArrayList();
    private final Map<String, Boolean> l = new HashMap();

    f() {
    }

    private static boolean a(boolean z, String str) {
        if (z && "*".equals(str)) {
            return true;
        }
        int i = Build.VERSION.SDK_INT;
        for (String str2 : str.split("#")) {
            if (str2.contains("-")) {
                String[] strArrSplit = str2.split("-");
                if (strArrSplit.length == 2 && i >= Integer.parseInt(strArrSplit[0]) && i <= Integer.parseInt(strArrSplit[1])) {
                    return true;
                }
            } else if (String.valueOf(i).equals(str2)) {
                return true;
            }
        }
        return false;
    }

    private static String b(Caller caller) {
        if (caller == null) {
            return "";
        }
        switch (caller) {
            case PUSH:
                return "gt";
            case IDO:
                return "ido";
            case GY:
                return "gy";
            case WUS:
                return "wus";
            case ONEID:
                return "oneid";
            default:
                return "";
        }
    }

    public static boolean h(String str) {
        byte b;
        if (!TextUtils.isEmpty(str)) {
            switch (str.hashCode()) {
                case 320919007:
                    b = !str.equals("dim-2-1-21-1") ? (byte) -1 : (byte) 2;
                    break;
                case 320919008:
                    b = !str.equals("dim-2-1-21-2") ? (byte) -1 : (byte) 3;
                    break;
                case 320919009:
                    b = !str.equals("dim-2-1-21-3") ? (byte) -1 : (byte) 1;
                    break;
                case 320919010:
                default:
                    b = -1;
                    break;
                case 320919011:
                    b = !str.equals("dim-2-1-21-5") ? (byte) -1 : (byte) 0;
                    break;
            }
            switch (b) {
                case 0:
                case 1:
                case 2:
                case 3:
                    return true;
            }
        }
        return false;
    }

    private static boolean i(String str) {
        try {
            Class.forName(str);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    private Boolean j(String str) {
        try {
            if (this.l.containsKey(str)) {
                return this.l.get(str);
            }
            d unused = d.a.a;
            h hVarA = d.a(str);
            Boolean bool = hVarA != null ? (Boolean) hVarA.a : null;
            com.getui.gtc.dim.e.b.a("dim sys callable from db : " + str + " : " + bool);
            this.l.put(str, bool);
            return bool;
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a("callable", th);
            return null;
        }
    }

    public int a() {
        return this.m;
    }

    public void a(int i) {
        this.m = i;
        com.getui.gtc.dim.e.b.a("dim sys busi enable set: ".concat(String.valueOf(i)));
    }

    public void a(AppDataProvider appDataProvider) {
        this.e = appDataProvider;
        com.getui.gtc.dim.e.b.a("dim sys app data provider set: ".concat(String.valueOf(appDataProvider)));
    }

    public void a(Caller caller) {
        if (caller != null) {
            synchronized (this) {
                this.k |= caller.index;
            }
        }
        com.getui.gtc.dim.e.b.a("dim sys gtc init caller set: ".concat(String.valueOf(caller)));
    }

    public void a(String str, int i) {
        if (str.equalsIgnoreCase(Build.BRAND)) {
            this.n[0] = i;
        } else if (str.equals("dim-2-2-0-1")) {
            this.n[1] = i;
        }
        com.getui.gtc.dim.e.b.a("dim sys pm policy set: " + str + " : " + i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0035, code lost:
    
        if (r4.j.contains(r6 + ":" + b(r5)) == false) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final boolean a(Caller caller, String str) {
        if (caller != null && caller != Caller.UNKNOWN && caller.containAt(this.k)) {
            try {
                if (!this.j.isEmpty()) {
                }
                if ((this.m & 1) == 0) {
                    com.getui.gtc.dim.e.b.a("dim sys ig ca");
                    return true;
                }
                a aVarA = a.a(str);
                if (aVarA != null) {
                    Boolean boolJ = j(aVarA.a + ":" + caller.name());
                    com.getui.gtc.dim.e.b.a("dim sys get callable " + caller + " : " + str + " : " + boolJ);
                    if (boolJ != null && !boolJ.booleanValue()) {
                        return false;
                    }
                }
                return true;
            } catch (Throwable th) {
                com.getui.gtc.dim.e.b.b(th);
            }
        }
        return false;
    }

    final boolean a(String str) {
        try {
            return this.i.contains(str);
        } catch (Throwable unused) {
            return false;
        }
    }

    public boolean a(String str, Caller caller, boolean z) {
        this.l.put(str + ":" + caller.name(), Boolean.valueOf(z));
        d unused = d.a.a;
        d.a(str + ":" + caller.name(), Boolean.valueOf(z));
        com.getui.gtc.dim.e.b.a("dim sys callable set: " + str + " : " + caller + " : " + z);
        return true;
    }

    public boolean a(String str, String str2) {
        if (Caller.valueOf(str2) == Caller.IDO) {
            return this.s;
        }
        com.getui.gtc.dim.e.b.a("dim sys gbdExecutable get always true");
        return true;
    }

    final int b(String str) {
        Integer num = 0;
        if (!TextUtils.isEmpty(str) && (num = this.g.get(str)) == null) {
            num = this.g.get("dim-2-2-0-1");
        }
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public Boolean b(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            return j(str + ":" + Caller.valueOf(str2).name());
        }
        for (Caller caller : Caller.values()) {
            if (caller.containAt(this.k)) {
                Boolean boolJ = j(str + ":" + caller.name());
                if (boolJ == null || boolJ.booleanValue()) {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    public void b(int i) {
        this.c = i;
        com.getui.gtc.dim.e.b.a("dim sys trace enable set: ".concat(String.valueOf(i)));
    }

    public void b(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.b.put(str, Integer.valueOf(i));
        com.getui.gtc.dim.e.b.a("dim sys globalAllow set: " + str + " : " + i);
    }

    final boolean b() {
        int i = this.n[0];
        return i >= 0 ? Build.VERSION.SDK_INT >= i : Build.VERSION.SDK_INT >= this.n[1];
    }

    public boolean b(String str, Caller caller, boolean z) {
        if (caller != Caller.IDO || i("com.igexin.sdk.PushManager") || i("com.g.gysdk.GYManager") || !i("com.getui.gs.sdk.GsManager")) {
            com.getui.gtc.dim.e.b.a("dim sys gbdExecutable set ignored");
            return false;
        }
        this.s = z;
        com.getui.gtc.dim.e.b.a("dim sys gbdExecutable set: ".concat(String.valueOf(z)));
        return true;
    }

    public int c() {
        return this.k;
    }

    public void c(int i) {
        this.d = i;
        com.getui.gtc.dim.e.b.a("dim sys trace hw oaid enable set: ".concat(String.valueOf(i)));
    }

    public void c(String str) {
        if (str != null) {
            try {
                if (str.contains(new String(Base64.decode("Y29tLmdldHVpLmd0Yy5leHRlbnNpb24uZGlzdHJpYnV0aW9uLmdkaS5zdHViLlB1c2hFeHRlbnNpb24=", 2)))) {
                    this.f = str;
                }
            } catch (Throwable th) {
                com.getui.gtc.dim.e.b.b(th);
                return;
            }
        }
        com.getui.gtc.dim.e.b.a("dim sys gtc dyc config set: ".concat(String.valueOf(str)));
    }

    public void c(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.g.put(str, Integer.valueOf(i));
        com.getui.gtc.dim.e.b.a("dim sys globalAllow policy set: " + str + " : " + i);
    }

    public void c(String str, String str2) {
        try {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            for (String str3 : str2.split("#")) {
                this.j.add(str + ":" + str3);
            }
            com.getui.gtc.dim.e.b.a("dim sys global disallow set: " + str + " : " + str2);
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a("dim sys global disallow set: " + str + " : " + str2, th);
        }
    }

    public void d(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.h.put(str, str2);
        com.getui.gtc.dim.e.b.a("dim sys global trace order set: " + str + " : " + str2);
    }

    final boolean d(String str) {
        for (Caller caller : Caller.values()) {
            if (a(caller, str)) {
                return true;
            }
        }
        return false;
    }

    final DimSource e(String str) {
        ArrayList<Caller> arrayList;
        int i;
        String str2;
        try {
            arrayList = new ArrayList();
            for (Caller caller : Caller.values()) {
                if (a(caller, str)) {
                    arrayList.add(caller);
                }
            }
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a("allowSource key:".concat(String.valueOf(str)), th);
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        if (this.h.isEmpty()) {
            str2 = null;
        } else {
            String str3 = this.h.get(str);
            str2 = str3 == null ? this.h.get("dim-2-2-0-1") : str3;
        }
        if (!TextUtils.isEmpty(str2)) {
            String[] strArrSplit = str2.split("#");
            if (strArrSplit.length >= 4) {
                for (String str4 : strArrSplit) {
                    for (Caller caller2 : arrayList) {
                        if (b(caller2).equals(str4)) {
                            return DimSource.of(caller2);
                        }
                    }
                }
            } else {
                com.getui.gtc.dim.e.b.b("dim sys trace order: " + str2 + " not match for " + str);
            }
        }
        if (arrayList.contains(Caller.IDO)) {
            return DimSource.of(Caller.IDO);
        }
        if (arrayList.contains(Caller.PUSH)) {
            return DimSource.of(Caller.PUSH);
        }
        if (arrayList.contains(Caller.GY)) {
            return DimSource.of(Caller.GY);
        }
        if (arrayList.contains(Caller.WUS)) {
            return DimSource.of(Caller.WUS);
        }
        if (arrayList.contains(Caller.ONEID)) {
            return DimSource.of(Caller.ONEID);
        }
        return null;
    }

    public void e(String str, String str2) {
        boolean zA = a(false, str2);
        this.o.put(str, Boolean.valueOf(zA));
        com.getui.gtc.dim.e.b.a("dim sys black version set: " + str + " : " + str2 + " : " + zA);
    }

    public void f(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.i.add(str);
        com.getui.gtc.dim.e.b.a("dim sys app provider globalAllow set: " + str + " : true");
    }

    public void f(String str, String str2) throws Throwable {
        StringBuilder sb;
        String str3;
        String[] strArrSplit;
        String str4;
        boolean z = false;
        try {
            try {
                str3 = Build.BRAND;
                strArrSplit = str2.split(com.alipay.sdk.sys.a.b);
            } catch (Throwable th) {
                th = th;
            }
            if (str3.equalsIgnoreCase(strArrSplit[0]) && a(true, strArrSplit[1])) {
                String str5 = strArrSplit[2];
                if (!str5.equals("*")) {
                    if (this.r == null) {
                        this.r = com.getui.gtc.dim.c.a.d();
                    }
                    if (TextUtils.isEmpty(this.r)) {
                        sb = new StringBuilder("dim sys black rom set: ");
                    } else {
                        String[] strArrSplit2 = str5.split("#");
                        int length = strArrSplit2.length;
                        int i = 0;
                        while (true) {
                            if (i >= length) {
                                break;
                            }
                            if (this.r.startsWith(strArrSplit2[i])) {
                                z = true;
                                break;
                            }
                            i++;
                        }
                        if (!z) {
                            sb = new StringBuilder("dim sys black rom set: ");
                        }
                    }
                }
                try {
                    this.q.add(str);
                    sb = new StringBuilder("dim sys black rom set: ");
                    sb.append(str);
                    sb.append(" : ");
                    sb.append(str2);
                    str4 = " : true";
                    sb.append(str4);
                } catch (Throwable th2) {
                    th = th2;
                    z = true;
                    com.getui.gtc.dim.e.b.b(th);
                    sb = new StringBuilder("dim sys black rom set: ");
                    sb.append(str);
                    sb.append(" : ");
                    sb.append(str2);
                    sb.append(" : ");
                    sb.append(z);
                }
                com.getui.gtc.dim.e.b.a(sb.toString());
            }
            sb = new StringBuilder("dim sys black rom set: ");
            sb.append(str);
            sb.append(" : ");
            sb.append(str2);
            str4 = " : false";
            sb.append(str4);
            com.getui.gtc.dim.e.b.a(sb.toString());
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public void g(String str, String str2) {
        String[] strArrSplit = str2.split("#");
        int length = strArrSplit.length;
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            if (strArrSplit[i].equals(Build.MODEL)) {
                this.p.add(str);
                z = true;
                break;
            }
            i++;
        }
        com.getui.gtc.dim.e.b.a("dim sys black model set: " + str + " : " + str2 + " : " + z);
    }

    final boolean g(String str) {
        try {
            Boolean bool = this.o.get(str);
            if (bool == null) {
                bool = this.o.get("dim-2-2-0-1");
            }
            if (bool == null) {
                if ((Build.VERSION.SDK_INT >= 34 || Build.VERSION.SDK_INT < 7) && h(str)) {
                    com.getui.gtc.dim.e.b.a("dim sys black version use ld for: " + str + " : true");
                    return true;
                }
            } else if (bool.booleanValue()) {
                return true;
            }
            return this.p.contains(str) || this.q.contains(str);
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.b(th);
            return true;
        }
    }
}
