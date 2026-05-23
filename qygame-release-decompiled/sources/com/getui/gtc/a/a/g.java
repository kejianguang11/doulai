package com.getui.gtc.a.a;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/* JADX INFO: loaded from: classes.dex */
public final class g {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0047 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static byte[] a(byte[] bArr) throws Throwable {
        GZIPOutputStream gZIPOutputStream;
        byte[] byteArray;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            try {
                gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                try {
                    gZIPOutputStream.write(bArr);
                    gZIPOutputStream.finish();
                    byteArray = byteArrayOutputStream.toByteArray();
                    try {
                        gZIPOutputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        byteArrayOutputStream.close();
                        byteArrayOutputStream = byteArrayOutputStream;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        byteArrayOutputStream = e2;
                    }
                } catch (Throwable th) {
                    th = th;
                    th.printStackTrace();
                    if (gZIPOutputStream != null) {
                        try {
                            gZIPOutputStream.close();
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                    try {
                        byteArrayOutputStream.close();
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                    byteArray = null;
                    byteArrayOutputStream = byteArrayOutputStream;
                }
            } catch (Throwable th2) {
                th = th2;
                if (gZIPOutputStream != null) {
                    try {
                        gZIPOutputStream.close();
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
                try {
                    byteArrayOutputStream.close();
                    throw th;
                } catch (Exception e6) {
                    e6.printStackTrace();
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            gZIPOutputStream = null;
            if (gZIPOutputStream != null) {
            }
            byteArrayOutputStream.close();
            throw th;
        }
        return byteArray;
    }

    public static byte[] b(byte[] bArr) throws Throwable {
        GZIPInputStream gZIPInputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        ByteArrayOutputStream byteArrayOutputStream2;
        Throwable th;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        try {
            try {
                gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            gZIPInputStream = null;
            byteArrayOutputStream = null;
        }
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                try {
                    int i = gZIPInputStream.read();
                    if (i == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(i);
                } catch (Throwable th4) {
                    th = th4;
                    th.printStackTrace();
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (gZIPInputStream != null) {
                        try {
                            gZIPInputStream.close();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    try {
                        byteArrayInputStream.close();
                        return null;
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        return null;
                    }
                }
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
            } catch (Exception e4) {
                e4.printStackTrace();
            }
            try {
                gZIPInputStream.close();
            } catch (Exception e5) {
                e5.printStackTrace();
            }
            try {
                byteArrayInputStream.close();
            } catch (Exception e6) {
                e6.printStackTrace();
            }
            return byteArray;
        } catch (Throwable th5) {
            byteArrayOutputStream2 = null;
            th = th5;
            if (byteArrayOutputStream2 != null) {
                try {
                    byteArrayOutputStream2.close();
                } catch (Exception e7) {
                    e7.printStackTrace();
                }
            }
            if (gZIPInputStream != null) {
                try {
                    gZIPInputStream.close();
                } catch (Exception e8) {
                    e8.printStackTrace();
                }
            }
            try {
                byteArrayInputStream.close();
                throw th;
            } catch (Exception e9) {
                e9.printStackTrace();
                throw th;
            }
        }
    }
}
