package com.loc;

import com.igexin.sdk.PushBuildConfig;
import com.loc.bl;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class am extends r {
    private byte[] a;
    private String b;

    public am(byte[] bArr, String str) {
        this.b = "1";
        this.a = (byte[]) bArr.clone();
        this.b = str;
        a(bl.a.SINGLE);
        a(bl.c.HTTP);
    }

    @Override // com.loc.bl
    public final Map<String, String> a() {
        HashMap map = new HashMap();
        map.put("Content-Type", "application/zip");
        map.put("Content-Length", String.valueOf(this.a.length));
        return map;
    }

    @Override // com.loc.bl
    public final String b() {
        String strC = x.c(ag.b);
        byte[] bArrA = x.a(ag.a);
        byte[] bArr = new byte[bArrA.length + 50];
        System.arraycopy(this.a, 0, bArr, 0, 50);
        System.arraycopy(bArrA, 0, bArr, 50, bArrA.length);
        return String.format(strC, "1", this.b, "1", PushBuildConfig.sdk_conf_channelid, s.a(bArr));
    }

    @Override // com.loc.bl
    public final boolean c_() {
        return false;
    }

    @Override // com.loc.bl
    public final Map<String, String> d() {
        return null;
    }

    @Override // com.loc.bl
    public final byte[] e() {
        return this.a;
    }
}
