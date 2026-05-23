package com.mobile.auth.c;

import android.text.TextUtils;
import android.util.Log;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class g {
    private static final String a = "g";
    private final int[] b = {1732584193, -271733879, -1732584194, 271733878, -1009589776};
    private int[] c = new int[5];
    private int[] d = new int[80];

    private int a(int i, int i2) {
        return (i << i2) | (i >>> (32 - i2));
    }

    private int a(int i, int i2, int i3) {
        return (i & i2) | ((~i) & i3);
    }

    private int a(byte[] bArr, int i) {
        try {
            return ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8) | (bArr[i + 3] & 255);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
    }

    private void a() {
        for (int i = 16; i <= 79; i++) {
            try {
                this.d[i] = a(((this.d[i - 3] ^ this.d[i - 8]) ^ this.d[i - 14]) ^ this.d[i - 16], 1);
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return;
                }
            }
        }
        int[] iArr = new int[5];
        for (int i2 = 0; i2 < iArr.length; i2++) {
            iArr[i2] = this.c[i2];
        }
        for (int i3 = 0; i3 <= 19; i3++) {
            int iA = a(iArr[0], 5) + a(iArr[1], iArr[2], iArr[3]) + iArr[4] + this.d[i3] + 1518500249;
            iArr[4] = iArr[3];
            iArr[3] = iArr[2];
            iArr[2] = a(iArr[1], 30);
            iArr[1] = iArr[0];
            iArr[0] = iA;
        }
        for (int i4 = 20; i4 <= 39; i4++) {
            int iA2 = a(iArr[0], 5) + b(iArr[1], iArr[2], iArr[3]) + iArr[4] + this.d[i4] + 1859775393;
            iArr[4] = iArr[3];
            iArr[3] = iArr[2];
            iArr[2] = a(iArr[1], 30);
            iArr[1] = iArr[0];
            iArr[0] = iA2;
        }
        for (int i5 = 40; i5 <= 59; i5++) {
            int iA3 = (((a(iArr[0], 5) + c(iArr[1], iArr[2], iArr[3])) + iArr[4]) + this.d[i5]) - 1894007588;
            iArr[4] = iArr[3];
            iArr[3] = iArr[2];
            iArr[2] = a(iArr[1], 30);
            iArr[1] = iArr[0];
            iArr[0] = iA3;
        }
        for (int i6 = 60; i6 <= 79; i6++) {
            int iA4 = (((a(iArr[0], 5) + b(iArr[1], iArr[2], iArr[3])) + iArr[4]) + this.d[i6]) - 899497514;
            iArr[4] = iArr[3];
            iArr[3] = iArr[2];
            iArr[2] = a(iArr[1], 30);
            iArr[1] = iArr[0];
            iArr[0] = iA4;
        }
        for (int i7 = 0; i7 < iArr.length; i7++) {
            this.c[i7] = this.c[i7] + iArr[i7];
        }
        for (int i8 = 0; i8 < this.d.length; i8++) {
            this.d[i8] = 0;
        }
    }

    private void a(int i, byte[] bArr, int i2) {
        try {
            bArr[i2] = (byte) (i >>> 24);
            bArr[i2 + 1] = (byte) (i >>> 16);
            bArr[i2 + 2] = (byte) (i >>> 8);
            bArr[i2 + 3] = (byte) i;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static byte[] a(String str, String str2) {
        try {
            if (TextUtils.isEmpty(str2)) {
                Log.i(a, "when getHmacSHA1,the key is null");
                return null;
            }
            try {
                byte[] bArr = new byte[64];
                byte[] bArr2 = new byte[64];
                byte[] bArr3 = new byte[64];
                int length = str2.length();
                g gVar = new g();
                if (str2.length() > 64) {
                    byte[] bArrA = gVar.a(q.b(str2));
                    length = bArrA.length;
                    for (int i = 0; i < length; i++) {
                        bArr3[i] = bArrA[i];
                    }
                } else {
                    byte[] bArrB = q.b(str2);
                    for (int i2 = 0; i2 < bArrB.length; i2++) {
                        bArr3[i2] = bArrB[i2];
                    }
                }
                while (length < 64) {
                    bArr3[length] = 0;
                    length++;
                }
                for (int i3 = 0; i3 < 64; i3++) {
                    bArr[i3] = (byte) (bArr3[i3] ^ 54);
                    bArr2[i3] = (byte) (bArr3[i3] ^ 92);
                }
                return gVar.a(a(bArr2, gVar.a(a(bArr, q.b(str)))));
            } catch (Throwable th) {
                Log.w(a, "getHmacSHA1 error", th);
                return null;
            }
        } catch (Throwable th2) {
            try {
                ExceptionProcessor.processException(th2);
                return null;
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
                return null;
            }
        }
    }

    private static byte[] a(byte[] bArr, byte[] bArr2) {
        try {
            byte[] bArr3 = new byte[bArr.length + bArr2.length];
            for (int i = 0; i < bArr.length; i++) {
                bArr3[i] = bArr[i];
            }
            for (int i2 = 0; i2 < bArr2.length; i2++) {
                bArr3[bArr.length + i2] = bArr2[i2];
            }
            return bArr3;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private int b(int i, int i2, int i3) {
        return (i ^ i2) ^ i3;
    }

    public static String b(byte[] bArr) {
        try {
            StringBuilder sb = new StringBuilder("");
            if (bArr != null && bArr.length > 0) {
                for (byte b : bArr) {
                    String upperCase = Integer.toHexString(b & 255).toUpperCase(Locale.CHINA);
                    if (upperCase.length() < 2) {
                        sb.append(0);
                    }
                    sb.append(upperCase);
                }
                return sb.toString();
            }
            return null;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private int c(int i, int i2, int i3) {
        return (i & i2) | (i & i3) | (i2 & i3);
    }

    private int c(byte[] bArr) {
        try {
            System.arraycopy(this.b, 0, this.c, 0, this.b.length);
            byte[] bArrD = d(bArr);
            int length = bArrD.length / 64;
            for (int i = 0; i < length; i++) {
                for (int i2 = 0; i2 < 16; i2++) {
                    this.d[i2] = a(bArrD, (i * 64) + (i2 * 4));
                }
                a();
            }
            return 20;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002c A[Catch: Throwable -> 0x008a, LOOP:0: B:10:0x002a->B:11:0x002c, LOOP_END, TryCatch #0 {Throwable -> 0x008a, blocks: (B:2:0x0000, B:4:0x0009, B:5:0x000d, B:9:0x001c, B:11:0x002c, B:12:0x0034, B:7:0x0012, B:8:0x0015), top: B:21:0x0000 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private byte[] d(byte[] bArr) {
        int i;
        int i2;
        int i3;
        try {
            int length = bArr.length;
            int i4 = length % 64;
            int i5 = 63;
            if (i4 < 56) {
                i5 = 55 - i4;
                i2 = length - i4;
            } else {
                if (i4 != 56) {
                    i5 = (63 - i4) + 56;
                    i = ((length + 64) - i4) + 64;
                    byte[] bArr2 = new byte[i];
                    System.arraycopy(bArr, 0, bArr2, 0, length);
                    bArr2[length] = -128;
                    int i6 = length + 1;
                    i3 = 0;
                    while (i3 < i5) {
                        bArr2[i6] = 0;
                        i3++;
                        i6++;
                    }
                    long j = ((long) length) * 8;
                    byte b = (byte) (j & 255);
                    byte b2 = (byte) ((j >> 8) & 255);
                    byte b3 = (byte) ((j >> 16) & 255);
                    byte b4 = (byte) ((j >> 24) & 255);
                    byte b5 = (byte) ((j >> 32) & 255);
                    byte b6 = (byte) ((j >> 40) & 255);
                    byte b7 = (byte) (255 & (j >> 48));
                    int i7 = i6 + 1;
                    bArr2[i6] = (byte) (j >> 56);
                    int i8 = i7 + 1;
                    bArr2[i7] = b7;
                    int i9 = i8 + 1;
                    bArr2[i8] = b6;
                    int i10 = i9 + 1;
                    bArr2[i9] = b5;
                    int i11 = i10 + 1;
                    bArr2[i10] = b4;
                    int i12 = i11 + 1;
                    bArr2[i11] = b3;
                    bArr2[i12] = b2;
                    bArr2[i12 + 1] = b;
                    return bArr2;
                }
                i2 = length + 8;
            }
            i = i2 + 64;
            byte[] bArr22 = new byte[i];
            System.arraycopy(bArr, 0, bArr22, 0, length);
            bArr22[length] = -128;
            int i62 = length + 1;
            i3 = 0;
            while (i3 < i5) {
            }
            long j2 = ((long) length) * 8;
            byte b8 = (byte) (j2 & 255);
            byte b22 = (byte) ((j2 >> 8) & 255);
            byte b32 = (byte) ((j2 >> 16) & 255);
            byte b42 = (byte) ((j2 >> 24) & 255);
            byte b52 = (byte) ((j2 >> 32) & 255);
            byte b62 = (byte) ((j2 >> 40) & 255);
            byte b72 = (byte) (255 & (j2 >> 48));
            int i72 = i62 + 1;
            bArr22[i62] = (byte) (j2 >> 56);
            int i82 = i72 + 1;
            bArr22[i72] = b72;
            int i92 = i82 + 1;
            bArr22[i82] = b62;
            int i102 = i92 + 1;
            bArr22[i92] = b52;
            int i112 = i102 + 1;
            bArr22[i102] = b42;
            int i122 = i112 + 1;
            bArr22[i112] = b32;
            bArr22[i122] = b22;
            bArr22[i122 + 1] = b8;
            return bArr22;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public byte[] a(byte[] bArr) {
        try {
            c(bArr);
            byte[] bArr2 = new byte[20];
            for (int i = 0; i < this.c.length; i++) {
                a(this.c[i], bArr2, i * 4);
            }
            return bArr2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }
}
