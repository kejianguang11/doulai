package com.loc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.igexin.assist.sdk.AssistPushConsts;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class ez {
    Hashtable<String, ArrayList<a>> a = new Hashtable<>();
    private long i = 0;
    private boolean j = false;
    private String k = "2.0.201501131131".replace(".", "");
    private String l = null;
    boolean b = true;
    long c = 0;
    String d = null;
    es e = null;
    private String m = null;
    private long n = 0;
    boolean f = true;
    boolean g = true;
    String h = String.valueOf(AMapLocationClientOption.GeoLanguage.DEFAULT);

    static class a {
        private eo a = null;
        private String b = null;

        protected a() {
        }

        public final eo a() {
            return this.a;
        }

        public final void a(eo eoVar) {
            this.a = eoVar;
        }

        public final void a(String str) {
            this.b = TextUtils.isEmpty(str) ? null : str.replace("##", "#");
        }

        public final String b() {
            return this.b;
        }
    }

    private eo a(String str, StringBuilder sb, boolean z) {
        try {
            a aVarA = (str.contains("cgiwifi") || str.contains("wifi")) ? a(sb, str) : (str.contains("cgi") && this.a.containsKey(str) && this.a.get(str).size() > 0) ? this.a.get(str).get(0) : null;
            if (aVarA != null && fq.a(aVarA.a())) {
                eo eoVarA = aVarA.a();
                eoVarA.e("mem");
                eoVarA.h(aVarA.b());
                if (!z && !fi.a(eoVarA.getTime())) {
                    if (this.a != null && this.a.containsKey(str)) {
                        this.a.get(str).remove(aVarA);
                    }
                }
                if (fq.a(eoVarA)) {
                    this.c = 0L;
                }
                eoVarA.setLocationType(4);
                return eoVarA;
            }
        } catch (Throwable th) {
            fj.a(th, "Cache", "get1");
        }
        return null;
    }

    private a a(StringBuilder sb, String str) {
        a aVar;
        char c;
        a aVar2;
        if (this.a.isEmpty() || TextUtils.isEmpty(sb)) {
            return null;
        }
        if (!this.a.containsKey(str)) {
            return null;
        }
        Hashtable hashtable = new Hashtable();
        Hashtable hashtable2 = new Hashtable();
        Hashtable hashtable3 = new Hashtable();
        ArrayList<a> arrayList = this.a.get(str);
        char c2 = 1;
        int size = arrayList.size() - 1;
        while (size >= 0) {
            a aVar3 = arrayList.get(size);
            if (!TextUtils.isEmpty(aVar3.b())) {
                if (!a(aVar3.b(), sb)) {
                    c = 0;
                } else {
                    if (fq.a(aVar3.b(), sb.toString())) {
                        aVar2 = aVar3;
                        aVar = aVar2;
                        break;
                    }
                    c = c2;
                }
                a(aVar3.b(), (Hashtable<String, String>) hashtable);
                a(sb.toString(), (Hashtable<String, String>) hashtable2);
                hashtable3.clear();
                Iterator it = hashtable.keySet().iterator();
                while (it.hasNext()) {
                    hashtable3.put((String) it.next(), "");
                }
                Iterator it2 = hashtable2.keySet().iterator();
                while (it2.hasNext()) {
                    hashtable3.put((String) it2.next(), "");
                }
                Set setKeySet = hashtable3.keySet();
                double[] dArr = new double[setKeySet.size()];
                double[] dArr2 = new double[setKeySet.size()];
                Iterator it3 = setKeySet.iterator();
                int i = 0;
                while (it3 != null && it3.hasNext()) {
                    String str2 = (String) it3.next();
                    double d = 0.0d;
                    dArr[i] = hashtable.containsKey(str2) ? 1.0d : 0.0d;
                    if (hashtable2.containsKey(str2)) {
                        d = 1.0d;
                    }
                    dArr2[i] = d;
                    i++;
                }
                setKeySet.clear();
                double[] dArrA = a(dArr, dArr2);
                if (dArrA[0] < 0.800000011920929d) {
                    aVar2 = aVar3;
                    if (dArrA[c2] >= Math.min(fi.g(), 0.618d) || (c != 0 && dArrA[0] >= Math.min(fi.g(), 0.618d))) {
                        aVar = aVar2;
                        break;
                    }
                } else {
                    aVar2 = aVar3;
                    aVar = aVar2;
                    break;
                }
            }
            size--;
            c2 = 1;
        }
        aVar = null;
        hashtable.clear();
        hashtable2.clear();
        hashtable3.clear();
        return aVar;
    }

    private String a(String str, StringBuilder sb, Context context) {
        String strSubstring;
        String str2;
        if (context == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            this.l = fq.l(context);
            if (str.contains(com.alipay.sdk.sys.a.b)) {
                str = str.substring(0, str.indexOf(com.alipay.sdk.sys.a.b));
            }
            String strSubstring2 = str.substring(str.lastIndexOf("#") + 1);
            if (!strSubstring2.equals("cgi")) {
                if (!TextUtils.isEmpty(sb) && sb.indexOf(",access") != -1) {
                    jSONObject.put("cgi", str.substring(0, str.length() - (strSubstring2.length() + 9)));
                    String[] strArrSplit = sb.toString().split(",access");
                    strSubstring = strArrSplit[0].contains("#") ? strArrSplit[0].substring(strArrSplit[0].lastIndexOf("#") + 1) : strArrSplit[0];
                    str2 = "mmac";
                }
                return p.b(ey.a(jSONObject.toString().getBytes(com.alipay.sdk.sys.a.m), this.l));
            }
            str2 = "cgi";
            strSubstring = str.substring(0, str.length() - 12);
            jSONObject.put(str2, strSubstring);
            return p.b(ey.a(jSONObject.toString().getBytes(com.alipay.sdk.sys.a.m), this.l));
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:128:0x02f3  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x02f8  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x00c1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0298 A[EDGE_INSN: B:145:0x0298->B:89:0x0298 BREAK  A[LOOP:0: B:42:0x00d6->B:91:0x02a7], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0130 A[Catch: all -> 0x00c8, Throwable -> 0x00cb, TRY_LEAVE, TryCatch #12 {Throwable -> 0x00cb, all -> 0x00c8, blocks: (B:33:0x00c1, B:45:0x00e4, B:47:0x00fe, B:48:0x0102, B:52:0x011f, B:54:0x0130, B:69:0x01d4, B:71:0x01dc, B:73:0x0212, B:74:0x021f, B:76:0x0225, B:49:0x0106, B:51:0x010e, B:58:0x0164, B:65:0x01ba, B:62:0x0185), top: B:136:0x00c1 }] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x02a7 A[LOOP:0: B:42:0x00d6->B:91:0x02a7, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x02b2  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x02b5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void a(Context context, String str, boolean z) throws Exception {
        SQLiteDatabase sQLiteDatabaseOpenOrCreateDatabase;
        Cursor cursorQuery;
        String str2;
        SQLiteDatabase sQLiteDatabase;
        JSONObject jSONObject;
        JSONObject jSONObject2;
        StringBuilder sb;
        String string;
        StringBuilder sb2;
        String string2;
        if (fi.e() && context != null) {
            Cursor cursor = null;
            try {
                try {
                    sQLiteDatabaseOpenOrCreateDatabase = context.openOrCreateDatabase("hmdb", 0, null);
                } catch (Throwable th) {
                    th = th;
                }
            } catch (Throwable th2) {
                th = th2;
                sQLiteDatabaseOpenOrCreateDatabase = null;
            }
            try {
                try {
                    if (!fq.a(sQLiteDatabaseOpenOrCreateDatabase, "hist")) {
                        if (sQLiteDatabaseOpenOrCreateDatabase == null || !sQLiteDatabaseOpenOrCreateDatabase.isOpen()) {
                            return;
                        }
                        sQLiteDatabaseOpenOrCreateDatabase.close();
                        return;
                    }
                    StringBuilder sb3 = new StringBuilder();
                    if (z) {
                        long jA = fq.a() - 172800000;
                        sb3.append("time >");
                        sb3.append(jA);
                        if (str != null) {
                            sb3.append(" and feature = '");
                            str2 = str + "'";
                            sb3.append(str2);
                        }
                        cursorQuery = sQLiteDatabaseOpenOrCreateDatabase.query("hist" + this.k, new String[]{"feature", " nb", "loc"}, sb3.toString(), null, null, null, "time ASC", null);
                        StringBuilder sb4 = new StringBuilder();
                        if (this.l == null) {
                        }
                        if (cursorQuery == null) {
                            sQLiteDatabase = sQLiteDatabaseOpenOrCreateDatabase;
                        }
                        if (cursorQuery != null) {
                        }
                        if (sQLiteDatabase == null) {
                            return;
                        } else {
                            return;
                        }
                    }
                    long jA2 = fq.a() - fi.d();
                    sb3.append("time >");
                    sb3.append(jA2);
                    if (str != null) {
                        sb3.append(" and feature = '");
                        str2 = str + "'";
                        sb3.append(str2);
                    }
                    cursorQuery = sQLiteDatabaseOpenOrCreateDatabase.query("hist" + this.k, new String[]{"feature", " nb", "loc"}, sb3.toString(), null, null, null, "time ASC", null);
                    try {
                        StringBuilder sb42 = new StringBuilder();
                        if (this.l == null) {
                            try {
                                this.l = fq.l(context);
                            } catch (Throwable th3) {
                                th = th3;
                                if (cursorQuery != null) {
                                    cursorQuery.close();
                                }
                                if (sQLiteDatabaseOpenOrCreateDatabase != null && sQLiteDatabaseOpenOrCreateDatabase.isOpen()) {
                                    sQLiteDatabaseOpenOrCreateDatabase.close();
                                }
                                throw th;
                            }
                        }
                        if (cursorQuery == null && cursorQuery.moveToFirst()) {
                            while (true) {
                                if (cursorQuery.getString(0).startsWith("{")) {
                                    jSONObject2 = new JSONObject(cursorQuery.getString(0));
                                    sb42.delete(0, sb42.length());
                                    if (TextUtils.isEmpty(cursorQuery.getString(1))) {
                                        if (fq.a(jSONObject2, "mmac")) {
                                            sb42.append("#");
                                            sb42.append(jSONObject2.getString("mmac"));
                                            string2 = ",access";
                                        }
                                        jSONObject = new JSONObject(cursorQuery.getString(2));
                                        if (fq.a(jSONObject, "type")) {
                                            jSONObject.put("type", "new");
                                        }
                                    } else {
                                        string2 = cursorQuery.getString(1);
                                    }
                                    sb42.append(string2);
                                    jSONObject = new JSONObject(cursorQuery.getString(2));
                                    if (fq.a(jSONObject, "type")) {
                                    }
                                } else {
                                    JSONObject jSONObject3 = new JSONObject(new String(ey.b(p.b(cursorQuery.getString(0)), this.l), com.alipay.sdk.sys.a.m));
                                    sb42.delete(0, sb42.length());
                                    if (!TextUtils.isEmpty(cursorQuery.getString(1))) {
                                        sb42.append(new String(ey.b(p.b(cursorQuery.getString(1)), this.l), com.alipay.sdk.sys.a.m));
                                    } else if (fq.a(jSONObject3, "mmac")) {
                                        sb42.append("#");
                                        sb42.append(jSONObject3.getString("mmac"));
                                        sb42.append(",access");
                                    }
                                    jSONObject = new JSONObject(new String(ey.b(p.b(cursorQuery.getString(2)), this.l), com.alipay.sdk.sys.a.m));
                                    if (fq.a(jSONObject, "type")) {
                                        jSONObject.put("type", "new");
                                    }
                                    jSONObject2 = jSONObject3;
                                }
                                eo eoVar = new eo("");
                                eoVar.b(jSONObject);
                                try {
                                    if (fq.a(jSONObject2, "mmac") && fq.a(jSONObject2, "cgi")) {
                                        String str3 = (jSONObject2.getString("cgi") + "#") + "network#";
                                        if (jSONObject2.getString("cgi").contains("#")) {
                                            sb2 = new StringBuilder();
                                            sb2.append(str3);
                                            sb2.append("cgiwifi");
                                        } else {
                                            sb2 = new StringBuilder();
                                            sb2.append(str3);
                                            sb2.append("wifi");
                                        }
                                        string = sb2.toString();
                                    } else {
                                        if (fq.a(jSONObject2, "cgi")) {
                                            String str4 = (jSONObject2.getString("cgi") + "#") + "network#";
                                            if (jSONObject2.getString("cgi").contains("#")) {
                                                string = str4 + "cgi";
                                            }
                                        }
                                        sb = sb3;
                                        sQLiteDatabase = sQLiteDatabaseOpenOrCreateDatabase;
                                        if (cursorQuery.moveToNext()) {
                                            break;
                                        }
                                        sb3 = sb;
                                        sQLiteDatabaseOpenOrCreateDatabase = sQLiteDatabase;
                                    }
                                    a(string, sb42, eoVar, context, false);
                                    if (cursorQuery.moveToNext()) {
                                    }
                                } catch (Throwable th4) {
                                    th = th4;
                                    sQLiteDatabaseOpenOrCreateDatabase = sQLiteDatabase;
                                    if (cursorQuery != null) {
                                    }
                                    if (sQLiteDatabaseOpenOrCreateDatabase != null) {
                                        sQLiteDatabaseOpenOrCreateDatabase.close();
                                    }
                                    throw th;
                                }
                                sb = sb3;
                                sQLiteDatabase = sQLiteDatabaseOpenOrCreateDatabase;
                            }
                            sb42.delete(0, sb42.length());
                            sb.delete(0, sb.length());
                        } else {
                            sQLiteDatabase = sQLiteDatabaseOpenOrCreateDatabase;
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        if (sQLiteDatabase == null && sQLiteDatabase.isOpen()) {
                            sQLiteDatabase.close();
                            return;
                        }
                        return;
                    } catch (Throwable th5) {
                        th = th5;
                    }
                } catch (Throwable th6) {
                    th = th6;
                }
            } catch (Throwable th7) {
                th = th7;
                cursorQuery = null;
                if (cursorQuery != null) {
                }
                if (sQLiteDatabaseOpenOrCreateDatabase != null) {
                }
                throw th;
            }
            fj.a(th, "DB", "fetchHist p2");
            if (cursor != null) {
                cursor.close();
            }
            if (sQLiteDatabaseOpenOrCreateDatabase == null || !sQLiteDatabaseOpenOrCreateDatabase.isOpen()) {
                return;
            }
            sQLiteDatabaseOpenOrCreateDatabase.close();
        }
    }

    private void a(String str, AMapLocation aMapLocation, StringBuilder sb, Context context) throws Exception {
        SQLiteDatabase sQLiteDatabaseOpenOrCreateDatabase;
        if (context == null) {
            return;
        }
        if (this.l == null) {
            this.l = fq.l(context);
        }
        String strA = a(str, sb, context);
        StringBuilder sb2 = new StringBuilder();
        SQLiteDatabase sQLiteDatabase = null;
        try {
            try {
                sQLiteDatabaseOpenOrCreateDatabase = context.openOrCreateDatabase("hmdb", 0, null);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            sQLiteDatabaseOpenOrCreateDatabase = sQLiteDatabase;
        }
        try {
            sb2.append("CREATE TABLE IF NOT EXISTS hist");
            sb2.append(this.k);
            sb2.append(" (feature VARCHAR PRIMARY KEY, nb VARCHAR, loc VARCHAR, time VARCHAR);");
            sQLiteDatabaseOpenOrCreateDatabase.execSQL(sb2.toString());
            sb2.delete(0, sb2.length());
            sb2.append("REPLACE INTO ");
            sb2.append("hist");
            sb2.append(this.k);
            sb2.append(" VALUES (?, ?, ?, ?)");
            Object[] objArr = new Object[4];
            objArr[0] = strA;
            byte[] bArrA = ey.a(sb.toString().getBytes(com.alipay.sdk.sys.a.m), this.l);
            objArr[1] = bArrA;
            objArr[2] = ey.a(aMapLocation.toStr().getBytes(com.alipay.sdk.sys.a.m), this.l);
            objArr[3] = Long.valueOf(aMapLocation.getTime());
            for (int i = 1; i < 3; i++) {
                objArr[i] = p.b((byte[]) objArr[i]);
            }
            sQLiteDatabaseOpenOrCreateDatabase.execSQL(sb2.toString(), objArr);
            sb2.delete(0, sb2.length());
            sb2.delete(0, sb2.length());
            if (sQLiteDatabaseOpenOrCreateDatabase == null || !sQLiteDatabaseOpenOrCreateDatabase.isOpen()) {
                return;
            }
            sQLiteDatabaseOpenOrCreateDatabase.close();
        } catch (Throwable th3) {
            th = th3;
            sQLiteDatabase = sQLiteDatabaseOpenOrCreateDatabase;
            fj.a(th, "DB", "updateHist");
            sb2.delete(0, sb2.length());
            if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
                return;
            }
            sQLiteDatabase.close();
        }
    }

    private static void a(String str, Hashtable<String, String> hashtable) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        hashtable.clear();
        for (String str2 : str.split("#")) {
            if (!TextUtils.isEmpty(str2) && !str2.contains("|")) {
                hashtable.put(str2, "");
            }
        }
    }

    private boolean a(eo eoVar, boolean z) {
        if (a(z)) {
            return eoVar == null || fi.a(eoVar.getTime()) || z;
        }
        return false;
    }

    private static boolean a(String str, eo eoVar) {
        if (TextUtils.isEmpty(str) || !fq.a(eoVar) || str.startsWith("#")) {
            return false;
        }
        return str.contains("network");
    }

    private static boolean a(String str, StringBuilder sb) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(sb) || !str.contains(",access") || sb.indexOf(",access") == -1) {
            return false;
        }
        String[] strArrSplit = str.split(",access");
        String strSubstring = strArrSplit[0].contains("#") ? strArrSplit[0].substring(strArrSplit[0].lastIndexOf("#") + 1) : strArrSplit[0];
        if (TextUtils.isEmpty(strSubstring)) {
            return false;
        }
        return sb.toString().contains(strSubstring + ",access");
    }

    private boolean a(boolean z) {
        if (fi.e() || z) {
            return this.b || fi.f() || z;
        }
        return false;
    }

    private static double[] a(double[] dArr, double[] dArr2) {
        double[] dArr3 = new double[3];
        double d = 0.0d;
        double d2 = 0.0d;
        double d3 = 0.0d;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < dArr.length; i3++) {
            d += dArr[i3] * dArr[i3];
            d2 += dArr2[i3] * dArr2[i3];
            d3 += dArr[i3] * dArr2[i3];
            if (dArr2[i3] == 1.0d) {
                i2++;
                if (dArr[i3] == 1.0d) {
                    i++;
                }
            }
        }
        dArr3[0] = d3 / (Math.sqrt(d) * Math.sqrt(d2));
        double d4 = i;
        dArr3[1] = (d4 * 1.0d) / ((double) i2);
        dArr3[2] = d4;
        for (int i4 = 0; i4 < 2; i4++) {
            if (dArr3[i4] > 1.0d) {
                dArr3[i4] = 1.0d;
            }
        }
        return dArr3;
    }

    private boolean b() {
        long jB = fq.b() - this.i;
        if (this.i == 0) {
            return false;
        }
        return this.a.size() > 360 || jB > 172800000;
    }

    private void c() {
        this.i = 0L;
        if (!this.a.isEmpty()) {
            this.a.clear();
        }
        this.j = false;
    }

    private void c(Context context) throws Exception {
        SQLiteDatabase sQLiteDatabaseOpenOrCreateDatabase;
        if (context == null) {
            return;
        }
        SQLiteDatabase sQLiteDatabase = null;
        try {
            try {
                sQLiteDatabaseOpenOrCreateDatabase = context.openOrCreateDatabase("hmdb", 0, null);
                try {
                    try {
                        if (!fq.a(sQLiteDatabaseOpenOrCreateDatabase, "hist")) {
                            if (sQLiteDatabaseOpenOrCreateDatabase == null || !sQLiteDatabaseOpenOrCreateDatabase.isOpen()) {
                                return;
                            }
                            sQLiteDatabaseOpenOrCreateDatabase.close();
                            return;
                        }
                        try {
                            sQLiteDatabaseOpenOrCreateDatabase.delete("hist" + this.k, "time<?", new String[]{String.valueOf(fq.a() - 172800000)});
                        } catch (Throwable th) {
                            fj.a(th, "DB", "clearHist");
                            String message = th.getMessage();
                            if (!TextUtils.isEmpty(message)) {
                                message.contains("no such table");
                            }
                        }
                        if (sQLiteDatabaseOpenOrCreateDatabase == null || !sQLiteDatabaseOpenOrCreateDatabase.isOpen()) {
                            return;
                        }
                        sQLiteDatabaseOpenOrCreateDatabase.close();
                    } catch (Throwable th2) {
                        th = th2;
                        sQLiteDatabase = sQLiteDatabaseOpenOrCreateDatabase;
                        fj.a(th, "DB", "clearHist p2");
                        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
                            return;
                        }
                        sQLiteDatabase.close();
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (sQLiteDatabaseOpenOrCreateDatabase != null && sQLiteDatabaseOpenOrCreateDatabase.isOpen()) {
                        sQLiteDatabaseOpenOrCreateDatabase.close();
                    }
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
            }
        } catch (Throwable th5) {
            th = th5;
            sQLiteDatabaseOpenOrCreateDatabase = sQLiteDatabase;
        }
    }

    public final eo a(Context context, String str, StringBuilder sb, boolean z, boolean z2) {
        if (TextUtils.isEmpty(str) || !fi.e()) {
            return null;
        }
        String str2 = str + com.alipay.sdk.sys.a.b + this.f + com.alipay.sdk.sys.a.b + this.g + com.alipay.sdk.sys.a.b + this.h;
        if (str2.contains("gps") || !fi.e() || sb == null) {
            return null;
        }
        if (b()) {
            c();
            return null;
        }
        if (z && !this.j) {
            try {
                String strA = a(str2, sb, context);
                c();
                a(context, strA, z2);
            } catch (Throwable unused) {
            }
        }
        if (this.a.isEmpty()) {
            return null;
        }
        return a(str2, sb, z2);
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x00cc A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00e6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final eo a(et etVar, boolean z, eo eoVar, ex exVar, StringBuilder sb, String str, Context context, boolean z2) {
        boolean z3;
        boolean zA;
        long jA;
        String str2;
        eo eoVarA;
        if (!a(eoVar, z2)) {
            return null;
        }
        try {
            es esVarE = etVar.e();
            boolean z4 = !(esVarE == null && this.e == null) && (this.e == null || !this.e.equals(esVarE));
            if (eoVar != null) {
                z3 = eoVar.getAccuracy() > 299.0f && exVar.e().size() > 5;
            } else {
                z3 = false;
            }
            if (eoVar == null || this.d == null || z3 || z4) {
                zA = false;
            } else {
                zA = fq.a(this.d, sb.toString());
                boolean z5 = this.c != 0 && fq.b() - this.c < 3000;
                if ((zA || z5) && fq.a(eoVar)) {
                    eoVar.e("mem");
                    eoVar.setLocationType(2);
                    return eoVar;
                }
            }
            if (zA) {
                this.c = 0L;
            } else {
                this.c = fq.b();
            }
        } catch (Throwable unused) {
        }
        if (this.m == null || str.equals(this.m)) {
            if (this.m != null) {
                this.n = fq.a();
                str2 = str;
                eoVarA = (z3 || z) ? null : a(context, str2, sb, false, false);
                if (!(z && !fq.a(eoVarA)) || z3 || z) {
                    return null;
                }
                this.c = 0L;
                eoVarA.setLocationType(4);
                return eoVarA;
            }
            jA = fq.a();
        } else {
            if (fq.a() - this.n < 3000) {
                str2 = this.m;
                if (z3) {
                    if (z) {
                        if (z && !fq.a(eoVarA)) {
                        }
                    }
                }
                return null;
            }
            jA = fq.a();
        }
        this.n = jA;
        this.m = str;
        str2 = str;
        if (z3) {
        }
        return null;
    }

    public final void a() {
        this.c = 0L;
        this.d = null;
    }

    public final void a(Context context) {
        if (this.j) {
            return;
        }
        try {
            c();
            a(context, (String) null, false);
        } catch (Throwable th) {
            fj.a(th, "Cache", "loadDB");
        }
        this.j = true;
    }

    public final void a(AMapLocationClientOption aMapLocationClientOption) {
        this.g = aMapLocationClientOption.isNeedAddress();
        this.f = aMapLocationClientOption.isOffset();
        this.b = aMapLocationClientOption.isLocationCacheEnable();
        this.h = String.valueOf(aMapLocationClientOption.getGeoLanguage());
    }

    public final void a(es esVar) {
        this.e = esVar;
    }

    public final void a(String str) {
        this.d = str;
    }

    public final void a(String str, StringBuilder sb, eo eoVar, Context context, boolean z) {
        try {
            if (fq.a(eoVar)) {
                String str2 = str + com.alipay.sdk.sys.a.b + eoVar.isOffset() + com.alipay.sdk.sys.a.b + eoVar.i() + com.alipay.sdk.sys.a.b + eoVar.j();
                if (!a(str2, eoVar) || eoVar.e().equals("mem") || eoVar.e().equals("file") || eoVar.e().equals("wifioff") || "-3".equals(eoVar.d())) {
                    return;
                }
                if (b()) {
                    c();
                }
                JSONObject jSONObjectF = eoVar.f();
                if (fq.a(jSONObjectF, "offpct")) {
                    jSONObjectF.remove("offpct");
                    eoVar.a(jSONObjectF);
                }
                if (str2.contains("wifi")) {
                    if (TextUtils.isEmpty(sb)) {
                        return;
                    }
                    if (eoVar.getAccuracy() >= 300.0f) {
                        int i = 0;
                        for (String str3 : sb.toString().split("#")) {
                            if (str3.contains(com.igexin.push.core.b.an)) {
                                i++;
                            }
                        }
                        if (i >= 8) {
                            return;
                        }
                    } else if (eoVar.getAccuracy() <= 3.0f) {
                        return;
                    }
                    if (str2.contains("cgiwifi") && !TextUtils.isEmpty(eoVar.g())) {
                        String strReplace = str2.replace("cgiwifi", "cgi");
                        eo eoVarH = eoVar.h();
                        if (fq.a(eoVarH)) {
                            a(strReplace, new StringBuilder(), eoVarH, context, true);
                        }
                    }
                } else if (str2.contains("cgi") && ((sb != null && sb.indexOf(com.igexin.push.core.b.an) != -1) || AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_MZ.equals(eoVar.d()))) {
                    return;
                }
                eo eoVarA = a(str2, sb, false);
                if (fq.a(eoVarA) && eoVarA.toStr().equals(eoVar.toStr(3))) {
                    return;
                }
                this.i = fq.b();
                a aVar = new a();
                aVar.a(eoVar);
                aVar.a(TextUtils.isEmpty(sb) ? null : sb.toString());
                if (this.a.containsKey(str2)) {
                    this.a.get(str2).add(aVar);
                } else {
                    ArrayList<a> arrayList = new ArrayList<>();
                    arrayList.add(aVar);
                    this.a.put(str2, arrayList);
                }
                if (z) {
                    try {
                        a(str2, eoVar, sb, context);
                    } catch (Throwable th) {
                        fj.a(th, "Cache", "add");
                    }
                }
            }
        } catch (Throwable th2) {
            fj.a(th2, "Cache", "add");
        }
    }

    public final void b(Context context) {
        try {
            c();
            c(context);
            this.j = false;
            this.d = null;
            this.n = 0L;
        } catch (Throwable th) {
            fj.a(th, "Cache", "destroy part");
        }
    }
}
