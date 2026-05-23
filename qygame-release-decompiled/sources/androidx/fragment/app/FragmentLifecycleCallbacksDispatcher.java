package androidx.fragment.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: loaded from: classes.dex */
class FragmentLifecycleCallbacksDispatcher {

    @NonNull
    private final FragmentManager mFragmentManager;

    @NonNull
    private final CopyOnWriteArrayList<FragmentLifecycleCallbacksHolder> mLifecycleCallbacks = new CopyOnWriteArrayList<>();

    private static final class FragmentLifecycleCallbacksHolder {

        @NonNull
        final FragmentManager.FragmentLifecycleCallbacks a;
        final boolean b;

        FragmentLifecycleCallbacksHolder(@NonNull FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z) {
            this.a = fragmentLifecycleCallbacks;
            this.b = z;
        }
    }

    FragmentLifecycleCallbacksDispatcher(@NonNull FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    void a(@NonNull Fragment fragment, @Nullable Bundle bundle, boolean z) {
        Fragment fragmentI = this.mFragmentManager.i();
        if (fragmentI != null) {
            fragmentI.getParentFragmentManager().z().a(fragment, bundle, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.b) {
                fragmentLifecycleCallbacksHolder.a.onFragmentPreCreated(this.mFragmentManager, fragment, bundle);
            }
        }
    }

    void a(@NonNull Fragment fragment, @NonNull View view, @Nullable Bundle bundle, boolean z) {
        Fragment fragmentI = this.mFragmentManager.i();
        if (fragmentI != null) {
            fragmentI.getParentFragmentManager().z().a(fragment, view, bundle, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.b) {
                fragmentLifecycleCallbacksHolder.a.onFragmentViewCreated(this.mFragmentManager, fragment, view, bundle);
            }
        }
    }

    void a(@NonNull Fragment fragment, boolean z) {
        Context contextB = this.mFragmentManager.h().b();
        Fragment fragmentI = this.mFragmentManager.i();
        if (fragmentI != null) {
            fragmentI.getParentFragmentManager().z().a(fragment, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.b) {
                fragmentLifecycleCallbacksHolder.a.onFragmentPreAttached(this.mFragmentManager, fragment, contextB);
            }
        }
    }

    void b(@NonNull Fragment fragment, @Nullable Bundle bundle, boolean z) {
        Fragment fragmentI = this.mFragmentManager.i();
        if (fragmentI != null) {
            fragmentI.getParentFragmentManager().z().b(fragment, bundle, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.b) {
                fragmentLifecycleCallbacksHolder.a.onFragmentCreated(this.mFragmentManager, fragment, bundle);
            }
        }
    }

    void b(@NonNull Fragment fragment, boolean z) {
        Context contextB = this.mFragmentManager.h().b();
        Fragment fragmentI = this.mFragmentManager.i();
        if (fragmentI != null) {
            fragmentI.getParentFragmentManager().z().b(fragment, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.b) {
                fragmentLifecycleCallbacksHolder.a.onFragmentAttached(this.mFragmentManager, fragment, contextB);
            }
        }
    }

    void c(@NonNull Fragment fragment, @Nullable Bundle bundle, boolean z) {
        Fragment fragmentI = this.mFragmentManager.i();
        if (fragmentI != null) {
            fragmentI.getParentFragmentManager().z().c(fragment, bundle, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.b) {
                fragmentLifecycleCallbacksHolder.a.onFragmentActivityCreated(this.mFragmentManager, fragment, bundle);
            }
        }
    }

    void c(@NonNull Fragment fragment, boolean z) {
        Fragment fragmentI = this.mFragmentManager.i();
        if (fragmentI != null) {
            fragmentI.getParentFragmentManager().z().c(fragment, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.b) {
                fragmentLifecycleCallbacksHolder.a.onFragmentStarted(this.mFragmentManager, fragment);
            }
        }
    }

    void d(@NonNull Fragment fragment, @NonNull Bundle bundle, boolean z) {
        Fragment fragmentI = this.mFragmentManager.i();
        if (fragmentI != null) {
            fragmentI.getParentFragmentManager().z().d(fragment, bundle, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.b) {
                fragmentLifecycleCallbacksHolder.a.onFragmentSaveInstanceState(this.mFragmentManager, fragment, bundle);
            }
        }
    }

    void d(@NonNull Fragment fragment, boolean z) {
        Fragment fragmentI = this.mFragmentManager.i();
        if (fragmentI != null) {
            fragmentI.getParentFragmentManager().z().d(fragment, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.b) {
                fragmentLifecycleCallbacksHolder.a.onFragmentResumed(this.mFragmentManager, fragment);
            }
        }
    }

    void e(@NonNull Fragment fragment, boolean z) {
        Fragment fragmentI = this.mFragmentManager.i();
        if (fragmentI != null) {
            fragmentI.getParentFragmentManager().z().e(fragment, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.b) {
                fragmentLifecycleCallbacksHolder.a.onFragmentPaused(this.mFragmentManager, fragment);
            }
        }
    }

    void f(@NonNull Fragment fragment, boolean z) {
        Fragment fragmentI = this.mFragmentManager.i();
        if (fragmentI != null) {
            fragmentI.getParentFragmentManager().z().f(fragment, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.b) {
                fragmentLifecycleCallbacksHolder.a.onFragmentStopped(this.mFragmentManager, fragment);
            }
        }
    }

    void g(@NonNull Fragment fragment, boolean z) {
        Fragment fragmentI = this.mFragmentManager.i();
        if (fragmentI != null) {
            fragmentI.getParentFragmentManager().z().g(fragment, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.b) {
                fragmentLifecycleCallbacksHolder.a.onFragmentViewDestroyed(this.mFragmentManager, fragment);
            }
        }
    }

    void h(@NonNull Fragment fragment, boolean z) {
        Fragment fragmentI = this.mFragmentManager.i();
        if (fragmentI != null) {
            fragmentI.getParentFragmentManager().z().h(fragment, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.b) {
                fragmentLifecycleCallbacksHolder.a.onFragmentDestroyed(this.mFragmentManager, fragment);
            }
        }
    }

    void i(@NonNull Fragment fragment, boolean z) {
        Fragment fragmentI = this.mFragmentManager.i();
        if (fragmentI != null) {
            fragmentI.getParentFragmentManager().z().i(fragment, true);
        }
        for (FragmentLifecycleCallbacksHolder fragmentLifecycleCallbacksHolder : this.mLifecycleCallbacks) {
            if (!z || fragmentLifecycleCallbacksHolder.b) {
                fragmentLifecycleCallbacksHolder.a.onFragmentDetached(this.mFragmentManager, fragment);
            }
        }
    }

    public void registerFragmentLifecycleCallbacks(@NonNull FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks, boolean z) {
        this.mLifecycleCallbacks.add(new FragmentLifecycleCallbacksHolder(fragmentLifecycleCallbacks, z));
    }

    public void unregisterFragmentLifecycleCallbacks(@NonNull FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks) {
        synchronized (this.mLifecycleCallbacks) {
            int i = 0;
            int size = this.mLifecycleCallbacks.size();
            while (true) {
                if (i >= size) {
                    break;
                }
                if (this.mLifecycleCallbacks.get(i).a == fragmentLifecycleCallbacks) {
                    this.mLifecycleCallbacks.remove(i);
                    break;
                }
                i++;
            }
        }
    }
}
