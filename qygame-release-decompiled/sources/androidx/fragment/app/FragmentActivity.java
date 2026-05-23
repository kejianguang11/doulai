package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import androidx.activity.ComponentActivity;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.activity.contextaware.OnContextAvailableListener;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.ActivityResultRegistryOwner;
import androidx.annotation.CallSuper;
import androidx.annotation.ContentView;
import androidx.annotation.LayoutRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.app.ActivityCompat;
import androidx.core.app.SharedElementCallback;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.loader.app.LoaderManager;
import androidx.savedstate.SavedStateRegistry;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class FragmentActivity extends ComponentActivity implements ActivityCompat.OnRequestPermissionsResultCallback, ActivityCompat.RequestPermissionsRequestCodeValidator {
    final FragmentController c;
    final LifecycleRegistry d;
    boolean e;
    boolean f;
    boolean g;

    class HostCallbacks extends FragmentHostCallback<FragmentActivity> implements OnBackPressedDispatcherOwner, ActivityResultRegistryOwner, FragmentOnAttachListener, ViewModelStoreOwner {
        public HostCallbacks() {
            super(FragmentActivity.this);
        }

        @Override // androidx.activity.result.ActivityResultRegistryOwner
        @NonNull
        public ActivityResultRegistry getActivityResultRegistry() {
            return FragmentActivity.this.getActivityResultRegistry();
        }

        @Override // androidx.lifecycle.LifecycleOwner
        @NonNull
        public Lifecycle getLifecycle() {
            return FragmentActivity.this.d;
        }

        @Override // androidx.activity.OnBackPressedDispatcherOwner
        @NonNull
        public OnBackPressedDispatcher getOnBackPressedDispatcher() {
            return FragmentActivity.this.getOnBackPressedDispatcher();
        }

        @Override // androidx.lifecycle.ViewModelStoreOwner
        @NonNull
        public ViewModelStore getViewModelStore() {
            return FragmentActivity.this.getViewModelStore();
        }

        @Override // androidx.fragment.app.FragmentOnAttachListener
        public void onAttachFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
            FragmentActivity.this.onAttachFragment(fragment);
        }

        @Override // androidx.fragment.app.FragmentHostCallback
        public void onDump(@NonNull String str, @Nullable FileDescriptor fileDescriptor, @NonNull PrintWriter printWriter, @Nullable String[] strArr) {
            FragmentActivity.this.dump(str, fileDescriptor, printWriter, strArr);
        }

        @Override // androidx.fragment.app.FragmentHostCallback, androidx.fragment.app.FragmentContainer
        @Nullable
        public View onFindViewById(int i) {
            return FragmentActivity.this.findViewById(i);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.fragment.app.FragmentHostCallback
        public FragmentActivity onGetHost() {
            return FragmentActivity.this;
        }

        @Override // androidx.fragment.app.FragmentHostCallback
        @NonNull
        public LayoutInflater onGetLayoutInflater() {
            return FragmentActivity.this.getLayoutInflater().cloneInContext(FragmentActivity.this);
        }

        @Override // androidx.fragment.app.FragmentHostCallback
        public int onGetWindowAnimations() {
            Window window = FragmentActivity.this.getWindow();
            if (window == null) {
                return 0;
            }
            return window.getAttributes().windowAnimations;
        }

        @Override // androidx.fragment.app.FragmentHostCallback, androidx.fragment.app.FragmentContainer
        public boolean onHasView() {
            Window window = FragmentActivity.this.getWindow();
            return (window == null || window.peekDecorView() == null) ? false : true;
        }

        @Override // androidx.fragment.app.FragmentHostCallback
        public boolean onHasWindowAnimations() {
            return FragmentActivity.this.getWindow() != null;
        }

        @Override // androidx.fragment.app.FragmentHostCallback
        public boolean onShouldSaveFragmentState(@NonNull Fragment fragment) {
            return !FragmentActivity.this.isFinishing();
        }

        @Override // androidx.fragment.app.FragmentHostCallback
        public boolean onShouldShowRequestPermissionRationale(@NonNull String str) {
            return ActivityCompat.shouldShowRequestPermissionRationale(FragmentActivity.this, str);
        }

        @Override // androidx.fragment.app.FragmentHostCallback
        public void onSupportInvalidateOptionsMenu() {
            FragmentActivity.this.supportInvalidateOptionsMenu();
        }
    }

    public FragmentActivity() {
        this.c = FragmentController.createController(new HostCallbacks());
        this.d = new LifecycleRegistry(this);
        this.g = true;
        init();
    }

    @ContentView
    public FragmentActivity(@LayoutRes int i) {
        super(i);
        this.c = FragmentController.createController(new HostCallbacks());
        this.d = new LifecycleRegistry(this);
        this.g = true;
        init();
    }

    private void init() {
        getSavedStateRegistry().registerSavedStateProvider("android:support:fragments", new SavedStateRegistry.SavedStateProvider() { // from class: androidx.fragment.app.FragmentActivity.1
            @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
            @NonNull
            public Bundle saveState() {
                Bundle bundle = new Bundle();
                FragmentActivity.this.c();
                FragmentActivity.this.d.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
                Parcelable parcelableSaveAllState = FragmentActivity.this.c.saveAllState();
                if (parcelableSaveAllState != null) {
                    bundle.putParcelable("android:support:fragments", parcelableSaveAllState);
                }
                return bundle;
            }
        });
        addOnContextAvailableListener(new OnContextAvailableListener() { // from class: androidx.fragment.app.FragmentActivity.2
            @Override // androidx.activity.contextaware.OnContextAvailableListener
            public void onContextAvailable(@NonNull Context context) {
                FragmentActivity.this.c.attachHost(null);
                Bundle bundleConsumeRestoredStateForKey = FragmentActivity.this.getSavedStateRegistry().consumeRestoredStateForKey("android:support:fragments");
                if (bundleConsumeRestoredStateForKey != null) {
                    FragmentActivity.this.c.restoreSaveState(bundleConsumeRestoredStateForKey.getParcelable("android:support:fragments"));
                }
            }
        });
    }

    private static boolean markState(FragmentManager fragmentManager, Lifecycle.State state) {
        boolean zMarkState = false;
        for (Fragment fragment : fragmentManager.getFragments()) {
            if (fragment != null) {
                if (fragment.getHost() != null) {
                    zMarkState |= markState(fragment.getChildFragmentManager(), state);
                }
                if (fragment.S != null && fragment.S.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                    fragment.S.a(state);
                    zMarkState = true;
                }
                if (fragment.R.getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                    fragment.R.setCurrentState(state);
                    zMarkState = true;
                }
            }
        }
        return zMarkState;
    }

    @Nullable
    final View a(@Nullable View view, @NonNull String str, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        return this.c.onCreateView(view, str, context, attributeSet);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Deprecated
    protected boolean a(@Nullable View view, @NonNull Menu menu) {
        return super.onPreparePanel(0, view, menu);
    }

    protected void b() {
        this.d.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        this.c.dispatchResume();
    }

    void c() {
        while (markState(getSupportFragmentManager(), Lifecycle.State.CREATED)) {
        }
    }

    @Override // android.app.Activity
    public void dump(@NonNull String str, @Nullable FileDescriptor fileDescriptor, @NonNull PrintWriter printWriter, @Nullable String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        printWriter.print(str);
        printWriter.print("Local FragmentActivity ");
        printWriter.print(Integer.toHexString(System.identityHashCode(this)));
        printWriter.println(" State:");
        String str2 = str + "  ";
        printWriter.print(str2);
        printWriter.print("mCreated=");
        printWriter.print(this.e);
        printWriter.print(" mResumed=");
        printWriter.print(this.f);
        printWriter.print(" mStopped=");
        printWriter.print(this.g);
        if (getApplication() != null) {
            LoaderManager.getInstance(this).dump(str2, fileDescriptor, printWriter, strArr);
        }
        this.c.getSupportFragmentManager().dump(str, fileDescriptor, printWriter, strArr);
    }

    @NonNull
    public FragmentManager getSupportFragmentManager() {
        return this.c.getSupportFragmentManager();
    }

    @NonNull
    @Deprecated
    public LoaderManager getSupportLoaderManager() {
        return LoaderManager.getInstance(this);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    @CallSuper
    protected void onActivityResult(int i, int i2, @Nullable Intent intent) {
        this.c.noteStateNotSaved();
        super.onActivityResult(i, i2, intent);
    }

    @MainThread
    @Deprecated
    public void onAttachFragment(@NonNull Fragment fragment) {
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NonNull Configuration configuration) {
        this.c.noteStateNotSaved();
        super.onConfigurationChanged(configuration);
        this.c.dispatchConfigurationChanged(configuration);
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.d.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        this.c.dispatchCreate();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean onCreatePanelMenu(int i, @NonNull Menu menu) {
        if (i != 0) {
            return super.onCreatePanelMenu(i, menu);
        }
        return this.c.dispatchCreateOptionsMenu(menu, getMenuInflater()) | super.onCreatePanelMenu(i, menu);
    }

    @Override // android.app.Activity, android.view.LayoutInflater.Factory2
    @Nullable
    public View onCreateView(@Nullable View view, @NonNull String str, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        View viewA = a(view, str, context, attributeSet);
        return viewA == null ? super.onCreateView(view, str, context, attributeSet) : viewA;
    }

    @Override // android.app.Activity, android.view.LayoutInflater.Factory
    @Nullable
    public View onCreateView(@NonNull String str, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        View viewA = a(null, str, context, attributeSet);
        return viewA == null ? super.onCreateView(str, context, attributeSet) : viewA;
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.c.dispatchDestroy();
        this.d.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        this.c.dispatchLowMemory();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean onMenuItemSelected(int i, @NonNull MenuItem menuItem) {
        if (super.onMenuItemSelected(i, menuItem)) {
            return true;
        }
        if (i == 0) {
            return this.c.dispatchOptionsItemSelected(menuItem);
        }
        if (i != 6) {
            return false;
        }
        return this.c.dispatchContextItemSelected(menuItem);
    }

    @Override // android.app.Activity
    @CallSuper
    public void onMultiWindowModeChanged(boolean z) {
        this.c.dispatchMultiWindowModeChanged(z);
    }

    @Override // android.app.Activity
    @CallSuper
    protected void onNewIntent(@SuppressLint({"UnknownNullness"}) Intent intent) {
        this.c.noteStateNotSaved();
        super.onNewIntent(intent);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onPanelClosed(int i, @NonNull Menu menu) {
        if (i == 0) {
            this.c.dispatchOptionsMenuClosed(menu);
        }
        super.onPanelClosed(i, menu);
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        this.f = false;
        this.c.dispatchPause();
        this.d.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }

    @Override // android.app.Activity
    @CallSuper
    public void onPictureInPictureModeChanged(boolean z) {
        this.c.dispatchPictureInPictureModeChanged(z);
    }

    @Override // android.app.Activity
    protected void onPostResume() {
        super.onPostResume();
        b();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean onPreparePanel(int i, @Nullable View view, @NonNull Menu menu) {
        if (i != 0) {
            return super.onPreparePanel(i, view, menu);
        }
        return this.c.dispatchPrepareOptionsMenu(menu) | a(view, menu);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    @CallSuper
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        this.c.noteStateNotSaved();
        super.onRequestPermissionsResult(i, strArr, iArr);
    }

    @Override // android.app.Activity
    protected void onResume() {
        this.c.noteStateNotSaved();
        super.onResume();
        this.f = true;
        this.c.execPendingActions();
    }

    @Override // android.app.Activity
    protected void onStart() {
        this.c.noteStateNotSaved();
        super.onStart();
        this.g = false;
        if (!this.e) {
            this.e = true;
            this.c.dispatchActivityCreated();
        }
        this.c.execPendingActions();
        this.d.handleLifecycleEvent(Lifecycle.Event.ON_START);
        this.c.dispatchStart();
    }

    @Override // android.app.Activity
    public void onStateNotSaved() {
        this.c.noteStateNotSaved();
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        this.g = true;
        c();
        this.c.dispatchStop();
        this.d.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    public void setEnterSharedElementCallback(@Nullable SharedElementCallback sharedElementCallback) {
        ActivityCompat.setEnterSharedElementCallback(this, sharedElementCallback);
    }

    public void setExitSharedElementCallback(@Nullable SharedElementCallback sharedElementCallback) {
        ActivityCompat.setExitSharedElementCallback(this, sharedElementCallback);
    }

    public void startActivityFromFragment(@NonNull Fragment fragment, @SuppressLint({"UnknownNullness"}) Intent intent, int i) {
        startActivityFromFragment(fragment, intent, i, (Bundle) null);
    }

    public void startActivityFromFragment(@NonNull Fragment fragment, @SuppressLint({"UnknownNullness"}) Intent intent, int i, @Nullable Bundle bundle) {
        if (i == -1) {
            ActivityCompat.startActivityForResult(this, intent, -1, bundle);
        } else {
            fragment.startActivityForResult(intent, i, bundle);
        }
    }

    @Deprecated
    public void startIntentSenderFromFragment(@NonNull Fragment fragment, @SuppressLint({"UnknownNullness"}) IntentSender intentSender, int i, @Nullable Intent intent, int i2, int i3, int i4, @Nullable Bundle bundle) throws IntentSender.SendIntentException {
        if (i == -1) {
            ActivityCompat.startIntentSenderForResult(this, intentSender, i, intent, i2, i3, i4, bundle);
        } else {
            fragment.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4, bundle);
        }
    }

    public void supportFinishAfterTransition() {
        ActivityCompat.finishAfterTransition(this);
    }

    @Deprecated
    public void supportInvalidateOptionsMenu() {
        invalidateOptionsMenu();
    }

    public void supportPostponeEnterTransition() {
        ActivityCompat.postponeEnterTransition(this);
    }

    public void supportStartPostponedEnterTransition() {
        ActivityCompat.startPostponedEnterTransition(this);
    }

    @Override // androidx.core.app.ActivityCompat.RequestPermissionsRequestCodeValidator
    @Deprecated
    public final void validateRequestPermissionsRequestCode(int i) {
    }
}
