package com.mobile.auth.u;

import android.util.SparseArray;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.model.VendorConfig;
import com.nirvana.tools.requestqueue.TimeoutResponse;

/* JADX INFO: loaded from: classes.dex */
public abstract class a extends TimeoutResponse {
    private boolean a;
    private String b;
    private String c;
    private String d;

    public a(boolean z, String str, String str2) {
        super(z);
        this.b = str;
        this.d = str2;
    }

    public void a(String str) {
        try {
            this.c = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(boolean z) {
        try {
            this.a = z;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean a() {
        try {
            return this.a;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public String b() {
        try {
            return this.c == null ? "" : this.c;
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

    public String c() {
        try {
            return this.b;
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

    public String d() {
        try {
            return this.d;
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

    public abstract SparseArray<VendorConfig> e();
}
