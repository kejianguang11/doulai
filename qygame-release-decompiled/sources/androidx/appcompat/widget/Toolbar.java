package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.R;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.CollapsibleActionView;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.appcompat.widget.ActionMenuView;
import androidx.core.view.GravityCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.view.AbsSavedState;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Toolbar extends ViewGroup {
    private static final String TAG = "Toolbar";
    ImageButton a;
    View b;
    int c;
    OnMenuItemClickListener d;
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    private CharSequence mCollapseDescription;
    private Drawable mCollapseIcon;
    private boolean mCollapsible;
    private int mContentInsetEndWithActions;
    private int mContentInsetStartWithNavigation;
    private RtlSpacingHelper mContentInsets;
    private boolean mEatingHover;
    private boolean mEatingTouch;
    private ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    private int mGravity;
    private final ArrayList<View> mHiddenViews;
    private ImageView mLogoView;
    private int mMaxButtonHeight;
    private MenuBuilder.Callback mMenuBuilderCallback;
    private ActionMenuView mMenuView;
    private final ActionMenuView.OnMenuItemClickListener mMenuViewItemClickListener;
    private ImageButton mNavButtonView;
    private ActionMenuPresenter mOuterActionMenuPresenter;
    private Context mPopupContext;
    private int mPopupTheme;
    private final Runnable mShowOverflowMenuRunnable;
    private CharSequence mSubtitleText;
    private int mSubtitleTextAppearance;
    private ColorStateList mSubtitleTextColor;
    private TextView mSubtitleTextView;
    private final int[] mTempMargins;
    private final ArrayList<View> mTempViews;
    private int mTitleMarginBottom;
    private int mTitleMarginEnd;
    private int mTitleMarginStart;
    private int mTitleMarginTop;
    private CharSequence mTitleText;
    private int mTitleTextAppearance;
    private ColorStateList mTitleTextColor;
    private TextView mTitleTextView;
    private ToolbarWidgetWrapper mWrapper;

    private class ExpandedActionViewMenuPresenter implements MenuPresenter {
        MenuBuilder a;
        MenuItemImpl b;

        ExpandedActionViewMenuPresenter() {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
            if (Toolbar.this.b instanceof CollapsibleActionView) {
                ((CollapsibleActionView) Toolbar.this.b).onActionViewCollapsed();
            }
            Toolbar.this.removeView(Toolbar.this.b);
            Toolbar.this.removeView(Toolbar.this.a);
            Toolbar.this.b = null;
            Toolbar.this.d();
            this.b = null;
            Toolbar.this.requestLayout();
            menuItemImpl.setActionViewExpanded(false);
            return true;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
            Toolbar.this.a();
            ViewParent parent = Toolbar.this.a.getParent();
            if (parent != Toolbar.this) {
                if (parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(Toolbar.this.a);
                }
                Toolbar.this.addView(Toolbar.this.a);
            }
            Toolbar.this.b = menuItemImpl.getActionView();
            this.b = menuItemImpl;
            ViewParent parent2 = Toolbar.this.b.getParent();
            if (parent2 != Toolbar.this) {
                if (parent2 instanceof ViewGroup) {
                    ((ViewGroup) parent2).removeView(Toolbar.this.b);
                }
                LayoutParams layoutParamsGenerateDefaultLayoutParams = Toolbar.this.generateDefaultLayoutParams();
                layoutParamsGenerateDefaultLayoutParams.gravity = 8388611 | (Toolbar.this.c & 112);
                layoutParamsGenerateDefaultLayoutParams.a = 2;
                Toolbar.this.b.setLayoutParams(layoutParamsGenerateDefaultLayoutParams);
                Toolbar.this.addView(Toolbar.this.b);
            }
            Toolbar.this.c();
            Toolbar.this.requestLayout();
            menuItemImpl.setActionViewExpanded(true);
            if (Toolbar.this.b instanceof CollapsibleActionView) {
                ((CollapsibleActionView) Toolbar.this.b).onActionViewExpanded();
            }
            return true;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public boolean flagActionItems() {
            return false;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public int getId() {
            return 0;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public MenuView getMenuView(ViewGroup viewGroup) {
            return null;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public void initForMenu(Context context, MenuBuilder menuBuilder) {
            if (this.a != null && this.b != null) {
                this.a.collapseItemActionView(this.b);
            }
            this.a = menuBuilder;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public Parcelable onSaveInstanceState() {
            return null;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            return false;
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public void setCallback(MenuPresenter.Callback callback) {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter
        public void updateMenuView(boolean z) {
            if (this.b != null) {
                boolean z2 = false;
                if (this.a != null) {
                    int size = this.a.size();
                    int i = 0;
                    while (true) {
                        if (i >= size) {
                            break;
                        }
                        if (this.a.getItem(i) == this.b) {
                            z2 = true;
                            break;
                        }
                        i++;
                    }
                }
                if (z2) {
                    return;
                }
                collapseItemActionView(this.a, this.b);
            }
        }
    }

    public static class LayoutParams extends ActionBar.LayoutParams {
        int a;

        public LayoutParams(int i) {
            this(-2, -1, i);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.a = 0;
            this.gravity = 8388627;
        }

        public LayoutParams(int i, int i2, int i3) {
            super(i, i2);
            this.a = 0;
            this.gravity = i3;
        }

        public LayoutParams(@NonNull Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.a = 0;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.a = 0;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.a = 0;
            a(marginLayoutParams);
        }

        public LayoutParams(ActionBar.LayoutParams layoutParams) {
            super(layoutParams);
            this.a = 0;
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ActionBar.LayoutParams) layoutParams);
            this.a = 0;
            this.a = layoutParams.a;
        }

        void a(ViewGroup.MarginLayoutParams marginLayoutParams) {
            this.leftMargin = marginLayoutParams.leftMargin;
            this.topMargin = marginLayoutParams.topMargin;
            this.rightMargin = marginLayoutParams.rightMargin;
            this.bottomMargin = marginLayoutParams.bottomMargin;
        }
    }

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: androidx.appcompat.widget.Toolbar.SavedState.1
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
        boolean b;

        public SavedState(Parcel parcel) {
            this(parcel, null);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.a = parcel.readInt();
            this.b = parcel.readInt() != 0;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.a);
            parcel.writeInt(this.b ? 1 : 0);
        }
    }

    public Toolbar(@NonNull Context context) {
        this(context, null);
    }

    public Toolbar(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.toolbarStyle);
    }

    public Toolbar(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mGravity = 8388627;
        this.mTempViews = new ArrayList<>();
        this.mHiddenViews = new ArrayList<>();
        this.mTempMargins = new int[2];
        this.mMenuViewItemClickListener = new ActionMenuView.OnMenuItemClickListener() { // from class: androidx.appcompat.widget.Toolbar.1
            @Override // androidx.appcompat.widget.ActionMenuView.OnMenuItemClickListener
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (Toolbar.this.d != null) {
                    return Toolbar.this.d.onMenuItemClick(menuItem);
                }
                return false;
            }
        };
        this.mShowOverflowMenuRunnable = new Runnable() { // from class: androidx.appcompat.widget.Toolbar.2
            @Override // java.lang.Runnable
            public void run() {
                Toolbar.this.showOverflowMenu();
            }
        };
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(getContext(), attributeSet, R.styleable.Toolbar, i, 0);
        ViewCompat.saveAttributeDataForStyleable(this, context, R.styleable.Toolbar, attributeSet, tintTypedArrayObtainStyledAttributes.getWrappedTypeArray(), i, 0);
        this.mTitleTextAppearance = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.Toolbar_titleTextAppearance, 0);
        this.mSubtitleTextAppearance = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.Toolbar_subtitleTextAppearance, 0);
        this.mGravity = tintTypedArrayObtainStyledAttributes.getInteger(R.styleable.Toolbar_android_gravity, this.mGravity);
        this.c = tintTypedArrayObtainStyledAttributes.getInteger(R.styleable.Toolbar_buttonGravity, 48);
        int dimensionPixelOffset = tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_titleMargin, 0);
        dimensionPixelOffset = tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.Toolbar_titleMargins) ? tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_titleMargins, dimensionPixelOffset) : dimensionPixelOffset;
        this.mTitleMarginBottom = dimensionPixelOffset;
        this.mTitleMarginTop = dimensionPixelOffset;
        this.mTitleMarginEnd = dimensionPixelOffset;
        this.mTitleMarginStart = dimensionPixelOffset;
        int dimensionPixelOffset2 = tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginStart, -1);
        if (dimensionPixelOffset2 >= 0) {
            this.mTitleMarginStart = dimensionPixelOffset2;
        }
        int dimensionPixelOffset3 = tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginEnd, -1);
        if (dimensionPixelOffset3 >= 0) {
            this.mTitleMarginEnd = dimensionPixelOffset3;
        }
        int dimensionPixelOffset4 = tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginTop, -1);
        if (dimensionPixelOffset4 >= 0) {
            this.mTitleMarginTop = dimensionPixelOffset4;
        }
        int dimensionPixelOffset5 = tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginBottom, -1);
        if (dimensionPixelOffset5 >= 0) {
            this.mTitleMarginBottom = dimensionPixelOffset5;
        }
        this.mMaxButtonHeight = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.Toolbar_maxButtonHeight, -1);
        int dimensionPixelOffset6 = tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetStart, Integer.MIN_VALUE);
        int dimensionPixelOffset7 = tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetEnd, Integer.MIN_VALUE);
        int dimensionPixelSize = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.Toolbar_contentInsetLeft, 0);
        int dimensionPixelSize2 = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.Toolbar_contentInsetRight, 0);
        ensureContentInsets();
        this.mContentInsets.setAbsolute(dimensionPixelSize, dimensionPixelSize2);
        if (dimensionPixelOffset6 != Integer.MIN_VALUE || dimensionPixelOffset7 != Integer.MIN_VALUE) {
            this.mContentInsets.setRelative(dimensionPixelOffset6, dimensionPixelOffset7);
        }
        this.mContentInsetStartWithNavigation = tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetStartWithNavigation, Integer.MIN_VALUE);
        this.mContentInsetEndWithActions = tintTypedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetEndWithActions, Integer.MIN_VALUE);
        this.mCollapseIcon = tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.Toolbar_collapseIcon);
        this.mCollapseDescription = tintTypedArrayObtainStyledAttributes.getText(R.styleable.Toolbar_collapseContentDescription);
        CharSequence text = tintTypedArrayObtainStyledAttributes.getText(R.styleable.Toolbar_title);
        if (!TextUtils.isEmpty(text)) {
            setTitle(text);
        }
        CharSequence text2 = tintTypedArrayObtainStyledAttributes.getText(R.styleable.Toolbar_subtitle);
        if (!TextUtils.isEmpty(text2)) {
            setSubtitle(text2);
        }
        this.mPopupContext = getContext();
        setPopupTheme(tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.Toolbar_popupTheme, 0));
        Drawable drawable = tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.Toolbar_navigationIcon);
        if (drawable != null) {
            setNavigationIcon(drawable);
        }
        CharSequence text3 = tintTypedArrayObtainStyledAttributes.getText(R.styleable.Toolbar_navigationContentDescription);
        if (!TextUtils.isEmpty(text3)) {
            setNavigationContentDescription(text3);
        }
        Drawable drawable2 = tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.Toolbar_logo);
        if (drawable2 != null) {
            setLogo(drawable2);
        }
        CharSequence text4 = tintTypedArrayObtainStyledAttributes.getText(R.styleable.Toolbar_logoDescription);
        if (!TextUtils.isEmpty(text4)) {
            setLogoDescription(text4);
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.Toolbar_titleTextColor)) {
            setTitleTextColor(tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.Toolbar_titleTextColor));
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.Toolbar_subtitleTextColor)) {
            setSubtitleTextColor(tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.Toolbar_subtitleTextColor));
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.Toolbar_menu)) {
            inflateMenu(tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.Toolbar_menu, 0));
        }
        tintTypedArrayObtainStyledAttributes.recycle();
    }

    private void addCustomViewsWithGravity(List<View> list, int i) {
        boolean z = ViewCompat.getLayoutDirection(this) == 1;
        int childCount = getChildCount();
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection(this));
        list.clear();
        if (!z) {
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.a == 0 && shouldLayout(childAt) && getChildHorizontalGravity(layoutParams.gravity) == absoluteGravity) {
                    list.add(childAt);
                }
            }
            return;
        }
        for (int i3 = childCount - 1; i3 >= 0; i3--) {
            View childAt2 = getChildAt(i3);
            LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
            if (layoutParams2.a == 0 && shouldLayout(childAt2) && getChildHorizontalGravity(layoutParams2.gravity) == absoluteGravity) {
                list.add(childAt2);
            }
        }
    }

    private void addSystemView(View view, boolean z) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        LayoutParams layoutParamsGenerateDefaultLayoutParams = layoutParams == null ? generateDefaultLayoutParams() : !checkLayoutParams(layoutParams) ? generateLayoutParams(layoutParams) : (LayoutParams) layoutParams;
        layoutParamsGenerateDefaultLayoutParams.a = 1;
        if (!z || this.b == null) {
            addView(view, layoutParamsGenerateDefaultLayoutParams);
        } else {
            view.setLayoutParams(layoutParamsGenerateDefaultLayoutParams);
            this.mHiddenViews.add(view);
        }
    }

    private void ensureContentInsets() {
        if (this.mContentInsets == null) {
            this.mContentInsets = new RtlSpacingHelper();
        }
    }

    private void ensureLogoView() {
        if (this.mLogoView == null) {
            this.mLogoView = new AppCompatImageView(getContext());
        }
    }

    private void ensureMenu() {
        ensureMenuView();
        if (this.mMenuView.peekMenu() == null) {
            MenuBuilder menuBuilder = (MenuBuilder) this.mMenuView.getMenu();
            if (this.mExpandedMenuPresenter == null) {
                this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
            }
            this.mMenuView.setExpandedActionViewsExclusive(true);
            menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
        }
    }

    private void ensureMenuView() {
        if (this.mMenuView == null) {
            this.mMenuView = new ActionMenuView(getContext());
            this.mMenuView.setPopupTheme(this.mPopupTheme);
            this.mMenuView.setOnMenuItemClickListener(this.mMenuViewItemClickListener);
            this.mMenuView.setMenuCallbacks(this.mActionMenuPresenterCallback, this.mMenuBuilderCallback);
            LayoutParams layoutParamsGenerateDefaultLayoutParams = generateDefaultLayoutParams();
            layoutParamsGenerateDefaultLayoutParams.gravity = 8388613 | (this.c & 112);
            this.mMenuView.setLayoutParams(layoutParamsGenerateDefaultLayoutParams);
            addSystemView(this.mMenuView, false);
        }
    }

    private void ensureNavButtonView() {
        if (this.mNavButtonView == null) {
            this.mNavButtonView = new AppCompatImageButton(getContext(), null, R.attr.toolbarNavigationButtonStyle);
            LayoutParams layoutParamsGenerateDefaultLayoutParams = generateDefaultLayoutParams();
            layoutParamsGenerateDefaultLayoutParams.gravity = 8388611 | (this.c & 112);
            this.mNavButtonView.setLayoutParams(layoutParamsGenerateDefaultLayoutParams);
        }
    }

    private int getChildHorizontalGravity(int i) {
        int layoutDirection = ViewCompat.getLayoutDirection(this);
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i, layoutDirection) & 7;
        return (absoluteGravity == 1 || absoluteGravity == 3 || absoluteGravity == 5) ? absoluteGravity : layoutDirection == 1 ? 5 : 3;
    }

    private int getChildTop(View view, int i) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int measuredHeight = view.getMeasuredHeight();
        int i2 = i > 0 ? (measuredHeight - i) / 2 : 0;
        int childVerticalGravity = getChildVerticalGravity(layoutParams.gravity);
        if (childVerticalGravity == 48) {
            return getPaddingTop() - i2;
        }
        if (childVerticalGravity == 80) {
            return (((getHeight() - getPaddingBottom()) - measuredHeight) - layoutParams.bottomMargin) - i2;
        }
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int height = getHeight();
        int iMax = (((height - paddingTop) - paddingBottom) - measuredHeight) / 2;
        if (iMax < layoutParams.topMargin) {
            iMax = layoutParams.topMargin;
        } else {
            int i3 = (((height - paddingBottom) - measuredHeight) - iMax) - paddingTop;
            if (i3 < layoutParams.bottomMargin) {
                iMax = Math.max(0, iMax - (layoutParams.bottomMargin - i3));
            }
        }
        return paddingTop + iMax;
    }

    private int getChildVerticalGravity(int i) {
        int i2 = i & 112;
        return (i2 == 16 || i2 == 48 || i2 == 80) ? i2 : this.mGravity & 112;
    }

    private int getHorizontalMargins(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return MarginLayoutParamsCompat.getMarginStart(marginLayoutParams) + MarginLayoutParamsCompat.getMarginEnd(marginLayoutParams);
    }

    private MenuInflater getMenuInflater() {
        return new SupportMenuInflater(getContext());
    }

    private int getVerticalMargins(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
    }

    private int getViewListMeasuredWidth(List<View> list, int[] iArr) {
        int i = iArr[0];
        int i2 = iArr[1];
        int size = list.size();
        int measuredWidth = 0;
        int i3 = i2;
        int i4 = 0;
        while (i4 < size) {
            View view = list.get(i4);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            int i5 = layoutParams.leftMargin - i;
            int i6 = layoutParams.rightMargin - i3;
            int iMax = Math.max(0, i5);
            int iMax2 = Math.max(0, i6);
            int iMax3 = Math.max(0, -i5);
            int iMax4 = Math.max(0, -i6);
            measuredWidth += iMax + view.getMeasuredWidth() + iMax2;
            i4++;
            i3 = iMax4;
            i = iMax3;
        }
        return measuredWidth;
    }

    private boolean isChildOrHidden(View view) {
        return view.getParent() == this || this.mHiddenViews.contains(view);
    }

    private int layoutChildLeft(View view, int i, int[] iArr, int i2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int i3 = layoutParams.leftMargin - iArr[0];
        int iMax = i + Math.max(0, i3);
        iArr[0] = Math.max(0, -i3);
        int childTop = getChildTop(view, i2);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(iMax, childTop, iMax + measuredWidth, view.getMeasuredHeight() + childTop);
        return iMax + measuredWidth + layoutParams.rightMargin;
    }

    private int layoutChildRight(View view, int i, int[] iArr, int i2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int i3 = layoutParams.rightMargin - iArr[1];
        int iMax = i - Math.max(0, i3);
        iArr[1] = Math.max(0, -i3);
        int childTop = getChildTop(view, i2);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(iMax - measuredWidth, childTop, iMax, view.getMeasuredHeight() + childTop);
        return iMax - (measuredWidth + layoutParams.leftMargin);
    }

    private int measureChildCollapseMargins(View view, int i, int i2, int i3, int i4, int[] iArr) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int i5 = marginLayoutParams.leftMargin - iArr[0];
        int i6 = marginLayoutParams.rightMargin - iArr[1];
        int iMax = Math.max(0, i5) + Math.max(0, i6);
        iArr[0] = Math.max(0, -i5);
        iArr[1] = Math.max(0, -i6);
        view.measure(getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + iMax + i2, marginLayoutParams.width), getChildMeasureSpec(i3, getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height));
        return view.getMeasuredWidth() + iMax;
    }

    private void measureChildConstrained(View view, int i, int i2, int i3, int i4, int i5) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int childMeasureSpec = getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width);
        int childMeasureSpec2 = getChildMeasureSpec(i3, getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height);
        int mode = View.MeasureSpec.getMode(childMeasureSpec2);
        if (mode != 1073741824 && i5 >= 0) {
            if (mode != 0) {
                i5 = Math.min(View.MeasureSpec.getSize(childMeasureSpec2), i5);
            }
            childMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i5, 1073741824);
        }
        view.measure(childMeasureSpec, childMeasureSpec2);
    }

    private void postShowOverflowMenu() {
        removeCallbacks(this.mShowOverflowMenuRunnable);
        post(this.mShowOverflowMenuRunnable);
    }

    private boolean shouldCollapse() {
        if (!this.mCollapsible) {
            return false;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (shouldLayout(childAt) && childAt.getMeasuredWidth() > 0 && childAt.getMeasuredHeight() > 0) {
                return false;
            }
        }
        return true;
    }

    private boolean shouldLayout(View view) {
        return (view == null || view.getParent() != this || view.getVisibility() == 8) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams ? new LayoutParams((LayoutParams) layoutParams) : layoutParams instanceof ActionBar.LayoutParams ? new LayoutParams((ActionBar.LayoutParams) layoutParams) : layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    void a() {
        if (this.a == null) {
            this.a = new AppCompatImageButton(getContext(), null, R.attr.toolbarNavigationButtonStyle);
            this.a.setImageDrawable(this.mCollapseIcon);
            this.a.setContentDescription(this.mCollapseDescription);
            LayoutParams layoutParamsGenerateDefaultLayoutParams = generateDefaultLayoutParams();
            layoutParamsGenerateDefaultLayoutParams.gravity = 8388611 | (this.c & 112);
            layoutParamsGenerateDefaultLayoutParams.a = 2;
            this.a.setLayoutParams(layoutParamsGenerateDefaultLayoutParams);
            this.a.setOnClickListener(new View.OnClickListener() { // from class: androidx.appcompat.widget.Toolbar.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    Toolbar.this.collapseActionView();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    void c() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (((LayoutParams) childAt.getLayoutParams()).a != 2 && childAt != this.mMenuView) {
                removeViewAt(childCount);
                this.mHiddenViews.add(childAt);
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean canShowOverflowMenu() {
        return getVisibility() == 0 && this.mMenuView != null && this.mMenuView.isOverflowReserved();
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams) && (layoutParams instanceof LayoutParams);
    }

    public void collapseActionView() {
        MenuItemImpl menuItemImpl = this.mExpandedMenuPresenter == null ? null : this.mExpandedMenuPresenter.b;
        if (menuItemImpl != null) {
            menuItemImpl.collapseActionView();
        }
    }

    void d() {
        for (int size = this.mHiddenViews.size() - 1; size >= 0; size--) {
            addView(this.mHiddenViews.get(size));
        }
        this.mHiddenViews.clear();
    }

    public void dismissPopupMenus() {
        if (this.mMenuView != null) {
            this.mMenuView.dismissPopupMenus();
        }
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Nullable
    public CharSequence getCollapseContentDescription() {
        if (this.a != null) {
            return this.a.getContentDescription();
        }
        return null;
    }

    @Nullable
    public Drawable getCollapseIcon() {
        if (this.a != null) {
            return this.a.getDrawable();
        }
        return null;
    }

    public int getContentInsetEnd() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getEnd();
        }
        return 0;
    }

    public int getContentInsetEndWithActions() {
        return this.mContentInsetEndWithActions != Integer.MIN_VALUE ? this.mContentInsetEndWithActions : getContentInsetEnd();
    }

    public int getContentInsetLeft() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getLeft();
        }
        return 0;
    }

    public int getContentInsetRight() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getRight();
        }
        return 0;
    }

    public int getContentInsetStart() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getStart();
        }
        return 0;
    }

    public int getContentInsetStartWithNavigation() {
        return this.mContentInsetStartWithNavigation != Integer.MIN_VALUE ? this.mContentInsetStartWithNavigation : getContentInsetStart();
    }

    public int getCurrentContentInsetEnd() {
        MenuBuilder menuBuilderPeekMenu;
        return this.mMenuView != null && (menuBuilderPeekMenu = this.mMenuView.peekMenu()) != null && menuBuilderPeekMenu.hasVisibleItems() ? Math.max(getContentInsetEnd(), Math.max(this.mContentInsetEndWithActions, 0)) : getContentInsetEnd();
    }

    public int getCurrentContentInsetLeft() {
        return ViewCompat.getLayoutDirection(this) == 1 ? getCurrentContentInsetEnd() : getCurrentContentInsetStart();
    }

    public int getCurrentContentInsetRight() {
        return ViewCompat.getLayoutDirection(this) == 1 ? getCurrentContentInsetStart() : getCurrentContentInsetEnd();
    }

    public int getCurrentContentInsetStart() {
        return getNavigationIcon() != null ? Math.max(getContentInsetStart(), Math.max(this.mContentInsetStartWithNavigation, 0)) : getContentInsetStart();
    }

    public Drawable getLogo() {
        if (this.mLogoView != null) {
            return this.mLogoView.getDrawable();
        }
        return null;
    }

    public CharSequence getLogoDescription() {
        if (this.mLogoView != null) {
            return this.mLogoView.getContentDescription();
        }
        return null;
    }

    public Menu getMenu() {
        ensureMenu();
        return this.mMenuView.getMenu();
    }

    @Nullable
    public CharSequence getNavigationContentDescription() {
        if (this.mNavButtonView != null) {
            return this.mNavButtonView.getContentDescription();
        }
        return null;
    }

    @Nullable
    public Drawable getNavigationIcon() {
        if (this.mNavButtonView != null) {
            return this.mNavButtonView.getDrawable();
        }
        return null;
    }

    ActionMenuPresenter getOuterActionMenuPresenter() {
        return this.mOuterActionMenuPresenter;
    }

    @Nullable
    public Drawable getOverflowIcon() {
        ensureMenu();
        return this.mMenuView.getOverflowIcon();
    }

    Context getPopupContext() {
        return this.mPopupContext;
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitleText;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.TESTS})
    final TextView getSubtitleTextView() {
        return this.mSubtitleTextView;
    }

    public CharSequence getTitle() {
        return this.mTitleText;
    }

    public int getTitleMarginBottom() {
        return this.mTitleMarginBottom;
    }

    public int getTitleMarginEnd() {
        return this.mTitleMarginEnd;
    }

    public int getTitleMarginStart() {
        return this.mTitleMarginStart;
    }

    public int getTitleMarginTop() {
        return this.mTitleMarginTop;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.TESTS})
    final TextView getTitleTextView() {
        return this.mTitleTextView;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public DecorToolbar getWrapper() {
        if (this.mWrapper == null) {
            this.mWrapper = new ToolbarWidgetWrapper(this, true);
        }
        return this.mWrapper;
    }

    public boolean hasExpandedActionView() {
        return (this.mExpandedMenuPresenter == null || this.mExpandedMenuPresenter.b == null) ? false : true;
    }

    public boolean hideOverflowMenu() {
        return this.mMenuView != null && this.mMenuView.hideOverflowMenu();
    }

    public void inflateMenu(@MenuRes int i) {
        getMenuInflater().inflate(i, getMenu());
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean isOverflowMenuShowPending() {
        return this.mMenuView != null && this.mMenuView.isOverflowMenuShowPending();
    }

    public boolean isOverflowMenuShowing() {
        return this.mMenuView != null && this.mMenuView.isOverflowMenuShowing();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean isTitleTruncated() {
        Layout layout;
        if (this.mTitleTextView == null || (layout = this.mTitleTextView.getLayout()) == null) {
            return false;
        }
        int lineCount = layout.getLineCount();
        for (int i = 0; i < lineCount; i++) {
            if (layout.getEllipsisCount(i) > 0) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mShowOverflowMenuRunnable);
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9) {
            this.mEatingHover = false;
        }
        if (!this.mEatingHover) {
            boolean zOnHoverEvent = super.onHoverEvent(motionEvent);
            if (actionMasked == 9 && !zOnHoverEvent) {
                this.mEatingHover = true;
            }
        }
        if (actionMasked == 10 || actionMasked == 3) {
            this.mEatingHover = false;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x02aa A[LOOP:0: B:104:0x02a8->B:105:0x02aa, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:108:0x02cc A[LOOP:1: B:107:0x02ca->B:108:0x02cc, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:112:0x02f7  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0306 A[LOOP:2: B:116:0x0304->B:117:0x0306, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x022b  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int iLayoutChildLeft;
        int iLayoutChildRight;
        boolean zShouldLayout;
        boolean zShouldLayout2;
        int i5;
        int measuredHeight;
        int i6;
        int i7;
        boolean z2;
        int i8;
        int i9;
        int i10;
        int paddingTop;
        int i11;
        int i12;
        int i13;
        int i14;
        char c;
        int i15;
        int i16;
        int i17;
        int size;
        int iLayoutChildLeft2;
        int i18;
        int size2;
        int i19;
        int i20;
        int size3;
        boolean z3 = ViewCompat.getLayoutDirection(this) == 1;
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop2 = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int i21 = width - paddingRight;
        int[] iArr = this.mTempMargins;
        iArr[1] = 0;
        iArr[0] = 0;
        int minimumHeight = ViewCompat.getMinimumHeight(this);
        int iMin = minimumHeight >= 0 ? Math.min(minimumHeight, i4 - i2) : 0;
        if (!shouldLayout(this.mNavButtonView)) {
            iLayoutChildLeft = paddingLeft;
        } else {
            if (z3) {
                iLayoutChildRight = layoutChildRight(this.mNavButtonView, i21, iArr, iMin);
                iLayoutChildLeft = paddingLeft;
                if (shouldLayout(this.a)) {
                    if (z3) {
                        iLayoutChildRight = layoutChildRight(this.a, iLayoutChildRight, iArr, iMin);
                    } else {
                        iLayoutChildLeft = layoutChildLeft(this.a, iLayoutChildLeft, iArr, iMin);
                    }
                }
                if (shouldLayout(this.mMenuView)) {
                    if (z3) {
                        iLayoutChildLeft = layoutChildLeft(this.mMenuView, iLayoutChildLeft, iArr, iMin);
                    } else {
                        iLayoutChildRight = layoutChildRight(this.mMenuView, iLayoutChildRight, iArr, iMin);
                    }
                }
                int currentContentInsetLeft = getCurrentContentInsetLeft();
                int currentContentInsetRight = getCurrentContentInsetRight();
                iArr[0] = Math.max(0, currentContentInsetLeft - iLayoutChildLeft);
                iArr[1] = Math.max(0, currentContentInsetRight - (i21 - iLayoutChildRight));
                int iMax = Math.max(iLayoutChildLeft, currentContentInsetLeft);
                int iMin2 = Math.min(iLayoutChildRight, i21 - currentContentInsetRight);
                if (shouldLayout(this.b)) {
                    if (z3) {
                        iMin2 = layoutChildRight(this.b, iMin2, iArr, iMin);
                    } else {
                        iMax = layoutChildLeft(this.b, iMax, iArr, iMin);
                    }
                }
                if (shouldLayout(this.mLogoView)) {
                    if (z3) {
                        iMin2 = layoutChildRight(this.mLogoView, iMin2, iArr, iMin);
                    } else {
                        iMax = layoutChildLeft(this.mLogoView, iMax, iArr, iMin);
                    }
                }
                zShouldLayout = shouldLayout(this.mTitleTextView);
                zShouldLayout2 = shouldLayout(this.mSubtitleTextView);
                if (zShouldLayout) {
                    i5 = paddingRight;
                    measuredHeight = 0;
                } else {
                    LayoutParams layoutParams = (LayoutParams) this.mTitleTextView.getLayoutParams();
                    i5 = paddingRight;
                    measuredHeight = layoutParams.topMargin + this.mTitleTextView.getMeasuredHeight() + layoutParams.bottomMargin + 0;
                }
                if (zShouldLayout2) {
                    i6 = width;
                } else {
                    LayoutParams layoutParams2 = (LayoutParams) this.mSubtitleTextView.getLayoutParams();
                    i6 = width;
                    measuredHeight += layoutParams2.topMargin + this.mSubtitleTextView.getMeasuredHeight() + layoutParams2.bottomMargin;
                }
                if (!zShouldLayout || zShouldLayout2) {
                    TextView textView = !zShouldLayout ? this.mTitleTextView : this.mSubtitleTextView;
                    TextView textView2 = !zShouldLayout2 ? this.mSubtitleTextView : this.mTitleTextView;
                    LayoutParams layoutParams3 = (LayoutParams) textView.getLayoutParams();
                    LayoutParams layoutParams4 = (LayoutParams) textView2.getLayoutParams();
                    if ((zShouldLayout || this.mTitleTextView.getMeasuredWidth() <= 0) && (!zShouldLayout2 || this.mSubtitleTextView.getMeasuredWidth() <= 0)) {
                        i7 = paddingLeft;
                        z2 = false;
                    } else {
                        i7 = paddingLeft;
                        z2 = true;
                    }
                    i8 = this.mGravity & 112;
                    i9 = iMin;
                    if (i8 == 48) {
                        i10 = iMax;
                        paddingTop = getPaddingTop() + layoutParams3.topMargin + this.mTitleMarginTop;
                    } else if (i8 != 80) {
                        int iMax2 = (((height - paddingTop2) - paddingBottom) - measuredHeight) / 2;
                        i10 = iMax;
                        if (iMax2 < layoutParams3.topMargin + this.mTitleMarginTop) {
                            iMax2 = layoutParams3.topMargin + this.mTitleMarginTop;
                        } else {
                            int i22 = (((height - paddingBottom) - measuredHeight) - iMax2) - paddingTop2;
                            if (i22 < layoutParams3.bottomMargin + this.mTitleMarginBottom) {
                                iMax2 = Math.max(0, iMax2 - ((layoutParams4.bottomMargin + this.mTitleMarginBottom) - i22));
                            }
                        }
                        paddingTop = paddingTop2 + iMax2;
                    } else {
                        i10 = iMax;
                        paddingTop = (((height - paddingBottom) - layoutParams4.bottomMargin) - this.mTitleMarginBottom) - measuredHeight;
                    }
                    if (!z3) {
                        if (z2) {
                            i12 = this.mTitleMarginStart;
                            i11 = 0;
                        } else {
                            i11 = 0;
                            i12 = 0;
                        }
                        int i23 = i12 - iArr[i11];
                        iMax = i10 + Math.max(i11, i23);
                        iArr[i11] = Math.max(i11, -i23);
                        if (zShouldLayout) {
                            LayoutParams layoutParams5 = (LayoutParams) this.mTitleTextView.getLayoutParams();
                            int measuredWidth = this.mTitleTextView.getMeasuredWidth() + iMax;
                            int measuredHeight2 = this.mTitleTextView.getMeasuredHeight() + paddingTop;
                            this.mTitleTextView.layout(iMax, paddingTop, measuredWidth, measuredHeight2);
                            i13 = measuredWidth + this.mTitleMarginEnd;
                            paddingTop = measuredHeight2 + layoutParams5.bottomMargin;
                        } else {
                            i13 = iMax;
                        }
                        if (zShouldLayout2) {
                            LayoutParams layoutParams6 = (LayoutParams) this.mSubtitleTextView.getLayoutParams();
                            int i24 = paddingTop + layoutParams6.topMargin;
                            int measuredWidth2 = this.mSubtitleTextView.getMeasuredWidth() + iMax;
                            this.mSubtitleTextView.layout(iMax, i24, measuredWidth2, this.mSubtitleTextView.getMeasuredHeight() + i24);
                            i14 = measuredWidth2 + this.mTitleMarginEnd;
                            int i25 = layoutParams6.bottomMargin;
                        } else {
                            i14 = iMax;
                        }
                        if (z2) {
                            iMax = Math.max(i13, i14);
                        }
                        addCustomViewsWithGravity(this.mTempViews, 3);
                        size = this.mTempViews.size();
                        iLayoutChildLeft2 = iMax;
                        for (i18 = i11; i18 < size; i18++) {
                            iLayoutChildLeft2 = layoutChildLeft(this.mTempViews.get(i18), iLayoutChildLeft2, iArr, i9);
                        }
                        int i26 = i9;
                        addCustomViewsWithGravity(this.mTempViews, 5);
                        size2 = this.mTempViews.size();
                        for (i19 = i11; i19 < size2; i19++) {
                            iMin2 = layoutChildRight(this.mTempViews.get(i19), iMin2, iArr, i26);
                        }
                        addCustomViewsWithGravity(this.mTempViews, 1);
                        int viewListMeasuredWidth = getViewListMeasuredWidth(this.mTempViews, iArr);
                        i20 = (i7 + (((i6 - i7) - i5) / 2)) - (viewListMeasuredWidth / 2);
                        int i27 = viewListMeasuredWidth + i20;
                        if (i20 >= iLayoutChildLeft2) {
                            iLayoutChildLeft2 = i27 > iMin2 ? i20 - (i27 - iMin2) : i20;
                        }
                        size3 = this.mTempViews.size();
                        while (i11 < size3) {
                            iLayoutChildLeft2 = layoutChildLeft(this.mTempViews.get(i11), iLayoutChildLeft2, iArr, i26);
                            i11++;
                        }
                        this.mTempViews.clear();
                    }
                    if (z2) {
                        i15 = this.mTitleMarginStart;
                        c = 1;
                    } else {
                        c = 1;
                        i15 = 0;
                    }
                    int i28 = i15 - iArr[c];
                    iMin2 -= Math.max(0, i28);
                    iArr[c] = Math.max(0, -i28);
                    if (zShouldLayout) {
                        LayoutParams layoutParams7 = (LayoutParams) this.mTitleTextView.getLayoutParams();
                        int measuredWidth3 = iMin2 - this.mTitleTextView.getMeasuredWidth();
                        int measuredHeight3 = this.mTitleTextView.getMeasuredHeight() + paddingTop;
                        this.mTitleTextView.layout(measuredWidth3, paddingTop, iMin2, measuredHeight3);
                        i16 = measuredWidth3 - this.mTitleMarginEnd;
                        paddingTop = measuredHeight3 + layoutParams7.bottomMargin;
                    } else {
                        i16 = iMin2;
                    }
                    if (zShouldLayout2) {
                        LayoutParams layoutParams8 = (LayoutParams) this.mSubtitleTextView.getLayoutParams();
                        int i29 = paddingTop + layoutParams8.topMargin;
                        this.mSubtitleTextView.layout(iMin2 - this.mSubtitleTextView.getMeasuredWidth(), i29, iMin2, this.mSubtitleTextView.getMeasuredHeight() + i29);
                        i17 = iMin2 - this.mTitleMarginEnd;
                        int i30 = layoutParams8.bottomMargin;
                    } else {
                        i17 = iMin2;
                    }
                    if (z2) {
                        iMin2 = Math.min(i16, i17);
                    }
                    iMax = i10;
                } else {
                    i7 = paddingLeft;
                    i9 = iMin;
                }
                i11 = 0;
                addCustomViewsWithGravity(this.mTempViews, 3);
                size = this.mTempViews.size();
                iLayoutChildLeft2 = iMax;
                while (i18 < size) {
                }
                int i262 = i9;
                addCustomViewsWithGravity(this.mTempViews, 5);
                size2 = this.mTempViews.size();
                while (i19 < size2) {
                }
                addCustomViewsWithGravity(this.mTempViews, 1);
                int viewListMeasuredWidth2 = getViewListMeasuredWidth(this.mTempViews, iArr);
                i20 = (i7 + (((i6 - i7) - i5) / 2)) - (viewListMeasuredWidth2 / 2);
                int i272 = viewListMeasuredWidth2 + i20;
                if (i20 >= iLayoutChildLeft2) {
                }
                size3 = this.mTempViews.size();
                while (i11 < size3) {
                }
                this.mTempViews.clear();
            }
            iLayoutChildLeft = layoutChildLeft(this.mNavButtonView, paddingLeft, iArr, iMin);
        }
        iLayoutChildRight = i21;
        if (shouldLayout(this.a)) {
        }
        if (shouldLayout(this.mMenuView)) {
        }
        int currentContentInsetLeft2 = getCurrentContentInsetLeft();
        int currentContentInsetRight2 = getCurrentContentInsetRight();
        iArr[0] = Math.max(0, currentContentInsetLeft2 - iLayoutChildLeft);
        iArr[1] = Math.max(0, currentContentInsetRight2 - (i21 - iLayoutChildRight));
        int iMax3 = Math.max(iLayoutChildLeft, currentContentInsetLeft2);
        int iMin22 = Math.min(iLayoutChildRight, i21 - currentContentInsetRight2);
        if (shouldLayout(this.b)) {
        }
        if (shouldLayout(this.mLogoView)) {
        }
        zShouldLayout = shouldLayout(this.mTitleTextView);
        zShouldLayout2 = shouldLayout(this.mSubtitleTextView);
        if (zShouldLayout) {
        }
        if (zShouldLayout2) {
        }
        if (zShouldLayout) {
            if (!zShouldLayout) {
            }
            if (!zShouldLayout2) {
            }
            LayoutParams layoutParams32 = (LayoutParams) textView.getLayoutParams();
            LayoutParams layoutParams42 = (LayoutParams) textView2.getLayoutParams();
            if (zShouldLayout) {
                i7 = paddingLeft;
                z2 = false;
                i8 = this.mGravity & 112;
                i9 = iMin;
                if (i8 == 48) {
                }
                if (!z3) {
                }
            } else {
                i7 = paddingLeft;
                z2 = false;
                i8 = this.mGravity & 112;
                i9 = iMin;
                if (i8 == 48) {
                }
                if (!z3) {
                }
            }
        }
        addCustomViewsWithGravity(this.mTempViews, 3);
        size = this.mTempViews.size();
        iLayoutChildLeft2 = iMax3;
        while (i18 < size) {
        }
        int i2622 = i9;
        addCustomViewsWithGravity(this.mTempViews, 5);
        size2 = this.mTempViews.size();
        while (i19 < size2) {
        }
        addCustomViewsWithGravity(this.mTempViews, 1);
        int viewListMeasuredWidth22 = getViewListMeasuredWidth(this.mTempViews, iArr);
        i20 = (i7 + (((i6 - i7) - i5) / 2)) - (viewListMeasuredWidth22 / 2);
        int i2722 = viewListMeasuredWidth22 + i20;
        if (i20 >= iLayoutChildLeft2) {
        }
        size3 = this.mTempViews.size();
        while (i11 < size3) {
        }
        this.mTempViews.clear();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        char c;
        char c2;
        int measuredWidth;
        int iMax;
        int iCombineMeasuredStates;
        int measuredWidth2;
        int measuredHeight;
        int iCombineMeasuredStates2;
        int iMax2;
        int[] iArr = this.mTempMargins;
        if (ViewUtils.isLayoutRtl(this)) {
            c2 = 1;
            c = 0;
        } else {
            c = 1;
            c2 = 0;
        }
        if (shouldLayout(this.mNavButtonView)) {
            measureChildConstrained(this.mNavButtonView, i, 0, i2, 0, this.mMaxButtonHeight);
            measuredWidth = this.mNavButtonView.getMeasuredWidth() + getHorizontalMargins(this.mNavButtonView);
            iMax = Math.max(0, this.mNavButtonView.getMeasuredHeight() + getVerticalMargins(this.mNavButtonView));
            iCombineMeasuredStates = View.combineMeasuredStates(0, this.mNavButtonView.getMeasuredState());
        } else {
            measuredWidth = 0;
            iMax = 0;
            iCombineMeasuredStates = 0;
        }
        if (shouldLayout(this.a)) {
            measureChildConstrained(this.a, i, 0, i2, 0, this.mMaxButtonHeight);
            measuredWidth = this.a.getMeasuredWidth() + getHorizontalMargins(this.a);
            iMax = Math.max(iMax, this.a.getMeasuredHeight() + getVerticalMargins(this.a));
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.a.getMeasuredState());
        }
        int currentContentInsetStart = getCurrentContentInsetStart();
        int iMax3 = Math.max(currentContentInsetStart, measuredWidth) + 0;
        iArr[c2] = Math.max(0, currentContentInsetStart - measuredWidth);
        if (shouldLayout(this.mMenuView)) {
            measureChildConstrained(this.mMenuView, i, iMax3, i2, 0, this.mMaxButtonHeight);
            measuredWidth2 = this.mMenuView.getMeasuredWidth() + getHorizontalMargins(this.mMenuView);
            iMax = Math.max(iMax, this.mMenuView.getMeasuredHeight() + getVerticalMargins(this.mMenuView));
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.mMenuView.getMeasuredState());
        } else {
            measuredWidth2 = 0;
        }
        int currentContentInsetEnd = getCurrentContentInsetEnd();
        int iMax4 = iMax3 + Math.max(currentContentInsetEnd, measuredWidth2);
        iArr[c] = Math.max(0, currentContentInsetEnd - measuredWidth2);
        if (shouldLayout(this.b)) {
            iMax4 += measureChildCollapseMargins(this.b, i, iMax4, i2, 0, iArr);
            iMax = Math.max(iMax, this.b.getMeasuredHeight() + getVerticalMargins(this.b));
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.b.getMeasuredState());
        }
        if (shouldLayout(this.mLogoView)) {
            iMax4 += measureChildCollapseMargins(this.mLogoView, i, iMax4, i2, 0, iArr);
            iMax = Math.max(iMax, this.mLogoView.getMeasuredHeight() + getVerticalMargins(this.mLogoView));
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.mLogoView.getMeasuredState());
        }
        int childCount = getChildCount();
        int iMax5 = iMax;
        int iMeasureChildCollapseMargins = iMax4;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (((LayoutParams) childAt.getLayoutParams()).a == 0 && shouldLayout(childAt)) {
                iMeasureChildCollapseMargins += measureChildCollapseMargins(childAt, i, iMeasureChildCollapseMargins, i2, 0, iArr);
                iMax5 = Math.max(iMax5, childAt.getMeasuredHeight() + getVerticalMargins(childAt));
                iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, childAt.getMeasuredState());
            }
        }
        int i4 = this.mTitleMarginTop + this.mTitleMarginBottom;
        int i5 = this.mTitleMarginStart + this.mTitleMarginEnd;
        if (shouldLayout(this.mTitleTextView)) {
            measureChildCollapseMargins(this.mTitleTextView, i, iMeasureChildCollapseMargins + i5, i2, i4, iArr);
            int measuredWidth3 = this.mTitleTextView.getMeasuredWidth() + getHorizontalMargins(this.mTitleTextView);
            measuredHeight = this.mTitleTextView.getMeasuredHeight() + getVerticalMargins(this.mTitleTextView);
            iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates, this.mTitleTextView.getMeasuredState());
            iMax2 = measuredWidth3;
        } else {
            measuredHeight = 0;
            iCombineMeasuredStates2 = iCombineMeasuredStates;
            iMax2 = 0;
        }
        if (shouldLayout(this.mSubtitleTextView)) {
            iMax2 = Math.max(iMax2, measureChildCollapseMargins(this.mSubtitleTextView, i, iMeasureChildCollapseMargins + i5, i2, measuredHeight + i4, iArr));
            measuredHeight += this.mSubtitleTextView.getMeasuredHeight() + getVerticalMargins(this.mSubtitleTextView);
            iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates2, this.mSubtitleTextView.getMeasuredState());
        }
        int iMax6 = Math.max(iMax5, measuredHeight);
        int paddingLeft = iMeasureChildCollapseMargins + iMax2 + getPaddingLeft() + getPaddingRight();
        int paddingTop = iMax6 + getPaddingTop() + getPaddingBottom();
        int iResolveSizeAndState = View.resolveSizeAndState(Math.max(paddingLeft, getSuggestedMinimumWidth()), i, (-16777216) & iCombineMeasuredStates2);
        int iResolveSizeAndState2 = View.resolveSizeAndState(Math.max(paddingTop, getSuggestedMinimumHeight()), i2, iCombineMeasuredStates2 << 16);
        if (shouldCollapse()) {
            iResolveSizeAndState2 = 0;
        }
        setMeasuredDimension(iResolveSizeAndState, iResolveSizeAndState2);
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        MenuItem menuItemFindItem;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        MenuBuilder menuBuilderPeekMenu = this.mMenuView != null ? this.mMenuView.peekMenu() : null;
        if (savedState.a != 0 && this.mExpandedMenuPresenter != null && menuBuilderPeekMenu != null && (menuItemFindItem = menuBuilderPeekMenu.findItem(savedState.a)) != null) {
            menuItemFindItem.expandActionView();
        }
        if (savedState.b) {
            postShowOverflowMenu();
        }
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        if (Build.VERSION.SDK_INT >= 17) {
            super.onRtlPropertiesChanged(i);
        }
        ensureContentInsets();
        this.mContentInsets.setDirection(i == 1);
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.mExpandedMenuPresenter != null && this.mExpandedMenuPresenter.b != null) {
            savedState.a = this.mExpandedMenuPresenter.b.getItemId();
        }
        savedState.b = isOverflowMenuShowing();
        return savedState;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mEatingTouch = false;
        }
        if (!this.mEatingTouch) {
            boolean zOnTouchEvent = super.onTouchEvent(motionEvent);
            if (actionMasked == 0 && !zOnTouchEvent) {
                this.mEatingTouch = true;
            }
        }
        if (actionMasked == 1 || actionMasked == 3) {
            this.mEatingTouch = false;
        }
        return true;
    }

    public void setCollapseContentDescription(@StringRes int i) {
        setCollapseContentDescription(i != 0 ? getContext().getText(i) : null);
    }

    public void setCollapseContentDescription(@Nullable CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            a();
        }
        if (this.a != null) {
            this.a.setContentDescription(charSequence);
        }
    }

    public void setCollapseIcon(@DrawableRes int i) {
        setCollapseIcon(AppCompatResources.getDrawable(getContext(), i));
    }

    public void setCollapseIcon(@Nullable Drawable drawable) {
        if (drawable != null) {
            a();
            this.a.setImageDrawable(drawable);
        } else if (this.a != null) {
            this.a.setImageDrawable(this.mCollapseIcon);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setCollapsible(boolean z) {
        this.mCollapsible = z;
        requestLayout();
    }

    public void setContentInsetEndWithActions(int i) {
        if (i < 0) {
            i = Integer.MIN_VALUE;
        }
        if (i != this.mContentInsetEndWithActions) {
            this.mContentInsetEndWithActions = i;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public void setContentInsetStartWithNavigation(int i) {
        if (i < 0) {
            i = Integer.MIN_VALUE;
        }
        if (i != this.mContentInsetStartWithNavigation) {
            this.mContentInsetStartWithNavigation = i;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public void setContentInsetsAbsolute(int i, int i2) {
        ensureContentInsets();
        this.mContentInsets.setAbsolute(i, i2);
    }

    public void setContentInsetsRelative(int i, int i2) {
        ensureContentInsets();
        this.mContentInsets.setRelative(i, i2);
    }

    public void setLogo(@DrawableRes int i) {
        setLogo(AppCompatResources.getDrawable(getContext(), i));
    }

    public void setLogo(Drawable drawable) {
        if (drawable != null) {
            ensureLogoView();
            if (!isChildOrHidden(this.mLogoView)) {
                addSystemView(this.mLogoView, true);
            }
        } else if (this.mLogoView != null && isChildOrHidden(this.mLogoView)) {
            removeView(this.mLogoView);
            this.mHiddenViews.remove(this.mLogoView);
        }
        if (this.mLogoView != null) {
            this.mLogoView.setImageDrawable(drawable);
        }
    }

    public void setLogoDescription(@StringRes int i) {
        setLogoDescription(getContext().getText(i));
    }

    public void setLogoDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            ensureLogoView();
        }
        if (this.mLogoView != null) {
            this.mLogoView.setContentDescription(charSequence);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void setMenu(MenuBuilder menuBuilder, ActionMenuPresenter actionMenuPresenter) {
        if (menuBuilder == null && this.mMenuView == null) {
            return;
        }
        ensureMenuView();
        MenuBuilder menuBuilderPeekMenu = this.mMenuView.peekMenu();
        if (menuBuilderPeekMenu == menuBuilder) {
            return;
        }
        if (menuBuilderPeekMenu != null) {
            menuBuilderPeekMenu.removeMenuPresenter(this.mOuterActionMenuPresenter);
            menuBuilderPeekMenu.removeMenuPresenter(this.mExpandedMenuPresenter);
        }
        if (this.mExpandedMenuPresenter == null) {
            this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
        }
        actionMenuPresenter.setExpandedActionViewsExclusive(true);
        if (menuBuilder != null) {
            menuBuilder.addMenuPresenter(actionMenuPresenter, this.mPopupContext);
            menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
        } else {
            actionMenuPresenter.initForMenu(this.mPopupContext, null);
            this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, null);
            actionMenuPresenter.updateMenuView(true);
            this.mExpandedMenuPresenter.updateMenuView(true);
        }
        this.mMenuView.setPopupTheme(this.mPopupTheme);
        this.mMenuView.setPresenter(actionMenuPresenter);
        this.mOuterActionMenuPresenter = actionMenuPresenter;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        this.mActionMenuPresenterCallback = callback;
        this.mMenuBuilderCallback = callback2;
        if (this.mMenuView != null) {
            this.mMenuView.setMenuCallbacks(callback, callback2);
        }
    }

    public void setNavigationContentDescription(@StringRes int i) {
        setNavigationContentDescription(i != 0 ? getContext().getText(i) : null);
    }

    public void setNavigationContentDescription(@Nullable CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            ensureNavButtonView();
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setContentDescription(charSequence);
        }
    }

    public void setNavigationIcon(@DrawableRes int i) {
        setNavigationIcon(AppCompatResources.getDrawable(getContext(), i));
    }

    public void setNavigationIcon(@Nullable Drawable drawable) {
        if (drawable != null) {
            ensureNavButtonView();
            if (!isChildOrHidden(this.mNavButtonView)) {
                addSystemView(this.mNavButtonView, true);
            }
        } else if (this.mNavButtonView != null && isChildOrHidden(this.mNavButtonView)) {
            removeView(this.mNavButtonView);
            this.mHiddenViews.remove(this.mNavButtonView);
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setImageDrawable(drawable);
        }
    }

    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        ensureNavButtonView();
        this.mNavButtonView.setOnClickListener(onClickListener);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.d = onMenuItemClickListener;
    }

    public void setOverflowIcon(@Nullable Drawable drawable) {
        ensureMenu();
        this.mMenuView.setOverflowIcon(drawable);
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

    public void setSubtitle(@StringRes int i) {
        setSubtitle(getContext().getText(i));
    }

    public void setSubtitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (this.mSubtitleTextView == null) {
                Context context = getContext();
                this.mSubtitleTextView = new AppCompatTextView(context);
                this.mSubtitleTextView.setSingleLine();
                this.mSubtitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                if (this.mSubtitleTextAppearance != 0) {
                    this.mSubtitleTextView.setTextAppearance(context, this.mSubtitleTextAppearance);
                }
                if (this.mSubtitleTextColor != null) {
                    this.mSubtitleTextView.setTextColor(this.mSubtitleTextColor);
                }
            }
            if (!isChildOrHidden(this.mSubtitleTextView)) {
                addSystemView(this.mSubtitleTextView, true);
            }
        } else if (this.mSubtitleTextView != null && isChildOrHidden(this.mSubtitleTextView)) {
            removeView(this.mSubtitleTextView);
            this.mHiddenViews.remove(this.mSubtitleTextView);
        }
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setText(charSequence);
        }
        this.mSubtitleText = charSequence;
    }

    public void setSubtitleTextAppearance(Context context, @StyleRes int i) {
        this.mSubtitleTextAppearance = i;
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextAppearance(context, i);
        }
    }

    public void setSubtitleTextColor(@ColorInt int i) {
        setSubtitleTextColor(ColorStateList.valueOf(i));
    }

    public void setSubtitleTextColor(@NonNull ColorStateList colorStateList) {
        this.mSubtitleTextColor = colorStateList;
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextColor(colorStateList);
        }
    }

    public void setTitle(@StringRes int i) {
        setTitle(getContext().getText(i));
    }

    public void setTitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (this.mTitleTextView == null) {
                Context context = getContext();
                this.mTitleTextView = new AppCompatTextView(context);
                this.mTitleTextView.setSingleLine();
                this.mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                if (this.mTitleTextAppearance != 0) {
                    this.mTitleTextView.setTextAppearance(context, this.mTitleTextAppearance);
                }
                if (this.mTitleTextColor != null) {
                    this.mTitleTextView.setTextColor(this.mTitleTextColor);
                }
            }
            if (!isChildOrHidden(this.mTitleTextView)) {
                addSystemView(this.mTitleTextView, true);
            }
        } else if (this.mTitleTextView != null && isChildOrHidden(this.mTitleTextView)) {
            removeView(this.mTitleTextView);
            this.mHiddenViews.remove(this.mTitleTextView);
        }
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setText(charSequence);
        }
        this.mTitleText = charSequence;
    }

    public void setTitleMargin(int i, int i2, int i3, int i4) {
        this.mTitleMarginStart = i;
        this.mTitleMarginTop = i2;
        this.mTitleMarginEnd = i3;
        this.mTitleMarginBottom = i4;
        requestLayout();
    }

    public void setTitleMarginBottom(int i) {
        this.mTitleMarginBottom = i;
        requestLayout();
    }

    public void setTitleMarginEnd(int i) {
        this.mTitleMarginEnd = i;
        requestLayout();
    }

    public void setTitleMarginStart(int i) {
        this.mTitleMarginStart = i;
        requestLayout();
    }

    public void setTitleMarginTop(int i) {
        this.mTitleMarginTop = i;
        requestLayout();
    }

    public void setTitleTextAppearance(Context context, @StyleRes int i) {
        this.mTitleTextAppearance = i;
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextAppearance(context, i);
        }
    }

    public void setTitleTextColor(@ColorInt int i) {
        setTitleTextColor(ColorStateList.valueOf(i));
    }

    public void setTitleTextColor(@NonNull ColorStateList colorStateList) {
        this.mTitleTextColor = colorStateList;
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextColor(colorStateList);
        }
    }

    public boolean showOverflowMenu() {
        return this.mMenuView != null && this.mMenuView.showOverflowMenu();
    }
}
