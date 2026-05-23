package org.cocos2dx.okhttp3;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import org.cocos2dx.okhttp3.internal.NamedRunnable;
import org.cocos2dx.okhttp3.internal.Util;
import org.cocos2dx.okhttp3.internal.cache.CacheInterceptor;
import org.cocos2dx.okhttp3.internal.connection.ConnectInterceptor;
import org.cocos2dx.okhttp3.internal.connection.StreamAllocation;
import org.cocos2dx.okhttp3.internal.http.BridgeInterceptor;
import org.cocos2dx.okhttp3.internal.http.CallServerInterceptor;
import org.cocos2dx.okhttp3.internal.http.RealInterceptorChain;
import org.cocos2dx.okhttp3.internal.http.RetryAndFollowUpInterceptor;
import org.cocos2dx.okhttp3.internal.platform.Platform;
import org.cocos2dx.okio.AsyncTimeout;
import org.cocos2dx.okio.Timeout;

/* JADX INFO: loaded from: classes.dex */
final class RealCall implements Call {
    final OkHttpClient a;
    final RetryAndFollowUpInterceptor b;
    final AsyncTimeout c = new AsyncTimeout() { // from class: org.cocos2dx.okhttp3.RealCall.1
        @Override // org.cocos2dx.okio.AsyncTimeout
        protected void timedOut() {
            RealCall.this.cancel();
        }
    };
    final Request d;
    final boolean e;

    @Nullable
    private EventListener eventListener;
    private boolean executed;

    final class AsyncCall extends NamedRunnable {
        static final /* synthetic */ boolean a = !RealCall.class.desiredAssertionStatus();
        private final Callback responseCallback;

        AsyncCall(Callback callback) {
            super("OkHttp %s", RealCall.this.c());
            this.responseCallback = callback;
        }

        String a() {
            return RealCall.this.d.url().host();
        }

        void a(ExecutorService executorService) {
            if (!a && Thread.holdsLock(RealCall.this.a.dispatcher())) {
                throw new AssertionError();
            }
            try {
                try {
                    executorService.execute(this);
                } catch (RejectedExecutionException e) {
                    InterruptedIOException interruptedIOException = new InterruptedIOException("executor rejected");
                    interruptedIOException.initCause(e);
                    RealCall.this.eventListener.callFailed(RealCall.this, interruptedIOException);
                    this.responseCallback.onFailure(RealCall.this, interruptedIOException);
                    RealCall.this.a.dispatcher().finished(this);
                }
            } catch (Throwable th) {
                RealCall.this.a.dispatcher().finished(this);
                throw th;
            }
        }

        RealCall b() {
            return RealCall.this;
        }

        @Override // org.cocos2dx.okhttp3.internal.NamedRunnable
        protected void execute() {
            RealCall.this.c.enter();
            boolean z = false;
            try {
                try {
                    z = true;
                    this.responseCallback.onResponse(RealCall.this, RealCall.this.d());
                } catch (IOException e) {
                    IOException iOExceptionA = RealCall.this.a(e);
                    if (z) {
                        Platform.get().log(4, "Callback failure for " + RealCall.this.b(), iOExceptionA);
                    } else {
                        RealCall.this.eventListener.callFailed(RealCall.this, iOExceptionA);
                        this.responseCallback.onFailure(RealCall.this, iOExceptionA);
                    }
                } catch (Throwable th) {
                    RealCall.this.cancel();
                    if (!z) {
                        this.responseCallback.onFailure(RealCall.this, new IOException("canceled due to " + th));
                    }
                    throw th;
                }
            } finally {
                RealCall.this.a.dispatcher().finished(this);
            }
        }
    }

    private RealCall(OkHttpClient okHttpClient, Request request, boolean z) {
        this.a = okHttpClient;
        this.d = request;
        this.e = z;
        this.b = new RetryAndFollowUpInterceptor(okHttpClient, z);
        this.c.timeout(okHttpClient.callTimeoutMillis(), TimeUnit.MILLISECONDS);
    }

    static RealCall a(OkHttpClient okHttpClient, Request request, boolean z) {
        RealCall realCall = new RealCall(okHttpClient, request, z);
        realCall.eventListener = okHttpClient.eventListenerFactory().create(realCall);
        return realCall;
    }

    private void captureCallStackTrace() {
        this.b.setCallStackTrace(Platform.get().getStackTraceForCloseable("response.body().close()"));
    }

    @Nullable
    IOException a(@Nullable IOException iOException) {
        if (!this.c.exit()) {
            return iOException;
        }
        InterruptedIOException interruptedIOException = new InterruptedIOException("timeout");
        if (iOException != null) {
            interruptedIOException.initCause(iOException);
        }
        return interruptedIOException;
    }

    StreamAllocation a() {
        return this.b.streamAllocation();
    }

    String b() {
        StringBuilder sb = new StringBuilder();
        sb.append(isCanceled() ? "canceled " : "");
        sb.append(this.e ? "web socket" : "call");
        sb.append(" to ");
        sb.append(c());
        return sb.toString();
    }

    String c() {
        return this.d.url().redact();
    }

    @Override // org.cocos2dx.okhttp3.Call
    public void cancel() {
        this.b.cancel();
    }

    @Override // org.cocos2dx.okhttp3.Call
    public RealCall clone() {
        return a(this.a, this.d, this.e);
    }

    Response d() throws IOException {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.a.interceptors());
        arrayList.add(this.b);
        arrayList.add(new BridgeInterceptor(this.a.cookieJar()));
        arrayList.add(new CacheInterceptor(this.a.internalCache()));
        arrayList.add(new ConnectInterceptor(this.a));
        if (!this.e) {
            arrayList.addAll(this.a.networkInterceptors());
        }
        arrayList.add(new CallServerInterceptor(this.e));
        Response responseProceed = new RealInterceptorChain(arrayList, null, null, null, 0, this.d, this, this.eventListener, this.a.connectTimeoutMillis(), this.a.readTimeoutMillis(), this.a.writeTimeoutMillis()).proceed(this.d);
        if (!this.b.isCanceled()) {
            return responseProceed;
        }
        Util.closeQuietly(responseProceed);
        throw new IOException("Canceled");
    }

    @Override // org.cocos2dx.okhttp3.Call
    public void enqueue(Callback callback) {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        captureCallStackTrace();
        this.eventListener.callStart(this);
        this.a.dispatcher().enqueue(new AsyncCall(callback));
    }

    @Override // org.cocos2dx.okhttp3.Call
    public Response execute() throws IOException {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        captureCallStackTrace();
        this.c.enter();
        this.eventListener.callStart(this);
        try {
            try {
                this.a.dispatcher().executed(this);
                Response responseD = d();
                if (responseD != null) {
                    return responseD;
                }
                throw new IOException("Canceled");
            } catch (IOException e) {
                IOException iOExceptionA = a(e);
                this.eventListener.callFailed(this, iOExceptionA);
                throw iOExceptionA;
            }
        } finally {
            this.a.dispatcher().finished(this);
        }
        this.a.dispatcher().finished(this);
    }

    @Override // org.cocos2dx.okhttp3.Call
    public boolean isCanceled() {
        return this.b.isCanceled();
    }

    @Override // org.cocos2dx.okhttp3.Call
    public synchronized boolean isExecuted() {
        return this.executed;
    }

    @Override // org.cocos2dx.okhttp3.Call
    public Request request() {
        return this.d;
    }

    @Override // org.cocos2dx.okhttp3.Call
    public Timeout timeout() {
        return this.c;
    }
}
