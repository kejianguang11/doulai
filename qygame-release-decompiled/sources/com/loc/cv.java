package com.loc;

import android.util.Base64;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes.dex */
public final class cv {
    public static boolean a(byte[] bArr) {
        String str;
        if (bArr == null) {
            return false;
        }
        byte[] bArr2 = null;
        try {
            ec ecVar = new ec();
            ecVar.b.put("Content-Type", "application/octet-stream");
            ecVar.b.put("aps_c_src", Base64.encodeToString(ec.a().getBytes(), 2));
            ecVar.b.put("aps_c_key", Base64.encodeToString(ec.b().getBytes(), 2));
            ecVar.d = bArr;
            if (cm.a) {
                str = "http://cgicol.amap.com/collection/collectData?src=baseCol&ver=v74&";
            } else {
                str = (cm.b ? "https://" : "http://") + "cgicol.amap.com/collection/collectData?src=baseCol&ver=v74&";
            }
            ecVar.a = str;
            ed edVarA = dq.b().a(ecVar);
            if (edVarA != null && edVarA.a == 200) {
                bArr2 = edVarA.c;
            }
            if (bArr2 != null) {
                return "true".equals(new String(bArr2, StandardCharsets.UTF_8));
            }
            return false;
        } catch (Exception e) {
            eb.a(e);
            return false;
        }
    }
}
