package com.gme.liteav.sdkcommon;

import android.R;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;

/* JADX INFO: loaded from: classes.dex */
final class g {
    final Context c;
    final ArrayAdapter<String> e;
    WindowManager f;
    View g;
    TextView h;
    TextView i;
    Spinner j;
    ScrollView k;
    String l;
    final a o;
    final DisplayMetrics a = new DisplayMetrics();
    final WindowManager.LayoutParams b = new WindowManager.LayoutParams();
    private final int p = SupportMenu.CATEGORY_MASK;
    private boolean q = false;
    boolean m = false;
    int n = 1920;
    final Handler d = new Handler(Looper.getMainLooper());

    public interface a {
        void a(int i);
    }

    class b implements View.OnTouchListener {
        private int b;
        private int c;

        private b() {
        }

        /* synthetic */ b(g gVar, byte b) {
            this();
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            WindowManager.LayoutParams layoutParams;
            int i;
            int action = motionEvent.getAction();
            if (action == 0) {
                this.b = (int) motionEvent.getRawX();
                this.c = (int) motionEvent.getRawY();
            } else if (action == 2) {
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();
                int i2 = rawX - this.b;
                int i3 = rawY - this.c;
                g.this.b.x += i2;
                g.this.b.y += i3;
                this.b = rawX;
                this.c = rawY;
                g.this.b.x = Math.max(g.this.b.x, 0);
                g.this.b.y = Math.max(g.this.b.y, 0);
                if (g.this.b.x + g.this.a.widthPixels > g.this.a.widthPixels) {
                    layoutParams = g.this.b;
                    i = g.this.a.widthPixels - g.this.b.x;
                } else {
                    layoutParams = g.this.b;
                    i = g.this.a.widthPixels;
                }
                layoutParams.width = i;
                g.this.b.height = g.this.n;
                if (g.this.m) {
                    g.this.b.height = g.this.n / 2;
                }
                if (g.this.b.y + g.this.b.height > g.this.a.heightPixels) {
                    g.this.b.height = g.this.a.heightPixels - g.this.b.y;
                }
                ViewGroup.LayoutParams layoutParams2 = g.this.k.getLayoutParams();
                layoutParams2.height = g.this.b();
                g.this.k.setLayoutParams(layoutParams2);
                g.this.f.updateViewLayout(view, g.this.b);
            }
            view.performClick();
            return false;
        }
    }

    class c implements AdapterView.OnItemSelectedListener {
        private c() {
        }

        /* synthetic */ c(g gVar, byte b) {
            this();
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public final void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
            if (view == null) {
                return;
            }
            ((TextView) view).setTextColor(SupportMenu.CATEGORY_MASK);
            g.this.l = g.this.e.getItem(i);
            g.this.o.a(i);
        }

        @Override // android.widget.AdapterView.OnItemSelectedListener
        public final void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public g(Context context, a aVar) {
        this.c = context;
        this.o = aVar;
        this.e = new ArrayAdapter<>(this.c, R.layout.simple_spinner_item);
    }

    final int a(int i) {
        return (int) ((i * this.c.getResources().getDisplayMetrics().density) + 0.5f);
    }

    final void a() {
        TextView textView;
        if (this.j == null || (textView = (TextView) this.j.getChildAt(this.j.getSelectedItemPosition())) == null) {
            return;
        }
        textView.setTextColor(SupportMenu.CATEGORY_MASK);
    }

    public final void a(String str) {
        if (this.i != null) {
            this.i.setText(str);
        }
        this.d.post(h.a(this));
    }

    public final void a(boolean z) {
        if (z == this.q) {
            return;
        }
        if (z) {
            this.f.addView(this.g, this.b);
        } else {
            this.f.removeView(this.g);
        }
        this.q = z;
    }

    final int b() {
        return Math.max((this.b.height - a(230)) - a(20), 0);
    }

    public final void b(String str) {
        if (this.h != null) {
            this.h.setText(str);
        }
    }
}
