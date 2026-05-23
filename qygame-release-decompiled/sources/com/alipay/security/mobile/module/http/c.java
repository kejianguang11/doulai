package com.alipay.security.mobile.module.http;

import com.alipay.tscenter.biz.rpc.report.general.model.DataReportRequest;
import com.alipay.tscenter.biz.rpc.report.general.model.DataReportResult;

/* JADX INFO: loaded from: classes.dex */
final class c implements Runnable {
    final /* synthetic */ DataReportRequest a;
    final /* synthetic */ b b;

    c(b bVar, DataReportRequest dataReportRequest) {
        this.b = bVar;
        this.a = dataReportRequest;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            DataReportResult unused = b.e = this.b.c.a();
        } catch (Throwable th) {
            DataReportResult unused2 = b.e = new DataReportResult();
            b.e.success = false;
            b.e.resultCode = "static data rpc upload error, " + com.alipay.security.mobile.module.a.a.a(th);
            com.alipay.security.mobile.module.a.a.a(th);
        }
    }
}
