package androidx.activity;

import android.annotation.SuppressLint;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.ArrayDeque;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public final class OnBackPressedDispatcher {
    final ArrayDeque<OnBackPressedCallback> a;

    @Nullable
    private final Runnable mFallbackOnBackPressed;

    private class LifecycleOnBackPressedCancellable implements Cancellable, LifecycleEventObserver {

        @Nullable
        private Cancellable mCurrentCancellable;
        private final Lifecycle mLifecycle;
        private final OnBackPressedCallback mOnBackPressedCallback;

        LifecycleOnBackPressedCancellable(Lifecycle lifecycle, @NonNull OnBackPressedCallback onBackPressedCallback) {
            this.mLifecycle = lifecycle;
            this.mOnBackPressedCallback = onBackPressedCallback;
            lifecycle.addObserver(this);
        }

        @Override // androidx.activity.Cancellable
        public void cancel() {
            this.mLifecycle.removeObserver(this);
            this.mOnBackPressedCallback.b(this);
            if (this.mCurrentCancellable != null) {
                this.mCurrentCancellable.cancel();
                this.mCurrentCancellable = null;
            }
        }

        @Override // androidx.lifecycle.LifecycleEventObserver
        public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_START) {
                this.mCurrentCancellable = OnBackPressedDispatcher.this.a(this.mOnBackPressedCallback);
                return;
            }
            if (event == Lifecycle.Event.ON_STOP) {
                if (this.mCurrentCancellable != null) {
                    this.mCurrentCancellable.cancel();
                }
            } else if (event == Lifecycle.Event.ON_DESTROY) {
                cancel();
            }
        }
    }

    private class OnBackPressedCancellable implements Cancellable {
        private final OnBackPressedCallback mOnBackPressedCallback;

        OnBackPressedCancellable(OnBackPressedCallback onBackPressedCallback) {
            this.mOnBackPressedCallback = onBackPressedCallback;
        }

        @Override // androidx.activity.Cancellable
        public void cancel() {
            OnBackPressedDispatcher.this.a.remove(this.mOnBackPressedCallback);
            this.mOnBackPressedCallback.b(this);
        }
    }

    public OnBackPressedDispatcher() {
        this(null);
    }

    public OnBackPressedDispatcher(@Nullable Runnable runnable) {
        this.a = new ArrayDeque<>();
        this.mFallbackOnBackPressed = runnable;
    }

    @NonNull
    @MainThread
    Cancellable a(@NonNull OnBackPressedCallback onBackPressedCallback) {
        this.a.add(onBackPressedCallback);
        OnBackPressedCancellable onBackPressedCancellable = new OnBackPressedCancellable(onBackPressedCallback);
        onBackPressedCallback.a(onBackPressedCancellable);
        return onBackPressedCancellable;
    }

    @MainThread
    public void addCallback(@NonNull OnBackPressedCallback onBackPressedCallback) {
        a(onBackPressedCallback);
    }

    @SuppressLint({"LambdaLast"})
    @MainThread
    public void addCallback(@NonNull LifecycleOwner lifecycleOwner, @NonNull OnBackPressedCallback onBackPressedCallback) {
        Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
            return;
        }
        onBackPressedCallback.a(new LifecycleOnBackPressedCancellable(lifecycle, onBackPressedCallback));
    }

    @MainThread
    public boolean hasEnabledCallbacks() {
        Iterator<OnBackPressedCallback> itDescendingIterator = this.a.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            if (itDescendingIterator.next().isEnabled()) {
                return true;
            }
        }
        return false;
    }

    @MainThread
    public void onBackPressed() {
        Iterator<OnBackPressedCallback> itDescendingIterator = this.a.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            OnBackPressedCallback next = itDescendingIterator.next();
            if (next.isEnabled()) {
                next.handleOnBackPressed();
                return;
            }
        }
        if (this.mFallbackOnBackPressed != null) {
            this.mFallbackOnBackPressed.run();
        }
    }
}
