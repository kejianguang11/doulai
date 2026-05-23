package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.LinearLayoutCompat;

/* JADX INFO: loaded from: classes.dex */
public class ActionMenuView extends LinearLayoutCompat implements MenuBuilder.ItemInvoker, MenuView {
    private static final String TAG = "ActionMenuView";
    MenuBuilder.Callback a;
    OnMenuItemClickListener b;
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    private boolean mFormatItems;
    private int mFormatItemsWidth;
    private int mGeneratedItemPadding;
    private MenuBuilder mMenu;
    private int mMinCellSize;
    private Context mPopupContext;
    private int mPopupTheme;
    private ActionMenuPresenter mPresenter;
    private boolean mReserveOverflow;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public interface ActionMenuChildView {
        boolean needsDividerAfter();

        boolean needsDividerBefore();
    }

    private static class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        ActionMenuPresenterCallback() {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public void onCloseMenu(@NonNull MenuBuilder menuBuilder, boolean z) {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(@NonNull MenuBuilder menuBuilder) {
            return false;
        }
    }

    public static class LayoutParams extends LinearLayoutCompat.LayoutParams {
        boolean a;

        @ViewDebug.ExportedProperty
        public int cellsUsed;

        @ViewDebug.ExportedProperty
        public boolean expandable;

        @ViewDebug.ExportedProperty
        public int extraPixels;

        @ViewDebug.ExportedProperty
        public boolean isOverflowButton;

        @ViewDebug.ExportedProperty
        public boolean preventEdgeOffset;

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.isOverflowButton = false;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.LayoutParams) layoutParams);
            this.isOverflowButton = layoutParams.isOverflowButton;
        }
    }

    private class MenuBuilderCallback implements MenuBuilder.Callback {
        MenuBuilderCallback() {
        }

        @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
        public boolean onMenuItemSelected(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
            return ActionMenuView.this.b != null && ActionMenuView.this.b.onMenuItemClick(menuItem);
        }

        @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
        public void onMenuModeChange(@NonNull MenuBuilder menuBuilder) {
            if (ActionMenuView.this.a != null) {
                ActionMenuView.this.a.onMenuModeChange(menuBuilder);
            }
        }
    }

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public ActionMenuView(@NonNull Context context) {
        this(context, null);
    }

    public ActionMenuView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        setBaselineAligned(false);
        float f = context.getResources().getDisplayMetrics().density;
        this.mMinCellSize = (int) (56.0f * f);
        this.mGeneratedItemPadding = (int) (f * 4.0f);
        this.mPopupContext = context;
        this.mPopupTheme = 0;
    }

    static int a(View view, int i, int i2, int i3, int i4) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i3) - i4, View.MeasureSpec.getMode(i3));
        ActionMenuItemView actionMenuItemView = view instanceof ActionMenuItemView ? (ActionMenuItemView) view : null;
        boolean z = actionMenuItemView != null && actionMenuItemView.hasText();
        int i5 = 2;
        if (i2 <= 0 || (z && i2 < 2)) {
            i5 = 0;
        } else {
            view.measure(View.MeasureSpec.makeMeasureSpec(i2 * i, Integer.MIN_VALUE), iMakeMeasureSpec);
            int measuredWidth = view.getMeasuredWidth();
            int i6 = measuredWidth / i;
            if (measuredWidth % i != 0) {
                i6++;
            }
            if (!z || i6 >= 2) {
                i5 = i6;
            }
        }
        layoutParams.expandable = !layoutParams.isOverflowButton && z;
        layoutParams.cellsUsed = i5;
        view.measure(View.MeasureSpec.makeMeasureSpec(i * i5, 1073741824), iMakeMeasureSpec);
        return i5;
    }

    /* JADX WARN: Removed duplicated region for block: B:135:0x0246  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x024d A[ADDED_TO_REGION, LOOP:5: B:138:0x024d->B:143:0x026f, LOOP_START, PHI: r3 r32
      0x024d: PHI (r3v8 int) = (r3v7 int), (r3v9 int) binds: [B:137:0x024b, B:143:0x026f] A[DONT_GENERATE, DONT_INLINE]
      0x024d: PHI (r32v1 int) = (r32v0 int), (r32v2 int) binds: [B:137:0x024b, B:143:0x026f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0276  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x027b  */
    /* JADX WARN: Type inference failed for: r2v29 */
    /* JADX WARN: Type inference failed for: r2v30, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v45 */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v16, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r6v18 */
    /* JADX WARN: Type inference failed for: r6v20 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void onMeasureExactFormat(int i, int i2) {
        boolean z;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        ?? r6;
        int i9;
        int i10;
        ?? r2;
        int i11;
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int childMeasureSpec = getChildMeasureSpec(i2, paddingTop, -2);
        int i12 = size - paddingLeft;
        int i13 = i12 / this.mMinCellSize;
        int i14 = i12 % this.mMinCellSize;
        if (i13 == 0) {
            setMeasuredDimension(i12, 0);
            return;
        }
        int i15 = this.mMinCellSize + (i14 / i13);
        int childCount = getChildCount();
        int i16 = i13;
        int i17 = 0;
        int iMax = 0;
        boolean z2 = false;
        int i18 = 0;
        int i19 = 0;
        int i20 = 0;
        long j = 0;
        while (i17 < childCount) {
            View childAt = getChildAt(i17);
            int i21 = size2;
            if (childAt.getVisibility() == 8) {
                i9 = i12;
            } else {
                boolean z3 = childAt instanceof ActionMenuItemView;
                int i22 = i18 + 1;
                if (z3) {
                    i10 = i22;
                    i9 = i12;
                    r2 = 0;
                    childAt.setPadding(this.mGeneratedItemPadding, 0, this.mGeneratedItemPadding, 0);
                } else {
                    i9 = i12;
                    i10 = i22;
                    r2 = 0;
                }
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                layoutParams.a = r2;
                layoutParams.extraPixels = r2;
                layoutParams.cellsUsed = r2;
                layoutParams.expandable = r2;
                layoutParams.leftMargin = r2;
                layoutParams.rightMargin = r2;
                layoutParams.preventEdgeOffset = z3 && ((ActionMenuItemView) childAt).hasText();
                int iA = a(childAt, i15, layoutParams.isOverflowButton ? 1 : i16, childMeasureSpec, paddingTop);
                int iMax2 = Math.max(i19, iA);
                if (layoutParams.expandable) {
                    i20++;
                }
                if (layoutParams.isOverflowButton) {
                    z2 = true;
                }
                i16 -= iA;
                iMax = Math.max(iMax, childAt.getMeasuredHeight());
                if (iA == 1) {
                    i11 = iMax2;
                    j |= (long) (1 << i17);
                } else {
                    i11 = iMax2;
                }
                i18 = i10;
                i19 = i11;
            }
            i17++;
            size2 = i21;
            i12 = i9;
        }
        int i23 = i12;
        int i24 = size2;
        boolean z4 = z2 && i18 == 2;
        boolean z5 = false;
        while (i20 > 0 && i16 > 0) {
            int i25 = Integer.MAX_VALUE;
            int i26 = 0;
            int i27 = 0;
            long j2 = 0;
            while (i26 < childCount) {
                LayoutParams layoutParams2 = (LayoutParams) getChildAt(i26).getLayoutParams();
                boolean z6 = z5;
                if (layoutParams2.expandable) {
                    if (layoutParams2.cellsUsed < i25) {
                        j2 = 1 << i26;
                        i25 = layoutParams2.cellsUsed;
                        i27 = 1;
                    } else if (layoutParams2.cellsUsed == i25) {
                        j2 |= 1 << i26;
                        i27++;
                    }
                }
                i26++;
                z5 = z6;
            }
            z = z5;
            j |= j2;
            if (i27 > i16) {
                break;
            }
            int i28 = i25 + 1;
            int i29 = 0;
            while (i29 < childCount) {
                View childAt2 = getChildAt(i29);
                LayoutParams layoutParams3 = (LayoutParams) childAt2.getLayoutParams();
                int i30 = iMax;
                int i31 = childMeasureSpec;
                int i32 = childCount;
                long j3 = 1 << i29;
                if ((j2 & j3) != 0) {
                    if (z4 && layoutParams3.preventEdgeOffset) {
                        r6 = 1;
                        r6 = 1;
                        if (i16 == 1) {
                            childAt2.setPadding(this.mGeneratedItemPadding + i15, 0, this.mGeneratedItemPadding, 0);
                        }
                    } else {
                        r6 = 1;
                    }
                    layoutParams3.cellsUsed += r6;
                    layoutParams3.a = r6;
                    i16--;
                } else if (layoutParams3.cellsUsed == i28) {
                    j |= j3;
                }
                i29++;
                iMax = i30;
                childMeasureSpec = i31;
                childCount = i32;
            }
            z5 = true;
        }
        z = z5;
        int i33 = childMeasureSpec;
        int i34 = childCount;
        int i35 = iMax;
        if (!z2) {
            i3 = 1;
            boolean z7 = i18 == 1;
            if (i16 > 0 || j == 0 || (i16 >= i18 - i3 && !z7 && i19 <= i3)) {
                i4 = i34;
                i5 = 0;
            } else {
                float fBitCount = Long.bitCount(j);
                if (z7) {
                    i5 = 0;
                } else {
                    if ((j & 1) != 0) {
                        i5 = 0;
                        if (!((LayoutParams) getChildAt(0).getLayoutParams()).preventEdgeOffset) {
                            fBitCount -= 0.5f;
                        }
                    } else {
                        i5 = 0;
                    }
                    int i36 = i34 - 1;
                    if ((j & ((long) (1 << i36))) != 0 && !((LayoutParams) getChildAt(i36).getLayoutParams()).preventEdgeOffset) {
                        fBitCount -= 0.5f;
                    }
                }
                int i37 = fBitCount > 0.0f ? (int) ((i16 * i15) / fBitCount) : i5;
                boolean z8 = z;
                i4 = i34;
                for (int i38 = i5; i38 < i4; i38++) {
                    if ((j & ((long) (1 << i38))) != 0) {
                        View childAt3 = getChildAt(i38);
                        LayoutParams layoutParams4 = (LayoutParams) childAt3.getLayoutParams();
                        if (childAt3 instanceof ActionMenuItemView) {
                            layoutParams4.extraPixels = i37;
                            layoutParams4.a = true;
                            if (i38 == 0 && !layoutParams4.preventEdgeOffset) {
                                layoutParams4.leftMargin = (-i37) / 2;
                            }
                            z8 = true;
                        } else if (layoutParams4.isOverflowButton) {
                            layoutParams4.extraPixels = i37;
                            layoutParams4.a = true;
                            layoutParams4.rightMargin = (-i37) / 2;
                            z8 = true;
                        } else {
                            if (i38 != 0) {
                                layoutParams4.leftMargin = i37 / 2;
                            }
                            if (i38 != i4 - 1) {
                                layoutParams4.rightMargin = i37 / 2;
                            }
                        }
                    }
                }
                z = z8;
            }
            if (z) {
                while (i5 < i4) {
                    View childAt4 = getChildAt(i5);
                    LayoutParams layoutParams5 = (LayoutParams) childAt4.getLayoutParams();
                    if (layoutParams5.a) {
                        i8 = i33;
                        childAt4.measure(View.MeasureSpec.makeMeasureSpec((layoutParams5.cellsUsed * i15) + layoutParams5.extraPixels, 1073741824), i8);
                    } else {
                        i8 = i33;
                    }
                    i5++;
                    i33 = i8;
                }
            }
            if (mode == 1073741824) {
                i7 = i23;
                i6 = i35;
            } else {
                i6 = i24;
                i7 = i23;
            }
            setMeasuredDimension(i7, i6);
        }
        i3 = 1;
        if (i16 > 0) {
            i4 = i34;
            i5 = 0;
        }
        if (z) {
        }
        if (mode == 1073741824) {
        }
        setMeasuredDimension(i7, i6);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public LayoutParams generateDefaultLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 16;
        return layoutParams;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams == null) {
            return generateDefaultLayoutParams();
        }
        LayoutParams layoutParams2 = layoutParams instanceof LayoutParams ? new LayoutParams((LayoutParams) layoutParams) : new LayoutParams(layoutParams);
        if (layoutParams2.gravity <= 0) {
            layoutParams2.gravity = 16;
        }
        return layoutParams2;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    protected boolean a(int i) {
        boolean zNeedsDividerAfter = false;
        if (i == 0) {
            return false;
        }
        KeyEvent.Callback childAt = getChildAt(i - 1);
        KeyEvent.Callback childAt2 = getChildAt(i);
        if (i < getChildCount() && (childAt instanceof ActionMenuChildView)) {
            zNeedsDividerAfter = false | ((ActionMenuChildView) childAt).needsDividerAfter();
        }
        return (i <= 0 || !(childAt2 instanceof ActionMenuChildView)) ? zNeedsDividerAfter : zNeedsDividerAfter | ((ActionMenuChildView) childAt2).needsDividerBefore();
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void dismissPopupMenus() {
        if (this.mPresenter != null) {
            this.mPresenter.dismissPopupMenus();
        }
    }

    @Override // android.view.View
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public LayoutParams generateOverflowButtonLayoutParams() {
        LayoutParams layoutParamsGenerateDefaultLayoutParams = generateDefaultLayoutParams();
        layoutParamsGenerateDefaultLayoutParams.isOverflowButton = true;
        return layoutParamsGenerateDefaultLayoutParams;
    }

    public Menu getMenu() {
        if (this.mMenu == null) {
            Context context = getContext();
            this.mMenu = new MenuBuilder(context);
            this.mMenu.setCallback(new MenuBuilderCallback());
            this.mPresenter = new ActionMenuPresenter(context);
            this.mPresenter.setReserveOverflow(true);
            this.mPresenter.setCallback(this.mActionMenuPresenterCallback != null ? this.mActionMenuPresenterCallback : new ActionMenuPresenterCallback());
            this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
            this.mPresenter.setMenuView(this);
        }
        return this.mMenu;
    }

    @Nullable
    public Drawable getOverflowIcon() {
        getMenu();
        return this.mPresenter.getOverflowIcon();
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    @Override // androidx.appcompat.view.menu.MenuView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int getWindowAnimations() {
        return 0;
    }

    public boolean hideOverflowMenu() {
        return this.mPresenter != null && this.mPresenter.hideOverflowMenu();
    }

    @Override // androidx.appcompat.view.menu.MenuView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void initialize(MenuBuilder menuBuilder) {
        this.mMenu = menuBuilder;
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder.ItemInvoker
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean invokeItem(MenuItemImpl menuItemImpl) {
        return this.mMenu.performItemAction(menuItemImpl, 0);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean isOverflowMenuShowPending() {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowPending();
    }

    public boolean isOverflowMenuShowing() {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowing();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mPresenter != null) {
            this.mPresenter.updateMenuView(false);
            if (this.mPresenter.isOverflowMenuShowing()) {
                this.mPresenter.hideOverflowMenu();
                this.mPresenter.showOverflowMenu();
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dismissPopupMenus();
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int width;
        int paddingLeft;
        if (!this.mFormatItems) {
            super.onLayout(z, i, i2, i3, i4);
            return;
        }
        int childCount = getChildCount();
        int i7 = (i4 - i2) / 2;
        int dividerWidth = getDividerWidth();
        int i8 = i3 - i;
        int paddingRight = (i8 - getPaddingRight()) - getPaddingLeft();
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this);
        int measuredWidth = paddingRight;
        int i9 = 0;
        int i10 = 0;
        for (int i11 = 0; i11 < childCount; i11++) {
            View childAt = getChildAt(i11);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isOverflowButton) {
                    int measuredWidth2 = childAt.getMeasuredWidth();
                    if (a(i11)) {
                        measuredWidth2 += dividerWidth;
                    }
                    int measuredHeight = childAt.getMeasuredHeight();
                    if (zIsLayoutRtl) {
                        paddingLeft = getPaddingLeft() + layoutParams.leftMargin;
                        width = paddingLeft + measuredWidth2;
                    } else {
                        width = (getWidth() - getPaddingRight()) - layoutParams.rightMargin;
                        paddingLeft = width - measuredWidth2;
                    }
                    int i12 = i7 - (measuredHeight / 2);
                    childAt.layout(paddingLeft, i12, width, measuredHeight + i12);
                    measuredWidth -= measuredWidth2;
                    i9 = 1;
                } else {
                    measuredWidth -= (childAt.getMeasuredWidth() + layoutParams.leftMargin) + layoutParams.rightMargin;
                    a(i11);
                    i10++;
                }
            }
        }
        if (childCount == 1 && i9 == 0) {
            View childAt2 = getChildAt(0);
            int measuredWidth3 = childAt2.getMeasuredWidth();
            int measuredHeight2 = childAt2.getMeasuredHeight();
            int i13 = (i8 / 2) - (measuredWidth3 / 2);
            int i14 = i7 - (measuredHeight2 / 2);
            childAt2.layout(i13, i14, measuredWidth3 + i13, measuredHeight2 + i14);
            return;
        }
        int i15 = i10 - (i9 ^ 1);
        if (i15 > 0) {
            i6 = measuredWidth / i15;
            i5 = 0;
        } else {
            i5 = 0;
            i6 = 0;
        }
        int iMax = Math.max(i5, i6);
        if (zIsLayoutRtl) {
            int width2 = getWidth() - getPaddingRight();
            while (i5 < childCount) {
                View childAt3 = getChildAt(i5);
                LayoutParams layoutParams2 = (LayoutParams) childAt3.getLayoutParams();
                if (childAt3.getVisibility() != 8 && !layoutParams2.isOverflowButton) {
                    int i16 = width2 - layoutParams2.rightMargin;
                    int measuredWidth4 = childAt3.getMeasuredWidth();
                    int measuredHeight3 = childAt3.getMeasuredHeight();
                    int i17 = i7 - (measuredHeight3 / 2);
                    childAt3.layout(i16 - measuredWidth4, i17, i16, measuredHeight3 + i17);
                    width2 = i16 - ((measuredWidth4 + layoutParams2.leftMargin) + iMax);
                }
                i5++;
            }
            return;
        }
        int paddingLeft2 = getPaddingLeft();
        while (i5 < childCount) {
            View childAt4 = getChildAt(i5);
            LayoutParams layoutParams3 = (LayoutParams) childAt4.getLayoutParams();
            if (childAt4.getVisibility() != 8 && !layoutParams3.isOverflowButton) {
                int i18 = paddingLeft2 + layoutParams3.leftMargin;
                int measuredWidth5 = childAt4.getMeasuredWidth();
                int measuredHeight4 = childAt4.getMeasuredHeight();
                int i19 = i7 - (measuredHeight4 / 2);
                childAt4.layout(i18, i19, i18 + measuredWidth5, measuredHeight4 + i19);
                paddingLeft2 = i18 + measuredWidth5 + layoutParams3.rightMargin + iMax;
            }
            i5++;
        }
    }

    @Override // androidx.appcompat.widget.LinearLayoutCompat, android.view.View
    protected void onMeasure(int i, int i2) {
        boolean z = this.mFormatItems;
        this.mFormatItems = View.MeasureSpec.getMode(i) == 1073741824;
        if (z != this.mFormatItems) {
            this.mFormatItemsWidth = 0;
        }
        int size = View.MeasureSpec.getSize(i);
        if (this.mFormatItems && this.mMenu != null && size != this.mFormatItemsWidth) {
            this.mFormatItemsWidth = size;
            this.mMenu.onItemsChanged(true);
        }
        int childCount = getChildCount();
        if (this.mFormatItems && childCount > 0) {
            onMeasureExactFormat(i, i2);
            return;
        }
        for (int i3 = 0; i3 < childCount; i3++) {
            LayoutParams layoutParams = (LayoutParams) getChildAt(i3).getLayoutParams();
            layoutParams.rightMargin = 0;
            layoutParams.leftMargin = 0;
        }
        super.onMeasure(i, i2);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public MenuBuilder peekMenu() {
        return this.mMenu;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setExpandedActionViewsExclusive(boolean z) {
        this.mPresenter.setExpandedActionViewsExclusive(z);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mActionMenuPresenterCallback = callback;
        this.a = callback2;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.b = onMenuItemClickListener;
    }

    public void setOverflowIcon(@Nullable Drawable drawable) {
        getMenu();
        this.mPresenter.setOverflowIcon(drawable);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setOverflowReserved(boolean z) {
        this.mReserveOverflow = z;
    }

    public void setPopupTheme(@StyleRes int i) {
        if (this.mPopupTheme != i) {
            this.mPopupTheme = i;
            if (i == 0) {
                this.mPopupContext = getContext();
            } else {
                this.mPopupContext = new ContextThemeWrapper(getContext(), i);
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void setPresenter(ActionMenuPresenter actionMenuPresenter) {
        this.mPresenter = actionMenuPresenter;
        this.mPresenter.setMenuView(this);
    }

    public boolean showOverflowMenu() {
        return this.mPresenter != null && this.mPresenter.showOverflowMenu();
    }
}
