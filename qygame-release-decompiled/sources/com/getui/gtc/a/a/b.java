package com.getui.gtc.a.a;

import android.annotation.TargetApi;
import android.os.Process;
import android.text.TextUtils;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public final class b implements Runnable {
    public f a;
    private HttpURLConnection b;
    private boolean c = false;
    private SecretKeySpec d;

    public b(f fVar) {
        this.a = fVar;
        byte[] bArr = new byte[16];
        new SecureRandom().nextBytes(bArr);
        this.d = new SecretKeySpec(bArr, "AES/CFB/NoPadding");
    }

    private void a() {
        if (this.b != null) {
            try {
                this.b.disconnect();
                this.b = null;
            } catch (Throwable th) {
                com.getui.gtc.i.c.a.c(th);
            }
        }
    }

    private byte[] a(HttpURLConnection httpURLConnection, byte[] bArr) {
        try {
            if (!this.a.d) {
                return this.a.g ? p.b(c.a(bArr)) : bArr;
            }
            String headerField = httpURLConnection.getHeaderField("GT_ERR");
            if (headerField != null && "0".equals(headerField)) {
                String headerField2 = httpURLConnection.getHeaderField("GT_T");
                if (headerField2 == null) {
                    throw new SecurityException("sdk config response error, GT_T header not found");
                }
                byte[] bytes = headerField2.getBytes();
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.update(bytes);
                IvParameterSpec ivParameterSpec = new IvParameterSpec(messageDigest.digest());
                String headerField3 = httpURLConnection.getHeaderField("GT_C_S");
                if (headerField3 == null) {
                    throw new SecurityException("sdk config response error, GT_C_S header not found");
                }
                byte[] bArrDecode = Base64.decode(headerField3, 2);
                Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
                cipher.init(2, this.d, ivParameterSpec);
                byte[] bArrDoFinal = cipher.doFinal(bArr);
                byte[] bArr2 = new byte[bArrDoFinal.length + bytes.length];
                System.arraycopy(bytes, 0, bArr2, 0, bytes.length);
                System.arraycopy(bArrDoFinal, 0, bArr2, bytes.length, bArrDoFinal.length);
                MessageDigest messageDigest2 = MessageDigest.getInstance("SHA1");
                messageDigest2.update(bArr2);
                if (Arrays.equals(messageDigest2.digest(), bArrDecode)) {
                    return bArrDoFinal;
                }
                throw new SecurityException("sdk config response error, response body sign check failed");
            }
            if (headerField != null) {
                throw new SecurityException("sdk config response error, error code is ".concat(String.valueOf(headerField)));
            }
            throw new SecurityException("sdk config response error, GT_ERR header not found");
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.c(th);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:121:0x0179 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:123:0x019d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0183 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x01a7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x018d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:141:0x01b1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x016b A[Catch: all -> 0x0199, TryCatch #14 {all -> 0x0199, blocks: (B:81:0x0167, B:83:0x016b, B:84:0x0170), top: B:139:0x0167 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private byte[] a(Map<String, List<String>> map) throws Throwable {
        InputStream inputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        DataOutputStream dataOutputStream;
        byte[] bArrA;
        InputStream inputStream2;
        ByteArrayOutputStream byteArrayOutputStream2;
        DataOutputStream dataOutputStream2 = null;
        try {
            this.b = (HttpURLConnection) new URL(this.a.a).openConnection();
            this.b.setConnectTimeout(this.a.h);
            this.b.setReadTimeout(this.a.i);
            this.b.setDoInput(this.a.k);
            this.b.setDoOutput(this.a.j);
            this.b.setRequestMethod("POST");
            this.b.setUseCaches(this.a.l);
            this.b.setInstanceFollowRedirects(this.a.m);
            for (String str : this.a.n.keySet()) {
                this.b.setRequestProperty(str, this.a.n.get(str));
            }
            bArrA = this.a.b;
            if (this.a.d) {
                bArrA = a(bArrA);
            } else if (this.a.f) {
                bArrA = p.a(bArrA);
            }
        } catch (Throwable th) {
            th = th;
            inputStream = null;
            byteArrayOutputStream = null;
        }
        try {
            if (bArrA == null) {
                a();
                return null;
            }
            this.b.connect();
            dataOutputStream = new DataOutputStream(this.b.getOutputStream());
            try {
                dataOutputStream.write(bArrA, 0, bArrA.length);
                dataOutputStream.flush();
                if (this.b.getResponseCode() == 200) {
                    if (this.b.getHeaderFields() != null) {
                        map.putAll(this.b.getHeaderFields());
                    }
                    inputStream2 = this.b.getInputStream();
                    try {
                        byteArrayOutputStream2 = new ByteArrayOutputStream();
                    } catch (Throwable th2) {
                        byteArrayOutputStream = null;
                        dataOutputStream2 = dataOutputStream;
                        inputStream = inputStream2;
                        th = th2;
                        if (dataOutputStream2 != null) {
                        }
                        if (inputStream != null) {
                        }
                        if (byteArrayOutputStream != null) {
                        }
                        a();
                        throw th;
                    }
                    try {
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int i = inputStream2.read(bArr);
                            if (i == -1) {
                                break;
                            }
                            byteArrayOutputStream2.write(bArr, 0, i);
                        }
                        if (byteArrayOutputStream2.toByteArray() != null) {
                            byte[] bArrA2 = a(this.b, byteArrayOutputStream2.toByteArray());
                            try {
                                dataOutputStream.close();
                            } catch (Throwable th3) {
                                com.getui.gtc.i.c.a.c(th3);
                            }
                            if (inputStream2 != null) {
                                try {
                                    inputStream2.close();
                                } catch (Throwable th4) {
                                    com.getui.gtc.i.c.a.c(th4);
                                }
                            }
                            try {
                                byteArrayOutputStream2.close();
                            } catch (Throwable th5) {
                                com.getui.gtc.i.c.a.c(th5);
                            }
                            a();
                            return bArrA2;
                        }
                    } catch (Throwable th6) {
                        inputStream = inputStream2;
                        th = th6;
                        byteArrayOutputStream = byteArrayOutputStream2;
                        if (this.a != null) {
                        }
                        com.getui.gtc.i.c.a.d(th.toString());
                        if (dataOutputStream != null) {
                        }
                        if (inputStream != null) {
                        }
                        if (byteArrayOutputStream != null) {
                        }
                    }
                } else {
                    if (this.a != null) {
                        this.a.a(this.b.getResponseCode());
                    }
                    inputStream2 = null;
                    byteArrayOutputStream2 = null;
                }
                try {
                    dataOutputStream.close();
                } catch (Throwable th7) {
                    com.getui.gtc.i.c.a.c(th7);
                }
                if (inputStream2 != null) {
                    try {
                        inputStream2.close();
                    } catch (Throwable th8) {
                        com.getui.gtc.i.c.a.c(th8);
                    }
                }
                if (byteArrayOutputStream2 != null) {
                    try {
                        byteArrayOutputStream2.close();
                    } catch (Throwable th9) {
                        th = th9;
                        com.getui.gtc.i.c.a.c(th);
                    }
                }
            } catch (Throwable th10) {
                th = th10;
                inputStream = null;
                byteArrayOutputStream = null;
            }
            a();
            return null;
            if (this.a != null) {
                this.a.a();
            }
            com.getui.gtc.i.c.a.d(th.toString());
            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                } catch (Throwable th11) {
                    com.getui.gtc.i.c.a.c(th11);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable th12) {
                    com.getui.gtc.i.c.a.c(th12);
                }
            }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th13) {
                    th = th13;
                    com.getui.gtc.i.c.a.c(th);
                }
            }
            a();
            return null;
        } catch (Throwable th14) {
            th = th14;
            dataOutputStream2 = dataOutputStream;
            if (dataOutputStream2 != null) {
                try {
                    dataOutputStream2.close();
                } catch (Throwable th15) {
                    com.getui.gtc.i.c.a.c(th15);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable th16) {
                    com.getui.gtc.i.c.a.c(th16);
                }
            }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th17) {
                    com.getui.gtc.i.c.a.c(th17);
                }
            }
            a();
            throw th;
        }
    }

    private byte[] a(byte[] bArr) {
        try {
            byte[] bArrA = g.a(bArr);
            byte[] bArr2 = new byte[0];
            if (bArrA == null) {
                bArrA = bArr2;
            }
            String strValueOf = String.valueOf(System.currentTimeMillis());
            byte[] bArr3 = new byte[16];
            new SecureRandom().nextBytes(bArr3);
            PublicKey publicKeyGeneratePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(com.igexin.push.a.k, 0)));
            Cipher cipher = Cipher.getInstance("RSA/NONE/OAEPWithSHA1AndMGF1Padding");
            cipher.init(1, publicKeyGeneratePublic);
            byte[] bArrDoFinal = cipher.doFinal(this.d.getEncoded());
            byte[] bArr4 = new byte[bArrDoFinal.length + 16];
            System.arraycopy(bArr3, 0, bArr4, 0, 16);
            System.arraycopy(bArrDoFinal, 0, bArr4, 16, bArrDoFinal.length);
            String strEncodeToString = Base64.encodeToString(bArr4, 2);
            byte[] bytes = strValueOf.getBytes();
            byte[] bArr5 = new byte[bytes.length + bArrA.length];
            System.arraycopy(bytes, 0, bArr5, 0, bytes.length);
            System.arraycopy(bArrA, 0, bArr5, bytes.length, bArrA.length);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(bArr5);
            String strEncodeToString2 = Base64.encodeToString(messageDigest.digest(), 2);
            MessageDigest messageDigest2 = MessageDigest.getInstance("MD5");
            messageDigest2.update(strEncodeToString2.getBytes());
            IvParameterSpec ivParameterSpec = new IvParameterSpec(messageDigest2.digest());
            Cipher cipher2 = Cipher.getInstance("AES/CFB/NoPadding");
            cipher2.init(1, this.d, ivParameterSpec);
            byte[] bArrDoFinal2 = cipher2.doFinal(bArrA);
            this.b.addRequestProperty("GT_T", strValueOf);
            this.b.addRequestProperty("GT_C_T", "1");
            this.b.addRequestProperty("GT_C_K", com.igexin.push.a.j);
            this.b.addRequestProperty("GT_C_V", strEncodeToString);
            this.b.addRequestProperty("GT_C_S", strEncodeToString2);
            return bArrDoFinal2;
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.c(th);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:107:0x01cd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01d7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @TargetApi(21)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private byte[] b(Map<String, List<String>> map) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        InputStream inputStream;
        ByteArrayOutputStream byteArrayOutputStream2;
        HttpURLConnection httpURLConnection;
        InputStream inputStream2 = null;
        try {
            try {
                try {
                    URL url = new URL(this.a.a);
                    this.b = this.a.c == null ? (HttpURLConnection) url.openConnection() : (HttpURLConnection) this.a.c.openConnection(url);
                    this.b.setConnectTimeout(this.a.h);
                    this.b.setReadTimeout(this.a.i);
                    this.b.setDoInput(this.a.k);
                    this.b.setRequestMethod("GET");
                    this.b.setUseCaches(this.a.l);
                    this.b.setInstanceFollowRedirects(this.a.m);
                    for (String str : this.a.n.keySet()) {
                        this.b.setRequestProperty(str, this.a.n.get(str));
                    }
                    if (this.a.d && (httpURLConnection = this.b) != null) {
                        try {
                            httpURLConnection.addRequestProperty("GT_C_T", "1");
                            httpURLConnection.addRequestProperty("GT_C_K", com.igexin.push.a.j);
                            byte[] bArr = new byte[16];
                            new SecureRandom().nextBytes(bArr);
                            PublicKey publicKeyGeneratePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(com.igexin.push.a.k, 0)));
                            Cipher cipher = Cipher.getInstance("RSA/NONE/OAEPWithSHA1AndMGF1Padding");
                            cipher.init(1, publicKeyGeneratePublic);
                            byte[] bArrDoFinal = cipher.doFinal(this.d.getEncoded());
                            byte[] bArr2 = new byte[bArrDoFinal.length + 16];
                            System.arraycopy(bArr, 0, bArr2, 0, 16);
                            System.arraycopy(bArrDoFinal, 0, bArr2, 16, bArrDoFinal.length);
                            httpURLConnection.addRequestProperty("GT_C_V", Base64.encodeToString(bArr2, 2));
                            String strValueOf = String.valueOf(System.currentTimeMillis());
                            httpURLConnection.addRequestProperty("GT_T", strValueOf);
                            byte[] bytes = strValueOf.getBytes();
                            byte[] bArr3 = new byte[bytes.length + 0];
                            System.arraycopy(bytes, 0, bArr3, 0, bytes.length);
                            System.arraycopy(new byte[0], 0, bArr3, bytes.length, 0);
                            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
                            messageDigest.update(bArr3);
                            httpURLConnection.addRequestProperty("GT_C_S", Base64.encodeToString(messageDigest.digest(), 2));
                        } catch (Throwable th) {
                            com.getui.gtc.i.c.a.c(th);
                        }
                    }
                    inputStream = this.b.getInputStream();
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                inputStream = null;
                byteArrayOutputStream2 = null;
            }
        } catch (Throwable th4) {
            th = th4;
            byteArrayOutputStream = null;
            if (inputStream2 != null) {
                try {
                    inputStream2.close();
                } catch (Throwable th5) {
                    com.getui.gtc.i.c.a.c(th5);
                }
            }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th6) {
                    com.getui.gtc.i.c.a.c(th6);
                }
            }
            a();
            throw th;
        }
        try {
            byteArrayOutputStream2 = new ByteArrayOutputStream();
            try {
                if (this.b.getResponseCode() == 200) {
                    if (this.b.getHeaderFields() != null) {
                        map.putAll(this.b.getHeaderFields());
                    }
                    byte[] bArr4 = new byte[1024];
                    while (true) {
                        int i = inputStream.read(bArr4);
                        if (i == -1) {
                            break;
                        }
                        byteArrayOutputStream2.write(bArr4, 0, i);
                    }
                    if (byteArrayOutputStream2.toByteArray() != null) {
                        byte[] bArrA = a(this.b, byteArrayOutputStream2.toByteArray());
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (Throwable th7) {
                                com.getui.gtc.i.c.a.c(th7);
                            }
                        }
                        try {
                            byteArrayOutputStream2.close();
                        } catch (Throwable th8) {
                            com.getui.gtc.i.c.a.c(th8);
                        }
                        a();
                        return bArrA;
                    }
                } else if (this.a != null) {
                    this.a.a(this.b.getResponseCode());
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable th9) {
                        com.getui.gtc.i.c.a.c(th9);
                    }
                }
                try {
                    byteArrayOutputStream2.close();
                } catch (Throwable th10) {
                    th = th10;
                    com.getui.gtc.i.c.a.c(th);
                }
            } catch (Throwable th11) {
                th = th11;
                if (this.a != null) {
                    this.a.a();
                }
                com.getui.gtc.i.c.a.c(th);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable th12) {
                        com.getui.gtc.i.c.a.c(th12);
                    }
                }
                if (byteArrayOutputStream2 != null) {
                    try {
                        byteArrayOutputStream2.close();
                    } catch (Throwable th13) {
                        th = th13;
                        com.getui.gtc.i.c.a.c(th);
                    }
                }
            }
        } catch (Throwable th14) {
            th = th14;
            byteArrayOutputStream = null;
            inputStream2 = inputStream;
            if (inputStream2 != null) {
            }
            if (byteArrayOutputStream != null) {
            }
            a();
            throw th;
        }
        a();
        return null;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            Process.setThreadPriority(10);
            if (this.c) {
                return;
            }
            this.c = false;
            if (this.a == null || TextUtils.isEmpty(this.a.a)) {
                return;
            }
            HashMap map = new HashMap();
            byte[] bArrB = this.a.b == null ? b(map) : a(map);
            if (bArrB != null) {
                this.a.a(map, bArrB);
            }
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.c(th);
        }
    }
}
