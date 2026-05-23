package androidx.appcompat.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class ActionBarContextView extends AbsActionBarView {
    private View mClose;
    private View mCloseButton;
    private int mCloseItemLayout;
    private View mCustomView;
    private CharSequence mSubtitle;
    private int mSubtitleStyleRes;
    private TextView mSubtitleView;
    private CharSequence mTitle;
    private LinearLayout mTitleLayout;
    private boolean mTitleOptional;
    private int mTitleStyleRes;
    private TextView mTitleView;

    public ActionBarContextView(@NonNull Context context) {
        this(context, null);
    }

    public ActionBarContextView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.actionModeStyle);
    }

    public ActionBarContextView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.ActionMode, i, 0);
        ViewCompat.setBackground(this, tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.ActionMode_background));
        this.mTitleStyleRes = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.ActionMode_titleTextStyle, 0);
        this.mSubtitleStyleRes = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.ActionMode_subtitleTextStyle, 0);
        this.e = tintTypedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.ActionMode_height, 0);
        this.mCloseItemLayout = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.ActionMode_closeItemLayout, R.layout.abc_action_mode_close_item_material);
        tintTypedArrayObtainStyledAttributes.recycle();
    }

    private void initTitle() {
        if (this.mTitleLayout == null) {
            LayoutInflater.from(getContext()).inflate(R.layout.abc_action_bar_title_item, this);
            this.mTitleLayout = (LinearLayout) getChildAt(getChildCount() - 1);
            this.mTitleView = (TextView) this.mTitleLayout.findViewById(R.id.action_bar_title);
            this.mSubtitleView = (TextView) this.mTitleLayout.findViewById(R.id.action_bar_subtitle);
            if (this.mTitleStyleRes != 0) {
                this.mTitleView.setTextAppearance(getContext(), this.mTitleStyleRes);
            }
            if (this.mSubtitleStyleRes != 0) {
                this.mSubtitleView.setTextAppearance(getContext(), this.mSubtitleStyleRes);
            }
        }
        this.mTitleView.setText(this.mTitle);
        this.mSubtitleView.setText(this.mSubtitle);
        boolean z = !TextUtils.isEmpty(this.mTitle);
        boolean z2 = !TextUtils.isEmpty(this.mSubtitle);
        this.mSubtitleView.setVisibility(z2 ? 0 : 8);
        this.mTitleLayout.setVisibility((z || z2) ? 0 : 8);
        if (this.mTitleLayout.getParent() == null) {
            addView(this.mTitleLayout);
        }
    }

    @Override // androidx.appcompat.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void animateToVisibility(int i) {
        super.animateToVisibility(i);
    }

    @Override // androidx.appcompat.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean canShowOverflowMenu() {
        return super.canShowOverflowMenu();
    }

    public void closeMode() {
        if (this.mClose == null) {
            killMode();
        }
    }

    @Override // androidx.appcompat.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void dismissPopupMenus() {
        super.dismissPopupMenus();
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.MarginLayoutParams(-1, -2);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ViewGroup.MarginLayoutParams(getContext(), attributeSet);
    }

    @Override // androidx.appcompat.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ int getAnimatedVisibility() {
        return super.getAnimatedVisibility();
    }

    @Override // androidx.appcompat.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ int getContentHeight() {
        return super.getContentHeight();
    }

    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    @Override // androidx.appcompat.widget.AbsActionBarView
    public boolean hideOverflowMenu() {
        if (this.d != null) {
            return this.d.hideOverflowMenu();
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void initForMode(final ActionMode actionMode) {
        if (this.mClose != null) {
            if (this.mClose.getParent() == null) {
            }
            this.mCloseButton = this.mClose.findViewById(R.id.action_mode_close_button);
            this.mCloseButton.setOnClickListener(new View.OnClickListener() { // from class: androidx.appcompat.widget.ActionBarContextView.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    actionMode.finish();
                }
            });
            MenuBuilder menuBuilder = (MenuBuilder) actionMode.getMenu();
            if (this.d != null) {
                this.d.dismissPopupMenus();
            }
            this.d = new ActionMenuPresenter(getContext());
            this.d.setReserveOverflow(true);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1);
            menuBuilder.addMenuPresenter(this.d, this.b);
            this.c = (ActionMenuView) this.d.getMenuView(this);
            ViewCompat.setBackground(this.c, null);
            addView(this.c, layoutParams);
        }
        this.mClose = LayoutInflater.from(getContext()).inflate(this.mCloseItemLayout, (ViewGroup) this, false);
        addView(this.mClose);
        this.mCloseButton = this.mClose.findViewById(R.id.action_mode_close_button);
        this.mCloseButton.setOnClickListener(new View.OnClickListener() { // from class: androidx.appcompat.widget.ActionBarContextView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                actionMode.finish();
            }
        });
        MenuBuilder menuBuilder2 = (MenuBuilder) actionMode.getMenu();
        if (this.d != null) {
        }
        this.d = new ActionMenuPresenter(getContext());
        this.d.setReserveOverflow(true);
        ViewGroup.LayoutParams layoutParams2 = new ViewGroup.LayoutParams(-2, -1);
        menuBuilder2.addMenuPresenter(this.d, this.b);
        this.c = (ActionMenuView) this.d.getMenuView(this);
        ViewCompat.setBackground(this.c, null);
        addView(this.c, layoutParams2);
    }

    @Override // androidx.appcompat.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean isOverflowMenuShowPending() {
        return super.isOverflowMenuShowPending();
    }

    @Override // androidx.appcompat.widget.AbsActionBarView
    public boolean isOverflowMenuShowing() {
        if (this.d != null) {
            return this.d.isOverflowMenuShowing();
        }
        return false;
    }

    @Override // androidx.appcompat.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ boolean isOverflowReserved() {
        return super.isOverflowReserved();
    }

    public boolean isTitleOptional() {
        return this.mTitleOptional;
    }

    public void killMode() {
        removeAllViews();
        this.mCustomView = null;
        this.c = null;
        this.d = null;
        if (this.mCloseButton != null) {
            this.mCloseButton.setOnClickListener(null);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.d != null) {
            this.d.hideOverflowMenu();
            this.d.hideSubMenus();
        }
    }

    @Override // androidx.appcompat.widget.AbsActionBarView, android.view.View
    public /* bridge */ /* synthetic */ boolean onHoverEvent(MotionEvent motionEvent) {
        return super.onHoverEvent(motionEvent);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() != 32) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            return;
        }
        accessibilityEvent.setSource(this);
        accessibilityEvent.setClassName(getClass().getName());
        accessibilityEvent.setPackageName(getContext().getPackageName());
        accessibilityEvent.setContentDescription(this.mTitle);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int iA;
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this);
        int paddingRight = zIsLayoutRtl ? (i3 - i) - getPaddingRight() : getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingTop2 = ((i4 - i2) - getPaddingTop()) - getPaddingBottom();
        if (this.mClose == null || this.mClose.getVisibility() == 8) {
            iA = paddingRight;
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mClose.getLayoutParams();
            int i5 = zIsLayoutRtl ? marginLayoutParams.rightMargin : marginLayoutParams.leftMargin;
            int i6 = zIsLayoutRtl ? marginLayoutParams.leftMargin : marginLayoutParams.rightMargin;
            int iA2 = a(paddingRight, i5, zIsLayoutRtl);
            iA = a(iA2 + a(this.mClose, iA2, paddingTop, paddingTop2, zIsLayoutRtl), i6, zIsLayoutRtl);
        }
        if (this.mTitleLayout != null && this.mCustomView == null && this.mTitleLayout.getVisibility() != 8) {
            iA += a(this.mTitleLayout, iA, paddingTop, paddingTop2, zIsLayoutRtl);
        }
        int i7 = iA;
        if (this.mCustomView != null) {
            a(this.mCustomView, i7, paddingTop, paddingTop2, zIsLayoutRtl);
        }
        int paddingLeft = zIsLayoutRtl ? getPaddingLeft() : (i3 - i) - getPaddingRight();
        if (this.c != null) {
            a(this.c, paddingLeft, paddingTop, paddingTop2, !zIsLayoutRtl);
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        if (View.MeasureSpec.getMode(i) != 1073741824) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_width=\"match_parent\" (or fill_parent)");
        }
        if (View.MeasureSpec.getMode(i2) == 0) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used with android:layout_height=\"wrap_content\"");
        }
        int size = View.MeasureSpec.getSize(i);
        int size2 = this.e > 0 ? this.e : View.MeasureSpec.getSize(i2);
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
        int iMin = size2 - paddingTop;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(iMin, Integer.MIN_VALUE);
        if (this.mClose != null) {
            int iA = a(this.mClose, paddingLeft, iMakeMeasureSpec, 0);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mClose.getLayoutParams();
            paddingLeft = iA - (marginLayoutParams.leftMargin + marginLayoutParams.rightMargin);
        }
        if (this.c != null && this.c.getParent() == this) {
            paddingLeft = a(this.c, paddingLeft, iMakeMeasureSpec, 0);
        }
        if (this.mTitleLayout != null && this.mCustomView == null) {
            if (this.mTitleOptional) {
                this.mTitleLayout.measure(View.MeasureSpec.makeMeasureSpec(0, 0), iMakeMeasureSpec);
                int measuredWidth = this.mTitleLayout.getMeasuredWidth();
                boolean z = measuredWidth <= paddingLeft;
                if (z) {
                    paddingLeft -= measuredWidth;
                }
                this.mTitleLayout.setVisibility(z ? 0 : 8);
            } else {
                paddingLeft = a(this.mTitleLayout, paddingLeft, iMakeMeasureSpec, 0);
            }
        }
        if (this.mCustomView != null) {
            ViewGroup.LayoutParams layoutParams = this.mCustomView.getLayoutParams();
            int i3 = layoutParams.width != -2 ? 1073741824 : Integer.MIN_VALUE;
            if (layoutParams.width >= 0) {
                paddingLeft = Math.min(layoutParams.width, paddingLeft);
            }
            int i4 = layoutParams.height == -2 ? Integer.MIN_VALUE : 1073741824;
            if (layoutParams.height >= 0) {
                iMin = Math.min(layoutParams.height, iMin);
            }
            this.mCustomView.measure(View.MeasureSpec.makeMeasureSpec(paddingLeft, i3), View.MeasureSpec.makeMeasureSpec(iMin, i4));
        }
        if (this.e > 0) {
            setMeasuredDimension(size, size2);
            return;
        }
        int childCount = getChildCount();
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            int measuredHeight = getChildAt(i6).getMeasuredHeight() + paddingTop;
            if (measuredHeight > i5) {
                i5 = measuredHeight;
            }
        }
        setMeasuredDimension(size, i5);
    }

    @Override // androidx.appcompat.widget.AbsActionBarView, android.view.View
    public /* bridge */ /* synthetic */ boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }

    @Override // androidx.appcompat.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ void postShowOverflowMenu() {
        super.postShowOverflowMenu();
    }

    @Override // androidx.appcompat.widget.AbsActionBarView
    public void setContentHeight(int i) {
        this.e = i;
    }

    public void setCustomView(View view) {
        if (this.mCustomView != null) {
            removeView(this.mCustomView);
        }
        this.mCustomView = view;
        if (view != null && this.mTitleLayout != null) {
            removeView(this.mTitleLayout);
            this.mTitleLayout = null;
        }
        if (view != null) {
            addView(view);
        }
        requestLayout();
    }

    public void setSubtitle(CharSequence charSequence) {
        this.mSubtitle = charSequence;
        initTitle();
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        initTitle();
    }

    public void setTitleOptional(boolean z) {
        if (z != this.mTitleOptional) {
            requestLayout();
        }
        this.mTitleOptional = z;
    }

    @Override // androidx.appcompat.widget.AbsActionBarView, android.view.View
    public /* bridge */ /* synthetic */ void setVisibility(int i) {
        super.setVisibility(i);
    }

    @Override // androidx.appcompat.widget.AbsActionBarView
    public /* bridge */ /* synthetic */ ViewPropertyAnimatorCompat setupAnimatorToVisibility(int i, long j) {
        return super.setupAnimatorToVisibility(i, j);
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override // androidx.appcompat.widget.AbsActionBarView
    public boolean showOverflowMenu() {
        if (this.d != null) {
            return this.d.showOverflowMenu();
        }
        return false;
    }
}
