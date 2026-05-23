package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.SimpleArrayMap;
import androidx.core.util.Preconditions;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.loader.app.LoaderManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class FragmentController {
    private final FragmentHostCallback<?> mHost;

    private FragmentController(FragmentHostCallback<?> fragmentHostCallback) {
        this.mHost = fragmentHostCallback;
    }

    @NonNull
    public static FragmentController createController(@NonNull FragmentHostCallback<?> fragmentHostCallback) {
        return new FragmentController((FragmentHostCallback) Preconditions.checkNotNull(fragmentHostCallback, "callbacks == null"));
    }

    public void attachHost(@Nullable Fragment fragment) {
        this.mHost.b.a(this.mHost, this.mHost, fragment);
    }

    public void dispatchActivityCreated() {
        this.mHost.b.p();
    }

    public void dispatchConfigurationChanged(@NonNull Configuration configuration) {
        this.mHost.b.a(configuration);
    }

    public boolean dispatchContextItemSelected(@NonNull MenuItem menuItem) {
        return this.mHost.b.b(menuItem);
    }

    public void dispatchCreate() {
        this.mHost.b.n();
    }

    public boolean dispatchCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        return this.mHost.b.a(menu, menuInflater);
    }

    public void dispatchDestroy() {
        this.mHost.b.v();
    }

    public void dispatchDestroyView() {
        this.mHost.b.u();
    }

    public void dispatchLowMemory() {
        this.mHost.b.w();
    }

    public void dispatchMultiWindowModeChanged(boolean z) {
        this.mHost.b.b(z);
    }

    public boolean dispatchOptionsItemSelected(@NonNull MenuItem menuItem) {
        return this.mHost.b.a(menuItem);
    }

    public void dispatchOptionsMenuClosed(@NonNull Menu menu) {
        this.mHost.b.b(menu);
    }

    public void dispatchPause() {
        this.mHost.b.s();
    }

    public void dispatchPictureInPictureModeChanged(boolean z) {
        this.mHost.b.c(z);
    }

    public boolean dispatchPrepareOptionsMenu(@NonNull Menu menu) {
        return this.mHost.b.a(menu);
    }

    @Deprecated
    public void dispatchReallyStop() {
    }

    public void dispatchResume() {
        this.mHost.b.r();
    }

    public void dispatchStart() {
        this.mHost.b.q();
    }

    public void dispatchStop() {
        this.mHost.b.t();
    }

    @Deprecated
    public void doLoaderDestroy() {
    }

    @Deprecated
    public void doLoaderRetain() {
    }

    @Deprecated
    public void doLoaderStart() {
    }

    @Deprecated
    public void doLoaderStop(boolean z) {
    }

    @Deprecated
    public void dumpLoaders(@NonNull String str, @Nullable FileDescriptor fileDescriptor, @NonNull PrintWriter printWriter, @Nullable String[] strArr) {
    }

    public boolean execPendingActions() {
        return this.mHost.b.a(true);
    }

    @Nullable
    public Fragment findFragmentByWho(@NonNull String str) {
        return this.mHost.b.a(str);
    }

    @NonNull
    public List<Fragment> getActiveFragments(@SuppressLint({"UnknownNullness"}) List<Fragment> list) {
        return this.mHost.b.b();
    }

    public int getActiveFragmentsCount() {
        return this.mHost.b.c();
    }

    @NonNull
    public FragmentManager getSupportFragmentManager() {
        return this.mHost.b;
    }

    @SuppressLint({"UnknownNullness"})
    @Deprecated
    public LoaderManager getSupportLoaderManager() {
        throw new UnsupportedOperationException("Loaders are managed separately from FragmentController, use LoaderManager.getInstance() to obtain a LoaderManager.");
    }

    public void noteStateNotSaved() {
        this.mHost.b.l();
    }

    @Nullable
    public View onCreateView(@Nullable View view, @NonNull String str, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        return this.mHost.b.B().onCreateView(view, str, context, attributeSet);
    }

    @Deprecated
    public void reportLoaderStart() {
    }

    @Deprecated
    public void restoreAllState(@Nullable Parcelable parcelable, @Nullable FragmentManagerNonConfig fragmentManagerNonConfig) {
        this.mHost.b.a(parcelable, fragmentManagerNonConfig);
    }

    @Deprecated
    public void restoreAllState(@Nullable Parcelable parcelable, @Nullable List<Fragment> list) {
        this.mHost.b.a(parcelable, new FragmentManagerNonConfig(list, null, null));
    }

    @Deprecated
    public void restoreLoaderNonConfig(@SuppressLint({"UnknownNullness"}) SimpleArrayMap<String, LoaderManager> simpleArrayMap) {
    }

    public void restoreSaveState(@Nullable Parcelable parcelable) {
        if (!(this.mHost instanceof ViewModelStoreOwner)) {
            throw new IllegalStateException("Your FragmentHostCallback must implement ViewModelStoreOwner to call restoreSaveState(). Call restoreAllState()  if you're still using retainNestedNonConfig().");
        }
        this.mHost.b.a(parcelable);
    }

    @Nullable
    @Deprecated
    public SimpleArrayMap<String, LoaderManager> retainLoaderNonConfig() {
        return null;
    }

    @Nullable
    @Deprecated
    public FragmentManagerNonConfig retainNestedNonConfig() {
        return this.mHost.b.f();
    }

    @Nullable
    @Deprecated
    public List<Fragment> retainNonConfig() {
        FragmentManagerNonConfig fragmentManagerNonConfigF = this.mHost.b.f();
        if (fragmentManagerNonConfigF == null || fragmentManagerNonConfigF.a() == null) {
            return null;
        }
        return new ArrayList(fragmentManagerNonConfigF.a());
    }

    @Nullable
    public Parcelable saveAllState() {
        return this.mHost.b.g();
    }
}
