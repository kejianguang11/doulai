package com.alipay.security.mobile.module.http.v2;

import android.content.Context;
import com.alipay.security.mobile.module.http.model.c;
import com.alipay.security.mobile.module.http.model.d;
import com.alipay.tscenter.biz.rpc.report.general.model.DataReportRequest;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class b implements a {
    private static a a;
    private static com.alipay.security.mobile.module.http.a b;

    public static a a(Context context, String str) {
        if (context == null) {
            return null;
        }
        if (a == null) {
            b = context != null ? com.alipay.security.mobile.module.http.b.a(context, str) : null;
            a = new b();
        }
        return a;
    }

    @Override // com.alipay.security.mobile.module.http.v2.a
    public final c a(d dVar) {
        DataReportRequest dataReportRequest = new DataReportRequest();
        dataReportRequest.os = com.alipay.security.mobile.module.a.a.d(dVar.a);
        dataReportRequest.rpcVersion = dVar.j;
        dataReportRequest.bizType = "1";
        dataReportRequest.bizData = new HashMap();
        dataReportRequest.bizData.put("apdid", com.alipay.security.mobile.module.a.a.d(dVar.b));
        dataReportRequest.bizData.put("apdidToken", com.alipay.security.mobile.module.a.a.d(dVar.c));
        dataReportRequest.bizData.put("umidToken", com.alipay.security.mobile.module.a.a.d(dVar.d));
        dataReportRequest.bizData.put("dynamicKey", dVar.e);
        dataReportRequest.deviceData = dVar.f == null ? new HashMap<>() : dVar.f;
        return com.alipay.security.mobile.module.http.model.b.a(b.a(dataReportRequest));
    }

    @Override // com.alipay.security.mobile.module.http.v2.a
    public final boolean a(String str) {
        return b.a(str);
    }
}
