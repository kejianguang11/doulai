package com.zx.a.I8b7;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

/* JADX INFO: loaded from: classes.dex */
public class r2 implements HostnameVerifier {
    public static final r2 a = new r2();
    public static final Pattern b = Pattern.compile("([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");

    public static List<String> a(X509Certificate x509Certificate, int i) {
        Integer num;
        String str;
        ArrayList arrayList = new ArrayList();
        try {
            Collection<List<?>> subjectAlternativeNames = x509Certificate.getSubjectAlternativeNames();
            if (subjectAlternativeNames == null) {
                return Collections.emptyList();
            }
            for (List<?> list : subjectAlternativeNames) {
                if (list != null && list.size() >= 2 && (num = (Integer) list.get(0)) != null && num.intValue() == i && (str = (String) list.get(1)) != null) {
                    arrayList.add(str);
                }
            }
            return arrayList;
        } catch (CertificateParsingException unused) {
            return Collections.emptyList();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x0107  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean a(String str, X509Certificate x509Certificate) {
        boolean zEquals;
        int length;
        if (b.matcher(str).matches()) {
            List<String> listA = a(x509Certificate, 7);
            int size = listA.size();
            for (int i = 0; i < size; i++) {
                if (str.equalsIgnoreCase(listA.get(i))) {
                    return true;
                }
            }
            return false;
        }
        String lowerCase = str.toLowerCase(Locale.US);
        for (String str2 : a(x509Certificate, 2)) {
            if (lowerCase == null || lowerCase.length() == 0 || lowerCase.startsWith(".") || lowerCase.endsWith("..") || str2 == null || str2.length() == 0 || str2.startsWith(".") || str2.endsWith("..")) {
                zEquals = false;
            } else {
                String str3 = lowerCase.endsWith(".") ? lowerCase : lowerCase + '.';
                if (!str2.endsWith(".")) {
                    str2 = str2 + '.';
                }
                String lowerCase2 = str2.toLowerCase(Locale.US);
                if (!lowerCase2.contains("*")) {
                    zEquals = str3.equals(lowerCase2);
                } else if (lowerCase2.startsWith("*.") && lowerCase2.indexOf(42, 1) == -1 && str3.length() >= lowerCase2.length() && !"*.".equals(lowerCase2)) {
                    String strSubstring = lowerCase2.substring(1);
                    if (str3.endsWith(strSubstring) && ((length = str3.length() - strSubstring.length()) <= 0 || str3.lastIndexOf(46, length - 1) == -1)) {
                        zEquals = true;
                    }
                }
            }
            if (zEquals) {
                return true;
            }
        }
        return false;
    }

    @Override // javax.net.ssl.HostnameVerifier
    public boolean verify(String str, SSLSession sSLSession) {
        try {
            return a(str, (X509Certificate) sSLSession.getPeerCertificates()[0]);
        } catch (SSLException unused) {
            return false;
        }
    }
}
