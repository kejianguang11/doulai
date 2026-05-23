package com.igexin.push.core.i.a;

import androidx.appcompat.widget.ActivityChooserView;
import androidx.core.view.ViewCompat;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class j {
    static final int a = 2;
    static final int b = 10;
    private static final String e = "GifHeaderParser";
    private static final int f = 255;
    private static final int g = 44;
    private static final int h = 33;
    private static final int i = 59;
    private static final int j = 249;
    private static final int k = 255;
    private static final int l = 254;
    private static final int m = 1;
    private static final int n = 28;
    private static final int o = 2;
    private static final int p = 1;
    private static final int q = 128;
    private static final int r = 64;
    private static final int s = 7;
    private static final int t = 128;
    private static final int u = 7;
    private static final int v = 256;
    ByteBuffer c;
    i d;
    private final byte[] w = new byte[256];
    private int x = 0;

    private j a(byte[] bArr) {
        if (bArr != null) {
            a(ByteBuffer.wrap(bArr));
        } else {
            this.c = null;
            this.d.d = 2;
        }
        return this;
    }

    private void a(int i2) {
        boolean z = false;
        while (!z && !p() && this.d.e <= i2) {
            int iN = n();
            if (iN == 33) {
                int iN2 = n();
                if (iN2 != 1) {
                    if (iN2 != j) {
                        switch (iN2) {
                            case 255:
                                m();
                                StringBuilder sb = new StringBuilder();
                                for (int i3 = 0; i3 < 11; i3++) {
                                    sb.append((char) this.w[i3]);
                                }
                                if (sb.toString().equals("NETSCAPE2.0")) {
                                    h();
                                }
                                break;
                        }
                    } else {
                        this.d.f = new g();
                        f();
                    }
                }
                l();
            } else if (iN == 44) {
                if (this.d.f == null) {
                    this.d.f = new g();
                }
                g();
            } else if (iN != 59) {
                this.d.d = 1;
            } else {
                z = true;
            }
        }
    }

    private int[] b(int i2) {
        int[] iArr;
        byte[] bArr = new byte[i2 * 3];
        try {
            this.c.get(bArr);
            iArr = new int[256];
            int i3 = 0;
            int i4 = 0;
            while (i3 < i2) {
                int i5 = i4 + 1;
                try {
                    int i6 = i5 + 1;
                    int i7 = i6 + 1;
                    int i8 = i3 + 1;
                    iArr[i3] = ((bArr[i4] & 255) << 16) | ViewCompat.MEASURED_STATE_MASK | ((bArr[i5] & 255) << 8) | (bArr[i6] & 255);
                    i4 = i7;
                    i3 = i8;
                } catch (BufferUnderflowException e2) {
                    e = e2;
                    com.igexin.c.a.c.a.b(e, "Format Error Reading Color Table " + e.getMessage());
                    this.d.d = 1;
                    return iArr;
                }
            }
        } catch (BufferUnderflowException e3) {
            e = e3;
            iArr = null;
        }
        return iArr;
    }

    private void c() {
        this.c = null;
        Arrays.fill(this.w, (byte) 0);
        this.d = new i();
        this.x = 0;
    }

    private boolean d() {
        i();
        if (!p()) {
            a(2);
        }
        return this.d.e > 1;
    }

    private void e() {
        a(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    }

    private void f() {
        n();
        int iN = n();
        this.d.f.k = (iN & 28) >> 2;
        if (this.d.f.k == 0) {
            this.d.f.k = 1;
        }
        this.d.f.j = (iN & 1) != 0;
        short s2 = this.c.getShort();
        if (s2 < 2) {
            s2 = 10;
        }
        this.d.f.m = s2 * 10;
        this.d.f.l = n();
        n();
    }

    private void g() {
        g gVar;
        int[] iArrB;
        this.d.f.e = this.c.getShort();
        this.d.f.f = this.c.getShort();
        this.d.f.g = this.c.getShort();
        this.d.f.h = this.c.getShort();
        int iN = n();
        boolean z = (iN & 128) != 0;
        int iPow = (int) Math.pow(2.0d, (iN & 7) + 1);
        this.d.f.i = (iN & 64) != 0;
        if (z) {
            gVar = this.d.f;
            iArrB = b(iPow);
        } else {
            gVar = this.d.f;
            iArrB = null;
        }
        gVar.o = iArrB;
        this.d.f.n = this.c.position();
        k();
        if (p()) {
            return;
        }
        this.d.e++;
        this.d.g.add(this.d.f);
    }

    private void h() {
        do {
            m();
            if (this.w[0] == 1) {
                this.d.o = (this.w[1] & 255) | ((this.w[2] & 255) << 8);
            }
            if (this.x <= 0) {
                return;
            }
        } while (!p());
    }

    private void i() {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < 6; i2++) {
            sb.append((char) n());
        }
        if (!sb.toString().startsWith("GIF")) {
            this.d.d = 1;
            return;
        }
        j();
        if (!this.d.j || p()) {
            return;
        }
        this.d.c = b(this.d.k);
        this.d.n = this.d.c[this.d.l];
    }

    private void j() {
        this.d.h = this.c.getShort();
        this.d.i = this.c.getShort();
        this.d.j = (n() & 128) != 0;
        this.d.k = (int) Math.pow(2.0d, (r0 & 7) + 1);
        this.d.l = n();
        this.d.m = n();
    }

    private void k() {
        n();
        l();
    }

    private void l() {
        int iN;
        do {
            iN = n();
            this.c.position(Math.min(this.c.position() + iN, this.c.limit()));
        } while (iN > 0);
    }

    private void m() {
        this.x = n();
        if (this.x > 0) {
            int i2 = 0;
            int i3 = 0;
            while (i2 < this.x) {
                try {
                    i3 = this.x - i2;
                    this.c.get(this.w, i2, i3);
                    i2 += i3;
                } catch (Exception e2) {
                    com.igexin.c.a.c.a.b(e, "Error Reading Block n: " + i2 + " count: " + i3 + " blockSize: " + this.x + e2.getMessage());
                    this.d.d = 1;
                    return;
                }
            }
        }
    }

    private int n() {
        try {
            return this.c.get() & 255;
        } catch (Exception unused) {
            this.d.d = 1;
            return 0;
        }
    }

    private int o() {
        return this.c.getShort();
    }

    private boolean p() {
        return this.d.d != 0;
    }

    public final j a(ByteBuffer byteBuffer) {
        c();
        this.c = byteBuffer.asReadOnlyBuffer();
        this.c.position(0);
        this.c.order(ByteOrder.LITTLE_ENDIAN);
        return this;
    }

    public final void a() {
        this.c = null;
        this.d = null;
    }

    public final i b() {
        if (this.c == null) {
            throw new IllegalStateException("You must call setData() before parseHeader()");
        }
        if (p()) {
            return this.d;
        }
        i();
        if (!p()) {
            e();
            if (this.d.e < 0) {
                this.d.d = 1;
            }
        }
        return this.d;
    }
}
