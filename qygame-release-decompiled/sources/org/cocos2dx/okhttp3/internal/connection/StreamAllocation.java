package org.cocos2dx.okhttp3.internal.connection;

import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.List;
import org.cocos2dx.okhttp3.Address;
import org.cocos2dx.okhttp3.Call;
import org.cocos2dx.okhttp3.ConnectionPool;
import org.cocos2dx.okhttp3.EventListener;
import org.cocos2dx.okhttp3.Interceptor;
import org.cocos2dx.okhttp3.OkHttpClient;
import org.cocos2dx.okhttp3.Route;
import org.cocos2dx.okhttp3.internal.Internal;
import org.cocos2dx.okhttp3.internal.Util;
import org.cocos2dx.okhttp3.internal.connection.RouteSelector;
import org.cocos2dx.okhttp3.internal.http.HttpCodec;
import org.cocos2dx.okhttp3.internal.http2.ConnectionShutdownException;
import org.cocos2dx.okhttp3.internal.http2.ErrorCode;
import org.cocos2dx.okhttp3.internal.http2.StreamResetException;

/* JADX INFO: loaded from: classes.dex */
public final class StreamAllocation {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public final Address address;
    public final Call call;
    private final Object callStackTrace;
    private boolean canceled;
    private HttpCodec codec;
    private RealConnection connection;
    private final ConnectionPool connectionPool;
    public final EventListener eventListener;
    private int refusedStreamCount;
    private boolean released;
    private boolean reportedAcquired;
    private Route route;
    private RouteSelector.Selection routeSelection;
    private final RouteSelector routeSelector;

    public static final class StreamAllocationReference extends WeakReference<StreamAllocation> {
        public final Object callStackTrace;

        StreamAllocationReference(StreamAllocation streamAllocation, Object obj) {
            super(streamAllocation);
            this.callStackTrace = obj;
        }
    }

    public StreamAllocation(ConnectionPool connectionPool, Address address, Call call, EventListener eventListener, Object obj) {
        this.connectionPool = connectionPool;
        this.address = address;
        this.call = call;
        this.eventListener = eventListener;
        this.routeSelector = new RouteSelector(address, routeDatabase(), call, eventListener);
        this.callStackTrace = obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Socket deallocate(boolean z, boolean z2, boolean z3) {
        Socket socket;
        if (z3) {
            this.codec = null;
        }
        if (z2) {
            this.released = true;
        }
        if (this.connection != null) {
            if (z) {
                this.connection.noNewStreams = true;
            }
            if (this.codec == null && (this.released || this.connection.noNewStreams)) {
                release(this.connection);
                if (this.connection.allocations.isEmpty()) {
                    this.connection.idleAtNanos = System.nanoTime();
                    socket = Internal.instance.connectionBecameIdle(this.connectionPool, this.connection) ? this.connection.socket() : null;
                }
                this.connection = null;
                return socket;
            }
        }
        return null;
    }

    private RealConnection findConnection(int i, int i2, int i3, int i4, boolean z) throws Throwable {
        Socket socketReleaseIfNoNewStreams;
        Socket socketDeduplicate;
        RealConnection realConnection;
        RealConnection realConnection2;
        RealConnection realConnection3;
        Route next;
        boolean z2;
        boolean z3;
        synchronized (this.connectionPool) {
            if (this.released) {
                throw new IllegalStateException("released");
            }
            if (this.codec != null) {
                throw new IllegalStateException("codec != null");
            }
            if (this.canceled) {
                throw new IOException("Canceled");
            }
            RealConnection realConnection4 = this.connection;
            socketReleaseIfNoNewStreams = releaseIfNoNewStreams();
            socketDeduplicate = null;
            if (this.connection != null) {
                realConnection2 = this.connection;
                realConnection = null;
            } else {
                realConnection = realConnection4;
                realConnection2 = null;
            }
            if (!this.reportedAcquired) {
                realConnection = null;
            }
            if (realConnection2 == null) {
                Internal.instance.get(this.connectionPool, this.address, this, null);
                if (this.connection != null) {
                    realConnection3 = this.connection;
                    next = null;
                    z2 = true;
                } else {
                    next = this.route;
                    realConnection3 = realConnection2;
                }
            } else {
                realConnection3 = realConnection2;
                next = null;
            }
            z2 = false;
        }
        Util.closeQuietly(socketReleaseIfNoNewStreams);
        if (realConnection != null) {
            this.eventListener.connectionReleased(this.call, realConnection);
        }
        if (z2) {
            this.eventListener.connectionAcquired(this.call, realConnection3);
        }
        if (realConnection3 != null) {
            this.route = this.connection.route();
            return realConnection3;
        }
        if (next != null || (this.routeSelection != null && this.routeSelection.hasNext())) {
            z3 = false;
        } else {
            this.routeSelection = this.routeSelector.next();
            z3 = true;
        }
        synchronized (this.connectionPool) {
            if (this.canceled) {
                throw new IOException("Canceled");
            }
            if (z3) {
                List<Route> all = this.routeSelection.getAll();
                int size = all.size();
                int i5 = 0;
                while (true) {
                    if (i5 >= size) {
                        break;
                    }
                    Route route = all.get(i5);
                    Internal.instance.get(this.connectionPool, this.address, this, route);
                    if (this.connection != null) {
                        realConnection3 = this.connection;
                        this.route = route;
                        z2 = true;
                        break;
                    }
                    i5++;
                }
            }
            if (!z2) {
                if (next == null) {
                    next = this.routeSelection.next();
                }
                this.route = next;
                this.refusedStreamCount = 0;
                realConnection3 = new RealConnection(this.connectionPool, next);
                acquire(realConnection3, false);
            }
        }
        if (z2) {
            this.eventListener.connectionAcquired(this.call, realConnection3);
            return realConnection3;
        }
        realConnection3.connect(i, i2, i3, i4, z, this.call, this.eventListener);
        routeDatabase().connected(realConnection3.route());
        synchronized (this.connectionPool) {
            this.reportedAcquired = true;
            Internal.instance.put(this.connectionPool, realConnection3);
            if (realConnection3.isMultiplexed()) {
                socketDeduplicate = Internal.instance.deduplicate(this.connectionPool, this.address, this);
                realConnection3 = this.connection;
            }
        }
        Util.closeQuietly(socketDeduplicate);
        this.eventListener.connectionAcquired(this.call, realConnection3);
        return realConnection3;
    }

    private RealConnection findHealthyConnection(int i, int i2, int i3, int i4, boolean z, boolean z2) throws Throwable {
        while (true) {
            RealConnection realConnectionFindConnection = findConnection(i, i2, i3, i4, z);
            synchronized (this.connectionPool) {
                if (realConnectionFindConnection.successCount == 0) {
                    return realConnectionFindConnection;
                }
                if (realConnectionFindConnection.isHealthy(z2)) {
                    return realConnectionFindConnection;
                }
                noNewStreams();
            }
        }
    }

    private void release(RealConnection realConnection) {
        int size = realConnection.allocations.size();
        for (int i = 0; i < size; i++) {
            if (realConnection.allocations.get(i).get() == this) {
                realConnection.allocations.remove(i);
                return;
            }
        }
        throw new IllegalStateException();
    }

    private Socket releaseIfNoNewStreams() {
        RealConnection realConnection = this.connection;
        if (realConnection == null || !realConnection.noNewStreams) {
            return null;
        }
        return deallocate(false, false, true);
    }

    private RouteDatabase routeDatabase() {
        return Internal.instance.routeDatabase(this.connectionPool);
    }

    public void acquire(RealConnection realConnection, boolean z) {
        if (this.connection != null) {
            throw new IllegalStateException();
        }
        this.connection = realConnection;
        this.reportedAcquired = z;
        realConnection.allocations.add(new StreamAllocationReference(this, this.callStackTrace));
    }

    public void cancel() {
        HttpCodec httpCodec;
        RealConnection realConnection;
        synchronized (this.connectionPool) {
            this.canceled = true;
            httpCodec = this.codec;
            realConnection = this.connection;
        }
        if (httpCodec != null) {
            httpCodec.cancel();
        } else if (realConnection != null) {
            realConnection.cancel();
        }
    }

    public HttpCodec codec() {
        HttpCodec httpCodec;
        synchronized (this.connectionPool) {
            httpCodec = this.codec;
        }
        return httpCodec;
    }

    public synchronized RealConnection connection() {
        return this.connection;
    }

    public boolean hasMoreRoutes() {
        return this.route != null || (this.routeSelection != null && this.routeSelection.hasNext()) || this.routeSelector.hasNext();
    }

    public HttpCodec newStream(OkHttpClient okHttpClient, Interceptor.Chain chain, boolean z) {
        try {
            HttpCodec httpCodecNewCodec = findHealthyConnection(chain.connectTimeoutMillis(), chain.readTimeoutMillis(), chain.writeTimeoutMillis(), okHttpClient.pingIntervalMillis(), okHttpClient.retryOnConnectionFailure(), z).newCodec(okHttpClient, chain, this);
            synchronized (this.connectionPool) {
                this.codec = httpCodecNewCodec;
            }
            return httpCodecNewCodec;
        } catch (IOException e) {
            throw new RouteException(e);
        }
    }

    public void noNewStreams() {
        RealConnection realConnection;
        Socket socketDeallocate;
        synchronized (this.connectionPool) {
            realConnection = this.connection;
            socketDeallocate = deallocate(true, false, false);
            if (this.connection != null) {
                realConnection = null;
            }
        }
        Util.closeQuietly(socketDeallocate);
        if (realConnection != null) {
            this.eventListener.connectionReleased(this.call, realConnection);
        }
    }

    public void release() {
        RealConnection realConnection;
        Socket socketDeallocate;
        synchronized (this.connectionPool) {
            realConnection = this.connection;
            socketDeallocate = deallocate(false, true, false);
            if (this.connection != null) {
                realConnection = null;
            }
        }
        Util.closeQuietly(socketDeallocate);
        if (realConnection != null) {
            Internal.instance.timeoutExit(this.call, null);
            this.eventListener.connectionReleased(this.call, realConnection);
            this.eventListener.callEnd(this.call);
        }
    }

    public Socket releaseAndAcquire(RealConnection realConnection) {
        if (this.codec != null || this.connection.allocations.size() != 1) {
            throw new IllegalStateException();
        }
        Reference<StreamAllocation> reference = this.connection.allocations.get(0);
        Socket socketDeallocate = deallocate(true, false, false);
        this.connection = realConnection;
        realConnection.allocations.add(reference);
        return socketDeallocate;
    }

    public Route route() {
        return this.route;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x001e, code lost:
    
        if (r7 != org.cocos2dx.okhttp3.internal.http2.ErrorCode.CANCEL) goto L13;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0054 A[Catch: all -> 0x0067, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x000a, B:8:0x0012, B:13:0x0020, B:28:0x004a, B:30:0x0054, B:33:0x0059, B:11:0x001c, B:14:0x0023, B:16:0x0027, B:18:0x002f, B:20:0x0033, B:22:0x0039, B:25:0x003f), top: B:41:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0058  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void streamFailed(IOException iOException) {
        boolean z;
        RealConnection realConnection;
        Socket socketDeallocate;
        synchronized (this.connectionPool) {
            if (iOException instanceof StreamResetException) {
                ErrorCode errorCode = ((StreamResetException) iOException).errorCode;
                if (errorCode == ErrorCode.REFUSED_STREAM) {
                    this.refusedStreamCount++;
                    if (this.refusedStreamCount > 1) {
                        this.route = null;
                        z = true;
                        realConnection = this.connection;
                        socketDeallocate = deallocate(z, false, true);
                        if (this.connection == null || !this.reportedAcquired) {
                            realConnection = null;
                        }
                    }
                    z = false;
                    realConnection = this.connection;
                    socketDeallocate = deallocate(z, false, true);
                    if (this.connection == null) {
                        realConnection = null;
                    }
                }
            } else {
                if (this.connection != null && (!this.connection.isMultiplexed() || (iOException instanceof ConnectionShutdownException))) {
                    if (this.connection.successCount == 0) {
                        if (this.route != null && iOException != null) {
                            this.routeSelector.connectFailed(this.route, iOException);
                        }
                        this.route = null;
                    }
                    z = true;
                    realConnection = this.connection;
                    socketDeallocate = deallocate(z, false, true);
                    if (this.connection == null) {
                    }
                }
                z = false;
                realConnection = this.connection;
                socketDeallocate = deallocate(z, false, true);
                if (this.connection == null) {
                }
            }
        }
        Util.closeQuietly(socketDeallocate);
        if (realConnection != null) {
            this.eventListener.connectionReleased(this.call, realConnection);
        }
    }

    public void streamFinished(boolean z, HttpCodec httpCodec, long j, IOException iOException) {
        RealConnection realConnection;
        Socket socketDeallocate;
        boolean z2;
        this.eventListener.responseBodyEnd(this.call, j);
        synchronized (this.connectionPool) {
            if (httpCodec != null) {
                if (httpCodec == this.codec) {
                    if (!z) {
                        this.connection.successCount++;
                    }
                    realConnection = this.connection;
                    socketDeallocate = deallocate(z, false, true);
                    if (this.connection != null) {
                        realConnection = null;
                    }
                    z2 = this.released;
                }
            }
            throw new IllegalStateException("expected " + this.codec + " but was " + httpCodec);
        }
        Util.closeQuietly(socketDeallocate);
        if (realConnection != null) {
            this.eventListener.connectionReleased(this.call, realConnection);
        }
        if (iOException != null) {
            this.eventListener.callFailed(this.call, Internal.instance.timeoutExit(this.call, iOException));
        } else if (z2) {
            Internal.instance.timeoutExit(this.call, null);
            this.eventListener.callEnd(this.call);
        }
    }

    public String toString() {
        RealConnection realConnectionConnection = connection();
        return realConnectionConnection != null ? realConnectionConnection.toString() : this.address.toString();
    }
}
