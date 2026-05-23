package com.zx.a.I8b7;

import com.zx.a.I8b7.p0;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class i0 implements p0 {
    @Override // com.zx.a.I8b7.p0
    public v1 a(p0.a aVar) throws IOException {
        if (!b4.a(q3.a, true)) {
            throw new IllegalStateException("network is not available");
        }
        l1 l1Var = (l1) aVar;
        return l1Var.a(l1Var.c, l1Var.d);
    }
}
