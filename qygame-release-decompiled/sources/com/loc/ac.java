package com.loc;

import android.content.Context;
import android.text.TextUtils;
import com.loc.o;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class ac {
    private static final String a = x.c("SRFZHZUVZT3BOa0ZiemZRQQ");
    private static final String b = x.c("FbGJzX3Nkaw");
    private static final String c = x.c("SWjJuYVh2eEMwSzVmNklFSmh0UXpVb2xtOVM4eU9Ua3E");
    private static final String d = x.c("FQU5EU0RLMTA");
    private static final String e = x.c("FMTAw");
    private static boolean f = false;
    private String g = "";

    public static o.a a() {
        return new o.a() { // from class: com.loc.ac.1
            private ac a = new ac();

            @Override // com.loc.o.a
            public final bl a(byte[] bArr, Map<String, String> map) {
                return new bf(bArr, map);
            }

            @Override // com.loc.o.a
            public final String a() {
                return ac.c();
            }

            @Override // com.loc.o.a
            public final String a(Context context, String str) {
                return ac.a(context, str);
            }

            @Override // com.loc.o.a
            public final String a(String str, String str2, String str3, String str4) {
                return this.a.a(str, str2, str3, str4);
            }

            @Override // com.loc.o.a
            public final Map<String, String> b() {
                return this.a.b();
            }
        };
    }

    public static String a(Context context, String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optInt(x.c("UY29kZQ")) != 1) {
                return "";
            }
            String strOptString = new JSONObject(jSONObject.optString(x.c("FZGF0YQ"))).optString(x.c("FYWRpdQ"));
            if (TextUtils.isEmpty(strOptString)) {
                return "";
            }
            ad.a(strOptString);
            y.a(context).a(strOptString);
            return strOptString;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static String c() {
        return ad.a();
    }

    private String d() {
        if (!TextUtils.isEmpty(this.g)) {
            return this.g;
        }
        String strA = p.a("TUpJaVFGNk5LXHtSX1ZwQlRiV1VVZmtYWU1haV1hYWHCiXJtZcKLdmp8wpFewo1/wphwwoFzZmR8aWp6X2k6XsKDwoF+WGbChGdAScKLwoVXfmNxYEvCjcKLSG7CjGNvwoZtVFZ7WMKXYMKfwo5dZcKHfzZXUG85X0hNOVJrb2U8ZlJGW8KCe8KOV8KQWllrcGrCjcKIT25lUHPCicKGVsKKeG5fwp56XsKbc8KJbUVYR0pqU09gfE5/WT5YeHNAwoDCh1Z4V8KQT3JQYmxQbcKYwpFxdG/Ci3rCmMKQwop+YVbCmWFxwpxBdW07Zjp/ODlAbcKEY1pQwoJowohbV1VmV1laWmtcYGbClXfCk2NvesKdwohdWFnCol/CjWTCmMKicG1ENnAvPFtpcXtfclhfXsKAwolgRWNbS29OwpFafV3CkMKLTcKCwolrU3DCmGnCmX9wdsKPcXDCg3LCnFpGcDVTeTxNWW07bXJePVRfQn3ChGNraFhbwpNcwpXChMKNaFVjeVF8wojChm9YbmvChGDCmHvChGVQWjo0Z3o9djleOztWcVxSfWE9woLChkZdcGTCgVzCjMKUVE12wpV5bcKVwprCnntZworCgsKfwpHCksKnwpHClURURW9YaDtwXU1bck5YX3hSVFZUYlxKWFlua1xeYm9jU8KDa3ZrwpZ5am9Za3jCknR3fA");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < strA.length(); i++) {
            stringBuffer.append((char) (strA.charAt(i) - (i % 48)));
        }
        String string = stringBuffer.toString();
        StringBuffer stringBuffer2 = new StringBuffer();
        for (int i2 = 0; i2 < string.length() / 2; i2++) {
            stringBuffer2.append((char) ((string.charAt(i2) + string.charAt((string.length() - 1) - i2)) / 2));
        }
        this.g = stringBuffer2.toString();
        return this.g;
    }

    public final String a(String str, String str2, String str3, String str4) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(x.c("LdGlk"), str);
            jSONObject.put(x.c("FZGl1"), str2);
            jSONObject.put(x.c("AZGl1Mg"), str3);
            jSONObject.put(x.c("EZGl1Mw"), str4);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        String string = jSONObject.toString();
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        String strA = af.a();
        if (!TextUtils.isEmpty(strA)) {
            String strA2 = aa.a(bc.a((string + "\u0000").getBytes(), strA.getBytes()));
            if (!TextUtils.isEmpty(strA2)) {
                try {
                    return x.c("Fa2V5PQ") + URLEncoder.encode(aa.a(ae.a(strA.getBytes(com.igexin.push.g.s.b), ae.a(d())))) + x.c("SJmRhdGE9") + URLEncoder.encode(strA2);
                } catch (Throwable th2) {
                    th2.printStackTrace();
                }
            }
        }
        return null;
    }

    public final synchronized Map<String, String> b() {
        if (f) {
            return null;
        }
        f = true;
        HashMap map = new HashMap();
        map.put(x.c("FZW50"), x.c("FMg"));
        StringBuilder sb = new StringBuilder();
        sb.append(x.c("SY2hhbm5lbD0"));
        sb.append(b);
        sb.append(x.c("SJmRpdj0"));
        sb.append(d);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(b);
        stringBuffer.append(d);
        stringBuffer.append(x.c("FQA"));
        stringBuffer.append(c);
        String strA = af.a(stringBuffer.toString());
        sb.append(x.c("FJnNpZ249"));
        sb.append(strA.toUpperCase(Locale.US));
        sb.append(x.c("SJm91dHB1dD1qc29u") + "\u0000");
        map.put(x.c("FaW4"), aa.a(bc.a(sb.toString().getBytes(), a.getBytes())));
        map.put(x.c("Sa2V5dA"), e);
        return map;
    }
}
