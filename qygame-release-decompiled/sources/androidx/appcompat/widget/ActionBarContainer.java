package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.core.view.ViewCompat;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class ActionBarContainer extends FrameLayout {
    Drawable a;
    Drawable b;
    Drawable c;
    boolean d;
    boolean e;
    private View mActionBarView;
    private View mContextView;
    private int mHeight;
    private boolean mIsTransitioning;
    private View mTabContainer;

    public ActionBarContainer(Context context) {
        this(context, null);
    }

    public ActionBarContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        ViewCompat.setBackground(this, new ActionBarBackgroundDrawable(this));
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ActionBar);
        this.a = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ActionBar_background);
        this.b = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ActionBar_backgroundStacked);
        this.mHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ActionBar_height, -1);
        if (getId() == R.id.split_action_bar) {
            this.d = true;
            this.c = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ActionBar_backgroundSplit);
        }
        typedArrayObtainStyledAttributes.recycle();
        boolean z = false;
        if (!this.d ? !(this.a != null || this.b != null) : this.c == null) {
            z = true;
        }
        setWillNotDraw(z);
    }

    private int getMeasuredHeightWithMargins(View view) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        return view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
    }

    private boolean isCollapsed(View view) {
        return view == null || view.getVisibility() == 8 || view.getMeasuredHeight() == 0;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.a != null && this.a.isStateful()) {
            this.a.setState(getDrawableState());
        }
        if (this.b != null && this.b.isStateful()) {
            this.b.setState(getDrawableState());
        }
        if (this.c == null || !this.c.isStateful()) {
            return;
        }
        this.c.setState(getDrawableState());
    }

    public View getTabContainer() {
        return this.mTabContainer;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.a != null) {
            this.a.jumpToCurrentState();
        }
        if (this.b != null) {
            this.b.jumpToCurrentState();
        }
        if (this.c != null) {
            this.c.jumpToCurrentState();
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mActionBarView = findViewById(R.id.action_bar);
        this.mContextView = findViewById(R.id.action_context_bar);
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        super.onHoverEvent(motionEvent);
        return true;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.mIsTransitioning || super.onInterceptTouchEvent(motionEvent);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x004c A[PHI: r0
      0x004c: PHI (r0v9 boolean) = (r0v1 boolean), (r0v1 boolean), (r0v0 boolean) binds: [B:32:0x00a3, B:34:0x00a7, B:15:0x003b] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        Drawable drawable;
        int left;
        int top;
        int right;
        View view;
        super.onLayout(z, i, i2, i3, i4);
        View view2 = this.mTabContainer;
        boolean z2 = true;
        boolean z3 = false;
        boolean z4 = (view2 == null || view2.getVisibility() == 8) ? false : true;
        if (view2 != null && view2.getVisibility() != 8) {
            int measuredHeight = getMeasuredHeight();
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view2.getLayoutParams();
            view2.layout(i, (measuredHeight - view2.getMeasuredHeight()) - layoutParams.bottomMargin, i3, measuredHeight - layoutParams.bottomMargin);
        }
        if (!this.d) {
            if (this.a != null) {
                if (this.mActionBarView.getVisibility() == 0) {
                    drawable = this.a;
                    left = this.mActionBarView.getLeft();
                    top = this.mActionBarView.getTop();
                    right = this.mActionBarView.getRight();
                    view = this.mActionBarView;
                } else if (this.mContextView == null || this.mContextView.getVisibility() != 0) {
                    this.a.setBounds(0, 0, 0, 0);
                    z3 = true;
                } else {
                    drawable = this.a;
                    left = this.mContextView.getLeft();
                    top = this.mContextView.getTop();
                    right = this.mContextView.getRight();
                    view = this.mContextView;
                }
                drawable.setBounds(left, top, right, view.getBottom());
                z3 = true;
            }
            this.e = z4;
            if (z4 && this.b != null) {
                this.b.setBounds(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom());
            }
        } else if (this.c != null) {
            this.c.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        } else {
            z2 = z3;
        }
        if (z2) {
            invalidate();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005e  */
    @Override // android.widget.FrameLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onMeasure(int i, int i2) {
        int measuredHeightWithMargins;
        View view;
        if (this.mActionBarView == null && View.MeasureSpec.getMode(i2) == Integer.MIN_VALUE && this.mHeight >= 0) {
            i2 = View.MeasureSpec.makeMeasureSpec(Math.min(this.mHeight, View.MeasureSpec.getSize(i2)), Integer.MIN_VALUE);
        }
        super.onMeasure(i, i2);
        if (this.mActionBarView == null) {
            return;
        }
        int mode = View.MeasureSpec.getMode(i2);
        if (this.mTabContainer == null || this.mTabContainer.getVisibility() == 8 || mode == 1073741824) {
            return;
        }
        if (!isCollapsed(this.mActionBarView)) {
            view = this.mActionBarView;
        } else {
            if (isCollapsed(this.mContextView)) {
                measuredHeightWithMargins = 0;
                setMeasuredDimension(getMeasuredWidth(), Math.min(measuredHeightWithMargins + getMeasuredHeightWithMargins(this.mTabContainer), mode != Integer.MIN_VALUE ? View.MeasureSpec.getSize(i2) : ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED));
            }
            view = this.mContextView;
        }
        measuredHeightWithMargins = getMeasuredHeightWithMargins(view);
        setMeasuredDimension(getMeasuredWidth(), Math.min(measuredHeightWithMargins + getMeasuredHeightWithMargins(this.mTabContainer), mode != Integer.MIN_VALUE ? View.MeasureSpec.getSize(i2) : ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED));
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    public void setPrimaryBackground(Drawable drawable) {
        if (this.a != null) {
            this.a.setCallback(null);
            unscheduleDrawable(this.a);
        }
        this.a = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            if (this.mActionBarView != null) {
                this.a.setBounds(this.mActionBarView.getLeft(), this.mActionBarView.getTop(), this.mActionBarView.getRight(), this.mActionBarView.getBottom());
            }
        }
        boolean z = false;
        if (!this.d ? !(this.a != null || this.b != null) : this.c == null) {
            z = true;
        }
        setWillNotDraw(z);
        invalidate();
        if (Build.VERSION.SDK_INT >= 21) {
            invalidateOutline();
        }
    }

    public void setSplitBackground(Drawable drawable) {
        if (this.c != null) {
            this.c.setCallback(null);
            unscheduleDrawable(this.c);
        }
        this.c = drawable;
        boolean z = false;
        if (drawable != null) {
            drawable.setCallback(this);
            if (this.d && this.c != null) {
                this.c.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            }
        }
        if (!this.d ? !(this.a != null || this.b != null) : this.c == null) {
            z = true;
        }
        setWillNotDraw(z);
        invalidate();
        if (Build.VERSION.SDK_INT >= 21) {
            invalidateOutline();
        }
    }

    public void setStackedBackground(Drawable drawable) {
        if (this.b != null) {
            this.b.setCallback(null);
            unscheduleDrawable(this.b);
        }
        this.b = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            if (this.e && this.b != null) {
                this.b.setBounds(this.mTabContainer.getLeft(), this.mTabContainer.getTop(), this.mTabContainer.getRight(), this.mTabContainer.getBottom());
            }
        }
        boolean z = false;
        if (!this.d ? !(this.a != null || this.b != null) : this.c == null) {
            z = true;
        }
        setWillNotDraw(z);
        invalidate();
        if (Build.VERSION.SDK_INT >= 21) {
            invalidateOutline();
        }
    }

    public void setTabContainer(ScrollingTabContainerView scrollingTabContainerView) {
        if (this.mTabContainer != null) {
            removeView(this.mTabContainer);
        }
        this.mTabContainer = scrollingTabContainerView;
        if (scrollingTabContainerView != null) {
            addView(scrollingTabContainerView);
            ViewGroup.LayoutParams layoutParams = scrollingTabContainerView.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -2;
            scrollingTabContainerView.setAllowCollapse(false);
        }
    }

    public void setTransitioning(boolean z) {
        this.mIsTransitioning = z;
        setDescendantFocusability(z ? 393216 : 262144);
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        if (this.a != null) {
            this.a.setVisible(z, false);
        }
        if (this.b != null) {
            this.b.setVisible(z, false);
        }
        if (this.c != null) {
            this.c.setVisible(z, false);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback) {
        return null;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback, int i) {
        if (i != 0) {
            return super.startActionModeForChild(view, callback, i);
        }
        return null;
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return (drawable == this.a && !this.d) || (drawable == this.b && this.e) || ((drawable == this.c && this.d) || super.verifyDrawable(drawable));
    }
}
