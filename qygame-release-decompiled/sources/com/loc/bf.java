package com.loc;

import com.loc.bl;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class bf extends bl {
    private byte[] a;
    private Map<String, String> b;

    public bf(byte[] bArr, Map<String, String> map) {
        this.a = bArr;
        this.b = map;
        a(bl.a.SINGLE);
        a(bl.c.HTTPS);
    }

    @Override // com.loc.bl
    public final Map<String, String> a() {
        return null;
    }

    @Override // com.loc.bl
    public final String b() {
        return "https://adiu.amap.com/ws/device/adius";
    }

    @Override // com.loc.bl
    public final Map<String, String> d() {
        return this.b;
    }

    @Override // com.loc.bl
    public final byte[] e() {
        return this.a;
    }
}
