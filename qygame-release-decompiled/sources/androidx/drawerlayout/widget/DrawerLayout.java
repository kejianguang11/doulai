package androidx.drawerlayout.widget;

import android.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DrawerLayout extends ViewGroup {
    private static final boolean ALLOW_EDGE_LOCK = false;
    private static final boolean CHILDREN_DISALLOW_INTERCEPT = true;
    private static final int DEFAULT_SCRIM_COLOR = -1728053248;
    private static final int DRAWER_ELEVATION = 10;
    public static final int LOCK_MODE_LOCKED_CLOSED = 1;
    public static final int LOCK_MODE_LOCKED_OPEN = 2;
    public static final int LOCK_MODE_UNDEFINED = 3;
    public static final int LOCK_MODE_UNLOCKED = 0;
    private static final int MIN_DRAWER_MARGIN = 64;
    private static final int MIN_FLING_VELOCITY = 400;
    private static final int PEEK_DELAY = 160;
    private static final boolean SET_DRAWER_SHADOW_FROM_ELEVATION;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "DrawerLayout";
    private static final float TOUCH_SLOP_SENSITIVITY = 1.0f;
    static final boolean b;
    private final ChildAccessibilityDelegate mChildAccessibilityDelegate;
    private Rect mChildHitRect;
    private Matrix mChildInvertedMatrix;
    private boolean mChildrenCanceledTouch;
    private boolean mDisallowInterceptRequested;
    private boolean mDrawStatusBarBackground;
    private float mDrawerElevation;
    private int mDrawerState;
    private boolean mFirstLayout;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private Object mLastInsets;
    private final ViewDragCallback mLeftCallback;
    private final ViewDragHelper mLeftDragger;

    @Nullable
    private DrawerListener mListener;
    private List<DrawerListener> mListeners;
    private int mLockModeEnd;
    private int mLockModeLeft;
    private int mLockModeRight;
    private int mLockModeStart;
    private int mMinDrawerMargin;
    private final ArrayList<View> mNonDrawerViews;
    private final ViewDragCallback mRightCallback;
    private final ViewDragHelper mRightDragger;
    private int mScrimColor;
    private float mScrimOpacity;
    private Paint mScrimPaint;
    private Drawable mShadowEnd;
    private Drawable mShadowLeft;
    private Drawable mShadowLeftResolved;
    private Drawable mShadowRight;
    private Drawable mShadowRightResolved;
    private Drawable mShadowStart;
    private Drawable mStatusBarBackground;
    private CharSequence mTitleLeft;
    private CharSequence mTitleRight;
    private static final int[] THEME_ATTRS = {R.attr.colorPrimaryDark};
    static final int[] a = {R.attr.layout_gravity};

    class AccessibilityDelegate extends AccessibilityDelegateCompat {
        private final Rect mTmpRect = new Rect();

        AccessibilityDelegate() {
        }

        private void addChildrenForAccessibility(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, ViewGroup viewGroup) {
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (DrawerLayout.g(childAt)) {
                    accessibilityNodeInfoCompat.addChild(childAt);
                }
            }
        }

        private void copyNodeInfoNoChildren(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2) {
            Rect rect = this.mTmpRect;
            accessibilityNodeInfoCompat2.getBoundsInParent(rect);
            accessibilityNodeInfoCompat.setBoundsInParent(rect);
            accessibilityNodeInfoCompat2.getBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setVisibleToUser(accessibilityNodeInfoCompat2.isVisibleToUser());
            accessibilityNodeInfoCompat.setPackageName(accessibilityNodeInfoCompat2.getPackageName());
            accessibilityNodeInfoCompat.setClassName(accessibilityNodeInfoCompat2.getClassName());
            accessibilityNodeInfoCompat.setContentDescription(accessibilityNodeInfoCompat2.getContentDescription());
            accessibilityNodeInfoCompat.setEnabled(accessibilityNodeInfoCompat2.isEnabled());
            accessibilityNodeInfoCompat.setClickable(accessibilityNodeInfoCompat2.isClickable());
            accessibilityNodeInfoCompat.setFocusable(accessibilityNodeInfoCompat2.isFocusable());
            accessibilityNodeInfoCompat.setFocused(accessibilityNodeInfoCompat2.isFocused());
            accessibilityNodeInfoCompat.setAccessibilityFocused(accessibilityNodeInfoCompat2.isAccessibilityFocused());
            accessibilityNodeInfoCompat.setSelected(accessibilityNodeInfoCompat2.isSelected());
            accessibilityNodeInfoCompat.setLongClickable(accessibilityNodeInfoCompat2.isLongClickable());
            accessibilityNodeInfoCompat.addAction(accessibilityNodeInfoCompat2.getActions());
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            if (accessibilityEvent.getEventType() != 32) {
                return super.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
            }
            List<CharSequence> text = accessibilityEvent.getText();
            View viewB = DrawerLayout.this.b();
            if (viewB == null) {
                return true;
            }
            CharSequence drawerTitle = DrawerLayout.this.getDrawerTitle(DrawerLayout.this.d(viewB));
            if (drawerTitle == null) {
                return true;
            }
            text.add(drawerTitle);
            return true;
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName(DrawerLayout.class.getName());
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (DrawerLayout.b) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            } else {
                AccessibilityNodeInfoCompat accessibilityNodeInfoCompatObtain = AccessibilityNodeInfoCompat.obtain(accessibilityNodeInfoCompat);
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompatObtain);
                accessibilityNodeInfoCompat.setSource(view);
                Object parentForAccessibility = ViewCompat.getParentForAccessibility(view);
                if (parentForAccessibility instanceof View) {
                    accessibilityNodeInfoCompat.setParent((View) parentForAccessibility);
                }
                copyNodeInfoNoChildren(accessibilityNodeInfoCompat, accessibilityNodeInfoCompatObtain);
                accessibilityNodeInfoCompatObtain.recycle();
                addChildrenForAccessibility(accessibilityNodeInfoCompat, (ViewGroup) view);
            }
            accessibilityNodeInfoCompat.setClassName(DrawerLayout.class.getName());
            accessibilityNodeInfoCompat.setFocusable(false);
            accessibilityNodeInfoCompat.setFocused(false);
            accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_FOCUS);
            accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLEAR_FOCUS);
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            if (DrawerLayout.b || DrawerLayout.g(view)) {
                return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
            }
            return false;
        }
    }

    static final class ChildAccessibilityDelegate extends AccessibilityDelegateCompat {
        ChildAccessibilityDelegate() {
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            if (DrawerLayout.g(view)) {
                return;
            }
            accessibilityNodeInfoCompat.setParent(null);
        }
    }

    public interface DrawerListener {
        void onDrawerClosed(@NonNull View view);

        void onDrawerOpened(@NonNull View view);

        void onDrawerSlide(@NonNull View view, float f);

        void onDrawerStateChanged(int i);
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        private static final int FLAG_IS_CLOSING = 4;
        private static final int FLAG_IS_OPENED = 1;
        private static final int FLAG_IS_OPENING = 2;
        float a;
        boolean b;
        int c;
        public int gravity;

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.gravity = 0;
        }

        public LayoutParams(int i, int i2, int i3) {
            this(i, i2);
            this.gravity = i3;
        }

        public LayoutParams(@NonNull Context context, @Nullable AttributeSet attributeSet) {
            super(context, attributeSet);
            this.gravity = 0;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, DrawerLayout.a);
            this.gravity = typedArrayObtainStyledAttributes.getInt(0, 0);
            typedArrayObtainStyledAttributes.recycle();
        }

        public LayoutParams(@NonNull ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.gravity = 0;
        }

        public LayoutParams(@NonNull ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.gravity = 0;
        }

        public LayoutParams(@NonNull LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams);
            this.gravity = 0;
            this.gravity = layoutParams.gravity;
        }
    }

    protected static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: androidx.drawerlayout.widget.DrawerLayout.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        int a;
        int b;
        int c;
        int d;
        int e;

        public SavedState(@NonNull Parcel parcel, @Nullable ClassLoader classLoader) {
            super(parcel, classLoader);
            this.a = 0;
            this.a = parcel.readInt();
            this.b = parcel.readInt();
            this.c = parcel.readInt();
            this.d = parcel.readInt();
            this.e = parcel.readInt();
        }

        public SavedState(@NonNull Parcelable parcelable) {
            super(parcelable);
            this.a = 0;
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.a);
            parcel.writeInt(this.b);
            parcel.writeInt(this.c);
            parcel.writeInt(this.d);
            parcel.writeInt(this.e);
        }
    }

    public static abstract class SimpleDrawerListener implements DrawerListener {
        @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
        public void onDrawerClosed(View view) {
        }

        @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
        public void onDrawerOpened(View view) {
        }

        @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
        public void onDrawerSlide(View view, float f) {
        }

        @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
        public void onDrawerStateChanged(int i) {
        }
    }

    private class ViewDragCallback extends ViewDragHelper.Callback {
        private final int mAbsGravity;
        private ViewDragHelper mDragger;
        private final Runnable mPeekRunnable = new Runnable() { // from class: androidx.drawerlayout.widget.DrawerLayout.ViewDragCallback.1
            @Override // java.lang.Runnable
            public void run() {
                ViewDragCallback.this.a();
            }
        };

        ViewDragCallback(int i) {
            this.mAbsGravity = i;
        }

        private void closeOtherDrawer() {
            View viewA = DrawerLayout.this.a(this.mAbsGravity == 3 ? 5 : 3);
            if (viewA != null) {
                DrawerLayout.this.closeDrawer(viewA);
            }
        }

        void a() {
            View viewA;
            int width;
            int edgeSize = this.mDragger.getEdgeSize();
            boolean z = this.mAbsGravity == 3;
            if (z) {
                viewA = DrawerLayout.this.a(3);
                width = (viewA != null ? -viewA.getWidth() : 0) + edgeSize;
            } else {
                viewA = DrawerLayout.this.a(5);
                width = DrawerLayout.this.getWidth() - edgeSize;
            }
            if (viewA != null) {
                if (((!z || viewA.getLeft() >= width) && (z || viewA.getLeft() <= width)) || DrawerLayout.this.getDrawerLockMode(viewA) != 0) {
                    return;
                }
                LayoutParams layoutParams = (LayoutParams) viewA.getLayoutParams();
                this.mDragger.smoothSlideViewTo(viewA, width, viewA.getTop());
                layoutParams.b = true;
                DrawerLayout.this.invalidate();
                closeOtherDrawer();
                DrawerLayout.this.c();
            }
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public int clampViewPositionHorizontal(View view, int i, int i2) {
            if (DrawerLayout.this.a(view, 3)) {
                return Math.max(-view.getWidth(), Math.min(i, 0));
            }
            int width = DrawerLayout.this.getWidth();
            return Math.max(width - view.getWidth(), Math.min(i, width));
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public int clampViewPositionVertical(View view, int i, int i2) {
            return view.getTop();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public int getViewHorizontalDragRange(View view) {
            if (DrawerLayout.this.f(view)) {
                return view.getWidth();
            }
            return 0;
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onEdgeDragStarted(int i, int i2) {
            DrawerLayout drawerLayout;
            int i3;
            if ((i & 1) == 1) {
                drawerLayout = DrawerLayout.this;
                i3 = 3;
            } else {
                drawerLayout = DrawerLayout.this;
                i3 = 5;
            }
            View viewA = drawerLayout.a(i3);
            if (viewA == null || DrawerLayout.this.getDrawerLockMode(viewA) != 0) {
                return;
            }
            this.mDragger.captureChildView(viewA, i2);
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public boolean onEdgeLock(int i) {
            return false;
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onEdgeTouched(int i, int i2) {
            DrawerLayout.this.postDelayed(this.mPeekRunnable, 160L);
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewCaptured(View view, int i) {
            ((LayoutParams) view.getLayoutParams()).b = false;
            closeOtherDrawer();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewDragStateChanged(int i) {
            DrawerLayout.this.a(this.mAbsGravity, i, this.mDragger.getCapturedView());
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
            float width = (DrawerLayout.this.a(view, 3) ? i + r3 : DrawerLayout.this.getWidth() - i) / view.getWidth();
            DrawerLayout.this.b(view, width);
            view.setVisibility(width == 0.0f ? 4 : 0);
            DrawerLayout.this.invalidate();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewReleased(View view, float f, float f2) {
            int i;
            float fC = DrawerLayout.this.c(view);
            int width = view.getWidth();
            if (DrawerLayout.this.a(view, 3)) {
                i = (f > 0.0f || (f == 0.0f && fC > 0.5f)) ? 0 : -width;
            } else {
                int width2 = DrawerLayout.this.getWidth();
                if (f < 0.0f || (f == 0.0f && fC > 0.5f)) {
                    width2 -= width;
                }
                i = width2;
            }
            this.mDragger.settleCapturedViewAt(i, view.getTop());
            DrawerLayout.this.invalidate();
        }

        public void removeCallbacks() {
            DrawerLayout.this.removeCallbacks(this.mPeekRunnable);
        }

        public void setDragger(ViewDragHelper viewDragHelper) {
            this.mDragger = viewDragHelper;
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public boolean tryCaptureView(View view, int i) {
            return DrawerLayout.this.f(view) && DrawerLayout.this.a(view, this.mAbsGravity) && DrawerLayout.this.getDrawerLockMode(view) == 0;
        }
    }

    static {
        b = Build.VERSION.SDK_INT >= 19;
        SET_DRAWER_SHADOW_FROM_ELEVATION = Build.VERSION.SDK_INT >= 21;
    }

    public DrawerLayout(@NonNull Context context) {
        this(context, null);
    }

    public DrawerLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DrawerLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mChildAccessibilityDelegate = new ChildAccessibilityDelegate();
        this.mScrimColor = DEFAULT_SCRIM_COLOR;
        this.mScrimPaint = new Paint();
        this.mFirstLayout = true;
        this.mLockModeLeft = 3;
        this.mLockModeRight = 3;
        this.mLockModeStart = 3;
        this.mLockModeEnd = 3;
        this.mShadowStart = null;
        this.mShadowEnd = null;
        this.mShadowLeft = null;
        this.mShadowRight = null;
        setDescendantFocusability(262144);
        float f = getResources().getDisplayMetrics().density;
        this.mMinDrawerMargin = (int) ((64.0f * f) + 0.5f);
        float f2 = 400.0f * f;
        this.mLeftCallback = new ViewDragCallback(3);
        this.mRightCallback = new ViewDragCallback(5);
        this.mLeftDragger = ViewDragHelper.create(this, TOUCH_SLOP_SENSITIVITY, this.mLeftCallback);
        this.mLeftDragger.setEdgeTrackingEnabled(1);
        this.mLeftDragger.setMinVelocity(f2);
        this.mLeftCallback.setDragger(this.mLeftDragger);
        this.mRightDragger = ViewDragHelper.create(this, TOUCH_SLOP_SENSITIVITY, this.mRightCallback);
        this.mRightDragger.setEdgeTrackingEnabled(2);
        this.mRightDragger.setMinVelocity(f2);
        this.mRightCallback.setDragger(this.mRightDragger);
        setFocusableInTouchMode(true);
        ViewCompat.setImportantForAccessibility(this, 1);
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegate());
        setMotionEventSplittingEnabled(false);
        if (ViewCompat.getFitsSystemWindows(this)) {
            if (Build.VERSION.SDK_INT >= 21) {
                setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: androidx.drawerlayout.widget.DrawerLayout.1
                    @Override // android.view.View.OnApplyWindowInsetsListener
                    public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                        ((DrawerLayout) view).setChildInsets(windowInsets, windowInsets.getSystemWindowInsetTop() > 0);
                        return windowInsets.consumeSystemWindowInsets();
                    }
                });
                setSystemUiVisibility(1280);
                TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(THEME_ATTRS);
                try {
                    this.mStatusBarBackground = typedArrayObtainStyledAttributes.getDrawable(0);
                } finally {
                    typedArrayObtainStyledAttributes.recycle();
                }
            } else {
                this.mStatusBarBackground = null;
            }
        }
        this.mDrawerElevation = f * 10.0f;
        this.mNonDrawerViews = new ArrayList<>();
    }

    static String b(int i) {
        return (i & 3) == 3 ? "LEFT" : (i & 5) == 5 ? "RIGHT" : Integer.toHexString(i);
    }

    private boolean dispatchTransformedGenericPointerEvent(MotionEvent motionEvent, View view) {
        if (!view.getMatrix().isIdentity()) {
            MotionEvent transformedMotionEvent = getTransformedMotionEvent(motionEvent, view);
            boolean zDispatchGenericMotionEvent = view.dispatchGenericMotionEvent(transformedMotionEvent);
            transformedMotionEvent.recycle();
            return zDispatchGenericMotionEvent;
        }
        float scrollX = getScrollX() - view.getLeft();
        float scrollY = getScrollY() - view.getTop();
        motionEvent.offsetLocation(scrollX, scrollY);
        boolean zDispatchGenericMotionEvent2 = view.dispatchGenericMotionEvent(motionEvent);
        motionEvent.offsetLocation(-scrollX, -scrollY);
        return zDispatchGenericMotionEvent2;
    }

    static boolean g(View view) {
        return (ViewCompat.getImportantForAccessibility(view) == 4 || ViewCompat.getImportantForAccessibility(view) == 2) ? false : true;
    }

    private MotionEvent getTransformedMotionEvent(MotionEvent motionEvent, View view) {
        float scrollX = getScrollX() - view.getLeft();
        float scrollY = getScrollY() - view.getTop();
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
        motionEventObtain.offsetLocation(scrollX, scrollY);
        Matrix matrix = view.getMatrix();
        if (!matrix.isIdentity()) {
            if (this.mChildInvertedMatrix == null) {
                this.mChildInvertedMatrix = new Matrix();
            }
            matrix.invert(this.mChildInvertedMatrix);
            motionEventObtain.transform(this.mChildInvertedMatrix);
        }
        return motionEventObtain;
    }

    private static boolean hasOpaqueBackground(View view) {
        Drawable background = view.getBackground();
        return background != null && background.getOpacity() == -1;
    }

    private boolean hasPeekingDrawer() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (((LayoutParams) getChildAt(i).getLayoutParams()).b) {
                return true;
            }
        }
        return false;
    }

    private boolean hasVisibleDrawer() {
        return b() != null;
    }

    private boolean isInBoundsOfChild(float f, float f2, View view) {
        if (this.mChildHitRect == null) {
            this.mChildHitRect = new Rect();
        }
        view.getHitRect(this.mChildHitRect);
        return this.mChildHitRect.contains((int) f, (int) f2);
    }

    private boolean mirror(Drawable drawable, int i) {
        if (drawable == null || !DrawableCompat.isAutoMirrored(drawable)) {
            return false;
        }
        DrawableCompat.setLayoutDirection(drawable, i);
        return true;
    }

    private Drawable resolveLeftShadow() {
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        if (layoutDirection == 0) {
            if (this.mShadowStart != null) {
                mirror(this.mShadowStart, layoutDirection);
                return this.mShadowStart;
            }
        } else if (this.mShadowEnd != null) {
            mirror(this.mShadowEnd, layoutDirection);
            return this.mShadowEnd;
        }
        return this.mShadowLeft;
    }

    private Drawable resolveRightShadow() {
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        if (layoutDirection == 0) {
            if (this.mShadowEnd != null) {
                mirror(this.mShadowEnd, layoutDirection);
                return this.mShadowEnd;
            }
        } else if (this.mShadowStart != null) {
            mirror(this.mShadowStart, layoutDirection);
            return this.mShadowStart;
        }
        return this.mShadowRight;
    }

    private void resolveShadowDrawables() {
        if (SET_DRAWER_SHADOW_FROM_ELEVATION) {
            return;
        }
        this.mShadowLeftResolved = resolveLeftShadow();
        this.mShadowRightResolved = resolveRightShadow();
    }

    private void updateChildrenImportantForAccessibility(View view, boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            ViewCompat.setImportantForAccessibility(childAt, ((z || f(childAt)) && !(z && childAt == view)) ? 4 : 1);
        }
    }

    View a() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if ((((LayoutParams) childAt.getLayoutParams()).c & 1) == 1) {
                return childAt;
            }
        }
        return null;
    }

    View a(int i) {
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this)) & 7;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if ((d(childAt) & 7) == absoluteGravity) {
                return childAt;
            }
        }
        return null;
    }

    void a(int i, int i2, View view) {
        int viewDragState = this.mLeftDragger.getViewDragState();
        int viewDragState2 = this.mRightDragger.getViewDragState();
        int i3 = 2;
        if (viewDragState == 1 || viewDragState2 == 1) {
            i3 = 1;
        } else if (viewDragState != 2 && viewDragState2 != 2) {
            i3 = 0;
        }
        if (view != null && i2 == 0) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (layoutParams.a == 0.0f) {
                a(view);
            } else if (layoutParams.a == TOUCH_SLOP_SENSITIVITY) {
                b(view);
            }
        }
        if (i3 != this.mDrawerState) {
            this.mDrawerState = i3;
            if (this.mListeners != null) {
                for (int size = this.mListeners.size() - 1; size >= 0; size--) {
                    this.mListeners.get(size).onDrawerStateChanged(i3);
                }
            }
        }
    }

    void a(View view) {
        View rootView;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if ((layoutParams.c & 1) == 1) {
            layoutParams.c = 0;
            if (this.mListeners != null) {
                for (int size = this.mListeners.size() - 1; size >= 0; size--) {
                    this.mListeners.get(size).onDrawerClosed(view);
                }
            }
            updateChildrenImportantForAccessibility(view, false);
            if (!hasWindowFocus() || (rootView = getRootView()) == null) {
                return;
            }
            rootView.sendAccessibilityEvent(32);
        }
    }

    void a(View view, float f) {
        if (this.mListeners != null) {
            for (int size = this.mListeners.size() - 1; size >= 0; size--) {
                this.mListeners.get(size).onDrawerSlide(view, f);
            }
        }
    }

    void a(boolean z) {
        int childCount = getChildCount();
        boolean zSmoothSlideViewTo = false;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (f(childAt) && (!z || layoutParams.b)) {
                zSmoothSlideViewTo |= a(childAt, 3) ? this.mLeftDragger.smoothSlideViewTo(childAt, -childAt.getWidth(), childAt.getTop()) : this.mRightDragger.smoothSlideViewTo(childAt, getWidth(), childAt.getTop());
                layoutParams.b = false;
            }
        }
        this.mLeftCallback.removeCallbacks();
        this.mRightCallback.removeCallbacks();
        if (zSmoothSlideViewTo) {
            invalidate();
        }
    }

    boolean a(View view, int i) {
        return (d(view) & i) == i;
    }

    public void addDrawerListener(@NonNull DrawerListener drawerListener) {
        if (drawerListener == null) {
            return;
        }
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(drawerListener);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        if (getDescendantFocusability() == 393216) {
            return;
        }
        int childCount = getChildCount();
        boolean z = false;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (!f(childAt)) {
                this.mNonDrawerViews.add(childAt);
            } else if (isDrawerOpen(childAt)) {
                childAt.addFocusables(arrayList, i, i2);
                z = true;
            }
        }
        if (!z) {
            int size = this.mNonDrawerViews.size();
            for (int i4 = 0; i4 < size; i4++) {
                View view = this.mNonDrawerViews.get(i4);
                if (view.getVisibility() == 0) {
                    view.addFocusables(arrayList, i, i2);
                }
            }
        }
        this.mNonDrawerViews.clear();
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
        ViewCompat.setImportantForAccessibility(view, (a() != null || f(view)) ? 4 : 1);
        if (b) {
            return;
        }
        ViewCompat.setAccessibilityDelegate(view, this.mChildAccessibilityDelegate);
    }

    View b() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (f(childAt) && isDrawerVisible(childAt)) {
                return childAt;
            }
        }
        return null;
    }

    void b(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if ((layoutParams.c & 1) == 0) {
            layoutParams.c = 1;
            if (this.mListeners != null) {
                for (int size = this.mListeners.size() - 1; size >= 0; size--) {
                    this.mListeners.get(size).onDrawerOpened(view);
                }
            }
            updateChildrenImportantForAccessibility(view, true);
            if (hasWindowFocus()) {
                sendAccessibilityEvent(32);
            }
        }
    }

    void b(View view, float f) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (f == layoutParams.a) {
            return;
        }
        layoutParams.a = f;
        a(view, f);
    }

    float c(View view) {
        return ((LayoutParams) view.getLayoutParams()).a;
    }

    void c() {
        if (this.mChildrenCanceledTouch) {
            return;
        }
        long jUptimeMillis = SystemClock.uptimeMillis();
        MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).dispatchTouchEvent(motionEventObtain);
        }
        motionEventObtain.recycle();
        this.mChildrenCanceledTouch = true;
    }

    void c(View view, float f) {
        float fC = c(view);
        float width = view.getWidth();
        int i = ((int) (width * f)) - ((int) (fC * width));
        if (!a(view, 3)) {
            i = -i;
        }
        view.offsetLeftAndRight(i);
        b(view, f);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public void closeDrawer(int i) {
        closeDrawer(i, true);
    }

    public void closeDrawer(int i, boolean z) {
        View viewA = a(i);
        if (viewA != null) {
            closeDrawer(viewA, z);
            return;
        }
        throw new IllegalArgumentException("No drawer view found with gravity " + b(i));
    }

    public void closeDrawer(@NonNull View view) {
        closeDrawer(view, true);
    }

    public void closeDrawer(@NonNull View view, boolean z) {
        ViewDragHelper viewDragHelper;
        int width;
        if (!f(view)) {
            throw new IllegalArgumentException("View " + view + " is not a sliding drawer");
        }
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (this.mFirstLayout) {
            layoutParams.a = 0.0f;
            layoutParams.c = 0;
        } else if (z) {
            layoutParams.c |= 4;
            if (a(view, 3)) {
                viewDragHelper = this.mLeftDragger;
                width = -view.getWidth();
            } else {
                viewDragHelper = this.mRightDragger;
                width = getWidth();
            }
            viewDragHelper.smoothSlideViewTo(view, width, view.getTop());
        } else {
            c(view, 0.0f);
            a(layoutParams.gravity, 0, view);
            view.setVisibility(4);
        }
        invalidate();
    }

    public void closeDrawers() {
        a(false);
    }

    @Override // android.view.View
    public void computeScroll() {
        int childCount = getChildCount();
        float fMax = 0.0f;
        for (int i = 0; i < childCount; i++) {
            fMax = Math.max(fMax, ((LayoutParams) getChildAt(i).getLayoutParams()).a);
        }
        this.mScrimOpacity = fMax;
        boolean zContinueSettling = this.mLeftDragger.continueSettling(true);
        boolean zContinueSettling2 = this.mRightDragger.continueSettling(true);
        if (zContinueSettling || zContinueSettling2) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    int d(View view) {
        return GravityCompat.getAbsoluteGravity(((LayoutParams) view.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(this));
    }

    @Override // android.view.View
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) == 0 || motionEvent.getAction() == 10 || this.mScrimOpacity <= 0.0f) {
            return super.dispatchGenericMotionEvent(motionEvent);
        }
        int childCount = getChildCount();
        if (childCount == 0) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        for (int i = childCount - 1; i >= 0; i--) {
            View childAt = getChildAt(i);
            if (isInBoundsOfChild(x, y, childAt) && !e(childAt) && dispatchTransformedGenericPointerEvent(motionEvent, childAt)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        int i;
        Drawable drawable;
        int height = getHeight();
        boolean zE = e(view);
        int width = getWidth();
        int iSave = canvas.save();
        int i2 = 0;
        if (zE) {
            int childCount = getChildCount();
            i = width;
            int i3 = 0;
            for (int i4 = 0; i4 < childCount; i4++) {
                View childAt = getChildAt(i4);
                if (childAt != view && childAt.getVisibility() == 0 && hasOpaqueBackground(childAt) && f(childAt) && childAt.getHeight() >= height) {
                    if (a(childAt, 3)) {
                        int right = childAt.getRight();
                        if (right > i3) {
                            i3 = right;
                        }
                    } else {
                        int left = childAt.getLeft();
                        if (left < i) {
                            i = left;
                        }
                    }
                }
            }
            canvas.clipRect(i3, 0, i, getHeight());
            i2 = i3;
        } else {
            i = width;
        }
        boolean zDrawChild = super.drawChild(canvas, view, j);
        canvas.restoreToCount(iSave);
        if (this.mScrimOpacity <= 0.0f || !zE) {
            if (this.mShadowLeftResolved != null && a(view, 3)) {
                int intrinsicWidth = this.mShadowLeftResolved.getIntrinsicWidth();
                int right2 = view.getRight();
                float fMax = Math.max(0.0f, Math.min(right2 / this.mLeftDragger.getEdgeSize(), TOUCH_SLOP_SENSITIVITY));
                this.mShadowLeftResolved.setBounds(right2, view.getTop(), intrinsicWidth + right2, view.getBottom());
                this.mShadowLeftResolved.setAlpha((int) (fMax * 255.0f));
                drawable = this.mShadowLeftResolved;
            } else if (this.mShadowRightResolved != null && a(view, 5)) {
                int intrinsicWidth2 = this.mShadowRightResolved.getIntrinsicWidth();
                int left2 = view.getLeft();
                float fMax2 = Math.max(0.0f, Math.min((getWidth() - left2) / this.mRightDragger.getEdgeSize(), TOUCH_SLOP_SENSITIVITY));
                this.mShadowRightResolved.setBounds(left2 - intrinsicWidth2, view.getTop(), left2, view.getBottom());
                this.mShadowRightResolved.setAlpha((int) (fMax2 * 255.0f));
                drawable = this.mShadowRightResolved;
            }
            drawable.draw(canvas);
        } else {
            this.mScrimPaint.setColor((((int) (((this.mScrimColor & ViewCompat.MEASURED_STATE_MASK) >>> 24) * this.mScrimOpacity)) << 24) | (this.mScrimColor & ViewCompat.MEASURED_SIZE_MASK));
            canvas.drawRect(i2, 0.0f, i, getHeight(), this.mScrimPaint);
        }
        return zDrawChild;
    }

    boolean e(View view) {
        return ((LayoutParams) view.getLayoutParams()).gravity == 0;
    }

    boolean f(View view) {
        int absoluteGravity = GravityCompat.getAbsoluteGravity(((LayoutParams) view.getLayoutParams()).gravity, ViewCompat.getLayoutDirection(view));
        return ((absoluteGravity & 3) == 0 && (absoluteGravity & 5) == 0) ? false : true;
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams ? new LayoutParams((LayoutParams) layoutParams) : layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    public float getDrawerElevation() {
        if (SET_DRAWER_SHADOW_FROM_ELEVATION) {
            return this.mDrawerElevation;
        }
        return 0.0f;
    }

    public int getDrawerLockMode(int i) {
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        if (i == 3) {
            if (this.mLockModeLeft != 3) {
                return this.mLockModeLeft;
            }
            int i2 = layoutDirection == 0 ? this.mLockModeStart : this.mLockModeEnd;
            if (i2 != 3) {
                return i2;
            }
            return 0;
        }
        if (i == 5) {
            if (this.mLockModeRight != 3) {
                return this.mLockModeRight;
            }
            int i3 = layoutDirection == 0 ? this.mLockModeEnd : this.mLockModeStart;
            if (i3 != 3) {
                return i3;
            }
            return 0;
        }
        if (i == 8388611) {
            if (this.mLockModeStart != 3) {
                return this.mLockModeStart;
            }
            int i4 = layoutDirection == 0 ? this.mLockModeLeft : this.mLockModeRight;
            if (i4 != 3) {
                return i4;
            }
            return 0;
        }
        if (i != 8388613) {
            return 0;
        }
        if (this.mLockModeEnd != 3) {
            return this.mLockModeEnd;
        }
        int i5 = layoutDirection == 0 ? this.mLockModeRight : this.mLockModeLeft;
        if (i5 != 3) {
            return i5;
        }
        return 0;
    }

    public int getDrawerLockMode(@NonNull View view) {
        if (f(view)) {
            return getDrawerLockMode(((LayoutParams) view.getLayoutParams()).gravity);
        }
        throw new IllegalArgumentException("View " + view + " is not a drawer");
    }

    @Nullable
    public CharSequence getDrawerTitle(int i) {
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this));
        if (absoluteGravity == 3) {
            return this.mTitleLeft;
        }
        if (absoluteGravity == 5) {
            return this.mTitleRight;
        }
        return null;
    }

    @Nullable
    public Drawable getStatusBarBackgroundDrawable() {
        return this.mStatusBarBackground;
    }

    public boolean isDrawerOpen(int i) {
        View viewA = a(i);
        if (viewA != null) {
            return isDrawerOpen(viewA);
        }
        return false;
    }

    public boolean isDrawerOpen(@NonNull View view) {
        if (f(view)) {
            return (((LayoutParams) view.getLayoutParams()).c & 1) == 1;
        }
        throw new IllegalArgumentException("View " + view + " is not a drawer");
    }

    public boolean isDrawerVisible(int i) {
        View viewA = a(i);
        if (viewA != null) {
            return isDrawerVisible(viewA);
        }
        return false;
    }

    public boolean isDrawerVisible(@NonNull View view) {
        if (f(view)) {
            return ((LayoutParams) view.getLayoutParams()).a > 0.0f;
        }
        throw new IllegalArgumentException("View " + view + " is not a drawer");
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mFirstLayout = true;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.mDrawStatusBarBackground || this.mStatusBarBackground == null) {
            return;
        }
        int systemWindowInsetTop = (Build.VERSION.SDK_INT < 21 || this.mLastInsets == null) ? 0 : ((WindowInsets) this.mLastInsets).getSystemWindowInsetTop();
        if (systemWindowInsetTop > 0) {
            this.mStatusBarBackground.setBounds(0, 0, getWidth(), systemWindowInsetTop);
            this.mStatusBarBackground.draw(canvas);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        View viewFindTopChildUnder;
        int actionMasked = motionEvent.getActionMasked();
        boolean zShouldInterceptTouchEvent = this.mLeftDragger.shouldInterceptTouchEvent(motionEvent) | this.mRightDragger.shouldInterceptTouchEvent(motionEvent);
        switch (actionMasked) {
            case 0:
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                this.mInitialMotionX = x;
                this.mInitialMotionY = y;
                z = this.mScrimOpacity > 0.0f && (viewFindTopChildUnder = this.mLeftDragger.findTopChildUnder((int) x, (int) y)) != null && e(viewFindTopChildUnder);
                this.mDisallowInterceptRequested = false;
                this.mChildrenCanceledTouch = false;
                break;
            case 1:
            case 3:
                a(true);
                this.mDisallowInterceptRequested = false;
                this.mChildrenCanceledTouch = false;
                z = false;
                break;
            case 2:
                if (this.mLeftDragger.checkTouchSlop(3)) {
                    this.mLeftCallback.removeCallbacks();
                    this.mRightCallback.removeCallbacks();
                }
                z = false;
                break;
            default:
                z = false;
                break;
        }
        return zShouldInterceptTouchEvent || z || hasPeekingDrawer() || this.mChildrenCanceledTouch;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 || !hasVisibleDrawer()) {
            return super.onKeyDown(i, keyEvent);
        }
        keyEvent.startTracking();
        return true;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyUp(i, keyEvent);
        }
        View viewB = b();
        if (viewB != null && getDrawerLockMode(viewB) == 0) {
            closeDrawers();
        }
        return viewB != null;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        float f;
        int i5;
        boolean z2 = true;
        this.mInLayout = true;
        int i6 = i3 - i;
        int childCount = getChildCount();
        int i7 = 0;
        while (i7 < childCount) {
            View childAt = getChildAt(i7);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (e(childAt)) {
                    childAt.layout(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.leftMargin + childAt.getMeasuredWidth(), layoutParams.topMargin + childAt.getMeasuredHeight());
                } else {
                    int measuredWidth = childAt.getMeasuredWidth();
                    int measuredHeight = childAt.getMeasuredHeight();
                    if (a(childAt, 3)) {
                        float f2 = measuredWidth;
                        i5 = (-measuredWidth) + ((int) (layoutParams.a * f2));
                        f = (measuredWidth + i5) / f2;
                    } else {
                        float f3 = measuredWidth;
                        f = (i6 - r12) / f3;
                        i5 = i6 - ((int) (layoutParams.a * f3));
                    }
                    boolean z3 = f != layoutParams.a ? z2 : false;
                    int i8 = layoutParams.gravity & 112;
                    if (i8 == 16) {
                        int i9 = i4 - i2;
                        int i10 = (i9 - measuredHeight) / 2;
                        if (i10 < layoutParams.topMargin) {
                            i10 = layoutParams.topMargin;
                        } else if (i10 + measuredHeight > i9 - layoutParams.bottomMargin) {
                            i10 = (i9 - layoutParams.bottomMargin) - measuredHeight;
                        }
                        childAt.layout(i5, i10, measuredWidth + i5, measuredHeight + i10);
                    } else if (i8 != 80) {
                        childAt.layout(i5, layoutParams.topMargin, measuredWidth + i5, layoutParams.topMargin + measuredHeight);
                    } else {
                        int i11 = i4 - i2;
                        childAt.layout(i5, (i11 - layoutParams.bottomMargin) - childAt.getMeasuredHeight(), measuredWidth + i5, i11 - layoutParams.bottomMargin);
                    }
                    if (z3) {
                        b(childAt, f);
                    }
                    int i12 = layoutParams.a > 0.0f ? 0 : 4;
                    if (childAt.getVisibility() != i12) {
                        childAt.setVisibility(i12);
                    }
                }
            }
            i7++;
            z2 = true;
        }
        this.mInLayout = false;
        this.mFirstLayout = false;
    }

    @Override // android.view.View
    @SuppressLint({"WrongConstant"})
    protected void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        if (mode != 1073741824 || mode2 != 1073741824) {
            if (!isInEditMode()) {
                throw new IllegalArgumentException("DrawerLayout must be measured with MeasureSpec.EXACTLY.");
            }
            if (mode != Integer.MIN_VALUE && mode == 0) {
                size = 300;
            }
            if (mode2 != Integer.MIN_VALUE && mode2 == 0) {
                size2 = 300;
            }
        }
        setMeasuredDimension(size, size2);
        int i3 = 0;
        boolean z = this.mLastInsets != null && ViewCompat.getFitsSystemWindows(this);
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        int childCount = getChildCount();
        int i4 = 0;
        boolean z2 = false;
        boolean z3 = false;
        while (i4 < childCount) {
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (z) {
                    int absoluteGravity = GravityCompat.getAbsoluteGravity(layoutParams.gravity, layoutDirection);
                    if (ViewCompat.getFitsSystemWindows(childAt)) {
                        if (Build.VERSION.SDK_INT >= 21) {
                            WindowInsets windowInsetsReplaceSystemWindowInsets = (WindowInsets) this.mLastInsets;
                            if (absoluteGravity == 3) {
                                windowInsetsReplaceSystemWindowInsets = windowInsetsReplaceSystemWindowInsets.replaceSystemWindowInsets(windowInsetsReplaceSystemWindowInsets.getSystemWindowInsetLeft(), windowInsetsReplaceSystemWindowInsets.getSystemWindowInsetTop(), i3, windowInsetsReplaceSystemWindowInsets.getSystemWindowInsetBottom());
                            } else if (absoluteGravity == 5) {
                                windowInsetsReplaceSystemWindowInsets = windowInsetsReplaceSystemWindowInsets.replaceSystemWindowInsets(i3, windowInsetsReplaceSystemWindowInsets.getSystemWindowInsetTop(), windowInsetsReplaceSystemWindowInsets.getSystemWindowInsetRight(), windowInsetsReplaceSystemWindowInsets.getSystemWindowInsetBottom());
                            }
                            childAt.dispatchApplyWindowInsets(windowInsetsReplaceSystemWindowInsets);
                        }
                    } else if (Build.VERSION.SDK_INT >= 21) {
                        WindowInsets windowInsetsReplaceSystemWindowInsets2 = (WindowInsets) this.mLastInsets;
                        if (absoluteGravity == 3) {
                            windowInsetsReplaceSystemWindowInsets2 = windowInsetsReplaceSystemWindowInsets2.replaceSystemWindowInsets(windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetLeft(), windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetTop(), i3, windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetBottom());
                        } else if (absoluteGravity == 5) {
                            windowInsetsReplaceSystemWindowInsets2 = windowInsetsReplaceSystemWindowInsets2.replaceSystemWindowInsets(i3, windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetTop(), windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetRight(), windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetBottom());
                        }
                        layoutParams.leftMargin = windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetLeft();
                        layoutParams.topMargin = windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetTop();
                        layoutParams.rightMargin = windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetRight();
                        layoutParams.bottomMargin = windowInsetsReplaceSystemWindowInsets2.getSystemWindowInsetBottom();
                    }
                }
                if (e(childAt)) {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec((size - layoutParams.leftMargin) - layoutParams.rightMargin, 1073741824), View.MeasureSpec.makeMeasureSpec((size2 - layoutParams.topMargin) - layoutParams.bottomMargin, 1073741824));
                } else {
                    if (!f(childAt)) {
                        throw new IllegalStateException("Child " + childAt + " at index " + i4 + " does not have a valid layout_gravity - must be Gravity.LEFT, Gravity.RIGHT or Gravity.NO_GRAVITY");
                    }
                    if (SET_DRAWER_SHADOW_FROM_ELEVATION && ViewCompat.getElevation(childAt) != this.mDrawerElevation) {
                        ViewCompat.setElevation(childAt, this.mDrawerElevation);
                    }
                    int iD = d(childAt) & 7;
                    int i5 = iD == 3 ? 1 : i3;
                    if ((i5 != 0 && z2) || (i5 == 0 && z3)) {
                        throw new IllegalStateException("Child drawer has absolute gravity " + b(iD) + " but this " + TAG + " already has a drawer view along that edge");
                    }
                    if (i5 != 0) {
                        z2 = true;
                    } else {
                        z3 = true;
                    }
                    childAt.measure(getChildMeasureSpec(i, this.mMinDrawerMargin + layoutParams.leftMargin + layoutParams.rightMargin, layoutParams.width), getChildMeasureSpec(i2, layoutParams.topMargin + layoutParams.bottomMargin, layoutParams.height));
                }
            }
            i4++;
            i3 = 0;
        }
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        View viewA;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.a != 0 && (viewA = a(savedState.a)) != null) {
            openDrawer(viewA);
        }
        if (savedState.b != 3) {
            setDrawerLockMode(savedState.b, 3);
        }
        if (savedState.c != 3) {
            setDrawerLockMode(savedState.c, 5);
        }
        if (savedState.d != 3) {
            setDrawerLockMode(savedState.d, GravityCompat.START);
        }
        if (savedState.e != 3) {
            setDrawerLockMode(savedState.e, GravityCompat.END);
        }
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        resolveShadowDrawables();
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            LayoutParams layoutParams = (LayoutParams) getChildAt(i).getLayoutParams();
            boolean z = layoutParams.c == 1;
            boolean z2 = layoutParams.c == 2;
            if (z || z2) {
                savedState.a = layoutParams.gravity;
                break;
            }
        }
        savedState.b = this.mLockModeLeft;
        savedState.c = this.mLockModeRight;
        savedState.d = this.mLockModeStart;
        savedState.e = this.mLockModeEnd;
        return savedState;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0056  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        View viewA;
        this.mLeftDragger.processTouchEvent(motionEvent);
        this.mRightDragger.processTouchEvent(motionEvent);
        int action = motionEvent.getAction() & 255;
        if (action != 3) {
            switch (action) {
                case 0:
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    this.mInitialMotionX = x;
                    this.mInitialMotionY = y;
                    break;
                case 1:
                    float x2 = motionEvent.getX();
                    float y2 = motionEvent.getY();
                    View viewFindTopChildUnder = this.mLeftDragger.findTopChildUnder((int) x2, (int) y2);
                    if (viewFindTopChildUnder != null && e(viewFindTopChildUnder)) {
                        float f = x2 - this.mInitialMotionX;
                        float f2 = y2 - this.mInitialMotionY;
                        int touchSlop = this.mLeftDragger.getTouchSlop();
                        if ((f * f) + (f2 * f2) < touchSlop * touchSlop && (viewA = a()) != null && getDrawerLockMode(viewA) != 2) {
                            z = false;
                        }
                        a(z);
                        this.mDisallowInterceptRequested = false;
                        break;
                    } else {
                        z = true;
                        a(z);
                        this.mDisallowInterceptRequested = false;
                        break;
                    }
                default:
                    return true;
            }
        } else {
            a(true);
        }
        this.mDisallowInterceptRequested = false;
        this.mChildrenCanceledTouch = false;
        return true;
    }

    public void openDrawer(int i) {
        openDrawer(i, true);
    }

    public void openDrawer(int i, boolean z) {
        View viewA = a(i);
        if (viewA != null) {
            openDrawer(viewA, z);
            return;
        }
        throw new IllegalArgumentException("No drawer view found with gravity " + b(i));
    }

    public void openDrawer(@NonNull View view) {
        openDrawer(view, true);
    }

    public void openDrawer(@NonNull View view, boolean z) {
        if (!f(view)) {
            throw new IllegalArgumentException("View " + view + " is not a sliding drawer");
        }
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (this.mFirstLayout) {
            layoutParams.a = TOUCH_SLOP_SENSITIVITY;
            layoutParams.c = 1;
            updateChildrenImportantForAccessibility(view, true);
        } else if (z) {
            layoutParams.c |= 2;
            if (a(view, 3)) {
                this.mLeftDragger.smoothSlideViewTo(view, 0, view.getTop());
            } else {
                this.mRightDragger.smoothSlideViewTo(view, getWidth() - view.getWidth(), view.getTop());
            }
        } else {
            c(view, TOUCH_SLOP_SENSITIVITY);
            a(layoutParams.gravity, 0, view);
            view.setVisibility(0);
        }
        invalidate();
    }

    public void removeDrawerListener(@NonNull DrawerListener drawerListener) {
        if (drawerListener == null || this.mListeners == null) {
            return;
        }
        this.mListeners.remove(drawerListener);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        super.requestDisallowInterceptTouchEvent(z);
        this.mDisallowInterceptRequested = z;
        if (z) {
            a(true);
        }
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.mInLayout) {
            return;
        }
        super.requestLayout();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setChildInsets(Object obj, boolean z) {
        this.mLastInsets = obj;
        this.mDrawStatusBarBackground = z;
        setWillNotDraw(!z && getBackground() == null);
        requestLayout();
    }

    public void setDrawerElevation(float f) {
        this.mDrawerElevation = f;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (f(childAt)) {
                ViewCompat.setElevation(childAt, this.mDrawerElevation);
            }
        }
    }

    @Deprecated
    public void setDrawerListener(DrawerListener drawerListener) {
        if (this.mListener != null) {
            removeDrawerListener(this.mListener);
        }
        if (drawerListener != null) {
            addDrawerListener(drawerListener);
        }
        this.mListener = drawerListener;
    }

    public void setDrawerLockMode(int i) {
        setDrawerLockMode(i, 3);
        setDrawerLockMode(i, 5);
    }

    public void setDrawerLockMode(int i, int i2) {
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i2, ViewCompat.getLayoutDirection(this));
        if (i2 == 3) {
            this.mLockModeLeft = i;
        } else if (i2 == 5) {
            this.mLockModeRight = i;
        } else if (i2 == 8388611) {
            this.mLockModeStart = i;
        } else if (i2 == 8388613) {
            this.mLockModeEnd = i;
        }
        if (i != 0) {
            (absoluteGravity == 3 ? this.mLeftDragger : this.mRightDragger).cancel();
        }
        switch (i) {
            case 1:
                View viewA = a(absoluteGravity);
                if (viewA != null) {
                    closeDrawer(viewA);
                }
                break;
            case 2:
                View viewA2 = a(absoluteGravity);
                if (viewA2 != null) {
                    openDrawer(viewA2);
                }
                break;
        }
    }

    public void setDrawerLockMode(int i, @NonNull View view) {
        if (f(view)) {
            setDrawerLockMode(i, ((LayoutParams) view.getLayoutParams()).gravity);
            return;
        }
        throw new IllegalArgumentException("View " + view + " is not a drawer with appropriate layout_gravity");
    }

    public void setDrawerShadow(@DrawableRes int i, int i2) {
        setDrawerShadow(ContextCompat.getDrawable(getContext(), i), i2);
    }

    public void setDrawerShadow(Drawable drawable, int i) {
        if (SET_DRAWER_SHADOW_FROM_ELEVATION) {
            return;
        }
        if ((i & GravityCompat.START) == 8388611) {
            this.mShadowStart = drawable;
        } else if ((i & GravityCompat.END) == 8388613) {
            this.mShadowEnd = drawable;
        } else if ((i & 3) == 3) {
            this.mShadowLeft = drawable;
        } else if ((i & 5) != 5) {
            return;
        } else {
            this.mShadowRight = drawable;
        }
        resolveShadowDrawables();
        invalidate();
    }

    public void setDrawerTitle(int i, @Nullable CharSequence charSequence) {
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this));
        if (absoluteGravity == 3) {
            this.mTitleLeft = charSequence;
        } else if (absoluteGravity == 5) {
            this.mTitleRight = charSequence;
        }
    }

    public void setScrimColor(@ColorInt int i) {
        this.mScrimColor = i;
        invalidate();
    }

    public void setStatusBarBackground(int i) {
        this.mStatusBarBackground = i != 0 ? ContextCompat.getDrawable(getContext(), i) : null;
        invalidate();
    }

    public void setStatusBarBackground(@Nullable Drawable drawable) {
        this.mStatusBarBackground = drawable;
        invalidate();
    }

    public void setStatusBarBackgroundColor(@ColorInt int i) {
        this.mStatusBarBackground = new ColorDrawable(i);
        invalidate();
    }
}
