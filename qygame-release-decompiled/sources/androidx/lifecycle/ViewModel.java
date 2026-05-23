package androidx.lifecycle;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class ViewModel {

    @Nullable
    private final Map<String, Object> mBagOfTags = new HashMap();
    private volatile boolean mCleared = false;

    private static void closeWithRuntimeException(Object obj) {
        if (obj instanceof Closeable) {
            try {
                ((Closeable) obj).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    <T> T a(String str, T t) {
        Object obj;
        synchronized (this.mBagOfTags) {
            obj = this.mBagOfTags.get(str);
            if (obj == null) {
                this.mBagOfTags.put(str, t);
            }
        }
        if (obj != null) {
            t = (T) obj;
        }
        if (this.mCleared) {
            closeWithRuntimeException(t);
        }
        return t;
    }

    protected void a() {
    }

    <T> T b(String str) {
        T t;
        if (this.mBagOfTags == null) {
            return null;
        }
        synchronized (this.mBagOfTags) {
            t = (T) this.mBagOfTags.get(str);
        }
        return t;
    }

    @MainThread
    final void e() {
        this.mCleared = true;
        if (this.mBagOfTags != null) {
            synchronized (this.mBagOfTags) {
                Iterator<Object> it = this.mBagOfTags.values().iterator();
                while (it.hasNext()) {
                    closeWithRuntimeException(it.next());
                }
            }
        }
        a();
    }
}
