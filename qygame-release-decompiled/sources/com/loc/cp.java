package com.loc;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public final class cp extends fs {
    cp(ByteBuffer byteBuffer) {
        super(byteBuffer);
    }

    @Override // com.loc.fs
    public final int a(CharSequence charSequence) {
        try {
            return super.a(charSequence);
        } catch (Throwable th) {
            eb.a(th);
            return super.a("");
        }
    }
}
