package androidx.lifecycle;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
final class SavedStateHandleController implements LifecycleEventObserver {
    private final SavedStateHandle mHandle;
    private boolean mIsAttached = false;
    private final String mKey;

    static final class OnRecreation implements SavedStateRegistry.AutoRecreated {
        OnRecreation() {
        }

        @Override // androidx.savedstate.SavedStateRegistry.AutoRecreated
        public void onRecreated(@NonNull SavedStateRegistryOwner savedStateRegistryOwner) {
            if (!(savedStateRegistryOwner instanceof ViewModelStoreOwner)) {
                throw new IllegalStateException("Internal error: OnRecreation should be registered only on componentsthat implement ViewModelStoreOwner");
            }
            ViewModelStore viewModelStore = ((ViewModelStoreOwner) savedStateRegistryOwner).getViewModelStore();
            SavedStateRegistry savedStateRegistry = savedStateRegistryOwner.getSavedStateRegistry();
            Iterator<String> it = viewModelStore.a().iterator();
            while (it.hasNext()) {
                SavedStateHandleController.a(viewModelStore.a(it.next()), savedStateRegistry, savedStateRegistryOwner.getLifecycle());
            }
            if (viewModelStore.a().isEmpty()) {
                return;
            }
            savedStateRegistry.runOnNextRecreation(OnRecreation.class);
        }
    }

    SavedStateHandleController(String str, SavedStateHandle savedStateHandle) {
        this.mKey = str;
        this.mHandle = savedStateHandle;
    }

    static SavedStateHandleController a(SavedStateRegistry savedStateRegistry, Lifecycle lifecycle, String str, Bundle bundle) {
        SavedStateHandleController savedStateHandleController = new SavedStateHandleController(str, SavedStateHandle.a(savedStateRegistry.consumeRestoredStateForKey(str), bundle));
        savedStateHandleController.a(savedStateRegistry, lifecycle);
        tryToAddRecreator(savedStateRegistry, lifecycle);
        return savedStateHandleController;
    }

    static void a(ViewModel viewModel, SavedStateRegistry savedStateRegistry, Lifecycle lifecycle) {
        SavedStateHandleController savedStateHandleController = (SavedStateHandleController) viewModel.b("androidx.lifecycle.savedstate.vm.tag");
        if (savedStateHandleController == null || savedStateHandleController.a()) {
            return;
        }
        savedStateHandleController.a(savedStateRegistry, lifecycle);
        tryToAddRecreator(savedStateRegistry, lifecycle);
    }

    private static void tryToAddRecreator(final SavedStateRegistry savedStateRegistry, final Lifecycle lifecycle) {
        Lifecycle.State currentState = lifecycle.getCurrentState();
        if (currentState == Lifecycle.State.INITIALIZED || currentState.isAtLeast(Lifecycle.State.STARTED)) {
            savedStateRegistry.runOnNextRecreation(OnRecreation.class);
        } else {
            lifecycle.addObserver(new LifecycleEventObserver() { // from class: androidx.lifecycle.SavedStateHandleController.1
                @Override // androidx.lifecycle.LifecycleEventObserver
                public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
                    if (event == Lifecycle.Event.ON_START) {
                        lifecycle.removeObserver(this);
                        savedStateRegistry.runOnNextRecreation(OnRecreation.class);
                    }
                }
            });
        }
    }

    void a(SavedStateRegistry savedStateRegistry, Lifecycle lifecycle) {
        if (this.mIsAttached) {
            throw new IllegalStateException("Already attached to lifecycleOwner");
        }
        this.mIsAttached = true;
        lifecycle.addObserver(this);
        savedStateRegistry.registerSavedStateProvider(this.mKey, this.mHandle.a());
    }

    boolean a() {
        return this.mIsAttached;
    }

    SavedStateHandle b() {
        return this.mHandle;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            this.mIsAttached = false;
            lifecycleOwner.getLifecycle().removeObserver(this);
        }
    }
}
