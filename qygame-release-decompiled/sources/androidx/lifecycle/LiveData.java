package androidx.lifecycle;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.Lifecycle;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class LiveData<T> {
    static final Object c = new Object();
    final Object b;
    int d;
    volatile Object e;
    private boolean mChangingActiveState;
    private volatile Object mData;
    private boolean mDispatchInvalidated;
    private boolean mDispatchingValue;
    private SafeIterableMap<Observer<? super T>, LiveData<T>.ObserverWrapper> mObservers;
    private final Runnable mPostValueRunnable;
    private int mVersion;

    private class AlwaysActiveObserver extends LiveData<T>.ObserverWrapper {
        AlwaysActiveObserver(Observer<? super T> observer) {
            super(observer);
        }

        @Override // androidx.lifecycle.LiveData.ObserverWrapper
        boolean a() {
            return true;
        }
    }

    class LifecycleBoundObserver extends LiveData<T>.ObserverWrapper implements LifecycleEventObserver {

        @NonNull
        final LifecycleOwner a;

        LifecycleBoundObserver(LifecycleOwner lifecycleOwner, @NonNull Observer<? super T> observer) {
            super(observer);
            this.a = lifecycleOwner;
        }

        @Override // androidx.lifecycle.LiveData.ObserverWrapper
        boolean a() {
            return this.a.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
        }

        @Override // androidx.lifecycle.LiveData.ObserverWrapper
        boolean a(LifecycleOwner lifecycleOwner) {
            return this.a == lifecycleOwner;
        }

        @Override // androidx.lifecycle.LiveData.ObserverWrapper
        void b() {
            this.a.getLifecycle().removeObserver(this);
        }

        @Override // androidx.lifecycle.LifecycleEventObserver
        public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
            Lifecycle.State currentState = this.a.getLifecycle().getCurrentState();
            if (currentState == Lifecycle.State.DESTROYED) {
                LiveData.this.removeObserver(this.c);
                return;
            }
            Lifecycle.State state = null;
            while (state != currentState) {
                a(a());
                state = currentState;
                currentState = this.a.getLifecycle().getCurrentState();
            }
        }
    }

    private abstract class ObserverWrapper {
        final Observer<? super T> c;
        boolean d;
        int e = -1;

        ObserverWrapper(Observer<? super T> observer) {
            this.c = observer;
        }

        void a(boolean z) {
            if (z == this.d) {
                return;
            }
            this.d = z;
            LiveData.this.a(this.d ? 1 : -1);
            if (this.d) {
                LiveData.this.a(this);
            }
        }

        abstract boolean a();

        boolean a(LifecycleOwner lifecycleOwner) {
            return false;
        }

        void b() {
        }
    }

    public LiveData() {
        this.b = new Object();
        this.mObservers = new SafeIterableMap<>();
        this.d = 0;
        this.e = c;
        this.mPostValueRunnable = new Runnable() { // from class: androidx.lifecycle.LiveData.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                Object obj;
                synchronized (LiveData.this.b) {
                    obj = LiveData.this.e;
                    LiveData.this.e = LiveData.c;
                }
                LiveData.this.setValue(obj);
            }
        };
        this.mData = c;
        this.mVersion = -1;
    }

    public LiveData(T t) {
        this.b = new Object();
        this.mObservers = new SafeIterableMap<>();
        this.d = 0;
        this.e = c;
        this.mPostValueRunnable = new Runnable() { // from class: androidx.lifecycle.LiveData.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                Object obj;
                synchronized (LiveData.this.b) {
                    obj = LiveData.this.e;
                    LiveData.this.e = LiveData.c;
                }
                LiveData.this.setValue(obj);
            }
        };
        this.mData = t;
        this.mVersion = 0;
    }

    static void a(String str) {
        if (ArchTaskExecutor.getInstance().isMainThread()) {
            return;
        }
        throw new IllegalStateException("Cannot invoke " + str + " on a background thread");
    }

    private void considerNotify(LiveData<T>.ObserverWrapper observerWrapper) {
        if (observerWrapper.d) {
            if (!observerWrapper.a()) {
                observerWrapper.a(false);
            } else {
                if (observerWrapper.e >= this.mVersion) {
                    return;
                }
                observerWrapper.e = this.mVersion;
                observerWrapper.c.onChanged((Object) this.mData);
            }
        }
    }

    protected void a() {
    }

    @MainThread
    void a(int i) {
        int i2 = this.d;
        this.d += i;
        if (this.mChangingActiveState) {
            return;
        }
        this.mChangingActiveState = true;
        while (true) {
            try {
                if (i2 == this.d) {
                    return;
                }
                boolean z = i2 == 0 && this.d > 0;
                boolean z2 = i2 > 0 && this.d == 0;
                int i3 = this.d;
                if (z) {
                    a();
                } else if (z2) {
                    c();
                }
                i2 = i3;
            } finally {
                this.mChangingActiveState = false;
            }
        }
    }

    void a(@Nullable LiveData<T>.ObserverWrapper observerWrapper) {
        if (this.mDispatchingValue) {
            this.mDispatchInvalidated = true;
            return;
        }
        this.mDispatchingValue = true;
        do {
            this.mDispatchInvalidated = false;
            if (observerWrapper != null) {
                considerNotify(observerWrapper);
                observerWrapper = null;
            } else {
                SafeIterableMap<Observer<? super T>, LiveData<T>.ObserverWrapper>.IteratorWithAdditions iteratorWithAdditions = this.mObservers.iteratorWithAdditions();
                while (iteratorWithAdditions.hasNext()) {
                    considerNotify((ObserverWrapper) iteratorWithAdditions.next().getValue());
                    if (this.mDispatchInvalidated) {
                        break;
                    }
                }
            }
        } while (this.mDispatchInvalidated);
        this.mDispatchingValue = false;
    }

    int b() {
        return this.mVersion;
    }

    protected void c() {
    }

    @Nullable
    public T getValue() {
        T t = (T) this.mData;
        if (t != c) {
            return t;
        }
        return null;
    }

    public boolean hasActiveObservers() {
        return this.d > 0;
    }

    public boolean hasObservers() {
        return this.mObservers.size() > 0;
    }

    @MainThread
    public void observe(@NonNull LifecycleOwner lifecycleOwner, @NonNull Observer<? super T> observer) {
        a("observe");
        if (lifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
            return;
        }
        LifecycleBoundObserver lifecycleBoundObserver = new LifecycleBoundObserver(lifecycleOwner, observer);
        LiveData<T>.ObserverWrapper observerWrapperPutIfAbsent = this.mObservers.putIfAbsent(observer, lifecycleBoundObserver);
        if (observerWrapperPutIfAbsent != null && !observerWrapperPutIfAbsent.a(lifecycleOwner)) {
            throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
        }
        if (observerWrapperPutIfAbsent != null) {
            return;
        }
        lifecycleOwner.getLifecycle().addObserver(lifecycleBoundObserver);
    }

    @MainThread
    public void observeForever(@NonNull Observer<? super T> observer) {
        a("observeForever");
        AlwaysActiveObserver alwaysActiveObserver = new AlwaysActiveObserver(observer);
        LiveData<T>.ObserverWrapper observerWrapperPutIfAbsent = this.mObservers.putIfAbsent(observer, alwaysActiveObserver);
        if (observerWrapperPutIfAbsent instanceof LifecycleBoundObserver) {
            throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
        }
        if (observerWrapperPutIfAbsent != null) {
            return;
        }
        alwaysActiveObserver.a(true);
    }

    protected void postValue(T t) {
        boolean z;
        synchronized (this.b) {
            z = this.e == c;
            this.e = t;
        }
        if (z) {
            ArchTaskExecutor.getInstance().postToMainThread(this.mPostValueRunnable);
        }
    }

    @MainThread
    public void removeObserver(@NonNull Observer<? super T> observer) {
        a("removeObserver");
        LiveData<T>.ObserverWrapper observerWrapperRemove = this.mObservers.remove(observer);
        if (observerWrapperRemove == null) {
            return;
        }
        observerWrapperRemove.b();
        observerWrapperRemove.a(false);
    }

    @MainThread
    public void removeObservers(@NonNull LifecycleOwner lifecycleOwner) {
        a("removeObservers");
        for (Map.Entry<Observer<? super T>, LiveData<T>.ObserverWrapper> entry : this.mObservers) {
            if (entry.getValue().a(lifecycleOwner)) {
                removeObserver(entry.getKey());
            }
        }
    }

    @MainThread
    protected void setValue(T t) {
        a("setValue");
        this.mVersion++;
        this.mData = t;
        a((ObserverWrapper) null);
    }
}
