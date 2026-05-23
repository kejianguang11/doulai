package com.igexin.push.a;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/* JADX INFO: loaded from: classes.dex */
public final class d extends ImageView {
    private static final int e = 1000;
    Movie a;
    long b;
    int c;
    volatile boolean d;
    private float f;
    private float g;
    private float h;

    public d(Context context) {
        super(context, null);
        this.d = true;
    }

    private d(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.d = true;
    }

    private void a(Canvas canvas) {
        this.a.setTime(this.c);
        canvas.save();
        canvas.scale(this.h, this.h);
        this.a.draw(canvas, this.f / this.h, this.g / this.h);
        canvas.restore();
    }

    private boolean a() {
        return !this.d;
    }

    private void b() {
        if (this.d) {
            this.d = false;
            if (this.a != null) {
                this.b = SystemClock.uptimeMillis() - ((long) this.c);
                invalidate();
            }
        }
    }

    private void c() {
        if (this.d) {
            return;
        }
        this.d = true;
        if (this.a != null) {
            invalidate();
        }
    }

    private void d() {
        if (getVisibility() == 0) {
            if (Build.VERSION.SDK_INT >= 16) {
                postInvalidateOnAnimation();
            } else {
                invalidate();
            }
        }
    }

    private void e() {
        long jUptimeMillis = SystemClock.uptimeMillis();
        if (this.b == 0) {
            this.b = jUptimeMillis;
        }
        int iDuration = this.a.duration();
        if (iDuration == 0) {
            iDuration = 1000;
        }
        this.c = (int) ((jUptimeMillis - this.b) % ((long) iDuration));
    }

    private void setGifMovie$304a7d5c(Movie movie) {
        this.a = movie;
        this.b = 0L;
        this.c = 0;
        setLayerType(1, null);
        setImageDrawable(null);
        requestLayout();
        invalidate();
    }

    @Override // android.widget.ImageView, android.view.View
    protected final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.a != null) {
            if (this.d) {
                a(canvas);
                return;
            }
            long jUptimeMillis = SystemClock.uptimeMillis();
            if (this.b == 0) {
                this.b = jUptimeMillis;
            }
            int iDuration = this.a.duration();
            if (iDuration == 0) {
                iDuration = 1000;
            }
            this.c = (int) ((jUptimeMillis - this.b) % ((long) iDuration));
            a(canvas);
            d();
        }
    }

    @Override // android.view.View
    protected final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.a != null) {
            int width = getWidth();
            int height = getHeight();
            int iWidth = this.a.width();
            int iHeight = this.a.height();
            this.h = 1.0f / Math.max(width != 0 ? iWidth / width : 1.0f, height != 0 ? iHeight / height : 1.0f);
            int i5 = (int) (iWidth * this.h);
            int i6 = (int) (iHeight * this.h);
            this.f = (width - i5) / 2.0f;
            this.g = (height - i6) / 2.0f;
        }
    }

    @Override // android.view.View
    public final void onScreenStateChanged(int i) {
        super.onScreenStateChanged(i);
        d();
    }

    @Override // android.view.View
    protected final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        d();
    }

    @Override // android.view.View
    protected final void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        d();
    }
}
