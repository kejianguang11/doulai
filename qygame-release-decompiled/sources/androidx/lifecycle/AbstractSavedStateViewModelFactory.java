package androidx.lifecycle;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.lifecycle.ViewModelProvider;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;

/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractSavedStateViewModelFactory extends ViewModelProvider.KeyedFactory {
    private final Bundle mDefaultArgs;
    private final Lifecycle mLifecycle;
    private final SavedStateRegistry mSavedStateRegistry;

    public AbstractSavedStateViewModelFactory(@NonNull SavedStateRegistryOwner savedStateRegistryOwner, @Nullable Bundle bundle) {
        this.mSavedStateRegistry = savedStateRegistryOwner.getSavedStateRegistry();
        this.mLifecycle = savedStateRegistryOwner.getLifecycle();
        this.mDefaultArgs = bundle;
    }

    @NonNull
    protected abstract <T extends ViewModel> T a(@NonNull String str, @NonNull Class<T> cls, @NonNull SavedStateHandle savedStateHandle);

    @Override // androidx.lifecycle.ViewModelProvider.OnRequeryFactory
    void a(@NonNull ViewModel viewModel) {
        SavedStateHandleController.a(viewModel, this.mSavedStateRegistry, this.mLifecycle);
    }

    @Override // androidx.lifecycle.ViewModelProvider.KeyedFactory, androidx.lifecycle.ViewModelProvider.Factory
    @NonNull
    public final <T extends ViewModel> T create(@NonNull Class<T> cls) {
        String canonicalName = cls.getCanonicalName();
        if (canonicalName != null) {
            return (T) create(canonicalName, cls);
        }
        throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
    }

    @Override // androidx.lifecycle.ViewModelProvider.KeyedFactory
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final <T extends ViewModel> T create(@NonNull String str, @NonNull Class<T> cls) {
        SavedStateHandleController savedStateHandleControllerA = SavedStateHandleController.a(this.mSavedStateRegistry, this.mLifecycle, str, this.mDefaultArgs);
        T t = (T) a(str, cls, savedStateHandleControllerA.b());
        t.a("androidx.lifecycle.savedstate.vm.tag", savedStateHandleControllerA);
        return t;
    }
}
