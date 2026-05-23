package androidx.fragment.app;

import android.util.Log;
import android.view.ViewGroup;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class FragmentStore {
    private static final String TAG = "FragmentManager";
    private FragmentManagerViewModel mNonConfig;
    private final ArrayList<Fragment> mAdded = new ArrayList<>();
    private final HashMap<String, FragmentStateManager> mActive = new HashMap<>();

    FragmentStore() {
    }

    @Nullable
    Fragment a(@Nullable String str) {
        if (str != null) {
            for (int size = this.mAdded.size() - 1; size >= 0; size--) {
                Fragment fragment = this.mAdded.get(size);
                if (fragment != null && str.equals(fragment.y)) {
                    return fragment;
                }
            }
        }
        if (str == null) {
            return null;
        }
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                Fragment fragmentA = fragmentStateManager.a();
                if (str.equals(fragmentA.y)) {
                    return fragmentA;
                }
            }
        }
        return null;
    }

    FragmentManagerViewModel a() {
        return this.mNonConfig;
    }

    void a(int i) {
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                fragmentStateManager.a(i);
            }
        }
    }

    void a(@NonNull Fragment fragment) {
        if (this.mAdded.contains(fragment)) {
            throw new IllegalStateException("Fragment already added: " + fragment);
        }
        synchronized (this.mAdded) {
            this.mAdded.add(fragment);
        }
        fragment.l = true;
    }

    void a(@NonNull FragmentManagerViewModel fragmentManagerViewModel) {
        this.mNonConfig = fragmentManagerViewModel;
    }

    void a(@NonNull FragmentStateManager fragmentStateManager) {
        Fragment fragmentA = fragmentStateManager.a();
        if (b(fragmentA.g)) {
            return;
        }
        this.mActive.put(fragmentA.g, fragmentStateManager);
        if (fragmentA.C) {
            if (fragmentA.B) {
                this.mNonConfig.a(fragmentA);
            } else {
                this.mNonConfig.c(fragmentA);
            }
            fragmentA.C = false;
        }
        if (FragmentManager.a(2)) {
            Log.v(TAG, "Added fragment to active set " + fragmentA);
        }
    }

    void a(@NonNull String str, @Nullable FileDescriptor fileDescriptor, @NonNull PrintWriter printWriter, @Nullable String[] strArr) {
        String str2 = str + "    ";
        if (!this.mActive.isEmpty()) {
            printWriter.print(str);
            printWriter.println("Active Fragments:");
            for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
                printWriter.print(str);
                if (fragmentStateManager != null) {
                    Fragment fragmentA = fragmentStateManager.a();
                    printWriter.println(fragmentA);
                    fragmentA.dump(str2, fileDescriptor, printWriter, strArr);
                } else {
                    printWriter.println("null");
                }
            }
        }
        int size = this.mAdded.size();
        if (size > 0) {
            printWriter.print(str);
            printWriter.println("Added Fragments:");
            for (int i = 0; i < size; i++) {
                Fragment fragment = this.mAdded.get(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(fragment.toString());
            }
        }
    }

    void a(@Nullable List<String> list) {
        this.mAdded.clear();
        if (list != null) {
            for (String str : list) {
                Fragment fragmentE = e(str);
                if (fragmentE == null) {
                    throw new IllegalStateException("No instantiated fragment for (" + str + ")");
                }
                if (FragmentManager.a(2)) {
                    Log.v(TAG, "restoreSaveState: added (" + str + "): " + fragmentE);
                }
                a(fragmentE);
            }
        }
    }

    @Nullable
    Fragment b(@IdRes int i) {
        for (int size = this.mAdded.size() - 1; size >= 0; size--) {
            Fragment fragment = this.mAdded.get(size);
            if (fragment != null && fragment.w == i) {
                return fragment;
            }
        }
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                Fragment fragmentA = fragmentStateManager.a();
                if (fragmentA.w == i) {
                    return fragmentA;
                }
            }
        }
        return null;
    }

    void b() {
        this.mActive.clear();
    }

    void b(@NonNull Fragment fragment) {
        synchronized (this.mAdded) {
            this.mAdded.remove(fragment);
        }
        fragment.l = false;
    }

    void b(@NonNull FragmentStateManager fragmentStateManager) {
        Fragment fragmentA = fragmentStateManager.a();
        if (fragmentA.B) {
            this.mNonConfig.c(fragmentA);
        }
        if (this.mActive.put(fragmentA.g, null) != null && FragmentManager.a(2)) {
            Log.v(TAG, "Removed fragment from active set " + fragmentA);
        }
    }

    boolean b(@NonNull String str) {
        return this.mActive.get(str) != null;
    }

    int c(@NonNull Fragment fragment) {
        ViewGroup viewGroup = fragment.F;
        if (viewGroup == null) {
            return -1;
        }
        int iIndexOf = this.mAdded.indexOf(fragment);
        for (int i = iIndexOf - 1; i >= 0; i--) {
            Fragment fragment2 = this.mAdded.get(i);
            if (fragment2.F == viewGroup && fragment2.G != null) {
                return viewGroup.indexOfChild(fragment2.G) + 1;
            }
        }
        while (true) {
            iIndexOf++;
            if (iIndexOf >= this.mAdded.size()) {
                return -1;
            }
            Fragment fragment3 = this.mAdded.get(iIndexOf);
            if (fragment3.F == viewGroup && fragment3.G != null) {
                return viewGroup.indexOfChild(fragment3.G);
            }
        }
    }

    @Nullable
    FragmentStateManager c(@NonNull String str) {
        return this.mActive.get(str);
    }

    void c() {
        Iterator<Fragment> it = this.mAdded.iterator();
        while (it.hasNext()) {
            FragmentStateManager fragmentStateManager = this.mActive.get(it.next().g);
            if (fragmentStateManager != null) {
                fragmentStateManager.c();
            }
        }
        for (FragmentStateManager fragmentStateManager2 : this.mActive.values()) {
            if (fragmentStateManager2 != null) {
                fragmentStateManager2.c();
                Fragment fragmentA = fragmentStateManager2.a();
                if (fragmentA.m && !fragmentA.c()) {
                    b(fragmentStateManager2);
                }
            }
        }
    }

    @Nullable
    Fragment d(@NonNull String str) {
        Fragment fragmentA;
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null && (fragmentA = fragmentStateManager.a().a(str)) != null) {
                return fragmentA;
            }
        }
        return null;
    }

    void d() {
        this.mActive.values().removeAll(Collections.singleton(null));
    }

    @Nullable
    Fragment e(@NonNull String str) {
        FragmentStateManager fragmentStateManager = this.mActive.get(str);
        if (fragmentStateManager != null) {
            return fragmentStateManager.a();
        }
        return null;
    }

    @NonNull
    ArrayList<FragmentState> e() {
        ArrayList<FragmentState> arrayList = new ArrayList<>(this.mActive.size());
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                Fragment fragmentA = fragmentStateManager.a();
                FragmentState fragmentStateM = fragmentStateManager.m();
                arrayList.add(fragmentStateM);
                if (FragmentManager.a(2)) {
                    Log.v(TAG, "Saved state of " + fragmentA + ": " + fragmentStateM.m);
                }
            }
        }
        return arrayList;
    }

    @Nullable
    ArrayList<String> f() {
        synchronized (this.mAdded) {
            if (this.mAdded.isEmpty()) {
                return null;
            }
            ArrayList<String> arrayList = new ArrayList<>(this.mAdded.size());
            for (Fragment fragment : this.mAdded) {
                arrayList.add(fragment.g);
                if (FragmentManager.a(2)) {
                    Log.v(TAG, "saveAllState: adding fragment (" + fragment.g + "): " + fragment);
                }
            }
            return arrayList;
        }
    }

    @NonNull
    List<FragmentStateManager> g() {
        ArrayList arrayList = new ArrayList();
        for (FragmentStateManager fragmentStateManager : this.mActive.values()) {
            if (fragmentStateManager != null) {
                arrayList.add(fragmentStateManager);
            }
        }
        return arrayList;
    }

    @NonNull
    List<Fragment> h() {
        ArrayList arrayList;
        if (this.mAdded.isEmpty()) {
            return Collections.emptyList();
        }
        synchronized (this.mAdded) {
            arrayList = new ArrayList(this.mAdded);
        }
        return arrayList;
    }

    @NonNull
    List<Fragment> i() {
        ArrayList arrayList = new ArrayList();
        Iterator<FragmentStateManager> it = this.mActive.values().iterator();
        while (it.hasNext()) {
            FragmentStateManager next = it.next();
            arrayList.add(next != null ? next.a() : null);
        }
        return arrayList;
    }

    int j() {
        return this.mActive.size();
    }
}
