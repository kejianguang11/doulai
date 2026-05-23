package androidx.savedstate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.savedstate.Recreator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"RestrictedApi"})
public final class SavedStateRegistry {
    private static final String SAVED_COMPONENTS_KEY = "androidx.lifecycle.BundlableSavedStateRegistry.key";
    private Recreator.SavedStateProvider mRecreatorProvider;
    private boolean mRestored;

    @Nullable
    private Bundle mRestoredState;
    private SafeIterableMap<String, SavedStateProvider> mComponents = new SafeIterableMap<>();
    boolean a = true;

    public interface AutoRecreated {
        void onRecreated(@NonNull SavedStateRegistryOwner savedStateRegistryOwner);
    }

    public interface SavedStateProvider {
        @NonNull
        Bundle saveState();
    }

    SavedStateRegistry() {
    }

    @MainThread
    void a(@NonNull Bundle bundle) {
        Bundle bundle2 = new Bundle();
        if (this.mRestoredState != null) {
            bundle2.putAll(this.mRestoredState);
        }
        SafeIterableMap<String, SavedStateProvider>.IteratorWithAdditions iteratorWithAdditions = this.mComponents.iteratorWithAdditions();
        while (iteratorWithAdditions.hasNext()) {
            Map.Entry next = iteratorWithAdditions.next();
            bundle2.putBundle((String) next.getKey(), ((SavedStateProvider) next.getValue()).saveState());
        }
        bundle.putBundle(SAVED_COMPONENTS_KEY, bundle2);
    }

    @MainThread
    void a(@NonNull Lifecycle lifecycle, @Nullable Bundle bundle) {
        if (this.mRestored) {
            throw new IllegalStateException("SavedStateRegistry was already restored.");
        }
        if (bundle != null) {
            this.mRestoredState = bundle.getBundle(SAVED_COMPONENTS_KEY);
        }
        lifecycle.addObserver(new GenericLifecycleObserver() { // from class: androidx.savedstate.SavedStateRegistry.1
            @Override // androidx.lifecycle.LifecycleEventObserver
            public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                SavedStateRegistry savedStateRegistry;
                boolean z;
                if (event == Lifecycle.Event.ON_START) {
                    savedStateRegistry = SavedStateRegistry.this;
                    z = true;
                } else {
                    if (event != Lifecycle.Event.ON_STOP) {
                        return;
                    }
                    savedStateRegistry = SavedStateRegistry.this;
                    z = false;
                }
                savedStateRegistry.a = z;
            }
        });
        this.mRestored = true;
    }

    @Nullable
    @MainThread
    public Bundle consumeRestoredStateForKey(@NonNull String str) {
        if (!this.mRestored) {
            throw new IllegalStateException("You can consumeRestoredStateForKey only after super.onCreate of corresponding component");
        }
        if (this.mRestoredState == null) {
            return null;
        }
        Bundle bundle = this.mRestoredState.getBundle(str);
        this.mRestoredState.remove(str);
        if (this.mRestoredState.isEmpty()) {
            this.mRestoredState = null;
        }
        return bundle;
    }

    @MainThread
    public boolean isRestored() {
        return this.mRestored;
    }

    @MainThread
    public void registerSavedStateProvider(@NonNull String str, @NonNull SavedStateProvider savedStateProvider) {
        if (this.mComponents.putIfAbsent(str, savedStateProvider) != null) {
            throw new IllegalArgumentException("SavedStateProvider with the given key is already registered");
        }
    }

    @MainThread
    public void runOnNextRecreation(@NonNull Class<? extends AutoRecreated> cls) {
        if (!this.a) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
        if (this.mRecreatorProvider == null) {
            this.mRecreatorProvider = new Recreator.SavedStateProvider(this);
        }
        try {
            cls.getDeclaredConstructor(new Class[0]);
            this.mRecreatorProvider.a(cls.getName());
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Class" + cls.getSimpleName() + " must have default constructor in order to be automatically recreated", e);
        }
    }

    @MainThread
    public void unregisterSavedStateProvider(@NonNull String str) {
        this.mComponents.remove(str);
    }
}
