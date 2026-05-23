package com.igexin.push.core.i.a;

import android.graphics.Bitmap;
import com.igexin.push.core.i.a.d;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class c implements d.a {
    private ArrayList<Bitmap> a = null;
    private final String b = "GifBitmapProvider";

    @Override // com.igexin.push.core.i.a.d.a
    public final Bitmap a(int i, int i2, Bitmap.Config config, int i3) {
        if (this.a == null) {
            this.a = new ArrayList<>(2);
            this.a.add(0, Bitmap.createBitmap(i, i2, config));
            this.a.add(1, Bitmap.createBitmap(i, i2, config));
        }
        return this.a.get(i3 % 2);
    }

    @Override // com.igexin.push.core.i.a.d.a
    public final void a() {
        if (this.a != null) {
            for (Bitmap bitmap : this.a) {
                if (bitmap != null) {
                    bitmap.recycle();
                }
            }
            this.a = null;
        }
    }

    @Override // com.igexin.push.core.i.a.d.a
    public final void a(Bitmap bitmap) {
        com.igexin.c.a.c.a.b("GifBitmapProvider", "release bitmap  ");
        bitmap.recycle();
    }

    @Override // com.igexin.push.core.i.a.d.a
    public final byte[] a(int i) {
        return new byte[i];
    }

    @Override // com.igexin.push.core.i.a.d.a
    public final void b() {
    }

    @Override // com.igexin.push.core.i.a.d.a
    public final int[] b(int i) {
        return new int[i];
    }

    @Override // com.igexin.push.core.i.a.d.a
    public final void c() {
    }
}
