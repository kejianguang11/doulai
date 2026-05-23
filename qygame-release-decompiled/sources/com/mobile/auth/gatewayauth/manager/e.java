package com.mobile.auth.gatewayauth.manager;

import android.content.Context;
import android.util.SparseArray;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.model.VendorConfig;
import com.nirvana.tools.requestqueue.Callback;
import com.nirvana.tools.requestqueue.RequestQueue;
import com.nirvana.tools.requestqueue.Response;
import com.nirvana.tools.requestqueue.strategy.ThreadStrategy;

/* JADX INFO: loaded from: classes.dex */
public class e {
    private com.mobile.auth.p.a a;

    interface a {
        void a(String str, String str2);

        void a(String str, String str2, SparseArray<VendorConfig> sparseArray);
    }

    public e(com.mobile.auth.p.a aVar) {
        this.a = aVar;
    }

    public static final e a(Context context, VendorSdkInfoManager vendorSdkInfoManager, b bVar, com.mobile.auth.o.a aVar) {
        try {
            return new e(new com.mobile.auth.p.b(context, vendorSdkInfoManager, bVar, aVar));
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public void a(final a aVar) {
        try {
            RequestQueue.getInstance().pushRequest(new com.mobile.auth.t.c(new Callback<com.mobile.auth.u.a>(ThreadStrategy.SAME_WITH_CALLABLE, 2000L) { // from class: com.mobile.auth.gatewayauth.manager.e.1
                public void a(com.mobile.auth.u.a aVar2) {
                    try {
                        if (aVar != null) {
                            if (aVar2.a()) {
                                aVar.a(aVar2.c(), aVar2.b(), aVar2.e());
                            } else {
                                aVar.a(aVar2.c(), aVar2.b());
                            }
                        }
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.nirvana.tools.requestqueue.Callback
                public /* synthetic */ void onResult(Response response) {
                    try {
                        a((com.mobile.auth.u.a) response);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            }, this.a));
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
