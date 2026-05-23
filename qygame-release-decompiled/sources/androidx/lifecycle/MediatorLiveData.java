package androidx.lifecycle;

import androidx.annotation.CallSuper;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.internal.SafeIterableMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class MediatorLiveData<T> extends MutableLiveData<T> {
    private SafeIterableMap<LiveData<?>, Source<?>> mSources = new SafeIterableMap<>();

    private static class Source<V> implements Observer<V> {
        final LiveData<V> a;
        final Observer<? super V> b;
        int c = -1;

        Source(LiveData<V> liveData, Observer<? super V> observer) {
            this.a = liveData;
            this.b = observer;
        }

        void a() {
            this.a.observeForever(this);
        }

        void b() {
            this.a.removeObserver(this);
        }

        @Override // androidx.lifecycle.Observer
        public void onChanged(@Nullable V v) {
            if (this.c != this.a.b()) {
                this.c = this.a.b();
                this.b.onChanged(v);
            }
        }
    }

    @Override // androidx.lifecycle.LiveData
    @CallSuper
    protected void a() {
        Iterator<Map.Entry<LiveData<?>, Source<?>>> it = this.mSources.iterator();
        while (it.hasNext()) {
            it.next().getValue().a();
        }
    }

    @MainThread
    public <S> void addSource(@NonNull LiveData<S> liveData, @NonNull Observer<? super S> observer) {
        Source<?> source = new Source<>(liveData, observer);
        Source<?> sourcePutIfAbsent = this.mSources.putIfAbsent(liveData, source);
        if (sourcePutIfAbsent != null && sourcePutIfAbsent.b != observer) {
            throw new IllegalArgumentException("This source was already added with the different observer");
        }
        if (sourcePutIfAbsent == null && hasActiveObservers()) {
            source.a();
        }
    }

    @Override // androidx.lifecycle.LiveData
    @CallSuper
    protected void c() {
        Iterator<Map.Entry<LiveData<?>, Source<?>>> it = this.mSources.iterator();
        while (it.hasNext()) {
            it.next().getValue().b();
        }
    }

    @MainThread
    public <S> void removeSource(@NonNull LiveData<S> liveData) {
        Source<?> sourceRemove = this.mSources.remove(liveData);
        if (sourceRemove != null) {
            sourceRemove.b();
        }
    }
}
