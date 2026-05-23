package com.getui.gtc.base.util.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/* JADX INFO: loaded from: classes.dex */
public class IOUtils {
    public static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    static final char pad = '=';

    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[1024];
        while (true) {
            int i = inputStream.read(bArr);
            if (i == -1) {
                return;
            } else {
                outputStream.write(bArr, 0, i);
            }
        }
    }

    public static void copy(byte[] bArr, OutputStream outputStream) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        try {
            byte[] bArr2 = new byte[1024];
            while (true) {
                int i = byteArrayInputStream.read(bArr2);
                if (i == -1) {
                    return;
                } else {
                    outputStream.write(bArr2, 0, i);
                }
            }
        } catch (IOException e) {
            safeClose(byteArrayInputStream);
            throw e;
        }
    }

    public static void encode(InputStream inputStream, OutputStream outputStream, int i) throws IOException {
        Base64OutputStream base64OutputStream = new Base64OutputStream(outputStream, i);
        copy(inputStream, base64OutputStream);
        base64OutputStream.commit();
    }

    public static byte[] encode(byte[] bArr, int i) throws RuntimeException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            try {
                encode(byteArrayInputStream, byteArrayOutputStream, i);
                safeClose(byteArrayInputStream);
                safeClose(byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException("Unexpected I/O error", e);
            }
        } catch (Throwable th) {
            safeClose(byteArrayInputStream);
            safeClose(byteArrayOutputStream);
            throw th;
        }
    }

    public static byte[] gzip(byte[] bArr) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        GZIPOutputStream gZIPOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                GZIPOutputStream gZIPOutputStream2 = new GZIPOutputStream(byteArrayOutputStream);
                try {
                    gZIPOutputStream2.write(bArr);
                    gZIPOutputStream2.finish();
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    safeClose(gZIPOutputStream2);
                    safeClose(byteArrayOutputStream);
                    return byteArray;
                } catch (Throwable th) {
                    th = th;
                    gZIPOutputStream = gZIPOutputStream2;
                    safeClose(gZIPOutputStream);
                    safeClose(byteArrayOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            byteArrayOutputStream = null;
        }
    }

    public static byte[] readFile(File file) throws Throwable {
        FileInputStream fileInputStream;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = fileInputStream.read(bArr);
                        if (i == -1) {
                            break;
                        }
                        byteArrayOutputStream2.write(bArr, 0, i);
                    }
                    byte[] byteArray = byteArrayOutputStream2.toByteArray();
                    try {
                        fileInputStream.close();
                    } catch (IOException unused) {
                    }
                    try {
                        byteArrayOutputStream2.close();
                    } catch (IOException unused2) {
                    }
                    return byteArray;
                } catch (Throwable th) {
                    byteArrayOutputStream = byteArrayOutputStream2;
                    th = th;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException unused3) {
                        }
                    }
                    if (byteArrayOutputStream == null) {
                        throw th;
                    }
                    try {
                        byteArrayOutputStream.close();
                        throw th;
                    } catch (IOException unused4) {
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
        }
    }

    public static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void saveToFile(InputStream inputStream, File file) throws Throwable {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int i = inputStream.read(bArr);
                    if (i == -1) {
                        safeClose(fileOutputStream);
                        return;
                    }
                    fileOutputStream.write(bArr, 0, i);
                }
            } catch (Throwable th) {
                th = th;
                safeClose(fileOutputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
        }
    }

    public static void saveToFile(InputStream inputStream, String str) throws Throwable {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(str);
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int i = inputStream.read(bArr);
                    if (i == -1) {
                        safeClose(fileOutputStream);
                        return;
                    }
                    fileOutputStream.write(bArr, 0, i);
                }
            } catch (Throwable th) {
                th = th;
                safeClose(fileOutputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
        }
    }

    public static void saveToFile(byte[] bArr, File file) throws Throwable {
        ByteArrayInputStream byteArrayInputStream;
        FileOutputStream fileOutputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bArr);
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    byte[] bArr2 = new byte[1024];
                    while (true) {
                        int i = byteArrayInputStream.read(bArr2);
                        if (i == -1) {
                            safeClose(byteArrayInputStream);
                            safeClose(fileOutputStream2);
                            return;
                        }
                        fileOutputStream2.write(bArr2, 0, i);
                    }
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    safeClose(byteArrayInputStream);
                    safeClose(fileOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            byteArrayInputStream = null;
        }
    }

    public static byte[] unGzip(byte[] bArr) throws Throwable {
        ByteArrayInputStream byteArrayInputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        GZIPInputStream gZIPInputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bArr);
            try {
                GZIPInputStream gZIPInputStream2 = new GZIPInputStream(byteArrayInputStream);
                try {
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    while (true) {
                        try {
                            int i = gZIPInputStream2.read();
                            if (i == -1) {
                                byte[] byteArray = byteArrayOutputStream.toByteArray();
                                safeClose(gZIPInputStream2);
                                safeClose(byteArrayOutputStream);
                                safeClose(byteArrayInputStream);
                                return byteArray;
                            }
                            byteArrayOutputStream.write(i);
                        } catch (Throwable th) {
                            gZIPInputStream = gZIPInputStream2;
                            th = th;
                            safeClose(gZIPInputStream);
                            safeClose(byteArrayOutputStream);
                            safeClose(byteArrayInputStream);
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    gZIPInputStream = gZIPInputStream2;
                    th = th2;
                    byteArrayOutputStream = null;
                }
            } catch (Throwable th3) {
                th = th3;
                byteArrayOutputStream = null;
            }
        } catch (Throwable th4) {
            th = th4;
            byteArrayInputStream = null;
            byteArrayOutputStream = null;
        }
    }
}
