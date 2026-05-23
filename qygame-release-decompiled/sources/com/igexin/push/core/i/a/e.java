package com.igexin.push.core.i.a;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import com.igexin.push.core.i.a.h;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class e extends Drawable implements Animatable, h.b {
    public static final int a = -1;
    public static final int b = 0;
    private static final int f = 119;
    public final a c;
    public boolean d;
    boolean e;
    private boolean g;
    private boolean h;
    private int i;
    private int j;
    private boolean k;
    private Paint l;
    private Rect m;

    public static final class a extends Drawable.ConstantState {
        public final h a;

        a(h hVar) {
            this.a = hVar;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final int getChangingConfigurations() {
            return 0;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final Drawable newDrawable() {
            return new e(this);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final Drawable newDrawable(Resources resources) {
            return newDrawable();
        }
    }

    public e(d dVar, Bitmap bitmap) {
        this(new a(new h(dVar, bitmap)));
    }

    e(a aVar) {
        this.h = true;
        this.j = -1;
        this.c = (a) k.a(aVar);
    }

    private void a(int i) {
        if (i <= 0 && i != -1 && i != 0) {
            throw new IllegalArgumentException("Loop count must be greater than 0, or equal to GlideDrawable.LOOP_FOREVER, or equal to GlideDrawable.LOOP_INTRINSIC");
        }
        if (i != 0) {
            this.j = i;
            return;
        }
        int iL = this.c.a.a.l();
        if (iL == 0) {
            iL = -1;
        }
        this.j = iL;
    }

    private void a(boolean z) {
        this.d = z;
    }

    private int c() {
        h hVar = this.c.a;
        return hVar.a.m() + hVar.j;
    }

    private ByteBuffer d() {
        return this.c.a.a.c().asReadOnlyBuffer();
    }

    private int e() {
        return this.c.a.a();
    }

    private int f() {
        h hVar = this.c.a;
        if (hVar.e != null) {
            return hVar.e.a;
        }
        return -1;
    }

    private void g() {
        this.i = 0;
    }

    private void h() {
        k.a(!this.d, "You cannot restart a currently running animation.");
        h hVar = this.c.a;
        k.a(!hVar.c, "Can't restart a running animation");
        hVar.d = true;
        if (hVar.i != null) {
            hVar.i = null;
        }
        start();
    }

    private void i() {
        k.a(!this.e, "You cannot start a recycled Drawable. Ensure thatyou clear any references to the Drawable when clearing the corresponding request.");
        if (this.c.a.a() == 1) {
            invalidateSelf();
        } else {
            if (this.d) {
                return;
            }
            this.d = true;
            this.c.a.a(this);
            invalidateSelf();
        }
    }

    private void j() {
        this.d = false;
        this.c.a.b(this);
    }

    private Rect k() {
        if (this.m == null) {
            this.m = new Rect();
        }
        return this.m;
    }

    private Paint l() {
        if (this.l == null) {
            this.l = new Paint(2);
        }
        return this.l;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Drawable.Callback m() {
        Drawable.Callback callback = getCallback();
        while (callback instanceof Drawable) {
            callback = ((Drawable) callback).getCallback();
        }
        return callback;
    }

    private void n() {
        this.e = true;
        h hVar = this.c.a;
        hVar.b.clear();
        hVar.b();
        hVar.c = false;
        if (hVar.e != null) {
            hVar.e = null;
        }
        if (hVar.g != null) {
            hVar.g = null;
        }
        if (hVar.i != null) {
            hVar.i = null;
        }
        hVar.a.o();
        hVar.f = true;
    }

    private boolean o() {
        return this.e;
    }

    public final Bitmap a() {
        return this.c.a.h;
    }

    @Override // com.igexin.push.core.i.a.h.b
    public final void b() {
        Object callback = getCallback();
        while (callback instanceof Drawable) {
            callback = ((Drawable) callback).getCallback();
        }
        if (callback == null) {
            stop();
            invalidateSelf();
            return;
        }
        invalidateSelf();
        h hVar = this.c.a;
        if ((hVar.e != null ? hVar.e.a : -1) == this.c.a.a() - 1) {
            this.i++;
        }
        if (this.j == -1 || this.i < this.j) {
            return;
        }
        stop();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.e) {
            return;
        }
        if (this.k) {
            Gravity.apply(119, getIntrinsicWidth(), getIntrinsicHeight(), getBounds(), k());
            this.k = false;
        }
        h hVar = this.c.a;
        canvas.drawBitmap(hVar.e != null ? hVar.e.b : hVar.h, (Rect) null, k(), l());
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return this.c;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.c.a.l;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.c.a.k;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.d;
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.k = true;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        l().setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        l().setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        if (this.e) {
            com.igexin.c.a.c.a.b("GifBitmapProvider", "Cannot change the visibility of a recycled resource. Ensure that you unset the Drawable from your View before changing the View's visibility.");
            return super.setVisible(z, z2);
        }
        this.h = z;
        if (!z) {
            com.igexin.c.a.c.a.b("GifBitmapProvider", "invisible  stopRunning");
            j();
        } else if (this.g) {
            i();
        }
        return super.setVisible(z, z2);
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        this.g = true;
        this.i = 0;
        if (this.h) {
            i();
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        this.g = false;
        j();
    }
}
