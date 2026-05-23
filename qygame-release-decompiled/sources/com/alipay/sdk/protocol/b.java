package com.alipay.sdk.protocol;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.util.h;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    public a a;
    public String[] b;
    private String c;

    private b(String str) {
        this.c = str;
    }

    private b(String str, a aVar) {
        this.c = str;
        this.a = aVar;
    }

    private String a() {
        return this.c;
    }

    public static List<b> a(JSONObject jSONObject) {
        ArrayList arrayList = new ArrayList();
        if (jSONObject == null) {
            return arrayList;
        }
        String strOptString = jSONObject.optString("name", "");
        String[] strArrSplit = TextUtils.isEmpty(strOptString) ? null : strOptString.split(h.b);
        for (int i = 0; i < strArrSplit.length; i++) {
            a aVarA = a.a(strArrSplit[i]);
            if (aVarA != a.None) {
                b bVar = new b(strArrSplit[i], aVarA);
                bVar.b = a(strArrSplit[i]);
                arrayList.add(bVar);
            }
        }
        return arrayList;
    }

    private static void a(b bVar) {
        String[] strArr = bVar.b;
        if (strArr.length == 3 && TextUtils.equals(com.alipay.sdk.cons.b.c, strArr[0])) {
            Context context = com.alipay.sdk.sys.b.a().a;
            com.alipay.sdk.tid.b bVarA = com.alipay.sdk.tid.b.a();
            if (TextUtils.isEmpty(strArr[1]) || TextUtils.isEmpty(strArr[2])) {
                return;
            }
            bVarA.a = strArr[1];
            bVarA.b = strArr[2];
            com.alipay.sdk.tid.a aVar = new com.alipay.sdk.tid.a(context);
            try {
                aVar.a(com.alipay.sdk.util.a.a(context).a(), com.alipay.sdk.util.a.a(context).b(), bVarA.a, bVarA.b);
            } catch (Exception unused) {
            } finally {
                aVar.close();
            }
        }
    }

    private static String[] a(String str) {
        ArrayList arrayList = new ArrayList();
        int iIndexOf = str.indexOf(40);
        int iLastIndexOf = str.lastIndexOf(41);
        if (iIndexOf == -1 || iLastIndexOf == -1 || iLastIndexOf <= iIndexOf) {
            return null;
        }
        String[] strArrSplit = str.substring(iIndexOf + 1, iLastIndexOf).split(com.igexin.push.core.b.an);
        if (strArrSplit != null) {
            for (int i = 0; i < strArrSplit.length; i++) {
                if (!TextUtils.isEmpty(strArrSplit[i])) {
                    arrayList.add(strArrSplit[i].trim().replaceAll("'", "").replaceAll("\"", ""));
                }
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    private a b() {
        return this.a;
    }

    private static String[] b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return str.split(h.b);
    }

    private String[] c() {
        return this.b;
    }
}
