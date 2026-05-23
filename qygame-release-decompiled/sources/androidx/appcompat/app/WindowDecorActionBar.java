package androidx.appcompat.app;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.SpinnerAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.ViewPropertyAnimatorCompatSet;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.appcompat.widget.ActionBarContainer;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.DecorToolbar;
import androidx.appcompat.widget.ScrollingTabContainerView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.view.ViewPropertyAnimatorUpdateListener;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class WindowDecorActionBar extends ActionBar implements ActionBarOverlayLayout.ActionBarVisibilityCallback {
    private static final long FADE_IN_DURATION_MS = 200;
    private static final long FADE_OUT_DURATION_MS = 100;
    private static final int INVALID_POSITION = -1;
    private static final String TAG = "WindowDecorActionBar";
    private static final Interpolator sHideInterpolator = new AccelerateInterpolator();
    private static final Interpolator sShowInterpolator = new DecelerateInterpolator();
    Context a;
    ActionBarOverlayLayout b;
    ActionBarContainer c;
    DecorToolbar d;
    ActionBarContextView e;
    View f;
    ScrollingTabContainerView g;
    ActionModeImpl h;
    ActionMode i;
    ActionMode.Callback j;
    boolean l;
    boolean m;
    private Activity mActivity;
    private boolean mDisplayHomeAsUpSet;
    private boolean mHasEmbeddedTabs;
    private boolean mLastMenuVisibility;
    private TabImpl mSelectedTab;
    private boolean mShowHideAnimationEnabled;
    private boolean mShowingForMode;
    private Context mThemedContext;
    ViewPropertyAnimatorCompatSet n;
    boolean o;
    private ArrayList<TabImpl> mTabs = new ArrayList<>();
    private int mSavedTabPosition = -1;
    private ArrayList<ActionBar.OnMenuVisibilityListener> mMenuVisibilityListeners = new ArrayList<>();
    private int mCurWindowVisibility = 0;
    boolean k = true;
    private boolean mNowShowing = true;
    final ViewPropertyAnimatorListener p = new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.app.WindowDecorActionBar.1
        @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
        public void onAnimationEnd(View view) {
            if (WindowDecorActionBar.this.k && WindowDecorActionBar.this.f != null) {
                WindowDecorActionBar.this.f.setTranslationY(0.0f);
                WindowDecorActionBar.this.c.setTranslationY(0.0f);
            }
            WindowDecorActionBar.this.c.setVisibility(8);
            WindowDecorActionBar.this.c.setTransitioning(false);
            WindowDecorActionBar.this.n = null;
            WindowDecorActionBar.this.b();
            if (WindowDecorActionBar.this.b != null) {
                ViewCompat.requestApplyInsets(WindowDecorActionBar.this.b);
            }
        }
    };
    final ViewPropertyAnimatorListener q = new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.app.WindowDecorActionBar.2
        @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
        public void onAnimationEnd(View view) {
            WindowDecorActionBar.this.n = null;
            WindowDecorActionBar.this.c.requestLayout();
        }
    };
    final ViewPropertyAnimatorUpdateListener r = new ViewPropertyAnimatorUpdateListener() { // from class: androidx.appcompat.app.WindowDecorActionBar.3
        @Override // androidx.core.view.ViewPropertyAnimatorUpdateListener
        public void onAnimationUpdate(View view) {
            ((View) WindowDecorActionBar.this.c.getParent()).invalidate();
        }
    };

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public class ActionModeImpl extends ActionMode implements MenuBuilder.Callback {
        private final Context mActionModeContext;
        private ActionMode.Callback mCallback;
        private WeakReference<View> mCustomView;
        private final MenuBuilder mMenu;

        public ActionModeImpl(Context context, ActionMode.Callback callback) {
            this.mActionModeContext = context;
            this.mCallback = callback;
            this.mMenu = new MenuBuilder(context).setDefaultShowAsAction(1);
            this.mMenu.setCallback(this);
        }

        public boolean dispatchOnCreate() {
            this.mMenu.stopDispatchingItemsChanged();
            try {
                return this.mCallback.onCreateActionMode(this, this.mMenu);
            } finally {
                this.mMenu.startDispatchingItemsChanged();
            }
        }

        @Override // androidx.appcompat.view.ActionMode
        public void finish() {
            if (WindowDecorActionBar.this.h != this) {
                return;
            }
            if (WindowDecorActionBar.a(WindowDecorActionBar.this.l, WindowDecorActionBar.this.m, false)) {
                this.mCallback.onDestroyActionMode(this);
            } else {
                WindowDecorActionBar.this.i = this;
                WindowDecorActionBar.this.j = this.mCallback;
            }
            this.mCallback = null;
            WindowDecorActionBar.this.animateToMode(false);
            WindowDecorActionBar.this.e.closeMode();
            WindowDecorActionBar.this.d.getViewGroup().sendAccessibilityEvent(32);
            WindowDecorActionBar.this.b.setHideOnContentScrollEnabled(WindowDecorActionBar.this.o);
            WindowDecorActionBar.this.h = null;
        }

        @Override // androidx.appcompat.view.ActionMode
        public View getCustomView() {
            if (this.mCustomView != null) {
                return this.mCustomView.get();
            }
            return null;
        }

        @Override // androidx.appcompat.view.ActionMode
        public Menu getMenu() {
            return this.mMenu;
        }

        @Override // androidx.appcompat.view.ActionMode
        public MenuInflater getMenuInflater() {
            return new SupportMenuInflater(this.mActionModeContext);
        }

        @Override // androidx.appcompat.view.ActionMode
        public CharSequence getSubtitle() {
            return WindowDecorActionBar.this.e.getSubtitle();
        }

        @Override // androidx.appcompat.view.ActionMode
        public CharSequence getTitle() {
            return WindowDecorActionBar.this.e.getTitle();
        }

        @Override // androidx.appcompat.view.ActionMode
        public void invalidate() {
            if (WindowDecorActionBar.this.h != this) {
                return;
            }
            this.mMenu.stopDispatchingItemsChanged();
            try {
                this.mCallback.onPrepareActionMode(this, this.mMenu);
            } finally {
                this.mMenu.startDispatchingItemsChanged();
            }
        }

        @Override // androidx.appcompat.view.ActionMode
        public boolean isTitleOptional() {
            return WindowDecorActionBar.this.e.isTitleOptional();
        }

        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        }

        public void onCloseSubMenu(SubMenuBuilder subMenuBuilder) {
        }

        @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
        public boolean onMenuItemSelected(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
            if (this.mCallback != null) {
                return this.mCallback.onActionItemClicked(this, menuItem);
            }
            return false;
        }

        @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
        public void onMenuModeChange(@NonNull MenuBuilder menuBuilder) {
            if (this.mCallback == null) {
                return;
            }
            invalidate();
            WindowDecorActionBar.this.e.showOverflowMenu();
        }

        public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            if (this.mCallback == null) {
                return false;
            }
            if (!subMenuBuilder.hasVisibleItems()) {
                return true;
            }
            new MenuPopupHelper(WindowDecorActionBar.this.getThemedContext(), subMenuBuilder).show();
            return true;
        }

        @Override // androidx.appcompat.view.ActionMode
        public void setCustomView(View view) {
            WindowDecorActionBar.this.e.setCustomView(view);
            this.mCustomView = new WeakReference<>(view);
        }

        @Override // androidx.appcompat.view.ActionMode
        public void setSubtitle(int i) {
            setSubtitle(WindowDecorActionBar.this.a.getResources().getString(i));
        }

        @Override // androidx.appcompat.view.ActionMode
        public void setSubtitle(CharSequence charSequence) {
            WindowDecorActionBar.this.e.setSubtitle(charSequence);
        }

        @Override // androidx.appcompat.view.ActionMode
        public void setTitle(int i) {
            setTitle(WindowDecorActionBar.this.a.getResources().getString(i));
        }

        @Override // androidx.appcompat.view.ActionMode
        public void setTitle(CharSequence charSequence) {
            WindowDecorActionBar.this.e.setTitle(charSequence);
        }

        @Override // androidx.appcompat.view.ActionMode
        public void setTitleOptionalHint(boolean z) {
            super.setTitleOptionalHint(z);
            WindowDecorActionBar.this.e.setTitleOptional(z);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public class TabImpl extends ActionBar.Tab {
        private ActionBar.TabListener mCallback;
        private CharSequence mContentDesc;
        private View mCustomView;
        private Drawable mIcon;
        private int mPosition = -1;
        private Object mTag;
        private CharSequence mText;

        public TabImpl() {
        }

        public ActionBar.TabListener getCallback() {
            return this.mCallback;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public CharSequence getContentDescription() {
            return this.mContentDesc;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public View getCustomView() {
            return this.mCustomView;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public Drawable getIcon() {
            return this.mIcon;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public int getPosition() {
            return this.mPosition;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public Object getTag() {
            return this.mTag;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public CharSequence getText() {
            return this.mText;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public void select() {
            WindowDecorActionBar.this.selectTab(this);
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public ActionBar.Tab setContentDescription(int i) {
            return setContentDescription(WindowDecorActionBar.this.a.getResources().getText(i));
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public ActionBar.Tab setContentDescription(CharSequence charSequence) {
            this.mContentDesc = charSequence;
            if (this.mPosition >= 0) {
                WindowDecorActionBar.this.g.updateTab(this.mPosition);
            }
            return this;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public ActionBar.Tab setCustomView(int i) {
            return setCustomView(LayoutInflater.from(WindowDecorActionBar.this.getThemedContext()).inflate(i, (ViewGroup) null));
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public ActionBar.Tab setCustomView(View view) {
            this.mCustomView = view;
            if (this.mPosition >= 0) {
                WindowDecorActionBar.this.g.updateTab(this.mPosition);
            }
            return this;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public ActionBar.Tab setIcon(int i) {
            return setIcon(AppCompatResources.getDrawable(WindowDecorActionBar.this.a, i));
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public ActionBar.Tab setIcon(Drawable drawable) {
            this.mIcon = drawable;
            if (this.mPosition >= 0) {
                WindowDecorActionBar.this.g.updateTab(this.mPosition);
            }
            return this;
        }

        public void setPosition(int i) {
            this.mPosition = i;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public ActionBar.Tab setTabListener(ActionBar.TabListener tabListener) {
            this.mCallback = tabListener;
            return this;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public ActionBar.Tab setTag(Object obj) {
            this.mTag = obj;
            return this;
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public ActionBar.Tab setText(int i) {
            return setText(WindowDecorActionBar.this.a.getResources().getText(i));
        }

        @Override // androidx.appcompat.app.ActionBar.Tab
        public ActionBar.Tab setText(CharSequence charSequence) {
            this.mText = charSequence;
            if (this.mPosition >= 0) {
                WindowDecorActionBar.this.g.updateTab(this.mPosition);
            }
            return this;
        }
    }

    public WindowDecorActionBar(Activity activity, boolean z) {
        this.mActivity = activity;
        View decorView = activity.getWindow().getDecorView();
        init(decorView);
        if (z) {
            return;
        }
        this.f = decorView.findViewById(R.id.content);
    }

    public WindowDecorActionBar(Dialog dialog) {
        init(dialog.getWindow().getDecorView());
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public WindowDecorActionBar(View view) {
        init(view);
    }

    static boolean a(boolean z, boolean z2, boolean z3) {
        if (z3) {
            return true;
        }
        return (z || z2) ? false : true;
    }

    private void cleanupTabs() {
        if (this.mSelectedTab != null) {
            selectTab(null);
        }
        this.mTabs.clear();
        if (this.g != null) {
            this.g.removeAllTabs();
        }
        this.mSavedTabPosition = -1;
    }

    private void configureTab(ActionBar.Tab tab, int i) {
        TabImpl tabImpl = (TabImpl) tab;
        if (tabImpl.getCallback() == null) {
            throw new IllegalStateException("Action Bar Tab must have a Callback");
        }
        tabImpl.setPosition(i);
        this.mTabs.add(i, tabImpl);
        int size = this.mTabs.size();
        while (true) {
            i++;
            if (i >= size) {
                return;
            } else {
                this.mTabs.get(i).setPosition(i);
            }
        }
    }

    private void ensureTabsExist() {
        if (this.g != null) {
            return;
        }
        ScrollingTabContainerView scrollingTabContainerView = new ScrollingTabContainerView(this.a);
        if (this.mHasEmbeddedTabs) {
            scrollingTabContainerView.setVisibility(0);
            this.d.setEmbeddedTabView(scrollingTabContainerView);
        } else {
            if (getNavigationMode() == 2) {
                scrollingTabContainerView.setVisibility(0);
                if (this.b != null) {
                    ViewCompat.requestApplyInsets(this.b);
                }
            } else {
                scrollingTabContainerView.setVisibility(8);
            }
            this.c.setTabContainer(scrollingTabContainerView);
        }
        this.g = scrollingTabContainerView;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private DecorToolbar getDecorToolbar(View view) {
        if (view instanceof DecorToolbar) {
            return (DecorToolbar) view;
        }
        if (view instanceof Toolbar) {
            return ((Toolbar) view).getWrapper();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Can't make a decor toolbar out of ");
        sb.append(view != 0 ? view.getClass().getSimpleName() : "null");
        throw new IllegalStateException(sb.toString());
    }

    private void hideForActionMode() {
        if (this.mShowingForMode) {
            this.mShowingForMode = false;
            if (this.b != null) {
                this.b.setShowingForActionMode(false);
            }
            updateVisibility(false);
        }
    }

    private void init(View view) {
        this.b = (ActionBarOverlayLayout) view.findViewById(androidx.appcompat.R.id.decor_content_parent);
        if (this.b != null) {
            this.b.setActionBarVisibilityCallback(this);
        }
        this.d = getDecorToolbar(view.findViewById(androidx.appcompat.R.id.action_bar));
        this.e = (ActionBarContextView) view.findViewById(androidx.appcompat.R.id.action_context_bar);
        this.c = (ActionBarContainer) view.findViewById(androidx.appcompat.R.id.action_bar_container);
        if (this.d == null || this.e == null || this.c == null) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be used with a compatible window decor layout");
        }
        this.a = this.d.getContext();
        boolean z = (this.d.getDisplayOptions() & 4) != 0;
        if (z) {
            this.mDisplayHomeAsUpSet = true;
        }
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(this.a);
        setHomeButtonEnabled(actionBarPolicy.enableHomeButtonByDefault() || z);
        setHasEmbeddedTabs(actionBarPolicy.hasEmbeddedTabs());
        TypedArray typedArrayObtainStyledAttributes = this.a.obtainStyledAttributes(null, androidx.appcompat.R.styleable.ActionBar, androidx.appcompat.R.attr.actionBarStyle, 0);
        if (typedArrayObtainStyledAttributes.getBoolean(androidx.appcompat.R.styleable.ActionBar_hideOnContentScroll, false)) {
            setHideOnContentScrollEnabled(true);
        }
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(androidx.appcompat.R.styleable.ActionBar_elevation, 0);
        if (dimensionPixelSize != 0) {
            setElevation(dimensionPixelSize);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    private void setHasEmbeddedTabs(boolean z) {
        this.mHasEmbeddedTabs = z;
        if (this.mHasEmbeddedTabs) {
            this.c.setTabContainer(null);
            this.d.setEmbeddedTabView(this.g);
        } else {
            this.d.setEmbeddedTabView(null);
            this.c.setTabContainer(this.g);
        }
        boolean z2 = getNavigationMode() == 2;
        if (this.g != null) {
            if (z2) {
                this.g.setVisibility(0);
                if (this.b != null) {
                    ViewCompat.requestApplyInsets(this.b);
                }
            } else {
                this.g.setVisibility(8);
            }
        }
        this.d.setCollapsible(!this.mHasEmbeddedTabs && z2);
        this.b.setHasNonEmbeddedTabs(!this.mHasEmbeddedTabs && z2);
    }

    private boolean shouldAnimateContextView() {
        return ViewCompat.isLaidOut(this.c);
    }

    private void showForActionMode() {
        if (this.mShowingForMode) {
            return;
        }
        this.mShowingForMode = true;
        if (this.b != null) {
            this.b.setShowingForActionMode(true);
        }
        updateVisibility(false);
    }

    private void updateVisibility(boolean z) {
        if (a(this.l, this.m, this.mShowingForMode)) {
            if (this.mNowShowing) {
                return;
            }
            this.mNowShowing = true;
            doShow(z);
            return;
        }
        if (this.mNowShowing) {
            this.mNowShowing = false;
            doHide(z);
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.mMenuVisibilityListeners.add(onMenuVisibilityListener);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void addTab(ActionBar.Tab tab) {
        addTab(tab, this.mTabs.isEmpty());
    }

    @Override // androidx.appcompat.app.ActionBar
    public void addTab(ActionBar.Tab tab, int i) {
        addTab(tab, i, this.mTabs.isEmpty());
    }

    @Override // androidx.appcompat.app.ActionBar
    public void addTab(ActionBar.Tab tab, int i, boolean z) {
        ensureTabsExist();
        this.g.addTab(tab, i, z);
        configureTab(tab, i);
        if (z) {
            selectTab(tab);
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public void addTab(ActionBar.Tab tab, boolean z) {
        ensureTabsExist();
        this.g.addTab(tab, z);
        configureTab(tab, this.mTabs.size());
        if (z) {
            selectTab(tab);
        }
    }

    public void animateToMode(boolean z) {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat;
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2;
        if (z) {
            showForActionMode();
        } else {
            hideForActionMode();
        }
        if (!shouldAnimateContextView()) {
            if (z) {
                this.d.setVisibility(4);
                this.e.setVisibility(0);
                return;
            } else {
                this.d.setVisibility(0);
                this.e.setVisibility(8);
                return;
            }
        }
        if (z) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat3 = this.d.setupAnimatorToVisibility(4, FADE_OUT_DURATION_MS);
            viewPropertyAnimatorCompat = this.e.setupAnimatorToVisibility(0, 200L);
            viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat3;
        } else {
            viewPropertyAnimatorCompat = this.d.setupAnimatorToVisibility(0, 200L);
            viewPropertyAnimatorCompat2 = this.e.setupAnimatorToVisibility(8, FADE_OUT_DURATION_MS);
        }
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
        viewPropertyAnimatorCompatSet.playSequentially(viewPropertyAnimatorCompat2, viewPropertyAnimatorCompat);
        viewPropertyAnimatorCompatSet.start();
    }

    void b() {
        if (this.j != null) {
            this.j.onDestroyActionMode(this.i);
            this.i = null;
            this.j = null;
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean collapseActionView() {
        if (this.d == null || !this.d.hasExpandedActionView()) {
            return false;
        }
        this.d.collapseActionView();
        return true;
    }

    @Override // androidx.appcompat.app.ActionBar
    public void dispatchMenuVisibilityChanged(boolean z) {
        if (z == this.mLastMenuVisibility) {
            return;
        }
        this.mLastMenuVisibility = z;
        int size = this.mMenuVisibilityListeners.size();
        for (int i = 0; i < size; i++) {
            this.mMenuVisibilityListeners.get(i).onMenuVisibilityChanged(z);
        }
    }

    public void doHide(boolean z) {
        if (this.n != null) {
            this.n.cancel();
        }
        if (this.mCurWindowVisibility != 0 || (!this.mShowHideAnimationEnabled && !z)) {
            this.p.onAnimationEnd(null);
            return;
        }
        this.c.setAlpha(1.0f);
        this.c.setTransitioning(true);
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
        float f = -this.c.getHeight();
        if (z) {
            this.c.getLocationInWindow(new int[]{0, 0});
            f -= r5[1];
        }
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompatTranslationY = ViewCompat.animate(this.c).translationY(f);
        viewPropertyAnimatorCompatTranslationY.setUpdateListener(this.r);
        viewPropertyAnimatorCompatSet.play(viewPropertyAnimatorCompatTranslationY);
        if (this.k && this.f != null) {
            viewPropertyAnimatorCompatSet.play(ViewCompat.animate(this.f).translationY(f));
        }
        viewPropertyAnimatorCompatSet.setInterpolator(sHideInterpolator);
        viewPropertyAnimatorCompatSet.setDuration(250L);
        viewPropertyAnimatorCompatSet.setListener(this.p);
        this.n = viewPropertyAnimatorCompatSet;
        viewPropertyAnimatorCompatSet.start();
    }

    public void doShow(boolean z) {
        if (this.n != null) {
            this.n.cancel();
        }
        this.c.setVisibility(0);
        if (this.mCurWindowVisibility == 0 && (this.mShowHideAnimationEnabled || z)) {
            this.c.setTranslationY(0.0f);
            float f = -this.c.getHeight();
            if (z) {
                this.c.getLocationInWindow(new int[]{0, 0});
                f -= r5[1];
            }
            this.c.setTranslationY(f);
            ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompatTranslationY = ViewCompat.animate(this.c).translationY(0.0f);
            viewPropertyAnimatorCompatTranslationY.setUpdateListener(this.r);
            viewPropertyAnimatorCompatSet.play(viewPropertyAnimatorCompatTranslationY);
            if (this.k && this.f != null) {
                this.f.setTranslationY(f);
                viewPropertyAnimatorCompatSet.play(ViewCompat.animate(this.f).translationY(0.0f));
            }
            viewPropertyAnimatorCompatSet.setInterpolator(sShowInterpolator);
            viewPropertyAnimatorCompatSet.setDuration(250L);
            viewPropertyAnimatorCompatSet.setListener(this.q);
            this.n = viewPropertyAnimatorCompatSet;
            viewPropertyAnimatorCompatSet.start();
        } else {
            this.c.setAlpha(1.0f);
            this.c.setTranslationY(0.0f);
            if (this.k && this.f != null) {
                this.f.setTranslationY(0.0f);
            }
            this.q.onAnimationEnd(null);
        }
        if (this.b != null) {
            ViewCompat.requestApplyInsets(this.b);
        }
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void enableContentAnimations(boolean z) {
        this.k = z;
    }

    @Override // androidx.appcompat.app.ActionBar
    public View getCustomView() {
        return this.d.getCustomView();
    }

    @Override // androidx.appcompat.app.ActionBar
    public int getDisplayOptions() {
        return this.d.getDisplayOptions();
    }

    @Override // androidx.appcompat.app.ActionBar
    public float getElevation() {
        return ViewCompat.getElevation(this.c);
    }

    @Override // androidx.appcompat.app.ActionBar
    public int getHeight() {
        return this.c.getHeight();
    }

    @Override // androidx.appcompat.app.ActionBar
    public int getHideOffset() {
        return this.b.getActionBarHideOffset();
    }

    @Override // androidx.appcompat.app.ActionBar
    public int getNavigationItemCount() {
        switch (this.d.getNavigationMode()) {
            case 1:
                return this.d.getDropdownItemCount();
            case 2:
                return this.mTabs.size();
            default:
                return 0;
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public int getNavigationMode() {
        return this.d.getNavigationMode();
    }

    @Override // androidx.appcompat.app.ActionBar
    public int getSelectedNavigationIndex() {
        switch (this.d.getNavigationMode()) {
            case 1:
                return this.d.getDropdownSelectedPosition();
            case 2:
                if (this.mSelectedTab != null) {
                    return this.mSelectedTab.getPosition();
                }
                return -1;
            default:
                return -1;
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public ActionBar.Tab getSelectedTab() {
        return this.mSelectedTab;
    }

    @Override // androidx.appcompat.app.ActionBar
    public CharSequence getSubtitle() {
        return this.d.getSubtitle();
    }

    @Override // androidx.appcompat.app.ActionBar
    public ActionBar.Tab getTabAt(int i) {
        return this.mTabs.get(i);
    }

    @Override // androidx.appcompat.app.ActionBar
    public int getTabCount() {
        return this.mTabs.size();
    }

    @Override // androidx.appcompat.app.ActionBar
    public Context getThemedContext() {
        if (this.mThemedContext == null) {
            TypedValue typedValue = new TypedValue();
            this.a.getTheme().resolveAttribute(androidx.appcompat.R.attr.actionBarWidgetTheme, typedValue, true);
            int i = typedValue.resourceId;
            if (i != 0) {
                this.mThemedContext = new ContextThemeWrapper(this.a, i);
            } else {
                this.mThemedContext = this.a;
            }
        }
        return this.mThemedContext;
    }

    @Override // androidx.appcompat.app.ActionBar
    public CharSequence getTitle() {
        return this.d.getTitle();
    }

    public boolean hasIcon() {
        return this.d.hasIcon();
    }

    public boolean hasLogo() {
        return this.d.hasLogo();
    }

    @Override // androidx.appcompat.app.ActionBar
    public void hide() {
        if (this.l) {
            return;
        }
        this.l = true;
        updateVisibility(false);
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void hideForSystem() {
        if (this.m) {
            return;
        }
        this.m = true;
        updateVisibility(true);
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean isHideOnContentScrollEnabled() {
        return this.b.isHideOnContentScrollEnabled();
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean isShowing() {
        int height = getHeight();
        return this.mNowShowing && (height == 0 || getHideOffset() < height);
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean isTitleTruncated() {
        return this.d != null && this.d.isTitleTruncated();
    }

    @Override // androidx.appcompat.app.ActionBar
    public ActionBar.Tab newTab() {
        return new TabImpl();
    }

    @Override // androidx.appcompat.app.ActionBar
    public void onConfigurationChanged(Configuration configuration) {
        setHasEmbeddedTabs(ActionBarPolicy.get(this.a).hasEmbeddedTabs());
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void onContentScrollStarted() {
        if (this.n != null) {
            this.n.cancel();
            this.n = null;
        }
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void onContentScrollStopped() {
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        Menu menu;
        if (this.h == null || (menu = this.h.getMenu()) == null) {
            return false;
        }
        menu.setQwertyMode(KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1);
        return menu.performShortcut(i, keyEvent, 0);
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void onWindowVisibilityChanged(int i) {
        this.mCurWindowVisibility = i;
    }

    @Override // androidx.appcompat.app.ActionBar
    public void removeAllTabs() {
        cleanupTabs();
    }

    @Override // androidx.appcompat.app.ActionBar
    public void removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener onMenuVisibilityListener) {
        this.mMenuVisibilityListeners.remove(onMenuVisibilityListener);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void removeTab(ActionBar.Tab tab) {
        removeTabAt(tab.getPosition());
    }

    @Override // androidx.appcompat.app.ActionBar
    public void removeTabAt(int i) {
        if (this.g == null) {
            return;
        }
        int position = this.mSelectedTab != null ? this.mSelectedTab.getPosition() : this.mSavedTabPosition;
        this.g.removeTabAt(i);
        TabImpl tabImplRemove = this.mTabs.remove(i);
        if (tabImplRemove != null) {
            tabImplRemove.setPosition(-1);
        }
        int size = this.mTabs.size();
        for (int i2 = i; i2 < size; i2++) {
            this.mTabs.get(i2).setPosition(i2);
        }
        if (position == i) {
            selectTab(this.mTabs.isEmpty() ? null : this.mTabs.get(Math.max(0, i - 1)));
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public boolean requestFocus() {
        ViewGroup viewGroup = this.d.getViewGroup();
        if (viewGroup == null || viewGroup.hasFocus()) {
            return false;
        }
        viewGroup.requestFocus();
        return true;
    }

    @Override // androidx.appcompat.app.ActionBar
    public void selectTab(ActionBar.Tab tab) {
        if (getNavigationMode() != 2) {
            this.mSavedTabPosition = tab != null ? tab.getPosition() : -1;
            return;
        }
        FragmentTransaction fragmentTransactionDisallowAddToBackStack = (!(this.mActivity instanceof FragmentActivity) || this.d.getViewGroup().isInEditMode()) ? null : ((FragmentActivity) this.mActivity).getSupportFragmentManager().beginTransaction().disallowAddToBackStack();
        if (this.mSelectedTab != tab) {
            this.g.setTabSelected(tab != null ? tab.getPosition() : -1);
            if (this.mSelectedTab != null) {
                this.mSelectedTab.getCallback().onTabUnselected(this.mSelectedTab, fragmentTransactionDisallowAddToBackStack);
            }
            this.mSelectedTab = (TabImpl) tab;
            if (this.mSelectedTab != null) {
                this.mSelectedTab.getCallback().onTabSelected(this.mSelectedTab, fragmentTransactionDisallowAddToBackStack);
            }
        } else if (this.mSelectedTab != null) {
            this.mSelectedTab.getCallback().onTabReselected(this.mSelectedTab, fragmentTransactionDisallowAddToBackStack);
            this.g.animateToTab(tab.getPosition());
        }
        if (fragmentTransactionDisallowAddToBackStack == null || fragmentTransactionDisallowAddToBackStack.isEmpty()) {
            return;
        }
        fragmentTransactionDisallowAddToBackStack.commit();
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setBackgroundDrawable(Drawable drawable) {
        this.c.setPrimaryBackground(drawable);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setCustomView(int i) {
        setCustomView(LayoutInflater.from(getThemedContext()).inflate(i, this.d.getViewGroup(), false));
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setCustomView(View view) {
        this.d.setCustomView(view);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setCustomView(View view, ActionBar.LayoutParams layoutParams) {
        view.setLayoutParams(layoutParams);
        this.d.setCustomView(view);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setDefaultDisplayHomeAsUpEnabled(boolean z) {
        if (this.mDisplayHomeAsUpSet) {
            return;
        }
        setDisplayHomeAsUpEnabled(z);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setDisplayHomeAsUpEnabled(boolean z) {
        setDisplayOptions(z ? 4 : 0, 4);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setDisplayOptions(int i) {
        if ((i & 4) != 0) {
            this.mDisplayHomeAsUpSet = true;
        }
        this.d.setDisplayOptions(i);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setDisplayOptions(int i, int i2) {
        int displayOptions = this.d.getDisplayOptions();
        if ((i2 & 4) != 0) {
            this.mDisplayHomeAsUpSet = true;
        }
        this.d.setDisplayOptions((i & i2) | ((~i2) & displayOptions));
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setDisplayShowCustomEnabled(boolean z) {
        setDisplayOptions(z ? 16 : 0, 16);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setDisplayShowHomeEnabled(boolean z) {
        setDisplayOptions(z ? 2 : 0, 2);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setDisplayShowTitleEnabled(boolean z) {
        setDisplayOptions(z ? 8 : 0, 8);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setDisplayUseLogoEnabled(boolean z) {
        setDisplayOptions(z ? 1 : 0, 1);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setElevation(float f) {
        ViewCompat.setElevation(this.c, f);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setHideOffset(int i) {
        if (i != 0 && !this.b.isInOverlayMode()) {
            throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to set a non-zero hide offset");
        }
        this.b.setActionBarHideOffset(i);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setHideOnContentScrollEnabled(boolean z) {
        if (z && !this.b.isInOverlayMode()) {
            throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
        }
        this.o = z;
        this.b.setHideOnContentScrollEnabled(z);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setHomeActionContentDescription(int i) {
        this.d.setNavigationContentDescription(i);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setHomeActionContentDescription(CharSequence charSequence) {
        this.d.setNavigationContentDescription(charSequence);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setHomeAsUpIndicator(int i) {
        this.d.setNavigationIcon(i);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setHomeAsUpIndicator(Drawable drawable) {
        this.d.setNavigationIcon(drawable);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setHomeButtonEnabled(boolean z) {
        this.d.setHomeButtonEnabled(z);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setIcon(int i) {
        this.d.setIcon(i);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setIcon(Drawable drawable) {
        this.d.setIcon(drawable);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setListNavigationCallbacks(SpinnerAdapter spinnerAdapter, ActionBar.OnNavigationListener onNavigationListener) {
        this.d.setDropdownParams(spinnerAdapter, new NavItemSelectedListener(onNavigationListener));
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setLogo(int i) {
        this.d.setLogo(i);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setLogo(Drawable drawable) {
        this.d.setLogo(drawable);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setNavigationMode(int i) {
        int navigationMode = this.d.getNavigationMode();
        if (navigationMode == 2) {
            this.mSavedTabPosition = getSelectedNavigationIndex();
            selectTab(null);
            this.g.setVisibility(8);
        }
        if (navigationMode != i && !this.mHasEmbeddedTabs && this.b != null) {
            ViewCompat.requestApplyInsets(this.b);
        }
        this.d.setNavigationMode(i);
        boolean z = false;
        if (i == 2) {
            ensureTabsExist();
            this.g.setVisibility(0);
            if (this.mSavedTabPosition != -1) {
                setSelectedNavigationItem(this.mSavedTabPosition);
                this.mSavedTabPosition = -1;
            }
        }
        this.d.setCollapsible(i == 2 && !this.mHasEmbeddedTabs);
        ActionBarOverlayLayout actionBarOverlayLayout = this.b;
        if (i == 2 && !this.mHasEmbeddedTabs) {
            z = true;
        }
        actionBarOverlayLayout.setHasNonEmbeddedTabs(z);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setSelectedNavigationItem(int i) {
        switch (this.d.getNavigationMode()) {
            case 1:
                this.d.setDropdownSelectedPosition(i);
                return;
            case 2:
                selectTab(this.mTabs.get(i));
                return;
            default:
                throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setShowHideAnimationEnabled(boolean z) {
        this.mShowHideAnimationEnabled = z;
        if (z || this.n == null) {
            return;
        }
        this.n.cancel();
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setSplitBackgroundDrawable(Drawable drawable) {
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setStackedBackgroundDrawable(Drawable drawable) {
        this.c.setStackedBackground(drawable);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setSubtitle(int i) {
        setSubtitle(this.a.getString(i));
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setSubtitle(CharSequence charSequence) {
        this.d.setSubtitle(charSequence);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setTitle(int i) {
        setTitle(this.a.getString(i));
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setTitle(CharSequence charSequence) {
        this.d.setTitle(charSequence);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void setWindowTitle(CharSequence charSequence) {
        this.d.setWindowTitle(charSequence);
    }

    @Override // androidx.appcompat.app.ActionBar
    public void show() {
        if (this.l) {
            this.l = false;
            updateVisibility(false);
        }
    }

    @Override // androidx.appcompat.widget.ActionBarOverlayLayout.ActionBarVisibilityCallback
    public void showForSystem() {
        if (this.m) {
            this.m = false;
            updateVisibility(true);
        }
    }

    @Override // androidx.appcompat.app.ActionBar
    public ActionMode startActionMode(ActionMode.Callback callback) {
        if (this.h != null) {
            this.h.finish();
        }
        this.b.setHideOnContentScrollEnabled(false);
        this.e.killMode();
        ActionModeImpl actionModeImpl = new ActionModeImpl(this.e.getContext(), callback);
        if (!actionModeImpl.dispatchOnCreate()) {
            return null;
        }
        this.h = actionModeImpl;
        actionModeImpl.invalidate();
        this.e.initForMode(actionModeImpl);
        animateToMode(true);
        this.e.sendAccessibilityEvent(32);
        return actionModeImpl;
    }
}
