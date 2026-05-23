package com.alibaba.fastjson.util;

import com.igexin.push.config.c;
import java.lang.reflect.Array;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes.dex */
public final class RyuDouble {
    private static final int[][] POW5_SPLIT = (int[][]) Array.newInstance((Class<?>) int.class, 326, 4);
    private static final int[][] POW5_INV_SPLIT = (int[][]) Array.newInstance((Class<?>) int.class, 291, 4);

    static {
        BigInteger bigIntegerSubtract = BigInteger.ONE.shiftLeft(31).subtract(BigInteger.ONE);
        BigInteger bigIntegerSubtract2 = BigInteger.ONE.shiftLeft(31).subtract(BigInteger.ONE);
        int i = 0;
        while (i < 326) {
            BigInteger bigIntegerPow = BigInteger.valueOf(5L).pow(i);
            int iBitLength = bigIntegerPow.bitLength();
            int i2 = i == 0 ? 1 : (int) ((((((long) i) * 23219280) + 10000000) - 1) / 10000000);
            if (i2 != iBitLength) {
                throw new IllegalStateException(iBitLength + " != " + i2);
            }
            if (i < POW5_SPLIT.length) {
                for (int i3 = 0; i3 < 4; i3++) {
                    POW5_SPLIT[i][i3] = bigIntegerPow.shiftRight((iBitLength - 121) + ((3 - i3) * 31)).and(bigIntegerSubtract).intValue();
                }
            }
            if (i < POW5_INV_SPLIT.length) {
                BigInteger bigIntegerAdd = BigInteger.ONE.shiftLeft(iBitLength + 121).divide(bigIntegerPow).add(BigInteger.ONE);
                for (int i4 = 0; i4 < 4; i4++) {
                    if (i4 == 0) {
                        POW5_INV_SPLIT[i][i4] = bigIntegerAdd.shiftRight((3 - i4) * 31).intValue();
                    } else {
                        POW5_INV_SPLIT[i][i4] = bigIntegerAdd.shiftRight((3 - i4) * 31).and(bigIntegerSubtract2).intValue();
                    }
                }
            }
            i++;
        }
    }

    public static int toString(double d, char[] cArr, int i) {
        int i2;
        boolean z;
        boolean z2;
        long j;
        long j2;
        int i3;
        long j3;
        int i4;
        int i5;
        int i6;
        long j4;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        if (!Double.isNaN(d)) {
            if (d == Double.POSITIVE_INFINITY) {
                int i19 = i + 1;
                cArr[i] = 'I';
                int i20 = i19 + 1;
                cArr[i19] = 'n';
                int i21 = i20 + 1;
                cArr[i20] = 'f';
                int i22 = i21 + 1;
                cArr[i21] = 'i';
                int i23 = i22 + 1;
                cArr[i22] = 'n';
                int i24 = i23 + 1;
                cArr[i23] = 'i';
                int i25 = i24 + 1;
                cArr[i24] = 't';
                i17 = i25 + 1;
                cArr[i25] = 'y';
            } else if (d == Double.NEGATIVE_INFINITY) {
                int i26 = i + 1;
                cArr[i] = '-';
                int i27 = i26 + 1;
                cArr[i26] = 'I';
                int i28 = i27 + 1;
                cArr[i27] = 'n';
                int i29 = i28 + 1;
                cArr[i28] = 'f';
                int i30 = i29 + 1;
                cArr[i29] = 'i';
                int i31 = i30 + 1;
                cArr[i30] = 'n';
                int i32 = i31 + 1;
                cArr[i31] = 'i';
                int i33 = i32 + 1;
                cArr[i32] = 't';
                i18 = i33 + 1;
                cArr[i33] = 'y';
            } else {
                long jDoubleToLongBits = Double.doubleToLongBits(d);
                if (jDoubleToLongBits == 0) {
                    int i34 = i + 1;
                    cArr[i] = '0';
                    int i35 = i34 + 1;
                    cArr[i34] = '.';
                    i18 = i35 + 1;
                    cArr[i35] = '0';
                } else {
                    if (jDoubleToLongBits != Long.MIN_VALUE) {
                        int i36 = (int) ((jDoubleToLongBits >>> 52) & 2047);
                        long j5 = jDoubleToLongBits & 4503599627370495L;
                        if (i36 == 0) {
                            i2 = -1074;
                        } else {
                            i2 = (i36 - 1023) - 52;
                            j5 |= 4503599627370496L;
                        }
                        boolean z3 = jDoubleToLongBits < 0;
                        boolean z4 = (j5 & 1) == 0;
                        long j6 = 4 * j5;
                        long j7 = j6 + 2;
                        int i37 = (j5 != 4503599627370496L || i36 <= 1) ? 1 : 0;
                        long j8 = (j6 - 1) - ((long) i37);
                        int i38 = i2 - 2;
                        if (i38 >= 0) {
                            int iMax = Math.max(0, ((int) ((((long) i38) * 3010299) / 10000000)) - 1);
                            int i39 = ((((-i38) + iMax) + (((iMax == 0 ? 1 : (int) ((((((long) iMax) * 23219280) + 10000000) - 1) / 10000000)) + 122) - 1)) - 93) - 21;
                            if (i39 < 0) {
                                throw new IllegalArgumentException("" + i39);
                            }
                            int[] iArr = POW5_INV_SPLIT[iMax];
                            long j9 = j6 >>> 31;
                            long j10 = j6 & 2147483647L;
                            z2 = z4;
                            z = z3;
                            long j11 = ((((((((((((j10 * ((long) iArr[3])) >>> 31) + (((long) iArr[2]) * j10)) + (j9 * ((long) iArr[3]))) >>> 31) + (((long) iArr[1]) * j10)) + (((long) iArr[2]) * j9)) >>> 31) + (((long) iArr[0]) * j10)) + (((long) iArr[1]) * j9)) >>> 21) + ((((long) iArr[0]) * j9) << 10)) >>> i39;
                            long j12 = j7 >>> 31;
                            long j13 = j7 & 2147483647L;
                            long j14 = ((((((((((((j13 * ((long) iArr[3])) >>> 31) + (((long) iArr[2]) * j13)) + (j12 * ((long) iArr[3]))) >>> 31) + (((long) iArr[1]) * j13)) + (((long) iArr[2]) * j12)) >>> 31) + (((long) iArr[0]) * j13)) + (((long) iArr[1]) * j12)) >>> 21) + ((((long) iArr[0]) * j12) << 10)) >>> i39;
                            long j15 = j8 >>> 31;
                            long j16 = j8 & 2147483647L;
                            j2 = j14;
                            j3 = ((((((((((((j16 * ((long) iArr[3])) >>> 31) + (((long) iArr[2]) * j16)) + (j15 * ((long) iArr[3]))) >>> 31) + (((long) iArr[1]) * j16)) + (((long) iArr[2]) * j15)) >>> 31) + (((long) iArr[0]) * j16)) + (((long) iArr[1]) * j15)) >>> 21) + ((((long) iArr[0]) * j15) << 10)) >>> i39;
                            if (iMax <= 21) {
                                long j17 = j6 % 5;
                                if (j17 == 0) {
                                    if (j17 != 0) {
                                        i16 = 0;
                                    } else if (j6 % 25 != 0) {
                                        i16 = 1;
                                    } else if (j6 % 125 != 0) {
                                        i16 = 2;
                                    } else if (j6 % 625 != 0) {
                                        i16 = 3;
                                    } else {
                                        long j18 = j6 / 625;
                                        i16 = 4;
                                        for (long j19 = 0; j18 > j19 && j18 % 5 == j19; j19 = 0) {
                                            j18 /= 5;
                                            i16++;
                                        }
                                    }
                                    i13 = i16 >= iMax ? 1 : 0;
                                    i6 = 0;
                                    i5 = i13;
                                    j = j11;
                                    i4 = iMax;
                                    i3 = 0;
                                } else if (z2) {
                                    if (j8 % 5 != 0) {
                                        i15 = 0;
                                    } else if (j8 % 25 != 0) {
                                        i15 = 1;
                                    } else if (j8 % 125 != 0) {
                                        i15 = 2;
                                    } else if (j8 % 625 != 0) {
                                        i15 = 3;
                                    } else {
                                        long j20 = j8 / 625;
                                        i15 = 4;
                                        for (long j21 = 0; j20 > j21 && j20 % 5 == j21; j21 = 0) {
                                            j20 /= 5;
                                            i15++;
                                        }
                                    }
                                    i6 = i15 >= iMax ? 1 : 0;
                                    i13 = 0;
                                    i5 = i13;
                                    j = j11;
                                    i4 = iMax;
                                    i3 = 0;
                                } else {
                                    if (j7 % 5 != 0) {
                                        i14 = 0;
                                    } else if (j7 % 25 != 0) {
                                        i14 = 1;
                                    } else if (j7 % 125 != 0) {
                                        i14 = 2;
                                    } else if (j7 % 625 != 0) {
                                        i14 = 3;
                                    } else {
                                        long j22 = j7 / 625;
                                        i14 = 4;
                                        for (long j23 = 0; j22 > j23 && j22 % 5 == j23; j23 = 0) {
                                            j22 /= 5;
                                            i14++;
                                        }
                                    }
                                    if (i14 >= iMax) {
                                        j2--;
                                    }
                                    i13 = 0;
                                    i6 = 0;
                                    i5 = i13;
                                    j = j11;
                                    i4 = iMax;
                                    i3 = 0;
                                }
                            } else {
                                i13 = 0;
                                i6 = 0;
                                i5 = i13;
                                j = j11;
                                i4 = iMax;
                                i3 = 0;
                            }
                        } else {
                            z = z3;
                            z2 = z4;
                            int i40 = -i38;
                            int iMax2 = Math.max(0, ((int) ((((long) i40) * 6989700) / 10000000)) - 1);
                            int i41 = i40 - iMax2;
                            int i42 = ((iMax2 - ((i41 == 0 ? 1 : (int) ((((((long) i41) * 23219280) + 10000000) - 1) / 10000000)) - 121)) - 93) - 21;
                            if (i42 < 0) {
                                throw new IllegalArgumentException("" + i42);
                            }
                            int[] iArr2 = POW5_SPLIT[i41];
                            long j24 = j6 >>> 31;
                            long j25 = j6 & 2147483647L;
                            int i43 = i37;
                            long j26 = ((((((((((((j25 * ((long) iArr2[3])) >>> 31) + (((long) iArr2[2]) * j25)) + (j24 * ((long) iArr2[3]))) >>> 31) + (((long) iArr2[1]) * j25)) + (((long) iArr2[2]) * j24)) >>> 31) + (((long) iArr2[0]) * j25)) + (((long) iArr2[1]) * j24)) >>> 21) + ((((long) iArr2[0]) * j24) << 10)) >>> i42;
                            long j27 = j7 >>> 31;
                            long j28 = j7 & 2147483647L;
                            j = j26;
                            j2 = ((((((((((((j28 * ((long) iArr2[3])) >>> 31) + (((long) iArr2[2]) * j28)) + (j27 * ((long) iArr2[3]))) >>> 31) + (((long) iArr2[1]) * j28)) + (((long) iArr2[2]) * j27)) >>> 31) + (((long) iArr2[0]) * j28)) + (((long) iArr2[1]) * j27)) >>> 21) + ((((long) iArr2[0]) * j27) << 10)) >>> i42;
                            long j29 = j8 >>> 31;
                            long j30 = j8 & 2147483647L;
                            i3 = 0;
                            j3 = ((((((((((((j30 * ((long) iArr2[3])) >>> 31) + (((long) iArr2[2]) * j30)) + (j29 * ((long) iArr2[3]))) >>> 31) + (((long) iArr2[1]) * j30)) + (((long) iArr2[2]) * j29)) >>> 31) + (((long) iArr2[0]) * j30)) + (((long) iArr2[1]) * j29)) >>> 21) + ((((long) iArr2[0]) * j29) << 10)) >>> i42;
                            i4 = iMax2 + i38;
                            i5 = 1;
                            if (iMax2 <= 1) {
                                if (!z2) {
                                    j2--;
                                } else if (i43 == 1) {
                                    i6 = i5;
                                }
                                i6 = 0;
                            } else if (iMax2 < 63) {
                                i5 = (j6 & ((1 << (iMax2 - 1)) - 1)) == 0 ? 1 : 0;
                                i6 = 0;
                            } else {
                                i5 = 0;
                                i6 = i5;
                            }
                        }
                        int i44 = j2 >= 1000000000000000000L ? 19 : j2 >= 100000000000000000L ? 18 : j2 >= 10000000000000000L ? 17 : j2 >= 1000000000000000L ? 16 : j2 >= 100000000000000L ? 15 : j2 >= 10000000000000L ? 14 : j2 >= 1000000000000L ? 13 : j2 >= 100000000000L ? 12 : j2 >= 10000000000L ? 11 : j2 >= 1000000000 ? 10 : j2 >= 100000000 ? 9 : j2 >= 10000000 ? 8 : j2 >= 1000000 ? 7 : j2 >= 100000 ? 6 : j2 >= c.i ? 5 : j2 >= 1000 ? 4 : j2 >= 100 ? 3 : j2 >= 10 ? 2 : 1;
                        int i45 = (i4 + i44) - 1;
                        int i46 = (i45 < -3 || i45 >= 7) ? 1 : i3;
                        if (i6 == 0 && i5 == 0) {
                            i7 = i3;
                            int i47 = i7;
                            while (true) {
                                long j31 = j2 / 10;
                                long j32 = j3 / 10;
                                if (j31 <= j32 || (j2 < 100 && i46 != 0)) {
                                    break;
                                }
                                i47 = (int) (j % 10);
                                j /= 10;
                                i7++;
                                j2 = j31;
                                j3 = j32;
                            }
                            j4 = j + ((long) ((j == j3 || i47 >= 5) ? 1 : i3));
                        } else {
                            int i48 = i3;
                            int i49 = i48;
                            while (true) {
                                long j33 = j2 / 10;
                                long j34 = j3 / 10;
                                if (j33 <= j34 || (j2 < 100 && i46 != 0)) {
                                    break;
                                }
                                i6 &= j3 % 10 == 0 ? 1 : i3;
                                i5 &= i48 == 0 ? 1 : i3;
                                i48 = (int) (j % 10);
                                j /= 10;
                                i49++;
                                j2 = j33;
                                j3 = j34;
                            }
                            if (i6 != 0 && z2) {
                                while (j3 % 10 == 0 && (j2 >= 100 || i46 == 0)) {
                                    i5 &= i48 == 0 ? 1 : i3;
                                    i48 = (int) (j % 10);
                                    j2 /= 10;
                                    j /= 10;
                                    j3 /= 10;
                                    i49++;
                                }
                            }
                            if (i5 != 0 && i48 == 5 && j % 2 == 0) {
                                i48 = 4;
                            }
                            j4 = j + ((long) (((j != j3 || (i6 != 0 && z2)) && i48 < 5) ? i3 : 1));
                            i7 = i49;
                        }
                        int i50 = i44 - i7;
                        if (z) {
                            i8 = i + 1;
                            cArr[i] = '-';
                        } else {
                            i8 = i;
                        }
                        if (i46 != 0) {
                            while (i3 < i50 - 1) {
                                int i51 = (int) (j4 % 10);
                                j4 /= 10;
                                cArr[(i8 + i50) - i3] = (char) (i51 + 48);
                                i3++;
                            }
                            cArr[i8] = (char) ((j4 % 10) + 48);
                            cArr[i8 + 1] = '.';
                            int i52 = i8 + i50 + 1;
                            if (i50 == 1) {
                                i10 = i52 + 1;
                                cArr[i52] = '0';
                            } else {
                                i10 = i52;
                            }
                            int i53 = i10 + 1;
                            cArr[i10] = 'E';
                            if (i45 < 0) {
                                i11 = i53 + 1;
                                cArr[i53] = '-';
                                i45 = -i45;
                            } else {
                                i11 = i53;
                            }
                            if (i45 >= 100) {
                                int i54 = i11 + 1;
                                i12 = 48;
                                cArr[i11] = (char) ((i45 / 100) + 48);
                                i45 %= 100;
                                i11 = i54 + 1;
                                cArr[i54] = (char) ((i45 / 10) + 48);
                            } else {
                                i12 = 48;
                                if (i45 >= 10) {
                                    cArr[i11] = (char) ((i45 / 10) + 48);
                                    i11++;
                                }
                            }
                            cArr[i11] = (char) ((i45 % 10) + i12);
                            return (i11 + 1) - i;
                        }
                        char c = '0';
                        if (i45 < 0) {
                            int i55 = i8 + 1;
                            cArr[i8] = '0';
                            int i56 = i55 + 1;
                            cArr[i55] = '.';
                            int i57 = -1;
                            while (i57 > i45) {
                                cArr[i56] = c;
                                i57--;
                                i56++;
                                c = '0';
                            }
                            i9 = i56;
                            while (i3 < i50) {
                                cArr[((i56 + i50) - i3) - 1] = (char) ((j4 % 10) + 48);
                                j4 /= 10;
                                i9++;
                                i3++;
                            }
                        } else {
                            int i58 = i45 + 1;
                            if (i58 >= i50) {
                                while (i3 < i50) {
                                    cArr[((i8 + i50) - i3) - 1] = (char) ((j4 % 10) + 48);
                                    j4 /= 10;
                                    i3++;
                                }
                                int i59 = i8 + i50;
                                while (i50 < i58) {
                                    cArr[i59] = '0';
                                    i50++;
                                    i59++;
                                }
                                int i60 = i59 + 1;
                                cArr[i59] = '.';
                                cArr[i60] = '0';
                                i9 = i60 + 1;
                            } else {
                                int i61 = i8 + 1;
                                while (i3 < i50) {
                                    if ((i50 - i3) - 1 == i45) {
                                        cArr[((i61 + i50) - i3) - 1] = '.';
                                        i61--;
                                    }
                                    cArr[((i61 + i50) - i3) - 1] = (char) (48 + (j4 % 10));
                                    j4 /= 10;
                                    i3++;
                                }
                                i9 = i8 + i50 + 1;
                            }
                        }
                        return i9 - i;
                    }
                    int i62 = i + 1;
                    cArr[i] = '-';
                    int i63 = i62 + 1;
                    cArr[i62] = '0';
                    int i64 = i63 + 1;
                    cArr[i63] = '.';
                    i17 = i64 + 1;
                    cArr[i64] = '0';
                }
            }
            return i17 - i;
        }
        int i65 = i + 1;
        cArr[i] = 'N';
        int i66 = i65 + 1;
        cArr[i65] = 'a';
        i18 = i66 + 1;
        cArr[i66] = 'N';
        return i18 - i;
    }

    public static String toString(double d) {
        char[] cArr = new char[24];
        return new String(cArr, 0, toString(d, cArr, 0));
    }
}
