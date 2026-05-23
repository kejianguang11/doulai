package com.getui.gtc.i.a;

import com.getui.gtc.base.util.io.IOUtils;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;

/* JADX INFO: loaded from: classes.dex */
public class a {
    static final /* synthetic */ boolean a = !a.class.desiredAssertionStatus();

    public static String a(String str) {
        byte[] bytes = str.getBytes();
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);
            byte[] bArrDigest = messageDigest.digest();
            char[] cArr2 = new char[32];
            int i = 0;
            for (int i2 = 0; i2 < 16; i2++) {
                byte b = bArrDigest[i2];
                int i3 = i + 1;
                cArr2[i] = cArr[(b >>> 4) & 15];
                i = i3 + 1;
                cArr2[i3] = cArr[b & 15];
            }
            return new String(cArr2);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static void a(File file, File file2, String str) throws Throwable {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        BufferedOutputStream bufferedOutputStream;
        byte[] bArr;
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                fileOutputStream = new FileOutputStream(file2);
                try {
                    bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                } catch (Exception e) {
                    e = e;
                    bufferedOutputStream = null;
                } catch (Throwable th) {
                    th = th;
                    bufferedOutputStream = null;
                }
            } catch (Exception e2) {
                e = e2;
                fileOutputStream = null;
                bufferedOutputStream = null;
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = null;
                bufferedOutputStream = null;
            }
        } catch (Exception e3) {
            e = e3;
            fileOutputStream = null;
            bufferedOutputStream = null;
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
            fileInputStream = null;
            bufferedOutputStream = null;
        }
        try {
            bArr = new byte[1024];
        } catch (Exception e4) {
            e = e4;
            fileInputStream2 = fileInputStream;
            try {
                com.getui.gtc.i.c.a.c(e);
                com.getui.gtc.i.b.a.a(file2);
                IOUtils.safeClose(fileInputStream2);
            } catch (Throwable th4) {
                th = th4;
                fileInputStream = fileInputStream2;
                IOUtils.safeClose(fileInputStream);
                IOUtils.safeClose(bufferedOutputStream);
                IOUtils.safeClose(fileOutputStream);
                throw th;
            }
        } catch (Throwable th5) {
            th = th5;
            IOUtils.safeClose(fileInputStream);
            IOUtils.safeClose(bufferedOutputStream);
            IOUtils.safeClose(fileOutputStream);
            throw th;
        }
        while (true) {
            int i = fileInputStream.read(bArr);
            if (i == -1) {
                break;
            }
            byte[] bArr2 = new byte[i];
            System.arraycopy(bArr, 0, bArr2, 0, i);
            bufferedOutputStream.write(b.a(bArr2, str));
            IOUtils.safeClose(bufferedOutputStream);
            IOUtils.safeClose(fileOutputStream);
        }
        bufferedOutputStream.flush();
        IOUtils.safeClose(fileInputStream);
        IOUtils.safeClose(bufferedOutputStream);
        IOUtils.safeClose(fileOutputStream);
    }
}
