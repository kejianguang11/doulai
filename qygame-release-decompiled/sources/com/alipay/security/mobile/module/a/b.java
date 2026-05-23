package com.alipay.security.mobile.module.a;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    /* JADX WARN: Can't wrap try/catch for region: R(2:(3:31|7|(1:9)(0))|29) */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String a(String str, String str2) throws Throwable {
        BufferedReader bufferedReader;
        File file;
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader2 = null;
        try {
            file = new File(str, str2);
        } catch (IOException unused) {
            bufferedReader = null;
        } catch (Throwable th) {
            th = th;
        }
        if (!file.exists()) {
            return null;
        }
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), com.alipay.sdk.sys.a.m));
        while (true) {
            try {
                String line = bufferedReader.readLine();
                if (line != null) {
                    sb.append(line);
                }
            } catch (IOException unused2) {
                if (bufferedReader != null) {
                    break;
                }
                return sb.toString();
            } catch (Throwable th2) {
                th = th2;
                bufferedReader2 = bufferedReader;
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (Throwable unused3) {
                    }
                }
                throw th;
            }
            break;
        }
        bufferedReader.close();
        return sb.toString();
        if (bufferedReader != null) {
            break;
            bufferedReader.close();
        }
        return sb.toString();
    }
}
