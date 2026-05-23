package androidx.fragment.app;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class FragmentManagerViewModel extends ViewModel {
    private static final ViewModelProvider.Factory FACTORY = new ViewModelProvider.Factory() { // from class: androidx.fragment.app.FragmentManagerViewModel.1
        @Override // androidx.lifecycle.ViewModelProvider.Factory
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> cls) {
            return new FragmentManagerViewModel(true);
        }
    };
    private static final String TAG = "FragmentManager";
    private final boolean mStateAutomaticallySaved;
    private final HashMap<String, Fragment> mRetainedFragments = new HashMap<>();
    private final HashMap<String, FragmentManagerViewModel> mChildNonConfigs = new HashMap<>();
    private final HashMap<String, ViewModelStore> mViewModelStores = new HashMap<>();
    private boolean mHasBeenCleared = false;
    private boolean mHasSavedSnapshot = false;
    private boolean mIsStateSaved = false;

    FragmentManagerViewModel(boolean z) {
        this.mStateAutomaticallySaved = z;
    }

    @NonNull
    static FragmentManagerViewModel a(ViewModelStore viewModelStore) {
        return (FragmentManagerViewModel) new ViewModelProvider(viewModelStore, FACTORY).get(FragmentManagerViewModel.class);
    }

    @Nullable
    Fragment a(String str) {
        return this.mRetainedFragments.get(str);
    }

    @Override // androidx.lifecycle.ViewModel
    protected void a() {
        if (FragmentManager.a(3)) {
            Log.d(TAG, "onCleared called for " + this);
        }
        this.mHasBeenCleared = true;
    }

    void a(@NonNull Fragment fragment) {
        if (this.mIsStateSaved) {
            if (FragmentManager.a(2)) {
                Log.v(TAG, "Ignoring addRetainedFragment as the state is already saved");
            }
        } else {
            if (this.mRetainedFragments.containsKey(fragment.g)) {
                return;
            }
            this.mRetainedFragments.put(fragment.g, fragment);
            if (FragmentManager.a(2)) {
                Log.v(TAG, "Updating retained Fragments: Added " + fragment);
            }
        }
    }

    @Deprecated
    void a(@Nullable FragmentManagerNonConfig fragmentManagerNonConfig) {
        this.mRetainedFragments.clear();
        this.mChildNonConfigs.clear();
        this.mViewModelStores.clear();
        if (fragmentManagerNonConfig != null) {
            Collection<Fragment> collectionA = fragmentManagerNonConfig.a();
            if (collectionA != null) {
                for (Fragment fragment : collectionA) {
                    if (fragment != null) {
                        this.mRetainedFragments.put(fragment.g, fragment);
                    }
                }
            }
            Map<String, FragmentManagerNonConfig> mapB = fragmentManagerNonConfig.b();
            if (mapB != null) {
                for (Map.Entry<String, FragmentManagerNonConfig> entry : mapB.entrySet()) {
                    FragmentManagerViewModel fragmentManagerViewModel = new FragmentManagerViewModel(this.mStateAutomaticallySaved);
                    fragmentManagerViewModel.a(entry.getValue());
                    this.mChildNonConfigs.put(entry.getKey(), fragmentManagerViewModel);
                }
            }
            Map<String, ViewModelStore> mapC = fragmentManagerNonConfig.c();
            if (mapC != null) {
                this.mViewModelStores.putAll(mapC);
            }
        }
        this.mHasSavedSnapshot = false;
    }

    void a(boolean z) {
        this.mIsStateSaved = z;
    }

    boolean b() {
        return this.mHasBeenCleared;
    }

    boolean b(@NonNull Fragment fragment) {
        if (this.mRetainedFragments.containsKey(fragment.g)) {
            return this.mStateAutomaticallySaved ? this.mHasBeenCleared : !this.mHasSavedSnapshot;
        }
        return true;
    }

    @NonNull
    Collection<Fragment> c() {
        return new ArrayList(this.mRetainedFragments.values());
    }

    void c(@NonNull Fragment fragment) {
        if (this.mIsStateSaved) {
            if (FragmentManager.a(2)) {
                Log.v(TAG, "Ignoring removeRetainedFragment as the state is already saved");
                return;
            }
            return;
        }
        if ((this.mRetainedFragments.remove(fragment.g) != null) && FragmentManager.a(2)) {
            Log.v(TAG, "Updating retained Fragments: Removed " + fragment);
        }
    }

    @Nullable
    @Deprecated
    FragmentManagerNonConfig d() {
        if (this.mRetainedFragments.isEmpty() && this.mChildNonConfigs.isEmpty() && this.mViewModelStores.isEmpty()) {
            return null;
        }
        HashMap map = new HashMap();
        for (Map.Entry<String, FragmentManagerViewModel> entry : this.mChildNonConfigs.entrySet()) {
            FragmentManagerNonConfig fragmentManagerNonConfigD = entry.getValue().d();
            if (fragmentManagerNonConfigD != null) {
                map.put(entry.getKey(), fragmentManagerNonConfigD);
            }
        }
        this.mHasSavedSnapshot = true;
        if (this.mRetainedFragments.isEmpty() && map.isEmpty() && this.mViewModelStores.isEmpty()) {
            return null;
        }
        return new FragmentManagerNonConfig(new ArrayList(this.mRetainedFragments.values()), map, new HashMap(this.mViewModelStores));
    }

    @NonNull
    FragmentManagerViewModel d(@NonNull Fragment fragment) {
        FragmentManagerViewModel fragmentManagerViewModel = this.mChildNonConfigs.get(fragment.g);
        if (fragmentManagerViewModel != null) {
            return fragmentManagerViewModel;
        }
        FragmentManagerViewModel fragmentManagerViewModel2 = new FragmentManagerViewModel(this.mStateAutomaticallySaved);
        this.mChildNonConfigs.put(fragment.g, fragmentManagerViewModel2);
        return fragmentManagerViewModel2;
    }

    @NonNull
    ViewModelStore e(@NonNull Fragment fragment) {
        ViewModelStore viewModelStore = this.mViewModelStores.get(fragment.g);
        if (viewModelStore != null) {
            return viewModelStore;
        }
        ViewModelStore viewModelStore2 = new ViewModelStore();
        this.mViewModelStores.put(fragment.g, viewModelStore2);
        return viewModelStore2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FragmentManagerViewModel fragmentManagerViewModel = (FragmentManagerViewModel) obj;
        return this.mRetainedFragments.equals(fragmentManagerViewModel.mRetainedFragments) && this.mChildNonConfigs.equals(fragmentManagerViewModel.mChildNonConfigs) && this.mViewModelStores.equals(fragmentManagerViewModel.mViewModelStores);
    }

    void f(@NonNull Fragment fragment) {
        if (FragmentManager.a(3)) {
            Log.d(TAG, "Clearing non-config state for " + fragment);
        }
        FragmentManagerViewModel fragmentManagerViewModel = this.mChildNonConfigs.get(fragment.g);
        if (fragmentManagerViewModel != null) {
            fragmentManagerViewModel.a();
            this.mChildNonConfigs.remove(fragment.g);
        }
        ViewModelStore viewModelStore = this.mViewModelStores.get(fragment.g);
        if (viewModelStore != null) {
            viewModelStore.clear();
            this.mViewModelStores.remove(fragment.g);
        }
    }

    public int hashCode() {
        return (((this.mRetainedFragments.hashCode() * 31) + this.mChildNonConfigs.hashCode()) * 31) + this.mViewModelStores.hashCode();
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder("FragmentManagerViewModel{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append("} Fragments (");
        Iterator<Fragment> it = this.mRetainedFragments.values().iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(") Child Non Config (");
        Iterator<String> it2 = this.mChildNonConfigs.keySet().iterator();
        while (it2.hasNext()) {
            sb.append(it2.next());
            if (it2.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(") ViewModelStores (");
        Iterator<String> it3 = this.mViewModelStores.keySet().iterator();
        while (it3.hasNext()) {
            sb.append(it3.next());
            if (it3.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(')');
        return sb.toString();
    }
}
