package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.core.os.CancellationSignal;
import androidx.core.util.Preconditions;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import androidx.fragment.app.FragmentAnim;
import androidx.fragment.app.SpecialEffectsController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class DefaultSpecialEffectsController extends SpecialEffectsController {

    private static class AnimationInfo extends SpecialEffectsInfo {

        @Nullable
        private FragmentAnim.AnimationOrAnimator mAnimation;
        private boolean mIsPop;
        private boolean mLoadedAnim;

        AnimationInfo(@NonNull SpecialEffectsController.Operation operation, @NonNull CancellationSignal cancellationSignal, boolean z) {
            super(operation, cancellationSignal);
            this.mLoadedAnim = false;
            this.mIsPop = z;
        }

        @Nullable
        FragmentAnim.AnimationOrAnimator a(@NonNull Context context) {
            if (this.mLoadedAnim) {
                return this.mAnimation;
            }
            this.mAnimation = FragmentAnim.a(context, a().getFragment(), a().getFinalState() == SpecialEffectsController.Operation.State.VISIBLE, this.mIsPop);
            this.mLoadedAnim = true;
            return this.mAnimation;
        }
    }

    private static class SpecialEffectsInfo {

        @NonNull
        private final SpecialEffectsController.Operation mOperation;

        @NonNull
        private final CancellationSignal mSignal;

        SpecialEffectsInfo(@NonNull SpecialEffectsController.Operation operation, @NonNull CancellationSignal cancellationSignal) {
            this.mOperation = operation;
            this.mSignal = cancellationSignal;
        }

        @NonNull
        SpecialEffectsController.Operation a() {
            return this.mOperation;
        }

        @NonNull
        CancellationSignal b() {
            return this.mSignal;
        }

        boolean c() {
            SpecialEffectsController.Operation.State stateA = SpecialEffectsController.Operation.State.a(this.mOperation.getFragment().G);
            SpecialEffectsController.Operation.State finalState = this.mOperation.getFinalState();
            return stateA == finalState || !(stateA == SpecialEffectsController.Operation.State.VISIBLE || finalState == SpecialEffectsController.Operation.State.VISIBLE);
        }

        void d() {
            this.mOperation.completeSpecialEffect(this.mSignal);
        }
    }

    private static class TransitionInfo extends SpecialEffectsInfo {
        private final boolean mOverlapAllowed;

        @Nullable
        private final Object mSharedElementTransition;

        @Nullable
        private final Object mTransition;

        TransitionInfo(@NonNull SpecialEffectsController.Operation operation, @NonNull CancellationSignal cancellationSignal, boolean z, boolean z2) {
            boolean allowReturnTransitionOverlap;
            super(operation, cancellationSignal);
            if (operation.getFinalState() == SpecialEffectsController.Operation.State.VISIBLE) {
                this.mTransition = z ? operation.getFragment().getReenterTransition() : operation.getFragment().getEnterTransition();
                allowReturnTransitionOverlap = z ? operation.getFragment().getAllowReturnTransitionOverlap() : operation.getFragment().getAllowEnterTransitionOverlap();
            } else {
                this.mTransition = z ? operation.getFragment().getReturnTransition() : operation.getFragment().getExitTransition();
                allowReturnTransitionOverlap = true;
            }
            this.mOverlapAllowed = allowReturnTransitionOverlap;
            this.mSharedElementTransition = z2 ? z ? operation.getFragment().getSharedElementReturnTransition() : operation.getFragment().getSharedElementEnterTransition() : null;
        }

        @Nullable
        private FragmentTransitionImpl getHandlingImpl(Object obj) {
            if (obj == null) {
                return null;
            }
            if (FragmentTransition.a != null && FragmentTransition.a.canHandle(obj)) {
                return FragmentTransition.a;
            }
            if (FragmentTransition.b != null && FragmentTransition.b.canHandle(obj)) {
                return FragmentTransition.b;
            }
            throw new IllegalArgumentException("Transition " + obj + " for fragment " + a().getFragment() + " is not a valid framework Transition or AndroidX Transition");
        }

        @Nullable
        Object e() {
            return this.mTransition;
        }

        boolean f() {
            return this.mOverlapAllowed;
        }

        @Nullable
        FragmentTransitionImpl g() {
            FragmentTransitionImpl handlingImpl = getHandlingImpl(this.mTransition);
            FragmentTransitionImpl handlingImpl2 = getHandlingImpl(this.mSharedElementTransition);
            if (handlingImpl == null || handlingImpl2 == null || handlingImpl == handlingImpl2) {
                return handlingImpl != null ? handlingImpl : handlingImpl2;
            }
            throw new IllegalArgumentException("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + a().getFragment() + " returned Transition " + this.mTransition + " which uses a different Transition  type than its shared element transition " + this.mSharedElementTransition);
        }

        @Nullable
        public Object getSharedElementTransition() {
            return this.mSharedElementTransition;
        }

        public boolean hasSharedElementTransition() {
            return this.mSharedElementTransition != null;
        }
    }

    DefaultSpecialEffectsController(@NonNull ViewGroup viewGroup) {
        super(viewGroup);
    }

    private void startAnimations(@NonNull List<AnimationInfo> list, @NonNull List<SpecialEffectsController.Operation> list2, boolean z, @NonNull Map<SpecialEffectsController.Operation, Boolean> map) {
        String str;
        StringBuilder sb;
        String str2;
        FragmentAnim.AnimationOrAnimator animationOrAnimatorA;
        final ViewGroup container = getContainer();
        Context context = container.getContext();
        ArrayList<AnimationInfo> arrayList = new ArrayList();
        boolean z2 = false;
        for (final AnimationInfo animationInfo : list) {
            if (animationInfo.c() || (animationOrAnimatorA = animationInfo.a(context)) == null) {
                animationInfo.d();
            } else {
                final Animator animator = animationOrAnimatorA.animator;
                if (animator == null) {
                    arrayList.add(animationInfo);
                } else {
                    final SpecialEffectsController.Operation operationA = animationInfo.a();
                    Fragment fragment = operationA.getFragment();
                    if (Boolean.TRUE.equals(map.get(operationA))) {
                        if (FragmentManager.a(2)) {
                            Log.v("FragmentManager", "Ignoring Animator set on " + fragment + " as this Fragment was involved in a Transition.");
                        }
                        animationInfo.d();
                    } else {
                        final boolean z3 = operationA.getFinalState() == SpecialEffectsController.Operation.State.GONE;
                        if (z3) {
                            list2.remove(operationA);
                        }
                        final View view = fragment.G;
                        container.startViewTransition(view);
                        animator.addListener(new AnimatorListenerAdapter() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.2
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animator2) {
                                container.endViewTransition(view);
                                if (z3) {
                                    operationA.getFinalState().b(view);
                                }
                                animationInfo.d();
                            }
                        });
                        animator.setTarget(view);
                        animator.start();
                        animationInfo.b().setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.3
                            @Override // androidx.core.os.CancellationSignal.OnCancelListener
                            public void onCancel() {
                                animator.end();
                            }
                        });
                        z2 = true;
                    }
                }
            }
        }
        for (final AnimationInfo animationInfo2 : arrayList) {
            SpecialEffectsController.Operation operationA2 = animationInfo2.a();
            Fragment fragment2 = operationA2.getFragment();
            if (z) {
                if (FragmentManager.a(2)) {
                    str = "FragmentManager";
                    sb = new StringBuilder();
                    sb.append("Ignoring Animation set on ");
                    sb.append(fragment2);
                    str2 = " as Animations cannot run alongside Transitions.";
                    sb.append(str2);
                    Log.v(str, sb.toString());
                }
                animationInfo2.d();
            } else if (z2) {
                if (FragmentManager.a(2)) {
                    str = "FragmentManager";
                    sb = new StringBuilder();
                    sb.append("Ignoring Animation set on ");
                    sb.append(fragment2);
                    str2 = " as Animations cannot run alongside Animators.";
                    sb.append(str2);
                    Log.v(str, sb.toString());
                }
                animationInfo2.d();
            } else {
                final View view2 = fragment2.G;
                Animation animation = (Animation) Preconditions.checkNotNull(((FragmentAnim.AnimationOrAnimator) Preconditions.checkNotNull(animationInfo2.a(context))).animation);
                if (operationA2.getFinalState() != SpecialEffectsController.Operation.State.REMOVED) {
                    view2.startAnimation(animation);
                    animationInfo2.d();
                } else {
                    container.startViewTransition(view2);
                    FragmentAnim.EndViewTransitionAnimation endViewTransitionAnimation = new FragmentAnim.EndViewTransitionAnimation(animation, container, view2);
                    endViewTransitionAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.4
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation2) {
                            container.post(new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.4.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    container.endViewTransition(view2);
                                    animationInfo2.d();
                                }
                            });
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation2) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation2) {
                        }
                    });
                    view2.startAnimation(endViewTransitionAnimation);
                }
                animationInfo2.b().setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.5
                    @Override // androidx.core.os.CancellationSignal.OnCancelListener
                    public void onCancel() {
                        view2.clearAnimation();
                        container.endViewTransition(view2);
                        animationInfo2.d();
                    }
                });
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v2, types: [androidx.fragment.app.DefaultSpecialEffectsController] */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1, types: [androidx.fragment.app.DefaultSpecialEffectsController] */
    /* JADX WARN: Type inference failed for: r6v25 */
    @NonNull
    private Map<SpecialEffectsController.Operation, Boolean> startTransitions(@NonNull List<TransitionInfo> list, @NonNull List<SpecialEffectsController.Operation> list2, final boolean z, @Nullable final SpecialEffectsController.Operation operation, @Nullable final SpecialEffectsController.Operation operation2) {
        View view;
        Object objMergeTransitionsTogether;
        ArrayList<View> arrayList;
        Object objMergeTransitionsTogether2;
        Object obj;
        SpecialEffectsController.Operation operation3;
        View view2;
        View view3;
        ArrayMap arrayMap;
        SpecialEffectsController.Operation operation4;
        Rect rect;
        int i;
        FragmentTransitionImpl fragmentTransitionImpl;
        SpecialEffectsController.Operation operation5;
        ArrayList<View> arrayList2;
        ?? r4;
        View view4;
        SharedElementCallback sharedElementCallbackA;
        SharedElementCallback sharedElementCallbackZ;
        ArrayList<String> arrayList3;
        View view5;
        final View view6;
        String strA;
        ArrayList<String> arrayList4;
        ?? r6 = this;
        boolean z2 = z;
        SpecialEffectsController.Operation operation6 = operation;
        SpecialEffectsController.Operation operation7 = operation2;
        HashMap map = new HashMap();
        final FragmentTransitionImpl fragmentTransitionImpl2 = null;
        for (TransitionInfo transitionInfo : list) {
            if (!transitionInfo.c()) {
                FragmentTransitionImpl fragmentTransitionImplG = transitionInfo.g();
                if (fragmentTransitionImpl2 == null) {
                    fragmentTransitionImpl2 = fragmentTransitionImplG;
                } else if (fragmentTransitionImplG != null && fragmentTransitionImpl2 != fragmentTransitionImplG) {
                    throw new IllegalArgumentException("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + transitionInfo.a().getFragment() + " returned Transition " + transitionInfo.e() + " which uses a different Transition  type than other Fragments.");
                }
            }
        }
        int i2 = 0;
        if (fragmentTransitionImpl2 == null) {
            for (TransitionInfo transitionInfo2 : list) {
                map.put(transitionInfo2.a(), false);
                transitionInfo2.d();
            }
            return map;
        }
        View view7 = new View(getContainer().getContext());
        final Rect rect2 = new Rect();
        ArrayList<View> arrayList5 = new ArrayList<>();
        ArrayList<View> arrayList6 = new ArrayList<>();
        ArrayMap arrayMap2 = new ArrayMap();
        boolean z3 = false;
        Object obj2 = null;
        View view8 = null;
        ArrayMap arrayMap3 = arrayMap2;
        for (TransitionInfo transitionInfo3 : list) {
            if (!transitionInfo3.hasSharedElementTransition() || operation6 == null || operation7 == null) {
                view3 = view8;
                arrayMap = arrayMap3;
                operation4 = operation7;
                rect = rect2;
                i = i2;
                fragmentTransitionImpl = fragmentTransitionImpl2;
                operation5 = operation6;
                arrayList2 = arrayList6;
                r4 = r6;
                view4 = view7;
            } else {
                Object objWrapTransitionInSet = fragmentTransitionImpl2.wrapTransitionInSet(fragmentTransitionImpl2.cloneTransition(transitionInfo3.getSharedElementTransition()));
                ArrayList<String> arrayListX = operation2.getFragment().x();
                ArrayList<String> arrayListX2 = operation.getFragment().x();
                ArrayList<String> arrayListY = operation.getFragment().y();
                View view9 = view8;
                int i3 = 0;
                while (i3 < arrayListY.size()) {
                    int iIndexOf = arrayListX.indexOf(arrayListY.get(i3));
                    ArrayList<String> arrayList7 = arrayListY;
                    if (iIndexOf != -1) {
                        arrayListX.set(iIndexOf, arrayListX2.get(i3));
                    }
                    i3++;
                    arrayListY = arrayList7;
                }
                ArrayList<String> arrayListY2 = operation2.getFragment().y();
                if (z2) {
                    sharedElementCallbackZ = operation.getFragment().z();
                    sharedElementCallbackA = operation2.getFragment().A();
                } else {
                    sharedElementCallbackZ = operation.getFragment().A();
                    sharedElementCallbackA = operation2.getFragment().z();
                }
                int i4 = 0;
                for (int size = arrayListX.size(); i4 < size; size = size) {
                    arrayMap3.put(arrayListX.get(i4), arrayListY2.get(i4));
                    i4++;
                }
                ArrayMap arrayMap4 = new ArrayMap();
                r6.a(arrayMap4, operation.getFragment().G);
                arrayMap4.retainAll(arrayListX);
                if (sharedElementCallbackZ != null) {
                    sharedElementCallbackZ.onMapSharedElements(arrayListX, arrayMap4);
                    int size2 = arrayListX.size() - 1;
                    while (size2 >= 0) {
                        String str = arrayListX.get(size2);
                        View view10 = (View) arrayMap4.get(str);
                        if (view10 == null) {
                            arrayMap3.remove(str);
                            arrayList4 = arrayListX;
                        } else {
                            arrayList4 = arrayListX;
                            if (!str.equals(ViewCompat.getTransitionName(view10))) {
                                arrayMap3.put(ViewCompat.getTransitionName(view10), (String) arrayMap3.remove(str));
                            }
                        }
                        size2--;
                        arrayListX = arrayList4;
                    }
                    arrayList3 = arrayListX;
                } else {
                    arrayList3 = arrayListX;
                    arrayMap3.retainAll(arrayMap4.keySet());
                }
                final ArrayMap arrayMap5 = new ArrayMap();
                r6.a(arrayMap5, operation2.getFragment().G);
                arrayMap5.retainAll(arrayListY2);
                arrayMap5.retainAll(arrayMap3.values());
                if (sharedElementCallbackA != null) {
                    sharedElementCallbackA.onMapSharedElements(arrayListY2, arrayMap5);
                    for (int size3 = arrayListY2.size() - 1; size3 >= 0; size3--) {
                        String str2 = arrayListY2.get(size3);
                        View view11 = (View) arrayMap5.get(str2);
                        if (view11 == null) {
                            String strA2 = FragmentTransition.a((ArrayMap<String, String>) arrayMap3, str2);
                            if (strA2 != null) {
                                arrayMap3.remove(strA2);
                            }
                        } else if (!str2.equals(ViewCompat.getTransitionName(view11)) && (strA = FragmentTransition.a((ArrayMap<String, String>) arrayMap3, str2)) != null) {
                            arrayMap3.put(strA, ViewCompat.getTransitionName(view11));
                        }
                    }
                } else {
                    FragmentTransition.a((ArrayMap<String, String>) arrayMap3, (ArrayMap<String, View>) arrayMap5);
                }
                r6.a(arrayMap4, arrayMap3.keySet());
                r6.a(arrayMap5, arrayMap3.values());
                if (arrayMap3.isEmpty()) {
                    arrayList5.clear();
                    arrayList6.clear();
                    arrayMap = arrayMap3;
                    arrayList2 = arrayList6;
                    r4 = r6;
                    rect = rect2;
                    view4 = view7;
                    fragmentTransitionImpl = fragmentTransitionImpl2;
                    view3 = view9;
                    obj2 = null;
                    i = 0;
                    operation4 = operation2;
                    operation5 = operation;
                } else {
                    FragmentTransition.a(operation2.getFragment(), operation.getFragment(), z2, (ArrayMap<String, View>) arrayMap4, true);
                    ArrayList<String> arrayList8 = arrayList3;
                    HashMap map2 = map;
                    view3 = view9;
                    arrayMap = arrayMap3;
                    View view12 = view7;
                    ArrayList<View> arrayList9 = arrayList6;
                    ArrayList<View> arrayList10 = arrayList5;
                    OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.6
                        @Override // java.lang.Runnable
                        public void run() {
                            FragmentTransition.a(operation2.getFragment(), operation.getFragment(), z, (ArrayMap<String, View>) arrayMap5, false);
                        }
                    });
                    arrayList10.addAll(arrayMap4.values());
                    if (arrayList8.isEmpty()) {
                        i = 0;
                    } else {
                        i = 0;
                        View view13 = (View) arrayMap4.get(arrayList8.get(0));
                        fragmentTransitionImpl2.setEpicenter(objWrapTransitionInSet, view13);
                        view3 = view13;
                    }
                    arrayList9.addAll(arrayMap5.values());
                    if (arrayListY2.isEmpty() || (view6 = (View) arrayMap5.get(arrayListY2.get(i))) == null) {
                        arrayList5 = arrayList10;
                        r4 = this;
                        view5 = view12;
                    } else {
                        arrayList5 = arrayList10;
                        DefaultSpecialEffectsController defaultSpecialEffectsController = this;
                        OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.7
                            @Override // java.lang.Runnable
                            public void run() {
                                fragmentTransitionImpl2.a(view6, rect2);
                            }
                        });
                        view5 = view12;
                        z3 = true;
                        r4 = defaultSpecialEffectsController;
                    }
                    fragmentTransitionImpl2.setSharedElementTargets(objWrapTransitionInSet, view5, arrayList5);
                    rect = rect2;
                    view4 = view5;
                    arrayList2 = arrayList9;
                    fragmentTransitionImpl = fragmentTransitionImpl2;
                    fragmentTransitionImpl2.scheduleRemoveTargets(objWrapTransitionInSet, null, null, null, null, objWrapTransitionInSet, arrayList2);
                    map = map2;
                    operation5 = operation;
                    map.put(operation5, true);
                    operation4 = operation2;
                    map.put(operation4, true);
                    obj2 = objWrapTransitionInSet;
                }
            }
            i2 = i;
            rect2 = rect;
            view7 = view4;
            fragmentTransitionImpl2 = fragmentTransitionImpl;
            arrayMap3 = arrayMap;
            z2 = z;
            r6 = r4;
            arrayList6 = arrayList2;
            operation6 = operation5;
            operation7 = operation4;
            view8 = view3;
        }
        View view14 = view8;
        ArrayMap arrayMap6 = arrayMap3;
        SpecialEffectsController.Operation operation8 = operation7;
        Rect rect3 = rect2;
        ?? r1 = i2;
        FragmentTransitionImpl fragmentTransitionImpl3 = fragmentTransitionImpl2;
        SpecialEffectsController.Operation operation9 = operation6;
        ArrayList<View> arrayList11 = arrayList6;
        ?? r42 = r6;
        View view15 = view7;
        ArrayList arrayList12 = new ArrayList();
        Object obj3 = null;
        Object obj4 = null;
        for (TransitionInfo transitionInfo4 : list) {
            if (transitionInfo4.c()) {
                map.put(transitionInfo4.a(), Boolean.valueOf((boolean) r1));
                transitionInfo4.d();
            } else {
                Object objCloneTransition = fragmentTransitionImpl3.cloneTransition(transitionInfo4.e());
                SpecialEffectsController.Operation operationA = transitionInfo4.a();
                boolean z4 = (obj2 == null || !(operationA == operation9 || operationA == operation8)) ? r1 == true ? 1 : 0 : true;
                if (objCloneTransition == null) {
                    if (!z4) {
                        map.put(operationA, Boolean.valueOf((boolean) r1));
                        transitionInfo4.d();
                    }
                    arrayList = arrayList5;
                    view = view15;
                    objMergeTransitionsTogether = obj3;
                    view2 = view14;
                } else {
                    final ArrayList<View> arrayList13 = new ArrayList<>();
                    Object obj5 = obj3;
                    r42.a(arrayList13, operationA.getFragment().G);
                    if (z4) {
                        if (operationA == operation9) {
                            arrayList13.removeAll(arrayList5);
                        } else {
                            arrayList13.removeAll(arrayList11);
                        }
                    }
                    if (arrayList13.isEmpty()) {
                        fragmentTransitionImpl3.addTarget(objCloneTransition, view15);
                        arrayList = arrayList5;
                        view = view15;
                        objMergeTransitionsTogether2 = obj4;
                        objMergeTransitionsTogether = obj5;
                        operation3 = operationA;
                        obj = objCloneTransition;
                    } else {
                        fragmentTransitionImpl3.addTargets(objCloneTransition, arrayList13);
                        view = view15;
                        objMergeTransitionsTogether = obj5;
                        arrayList = arrayList5;
                        objMergeTransitionsTogether2 = obj4;
                        fragmentTransitionImpl3.scheduleRemoveTargets(objCloneTransition, objCloneTransition, arrayList13, null, null, null, null);
                        if (operationA.getFinalState() == SpecialEffectsController.Operation.State.GONE) {
                            operation3 = operationA;
                            list2.remove(operation3);
                            ArrayList<View> arrayList14 = new ArrayList<>(arrayList13);
                            arrayList14.remove(operation3.getFragment().G);
                            obj = objCloneTransition;
                            fragmentTransitionImpl3.scheduleHideFragmentView(obj, operation3.getFragment().G, arrayList14);
                            OneShotPreDrawListener.add(getContainer(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.8
                                @Override // java.lang.Runnable
                                public void run() {
                                    FragmentTransition.a((ArrayList<View>) arrayList13, 4);
                                }
                            });
                        } else {
                            obj = objCloneTransition;
                            operation3 = operationA;
                        }
                    }
                    if (operation3.getFinalState() == SpecialEffectsController.Operation.State.VISIBLE) {
                        arrayList12.addAll(arrayList13);
                        if (z3) {
                            fragmentTransitionImpl3.setEpicenter(obj, rect3);
                        }
                        view2 = view14;
                    } else {
                        view2 = view14;
                        fragmentTransitionImpl3.setEpicenter(obj, view2);
                    }
                    map.put(operation3, true);
                    if (transitionInfo4.f()) {
                        objMergeTransitionsTogether2 = fragmentTransitionImpl3.mergeTransitionsTogether(objMergeTransitionsTogether2, obj, null);
                    } else {
                        objMergeTransitionsTogether = fragmentTransitionImpl3.mergeTransitionsTogether(objMergeTransitionsTogether, obj, null);
                    }
                    obj4 = objMergeTransitionsTogether2;
                }
                view14 = view2;
                obj3 = objMergeTransitionsTogether;
                view15 = view;
                arrayList5 = arrayList;
                r1 = 0;
            }
        }
        ArrayList<View> arrayList15 = arrayList5;
        Object objMergeTransitionsInSequence = fragmentTransitionImpl3.mergeTransitionsInSequence(obj4, obj3, obj2);
        for (final TransitionInfo transitionInfo5 : list) {
            if (!transitionInfo5.c()) {
                Object objE = transitionInfo5.e();
                SpecialEffectsController.Operation operationA2 = transitionInfo5.a();
                boolean z5 = obj2 != null && (operationA2 == operation9 || operationA2 == operation8);
                if (objE != null || z5) {
                    if (ViewCompat.isLaidOut(getContainer())) {
                        fragmentTransitionImpl3.setListenerForTransitionEnd(transitionInfo5.a().getFragment(), objMergeTransitionsInSequence, transitionInfo5.b(), new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.9
                            @Override // java.lang.Runnable
                            public void run() {
                                transitionInfo5.d();
                            }
                        });
                    } else {
                        if (FragmentManager.a(2)) {
                            Log.v("FragmentManager", "SpecialEffectsController: Container " + getContainer() + " has not been laid out. Completing operation " + operationA2);
                        }
                        transitionInfo5.d();
                    }
                }
            }
        }
        if (!ViewCompat.isLaidOut(getContainer())) {
            return map;
        }
        FragmentTransition.a((ArrayList<View>) arrayList12, 4);
        ArrayList<String> arrayListA = fragmentTransitionImpl3.a(arrayList11);
        fragmentTransitionImpl3.beginDelayedTransition(getContainer(), objMergeTransitionsInSequence);
        fragmentTransitionImpl3.a(getContainer(), arrayList15, arrayList11, arrayListA, arrayMap6);
        FragmentTransition.a((ArrayList<View>) arrayList12, 0);
        fragmentTransitionImpl3.swapSharedElementTargets(obj2, arrayList15, arrayList11);
        return map;
    }

    void a(@NonNull ArrayMap<String, View> arrayMap, @NonNull Collection<String> collection) {
        Iterator<Map.Entry<String, View>> it = arrayMap.entrySet().iterator();
        while (it.hasNext()) {
            if (!collection.contains(ViewCompat.getTransitionName(it.next().getValue()))) {
                it.remove();
            }
        }
    }

    void a(@NonNull SpecialEffectsController.Operation operation) {
        operation.getFinalState().b(operation.getFragment().G);
    }

    void a(ArrayList<View> arrayList, View view) {
        if (!(view instanceof ViewGroup)) {
            if (arrayList.contains(view)) {
                return;
            }
            arrayList.add(view);
            return;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        if (ViewGroupCompat.isTransitionGroup(viewGroup)) {
            if (arrayList.contains(view)) {
                return;
            }
            arrayList.add(viewGroup);
            return;
        }
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt.getVisibility() == 0) {
                a(arrayList, childAt);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x007b  */
    @Override // androidx.fragment.app.SpecialEffectsController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void a(@NonNull List<SpecialEffectsController.Operation> list, boolean z) {
        SpecialEffectsController.Operation operation = null;
        SpecialEffectsController.Operation operation2 = null;
        for (SpecialEffectsController.Operation operation3 : list) {
            SpecialEffectsController.Operation.State stateA = SpecialEffectsController.Operation.State.a(operation3.getFragment().G);
            switch (operation3.getFinalState()) {
                case GONE:
                case INVISIBLE:
                case REMOVED:
                    if (stateA == SpecialEffectsController.Operation.State.VISIBLE && operation == null) {
                        operation = operation3;
                    }
                    break;
                case VISIBLE:
                    if (stateA != SpecialEffectsController.Operation.State.VISIBLE) {
                        operation2 = operation3;
                    }
                    break;
            }
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        final ArrayList arrayList3 = new ArrayList(list);
        Iterator<SpecialEffectsController.Operation> it = list.iterator();
        while (true) {
            boolean z2 = true;
            if (!it.hasNext()) {
                Map<SpecialEffectsController.Operation, Boolean> mapStartTransitions = startTransitions(arrayList2, arrayList3, z, operation, operation2);
                startAnimations(arrayList, arrayList3, mapStartTransitions.containsValue(true), mapStartTransitions);
                Iterator<SpecialEffectsController.Operation> it2 = arrayList3.iterator();
                while (it2.hasNext()) {
                    a(it2.next());
                }
                arrayList3.clear();
                return;
            }
            final SpecialEffectsController.Operation next = it.next();
            CancellationSignal cancellationSignal = new CancellationSignal();
            next.markStartedSpecialEffect(cancellationSignal);
            arrayList.add(new AnimationInfo(next, cancellationSignal, z));
            CancellationSignal cancellationSignal2 = new CancellationSignal();
            next.markStartedSpecialEffect(cancellationSignal2);
            if (z) {
                if (next != operation) {
                    z2 = false;
                }
            } else if (next == operation2) {
            }
            arrayList2.add(new TransitionInfo(next, cancellationSignal2, z, z2));
            next.a(new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.1
                @Override // java.lang.Runnable
                public void run() {
                    if (arrayList3.contains(next)) {
                        arrayList3.remove(next);
                        DefaultSpecialEffectsController.this.a(next);
                    }
                }
            });
        }
    }

    void a(Map<String, View> map, @NonNull View view) {
        String transitionName = ViewCompat.getTransitionName(view);
        if (transitionName != null) {
            map.put(transitionName, view);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt.getVisibility() == 0) {
                    a(map, childAt);
                }
            }
        }
    }
}
