package com.nirvana.tools.requestqueue;

import com.nirvana.tools.core.ExecutorManager;
import com.nirvana.tools.requestqueue.Response;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.RunnableScheduledFuture;

/* JADX INFO: loaded from: classes.dex */
final class RequestHandler<T extends Response> {
    List<Callback<T>> a = new ArrayList();
    RequestHandler<T>.a b;
    Request<T> c;
    private DoneAction d;

    public interface DoneAction {
        void run(RequestHandler requestHandler);
    }

    class a implements Runnable {
        private Runnable d;
        private volatile boolean c = false;
        RunnableScheduledFuture<?> a = null;

        public a(Runnable runnable) {
            this.d = null;
            this.d = runnable;
        }

        public final synchronized void a() {
            if (this.d != null) {
                ExecutorManager.getInstance().removeFromMain(this.d);
            }
            if (this.a != null) {
                ExecutorManager.getInstance().removeFromThread(this.a);
            }
            this.c = true;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (this.c) {
                return;
            }
            try {
                T tCall = RequestHandler.this.c.getAction().call();
                if (this.c) {
                    return;
                }
                RequestHandler.this.a(tCall);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public RequestHandler(Request<T> request, DoneAction doneAction) {
        this.c = request;
        this.d = doneAction;
    }

    final void a() {
        if (this.b == null) {
            Runnable runnable = new Runnable() { // from class: com.nirvana.tools.requestqueue.RequestHandler.3
                @Override // java.lang.Runnable
                public final void run() {
                    RequestHandler.this.a(RequestHandler.this.c.getAction().onTimeout());
                }
            };
            this.b = new a(runnable);
            switch (this.c.getThreadStrategy()) {
                case THREAD:
                    ExecutorManager.getInstance().scheduleFuture(this.b);
                    this.b.a = ExecutorManager.getInstance().scheduleFutureDelay(runnable, this.c.getTimeout());
                    return;
                case THREAD_MAIN:
                    ExecutorManager.getInstance().postMain(this.b);
                    ExecutorManager.getInstance().postMain(runnable, this.c.getTimeout());
                    return;
                default:
                    throw new IllegalArgumentException("Request Callable ThreadStrategy Illegal");
            }
        }
    }

    final synchronized void a(Request<T> request) {
        List<Callback<T>> list;
        Callback<T> callback;
        request.getCallback().setExpiredTime(request.getTimeout() + System.currentTimeMillis());
        switch (request.getCallbackStrategy()) {
            case LIST:
                list = this.a;
                callback = request.getCallback();
                list.add(callback);
                break;
            case COVER:
                this.a.clear();
                list = this.a;
                callback = request.getCallback();
                list.add(callback);
                break;
        }
        if (this.a.isEmpty()) {
            this.a.add(request.getCallback());
        }
    }

    final synchronized void a(final T t) {
        long j = 0;
        if (this.a.size() > 0) {
            ArrayList arrayList = new ArrayList(this.a.size());
            Iterator<Callback<T>> it = this.a.iterator();
            while (it.hasNext()) {
                final Callback<T> next = it.next();
                if (t.isTimeout()) {
                    long jCurrentTimeMillis = System.currentTimeMillis() - next.getExpiredTime();
                    if (jCurrentTimeMillis > next.getThreshold()) {
                        if (j > jCurrentTimeMillis) {
                            j = jCurrentTimeMillis;
                        }
                    }
                }
                switch (next.getThreadStrategy()) {
                    case THREAD:
                        ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.nirvana.tools.requestqueue.RequestHandler.1
                            /* JADX WARN: Multi-variable type inference failed */
                            @Override // java.lang.Runnable
                            public final void run() {
                                next.onResult(t);
                            }
                        });
                        break;
                    case THREAD_MAIN:
                        ExecutorManager.getInstance().postMain(new Runnable() { // from class: com.nirvana.tools.requestqueue.RequestHandler.2
                            /* JADX WARN: Multi-variable type inference failed */
                            @Override // java.lang.Runnable
                            public final void run() {
                                next.onResult(t);
                            }
                        });
                        break;
                    case SAME_WITH_CALLABLE:
                        arrayList.add(next);
                        break;
                }
                it.remove();
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ((Callback) it2.next()).onResult(t);
            }
            arrayList.clear();
            if (this.a.isEmpty()) {
                if (this.d != null) {
                    this.d.run(this);
                }
            } else {
                if (this.c != null) {
                    this.c.setTimeout(j);
                }
                a();
            }
        }
    }
}
