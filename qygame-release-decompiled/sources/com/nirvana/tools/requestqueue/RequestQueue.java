package com.nirvana.tools.requestqueue;

import com.nirvana.tools.requestqueue.RequestHandler;
import com.nirvana.tools.requestqueue.strategy.ExecuteStrategy;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
public class RequestQueue {
    private static volatile RequestQueue mInstance;
    ConcurrentHashMap<String, RequestHandler> mPendingRequests = new ConcurrentHashMap<>();

    private RequestQueue() {
    }

    public static RequestQueue getInstance() {
        if (mInstance == null) {
            synchronized (RequestQueue.class) {
                if (mInstance == null) {
                    mInstance = new RequestQueue();
                }
            }
        }
        return mInstance;
    }

    public <T extends Response> void pushRequest(Request<T> request) {
        if (request == null) {
            return;
        }
        RequestHandler requestHandler = this.mPendingRequests.get(request.getID());
        if (requestHandler == null) {
            RequestHandler requestHandler2 = new RequestHandler(request, new RequestHandler.DoneAction() { // from class: com.nirvana.tools.requestqueue.RequestQueue.1
                @Override // com.nirvana.tools.requestqueue.RequestHandler.DoneAction
                public final void run(RequestHandler requestHandler3) {
                    RequestQueue.this.mPendingRequests.remove(requestHandler3.c.getID());
                }
            });
            requestHandler2.a(request);
            this.mPendingRequests.put(request.getID(), requestHandler2);
            requestHandler2.a();
            return;
        }
        requestHandler.a(request);
        if (ExecuteStrategy.USE_NEW == request.getExecuteStrategy()) {
            if (requestHandler.b != null) {
                requestHandler.b.a();
                requestHandler.b = null;
            }
            requestHandler.c = request;
            requestHandler.a();
        }
    }
}
