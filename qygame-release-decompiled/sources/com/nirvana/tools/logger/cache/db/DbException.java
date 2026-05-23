package com.nirvana.tools.logger.cache.db;

/* JADX INFO: loaded from: classes.dex */
public class DbException extends Throwable {
    private Throwable originalThrowable;

    public DbException(String str, Throwable th) {
        super(str);
        this.originalThrowable = th;
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.originalThrowable != null ? this.originalThrowable.getCause() : this;
    }

    @Override // java.lang.Throwable
    public String getLocalizedMessage() {
        return this.originalThrowable != null ? this.originalThrowable.getLocalizedMessage() : super.getLocalizedMessage();
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.originalThrowable != null ? this.originalThrowable.getMessage() : super.getMessage();
    }

    @Override // java.lang.Throwable
    public StackTraceElement[] getStackTrace() {
        return this.originalThrowable != null ? this.originalThrowable.getStackTrace() : super.getStackTrace();
    }

    @Override // java.lang.Throwable
    public void printStackTrace() {
        if (this.originalThrowable != null) {
            this.originalThrowable.printStackTrace();
        } else {
            super.printStackTrace();
        }
    }
}
