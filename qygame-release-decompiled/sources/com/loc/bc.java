package com.loc;

/* JADX INFO: loaded from: classes.dex */
public final class bc {
    private static int a = 4;

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        if (bArr.length == 0) {
            return bArr;
        }
        int length = bArr.length;
        int i = a;
        int i2 = i - (length % i);
        byte[] bArr3 = new byte[((length / a) + 1) * a];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        while (length < bArr3.length) {
            bArr3[length] = (byte) i2;
            length++;
        }
        int[] iArrA = a(bArr3);
        int[] iArrA2 = a(bArr2);
        int length2 = iArrA.length - 1;
        if (length2 > 0) {
            if (iArrA2.length < 4) {
                int[] iArr = new int[4];
                System.arraycopy(iArrA2, 0, iArr, 0, iArrA2.length);
                iArrA2 = iArr;
            }
            int i3 = (52 / (length2 + 1)) + 6;
            int i4 = iArrA[length2];
            int i5 = 0;
            while (true) {
                int i6 = i3 - 1;
                if (i3 <= 0) {
                    break;
                }
                i5 -= 1640531527;
                int i7 = (i5 >>> 2) & 3;
                int i8 = i4;
                int i9 = 0;
                while (i9 < length2) {
                    int i10 = i9 + 1;
                    int i11 = iArrA[i10];
                    i8 = ((((i8 >>> 5) ^ (i11 << 2)) + ((i11 >>> 3) ^ (i8 << 4))) ^ ((i11 ^ i5) + (i8 ^ iArrA2[(i9 & 3) ^ i7]))) + iArrA[i9];
                    iArrA[i9] = i8;
                    i9 = i10;
                }
                int i12 = iArrA[0];
                i4 = iArrA[length2] + ((((i8 >>> 5) ^ (i12 << 2)) + ((i12 >>> 3) ^ (i8 << 4))) ^ ((i12 ^ i5) + (iArrA2[i7 ^ (i9 & 3)] ^ i8)));
                iArrA[length2] = i4;
                i3 = i6;
            }
        }
        int length3 = iArrA.length << 2;
        byte[] bArr4 = new byte[length3];
        for (int i13 = 0; i13 < length3; i13++) {
            bArr4[i13] = (byte) ((iArrA[i13 >>> 2] >>> ((i13 & 3) << 3)) & 255);
        }
        return bArr4;
    }

    private static int[] a(byte[] bArr) {
        int[] iArr = new int[bArr.length >>> 2];
        int length = bArr.length;
        for (int i = 0; i < length; i++) {
            int i2 = i >>> 2;
            iArr[i2] = iArr[i2] | ((bArr[i] & 255) << ((i & 3) << 3));
        }
        return iArr;
    }
}
