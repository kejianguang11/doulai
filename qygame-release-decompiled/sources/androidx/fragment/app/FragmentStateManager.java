package androidx.fragment.app;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.R;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.SpecialEffectsController;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelStoreOwner;
import com.alipay.sdk.util.e;

/* JADX INFO: loaded from: classes.dex */
class FragmentStateManager {
    private static final String TAG = "FragmentManager";
    private static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
    private static final String TARGET_STATE_TAG = "android:target_state";
    private static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
    private static final String VIEW_REGISTRY_STATE_TAG = "android:view_registry_state";
    private static final String VIEW_STATE_TAG = "android:view_state";
    private final FragmentLifecycleCallbacksDispatcher mDispatcher;

    @NonNull
    private final Fragment mFragment;
    private final FragmentStore mFragmentStore;
    private boolean mMovingToState = false;
    private int mFragmentManagerState = -1;

    FragmentStateManager(@NonNull FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher, @NonNull FragmentStore fragmentStore, @NonNull Fragment fragment) {
        this.mDispatcher = fragmentLifecycleCallbacksDispatcher;
        this.mFragmentStore = fragmentStore;
        this.mFragment = fragment;
    }

    FragmentStateManager(@NonNull FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher, @NonNull FragmentStore fragmentStore, @NonNull Fragment fragment, @NonNull FragmentState fragmentState) {
        Fragment fragment2;
        Bundle bundle;
        this.mDispatcher = fragmentLifecycleCallbacksDispatcher;
        this.mFragmentStore = fragmentStore;
        this.mFragment = fragment;
        this.mFragment.d = null;
        this.mFragment.e = null;
        this.mFragment.r = 0;
        this.mFragment.o = false;
        this.mFragment.l = false;
        this.mFragment.j = this.mFragment.i != null ? this.mFragment.i.g : null;
        this.mFragment.i = null;
        if (fragmentState.m != null) {
            fragment2 = this.mFragment;
            bundle = fragmentState.m;
        } else {
            fragment2 = this.mFragment;
            bundle = new Bundle();
        }
        fragment2.c = bundle;
    }

    FragmentStateManager(@NonNull FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher, @NonNull FragmentStore fragmentStore, @NonNull ClassLoader classLoader, @NonNull FragmentFactory fragmentFactory, @NonNull FragmentState fragmentState) {
        Fragment fragment;
        Bundle bundle;
        this.mDispatcher = fragmentLifecycleCallbacksDispatcher;
        this.mFragmentStore = fragmentStore;
        this.mFragment = fragmentFactory.instantiate(classLoader, fragmentState.a);
        if (fragmentState.j != null) {
            fragmentState.j.setClassLoader(classLoader);
        }
        this.mFragment.setArguments(fragmentState.j);
        this.mFragment.g = fragmentState.b;
        this.mFragment.n = fragmentState.c;
        this.mFragment.p = true;
        this.mFragment.w = fragmentState.d;
        this.mFragment.x = fragmentState.e;
        this.mFragment.y = fragmentState.f;
        this.mFragment.B = fragmentState.g;
        this.mFragment.m = fragmentState.h;
        this.mFragment.A = fragmentState.i;
        this.mFragment.z = fragmentState.k;
        this.mFragment.Q = Lifecycle.State.values()[fragmentState.l];
        if (fragmentState.m != null) {
            fragment = this.mFragment;
            bundle = fragmentState.m;
        } else {
            fragment = this.mFragment;
            bundle = new Bundle();
        }
        fragment.c = bundle;
        if (FragmentManager.a(2)) {
            Log.v(TAG, "Instantiated fragment " + this.mFragment);
        }
    }

    private boolean isFragmentViewChild(@NonNull View view) {
        if (view == this.mFragment.G) {
            return true;
        }
        for (ViewParent parent = view.getParent(); parent != null; parent = parent.getParent()) {
            if (parent == this.mFragment.G) {
                return true;
            }
        }
        return false;
    }

    private Bundle saveBasicState() {
        Bundle bundle = new Bundle();
        this.mFragment.f(bundle);
        this.mDispatcher.d(this.mFragment, bundle, false);
        if (bundle.isEmpty()) {
            bundle = null;
        }
        if (this.mFragment.G != null) {
            o();
        }
        if (this.mFragment.d != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putSparseParcelableArray(VIEW_STATE_TAG, this.mFragment.d);
        }
        if (this.mFragment.e != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBundle(VIEW_REGISTRY_STATE_TAG, this.mFragment.e);
        }
        if (!this.mFragment.I) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean(USER_VISIBLE_HINT_TAG, this.mFragment.I);
        }
        return bundle;
    }

    @NonNull
    Fragment a() {
        return this.mFragment;
    }

    void a(int i) {
        this.mFragmentManagerState = i;
    }

    void a(@NonNull ClassLoader classLoader) {
        if (this.mFragment.c == null) {
            return;
        }
        this.mFragment.c.setClassLoader(classLoader);
        this.mFragment.d = this.mFragment.c.getSparseParcelableArray(VIEW_STATE_TAG);
        this.mFragment.e = this.mFragment.c.getBundle(VIEW_REGISTRY_STATE_TAG);
        this.mFragment.j = this.mFragment.c.getString(TARGET_STATE_TAG);
        if (this.mFragment.j != null) {
            this.mFragment.k = this.mFragment.c.getInt(TARGET_REQUEST_CODE_STATE_TAG, 0);
        }
        if (this.mFragment.f != null) {
            this.mFragment.I = this.mFragment.f.booleanValue();
            this.mFragment.f = null;
        } else {
            this.mFragment.I = this.mFragment.c.getBoolean(USER_VISIBLE_HINT_TAG, true);
        }
        if (this.mFragment.I) {
            return;
        }
        this.mFragment.H = true;
    }

    int b() {
        if (this.mFragment.s == null) {
            return this.mFragment.b;
        }
        int iMin = this.mFragmentManagerState;
        switch (this.mFragment.Q) {
            case RESUMED:
                break;
            case STARTED:
                iMin = Math.min(iMin, 5);
                break;
            case CREATED:
                iMin = Math.min(iMin, 1);
                break;
            case INITIALIZED:
                iMin = Math.min(iMin, 0);
                break;
            default:
                iMin = Math.min(iMin, -1);
                break;
        }
        if (this.mFragment.n) {
            if (this.mFragment.o) {
                iMin = Math.max(this.mFragmentManagerState, 2);
                if (this.mFragment.G != null && this.mFragment.G.getParent() == null) {
                    iMin = Math.min(iMin, 2);
                }
            } else {
                iMin = this.mFragmentManagerState < 4 ? Math.min(iMin, this.mFragment.b) : Math.min(iMin, 1);
            }
        }
        if (!this.mFragment.l) {
            iMin = Math.min(iMin, 1);
        }
        SpecialEffectsController.Operation.LifecycleImpact lifecycleImpactA = null;
        if (FragmentManager.a && this.mFragment.F != null) {
            lifecycleImpactA = SpecialEffectsController.a(this.mFragment.F, this.mFragment.getParentFragmentManager()).a(this);
        }
        if (lifecycleImpactA == SpecialEffectsController.Operation.LifecycleImpact.ADDING) {
            iMin = Math.min(iMin, 6);
        } else if (lifecycleImpactA == SpecialEffectsController.Operation.LifecycleImpact.REMOVING) {
            iMin = Math.max(iMin, 3);
        } else if (this.mFragment.m) {
            iMin = this.mFragment.c() ? Math.min(iMin, 1) : Math.min(iMin, -1);
        }
        if (this.mFragment.H && this.mFragment.b < 5) {
            iMin = Math.min(iMin, 4);
        }
        if (FragmentManager.a(2)) {
            Log.v(TAG, "computeExpectedState() of " + iMin + " for " + this.mFragment);
        }
        return iMin;
    }

    void c() {
        if (this.mMovingToState) {
            if (FragmentManager.a(2)) {
                Log.v(TAG, "Ignoring re-entrant call to moveToExpectedState() for " + a());
                return;
            }
            return;
        }
        try {
            this.mMovingToState = true;
            while (true) {
                int iB = b();
                if (iB == this.mFragment.b) {
                    if (FragmentManager.a && this.mFragment.M) {
                        if (this.mFragment.G != null && this.mFragment.F != null) {
                            SpecialEffectsController specialEffectsControllerA = SpecialEffectsController.a(this.mFragment.F, this.mFragment.getParentFragmentManager());
                            if (this.mFragment.z) {
                                specialEffectsControllerA.c(this);
                            } else {
                                specialEffectsControllerA.b(this);
                            }
                        }
                        if (this.mFragment.s != null) {
                            this.mFragment.s.q(this.mFragment);
                        }
                        this.mFragment.M = false;
                        this.mFragment.onHiddenChanged(this.mFragment.z);
                    }
                    return;
                }
                if (iB <= this.mFragment.b) {
                    switch (this.mFragment.b - 1) {
                        case -1:
                            r();
                            break;
                        case 0:
                            q();
                            break;
                        case 1:
                            p();
                            this.mFragment.b = 1;
                            break;
                        case 2:
                            this.mFragment.o = false;
                            this.mFragment.b = 2;
                            break;
                        case 3:
                            if (FragmentManager.a(3)) {
                                Log.d(TAG, "movefrom ACTIVITY_CREATED: " + this.mFragment);
                            }
                            if (this.mFragment.G != null && this.mFragment.d == null) {
                                o();
                            }
                            if (this.mFragment.G != null && this.mFragment.F != null) {
                                SpecialEffectsController.a(this.mFragment.F, this.mFragment.getParentFragmentManager()).d(this);
                            }
                            this.mFragment.b = 3;
                            break;
                        case 4:
                            l();
                            break;
                        case 5:
                            this.mFragment.b = 5;
                            break;
                        case 6:
                            k();
                            break;
                    }
                } else {
                    switch (this.mFragment.b + 1) {
                        case 0:
                            e();
                            break;
                        case 1:
                            f();
                            break;
                        case 2:
                            d();
                            g();
                            break;
                        case 3:
                            h();
                            break;
                        case 4:
                            if (this.mFragment.G != null && this.mFragment.F != null) {
                                SpecialEffectsController.a(this.mFragment.F, this.mFragment.getParentFragmentManager()).a(SpecialEffectsController.Operation.State.a(this.mFragment.G.getVisibility()), this);
                            }
                            this.mFragment.b = 4;
                            break;
                        case 5:
                            i();
                            break;
                        case 6:
                            this.mFragment.b = 6;
                            break;
                        case 7:
                            j();
                            break;
                    }
                }
            }
        } finally {
            this.mMovingToState = false;
        }
    }

    void d() {
        if (this.mFragment.n && this.mFragment.o && !this.mFragment.q) {
            if (FragmentManager.a(3)) {
                Log.d(TAG, "moveto CREATE_VIEW: " + this.mFragment);
            }
            this.mFragment.a(this.mFragment.b(this.mFragment.c), null, this.mFragment.c);
            if (this.mFragment.G != null) {
                this.mFragment.G.setSaveFromParentEnabled(false);
                this.mFragment.G.setTag(R.id.fragment_container_view_tag, this.mFragment);
                if (this.mFragment.z) {
                    this.mFragment.G.setVisibility(8);
                }
                this.mFragment.g();
                this.mDispatcher.a(this.mFragment, this.mFragment.G, this.mFragment.c, false);
                this.mFragment.b = 2;
            }
        }
    }

    void e() {
        if (FragmentManager.a(3)) {
            Log.d(TAG, "moveto ATTACHED: " + this.mFragment);
        }
        FragmentStateManager fragmentStateManagerC = null;
        if (this.mFragment.i != null) {
            FragmentStateManager fragmentStateManagerC2 = this.mFragmentStore.c(this.mFragment.i.g);
            if (fragmentStateManagerC2 == null) {
                throw new IllegalStateException("Fragment " + this.mFragment + " declared target fragment " + this.mFragment.i + " that does not belong to this FragmentManager!");
            }
            this.mFragment.j = this.mFragment.i.g;
            this.mFragment.i = null;
            fragmentStateManagerC = fragmentStateManagerC2;
        } else if (this.mFragment.j != null && (fragmentStateManagerC = this.mFragmentStore.c(this.mFragment.j)) == null) {
            throw new IllegalStateException("Fragment " + this.mFragment + " declared target fragment " + this.mFragment.j + " that does not belong to this FragmentManager!");
        }
        if (fragmentStateManagerC != null && (FragmentManager.a || fragmentStateManagerC.a().b < 1)) {
            fragmentStateManagerC.c();
        }
        this.mFragment.t = this.mFragment.s.h();
        this.mFragment.v = this.mFragment.s.i();
        this.mDispatcher.a(this.mFragment, false);
        this.mFragment.f();
        this.mDispatcher.b(this.mFragment, false);
    }

    void f() {
        if (FragmentManager.a(3)) {
            Log.d(TAG, "moveto CREATED: " + this.mFragment);
        }
        if (this.mFragment.P) {
            this.mFragment.c(this.mFragment.c);
            this.mFragment.b = 1;
        } else {
            this.mDispatcher.a(this.mFragment, this.mFragment.c, false);
            this.mFragment.d(this.mFragment.c);
            this.mDispatcher.b(this.mFragment, this.mFragment.c, false);
        }
    }

    void g() {
        String resourceName;
        if (this.mFragment.n) {
            return;
        }
        if (FragmentManager.a(3)) {
            Log.d(TAG, "moveto CREATE_VIEW: " + this.mFragment);
        }
        LayoutInflater layoutInflaterB = this.mFragment.b(this.mFragment.c);
        ViewGroup viewGroup = null;
        if (this.mFragment.F != null) {
            viewGroup = this.mFragment.F;
        } else if (this.mFragment.x != 0) {
            if (this.mFragment.x == -1) {
                throw new IllegalArgumentException("Cannot create fragment " + this.mFragment + " for a container view with no id");
            }
            viewGroup = (ViewGroup) this.mFragment.s.j().onFindViewById(this.mFragment.x);
            if (viewGroup == null && !this.mFragment.p) {
                try {
                    resourceName = this.mFragment.getResources().getResourceName(this.mFragment.x);
                } catch (Resources.NotFoundException unused) {
                    resourceName = "unknown";
                }
                throw new IllegalArgumentException("No view found for id 0x" + Integer.toHexString(this.mFragment.x) + " (" + resourceName + ") for fragment " + this.mFragment);
            }
        }
        this.mFragment.F = viewGroup;
        this.mFragment.a(layoutInflaterB, viewGroup, this.mFragment.c);
        if (this.mFragment.G != null) {
            boolean z = false;
            this.mFragment.G.setSaveFromParentEnabled(false);
            this.mFragment.G.setTag(R.id.fragment_container_view_tag, this.mFragment);
            if (viewGroup != null) {
                s();
            }
            if (this.mFragment.z) {
                this.mFragment.G.setVisibility(8);
            }
            if (ViewCompat.isAttachedToWindow(this.mFragment.G)) {
                ViewCompat.requestApplyInsets(this.mFragment.G);
            } else {
                final View view = this.mFragment.G;
                view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: androidx.fragment.app.FragmentStateManager.1
                    @Override // android.view.View.OnAttachStateChangeListener
                    public void onViewAttachedToWindow(View view2) {
                        view.removeOnAttachStateChangeListener(this);
                        ViewCompat.requestApplyInsets(view);
                    }

                    @Override // android.view.View.OnAttachStateChangeListener
                    public void onViewDetachedFromWindow(View view2) {
                    }
                });
            }
            this.mFragment.g();
            this.mDispatcher.a(this.mFragment, this.mFragment.G, this.mFragment.c, false);
            int visibility = this.mFragment.G.getVisibility();
            float alpha = this.mFragment.G.getAlpha();
            if (FragmentManager.a) {
                this.mFragment.a(alpha);
                if (this.mFragment.F != null && visibility == 0) {
                    View viewFindFocus = this.mFragment.G.findFocus();
                    if (viewFindFocus != null) {
                        this.mFragment.b(viewFindFocus);
                        if (FragmentManager.a(2)) {
                            Log.v(TAG, "requestFocus: Saved focused view " + viewFindFocus + " for Fragment " + this.mFragment);
                        }
                    }
                    this.mFragment.G.setAlpha(0.0f);
                }
            } else {
                Fragment fragment = this.mFragment;
                if (visibility == 0 && this.mFragment.F != null) {
                    z = true;
                }
                fragment.L = z;
            }
        }
        this.mFragment.b = 2;
    }

    void h() {
        if (FragmentManager.a(3)) {
            Log.d(TAG, "moveto ACTIVITY_CREATED: " + this.mFragment);
        }
        this.mFragment.e(this.mFragment.c);
        this.mDispatcher.c(this.mFragment, this.mFragment.c, false);
    }

    void i() {
        if (FragmentManager.a(3)) {
            Log.d(TAG, "moveto STARTED: " + this.mFragment);
        }
        this.mFragment.h();
        this.mDispatcher.c(this.mFragment, false);
    }

    void j() {
        if (FragmentManager.a(3)) {
            Log.d(TAG, "moveto RESUMED: " + this.mFragment);
        }
        View viewE = this.mFragment.E();
        if (viewE != null && isFragmentViewChild(viewE)) {
            boolean zRequestFocus = viewE.requestFocus();
            if (FragmentManager.a(2)) {
                StringBuilder sb = new StringBuilder();
                sb.append("requestFocus: Restoring focused view ");
                sb.append(viewE);
                sb.append(" ");
                sb.append(zRequestFocus ? "succeeded" : e.b);
                sb.append(" on Fragment ");
                sb.append(this.mFragment);
                sb.append(" resulting in focused view ");
                sb.append(this.mFragment.G.findFocus());
                Log.v(TAG, sb.toString());
            }
        }
        this.mFragment.b((View) null);
        this.mFragment.i();
        this.mDispatcher.d(this.mFragment, false);
        this.mFragment.c = null;
        this.mFragment.d = null;
        this.mFragment.e = null;
    }

    void k() {
        if (FragmentManager.a(3)) {
            Log.d(TAG, "movefrom RESUMED: " + this.mFragment);
        }
        this.mFragment.m();
        this.mDispatcher.e(this.mFragment, false);
    }

    void l() {
        if (FragmentManager.a(3)) {
            Log.d(TAG, "movefrom STARTED: " + this.mFragment);
        }
        this.mFragment.n();
        this.mDispatcher.f(this.mFragment, false);
    }

    @NonNull
    FragmentState m() {
        FragmentState fragmentState = new FragmentState(this.mFragment);
        if (this.mFragment.b <= -1 || fragmentState.m != null) {
            fragmentState.m = this.mFragment.c;
        } else {
            fragmentState.m = saveBasicState();
            if (this.mFragment.j != null) {
                if (fragmentState.m == null) {
                    fragmentState.m = new Bundle();
                }
                fragmentState.m.putString(TARGET_STATE_TAG, this.mFragment.j);
                if (this.mFragment.k != 0) {
                    fragmentState.m.putInt(TARGET_REQUEST_CODE_STATE_TAG, this.mFragment.k);
                }
            }
        }
        return fragmentState;
    }

    @Nullable
    Fragment.SavedState n() {
        Bundle bundleSaveBasicState;
        if (this.mFragment.b <= -1 || (bundleSaveBasicState = saveBasicState()) == null) {
            return null;
        }
        return new Fragment.SavedState(bundleSaveBasicState);
    }

    void o() {
        if (this.mFragment.G == null) {
            return;
        }
        SparseArray<Parcelable> sparseArray = new SparseArray<>();
        this.mFragment.G.saveHierarchyState(sparseArray);
        if (sparseArray.size() > 0) {
            this.mFragment.d = sparseArray;
        }
        Bundle bundle = new Bundle();
        this.mFragment.S.b(bundle);
        if (bundle.isEmpty()) {
            return;
        }
        this.mFragment.e = bundle;
    }

    void p() {
        if (FragmentManager.a(3)) {
            Log.d(TAG, "movefrom CREATE_VIEW: " + this.mFragment);
        }
        if (this.mFragment.F != null && this.mFragment.G != null) {
            this.mFragment.F.removeView(this.mFragment.G);
        }
        this.mFragment.o();
        this.mDispatcher.g(this.mFragment, false);
        this.mFragment.F = null;
        this.mFragment.G = null;
        this.mFragment.S = null;
        this.mFragment.T.setValue(null);
        this.mFragment.o = false;
    }

    void q() {
        Fragment fragmentE;
        if (FragmentManager.a(3)) {
            Log.d(TAG, "movefrom CREATED: " + this.mFragment);
        }
        boolean zIsChangingConfigurations = true;
        boolean z = this.mFragment.m && !this.mFragment.c();
        if (!(z || this.mFragmentStore.a().b(this.mFragment))) {
            if (this.mFragment.j != null && (fragmentE = this.mFragmentStore.e(this.mFragment.j)) != null && fragmentE.B) {
                this.mFragment.i = fragmentE;
            }
            this.mFragment.b = 0;
            return;
        }
        FragmentHostCallback<?> fragmentHostCallback = this.mFragment.t;
        if (fragmentHostCallback instanceof ViewModelStoreOwner) {
            zIsChangingConfigurations = this.mFragmentStore.a().b();
        } else if (fragmentHostCallback.b() instanceof Activity) {
            zIsChangingConfigurations = true ^ ((Activity) fragmentHostCallback.b()).isChangingConfigurations();
        }
        if (z || zIsChangingConfigurations) {
            this.mFragmentStore.a().f(this.mFragment);
        }
        this.mFragment.p();
        this.mDispatcher.h(this.mFragment, false);
        for (FragmentStateManager fragmentStateManager : this.mFragmentStore.g()) {
            if (fragmentStateManager != null) {
                Fragment fragmentA = fragmentStateManager.a();
                if (this.mFragment.g.equals(fragmentA.j)) {
                    fragmentA.i = this.mFragment;
                    fragmentA.j = null;
                }
            }
        }
        if (this.mFragment.j != null) {
            this.mFragment.i = this.mFragmentStore.e(this.mFragment.j);
        }
        this.mFragmentStore.b(this);
    }

    void r() {
        if (FragmentManager.a(3)) {
            Log.d(TAG, "movefrom ATTACHED: " + this.mFragment);
        }
        this.mFragment.q();
        boolean z = false;
        this.mDispatcher.i(this.mFragment, false);
        this.mFragment.b = -1;
        this.mFragment.t = null;
        this.mFragment.v = null;
        this.mFragment.s = null;
        if (this.mFragment.m && !this.mFragment.c()) {
            z = true;
        }
        if (z || this.mFragmentStore.a().b(this.mFragment)) {
            if (FragmentManager.a(3)) {
                Log.d(TAG, "initState called for fragment: " + this.mFragment);
            }
            this.mFragment.e();
        }
    }

    void s() {
        this.mFragment.F.addView(this.mFragment.G, this.mFragmentStore.c(this.mFragment));
    }
}
