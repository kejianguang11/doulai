package androidx.fragment.app;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.ActivityResultRegistryOwner;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.CallSuper;
import androidx.annotation.ContentView;
import androidx.annotation.LayoutRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.UiThread;
import androidx.arch.core.util.Function;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.app.SharedElementCallback;
import androidx.core.view.LayoutInflaterCompat;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;
import androidx.loader.app.LoaderManager;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;
import androidx.savedstate.ViewTreeSavedStateRegistryOwner;
import com.alipay.sdk.util.h;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public class Fragment implements ComponentCallbacks, View.OnCreateContextMenuListener, ActivityResultCaller, HasDefaultViewModelProviderFactory, LifecycleOwner, ViewModelStoreOwner, SavedStateRegistryOwner {
    static final Object a = new Object();
    boolean A;
    boolean B;
    boolean C;
    boolean D;
    boolean E;
    ViewGroup F;
    View G;
    boolean H;
    boolean I;
    AnimationInfo J;
    Runnable K;
    boolean L;
    boolean M;
    float N;
    LayoutInflater O;
    boolean P;
    Lifecycle.State Q;
    LifecycleRegistry R;

    @Nullable
    FragmentViewLifecycleOwner S;
    MutableLiveData<LifecycleOwner> T;
    ViewModelProvider.Factory U;
    SavedStateRegistryController V;
    int b;
    Bundle c;
    SparseArray<Parcelable> d;
    Bundle e;

    @Nullable
    Boolean f;

    @NonNull
    String g;
    Bundle h;
    Fragment i;
    String j;
    int k;
    boolean l;
    boolean m;
    private boolean mCalled;

    @LayoutRes
    private int mContentLayoutId;
    private Boolean mIsPrimaryNavigationFragment;
    private final AtomicInteger mNextLocalRequestCode;
    private final ArrayList<OnPreAttachedListener> mOnPreAttachedListeners;
    boolean n;
    boolean o;
    boolean p;
    boolean q;
    int r;
    FragmentManager s;
    FragmentHostCallback<?> t;

    @NonNull
    FragmentManager u;
    Fragment v;
    int w;
    int x;
    String y;
    boolean z;

    static class AnimationInfo {
        View a;
        Animator b;
        boolean c;
        int d;
        int e;
        int f;
        int g;
        int h;
        ArrayList<String> i;
        ArrayList<String> j;
        Boolean q;
        Boolean r;
        boolean w;
        OnStartEnterTransitionListener x;
        boolean y;
        Object k = null;
        Object l = Fragment.a;
        Object m = null;
        Object n = Fragment.a;
        Object o = null;
        Object p = Fragment.a;
        SharedElementCallback s = null;
        SharedElementCallback t = null;
        float u = 1.0f;
        View v = null;

        AnimationInfo() {
        }
    }

    public static class InstantiationException extends RuntimeException {
        public InstantiationException(@NonNull String str, @Nullable Exception exc) {
            super(str, exc);
        }
    }

    private static abstract class OnPreAttachedListener {
        private OnPreAttachedListener() {
        }

        abstract void a();
    }

    interface OnStartEnterTransitionListener {
        void onStartEnterTransition();

        void startListening();
    }

    @SuppressLint({"BanParcelableUsage, ParcelClassLoader"})
    public static class SavedState implements Parcelable {

        @NonNull
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: androidx.fragment.app.Fragment.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        final Bundle a;

        SavedState(Bundle bundle) {
            this.a = bundle;
        }

        SavedState(@NonNull Parcel parcel, @Nullable ClassLoader classLoader) {
            this.a = parcel.readBundle();
            if (classLoader == null || this.a == null) {
                return;
            }
            this.a.setClassLoader(classLoader);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i) {
            parcel.writeBundle(this.a);
        }
    }

    public Fragment() {
        this.b = -1;
        this.g = UUID.randomUUID().toString();
        this.j = null;
        this.mIsPrimaryNavigationFragment = null;
        this.u = new FragmentManagerImpl();
        this.E = true;
        this.I = true;
        this.K = new Runnable() { // from class: androidx.fragment.app.Fragment.1
            @Override // java.lang.Runnable
            public void run() {
                Fragment.this.startPostponedEnterTransition();
            }
        };
        this.Q = Lifecycle.State.RESUMED;
        this.T = new MutableLiveData<>();
        this.mNextLocalRequestCode = new AtomicInteger();
        this.mOnPreAttachedListeners = new ArrayList<>();
        initLifecycle();
    }

    @ContentView
    public Fragment(@LayoutRes int i) {
        this();
        this.mContentLayoutId = i;
    }

    private AnimationInfo ensureAnimationInfo() {
        if (this.J == null) {
            this.J = new AnimationInfo();
        }
        return this.J;
    }

    private int getMinimumMaxLifecycleState() {
        return (this.Q == Lifecycle.State.INITIALIZED || this.v == null) ? this.Q.ordinal() : Math.min(this.Q.ordinal(), this.v.getMinimumMaxLifecycleState());
    }

    private void initLifecycle() {
        this.R = new LifecycleRegistry(this);
        this.V = SavedStateRegistryController.create(this);
        this.U = null;
    }

    @NonNull
    @Deprecated
    public static Fragment instantiate(@NonNull Context context, @NonNull String str) {
        return instantiate(context, str, null);
    }

    @NonNull
    @Deprecated
    public static Fragment instantiate(@NonNull Context context, @NonNull String str, @Nullable Bundle bundle) {
        try {
            Fragment fragmentNewInstance = FragmentFactory.loadFragmentClass(context.getClassLoader(), str).getConstructor(new Class[0]).newInstance(new Object[0]);
            if (bundle != null) {
                bundle.setClassLoader(fragmentNewInstance.getClass().getClassLoader());
                fragmentNewInstance.setArguments(bundle);
            }
            return fragmentNewInstance;
        } catch (IllegalAccessException e) {
            throw new InstantiationException("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an empty constructor that is public", e);
        } catch (java.lang.InstantiationException e2) {
            throw new InstantiationException("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an empty constructor that is public", e2);
        } catch (NoSuchMethodException e3) {
            throw new InstantiationException("Unable to instantiate fragment " + str + ": could not find Fragment constructor", e3);
        } catch (InvocationTargetException e4) {
            throw new InstantiationException("Unable to instantiate fragment " + str + ": calling Fragment constructor caused an exception", e4);
        }
    }

    @NonNull
    private <I, O> ActivityResultLauncher<I> prepareCallInternal(@NonNull final ActivityResultContract<I, O> activityResultContract, @NonNull final Function<Void, ActivityResultRegistry> function, @NonNull final ActivityResultCallback<O> activityResultCallback) {
        if (this.b <= 1) {
            final AtomicReference atomicReference = new AtomicReference();
            registerOnPreAttachListener(new OnPreAttachedListener() { // from class: androidx.fragment.app.Fragment.8
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                @Override // androidx.fragment.app.Fragment.OnPreAttachedListener
                void a() {
                    String strH = Fragment.this.H();
                    atomicReference.set(((ActivityResultRegistry) function.apply(null)).register(strH, Fragment.this, activityResultContract, activityResultCallback));
                }
            });
            return new ActivityResultLauncher<I>() { // from class: androidx.fragment.app.Fragment.9
                @Override // androidx.activity.result.ActivityResultLauncher
                @NonNull
                public ActivityResultContract<I, ?> getContract() {
                    return activityResultContract;
                }

                @Override // androidx.activity.result.ActivityResultLauncher
                public void launch(I i, @Nullable ActivityOptionsCompat activityOptionsCompat) {
                    ActivityResultLauncher activityResultLauncher = (ActivityResultLauncher) atomicReference.get();
                    if (activityResultLauncher == null) {
                        throw new IllegalStateException("Operation cannot be started before fragment is in created state");
                    }
                    activityResultLauncher.launch(i, activityOptionsCompat);
                }

                @Override // androidx.activity.result.ActivityResultLauncher
                public void unregister() {
                    ActivityResultLauncher activityResultLauncher = (ActivityResultLauncher) atomicReference.getAndSet(null);
                    if (activityResultLauncher != null) {
                        activityResultLauncher.unregister();
                    }
                }
            };
        }
        throw new IllegalStateException("Fragment " + this + " is attempting to registerForActivityResult after being created. Fragments must call registerForActivityResult() before they are created (i.e. initialization, onAttach(), or onCreate()).");
    }

    private void registerOnPreAttachListener(@NonNull OnPreAttachedListener onPreAttachedListener) {
        if (this.b >= 0) {
            onPreAttachedListener.a();
        } else {
            this.mOnPreAttachedListeners.add(onPreAttachedListener);
        }
    }

    private void restoreViewState() {
        if (FragmentManager.a(3)) {
            Log.d("FragmentManager", "moveto RESTORE_VIEW_STATE: " + this);
        }
        if (this.G != null) {
            a(this.c);
        }
        this.c = null;
    }

    SharedElementCallback A() {
        if (this.J == null) {
            return null;
        }
        return this.J.t;
    }

    View B() {
        if (this.J == null) {
            return null;
        }
        return this.J.a;
    }

    Animator C() {
        if (this.J == null) {
            return null;
        }
        return this.J.b;
    }

    float D() {
        if (this.J == null) {
            return 1.0f;
        }
        return this.J.u;
    }

    View E() {
        if (this.J == null) {
            return null;
        }
        return this.J.v;
    }

    boolean F() {
        if (this.J == null) {
            return false;
        }
        return this.J.w;
    }

    boolean G() {
        if (this.J == null) {
            return false;
        }
        return this.J.y;
    }

    @NonNull
    String H() {
        return "fragment_" + this.g + "_rq#" + this.mNextLocalRequestCode.getAndIncrement();
    }

    @Nullable
    Fragment a(@NonNull String str) {
        return str.equals(this.g) ? this : this.u.a(str);
    }

    @NonNull
    FragmentContainer a() {
        return new FragmentContainer() { // from class: androidx.fragment.app.Fragment.4
            @Override // androidx.fragment.app.FragmentContainer
            @Nullable
            public View onFindViewById(int i) {
                if (Fragment.this.G != null) {
                    return Fragment.this.G.findViewById(i);
                }
                throw new IllegalStateException("Fragment " + Fragment.this + " does not have a view");
            }

            @Override // androidx.fragment.app.FragmentContainer
            public boolean onHasView() {
                return Fragment.this.G != null;
            }
        };
    }

    void a(float f) {
        ensureAnimationInfo().u = f;
    }

    void a(int i, int i2, int i3, int i4) {
        if (this.J == null && i == 0 && i2 == 0 && i3 == 0 && i4 == 0) {
            return;
        }
        ensureAnimationInfo().d = i;
        ensureAnimationInfo().e = i2;
        ensureAnimationInfo().f = i3;
        ensureAnimationInfo().g = i4;
    }

    void a(Animator animator) {
        ensureAnimationInfo().b = animator;
    }

    void a(@NonNull Configuration configuration) {
        onConfigurationChanged(configuration);
        this.u.a(configuration);
    }

    final void a(Bundle bundle) {
        if (this.d != null) {
            this.G.restoreHierarchyState(this.d);
            this.d = null;
        }
        if (this.G != null) {
            this.S.a(this.e);
            this.e = null;
        }
        this.mCalled = false;
        onViewStateRestored(bundle);
        if (this.mCalled) {
            if (this.G != null) {
                this.S.a(Lifecycle.Event.ON_CREATE);
            }
        } else {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onViewStateRestored()");
        }
    }

    void a(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.u.l();
        this.q = true;
        this.S = new FragmentViewLifecycleOwner(this, getViewModelStore());
        this.G = onCreateView(layoutInflater, viewGroup, bundle);
        if (this.G == null) {
            if (this.S.b()) {
                throw new IllegalStateException("Called getViewLifecycleOwner() but onCreateView() returned null");
            }
            this.S = null;
        } else {
            this.S.a();
            ViewTreeLifecycleOwner.set(this.G, this.S);
            ViewTreeViewModelStoreOwner.set(this.G, this.S);
            ViewTreeSavedStateRegistryOwner.set(this.G, this.S);
            this.T.setValue(this.S);
        }
    }

    void a(View view) {
        ensureAnimationInfo().a = view;
    }

    void a(OnStartEnterTransitionListener onStartEnterTransitionListener) {
        ensureAnimationInfo();
        if (onStartEnterTransitionListener == this.J.x) {
            return;
        }
        if (onStartEnterTransitionListener != null && this.J.x != null) {
            throw new IllegalStateException("Trying to set a replacement startPostponedEnterTransition on " + this);
        }
        if (this.J.w) {
            this.J.x = onStartEnterTransitionListener;
        }
        if (onStartEnterTransitionListener != null) {
            onStartEnterTransitionListener.startListening();
        }
    }

    void a(@Nullable ArrayList<String> arrayList, @Nullable ArrayList<String> arrayList2) {
        ensureAnimationInfo();
        this.J.i = arrayList;
        this.J.j = arrayList2;
    }

    void a(boolean z) {
        OnStartEnterTransitionListener onStartEnterTransitionListener;
        if (this.J == null) {
            onStartEnterTransitionListener = null;
        } else {
            this.J.w = false;
            onStartEnterTransitionListener = this.J.x;
            this.J.x = null;
        }
        if (onStartEnterTransitionListener != null) {
            onStartEnterTransitionListener.onStartEnterTransition();
            return;
        }
        if (!FragmentManager.a || this.G == null || this.F == null || this.s == null) {
            return;
        }
        final SpecialEffectsController specialEffectsControllerA = SpecialEffectsController.a(this.F, this.s);
        specialEffectsControllerA.a();
        if (z) {
            this.t.c().post(new Runnable() { // from class: androidx.fragment.app.Fragment.3
                @Override // java.lang.Runnable
                public void run() {
                    specialEffectsControllerA.c();
                }
            });
        } else {
            specialEffectsControllerA.c();
        }
    }

    boolean a(@NonNull Menu menu) {
        boolean z = false;
        if (this.z) {
            return false;
        }
        if (this.D && this.E) {
            z = true;
            onPrepareOptionsMenu(menu);
        }
        return z | this.u.a(menu);
    }

    boolean a(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        boolean z = false;
        if (this.z) {
            return false;
        }
        if (this.D && this.E) {
            z = true;
            onCreateOptionsMenu(menu, menuInflater);
        }
        return z | this.u.a(menu, menuInflater);
    }

    boolean a(@NonNull MenuItem menuItem) {
        if (this.z) {
            return false;
        }
        if (this.D && this.E && onOptionsItemSelected(menuItem)) {
            return true;
        }
        return this.u.a(menuItem);
    }

    @NonNull
    LayoutInflater b(@Nullable Bundle bundle) {
        this.O = onGetLayoutInflater(bundle);
        return this.O;
    }

    void b(int i) {
        if (this.J == null && i == 0) {
            return;
        }
        ensureAnimationInfo();
        this.J.h = i;
    }

    void b(@NonNull Menu menu) {
        if (this.z) {
            return;
        }
        if (this.D && this.E) {
            onOptionsMenuClosed(menu);
        }
        this.u.b(menu);
    }

    void b(View view) {
        ensureAnimationInfo().v = view;
    }

    void b(boolean z) {
        onMultiWindowModeChanged(z);
        this.u.b(z);
    }

    boolean b(@NonNull MenuItem menuItem) {
        if (this.z) {
            return false;
        }
        if (onContextItemSelected(menuItem)) {
            return true;
        }
        return this.u.b(menuItem);
    }

    void c(@Nullable Bundle bundle) {
        Parcelable parcelable;
        if (bundle == null || (parcelable = bundle.getParcelable("android:support:fragments")) == null) {
            return;
        }
        this.u.a(parcelable);
        this.u.n();
    }

    void c(boolean z) {
        onPictureInPictureModeChanged(z);
        this.u.c(z);
    }

    final boolean c() {
        return this.r > 0;
    }

    void d(Bundle bundle) {
        this.u.l();
        this.b = 1;
        this.mCalled = false;
        if (Build.VERSION.SDK_INT >= 19) {
            this.R.addObserver(new LifecycleEventObserver() { // from class: androidx.fragment.app.Fragment.5
                @Override // androidx.lifecycle.LifecycleEventObserver
                public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
                    if (event != Lifecycle.Event.ON_STOP || Fragment.this.G == null) {
                        return;
                    }
                    Fragment.this.G.cancelPendingInputEvents();
                }
            });
        }
        this.V.performRestore(bundle);
        onCreate(bundle);
        this.P = true;
        if (this.mCalled) {
            this.R.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
            return;
        }
        throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onCreate()");
    }

    void d(boolean z) {
        if (this.J == null) {
            return;
        }
        ensureAnimationInfo().c = z;
    }

    final boolean d() {
        Fragment parentFragment = getParentFragment();
        return parentFragment != null && (parentFragment.isRemoving() || parentFragment.d());
    }

    public void dump(@NonNull String str, @Nullable FileDescriptor fileDescriptor, @NonNull PrintWriter printWriter, @Nullable String[] strArr) {
        printWriter.print(str);
        printWriter.print("mFragmentId=#");
        printWriter.print(Integer.toHexString(this.w));
        printWriter.print(" mContainerId=#");
        printWriter.print(Integer.toHexString(this.x));
        printWriter.print(" mTag=");
        printWriter.println(this.y);
        printWriter.print(str);
        printWriter.print("mState=");
        printWriter.print(this.b);
        printWriter.print(" mWho=");
        printWriter.print(this.g);
        printWriter.print(" mBackStackNesting=");
        printWriter.println(this.r);
        printWriter.print(str);
        printWriter.print("mAdded=");
        printWriter.print(this.l);
        printWriter.print(" mRemoving=");
        printWriter.print(this.m);
        printWriter.print(" mFromLayout=");
        printWriter.print(this.n);
        printWriter.print(" mInLayout=");
        printWriter.println(this.o);
        printWriter.print(str);
        printWriter.print("mHidden=");
        printWriter.print(this.z);
        printWriter.print(" mDetached=");
        printWriter.print(this.A);
        printWriter.print(" mMenuVisible=");
        printWriter.print(this.E);
        printWriter.print(" mHasMenu=");
        printWriter.println(this.D);
        printWriter.print(str);
        printWriter.print("mRetainInstance=");
        printWriter.print(this.B);
        printWriter.print(" mUserVisibleHint=");
        printWriter.println(this.I);
        if (this.s != null) {
            printWriter.print(str);
            printWriter.print("mFragmentManager=");
            printWriter.println(this.s);
        }
        if (this.t != null) {
            printWriter.print(str);
            printWriter.print("mHost=");
            printWriter.println(this.t);
        }
        if (this.v != null) {
            printWriter.print(str);
            printWriter.print("mParentFragment=");
            printWriter.println(this.v);
        }
        if (this.h != null) {
            printWriter.print(str);
            printWriter.print("mArguments=");
            printWriter.println(this.h);
        }
        if (this.c != null) {
            printWriter.print(str);
            printWriter.print("mSavedFragmentState=");
            printWriter.println(this.c);
        }
        if (this.d != null) {
            printWriter.print(str);
            printWriter.print("mSavedViewState=");
            printWriter.println(this.d);
        }
        if (this.e != null) {
            printWriter.print(str);
            printWriter.print("mSavedViewRegistryState=");
            printWriter.println(this.e);
        }
        Fragment targetFragment = getTargetFragment();
        if (targetFragment != null) {
            printWriter.print(str);
            printWriter.print("mTarget=");
            printWriter.print(targetFragment);
            printWriter.print(" mTargetRequestCode=");
            printWriter.println(this.k);
        }
        printWriter.print(str);
        printWriter.print("mPopDirection=");
        printWriter.println(v());
        if (r() != 0) {
            printWriter.print(str);
            printWriter.print("getEnterAnim=");
            printWriter.println(r());
        }
        if (s() != 0) {
            printWriter.print(str);
            printWriter.print("getExitAnim=");
            printWriter.println(s());
        }
        if (t() != 0) {
            printWriter.print(str);
            printWriter.print("getPopEnterAnim=");
            printWriter.println(t());
        }
        if (u() != 0) {
            printWriter.print(str);
            printWriter.print("getPopExitAnim=");
            printWriter.println(u());
        }
        if (this.F != null) {
            printWriter.print(str);
            printWriter.print("mContainer=");
            printWriter.println(this.F);
        }
        if (this.G != null) {
            printWriter.print(str);
            printWriter.print("mView=");
            printWriter.println(this.G);
        }
        if (B() != null) {
            printWriter.print(str);
            printWriter.print("mAnimatingAway=");
            printWriter.println(B());
        }
        if (getContext() != null) {
            LoaderManager.getInstance(this).dump(str, fileDescriptor, printWriter, strArr);
        }
        printWriter.print(str);
        printWriter.println("Child " + this.u + ":");
        this.u.dump(str + "  ", fileDescriptor, printWriter, strArr);
    }

    void e() {
        initLifecycle();
        this.g = UUID.randomUUID().toString();
        this.l = false;
        this.m = false;
        this.n = false;
        this.o = false;
        this.p = false;
        this.r = 0;
        this.s = null;
        this.u = new FragmentManagerImpl();
        this.t = null;
        this.w = 0;
        this.x = 0;
        this.y = null;
        this.z = false;
        this.A = false;
    }

    void e(Bundle bundle) {
        this.u.l();
        this.b = 3;
        this.mCalled = false;
        onActivityCreated(bundle);
        if (this.mCalled) {
            restoreViewState();
            this.u.p();
        } else {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onActivityCreated()");
        }
    }

    void e(boolean z) {
        ensureAnimationInfo().y = z;
    }

    public final boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    void f() {
        Iterator<OnPreAttachedListener> it = this.mOnPreAttachedListeners.iterator();
        while (it.hasNext()) {
            it.next().a();
        }
        this.mOnPreAttachedListeners.clear();
        this.u.a(this.t, a(), this);
        this.b = 0;
        this.mCalled = false;
        onAttach(this.t.b());
        if (this.mCalled) {
            this.s.p(this);
            this.u.m();
        } else {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onAttach()");
        }
    }

    void f(Bundle bundle) {
        onSaveInstanceState(bundle);
        this.V.performSave(bundle);
        Parcelable parcelableG = this.u.g();
        if (parcelableG != null) {
            bundle.putParcelable("android:support:fragments", parcelableG);
        }
    }

    void g() {
        onViewCreated(this.G, this.c);
        this.u.o();
    }

    @Nullable
    public final FragmentActivity getActivity() {
        if (this.t == null) {
            return null;
        }
        return (FragmentActivity) this.t.a();
    }

    public boolean getAllowEnterTransitionOverlap() {
        if (this.J == null || this.J.r == null) {
            return true;
        }
        return this.J.r.booleanValue();
    }

    public boolean getAllowReturnTransitionOverlap() {
        if (this.J == null || this.J.q == null) {
            return true;
        }
        return this.J.q.booleanValue();
    }

    @Nullable
    public final Bundle getArguments() {
        return this.h;
    }

    @NonNull
    public final FragmentManager getChildFragmentManager() {
        if (this.t != null) {
            return this.u;
        }
        throw new IllegalStateException("Fragment " + this + " has not been attached yet.");
    }

    @Nullable
    public Context getContext() {
        if (this.t == null) {
            return null;
        }
        return this.t.b();
    }

    @Override // androidx.lifecycle.HasDefaultViewModelProviderFactory
    @NonNull
    public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        if (this.s == null) {
            throw new IllegalStateException("Can't access ViewModels from detached fragment");
        }
        if (this.U == null) {
            Application application = null;
            Context applicationContext = requireContext().getApplicationContext();
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
            if (application == null && FragmentManager.a(3)) {
                Log.d("FragmentManager", "Could not find Application instance from Context " + requireContext().getApplicationContext() + ", you will not be able to use AndroidViewModel with the default ViewModelProvider.Factory");
            }
            this.U = new SavedStateViewModelFactory(application, this, getArguments());
        }
        return this.U;
    }

    @Nullable
    public Object getEnterTransition() {
        if (this.J == null) {
            return null;
        }
        return this.J.k;
    }

    @Nullable
    public Object getExitTransition() {
        if (this.J == null) {
            return null;
        }
        return this.J.m;
    }

    @Nullable
    @Deprecated
    public final FragmentManager getFragmentManager() {
        return this.s;
    }

    @Nullable
    public final Object getHost() {
        if (this.t == null) {
            return null;
        }
        return this.t.onGetHost();
    }

    public final int getId() {
        return this.w;
    }

    @NonNull
    public final LayoutInflater getLayoutInflater() {
        return this.O == null ? b((Bundle) null) : this.O;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Deprecated
    public LayoutInflater getLayoutInflater(@Nullable Bundle bundle) {
        if (this.t == null) {
            throw new IllegalStateException("onGetLayoutInflater() cannot be executed until the Fragment is attached to the FragmentManager.");
        }
        LayoutInflater layoutInflaterOnGetLayoutInflater = this.t.onGetLayoutInflater();
        LayoutInflaterCompat.setFactory2(layoutInflaterOnGetLayoutInflater, this.u.B());
        return layoutInflaterOnGetLayoutInflater;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    @NonNull
    public Lifecycle getLifecycle() {
        return this.R;
    }

    @NonNull
    @Deprecated
    public LoaderManager getLoaderManager() {
        return LoaderManager.getInstance(this);
    }

    @Nullable
    public final Fragment getParentFragment() {
        return this.v;
    }

    @NonNull
    public final FragmentManager getParentFragmentManager() {
        FragmentManager fragmentManager = this.s;
        if (fragmentManager != null) {
            return fragmentManager;
        }
        throw new IllegalStateException("Fragment " + this + " not associated with a fragment manager.");
    }

    @Nullable
    public Object getReenterTransition() {
        if (this.J == null) {
            return null;
        }
        return this.J.n == a ? getExitTransition() : this.J.n;
    }

    @NonNull
    public final Resources getResources() {
        return requireContext().getResources();
    }

    @Deprecated
    public final boolean getRetainInstance() {
        return this.B;
    }

    @Nullable
    public Object getReturnTransition() {
        if (this.J == null) {
            return null;
        }
        return this.J.l == a ? getEnterTransition() : this.J.l;
    }

    @Override // androidx.savedstate.SavedStateRegistryOwner
    @NonNull
    public final SavedStateRegistry getSavedStateRegistry() {
        return this.V.getSavedStateRegistry();
    }

    @Nullable
    public Object getSharedElementEnterTransition() {
        if (this.J == null) {
            return null;
        }
        return this.J.o;
    }

    @Nullable
    public Object getSharedElementReturnTransition() {
        if (this.J == null) {
            return null;
        }
        return this.J.p == a ? getSharedElementEnterTransition() : this.J.p;
    }

    @NonNull
    public final String getString(@StringRes int i) {
        return getResources().getString(i);
    }

    @NonNull
    public final String getString(@StringRes int i, @Nullable Object... objArr) {
        return getResources().getString(i, objArr);
    }

    @Nullable
    public final String getTag() {
        return this.y;
    }

    @Nullable
    @Deprecated
    public final Fragment getTargetFragment() {
        if (this.i != null) {
            return this.i;
        }
        if (this.s == null || this.j == null) {
            return null;
        }
        return this.s.b(this.j);
    }

    @Deprecated
    public final int getTargetRequestCode() {
        return this.k;
    }

    @NonNull
    public final CharSequence getText(@StringRes int i) {
        return getResources().getText(i);
    }

    @Deprecated
    public boolean getUserVisibleHint() {
        return this.I;
    }

    @Nullable
    public View getView() {
        return this.G;
    }

    @NonNull
    @MainThread
    public LifecycleOwner getViewLifecycleOwner() {
        if (this.S != null) {
            return this.S;
        }
        throw new IllegalStateException("Can't access the Fragment View's LifecycleOwner when getView() is null i.e., before onCreateView() or after onDestroyView()");
    }

    @NonNull
    public LiveData<LifecycleOwner> getViewLifecycleOwnerLiveData() {
        return this.T;
    }

    @Override // androidx.lifecycle.ViewModelStoreOwner
    @NonNull
    public ViewModelStore getViewModelStore() {
        if (this.s == null) {
            throw new IllegalStateException("Can't access ViewModels from detached fragment");
        }
        if (getMinimumMaxLifecycleState() != Lifecycle.State.INITIALIZED.ordinal()) {
            return this.s.c(this);
        }
        throw new IllegalStateException("Calling getViewModelStore() before a Fragment reaches onCreate() when using setMaxLifecycle(INITIALIZED) is not supported");
    }

    void h() {
        this.u.l();
        this.u.a(true);
        this.b = 5;
        this.mCalled = false;
        onStart();
        if (this.mCalled) {
            this.R.handleLifecycleEvent(Lifecycle.Event.ON_START);
            if (this.G != null) {
                this.S.a(Lifecycle.Event.ON_START);
            }
            this.u.q();
            return;
        }
        throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onStart()");
    }

    @SuppressLint({"KotlinPropertyAccess"})
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public final boolean hasOptionsMenu() {
        return this.D;
    }

    public final int hashCode() {
        return super.hashCode();
    }

    void i() {
        this.u.l();
        this.u.a(true);
        this.b = 7;
        this.mCalled = false;
        onResume();
        if (this.mCalled) {
            this.R.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
            if (this.G != null) {
                this.S.a(Lifecycle.Event.ON_RESUME);
            }
            this.u.r();
            return;
        }
        throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onResume()");
    }

    public final boolean isAdded() {
        return this.t != null && this.l;
    }

    public final boolean isDetached() {
        return this.A;
    }

    public final boolean isHidden() {
        return this.z;
    }

    public final boolean isInLayout() {
        return this.o;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public final boolean isMenuVisible() {
        return this.E && (this.s == null || this.s.b(this.v));
    }

    public final boolean isRemoving() {
        return this.m;
    }

    public final boolean isResumed() {
        return this.b >= 7;
    }

    public final boolean isStateSaved() {
        if (this.s == null) {
            return false;
        }
        return this.s.isStateSaved();
    }

    public final boolean isVisible() {
        return (!isAdded() || isHidden() || this.G == null || this.G.getWindowToken() == null || this.G.getVisibility() != 0) ? false : true;
    }

    void j() {
        this.u.l();
    }

    void k() {
        boolean zA = this.s.a(this);
        if (this.mIsPrimaryNavigationFragment == null || this.mIsPrimaryNavigationFragment.booleanValue() != zA) {
            this.mIsPrimaryNavigationFragment = Boolean.valueOf(zA);
            onPrimaryNavigationFragmentChanged(zA);
            this.u.x();
        }
    }

    void l() {
        onLowMemory();
        this.u.w();
    }

    void m() {
        this.u.s();
        if (this.G != null) {
            this.S.a(Lifecycle.Event.ON_PAUSE);
        }
        this.R.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        this.b = 6;
        this.mCalled = false;
        onPause();
        if (this.mCalled) {
            return;
        }
        throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onPause()");
    }

    void n() {
        this.u.t();
        if (this.G != null) {
            this.S.a(Lifecycle.Event.ON_STOP);
        }
        this.R.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        this.b = 4;
        this.mCalled = false;
        onStop();
        if (this.mCalled) {
            return;
        }
        throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onStop()");
    }

    void o() {
        this.u.u();
        if (this.G != null && this.S.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
            this.S.a(Lifecycle.Event.ON_DESTROY);
        }
        this.b = 1;
        this.mCalled = false;
        onDestroyView();
        if (this.mCalled) {
            LoaderManager.getInstance(this).markForRedelivery();
            this.q = false;
        } else {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDestroyView()");
        }
    }

    @CallSuper
    @MainThread
    @Deprecated
    public void onActivityCreated(@Nullable Bundle bundle) {
        this.mCalled = true;
    }

    @Deprecated
    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
        if (FragmentManager.a(2)) {
            Log.v("FragmentManager", "Fragment " + this + " received the following in onActivityResult(): requestCode: " + i + " resultCode: " + i2 + " data: " + intent);
        }
    }

    @CallSuper
    @MainThread
    @Deprecated
    public void onAttach(@NonNull Activity activity) {
        this.mCalled = true;
    }

    @CallSuper
    @MainThread
    public void onAttach(@NonNull Context context) {
        this.mCalled = true;
        Activity activityA = this.t == null ? null : this.t.a();
        if (activityA != null) {
            this.mCalled = false;
            onAttach(activityA);
        }
    }

    @MainThread
    @Deprecated
    public void onAttachFragment(@NonNull Fragment fragment) {
    }

    @Override // android.content.ComponentCallbacks
    @CallSuper
    public void onConfigurationChanged(@NonNull Configuration configuration) {
        this.mCalled = true;
    }

    @MainThread
    public boolean onContextItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @CallSuper
    @MainThread
    public void onCreate(@Nullable Bundle bundle) {
        this.mCalled = true;
        c(bundle);
        if (this.u.b(1)) {
            return;
        }
        this.u.n();
    }

    @Nullable
    @MainThread
    public Animation onCreateAnimation(int i, boolean z, int i2) {
        return null;
    }

    @Nullable
    @MainThread
    public Animator onCreateAnimator(int i, boolean z, int i2) {
        return null;
    }

    @Override // android.view.View.OnCreateContextMenuListener
    @MainThread
    public void onCreateContextMenu(@NonNull ContextMenu contextMenu, @NonNull View view, @Nullable ContextMenu.ContextMenuInfo contextMenuInfo) {
        requireActivity().onCreateContextMenu(contextMenu, view, contextMenuInfo);
    }

    @MainThread
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
    }

    @Nullable
    @MainThread
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        if (this.mContentLayoutId != 0) {
            return layoutInflater.inflate(this.mContentLayoutId, viewGroup, false);
        }
        return null;
    }

    @CallSuper
    @MainThread
    public void onDestroy() {
        this.mCalled = true;
    }

    @MainThread
    public void onDestroyOptionsMenu() {
    }

    @CallSuper
    @MainThread
    public void onDestroyView() {
        this.mCalled = true;
    }

    @CallSuper
    @MainThread
    public void onDetach() {
        this.mCalled = true;
    }

    @NonNull
    public LayoutInflater onGetLayoutInflater(@Nullable Bundle bundle) {
        return getLayoutInflater(bundle);
    }

    @MainThread
    public void onHiddenChanged(boolean z) {
    }

    @CallSuper
    @UiThread
    @Deprecated
    public void onInflate(@NonNull Activity activity, @NonNull AttributeSet attributeSet, @Nullable Bundle bundle) {
        this.mCalled = true;
    }

    @CallSuper
    @UiThread
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attributeSet, @Nullable Bundle bundle) {
        this.mCalled = true;
        Activity activityA = this.t == null ? null : this.t.a();
        if (activityA != null) {
            this.mCalled = false;
            onInflate(activityA, attributeSet, bundle);
        }
    }

    @Override // android.content.ComponentCallbacks
    @CallSuper
    @MainThread
    public void onLowMemory() {
        this.mCalled = true;
    }

    public void onMultiWindowModeChanged(boolean z) {
    }

    @MainThread
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @MainThread
    public void onOptionsMenuClosed(@NonNull Menu menu) {
    }

    @CallSuper
    @MainThread
    public void onPause() {
        this.mCalled = true;
    }

    public void onPictureInPictureModeChanged(boolean z) {
    }

    @MainThread
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
    }

    @MainThread
    public void onPrimaryNavigationFragmentChanged(boolean z) {
    }

    @Deprecated
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
    }

    @CallSuper
    @MainThread
    public void onResume() {
        this.mCalled = true;
    }

    @MainThread
    public void onSaveInstanceState(@NonNull Bundle bundle) {
    }

    @CallSuper
    @MainThread
    public void onStart() {
        this.mCalled = true;
    }

    @CallSuper
    @MainThread
    public void onStop() {
        this.mCalled = true;
    }

    @MainThread
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
    }

    @CallSuper
    @MainThread
    public void onViewStateRestored(@Nullable Bundle bundle) {
        this.mCalled = true;
    }

    void p() {
        this.u.v();
        this.R.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        this.b = 0;
        this.mCalled = false;
        this.P = false;
        onDestroy();
        if (this.mCalled) {
            return;
        }
        throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDestroy()");
    }

    public void postponeEnterTransition() {
        ensureAnimationInfo().w = true;
    }

    public final void postponeEnterTransition(long j, @NonNull TimeUnit timeUnit) {
        ensureAnimationInfo().w = true;
        Handler handlerC = this.s != null ? this.s.h().c() : new Handler(Looper.getMainLooper());
        handlerC.removeCallbacks(this.K);
        handlerC.postDelayed(this.K, timeUnit.toMillis(j));
    }

    void q() {
        this.b = -1;
        this.mCalled = false;
        onDetach();
        this.O = null;
        if (this.mCalled) {
            if (this.u.isDestroyed()) {
                return;
            }
            this.u.v();
            this.u = new FragmentManagerImpl();
            return;
        }
        throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDetach()");
    }

    int r() {
        if (this.J == null) {
            return 0;
        }
        return this.J.d;
    }

    @Override // androidx.activity.result.ActivityResultCaller
    @NonNull
    @MainThread
    public final <I, O> ActivityResultLauncher<I> registerForActivityResult(@NonNull ActivityResultContract<I, O> activityResultContract, @NonNull ActivityResultCallback<O> activityResultCallback) {
        return prepareCallInternal(activityResultContract, new Function<Void, ActivityResultRegistry>() { // from class: androidx.fragment.app.Fragment.6
            @Override // androidx.arch.core.util.Function
            public ActivityResultRegistry apply(Void r1) {
                return Fragment.this.t instanceof ActivityResultRegistryOwner ? ((ActivityResultRegistryOwner) Fragment.this.t).getActivityResultRegistry() : Fragment.this.requireActivity().getActivityResultRegistry();
            }
        }, activityResultCallback);
    }

    @Override // androidx.activity.result.ActivityResultCaller
    @NonNull
    @MainThread
    public final <I, O> ActivityResultLauncher<I> registerForActivityResult(@NonNull ActivityResultContract<I, O> activityResultContract, @NonNull final ActivityResultRegistry activityResultRegistry, @NonNull ActivityResultCallback<O> activityResultCallback) {
        return prepareCallInternal(activityResultContract, new Function<Void, ActivityResultRegistry>() { // from class: androidx.fragment.app.Fragment.7
            @Override // androidx.arch.core.util.Function
            public ActivityResultRegistry apply(Void r1) {
                return activityResultRegistry;
            }
        }, activityResultCallback);
    }

    public void registerForContextMenu(@NonNull View view) {
        view.setOnCreateContextMenuListener(this);
    }

    @Deprecated
    public final void requestPermissions(@NonNull String[] strArr, int i) {
        if (this.t != null) {
            getParentFragmentManager().a(this, strArr, i);
            return;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to Activity");
    }

    @NonNull
    public final FragmentActivity requireActivity() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            return activity;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to an activity.");
    }

    @NonNull
    public final Bundle requireArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            return arguments;
        }
        throw new IllegalStateException("Fragment " + this + " does not have any arguments.");
    }

    @NonNull
    public final Context requireContext() {
        Context context = getContext();
        if (context != null) {
            return context;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to a context.");
    }

    @NonNull
    @Deprecated
    public final FragmentManager requireFragmentManager() {
        return getParentFragmentManager();
    }

    @NonNull
    public final Object requireHost() {
        Object host = getHost();
        if (host != null) {
            return host;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to a host.");
    }

    @NonNull
    public final Fragment requireParentFragment() {
        Fragment parentFragment = getParentFragment();
        if (parentFragment != null) {
            return parentFragment;
        }
        if (getContext() == null) {
            throw new IllegalStateException("Fragment " + this + " is not attached to any Fragment or host");
        }
        throw new IllegalStateException("Fragment " + this + " is not a child Fragment, it is directly attached to " + getContext());
    }

    @NonNull
    public final View requireView() {
        View view = getView();
        if (view != null) {
            return view;
        }
        throw new IllegalStateException("Fragment " + this + " did not return a View from onCreateView() or this was called before onCreateView().");
    }

    int s() {
        if (this.J == null) {
            return 0;
        }
        return this.J.e;
    }

    public void setAllowEnterTransitionOverlap(boolean z) {
        ensureAnimationInfo().r = Boolean.valueOf(z);
    }

    public void setAllowReturnTransitionOverlap(boolean z) {
        ensureAnimationInfo().q = Boolean.valueOf(z);
    }

    public void setArguments(@Nullable Bundle bundle) {
        if (this.s != null && isStateSaved()) {
            throw new IllegalStateException("Fragment already added and state has been saved");
        }
        this.h = bundle;
    }

    public void setEnterSharedElementCallback(@Nullable SharedElementCallback sharedElementCallback) {
        ensureAnimationInfo().s = sharedElementCallback;
    }

    public void setEnterTransition(@Nullable Object obj) {
        ensureAnimationInfo().k = obj;
    }

    public void setExitSharedElementCallback(@Nullable SharedElementCallback sharedElementCallback) {
        ensureAnimationInfo().t = sharedElementCallback;
    }

    public void setExitTransition(@Nullable Object obj) {
        ensureAnimationInfo().m = obj;
    }

    public void setHasOptionsMenu(boolean z) {
        if (this.D != z) {
            this.D = z;
            if (!isAdded() || isHidden()) {
                return;
            }
            this.t.onSupportInvalidateOptionsMenu();
        }
    }

    public void setInitialSavedState(@Nullable SavedState savedState) {
        if (this.s != null) {
            throw new IllegalStateException("Fragment already added");
        }
        this.c = (savedState == null || savedState.a == null) ? null : savedState.a;
    }

    public void setMenuVisibility(boolean z) {
        if (this.E != z) {
            this.E = z;
            if (this.D && isAdded() && !isHidden()) {
                this.t.onSupportInvalidateOptionsMenu();
            }
        }
    }

    public void setReenterTransition(@Nullable Object obj) {
        ensureAnimationInfo().n = obj;
    }

    @Deprecated
    public void setRetainInstance(boolean z) {
        this.B = z;
        if (this.s == null) {
            this.C = true;
        } else if (z) {
            this.s.d(this);
        } else {
            this.s.e(this);
        }
    }

    public void setReturnTransition(@Nullable Object obj) {
        ensureAnimationInfo().l = obj;
    }

    public void setSharedElementEnterTransition(@Nullable Object obj) {
        ensureAnimationInfo().o = obj;
    }

    public void setSharedElementReturnTransition(@Nullable Object obj) {
        ensureAnimationInfo().p = obj;
    }

    @Deprecated
    public void setTargetFragment(@Nullable Fragment fragment, int i) {
        FragmentManager fragmentManager = this.s;
        FragmentManager fragmentManager2 = fragment != null ? fragment.s : null;
        if (fragmentManager != null && fragmentManager2 != null && fragmentManager != fragmentManager2) {
            throw new IllegalArgumentException("Fragment " + fragment + " must share the same FragmentManager to be set as a target fragment");
        }
        for (Fragment targetFragment = fragment; targetFragment != null; targetFragment = targetFragment.getTargetFragment()) {
            if (targetFragment.equals(this)) {
                throw new IllegalArgumentException("Setting " + fragment + " as the target of " + this + " would create a target cycle");
            }
        }
        if (fragment == null) {
            this.j = null;
        } else {
            if (this.s == null || fragment.s == null) {
                this.j = null;
                this.i = fragment;
                this.k = i;
            }
            this.j = fragment.g;
        }
        this.i = null;
        this.k = i;
    }

    @Deprecated
    public void setUserVisibleHint(boolean z) {
        if (!this.I && z && this.b < 5 && this.s != null && isAdded() && this.P) {
            this.s.a(this.s.h(this));
        }
        this.I = z;
        this.H = this.b < 5 && !z;
        if (this.c != null) {
            this.f = Boolean.valueOf(z);
        }
    }

    public boolean shouldShowRequestPermissionRationale(@NonNull String str) {
        if (this.t != null) {
            return this.t.onShouldShowRequestPermissionRationale(str);
        }
        return false;
    }

    public void startActivity(@SuppressLint({"UnknownNullness"}) Intent intent) {
        startActivity(intent, null);
    }

    public void startActivity(@SuppressLint({"UnknownNullness"}) Intent intent, @Nullable Bundle bundle) {
        if (this.t != null) {
            this.t.onStartActivityFromFragment(this, intent, -1, bundle);
            return;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to Activity");
    }

    @Deprecated
    public void startActivityForResult(@SuppressLint({"UnknownNullness"}) Intent intent, int i) {
        startActivityForResult(intent, i, null);
    }

    @Deprecated
    public void startActivityForResult(@SuppressLint({"UnknownNullness"}) Intent intent, int i, @Nullable Bundle bundle) {
        if (this.t != null) {
            getParentFragmentManager().a(this, intent, i, bundle);
            return;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to Activity");
    }

    @Deprecated
    public void startIntentSenderForResult(@SuppressLint({"UnknownNullness"}) IntentSender intentSender, int i, @Nullable Intent intent, int i2, int i3, int i4, @Nullable Bundle bundle) throws IntentSender.SendIntentException {
        if (this.t == null) {
            throw new IllegalStateException("Fragment " + this + " not attached to Activity");
        }
        if (FragmentManager.a(2)) {
            Log.v("FragmentManager", "Fragment " + this + " received the following in startIntentSenderForResult() requestCode: " + i + " IntentSender: " + intentSender + " fillInIntent: " + intent + " options: " + bundle);
        }
        getParentFragmentManager().a(this, intentSender, i, intent, i2, i3, i4, bundle);
    }

    public void startPostponedEnterTransition() {
        if (this.J == null || !ensureAnimationInfo().w) {
            return;
        }
        if (this.t == null) {
            ensureAnimationInfo().w = false;
        } else if (Looper.myLooper() != this.t.c().getLooper()) {
            this.t.c().postAtFrontOfQueue(new Runnable() { // from class: androidx.fragment.app.Fragment.2
                @Override // java.lang.Runnable
                public void run() {
                    Fragment.this.a(false);
                }
            });
        } else {
            a(true);
        }
    }

    int t() {
        if (this.J == null) {
            return 0;
        }
        return this.J.f;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append(getClass().getSimpleName());
        sb.append("{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(h.d);
        sb.append(" (");
        sb.append(this.g);
        if (this.w != 0) {
            sb.append(" id=0x");
            sb.append(Integer.toHexString(this.w));
        }
        if (this.y != null) {
            sb.append(" tag=");
            sb.append(this.y);
        }
        sb.append(")");
        return sb.toString();
    }

    int u() {
        if (this.J == null) {
            return 0;
        }
        return this.J.g;
    }

    public void unregisterForContextMenu(@NonNull View view) {
        view.setOnCreateContextMenuListener(null);
    }

    boolean v() {
        if (this.J == null) {
            return false;
        }
        return this.J.c;
    }

    int w() {
        if (this.J == null) {
            return 0;
        }
        return this.J.h;
    }

    @NonNull
    ArrayList<String> x() {
        return (this.J == null || this.J.i == null) ? new ArrayList<>() : this.J.i;
    }

    @NonNull
    ArrayList<String> y() {
        return (this.J == null || this.J.j == null) ? new ArrayList<>() : this.J.j;
    }

    SharedElementCallback z() {
        if (this.J == null) {
            return null;
        }
        return this.J.s;
    }
}
