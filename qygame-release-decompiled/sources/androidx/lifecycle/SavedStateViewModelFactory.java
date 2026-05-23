package androidx.lifecycle;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class SavedStateViewModelFactory extends ViewModelProvider.KeyedFactory {
    private static final Class<?>[] ANDROID_VIEWMODEL_SIGNATURE = {Application.class, SavedStateHandle.class};
    private static final Class<?>[] VIEWMODEL_SIGNATURE = {SavedStateHandle.class};
    private final Application mApplication;
    private final Bundle mDefaultArgs;
    private final ViewModelProvider.Factory mFactory;
    private final Lifecycle mLifecycle;
    private final SavedStateRegistry mSavedStateRegistry;

    public SavedStateViewModelFactory(@Nullable Application application, @NonNull SavedStateRegistryOwner savedStateRegistryOwner) {
        this(application, savedStateRegistryOwner, null);
    }

    @SuppressLint({"LambdaLast"})
    public SavedStateViewModelFactory(@Nullable Application application, @NonNull SavedStateRegistryOwner savedStateRegistryOwner, @Nullable Bundle bundle) {
        this.mSavedStateRegistry = savedStateRegistryOwner.getSavedStateRegistry();
        this.mLifecycle = savedStateRegistryOwner.getLifecycle();
        this.mDefaultArgs = bundle;
        this.mApplication = application;
        this.mFactory = application != null ? ViewModelProvider.AndroidViewModelFactory.getInstance(application) : ViewModelProvider.NewInstanceFactory.a();
    }

    private static <T> Constructor<T> findMatchingConstructor(Class<T> cls, Class<?>[] clsArr) {
        for (Object obj : cls.getConstructors()) {
            Constructor<T> constructor = (Constructor<T>) obj;
            if (Arrays.equals(clsArr, constructor.getParameterTypes())) {
                return constructor;
            }
        }
        return null;
    }

    @Override // androidx.lifecycle.ViewModelProvider.OnRequeryFactory
    void a(@NonNull ViewModel viewModel) {
        SavedStateHandleController.a(viewModel, this.mSavedStateRegistry, this.mLifecycle);
    }

    @Override // androidx.lifecycle.ViewModelProvider.KeyedFactory, androidx.lifecycle.ViewModelProvider.Factory
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> cls) {
        String canonicalName = cls.getCanonicalName();
        if (canonicalName != null) {
            return (T) create(canonicalName, cls);
        }
        throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x004a A[Catch: InvocationTargetException -> 0x0044, InstantiationException -> 0x0046, IllegalAccessException -> 0x0048, TryCatch #2 {IllegalAccessException -> 0x0048, InstantiationException -> 0x0046, InvocationTargetException -> 0x0044, blocks: (B:14:0x002c, B:16:0x0030, B:17:0x0041, B:25:0x0057, B:24:0x004a), top: B:33:0x002c }] */
    @Override // androidx.lifecycle.ViewModelProvider.KeyedFactory
    @NonNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public <T extends ViewModel> T create(@NonNull String str, @NonNull Class<T> cls) {
        Object objNewInstance;
        boolean zIsAssignableFrom = AndroidViewModel.class.isAssignableFrom(cls);
        Constructor constructorFindMatchingConstructor = findMatchingConstructor(cls, (!zIsAssignableFrom || this.mApplication == null) ? VIEWMODEL_SIGNATURE : ANDROID_VIEWMODEL_SIGNATURE);
        if (constructorFindMatchingConstructor == null) {
            return (T) this.mFactory.create(cls);
        }
        SavedStateHandleController savedStateHandleControllerA = SavedStateHandleController.a(this.mSavedStateRegistry, this.mLifecycle, str, this.mDefaultArgs);
        if (zIsAssignableFrom) {
            try {
                objNewInstance = this.mApplication != null ? constructorFindMatchingConstructor.newInstance(this.mApplication, savedStateHandleControllerA.b()) : constructorFindMatchingConstructor.newInstance(savedStateHandleControllerA.b());
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to access " + cls, e);
            } catch (InstantiationException e2) {
                throw new RuntimeException("A " + cls + " cannot be instantiated.", e2);
            } catch (InvocationTargetException e3) {
                throw new RuntimeException("An exception happened in constructor of " + cls, e3.getCause());
            }
        }
        T t = (T) objNewInstance;
        t.a("androidx.lifecycle.savedstate.vm.tag", savedStateHandleControllerA);
        return t;
    }
}
