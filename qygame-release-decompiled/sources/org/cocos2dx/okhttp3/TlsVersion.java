package org.cocos2dx.okhttp3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public enum TlsVersion {
    TLS_1_3("TLSv1.3"),
    TLS_1_2("TLSv1.2"),
    TLS_1_1("TLSv1.1"),
    TLS_1_0("TLSv1"),
    SSL_3_0("SSLv3");

    final String javaName;

    TlsVersion(String str) {
        this.javaName = str;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static TlsVersion forJavaName(String str) {
        byte b;
        int iHashCode = str.hashCode();
        if (iHashCode != 79201641) {
            if (iHashCode != 79923350) {
                switch (iHashCode) {
                    case -503070503:
                        b = !str.equals("TLSv1.1") ? (byte) -1 : (byte) 2;
                        break;
                    case -503070502:
                        if (str.equals("TLSv1.2")) {
                            b = 1;
                            break;
                        }
                        break;
                    case -503070501:
                        if (str.equals("TLSv1.3")) {
                            b = 0;
                            break;
                        }
                        break;
                }
            } else if (str.equals("TLSv1")) {
                b = 3;
            }
        } else if (str.equals("SSLv3")) {
            b = 4;
        }
        switch (b) {
            case 0:
                return TLS_1_3;
            case 1:
                return TLS_1_2;
            case 2:
                return TLS_1_1;
            case 3:
                return TLS_1_0;
            case 4:
                return SSL_3_0;
            default:
                throw new IllegalArgumentException("Unexpected TLS version: " + str);
        }
    }

    static List<TlsVersion> forJavaNames(String... strArr) {
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String str : strArr) {
            arrayList.add(forJavaName(str));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public String javaName() {
        return this.javaName;
    }
}
