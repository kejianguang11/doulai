package com.getui.gtc.base.crypt;

import android.content.Context;
import com.getui.gtc.base.GtcProvider;
import com.getui.gtc.base.util.io.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public class SecureCryptTools {
    private static final String CIPHER_FLAG_FIRST = "First";
    private static final String CIPHER_FLAG_SECOND = "Second";
    private static final String CIPHER_FLAG_SEPARATOR = "-";
    private static final String CIPHER_FLAG_STARTER = ":::";
    private volatile boolean initInvoked;
    private ReentrantLock lock;
    private d secureKeyStore;

    static class a {
        private static SecureCryptTools a = new SecureCryptTools();
    }

    private SecureCryptTools() {
        this.lock = new ReentrantLock();
        try {
            init(GtcProvider.context());
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private synchronized byte[] doDecrypt(byte[] bArr) throws CryptException {
        ByteArrayOutputStream byteArrayOutputStream;
        InputStream byteArrayInputStream;
        InputStream inputStreamDecrypt;
        ByteArrayOutputStream byteArrayOutputStream2;
        InputStream inputStreamDecrypt2;
        InputStream byteArrayInputStream2;
        String cipherFlag = getCipherFlag(bArr);
        if (cipherFlag == null) {
            throw new CryptException("Cipher flag not found in cipher text!");
        }
        String[] strArrSplit = cipherFlag.split(CIPHER_FLAG_SEPARATOR);
        if (strArrSplit.length < 2) {
            throw new CryptException("Cipher flag is wrong in cipher text!");
        }
        String str = strArrSplit[0];
        byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 0, (bArr.length - cipherFlag.length()) - 3);
        InputStream inputStream = null;
        if (cipherFlag.endsWith(CIPHER_FLAG_FIRST)) {
            try {
                byteArrayInputStream = new ByteArrayInputStream(bArrCopyOfRange);
                try {
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    try {
                        inputStreamDecrypt = CryptTools.decrypt("AES/CBC/PKCS7Padding", this.secureKeyStore.a(str), this.secureKeyStore.c(str), byteArrayInputStream);
                        try {
                            byte[] bArr2 = new byte[256];
                            while (true) {
                                int i = inputStreamDecrypt.read(bArr2);
                                if (i == -1) {
                                    byteArrayOutputStream.flush();
                                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                                    IOUtils.safeClose(inputStreamDecrypt);
                                    IOUtils.safeClose(byteArrayInputStream);
                                    IOUtils.safeClose(byteArrayOutputStream);
                                    return byteArray;
                                }
                                byteArrayOutputStream.write(bArr2, 0, i);
                            }
                        } catch (Throwable th) {
                            th = th;
                            inputStream = byteArrayInputStream;
                            try {
                                throw new CryptException("decrypt failed!", th);
                            } catch (Throwable th2) {
                                th = th2;
                                byteArrayInputStream = inputStream;
                                inputStream = inputStreamDecrypt;
                                IOUtils.safeClose(inputStream);
                                IOUtils.safeClose(byteArrayInputStream);
                                IOUtils.safeClose(byteArrayOutputStream);
                                throw th;
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        inputStreamDecrypt = null;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    byteArrayOutputStream = null;
                    inputStreamDecrypt = null;
                }
            } catch (Throwable th5) {
                th = th5;
                byteArrayOutputStream = null;
                byteArrayInputStream = null;
            }
        } else {
            if (!cipherFlag.endsWith(CIPHER_FLAG_SECOND)) {
                throw new CryptException("Cipher flag not found in cipher text!");
            }
            try {
                byteArrayInputStream2 = new ByteArrayInputStream(bArrCopyOfRange);
                try {
                    byteArrayOutputStream2 = new ByteArrayOutputStream();
                    try {
                        inputStreamDecrypt2 = CryptTools.decrypt("AES/CBC/PKCS7Padding", this.secureKeyStore.b(str), this.secureKeyStore.c(str), byteArrayInputStream2);
                    } catch (Throwable th6) {
                        th = th6;
                        IOUtils.safeClose(inputStream);
                        IOUtils.safeClose(byteArrayInputStream2);
                        IOUtils.safeClose(byteArrayOutputStream2);
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    byteArrayOutputStream2 = null;
                }
            } catch (Throwable th8) {
                th = th8;
                byteArrayOutputStream2 = null;
                inputStreamDecrypt2 = null;
            }
            try {
                byte[] bArr3 = new byte[256];
                while (true) {
                    int i2 = inputStreamDecrypt2.read(bArr3);
                    if (i2 == -1) {
                        byteArrayOutputStream2.flush();
                        byte[] byteArray2 = byteArrayOutputStream2.toByteArray();
                        IOUtils.safeClose(inputStreamDecrypt2);
                        IOUtils.safeClose(byteArrayInputStream2);
                        IOUtils.safeClose(byteArrayOutputStream2);
                        return byteArray2;
                    }
                    byteArrayOutputStream2.write(bArr3, 0, i2);
                }
            } catch (Throwable th9) {
                th = th9;
                inputStream = byteArrayInputStream2;
                try {
                    throw new CryptException("decrypt failed!", th);
                } catch (Throwable th10) {
                    th = th10;
                    byteArrayInputStream2 = inputStream;
                    inputStream = inputStreamDecrypt2;
                    IOUtils.safeClose(inputStream);
                    IOUtils.safeClose(byteArrayInputStream2);
                    IOUtils.safeClose(byteArrayOutputStream2);
                    throw th;
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.util.Map<java.lang.String, javax.crypto.SecretKey>] */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v7, types: [java.io.ByteArrayInputStream, java.io.Closeable, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r0v8, types: [java.io.ByteArrayInputStream, java.io.Closeable, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    private byte[] doEncrypt(byte[] bArr) throws Throwable {
        Closeable closeable;
        ByteArrayOutputStream byteArrayOutputStream;
        InputStream inputStream;
        InputStream inputStream2;
        InputStream inputStream3;
        InputStream inputStreamEncrypt;
        ByteArrayInputStream byteArrayInputStream;
        InputStream inputStreamEncrypt2;
        ?? byteArrayInputStream2 = this.secureKeyStore.c;
        ?? r3 = 0;
        ByteArrayInputStream byteArrayInputStream3 = null;
        try {
            if (byteArrayInputStream2 != 0) {
                try {
                    byteArrayInputStream2 = new ByteArrayInputStream(bArr);
                } catch (Throwable th) {
                    th = th;
                    byteArrayInputStream2 = 0;
                    byteArrayOutputStream = null;
                }
                try {
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    try {
                        d dVar = this.secureKeyStore;
                        inputStreamEncrypt = CryptTools.encrypt("AES/CBC/PKCS7Padding", dVar.a(dVar.g), this.secureKeyStore.b(), (InputStream) byteArrayInputStream2);
                        try {
                            byte[] bArr2 = new byte[256];
                            while (true) {
                                int i = inputStreamEncrypt.read(bArr2);
                                if (i == -1) {
                                    byteArrayOutputStream.flush();
                                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                                    byte[] bytes = (CIPHER_FLAG_STARTER + this.secureKeyStore.g + "-First").getBytes();
                                    int length = bytes.length;
                                    byte[] bArr3 = new byte[byteArray.length + length];
                                    System.arraycopy(byteArray, 0, bArr3, 0, byteArray.length);
                                    System.arraycopy(bytes, 0, bArr3, byteArray.length, length);
                                    IOUtils.safeClose(inputStreamEncrypt);
                                    IOUtils.safeClose(byteArrayInputStream2);
                                    IOUtils.safeClose(byteArrayOutputStream);
                                    return bArr3;
                                }
                                byteArrayOutputStream.write(bArr2, 0, i);
                            }
                        } catch (Throwable unused) {
                            r3 = byteArrayInputStream2;
                            inputStream = inputStreamEncrypt;
                            try {
                                byteArrayInputStream2 = new ByteArrayInputStream(bArr);
                                try {
                                    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                                    try {
                                        InputStream inputStreamEncrypt3 = CryptTools.encrypt("AES/CBC/PKCS7Padding", this.secureKeyStore.a(), this.secureKeyStore.b(), (InputStream) byteArrayInputStream2);
                                        try {
                                            byte[] bArr4 = new byte[256];
                                            while (true) {
                                                int i2 = inputStreamEncrypt3.read(bArr4);
                                                if (i2 == -1) {
                                                    byteArrayOutputStream2.flush();
                                                    byte[] byteArray2 = byteArrayOutputStream2.toByteArray();
                                                    byte[] bytes2 = (CIPHER_FLAG_STARTER + this.secureKeyStore.g + "-Second").getBytes();
                                                    int length2 = bytes2.length;
                                                    byte[] bArr5 = new byte[byteArray2.length + length2];
                                                    System.arraycopy(byteArray2, 0, bArr5, 0, byteArray2.length);
                                                    System.arraycopy(bytes2, 0, bArr5, byteArray2.length, length2);
                                                    IOUtils.safeClose(inputStreamEncrypt3);
                                                    IOUtils.safeClose(byteArrayInputStream2);
                                                    IOUtils.safeClose(byteArrayOutputStream2);
                                                    return bArr5;
                                                }
                                                byteArrayOutputStream2.write(bArr4, 0, i2);
                                            }
                                        } catch (Throwable th2) {
                                            th = th2;
                                            byteArrayOutputStream = byteArrayOutputStream2;
                                            inputStream3 = inputStreamEncrypt3;
                                            r3 = byteArrayInputStream2;
                                            inputStream2 = inputStream3;
                                            try {
                                                throw new CryptException("encrypt failed", th);
                                            } catch (Throwable th3) {
                                                th = th3;
                                                inputStream = inputStream2;
                                                byteArrayInputStream2 = r3;
                                                closeable = inputStream;
                                                IOUtils.safeClose(closeable);
                                                IOUtils.safeClose(byteArrayInputStream2);
                                                IOUtils.safeClose(byteArrayOutputStream);
                                                throw th;
                                            }
                                        }
                                    } catch (Throwable th4) {
                                        th = th4;
                                        byteArrayOutputStream = byteArrayOutputStream2;
                                        inputStream3 = inputStream;
                                    }
                                } catch (Throwable th5) {
                                    th = th5;
                                    r3 = byteArrayInputStream2;
                                    inputStream2 = inputStream;
                                    r3 = r3;
                                    throw new CryptException("encrypt failed", th);
                                }
                            } catch (Throwable th6) {
                                th = th6;
                                byteArrayInputStream2 = r3;
                                closeable = inputStream;
                                IOUtils.safeClose(closeable);
                                IOUtils.safeClose(byteArrayInputStream2);
                                IOUtils.safeClose(byteArrayOutputStream);
                                throw th;
                            }
                        }
                    } catch (Throwable unused2) {
                        inputStreamEncrypt = null;
                    }
                } catch (Throwable unused3) {
                    byteArrayOutputStream = null;
                    inputStreamEncrypt = null;
                }
            } else {
                try {
                    byteArrayInputStream = new ByteArrayInputStream(bArr);
                    try {
                        ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
                        inputStreamEncrypt2 = CryptTools.encrypt("AES/CBC/PKCS7Padding", this.secureKeyStore.a(), this.secureKeyStore.b(), byteArrayInputStream);
                        try {
                            byte[] bArr6 = new byte[256];
                            while (true) {
                                int i3 = inputStreamEncrypt2.read(bArr6);
                                if (i3 == -1) {
                                    byteArrayOutputStream3.flush();
                                    byte[] byteArray3 = byteArrayOutputStream3.toByteArray();
                                    byte[] bytes3 = (CIPHER_FLAG_STARTER + this.secureKeyStore.g + "-Second").getBytes();
                                    int length3 = bytes3.length;
                                    byte[] bArr7 = new byte[byteArray3.length + length3];
                                    System.arraycopy(byteArray3, 0, bArr7, 0, byteArray3.length);
                                    System.arraycopy(bytes3, 0, bArr7, byteArray3.length, length3);
                                    IOUtils.safeClose(inputStreamEncrypt2);
                                    IOUtils.safeClose(byteArrayInputStream);
                                    return bArr7;
                                }
                                byteArrayOutputStream3.write(bArr6, 0, i3);
                            }
                        } catch (Throwable th7) {
                            th = th7;
                            byteArrayInputStream3 = byteArrayInputStream;
                            try {
                                throw new CryptException("encrypt failed", th);
                            } catch (Throwable th8) {
                                th = th8;
                                byteArrayInputStream = byteArrayInputStream3;
                                IOUtils.safeClose(inputStreamEncrypt2);
                                IOUtils.safeClose(byteArrayInputStream);
                                throw th;
                            }
                        }
                    } catch (Throwable th9) {
                        th = th9;
                        inputStreamEncrypt2 = null;
                    }
                } catch (Throwable th10) {
                    th = th10;
                    byteArrayInputStream = null;
                    inputStreamEncrypt2 = null;
                }
            }
        } catch (Throwable th11) {
            th = th11;
        }
    }

    private String getCipherFlag(byte[] bArr) {
        String str = new String(bArr);
        int iLastIndexOf = str.lastIndexOf(CIPHER_FLAG_STARTER);
        if (iLastIndexOf < 0) {
            return null;
        }
        return str.substring(iLastIndexOf + 3);
    }

    public static SecureCryptTools getInstance() {
        return a.a;
    }

    private List<CryptException> init(Context context) throws CryptException {
        List<CryptException> listA;
        try {
            this.lock.lock();
            if (this.initInvoked) {
                listA = Collections.emptyList();
            } else {
                this.initInvoked = true;
                this.secureKeyStore = new d();
                listA = this.secureKeyStore.a(context);
            }
            return listA;
        } finally {
            this.lock.unlock();
        }
    }

    public byte[] decrypt(byte[] bArr) throws CryptException {
        if (!this.initInvoked) {
            throw new CryptException("SecureCryptTools: please init firstly!");
        }
        try {
            try {
                this.lock.tryLock(3000L, TimeUnit.MILLISECONDS);
                return doDecrypt(bArr);
            } catch (InterruptedException unused) {
                throw new CryptException("SecureCryptTools: wait init time out!");
            }
        } finally {
            if (this.lock.isLocked()) {
                this.lock.unlock();
            }
        }
    }

    public byte[] encrypt(byte[] bArr) throws CryptException {
        if (!this.initInvoked) {
            throw new CryptException("SecureCryptTools: please init firstly!");
        }
        try {
            try {
                this.lock.tryLock(3000L, TimeUnit.MILLISECONDS);
                return doEncrypt(bArr);
            } catch (InterruptedException unused) {
                throw new CryptException("SecureCryptTools: wait init time out!");
            }
        } finally {
            if (this.lock.isLocked()) {
                this.lock.unlock();
            }
        }
    }
}
