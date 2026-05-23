package androidx.appcompat.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.R;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.StandaloneActionMode;
import androidx.appcompat.view.SupportActionModeWrapper;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.WindowCallbackWrapper;
import androidx.appcompat.view.menu.ListMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.DecorContentParent;
import androidx.appcompat.widget.FitWindowsViewGroup;
import androidx.appcompat.widget.TintTypedArray;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.appcompat.widget.ViewStubCompat;
import androidx.appcompat.widget.ViewUtils;
import androidx.collection.SimpleArrayMap;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.KeyEventDispatcher;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.PopupWindowCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.igexin.push.core.b;
import java.lang.Thread;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY})
class AppCompatDelegateImpl extends AppCompatDelegate implements LayoutInflater.Factory2, MenuBuilder.Callback {
    private static final boolean IS_PRE_LOLLIPOP;
    private static final boolean sCanApplyOverrideConfiguration;
    private static final boolean sCanReturnDifferentContext;
    private static boolean sInstalledExceptionHandler;
    private static final SimpleArrayMap<String, Integer> sLocalNightModes = new SimpleArrayMap<>();
    private static final int[] sWindowBackgroundStyleable;
    final Object a;
    final Context b;
    Window c;
    final AppCompatCallback d;
    ActionBar e;
    MenuInflater f;
    ActionMode g;
    ActionBarContextView h;
    PopupWindow i;
    Runnable j;
    ViewPropertyAnimatorCompat k;
    ViewGroup l;
    boolean m;
    private ActionMenuPresenterCallback mActionMenuPresenterCallback;
    private boolean mActivityHandlesUiMode;
    private boolean mActivityHandlesUiModeChecked;
    private AppCompatViewInflater mAppCompatViewInflater;
    private AppCompatWindowCallback mAppCompatWindowCallback;
    private AutoNightModeManager mAutoBatteryNightModeManager;
    private AutoNightModeManager mAutoTimeNightModeManager;
    private boolean mBaseContextAttached;
    private boolean mClosingActionMenu;
    private boolean mCreated;
    private DecorContentParent mDecorContentParent;
    private boolean mEnableDefaultActionBarUp;
    private boolean mFeatureIndeterminateProgress;
    private boolean mFeatureProgress;
    private boolean mHandleNativeActionModes;
    private final Runnable mInvalidatePanelMenuRunnable;
    private LayoutIncludeDetector mLayoutIncludeDetector;
    private int mLocalNightMode;
    private boolean mLongPressBackDown;
    private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
    private PanelFeatureState[] mPanels;
    private PanelFeatureState mPreparedPanel;
    private boolean mStarted;
    private View mStatusGuard;
    private boolean mSubDecorInstalled;
    private Rect mTempRect1;
    private Rect mTempRect2;
    private int mThemeResId;
    private CharSequence mTitle;
    private TextView mTitleView;
    boolean n;
    boolean o;
    boolean p;
    boolean q;
    boolean r;
    boolean s;
    int t;

    private class ActionBarDrawableToggleImpl implements ActionBarDrawerToggle.Delegate {
        ActionBarDrawableToggleImpl() {
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public Context getActionBarThemedContext() {
            return AppCompatDelegateImpl.this.c();
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public Drawable getThemeUpIndicator() {
            TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(getActionBarThemedContext(), (AttributeSet) null, new int[]{R.attr.homeAsUpIndicator});
            Drawable drawable = tintTypedArrayObtainStyledAttributes.getDrawable(0);
            tintTypedArrayObtainStyledAttributes.recycle();
            return drawable;
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public boolean isNavigationVisible() {
            ActionBar supportActionBar = AppCompatDelegateImpl.this.getSupportActionBar();
            return (supportActionBar == null || (supportActionBar.getDisplayOptions() & 4) == 0) ? false : true;
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public void setActionBarDescription(int i) {
            ActionBar supportActionBar = AppCompatDelegateImpl.this.getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setHomeActionContentDescription(i);
            }
        }

        @Override // androidx.appcompat.app.ActionBarDrawerToggle.Delegate
        public void setActionBarUpIndicator(Drawable drawable, int i) {
            ActionBar supportActionBar = AppCompatDelegateImpl.this.getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setHomeAsUpIndicator(drawable);
                supportActionBar.setHomeActionContentDescription(i);
            }
        }
    }

    private final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        ActionMenuPresenterCallback() {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public void onCloseMenu(@NonNull MenuBuilder menuBuilder, boolean z) {
            AppCompatDelegateImpl.this.a(menuBuilder);
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(@NonNull MenuBuilder menuBuilder) {
            Window.Callback callbackB = AppCompatDelegateImpl.this.b();
            if (callbackB == null) {
                return true;
            }
            callbackB.onMenuOpened(108, menuBuilder);
            return true;
        }
    }

    class ActionModeCallbackWrapperV9 implements ActionMode.Callback {
        private ActionMode.Callback mWrapped;

        public ActionModeCallbackWrapperV9(ActionMode.Callback callback) {
            this.mWrapped = callback;
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrapped.onCreateActionMode(actionMode, menu);
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            this.mWrapped.onDestroyActionMode(actionMode);
            if (AppCompatDelegateImpl.this.i != null) {
                AppCompatDelegateImpl.this.c.getDecorView().removeCallbacks(AppCompatDelegateImpl.this.j);
            }
            if (AppCompatDelegateImpl.this.h != null) {
                AppCompatDelegateImpl.this.f();
                AppCompatDelegateImpl.this.k = ViewCompat.animate(AppCompatDelegateImpl.this.h).alpha(0.0f);
                AppCompatDelegateImpl.this.k.setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.ActionModeCallbackWrapperV9.1
                    @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                    public void onAnimationEnd(View view) {
                        AppCompatDelegateImpl.this.h.setVisibility(8);
                        if (AppCompatDelegateImpl.this.i != null) {
                            AppCompatDelegateImpl.this.i.dismiss();
                        } else if (AppCompatDelegateImpl.this.h.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View) AppCompatDelegateImpl.this.h.getParent());
                        }
                        AppCompatDelegateImpl.this.h.killMode();
                        AppCompatDelegateImpl.this.k.setListener(null);
                        AppCompatDelegateImpl.this.k = null;
                        ViewCompat.requestApplyInsets(AppCompatDelegateImpl.this.l);
                    }
                });
            }
            if (AppCompatDelegateImpl.this.d != null) {
                AppCompatDelegateImpl.this.d.onSupportActionModeFinished(AppCompatDelegateImpl.this.g);
            }
            AppCompatDelegateImpl.this.g = null;
            ViewCompat.requestApplyInsets(AppCompatDelegateImpl.this.l);
        }

        @Override // androidx.appcompat.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            ViewCompat.requestApplyInsets(AppCompatDelegateImpl.this.l);
            return this.mWrapped.onPrepareActionMode(actionMode, menu);
        }
    }

    @RequiresApi(17)
    static class Api17Impl {
        private Api17Impl() {
        }

        static Context a(@NonNull Context context, @NonNull Configuration configuration) {
            return context.createConfigurationContext(configuration);
        }

        static void a(@NonNull Configuration configuration, @NonNull Configuration configuration2, @NonNull Configuration configuration3) {
            if (configuration.densityDpi != configuration2.densityDpi) {
                configuration3.densityDpi = configuration2.densityDpi;
            }
        }
    }

    @RequiresApi(21)
    static class Api21Impl {
        private Api21Impl() {
        }

        static boolean a(PowerManager powerManager) {
            return powerManager.isPowerSaveMode();
        }
    }

    @RequiresApi(24)
    static class Api24Impl {
        private Api24Impl() {
        }

        static void a(@NonNull Configuration configuration, @NonNull Configuration configuration2, @NonNull Configuration configuration3) {
            LocaleList locales = configuration.getLocales();
            LocaleList locales2 = configuration2.getLocales();
            if (locales.equals(locales2)) {
                return;
            }
            configuration3.setLocales(locales2);
            configuration3.locale = configuration2.locale;
        }
    }

    @RequiresApi(26)
    static class Api26Impl {
        private Api26Impl() {
        }

        static void a(@NonNull Configuration configuration, @NonNull Configuration configuration2, @NonNull Configuration configuration3) {
            if ((configuration.colorMode & 3) != (configuration2.colorMode & 3)) {
                configuration3.colorMode |= configuration2.colorMode & 3;
            }
            if ((configuration.colorMode & 12) != (configuration2.colorMode & 12)) {
                configuration3.colorMode |= configuration2.colorMode & 12;
            }
        }
    }

    class AppCompatWindowCallback extends WindowCallbackWrapper {
        AppCompatWindowCallback(Window.Callback callback) {
            super(callback);
        }

        final android.view.ActionMode a(ActionMode.Callback callback) {
            SupportActionModeWrapper.CallbackWrapper callbackWrapper = new SupportActionModeWrapper.CallbackWrapper(AppCompatDelegateImpl.this.b, callback);
            androidx.appcompat.view.ActionMode actionModeStartSupportActionMode = AppCompatDelegateImpl.this.startSupportActionMode(callbackWrapper);
            if (actionModeStartSupportActionMode != null) {
                return callbackWrapper.getActionModeWrapper(actionModeStartSupportActionMode);
            }
            return null;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return AppCompatDelegateImpl.this.a(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
            return super.dispatchKeyShortcutEvent(keyEvent) || AppCompatDelegateImpl.this.a(keyEvent.getKeyCode(), keyEvent);
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public void onContentChanged() {
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean onCreatePanelMenu(int i, Menu menu) {
            if (i != 0 || (menu instanceof MenuBuilder)) {
                return super.onCreatePanelMenu(i, menu);
            }
            return false;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean onMenuOpened(int i, Menu menu) {
            super.onMenuOpened(i, menu);
            AppCompatDelegateImpl.this.b(i);
            return true;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public void onPanelClosed(int i, Menu menu) {
            super.onPanelClosed(i, menu);
            AppCompatDelegateImpl.this.a(i);
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean onPreparePanel(int i, View view, Menu menu) {
            MenuBuilder menuBuilder = menu instanceof MenuBuilder ? (MenuBuilder) menu : null;
            if (i == 0 && menuBuilder == null) {
                return false;
            }
            if (menuBuilder != null) {
                menuBuilder.setOverrideVisibleItems(true);
            }
            boolean zOnPreparePanel = super.onPreparePanel(i, view, menu);
            if (menuBuilder != null) {
                menuBuilder.setOverrideVisibleItems(false);
            }
            return zOnPreparePanel;
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        @RequiresApi(24)
        public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu, int i) {
            PanelFeatureState panelFeatureStateA = AppCompatDelegateImpl.this.a(0, true);
            if (panelFeatureStateA != null && panelFeatureStateA.j != null) {
                menu = panelFeatureStateA.j;
            }
            super.onProvideKeyboardShortcuts(list, menu, i);
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        public android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            if (Build.VERSION.SDK_INT >= 23) {
                return null;
            }
            return AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled() ? a(callback) : super.onWindowStartingActionMode(callback);
        }

        @Override // androidx.appcompat.view.WindowCallbackWrapper, android.view.Window.Callback
        @RequiresApi(23)
        public android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
            return (AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled() && i == 0) ? a(callback) : super.onWindowStartingActionMode(callback, i);
        }
    }

    private class AutoBatteryNightModeManager extends AutoNightModeManager {
        private final PowerManager mPowerManager;

        AutoBatteryNightModeManager(Context context) {
            super();
            this.mPowerManager = (PowerManager) context.getApplicationContext().getSystemService("power");
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        IntentFilter a() {
            if (Build.VERSION.SDK_INT < 21) {
                return null;
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
            return intentFilter;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        public int getApplyableNightMode() {
            return (Build.VERSION.SDK_INT < 21 || !Api21Impl.a(this.mPowerManager)) ? 1 : 2;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        public void onChange() {
            AppCompatDelegateImpl.this.applyDayNight();
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @VisibleForTesting
    abstract class AutoNightModeManager {
        private BroadcastReceiver mReceiver;

        AutoNightModeManager() {
        }

        @Nullable
        abstract IntentFilter a();

        void b() {
            c();
            IntentFilter intentFilterA = a();
            if (intentFilterA == null || intentFilterA.countActions() == 0) {
                return;
            }
            if (this.mReceiver == null) {
                this.mReceiver = new BroadcastReceiver() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager.1
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context, Intent intent) {
                        AutoNightModeManager.this.onChange();
                    }
                };
            }
            AppCompatDelegateImpl.this.b.registerReceiver(this.mReceiver, intentFilterA);
        }

        void c() {
            if (this.mReceiver != null) {
                try {
                    AppCompatDelegateImpl.this.b.unregisterReceiver(this.mReceiver);
                } catch (IllegalArgumentException unused) {
                }
                this.mReceiver = null;
            }
        }

        abstract int getApplyableNightMode();

        abstract void onChange();
    }

    private class AutoTimeNightModeManager extends AutoNightModeManager {
        private final TwilightManager mTwilightManager;

        AutoTimeNightModeManager(TwilightManager twilightManager) {
            super();
            this.mTwilightManager = twilightManager;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        IntentFilter a() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(b.N);
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            intentFilter.addAction("android.intent.action.TIME_TICK");
            return intentFilter;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        public int getApplyableNightMode() {
            return this.mTwilightManager.a() ? 2 : 1;
        }

        @Override // androidx.appcompat.app.AppCompatDelegateImpl.AutoNightModeManager
        public void onChange() {
            AppCompatDelegateImpl.this.applyDayNight();
        }
    }

    @RequiresApi(17)
    private static class ContextThemeWrapperCompatApi17Impl {
        private ContextThemeWrapperCompatApi17Impl() {
        }

        static void a(ContextThemeWrapper contextThemeWrapper, Configuration configuration) {
            contextThemeWrapper.applyOverrideConfiguration(configuration);
        }
    }

    private class ListMenuDecorView extends ContentFrameLayout {
        public ListMenuDecorView(Context context) {
            super(context);
        }

        private boolean isOutOfBounds(int i, int i2) {
            return i < -5 || i2 < -5 || i > getWidth() + 5 || i2 > getHeight() + 5;
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return AppCompatDelegateImpl.this.a(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        @Override // android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0 || !isOutOfBounds((int) motionEvent.getX(), (int) motionEvent.getY())) {
                return super.onInterceptTouchEvent(motionEvent);
            }
            AppCompatDelegateImpl.this.c(0);
            return true;
        }

        @Override // android.view.View
        public void setBackgroundResource(int i) {
            setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), i));
        }
    }

    protected static final class PanelFeatureState {
        int a;
        int b;
        int c;
        int d;
        int e;
        int f;
        ViewGroup g;
        View h;
        View i;
        MenuBuilder j;
        ListMenuPresenter k;
        Context l;
        boolean m;
        boolean n;
        boolean o;
        boolean p = false;
        boolean q;
        public boolean qwertyMode;
        Bundle r;

        @SuppressLint({"BanParcelableUsage"})
        private static class SavedState implements Parcelable {
            public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.PanelFeatureState.SavedState.1
                @Override // android.os.Parcelable.Creator
                public SavedState createFromParcel(Parcel parcel) {
                    return SavedState.a(parcel, null);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.ClassLoaderCreator
                public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                    return SavedState.a(parcel, classLoader);
                }

                @Override // android.os.Parcelable.Creator
                public SavedState[] newArray(int i) {
                    return new SavedState[i];
                }
            };
            int a;
            boolean b;
            Bundle c;

            SavedState() {
            }

            static SavedState a(Parcel parcel, ClassLoader classLoader) {
                SavedState savedState = new SavedState();
                savedState.a = parcel.readInt();
                savedState.b = parcel.readInt() == 1;
                if (savedState.b) {
                    savedState.c = parcel.readBundle(classLoader);
                }
                return savedState;
            }

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(this.a);
                parcel.writeInt(this.b ? 1 : 0);
                if (this.b) {
                    parcel.writeBundle(this.c);
                }
            }
        }

        PanelFeatureState(int i) {
            this.a = i;
        }

        MenuView a(MenuPresenter.Callback callback) {
            if (this.j == null) {
                return null;
            }
            if (this.k == null) {
                this.k = new ListMenuPresenter(this.l, R.layout.abc_list_menu_item_layout);
                this.k.setCallback(callback);
                this.j.addMenuPresenter(this.k);
            }
            return this.k.getMenuView(this.g);
        }

        void a(Context context) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme themeNewTheme = context.getResources().newTheme();
            themeNewTheme.setTo(context.getTheme());
            themeNewTheme.resolveAttribute(R.attr.actionBarPopupTheme, typedValue, true);
            if (typedValue.resourceId != 0) {
                themeNewTheme.applyStyle(typedValue.resourceId, true);
            }
            themeNewTheme.resolveAttribute(R.attr.panelMenuListTheme, typedValue, true);
            themeNewTheme.applyStyle(typedValue.resourceId != 0 ? typedValue.resourceId : R.style.Theme_AppCompat_CompactMenu, true);
            androidx.appcompat.view.ContextThemeWrapper contextThemeWrapper = new androidx.appcompat.view.ContextThemeWrapper(context, 0);
            contextThemeWrapper.getTheme().setTo(themeNewTheme);
            this.l = contextThemeWrapper;
            TypedArray typedArrayObtainStyledAttributes = contextThemeWrapper.obtainStyledAttributes(R.styleable.AppCompatTheme);
            this.b = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTheme_panelBackground, 0);
            this.f = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTheme_android_windowAnimationStyle, 0);
            typedArrayObtainStyledAttributes.recycle();
        }

        void a(MenuBuilder menuBuilder) {
            if (menuBuilder == this.j) {
                return;
            }
            if (this.j != null) {
                this.j.removeMenuPresenter(this.k);
            }
            this.j = menuBuilder;
            if (menuBuilder == null || this.k == null) {
                return;
            }
            menuBuilder.addMenuPresenter(this.k);
        }

        public void clearMenuPresenters() {
            if (this.j != null) {
                this.j.removeMenuPresenter(this.k);
            }
            this.k = null;
        }

        public boolean hasPanelItems() {
            if (this.h == null) {
                return false;
            }
            return this.i != null || this.k.getAdapter().getCount() > 0;
        }
    }

    private final class PanelMenuPresenterCallback implements MenuPresenter.Callback {
        PanelMenuPresenterCallback() {
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public void onCloseMenu(@NonNull MenuBuilder menuBuilder, boolean z) {
            MenuBuilder rootMenu = menuBuilder.getRootMenu();
            boolean z2 = rootMenu != menuBuilder;
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (z2) {
                menuBuilder = rootMenu;
            }
            PanelFeatureState panelFeatureStateA = appCompatDelegateImpl.a((Menu) menuBuilder);
            if (panelFeatureStateA != null) {
                if (!z2) {
                    AppCompatDelegateImpl.this.a(panelFeatureStateA, z);
                } else {
                    AppCompatDelegateImpl.this.a(panelFeatureStateA.a, panelFeatureStateA, rootMenu);
                    AppCompatDelegateImpl.this.a(panelFeatureStateA, true);
                }
            }
        }

        @Override // androidx.appcompat.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(@NonNull MenuBuilder menuBuilder) {
            Window.Callback callbackB;
            if (menuBuilder != menuBuilder.getRootMenu() || !AppCompatDelegateImpl.this.m || (callbackB = AppCompatDelegateImpl.this.b()) == null || AppCompatDelegateImpl.this.r) {
                return true;
            }
            callbackB.onMenuOpened(108, menuBuilder);
            return true;
        }
    }

    static {
        IS_PRE_LOLLIPOP = Build.VERSION.SDK_INT < 21;
        sWindowBackgroundStyleable = new int[]{android.R.attr.windowBackground};
        sCanReturnDifferentContext = !"robolectric".equals(Build.FINGERPRINT);
        sCanApplyOverrideConfiguration = Build.VERSION.SDK_INT >= 17;
        if (!IS_PRE_LOLLIPOP || sInstalledExceptionHandler) {
            return;
        }
        final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.1
            private boolean shouldWrapException(Throwable th) {
                String message;
                if (!(th instanceof Resources.NotFoundException) || (message = th.getMessage()) == null) {
                    return false;
                }
                return message.contains("drawable") || message.contains("Drawable");
            }

            @Override // java.lang.Thread.UncaughtExceptionHandler
            public void uncaughtException(@NonNull Thread thread, @NonNull Throwable th) {
                if (!shouldWrapException(th)) {
                    defaultUncaughtExceptionHandler.uncaughtException(thread, th);
                    return;
                }
                Resources.NotFoundException notFoundException = new Resources.NotFoundException(th.getMessage() + ". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.");
                notFoundException.initCause(th.getCause());
                notFoundException.setStackTrace(th.getStackTrace());
                defaultUncaughtExceptionHandler.uncaughtException(thread, notFoundException);
            }
        });
        sInstalledExceptionHandler = true;
    }

    AppCompatDelegateImpl(Activity activity, AppCompatCallback appCompatCallback) {
        this(activity, null, appCompatCallback, activity);
    }

    AppCompatDelegateImpl(Dialog dialog, AppCompatCallback appCompatCallback) {
        this(dialog.getContext(), dialog.getWindow(), appCompatCallback, dialog);
    }

    AppCompatDelegateImpl(Context context, Activity activity, AppCompatCallback appCompatCallback) {
        this(context, null, appCompatCallback, activity);
    }

    AppCompatDelegateImpl(Context context, Window window, AppCompatCallback appCompatCallback) {
        this(context, window, appCompatCallback, context);
    }

    private AppCompatDelegateImpl(Context context, Window window, AppCompatCallback appCompatCallback, Object obj) {
        Integer num;
        AppCompatActivity appCompatActivityTryUnwrapContext;
        this.k = null;
        this.mHandleNativeActionModes = true;
        this.mLocalNightMode = -100;
        this.mInvalidatePanelMenuRunnable = new Runnable() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.2
            @Override // java.lang.Runnable
            public void run() {
                if ((AppCompatDelegateImpl.this.t & 1) != 0) {
                    AppCompatDelegateImpl.this.d(0);
                }
                if ((AppCompatDelegateImpl.this.t & 4096) != 0) {
                    AppCompatDelegateImpl.this.d(108);
                }
                AppCompatDelegateImpl.this.s = false;
                AppCompatDelegateImpl.this.t = 0;
            }
        };
        this.b = context;
        this.d = appCompatCallback;
        this.a = obj;
        if (this.mLocalNightMode == -100 && (this.a instanceof Dialog) && (appCompatActivityTryUnwrapContext = tryUnwrapContext()) != null) {
            this.mLocalNightMode = appCompatActivityTryUnwrapContext.getDelegate().getLocalNightMode();
        }
        if (this.mLocalNightMode == -100 && (num = sLocalNightModes.get(this.a.getClass().getName())) != null) {
            this.mLocalNightMode = num.intValue();
            sLocalNightModes.remove(this.a.getClass().getName());
        }
        if (window != null) {
            attachToWindow(window);
        }
        AppCompatDrawableManager.preload();
    }

    private boolean applyDayNight(boolean z) {
        if (this.r) {
            return false;
        }
        int iCalculateNightMode = calculateNightMode();
        boolean zUpdateForNightMode = updateForNightMode(a(this.b, iCalculateNightMode), z);
        if (iCalculateNightMode == 0) {
            getAutoTimeNightModeManager(this.b).b();
        } else if (this.mAutoTimeNightModeManager != null) {
            this.mAutoTimeNightModeManager.c();
        }
        if (iCalculateNightMode == 3) {
            getAutoBatteryNightModeManager(this.b).b();
        } else if (this.mAutoBatteryNightModeManager != null) {
            this.mAutoBatteryNightModeManager.c();
        }
        return zUpdateForNightMode;
    }

    private void applyFixedSizeWindow() {
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout) this.l.findViewById(android.R.id.content);
        View decorView = this.c.getDecorView();
        contentFrameLayout.setDecorPadding(decorView.getPaddingLeft(), decorView.getPaddingTop(), decorView.getPaddingRight(), decorView.getPaddingBottom());
        TypedArray typedArrayObtainStyledAttributes = this.b.obtainStyledAttributes(R.styleable.AppCompatTheme);
        typedArrayObtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowMinWidthMajor, contentFrameLayout.getMinWidthMajor());
        typedArrayObtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowMinWidthMinor, contentFrameLayout.getMinWidthMinor());
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTheme_windowFixedWidthMajor)) {
            typedArrayObtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowFixedWidthMajor, contentFrameLayout.getFixedWidthMajor());
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTheme_windowFixedWidthMinor)) {
            typedArrayObtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowFixedWidthMinor, contentFrameLayout.getFixedWidthMinor());
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTheme_windowFixedHeightMajor)) {
            typedArrayObtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowFixedHeightMajor, contentFrameLayout.getFixedHeightMajor());
        }
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTheme_windowFixedHeightMinor)) {
            typedArrayObtainStyledAttributes.getValue(R.styleable.AppCompatTheme_windowFixedHeightMinor, contentFrameLayout.getFixedHeightMinor());
        }
        typedArrayObtainStyledAttributes.recycle();
        contentFrameLayout.requestLayout();
    }

    private void attachToWindow(@NonNull Window window) {
        if (this.c != null) {
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        Window.Callback callback = window.getCallback();
        if (callback instanceof AppCompatWindowCallback) {
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        this.mAppCompatWindowCallback = new AppCompatWindowCallback(callback);
        window.setCallback(this.mAppCompatWindowCallback);
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.b, (AttributeSet) null, sWindowBackgroundStyleable);
        Drawable drawableIfKnown = tintTypedArrayObtainStyledAttributes.getDrawableIfKnown(0);
        if (drawableIfKnown != null) {
            window.setBackgroundDrawable(drawableIfKnown);
        }
        tintTypedArrayObtainStyledAttributes.recycle();
        this.c = window;
    }

    private int calculateNightMode() {
        return this.mLocalNightMode != -100 ? this.mLocalNightMode : getDefaultNightMode();
    }

    private void cleanupAutoManagers() {
        if (this.mAutoTimeNightModeManager != null) {
            this.mAutoTimeNightModeManager.c();
        }
        if (this.mAutoBatteryNightModeManager != null) {
            this.mAutoBatteryNightModeManager.c();
        }
    }

    @NonNull
    private Configuration createOverrideConfigurationForDayNight(@NonNull Context context, int i, @Nullable Configuration configuration) {
        int i2;
        switch (i) {
            case 1:
                i2 = 16;
                break;
            case 2:
                i2 = 32;
                break;
            default:
                i2 = context.getApplicationContext().getResources().getConfiguration().uiMode & 48;
                break;
        }
        Configuration configuration2 = new Configuration();
        configuration2.fontScale = 0.0f;
        if (configuration != null) {
            configuration2.setTo(configuration);
        }
        configuration2.uiMode = i2 | (configuration2.uiMode & (-49));
        return configuration2;
    }

    private ViewGroup createSubDecor() {
        ViewGroup viewGroup;
        TypedArray typedArrayObtainStyledAttributes = this.b.obtainStyledAttributes(R.styleable.AppCompatTheme);
        if (!typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTheme_windowActionBar)) {
            typedArrayObtainStyledAttributes.recycle();
            throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
        }
        if (typedArrayObtainStyledAttributes.getBoolean(R.styleable.AppCompatTheme_windowNoTitle, false)) {
            requestWindowFeature(1);
        } else if (typedArrayObtainStyledAttributes.getBoolean(R.styleable.AppCompatTheme_windowActionBar, false)) {
            requestWindowFeature(108);
        }
        if (typedArrayObtainStyledAttributes.getBoolean(R.styleable.AppCompatTheme_windowActionBarOverlay, false)) {
            requestWindowFeature(109);
        }
        if (typedArrayObtainStyledAttributes.getBoolean(R.styleable.AppCompatTheme_windowActionModeOverlay, false)) {
            requestWindowFeature(10);
        }
        this.p = typedArrayObtainStyledAttributes.getBoolean(R.styleable.AppCompatTheme_android_windowIsFloating, false);
        typedArrayObtainStyledAttributes.recycle();
        ensureWindow();
        this.c.getDecorView();
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.b);
        if (this.q) {
            viewGroup = (ViewGroup) layoutInflaterFrom.inflate(this.o ? R.layout.abc_screen_simple_overlay_action_mode : R.layout.abc_screen_simple, (ViewGroup) null);
        } else if (this.p) {
            viewGroup = (ViewGroup) layoutInflaterFrom.inflate(R.layout.abc_dialog_title_material, (ViewGroup) null);
            this.n = false;
            this.m = false;
        } else if (this.m) {
            TypedValue typedValue = new TypedValue();
            this.b.getTheme().resolveAttribute(R.attr.actionBarTheme, typedValue, true);
            viewGroup = (ViewGroup) LayoutInflater.from(typedValue.resourceId != 0 ? new androidx.appcompat.view.ContextThemeWrapper(this.b, typedValue.resourceId) : this.b).inflate(R.layout.abc_screen_toolbar, (ViewGroup) null);
            this.mDecorContentParent = (DecorContentParent) viewGroup.findViewById(R.id.decor_content_parent);
            this.mDecorContentParent.setWindowCallback(b());
            if (this.n) {
                this.mDecorContentParent.initFeature(109);
            }
            if (this.mFeatureProgress) {
                this.mDecorContentParent.initFeature(2);
            }
            if (this.mFeatureIndeterminateProgress) {
                this.mDecorContentParent.initFeature(5);
            }
        } else {
            viewGroup = null;
        }
        if (viewGroup == null) {
            throw new IllegalArgumentException("AppCompat does not support the current theme features: { windowActionBar: " + this.m + ", windowActionBarOverlay: " + this.n + ", android:windowIsFloating: " + this.p + ", windowActionModeOverlay: " + this.o + ", windowNoTitle: " + this.q + " }");
        }
        if (Build.VERSION.SDK_INT >= 21) {
            ViewCompat.setOnApplyWindowInsetsListener(viewGroup, new OnApplyWindowInsetsListener() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.3
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                    int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
                    int iA = AppCompatDelegateImpl.this.a(windowInsetsCompat, (Rect) null);
                    if (systemWindowInsetTop != iA) {
                        windowInsetsCompat = windowInsetsCompat.replaceSystemWindowInsets(windowInsetsCompat.getSystemWindowInsetLeft(), iA, windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom());
                    }
                    return ViewCompat.onApplyWindowInsets(view, windowInsetsCompat);
                }
            });
        } else if (viewGroup instanceof FitWindowsViewGroup) {
            ((FitWindowsViewGroup) viewGroup).setOnFitSystemWindowsListener(new FitWindowsViewGroup.OnFitSystemWindowsListener() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.4
                @Override // androidx.appcompat.widget.FitWindowsViewGroup.OnFitSystemWindowsListener
                public void onFitSystemWindows(Rect rect) {
                    rect.top = AppCompatDelegateImpl.this.a((WindowInsetsCompat) null, rect);
                }
            });
        }
        if (this.mDecorContentParent == null) {
            this.mTitleView = (TextView) viewGroup.findViewById(R.id.title);
        }
        ViewUtils.makeOptionalFitsSystemWindows(viewGroup);
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout) viewGroup.findViewById(R.id.action_bar_activity_content);
        ViewGroup viewGroup2 = (ViewGroup) this.c.findViewById(android.R.id.content);
        if (viewGroup2 != null) {
            while (viewGroup2.getChildCount() > 0) {
                View childAt = viewGroup2.getChildAt(0);
                viewGroup2.removeViewAt(0);
                contentFrameLayout.addView(childAt);
            }
            viewGroup2.setId(-1);
            contentFrameLayout.setId(android.R.id.content);
            if (viewGroup2 instanceof FrameLayout) {
                ((FrameLayout) viewGroup2).setForeground(null);
            }
        }
        this.c.setContentView(viewGroup);
        contentFrameLayout.setAttachListener(new ContentFrameLayout.OnAttachListener() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.5
            @Override // androidx.appcompat.widget.ContentFrameLayout.OnAttachListener
            public void onAttachedFromWindow() {
            }

            @Override // androidx.appcompat.widget.ContentFrameLayout.OnAttachListener
            public void onDetachedFromWindow() {
                AppCompatDelegateImpl.this.h();
            }
        });
        return viewGroup;
    }

    private void ensureSubDecor() {
        if (this.mSubDecorInstalled) {
            return;
        }
        this.l = createSubDecor();
        CharSequence charSequenceD = d();
        if (!TextUtils.isEmpty(charSequenceD)) {
            if (this.mDecorContentParent != null) {
                this.mDecorContentParent.setWindowTitle(charSequenceD);
            } else if (a() != null) {
                a().setWindowTitle(charSequenceD);
            } else if (this.mTitleView != null) {
                this.mTitleView.setText(charSequenceD);
            }
        }
        applyFixedSizeWindow();
        a(this.l);
        this.mSubDecorInstalled = true;
        PanelFeatureState panelFeatureStateA = a(0, false);
        if (this.r) {
            return;
        }
        if (panelFeatureStateA == null || panelFeatureStateA.j == null) {
            invalidatePanelMenu(108);
        }
    }

    private void ensureWindow() {
        if (this.c == null && (this.a instanceof Activity)) {
            attachToWindow(((Activity) this.a).getWindow());
        }
        if (this.c == null) {
            throw new IllegalStateException("We have not been given a Window");
        }
    }

    @NonNull
    private static Configuration generateConfigDelta(@NonNull Configuration configuration, @Nullable Configuration configuration2) {
        Configuration configuration3 = new Configuration();
        configuration3.fontScale = 0.0f;
        if (configuration2 != null && configuration.diff(configuration2) != 0) {
            if (configuration.fontScale != configuration2.fontScale) {
                configuration3.fontScale = configuration2.fontScale;
            }
            if (configuration.mcc != configuration2.mcc) {
                configuration3.mcc = configuration2.mcc;
            }
            if (configuration.mnc != configuration2.mnc) {
                configuration3.mnc = configuration2.mnc;
            }
            if (Build.VERSION.SDK_INT >= 24) {
                Api24Impl.a(configuration, configuration2, configuration3);
            } else if (!ObjectsCompat.equals(configuration.locale, configuration2.locale)) {
                configuration3.locale = configuration2.locale;
            }
            if (configuration.touchscreen != configuration2.touchscreen) {
                configuration3.touchscreen = configuration2.touchscreen;
            }
            if (configuration.keyboard != configuration2.keyboard) {
                configuration3.keyboard = configuration2.keyboard;
            }
            if (configuration.keyboardHidden != configuration2.keyboardHidden) {
                configuration3.keyboardHidden = configuration2.keyboardHidden;
            }
            if (configuration.navigation != configuration2.navigation) {
                configuration3.navigation = configuration2.navigation;
            }
            if (configuration.navigationHidden != configuration2.navigationHidden) {
                configuration3.navigationHidden = configuration2.navigationHidden;
            }
            if (configuration.orientation != configuration2.orientation) {
                configuration3.orientation = configuration2.orientation;
            }
            if ((configuration.screenLayout & 15) != (configuration2.screenLayout & 15)) {
                configuration3.screenLayout |= configuration2.screenLayout & 15;
            }
            if ((configuration.screenLayout & 192) != (configuration2.screenLayout & 192)) {
                configuration3.screenLayout |= configuration2.screenLayout & 192;
            }
            if ((configuration.screenLayout & 48) != (configuration2.screenLayout & 48)) {
                configuration3.screenLayout |= configuration2.screenLayout & 48;
            }
            if ((configuration.screenLayout & 768) != (configuration2.screenLayout & 768)) {
                configuration3.screenLayout |= configuration2.screenLayout & 768;
            }
            if (Build.VERSION.SDK_INT >= 26) {
                Api26Impl.a(configuration, configuration2, configuration3);
            }
            if ((configuration.uiMode & 15) != (configuration2.uiMode & 15)) {
                configuration3.uiMode |= configuration2.uiMode & 15;
            }
            if ((configuration.uiMode & 48) != (configuration2.uiMode & 48)) {
                configuration3.uiMode |= configuration2.uiMode & 48;
            }
            if (configuration.screenWidthDp != configuration2.screenWidthDp) {
                configuration3.screenWidthDp = configuration2.screenWidthDp;
            }
            if (configuration.screenHeightDp != configuration2.screenHeightDp) {
                configuration3.screenHeightDp = configuration2.screenHeightDp;
            }
            if (configuration.smallestScreenWidthDp != configuration2.smallestScreenWidthDp) {
                configuration3.smallestScreenWidthDp = configuration2.smallestScreenWidthDp;
            }
            if (Build.VERSION.SDK_INT >= 17) {
                Api17Impl.a(configuration, configuration2, configuration3);
            }
        }
        return configuration3;
    }

    private AutoNightModeManager getAutoBatteryNightModeManager(@NonNull Context context) {
        if (this.mAutoBatteryNightModeManager == null) {
            this.mAutoBatteryNightModeManager = new AutoBatteryNightModeManager(context);
        }
        return this.mAutoBatteryNightModeManager;
    }

    private AutoNightModeManager getAutoTimeNightModeManager(@NonNull Context context) {
        if (this.mAutoTimeNightModeManager == null) {
            this.mAutoTimeNightModeManager = new AutoTimeNightModeManager(TwilightManager.a(context));
        }
        return this.mAutoTimeNightModeManager;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void initWindowDecorActionBar() {
        WindowDecorActionBar windowDecorActionBar;
        ensureSubDecor();
        if (!this.m || this.e != null) {
            return;
        }
        if (!(this.a instanceof Activity)) {
            if (this.a instanceof Dialog) {
                windowDecorActionBar = new WindowDecorActionBar((Dialog) this.a);
            }
            if (this.e == null) {
                this.e.setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp);
                return;
            }
            return;
        }
        windowDecorActionBar = new WindowDecorActionBar((Activity) this.a, this.n);
        this.e = windowDecorActionBar;
        if (this.e == null) {
        }
    }

    private boolean initializePanelContent(PanelFeatureState panelFeatureState) {
        if (panelFeatureState.i != null) {
            panelFeatureState.h = panelFeatureState.i;
            return true;
        }
        if (panelFeatureState.j == null) {
            return false;
        }
        if (this.mPanelMenuPresenterCallback == null) {
            this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback();
        }
        panelFeatureState.h = (View) panelFeatureState.a(this.mPanelMenuPresenterCallback);
        return panelFeatureState.h != null;
    }

    private boolean initializePanelDecor(PanelFeatureState panelFeatureState) {
        panelFeatureState.a(c());
        panelFeatureState.g = new ListMenuDecorView(panelFeatureState.l);
        panelFeatureState.c = 81;
        return true;
    }

    private boolean initializePanelMenu(PanelFeatureState panelFeatureState) {
        Context context = this.b;
        if ((panelFeatureState.a == 0 || panelFeatureState.a == 108) && this.mDecorContentParent != null) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = context.getTheme();
            theme.resolveAttribute(R.attr.actionBarTheme, typedValue, true);
            Resources.Theme themeNewTheme = null;
            if (typedValue.resourceId != 0) {
                themeNewTheme = context.getResources().newTheme();
                themeNewTheme.setTo(theme);
                themeNewTheme.applyStyle(typedValue.resourceId, true);
                themeNewTheme.resolveAttribute(R.attr.actionBarWidgetTheme, typedValue, true);
            } else {
                theme.resolveAttribute(R.attr.actionBarWidgetTheme, typedValue, true);
            }
            if (typedValue.resourceId != 0) {
                if (themeNewTheme == null) {
                    themeNewTheme = context.getResources().newTheme();
                    themeNewTheme.setTo(theme);
                }
                themeNewTheme.applyStyle(typedValue.resourceId, true);
            }
            if (themeNewTheme != null) {
                androidx.appcompat.view.ContextThemeWrapper contextThemeWrapper = new androidx.appcompat.view.ContextThemeWrapper(context, 0);
                contextThemeWrapper.getTheme().setTo(themeNewTheme);
                context = contextThemeWrapper;
            }
        }
        MenuBuilder menuBuilder = new MenuBuilder(context);
        menuBuilder.setCallback(this);
        panelFeatureState.a(menuBuilder);
        return true;
    }

    private void invalidatePanelMenu(int i) {
        this.t = (1 << i) | this.t;
        if (this.s) {
            return;
        }
        ViewCompat.postOnAnimation(this.c.getDecorView(), this.mInvalidatePanelMenuRunnable);
        this.s = true;
    }

    private boolean isActivityManifestHandlingUiMode() {
        if (!this.mActivityHandlesUiModeChecked && (this.a instanceof Activity)) {
            PackageManager packageManager = this.b.getPackageManager();
            if (packageManager == null) {
                return false;
            }
            try {
                ActivityInfo activityInfo = packageManager.getActivityInfo(new ComponentName(this.b, this.a.getClass()), Build.VERSION.SDK_INT >= 29 ? 269221888 : Build.VERSION.SDK_INT >= 24 ? 786432 : 0);
                this.mActivityHandlesUiMode = (activityInfo == null || (activityInfo.configChanges & 512) == 0) ? false : true;
            } catch (PackageManager.NameNotFoundException e) {
                Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", e);
                this.mActivityHandlesUiMode = false;
            }
        }
        this.mActivityHandlesUiModeChecked = true;
        return this.mActivityHandlesUiMode;
    }

    private boolean onKeyDownPanel(int i, KeyEvent keyEvent) {
        if (keyEvent.getRepeatCount() != 0) {
            return false;
        }
        PanelFeatureState panelFeatureStateA = a(i, true);
        if (panelFeatureStateA.o) {
            return false;
        }
        return preparePanel(panelFeatureStateA, keyEvent);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0065  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean onKeyUpPanel(int i, KeyEvent keyEvent) {
        boolean zHideOverflowMenu;
        boolean zPreparePanel;
        if (this.g != null) {
            return false;
        }
        PanelFeatureState panelFeatureStateA = a(i, true);
        if (i != 0 || this.mDecorContentParent == null || !this.mDecorContentParent.canShowOverflowMenu() || ViewConfiguration.get(this.b).hasPermanentMenuKey()) {
            if (panelFeatureStateA.o || panelFeatureStateA.n) {
                zHideOverflowMenu = panelFeatureStateA.o;
                a(panelFeatureStateA, true);
            } else if (panelFeatureStateA.m) {
                if (panelFeatureStateA.q) {
                    panelFeatureStateA.m = false;
                    zPreparePanel = preparePanel(panelFeatureStateA, keyEvent);
                } else {
                    zPreparePanel = true;
                }
                if (zPreparePanel) {
                    openPanel(panelFeatureStateA, keyEvent);
                    zHideOverflowMenu = true;
                }
            } else {
                zHideOverflowMenu = false;
            }
        } else if (this.mDecorContentParent.isOverflowMenuShowing()) {
            zHideOverflowMenu = this.mDecorContentParent.hideOverflowMenu();
        } else if (!this.r && preparePanel(panelFeatureStateA, keyEvent)) {
            zHideOverflowMenu = this.mDecorContentParent.showOverflowMenu();
        }
        if (zHideOverflowMenu) {
            AudioManager audioManager = (AudioManager) this.b.getApplicationContext().getSystemService("audio");
            if (audioManager != null) {
                audioManager.playSoundEffect(0);
            } else {
                Log.w("AppCompatDelegate", "Couldn't get audio manager");
            }
        }
        return zHideOverflowMenu;
    }

    private void openPanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        int i;
        ViewGroup.LayoutParams layoutParams;
        if (panelFeatureState.o || this.r) {
            return;
        }
        if (panelFeatureState.a == 0) {
            if ((this.b.getResources().getConfiguration().screenLayout & 15) == 4) {
                return;
            }
        }
        Window.Callback callbackB = b();
        if (callbackB != null && !callbackB.onMenuOpened(panelFeatureState.a, panelFeatureState.j)) {
            a(panelFeatureState, true);
            return;
        }
        WindowManager windowManager = (WindowManager) this.b.getSystemService("window");
        if (windowManager != null && preparePanel(panelFeatureState, keyEvent)) {
            if (panelFeatureState.g != null && !panelFeatureState.p) {
                if (panelFeatureState.i != null && (layoutParams = panelFeatureState.i.getLayoutParams()) != null && layoutParams.width == -1) {
                    i = -1;
                }
                panelFeatureState.n = false;
                WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams(i, -2, panelFeatureState.d, panelFeatureState.e, 1002, 8519680, -3);
                layoutParams2.gravity = panelFeatureState.c;
                layoutParams2.windowAnimations = panelFeatureState.f;
                windowManager.addView(panelFeatureState.g, layoutParams2);
                panelFeatureState.o = true;
            }
            if (panelFeatureState.g == null) {
                if (!initializePanelDecor(panelFeatureState) || panelFeatureState.g == null) {
                    return;
                }
            } else if (panelFeatureState.p && panelFeatureState.g.getChildCount() > 0) {
                panelFeatureState.g.removeAllViews();
            }
            if (!initializePanelContent(panelFeatureState) || !panelFeatureState.hasPanelItems()) {
                panelFeatureState.p = true;
                return;
            }
            ViewGroup.LayoutParams layoutParams3 = panelFeatureState.h.getLayoutParams();
            if (layoutParams3 == null) {
                layoutParams3 = new ViewGroup.LayoutParams(-2, -2);
            }
            panelFeatureState.g.setBackgroundResource(panelFeatureState.b);
            ViewParent parent = panelFeatureState.h.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(panelFeatureState.h);
            }
            panelFeatureState.g.addView(panelFeatureState.h, layoutParams3);
            if (!panelFeatureState.h.hasFocus()) {
                panelFeatureState.h.requestFocus();
            }
            i = -2;
            panelFeatureState.n = false;
            WindowManager.LayoutParams layoutParams22 = new WindowManager.LayoutParams(i, -2, panelFeatureState.d, panelFeatureState.e, 1002, 8519680, -3);
            layoutParams22.gravity = panelFeatureState.c;
            layoutParams22.windowAnimations = panelFeatureState.f;
            windowManager.addView(panelFeatureState.g, layoutParams22);
            panelFeatureState.o = true;
        }
    }

    private boolean performPanelShortcut(PanelFeatureState panelFeatureState, int i, KeyEvent keyEvent, int i2) {
        boolean zPerformShortcut = false;
        if (keyEvent.isSystem()) {
            return false;
        }
        if ((panelFeatureState.m || preparePanel(panelFeatureState, keyEvent)) && panelFeatureState.j != null) {
            zPerformShortcut = panelFeatureState.j.performShortcut(i, keyEvent, i2);
        }
        if (zPerformShortcut && (i2 & 1) == 0 && this.mDecorContentParent == null) {
            a(panelFeatureState, true);
        }
        return zPerformShortcut;
    }

    private boolean preparePanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        if (this.r) {
            return false;
        }
        if (panelFeatureState.m) {
            return true;
        }
        if (this.mPreparedPanel != null && this.mPreparedPanel != panelFeatureState) {
            a(this.mPreparedPanel, false);
        }
        Window.Callback callbackB = b();
        if (callbackB != null) {
            panelFeatureState.i = callbackB.onCreatePanelView(panelFeatureState.a);
        }
        boolean z = panelFeatureState.a == 0 || panelFeatureState.a == 108;
        if (z && this.mDecorContentParent != null) {
            this.mDecorContentParent.setMenuPrepared();
        }
        if (panelFeatureState.i == null && (!z || !(a() instanceof ToolbarActionBar))) {
            if (panelFeatureState.j == null || panelFeatureState.q) {
                if (panelFeatureState.j == null && (!initializePanelMenu(panelFeatureState) || panelFeatureState.j == null)) {
                    return false;
                }
                if (z && this.mDecorContentParent != null) {
                    if (this.mActionMenuPresenterCallback == null) {
                        this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback();
                    }
                    this.mDecorContentParent.setMenu(panelFeatureState.j, this.mActionMenuPresenterCallback);
                }
                panelFeatureState.j.stopDispatchingItemsChanged();
                if (!callbackB.onCreatePanelMenu(panelFeatureState.a, panelFeatureState.j)) {
                    panelFeatureState.a((MenuBuilder) null);
                    if (z && this.mDecorContentParent != null) {
                        this.mDecorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
                    }
                    return false;
                }
                panelFeatureState.q = false;
            }
            panelFeatureState.j.stopDispatchingItemsChanged();
            if (panelFeatureState.r != null) {
                panelFeatureState.j.restoreActionViewStates(panelFeatureState.r);
                panelFeatureState.r = null;
            }
            if (!callbackB.onPreparePanel(0, panelFeatureState.i, panelFeatureState.j)) {
                if (z && this.mDecorContentParent != null) {
                    this.mDecorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
                }
                panelFeatureState.j.startDispatchingItemsChanged();
                return false;
            }
            panelFeatureState.qwertyMode = KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1;
            panelFeatureState.j.setQwertyMode(panelFeatureState.qwertyMode);
            panelFeatureState.j.startDispatchingItemsChanged();
        }
        panelFeatureState.m = true;
        panelFeatureState.n = false;
        this.mPreparedPanel = panelFeatureState;
        return true;
    }

    private void reopenMenu(boolean z) {
        if (this.mDecorContentParent == null || !this.mDecorContentParent.canShowOverflowMenu() || (ViewConfiguration.get(this.b).hasPermanentMenuKey() && !this.mDecorContentParent.isOverflowMenuShowPending())) {
            PanelFeatureState panelFeatureStateA = a(0, true);
            panelFeatureStateA.p = true;
            a(panelFeatureStateA, false);
            openPanel(panelFeatureStateA, null);
            return;
        }
        Window.Callback callbackB = b();
        if (this.mDecorContentParent.isOverflowMenuShowing() && z) {
            this.mDecorContentParent.hideOverflowMenu();
            if (this.r) {
                return;
            }
            callbackB.onPanelClosed(108, a(0, true).j);
            return;
        }
        if (callbackB == null || this.r) {
            return;
        }
        if (this.s && (this.t & 1) != 0) {
            this.c.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuRunnable.run();
        }
        PanelFeatureState panelFeatureStateA2 = a(0, true);
        if (panelFeatureStateA2.j == null || panelFeatureStateA2.q || !callbackB.onPreparePanel(0, panelFeatureStateA2.i, panelFeatureStateA2.j)) {
            return;
        }
        callbackB.onMenuOpened(108, panelFeatureStateA2.j);
        this.mDecorContentParent.showOverflowMenu();
    }

    private int sanitizeWindowFeatureId(int i) {
        if (i == 8) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            return 108;
        }
        if (i != 9) {
            return i;
        }
        Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
        return 109;
    }

    private boolean shouldInheritContext(ViewParent viewParent) {
        if (viewParent == null) {
            return false;
        }
        View decorView = this.c.getDecorView();
        while (viewParent != null) {
            if (viewParent == decorView || !(viewParent instanceof View) || ViewCompat.isAttachedToWindow((View) viewParent)) {
                return false;
            }
            viewParent = viewParent.getParent();
        }
        return true;
    }

    private void throwFeatureRequestIfSubDecorInstalled() {
        if (this.mSubDecorInstalled) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }

    @Nullable
    private AppCompatActivity tryUnwrapContext() {
        for (Context baseContext = this.b; baseContext != null; baseContext = ((ContextWrapper) baseContext).getBaseContext()) {
            if (baseContext instanceof AppCompatActivity) {
                return (AppCompatActivity) baseContext;
            }
            if (!(baseContext instanceof ContextWrapper)) {
                break;
            }
        }
        return null;
    }

    private boolean updateForNightMode(int i, boolean z) {
        boolean z2;
        Configuration configurationCreateOverrideConfigurationForDayNight = createOverrideConfigurationForDayNight(this.b, i, null);
        boolean zIsActivityManifestHandlingUiMode = isActivityManifestHandlingUiMode();
        int i2 = this.b.getResources().getConfiguration().uiMode & 48;
        int i3 = configurationCreateOverrideConfigurationForDayNight.uiMode & 48;
        if (i2 != i3 && z && !zIsActivityManifestHandlingUiMode && this.mBaseContextAttached && ((sCanReturnDifferentContext || this.mCreated) && (this.a instanceof Activity) && !((Activity) this.a).isChild())) {
            ActivityCompat.recreate((Activity) this.a);
            z2 = true;
        } else {
            z2 = false;
        }
        if (!z2 && i2 != i3) {
            updateResourcesConfigurationForNightMode(i3, zIsActivityManifestHandlingUiMode, null);
            z2 = true;
        }
        if (z2 && (this.a instanceof AppCompatActivity)) {
            ((AppCompatActivity) this.a).a(i);
        }
        return z2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void updateResourcesConfigurationForNightMode(int i, boolean z, @Nullable Configuration configuration) {
        Resources resources = this.b.getResources();
        Configuration configuration2 = new Configuration(resources.getConfiguration());
        if (configuration != null) {
            configuration2.updateFrom(configuration);
        }
        configuration2.uiMode = i | (resources.getConfiguration().uiMode & (-49));
        resources.updateConfiguration(configuration2, null);
        if (Build.VERSION.SDK_INT < 26) {
            ResourcesFlusher.a(resources);
        }
        if (this.mThemeResId != 0) {
            this.b.setTheme(this.mThemeResId);
            if (Build.VERSION.SDK_INT >= 23) {
                this.b.getTheme().applyStyle(this.mThemeResId, true);
            }
        }
        if (z && (this.a instanceof Activity)) {
            Activity activity = (Activity) this.a;
            if (activity instanceof LifecycleOwner) {
                if (!((LifecycleOwner) activity).getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                    return;
                }
            } else if (!this.mStarted) {
                return;
            }
            activity.onConfigurationChanged(configuration2);
        }
    }

    private void updateStatusGuardColor(View view) {
        Context context;
        int i;
        if ((ViewCompat.getWindowSystemUiVisibility(view) & 8192) != 0) {
            context = this.b;
            i = R.color.abc_decor_view_status_guard_light;
        } else {
            context = this.b;
            i = R.color.abc_decor_view_status_guard;
        }
        view.setBackgroundColor(ContextCompat.getColor(context, i));
    }

    int a(@NonNull Context context, int i) {
        AutoNightModeManager autoTimeNightModeManager;
        if (i == -100) {
            return -1;
        }
        switch (i) {
            case -1:
            case 1:
            case 2:
                return i;
            case 0:
                if (Build.VERSION.SDK_INT >= 23 && ((UiModeManager) context.getApplicationContext().getSystemService("uimode")).getNightMode() == 0) {
                    return -1;
                }
                autoTimeNightModeManager = getAutoTimeNightModeManager(context);
                break;
                break;
            case 3:
                autoTimeNightModeManager = getAutoBatteryNightModeManager(context);
                break;
            default:
                throw new IllegalStateException("Unknown value set for night mode. Please use one of the MODE_NIGHT values from AppCompatDelegate.");
        }
        return autoTimeNightModeManager.getApplyableNightMode();
    }

    final int a(@Nullable WindowInsetsCompat windowInsetsCompat, @Nullable Rect rect) {
        boolean z;
        boolean z2;
        int systemWindowInsetTop = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : rect != null ? rect.top : 0;
        if (this.h == null || !(this.h.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            z = false;
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.h.getLayoutParams();
            if (this.h.isShown()) {
                if (this.mTempRect1 == null) {
                    this.mTempRect1 = new Rect();
                    this.mTempRect2 = new Rect();
                }
                Rect rect2 = this.mTempRect1;
                Rect rect3 = this.mTempRect2;
                if (windowInsetsCompat == null) {
                    rect2.set(rect);
                } else {
                    rect2.set(windowInsetsCompat.getSystemWindowInsetLeft(), windowInsetsCompat.getSystemWindowInsetTop(), windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom());
                }
                ViewUtils.computeFitSystemWindows(this.l, rect2, rect3);
                int i = rect2.top;
                int i2 = rect2.left;
                int i3 = rect2.right;
                WindowInsetsCompat rootWindowInsets = ViewCompat.getRootWindowInsets(this.l);
                int systemWindowInsetLeft = rootWindowInsets == null ? 0 : rootWindowInsets.getSystemWindowInsetLeft();
                int systemWindowInsetRight = rootWindowInsets == null ? 0 : rootWindowInsets.getSystemWindowInsetRight();
                if (marginLayoutParams.topMargin == i && marginLayoutParams.leftMargin == i2 && marginLayoutParams.rightMargin == i3) {
                    z2 = false;
                } else {
                    marginLayoutParams.topMargin = i;
                    marginLayoutParams.leftMargin = i2;
                    marginLayoutParams.rightMargin = i3;
                    z2 = true;
                }
                if (i > 0 && this.mStatusGuard == null) {
                    this.mStatusGuard = new View(this.b);
                    this.mStatusGuard.setVisibility(8);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, marginLayoutParams.topMargin, 51);
                    layoutParams.leftMargin = systemWindowInsetLeft;
                    layoutParams.rightMargin = systemWindowInsetRight;
                    this.l.addView(this.mStatusGuard, -1, layoutParams);
                } else if (this.mStatusGuard != null) {
                    ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mStatusGuard.getLayoutParams();
                    if (marginLayoutParams2.height != marginLayoutParams.topMargin || marginLayoutParams2.leftMargin != systemWindowInsetLeft || marginLayoutParams2.rightMargin != systemWindowInsetRight) {
                        marginLayoutParams2.height = marginLayoutParams.topMargin;
                        marginLayoutParams2.leftMargin = systemWindowInsetLeft;
                        marginLayoutParams2.rightMargin = systemWindowInsetRight;
                        this.mStatusGuard.setLayoutParams(marginLayoutParams2);
                    }
                }
                z = this.mStatusGuard != null;
                if (z && this.mStatusGuard.getVisibility() != 0) {
                    updateStatusGuardColor(this.mStatusGuard);
                }
                if (!this.o && z) {
                    systemWindowInsetTop = 0;
                }
            } else if (marginLayoutParams.topMargin != 0) {
                marginLayoutParams.topMargin = 0;
                z2 = true;
                z = false;
            } else {
                z2 = false;
                z = false;
            }
            if (z2) {
                this.h.setLayoutParams(marginLayoutParams);
            }
        }
        if (this.mStatusGuard != null) {
            this.mStatusGuard.setVisibility(z ? 0 : 8);
        }
        return systemWindowInsetTop;
    }

    final ActionBar a() {
        return this.e;
    }

    protected PanelFeatureState a(int i, boolean z) {
        PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        if (panelFeatureStateArr == null || panelFeatureStateArr.length <= i) {
            PanelFeatureState[] panelFeatureStateArr2 = new PanelFeatureState[i + 1];
            if (panelFeatureStateArr != null) {
                System.arraycopy(panelFeatureStateArr, 0, panelFeatureStateArr2, 0, panelFeatureStateArr.length);
            }
            this.mPanels = panelFeatureStateArr2;
            panelFeatureStateArr = panelFeatureStateArr2;
        }
        PanelFeatureState panelFeatureState = panelFeatureStateArr[i];
        if (panelFeatureState != null) {
            return panelFeatureState;
        }
        PanelFeatureState panelFeatureState2 = new PanelFeatureState(i);
        panelFeatureStateArr[i] = panelFeatureState2;
        return panelFeatureState2;
    }

    PanelFeatureState a(Menu menu) {
        PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        int length = panelFeatureStateArr != null ? panelFeatureStateArr.length : 0;
        for (int i = 0; i < length; i++) {
            PanelFeatureState panelFeatureState = panelFeatureStateArr[i];
            if (panelFeatureState != null && panelFeatureState.j == menu) {
                return panelFeatureState;
            }
        }
        return null;
    }

    androidx.appcompat.view.ActionMode a(@NonNull ActionMode.Callback callback) {
        androidx.appcompat.view.ActionMode actionModeOnWindowStartingSupportActionMode;
        Context contextThemeWrapper;
        f();
        if (this.g != null) {
            this.g.finish();
        }
        if (!(callback instanceof ActionModeCallbackWrapperV9)) {
            callback = new ActionModeCallbackWrapperV9(callback);
        }
        if (this.d == null || this.r) {
            actionModeOnWindowStartingSupportActionMode = null;
        } else {
            try {
                actionModeOnWindowStartingSupportActionMode = this.d.onWindowStartingSupportActionMode(callback);
            } catch (AbstractMethodError unused) {
                actionModeOnWindowStartingSupportActionMode = null;
            }
        }
        if (actionModeOnWindowStartingSupportActionMode != null) {
            this.g = actionModeOnWindowStartingSupportActionMode;
        } else {
            if (this.h == null) {
                if (this.p) {
                    TypedValue typedValue = new TypedValue();
                    Resources.Theme theme = this.b.getTheme();
                    theme.resolveAttribute(R.attr.actionBarTheme, typedValue, true);
                    if (typedValue.resourceId != 0) {
                        Resources.Theme themeNewTheme = this.b.getResources().newTheme();
                        themeNewTheme.setTo(theme);
                        themeNewTheme.applyStyle(typedValue.resourceId, true);
                        contextThemeWrapper = new androidx.appcompat.view.ContextThemeWrapper(this.b, 0);
                        contextThemeWrapper.getTheme().setTo(themeNewTheme);
                    } else {
                        contextThemeWrapper = this.b;
                    }
                    this.h = new ActionBarContextView(contextThemeWrapper);
                    this.i = new PopupWindow(contextThemeWrapper, (AttributeSet) null, R.attr.actionModePopupWindowStyle);
                    PopupWindowCompat.setWindowLayoutType(this.i, 2);
                    this.i.setContentView(this.h);
                    this.i.setWidth(-1);
                    contextThemeWrapper.getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true);
                    this.h.setContentHeight(TypedValue.complexToDimensionPixelSize(typedValue.data, contextThemeWrapper.getResources().getDisplayMetrics()));
                    this.i.setHeight(-2);
                    this.j = new Runnable() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.6
                        @Override // java.lang.Runnable
                        public void run() {
                            AppCompatDelegateImpl.this.i.showAtLocation(AppCompatDelegateImpl.this.h, 55, 0, 0);
                            AppCompatDelegateImpl.this.f();
                            if (!AppCompatDelegateImpl.this.e()) {
                                AppCompatDelegateImpl.this.h.setAlpha(1.0f);
                                AppCompatDelegateImpl.this.h.setVisibility(0);
                            } else {
                                AppCompatDelegateImpl.this.h.setAlpha(0.0f);
                                AppCompatDelegateImpl.this.k = ViewCompat.animate(AppCompatDelegateImpl.this.h).alpha(1.0f);
                                AppCompatDelegateImpl.this.k.setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.6.1
                                    @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                                    public void onAnimationEnd(View view) {
                                        AppCompatDelegateImpl.this.h.setAlpha(1.0f);
                                        AppCompatDelegateImpl.this.k.setListener(null);
                                        AppCompatDelegateImpl.this.k = null;
                                    }

                                    @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                                    public void onAnimationStart(View view) {
                                        AppCompatDelegateImpl.this.h.setVisibility(0);
                                    }
                                });
                            }
                        }
                    };
                } else {
                    ViewStubCompat viewStubCompat = (ViewStubCompat) this.l.findViewById(R.id.action_mode_bar_stub);
                    if (viewStubCompat != null) {
                        viewStubCompat.setLayoutInflater(LayoutInflater.from(c()));
                        this.h = (ActionBarContextView) viewStubCompat.inflate();
                    }
                }
            }
            if (this.h != null) {
                f();
                this.h.killMode();
                StandaloneActionMode standaloneActionMode = new StandaloneActionMode(this.h.getContext(), this.h, callback, this.i == null);
                if (callback.onCreateActionMode(standaloneActionMode, standaloneActionMode.getMenu())) {
                    standaloneActionMode.invalidate();
                    this.h.initForMode(standaloneActionMode);
                    this.g = standaloneActionMode;
                    if (e()) {
                        this.h.setAlpha(0.0f);
                        this.k = ViewCompat.animate(this.h).alpha(1.0f);
                        this.k.setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: androidx.appcompat.app.AppCompatDelegateImpl.7
                            @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                            public void onAnimationEnd(View view) {
                                AppCompatDelegateImpl.this.h.setAlpha(1.0f);
                                AppCompatDelegateImpl.this.k.setListener(null);
                                AppCompatDelegateImpl.this.k = null;
                            }

                            @Override // androidx.core.view.ViewPropertyAnimatorListenerAdapter, androidx.core.view.ViewPropertyAnimatorListener
                            public void onAnimationStart(View view) {
                                AppCompatDelegateImpl.this.h.setVisibility(0);
                                AppCompatDelegateImpl.this.h.sendAccessibilityEvent(32);
                                if (AppCompatDelegateImpl.this.h.getParent() instanceof View) {
                                    ViewCompat.requestApplyInsets((View) AppCompatDelegateImpl.this.h.getParent());
                                }
                            }
                        });
                    } else {
                        this.h.setAlpha(1.0f);
                        this.h.setVisibility(0);
                        this.h.sendAccessibilityEvent(32);
                        if (this.h.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View) this.h.getParent());
                        }
                    }
                    if (this.i != null) {
                        this.c.getDecorView().post(this.j);
                    }
                } else {
                    this.g = null;
                }
            }
        }
        if (this.g != null && this.d != null) {
            this.d.onSupportActionModeStarted(this.g);
        }
        return this.g;
    }

    void a(int i) {
        if (i == 108) {
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.dispatchMenuVisibilityChanged(false);
                return;
            }
            return;
        }
        if (i == 0) {
            PanelFeatureState panelFeatureStateA = a(i, true);
            if (panelFeatureStateA.o) {
                a(panelFeatureStateA, false);
            }
        }
    }

    void a(int i, PanelFeatureState panelFeatureState, Menu menu) {
        if (menu == null) {
            if (panelFeatureState == null && i >= 0 && i < this.mPanels.length) {
                panelFeatureState = this.mPanels[i];
            }
            if (panelFeatureState != null) {
                menu = panelFeatureState.j;
            }
        }
        if ((panelFeatureState == null || panelFeatureState.o) && !this.r) {
            this.mAppCompatWindowCallback.getWrapped().onPanelClosed(i, menu);
        }
    }

    void a(ViewGroup viewGroup) {
    }

    void a(PanelFeatureState panelFeatureState, boolean z) {
        if (z && panelFeatureState.a == 0 && this.mDecorContentParent != null && this.mDecorContentParent.isOverflowMenuShowing()) {
            a(panelFeatureState.j);
            return;
        }
        WindowManager windowManager = (WindowManager) this.b.getSystemService("window");
        if (windowManager != null && panelFeatureState.o && panelFeatureState.g != null) {
            windowManager.removeView(panelFeatureState.g);
            if (z) {
                a(panelFeatureState.a, panelFeatureState, null);
            }
        }
        panelFeatureState.m = false;
        panelFeatureState.n = false;
        panelFeatureState.o = false;
        panelFeatureState.h = null;
        panelFeatureState.p = true;
        if (this.mPreparedPanel == panelFeatureState) {
            this.mPreparedPanel = null;
        }
    }

    void a(@NonNull MenuBuilder menuBuilder) {
        if (this.mClosingActionMenu) {
            return;
        }
        this.mClosingActionMenu = true;
        this.mDecorContentParent.dismissPopups();
        Window.Callback callbackB = b();
        if (callbackB != null && !this.r) {
            callbackB.onPanelClosed(108, menuBuilder);
        }
        this.mClosingActionMenu = false;
    }

    boolean a(int i, KeyEvent keyEvent) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null && supportActionBar.onKeyShortcut(i, keyEvent)) {
            return true;
        }
        if (this.mPreparedPanel != null && performPanelShortcut(this.mPreparedPanel, keyEvent.getKeyCode(), keyEvent, 1)) {
            if (this.mPreparedPanel != null) {
                this.mPreparedPanel.n = true;
            }
            return true;
        }
        if (this.mPreparedPanel == null) {
            PanelFeatureState panelFeatureStateA = a(0, true);
            preparePanel(panelFeatureStateA, keyEvent);
            boolean zPerformPanelShortcut = performPanelShortcut(panelFeatureStateA, keyEvent.getKeyCode(), keyEvent, 1);
            panelFeatureStateA.m = false;
            if (zPerformPanelShortcut) {
                return true;
            }
        }
        return false;
    }

    boolean a(KeyEvent keyEvent) {
        View decorView;
        if (((this.a instanceof KeyEventDispatcher.Component) || (this.a instanceof AppCompatDialog)) && (decorView = this.c.getDecorView()) != null && KeyEventDispatcher.dispatchBeforeHierarchy(decorView, keyEvent)) {
            return true;
        }
        if (keyEvent.getKeyCode() == 82 && this.mAppCompatWindowCallback.getWrapped().dispatchKeyEvent(keyEvent)) {
            return true;
        }
        int keyCode = keyEvent.getKeyCode();
        return keyEvent.getAction() == 0 ? c(keyCode, keyEvent) : b(keyCode, keyEvent);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        ensureSubDecor();
        ((ViewGroup) this.l.findViewById(android.R.id.content)).addView(view, layoutParams);
        this.mAppCompatWindowCallback.getWrapped().onContentChanged();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public boolean applyDayNight() {
        return applyDayNight(true);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    @NonNull
    @CallSuper
    public Context attachBaseContext2(@NonNull Context context) {
        this.mBaseContextAttached = true;
        int iA = a(context, calculateNightMode());
        Configuration configurationGenerateConfigDelta = null;
        if (sCanApplyOverrideConfiguration && (context instanceof ContextThemeWrapper)) {
            try {
                ContextThemeWrapperCompatApi17Impl.a((ContextThemeWrapper) context, createOverrideConfigurationForDayNight(context, iA, null));
                return context;
            } catch (IllegalStateException unused) {
            }
        }
        if (context instanceof androidx.appcompat.view.ContextThemeWrapper) {
            try {
                ((androidx.appcompat.view.ContextThemeWrapper) context).applyOverrideConfiguration(createOverrideConfigurationForDayNight(context, iA, null));
                return context;
            } catch (IllegalStateException unused2) {
            }
        }
        if (!sCanReturnDifferentContext) {
            return super.attachBaseContext2(context);
        }
        if (Build.VERSION.SDK_INT >= 17) {
            Configuration configuration = new Configuration();
            configuration.uiMode = -1;
            configuration.fontScale = 0.0f;
            Configuration configuration2 = Api17Impl.a(context, configuration).getResources().getConfiguration();
            Configuration configuration3 = context.getResources().getConfiguration();
            configuration2.uiMode = configuration3.uiMode;
            if (!configuration2.equals(configuration3)) {
                configurationGenerateConfigDelta = generateConfigDelta(configuration2, configuration3);
            }
        }
        Configuration configurationCreateOverrideConfigurationForDayNight = createOverrideConfigurationForDayNight(context, iA, configurationGenerateConfigDelta);
        androidx.appcompat.view.ContextThemeWrapper contextThemeWrapper = new androidx.appcompat.view.ContextThemeWrapper(context, R.style.Theme_AppCompat_Empty);
        contextThemeWrapper.applyOverrideConfiguration(configurationCreateOverrideConfigurationForDayNight);
        boolean z = false;
        try {
            if (context.getTheme() != null) {
                z = true;
            }
        } catch (NullPointerException unused3) {
        }
        if (z) {
            ResourcesCompat.ThemeCompat.rebase(contextThemeWrapper.getTheme());
        }
        return super.attachBaseContext2(contextThemeWrapper);
    }

    final Window.Callback b() {
        return this.c.getCallback();
    }

    void b(int i) {
        ActionBar supportActionBar;
        if (i != 108 || (supportActionBar = getSupportActionBar()) == null) {
            return;
        }
        supportActionBar.dispatchMenuVisibilityChanged(true);
    }

    boolean b(int i, KeyEvent keyEvent) {
        if (i == 4) {
            boolean z = this.mLongPressBackDown;
            this.mLongPressBackDown = false;
            PanelFeatureState panelFeatureStateA = a(0, false);
            if (panelFeatureStateA != null && panelFeatureStateA.o) {
                if (!z) {
                    a(panelFeatureStateA, true);
                }
                return true;
            }
            if (g()) {
                return true;
            }
        } else if (i == 82) {
            onKeyUpPanel(0, keyEvent);
            return true;
        }
        return false;
    }

    final Context c() {
        ActionBar supportActionBar = getSupportActionBar();
        Context themedContext = supportActionBar != null ? supportActionBar.getThemedContext() : null;
        return themedContext == null ? this.b : themedContext;
    }

    void c(int i) {
        a(a(i, true), true);
    }

    boolean c(int i, KeyEvent keyEvent) {
        if (i == 4) {
            this.mLongPressBackDown = (keyEvent.getFlags() & 128) != 0;
        } else if (i == 82) {
            onKeyDownPanel(0, keyEvent);
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.appcompat.app.AppCompatDelegate
    public View createView(View view, String str, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        boolean z;
        AppCompatViewInflater appCompatViewInflater;
        boolean zShouldInheritContext = false;
        if (this.mAppCompatViewInflater == null) {
            String string = this.b.obtainStyledAttributes(R.styleable.AppCompatTheme).getString(R.styleable.AppCompatTheme_viewInflaterClass);
            if (string == null) {
                appCompatViewInflater = new AppCompatViewInflater();
            } else {
                try {
                    this.mAppCompatViewInflater = (AppCompatViewInflater) Class.forName(string).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                } catch (Throwable th) {
                    Log.i("AppCompatDelegate", "Failed to instantiate custom view inflater " + string + ". Falling back to default.", th);
                    appCompatViewInflater = new AppCompatViewInflater();
                    this.mAppCompatViewInflater = appCompatViewInflater;
                }
            }
            this.mAppCompatViewInflater = appCompatViewInflater;
        }
        if (IS_PRE_LOLLIPOP) {
            if (this.mLayoutIncludeDetector == null) {
                this.mLayoutIncludeDetector = new LayoutIncludeDetector();
            }
            if (this.mLayoutIncludeDetector.a(attributeSet)) {
                z = true;
            } else {
                if (!(attributeSet instanceof XmlPullParser)) {
                    zShouldInheritContext = shouldInheritContext((ViewParent) view);
                } else if (((XmlPullParser) attributeSet).getDepth() > 1) {
                    zShouldInheritContext = true;
                }
                z = zShouldInheritContext;
            }
        } else {
            z = zShouldInheritContext;
        }
        return this.mAppCompatViewInflater.a(view, str, context, attributeSet, z, IS_PRE_LOLLIPOP, true, VectorEnabledTintResources.shouldBeUsed());
    }

    final CharSequence d() {
        return this.a instanceof Activity ? ((Activity) this.a).getTitle() : this.mTitle;
    }

    void d(int i) {
        PanelFeatureState panelFeatureStateA;
        PanelFeatureState panelFeatureStateA2 = a(i, true);
        if (panelFeatureStateA2.j != null) {
            Bundle bundle = new Bundle();
            panelFeatureStateA2.j.saveActionViewStates(bundle);
            if (bundle.size() > 0) {
                panelFeatureStateA2.r = bundle;
            }
            panelFeatureStateA2.j.stopDispatchingItemsChanged();
            panelFeatureStateA2.j.clear();
        }
        panelFeatureStateA2.q = true;
        panelFeatureStateA2.p = true;
        if ((i != 108 && i != 0) || this.mDecorContentParent == null || (panelFeatureStateA = a(0, false)) == null) {
            return;
        }
        panelFeatureStateA.m = false;
        preparePanel(panelFeatureStateA, null);
    }

    final boolean e() {
        return this.mSubDecorInstalled && this.l != null && ViewCompat.isLaidOut(this.l);
    }

    void f() {
        if (this.k != null) {
            this.k.cancel();
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    @Nullable
    public <T extends View> T findViewById(@IdRes int i) {
        ensureSubDecor();
        return (T) this.c.findViewById(i);
    }

    boolean g() {
        if (this.g != null) {
            this.g.finish();
            return true;
        }
        ActionBar supportActionBar = getSupportActionBar();
        return supportActionBar != null && supportActionBar.collapseActionView();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
        return new ActionBarDrawableToggleImpl();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public int getLocalNightMode() {
        return this.mLocalNightMode;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public MenuInflater getMenuInflater() {
        if (this.f == null) {
            initWindowDecorActionBar();
            this.f = new SupportMenuInflater(this.e != null ? this.e.getThemedContext() : this.b);
        }
        return this.f;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public ActionBar getSupportActionBar() {
        initWindowDecorActionBar();
        return this.e;
    }

    void h() {
        if (this.mDecorContentParent != null) {
            this.mDecorContentParent.dismissPopups();
        }
        if (this.i != null) {
            this.c.getDecorView().removeCallbacks(this.j);
            if (this.i.isShowing()) {
                try {
                    this.i.dismiss();
                } catch (IllegalArgumentException unused) {
                }
            }
            this.i = null;
        }
        f();
        PanelFeatureState panelFeatureStateA = a(0, false);
        if (panelFeatureStateA == null || panelFeatureStateA.j == null) {
            return;
        }
        panelFeatureStateA.j.close();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public boolean hasWindowFeature(int i) {
        boolean z;
        switch (sanitizeWindowFeatureId(i)) {
            case 1:
                z = this.q;
                break;
            case 2:
                z = this.mFeatureProgress;
                break;
            case 5:
                z = this.mFeatureIndeterminateProgress;
                break;
            case 10:
                z = this.o;
                break;
            case 108:
                z = this.m;
                break;
            case 109:
                z = this.n;
                break;
            default:
                z = false;
                break;
        }
        return z || this.c.hasFeature(i);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void installViewFactory() {
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.b);
        if (layoutInflaterFrom.getFactory() == null) {
            LayoutInflaterCompat.setFactory2(layoutInflaterFrom, this);
        } else {
            if (layoutInflaterFrom.getFactory2() instanceof AppCompatDelegateImpl) {
                return;
            }
            Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void invalidateOptionsMenu() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar == null || !supportActionBar.invalidateOptionsMenu()) {
            invalidatePanelMenu(0);
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public boolean isHandleNativeActionModesEnabled() {
        return this.mHandleNativeActionModes;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onConfigurationChanged(Configuration configuration) {
        ActionBar supportActionBar;
        if (this.m && this.mSubDecorInstalled && (supportActionBar = getSupportActionBar()) != null) {
            supportActionBar.onConfigurationChanged(configuration);
        }
        AppCompatDrawableManager.get().onConfigurationChanged(this.b);
        applyDayNight(false);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onCreate(Bundle bundle) {
        this.mBaseContextAttached = true;
        applyDayNight(false);
        ensureWindow();
        if (this.a instanceof Activity) {
            String parentActivityName = null;
            try {
                parentActivityName = NavUtils.getParentActivityName((Activity) this.a);
            } catch (IllegalArgumentException unused) {
            }
            if (parentActivityName != null) {
                ActionBar actionBarA = a();
                if (actionBarA == null) {
                    this.mEnableDefaultActionBarUp = true;
                } else {
                    actionBarA.setDefaultDisplayHomeAsUpEnabled(true);
                }
            }
            a(this);
        }
        this.mCreated = true;
    }

    @Override // android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return createView(view, str, context, attributeSet);
    }

    @Override // android.view.LayoutInflater.Factory
    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onDestroy() {
        if (this.a instanceof Activity) {
            b(this);
        }
        if (this.s) {
            this.c.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
        }
        this.mStarted = false;
        this.r = true;
        if (this.mLocalNightMode != -100 && (this.a instanceof Activity) && ((Activity) this.a).isChangingConfigurations()) {
            sLocalNightModes.put(this.a.getClass().getName(), Integer.valueOf(this.mLocalNightMode));
        } else {
            sLocalNightModes.remove(this.a.getClass().getName());
        }
        if (this.e != null) {
            this.e.a();
        }
        cleanupAutoManagers();
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
    public boolean onMenuItemSelected(@NonNull MenuBuilder menuBuilder, @NonNull MenuItem menuItem) {
        PanelFeatureState panelFeatureStateA;
        Window.Callback callbackB = b();
        if (callbackB == null || this.r || (panelFeatureStateA = a((Menu) menuBuilder.getRootMenu())) == null) {
            return false;
        }
        return callbackB.onMenuItemSelected(panelFeatureStateA.a, menuItem);
    }

    @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
    public void onMenuModeChange(@NonNull MenuBuilder menuBuilder) {
        reopenMenu(true);
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onPostCreate(Bundle bundle) {
        ensureSubDecor();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onPostResume() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setShowHideAnimationEnabled(true);
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onSaveInstanceState(Bundle bundle) {
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onStart() {
        this.mStarted = true;
        applyDayNight();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void onStop() {
        this.mStarted = false;
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setShowHideAnimationEnabled(false);
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public boolean requestWindowFeature(int i) {
        int iSanitizeWindowFeatureId = sanitizeWindowFeatureId(i);
        if (this.q && iSanitizeWindowFeatureId == 108) {
            return false;
        }
        if (this.m && iSanitizeWindowFeatureId == 1) {
            this.m = false;
        }
        switch (iSanitizeWindowFeatureId) {
            case 1:
                throwFeatureRequestIfSubDecorInstalled();
                this.q = true;
                return true;
            case 2:
                throwFeatureRequestIfSubDecorInstalled();
                this.mFeatureProgress = true;
                return true;
            case 5:
                throwFeatureRequestIfSubDecorInstalled();
                this.mFeatureIndeterminateProgress = true;
                return true;
            case 10:
                throwFeatureRequestIfSubDecorInstalled();
                this.o = true;
                return true;
            case 108:
                throwFeatureRequestIfSubDecorInstalled();
                this.m = true;
                return true;
            case 109:
                throwFeatureRequestIfSubDecorInstalled();
                this.n = true;
                return true;
            default:
                return this.c.requestFeature(iSanitizeWindowFeatureId);
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void setContentView(int i) {
        ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup) this.l.findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        LayoutInflater.from(this.b).inflate(i, viewGroup);
        this.mAppCompatWindowCallback.getWrapped().onContentChanged();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void setContentView(View view) {
        ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup) this.l.findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(view);
        this.mAppCompatWindowCallback.getWrapped().onContentChanged();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup) this.l.findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        viewGroup.addView(view, layoutParams);
        this.mAppCompatWindowCallback.getWrapped().onContentChanged();
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void setHandleNativeActionModesEnabled(boolean z) {
        this.mHandleNativeActionModes = z;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    @RequiresApi(17)
    public void setLocalNightMode(int i) {
        if (this.mLocalNightMode != i) {
            this.mLocalNightMode = i;
            if (this.mBaseContextAttached) {
                applyDayNight();
            }
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void setSupportActionBar(Toolbar toolbar) {
        Window window;
        Window.Callback wrappedWindowCallback;
        if (this.a instanceof Activity) {
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar instanceof WindowDecorActionBar) {
                throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
            }
            this.f = null;
            if (supportActionBar != null) {
                supportActionBar.a();
            }
            if (toolbar != null) {
                ToolbarActionBar toolbarActionBar = new ToolbarActionBar(toolbar, d(), this.mAppCompatWindowCallback);
                this.e = toolbarActionBar;
                window = this.c;
                wrappedWindowCallback = toolbarActionBar.getWrappedWindowCallback();
            } else {
                this.e = null;
                window = this.c;
                wrappedWindowCallback = this.mAppCompatWindowCallback;
            }
            window.setCallback(wrappedWindowCallback);
            invalidateOptionsMenu();
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public void setTheme(@StyleRes int i) {
        this.mThemeResId = i;
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public final void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        if (this.mDecorContentParent != null) {
            this.mDecorContentParent.setWindowTitle(charSequence);
        } else if (a() != null) {
            a().setWindowTitle(charSequence);
        } else if (this.mTitleView != null) {
            this.mTitleView.setText(charSequence);
        }
    }

    @Override // androidx.appcompat.app.AppCompatDelegate
    public androidx.appcompat.view.ActionMode startSupportActionMode(@NonNull ActionMode.Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("ActionMode callback can not be null.");
        }
        if (this.g != null) {
            this.g.finish();
        }
        ActionModeCallbackWrapperV9 actionModeCallbackWrapperV9 = new ActionModeCallbackWrapperV9(callback);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            this.g = supportActionBar.startActionMode(actionModeCallbackWrapperV9);
            if (this.g != null && this.d != null) {
                this.d.onSupportActionModeStarted(this.g);
            }
        }
        if (this.g == null) {
            this.g = a(actionModeCallbackWrapperV9);
        }
        return this.g;
    }
}
