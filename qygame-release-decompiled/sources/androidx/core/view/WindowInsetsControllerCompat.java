package androidx.core.view;

import android.R;
import android.os.Build;
import android.os.CancellationSignal;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsAnimationControlListener;
import android.view.WindowInsetsAnimationController;
import android.view.WindowInsetsController;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.collection.SimpleArrayMap;

/* JADX INFO: loaded from: classes.dex */
public final class WindowInsetsControllerCompat {
    public static final int BEHAVIOR_SHOW_BARS_BY_SWIPE = 1;
    public static final int BEHAVIOR_SHOW_BARS_BY_TOUCH = 0;
    public static final int BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE = 2;
    private final Impl mImpl;

    private static class Impl {
        Impl() {
        }

        int a() {
            return 0;
        }

        void a(int i) {
        }

        void a(int i, long j, Interpolator interpolator, CancellationSignal cancellationSignal, WindowInsetsAnimationControlListenerCompat windowInsetsAnimationControlListenerCompat) {
        }

        void a(OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        }

        void b(int i) {
        }

        void b(@NonNull OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        }

        void c(int i) {
        }

        public boolean isAppearanceLightNavigationBars() {
            return false;
        }

        public boolean isAppearanceLightStatusBars() {
            return false;
        }

        public void setAppearanceLightNavigationBars(boolean z) {
        }

        public void setAppearanceLightStatusBars(boolean z) {
        }
    }

    @RequiresApi(20)
    private static class Impl20 extends Impl {

        @NonNull
        protected final Window a;

        @Nullable
        private final View mView;

        Impl20(@NonNull Window window, @Nullable View view) {
            this.a = window;
            this.mView = view;
        }

        private void hideForType(int i) {
            int i2;
            if (i == 8) {
                ((InputMethodManager) this.a.getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.a.getDecorView().getWindowToken(), 0);
                return;
            }
            switch (i) {
                case 1:
                    i2 = 4;
                    break;
                case 2:
                    i2 = 2;
                    break;
                default:
                    return;
            }
            d(i2);
        }

        private void showForType(int i) {
            if (i != 8) {
                switch (i) {
                    case 1:
                        e(4);
                        g(1024);
                        break;
                    case 2:
                        e(2);
                        break;
                }
                return;
            }
            final View currentFocus = this.mView;
            if (currentFocus == null || !(currentFocus.isInEditMode() || currentFocus.onCheckIsTextEditor())) {
                currentFocus = this.a.getCurrentFocus();
            } else {
                currentFocus.requestFocus();
            }
            if (currentFocus == null) {
                currentFocus = this.a.findViewById(R.id.content);
            }
            if (currentFocus == null || !currentFocus.hasWindowFocus()) {
                return;
            }
            currentFocus.post(new Runnable() { // from class: androidx.core.view.WindowInsetsControllerCompat.Impl20.1
                @Override // java.lang.Runnable
                public void run() {
                    ((InputMethodManager) currentFocus.getContext().getSystemService("input_method")).showSoftInput(currentFocus, 0);
                }
            });
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        int a() {
            return 0;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void a(int i) {
            for (int i2 = 1; i2 <= 256; i2 <<= 1) {
                if ((i & i2) != 0) {
                    showForType(i2);
                }
            }
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void a(int i, long j, Interpolator interpolator, CancellationSignal cancellationSignal, WindowInsetsAnimationControlListenerCompat windowInsetsAnimationControlListenerCompat) {
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void a(OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void b(int i) {
            for (int i2 = 1; i2 <= 256; i2 <<= 1) {
                if ((i & i2) != 0) {
                    hideForType(i2);
                }
            }
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void b(@NonNull OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void c(int i) {
            switch (i) {
                case 0:
                    e(6144);
                    break;
                case 1:
                    e(4096);
                    d(2048);
                    break;
                case 2:
                    e(2048);
                    d(4096);
                    break;
            }
        }

        protected void d(int i) {
            View decorView = this.a.getDecorView();
            decorView.setSystemUiVisibility(i | decorView.getSystemUiVisibility());
        }

        protected void e(int i) {
            View decorView = this.a.getDecorView();
            decorView.setSystemUiVisibility((~i) & decorView.getSystemUiVisibility());
        }

        protected void f(int i) {
            this.a.addFlags(i);
        }

        protected void g(int i) {
            this.a.clearFlags(i);
        }
    }

    @RequiresApi(23)
    private static class Impl23 extends Impl20 {
        Impl23(@NonNull Window window, @Nullable View view) {
            super(window, view);
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public boolean isAppearanceLightStatusBars() {
            return (this.a.getDecorView().getSystemUiVisibility() & 8192) != 0;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void setAppearanceLightStatusBars(boolean z) {
            if (!z) {
                e(8192);
                return;
            }
            g(67108864);
            f(Integer.MIN_VALUE);
            d(8192);
        }
    }

    @RequiresApi(26)
    private static class Impl26 extends Impl23 {
        Impl26(@NonNull Window window, @Nullable View view) {
            super(window, view);
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public boolean isAppearanceLightNavigationBars() {
            return (this.a.getDecorView().getSystemUiVisibility() & 16) != 0;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void setAppearanceLightNavigationBars(boolean z) {
            if (!z) {
                e(16);
                return;
            }
            g(134217728);
            f(Integer.MIN_VALUE);
            d(16);
        }
    }

    @RequiresApi(30)
    private static class Impl30 extends Impl {
        final WindowInsetsControllerCompat a;
        final WindowInsetsController b;
        private final SimpleArrayMap<OnControllableInsetsChangedListener, WindowInsetsController.OnControllableInsetsChangedListener> mListeners;

        Impl30(@NonNull Window window, @NonNull WindowInsetsControllerCompat windowInsetsControllerCompat) {
            this(window.getInsetsController(), windowInsetsControllerCompat);
        }

        Impl30(@NonNull WindowInsetsController windowInsetsController, @NonNull WindowInsetsControllerCompat windowInsetsControllerCompat) {
            this.mListeners = new SimpleArrayMap<>();
            this.b = windowInsetsController;
            this.a = windowInsetsControllerCompat;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        int a() {
            return this.b.getSystemBarsBehavior();
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void a(int i) {
            this.b.show(i);
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void a(int i, long j, @Nullable Interpolator interpolator, @Nullable CancellationSignal cancellationSignal, @NonNull final WindowInsetsAnimationControlListenerCompat windowInsetsAnimationControlListenerCompat) {
            this.b.controlWindowInsetsAnimation(i, j, interpolator, cancellationSignal, new WindowInsetsAnimationControlListener() { // from class: androidx.core.view.WindowInsetsControllerCompat.Impl30.1
                private WindowInsetsAnimationControllerCompat mCompatAnimController = null;

                @Override // android.view.WindowInsetsAnimationControlListener
                public void onCancelled(@Nullable WindowInsetsAnimationController windowInsetsAnimationController) {
                    windowInsetsAnimationControlListenerCompat.onCancelled(windowInsetsAnimationController == null ? null : this.mCompatAnimController);
                }

                @Override // android.view.WindowInsetsAnimationControlListener
                public void onFinished(@NonNull WindowInsetsAnimationController windowInsetsAnimationController) {
                    windowInsetsAnimationControlListenerCompat.onFinished(this.mCompatAnimController);
                }

                @Override // android.view.WindowInsetsAnimationControlListener
                public void onReady(@NonNull WindowInsetsAnimationController windowInsetsAnimationController, int i2) {
                    this.mCompatAnimController = new WindowInsetsAnimationControllerCompat(windowInsetsAnimationController);
                    windowInsetsAnimationControlListenerCompat.onReady(this.mCompatAnimController, i2);
                }
            });
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void a(@NonNull final OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
            if (this.mListeners.containsKey(onControllableInsetsChangedListener)) {
                return;
            }
            WindowInsetsController.OnControllableInsetsChangedListener onControllableInsetsChangedListener2 = new WindowInsetsController.OnControllableInsetsChangedListener() { // from class: androidx.core.view.WindowInsetsControllerCompat.Impl30.2
                @Override // android.view.WindowInsetsController.OnControllableInsetsChangedListener
                public void onControllableInsetsChanged(@NonNull WindowInsetsController windowInsetsController, int i) {
                    if (Impl30.this.b == windowInsetsController) {
                        onControllableInsetsChangedListener.onControllableInsetsChanged(Impl30.this.a, i);
                    }
                }
            };
            this.mListeners.put(onControllableInsetsChangedListener, onControllableInsetsChangedListener2);
            this.b.addOnControllableInsetsChangedListener(onControllableInsetsChangedListener2);
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void b(int i) {
            this.b.hide(i);
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void b(@NonNull OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
            WindowInsetsController.OnControllableInsetsChangedListener onControllableInsetsChangedListenerRemove = this.mListeners.remove(onControllableInsetsChangedListener);
            if (onControllableInsetsChangedListenerRemove != null) {
                this.b.removeOnControllableInsetsChangedListener(onControllableInsetsChangedListenerRemove);
            }
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        void c(int i) {
            this.b.setSystemBarsBehavior(i);
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public boolean isAppearanceLightNavigationBars() {
            return (this.b.getSystemBarsAppearance() & 16) != 0;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public boolean isAppearanceLightStatusBars() {
            return (this.b.getSystemBarsAppearance() & 8) != 0;
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void setAppearanceLightNavigationBars(boolean z) {
            if (z) {
                this.b.setSystemBarsAppearance(16, 16);
            } else {
                this.b.setSystemBarsAppearance(0, 16);
            }
        }

        @Override // androidx.core.view.WindowInsetsControllerCompat.Impl
        public void setAppearanceLightStatusBars(boolean z) {
            if (z) {
                this.b.setSystemBarsAppearance(8, 8);
            } else {
                this.b.setSystemBarsAppearance(0, 8);
            }
        }
    }

    public interface OnControllableInsetsChangedListener {
        void onControllableInsetsChanged(@NonNull WindowInsetsControllerCompat windowInsetsControllerCompat, int i);
    }

    public WindowInsetsControllerCompat(@NonNull Window window, @NonNull View view) {
        Impl impl20;
        if (Build.VERSION.SDK_INT >= 30) {
            this.mImpl = new Impl30(window, this);
            return;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            impl20 = new Impl26(window, view);
        } else if (Build.VERSION.SDK_INT >= 23) {
            impl20 = new Impl23(window, view);
        } else {
            if (Build.VERSION.SDK_INT < 20) {
                this.mImpl = new Impl();
                return;
            }
            impl20 = new Impl20(window, view);
        }
        this.mImpl = impl20;
    }

    @RequiresApi(30)
    private WindowInsetsControllerCompat(@NonNull WindowInsetsController windowInsetsController) {
        if (Build.VERSION.SDK_INT >= 30) {
            this.mImpl = new Impl30(windowInsetsController, this);
        } else {
            this.mImpl = new Impl();
        }
    }

    @NonNull
    @RequiresApi(30)
    public static WindowInsetsControllerCompat toWindowInsetsControllerCompat(@NonNull WindowInsetsController windowInsetsController) {
        return new WindowInsetsControllerCompat(windowInsetsController);
    }

    public void addOnControllableInsetsChangedListener(@NonNull OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        this.mImpl.a(onControllableInsetsChangedListener);
    }

    public void controlWindowInsetsAnimation(int i, long j, @Nullable Interpolator interpolator, @Nullable CancellationSignal cancellationSignal, @NonNull WindowInsetsAnimationControlListenerCompat windowInsetsAnimationControlListenerCompat) {
        this.mImpl.a(i, j, interpolator, cancellationSignal, windowInsetsAnimationControlListenerCompat);
    }

    public int getSystemBarsBehavior() {
        return this.mImpl.a();
    }

    public void hide(int i) {
        this.mImpl.b(i);
    }

    public boolean isAppearanceLightNavigationBars() {
        return this.mImpl.isAppearanceLightNavigationBars();
    }

    public boolean isAppearanceLightStatusBars() {
        return this.mImpl.isAppearanceLightStatusBars();
    }

    public void removeOnControllableInsetsChangedListener(@NonNull OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        this.mImpl.b(onControllableInsetsChangedListener);
    }

    public void setAppearanceLightNavigationBars(boolean z) {
        this.mImpl.setAppearanceLightNavigationBars(z);
    }

    public void setAppearanceLightStatusBars(boolean z) {
        this.mImpl.setAppearanceLightStatusBars(z);
    }

    public void setSystemBarsBehavior(int i) {
        this.mImpl.c(i);
    }

    public void show(int i) {
        this.mImpl.a(i);
    }
}
