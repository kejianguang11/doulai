package com.zx.a.I8b7;

import android.media.MediaDrm;
import android.os.Build;
import android.util.Base64;
import com.zx.module.base.Callback;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class x0 implements Runnable {
    public final /* synthetic */ Callback a;

    public x0(Callback callback) {
        this.a = callback;
    }

    @Override // java.lang.Runnable
    public void run() {
        MediaDrm mediaDrm;
        MediaDrm mediaDrm2 = null;
        try {
            try {
                mediaDrm = new MediaDrm(new UUID(-1301668207276963122L, -6645017420763422227L));
            } catch (Throwable th) {
                th = th;
            }
            try {
                String str = new String(Base64.encode(mediaDrm.getPropertyByteArray("deviceUniqueId"), 2), StandardCharsets.UTF_8);
                if (Build.VERSION.SDK_INT >= 28) {
                    mediaDrm.close();
                } else {
                    mediaDrm.release();
                }
                this.a.callback(str);
            } catch (Throwable th2) {
                th = th2;
                mediaDrm2 = mediaDrm;
                try {
                    v2.a(th);
                    if (Build.VERSION.SDK_INT >= 28) {
                        if (mediaDrm2 != null) {
                            mediaDrm2.close();
                        }
                    } else if (mediaDrm2 != null) {
                        mediaDrm2.release();
                    }
                    this.a.callback("");
                } catch (Throwable th3) {
                    try {
                        if (Build.VERSION.SDK_INT >= 28) {
                            if (mediaDrm2 != null) {
                                mediaDrm2.close();
                            }
                        } else if (mediaDrm2 != null) {
                            mediaDrm2.release();
                        }
                        this.a.callback("");
                    } catch (Throwable unused) {
                    }
                    throw th3;
                }
            }
        } catch (Throwable unused2) {
        }
    }
}
