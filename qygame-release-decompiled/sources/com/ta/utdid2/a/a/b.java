package com.ta.utdid2.a.a;

import android.annotation.SuppressLint;
import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes.dex */
public class b {
    static final /* synthetic */ boolean a = !b.class.desiredAssertionStatus();

    static abstract class a {
        public int op;
        public byte[] output;

        a() {
        }
    }

    /* JADX INFO: renamed from: com.ta.utdid2.a.a.b$b, reason: collision with other inner class name */
    static class C0052b extends a {
        private static final int[] a = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int[] b = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private final int[] c;
        private int state;
        private int value;

        public C0052b(int i, byte[] bArr) {
            this.output = bArr;
            this.c = (i & 8) == 0 ? a : b;
            this.state = 0;
            this.value = 0;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:53:0x00d9  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x00e0  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean process(byte[] bArr, int i, int i2, boolean z) {
            if (this.state == 6) {
                return false;
            }
            int i3 = i2 + i;
            int i4 = this.state;
            int i5 = this.value;
            byte[] bArr2 = this.output;
            int[] iArr = this.c;
            int i6 = 0;
            while (i < i3) {
                if (i4 == 0) {
                    while (true) {
                        int i7 = i + 4;
                        if (i7 <= i3 && (i5 = (iArr[bArr[i] & 255] << 18) | (iArr[bArr[i + 1] & 255] << 12) | (iArr[bArr[i + 2] & 255] << 6) | iArr[bArr[i + 3] & 255]) >= 0) {
                            bArr2[i6 + 2] = (byte) i5;
                            bArr2[i6 + 1] = (byte) (i5 >> 8);
                            bArr2[i6] = (byte) (i5 >> 16);
                            i6 += 3;
                            i = i7;
                        }
                    }
                    if (i >= i3) {
                        if (!z) {
                            switch (i4) {
                                case 0:
                                default:
                                    this.state = i4;
                                    break;
                                case 1:
                                    this.state = 6;
                                    break;
                                case 2:
                                    bArr2[i6] = (byte) (i5 >> 4);
                                    i6++;
                                    this.state = i4;
                                    break;
                                case 3:
                                    int i8 = i6 + 1;
                                    bArr2[i6] = (byte) (i5 >> 10);
                                    i6 = i8 + 1;
                                    bArr2[i8] = (byte) (i5 >> 2);
                                    this.state = i4;
                                    break;
                                case 4:
                                    this.state = 6;
                                    break;
                            }
                            return false;
                        }
                        this.state = i4;
                        this.value = i5;
                        this.op = i6;
                        return true;
                    }
                }
                int i9 = i + 1;
                int i10 = iArr[bArr[i] & 255];
                switch (i4) {
                    case 0:
                        if (i10 >= 0) {
                            i4++;
                            i5 = i10;
                        } else if (i10 != -1) {
                            this.state = 6;
                            break;
                        }
                        i = i9;
                        break;
                    case 1:
                        if (i10 < 0) {
                            if (i10 == -1) {
                                i = i9;
                            } else {
                                this.state = 6;
                            }
                        }
                        i5 = (i5 << 6) | i10;
                        i4++;
                        i = i9;
                        break;
                    case 2:
                        if (i10 < 0) {
                            if (i10 == -2) {
                                bArr2[i6] = (byte) (i5 >> 4);
                                i6++;
                                i4 = 4;
                            } else if (i10 != -1) {
                                this.state = 6;
                                break;
                            }
                            i = i9;
                        }
                        i5 = (i5 << 6) | i10;
                        i4++;
                        i = i9;
                        break;
                    case 3:
                        if (i10 >= 0) {
                            i5 = (i5 << 6) | i10;
                            bArr2[i6 + 2] = (byte) i5;
                            bArr2[i6 + 1] = (byte) (i5 >> 8);
                            bArr2[i6] = (byte) (i5 >> 16);
                            i6 += 3;
                            i4 = 0;
                        } else if (i10 == -2) {
                            bArr2[i6 + 1] = (byte) (i5 >> 2);
                            bArr2[i6] = (byte) (i5 >> 10);
                            i6 += 2;
                            i4 = 5;
                        } else if (i10 != -1) {
                            this.state = 6;
                            break;
                        }
                        i = i9;
                        break;
                    case 4:
                        if (i10 == -2) {
                            i4++;
                        } else if (i10 != -1) {
                            this.state = 6;
                            break;
                        }
                        i = i9;
                        break;
                    case 5:
                        if (i10 == -1) {
                            i = i9;
                        } else {
                            this.state = 6;
                        }
                        break;
                    default:
                        i = i9;
                        break;
                }
                return false;
            }
            if (!z) {
            }
            this.op = i6;
            return true;
        }
    }

    static class c extends a {
        private final byte[] c;
        private int count;
        private final byte[] d;
        public final boolean do_cr;
        public final boolean do_newline;
        public final boolean do_padding;
        int e;
        static final /* synthetic */ boolean f = !b.class.desiredAssertionStatus();
        private static final byte[] a = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        private static final byte[] b = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};

        public c(int i, byte[] bArr) {
            this.output = bArr;
            this.do_padding = (i & 1) == 0;
            this.do_newline = (i & 2) == 0;
            this.do_cr = (i & 4) != 0;
            this.d = (i & 8) == 0 ? a : b;
            this.c = new byte[2];
            this.e = 0;
            this.count = this.do_newline ? 19 : -1;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public boolean process(byte[] bArr, int i, int i2, boolean z) {
            int i3;
            int i4;
            int i5;
            int i6;
            int i7;
            int i8;
            byte b2;
            byte b3;
            int i9;
            int i10;
            byte b4;
            int i11;
            int i12;
            int i13;
            byte[] bArr2 = this.d;
            byte[] bArr3 = this.output;
            int i14 = this.count;
            int i15 = i2 + i;
            int i16 = 0;
            switch (this.e) {
                case 0:
                default:
                    i3 = i;
                    i4 = -1;
                    break;
                case 1:
                    if (i + 2 <= i15) {
                        int i17 = i + 1;
                        i3 = i17 + 1;
                        i4 = ((bArr[i] & 255) << 8) | ((this.c[0] & 255) << 16) | (bArr[i17] & 255);
                        this.e = 0;
                    }
                    i3 = i;
                    i4 = -1;
                    break;
                case 2:
                    i3 = i + 1;
                    if (i3 <= i15) {
                        i4 = (bArr[i] & 255) | ((this.c[0] & 255) << 16) | ((this.c[1] & 255) << 8);
                        this.e = 0;
                    }
                    i3 = i;
                    i4 = -1;
                    break;
            }
            if (i4 != -1) {
                bArr3[0] = bArr2[(i4 >> 18) & 63];
                bArr3[1] = bArr2[(i4 >> 12) & 63];
                bArr3[2] = bArr2[(i4 >> 6) & 63];
                bArr3[3] = bArr2[i4 & 63];
                int i18 = i14 - 1;
                if (i18 == 0) {
                    if (this.do_cr) {
                        i13 = 5;
                        bArr3[4] = 13;
                    } else {
                        i13 = 4;
                    }
                    i6 = i13 + 1;
                    bArr3[i13] = 10;
                    i5 = 19;
                } else {
                    i5 = i18;
                    i6 = 4;
                }
            } else {
                i5 = i14;
                i6 = 0;
            }
            while (true) {
                int i19 = i3 + 3;
                if (i19 > i15) {
                    if (z) {
                        if (i3 - this.e == i15 - 1) {
                            if (this.e > 0) {
                                b4 = this.c[0];
                                i16 = 1;
                            } else {
                                b4 = bArr[i3];
                                i3++;
                            }
                            int i20 = (b4 & 255) << 4;
                            this.e -= i16;
                            int i21 = i6 + 1;
                            bArr3[i6] = bArr2[(i20 >> 6) & 63];
                            i6 = i21 + 1;
                            bArr3[i21] = bArr2[i20 & 63];
                            if (this.do_padding) {
                                int i22 = i6 + 1;
                                bArr3[i6] = 61;
                                i6 = i22 + 1;
                                bArr3[i22] = 61;
                            }
                            if (this.do_newline) {
                                if (this.do_cr) {
                                    i11 = i6 + 1;
                                    bArr3[i6] = 13;
                                } else {
                                    i11 = i6;
                                }
                                i6 = i11 + 1;
                                bArr3[i11] = 10;
                            }
                        } else if (i3 - this.e == i15 - 2) {
                            if (this.e > 1) {
                                i16 = 1;
                                i8 = i3;
                                b2 = this.c[0];
                            } else {
                                i8 = i3 + 1;
                                b2 = bArr[i3];
                            }
                            int i23 = (b2 & 255) << 10;
                            if (this.e > 0) {
                                i9 = i16 + 1;
                                b3 = this.c[i16];
                            } else {
                                b3 = bArr[i8];
                                i8++;
                                i9 = i16;
                            }
                            int i24 = ((b3 & 255) << 2) | i23;
                            this.e -= i9;
                            int i25 = i6 + 1;
                            bArr3[i6] = bArr2[(i24 >> 12) & 63];
                            int i26 = i25 + 1;
                            bArr3[i25] = bArr2[(i24 >> 6) & 63];
                            int i27 = i26 + 1;
                            bArr3[i26] = bArr2[i24 & 63];
                            if (this.do_padding) {
                                i10 = i27 + 1;
                                bArr3[i27] = 61;
                            } else {
                                i10 = i27;
                            }
                            if (this.do_newline) {
                                if (this.do_cr) {
                                    bArr3[i10] = 13;
                                    i10++;
                                }
                                bArr3[i10] = 10;
                                i10++;
                            }
                            i6 = i10;
                            i3 = i8;
                        } else if (this.do_newline && i6 > 0 && i5 != 19) {
                            if (this.do_cr) {
                                i7 = i6 + 1;
                                bArr3[i6] = 13;
                            } else {
                                i7 = i6;
                            }
                            bArr3[i7] = 10;
                            i6 = i7 + 1;
                        }
                        if (!f && this.e != 0) {
                            throw new AssertionError();
                        }
                        if (!f && i3 != i15) {
                            throw new AssertionError();
                        }
                    } else if (i3 == i15 - 1) {
                        byte[] bArr4 = this.c;
                        int i28 = this.e;
                        this.e = i28 + 1;
                        bArr4[i28] = bArr[i3];
                    } else if (i3 == i15 - 2) {
                        byte[] bArr5 = this.c;
                        int i29 = this.e;
                        this.e = i29 + 1;
                        bArr5[i29] = bArr[i3];
                        byte[] bArr6 = this.c;
                        int i30 = this.e;
                        this.e = i30 + 1;
                        bArr6[i30] = bArr[i3 + 1];
                    }
                    this.op = i6;
                    this.count = i5;
                    return true;
                }
                int i31 = (bArr[i3 + 2] & 255) | ((bArr[i3] & 255) << 16) | ((bArr[i3 + 1] & 255) << 8);
                bArr3[i6] = bArr2[(i31 >> 18) & 63];
                bArr3[i6 + 1] = bArr2[(i31 >> 12) & 63];
                bArr3[i6 + 2] = bArr2[(i31 >> 6) & 63];
                bArr3[i6 + 3] = bArr2[i31 & 63];
                i6 += 4;
                i5--;
                if (i5 == 0) {
                    if (this.do_cr) {
                        i12 = i6 + 1;
                        bArr3[i6] = 13;
                    } else {
                        i12 = i6;
                    }
                    i6 = i12 + 1;
                    bArr3[i12] = 10;
                    i3 = i19;
                    i5 = 19;
                } else {
                    i3 = i19;
                }
            }
        }
    }

    private b() {
    }

    public static byte[] decode(String str, int i) {
        return decode(str.getBytes(), i);
    }

    public static byte[] decode(byte[] bArr, int i) {
        return decode(bArr, 0, bArr.length, i);
    }

    public static byte[] decode(byte[] bArr, int i, int i2, int i3) {
        C0052b c0052b = new C0052b(i3, new byte[(i2 * 3) / 4]);
        if (!c0052b.process(bArr, i, i2, true)) {
            throw new IllegalArgumentException("bad base-64");
        }
        if (c0052b.op == c0052b.output.length) {
            return c0052b.output;
        }
        byte[] bArr2 = new byte[c0052b.op];
        System.arraycopy(c0052b.output, 0, bArr2, 0, c0052b.op);
        return bArr2;
    }

    public static byte[] encode(byte[] bArr, int i) {
        return encode(bArr, 0, bArr.length, i);
    }

    @SuppressLint({"Assert"})
    public static byte[] encode(byte[] bArr, int i, int i2, int i3) {
        c cVar = new c(i3, null);
        int i4 = (i2 / 3) * 4;
        if (!cVar.do_padding) {
            switch (i2 % 3) {
                case 1:
                    i4 += 2;
                    break;
                case 2:
                    i4 += 3;
                    break;
            }
        } else if (i2 % 3 > 0) {
            i4 += 4;
        }
        if (cVar.do_newline && i2 > 0) {
            i4 += (((i2 - 1) / 57) + 1) * (cVar.do_cr ? 2 : 1);
        }
        cVar.output = new byte[i4];
        cVar.process(bArr, i, i2, true);
        if (a || cVar.op == i4) {
            return cVar.output;
        }
        throw new AssertionError();
    }

    public static String encodeToString(byte[] bArr, int i) {
        try {
            return new String(encode(bArr, i), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }
}
