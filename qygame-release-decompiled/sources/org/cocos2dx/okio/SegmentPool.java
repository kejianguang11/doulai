package org.cocos2dx.okio;

import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
final class SegmentPool {

    @Nullable
    static Segment a;
    static long b;

    private SegmentPool() {
    }

    static Segment a() {
        synchronized (SegmentPool.class) {
            if (a == null) {
                return new Segment();
            }
            Segment segment = a;
            a = segment.f;
            segment.f = null;
            b -= 8192;
            return segment;
        }
    }

    static void a(Segment segment) {
        if (segment.f != null || segment.g != null) {
            throw new IllegalArgumentException();
        }
        if (segment.d) {
            return;
        }
        synchronized (SegmentPool.class) {
            if (b + 8192 > 65536) {
                return;
            }
            b += 8192;
            segment.f = a;
            segment.c = 0;
            segment.b = 0;
            a = segment;
        }
    }
}
