package androidx.fragment.app;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.core.os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class FragmentTransition {
    private static final int[] INVERSE_OPS = {0, 3, 0, 1, 5, 4, 7, 6, 9, 8, 10};
    static final FragmentTransitionImpl a;
    static final FragmentTransitionImpl b;

    interface Callback {
        void onComplete(@NonNull Fragment fragment, @NonNull CancellationSignal cancellationSignal);

        void onStart(@NonNull Fragment fragment, @NonNull CancellationSignal cancellationSignal);
    }

    static class FragmentContainerTransition {
        public Fragment firstOut;
        public boolean firstOutIsPop;
        public BackStackRecord firstOutTransaction;
        public Fragment lastIn;
        public boolean lastInIsPop;
        public BackStackRecord lastInTransaction;

        FragmentContainerTransition() {
        }
    }

    static {
        a = Build.VERSION.SDK_INT >= 21 ? new FragmentTransitionCompat21() : null;
        b = resolveSupportImpl();
    }

    private FragmentTransition() {
    }

    static View a(ArrayMap<String, View> arrayMap, FragmentContainerTransition fragmentContainerTransition, Object obj, boolean z) {
        BackStackRecord backStackRecord = fragmentContainerTransition.lastInTransaction;
        if (obj == null || arrayMap == null || backStackRecord.q == null || backStackRecord.q.isEmpty()) {
            return null;
        }
        return arrayMap.get((z ? backStackRecord.q : backStackRecord.r).get(0));
    }

    static ArrayMap<String, View> a(FragmentTransitionImpl fragmentTransitionImpl, ArrayMap<String, String> arrayMap, Object obj, FragmentContainerTransition fragmentContainerTransition) {
        SharedElementCallback sharedElementCallbackZ;
        ArrayList<String> arrayList;
        String strA;
        Fragment fragment = fragmentContainerTransition.lastIn;
        View view = fragment.getView();
        if (arrayMap.isEmpty() || obj == null || view == null) {
            arrayMap.clear();
            return null;
        }
        ArrayMap<String, View> arrayMap2 = new ArrayMap<>();
        fragmentTransitionImpl.a(arrayMap2, view);
        BackStackRecord backStackRecord = fragmentContainerTransition.lastInTransaction;
        if (fragmentContainerTransition.lastInIsPop) {
            sharedElementCallbackZ = fragment.A();
            arrayList = backStackRecord.q;
        } else {
            sharedElementCallbackZ = fragment.z();
            arrayList = backStackRecord.r;
        }
        if (arrayList != null) {
            arrayMap2.retainAll(arrayList);
            arrayMap2.retainAll(arrayMap.values());
        }
        if (sharedElementCallbackZ != null) {
            sharedElementCallbackZ.onMapSharedElements(arrayList, arrayMap2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                String str = arrayList.get(size);
                View view2 = arrayMap2.get(str);
                if (view2 == null) {
                    String strA2 = a(arrayMap, str);
                    if (strA2 != null) {
                        arrayMap.remove(strA2);
                    }
                } else if (!str.equals(ViewCompat.getTransitionName(view2)) && (strA = a(arrayMap, str)) != null) {
                    arrayMap.put(strA, ViewCompat.getTransitionName(view2));
                }
            }
        } else {
            a(arrayMap, arrayMap2);
        }
        return arrayMap2;
    }

    static String a(ArrayMap<String, String> arrayMap, String str) {
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            if (str.equals(arrayMap.valueAt(i))) {
                return arrayMap.keyAt(i);
            }
        }
        return null;
    }

    static ArrayList<View> a(FragmentTransitionImpl fragmentTransitionImpl, Object obj, Fragment fragment, ArrayList<View> arrayList, View view) {
        if (obj == null) {
            return null;
        }
        ArrayList<View> arrayList2 = new ArrayList<>();
        View view2 = fragment.getView();
        if (view2 != null) {
            fragmentTransitionImpl.a(arrayList2, view2);
        }
        if (arrayList != null) {
            arrayList2.removeAll(arrayList);
        }
        if (arrayList2.isEmpty()) {
            return arrayList2;
        }
        arrayList2.add(view);
        fragmentTransitionImpl.addTargets(obj, arrayList2);
        return arrayList2;
    }

    static void a(@NonNull Context context, @NonNull FragmentContainer fragmentContainer, ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i, int i2, boolean z, Callback callback) {
        ViewGroup viewGroup;
        SparseArray sparseArray = new SparseArray();
        for (int i3 = i; i3 < i2; i3++) {
            BackStackRecord backStackRecord = arrayList.get(i3);
            if (arrayList2.get(i3).booleanValue()) {
                calculatePopFragments(backStackRecord, sparseArray, z);
            } else {
                calculateFragments(backStackRecord, sparseArray, z);
            }
        }
        if (sparseArray.size() != 0) {
            View view = new View(context);
            int size = sparseArray.size();
            for (int i4 = 0; i4 < size; i4++) {
                int iKeyAt = sparseArray.keyAt(i4);
                ArrayMap<String, String> arrayMapCalculateNameOverrides = calculateNameOverrides(iKeyAt, arrayList, arrayList2, i, i2);
                FragmentContainerTransition fragmentContainerTransition = (FragmentContainerTransition) sparseArray.valueAt(i4);
                if (fragmentContainer.onHasView() && (viewGroup = (ViewGroup) fragmentContainer.onFindViewById(iKeyAt)) != null) {
                    if (z) {
                        configureTransitionsReordered(viewGroup, fragmentContainerTransition, view, arrayMapCalculateNameOverrides, callback);
                    } else {
                        configureTransitionsOrdered(viewGroup, fragmentContainerTransition, view, arrayMapCalculateNameOverrides, callback);
                    }
                }
            }
        }
    }

    static void a(@NonNull ArrayMap<String, String> arrayMap, @NonNull ArrayMap<String, View> arrayMap2) {
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            if (!arrayMap2.containsKey(arrayMap.valueAt(size))) {
                arrayMap.removeAt(size);
            }
        }
    }

    static void a(Fragment fragment, Fragment fragment2, boolean z, ArrayMap<String, View> arrayMap, boolean z2) {
        SharedElementCallback sharedElementCallbackZ = z ? fragment2.z() : fragment.z();
        if (sharedElementCallbackZ != null) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            int size = arrayMap == null ? 0 : arrayMap.size();
            for (int i = 0; i < size; i++) {
                arrayList2.add(arrayMap.keyAt(i));
                arrayList.add(arrayMap.valueAt(i));
            }
            if (z2) {
                sharedElementCallbackZ.onSharedElementStart(arrayList2, arrayList, null);
            } else {
                sharedElementCallbackZ.onSharedElementEnd(arrayList2, arrayList, null);
            }
        }
    }

    static void a(ArrayList<View> arrayList, int i) {
        if (arrayList == null) {
            return;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            arrayList.get(size).setVisibility(i);
        }
    }

    static boolean a() {
        return (a == null && b == null) ? false : true;
    }

    private static void addSharedElementsWithMatchingNames(ArrayList<View> arrayList, ArrayMap<String, View> arrayMap, Collection<String> collection) {
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            View viewValueAt = arrayMap.valueAt(size);
            if (collection.contains(ViewCompat.getTransitionName(viewValueAt))) {
                arrayList.add(viewValueAt);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0085  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void addToFirstInLastOut(BackStackRecord backStackRecord, FragmentTransaction.Op op, SparseArray<FragmentContainerTransition> sparseArray, boolean z, boolean z2) {
        int i;
        boolean z3;
        boolean z4;
        boolean z5;
        Fragment fragment = op.b;
        if (fragment == null || (i = fragment.x) == 0) {
            return;
        }
        int i2 = z ? INVERSE_OPS[op.a] : op.a;
        boolean z6 = false;
        boolean z7 = true;
        if (i2 != 1) {
            switch (i2) {
                case 3:
                case 6:
                    boolean z8 = !z2 ? !fragment.l || fragment.z : fragment.l || fragment.G == null || fragment.G.getVisibility() != 0 || fragment.N < 0.0f;
                    z4 = z8;
                    z5 = true;
                    z7 = false;
                    break;
                case 4:
                    if (!z2 ? !fragment.l || fragment.z : !fragment.M || !fragment.l || !fragment.z) {
                    }
                    z4 = z8;
                    z5 = true;
                    z7 = false;
                    break;
                case 5:
                    if (!z2) {
                        z3 = fragment.z;
                        z4 = false;
                        z6 = z3;
                        z5 = false;
                    } else {
                        z3 = fragment.M && !fragment.z && fragment.l;
                        z4 = false;
                        z6 = z3;
                        z5 = false;
                    }
                    break;
                case 7:
                    if (!z2) {
                        if (fragment.l || fragment.z) {
                        }
                        z4 = false;
                        z6 = z3;
                        z5 = false;
                    } else {
                        z3 = fragment.L;
                        z4 = false;
                        z6 = z3;
                        z5 = false;
                    }
                    break;
                default:
                    z5 = false;
                    z7 = false;
                    z4 = false;
                    break;
            }
        }
        FragmentContainerTransition fragmentContainerTransitionEnsureContainer = sparseArray.get(i);
        if (z6) {
            fragmentContainerTransitionEnsureContainer = ensureContainer(fragmentContainerTransitionEnsureContainer, sparseArray, i);
            fragmentContainerTransitionEnsureContainer.lastIn = fragment;
            fragmentContainerTransitionEnsureContainer.lastInIsPop = z;
            fragmentContainerTransitionEnsureContainer.lastInTransaction = backStackRecord;
        }
        if (!z2 && z7) {
            if (fragmentContainerTransitionEnsureContainer != null && fragmentContainerTransitionEnsureContainer.firstOut == fragment) {
                fragmentContainerTransitionEnsureContainer.firstOut = null;
            }
            if (!backStackRecord.s) {
                FragmentManager fragmentManager = backStackRecord.a;
                fragmentManager.k().a(fragmentManager.h(fragment));
                fragmentManager.f(fragment);
            }
        }
        if (z4 && (fragmentContainerTransitionEnsureContainer == null || fragmentContainerTransitionEnsureContainer.firstOut == null)) {
            fragmentContainerTransitionEnsureContainer = ensureContainer(fragmentContainerTransitionEnsureContainer, sparseArray, i);
            fragmentContainerTransitionEnsureContainer.firstOut = fragment;
            fragmentContainerTransitionEnsureContainer.firstOutIsPop = z;
            fragmentContainerTransitionEnsureContainer.firstOutTransaction = backStackRecord;
        }
        if (z2 || !z5 || fragmentContainerTransitionEnsureContainer == null || fragmentContainerTransitionEnsureContainer.lastIn != fragment) {
            return;
        }
        fragmentContainerTransitionEnsureContainer.lastIn = null;
    }

    public static void calculateFragments(BackStackRecord backStackRecord, SparseArray<FragmentContainerTransition> sparseArray, boolean z) {
        int size = backStackRecord.d.size();
        for (int i = 0; i < size; i++) {
            addToFirstInLastOut(backStackRecord, backStackRecord.d.get(i), sparseArray, false, z);
        }
    }

    private static ArrayMap<String, String> calculateNameOverrides(int i, ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i2, int i3) {
        ArrayList<String> arrayList3;
        ArrayList<String> arrayList4;
        ArrayMap<String, String> arrayMap = new ArrayMap<>();
        for (int i4 = i3 - 1; i4 >= i2; i4--) {
            BackStackRecord backStackRecord = arrayList.get(i4);
            if (backStackRecord.b(i)) {
                boolean zBooleanValue = arrayList2.get(i4).booleanValue();
                if (backStackRecord.q != null) {
                    int size = backStackRecord.q.size();
                    if (zBooleanValue) {
                        arrayList3 = backStackRecord.q;
                        arrayList4 = backStackRecord.r;
                    } else {
                        ArrayList<String> arrayList5 = backStackRecord.q;
                        arrayList3 = backStackRecord.r;
                        arrayList4 = arrayList5;
                    }
                    for (int i5 = 0; i5 < size; i5++) {
                        String str = arrayList4.get(i5);
                        String str2 = arrayList3.get(i5);
                        String strRemove = arrayMap.remove(str2);
                        if (strRemove != null) {
                            arrayMap.put(str, strRemove);
                        } else {
                            arrayMap.put(str, str2);
                        }
                    }
                }
            }
        }
        return arrayMap;
    }

    public static void calculatePopFragments(BackStackRecord backStackRecord, SparseArray<FragmentContainerTransition> sparseArray, boolean z) {
        if (backStackRecord.a.j().onHasView()) {
            for (int size = backStackRecord.d.size() - 1; size >= 0; size--) {
                addToFirstInLastOut(backStackRecord, backStackRecord.d.get(size), sparseArray, true, z);
            }
        }
    }

    private static boolean canHandleAll(FragmentTransitionImpl fragmentTransitionImpl, List<Object> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (!fragmentTransitionImpl.canHandle(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static ArrayMap<String, View> captureOutSharedElements(FragmentTransitionImpl fragmentTransitionImpl, ArrayMap<String, String> arrayMap, Object obj, FragmentContainerTransition fragmentContainerTransition) {
        SharedElementCallback sharedElementCallbackA;
        ArrayList<String> arrayList;
        if (arrayMap.isEmpty() || obj == null) {
            arrayMap.clear();
            return null;
        }
        Fragment fragment = fragmentContainerTransition.firstOut;
        ArrayMap<String, View> arrayMap2 = new ArrayMap<>();
        fragmentTransitionImpl.a(arrayMap2, fragment.requireView());
        BackStackRecord backStackRecord = fragmentContainerTransition.firstOutTransaction;
        if (fragmentContainerTransition.firstOutIsPop) {
            sharedElementCallbackA = fragment.z();
            arrayList = backStackRecord.r;
        } else {
            sharedElementCallbackA = fragment.A();
            arrayList = backStackRecord.q;
        }
        if (arrayList != null) {
            arrayMap2.retainAll(arrayList);
        }
        if (sharedElementCallbackA != null) {
            sharedElementCallbackA.onMapSharedElements(arrayList, arrayMap2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                String str = arrayList.get(size);
                View view = arrayMap2.get(str);
                if (view == null) {
                    arrayMap.remove(str);
                } else if (!str.equals(ViewCompat.getTransitionName(view))) {
                    arrayMap.put(ViewCompat.getTransitionName(view), arrayMap.remove(str));
                }
            }
        } else {
            arrayMap.retainAll(arrayMap2.keySet());
        }
        return arrayMap2;
    }

    private static FragmentTransitionImpl chooseImpl(Fragment fragment, Fragment fragment2) {
        ArrayList arrayList = new ArrayList();
        if (fragment != null) {
            Object exitTransition = fragment.getExitTransition();
            if (exitTransition != null) {
                arrayList.add(exitTransition);
            }
            Object returnTransition = fragment.getReturnTransition();
            if (returnTransition != null) {
                arrayList.add(returnTransition);
            }
            Object sharedElementReturnTransition = fragment.getSharedElementReturnTransition();
            if (sharedElementReturnTransition != null) {
                arrayList.add(sharedElementReturnTransition);
            }
        }
        if (fragment2 != null) {
            Object enterTransition = fragment2.getEnterTransition();
            if (enterTransition != null) {
                arrayList.add(enterTransition);
            }
            Object reenterTransition = fragment2.getReenterTransition();
            if (reenterTransition != null) {
                arrayList.add(reenterTransition);
            }
            Object sharedElementEnterTransition = fragment2.getSharedElementEnterTransition();
            if (sharedElementEnterTransition != null) {
                arrayList.add(sharedElementEnterTransition);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        if (a != null && canHandleAll(a, arrayList)) {
            return a;
        }
        if (b != null && canHandleAll(b, arrayList)) {
            return b;
        }
        if (a == null && b == null) {
            return null;
        }
        throw new IllegalArgumentException("Invalid Transition types");
    }

    private static Object configureSharedElementsOrdered(final FragmentTransitionImpl fragmentTransitionImpl, ViewGroup viewGroup, final View view, final ArrayMap<String, String> arrayMap, final FragmentContainerTransition fragmentContainerTransition, final ArrayList<View> arrayList, final ArrayList<View> arrayList2, final Object obj, Object obj2) {
        Object sharedElementTransition;
        ArrayMap<String, String> arrayMap2;
        Object obj3;
        Rect rect;
        final Fragment fragment = fragmentContainerTransition.lastIn;
        final Fragment fragment2 = fragmentContainerTransition.firstOut;
        if (fragment == null || fragment2 == null) {
            return null;
        }
        final boolean z = fragmentContainerTransition.lastInIsPop;
        if (arrayMap.isEmpty()) {
            arrayMap2 = arrayMap;
            sharedElementTransition = null;
        } else {
            sharedElementTransition = getSharedElementTransition(fragmentTransitionImpl, fragment, fragment2, z);
            arrayMap2 = arrayMap;
        }
        ArrayMap<String, View> arrayMapCaptureOutSharedElements = captureOutSharedElements(fragmentTransitionImpl, arrayMap2, sharedElementTransition, fragmentContainerTransition);
        if (arrayMap.isEmpty()) {
            obj3 = null;
        } else {
            arrayList.addAll(arrayMapCaptureOutSharedElements.values());
            obj3 = sharedElementTransition;
        }
        if (obj == null && obj2 == null && obj3 == null) {
            return null;
        }
        a(fragment, fragment2, z, arrayMapCaptureOutSharedElements, true);
        if (obj3 != null) {
            rect = new Rect();
            fragmentTransitionImpl.setSharedElementTargets(obj3, view, arrayList);
            setOutEpicenter(fragmentTransitionImpl, obj3, obj2, arrayMapCaptureOutSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
            if (obj != null) {
                fragmentTransitionImpl.setEpicenter(obj, rect);
            }
        } else {
            rect = null;
        }
        final Object obj4 = obj3;
        final Rect rect2 = rect;
        OneShotPreDrawListener.add(viewGroup, new Runnable() { // from class: androidx.fragment.app.FragmentTransition.6
            @Override // java.lang.Runnable
            public void run() {
                ArrayMap<String, View> arrayMapA = FragmentTransition.a(fragmentTransitionImpl, (ArrayMap<String, String>) arrayMap, obj4, fragmentContainerTransition);
                if (arrayMapA != null) {
                    arrayList2.addAll(arrayMapA.values());
                    arrayList2.add(view);
                }
                FragmentTransition.a(fragment, fragment2, z, arrayMapA, false);
                if (obj4 != null) {
                    fragmentTransitionImpl.swapSharedElementTargets(obj4, arrayList, arrayList2);
                    View viewA = FragmentTransition.a(arrayMapA, fragmentContainerTransition, obj, z);
                    if (viewA != null) {
                        fragmentTransitionImpl.a(viewA, rect2);
                    }
                }
            }
        });
        return obj3;
    }

    private static Object configureSharedElementsReordered(final FragmentTransitionImpl fragmentTransitionImpl, ViewGroup viewGroup, View view, ArrayMap<String, String> arrayMap, FragmentContainerTransition fragmentContainerTransition, ArrayList<View> arrayList, ArrayList<View> arrayList2, Object obj, Object obj2) {
        Object obj3;
        final View view2;
        final Rect rect;
        final Fragment fragment = fragmentContainerTransition.lastIn;
        final Fragment fragment2 = fragmentContainerTransition.firstOut;
        if (fragment != null) {
            fragment.requireView().setVisibility(0);
        }
        if (fragment == null || fragment2 == null) {
            return null;
        }
        final boolean z = fragmentContainerTransition.lastInIsPop;
        Object sharedElementTransition = arrayMap.isEmpty() ? null : getSharedElementTransition(fragmentTransitionImpl, fragment, fragment2, z);
        ArrayMap<String, View> arrayMapCaptureOutSharedElements = captureOutSharedElements(fragmentTransitionImpl, arrayMap, sharedElementTransition, fragmentContainerTransition);
        final ArrayMap<String, View> arrayMapA = a(fragmentTransitionImpl, arrayMap, sharedElementTransition, fragmentContainerTransition);
        if (arrayMap.isEmpty()) {
            if (arrayMapCaptureOutSharedElements != null) {
                arrayMapCaptureOutSharedElements.clear();
            }
            if (arrayMapA != null) {
                arrayMapA.clear();
            }
            obj3 = null;
        } else {
            addSharedElementsWithMatchingNames(arrayList, arrayMapCaptureOutSharedElements, arrayMap.keySet());
            addSharedElementsWithMatchingNames(arrayList2, arrayMapA, arrayMap.values());
            obj3 = sharedElementTransition;
        }
        if (obj == null && obj2 == null && obj3 == null) {
            return null;
        }
        a(fragment, fragment2, z, arrayMapCaptureOutSharedElements, true);
        if (obj3 != null) {
            arrayList2.add(view);
            fragmentTransitionImpl.setSharedElementTargets(obj3, view, arrayList);
            setOutEpicenter(fragmentTransitionImpl, obj3, obj2, arrayMapCaptureOutSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
            Rect rect2 = new Rect();
            View viewA = a(arrayMapA, fragmentContainerTransition, obj, z);
            if (viewA != null) {
                fragmentTransitionImpl.setEpicenter(obj, rect2);
            }
            rect = rect2;
            view2 = viewA;
        } else {
            view2 = null;
            rect = null;
        }
        OneShotPreDrawListener.add(viewGroup, new Runnable() { // from class: androidx.fragment.app.FragmentTransition.5
            @Override // java.lang.Runnable
            public void run() {
                FragmentTransition.a(fragment, fragment2, z, (ArrayMap<String, View>) arrayMapA, false);
                if (view2 != null) {
                    fragmentTransitionImpl.a(view2, rect);
                }
            }
        });
        return obj3;
    }

    private static void configureTransitionsOrdered(@NonNull ViewGroup viewGroup, FragmentContainerTransition fragmentContainerTransition, View view, ArrayMap<String, String> arrayMap, final Callback callback) {
        Object obj;
        Fragment fragment = fragmentContainerTransition.lastIn;
        final Fragment fragment2 = fragmentContainerTransition.firstOut;
        FragmentTransitionImpl fragmentTransitionImplChooseImpl = chooseImpl(fragment2, fragment);
        if (fragmentTransitionImplChooseImpl == null) {
            return;
        }
        boolean z = fragmentContainerTransition.lastInIsPop;
        boolean z2 = fragmentContainerTransition.firstOutIsPop;
        Object enterTransition = getEnterTransition(fragmentTransitionImplChooseImpl, fragment, z);
        Object exitTransition = getExitTransition(fragmentTransitionImplChooseImpl, fragment2, z2);
        ArrayList arrayList = new ArrayList();
        ArrayList<View> arrayList2 = new ArrayList<>();
        Object objConfigureSharedElementsOrdered = configureSharedElementsOrdered(fragmentTransitionImplChooseImpl, viewGroup, view, arrayMap, fragmentContainerTransition, arrayList, arrayList2, enterTransition, exitTransition);
        if (enterTransition == null && objConfigureSharedElementsOrdered == null) {
            obj = exitTransition;
            if (obj == null) {
                return;
            }
        } else {
            obj = exitTransition;
        }
        ArrayList<View> arrayListA = a(fragmentTransitionImplChooseImpl, obj, fragment2, (ArrayList<View>) arrayList, view);
        if (arrayListA == null || arrayListA.isEmpty()) {
            obj = null;
        }
        Object obj2 = obj;
        fragmentTransitionImplChooseImpl.addTarget(enterTransition, view);
        Object objMergeTransitions = mergeTransitions(fragmentTransitionImplChooseImpl, enterTransition, obj2, objConfigureSharedElementsOrdered, fragment, fragmentContainerTransition.lastInIsPop);
        if (fragment2 != null && arrayListA != null && (arrayListA.size() > 0 || arrayList.size() > 0)) {
            final CancellationSignal cancellationSignal = new CancellationSignal();
            callback.onStart(fragment2, cancellationSignal);
            fragmentTransitionImplChooseImpl.setListenerForTransitionEnd(fragment2, objMergeTransitions, cancellationSignal, new Runnable() { // from class: androidx.fragment.app.FragmentTransition.3
                @Override // java.lang.Runnable
                public void run() {
                    callback.onComplete(fragment2, cancellationSignal);
                }
            });
        }
        if (objMergeTransitions != null) {
            ArrayList<View> arrayList3 = new ArrayList<>();
            fragmentTransitionImplChooseImpl.scheduleRemoveTargets(objMergeTransitions, enterTransition, arrayList3, obj2, arrayListA, objConfigureSharedElementsOrdered, arrayList2);
            scheduleTargetChange(fragmentTransitionImplChooseImpl, viewGroup, fragment, view, arrayList2, enterTransition, arrayList3, obj2, arrayListA);
            fragmentTransitionImplChooseImpl.a((View) viewGroup, arrayList2, (Map<String, String>) arrayMap);
            fragmentTransitionImplChooseImpl.beginDelayedTransition(viewGroup, objMergeTransitions);
            fragmentTransitionImplChooseImpl.a(viewGroup, arrayList2, (Map<String, String>) arrayMap);
        }
    }

    private static void configureTransitionsReordered(@NonNull ViewGroup viewGroup, FragmentContainerTransition fragmentContainerTransition, View view, ArrayMap<String, String> arrayMap, final Callback callback) {
        Object obj;
        Fragment fragment = fragmentContainerTransition.lastIn;
        final Fragment fragment2 = fragmentContainerTransition.firstOut;
        FragmentTransitionImpl fragmentTransitionImplChooseImpl = chooseImpl(fragment2, fragment);
        if (fragmentTransitionImplChooseImpl == null) {
            return;
        }
        boolean z = fragmentContainerTransition.lastInIsPop;
        boolean z2 = fragmentContainerTransition.firstOutIsPop;
        ArrayList<View> arrayList = new ArrayList<>();
        ArrayList<View> arrayList2 = new ArrayList<>();
        Object enterTransition = getEnterTransition(fragmentTransitionImplChooseImpl, fragment, z);
        Object exitTransition = getExitTransition(fragmentTransitionImplChooseImpl, fragment2, z2);
        Object objConfigureSharedElementsReordered = configureSharedElementsReordered(fragmentTransitionImplChooseImpl, viewGroup, view, arrayMap, fragmentContainerTransition, arrayList2, arrayList, enterTransition, exitTransition);
        if (enterTransition == null && objConfigureSharedElementsReordered == null) {
            obj = exitTransition;
            if (obj == null) {
                return;
            }
        } else {
            obj = exitTransition;
        }
        ArrayList<View> arrayListA = a(fragmentTransitionImplChooseImpl, obj, fragment2, arrayList2, view);
        ArrayList<View> arrayListA2 = a(fragmentTransitionImplChooseImpl, enterTransition, fragment, arrayList, view);
        a(arrayListA2, 4);
        Object objMergeTransitions = mergeTransitions(fragmentTransitionImplChooseImpl, enterTransition, obj, objConfigureSharedElementsReordered, fragment, z);
        if (fragment2 != null && arrayListA != null && (arrayListA.size() > 0 || arrayList2.size() > 0)) {
            final CancellationSignal cancellationSignal = new CancellationSignal();
            callback.onStart(fragment2, cancellationSignal);
            fragmentTransitionImplChooseImpl.setListenerForTransitionEnd(fragment2, objMergeTransitions, cancellationSignal, new Runnable() { // from class: androidx.fragment.app.FragmentTransition.1
                @Override // java.lang.Runnable
                public void run() {
                    callback.onComplete(fragment2, cancellationSignal);
                }
            });
        }
        if (objMergeTransitions != null) {
            replaceHide(fragmentTransitionImplChooseImpl, obj, fragment2, arrayListA);
            ArrayList<String> arrayListA3 = fragmentTransitionImplChooseImpl.a(arrayList);
            fragmentTransitionImplChooseImpl.scheduleRemoveTargets(objMergeTransitions, enterTransition, arrayListA2, obj, arrayListA, objConfigureSharedElementsReordered, arrayList);
            fragmentTransitionImplChooseImpl.beginDelayedTransition(viewGroup, objMergeTransitions);
            fragmentTransitionImplChooseImpl.a(viewGroup, arrayList2, arrayList, arrayListA3, arrayMap);
            a(arrayListA2, 0);
            fragmentTransitionImplChooseImpl.swapSharedElementTargets(objConfigureSharedElementsReordered, arrayList2, arrayList);
        }
    }

    private static FragmentContainerTransition ensureContainer(FragmentContainerTransition fragmentContainerTransition, SparseArray<FragmentContainerTransition> sparseArray, int i) {
        if (fragmentContainerTransition != null) {
            return fragmentContainerTransition;
        }
        FragmentContainerTransition fragmentContainerTransition2 = new FragmentContainerTransition();
        sparseArray.put(i, fragmentContainerTransition2);
        return fragmentContainerTransition2;
    }

    private static Object getEnterTransition(FragmentTransitionImpl fragmentTransitionImpl, Fragment fragment, boolean z) {
        if (fragment == null) {
            return null;
        }
        return fragmentTransitionImpl.cloneTransition(z ? fragment.getReenterTransition() : fragment.getEnterTransition());
    }

    private static Object getExitTransition(FragmentTransitionImpl fragmentTransitionImpl, Fragment fragment, boolean z) {
        if (fragment == null) {
            return null;
        }
        return fragmentTransitionImpl.cloneTransition(z ? fragment.getReturnTransition() : fragment.getExitTransition());
    }

    private static Object getSharedElementTransition(FragmentTransitionImpl fragmentTransitionImpl, Fragment fragment, Fragment fragment2, boolean z) {
        if (fragment == null || fragment2 == null) {
            return null;
        }
        return fragmentTransitionImpl.wrapTransitionInSet(fragmentTransitionImpl.cloneTransition(z ? fragment2.getSharedElementReturnTransition() : fragment.getSharedElementEnterTransition()));
    }

    private static Object mergeTransitions(FragmentTransitionImpl fragmentTransitionImpl, Object obj, Object obj2, Object obj3, Fragment fragment, boolean z) {
        return (obj == null || obj2 == null || fragment == null) ? true : z ? fragment.getAllowReturnTransitionOverlap() : fragment.getAllowEnterTransitionOverlap() ? fragmentTransitionImpl.mergeTransitionsTogether(obj2, obj, obj3) : fragmentTransitionImpl.mergeTransitionsInSequence(obj2, obj, obj3);
    }

    private static void replaceHide(FragmentTransitionImpl fragmentTransitionImpl, Object obj, Fragment fragment, final ArrayList<View> arrayList) {
        if (fragment != null && obj != null && fragment.l && fragment.z && fragment.M) {
            fragment.e(true);
            fragmentTransitionImpl.scheduleHideFragmentView(obj, fragment.getView(), arrayList);
            OneShotPreDrawListener.add(fragment.F, new Runnable() { // from class: androidx.fragment.app.FragmentTransition.2
                @Override // java.lang.Runnable
                public void run() {
                    FragmentTransition.a((ArrayList<View>) arrayList, 4);
                }
            });
        }
    }

    private static FragmentTransitionImpl resolveSupportImpl() {
        try {
            return (FragmentTransitionImpl) Class.forName("androidx.transition.FragmentTransitionSupport").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }

    private static void scheduleTargetChange(final FragmentTransitionImpl fragmentTransitionImpl, ViewGroup viewGroup, final Fragment fragment, final View view, final ArrayList<View> arrayList, final Object obj, final ArrayList<View> arrayList2, final Object obj2, final ArrayList<View> arrayList3) {
        OneShotPreDrawListener.add(viewGroup, new Runnable() { // from class: androidx.fragment.app.FragmentTransition.4
            @Override // java.lang.Runnable
            public void run() {
                if (obj != null) {
                    fragmentTransitionImpl.removeTarget(obj, view);
                    arrayList2.addAll(FragmentTransition.a(fragmentTransitionImpl, obj, fragment, (ArrayList<View>) arrayList, view));
                }
                if (arrayList3 != null) {
                    if (obj2 != null) {
                        ArrayList<View> arrayList4 = new ArrayList<>();
                        arrayList4.add(view);
                        fragmentTransitionImpl.replaceTargets(obj2, arrayList3, arrayList4);
                    }
                    arrayList3.clear();
                    arrayList3.add(view);
                }
            }
        });
    }

    private static void setOutEpicenter(FragmentTransitionImpl fragmentTransitionImpl, Object obj, Object obj2, ArrayMap<String, View> arrayMap, boolean z, BackStackRecord backStackRecord) {
        if (backStackRecord.q == null || backStackRecord.q.isEmpty()) {
            return;
        }
        View view = arrayMap.get((z ? backStackRecord.r : backStackRecord.q).get(0));
        fragmentTransitionImpl.setEpicenter(obj, view);
        if (obj2 != null) {
            fragmentTransitionImpl.setEpicenter(obj2, view);
        }
    }
}
