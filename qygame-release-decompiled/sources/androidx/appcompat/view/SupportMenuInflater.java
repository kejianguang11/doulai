package androidx.appcompat.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import androidx.annotation.LayoutRes;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuItemWrapperICS;
import androidx.appcompat.widget.DrawableUtils;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ActionProvider;
import androidx.core.view.MenuItemCompat;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class SupportMenuInflater extends MenuInflater {
    private static final String XML_GROUP = "group";
    private static final String XML_ITEM = "item";
    private static final String XML_MENU = "menu";
    static final Class<?>[] a = {Context.class};
    static final Class<?>[] b = a;
    final Object[] c;
    final Object[] d;
    Context e;
    private Object mRealOwner;

    private static class InflatedOnMenuItemClickListener implements MenuItem.OnMenuItemClickListener {
        private static final Class<?>[] PARAM_TYPES = {MenuItem.class};
        private Method mMethod;
        private Object mRealOwner;

        public InflatedOnMenuItemClickListener(Object obj, String str) {
            this.mRealOwner = obj;
            Class<?> cls = obj.getClass();
            try {
                this.mMethod = cls.getMethod(str, PARAM_TYPES);
            } catch (Exception e) {
                InflateException inflateException = new InflateException("Couldn't resolve menu item onClick handler " + str + " in class " + cls.getName());
                inflateException.initCause(e);
                throw inflateException;
            }
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public boolean onMenuItemClick(MenuItem menuItem) {
            try {
                if (this.mMethod.getReturnType() == Boolean.TYPE) {
                    return ((Boolean) this.mMethod.invoke(this.mRealOwner, menuItem)).booleanValue();
                }
                this.mMethod.invoke(this.mRealOwner, menuItem);
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class MenuState {
        private static final int defaultGroupId = 0;
        private static final int defaultItemCategory = 0;
        private static final int defaultItemCheckable = 0;
        private static final boolean defaultItemChecked = false;
        private static final boolean defaultItemEnabled = true;
        private static final int defaultItemId = 0;
        private static final int defaultItemOrder = 0;
        private static final boolean defaultItemVisible = true;
        ActionProvider a;
        private int groupCategory;
        private int groupCheckable;
        private boolean groupEnabled;
        private int groupId;
        private int groupOrder;
        private boolean groupVisible;
        private String itemActionProviderClassName;
        private String itemActionViewClassName;
        private int itemActionViewLayout;
        private boolean itemAdded;
        private int itemAlphabeticModifiers;
        private char itemAlphabeticShortcut;
        private int itemCategoryOrder;
        private int itemCheckable;
        private boolean itemChecked;
        private CharSequence itemContentDescription;
        private boolean itemEnabled;
        private int itemIconResId;
        private ColorStateList itemIconTintList = null;
        private PorterDuff.Mode itemIconTintMode = null;
        private int itemId;
        private String itemListenerMethodName;
        private int itemNumericModifiers;
        private char itemNumericShortcut;
        private int itemShowAsAction;
        private CharSequence itemTitle;
        private CharSequence itemTitleCondensed;
        private CharSequence itemTooltipText;
        private boolean itemVisible;
        private Menu menu;

        public MenuState(Menu menu) {
            this.menu = menu;
            resetGroup();
        }

        private char getShortcut(String str) {
            if (str == null) {
                return (char) 0;
            }
            return str.charAt(0);
        }

        private <T> T newInstance(String str, Class<?>[] clsArr, Object[] objArr) {
            try {
                Constructor<?> constructor = Class.forName(str, false, SupportMenuInflater.this.e.getClassLoader()).getConstructor(clsArr);
                constructor.setAccessible(true);
                return (T) constructor.newInstance(objArr);
            } catch (Exception e) {
                Log.w("SupportMenuInflater", "Cannot instantiate class: " + str, e);
                return null;
            }
        }

        private void setItem(MenuItem menuItem) {
            boolean z = false;
            menuItem.setChecked(this.itemChecked).setVisible(this.itemVisible).setEnabled(this.itemEnabled).setCheckable(this.itemCheckable >= 1).setTitleCondensed(this.itemTitleCondensed).setIcon(this.itemIconResId);
            if (this.itemShowAsAction >= 0) {
                menuItem.setShowAsAction(this.itemShowAsAction);
            }
            if (this.itemListenerMethodName != null) {
                if (SupportMenuInflater.this.e.isRestricted()) {
                    throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                }
                menuItem.setOnMenuItemClickListener(new InflatedOnMenuItemClickListener(SupportMenuInflater.this.a(), this.itemListenerMethodName));
            }
            if (this.itemCheckable >= 2) {
                if (menuItem instanceof MenuItemImpl) {
                    ((MenuItemImpl) menuItem).setExclusiveCheckable(true);
                } else if (menuItem instanceof MenuItemWrapperICS) {
                    ((MenuItemWrapperICS) menuItem).setExclusiveCheckable(true);
                }
            }
            if (this.itemActionViewClassName != null) {
                menuItem.setActionView((View) newInstance(this.itemActionViewClassName, SupportMenuInflater.a, SupportMenuInflater.this.c));
                z = true;
            }
            if (this.itemActionViewLayout > 0) {
                if (z) {
                    Log.w("SupportMenuInflater", "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
                } else {
                    menuItem.setActionView(this.itemActionViewLayout);
                }
            }
            if (this.a != null) {
                MenuItemCompat.setActionProvider(menuItem, this.a);
            }
            MenuItemCompat.setContentDescription(menuItem, this.itemContentDescription);
            MenuItemCompat.setTooltipText(menuItem, this.itemTooltipText);
            MenuItemCompat.setAlphabeticShortcut(menuItem, this.itemAlphabeticShortcut, this.itemAlphabeticModifiers);
            MenuItemCompat.setNumericShortcut(menuItem, this.itemNumericShortcut, this.itemNumericModifiers);
            if (this.itemIconTintMode != null) {
                MenuItemCompat.setIconTintMode(menuItem, this.itemIconTintMode);
            }
            if (this.itemIconTintList != null) {
                MenuItemCompat.setIconTintList(menuItem, this.itemIconTintList);
            }
        }

        public void addItem() {
            this.itemAdded = true;
            setItem(this.menu.add(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle));
        }

        public SubMenu addSubMenuItem() {
            this.itemAdded = true;
            SubMenu subMenuAddSubMenu = this.menu.addSubMenu(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
            setItem(subMenuAddSubMenu.getItem());
            return subMenuAddSubMenu;
        }

        public boolean hasAddedItem() {
            return this.itemAdded;
        }

        public void readGroup(AttributeSet attributeSet) {
            TypedArray typedArrayObtainStyledAttributes = SupportMenuInflater.this.e.obtainStyledAttributes(attributeSet, R.styleable.MenuGroup);
            this.groupId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.MenuGroup_android_id, 0);
            this.groupCategory = typedArrayObtainStyledAttributes.getInt(R.styleable.MenuGroup_android_menuCategory, 0);
            this.groupOrder = typedArrayObtainStyledAttributes.getInt(R.styleable.MenuGroup_android_orderInCategory, 0);
            this.groupCheckable = typedArrayObtainStyledAttributes.getInt(R.styleable.MenuGroup_android_checkableBehavior, 0);
            this.groupVisible = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuGroup_android_visible, true);
            this.groupEnabled = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuGroup_android_enabled, true);
            typedArrayObtainStyledAttributes.recycle();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void readItem(AttributeSet attributeSet) {
            TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(SupportMenuInflater.this.e, attributeSet, R.styleable.MenuItem);
            this.itemId = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.MenuItem_android_id, 0);
            this.itemCategoryOrder = (tintTypedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_android_menuCategory, this.groupCategory) & SupportMenu.CATEGORY_MASK) | (tintTypedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_android_orderInCategory, this.groupOrder) & SupportMenu.USER_MASK);
            this.itemTitle = tintTypedArrayObtainStyledAttributes.getText(R.styleable.MenuItem_android_title);
            this.itemTitleCondensed = tintTypedArrayObtainStyledAttributes.getText(R.styleable.MenuItem_android_titleCondensed);
            this.itemIconResId = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.MenuItem_android_icon, 0);
            this.itemAlphabeticShortcut = getShortcut(tintTypedArrayObtainStyledAttributes.getString(R.styleable.MenuItem_android_alphabeticShortcut));
            this.itemAlphabeticModifiers = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_alphabeticModifiers, 4096);
            this.itemNumericShortcut = getShortcut(tintTypedArrayObtainStyledAttributes.getString(R.styleable.MenuItem_android_numericShortcut));
            this.itemNumericModifiers = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_numericModifiers, 4096);
            this.itemCheckable = tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.MenuItem_android_checkable) ? tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuItem_android_checkable, false) : this.groupCheckable;
            this.itemChecked = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuItem_android_checked, false);
            this.itemVisible = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuItem_android_visible, this.groupVisible);
            this.itemEnabled = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.MenuItem_android_enabled, this.groupEnabled);
            this.itemShowAsAction = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_showAsAction, -1);
            this.itemListenerMethodName = tintTypedArrayObtainStyledAttributes.getString(R.styleable.MenuItem_android_onClick);
            this.itemActionViewLayout = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.MenuItem_actionLayout, 0);
            this.itemActionViewClassName = tintTypedArrayObtainStyledAttributes.getString(R.styleable.MenuItem_actionViewClass);
            this.itemActionProviderClassName = tintTypedArrayObtainStyledAttributes.getString(R.styleable.MenuItem_actionProviderClass);
            boolean z = this.itemActionProviderClassName != null;
            if (z && this.itemActionViewLayout == 0 && this.itemActionViewClassName == null) {
                this.a = (ActionProvider) newInstance(this.itemActionProviderClassName, SupportMenuInflater.b, SupportMenuInflater.this.d);
            } else {
                if (z) {
                    Log.w("SupportMenuInflater", "Ignoring attribute 'actionProviderClass'. Action view already specified.");
                }
                this.a = null;
            }
            this.itemContentDescription = tintTypedArrayObtainStyledAttributes.getText(R.styleable.MenuItem_contentDescription);
            this.itemTooltipText = tintTypedArrayObtainStyledAttributes.getText(R.styleable.MenuItem_tooltipText);
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.MenuItem_iconTintMode)) {
                this.itemIconTintMode = DrawableUtils.parseTintMode(tintTypedArrayObtainStyledAttributes.getInt(R.styleable.MenuItem_iconTintMode, -1), this.itemIconTintMode);
            } else {
                this.itemIconTintMode = null;
            }
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.MenuItem_iconTint)) {
                this.itemIconTintList = tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.MenuItem_iconTint);
            } else {
                this.itemIconTintList = null;
            }
            tintTypedArrayObtainStyledAttributes.recycle();
            this.itemAdded = false;
        }

        public void resetGroup() {
            this.groupId = 0;
            this.groupCategory = 0;
            this.groupOrder = 0;
            this.groupCheckable = 0;
            this.groupVisible = true;
            this.groupEnabled = true;
        }
    }

    public SupportMenuInflater(Context context) {
        super(context);
        this.e = context;
        this.c = new Object[]{context};
        this.d = this.c;
    }

    private Object findRealOwner(Object obj) {
        return (!(obj instanceof Activity) && (obj instanceof ContextWrapper)) ? findRealOwner(((ContextWrapper) obj).getBaseContext()) : obj;
    }

    private void parseMenu(XmlPullParser xmlPullParser, AttributeSet attributeSet, Menu menu) throws XmlPullParserException, IOException {
        MenuState menuState = new MenuState(menu);
        int eventType = xmlPullParser.getEventType();
        while (true) {
            if (eventType != 2) {
                eventType = xmlPullParser.next();
                if (eventType == 1) {
                    break;
                }
            } else {
                String name = xmlPullParser.getName();
                if (!name.equals(XML_MENU)) {
                    throw new RuntimeException("Expecting menu, got " + name);
                }
                eventType = xmlPullParser.next();
            }
        }
        int next = eventType;
        String str = null;
        boolean z = false;
        boolean z2 = false;
        while (!z) {
            switch (next) {
                case 1:
                    throw new RuntimeException("Unexpected end of document");
                case 2:
                    if (!z2) {
                        String name2 = xmlPullParser.getName();
                        if (name2.equals(XML_GROUP)) {
                            menuState.readGroup(attributeSet);
                        } else if (name2.equals(XML_ITEM)) {
                            menuState.readItem(attributeSet);
                        } else if (!name2.equals(XML_MENU)) {
                            z2 = true;
                            str = name2;
                        } else {
                            parseMenu(xmlPullParser, attributeSet, menuState.addSubMenuItem());
                        }
                    }
                    break;
                case 3:
                    String name3 = xmlPullParser.getName();
                    if (z2 && name3.equals(str)) {
                        str = null;
                        z2 = false;
                    } else if (name3.equals(XML_GROUP)) {
                        menuState.resetGroup();
                    } else if (!name3.equals(XML_ITEM)) {
                        if (name3.equals(XML_MENU)) {
                            z = true;
                        }
                    } else if (!menuState.hasAddedItem()) {
                        if (menuState.a != null && menuState.a.hasSubMenu()) {
                            menuState.addSubMenuItem();
                        } else {
                            menuState.addItem();
                        }
                    }
                    break;
            }
            next = xmlPullParser.next();
        }
    }

    Object a() {
        if (this.mRealOwner == null) {
            this.mRealOwner = findRealOwner(this.e);
        }
        return this.mRealOwner;
    }

    @Override // android.view.MenuInflater
    public void inflate(@LayoutRes int i, Menu menu) throws Throwable {
        XmlResourceParser layout;
        if (!(menu instanceof SupportMenu)) {
            super.inflate(i, menu);
            return;
        }
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                layout = this.e.getResources().getLayout(i);
            } catch (Throwable th) {
                th = th;
            }
        } catch (IOException e) {
            e = e;
        } catch (XmlPullParserException e2) {
            e = e2;
        }
        try {
            parseMenu(layout, Xml.asAttributeSet(layout), menu);
            if (layout != null) {
                layout.close();
            }
        } catch (IOException e3) {
            e = e3;
            throw new InflateException("Error inflating menu XML", e);
        } catch (XmlPullParserException e4) {
            e = e4;
            throw new InflateException("Error inflating menu XML", e);
        } catch (Throwable th2) {
            th = th2;
            xmlResourceParser = layout;
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
            throw th;
        }
    }
}
