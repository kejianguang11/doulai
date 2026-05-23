package androidx.appcompat.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemClock;
import android.util.SparseArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.drawable.DrawableCompat;
import com.alibaba.fastjson.asm.Opcodes;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
class DrawableContainer extends Drawable implements Drawable.Callback {
    private static final boolean DEBUG = false;
    private static final boolean DEFAULT_DITHER = true;
    private static final String TAG = "DrawableContainer";
    private Runnable mAnimationRunnable;
    private BlockInvalidateCallback mBlockInvalidateCallback;
    private Drawable mCurrDrawable;
    private DrawableContainerState mDrawableContainerState;
    private long mEnterAnimationEnd;
    private long mExitAnimationEnd;
    private boolean mHasAlpha;
    private Rect mHotspotBounds;
    private Drawable mLastDrawable;
    private boolean mMutated;
    private int mAlpha = 255;
    private int mCurIndex = -1;

    @RequiresApi(21)
    private static class Api21Impl {
        private Api21Impl() {
        }

        public static boolean canApplyTheme(Drawable.ConstantState constantState) {
            return constantState.canApplyTheme();
        }

        public static void getOutline(Drawable drawable, Outline outline) {
            drawable.getOutline(outline);
        }

        public static Resources getResources(Resources.Theme theme) {
            return theme.getResources();
        }
    }

    static class BlockInvalidateCallback implements Drawable.Callback {
        private Drawable.Callback mCallback;

        BlockInvalidateCallback() {
        }

        @Override // android.graphics.drawable.Drawable.Callback
        public void invalidateDrawable(@NonNull Drawable drawable) {
        }

        @Override // android.graphics.drawable.Drawable.Callback
        public void scheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable, long j) {
            if (this.mCallback != null) {
                this.mCallback.scheduleDrawable(drawable, runnable, j);
            }
        }

        @Override // android.graphics.drawable.Drawable.Callback
        public void unscheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable) {
            if (this.mCallback != null) {
                this.mCallback.unscheduleDrawable(drawable, runnable);
            }
        }

        public Drawable.Callback unwrap() {
            Drawable.Callback callback = this.mCallback;
            this.mCallback = null;
            return callback;
        }

        public BlockInvalidateCallback wrap(Drawable.Callback callback) {
            this.mCallback = callback;
            return this;
        }
    }

    static abstract class DrawableContainerState extends Drawable.ConstantState {
        boolean A;
        int B;
        int C;
        int D;
        boolean E;
        ColorFilter F;
        boolean G;
        ColorStateList H;
        PorterDuff.Mode I;
        boolean J;
        boolean K;
        final DrawableContainer c;
        Resources d;
        int e;
        int f;
        int g;
        SparseArray<Drawable.ConstantState> h;
        Drawable[] i;
        int j;
        boolean k;
        boolean l;
        Rect m;
        boolean n;
        boolean o;
        int p;
        int q;
        int r;
        int s;
        boolean t;
        int u;
        boolean v;
        boolean w;
        boolean x;
        boolean y;
        boolean z;

        DrawableContainerState(DrawableContainerState drawableContainerState, DrawableContainer drawableContainer, Resources resources) {
            this.k = false;
            this.n = false;
            this.z = true;
            this.C = 0;
            this.D = 0;
            this.c = drawableContainer;
            this.d = resources != null ? resources : drawableContainerState != null ? drawableContainerState.d : null;
            this.e = DrawableContainer.a(resources, drawableContainerState != null ? drawableContainerState.e : 0);
            if (drawableContainerState == null) {
                this.i = new Drawable[10];
                this.j = 0;
                return;
            }
            this.f = drawableContainerState.f;
            this.g = drawableContainerState.g;
            this.x = true;
            this.y = true;
            this.k = drawableContainerState.k;
            this.n = drawableContainerState.n;
            this.z = drawableContainerState.z;
            this.A = drawableContainerState.A;
            this.B = drawableContainerState.B;
            this.C = drawableContainerState.C;
            this.D = drawableContainerState.D;
            this.E = drawableContainerState.E;
            this.F = drawableContainerState.F;
            this.G = drawableContainerState.G;
            this.H = drawableContainerState.H;
            this.I = drawableContainerState.I;
            this.J = drawableContainerState.J;
            this.K = drawableContainerState.K;
            if (drawableContainerState.e == this.e) {
                if (drawableContainerState.l) {
                    this.m = drawableContainerState.m != null ? new Rect(drawableContainerState.m) : null;
                    this.l = true;
                }
                if (drawableContainerState.o) {
                    this.p = drawableContainerState.p;
                    this.q = drawableContainerState.q;
                    this.r = drawableContainerState.r;
                    this.s = drawableContainerState.s;
                    this.o = true;
                }
            }
            if (drawableContainerState.t) {
                this.u = drawableContainerState.u;
                this.t = true;
            }
            if (drawableContainerState.v) {
                this.w = drawableContainerState.w;
                this.v = true;
            }
            Drawable[] drawableArr = drawableContainerState.i;
            this.i = new Drawable[drawableArr.length];
            this.j = drawableContainerState.j;
            SparseArray<Drawable.ConstantState> sparseArray = drawableContainerState.h;
            this.h = sparseArray != null ? sparseArray.clone() : new SparseArray<>(this.j);
            int i = this.j;
            for (int i2 = 0; i2 < i; i2++) {
                if (drawableArr[i2] != null) {
                    Drawable.ConstantState constantState = drawableArr[i2].getConstantState();
                    if (constantState != null) {
                        this.h.put(i2, constantState);
                    } else {
                        this.i[i2] = drawableArr[i2];
                    }
                }
            }
        }

        private void createAllFutures() {
            if (this.h != null) {
                int size = this.h.size();
                for (int i = 0; i < size; i++) {
                    this.i[this.h.keyAt(i)] = prepareDrawable(this.h.valueAt(i).newDrawable(this.d));
                }
                this.h = null;
            }
        }

        private Drawable prepareDrawable(Drawable drawable) {
            if (Build.VERSION.SDK_INT >= 23) {
                DrawableCompat.setLayoutDirection(drawable, this.B);
            }
            Drawable drawableMutate = drawable.mutate();
            drawableMutate.setCallback(this.c);
            return drawableMutate;
        }

        void a() {
            int i = this.j;
            Drawable[] drawableArr = this.i;
            for (int i2 = 0; i2 < i; i2++) {
                if (drawableArr[i2] != null) {
                    drawableArr[i2].mutate();
                }
            }
            this.A = true;
        }

        @RequiresApi(21)
        final void a(Resources.Theme theme) {
            if (theme != null) {
                createAllFutures();
                int i = this.j;
                Drawable[] drawableArr = this.i;
                for (int i2 = 0; i2 < i; i2++) {
                    if (drawableArr[i2] != null && DrawableCompat.canApplyTheme(drawableArr[i2])) {
                        DrawableCompat.applyTheme(drawableArr[i2], theme);
                        this.g |= drawableArr[i2].getChangingConfigurations();
                    }
                }
                a(Api21Impl.getResources(theme));
            }
        }

        final void a(Resources resources) {
            if (resources != null) {
                this.d = resources;
                int iA = DrawableContainer.a(resources, this.e);
                int i = this.e;
                this.e = iA;
                if (i != iA) {
                    this.o = false;
                    this.l = false;
                }
            }
        }

        public final int addChild(Drawable drawable) {
            int i = this.j;
            if (i >= this.i.length) {
                growArray(i, i + 10);
            }
            drawable.mutate();
            drawable.setVisible(false, true);
            drawable.setCallback(this.c);
            this.i[i] = drawable;
            this.j++;
            this.g = drawable.getChangingConfigurations() | this.g;
            b();
            this.m = null;
            this.l = false;
            this.o = false;
            this.x = false;
            return i;
        }

        void b() {
            this.t = false;
            this.v = false;
        }

        final int c() {
            return this.i.length;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @RequiresApi(21)
        public boolean canApplyTheme() {
            int i = this.j;
            Drawable[] drawableArr = this.i;
            for (int i2 = 0; i2 < i; i2++) {
                Drawable drawable = drawableArr[i2];
                if (drawable == null) {
                    Drawable.ConstantState constantState = this.h.get(i2);
                    if (constantState != null && Api21Impl.canApplyTheme(constantState)) {
                        return true;
                    }
                } else if (DrawableCompat.canApplyTheme(drawable)) {
                    return true;
                }
            }
            return false;
        }

        public boolean canConstantState() {
            if (this.x) {
                return this.y;
            }
            createAllFutures();
            this.x = true;
            int i = this.j;
            Drawable[] drawableArr = this.i;
            for (int i2 = 0; i2 < i; i2++) {
                if (drawableArr[i2].getConstantState() == null) {
                    this.y = false;
                    return false;
                }
            }
            this.y = true;
            return true;
        }

        protected void d() {
            this.o = true;
            createAllFutures();
            int i = this.j;
            Drawable[] drawableArr = this.i;
            this.q = -1;
            this.p = -1;
            this.s = 0;
            this.r = 0;
            for (int i2 = 0; i2 < i; i2++) {
                Drawable drawable = drawableArr[i2];
                int intrinsicWidth = drawable.getIntrinsicWidth();
                if (intrinsicWidth > this.p) {
                    this.p = intrinsicWidth;
                }
                int intrinsicHeight = drawable.getIntrinsicHeight();
                if (intrinsicHeight > this.q) {
                    this.q = intrinsicHeight;
                }
                int minimumWidth = drawable.getMinimumWidth();
                if (minimumWidth > this.r) {
                    this.r = minimumWidth;
                }
                int minimumHeight = drawable.getMinimumHeight();
                if (minimumHeight > this.s) {
                    this.s = minimumHeight;
                }
            }
        }

        final boolean d(int i, int i2) {
            int i3 = this.j;
            Drawable[] drawableArr = this.i;
            boolean z = false;
            for (int i4 = 0; i4 < i3; i4++) {
                if (drawableArr[i4] != null) {
                    boolean layoutDirection = Build.VERSION.SDK_INT >= 23 ? DrawableCompat.setLayoutDirection(drawableArr[i4], i) : false;
                    if (i4 == i2) {
                        z = layoutDirection;
                    }
                }
            }
            this.B = i;
            return z;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.g | this.f;
        }

        public final Drawable getChild(int i) {
            int iIndexOfKey;
            Drawable drawable = this.i[i];
            if (drawable != null) {
                return drawable;
            }
            if (this.h == null || (iIndexOfKey = this.h.indexOfKey(i)) < 0) {
                return null;
            }
            Drawable drawablePrepareDrawable = prepareDrawable(this.h.valueAt(iIndexOfKey).newDrawable(this.d));
            this.i[i] = drawablePrepareDrawable;
            this.h.removeAt(iIndexOfKey);
            if (this.h.size() == 0) {
                this.h = null;
            }
            return drawablePrepareDrawable;
        }

        public final int getChildCount() {
            return this.j;
        }

        public final int getConstantHeight() {
            if (!this.o) {
                d();
            }
            return this.q;
        }

        public final int getConstantMinimumHeight() {
            if (!this.o) {
                d();
            }
            return this.s;
        }

        public final int getConstantMinimumWidth() {
            if (!this.o) {
                d();
            }
            return this.r;
        }

        public final Rect getConstantPadding() {
            if (this.k) {
                return null;
            }
            if (this.m != null || this.l) {
                return this.m;
            }
            createAllFutures();
            Rect rect = new Rect();
            int i = this.j;
            Drawable[] drawableArr = this.i;
            Rect rect2 = null;
            for (int i2 = 0; i2 < i; i2++) {
                if (drawableArr[i2].getPadding(rect)) {
                    if (rect2 == null) {
                        rect2 = new Rect(0, 0, 0, 0);
                    }
                    if (rect.left > rect2.left) {
                        rect2.left = rect.left;
                    }
                    if (rect.top > rect2.top) {
                        rect2.top = rect.top;
                    }
                    if (rect.right > rect2.right) {
                        rect2.right = rect.right;
                    }
                    if (rect.bottom > rect2.bottom) {
                        rect2.bottom = rect.bottom;
                    }
                }
            }
            this.l = true;
            this.m = rect2;
            return rect2;
        }

        public final int getConstantWidth() {
            if (!this.o) {
                d();
            }
            return this.p;
        }

        public final int getEnterFadeDuration() {
            return this.C;
        }

        public final int getExitFadeDuration() {
            return this.D;
        }

        public final int getOpacity() {
            if (this.t) {
                return this.u;
            }
            createAllFutures();
            int i = this.j;
            Drawable[] drawableArr = this.i;
            int opacity = i > 0 ? drawableArr[0].getOpacity() : -2;
            for (int i2 = 1; i2 < i; i2++) {
                opacity = Drawable.resolveOpacity(opacity, drawableArr[i2].getOpacity());
            }
            this.u = opacity;
            this.t = true;
            return opacity;
        }

        public void growArray(int i, int i2) {
            Drawable[] drawableArr = new Drawable[i2];
            if (this.i != null) {
                System.arraycopy(this.i, 0, drawableArr, 0, i);
            }
            this.i = drawableArr;
        }

        public final boolean isConstantSize() {
            return this.n;
        }

        public final boolean isStateful() {
            if (this.v) {
                return this.w;
            }
            createAllFutures();
            int i = this.j;
            Drawable[] drawableArr = this.i;
            boolean z = false;
            int i2 = 0;
            while (true) {
                if (i2 >= i) {
                    break;
                }
                if (drawableArr[i2].isStateful()) {
                    z = true;
                    break;
                }
                i2++;
            }
            this.w = z;
            this.v = true;
            return z;
        }

        public final void setConstantSize(boolean z) {
            this.n = z;
        }

        public final void setEnterFadeDuration(int i) {
            this.C = i;
        }

        public final void setExitFadeDuration(int i) {
            this.D = i;
        }

        public final void setVariablePadding(boolean z) {
            this.k = z;
        }
    }

    DrawableContainer() {
    }

    static int a(@Nullable Resources resources, int i) {
        if (resources != null) {
            i = resources.getDisplayMetrics().densityDpi;
        }
        return i == 0 ? Opcodes.IF_ICMPNE : i;
    }

    private void initializeDrawableForDisplay(Drawable drawable) {
        if (this.mBlockInvalidateCallback == null) {
            this.mBlockInvalidateCallback = new BlockInvalidateCallback();
        }
        drawable.setCallback(this.mBlockInvalidateCallback.wrap(drawable.getCallback()));
        try {
            if (this.mDrawableContainerState.C <= 0 && this.mHasAlpha) {
                drawable.setAlpha(this.mAlpha);
            }
            if (this.mDrawableContainerState.G) {
                drawable.setColorFilter(this.mDrawableContainerState.F);
            } else {
                if (this.mDrawableContainerState.J) {
                    DrawableCompat.setTintList(drawable, this.mDrawableContainerState.H);
                }
                if (this.mDrawableContainerState.K) {
                    DrawableCompat.setTintMode(drawable, this.mDrawableContainerState.I);
                }
            }
            drawable.setVisible(isVisible(), true);
            drawable.setDither(this.mDrawableContainerState.z);
            drawable.setState(getState());
            drawable.setLevel(getLevel());
            drawable.setBounds(getBounds());
            if (Build.VERSION.SDK_INT >= 23) {
                DrawableCompat.setLayoutDirection(drawable, DrawableCompat.getLayoutDirection(this));
            }
            if (Build.VERSION.SDK_INT >= 19) {
                DrawableCompat.setAutoMirrored(drawable, this.mDrawableContainerState.E);
            }
            Rect rect = this.mHotspotBounds;
            if (Build.VERSION.SDK_INT >= 21 && rect != null) {
                DrawableCompat.setHotspotBounds(drawable, rect.left, rect.top, rect.right, rect.bottom);
            }
        } finally {
            drawable.setCallback(this.mBlockInvalidateCallback.unwrap());
        }
    }

    private boolean needsMirroring() {
        return isAutoMirrored() && DrawableCompat.getLayoutDirection(this) == 1;
    }

    final void a(Resources resources) {
        this.mDrawableContainerState.a(resources);
    }

    void a(DrawableContainerState drawableContainerState) {
        this.mDrawableContainerState = drawableContainerState;
        if (this.mCurIndex >= 0) {
            this.mCurrDrawable = drawableContainerState.getChild(this.mCurIndex);
            if (this.mCurrDrawable != null) {
                initializeDrawableForDisplay(this.mCurrDrawable);
            }
        }
        this.mLastDrawable = null;
    }

    void a(boolean z) {
        boolean z2;
        boolean z3 = true;
        this.mHasAlpha = true;
        long jUptimeMillis = SystemClock.uptimeMillis();
        if (this.mCurrDrawable == null) {
            this.mEnterAnimationEnd = 0L;
            z2 = false;
        } else if (this.mEnterAnimationEnd == 0) {
            z2 = false;
        } else if (this.mEnterAnimationEnd <= jUptimeMillis) {
            this.mCurrDrawable.setAlpha(this.mAlpha);
            this.mEnterAnimationEnd = 0L;
            z2 = false;
        } else {
            this.mCurrDrawable.setAlpha(((255 - (((int) ((this.mEnterAnimationEnd - jUptimeMillis) * 255)) / this.mDrawableContainerState.C)) * this.mAlpha) / 255);
            z2 = true;
        }
        if (this.mLastDrawable == null) {
            this.mExitAnimationEnd = 0L;
            z3 = z2;
        } else if (this.mExitAnimationEnd == 0) {
            z3 = z2;
        } else if (this.mExitAnimationEnd <= jUptimeMillis) {
            this.mLastDrawable.setVisible(false, false);
            this.mLastDrawable = null;
            this.mExitAnimationEnd = 0L;
            z3 = z2;
        } else {
            this.mLastDrawable.setAlpha(((((int) ((this.mExitAnimationEnd - jUptimeMillis) * 255)) / this.mDrawableContainerState.D) * this.mAlpha) / 255);
        }
        if (z && z3) {
            scheduleSelf(this.mAnimationRunnable, jUptimeMillis + 16);
        }
    }

    boolean a(int i) {
        if (i == this.mCurIndex) {
            return false;
        }
        long jUptimeMillis = SystemClock.uptimeMillis();
        if (this.mDrawableContainerState.D > 0) {
            if (this.mLastDrawable != null) {
                this.mLastDrawable.setVisible(false, false);
            }
            if (this.mCurrDrawable != null) {
                this.mLastDrawable = this.mCurrDrawable;
                this.mExitAnimationEnd = ((long) this.mDrawableContainerState.D) + jUptimeMillis;
            } else {
                this.mLastDrawable = null;
                this.mExitAnimationEnd = 0L;
            }
        } else if (this.mCurrDrawable != null) {
            this.mCurrDrawable.setVisible(false, false);
        }
        if (i < 0 || i >= this.mDrawableContainerState.j) {
            this.mCurrDrawable = null;
            this.mCurIndex = -1;
        } else {
            Drawable child = this.mDrawableContainerState.getChild(i);
            this.mCurrDrawable = child;
            this.mCurIndex = i;
            if (child != null) {
                if (this.mDrawableContainerState.C > 0) {
                    this.mEnterAnimationEnd = jUptimeMillis + ((long) this.mDrawableContainerState.C);
                }
                initializeDrawableForDisplay(child);
            }
        }
        if (this.mEnterAnimationEnd != 0 || this.mExitAnimationEnd != 0) {
            if (this.mAnimationRunnable == null) {
                this.mAnimationRunnable = new Runnable() { // from class: androidx.appcompat.graphics.drawable.DrawableContainer.1
                    @Override // java.lang.Runnable
                    public void run() {
                        DrawableContainer.this.a(true);
                        DrawableContainer.this.invalidateSelf();
                    }
                };
            } else {
                unscheduleSelf(this.mAnimationRunnable);
            }
            a(true);
        }
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    @RequiresApi(21)
    public void applyTheme(@NonNull Resources.Theme theme) {
        this.mDrawableContainerState.a(theme);
    }

    DrawableContainerState c() {
        return this.mDrawableContainerState;
    }

    @Override // android.graphics.drawable.Drawable
    @RequiresApi(21)
    public boolean canApplyTheme() {
        return this.mDrawableContainerState.canApplyTheme();
    }

    int d() {
        return this.mCurIndex;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NonNull Canvas canvas) {
        if (this.mCurrDrawable != null) {
            this.mCurrDrawable.draw(canvas);
        }
        if (this.mLastDrawable != null) {
            this.mLastDrawable.draw(canvas);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mAlpha;
    }

    @Override // android.graphics.drawable.Drawable
    public int getChangingConfigurations() {
        return this.mDrawableContainerState.getChangingConfigurations() | super.getChangingConfigurations();
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        if (!this.mDrawableContainerState.canConstantState()) {
            return null;
        }
        this.mDrawableContainerState.f = getChangingConfigurations();
        return this.mDrawableContainerState;
    }

    @Override // android.graphics.drawable.Drawable
    @NonNull
    public Drawable getCurrent() {
        return this.mCurrDrawable;
    }

    @Override // android.graphics.drawable.Drawable
    public void getHotspotBounds(@NonNull Rect rect) {
        if (this.mHotspotBounds != null) {
            rect.set(this.mHotspotBounds);
        } else {
            super.getHotspotBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantHeight();
        }
        if (this.mCurrDrawable != null) {
            return this.mCurrDrawable.getIntrinsicHeight();
        }
        return -1;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantWidth();
        }
        if (this.mCurrDrawable != null) {
            return this.mCurrDrawable.getIntrinsicWidth();
        }
        return -1;
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumHeight() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantMinimumHeight();
        }
        if (this.mCurrDrawable != null) {
            return this.mCurrDrawable.getMinimumHeight();
        }
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumWidth() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantMinimumWidth();
        }
        if (this.mCurrDrawable != null) {
            return this.mCurrDrawable.getMinimumWidth();
        }
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        if (this.mCurrDrawable == null || !this.mCurrDrawable.isVisible()) {
            return -2;
        }
        return this.mDrawableContainerState.getOpacity();
    }

    @Override // android.graphics.drawable.Drawable
    @RequiresApi(21)
    public void getOutline(@NonNull Outline outline) {
        if (this.mCurrDrawable != null) {
            Api21Impl.getOutline(this.mCurrDrawable, outline);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(@NonNull Rect rect) {
        boolean padding;
        Rect constantPadding = this.mDrawableContainerState.getConstantPadding();
        if (constantPadding != null) {
            rect.set(constantPadding);
            padding = (constantPadding.right | ((constantPadding.left | constantPadding.top) | constantPadding.bottom)) != 0;
        } else {
            padding = this.mCurrDrawable != null ? this.mCurrDrawable.getPadding(rect) : super.getPadding(rect);
        }
        if (needsMirroring()) {
            int i = rect.left;
            rect.left = rect.right;
            rect.right = i;
        }
        return padding;
    }

    public void invalidateDrawable(@NonNull Drawable drawable) {
        if (this.mDrawableContainerState != null) {
            this.mDrawableContainerState.b();
        }
        if (drawable != this.mCurrDrawable || getCallback() == null) {
            return;
        }
        getCallback().invalidateDrawable(this);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isAutoMirrored() {
        return this.mDrawableContainerState.E;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return this.mDrawableContainerState.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        boolean z;
        if (this.mLastDrawable != null) {
            this.mLastDrawable.jumpToCurrentState();
            this.mLastDrawable = null;
            z = true;
        } else {
            z = false;
        }
        if (this.mCurrDrawable != null) {
            this.mCurrDrawable.jumpToCurrentState();
            if (this.mHasAlpha) {
                this.mCurrDrawable.setAlpha(this.mAlpha);
            }
        }
        if (this.mExitAnimationEnd != 0) {
            this.mExitAnimationEnd = 0L;
            z = true;
        }
        if (this.mEnterAnimationEnd != 0) {
            this.mEnterAnimationEnd = 0L;
            z = true;
        }
        if (z) {
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            DrawableContainerState drawableContainerStateC = c();
            drawableContainerStateC.a();
            a(drawableContainerStateC);
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        if (this.mLastDrawable != null) {
            this.mLastDrawable.setBounds(rect);
        }
        if (this.mCurrDrawable != null) {
            this.mCurrDrawable.setBounds(rect);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onLayoutDirectionChanged(int i) {
        return this.mDrawableContainerState.d(i, d());
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        Drawable drawable;
        if (this.mLastDrawable != null) {
            drawable = this.mLastDrawable;
        } else {
            if (this.mCurrDrawable == null) {
                return false;
            }
            drawable = this.mCurrDrawable;
        }
        return drawable.setLevel(i);
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        Drawable drawable;
        if (this.mLastDrawable != null) {
            drawable = this.mLastDrawable;
        } else {
            if (this.mCurrDrawable == null) {
                return false;
            }
            drawable = this.mCurrDrawable;
        }
        return drawable.setState(iArr);
    }

    public void scheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable, long j) {
        if (drawable != this.mCurrDrawable || getCallback() == null) {
            return;
        }
        getCallback().scheduleDrawable(this, runnable, j);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (this.mHasAlpha && this.mAlpha == i) {
            return;
        }
        this.mHasAlpha = true;
        this.mAlpha = i;
        if (this.mCurrDrawable != null) {
            if (this.mEnterAnimationEnd == 0) {
                this.mCurrDrawable.setAlpha(i);
            } else {
                a(false);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        if (this.mDrawableContainerState.E != z) {
            this.mDrawableContainerState.E = z;
            if (this.mCurrDrawable != null) {
                DrawableCompat.setAutoMirrored(this.mCurrDrawable, this.mDrawableContainerState.E);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mDrawableContainerState.G = true;
        if (this.mDrawableContainerState.F != colorFilter) {
            this.mDrawableContainerState.F = colorFilter;
            if (this.mCurrDrawable != null) {
                this.mCurrDrawable.setColorFilter(colorFilter);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        if (this.mDrawableContainerState.z != z) {
            this.mDrawableContainerState.z = z;
            if (this.mCurrDrawable != null) {
                this.mCurrDrawable.setDither(this.mDrawableContainerState.z);
            }
        }
    }

    public void setEnterFadeDuration(int i) {
        this.mDrawableContainerState.C = i;
    }

    public void setExitFadeDuration(int i) {
        this.mDrawableContainerState.D = i;
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspot(float f, float f2) {
        if (this.mCurrDrawable != null) {
            DrawableCompat.setHotspot(this.mCurrDrawable, f, f2);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspotBounds(int i, int i2, int i3, int i4) {
        if (this.mHotspotBounds == null) {
            this.mHotspotBounds = new Rect(i, i2, i3, i4);
        } else {
            this.mHotspotBounds.set(i, i2, i3, i4);
        }
        if (this.mCurrDrawable != null) {
            DrawableCompat.setHotspotBounds(this.mCurrDrawable, i, i2, i3, i4);
        }
    }

    @Override // android.graphics.drawable.Drawable, androidx.core.graphics.drawable.TintAwareDrawable
    public void setTintList(ColorStateList colorStateList) {
        this.mDrawableContainerState.J = true;
        if (this.mDrawableContainerState.H != colorStateList) {
            this.mDrawableContainerState.H = colorStateList;
            DrawableCompat.setTintList(this.mCurrDrawable, colorStateList);
        }
    }

    @Override // android.graphics.drawable.Drawable, androidx.core.graphics.drawable.TintAwareDrawable
    public void setTintMode(@NonNull PorterDuff.Mode mode) {
        this.mDrawableContainerState.K = true;
        if (this.mDrawableContainerState.I != mode) {
            this.mDrawableContainerState.I = mode;
            DrawableCompat.setTintMode(this.mCurrDrawable, mode);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (this.mLastDrawable != null) {
            this.mLastDrawable.setVisible(z, z2);
        }
        if (this.mCurrDrawable != null) {
            this.mCurrDrawable.setVisible(z, z2);
        }
        return visible;
    }

    public void unscheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable) {
        if (drawable != this.mCurrDrawable || getCallback() == null) {
            return;
        }
        getCallback().unscheduleDrawable(this, runnable);
    }
}
