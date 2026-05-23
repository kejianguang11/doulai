package com.alipay.sdk.encrypt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/* JADX INFO: loaded from: classes.dex */
public final class c {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:40:0x004a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0045 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x004f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static byte[] a(byte[] bArr) throws Throwable {
        ByteArrayInputStream byteArrayInputStream;
        GZIPOutputStream gZIPOutputStream;
        Throwable th;
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bArr);
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                    try {
                        byte[] bArr2 = new byte[4096];
                        while (true) {
                            int i = byteArrayInputStream.read(bArr2);
                            if (i == -1) {
                                break;
                            }
                            gZIPOutputStream.write(bArr2, 0, i);
                        }
                        gZIPOutputStream.flush();
                        gZIPOutputStream.finish();
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        try {
                            byteArrayInputStream.close();
                        } catch (Exception unused) {
                        }
                        try {
                            byteArrayOutputStream.close();
                        } catch (Exception unused2) {
                        }
                        try {
                            gZIPOutputStream.close();
                        } catch (Exception unused3) {
                        }
                        return byteArray;
                    } catch (Throwable th2) {
                        th = th2;
                        if (byteArrayInputStream != null) {
                            try {
                                byteArrayInputStream.close();
                            } catch (Exception unused4) {
                            }
                        }
                        if (byteArrayOutputStream != 0) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (Exception unused5) {
                            }
                        }
                        if (gZIPOutputStream != null) {
                            throw th;
                        }
                        try {
                            gZIPOutputStream.close();
                            throw th;
                        } catch (Exception unused6) {
                            throw th;
                        }
                    }
                } catch (Throwable th3) {
                    gZIPOutputStream = null;
                    th = th3;
                }
            } catch (Throwable th4) {
                th = th4;
                gZIPOutputStream = null;
                th = th;
                byteArrayOutputStream = gZIPOutputStream;
                if (byteArrayInputStream != null) {
                }
                if (byteArrayOutputStream != 0) {
                }
                if (gZIPOutputStream != null) {
                }
            }
        } catch (Throwable th5) {
            th = th5;
            byteArrayInputStream = null;
            gZIPOutputStream = null;
        }
    }

    public static byte[] b(byte[] bArr) throws Throwable {
        GZIPInputStream gZIPInputStream;
        ByteArrayInputStream byteArrayInputStream;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(bArr);
            try {
                GZIPInputStream gZIPInputStream2 = new GZIPInputStream(byteArrayInputStream2);
                try {
                    byte[] bArr2 = new byte[4096];
                    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                    while (true) {
                        try {
                            int i = gZIPInputStream2.read(bArr2, 0, 4096);
                            if (i == -1) {
                                break;
                            }
                            byteArrayOutputStream2.write(bArr2, 0, i);
                        } catch (Throwable th) {
                            byteArrayInputStream = byteArrayInputStream2;
                            gZIPInputStream = gZIPInputStream2;
                            th = th;
                            byteArrayOutputStream = byteArrayOutputStream2;
                            try {
                                byteArrayOutputStream.close();
                            } catch (Exception unused) {
                            }
                            try {
                                gZIPInputStream.close();
                            } catch (Exception unused2) {
                            }
                            try {
                                byteArrayInputStream.close();
                                throw th;
                            } catch (Exception unused3) {
                                throw th;
                            }
                        }
                    }
                    byteArrayOutputStream2.flush();
                    byte[] byteArray = byteArrayOutputStream2.toByteArray();
                    try {
                        byteArrayOutputStream2.close();
                    } catch (Exception unused4) {
                    }
                    try {
                        gZIPInputStream2.close();
                    } catch (Exception unused5) {
                    }
                    try {
                        byteArrayInputStream2.close();
                    } catch (Exception unused6) {
                    }
                    return byteArray;
                } catch (Throwable th2) {
                    gZIPInputStream = gZIPInputStream2;
                    th = th2;
                    byteArrayInputStream = byteArrayInputStream2;
                }
            } catch (Throwable th3) {
                th = th3;
                byteArrayInputStream = byteArrayInputStream2;
                gZIPInputStream = null;
            }
        } catch (Throwable th4) {
            th = th4;
            gZIPInputStream = null;
            byteArrayInputStream = null;
        }
    }
}
