package androidx.fragment.app;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.CancellationSignal;
import androidx.core.view.ViewCompat;
import androidx.fragment.R;
import com.alipay.sdk.util.h;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
abstract class SpecialEffectsController {
    final ArrayList<Operation> a = new ArrayList<>();
    final ArrayList<Operation> b = new ArrayList<>();
    boolean c = false;
    boolean d = false;
    private final ViewGroup mContainer;

    private static class FragmentStateManagerOperation extends Operation {

        @NonNull
        private final FragmentStateManager mFragmentStateManager;

        FragmentStateManagerOperation(@NonNull Operation.State state, @NonNull Operation.LifecycleImpact lifecycleImpact, @NonNull FragmentStateManager fragmentStateManager, @NonNull CancellationSignal cancellationSignal) {
            super(state, lifecycleImpact, fragmentStateManager.a(), cancellationSignal);
            this.mFragmentStateManager = fragmentStateManager;
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Operation
        void a() {
            if (b() == Operation.LifecycleImpact.ADDING) {
                Fragment fragmentA = this.mFragmentStateManager.a();
                View viewFindFocus = fragmentA.G.findFocus();
                if (viewFindFocus != null) {
                    fragmentA.b(viewFindFocus);
                    if (FragmentManager.a(2)) {
                        Log.v("FragmentManager", "requestFocus: Saved focused view " + viewFindFocus + " for Fragment " + fragmentA);
                    }
                }
                View viewRequireView = getFragment().requireView();
                if (viewRequireView.getParent() == null) {
                    this.mFragmentStateManager.s();
                    viewRequireView.setAlpha(0.0f);
                }
                if (viewRequireView.getAlpha() == 0.0f && viewRequireView.getVisibility() == 0) {
                    viewRequireView.setVisibility(4);
                }
                viewRequireView.setAlpha(fragmentA.D());
            }
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Operation
        public void complete() {
            super.complete();
            this.mFragmentStateManager.c();
        }
    }

    static class Operation {

        @NonNull
        private State mFinalState;

        @NonNull
        private final Fragment mFragment;

        @NonNull
        private LifecycleImpact mLifecycleImpact;

        @NonNull
        private final List<Runnable> mCompletionListeners = new ArrayList();

        @NonNull
        private final HashSet<CancellationSignal> mSpecialEffectsSignals = new HashSet<>();
        private boolean mIsCanceled = false;
        private boolean mIsComplete = false;

        enum LifecycleImpact {
            NONE,
            ADDING,
            REMOVING
        }

        enum State {
            REMOVED,
            VISIBLE,
            GONE,
            INVISIBLE;

            @NonNull
            static State a(int i) {
                if (i == 0) {
                    return VISIBLE;
                }
                if (i == 4) {
                    return INVISIBLE;
                }
                if (i == 8) {
                    return GONE;
                }
                throw new IllegalArgumentException("Unknown visibility " + i);
            }

            @NonNull
            static State a(@NonNull View view) {
                return (view.getAlpha() == 0.0f && view.getVisibility() == 0) ? INVISIBLE : a(view.getVisibility());
            }

            void b(@NonNull View view) {
                int i;
                switch (this) {
                    case REMOVED:
                        ViewGroup viewGroup = (ViewGroup) view.getParent();
                        if (viewGroup != null) {
                            if (FragmentManager.a(2)) {
                                Log.v("FragmentManager", "SpecialEffectsController: Removing view " + view + " from container " + viewGroup);
                            }
                            viewGroup.removeView(view);
                            return;
                        }
                        return;
                    case VISIBLE:
                        if (FragmentManager.a(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: Setting view " + view + " to VISIBLE");
                        }
                        i = 0;
                        break;
                    case GONE:
                        if (FragmentManager.a(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: Setting view " + view + " to GONE");
                        }
                        i = 8;
                        break;
                    case INVISIBLE:
                        if (FragmentManager.a(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: Setting view " + view + " to INVISIBLE");
                        }
                        i = 4;
                        break;
                    default:
                        return;
                }
                view.setVisibility(i);
            }
        }

        Operation(@NonNull State state, @NonNull LifecycleImpact lifecycleImpact, @NonNull Fragment fragment, @NonNull CancellationSignal cancellationSignal) {
            this.mFinalState = state;
            this.mLifecycleImpact = lifecycleImpact;
            this.mFragment = fragment;
            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.SpecialEffectsController.Operation.1
                @Override // androidx.core.os.CancellationSignal.OnCancelListener
                public void onCancel() {
                    Operation.this.d();
                }
            });
        }

        void a() {
        }

        final void a(@NonNull State state, @NonNull LifecycleImpact lifecycleImpact) {
            LifecycleImpact lifecycleImpact2;
            switch (lifecycleImpact) {
                case ADDING:
                    if (this.mFinalState != State.REMOVED) {
                        return;
                    }
                    if (FragmentManager.a(2)) {
                        Log.v("FragmentManager", "SpecialEffectsController: For fragment " + this.mFragment + " mFinalState = REMOVED -> VISIBLE. mLifecycleImpact = " + this.mLifecycleImpact + " to ADDING.");
                    }
                    this.mFinalState = State.VISIBLE;
                    lifecycleImpact2 = LifecycleImpact.ADDING;
                    break;
                case REMOVING:
                    if (FragmentManager.a(2)) {
                        Log.v("FragmentManager", "SpecialEffectsController: For fragment " + this.mFragment + " mFinalState = " + this.mFinalState + " -> REMOVED. mLifecycleImpact  = " + this.mLifecycleImpact + " to REMOVING.");
                    }
                    this.mFinalState = State.REMOVED;
                    lifecycleImpact2 = LifecycleImpact.REMOVING;
                    break;
                case NONE:
                    if (this.mFinalState != State.REMOVED) {
                        if (FragmentManager.a(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: For fragment " + this.mFragment + " mFinalState = " + this.mFinalState + " -> " + state + ". ");
                        }
                        this.mFinalState = state;
                        return;
                    }
                    return;
                default:
                    return;
            }
            this.mLifecycleImpact = lifecycleImpact2;
        }

        final void a(@NonNull Runnable runnable) {
            this.mCompletionListeners.add(runnable);
        }

        @NonNull
        LifecycleImpact b() {
            return this.mLifecycleImpact;
        }

        final boolean c() {
            return this.mIsCanceled;
        }

        @CallSuper
        public void complete() {
            if (this.mIsComplete) {
                return;
            }
            if (FragmentManager.a(2)) {
                Log.v("FragmentManager", "SpecialEffectsController: " + this + " has called complete.");
            }
            this.mIsComplete = true;
            Iterator<Runnable> it = this.mCompletionListeners.iterator();
            while (it.hasNext()) {
                it.next().run();
            }
        }

        public final void completeSpecialEffect(@NonNull CancellationSignal cancellationSignal) {
            if (this.mSpecialEffectsSignals.remove(cancellationSignal) && this.mSpecialEffectsSignals.isEmpty()) {
                complete();
            }
        }

        final void d() {
            if (c()) {
                return;
            }
            this.mIsCanceled = true;
            if (this.mSpecialEffectsSignals.isEmpty()) {
                complete();
                return;
            }
            Iterator it = new ArrayList(this.mSpecialEffectsSignals).iterator();
            while (it.hasNext()) {
                ((CancellationSignal) it.next()).cancel();
            }
        }

        final boolean e() {
            return this.mIsComplete;
        }

        @NonNull
        public State getFinalState() {
            return this.mFinalState;
        }

        @NonNull
        public final Fragment getFragment() {
            return this.mFragment;
        }

        public final void markStartedSpecialEffect(@NonNull CancellationSignal cancellationSignal) {
            a();
            this.mSpecialEffectsSignals.add(cancellationSignal);
        }

        @NonNull
        public String toString() {
            return "Operation {" + Integer.toHexString(System.identityHashCode(this)) + "} {mFinalState = " + this.mFinalState + "} {mLifecycleImpact = " + this.mLifecycleImpact + "} {mFragment = " + this.mFragment + h.d;
        }
    }

    SpecialEffectsController(@NonNull ViewGroup viewGroup) {
        this.mContainer = viewGroup;
    }

    @NonNull
    static SpecialEffectsController a(@NonNull ViewGroup viewGroup, @NonNull FragmentManager fragmentManager) {
        return a(viewGroup, fragmentManager.y());
    }

    @NonNull
    static SpecialEffectsController a(@NonNull ViewGroup viewGroup, @NonNull SpecialEffectsControllerFactory specialEffectsControllerFactory) {
        Object tag = viewGroup.getTag(R.id.special_effects_controller_view_tag);
        if (tag instanceof SpecialEffectsController) {
            return (SpecialEffectsController) tag;
        }
        SpecialEffectsController specialEffectsControllerCreateController = specialEffectsControllerFactory.createController(viewGroup);
        viewGroup.setTag(R.id.special_effects_controller_view_tag, specialEffectsControllerCreateController);
        return specialEffectsControllerCreateController;
    }

    private void enqueue(@NonNull Operation.State state, @NonNull Operation.LifecycleImpact lifecycleImpact, @NonNull FragmentStateManager fragmentStateManager) {
        synchronized (this.a) {
            CancellationSignal cancellationSignal = new CancellationSignal();
            Operation operationFindPendingOperation = findPendingOperation(fragmentStateManager.a());
            if (operationFindPendingOperation != null) {
                operationFindPendingOperation.a(state, lifecycleImpact);
                return;
            }
            final FragmentStateManagerOperation fragmentStateManagerOperation = new FragmentStateManagerOperation(state, lifecycleImpact, fragmentStateManager, cancellationSignal);
            this.a.add(fragmentStateManagerOperation);
            fragmentStateManagerOperation.a(new Runnable() { // from class: androidx.fragment.app.SpecialEffectsController.1
                @Override // java.lang.Runnable
                public void run() {
                    if (SpecialEffectsController.this.a.contains(fragmentStateManagerOperation)) {
                        fragmentStateManagerOperation.getFinalState().b(fragmentStateManagerOperation.getFragment().G);
                    }
                }
            });
            fragmentStateManagerOperation.a(new Runnable() { // from class: androidx.fragment.app.SpecialEffectsController.2
                @Override // java.lang.Runnable
                public void run() {
                    SpecialEffectsController.this.a.remove(fragmentStateManagerOperation);
                    SpecialEffectsController.this.b.remove(fragmentStateManagerOperation);
                }
            });
        }
    }

    @Nullable
    private Operation findPendingOperation(@NonNull Fragment fragment) {
        for (Operation operation : this.a) {
            if (operation.getFragment().equals(fragment) && !operation.c()) {
                return operation;
            }
        }
        return null;
    }

    @Nullable
    private Operation findRunningOperation(@NonNull Fragment fragment) {
        for (Operation operation : this.b) {
            if (operation.getFragment().equals(fragment) && !operation.c()) {
                return operation;
            }
        }
        return null;
    }

    private void updateFinalState() {
        for (Operation operation : this.a) {
            if (operation.b() == Operation.LifecycleImpact.ADDING) {
                operation.a(Operation.State.a(operation.getFragment().requireView().getVisibility()), Operation.LifecycleImpact.NONE);
            }
        }
    }

    @Nullable
    Operation.LifecycleImpact a(@NonNull FragmentStateManager fragmentStateManager) {
        Operation operationFindPendingOperation = findPendingOperation(fragmentStateManager.a());
        Operation.LifecycleImpact lifecycleImpactB = operationFindPendingOperation != null ? operationFindPendingOperation.b() : null;
        Operation operationFindRunningOperation = findRunningOperation(fragmentStateManager.a());
        return (operationFindRunningOperation == null || !(lifecycleImpactB == null || lifecycleImpactB == Operation.LifecycleImpact.NONE)) ? lifecycleImpactB : operationFindRunningOperation.b();
    }

    void a() {
        synchronized (this.a) {
            updateFinalState();
            this.d = false;
            int size = this.a.size() - 1;
            while (true) {
                if (size < 0) {
                    break;
                }
                Operation operation = this.a.get(size);
                Operation.State stateA = Operation.State.a(operation.getFragment().G);
                if (operation.getFinalState() == Operation.State.VISIBLE && stateA != Operation.State.VISIBLE) {
                    this.d = operation.getFragment().F();
                    break;
                }
                size--;
            }
        }
    }

    void a(@NonNull Operation.State state, @NonNull FragmentStateManager fragmentStateManager) {
        if (FragmentManager.a(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Enqueuing add operation for fragment " + fragmentStateManager.a());
        }
        enqueue(state, Operation.LifecycleImpact.ADDING, fragmentStateManager);
    }

    abstract void a(@NonNull List<Operation> list, boolean z);

    void a(boolean z) {
        this.c = z;
    }

    void b() {
        if (this.d) {
            this.d = false;
            c();
        }
    }

    void b(@NonNull FragmentStateManager fragmentStateManager) {
        if (FragmentManager.a(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Enqueuing show operation for fragment " + fragmentStateManager.a());
        }
        enqueue(Operation.State.VISIBLE, Operation.LifecycleImpact.NONE, fragmentStateManager);
    }

    void c() {
        if (this.d) {
            return;
        }
        if (!ViewCompat.isAttachedToWindow(this.mContainer)) {
            d();
            this.c = false;
            return;
        }
        synchronized (this.a) {
            if (!this.a.isEmpty()) {
                ArrayList<Operation> arrayList = new ArrayList(this.b);
                this.b.clear();
                for (Operation operation : arrayList) {
                    if (FragmentManager.a(2)) {
                        Log.v("FragmentManager", "SpecialEffectsController: Cancelling operation " + operation);
                    }
                    operation.d();
                    if (!operation.e()) {
                        this.b.add(operation);
                    }
                }
                updateFinalState();
                ArrayList arrayList2 = new ArrayList(this.a);
                this.a.clear();
                this.b.addAll(arrayList2);
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    ((Operation) it.next()).a();
                }
                a(arrayList2, this.c);
                this.c = false;
            }
        }
    }

    void c(@NonNull FragmentStateManager fragmentStateManager) {
        if (FragmentManager.a(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Enqueuing hide operation for fragment " + fragmentStateManager.a());
        }
        enqueue(Operation.State.GONE, Operation.LifecycleImpact.NONE, fragmentStateManager);
    }

    void d() {
        boolean zIsAttachedToWindow = ViewCompat.isAttachedToWindow(this.mContainer);
        synchronized (this.a) {
            updateFinalState();
            Iterator<Operation> it = this.a.iterator();
            while (it.hasNext()) {
                it.next().a();
            }
            for (Operation operation : new ArrayList(this.b)) {
                if (FragmentManager.a(2)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("SpecialEffectsController: ");
                    sb.append(zIsAttachedToWindow ? "" : "Container " + this.mContainer + " is not attached to window. ");
                    sb.append("Cancelling running operation ");
                    sb.append(operation);
                    Log.v("FragmentManager", sb.toString());
                }
                operation.d();
            }
            for (Operation operation2 : new ArrayList(this.a)) {
                if (FragmentManager.a(2)) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("SpecialEffectsController: ");
                    sb2.append(zIsAttachedToWindow ? "" : "Container " + this.mContainer + " is not attached to window. ");
                    sb2.append("Cancelling pending operation ");
                    sb2.append(operation2);
                    Log.v("FragmentManager", sb2.toString());
                }
                operation2.d();
            }
        }
    }

    void d(@NonNull FragmentStateManager fragmentStateManager) {
        if (FragmentManager.a(2)) {
            Log.v("FragmentManager", "SpecialEffectsController: Enqueuing remove operation for fragment " + fragmentStateManager.a());
        }
        enqueue(Operation.State.REMOVED, Operation.LifecycleImpact.REMOVING, fragmentStateManager);
    }

    @NonNull
    public ViewGroup getContainer() {
        return this.mContainer;
    }
}
