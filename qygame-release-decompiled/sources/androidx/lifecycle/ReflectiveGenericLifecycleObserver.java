package androidx.lifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ClassesInfoCache;
import androidx.lifecycle.Lifecycle;

/* JADX INFO: loaded from: classes.dex */
class ReflectiveGenericLifecycleObserver implements LifecycleEventObserver {
    private final ClassesInfoCache.CallbackInfo mInfo;
    private final Object mWrapped;

    ReflectiveGenericLifecycleObserver(Object obj) {
        this.mWrapped = obj;
        this.mInfo = ClassesInfoCache.a.b(this.mWrapped.getClass());
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
        this.mInfo.a(lifecycleOwner, event, this.mWrapped);
    }
}
