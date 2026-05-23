package com.getui.gtc.dim.b;

import android.text.TextUtils;
import com.getui.gtc.base.GtcProvider;
import com.getui.gtc.base.db.DbManager;
import com.getui.gtc.dim.Caller;
import com.igexin.push.core.b.n;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class d {
    public final Map<String, Long> a;

    public static class a {
        private static final d a = new d(0);
    }

    private d() {
        this.a = new HashMap();
        try {
            DbManager.init(GtcProvider.context(), com.getui.gtc.dim.a.a.class, com.getui.gtc.dim.a.b.class);
            ((com.getui.gtc.dim.a.b) DbManager.getTable(com.getui.gtc.dim.a.a.class, com.getui.gtc.dim.a.b.class)).a();
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a(th);
        }
    }

    /* synthetic */ d(byte b) {
        this();
    }

    public static h a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return ((com.getui.gtc.dim.a.b) DbManager.getTable(com.getui.gtc.dim.a.a.class, com.getui.gtc.dim.a.b.class)).a(str);
    }

    public static boolean a(String str, Object obj) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return ((com.getui.gtc.dim.a.b) DbManager.getTable(com.getui.gtc.dim.a.a.class, com.getui.gtc.dim.a.b.class)).a(str, obj);
    }

    private Long b(String str) {
        try {
            if (this.a.containsKey(str)) {
                return this.a.get(str);
            }
            d unused = a.a;
            h hVarA = a(str);
            Long l = hVarA != null ? (Long) hVarA.a : null;
            com.getui.gtc.dim.e.b.a("dim interval from db : " + str + " : " + l);
            this.a.put(str, l);
            return l;
        } catch (Throwable th) {
            com.getui.gtc.dim.e.b.a("interval", th);
            return null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0184  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static long c(String str) {
        byte b;
        switch (str.hashCode()) {
            case 320892099:
                b = !str.equals("dim-2-1-14-1") ? (byte) -1 : (byte) 28;
                break;
            case 320894021:
                if (str.equals("dim-2-1-16-1")) {
                    b = 26;
                    break;
                }
                break;
            case 320894022:
                if (str.equals("dim-2-1-16-2")) {
                    b = 27;
                    break;
                }
                break;
            case 320894982:
                if (str.equals("dim-2-1-17-1")) {
                    b = 16;
                    break;
                }
                break;
            case 320894983:
                if (str.equals("dim-2-1-17-2")) {
                    b = 17;
                    break;
                }
                break;
            case 320894984:
                if (str.equals("dim-2-1-17-3")) {
                    b = 18;
                    break;
                }
                break;
            case 320894985:
                if (str.equals("dim-2-1-17-4")) {
                    b = 19;
                    break;
                }
                break;
            case 320895943:
                if (str.equals("dim-2-1-18-1")) {
                    b = 20;
                    break;
                }
                break;
            case 320895944:
                if (str.equals("dim-2-1-18-2")) {
                    b = 21;
                    break;
                }
                break;
            case 320895945:
                if (str.equals("dim-2-1-18-3")) {
                    b = 22;
                    break;
                }
                break;
            case 320895946:
                if (str.equals("dim-2-1-18-4")) {
                    b = 23;
                    break;
                }
                break;
            case 320896904:
                if (str.equals("dim-2-1-19-1")) {
                    b = 24;
                    break;
                }
                break;
            case 320896905:
                if (str.equals("dim-2-1-19-2")) {
                    b = 25;
                    break;
                }
                break;
            case 320919007:
                if (str.equals("dim-2-1-21-1")) {
                    b = 30;
                    break;
                }
                break;
            case 320919008:
                if (str.equals("dim-2-1-21-2")) {
                    b = 29;
                    break;
                }
                break;
            case 320919009:
                if (str.equals("dim-2-1-21-3")) {
                    b = 31;
                    break;
                }
                break;
            case 320919011:
                if (str.equals("dim-2-1-21-5")) {
                    b = 32;
                    break;
                }
                break;
            case 1672919129:
                if (str.equals("dim-2-1-1-1")) {
                    b = 0;
                    break;
                }
                break;
            case 1672919131:
                if (str.equals("dim-2-1-1-3")) {
                    b = 1;
                    break;
                }
                break;
            case 1672919132:
                if (str.equals("dim-2-1-1-4")) {
                    b = 2;
                    break;
                }
                break;
            case 1672920090:
                if (str.equals("dim-2-1-2-1")) {
                    b = 3;
                    break;
                }
                break;
            case 1672920092:
                if (str.equals("dim-2-1-2-3")) {
                    b = 4;
                    break;
                }
                break;
            case 1672920093:
                if (str.equals("dim-2-1-2-4")) {
                    b = 5;
                    break;
                }
                break;
            case 1672921051:
                if (str.equals("dim-2-1-3-1")) {
                    b = 9;
                    break;
                }
                break;
            case 1672921052:
                if (str.equals("dim-2-1-3-2")) {
                    b = 10;
                    break;
                }
                break;
            case 1672922012:
                if (str.equals("dim-2-1-4-1")) {
                    b = 15;
                    break;
                }
                break;
            case 1672922973:
                if (str.equals("dim-2-1-5-1")) {
                    b = n.l;
                    break;
                }
                break;
            case 1672922974:
                if (str.equals("dim-2-1-5-2")) {
                    b = 12;
                    break;
                }
                break;
            case 1672923934:
                if (str.equals("dim-2-1-6-1")) {
                    b = 6;
                    break;
                }
                break;
            case 1672923936:
                if (str.equals("dim-2-1-6-3")) {
                    b = 7;
                    break;
                }
                break;
            case 1672923937:
                if (str.equals("dim-2-1-6-4")) {
                    b = 8;
                    break;
                }
                break;
            case 1672924895:
                if (str.equals("dim-2-1-7-1")) {
                    b = 13;
                    break;
                }
                break;
            case 1672925856:
                if (str.equals("dim-2-1-8-1")) {
                    b = 14;
                    break;
                }
                break;
        }
        switch (b) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                return com.igexin.push.core.b.J;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
                return com.igexin.push.config.c.s;
            case 29:
            case 30:
            case 31:
            case 32:
                return 21600000L;
            default:
                return 0L;
        }
    }

    public final Long a(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            return b(str + ":" + Caller.valueOf(str2).name());
        }
        g gVarD = g.d();
        int iC = gVarD.c();
        com.getui.gtc.dim.b.a aVarA = com.getui.gtc.dim.b.a.a(str);
        if (aVarA == null) {
            return null;
        }
        Long l = null;
        for (Caller caller : Caller.values()) {
            if (caller.containAt(iC)) {
                Boolean boolB = gVarD.b(aVarA.a, caller.name());
                Long lB = b(str + ":" + caller.name());
                com.getui.gtc.dim.e.b.a("dim check interval for " + str + ", inited caller = " + caller + ", callable = " + boolB + ", interval = " + lB);
                if (boolB == null || boolB.booleanValue()) {
                    if (lB == null) {
                        return null;
                    }
                    if (l == null || lB.longValue() < l.longValue()) {
                        l = lB;
                    }
                }
            }
        }
        return l;
    }

    public final void a(String str, long j) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.a.put(str, Long.valueOf(j));
        com.getui.gtc.dim.e.b.a("dim storage globalValidTime set: " + str + " : " + j);
    }

    public final boolean a(h hVar, String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long jC = c(str);
        Long l = this.a.get(str);
        if (l == null) {
            l = this.a.get("dim-2-2-0-1");
        }
        long jLongValue = l != null ? l.longValue() : jC;
        Long lA = null;
        if ((g.d().a() & 2) != 0) {
            com.getui.gtc.dim.b.a aVarA = com.getui.gtc.dim.b.a.a(str);
            if (aVarA != null) {
                lA = a(aVarA.b, (String) null);
            }
        } else {
            com.getui.gtc.dim.e.b.a("dim ig in");
        }
        if (lA != null) {
            jLongValue = lA.longValue();
        }
        com.getui.gtc.dim.e.b.a("dim storageValidTime check for " + str + ", dycValue = " + l + ", localValue = " + jC + ", interval = " + lA + ", use " + jLongValue);
        boolean z = jCurrentTimeMillis - hVar.b > jLongValue;
        if (z) {
            com.getui.gtc.dim.e.b.b("dim storage source expired for ".concat(String.valueOf(str)));
        }
        return z;
    }
}
