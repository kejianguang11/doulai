package com.igexin.base.a;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import com.igexin.base.api.SharedPreferencesManager;
import com.igexin.base.util.IOUtils;
import com.igexin.base.util.StringUtil;
import com.igexin.push.g.e;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.security.KeyFactory;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
final class b implements Runnable {
    private static b b;
    final List<c> a = new ArrayList();

    private b() {
        Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(this, 5L, 5L, TimeUnit.SECONDS);
    }

    public static synchronized b a() {
        if (b == null) {
            b = new b();
        }
        return b;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01c5  */
    /* JADX WARN: Type inference failed for: r18v0 */
    /* JADX WARN: Type inference failed for: r18v1, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r18v2 */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v4, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r6v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static boolean a(c cVar) throws JSONException {
        FileLock fileLockLock;
        JSONObject jSONObject;
        byte[] bArrA;
        boolean z;
        ?? r18;
        OutputStream outputStream;
        OutputStream fileOutputStream;
        try {
            File file = new File(cVar.a(cVar.f));
            if (!file.exists()) {
                File parentFile = file.getParentFile();
                if ((!parentFile.exists() && !parentFile.mkdirs()) || !file.createNewFile()) {
                    return false;
                }
            }
            if (!file.isFile()) {
                return false;
            }
            StringBuilder sb = new StringBuilder();
            List<String> list = cVar.a;
            while (list.size() > 0) {
                sb.append(list.remove(0));
                sb.append("\r\n");
            }
            if (sb.length() <= 0) {
                return true;
            }
            try {
                ?? r6 = "rw";
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                try {
                    fileLockLock = randomAccessFile.getChannel().lock();
                    if (fileLockLock != null) {
                        try {
                            if (fileLockLock.isValid()) {
                                String string = sb.toString();
                                String str = cVar.e;
                                String md5 = StringUtil.getMD5(file.getAbsolutePath());
                                String string2 = "";
                                try {
                                    jSONObject = new JSONObject(SharedPreferencesManager.get("gbase").getParam("logkey3", "").toString());
                                    try {
                                        string2 = jSONObject.getString(md5);
                                    } catch (JSONException unused) {
                                    }
                                } catch (JSONException unused2) {
                                    jSONObject = null;
                                }
                                if (TextUtils.isEmpty(string2)) {
                                    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                                    keyGenerator.init(128);
                                    byte[] encoded = keyGenerator.generateKey().getEncoded();
                                    if (file.length() > 0) {
                                        file.delete();
                                    }
                                    bArrA = encoded;
                                    z = true;
                                } else {
                                    bArrA = com.igexin.base.util.a.a.a(StringUtil.hexStringToBytes(string2), (TextUtils.isEmpty(str) ? com.igexin.push.core.b.al : str).getBytes());
                                    z = false;
                                }
                                try {
                                    try {
                                        try {
                                            SecretKeySpec secretKeySpec = new SecretKeySpec(bArrA, "AES");
                                            byte[] bArr = new byte[16];
                                            if (randomAccessFile.length() == 0) {
                                                String strBytesToHexString = StringUtil.bytesToHexString(secretKeySpec.getEncoded());
                                                RSAPublicKey rSAPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(e.a, 0)));
                                                Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
                                                cipher.init(1, rSAPublicKey);
                                                randomAccessFile.write(cipher.doFinal(strBytesToHexString.getBytes(com.alipay.sdk.sys.a.m)));
                                                new SecureRandom().nextBytes(bArr);
                                                randomAccessFile.write(bArr);
                                            } else {
                                                if (randomAccessFile.length() < 144) {
                                                    throw new IllegalArgumentException("Invalid file length (need 2 blocks for iv and data)");
                                                }
                                                if (randomAccessFile.length() % 16 != 0) {
                                                    long length = (int) (randomAccessFile.length() % 16);
                                                    if (length < 16 && length > 0) {
                                                        randomAccessFile.setLength(randomAccessFile.length() - length);
                                                    }
                                                    randomAccessFile.seek(randomAccessFile.length() - 16);
                                                } else {
                                                    randomAccessFile.seek(randomAccessFile.length() - 16);
                                                }
                                                randomAccessFile.read(bArr);
                                            }
                                            Cipher cipher2 = Cipher.getInstance("AES/CBC/PKCS5Padding");
                                            cipher2.init(1, secretKeySpec, new IvParameterSpec(bArr));
                                            fileOutputStream = new CipherOutputStream(new FileOutputStream(randomAccessFile.getFD()), cipher2);
                                        } catch (Throwable th) {
                                            th = th;
                                            r18 = r6;
                                            IOUtils.close(r18);
                                            throw th;
                                        }
                                    } catch (Throwable th2) {
                                        th2.printStackTrace();
                                        fileOutputStream = new FileOutputStream(file, true);
                                    }
                                    outputStream = fileOutputStream;
                                    try {
                                        outputStream.write(string.getBytes(com.alipay.sdk.sys.a.m));
                                        r6 = outputStream;
                                    } catch (IOException e) {
                                        e = e;
                                        e.printStackTrace();
                                        r6 = outputStream;
                                    }
                                } catch (IOException e2) {
                                    e = e2;
                                    outputStream = null;
                                    e.printStackTrace();
                                    r6 = outputStream;
                                    IOUtils.close(r6);
                                    if (z) {
                                    }
                                    if (fileLockLock != null) {
                                        fileLockLock.release();
                                    }
                                    return true;
                                } catch (Throwable th3) {
                                    th = th3;
                                    r18 = 0;
                                    IOUtils.close(r18);
                                    throw th;
                                }
                                IOUtils.close(r6);
                                if (z) {
                                    if (jSONObject == null) {
                                        jSONObject = new JSONObject();
                                    }
                                    if (TextUtils.isEmpty(str)) {
                                        str = com.igexin.push.core.b.al;
                                    }
                                    jSONObject.put(md5, StringUtil.bytesToHexString(com.igexin.base.util.a.a.a(bArrA, str.getBytes())));
                                    SharedPreferencesManager.get("gbase").saveParam("logkey3", jSONObject.toString());
                                }
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            if (fileLockLock != null && fileLockLock.isValid()) {
                                fileLockLock.release();
                            }
                            throw th;
                        }
                    }
                    if (fileLockLock != null && fileLockLock.isValid()) {
                        fileLockLock.release();
                    }
                    return true;
                } catch (Throwable th5) {
                    th = th5;
                    fileLockLock = null;
                }
            } catch (Throwable unused3) {
                return false;
            }
        } catch (Throwable unused4) {
            return false;
        }
    }

    @Override // java.lang.Runnable
    public final void run() throws JSONException {
        for (c cVar : this.a) {
            if (cVar.isEnabled()) {
                if (cVar.a.size() >= cVar.b || SystemClock.elapsedRealtime() - cVar.d >= cVar.c) {
                    a(cVar);
                    cVar.d = SystemClock.elapsedRealtime();
                }
            }
        }
    }
}
