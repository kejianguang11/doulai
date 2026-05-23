package androidx.fragment.app;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;

/* JADX INFO: loaded from: classes.dex */
class FragmentViewLifecycleOwner implements HasDefaultViewModelProviderFactory, ViewModelStoreOwner, SavedStateRegistryOwner {
    private ViewModelProvider.Factory mDefaultFactory;
    private final Fragment mFragment;
    private LifecycleRegistry mLifecycleRegistry = null;
    private SavedStateRegistryController mSavedStateRegistryController = null;
    private final ViewModelStore mViewModelStore;

    FragmentViewLifecycleOwner(@NonNull Fragment fragment, @NonNull ViewModelStore viewModelStore) {
        this.mFragment = fragment;
        this.mViewModelStore = viewModelStore;
    }

    void a() {
        if (this.mLifecycleRegistry == null) {
            this.mLifecycleRegistry = new LifecycleRegistry(this);
            this.mSavedStateRegistryController = SavedStateRegistryController.create(this);
        }
    }

    void a(@Nullable Bundle bundle) {
        this.mSavedStateRegistryController.performRestore(bundle);
    }

    void a(@NonNull Lifecycle.Event event) {
        this.mLifecycleRegistry.handleLifecycleEvent(event);
    }

    void a(@NonNull Lifecycle.State state) {
        this.mLifecycleRegistry.setCurrentState(state);
    }

    void b(@NonNull Bundle bundle) {
        this.mSavedStateRegistryController.performSave(bundle);
    }

    boolean b() {
        return this.mLifecycleRegistry != null;
    }

    @Override // androidx.lifecycle.HasDefaultViewModelProviderFactory
    @NonNull
    public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        ViewModelProvider.Factory defaultViewModelProviderFactory = this.mFragment.getDefaultViewModelProviderFactory();
        if (!defaultViewModelProviderFactory.equals(this.mFragment.U)) {
            this.mDefaultFactory = defaultViewModelProviderFactory;
            return defaultViewModelProviderFactory;
        }
        if (this.mDefaultFactory == null) {
            Application application = null;
            Context applicationContext = this.mFragment.requireContext().getApplicationContext();
            while (true) {
                if (!(applicationContext instanceof ContextWrapper)) {
                    break;
                }
                if (applicationContext instanceof Application) {
                    application = (Application) applicationContext;
                    break;
                }
                applicationContext = ((ContextWrapper) applicationContext).getBaseContext();
            }
            this.mDefaultFactory = new SavedStateViewModelFactory(application, this, this.mFragment.getArguments());
        }
        return this.mDefaultFactory;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    @NonNull
    public Lifecycle getLifecycle() {
        a();
        return this.mLifecycleRegistry;
    }

    @Override // androidx.savedstate.SavedStateRegistryOwner
    @NonNull
    public SavedStateRegistry getSavedStateRegistry() {
        a();
        return this.mSavedStateRegistryController.getSavedStateRegistry();
    }

    @Override // androidx.lifecycle.ViewModelStoreOwner
    @NonNull
    public ViewModelStore getViewModelStore() {
        a();
        return this.mViewModelStore;
    }
}
