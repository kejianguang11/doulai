package androidx.fragment.app;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import com.alipay.sdk.util.h;
import java.io.PrintWriter;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
final class BackStackRecord extends FragmentTransaction implements FragmentManager.BackStackEntry, FragmentManager.OpGenerator {
    private static final String TAG = "FragmentManager";
    final FragmentManager a;
    boolean b;
    int c;

    BackStackRecord(@NonNull FragmentManager fragmentManager) {
        super(fragmentManager.getFragmentFactory(), fragmentManager.h() != null ? fragmentManager.h().b().getClassLoader() : null);
        this.c = -1;
        this.a = fragmentManager;
    }

    private static boolean isFragmentPostponed(FragmentTransaction.Op op) {
        Fragment fragment = op.b;
        return (fragment == null || !fragment.l || fragment.G == null || fragment.A || fragment.z || !fragment.F()) ? false : true;
    }

    int a(boolean z) {
        if (this.b) {
            throw new IllegalStateException("commit already called");
        }
        if (FragmentManager.a(2)) {
            Log.v(TAG, "Commit: " + this);
            PrintWriter printWriter = new PrintWriter(new LogWriter(TAG));
            dump("  ", printWriter);
            printWriter.close();
        }
        this.b = true;
        this.c = this.j ? this.a.e() : -1;
        this.a.a(this, z);
        return this.c;
    }

    Fragment a(ArrayList<Fragment> arrayList, Fragment fragment) {
        Fragment fragment2 = fragment;
        int i = 0;
        while (i < this.d.size()) {
            FragmentTransaction.Op op = this.d.get(i);
            switch (op.a) {
                case 1:
                case 7:
                    arrayList.add(op.b);
                    break;
                case 2:
                    Fragment fragment3 = op.b;
                    int i2 = fragment3.x;
                    Fragment fragment4 = fragment2;
                    int i3 = i;
                    boolean z = false;
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        Fragment fragment5 = arrayList.get(size);
                        if (fragment5.x == i2) {
                            if (fragment5 == fragment3) {
                                z = true;
                            } else {
                                if (fragment5 == fragment4) {
                                    this.d.add(i3, new FragmentTransaction.Op(9, fragment5));
                                    i3++;
                                    fragment4 = null;
                                }
                                FragmentTransaction.Op op2 = new FragmentTransaction.Op(3, fragment5);
                                op2.c = op.c;
                                op2.e = op.e;
                                op2.d = op.d;
                                op2.f = op.f;
                                this.d.add(i3, op2);
                                arrayList.remove(fragment5);
                                i3++;
                            }
                        }
                    }
                    if (z) {
                        this.d.remove(i3);
                        i3--;
                    } else {
                        op.a = 1;
                        arrayList.add(fragment3);
                    }
                    i = i3;
                    fragment2 = fragment4;
                    break;
                case 3:
                case 6:
                    arrayList.remove(op.b);
                    if (op.b == fragment2) {
                        this.d.add(i, new FragmentTransaction.Op(9, op.b));
                        i++;
                        fragment2 = null;
                    }
                    break;
                case 8:
                    this.d.add(i, new FragmentTransaction.Op(9, fragment2));
                    i++;
                    fragment2 = op.b;
                    break;
            }
            i++;
        }
        return fragment2;
    }

    void a() {
        int size = this.d.size();
        for (int i = 0; i < size; i++) {
            FragmentTransaction.Op op = this.d.get(i);
            Fragment fragment = op.b;
            if (fragment != null) {
                fragment.d(false);
                fragment.b(this.i);
                fragment.a(this.q, this.r);
            }
            int i2 = op.a;
            if (i2 != 1) {
                switch (i2) {
                    case 3:
                        fragment.a(op.c, op.d, op.e, op.f);
                        this.a.j(fragment);
                        break;
                    case 4:
                        fragment.a(op.c, op.d, op.e, op.f);
                        this.a.k(fragment);
                        break;
                    case 5:
                        fragment.a(op.c, op.d, op.e, op.f);
                        this.a.a(fragment, false);
                        this.a.l(fragment);
                        break;
                    case 6:
                        fragment.a(op.c, op.d, op.e, op.f);
                        this.a.m(fragment);
                        break;
                    case 7:
                        fragment.a(op.c, op.d, op.e, op.f);
                        this.a.a(fragment, false);
                        this.a.n(fragment);
                        break;
                    case 8:
                        this.a.o(fragment);
                        break;
                    case 9:
                        this.a.o(null);
                        break;
                    case 10:
                        this.a.a(fragment, op.h);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown cmd: " + op.a);
                }
            } else {
                fragment.a(op.c, op.d, op.e, op.f);
                this.a.a(fragment, false);
                this.a.i(fragment);
            }
            if (!this.s && op.a != 1 && fragment != null && !FragmentManager.a) {
                this.a.g(fragment);
            }
        }
        if (this.s || FragmentManager.a) {
            return;
        }
        this.a.a(this.a.c, true);
    }

    void a(int i) {
        if (this.j) {
            if (FragmentManager.a(2)) {
                Log.v(TAG, "Bump nesting in " + this + " by " + i);
            }
            int size = this.d.size();
            for (int i2 = 0; i2 < size; i2++) {
                FragmentTransaction.Op op = this.d.get(i2);
                if (op.b != null) {
                    op.b.r += i;
                    if (FragmentManager.a(2)) {
                        Log.v(TAG, "Bump nesting of " + op.b + " to " + op.b.r);
                    }
                }
            }
        }
    }

    @Override // androidx.fragment.app.FragmentTransaction
    void a(int i, Fragment fragment, @Nullable String str, int i2) {
        super.a(i, fragment, str, i2);
        fragment.s = this.a;
    }

    void a(Fragment.OnStartEnterTransitionListener onStartEnterTransitionListener) {
        for (int i = 0; i < this.d.size(); i++) {
            FragmentTransaction.Op op = this.d.get(i);
            if (isFragmentPostponed(op)) {
                op.b.a(onStartEnterTransitionListener);
            }
        }
    }

    boolean a(ArrayList<BackStackRecord> arrayList, int i, int i2) {
        if (i2 == i) {
            return false;
        }
        int size = this.d.size();
        int i3 = -1;
        for (int i4 = 0; i4 < size; i4++) {
            FragmentTransaction.Op op = this.d.get(i4);
            int i5 = op.b != null ? op.b.x : 0;
            if (i5 != 0 && i5 != i3) {
                for (int i6 = i; i6 < i2; i6++) {
                    BackStackRecord backStackRecord = arrayList.get(i6);
                    int size2 = backStackRecord.d.size();
                    for (int i7 = 0; i7 < size2; i7++) {
                        FragmentTransaction.Op op2 = backStackRecord.d.get(i7);
                        if ((op2.b != null ? op2.b.x : 0) == i5) {
                            return true;
                        }
                    }
                }
                i3 = i5;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    Fragment b(ArrayList<Fragment> arrayList, Fragment fragment) {
        for (int size = this.d.size() - 1; size >= 0; size--) {
            FragmentTransaction.Op op = this.d.get(size);
            int i = op.a;
            if (i == 1) {
                arrayList.remove(op.b);
            } else if (i != 3) {
                switch (i) {
                    case 6:
                        arrayList.add(op.b);
                        break;
                    case 8:
                        fragment = null;
                        break;
                    case 9:
                        fragment = op.b;
                        break;
                    case 10:
                        op.h = op.g;
                        break;
                }
            }
        }
        return fragment;
    }

    void b(boolean z) {
        for (int size = this.d.size() - 1; size >= 0; size--) {
            FragmentTransaction.Op op = this.d.get(size);
            Fragment fragment = op.b;
            if (fragment != null) {
                fragment.d(true);
                fragment.b(FragmentManager.c(this.i));
                fragment.a(this.r, this.q);
            }
            int i = op.a;
            if (i != 1) {
                switch (i) {
                    case 3:
                        fragment.a(op.c, op.d, op.e, op.f);
                        this.a.i(fragment);
                        break;
                    case 4:
                        fragment.a(op.c, op.d, op.e, op.f);
                        this.a.l(fragment);
                        break;
                    case 5:
                        fragment.a(op.c, op.d, op.e, op.f);
                        this.a.a(fragment, true);
                        this.a.k(fragment);
                        break;
                    case 6:
                        fragment.a(op.c, op.d, op.e, op.f);
                        this.a.n(fragment);
                        break;
                    case 7:
                        fragment.a(op.c, op.d, op.e, op.f);
                        this.a.a(fragment, true);
                        this.a.m(fragment);
                        break;
                    case 8:
                        this.a.o(null);
                        break;
                    case 9:
                        this.a.o(fragment);
                        break;
                    case 10:
                        this.a.a(fragment, op.g);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown cmd: " + op.a);
                }
            } else {
                fragment.a(op.c, op.d, op.e, op.f);
                this.a.a(fragment, true);
                this.a.j(fragment);
            }
            if (!this.s && op.a != 3 && fragment != null && !FragmentManager.a) {
                this.a.g(fragment);
            }
        }
        if (this.s || !z || FragmentManager.a) {
            return;
        }
        this.a.a(this.a.c, true);
    }

    boolean b() {
        for (int i = 0; i < this.d.size(); i++) {
            if (isFragmentPostponed(this.d.get(i))) {
                return true;
            }
        }
        return false;
    }

    boolean b(int i) {
        int size = this.d.size();
        for (int i2 = 0; i2 < size; i2++) {
            FragmentTransaction.Op op = this.d.get(i2);
            int i3 = op.b != null ? op.b.x : 0;
            if (i3 != 0 && i3 == i) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.fragment.app.FragmentTransaction
    public int commit() {
        return a(false);
    }

    @Override // androidx.fragment.app.FragmentTransaction
    public int commitAllowingStateLoss() {
        return a(true);
    }

    @Override // androidx.fragment.app.FragmentTransaction
    public void commitNow() {
        disallowAddToBackStack();
        this.a.b((FragmentManager.OpGenerator) this, false);
    }

    @Override // androidx.fragment.app.FragmentTransaction
    public void commitNowAllowingStateLoss() {
        disallowAddToBackStack();
        this.a.b((FragmentManager.OpGenerator) this, true);
    }

    @Override // androidx.fragment.app.FragmentTransaction
    @NonNull
    public FragmentTransaction detach(@NonNull Fragment fragment) {
        if (fragment.s == null || fragment.s == this.a) {
            return super.detach(fragment);
        }
        throw new IllegalStateException("Cannot detach Fragment attached to a different FragmentManager. Fragment " + fragment.toString() + " is already attached to a FragmentManager.");
    }

    public void dump(String str, PrintWriter printWriter) {
        dump(str, printWriter, true);
    }

    public void dump(String str, PrintWriter printWriter, boolean z) {
        String str2;
        if (z) {
            printWriter.print(str);
            printWriter.print("mName=");
            printWriter.print(this.l);
            printWriter.print(" mIndex=");
            printWriter.print(this.c);
            printWriter.print(" mCommitted=");
            printWriter.println(this.b);
            if (this.i != 0) {
                printWriter.print(str);
                printWriter.print("mTransition=#");
                printWriter.print(Integer.toHexString(this.i));
            }
            if (this.e != 0 || this.f != 0) {
                printWriter.print(str);
                printWriter.print("mEnterAnim=#");
                printWriter.print(Integer.toHexString(this.e));
                printWriter.print(" mExitAnim=#");
                printWriter.println(Integer.toHexString(this.f));
            }
            if (this.g != 0 || this.h != 0) {
                printWriter.print(str);
                printWriter.print("mPopEnterAnim=#");
                printWriter.print(Integer.toHexString(this.g));
                printWriter.print(" mPopExitAnim=#");
                printWriter.println(Integer.toHexString(this.h));
            }
            if (this.m != 0 || this.n != null) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbTitleRes=#");
                printWriter.print(Integer.toHexString(this.m));
                printWriter.print(" mBreadCrumbTitleText=");
                printWriter.println(this.n);
            }
            if (this.o != 0 || this.p != null) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbShortTitleRes=#");
                printWriter.print(Integer.toHexString(this.o));
                printWriter.print(" mBreadCrumbShortTitleText=");
                printWriter.println(this.p);
            }
        }
        if (this.d.isEmpty()) {
            return;
        }
        printWriter.print(str);
        printWriter.println("Operations:");
        int size = this.d.size();
        for (int i = 0; i < size; i++) {
            FragmentTransaction.Op op = this.d.get(i);
            switch (op.a) {
                case 0:
                    str2 = "NULL";
                    break;
                case 1:
                    str2 = "ADD";
                    break;
                case 2:
                    str2 = "REPLACE";
                    break;
                case 3:
                    str2 = "REMOVE";
                    break;
                case 4:
                    str2 = "HIDE";
                    break;
                case 5:
                    str2 = "SHOW";
                    break;
                case 6:
                    str2 = "DETACH";
                    break;
                case 7:
                    str2 = "ATTACH";
                    break;
                case 8:
                    str2 = "SET_PRIMARY_NAV";
                    break;
                case 9:
                    str2 = "UNSET_PRIMARY_NAV";
                    break;
                case 10:
                    str2 = "OP_SET_MAX_LIFECYCLE";
                    break;
                default:
                    str2 = "cmd=" + op.a;
                    break;
            }
            printWriter.print(str);
            printWriter.print("  Op #");
            printWriter.print(i);
            printWriter.print(": ");
            printWriter.print(str2);
            printWriter.print(" ");
            printWriter.println(op.b);
            if (z) {
                if (op.c != 0 || op.d != 0) {
                    printWriter.print(str);
                    printWriter.print("enterAnim=#");
                    printWriter.print(Integer.toHexString(op.c));
                    printWriter.print(" exitAnim=#");
                    printWriter.println(Integer.toHexString(op.d));
                }
                if (op.e != 0 || op.f != 0) {
                    printWriter.print(str);
                    printWriter.print("popEnterAnim=#");
                    printWriter.print(Integer.toHexString(op.e));
                    printWriter.print(" popExitAnim=#");
                    printWriter.println(Integer.toHexString(op.f));
                }
            }
        }
    }

    @Override // androidx.fragment.app.FragmentManager.OpGenerator
    public boolean generateOps(@NonNull ArrayList<BackStackRecord> arrayList, @NonNull ArrayList<Boolean> arrayList2) {
        if (FragmentManager.a(2)) {
            Log.v(TAG, "Run: " + this);
        }
        arrayList.add(this);
        arrayList2.add(false);
        if (!this.j) {
            return true;
        }
        this.a.a(this);
        return true;
    }

    @Override // androidx.fragment.app.FragmentManager.BackStackEntry
    @Nullable
    public CharSequence getBreadCrumbShortTitle() {
        return this.o != 0 ? this.a.h().b().getText(this.o) : this.p;
    }

    @Override // androidx.fragment.app.FragmentManager.BackStackEntry
    public int getBreadCrumbShortTitleRes() {
        return this.o;
    }

    @Override // androidx.fragment.app.FragmentManager.BackStackEntry
    @Nullable
    public CharSequence getBreadCrumbTitle() {
        return this.m != 0 ? this.a.h().b().getText(this.m) : this.n;
    }

    @Override // androidx.fragment.app.FragmentManager.BackStackEntry
    public int getBreadCrumbTitleRes() {
        return this.m;
    }

    @Override // androidx.fragment.app.FragmentManager.BackStackEntry
    public int getId() {
        return this.c;
    }

    @Override // androidx.fragment.app.FragmentManager.BackStackEntry
    @Nullable
    public String getName() {
        return this.l;
    }

    @Override // androidx.fragment.app.FragmentTransaction
    @NonNull
    public FragmentTransaction hide(@NonNull Fragment fragment) {
        if (fragment.s == null || fragment.s == this.a) {
            return super.hide(fragment);
        }
        throw new IllegalStateException("Cannot hide Fragment attached to a different FragmentManager. Fragment " + fragment.toString() + " is already attached to a FragmentManager.");
    }

    @Override // androidx.fragment.app.FragmentTransaction
    public boolean isEmpty() {
        return this.d.isEmpty();
    }

    @Override // androidx.fragment.app.FragmentTransaction
    @NonNull
    public FragmentTransaction remove(@NonNull Fragment fragment) {
        if (fragment.s == null || fragment.s == this.a) {
            return super.remove(fragment);
        }
        throw new IllegalStateException("Cannot remove Fragment attached to a different FragmentManager. Fragment " + fragment.toString() + " is already attached to a FragmentManager.");
    }

    public void runOnCommitRunnables() {
        if (this.t != null) {
            for (int i = 0; i < this.t.size(); i++) {
                this.t.get(i).run();
            }
            this.t = null;
        }
    }

    @Override // androidx.fragment.app.FragmentTransaction
    @NonNull
    public FragmentTransaction setMaxLifecycle(@NonNull Fragment fragment, @NonNull Lifecycle.State state) {
        if (fragment.s != this.a) {
            throw new IllegalArgumentException("Cannot setMaxLifecycle for Fragment not attached to FragmentManager " + this.a);
        }
        if (state == Lifecycle.State.INITIALIZED && fragment.b > -1) {
            throw new IllegalArgumentException("Cannot set maximum Lifecycle to " + state + " after the Fragment has been created");
        }
        if (state != Lifecycle.State.DESTROYED) {
            return super.setMaxLifecycle(fragment, state);
        }
        throw new IllegalArgumentException("Cannot set maximum Lifecycle to " + state + ". Use remove() to remove the fragment from the FragmentManager and trigger its destruction.");
    }

    @Override // androidx.fragment.app.FragmentTransaction
    @NonNull
    public FragmentTransaction setPrimaryNavigationFragment(@Nullable Fragment fragment) {
        if (fragment == null || fragment.s == null || fragment.s == this.a) {
            return super.setPrimaryNavigationFragment(fragment);
        }
        throw new IllegalStateException("Cannot setPrimaryNavigation for Fragment attached to a different FragmentManager. Fragment " + fragment.toString() + " is already attached to a FragmentManager.");
    }

    @Override // androidx.fragment.app.FragmentTransaction
    @NonNull
    public FragmentTransaction show(@NonNull Fragment fragment) {
        if (fragment.s == null || fragment.s == this.a) {
            return super.show(fragment);
        }
        throw new IllegalStateException("Cannot show Fragment attached to a different FragmentManager. Fragment " + fragment.toString() + " is already attached to a FragmentManager.");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("BackStackEntry{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        if (this.c >= 0) {
            sb.append(" #");
            sb.append(this.c);
        }
        if (this.l != null) {
            sb.append(" ");
            sb.append(this.l);
        }
        sb.append(h.d);
        return sb.toString();
    }
}
