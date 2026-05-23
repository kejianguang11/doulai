package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.ActivityResultRegistryOwner;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.IdRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.collection.ArraySet;
import androidx.core.os.CancellationSignal;
import androidx.fragment.R;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentAnim;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentTransition;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import com.alipay.sdk.util.h;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public abstract class FragmentManager implements FragmentResultOwner {
    private static boolean DEBUG = false;
    private static final String EXTRA_CREATED_FILLIN_INTENT = "androidx.fragment.extra.ACTIVITY_OPTIONS_BUNDLE";
    public static final int POP_BACK_STACK_INCLUSIVE = 1;
    static boolean a = true;
    ArrayList<BackStackRecord> b;

    @Nullable
    Fragment d;
    private ArrayList<OnBackStackChangedListener> mBackStackChangeListeners;
    private FragmentContainer mContainer;
    private ArrayList<Fragment> mCreatedMenus;
    private boolean mDestroyed;
    private boolean mExecutingActions;
    private boolean mHavePendingDeferredStart;
    private FragmentHostCallback<?> mHost;
    private boolean mNeedMenuInvalidate;
    private FragmentManagerViewModel mNonConfig;
    private OnBackPressedDispatcher mOnBackPressedDispatcher;
    private Fragment mParent;
    private ArrayList<StartEnterTransitionListener> mPostponedTransactions;
    private ActivityResultLauncher<String[]> mRequestPermissions;
    private ActivityResultLauncher<Intent> mStartActivityForResult;
    private ActivityResultLauncher<IntentSenderRequest> mStartIntentSenderForResult;
    private boolean mStateSaved;
    private boolean mStopped;
    private ArrayList<Fragment> mTmpAddedFragments;
    private ArrayList<Boolean> mTmpIsPop;
    private ArrayList<BackStackRecord> mTmpRecords;
    private final ArrayList<OpGenerator> mPendingActions = new ArrayList<>();
    private final FragmentStore mFragmentStore = new FragmentStore();
    private final FragmentLayoutInflaterFactory mLayoutInflaterFactory = new FragmentLayoutInflaterFactory(this);
    private final OnBackPressedCallback mOnBackPressedCallback = new OnBackPressedCallback(false) { // from class: androidx.fragment.app.FragmentManager.1
        @Override // androidx.activity.OnBackPressedCallback
        public void handleOnBackPressed() {
            FragmentManager.this.a();
        }
    };
    private final AtomicInteger mBackStackIndex = new AtomicInteger();
    private final Map<String, Bundle> mResults = Collections.synchronizedMap(new HashMap());
    private final Map<String, LifecycleAwareResultListener> mResultListeners = Collections.synchronizedMap(new HashMap());
    private Map<Fragment, HashSet<CancellationSignal>> mExitAnimationCancellationSignals = Collections.synchronizedMap(new HashMap());
    private final FragmentTransition.Callback mFragmentTransitionCallback = new FragmentTransition.Callback() { // from class: androidx.fragment.app.FragmentManager.2
        @Override // androidx.fragment.app.FragmentTransition.Callback
        public void onComplete(@NonNull Fragment fragment, @NonNull CancellationSignal cancellationSignal) {
            if (cancellationSignal.isCanceled()) {
                return;
            }
            FragmentManager.this.b(fragment, cancellationSignal);
        }

        @Override // androidx.fragment.app.FragmentTransition.Callback
        public void onStart(@NonNull Fragment fragment, @NonNull CancellationSignal cancellationSignal) {
            FragmentManager.this.a(fragment, cancellationSignal);
        }
    };
    private final FragmentLifecycleCallbacksDispatcher mLifecycleCallbacksDispatcher = new FragmentLifecycleCallbacksDispatcher(this);
    private final CopyOnWriteArrayList<FragmentOnAttachListener> mOnAttachListeners = new CopyOnWriteArrayList<>();
    int c = -1;
    private FragmentFactory mFragmentFactory = null;
    private FragmentFactory mHostFragmentFactory = new FragmentFactory() { // from class: androidx.fragment.app.FragmentManager.3
        @Override // androidx.fragment.app.FragmentFactory
        @NonNull
        public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String str) {
            return FragmentManager.this.h().instantiate(FragmentManager.this.h().b(), str, null);
        }
    };
    private SpecialEffectsControllerFactory mSpecialEffectsControllerFactory = null;
    private SpecialEffectsControllerFactory mDefaultSpecialEffectsControllerFactory = new SpecialEffectsControllerFactory() { // from class: androidx.fragment.app.FragmentManager.4
        @Override // androidx.fragment.app.SpecialEffectsControllerFactory
        @NonNull
        public SpecialEffectsController createController(@NonNull ViewGroup viewGroup) {
            return new DefaultSpecialEffectsController(viewGroup);
        }
    };
    ArrayDeque<LaunchedFragmentInfo> e = new ArrayDeque<>();
    private Runnable mExecCommit = new Runnable() { // from class: androidx.fragment.app.FragmentManager.5
        @Override // java.lang.Runnable
        public void run() {
            FragmentManager.this.a(true);
        }
    };

    public interface BackStackEntry {
        @Nullable
        @Deprecated
        CharSequence getBreadCrumbShortTitle();

        @StringRes
        @Deprecated
        int getBreadCrumbShortTitleRes();

        @Nullable
        @Deprecated
        CharSequence getBreadCrumbTitle();

        @StringRes
        @Deprecated
        int getBreadCrumbTitleRes();

        int getId();

        @Nullable
        String getName();
    }

    static class FragmentIntentSenderContract extends ActivityResultContract<IntentSenderRequest, ActivityResult> {
        FragmentIntentSenderContract() {
        }

        @Override // androidx.activity.result.contract.ActivityResultContract
        @NonNull
        public Intent createIntent(@NonNull Context context, IntentSenderRequest intentSenderRequest) {
            Bundle bundleExtra;
            Intent intent = new Intent(ActivityResultContracts.StartIntentSenderForResult.ACTION_INTENT_SENDER_REQUEST);
            Intent fillInIntent = intentSenderRequest.getFillInIntent();
            if (fillInIntent != null && (bundleExtra = fillInIntent.getBundleExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE)) != null) {
                intent.putExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE, bundleExtra);
                fillInIntent.removeExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE);
                if (fillInIntent.getBooleanExtra(FragmentManager.EXTRA_CREATED_FILLIN_INTENT, false)) {
                    intentSenderRequest = new IntentSenderRequest.Builder(intentSenderRequest.getIntentSender()).setFillInIntent(null).setFlags(intentSenderRequest.getFlagsValues(), intentSenderRequest.getFlagsMask()).build();
                }
            }
            intent.putExtra(ActivityResultContracts.StartIntentSenderForResult.EXTRA_INTENT_SENDER_REQUEST, intentSenderRequest);
            if (FragmentManager.a(2)) {
                Log.v("FragmentManager", "CreateIntent created the following intent: " + intent);
            }
            return intent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.activity.result.contract.ActivityResultContract
        @NonNull
        public ActivityResult parseResult(int i, @Nullable Intent intent) {
            return new ActivityResult(i, intent);
        }
    }

    public static abstract class FragmentLifecycleCallbacks {
        @Deprecated
        public void onFragmentActivityCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @Nullable Bundle bundle) {
        }

        public void onFragmentAttached(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull Context context) {
        }

        public void onFragmentCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @Nullable Bundle bundle) {
        }

        public void onFragmentDestroyed(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentDetached(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentPaused(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentPreAttached(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull Context context) {
        }

        public void onFragmentPreCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @Nullable Bundle bundle) {
        }

        public void onFragmentResumed(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentSaveInstanceState(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull Bundle bundle) {
        }

        public void onFragmentStarted(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentStopped(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }

        public void onFragmentViewCreated(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull View view, @Nullable Bundle bundle) {
        }

        public void onFragmentViewDestroyed(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        }
    }

    @SuppressLint({"BanParcelableUsage"})
    static class LaunchedFragmentInfo implements Parcelable {
        public static final Parcelable.Creator<LaunchedFragmentInfo> CREATOR = new Parcelable.Creator<LaunchedFragmentInfo>() { // from class: androidx.fragment.app.FragmentManager.LaunchedFragmentInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LaunchedFragmentInfo createFromParcel(Parcel parcel) {
                return new LaunchedFragmentInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public LaunchedFragmentInfo[] newArray(int i) {
                return new LaunchedFragmentInfo[i];
            }
        };
        String a;
        int b;

        LaunchedFragmentInfo(@NonNull Parcel parcel) {
            this.a = parcel.readString();
            this.b = parcel.readInt();
        }

        LaunchedFragmentInfo(@NonNull String str, int i) {
            this.a = str;
            this.b = i;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.a);
            parcel.writeInt(this.b);
        }
    }

    private static class LifecycleAwareResultListener implements FragmentResultListener {
        private final Lifecycle mLifecycle;
        private final FragmentResultListener mListener;
        private final LifecycleEventObserver mObserver;

        LifecycleAwareResultListener(@NonNull Lifecycle lifecycle, @NonNull FragmentResultListener fragmentResultListener, @NonNull LifecycleEventObserver lifecycleEventObserver) {
            this.mLifecycle = lifecycle;
            this.mListener = fragmentResultListener;
            this.mObserver = lifecycleEventObserver;
        }

        public boolean isAtLeast(Lifecycle.State state) {
            return this.mLifecycle.getCurrentState().isAtLeast(state);
        }

        @Override // androidx.fragment.app.FragmentResultListener
        public void onFragmentResult(@NonNull String str, @NonNull Bundle bundle) {
            this.mListener.onFragmentResult(str, bundle);
        }

        public void removeObserver() {
            this.mLifecycle.removeObserver(this.mObserver);
        }
    }

    public interface OnBackStackChangedListener {
        @MainThread
        void onBackStackChanged();
    }

    interface OpGenerator {
        boolean generateOps(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2);
    }

    private class PopBackStackState implements OpGenerator {
        final String a;
        final int b;
        final int c;

        PopBackStackState(String str, @Nullable int i, int i2) {
            this.a = str;
            this.b = i;
            this.c = i2;
        }

        @Override // androidx.fragment.app.FragmentManager.OpGenerator
        public boolean generateOps(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2) {
            if (FragmentManager.this.d == null || this.b >= 0 || this.a != null || !FragmentManager.this.d.getChildFragmentManager().popBackStackImmediate()) {
                return FragmentManager.this.a(arrayList, arrayList2, this.a, this.b, this.c);
            }
            return false;
        }
    }

    static class StartEnterTransitionListener implements Fragment.OnStartEnterTransitionListener {
        final boolean a;
        final BackStackRecord b;
        private int mNumPostponed;

        StartEnterTransitionListener(@NonNull BackStackRecord backStackRecord, boolean z) {
            this.a = z;
            this.b = backStackRecord;
        }

        void a() {
            boolean z = this.mNumPostponed > 0;
            for (Fragment fragment : this.b.a.getFragments()) {
                fragment.a((Fragment.OnStartEnterTransitionListener) null);
                if (z && fragment.F()) {
                    fragment.startPostponedEnterTransition();
                }
            }
            this.b.a.a(this.b, this.a, !z, true);
        }

        void b() {
            this.b.a.a(this.b, this.a, false, false);
        }

        public boolean isReady() {
            return this.mNumPostponed == 0;
        }

        @Override // androidx.fragment.app.Fragment.OnStartEnterTransitionListener
        public void onStartEnterTransition() {
            this.mNumPostponed--;
            if (this.mNumPostponed != 0) {
                return;
            }
            this.b.a.d();
        }

        @Override // androidx.fragment.app.Fragment.OnStartEnterTransitionListener
        public void startListening() {
            this.mNumPostponed++;
        }
    }

    @Nullable
    static Fragment a(@NonNull View view) {
        Object tag = view.getTag(R.id.fragment_container_view_tag);
        if (tag instanceof Fragment) {
            return (Fragment) tag;
        }
        return null;
    }

    static boolean a(int i) {
        return DEBUG || Log.isLoggable("FragmentManager", i);
    }

    private void addAddedFragments(@NonNull ArraySet<Fragment> arraySet) {
        if (this.c < 1) {
            return;
        }
        int iMin = Math.min(this.c, 5);
        for (Fragment fragment : this.mFragmentStore.h()) {
            if (fragment.b < iMin) {
                a(fragment, iMin);
                if (fragment.G != null && !fragment.z && fragment.L) {
                    arraySet.add(fragment);
                }
            }
        }
    }

    static int c(int i) {
        if (i == 4097) {
            return 8194;
        }
        if (i != 4099) {
            return i != 8194 ? 0 : 4097;
        }
        return 4099;
    }

    private void cancelExitAnimation(@NonNull Fragment fragment) {
        HashSet<CancellationSignal> hashSet = this.mExitAnimationCancellationSignals.get(fragment);
        if (hashSet != null) {
            Iterator<CancellationSignal> it = hashSet.iterator();
            while (it.hasNext()) {
                it.next().cancel();
            }
            hashSet.clear();
            destroyFragmentView(fragment);
            this.mExitAnimationCancellationSignals.remove(fragment);
        }
    }

    private void checkStateLoss() {
        if (isStateSaved()) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
    }

    private void cleanupExec() {
        this.mExecutingActions = false;
        this.mTmpIsPop.clear();
        this.mTmpRecords.clear();
    }

    private Set<SpecialEffectsController> collectAllSpecialEffectsController() {
        HashSet hashSet = new HashSet();
        Iterator<FragmentStateManager> it = this.mFragmentStore.g().iterator();
        while (it.hasNext()) {
            ViewGroup viewGroup = it.next().a().F;
            if (viewGroup != null) {
                hashSet.add(SpecialEffectsController.a(viewGroup, y()));
            }
        }
        return hashSet;
    }

    private Set<SpecialEffectsController> collectChangedControllers(@NonNull ArrayList<BackStackRecord> arrayList, int i, int i2) {
        ViewGroup viewGroup;
        HashSet hashSet = new HashSet();
        while (i < i2) {
            Iterator<FragmentTransaction.Op> it = arrayList.get(i).d.iterator();
            while (it.hasNext()) {
                Fragment fragment = it.next().b;
                if (fragment != null && (viewGroup = fragment.F) != null) {
                    hashSet.add(SpecialEffectsController.a(viewGroup, this));
                }
            }
            i++;
        }
        return hashSet;
    }

    private void completeShowHideFragment(@NonNull final Fragment fragment) {
        if (fragment.G != null) {
            FragmentAnim.AnimationOrAnimator animationOrAnimatorA = FragmentAnim.a(this.mHost.b(), fragment, !fragment.z, fragment.v());
            if (animationOrAnimatorA == null || animationOrAnimatorA.animator == null) {
                if (animationOrAnimatorA != null) {
                    fragment.G.startAnimation(animationOrAnimatorA.animation);
                    animationOrAnimatorA.animation.start();
                }
                fragment.G.setVisibility((!fragment.z || fragment.G()) ? 0 : 8);
                if (fragment.G()) {
                    fragment.e(false);
                }
            } else {
                animationOrAnimatorA.animator.setTarget(fragment.G);
                if (!fragment.z) {
                    fragment.G.setVisibility(0);
                } else if (fragment.G()) {
                    fragment.e(false);
                } else {
                    final ViewGroup viewGroup = fragment.F;
                    final View view = fragment.G;
                    viewGroup.startViewTransition(view);
                    animationOrAnimatorA.animator.addListener(new AnimatorListenerAdapter() { // from class: androidx.fragment.app.FragmentManager.7
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            viewGroup.endViewTransition(view);
                            animator.removeListener(this);
                            if (fragment.G == null || !fragment.z) {
                                return;
                            }
                            fragment.G.setVisibility(8);
                        }
                    });
                }
                animationOrAnimatorA.animator.start();
            }
        }
        q(fragment);
        fragment.M = false;
        fragment.onHiddenChanged(fragment.z);
    }

    private void destroyFragmentView(@NonNull Fragment fragment) {
        fragment.o();
        this.mLifecycleCallbacksDispatcher.g(fragment, false);
        fragment.F = null;
        fragment.G = null;
        fragment.S = null;
        fragment.T.setValue(null);
        fragment.o = false;
    }

    private void dispatchParentPrimaryNavigationFragmentChanged(@Nullable Fragment fragment) {
        if (fragment == null || !fragment.equals(b(fragment.g))) {
            return;
        }
        fragment.k();
    }

    private void dispatchStateChange(int i) {
        try {
            this.mExecutingActions = true;
            this.mFragmentStore.a(i);
            a(i, false);
            if (a) {
                Iterator<SpecialEffectsController> it = collectAllSpecialEffectsController().iterator();
                while (it.hasNext()) {
                    it.next().d();
                }
            }
            this.mExecutingActions = false;
            a(true);
        } catch (Throwable th) {
            this.mExecutingActions = false;
            throw th;
        }
    }

    private void doPendingDeferredStart() {
        if (this.mHavePendingDeferredStart) {
            this.mHavePendingDeferredStart = false;
            startPendingDeferredFragments();
        }
    }

    @Deprecated
    public static void enableDebugLogging(boolean z) {
        DEBUG = z;
    }

    @FragmentStateManagerControl
    public static void enableNewStateManager(boolean z) {
        a = z;
    }

    private void endAnimatingAwayFragments() {
        if (a) {
            Iterator<SpecialEffectsController> it = collectAllSpecialEffectsController().iterator();
            while (it.hasNext()) {
                it.next().d();
            }
        } else {
            if (this.mExitAnimationCancellationSignals.isEmpty()) {
                return;
            }
            for (Fragment fragment : this.mExitAnimationCancellationSignals.keySet()) {
                cancelExitAnimation(fragment);
                f(fragment);
            }
        }
    }

    private void ensureExecReady(boolean z) {
        if (this.mExecutingActions) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        }
        if (this.mHost == null) {
            if (!this.mDestroyed) {
                throw new IllegalStateException("FragmentManager has not been attached to a host.");
            }
            throw new IllegalStateException("FragmentManager has been destroyed");
        }
        if (Looper.myLooper() != this.mHost.c().getLooper()) {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
        if (!z) {
            checkStateLoss();
        }
        if (this.mTmpRecords == null) {
            this.mTmpRecords = new ArrayList<>();
            this.mTmpIsPop = new ArrayList<>();
        }
        this.mExecutingActions = true;
        try {
            executePostponedTransaction(null, null);
        } finally {
            this.mExecutingActions = false;
        }
    }

    private static void executeOps(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2, int i, int i2) {
        while (i < i2) {
            BackStackRecord backStackRecord = arrayList.get(i);
            if (arrayList2.get(i).booleanValue()) {
                backStackRecord.a(-1);
                backStackRecord.b(i == i2 + (-1));
            } else {
                backStackRecord.a(1);
                backStackRecord.a();
            }
            i++;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v1 */
    /* JADX WARN: Type inference failed for: r14v2 */
    /* JADX WARN: Type inference failed for: r14v4 */
    /* JADX WARN: Type inference failed for: r17v0, types: [androidx.fragment.app.FragmentManager] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7, types: [boolean] */
    private void executeOpsTogether(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2, int i, int i2) {
        ?? r1;
        ?? r14;
        boolean z;
        int i3;
        int i4;
        ArrayList<Boolean> arrayList3;
        int iPostponePostponableTransactions;
        ArrayList<Boolean> arrayList4;
        int i5;
        ?? r12;
        boolean z2 = arrayList.get(i).s;
        if (this.mTmpAddedFragments == null) {
            this.mTmpAddedFragments = new ArrayList<>();
        } else {
            this.mTmpAddedFragments.clear();
        }
        this.mTmpAddedFragments.addAll(this.mFragmentStore.h());
        Fragment primaryNavigationFragment = getPrimaryNavigationFragment();
        boolean z3 = false;
        for (int i6 = i; i6 < i2; i6++) {
            BackStackRecord backStackRecord = arrayList.get(i6);
            primaryNavigationFragment = !arrayList2.get(i6).booleanValue() ? backStackRecord.a(this.mTmpAddedFragments, primaryNavigationFragment) : backStackRecord.b(this.mTmpAddedFragments, primaryNavigationFragment);
            z3 = z3 || backStackRecord.j;
        }
        this.mTmpAddedFragments.clear();
        if (z2 || this.c < 1) {
            r1 = 1;
        } else if (a) {
            for (int i7 = i; i7 < i2; i7++) {
                Iterator<FragmentTransaction.Op> it = arrayList.get(i7).d.iterator();
                while (it.hasNext()) {
                    Fragment fragment = it.next().b;
                    if (fragment != null && fragment.s != null) {
                        this.mFragmentStore.a(h(fragment));
                    }
                }
            }
            r1 = 1;
        } else {
            r1 = 1;
            FragmentTransition.a(this.mHost.b(), this.mContainer, arrayList, arrayList2, i, i2, false, this.mFragmentTransitionCallback);
        }
        executeOps(arrayList, arrayList2, i, i2);
        if (a) {
            boolean zBooleanValue = arrayList2.get(i2 - 1).booleanValue();
            for (int i8 = i; i8 < i2; i8++) {
                BackStackRecord backStackRecord2 = arrayList.get(i8);
                if (zBooleanValue) {
                    for (int size = backStackRecord2.d.size() - r1; size >= 0; size--) {
                        Fragment fragment2 = backStackRecord2.d.get(size).b;
                        if (fragment2 != null) {
                            h(fragment2).c();
                        }
                    }
                } else {
                    Iterator<FragmentTransaction.Op> it2 = backStackRecord2.d.iterator();
                    while (it2.hasNext()) {
                        Fragment fragment3 = it2.next().b;
                        if (fragment3 != null) {
                            h(fragment3).c();
                        }
                    }
                }
            }
            a(this.c, r1);
            for (SpecialEffectsController specialEffectsController : collectChangedControllers(arrayList, i, i2)) {
                specialEffectsController.a(zBooleanValue);
                specialEffectsController.a();
                specialEffectsController.c();
            }
            i5 = i2;
            arrayList4 = arrayList2;
        } else {
            if (z2) {
                ArraySet arraySet = new ArraySet();
                addAddedFragments(arraySet);
                r14 = r1;
                z = z2;
                i3 = i2;
                i4 = i;
                arrayList3 = arrayList2;
                iPostponePostponableTransactions = postponePostponableTransactions(arrayList, arrayList2, i, i2, arraySet);
                makeRemovedFragmentsInvisible(arraySet);
            } else {
                r14 = r1;
                z = z2;
                i3 = i2;
                i4 = i;
                arrayList3 = arrayList2;
                iPostponePostponableTransactions = i3;
            }
            if (iPostponePostponableTransactions == i4 || !z) {
                arrayList4 = arrayList3;
                i5 = i3;
            } else {
                if (this.c >= r14) {
                    arrayList4 = arrayList3;
                    int i9 = iPostponePostponableTransactions;
                    i5 = i3;
                    r12 = r14;
                    FragmentTransition.a(this.mHost.b(), this.mContainer, arrayList, arrayList2, i, i9, true, this.mFragmentTransitionCallback);
                } else {
                    arrayList4 = arrayList3;
                    i5 = i3;
                    r12 = r14;
                }
                a(this.c, r12);
            }
        }
        for (int i10 = i; i10 < i5; i10++) {
            BackStackRecord backStackRecord3 = arrayList.get(i10);
            if (arrayList4.get(i10).booleanValue() && backStackRecord3.c >= 0) {
                backStackRecord3.c = -1;
            }
            backStackRecord3.runOnCommitRunnables();
        }
        if (z3) {
            reportBackStackChanged();
        }
    }

    private void executePostponedTransaction(@Nullable ArrayList<BackStackRecord> arrayList, @Nullable ArrayList<Boolean> arrayList2) {
        int iIndexOf;
        int iIndexOf2;
        int size = this.mPostponedTransactions == null ? 0 : this.mPostponedTransactions.size();
        int i = 0;
        while (i < size) {
            StartEnterTransitionListener startEnterTransitionListener = this.mPostponedTransactions.get(i);
            if (arrayList == null || startEnterTransitionListener.a || (iIndexOf2 = arrayList.indexOf(startEnterTransitionListener.b)) == -1 || arrayList2 == null || !arrayList2.get(iIndexOf2).booleanValue()) {
                if (startEnterTransitionListener.isReady() || (arrayList != null && startEnterTransitionListener.b.a(arrayList, 0, arrayList.size()))) {
                    this.mPostponedTransactions.remove(i);
                    i--;
                    size--;
                    if (arrayList == null || startEnterTransitionListener.a || (iIndexOf = arrayList.indexOf(startEnterTransitionListener.b)) == -1 || arrayList2 == null || !arrayList2.get(iIndexOf).booleanValue()) {
                        startEnterTransitionListener.a();
                    }
                }
                i++;
            } else {
                this.mPostponedTransactions.remove(i);
                i--;
                size--;
            }
            startEnterTransitionListener.b();
            i++;
        }
    }

    @NonNull
    public static <F extends Fragment> F findFragment(@NonNull View view) {
        F f = (F) findViewFragment(view);
        if (f != null) {
            return f;
        }
        throw new IllegalStateException("View " + view + " does not have a Fragment set");
    }

    @Nullable
    private static Fragment findViewFragment(@NonNull View view) {
        while (view != null) {
            Fragment fragmentA = a(view);
            if (fragmentA != null) {
                return fragmentA;
            }
            Object parent = view.getParent();
            view = parent instanceof View ? (View) parent : null;
        }
        return null;
    }

    private void forcePostponedTransactions() {
        if (a) {
            Iterator<SpecialEffectsController> it = collectAllSpecialEffectsController().iterator();
            while (it.hasNext()) {
                it.next().b();
            }
        } else if (this.mPostponedTransactions != null) {
            while (!this.mPostponedTransactions.isEmpty()) {
                this.mPostponedTransactions.remove(0).a();
            }
        }
    }

    private boolean generateOpsForPendingActions(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2) {
        synchronized (this.mPendingActions) {
            if (this.mPendingActions.isEmpty()) {
                return false;
            }
            int size = this.mPendingActions.size();
            boolean zGenerateOps = false;
            for (int i = 0; i < size; i++) {
                zGenerateOps |= this.mPendingActions.get(i).generateOps(arrayList, arrayList2);
            }
            this.mPendingActions.clear();
            this.mHost.c().removeCallbacks(this.mExecCommit);
            return zGenerateOps;
        }
    }

    @NonNull
    private FragmentManagerViewModel getChildNonConfig(@NonNull Fragment fragment) {
        return this.mNonConfig.d(fragment);
    }

    private ViewGroup getFragmentContainer(@NonNull Fragment fragment) {
        if (fragment.F != null) {
            return fragment.F;
        }
        if (fragment.x > 0 && this.mContainer.onHasView()) {
            View viewOnFindViewById = this.mContainer.onFindViewById(fragment.x);
            if (viewOnFindViewById instanceof ViewGroup) {
                return (ViewGroup) viewOnFindViewById;
            }
        }
        return null;
    }

    private boolean isMenuAvailable(@NonNull Fragment fragment) {
        return (fragment.D && fragment.E) || fragment.u.A();
    }

    private void makeRemovedFragmentsInvisible(@NonNull ArraySet<Fragment> arraySet) {
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            Fragment fragmentValueAt = arraySet.valueAt(i);
            if (!fragmentValueAt.l) {
                View viewRequireView = fragmentValueAt.requireView();
                fragmentValueAt.N = viewRequireView.getAlpha();
                viewRequireView.setAlpha(0.0f);
            }
        }
    }

    private boolean popBackStackImmediate(@Nullable String str, int i, int i2) {
        a(false);
        ensureExecReady(true);
        if (this.d != null && i < 0 && str == null && this.d.getChildFragmentManager().popBackStackImmediate()) {
            return true;
        }
        boolean zA = a(this.mTmpRecords, this.mTmpIsPop, str, i, i2);
        if (zA) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        updateOnBackPressedCallbackEnabled();
        doPendingDeferredStart();
        this.mFragmentStore.d();
        return zA;
    }

    private int postponePostponableTransactions(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2, int i, int i2, @NonNull ArraySet<Fragment> arraySet) {
        int i3 = i2;
        for (int i4 = i2 - 1; i4 >= i; i4--) {
            BackStackRecord backStackRecord = arrayList.get(i4);
            boolean zBooleanValue = arrayList2.get(i4).booleanValue();
            if (backStackRecord.b() && !backStackRecord.a(arrayList, i4 + 1, i2)) {
                if (this.mPostponedTransactions == null) {
                    this.mPostponedTransactions = new ArrayList<>();
                }
                StartEnterTransitionListener startEnterTransitionListener = new StartEnterTransitionListener(backStackRecord, zBooleanValue);
                this.mPostponedTransactions.add(startEnterTransitionListener);
                backStackRecord.a(startEnterTransitionListener);
                if (zBooleanValue) {
                    backStackRecord.a();
                } else {
                    backStackRecord.b(false);
                }
                i3--;
                if (i4 != i3) {
                    arrayList.remove(i4);
                    arrayList.add(i3, backStackRecord);
                }
                addAddedFragments(arraySet);
            }
        }
        return i3;
    }

    private void removeRedundantOperationsAndExecute(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2) {
        if (arrayList.isEmpty()) {
            return;
        }
        if (arrayList.size() != arrayList2.size()) {
            throw new IllegalStateException("Internal error with the back stack records");
        }
        executePostponedTransaction(arrayList, arrayList2);
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            if (!arrayList.get(i).s) {
                if (i2 != i) {
                    executeOpsTogether(arrayList, arrayList2, i2, i);
                }
                i2 = i + 1;
                if (arrayList2.get(i).booleanValue()) {
                    while (i2 < size && arrayList2.get(i2).booleanValue() && !arrayList.get(i2).s) {
                        i2++;
                    }
                }
                executeOpsTogether(arrayList, arrayList2, i, i2);
                i = i2 - 1;
            }
            i++;
        }
        if (i2 != size) {
            executeOpsTogether(arrayList, arrayList2, i2, size);
        }
    }

    private void reportBackStackChanged() {
        if (this.mBackStackChangeListeners != null) {
            for (int i = 0; i < this.mBackStackChangeListeners.size(); i++) {
                this.mBackStackChangeListeners.get(i).onBackStackChanged();
            }
        }
    }

    private void setVisibleRemovingFragment(@NonNull Fragment fragment) {
        ViewGroup fragmentContainer = getFragmentContainer(fragment);
        if (fragmentContainer == null || fragment.r() + fragment.s() + fragment.t() + fragment.u() <= 0) {
            return;
        }
        if (fragmentContainer.getTag(R.id.visible_removing_fragment_view_tag) == null) {
            fragmentContainer.setTag(R.id.visible_removing_fragment_view_tag, fragment);
        }
        ((Fragment) fragmentContainer.getTag(R.id.visible_removing_fragment_view_tag)).d(fragment.v());
    }

    private void startPendingDeferredFragments() {
        Iterator<FragmentStateManager> it = this.mFragmentStore.g().iterator();
        while (it.hasNext()) {
            a(it.next());
        }
    }

    private void throwException(RuntimeException runtimeException) {
        Log.e("FragmentManager", runtimeException.getMessage());
        Log.e("FragmentManager", "Activity state:");
        PrintWriter printWriter = new PrintWriter(new LogWriter("FragmentManager"));
        try {
            if (this.mHost != null) {
                this.mHost.onDump("  ", null, printWriter, new String[0]);
            } else {
                dump("  ", null, printWriter, new String[0]);
            }
            throw runtimeException;
        } catch (Exception e) {
            Log.e("FragmentManager", "Failed dumping state", e);
            throw runtimeException;
        }
    }

    private void updateOnBackPressedCallbackEnabled() {
        synchronized (this.mPendingActions) {
            if (this.mPendingActions.isEmpty()) {
                this.mOnBackPressedCallback.setEnabled(getBackStackEntryCount() > 0 && a(this.mParent));
            } else {
                this.mOnBackPressedCallback.setEnabled(true);
            }
        }
    }

    boolean A() {
        boolean zIsMenuAvailable = false;
        for (Fragment fragment : this.mFragmentStore.i()) {
            if (fragment != null) {
                zIsMenuAvailable = isMenuAvailable(fragment);
            }
            if (zIsMenuAvailable) {
                return true;
            }
        }
        return false;
    }

    @NonNull
    LayoutInflater.Factory2 B() {
        return this.mLayoutInflaterFactory;
    }

    Fragment a(@NonNull String str) {
        return this.mFragmentStore.d(str);
    }

    void a() {
        a(true);
        if (this.mOnBackPressedCallback.isEnabled()) {
            popBackStackImmediate();
        } else {
            this.mOnBackPressedDispatcher.onBackPressed();
        }
    }

    void a(int i, boolean z) {
        if (this.mHost == null && i != -1) {
            throw new IllegalStateException("No activity");
        }
        if (z || i != this.c) {
            this.c = i;
            if (a) {
                this.mFragmentStore.c();
            } else {
                Iterator<Fragment> it = this.mFragmentStore.h().iterator();
                while (it.hasNext()) {
                    g(it.next());
                }
                for (FragmentStateManager fragmentStateManager : this.mFragmentStore.g()) {
                    Fragment fragmentA = fragmentStateManager.a();
                    if (!fragmentA.L) {
                        g(fragmentA);
                    }
                    if (fragmentA.m && !fragmentA.c()) {
                        this.mFragmentStore.b(fragmentStateManager);
                    }
                }
            }
            startPendingDeferredFragments();
            if (this.mNeedMenuInvalidate && this.mHost != null && this.c == 7) {
                this.mHost.onSupportInvalidateOptionsMenu();
                this.mNeedMenuInvalidate = false;
            }
        }
    }

    void a(@NonNull Configuration configuration) {
        for (Fragment fragment : this.mFragmentStore.h()) {
            if (fragment != null) {
                fragment.a(configuration);
            }
        }
    }

    void a(@Nullable Parcelable parcelable) {
        FragmentStateManager fragmentStateManager;
        if (parcelable == null) {
            return;
        }
        FragmentManagerState fragmentManagerState = (FragmentManagerState) parcelable;
        if (fragmentManagerState.a == null) {
            return;
        }
        this.mFragmentStore.b();
        for (FragmentState fragmentState : fragmentManagerState.a) {
            if (fragmentState != null) {
                Fragment fragmentA = this.mNonConfig.a(fragmentState.b);
                if (fragmentA != null) {
                    if (a(2)) {
                        Log.v("FragmentManager", "restoreSaveState: re-attaching retained " + fragmentA);
                    }
                    fragmentStateManager = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, fragmentA, fragmentState);
                } else {
                    fragmentStateManager = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, this.mHost.b().getClassLoader(), getFragmentFactory(), fragmentState);
                }
                Fragment fragmentA2 = fragmentStateManager.a();
                fragmentA2.s = this;
                if (a(2)) {
                    Log.v("FragmentManager", "restoreSaveState: active (" + fragmentA2.g + "): " + fragmentA2);
                }
                fragmentStateManager.a(this.mHost.b().getClassLoader());
                this.mFragmentStore.a(fragmentStateManager);
                fragmentStateManager.a(this.c);
            }
        }
        for (Fragment fragment : this.mNonConfig.c()) {
            if (!this.mFragmentStore.b(fragment.g)) {
                if (a(2)) {
                    Log.v("FragmentManager", "Discarding retained Fragment " + fragment + " that was not found in the set of active Fragments " + fragmentManagerState.a);
                }
                this.mNonConfig.c(fragment);
                fragment.s = this;
                FragmentStateManager fragmentStateManager2 = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, fragment);
                fragmentStateManager2.a(1);
                fragmentStateManager2.c();
                fragment.m = true;
                fragmentStateManager2.c();
            }
        }
        this.mFragmentStore.a(fragmentManagerState.b);
        if (fragmentManagerState.c != null) {
            this.b = new ArrayList<>(fragmentManagerState.c.length);
            for (int i = 0; i < fragmentManagerState.c.length; i++) {
                BackStackRecord backStackRecordInstantiate = fragmentManagerState.c[i].instantiate(this);
                if (a(2)) {
                    Log.v("FragmentManager", "restoreAllState: back stack #" + i + " (index " + backStackRecordInstantiate.c + "): " + backStackRecordInstantiate);
                    PrintWriter printWriter = new PrintWriter(new LogWriter("FragmentManager"));
                    backStackRecordInstantiate.dump("  ", printWriter, false);
                    printWriter.close();
                }
                this.b.add(backStackRecordInstantiate);
            }
        } else {
            this.b = null;
        }
        this.mBackStackIndex.set(fragmentManagerState.d);
        if (fragmentManagerState.e != null) {
            this.d = b(fragmentManagerState.e);
            dispatchParentPrimaryNavigationFragmentChanged(this.d);
        }
        ArrayList<String> arrayList = fragmentManagerState.f;
        if (arrayList != null) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                Bundle bundle = fragmentManagerState.g.get(i2);
                bundle.setClassLoader(this.mHost.b().getClassLoader());
                this.mResults.put(arrayList.get(i2), bundle);
            }
        }
        this.e = new ArrayDeque<>(fragmentManagerState.h);
    }

    void a(@Nullable Parcelable parcelable, @Nullable FragmentManagerNonConfig fragmentManagerNonConfig) {
        if (this.mHost instanceof ViewModelStoreOwner) {
            throwException(new IllegalStateException("You must use restoreSaveState when your FragmentHostCallback implements ViewModelStoreOwner"));
        }
        this.mNonConfig.a(fragmentManagerNonConfig);
        a(parcelable);
    }

    void a(BackStackRecord backStackRecord) {
        if (this.b == null) {
            this.b = new ArrayList<>();
        }
        this.b.add(backStackRecord);
    }

    void a(@NonNull BackStackRecord backStackRecord, boolean z, boolean z2, boolean z3) {
        if (z) {
            backStackRecord.b(z3);
        } else {
            backStackRecord.a();
        }
        ArrayList arrayList = new ArrayList(1);
        ArrayList arrayList2 = new ArrayList(1);
        arrayList.add(backStackRecord);
        arrayList2.add(Boolean.valueOf(z));
        if (z2 && this.c >= 1) {
            FragmentTransition.a(this.mHost.b(), this.mContainer, arrayList, arrayList2, 0, 1, true, this.mFragmentTransitionCallback);
        }
        if (z3) {
            a(this.c, true);
        }
        for (Fragment fragment : this.mFragmentStore.i()) {
            if (fragment != null && fragment.G != null && fragment.L && backStackRecord.b(fragment.x)) {
                if (fragment.N > 0.0f) {
                    fragment.G.setAlpha(fragment.N);
                }
                if (z3) {
                    fragment.N = 0.0f;
                } else {
                    fragment.N = -1.0f;
                    fragment.L = false;
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x015a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void a(@NonNull Fragment fragment, int i) {
        FragmentStateManager fragmentStateManagerC = this.mFragmentStore.c(fragment.g);
        if (fragmentStateManagerC == null) {
            fragmentStateManagerC = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, fragment);
            fragmentStateManagerC.a(1);
        }
        if (fragment.n && fragment.o && fragment.b == 2) {
            i = Math.max(i, 2);
        }
        int iMin = Math.min(i, fragmentStateManagerC.b());
        if (fragment.b > iMin) {
            if (fragment.b > iMin) {
                switch (fragment.b) {
                    case 0:
                        if (iMin < 0) {
                            fragmentStateManagerC.r();
                        }
                        break;
                    case 1:
                        if (iMin < 1) {
                            if (this.mExitAnimationCancellationSignals.get(fragment) != null) {
                                iMin = 1;
                            } else {
                                fragmentStateManagerC.q();
                            }
                        }
                        if (iMin < 0) {
                        }
                        break;
                    case 2:
                        if (iMin < 2) {
                            FragmentAnim.AnimationOrAnimator animationOrAnimatorA = null;
                            if (fragment.G != null && fragment.F != null) {
                                fragment.F.endViewTransition(fragment.G);
                                fragment.G.clearAnimation();
                                if (!fragment.d()) {
                                    if (this.c > -1 && !this.mDestroyed && fragment.G.getVisibility() == 0 && fragment.N >= 0.0f) {
                                        animationOrAnimatorA = FragmentAnim.a(this.mHost.b(), fragment, false, fragment.v());
                                    }
                                    fragment.N = 0.0f;
                                    ViewGroup viewGroup = fragment.F;
                                    View view = fragment.G;
                                    if (animationOrAnimatorA != null) {
                                        FragmentAnim.a(fragment, animationOrAnimatorA, this.mFragmentTransitionCallback);
                                    }
                                    viewGroup.removeView(view);
                                    if (a(2)) {
                                        Log.v("FragmentManager", "Removing view " + view + " for fragment " + fragment + " from container " + viewGroup);
                                    }
                                    if (viewGroup != fragment.F) {
                                    }
                                }
                            }
                            if (this.mExitAnimationCancellationSignals.get(fragment) == null) {
                                fragmentStateManagerC.p();
                            }
                        }
                        if (iMin < 1) {
                        }
                        if (iMin < 0) {
                        }
                        break;
                    case 4:
                        if (iMin < 4) {
                            if (a(3)) {
                                Log.d("FragmentManager", "movefrom ACTIVITY_CREATED: " + fragment);
                            }
                            if (fragment.G != null && this.mHost.onShouldSaveFragmentState(fragment) && fragment.d == null) {
                                fragmentStateManagerC.o();
                            }
                        }
                        if (iMin < 2) {
                        }
                        if (iMin < 1) {
                        }
                        if (iMin < 0) {
                        }
                        break;
                    case 5:
                        if (iMin < 5) {
                            fragmentStateManagerC.l();
                        }
                        if (iMin < 4) {
                        }
                        if (iMin < 2) {
                        }
                        if (iMin < 1) {
                        }
                        if (iMin < 0) {
                        }
                        break;
                    case 7:
                        if (iMin < 7) {
                            fragmentStateManagerC.k();
                        }
                        if (iMin < 5) {
                        }
                        if (iMin < 4) {
                        }
                        if (iMin < 2) {
                        }
                        if (iMin < 1) {
                        }
                        if (iMin < 0) {
                        }
                        break;
                }
            }
        } else {
            if (fragment.b < iMin && !this.mExitAnimationCancellationSignals.isEmpty()) {
                cancelExitAnimation(fragment);
            }
            switch (fragment.b) {
                case -1:
                    if (iMin > -1) {
                        fragmentStateManagerC.e();
                    }
                    if (iMin > 0) {
                        fragmentStateManagerC.f();
                    }
                    if (iMin > -1) {
                        fragmentStateManagerC.d();
                    }
                    if (iMin > 1) {
                        fragmentStateManagerC.g();
                    }
                    if (iMin > 2) {
                        fragmentStateManagerC.h();
                    }
                    if (iMin > 4) {
                        fragmentStateManagerC.i();
                    }
                    if (iMin > 5) {
                        fragmentStateManagerC.j();
                    }
                    break;
                case 0:
                    if (iMin > 0) {
                    }
                    if (iMin > -1) {
                    }
                    if (iMin > 1) {
                    }
                    if (iMin > 2) {
                    }
                    if (iMin > 4) {
                    }
                    if (iMin > 5) {
                    }
                    break;
                case 1:
                    if (iMin > -1) {
                    }
                    if (iMin > 1) {
                    }
                    if (iMin > 2) {
                    }
                    if (iMin > 4) {
                    }
                    if (iMin > 5) {
                    }
                    break;
                case 2:
                    if (iMin > 2) {
                    }
                    if (iMin > 4) {
                    }
                    if (iMin > 5) {
                    }
                    break;
                case 4:
                    if (iMin > 4) {
                    }
                    if (iMin > 5) {
                    }
                    break;
                case 5:
                    if (iMin > 5) {
                    }
                    break;
            }
        }
        if (fragment.b != iMin) {
            if (a(3)) {
                Log.d("FragmentManager", "moveToState: Fragment state for " + fragment + " not updated inline; expected state " + iMin + " found " + fragment.b);
            }
            fragment.b = iMin;
        }
    }

    void a(@NonNull Fragment fragment, @SuppressLint({"UnknownNullness"}) Intent intent, int i, @Nullable Bundle bundle) {
        if (this.mStartActivityForResult == null) {
            this.mHost.onStartActivityFromFragment(fragment, intent, i, bundle);
            return;
        }
        this.e.addLast(new LaunchedFragmentInfo(fragment.g, i));
        if (intent != null && bundle != null) {
            intent.putExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE, bundle);
        }
        this.mStartActivityForResult.launch(intent);
    }

    void a(@NonNull Fragment fragment, @SuppressLint({"UnknownNullness"}) IntentSender intentSender, int i, @Nullable Intent intent, int i2, int i3, int i4, @Nullable Bundle bundle) throws IntentSender.SendIntentException {
        Intent intent2;
        if (this.mStartIntentSenderForResult == null) {
            this.mHost.onStartIntentSenderFromFragment(fragment, intentSender, i, intent, i2, i3, i4, bundle);
            return;
        }
        if (bundle != null) {
            if (intent == null) {
                intent2 = new Intent();
                intent2.putExtra(EXTRA_CREATED_FILLIN_INTENT, true);
            } else {
                intent2 = intent;
            }
            if (a(2)) {
                Log.v("FragmentManager", "ActivityOptions " + bundle + " were added to fillInIntent " + intent2 + " for fragment " + fragment);
            }
            intent2.putExtra(ActivityResultContracts.StartActivityForResult.EXTRA_ACTIVITY_OPTIONS_BUNDLE, bundle);
        } else {
            intent2 = intent;
        }
        IntentSenderRequest intentSenderRequestBuild = new IntentSenderRequest.Builder(intentSender).setFillInIntent(intent2).setFlags(i3, i2).build();
        this.e.addLast(new LaunchedFragmentInfo(fragment.g, i));
        if (a(2)) {
            Log.v("FragmentManager", "Fragment " + fragment + "is launching an IntentSender for result ");
        }
        this.mStartIntentSenderForResult.launch(intentSenderRequestBuild);
    }

    void a(@NonNull Fragment fragment, @NonNull CancellationSignal cancellationSignal) {
        if (this.mExitAnimationCancellationSignals.get(fragment) == null) {
            this.mExitAnimationCancellationSignals.put(fragment, new HashSet<>());
        }
        this.mExitAnimationCancellationSignals.get(fragment).add(cancellationSignal);
    }

    void a(@NonNull Fragment fragment, @NonNull Lifecycle.State state) {
        if (fragment.equals(b(fragment.g)) && (fragment.t == null || fragment.s == this)) {
            fragment.Q = state;
            return;
        }
        throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
    }

    void a(@NonNull Fragment fragment, boolean z) {
        ViewGroup fragmentContainer = getFragmentContainer(fragment);
        if (fragmentContainer == null || !(fragmentContainer instanceof FragmentContainerView)) {
            return;
        }
        ((FragmentContainerView) fragmentContainer).setDrawDisappearingViewsLast(!z);
    }

    void a(@NonNull Fragment fragment, @NonNull String[] strArr, int i) {
        if (this.mRequestPermissions == null) {
            this.mHost.onRequestPermissionsFromFragment(fragment, strArr, i);
            return;
        }
        this.e.addLast(new LaunchedFragmentInfo(fragment.g, i));
        this.mRequestPermissions.launch(strArr);
    }

    void a(@NonNull FragmentContainerView fragmentContainerView) {
        for (FragmentStateManager fragmentStateManager : this.mFragmentStore.g()) {
            Fragment fragmentA = fragmentStateManager.a();
            if (fragmentA.x == fragmentContainerView.getId() && fragmentA.G != null && fragmentA.G.getParent() == null) {
                fragmentA.F = fragmentContainerView;
                fragmentStateManager.s();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0023  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x002a  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @SuppressLint({"SyntheticAccessor"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void a(@NonNull FragmentHostCallback<?> fragmentHostCallback, @NonNull FragmentContainer fragmentContainer, @Nullable final Fragment fragment) {
        FragmentOnAttachListener fragmentOnAttachListener;
        String str;
        if (this.mHost != null) {
            throw new IllegalStateException("Already attached");
        }
        this.mHost = fragmentHostCallback;
        this.mContainer = fragmentContainer;
        this.mParent = fragment;
        if (this.mParent == null) {
            if (fragmentHostCallback instanceof FragmentOnAttachListener) {
                fragmentOnAttachListener = (FragmentOnAttachListener) fragmentHostCallback;
            }
            if (this.mParent != null) {
                updateOnBackPressedCallbackEnabled();
            }
            if (fragmentHostCallback instanceof OnBackPressedDispatcherOwner) {
                OnBackPressedDispatcherOwner onBackPressedDispatcherOwner = (OnBackPressedDispatcherOwner) fragmentHostCallback;
                this.mOnBackPressedDispatcher = onBackPressedDispatcherOwner.getOnBackPressedDispatcher();
                LifecycleOwner lifecycleOwner = onBackPressedDispatcherOwner;
                if (fragment != null) {
                    lifecycleOwner = fragment;
                }
                this.mOnBackPressedDispatcher.addCallback(lifecycleOwner, this.mOnBackPressedCallback);
            }
            this.mNonConfig = fragment == null ? fragment.s.getChildNonConfig(fragment) : fragmentHostCallback instanceof ViewModelStoreOwner ? FragmentManagerViewModel.a(((ViewModelStoreOwner) fragmentHostCallback).getViewModelStore()) : new FragmentManagerViewModel(false);
            this.mNonConfig.a(isStateSaved());
            this.mFragmentStore.a(this.mNonConfig);
            if (this.mHost instanceof ActivityResultRegistryOwner) {
                return;
            }
            ActivityResultRegistry activityResultRegistry = ((ActivityResultRegistryOwner) this.mHost).getActivityResultRegistry();
            if (fragment != null) {
                str = fragment.g + ":";
            } else {
                str = "";
            }
            String str2 = "FragmentManager:" + str;
            this.mStartActivityForResult = activityResultRegistry.register(str2 + "StartActivityForResult", new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() { // from class: androidx.fragment.app.FragmentManager.9
                @Override // androidx.activity.result.ActivityResultCallback
                public void onActivityResult(ActivityResult activityResult) {
                    LaunchedFragmentInfo launchedFragmentInfoPollFirst = FragmentManager.this.e.pollFirst();
                    if (launchedFragmentInfoPollFirst == null) {
                        Log.w("FragmentManager", "No Activities were started for result for " + this);
                        return;
                    }
                    String str3 = launchedFragmentInfoPollFirst.a;
                    int i = launchedFragmentInfoPollFirst.b;
                    Fragment fragmentD = FragmentManager.this.mFragmentStore.d(str3);
                    if (fragmentD != null) {
                        fragmentD.onActivityResult(i, activityResult.getResultCode(), activityResult.getData());
                        return;
                    }
                    Log.w("FragmentManager", "Activity result delivered for unknown Fragment " + str3);
                }
            });
            this.mStartIntentSenderForResult = activityResultRegistry.register(str2 + "StartIntentSenderForResult", new FragmentIntentSenderContract(), new ActivityResultCallback<ActivityResult>() { // from class: androidx.fragment.app.FragmentManager.10
                @Override // androidx.activity.result.ActivityResultCallback
                public void onActivityResult(ActivityResult activityResult) {
                    LaunchedFragmentInfo launchedFragmentInfoPollFirst = FragmentManager.this.e.pollFirst();
                    if (launchedFragmentInfoPollFirst == null) {
                        Log.w("FragmentManager", "No IntentSenders were started for " + this);
                        return;
                    }
                    String str3 = launchedFragmentInfoPollFirst.a;
                    int i = launchedFragmentInfoPollFirst.b;
                    Fragment fragmentD = FragmentManager.this.mFragmentStore.d(str3);
                    if (fragmentD != null) {
                        fragmentD.onActivityResult(i, activityResult.getResultCode(), activityResult.getData());
                        return;
                    }
                    Log.w("FragmentManager", "Intent Sender result delivered for unknown Fragment " + str3);
                }
            });
            this.mRequestPermissions = activityResultRegistry.register(str2 + "RequestPermissions", new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() { // from class: androidx.fragment.app.FragmentManager.11
                @Override // androidx.activity.result.ActivityResultCallback
                @SuppressLint({"SyntheticAccessor"})
                public void onActivityResult(Map<String, Boolean> map) {
                    String[] strArr = (String[]) map.keySet().toArray(new String[0]);
                    ArrayList arrayList = new ArrayList(map.values());
                    int[] iArr = new int[arrayList.size()];
                    for (int i = 0; i < arrayList.size(); i++) {
                        iArr[i] = ((Boolean) arrayList.get(i)).booleanValue() ? 0 : -1;
                    }
                    LaunchedFragmentInfo launchedFragmentInfoPollFirst = FragmentManager.this.e.pollFirst();
                    if (launchedFragmentInfoPollFirst == null) {
                        Log.w("FragmentManager", "No permissions were requested for " + this);
                        return;
                    }
                    String str3 = launchedFragmentInfoPollFirst.a;
                    int i2 = launchedFragmentInfoPollFirst.b;
                    Fragment fragmentD = FragmentManager.this.mFragmentStore.d(str3);
                    if (fragmentD != null) {
                        fragmentD.onRequestPermissionsResult(i2, strArr, iArr);
                        return;
                    }
                    Log.w("FragmentManager", "Permission request result delivered for unknown Fragment " + str3);
                }
            });
            return;
        }
        fragmentOnAttachListener = new FragmentOnAttachListener() { // from class: androidx.fragment.app.FragmentManager.8
            @Override // androidx.fragment.app.FragmentOnAttachListener
            public void onAttachFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment2) {
                fragment.onAttachFragment(fragment2);
            }
        };
        addFragmentOnAttachListener(fragmentOnAttachListener);
        if (this.mParent != null) {
        }
        if (fragmentHostCallback instanceof OnBackPressedDispatcherOwner) {
        }
        this.mNonConfig = fragment == null ? fragment.s.getChildNonConfig(fragment) : fragmentHostCallback instanceof ViewModelStoreOwner ? FragmentManagerViewModel.a(((ViewModelStoreOwner) fragmentHostCallback).getViewModelStore()) : new FragmentManagerViewModel(false);
        this.mNonConfig.a(isStateSaved());
        this.mFragmentStore.a(this.mNonConfig);
        if (this.mHost instanceof ActivityResultRegistryOwner) {
        }
    }

    void a(@NonNull OpGenerator opGenerator, boolean z) {
        if (!z) {
            if (this.mHost == null) {
                if (!this.mDestroyed) {
                    throw new IllegalStateException("FragmentManager has not been attached to a host.");
                }
                throw new IllegalStateException("FragmentManager has been destroyed");
            }
            checkStateLoss();
        }
        synchronized (this.mPendingActions) {
            if (this.mHost == null) {
                if (!z) {
                    throw new IllegalStateException("Activity has been destroyed");
                }
            } else {
                this.mPendingActions.add(opGenerator);
                d();
            }
        }
    }

    void a(@NonNull FragmentStateManager fragmentStateManager) {
        Fragment fragmentA = fragmentStateManager.a();
        if (fragmentA.H) {
            if (this.mExecutingActions) {
                this.mHavePendingDeferredStart = true;
                return;
            }
            fragmentA.H = false;
            if (a) {
                fragmentStateManager.c();
            } else {
                f(fragmentA);
            }
        }
    }

    boolean a(@NonNull Menu menu) {
        boolean z = false;
        if (this.c < 1) {
            return false;
        }
        for (Fragment fragment : this.mFragmentStore.h()) {
            if (fragment != null && b(fragment) && fragment.a(menu)) {
                z = true;
            }
        }
        return z;
    }

    boolean a(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        if (this.c < 1) {
            return false;
        }
        ArrayList<Fragment> arrayList = null;
        boolean z = false;
        for (Fragment fragment : this.mFragmentStore.h()) {
            if (fragment != null && b(fragment) && fragment.a(menu, menuInflater)) {
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                }
                arrayList.add(fragment);
                z = true;
            }
        }
        if (this.mCreatedMenus != null) {
            for (int i = 0; i < this.mCreatedMenus.size(); i++) {
                Fragment fragment2 = this.mCreatedMenus.get(i);
                if (arrayList == null || !arrayList.contains(fragment2)) {
                    fragment2.onDestroyOptionsMenu();
                }
            }
        }
        this.mCreatedMenus = arrayList;
        return z;
    }

    boolean a(@NonNull MenuItem menuItem) {
        if (this.c < 1) {
            return false;
        }
        for (Fragment fragment : this.mFragmentStore.h()) {
            if (fragment != null && fragment.a(menuItem)) {
                return true;
            }
        }
        return false;
    }

    boolean a(@Nullable Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        FragmentManager fragmentManager = fragment.s;
        return fragment.equals(fragmentManager.getPrimaryNavigationFragment()) && a(fragmentManager.mParent);
    }

    boolean a(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2, @Nullable String str, int i, int i2) {
        int size;
        if (this.b == null) {
            return false;
        }
        if (str == null && i < 0 && (i2 & 1) == 0) {
            int size2 = this.b.size() - 1;
            if (size2 < 0) {
                return false;
            }
            arrayList.add(this.b.remove(size2));
            arrayList2.add(true);
        } else {
            if (str != null || i >= 0) {
                size = this.b.size() - 1;
                while (size >= 0) {
                    BackStackRecord backStackRecord = this.b.get(size);
                    if ((str != null && str.equals(backStackRecord.getName())) || (i >= 0 && i == backStackRecord.c)) {
                        break;
                    }
                    size--;
                }
                if (size < 0) {
                    return false;
                }
                if ((i2 & 1) != 0) {
                    while (true) {
                        size--;
                        if (size < 0) {
                            break;
                        }
                        BackStackRecord backStackRecord2 = this.b.get(size);
                        if (str == null || !str.equals(backStackRecord2.getName())) {
                            if (i < 0 || i != backStackRecord2.c) {
                                break;
                            }
                        }
                    }
                }
            } else {
                size = -1;
            }
            if (size == this.b.size() - 1) {
                return false;
            }
            for (int size3 = this.b.size() - 1; size3 > size; size3--) {
                arrayList.add(this.b.remove(size3));
                arrayList2.add(true);
            }
        }
        return true;
    }

    boolean a(boolean z) {
        ensureExecReady(z);
        boolean z2 = false;
        while (generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
                cleanupExec();
                z2 = true;
            } catch (Throwable th) {
                cleanupExec();
                throw th;
            }
        }
        updateOnBackPressedCallbackEnabled();
        doPendingDeferredStart();
        this.mFragmentStore.d();
        return z2;
    }

    public void addFragmentOnAttachListener(@NonNull FragmentOnAttachListener fragmentOnAttachListener) {
        this.mOnAttachListeners.add(fragmentOnAttachListener);
    }

    public void addOnBackStackChangedListener(@NonNull OnBackStackChangedListener onBackStackChangedListener) {
        if (this.mBackStackChangeListeners == null) {
            this.mBackStackChangeListeners = new ArrayList<>();
        }
        this.mBackStackChangeListeners.add(onBackStackChangedListener);
    }

    @Nullable
    Fragment b(@NonNull String str) {
        return this.mFragmentStore.e(str);
    }

    @NonNull
    List<Fragment> b() {
        return this.mFragmentStore.i();
    }

    void b(@NonNull Menu menu) {
        if (this.c < 1) {
            return;
        }
        for (Fragment fragment : this.mFragmentStore.h()) {
            if (fragment != null) {
                fragment.b(menu);
            }
        }
    }

    void b(@NonNull Fragment fragment, @NonNull CancellationSignal cancellationSignal) {
        HashSet<CancellationSignal> hashSet = this.mExitAnimationCancellationSignals.get(fragment);
        if (hashSet != null && hashSet.remove(cancellationSignal) && hashSet.isEmpty()) {
            this.mExitAnimationCancellationSignals.remove(fragment);
            if (fragment.b < 5) {
                destroyFragmentView(fragment);
                f(fragment);
            }
        }
    }

    void b(@NonNull OpGenerator opGenerator, boolean z) {
        if (z && (this.mHost == null || this.mDestroyed)) {
            return;
        }
        ensureExecReady(z);
        if (opGenerator.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
            this.mExecutingActions = true;
            try {
                removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
            } finally {
                cleanupExec();
            }
        }
        updateOnBackPressedCallbackEnabled();
        doPendingDeferredStart();
        this.mFragmentStore.d();
    }

    void b(boolean z) {
        for (Fragment fragment : this.mFragmentStore.h()) {
            if (fragment != null) {
                fragment.b(z);
            }
        }
    }

    boolean b(int i) {
        return this.c >= i;
    }

    boolean b(@NonNull MenuItem menuItem) {
        if (this.c < 1) {
            return false;
        }
        for (Fragment fragment : this.mFragmentStore.h()) {
            if (fragment != null && fragment.b(menuItem)) {
                return true;
            }
        }
        return false;
    }

    boolean b(@Nullable Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        return fragment.isMenuVisible();
    }

    @NonNull
    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    int c() {
        return this.mFragmentStore.j();
    }

    @NonNull
    ViewModelStore c(@NonNull Fragment fragment) {
        return this.mNonConfig.e(fragment);
    }

    void c(boolean z) {
        for (Fragment fragment : this.mFragmentStore.h()) {
            if (fragment != null) {
                fragment.c(z);
            }
        }
    }

    @Override // androidx.fragment.app.FragmentResultOwner
    public final void clearFragmentResult(@NonNull String str) {
        this.mResults.remove(str);
    }

    @Override // androidx.fragment.app.FragmentResultOwner
    public final void clearFragmentResultListener(@NonNull String str) {
        LifecycleAwareResultListener lifecycleAwareResultListenerRemove = this.mResultListeners.remove(str);
        if (lifecycleAwareResultListenerRemove != null) {
            lifecycleAwareResultListenerRemove.removeObserver();
        }
    }

    void d() {
        synchronized (this.mPendingActions) {
            boolean z = (this.mPostponedTransactions == null || this.mPostponedTransactions.isEmpty()) ? false : true;
            boolean z2 = this.mPendingActions.size() == 1;
            if (z || z2) {
                this.mHost.c().removeCallbacks(this.mExecCommit);
                this.mHost.c().post(this.mExecCommit);
                updateOnBackPressedCallbackEnabled();
            }
        }
    }

    void d(@NonNull Fragment fragment) {
        this.mNonConfig.a(fragment);
    }

    public void dump(@NonNull String str, @Nullable FileDescriptor fileDescriptor, @NonNull PrintWriter printWriter, @Nullable String[] strArr) {
        int size;
        int size2;
        String str2 = str + "    ";
        this.mFragmentStore.a(str, fileDescriptor, printWriter, strArr);
        if (this.mCreatedMenus != null && (size2 = this.mCreatedMenus.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Fragments Created Menus:");
            for (int i = 0; i < size2; i++) {
                Fragment fragment = this.mCreatedMenus.get(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(fragment.toString());
            }
        }
        if (this.b != null && (size = this.b.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Back Stack:");
            for (int i2 = 0; i2 < size; i2++) {
                BackStackRecord backStackRecord = this.b.get(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(backStackRecord.toString());
                backStackRecord.dump(str2, printWriter);
            }
        }
        printWriter.print(str);
        printWriter.println("Back Stack Index: " + this.mBackStackIndex.get());
        synchronized (this.mPendingActions) {
            int size3 = this.mPendingActions.size();
            if (size3 > 0) {
                printWriter.print(str);
                printWriter.println("Pending Actions:");
                for (int i3 = 0; i3 < size3; i3++) {
                    OpGenerator opGenerator = this.mPendingActions.get(i3);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i3);
                    printWriter.print(": ");
                    printWriter.println(opGenerator);
                }
            }
        }
        printWriter.print(str);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(str);
        printWriter.print("  mHost=");
        printWriter.println(this.mHost);
        printWriter.print(str);
        printWriter.print("  mContainer=");
        printWriter.println(this.mContainer);
        if (this.mParent != null) {
            printWriter.print(str);
            printWriter.print("  mParent=");
            printWriter.println(this.mParent);
        }
        printWriter.print(str);
        printWriter.print("  mCurState=");
        printWriter.print(this.c);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.mStateSaved);
        printWriter.print(" mStopped=");
        printWriter.print(this.mStopped);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            printWriter.print(str);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.mNeedMenuInvalidate);
        }
    }

    int e() {
        return this.mBackStackIndex.getAndIncrement();
    }

    void e(@NonNull Fragment fragment) {
        this.mNonConfig.c(fragment);
    }

    public boolean executePendingTransactions() {
        boolean zA = a(true);
        forcePostponedTransactions();
        return zA;
    }

    @Deprecated
    FragmentManagerNonConfig f() {
        if (this.mHost instanceof ViewModelStoreOwner) {
            throwException(new IllegalStateException("You cannot use retainNonConfig when your FragmentHostCallback implements ViewModelStoreOwner."));
        }
        return this.mNonConfig.d();
    }

    void f(@NonNull Fragment fragment) {
        a(fragment, this.c);
    }

    @Nullable
    public Fragment findFragmentById(@IdRes int i) {
        return this.mFragmentStore.b(i);
    }

    @Nullable
    public Fragment findFragmentByTag(@Nullable String str) {
        return this.mFragmentStore.a(str);
    }

    Parcelable g() {
        int size;
        forcePostponedTransactions();
        endAnimatingAwayFragments();
        a(true);
        this.mStateSaved = true;
        this.mNonConfig.a(true);
        ArrayList<FragmentState> arrayListE = this.mFragmentStore.e();
        BackStackState[] backStackStateArr = null;
        if (arrayListE.isEmpty()) {
            if (a(2)) {
                Log.v("FragmentManager", "saveAllState: no fragments!");
            }
            return null;
        }
        ArrayList<String> arrayListF = this.mFragmentStore.f();
        if (this.b != null && (size = this.b.size()) > 0) {
            backStackStateArr = new BackStackState[size];
            for (int i = 0; i < size; i++) {
                backStackStateArr[i] = new BackStackState(this.b.get(i));
                if (a(2)) {
                    Log.v("FragmentManager", "saveAllState: adding back stack #" + i + ": " + this.b.get(i));
                }
            }
        }
        FragmentManagerState fragmentManagerState = new FragmentManagerState();
        fragmentManagerState.a = arrayListE;
        fragmentManagerState.b = arrayListF;
        fragmentManagerState.c = backStackStateArr;
        fragmentManagerState.d = this.mBackStackIndex.get();
        if (this.d != null) {
            fragmentManagerState.e = this.d.g;
        }
        fragmentManagerState.f.addAll(this.mResults.keySet());
        fragmentManagerState.g.addAll(this.mResults.values());
        fragmentManagerState.h = new ArrayList<>(this.e);
        return fragmentManagerState;
    }

    void g(@NonNull Fragment fragment) {
        if (!this.mFragmentStore.b(fragment.g)) {
            if (a(3)) {
                Log.d("FragmentManager", "Ignoring moving " + fragment + " to state " + this.c + "since it is not added to " + this);
                return;
            }
            return;
        }
        f(fragment);
        if (fragment.G != null && fragment.L && fragment.F != null) {
            if (fragment.N > 0.0f) {
                fragment.G.setAlpha(fragment.N);
            }
            fragment.N = 0.0f;
            fragment.L = false;
            FragmentAnim.AnimationOrAnimator animationOrAnimatorA = FragmentAnim.a(this.mHost.b(), fragment, true, fragment.v());
            if (animationOrAnimatorA != null) {
                if (animationOrAnimatorA.animation != null) {
                    fragment.G.startAnimation(animationOrAnimatorA.animation);
                } else {
                    animationOrAnimatorA.animator.setTarget(fragment.G);
                    animationOrAnimatorA.animator.start();
                }
            }
        }
        if (fragment.M) {
            completeShowHideFragment(fragment);
        }
    }

    @NonNull
    public BackStackEntry getBackStackEntryAt(int i) {
        return this.b.get(i);
    }

    public int getBackStackEntryCount() {
        if (this.b != null) {
            return this.b.size();
        }
        return 0;
    }

    @Nullable
    public Fragment getFragment(@NonNull Bundle bundle, @NonNull String str) {
        String string = bundle.getString(str);
        if (string == null) {
            return null;
        }
        Fragment fragmentB = b(string);
        if (fragmentB == null) {
            throwException(new IllegalStateException("Fragment no longer exists for key " + str + ": unique id " + string));
        }
        return fragmentB;
    }

    @NonNull
    public FragmentFactory getFragmentFactory() {
        return this.mFragmentFactory != null ? this.mFragmentFactory : this.mParent != null ? this.mParent.s.getFragmentFactory() : this.mHostFragmentFactory;
    }

    @NonNull
    public List<Fragment> getFragments() {
        return this.mFragmentStore.h();
    }

    @Nullable
    public Fragment getPrimaryNavigationFragment() {
        return this.d;
    }

    @NonNull
    FragmentHostCallback<?> h() {
        return this.mHost;
    }

    @NonNull
    FragmentStateManager h(@NonNull Fragment fragment) {
        FragmentStateManager fragmentStateManagerC = this.mFragmentStore.c(fragment.g);
        if (fragmentStateManagerC != null) {
            return fragmentStateManagerC;
        }
        FragmentStateManager fragmentStateManager = new FragmentStateManager(this.mLifecycleCallbacksDispatcher, this.mFragmentStore, fragment);
        fragmentStateManager.a(this.mHost.b().getClassLoader());
        fragmentStateManager.a(this.c);
        return fragmentStateManager;
    }

    @Nullable
    Fragment i() {
        return this.mParent;
    }

    FragmentStateManager i(@NonNull Fragment fragment) {
        if (a(2)) {
            Log.v("FragmentManager", "add: " + fragment);
        }
        FragmentStateManager fragmentStateManagerH = h(fragment);
        fragment.s = this;
        this.mFragmentStore.a(fragmentStateManagerH);
        if (!fragment.A) {
            this.mFragmentStore.a(fragment);
            fragment.m = false;
            if (fragment.G == null) {
                fragment.M = false;
            }
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
        }
        return fragmentStateManagerH;
    }

    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    public boolean isStateSaved() {
        return this.mStateSaved || this.mStopped;
    }

    @NonNull
    FragmentContainer j() {
        return this.mContainer;
    }

    void j(@NonNull Fragment fragment) {
        if (a(2)) {
            Log.v("FragmentManager", "remove: " + fragment + " nesting=" + fragment.r);
        }
        boolean z = !fragment.c();
        if (!fragment.A || z) {
            this.mFragmentStore.b(fragment);
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.m = true;
            setVisibleRemovingFragment(fragment);
        }
    }

    @NonNull
    FragmentStore k() {
        return this.mFragmentStore;
    }

    void k(@NonNull Fragment fragment) {
        if (a(2)) {
            Log.v("FragmentManager", "hide: " + fragment);
        }
        if (fragment.z) {
            return;
        }
        fragment.z = true;
        fragment.M = true ^ fragment.M;
        setVisibleRemovingFragment(fragment);
    }

    void l() {
        if (this.mHost == null) {
            return;
        }
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.a(false);
        for (Fragment fragment : this.mFragmentStore.h()) {
            if (fragment != null) {
                fragment.j();
            }
        }
    }

    void l(@NonNull Fragment fragment) {
        if (a(2)) {
            Log.v("FragmentManager", "show: " + fragment);
        }
        if (fragment.z) {
            fragment.z = false;
            fragment.M = !fragment.M;
        }
    }

    void m() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.a(false);
        dispatchStateChange(0);
    }

    void m(@NonNull Fragment fragment) {
        if (a(2)) {
            Log.v("FragmentManager", "detach: " + fragment);
        }
        if (fragment.A) {
            return;
        }
        fragment.A = true;
        if (fragment.l) {
            if (a(2)) {
                Log.v("FragmentManager", "remove from detach: " + fragment);
            }
            this.mFragmentStore.b(fragment);
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
            setVisibleRemovingFragment(fragment);
        }
    }

    void n() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.a(false);
        dispatchStateChange(1);
    }

    void n(@NonNull Fragment fragment) {
        if (a(2)) {
            Log.v("FragmentManager", "attach: " + fragment);
        }
        if (fragment.A) {
            fragment.A = false;
            if (fragment.l) {
                return;
            }
            this.mFragmentStore.a(fragment);
            if (a(2)) {
                Log.v("FragmentManager", "add from attach: " + fragment);
            }
            if (isMenuAvailable(fragment)) {
                this.mNeedMenuInvalidate = true;
            }
        }
    }

    void o() {
        dispatchStateChange(2);
    }

    void o(@Nullable Fragment fragment) {
        if (fragment == null || (fragment.equals(b(fragment.g)) && (fragment.t == null || fragment.s == this))) {
            Fragment fragment2 = this.d;
            this.d = fragment;
            dispatchParentPrimaryNavigationFragmentChanged(fragment2);
            dispatchParentPrimaryNavigationFragmentChanged(this.d);
            return;
        }
        throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Deprecated
    public FragmentTransaction openTransaction() {
        return beginTransaction();
    }

    void p() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.a(false);
        dispatchStateChange(4);
    }

    void p(@NonNull Fragment fragment) {
        Iterator<FragmentOnAttachListener> it = this.mOnAttachListeners.iterator();
        while (it.hasNext()) {
            it.next().onAttachFragment(this, fragment);
        }
    }

    public void popBackStack() {
        a((OpGenerator) new PopBackStackState(null, -1, 0), false);
    }

    public void popBackStack(int i, int i2) {
        if (i >= 0) {
            a((OpGenerator) new PopBackStackState(null, i, i2), false);
            return;
        }
        throw new IllegalArgumentException("Bad id: " + i);
    }

    public void popBackStack(@Nullable String str, int i) {
        a((OpGenerator) new PopBackStackState(str, -1, i), false);
    }

    public boolean popBackStackImmediate() {
        return popBackStackImmediate(null, -1, 0);
    }

    public boolean popBackStackImmediate(int i, int i2) {
        if (i >= 0) {
            return popBackStackImmediate(null, i, i2);
        }
        throw new IllegalArgumentException("Bad id: " + i);
    }

    public boolean popBackStackImmediate(@Nullable String str, int i) {
        return popBackStackImmediate(str, -1, i);
    }

    public void putFragment(@NonNull Bundle bundle, @NonNull String str, @NonNull Fragment fragment) {
        if (fragment.s != this) {
            throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        bundle.putString(str, fragment.g);
    }

    void q() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.a(false);
        dispatchStateChange(5);
    }

    void q(@NonNull Fragment fragment) {
        if (fragment.l && isMenuAvailable(fragment)) {
            this.mNeedMenuInvalidate = true;
        }
    }

    void r() {
        this.mStateSaved = false;
        this.mStopped = false;
        this.mNonConfig.a(false);
        dispatchStateChange(7);
    }

    public void registerFragmentLifecycleCallbacks(@NonNull FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z) {
        this.mLifecycleCallbacksDispatcher.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, z);
    }

    public void removeFragmentOnAttachListener(@NonNull FragmentOnAttachListener fragmentOnAttachListener) {
        this.mOnAttachListeners.remove(fragmentOnAttachListener);
    }

    public void removeOnBackStackChangedListener(@NonNull OnBackStackChangedListener onBackStackChangedListener) {
        if (this.mBackStackChangeListeners != null) {
            this.mBackStackChangeListeners.remove(onBackStackChangedListener);
        }
    }

    void s() {
        dispatchStateChange(5);
    }

    @Nullable
    public Fragment.SavedState saveFragmentInstanceState(@NonNull Fragment fragment) {
        FragmentStateManager fragmentStateManagerC = this.mFragmentStore.c(fragment.g);
        if (fragmentStateManagerC == null || !fragmentStateManagerC.a().equals(fragment)) {
            throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        return fragmentStateManagerC.n();
    }

    public void setFragmentFactory(@NonNull FragmentFactory fragmentFactory) {
        this.mFragmentFactory = fragmentFactory;
    }

    @Override // androidx.fragment.app.FragmentResultOwner
    public final void setFragmentResult(@NonNull String str, @NonNull Bundle bundle) {
        LifecycleAwareResultListener lifecycleAwareResultListener = this.mResultListeners.get(str);
        if (lifecycleAwareResultListener == null || !lifecycleAwareResultListener.isAtLeast(Lifecycle.State.STARTED)) {
            this.mResults.put(str, bundle);
        } else {
            lifecycleAwareResultListener.onFragmentResult(str, bundle);
        }
    }

    @Override // androidx.fragment.app.FragmentResultOwner
    @SuppressLint({"SyntheticAccessor"})
    public final void setFragmentResultListener(@NonNull final String str, @NonNull LifecycleOwner lifecycleOwner, @NonNull final FragmentResultListener fragmentResultListener) {
        final Lifecycle lifecycle = lifecycleOwner.getLifecycle();
        if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
            return;
        }
        LifecycleEventObserver lifecycleEventObserver = new LifecycleEventObserver() { // from class: androidx.fragment.app.FragmentManager.6
            @Override // androidx.lifecycle.LifecycleEventObserver
            public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner2, @NonNull Lifecycle.Event event) {
                Bundle bundle;
                if (event == Lifecycle.Event.ON_START && (bundle = (Bundle) FragmentManager.this.mResults.get(str)) != null) {
                    fragmentResultListener.onFragmentResult(str, bundle);
                    FragmentManager.this.clearFragmentResult(str);
                }
                if (event == Lifecycle.Event.ON_DESTROY) {
                    lifecycle.removeObserver(this);
                    FragmentManager.this.mResultListeners.remove(str);
                }
            }
        };
        lifecycle.addObserver(lifecycleEventObserver);
        LifecycleAwareResultListener lifecycleAwareResultListenerPut = this.mResultListeners.put(str, new LifecycleAwareResultListener(lifecycle, fragmentResultListener, lifecycleEventObserver));
        if (lifecycleAwareResultListenerPut != null) {
            lifecycleAwareResultListenerPut.removeObserver();
        }
    }

    void t() {
        this.mStopped = true;
        this.mNonConfig.a(true);
        dispatchStateChange(4);
    }

    @NonNull
    public String toString() {
        String str;
        Object obj;
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        if (this.mParent != null) {
            sb.append(this.mParent.getClass().getSimpleName());
            sb.append("{");
            obj = this.mParent;
        } else {
            if (this.mHost == null) {
                str = "null";
                sb.append(str);
                sb.append("}}");
                return sb.toString();
            }
            sb.append(this.mHost.getClass().getSimpleName());
            sb.append("{");
            obj = this.mHost;
        }
        sb.append(Integer.toHexString(System.identityHashCode(obj)));
        str = h.d;
        sb.append(str);
        sb.append("}}");
        return sb.toString();
    }

    void u() {
        dispatchStateChange(1);
    }

    public void unregisterFragmentLifecycleCallbacks(@NonNull FragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
        this.mLifecycleCallbacksDispatcher.unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks);
    }

    void v() {
        this.mDestroyed = true;
        a(true);
        endAnimatingAwayFragments();
        dispatchStateChange(-1);
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
        if (this.mOnBackPressedDispatcher != null) {
            this.mOnBackPressedCallback.remove();
            this.mOnBackPressedDispatcher = null;
        }
        if (this.mStartActivityForResult != null) {
            this.mStartActivityForResult.unregister();
            this.mStartIntentSenderForResult.unregister();
            this.mRequestPermissions.unregister();
        }
    }

    void w() {
        for (Fragment fragment : this.mFragmentStore.h()) {
            if (fragment != null) {
                fragment.l();
            }
        }
    }

    void x() {
        updateOnBackPressedCallbackEnabled();
        dispatchParentPrimaryNavigationFragmentChanged(this.d);
    }

    @NonNull
    SpecialEffectsControllerFactory y() {
        return this.mSpecialEffectsControllerFactory != null ? this.mSpecialEffectsControllerFactory : this.mParent != null ? this.mParent.s.y() : this.mDefaultSpecialEffectsControllerFactory;
    }

    @NonNull
    FragmentLifecycleCallbacksDispatcher z() {
        return this.mLifecycleCallbacksDispatcher;
    }
}
