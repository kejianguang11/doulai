package com.gme.liteav.sdkcommon;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.internal.view.SupportMenu;
import com.alibaba.fastjson.asm.Opcodes;
import com.gme.liteav.base.ContextUtils;
import com.gme.liteav.base.Log;
import com.gme.liteav.base.annotations.JNINamespace;
import com.gme.liteav.base.system.LiteavSystemInfo;
import com.gme.liteav.base.util.LiteavLog;
import com.gme.liteav.sdkcommon.g;
import com.gme.trtc.hardwareearmonitor.honor.HonorResultCode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@JNINamespace("liteav::dashboard")
public class DashboardManager {
    private static final int LOG_MAX_SIZE = 15000;
    private static final String TAG = "DashboardManager";
    private final Context mAppContext;
    private final g mDashboardManagerView;
    private boolean mIsInit;
    private String mSelectedDashboardId;
    private final Handler mUIHandler;
    private final ArrayList<String> mDashboards = new ArrayList<>();
    private final Map<String, String> mDashboardStatus = new HashMap();
    private final Map<String, StringBuilder> mDashboardLogs = new HashMap();
    private final g.a mSelectedDashboardChangeListener = new g.a() { // from class: com.gme.liteav.sdkcommon.DashboardManager.1
        @Override // com.gme.liteav.sdkcommon.g.a
        public final void a(int i) {
            g gVar;
            String string;
            if (DashboardManager.this.mDashboards.size() <= i) {
                return;
            }
            DashboardManager.this.mSelectedDashboardId = (String) DashboardManager.this.mDashboards.get(i);
            if (DashboardManager.this.mDashboards.contains(DashboardManager.this.mSelectedDashboardId)) {
                DashboardManager.this.mDashboardManagerView.b((String) DashboardManager.this.mDashboardStatus.get(DashboardManager.this.mSelectedDashboardId));
                StringBuilder sb = (StringBuilder) DashboardManager.this.mDashboardLogs.get(DashboardManager.this.mSelectedDashboardId);
                if (sb != null) {
                    gVar = DashboardManager.this.mDashboardManagerView;
                    string = sb.toString();
                } else {
                    gVar = DashboardManager.this.mDashboardManagerView;
                    string = "";
                }
                gVar.a(string);
            }
        }
    };

    public DashboardManager() {
        LiteavLog.i(TAG, "java DashBoardManager Construct");
        this.mIsInit = false;
        this.mAppContext = ContextUtils.getApplicationContext();
        this.mDashboardManagerView = new g(this.mAppContext, this.mSelectedDashboardChangeListener);
        this.mUIHandler = new Handler(Looper.getMainLooper());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addDashboardInternal(String str) {
        if (this.mDashboards.contains(str)) {
            return;
        }
        this.mDashboards.add(str);
        g gVar = this.mDashboardManagerView;
        gVar.e.add(str);
        if (gVar.l == null) {
            gVar.l = gVar.e.getItem(0);
            gVar.o.a(0);
        }
        gVar.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void appendLogInternal(String str, String str2) {
        if (this.mDashboards.contains(str)) {
            StringBuilder sb = this.mDashboardLogs.get(str);
            if (sb == null) {
                sb = new StringBuilder();
                this.mDashboardLogs.put(str, sb);
            }
            sb.append(str2);
            sb.append("\n");
            if (sb.length() > LOG_MAX_SIZE) {
                sb.delete(0, sb.length() / 2);
            }
            if (str.equals(this.mSelectedDashboardId)) {
                g gVar = this.mDashboardManagerView;
                if (gVar.i != null) {
                    gVar.i.append(str2 + "\n");
                    if (gVar.k != null) {
                        if ((gVar.k.getScrollY() + gVar.k.getHeight()) + gVar.a(100) >= gVar.i.getMeasuredHeight()) {
                            gVar.d.post(i.a(gVar));
                        }
                    }
                }
            }
        }
    }

    private boolean checkPermission() {
        if (LiteavSystemInfo.getSystemOSVersionInt() <= 23 || Settings.canDrawOverlays(this.mAppContext)) {
            return true;
        }
        Toast.makeText(this.mAppContext, "no system alert window permission, please authorize", 0).show();
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0048 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0049  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean init() {
        boolean z;
        String str;
        String str2;
        WindowManager.LayoutParams layoutParams;
        int i;
        if (this.mIsInit) {
            return true;
        }
        g gVar = this.mDashboardManagerView;
        if (gVar.c == null) {
            str = "DashboardManagerView";
            str2 = "dashBoardManagerView context is null";
        } else {
            gVar.f = (WindowManager) gVar.c.getSystemService("window");
            if (gVar.f != null) {
                gVar.f.getDefaultDisplay().getMetrics(gVar.a);
                gVar.n = gVar.a.heightPixels - gVar.a(50);
                z = true;
                if (z) {
                    return false;
                }
                g gVar2 = this.mDashboardManagerView;
                if (Build.VERSION.SDK_INT >= 26) {
                    layoutParams = gVar2.b;
                    i = 2038;
                } else {
                    layoutParams = gVar2.b;
                    i = HonorResultCode.ADVANCED_RECORD_SERVICE_LINKFAIL;
                }
                layoutParams.type = i;
                gVar2.b.format = 1;
                gVar2.b.gravity = 8388659;
                gVar2.b.width = gVar2.a.widthPixels;
                gVar2.b.height = gVar2.n;
                gVar2.b.x = 0;
                gVar2.b.y = 0;
                gVar2.b.flags = 32;
                LinearLayout linearLayout = new LinearLayout(gVar2.c);
                linearLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                linearLayout.setOrientation(1);
                linearLayout.setOnTouchListener(new g.b(gVar2, (byte) 0));
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(gVar2.a(70), gVar2.a(40));
                Button button = new Button(gVar2.c);
                button.setText("Resize");
                button.setLayoutParams(layoutParams2);
                button.setOnClickListener(j.a(gVar2, button));
                Button button2 = new Button(gVar2.c);
                button2.setText("close");
                layoutParams2.leftMargin = gVar2.a(10);
                button2.setLayoutParams(layoutParams2);
                button2.setOnClickListener(k.a(gVar2));
                LinearLayout linearLayout2 = new LinearLayout(gVar2.c);
                linearLayout2.addView(button);
                linearLayout2.addView(button2);
                linearLayout2.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
                linearLayout2.setOrientation(0);
                linearLayout2.setBackgroundColor(-7829368);
                linearLayout2.setAlpha(0.5f);
                linearLayout.addView(linearLayout2);
                gVar2.j = new Spinner(gVar2.c);
                gVar2.j.setAdapter((SpinnerAdapter) gVar2.e);
                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, gVar2.a(30));
                layoutParams3.topMargin = gVar2.a(2);
                gVar2.j.setLayoutParams(layoutParams3);
                gVar2.j.setOnItemSelectedListener(new g.c(gVar2, (byte) 0));
                gVar2.j.setBackgroundColor(-7829368);
                gVar2.j.setAlpha(0.5f);
                linearLayout.addView(gVar2.j);
                gVar2.h = new TextView(gVar2.c);
                LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-1, gVar2.a(Opcodes.IF_ICMPNE));
                layoutParams4.topMargin = gVar2.a(10);
                layoutParams4.leftMargin = gVar2.a(10);
                layoutParams4.rightMargin = gVar2.a(3);
                gVar2.h.setLayoutParams(layoutParams4);
                gVar2.h.setTextColor(SupportMenu.CATEGORY_MASK);
                linearLayout.addView(gVar2.h);
                gVar2.k = new ScrollView(gVar2.c);
                LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(-1, gVar2.b());
                layoutParams5.leftMargin = gVar2.a(10);
                layoutParams5.rightMargin = gVar2.a(3);
                gVar2.k.setLayoutParams(layoutParams5);
                gVar2.k.setVerticalScrollBarEnabled(true);
                gVar2.i = new TextView(gVar2.c);
                gVar2.i.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
                gVar2.i.setTextColor(SupportMenu.CATEGORY_MASK);
                gVar2.k.addView(gVar2.i);
                gVar2.k.fullScroll(130);
                linearLayout.addView(gVar2.k);
                gVar2.g = linearLayout;
                gVar2.o.a(0);
                this.mIsInit = true;
                return true;
            }
            str = "DashboardManagerView";
            str2 = "get windowManager is fail";
        }
        Log.e(str, str2, new Object[0]);
        z = false;
        if (z) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeAllDashboardInternal() {
        this.mDashboards.clear();
        this.mDashboardStatus.clear();
        this.mDashboardLogs.clear();
        g gVar = this.mDashboardManagerView;
        gVar.e.clear();
        gVar.l = null;
        if (gVar.h != null) {
            gVar.h.setText("");
        }
        if (gVar.i != null) {
            gVar.i.setText("");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:10:0x004a A[PHI: r0
      0x004a: PHI (r0v11 int) = (r0v10 int), (r0v12 int) binds: [B:13:0x0065, B:9:0x0048] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void removeDashboardInternal(String str) {
        int i;
        if (this.mDashboards.contains(str)) {
            this.mDashboards.remove(str);
            this.mDashboardStatus.remove(str);
            this.mDashboardLogs.remove(str);
            g gVar = this.mDashboardManagerView;
            if (str.equals(gVar.l)) {
                int position = gVar.e.getPosition(gVar.l);
                if (position != gVar.e.getCount() - 1) {
                    i = position + 1;
                    gVar.l = gVar.e.getItem(i);
                    gVar.o.a(i - 1);
                    if (gVar.j != null) {
                        gVar.j.setSelection(i);
                    }
                } else if (position > 0) {
                    i = position - 1;
                    gVar.l = gVar.e.getItem(i);
                    gVar.o.a(i);
                    if (gVar.j != null) {
                    }
                }
            }
            gVar.e.remove(str);
            if (gVar.e.getCount() == 0) {
                gVar.l = null;
            }
            gVar.a();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setStatusInternal(String str, String str2) {
        if (this.mDashboards.contains(str)) {
            this.mDashboardStatus.put(str, str2);
            if (str.equals(this.mSelectedDashboardId)) {
                this.mDashboardManagerView.b(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDashboardInternal(boolean z) {
        if (!z || (checkPermission() && init())) {
            this.mDashboardManagerView.a(z);
        } else {
            LiteavLog.i(TAG, "init or check permission is fail");
        }
    }

    public int addDashboard(String str) {
        LiteavLog.i(TAG, "addDashboard dashboardId = ".concat(String.valueOf(str)));
        this.mUIHandler.post(b.a(this, str));
        return 0;
    }

    public int appendLog(String str, String str2) {
        this.mUIHandler.post(f.a(this, str, str2));
        return 0;
    }

    public int removeAllDashboard() {
        LiteavLog.i(TAG, "removeAllDashboard ");
        this.mUIHandler.post(d.a(this));
        return 0;
    }

    public int removeDashboard(String str) {
        LiteavLog.i(TAG, "removeDashboard dashboardId = ".concat(String.valueOf(str)));
        this.mUIHandler.post(c.a(this, str));
        return 0;
    }

    public int setStatus(String str, String str2) {
        this.mUIHandler.post(e.a(this, str, str2));
        return 0;
    }

    public int showDashboard(boolean z) {
        LiteavLog.i(TAG, "showDashBoard isShow = ".concat(String.valueOf(z)));
        this.mUIHandler.post(a.a(this, z));
        return 0;
    }
}
