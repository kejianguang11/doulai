package androidx.core.view;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.Insets;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class WindowInsetsCompat {

    @NonNull
    public static final WindowInsetsCompat CONSUMED;
    private static final String TAG = "WindowInsetsCompat";
    private final Impl mImpl;

    @RequiresApi(21)
    static class Api21ReflectionHolder {
        private static Field sContentInsets;
        private static boolean sReflectionSucceeded;
        private static Field sStableInsets;
        private static Field sViewAttachInfoField;

        static {
            try {
                sViewAttachInfoField = View.class.getDeclaredField("mAttachInfo");
                sViewAttachInfoField.setAccessible(true);
                Class<?> cls = Class.forName("android.view.View$AttachInfo");
                sStableInsets = cls.getDeclaredField("mStableInsets");
                sStableInsets.setAccessible(true);
                sContentInsets = cls.getDeclaredField("mContentInsets");
                sContentInsets.setAccessible(true);
                sReflectionSucceeded = true;
            } catch (ReflectiveOperationException e) {
                Log.w(WindowInsetsCompat.TAG, "Failed to get visible insets from AttachInfo " + e.getMessage(), e);
            }
        }

        private Api21ReflectionHolder() {
        }

        @Nullable
        public static WindowInsetsCompat getRootWindowInsets(@NonNull View view) {
            if (sReflectionSucceeded && view.isAttachedToWindow()) {
                try {
                    Object obj = sViewAttachInfoField.get(view.getRootView());
                    if (obj != null) {
                        Rect rect = (Rect) sStableInsets.get(obj);
                        Rect rect2 = (Rect) sContentInsets.get(obj);
                        if (rect != null && rect2 != null) {
                            WindowInsetsCompat windowInsetsCompatBuild = new Builder().setStableInsets(Insets.of(rect)).setSystemWindowInsets(Insets.of(rect2)).build();
                            windowInsetsCompatBuild.a(windowInsetsCompatBuild);
                            windowInsetsCompatBuild.a(view.getRootView());
                            return windowInsetsCompatBuild;
                        }
                    }
                } catch (IllegalAccessException e) {
                    Log.w(WindowInsetsCompat.TAG, "Failed to get insets from AttachInfo. " + e.getMessage(), e);
                }
            }
            return null;
        }
    }

    public static final class Builder {
        private final BuilderImpl mImpl;

        public Builder() {
            this.mImpl = Build.VERSION.SDK_INT >= 30 ? new BuilderImpl30() : Build.VERSION.SDK_INT >= 29 ? new BuilderImpl29() : Build.VERSION.SDK_INT >= 20 ? new BuilderImpl20() : new BuilderImpl();
        }

        public Builder(@NonNull WindowInsetsCompat windowInsetsCompat) {
            this.mImpl = Build.VERSION.SDK_INT >= 30 ? new BuilderImpl30(windowInsetsCompat) : Build.VERSION.SDK_INT >= 29 ? new BuilderImpl29(windowInsetsCompat) : Build.VERSION.SDK_INT >= 20 ? new BuilderImpl20(windowInsetsCompat) : new BuilderImpl(windowInsetsCompat);
        }

        @NonNull
        public WindowInsetsCompat build() {
            return this.mImpl.b();
        }

        @NonNull
        public Builder setDisplayCutout(@Nullable DisplayCutoutCompat displayCutoutCompat) {
            this.mImpl.a(displayCutoutCompat);
            return this;
        }

        @NonNull
        public Builder setInsets(int i, @NonNull Insets insets) {
            this.mImpl.a(i, insets);
            return this;
        }

        @NonNull
        public Builder setInsetsIgnoringVisibility(int i, @NonNull Insets insets) {
            this.mImpl.b(i, insets);
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setMandatorySystemGestureInsets(@NonNull Insets insets) {
            this.mImpl.c(insets);
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setStableInsets(@NonNull Insets insets) {
            this.mImpl.e(insets);
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setSystemGestureInsets(@NonNull Insets insets) {
            this.mImpl.b(insets);
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setSystemWindowInsets(@NonNull Insets insets) {
            this.mImpl.a(insets);
            return this;
        }

        @NonNull
        @Deprecated
        public Builder setTappableElementInsets(@NonNull Insets insets) {
            this.mImpl.d(insets);
            return this;
        }

        @NonNull
        public Builder setVisible(int i, boolean z) {
            this.mImpl.a(i, z);
            return this;
        }
    }

    private static class BuilderImpl {
        Insets[] a;
        private final WindowInsetsCompat mInsets;

        BuilderImpl() {
            this(new WindowInsetsCompat((WindowInsetsCompat) null));
        }

        BuilderImpl(@NonNull WindowInsetsCompat windowInsetsCompat) {
            this.mInsets = windowInsetsCompat;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0036  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0045  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x0054  */
        /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        protected final void a() {
            Insets insets;
            Insets insets2;
            Insets insets3;
            if (this.a == null) {
                return;
            }
            Insets insetsMax = this.a[Type.a(1)];
            Insets insets4 = this.a[Type.a(2)];
            if (insetsMax == null || insets4 == null) {
                if (insetsMax == null) {
                    if (insets4 != null) {
                        a(insets4);
                    }
                }
                insets = this.a[Type.a(16)];
                if (insets != null) {
                    b(insets);
                }
                insets2 = this.a[Type.a(32)];
                if (insets2 != null) {
                    c(insets2);
                }
                insets3 = this.a[Type.a(64)];
                if (insets3 == null) {
                    d(insets3);
                    return;
                }
                return;
            }
            insetsMax = Insets.max(insetsMax, insets4);
            a(insetsMax);
            insets = this.a[Type.a(16)];
            if (insets != null) {
            }
            insets2 = this.a[Type.a(32)];
            if (insets2 != null) {
            }
            insets3 = this.a[Type.a(64)];
            if (insets3 == null) {
            }
        }

        void a(int i, @NonNull Insets insets) {
            if (this.a == null) {
                this.a = new Insets[9];
            }
            for (int i2 = 1; i2 <= 256; i2 <<= 1) {
                if ((i & i2) != 0) {
                    this.a[Type.a(i2)] = insets;
                }
            }
        }

        void a(int i, boolean z) {
        }

        void a(@NonNull Insets insets) {
        }

        void a(@Nullable DisplayCutoutCompat displayCutoutCompat) {
        }

        @NonNull
        WindowInsetsCompat b() {
            a();
            return this.mInsets;
        }

        void b(int i, @NonNull Insets insets) {
            if (i == 8) {
                throw new IllegalArgumentException("Ignoring visibility inset not available for IME");
            }
        }

        void b(@NonNull Insets insets) {
        }

        void c(@NonNull Insets insets) {
        }

        void d(@NonNull Insets insets) {
        }

        void e(@NonNull Insets insets) {
        }
    }

    @RequiresApi(api = 20)
    private static class BuilderImpl20 extends BuilderImpl {
        private static Constructor<WindowInsets> sConstructor = null;
        private static boolean sConstructorFetched = false;
        private static Field sConsumedField = null;
        private static boolean sConsumedFieldFetched = false;
        private WindowInsets mInsets;
        private Insets mStableInsets;

        BuilderImpl20() {
            this.mInsets = createWindowInsetsInstance();
        }

        BuilderImpl20(@NonNull WindowInsetsCompat windowInsetsCompat) {
            this.mInsets = windowInsetsCompat.toWindowInsets();
        }

        @Nullable
        private static WindowInsets createWindowInsetsInstance() {
            if (!sConsumedFieldFetched) {
                try {
                    sConsumedField = WindowInsets.class.getDeclaredField("CONSUMED");
                } catch (ReflectiveOperationException e) {
                    Log.i(WindowInsetsCompat.TAG, "Could not retrieve WindowInsets.CONSUMED field", e);
                }
                sConsumedFieldFetched = true;
            }
            if (sConsumedField != null) {
                try {
                    WindowInsets windowInsets = (WindowInsets) sConsumedField.get(null);
                    if (windowInsets != null) {
                        return new WindowInsets(windowInsets);
                    }
                } catch (ReflectiveOperationException e2) {
                    Log.i(WindowInsetsCompat.TAG, "Could not get value from WindowInsets.CONSUMED field", e2);
                }
            }
            if (!sConstructorFetched) {
                try {
                    sConstructor = WindowInsets.class.getConstructor(Rect.class);
                } catch (ReflectiveOperationException e3) {
                    Log.i(WindowInsetsCompat.TAG, "Could not retrieve WindowInsets(Rect) constructor", e3);
                }
                sConstructorFetched = true;
            }
            if (sConstructor != null) {
                try {
                    return sConstructor.newInstance(new Rect());
                } catch (ReflectiveOperationException e4) {
                    Log.i(WindowInsetsCompat.TAG, "Could not invoke WindowInsets(Rect) constructor", e4);
                }
            }
            return null;
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void a(@NonNull Insets insets) {
            if (this.mInsets != null) {
                this.mInsets = this.mInsets.replaceSystemWindowInsets(insets.left, insets.top, insets.right, insets.bottom);
            }
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        @NonNull
        WindowInsetsCompat b() {
            a();
            WindowInsetsCompat windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(this.mInsets);
            windowInsetsCompat.a(this.a);
            windowInsetsCompat.a(this.mStableInsets);
            return windowInsetsCompat;
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void e(@Nullable Insets insets) {
            this.mStableInsets = insets;
        }
    }

    @RequiresApi(api = 29)
    private static class BuilderImpl29 extends BuilderImpl {
        final WindowInsets.Builder b;

        BuilderImpl29() {
            this.b = new WindowInsets.Builder();
        }

        BuilderImpl29(@NonNull WindowInsetsCompat windowInsetsCompat) {
            WindowInsets windowInsets = windowInsetsCompat.toWindowInsets();
            this.b = windowInsets != null ? new WindowInsets.Builder(windowInsets) : new WindowInsets.Builder();
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void a(@NonNull Insets insets) {
            this.b.setSystemWindowInsets(insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void a(@Nullable DisplayCutoutCompat displayCutoutCompat) {
            this.b.setDisplayCutout(displayCutoutCompat != null ? displayCutoutCompat.a() : null);
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        @NonNull
        WindowInsetsCompat b() {
            a();
            WindowInsetsCompat windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(this.b.build());
            windowInsetsCompat.a(this.a);
            return windowInsetsCompat;
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void b(@NonNull Insets insets) {
            this.b.setSystemGestureInsets(insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void c(@NonNull Insets insets) {
            this.b.setMandatorySystemGestureInsets(insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void d(@NonNull Insets insets) {
            this.b.setTappableElementInsets(insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void e(@NonNull Insets insets) {
            this.b.setStableInsets(insets.toPlatformInsets());
        }
    }

    @RequiresApi(30)
    private static class BuilderImpl30 extends BuilderImpl29 {
        BuilderImpl30() {
        }

        BuilderImpl30(@NonNull WindowInsetsCompat windowInsetsCompat) {
            super(windowInsetsCompat);
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void a(int i, @NonNull Insets insets) {
            this.b.setInsets(TypeImpl30.a(i), insets.toPlatformInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void a(int i, boolean z) {
            this.b.setVisible(TypeImpl30.a(i), z);
        }

        @Override // androidx.core.view.WindowInsetsCompat.BuilderImpl
        void b(int i, @NonNull Insets insets) {
            this.b.setInsetsIgnoringVisibility(TypeImpl30.a(i), insets.toPlatformInsets());
        }
    }

    private static class Impl {

        @NonNull
        static final WindowInsetsCompat a = new Builder().build().consumeDisplayCutout().consumeStableInsets().consumeSystemWindowInsets();
        final WindowInsetsCompat b;

        Impl(@NonNull WindowInsetsCompat windowInsetsCompat) {
            this.b = windowInsetsCompat;
        }

        @NonNull
        WindowInsetsCompat a(int i, int i2, int i3, int i4) {
            return a;
        }

        void a(@NonNull View view) {
        }

        void a(@NonNull Insets insets) {
        }

        void a(@Nullable WindowInsetsCompat windowInsetsCompat) {
        }

        boolean a() {
            return false;
        }

        void b(@NonNull WindowInsetsCompat windowInsetsCompat) {
        }

        boolean b() {
            return false;
        }

        @NonNull
        WindowInsetsCompat c() {
            return this.b;
        }

        @NonNull
        WindowInsetsCompat d() {
            return this.b;
        }

        @Nullable
        DisplayCutoutCompat e() {
            return null;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Impl)) {
                return false;
            }
            Impl impl = (Impl) obj;
            return a() == impl.a() && b() == impl.b() && ObjectsCompat.equals(g(), impl.g()) && ObjectsCompat.equals(h(), impl.h()) && ObjectsCompat.equals(e(), impl.e());
        }

        @NonNull
        WindowInsetsCompat f() {
            return this.b;
        }

        @NonNull
        Insets g() {
            return Insets.NONE;
        }

        @NonNull
        Insets getInsets(int i) {
            return Insets.NONE;
        }

        @NonNull
        Insets getInsetsIgnoringVisibility(int i) {
            if ((i & 8) == 0) {
                return Insets.NONE;
            }
            throw new IllegalArgumentException("Unable to query the maximum insets for IME");
        }

        @NonNull
        Insets h() {
            return Insets.NONE;
        }

        public int hashCode() {
            return ObjectsCompat.hash(Boolean.valueOf(a()), Boolean.valueOf(b()), g(), h(), e());
        }

        @NonNull
        Insets i() {
            return g();
        }

        boolean isVisible(int i) {
            return true;
        }

        @NonNull
        Insets j() {
            return g();
        }

        @NonNull
        Insets k() {
            return g();
        }

        public void setOverriddenInsets(Insets[] insetsArr) {
        }

        public void setStableInsets(Insets insets) {
        }
    }

    @RequiresApi(20)
    private static class Impl20 extends Impl {
        private static Class<?> sAttachInfoClass = null;
        private static Field sAttachInfoField = null;
        private static Method sGetViewRootImplMethod = null;
        private static Class<?> sViewRootImplClass = null;
        private static Field sVisibleInsetsField = null;
        private static boolean sVisibleRectReflectionFetched = false;

        @NonNull
        final WindowInsets c;
        Insets d;
        private Insets[] mOverriddenInsets;
        private WindowInsetsCompat mRootWindowInsets;
        private Insets mSystemWindowInsets;

        Impl20(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull WindowInsets windowInsets) {
            super(windowInsetsCompat);
            this.mSystemWindowInsets = null;
            this.c = windowInsets;
        }

        Impl20(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull Impl20 impl20) {
            this(windowInsetsCompat, new WindowInsets(impl20.c));
        }

        @NonNull
        @SuppressLint({"WrongConstant"})
        private Insets getInsets(int i, boolean z) {
            Insets insetsMax = Insets.NONE;
            for (int i2 = 1; i2 <= 256; i2 <<= 1) {
                if ((i & i2) != 0) {
                    insetsMax = Insets.max(insetsMax, a(i2, z));
                }
            }
            return insetsMax;
        }

        private Insets getRootStableInsets() {
            return this.mRootWindowInsets != null ? this.mRootWindowInsets.getStableInsets() : Insets.NONE;
        }

        @Nullable
        private Insets getVisibleInsets(@NonNull View view) {
            if (Build.VERSION.SDK_INT >= 30) {
                throw new UnsupportedOperationException("getVisibleInsets() should not be called on API >= 30. Use WindowInsets.isVisible() instead.");
            }
            if (!sVisibleRectReflectionFetched) {
                loadReflectionField();
            }
            if (sGetViewRootImplMethod != null && sAttachInfoClass != null && sVisibleInsetsField != null) {
                try {
                    Object objInvoke = sGetViewRootImplMethod.invoke(view, new Object[0]);
                    if (objInvoke == null) {
                        Log.w(WindowInsetsCompat.TAG, "Failed to get visible insets. getViewRootImpl() returned null from the provided view. This means that the view is either not attached or the method has been overridden", new NullPointerException());
                        return null;
                    }
                    Rect rect = (Rect) sVisibleInsetsField.get(sAttachInfoField.get(objInvoke));
                    if (rect != null) {
                        return Insets.of(rect);
                    }
                    return null;
                } catch (ReflectiveOperationException e) {
                    Log.e(WindowInsetsCompat.TAG, "Failed to get visible insets. (Reflection error). " + e.getMessage(), e);
                }
            }
            return null;
        }

        @SuppressLint({"PrivateApi"})
        private static void loadReflectionField() {
            try {
                sGetViewRootImplMethod = View.class.getDeclaredMethod("getViewRootImpl", new Class[0]);
                sViewRootImplClass = Class.forName("android.view.ViewRootImpl");
                sAttachInfoClass = Class.forName("android.view.View$AttachInfo");
                sVisibleInsetsField = sAttachInfoClass.getDeclaredField("mVisibleInsets");
                sAttachInfoField = sViewRootImplClass.getDeclaredField("mAttachInfo");
                sVisibleInsetsField.setAccessible(true);
                sAttachInfoField.setAccessible(true);
            } catch (ReflectiveOperationException e) {
                Log.e(WindowInsetsCompat.TAG, "Failed to get visible insets. (Reflection error). " + e.getMessage(), e);
            }
            sVisibleRectReflectionFetched = true;
        }

        @NonNull
        protected Insets a(int i, boolean z) {
            Insets stableInsets;
            if (i == 8) {
                stableInsets = this.mOverriddenInsets != null ? this.mOverriddenInsets[Type.a(8)] : null;
                if (stableInsets != null) {
                    return stableInsets;
                }
                Insets insetsG = g();
                Insets rootStableInsets = getRootStableInsets();
                return insetsG.bottom > rootStableInsets.bottom ? Insets.of(0, 0, 0, insetsG.bottom) : (this.d == null || this.d.equals(Insets.NONE) || this.d.bottom <= rootStableInsets.bottom) ? Insets.NONE : Insets.of(0, 0, 0, this.d.bottom);
            }
            if (i == 16) {
                return i();
            }
            if (i == 32) {
                return j();
            }
            if (i == 64) {
                return k();
            }
            if (i == 128) {
                DisplayCutoutCompat displayCutout = this.mRootWindowInsets != null ? this.mRootWindowInsets.getDisplayCutout() : e();
                return displayCutout != null ? Insets.of(displayCutout.getSafeInsetLeft(), displayCutout.getSafeInsetTop(), displayCutout.getSafeInsetRight(), displayCutout.getSafeInsetBottom()) : Insets.NONE;
            }
            switch (i) {
                case 1:
                    return z ? Insets.of(0, Math.max(getRootStableInsets().top, g().top), 0, 0) : Insets.of(0, g().top, 0, 0);
                case 2:
                    if (z) {
                        Insets rootStableInsets2 = getRootStableInsets();
                        Insets insetsH = h();
                        return Insets.of(Math.max(rootStableInsets2.left, insetsH.left), 0, Math.max(rootStableInsets2.right, insetsH.right), Math.max(rootStableInsets2.bottom, insetsH.bottom));
                    }
                    Insets insetsG2 = g();
                    stableInsets = this.mRootWindowInsets != null ? this.mRootWindowInsets.getStableInsets() : null;
                    int iMin = insetsG2.bottom;
                    if (stableInsets != null) {
                        iMin = Math.min(iMin, stableInsets.bottom);
                    }
                    return Insets.of(insetsG2.left, 0, insetsG2.right, iMin);
                default:
                    return Insets.NONE;
            }
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        WindowInsetsCompat a(int i, int i2, int i3, int i4) {
            Builder builder = new Builder(WindowInsetsCompat.toWindowInsetsCompat(this.c));
            builder.setSystemWindowInsets(WindowInsetsCompat.a(g(), i, i2, i3, i4));
            builder.setStableInsets(WindowInsetsCompat.a(h(), i, i2, i3, i4));
            return builder.build();
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        void a(@NonNull View view) {
            Insets visibleInsets = getVisibleInsets(view);
            if (visibleInsets == null) {
                visibleInsets = Insets.NONE;
            }
            a(visibleInsets);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        void a(@NonNull Insets insets) {
            this.d = insets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        void a(@Nullable WindowInsetsCompat windowInsetsCompat) {
            this.mRootWindowInsets = windowInsetsCompat;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        boolean a() {
            return this.c.isRound();
        }

        protected boolean a(int i) {
            if (i == 4) {
                return false;
            }
            if (i != 8 && i != 128) {
                switch (i) {
                    case 1:
                    case 2:
                        break;
                    default:
                        return true;
                }
            }
            return !a(i, false).equals(Insets.NONE);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        void b(@NonNull WindowInsetsCompat windowInsetsCompat) {
            windowInsetsCompat.a(this.mRootWindowInsets);
            windowInsetsCompat.b(this.d);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public boolean equals(Object obj) {
            if (super.equals(obj)) {
                return Objects.equals(this.d, ((Impl20) obj).d);
            }
            return false;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        final Insets g() {
            if (this.mSystemWindowInsets == null) {
                this.mSystemWindowInsets = Insets.of(this.c.getSystemWindowInsetLeft(), this.c.getSystemWindowInsetTop(), this.c.getSystemWindowInsetRight(), this.c.getSystemWindowInsetBottom());
            }
            return this.mSystemWindowInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        public Insets getInsets(int i) {
            return getInsets(i, false);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        public Insets getInsetsIgnoringVisibility(int i) {
            return getInsets(i, true);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @SuppressLint({"WrongConstant"})
        boolean isVisible(int i) {
            for (int i2 = 1; i2 <= 256; i2 <<= 1) {
                if ((i & i2) != 0 && !a(i2)) {
                    return false;
                }
            }
            return true;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public void setOverriddenInsets(Insets[] insetsArr) {
            this.mOverriddenInsets = insetsArr;
        }
    }

    @RequiresApi(21)
    private static class Impl21 extends Impl20 {
        private Insets mStableInsets;

        Impl21(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull WindowInsets windowInsets) {
            super(windowInsetsCompat, windowInsets);
            this.mStableInsets = null;
        }

        Impl21(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull Impl21 impl21) {
            super(windowInsetsCompat, impl21);
            this.mStableInsets = null;
            this.mStableInsets = impl21.mStableInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        boolean b() {
            return this.c.isConsumed();
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        WindowInsetsCompat c() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.c.consumeSystemWindowInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        WindowInsetsCompat d() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.c.consumeStableInsets());
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        final Insets h() {
            if (this.mStableInsets == null) {
                this.mStableInsets = Insets.of(this.c.getStableInsetLeft(), this.c.getStableInsetTop(), this.c.getStableInsetRight(), this.c.getStableInsetBottom());
            }
            return this.mStableInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public void setStableInsets(@Nullable Insets insets) {
            this.mStableInsets = insets;
        }
    }

    @RequiresApi(28)
    private static class Impl28 extends Impl21 {
        Impl28(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull WindowInsets windowInsets) {
            super(windowInsetsCompat, windowInsets);
        }

        Impl28(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull Impl28 impl28) {
            super(windowInsetsCompat, impl28);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @Nullable
        DisplayCutoutCompat e() {
            return DisplayCutoutCompat.a(this.c.getDisplayCutout());
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Impl28)) {
                return false;
            }
            Impl28 impl28 = (Impl28) obj;
            return Objects.equals(this.c, impl28.c) && Objects.equals(this.d, impl28.d);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        WindowInsetsCompat f() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.c.consumeDisplayCutout());
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        public int hashCode() {
            return this.c.hashCode();
        }
    }

    @RequiresApi(29)
    private static class Impl29 extends Impl28 {
        private Insets mMandatorySystemGestureInsets;
        private Insets mSystemGestureInsets;
        private Insets mTappableElementInsets;

        Impl29(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull WindowInsets windowInsets) {
            super(windowInsetsCompat, windowInsets);
            this.mSystemGestureInsets = null;
            this.mMandatorySystemGestureInsets = null;
            this.mTappableElementInsets = null;
        }

        Impl29(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull Impl29 impl29) {
            super(windowInsetsCompat, impl29);
            this.mSystemGestureInsets = null;
            this.mMandatorySystemGestureInsets = null;
            this.mTappableElementInsets = null;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        WindowInsetsCompat a(int i, int i2, int i3, int i4) {
            return WindowInsetsCompat.toWindowInsetsCompat(this.c.inset(i, i2, i3, i4));
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        Insets i() {
            if (this.mSystemGestureInsets == null) {
                this.mSystemGestureInsets = Insets.toCompatInsets(this.c.getSystemGestureInsets());
            }
            return this.mSystemGestureInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        Insets j() {
            if (this.mMandatorySystemGestureInsets == null) {
                this.mMandatorySystemGestureInsets = Insets.toCompatInsets(this.c.getMandatorySystemGestureInsets());
            }
            return this.mMandatorySystemGestureInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        Insets k() {
            if (this.mTappableElementInsets == null) {
                this.mTappableElementInsets = Insets.toCompatInsets(this.c.getTappableElementInsets());
            }
            return this.mTappableElementInsets;
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl21, androidx.core.view.WindowInsetsCompat.Impl
        public void setStableInsets(@Nullable Insets insets) {
        }
    }

    @RequiresApi(30)
    private static class Impl30 extends Impl29 {

        @NonNull
        static final WindowInsetsCompat e = WindowInsetsCompat.toWindowInsetsCompat(WindowInsets.CONSUMED);

        Impl30(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull WindowInsets windowInsets) {
            super(windowInsetsCompat, windowInsets);
        }

        Impl30(@NonNull WindowInsetsCompat windowInsetsCompat, @NonNull Impl30 impl30) {
            super(windowInsetsCompat, impl30);
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        final void a(@NonNull View view) {
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        public Insets getInsets(int i) {
            return Insets.toCompatInsets(this.c.getInsets(TypeImpl30.a(i)));
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        @NonNull
        public Insets getInsetsIgnoringVisibility(int i) {
            return Insets.toCompatInsets(this.c.getInsetsIgnoringVisibility(TypeImpl30.a(i)));
        }

        @Override // androidx.core.view.WindowInsetsCompat.Impl20, androidx.core.view.WindowInsetsCompat.Impl
        public boolean isVisible(int i) {
            return this.c.isVisible(TypeImpl30.a(i));
        }
    }

    public static final class Type {

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public @interface InsetsType {
        }

        private Type() {
        }

        @SuppressLint({"WrongConstant"})
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        static int a() {
            return -1;
        }

        static int a(int i) {
            if (i == 4) {
                return 2;
            }
            if (i == 8) {
                return 3;
            }
            if (i == 16) {
                return 4;
            }
            if (i == 32) {
                return 5;
            }
            if (i == 64) {
                return 6;
            }
            if (i == 128) {
                return 7;
            }
            if (i == 256) {
                return 8;
            }
            switch (i) {
                case 1:
                    return 0;
                case 2:
                    return 1;
                default:
                    throw new IllegalArgumentException("type needs to be >= FIRST and <= LAST, type=" + i);
            }
        }

        public static int captionBar() {
            return 4;
        }

        public static int displayCutout() {
            return 128;
        }

        public static int ime() {
            return 8;
        }

        public static int mandatorySystemGestures() {
            return 32;
        }

        public static int navigationBars() {
            return 2;
        }

        public static int statusBars() {
            return 1;
        }

        public static int systemBars() {
            return 7;
        }

        public static int systemGestures() {
            return 16;
        }

        public static int tappableElement() {
            return 64;
        }
    }

    @RequiresApi(30)
    private static final class TypeImpl30 {
        private TypeImpl30() {
        }

        static int a(int i) {
            int iCaptionBar;
            int i2 = 0;
            for (int i3 = 1; i3 <= 256; i3 <<= 1) {
                if ((i & i3) != 0) {
                    if (i3 == 4) {
                        iCaptionBar = WindowInsets.Type.captionBar();
                    } else if (i3 == 8) {
                        iCaptionBar = WindowInsets.Type.ime();
                    } else if (i3 == 16) {
                        iCaptionBar = WindowInsets.Type.systemGestures();
                    } else if (i3 == 32) {
                        iCaptionBar = WindowInsets.Type.mandatorySystemGestures();
                    } else if (i3 == 64) {
                        iCaptionBar = WindowInsets.Type.tappableElement();
                    } else if (i3 != 128) {
                        switch (i3) {
                            case 1:
                                iCaptionBar = WindowInsets.Type.statusBars();
                                break;
                            case 2:
                                iCaptionBar = WindowInsets.Type.navigationBars();
                                break;
                        }
                    } else {
                        iCaptionBar = WindowInsets.Type.displayCutout();
                    }
                    i2 |= iCaptionBar;
                }
            }
            return i2;
        }
    }

    static {
        CONSUMED = Build.VERSION.SDK_INT >= 30 ? Impl30.e : Impl.a;
    }

    @RequiresApi(20)
    private WindowInsetsCompat(@NonNull WindowInsets windowInsets) {
        Impl impl20;
        if (Build.VERSION.SDK_INT >= 30) {
            impl20 = new Impl30(this, windowInsets);
        } else if (Build.VERSION.SDK_INT >= 29) {
            impl20 = new Impl29(this, windowInsets);
        } else if (Build.VERSION.SDK_INT >= 28) {
            impl20 = new Impl28(this, windowInsets);
        } else if (Build.VERSION.SDK_INT >= 21) {
            impl20 = new Impl21(this, windowInsets);
        } else {
            if (Build.VERSION.SDK_INT < 20) {
                this.mImpl = new Impl(this);
                return;
            }
            impl20 = new Impl20(this, windowInsets);
        }
        this.mImpl = impl20;
    }

    public WindowInsetsCompat(@Nullable WindowInsetsCompat windowInsetsCompat) {
        if (windowInsetsCompat == null) {
            this.mImpl = new Impl(this);
            return;
        }
        Impl impl = windowInsetsCompat.mImpl;
        this.mImpl = (Build.VERSION.SDK_INT < 30 || !(impl instanceof Impl30)) ? (Build.VERSION.SDK_INT < 29 || !(impl instanceof Impl29)) ? (Build.VERSION.SDK_INT < 28 || !(impl instanceof Impl28)) ? (Build.VERSION.SDK_INT < 21 || !(impl instanceof Impl21)) ? (Build.VERSION.SDK_INT < 20 || !(impl instanceof Impl20)) ? new Impl(this) : new Impl20(this, (Impl20) impl) : new Impl21(this, (Impl21) impl) : new Impl28(this, (Impl28) impl) : new Impl29(this, (Impl29) impl) : new Impl30(this, (Impl30) impl);
        impl.b(this);
    }

    static Insets a(@NonNull Insets insets, int i, int i2, int i3, int i4) {
        int iMax = Math.max(0, insets.left - i);
        int iMax2 = Math.max(0, insets.top - i2);
        int iMax3 = Math.max(0, insets.right - i3);
        int iMax4 = Math.max(0, insets.bottom - i4);
        return (iMax == i && iMax2 == i2 && iMax3 == i3 && iMax4 == i4) ? insets : Insets.of(iMax, iMax2, iMax3, iMax4);
    }

    @NonNull
    @RequiresApi(20)
    public static WindowInsetsCompat toWindowInsetsCompat(@NonNull WindowInsets windowInsets) {
        return toWindowInsetsCompat(windowInsets, null);
    }

    @NonNull
    @RequiresApi(20)
    public static WindowInsetsCompat toWindowInsetsCompat(@NonNull WindowInsets windowInsets, @Nullable View view) {
        WindowInsetsCompat windowInsetsCompat = new WindowInsetsCompat((WindowInsets) Preconditions.checkNotNull(windowInsets));
        if (view != null && view.isAttachedToWindow()) {
            windowInsetsCompat.a(ViewCompat.getRootWindowInsets(view));
            windowInsetsCompat.a(view.getRootView());
        }
        return windowInsetsCompat;
    }

    void a(@NonNull View view) {
        this.mImpl.a(view);
    }

    void a(@Nullable Insets insets) {
        this.mImpl.setStableInsets(insets);
    }

    void a(@Nullable WindowInsetsCompat windowInsetsCompat) {
        this.mImpl.a(windowInsetsCompat);
    }

    void a(Insets[] insetsArr) {
        this.mImpl.setOverriddenInsets(insetsArr);
    }

    void b(@NonNull Insets insets) {
        this.mImpl.a(insets);
    }

    @NonNull
    @Deprecated
    public WindowInsetsCompat consumeDisplayCutout() {
        return this.mImpl.f();
    }

    @NonNull
    @Deprecated
    public WindowInsetsCompat consumeStableInsets() {
        return this.mImpl.d();
    }

    @NonNull
    @Deprecated
    public WindowInsetsCompat consumeSystemWindowInsets() {
        return this.mImpl.c();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof WindowInsetsCompat) {
            return ObjectsCompat.equals(this.mImpl, ((WindowInsetsCompat) obj).mImpl);
        }
        return false;
    }

    @Nullable
    public DisplayCutoutCompat getDisplayCutout() {
        return this.mImpl.e();
    }

    @NonNull
    public Insets getInsets(int i) {
        return this.mImpl.getInsets(i);
    }

    @NonNull
    public Insets getInsetsIgnoringVisibility(int i) {
        return this.mImpl.getInsetsIgnoringVisibility(i);
    }

    @NonNull
    @Deprecated
    public Insets getMandatorySystemGestureInsets() {
        return this.mImpl.j();
    }

    @Deprecated
    public int getStableInsetBottom() {
        return this.mImpl.h().bottom;
    }

    @Deprecated
    public int getStableInsetLeft() {
        return this.mImpl.h().left;
    }

    @Deprecated
    public int getStableInsetRight() {
        return this.mImpl.h().right;
    }

    @Deprecated
    public int getStableInsetTop() {
        return this.mImpl.h().top;
    }

    @NonNull
    @Deprecated
    public Insets getStableInsets() {
        return this.mImpl.h();
    }

    @NonNull
    @Deprecated
    public Insets getSystemGestureInsets() {
        return this.mImpl.i();
    }

    @Deprecated
    public int getSystemWindowInsetBottom() {
        return this.mImpl.g().bottom;
    }

    @Deprecated
    public int getSystemWindowInsetLeft() {
        return this.mImpl.g().left;
    }

    @Deprecated
    public int getSystemWindowInsetRight() {
        return this.mImpl.g().right;
    }

    @Deprecated
    public int getSystemWindowInsetTop() {
        return this.mImpl.g().top;
    }

    @NonNull
    @Deprecated
    public Insets getSystemWindowInsets() {
        return this.mImpl.g();
    }

    @NonNull
    @Deprecated
    public Insets getTappableElementInsets() {
        return this.mImpl.k();
    }

    public boolean hasInsets() {
        return (getInsets(Type.a()).equals(Insets.NONE) && getInsetsIgnoringVisibility(Type.a() ^ Type.ime()).equals(Insets.NONE) && getDisplayCutout() == null) ? false : true;
    }

    @Deprecated
    public boolean hasStableInsets() {
        return !this.mImpl.h().equals(Insets.NONE);
    }

    @Deprecated
    public boolean hasSystemWindowInsets() {
        return !this.mImpl.g().equals(Insets.NONE);
    }

    public int hashCode() {
        if (this.mImpl == null) {
            return 0;
        }
        return this.mImpl.hashCode();
    }

    @NonNull
    public WindowInsetsCompat inset(@IntRange(from = 0) int i, @IntRange(from = 0) int i2, @IntRange(from = 0) int i3, @IntRange(from = 0) int i4) {
        return this.mImpl.a(i, i2, i3, i4);
    }

    @NonNull
    public WindowInsetsCompat inset(@NonNull Insets insets) {
        return inset(insets.left, insets.top, insets.right, insets.bottom);
    }

    public boolean isConsumed() {
        return this.mImpl.b();
    }

    public boolean isRound() {
        return this.mImpl.a();
    }

    public boolean isVisible(int i) {
        return this.mImpl.isVisible(i);
    }

    @NonNull
    @Deprecated
    public WindowInsetsCompat replaceSystemWindowInsets(int i, int i2, int i3, int i4) {
        return new Builder(this).setSystemWindowInsets(Insets.of(i, i2, i3, i4)).build();
    }

    @NonNull
    @Deprecated
    public WindowInsetsCompat replaceSystemWindowInsets(@NonNull Rect rect) {
        return new Builder(this).setSystemWindowInsets(Insets.of(rect)).build();
    }

    @Nullable
    @RequiresApi(20)
    public WindowInsets toWindowInsets() {
        if (this.mImpl instanceof Impl20) {
            return ((Impl20) this.mImpl).c;
        }
        return null;
    }
}
