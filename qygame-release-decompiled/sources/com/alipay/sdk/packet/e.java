package com.alipay.sdk.packet;

import com.alipay.sdk.util.l;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class e {
    private boolean a;
    private String b = l.e();

    public e(boolean z) {
        this.a = z;
    }

    private static int a(String str) {
        return Integer.parseInt(str);
    }

    private static String a(int i) {
        return String.format(Locale.getDefault(), "%05d", Integer.valueOf(i));
    }

    private static byte[] a(String str, String str2) {
        return com.alipay.sdk.encrypt.d.a(str, str2);
    }

    private static byte[] a(String str, byte[] bArr) {
        return com.alipay.sdk.encrypt.e.a(str, bArr);
    }

    private static byte[] a(byte[]... bArr) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        DataOutputStream dataOutputStream;
        if (bArr.length == 0) {
            return null;
        }
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                for (int i = 0; i < bArr.length; i++) {
                    try {
                        dataOutputStream.write(String.format(Locale.getDefault(), "%05d", Integer.valueOf(bArr[i].length)).getBytes());
                        dataOutputStream.write(bArr[i]);
                    } catch (Exception unused) {
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (Exception unused2) {
                            }
                        }
                        if (dataOutputStream != null) {
                            try {
                                dataOutputStream.close();
                            } catch (Exception unused3) {
                            }
                        }
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        if (byteArrayOutputStream != null) {
                            try {
                                byteArrayOutputStream.close();
                            } catch (Exception unused4) {
                            }
                        }
                        if (dataOutputStream == null) {
                            throw th;
                        }
                        try {
                            dataOutputStream.close();
                            throw th;
                        } catch (Exception unused5) {
                            throw th;
                        }
                    }
                }
                dataOutputStream.flush();
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                try {
                    byteArrayOutputStream.close();
                } catch (Exception unused6) {
                }
                try {
                    dataOutputStream.close();
                    return byteArray;
                } catch (Exception unused7) {
                    return byteArray;
                }
            } catch (Exception unused8) {
                dataOutputStream = null;
            } catch (Throwable th2) {
                th = th2;
                dataOutputStream = null;
            }
        } catch (Exception unused9) {
            byteArrayOutputStream = null;
            dataOutputStream = null;
        } catch (Throwable th3) {
            th = th3;
            byteArrayOutputStream = null;
            dataOutputStream = null;
        }
    }

    private static byte[] b(String str, byte[] bArr) {
        return com.alipay.sdk.encrypt.e.b(str, bArr);
    }

    public final b a(c cVar) throws Throwable {
        ByteArrayInputStream byteArrayInputStream;
        String str;
        String str2;
        try {
            byteArrayInputStream = new ByteArrayInputStream(cVar.b);
            try {
                try {
                    byte[] bArr = new byte[5];
                    byteArrayInputStream.read(bArr);
                    byte[] bArr2 = new byte[Integer.parseInt(new String(bArr))];
                    byteArrayInputStream.read(bArr2);
                    str = new String(bArr2);
                } catch (Throwable th) {
                    th = th;
                    if (byteArrayInputStream != null) {
                        try {
                            byteArrayInputStream.close();
                        } catch (Exception unused) {
                        }
                    }
                    throw th;
                }
            } catch (Exception unused2) {
                str = null;
            }
            try {
                byte[] bArr3 = new byte[5];
                byteArrayInputStream.read(bArr3);
                int i = Integer.parseInt(new String(bArr3));
                if (i > 0) {
                    byte[] bArrB = new byte[i];
                    byteArrayInputStream.read(bArrB);
                    if (this.a) {
                        bArrB = com.alipay.sdk.encrypt.e.b(this.b, bArrB);
                    }
                    if (cVar.a) {
                        bArrB = com.alipay.sdk.encrypt.c.b(bArrB);
                    }
                    str2 = new String(bArrB);
                } else {
                    str2 = null;
                }
                try {
                    byteArrayInputStream.close();
                } catch (Exception unused3) {
                }
            } catch (Exception unused4) {
                if (byteArrayInputStream != null) {
                    try {
                        byteArrayInputStream.close();
                    } catch (Exception unused5) {
                    }
                }
                str2 = null;
            }
        } catch (Exception unused6) {
            byteArrayInputStream = null;
            str = null;
        } catch (Throwable th2) {
            th = th2;
            byteArrayInputStream = null;
        }
        if (str == null && str2 == null) {
            return null;
        }
        return new b(str, str2);
    }

    public final c a(b bVar, boolean z) throws Throwable {
        byte[] bytes = bVar.a.getBytes();
        byte[] bytes2 = bVar.b.getBytes();
        if (z) {
            try {
                bytes2 = com.alipay.sdk.encrypt.c.a(bytes2);
            } catch (Exception unused) {
                z = false;
            }
        }
        return new c(z, this.a ? a(bytes, com.alipay.sdk.encrypt.d.a(this.b, com.alipay.sdk.cons.a.c), com.alipay.sdk.encrypt.e.a(this.b, bytes2)) : a(bytes, bytes2));
    }
}
