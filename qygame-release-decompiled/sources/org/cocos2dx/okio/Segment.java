package org.cocos2dx.okio;

import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
final class Segment {
    final byte[] a;
    int b;
    int c;
    boolean d;
    boolean e;
    Segment f;
    Segment g;

    Segment() {
        this.a = new byte[8192];
        this.e = true;
        this.d = false;
    }

    Segment(byte[] bArr, int i, int i2, boolean z, boolean z2) {
        this.a = bArr;
        this.b = i;
        this.c = i2;
        this.d = z;
        this.e = z2;
    }

    final Segment a() {
        this.d = true;
        return new Segment(this.a, this.b, this.c, true, false);
    }

    final Segment b() {
        return new Segment((byte[]) this.a.clone(), this.b, this.c, false, true);
    }

    public final void compact() {
        if (this.g == this) {
            throw new IllegalStateException();
        }
        if (this.g.e) {
            int i = this.c - this.b;
            if (i > (8192 - this.g.c) + (this.g.d ? 0 : this.g.b)) {
                return;
            }
            writeTo(this.g, i);
            pop();
            SegmentPool.a(this);
        }
    }

    @Nullable
    public final Segment pop() {
        Segment segment = this.f != this ? this.f : null;
        this.g.f = this.f;
        this.f.g = this.g;
        this.f = null;
        this.g = null;
        return segment;
    }

    public final Segment push(Segment segment) {
        segment.g = this;
        segment.f = this.f;
        this.f.g = segment;
        this.f = segment;
        return segment;
    }

    public final Segment split(int i) {
        Segment segmentA;
        if (i <= 0 || i > this.c - this.b) {
            throw new IllegalArgumentException();
        }
        if (i >= 1024) {
            segmentA = a();
        } else {
            segmentA = SegmentPool.a();
            System.arraycopy(this.a, this.b, segmentA.a, 0, i);
        }
        segmentA.c = segmentA.b + i;
        this.b += i;
        this.g.push(segmentA);
        return segmentA;
    }

    public final void writeTo(Segment segment, int i) {
        if (!segment.e) {
            throw new IllegalArgumentException();
        }
        if (segment.c + i > 8192) {
            if (segment.d) {
                throw new IllegalArgumentException();
            }
            if ((segment.c + i) - segment.b > 8192) {
                throw new IllegalArgumentException();
            }
            System.arraycopy(segment.a, segment.b, segment.a, 0, segment.c - segment.b);
            segment.c -= segment.b;
            segment.b = 0;
        }
        System.arraycopy(this.a, this.b, segment.a, segment.c, i);
        segment.c += i;
        this.b += i;
    }
}
