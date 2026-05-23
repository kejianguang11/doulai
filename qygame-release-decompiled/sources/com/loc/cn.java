package com.loc;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* JADX INFO: loaded from: classes.dex */
public abstract class cn {
    cp a;
    private ByteBuffer b;

    cn(int i) {
        this.b = ByteBuffer.allocate(i);
        this.b.order(ByteOrder.LITTLE_ENDIAN);
        this.a = new cp(this.b);
    }

    public final cn a() {
        this.a.a(this.b);
        return this;
    }
}
