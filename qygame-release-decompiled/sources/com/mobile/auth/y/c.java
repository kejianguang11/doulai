package com.mobile.auth.y;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public abstract class c {
    public static String a() {
        try {
            try {
                HashSet hashSet = new HashSet();
                HashSet hashSet2 = new HashSet();
                StringBuilder sb = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                StringBuilder sb3 = new StringBuilder();
                if (hashSet.size() > 0) {
                    Object[] array = hashSet.toArray();
                    int iMin = Math.min(array.length, 5);
                    for (int i = 0; i < iMin; i++) {
                        sb.append((String) array[i]);
                        if (i < iMin - 1) {
                            sb.append("-");
                        }
                    }
                    sb3.append("&private_ip=" + sb.toString());
                }
                if (hashSet2.size() > 0) {
                    Object[] array2 = hashSet2.toArray();
                    int iMin2 = Math.min(array2.length, 5);
                    for (int i2 = 0; i2 < iMin2; i2++) {
                        String strSubstring = (String) array2[i2];
                        if (strSubstring.contains("%")) {
                            strSubstring = strSubstring.substring(0, strSubstring.indexOf("%"));
                        }
                        sb2.append(strSubstring);
                        if (i2 < iMin2 - 1) {
                            sb2.append("-");
                        }
                    }
                    sb3.append("&private_ip_v6=" + sb2.toString());
                }
                return sb3.toString();
            } catch (Exception unused) {
                t.b();
                return "";
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }
}
