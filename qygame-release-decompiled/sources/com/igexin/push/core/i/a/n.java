package com.igexin.push.core.i.a;

import android.graphics.Bitmap;
import android.util.Log;
import com.igexin.push.core.i.a.d;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class n implements d {
    private static final String f = "n";
    private static final int g = 4096;
    private static final int h = -1;
    private static final int i = -1;
    private static final int j = 4;
    private static final int k = 255;
    private static final int l = 0;
    private boolean A;
    private int B;
    private int C;
    private int D;
    private int E;
    private Boolean F;
    private Bitmap.Config G;
    private int[] m;
    private final int[] n;
    private final d.a o;
    private ByteBuffer p;
    private byte[] q;
    private j r;
    private short[] s;
    private byte[] t;
    private byte[] u;
    private byte[] v;
    private int[] w;
    private int x;
    private i y;
    private Bitmap z;

    private n(d.a aVar) {
        this.n = new int[256];
        this.G = Bitmap.Config.ARGB_8888;
        this.o = aVar;
        this.y = new i();
    }

    private n(d.a aVar, i iVar, ByteBuffer byteBuffer) {
        this(aVar, iVar, byteBuffer, 1);
    }

    public n(d.a aVar, i iVar, ByteBuffer byteBuffer, int i2) {
        this(aVar);
        a(iVar, byteBuffer, i2);
    }

    private int a(int i2, int i3, int i4) {
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        for (int i10 = i2; i10 < this.C + i2 && i10 < this.v.length && i10 < i3; i10++) {
            int i11 = this.m[this.v[i10] & 255];
            if (i11 != 0) {
                i5 += (i11 >> 24) & 255;
                i6 += (i11 >> 16) & 255;
                i7 += (i11 >> 8) & 255;
                i8 += i11 & 255;
                i9++;
            }
        }
        int i12 = i2 + i4;
        for (int i13 = i12; i13 < this.C + i12 && i13 < this.v.length && i13 < i3; i13++) {
            int i14 = this.m[this.v[i13] & 255];
            if (i14 != 0) {
                i5 += (i14 >> 24) & 255;
                i6 += (i14 >> 16) & 255;
                i7 += (i14 >> 8) & 255;
                i8 += i14 & 255;
                i9++;
            }
        }
        if (i9 == 0) {
            return 0;
        }
        return ((i5 / i9) << 24) | ((i6 / i9) << 16) | ((i7 / i9) << 8) | (i8 / i9);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0049  */
    /* JADX WARN: Type inference failed for: r9v25 */
    /* JADX WARN: Type inference failed for: r9v26 */
    /* JADX WARN: Type inference failed for: r9v29, types: [short] */
    /* JADX WARN: Type inference failed for: r9v32 */
    /* JADX WARN: Type inference failed for: r9v42 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Bitmap a(g gVar, g gVar2) {
        int i2;
        int i3;
        byte b;
        int i4;
        int[] iArr;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        short s;
        int[] iArr2 = this.w;
        byte b2 = 0;
        if (gVar2 == null) {
            if (this.z != null) {
                this.o.a(this.z);
            }
            this.z = null;
            Arrays.fill(iArr2, 0);
        }
        char c = 3;
        if (gVar2 != null && gVar2.k == 3 && this.z == null) {
            Arrays.fill(iArr2, 0);
        }
        if (gVar2 != null && gVar2.k > 0) {
            if (gVar2.k == 2) {
                if (!gVar.j) {
                    int i11 = this.y.n;
                    if (gVar.o != null && this.y.l == gVar.l) {
                        i11 = 0;
                    }
                    int i12 = gVar2.h / this.C;
                    int i13 = gVar2.f / this.C;
                    int i14 = gVar2.g / this.C;
                    int i15 = (i13 * this.E) + (gVar2.e / this.C);
                    int i16 = (i12 * this.E) + i15;
                    while (i15 < i16) {
                        int i17 = i15 + i14;
                        for (int i18 = i15; i18 < i17; i18++) {
                            iArr2[i18] = i11;
                        }
                        i15 += this.E;
                    }
                }
            } else if (gVar2.k == 3 && this.z != null) {
                this.z.getPixels(iArr2, 0, this.E, 0, 0, this.E, this.D);
            }
        }
        if (gVar != null) {
            this.p.position(gVar.n);
        }
        if (gVar == null) {
            i2 = this.y.h;
            i3 = this.y.i;
        } else {
            i2 = gVar.g;
            i3 = gVar.h;
        }
        int i19 = i2 * i3;
        if (this.v == null || this.v.length < i19) {
            this.v = this.o.a(i19);
        }
        byte[] bArr = this.v;
        if (this.s == null) {
            this.s = new short[4096];
        }
        short[] sArr = this.s;
        if (this.t == null) {
            this.t = new byte[4096];
        }
        byte[] bArr2 = this.t;
        if (this.u == null) {
            this.u = new byte[4097];
        }
        byte[] bArr3 = this.u;
        int iQ = q();
        int i20 = 1 << iQ;
        int i21 = i20 + 1;
        int i22 = i20 + 2;
        int i23 = iQ + 1;
        int i24 = (1 << i23) - 1;
        for (int i25 = 0; i25 < i20; i25++) {
            sArr[i25] = 0;
            bArr2[i25] = (byte) i25;
        }
        byte[] bArr4 = this.q;
        int i26 = i23;
        int i27 = 0;
        int i28 = 0;
        int i29 = 0;
        int i30 = 0;
        int i31 = 0;
        int i32 = 0;
        int i33 = 0;
        int i34 = 0;
        int i35 = i22;
        int i36 = i24;
        int i37 = -1;
        while (true) {
            if (i27 >= i19) {
                b = b2;
                i4 = i29;
                break;
            }
            if (i28 == 0) {
                int iQ2 = q();
                if (iQ2 <= 0) {
                    i8 = i23;
                    i9 = i27;
                } else {
                    i8 = i23;
                    i9 = i27;
                    this.p.get(this.q, 0, Math.min(iQ2, this.p.remaining()));
                }
                if (iQ2 <= 0) {
                    this.B = 3;
                    i4 = i29;
                    b = 0;
                    break;
                }
                i28 = iQ2;
                i32 = 0;
            } else {
                i8 = i23;
                i9 = i27;
            }
            i31 += (bArr4[i32] & 255) << i30;
            i32++;
            i28--;
            int i38 = i30 + 8;
            int i39 = i35;
            int i40 = i26;
            int i41 = i37;
            int i42 = i33;
            i27 = i9;
            while (true) {
                if (i38 < i40) {
                    i35 = i39;
                    i37 = i41;
                    i33 = i42 == true ? 1 : 0 ? 1 : 0;
                    i30 = i38;
                    i26 = i40;
                    i23 = i8;
                    bArr4 = bArr4;
                    break;
                }
                int i43 = i31 & i36;
                i31 >>= i40;
                i38 -= i40;
                if (i43 != i20) {
                    if (i43 == i21) {
                        i35 = i39;
                        i37 = i41;
                        i33 = i42 == true ? 1 : 0 ? 1 : 0;
                        i26 = i40;
                        i23 = i8;
                        bArr4 = bArr4;
                        i30 = i38;
                        break;
                    }
                    byte[] bArr5 = bArr4;
                    if (i41 == -1) {
                        bArr[i29] = bArr2[i43 == true ? 1 : 0];
                        i29++;
                        i27++;
                        i41 = i43 == true ? 1 : 0;
                        i42 = i41;
                        bArr4 = bArr5;
                    } else {
                        int i44 = i39;
                        if (i43 >= i44) {
                            i10 = i43 == true ? 1 : 0;
                            bArr3[i34] = i42 == true ? 1 : 0 ? (byte) 1 : (byte) 0;
                            i34++;
                            s = i41;
                        } else {
                            i10 = i43 == true ? 1 : 0;
                            s = i43;
                        }
                        while (s >= i20) {
                            bArr3[i34] = bArr2[s];
                            i34++;
                            s = sArr[s];
                        }
                        int i45 = bArr2[s] & 255;
                        byte b3 = (byte) i45;
                        bArr[i29] = b3;
                        while (true) {
                            i29++;
                            i27++;
                            if (i34 <= 0) {
                                break;
                            }
                            i34--;
                            bArr[i29] = bArr3[i34];
                        }
                        byte[] bArr6 = bArr3;
                        if (i44 < 4096) {
                            sArr[i44] = (short) i41;
                            bArr2[i44] = b3;
                            i44++;
                            if ((i44 & i36) == 0 && i44 < 4096) {
                                i40++;
                                i36 += i44;
                            }
                        }
                        i39 = i44;
                        i42 = i45;
                        bArr4 = bArr5;
                        i41 = i10;
                        i38 = i38;
                        bArr3 = bArr6;
                    }
                } else {
                    i39 = i22;
                    i36 = i24;
                    i40 = i8;
                    i41 = -1;
                }
            }
            b2 = 0;
            c = 3;
        }
        Arrays.fill(bArr, i4, i19, b);
        if (gVar.i || this.C != 1) {
            int[] iArr3 = this.w;
            int i46 = gVar.h / this.C;
            int i47 = gVar.f / this.C;
            int i48 = gVar.g / this.C;
            int i49 = gVar.e / this.C;
            boolean z = this.x == 0;
            int i50 = this.C;
            int i51 = this.E;
            int i52 = this.D;
            byte[] bArr7 = this.v;
            int[] iArr4 = this.m;
            Boolean bool = this.F;
            int i53 = 8;
            int i54 = 0;
            int i55 = 0;
            int i56 = 1;
            while (i54 < i46) {
                int[] iArr5 = iArr2;
                if (gVar.i) {
                    if (i55 >= i46) {
                        i56++;
                        switch (i56) {
                            case 2:
                                i55 = 4;
                                break;
                            case 3:
                                i53 = 4;
                                i55 = 2;
                                break;
                            case 4:
                                i55 = 1;
                                i53 = 2;
                                break;
                        }
                    }
                    i5 = i55 + i53;
                } else {
                    i5 = i55;
                    i55 = i54;
                }
                int i57 = i55 + i47;
                int i58 = i46;
                boolean z2 = i50 == 1;
                if (i57 < i52) {
                    int i59 = i57 * i51;
                    int i60 = i59 + i49;
                    i6 = i47;
                    int i61 = i60 + i48;
                    int i62 = i59 + i51;
                    if (i62 >= i61) {
                        i62 = i61;
                    }
                    i7 = i48;
                    int i63 = i54 * i50 * gVar.g;
                    if (z2) {
                        for (int i64 = i60; i64 < i62; i64++) {
                            int i65 = iArr4[bArr7[i63] & 255];
                            if (i65 != 0) {
                                iArr3[i64] = i65;
                            } else if (z && bool == null) {
                                bool = Boolean.TRUE;
                            }
                            i63 += i50;
                        }
                    } else {
                        int i66 = ((i62 - i60) * i50) + i63;
                        int i67 = i63;
                        int i68 = i60;
                        while (i68 < i62) {
                            int i69 = i49;
                            int i70 = gVar.g;
                            int i71 = i51;
                            int i72 = i5;
                            int i73 = 0;
                            int i74 = 0;
                            int i75 = 0;
                            int i76 = 0;
                            int i77 = 0;
                            int i78 = i67;
                            while (i78 < this.C + i67 && i78 < this.v.length && i78 < i66) {
                                int i79 = i52;
                                int i80 = this.m[this.v[i78] & 255];
                                if (i80 != 0) {
                                    i73 += (i80 >> 24) & 255;
                                    i74 += (i80 >> 16) & 255;
                                    i75 += (i80 >> 8) & 255;
                                    i76 += i80 & 255;
                                    i77++;
                                }
                                i78++;
                                i52 = i79;
                            }
                            int i81 = i52;
                            int i82 = i70 + i67;
                            for (int i83 = i82; i83 < this.C + i82 && i83 < this.v.length && i83 < i66; i83++) {
                                int i84 = this.m[this.v[i83] & 255];
                                if (i84 != 0) {
                                    i73 += (i84 >> 24) & 255;
                                    i74 += (i84 >> 16) & 255;
                                    i75 += (i84 >> 8) & 255;
                                    i76 += i84 & 255;
                                    i77++;
                                }
                            }
                            int i85 = i77 == 0 ? 0 : ((i73 / i77) << 24) | ((i74 / i77) << 16) | ((i75 / i77) << 8) | (i76 / i77);
                            if (i85 != 0) {
                                iArr3[i68] = i85;
                            } else if (z && bool == null) {
                                bool = Boolean.TRUE;
                            }
                            i67 += i50;
                            i68++;
                            i49 = i69;
                            i51 = i71;
                            i5 = i72;
                            i52 = i81;
                        }
                    }
                } else {
                    i6 = i47;
                    i7 = i48;
                }
                int i86 = i5;
                i54++;
                iArr2 = iArr5;
                i46 = i58;
                i47 = i6;
                i48 = i7;
                i49 = i49;
                i51 = i51;
                i55 = i86;
                i52 = i52;
            }
            iArr = iArr2;
            if (this.F == null) {
                this.F = Boolean.valueOf(bool == null ? false : bool.booleanValue());
            }
        } else {
            int[] iArr6 = this.w;
            int i87 = gVar.h;
            int i88 = gVar.f;
            int i89 = gVar.g;
            int i90 = gVar.e;
            byte b4 = this.x == 0 ? (byte) 1 : b;
            int i91 = this.E;
            byte[] bArr8 = this.v;
            int[] iArr7 = this.m;
            int i92 = b;
            byte b5 = -1;
            while (i92 < i87) {
                int i93 = (i92 + i88) * i91;
                int i94 = i93 + i90;
                int i95 = i94 + i89;
                int i96 = i93 + i91;
                if (i96 < i95) {
                    i95 = i96;
                }
                int i97 = gVar.g * i92;
                int i98 = i87;
                byte b6 = b5;
                int i99 = i94;
                while (i99 < i95) {
                    int i100 = i88;
                    byte b7 = bArr8[i97];
                    int i101 = i89;
                    int i102 = b7 & 255;
                    if (i102 != b6) {
                        int i103 = iArr7[i102];
                        if (i103 != 0) {
                            iArr6[i99] = i103;
                        } else {
                            b6 = b7;
                        }
                    }
                    i97++;
                    i99++;
                    i88 = i100;
                    i89 = i101;
                }
                i92++;
                b5 = b6;
                i87 = i98;
            }
            this.F = Boolean.valueOf((this.F != null && this.F.booleanValue()) || !(this.F != null || b4 == 0 || b5 == -1));
            iArr = iArr2;
        }
        if (this.A && (gVar.k == 0 || gVar.k == 1)) {
            if (this.z == null) {
                this.z = s();
            }
            this.z.setPixels(iArr, 0, this.E, 0, 0, this.E, this.D);
        }
        Bitmap bitmapS = s();
        bitmapS.setPixels(iArr, 0, this.E, 0, 0, this.E, this.D);
        return bitmapS;
    }

    private void a(g gVar) {
        g gVar2 = gVar;
        int[] iArr = this.w;
        int i2 = gVar2.h;
        int i3 = gVar2.f;
        int i4 = gVar2.g;
        int i5 = gVar2.e;
        boolean z = this.x == 0;
        int i6 = this.E;
        byte[] bArr = this.v;
        int[] iArr2 = this.m;
        int i7 = 0;
        byte b = -1;
        while (i7 < i2) {
            int i8 = (i7 + i3) * i6;
            int i9 = i8 + i5;
            int i10 = i9 + i4;
            int i11 = i8 + i6;
            if (i11 < i10) {
                i10 = i11;
            }
            byte b2 = b;
            int i12 = gVar2.g * i7;
            int i13 = i9;
            while (i13 < i10) {
                byte b3 = bArr[i12];
                int i14 = i2;
                int i15 = b3 & 255;
                if (i15 != b2) {
                    int i16 = iArr2[i15];
                    if (i16 != 0) {
                        iArr[i13] = i16;
                    } else {
                        b2 = b3;
                    }
                }
                i12++;
                i13++;
                i2 = i14;
            }
            i7++;
            b = b2;
            gVar2 = gVar;
        }
        this.F = Boolean.valueOf((this.F != null && this.F.booleanValue()) || (this.F == null && z && b != -1));
    }

    private void b(g gVar) {
        int i2;
        int i3;
        int i4;
        g gVar2 = gVar;
        int[] iArr = this.w;
        int i5 = gVar2.h / this.C;
        int i6 = gVar2.f / this.C;
        int i7 = gVar2.g / this.C;
        int i8 = gVar2.e / this.C;
        boolean z = this.x == 0;
        int i9 = this.C;
        int i10 = this.E;
        int i11 = this.D;
        byte[] bArr = this.v;
        int[] iArr2 = this.m;
        Boolean bool = this.F;
        int i12 = 8;
        int i13 = 0;
        int i14 = 0;
        int i15 = 1;
        while (i14 < i5) {
            if (gVar2.i) {
                if (i13 >= i5) {
                    i15++;
                    switch (i15) {
                        case 2:
                            i13 = 4;
                            break;
                        case 3:
                            i13 = 2;
                            i12 = 4;
                            break;
                        case 4:
                            i12 = 2;
                            i13 = 1;
                            break;
                    }
                }
                i2 = i13 + i12;
            } else {
                i2 = i13;
                i13 = i14;
            }
            int i16 = i13 + i6;
            int i17 = i5;
            boolean z2 = i9 == 1;
            if (i16 < i11) {
                int i18 = i16 * i10;
                int i19 = i18 + i8;
                int i20 = i19 + i7;
                int i21 = i18 + i10;
                if (i21 < i20) {
                    i20 = i21;
                }
                i3 = i6;
                int i22 = i14 * i9 * gVar2.g;
                if (z2) {
                    int i23 = i19;
                    while (i23 < i20) {
                        int i24 = i7;
                        int i25 = iArr2[bArr[i22] & 255];
                        if (i25 != 0) {
                            iArr[i23] = i25;
                        } else if (z && bool == null) {
                            bool = Boolean.TRUE;
                        }
                        i22 += i9;
                        i23++;
                        i7 = i24;
                    }
                } else {
                    i4 = i7;
                    int i26 = ((i20 - i19) * i9) + i22;
                    int i27 = i19;
                    while (i27 < i20) {
                        int i28 = i20;
                        int i29 = gVar2.g;
                        int i30 = i8;
                        int i31 = i22;
                        int i32 = 0;
                        int i33 = 0;
                        int i34 = 0;
                        int i35 = 0;
                        int i36 = 0;
                        while (i31 < this.C + i22 && i31 < this.v.length && i31 < i26) {
                            int i37 = i2;
                            int i38 = this.m[this.v[i31] & 255];
                            if (i38 != 0) {
                                i32 += (i38 >> 24) & 255;
                                i33 += (i38 >> 16) & 255;
                                i34 += (i38 >> 8) & 255;
                                i35 += i38 & 255;
                                i36++;
                            }
                            i31++;
                            i2 = i37;
                        }
                        int i39 = i2;
                        int i40 = i29 + i22;
                        for (int i41 = i40; i41 < this.C + i40 && i41 < this.v.length && i41 < i26; i41++) {
                            int i42 = this.m[this.v[i41] & 255];
                            if (i42 != 0) {
                                i32 += (i42 >> 24) & 255;
                                i33 += (i42 >> 16) & 255;
                                i34 += (i42 >> 8) & 255;
                                i35 += i42 & 255;
                                i36++;
                            }
                        }
                        int i43 = i36 == 0 ? 0 : ((i32 / i36) << 24) | ((i33 / i36) << 16) | ((i34 / i36) << 8) | (i35 / i36);
                        if (i43 != 0) {
                            iArr[i27] = i43;
                        } else if (z && bool == null) {
                            bool = Boolean.TRUE;
                        }
                        i22 += i9;
                        i27++;
                        i20 = i28;
                        i8 = i30;
                        i2 = i39;
                        gVar2 = gVar;
                    }
                    i14++;
                    i5 = i17;
                    i6 = i3;
                    i7 = i4;
                    i8 = i8;
                    i13 = i2;
                    gVar2 = gVar;
                }
            } else {
                i3 = i6;
            }
            i4 = i7;
            i14++;
            i5 = i17;
            i6 = i3;
            i7 = i4;
            i8 = i8;
            i13 = i2;
            gVar2 = gVar;
        }
        if (this.F == null) {
            this.F = Boolean.valueOf(bool == null ? false : bool.booleanValue());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v14, types: [short] */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    private void c(g gVar) {
        byte b;
        int i2;
        int i3;
        int i4;
        int iQ;
        short s;
        n nVar = this;
        if (gVar != null) {
            nVar.p.position(gVar.n);
        }
        int i5 = gVar == null ? nVar.y.h * nVar.y.i : gVar.h * gVar.g;
        if (nVar.v == null || nVar.v.length < i5) {
            nVar.v = nVar.o.a(i5);
        }
        byte[] bArr = nVar.v;
        if (nVar.s == null) {
            nVar.s = new short[4096];
        }
        short[] sArr = nVar.s;
        if (nVar.t == null) {
            nVar.t = new byte[4096];
        }
        byte[] bArr2 = nVar.t;
        if (nVar.u == null) {
            nVar.u = new byte[4097];
        }
        byte[] bArr3 = nVar.u;
        int iQ2 = q();
        int i6 = 1 << iQ2;
        int i7 = i6 + 1;
        int i8 = i6 + 2;
        int i9 = iQ2 + 1;
        int i10 = (1 << i9) - 1;
        byte b2 = 0;
        for (int i11 = 0; i11 < i6; i11++) {
            sArr[i11] = 0;
            bArr2[i11] = (byte) i11;
        }
        byte[] bArr4 = nVar.q;
        int i12 = i9;
        int i13 = i8;
        int i14 = i10;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        int i20 = 0;
        int i21 = 0;
        int i22 = 0;
        int i23 = -1;
        while (true) {
            if (i15 >= i5) {
                b = b2;
                i2 = i17;
                break;
            }
            if (i16 == 0) {
                iQ = q();
                if (iQ <= 0) {
                    i3 = i15;
                    i4 = i9;
                } else {
                    i3 = i15;
                    i4 = i9;
                    nVar.p.get(nVar.q, 0, Math.min(iQ, nVar.p.remaining()));
                }
                if (iQ <= 0) {
                    nVar.B = 3;
                    i2 = i17;
                    b = 0;
                    break;
                }
                i20 = 0;
            } else {
                i3 = i15;
                i4 = i9;
                iQ = i16;
            }
            i19 += (bArr4[i20] & 255) << i18;
            i20++;
            i16 = iQ - 1;
            int i24 = i18 + 8;
            int i25 = i23;
            int i26 = i21;
            int i27 = i13;
            int i28 = i12;
            while (i24 >= i28) {
                int i29 = i19 & i14;
                i19 >>= i28;
                i24 -= i28;
                if (i29 == i6) {
                    i27 = i8;
                    i14 = i10;
                    i28 = i4;
                    i25 = -1;
                } else if (i29 != i7) {
                    if (i25 == -1) {
                        bArr[i17] = bArr2[i29];
                        i17++;
                        i3++;
                        i25 = i29;
                        i26 = i25;
                    } else {
                        if (i29 >= i27) {
                            bArr3[i22] = (byte) i26;
                            i22++;
                            s = i25;
                        } else {
                            s = i29;
                        }
                        while (s >= i6) {
                            bArr3[i22] = bArr2[s];
                            i22++;
                            s = sArr[s];
                        }
                        int i30 = bArr2[s] & 255;
                        byte b3 = (byte) i30;
                        bArr[i17] = b3;
                        while (true) {
                            i17++;
                            i3++;
                            if (i22 <= 0) {
                                break;
                            }
                            i22--;
                            bArr[i17] = bArr3[i22];
                        }
                        if (i27 < 4096) {
                            sArr[i27] = (short) i25;
                            bArr2[i27] = b3;
                            i27++;
                            if ((i27 & i14) == 0 && i27 < 4096) {
                                i28++;
                                i14 += i27;
                            }
                        }
                        i25 = i29;
                        i26 = i30;
                    }
                }
            }
            i13 = i27;
            i18 = i24;
            i12 = i28;
            i23 = i25;
            i15 = i3;
            i9 = i4;
            i21 = i26;
            nVar = this;
            b2 = 0;
        }
        Arrays.fill(bArr, i2, i5, b);
    }

    private j p() {
        if (this.r == null) {
            this.r = new j();
        }
        return this.r;
    }

    private int q() {
        return this.p.get() & 255;
    }

    private int r() {
        int iQ = q();
        if (iQ <= 0) {
            return iQ;
        }
        this.p.get(this.q, 0, Math.min(iQ, this.p.remaining()));
        return iQ;
    }

    private Bitmap s() {
        Bitmap bitmapA = this.o.a(this.E, this.D, (this.F == null || this.F.booleanValue()) ? Bitmap.Config.ARGB_8888 : this.G, this.x);
        bitmapA.setHasAlpha(true);
        return bitmapA;
    }

    @Override // com.igexin.push.core.i.a.d
    public final int a() {
        return this.y.h;
    }

    @Override // com.igexin.push.core.i.a.d
    public final int a(int i2) {
        if (i2 < 0 || i2 >= this.y.e) {
            return -1;
        }
        return this.y.g.get(i2).m;
    }

    @Override // com.igexin.push.core.i.a.d
    public final int a(InputStream inputStream, int i2) {
        if (inputStream != null) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(i2 > 0 ? i2 + 4096 : 16384);
                byte[] bArr = new byte[16384];
                while (true) {
                    int i3 = inputStream.read(bArr, 0, 16384);
                    if (i3 == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, i3);
                }
                byteArrayOutputStream.flush();
                a(byteArrayOutputStream.toByteArray());
            } catch (IOException e) {
                Log.w(f, "Error reading data from stream", e);
            }
        } else {
            this.B = 2;
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e2) {
                Log.w(f, "Error closing stream", e2);
            }
        }
        return this.B;
    }

    @Override // com.igexin.push.core.i.a.d
    public final synchronized int a(byte[] bArr) {
        if (this.r == null) {
            this.r = new j();
        }
        j jVar = this.r;
        if (bArr != null) {
            jVar.a(ByteBuffer.wrap(bArr));
        } else {
            jVar.c = null;
            jVar.d.d = 2;
        }
        this.y = jVar.b();
        if (bArr != null) {
            a(this.y, bArr);
        }
        return this.B;
    }

    @Override // com.igexin.push.core.i.a.d
    public final void a(Bitmap.Config config) {
        if (config == Bitmap.Config.ARGB_8888 || config == Bitmap.Config.RGB_565) {
            this.G = config;
            return;
        }
        throw new IllegalArgumentException("Unsupported format: " + config + ", must be one of " + Bitmap.Config.ARGB_8888 + " or " + Bitmap.Config.RGB_565);
    }

    @Override // com.igexin.push.core.i.a.d
    public final synchronized void a(i iVar, ByteBuffer byteBuffer) {
        a(iVar, byteBuffer, 1);
    }

    @Override // com.igexin.push.core.i.a.d
    public final synchronized void a(i iVar, ByteBuffer byteBuffer, int i2) {
        if (i2 <= 0) {
            throw new IllegalArgumentException("Sample size must be >=0, not: ".concat(String.valueOf(i2)));
        }
        int iHighestOneBit = Integer.highestOneBit(i2);
        this.B = 0;
        this.y = iVar;
        this.x = -1;
        this.p = byteBuffer.asReadOnlyBuffer();
        this.p.position(0);
        this.p.order(ByteOrder.LITTLE_ENDIAN);
        this.A = false;
        Iterator<g> it = iVar.g.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            } else if (it.next().k == 3) {
                this.A = true;
                break;
            }
        }
        this.C = iHighestOneBit;
        this.E = iVar.h / iHighestOneBit;
        this.D = iVar.i / iHighestOneBit;
        this.v = this.o.a(iVar.h * iVar.i);
        this.w = this.o.b(this.E * this.D);
    }

    @Override // com.igexin.push.core.i.a.d
    public final synchronized void a(i iVar, byte[] bArr) {
        a(iVar, ByteBuffer.wrap(bArr));
    }

    @Override // com.igexin.push.core.i.a.d
    public final int b() {
        return this.y.i;
    }

    @Override // com.igexin.push.core.i.a.d
    public final ByteBuffer c() {
        return this.p;
    }

    @Override // com.igexin.push.core.i.a.d
    public final int d() {
        return this.B;
    }

    @Override // com.igexin.push.core.i.a.d
    public final void e() {
        this.x = (this.x + 1) % this.y.e;
    }

    @Override // com.igexin.push.core.i.a.d
    public final int f() {
        if (this.y.e <= 0 || this.x < 0) {
            return 0;
        }
        int i2 = this.x;
        if (i2 < 0 || i2 >= this.y.e) {
            return -1;
        }
        return this.y.g.get(i2).m;
    }

    @Override // com.igexin.push.core.i.a.d
    public final int g() {
        return this.y.e;
    }

    @Override // com.igexin.push.core.i.a.d
    public final int h() {
        return this.x;
    }

    @Override // com.igexin.push.core.i.a.d
    public final void i() {
        this.x = -1;
    }

    @Override // com.igexin.push.core.i.a.d
    @Deprecated
    public final int j() {
        if (this.y.o == -1) {
            return 1;
        }
        return this.y.o;
    }

    @Override // com.igexin.push.core.i.a.d
    public final int k() {
        return this.y.o;
    }

    @Override // com.igexin.push.core.i.a.d
    public final int l() {
        if (this.y.o == -1) {
            return 1;
        }
        if (this.y.o == 0) {
            return 0;
        }
        return this.y.o + 1;
    }

    @Override // com.igexin.push.core.i.a.d
    public final int m() {
        return this.p.limit() + this.v.length + (this.w.length * 4);
    }

    @Override // com.igexin.push.core.i.a.d
    public final synchronized Bitmap n() {
        if (this.y.e <= 0 || this.x < 0) {
            com.igexin.c.a.c.a.b(f, "Unable to decode frame, frameCount=" + this.y.e + ", framePointer=" + this.x);
            this.B = 1;
        }
        if (this.B != 1 && this.B != 2) {
            this.B = 0;
            if (this.q == null) {
                this.q = this.o.a(255);
            }
            g gVar = this.y.g.get(this.x);
            int i2 = this.x - 1;
            g gVar2 = i2 >= 0 ? this.y.g.get(i2) : null;
            this.m = gVar.o != null ? gVar.o : this.y.c;
            if (this.m == null) {
                com.igexin.c.a.c.a.b(f, "No valid color table found for frame #" + this.x);
                this.B = 1;
                return null;
            }
            if (gVar.j) {
                System.arraycopy(this.m, 0, this.n, 0, this.m.length);
                this.m = this.n;
                this.m[gVar.l] = 0;
                if (gVar.k == 2 && this.x == 0) {
                    this.F = Boolean.TRUE;
                }
            }
            return a(gVar, gVar2);
        }
        com.igexin.c.a.c.a.b(f, "Unable to decode frame, status=" + this.B);
        return null;
    }

    @Override // com.igexin.push.core.i.a.d
    public final void o() {
        this.y = null;
        if (this.z != null) {
            this.o.a(this.z);
        }
        this.z = null;
        this.p = null;
        this.F = null;
        this.o.a();
    }
}
