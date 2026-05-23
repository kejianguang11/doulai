package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.collection.SimpleArrayMap;
import androidx.core.internal.view.SupportMenuItem;
import androidx.core.internal.view.SupportSubMenu;

/* JADX INFO: loaded from: classes.dex */
abstract class BaseMenuWrapper {
    final Context a;
    private SimpleArrayMap<SupportMenuItem, MenuItem> mMenuItems;
    private SimpleArrayMap<SupportSubMenu, SubMenu> mSubMenus;

    BaseMenuWrapper(Context context) {
        this.a = context;
    }

    final MenuItem a(MenuItem menuItem) {
        if (!(menuItem instanceof SupportMenuItem)) {
            return menuItem;
        }
        SupportMenuItem supportMenuItem = (SupportMenuItem) menuItem;
        if (this.mMenuItems == null) {
            this.mMenuItems = new SimpleArrayMap<>();
        }
        MenuItem menuItem2 = this.mMenuItems.get(menuItem);
        if (menuItem2 != null) {
            return menuItem2;
        }
        MenuItemWrapperICS menuItemWrapperICS = new MenuItemWrapperICS(this.a, supportMenuItem);
        this.mMenuItems.put(supportMenuItem, menuItemWrapperICS);
        return menuItemWrapperICS;
    }

    final SubMenu a(SubMenu subMenu) {
        if (!(subMenu instanceof SupportSubMenu)) {
            return subMenu;
        }
        SupportSubMenu supportSubMenu = (SupportSubMenu) subMenu;
        if (this.mSubMenus == null) {
            this.mSubMenus = new SimpleArrayMap<>();
        }
        SubMenu subMenu2 = this.mSubMenus.get(supportSubMenu);
        if (subMenu2 != null) {
            return subMenu2;
        }
        SubMenuWrapperICS subMenuWrapperICS = new SubMenuWrapperICS(this.a, supportSubMenu);
        this.mSubMenus.put(supportSubMenu, subMenuWrapperICS);
        return subMenuWrapperICS;
    }

    final void a() {
        if (this.mMenuItems != null) {
            this.mMenuItems.clear();
        }
        if (this.mSubMenus != null) {
            this.mSubMenus.clear();
        }
    }

    final void a(int i) {
        if (this.mMenuItems == null) {
            return;
        }
        int i2 = 0;
        while (i2 < this.mMenuItems.size()) {
            if (this.mMenuItems.keyAt(i2).getGroupId() == i) {
                this.mMenuItems.removeAt(i2);
                i2--;
            }
            i2++;
        }
    }

    final void b(int i) {
        if (this.mMenuItems == null) {
            return;
        }
        for (int i2 = 0; i2 < this.mMenuItems.size(); i2++) {
            if (this.mMenuItems.keyAt(i2).getItemId() == i) {
                this.mMenuItems.removeAt(i2);
                return;
            }
        }
    }
}
