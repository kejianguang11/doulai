package com.mobile.auth.gatewayauth.ui;

import com.mobile.auth.gatewayauth.ExceptionProcessor;

/* JADX INFO: loaded from: classes.dex */
public class b {
    private String a;
    private String b;
    private int c;

    public static final class a {
        private String a;
        private String b;
        private int c;

        private a() {
        }

        static /* synthetic */ String a(a aVar) {
            try {
                return aVar.a;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }

        static /* synthetic */ String b(a aVar) {
            try {
                return aVar.b;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }

        static /* synthetic */ int c(a aVar) {
            try {
                return aVar.c;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return -1;
            }
        }

        public a a(int i) {
            try {
                this.c = i;
                return this;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }

        public a a(String str) {
            try {
                this.a = str;
                return this;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }

        public b a() {
            try {
                return new b(this);
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }

        public a b(String str) {
            try {
                this.b = str;
                return this;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }
    }

    private b(a aVar) {
        this.a = a.a(aVar);
        this.b = a.b(aVar);
        this.c = a.c(aVar);
    }

    public static a a() {
        try {
            return new a();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public String b() {
        try {
            return this.a;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public String c() {
        try {
            return this.b;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public int d() {
        try {
            return this.c;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }
}
