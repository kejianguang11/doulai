package androidx.loader.content;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.DebugUtils;
import com.alipay.sdk.util.h;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class Loader<D> {
    int n;
    OnLoadCompleteListener<D> o;
    OnLoadCanceledListener<D> p;
    Context q;
    boolean r = false;
    boolean s = false;
    boolean t = true;
    boolean u = false;
    boolean v = false;

    public final class ForceLoadContentObserver extends ContentObserver {
        public ForceLoadContentObserver() {
            super(new Handler());
        }

        @Override // android.database.ContentObserver
        public boolean deliverSelfNotifications() {
            return true;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            Loader.this.onContentChanged();
        }
    }

    public interface OnLoadCanceledListener<D> {
        void onLoadCanceled(@NonNull Loader<D> loader);
    }

    public interface OnLoadCompleteListener<D> {
        void onLoadComplete(@NonNull Loader<D> loader, @Nullable D d);
    }

    public Loader(@NonNull Context context) {
        this.q = context.getApplicationContext();
    }

    @MainThread
    protected void a() {
    }

    @MainThread
    public void abandon() {
        this.s = true;
        h();
    }

    @MainThread
    protected boolean b() {
        return false;
    }

    @MainThread
    public boolean cancelLoad() {
        return b();
    }

    public void commitContentChanged() {
        this.v = false;
    }

    @NonNull
    public String dataToString(@Nullable D d) {
        StringBuilder sb = new StringBuilder(64);
        DebugUtils.buildShortClassTag(d, sb);
        sb.append(h.d);
        return sb.toString();
    }

    @MainThread
    public void deliverCancellation() {
        if (this.p != null) {
            this.p.onLoadCanceled(this);
        }
    }

    @MainThread
    public void deliverResult(@Nullable D d) {
        if (this.o != null) {
            this.o.onLoadComplete(this, d);
        }
    }

    @Deprecated
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print(str);
        printWriter.print("mId=");
        printWriter.print(this.n);
        printWriter.print(" mListener=");
        printWriter.println(this.o);
        if (this.r || this.u || this.v) {
            printWriter.print(str);
            printWriter.print("mStarted=");
            printWriter.print(this.r);
            printWriter.print(" mContentChanged=");
            printWriter.print(this.u);
            printWriter.print(" mProcessingChange=");
            printWriter.println(this.v);
        }
        if (this.s || this.t) {
            printWriter.print(str);
            printWriter.print("mAbandoned=");
            printWriter.print(this.s);
            printWriter.print(" mReset=");
            printWriter.println(this.t);
        }
    }

    @MainThread
    protected void e() {
    }

    @MainThread
    protected void f() {
    }

    @MainThread
    public void forceLoad() {
        a();
    }

    @MainThread
    protected void g() {
    }

    @NonNull
    public Context getContext() {
        return this.q;
    }

    public int getId() {
        return this.n;
    }

    @MainThread
    protected void h() {
    }

    public boolean isAbandoned() {
        return this.s;
    }

    public boolean isReset() {
        return this.t;
    }

    public boolean isStarted() {
        return this.r;
    }

    @MainThread
    public void onContentChanged() {
        if (this.r) {
            forceLoad();
        } else {
            this.u = true;
        }
    }

    @MainThread
    public void registerListener(int i, @NonNull OnLoadCompleteListener<D> onLoadCompleteListener) {
        if (this.o != null) {
            throw new IllegalStateException("There is already a listener registered");
        }
        this.o = onLoadCompleteListener;
        this.n = i;
    }

    @MainThread
    public void registerOnLoadCanceledListener(@NonNull OnLoadCanceledListener<D> onLoadCanceledListener) {
        if (this.p != null) {
            throw new IllegalStateException("There is already a listener registered");
        }
        this.p = onLoadCanceledListener;
    }

    @MainThread
    public void reset() {
        g();
        this.t = true;
        this.r = false;
        this.s = false;
        this.u = false;
        this.v = false;
    }

    public void rollbackContentChanged() {
        if (this.v) {
            onContentChanged();
        }
    }

    @MainThread
    public final void startLoading() {
        this.r = true;
        this.t = false;
        this.s = false;
        e();
    }

    @MainThread
    public void stopLoading() {
        this.r = false;
        f();
    }

    public boolean takeContentChanged() {
        boolean z = this.u;
        this.u = false;
        this.v |= z;
        return z;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        DebugUtils.buildShortClassTag(this, sb);
        sb.append(" id=");
        sb.append(this.n);
        sb.append(h.d);
        return sb.toString();
    }

    @MainThread
    public void unregisterListener(@NonNull OnLoadCompleteListener<D> onLoadCompleteListener) {
        if (this.o == null) {
            throw new IllegalStateException("No listener register");
        }
        if (this.o != onLoadCompleteListener) {
            throw new IllegalArgumentException("Attempting to unregister the wrong listener");
        }
        this.o = null;
    }

    @MainThread
    public void unregisterOnLoadCanceledListener(@NonNull OnLoadCanceledListener<D> onLoadCanceledListener) {
        if (this.p == null) {
            throw new IllegalStateException("No listener register");
        }
        if (this.p != onLoadCanceledListener) {
            throw new IllegalArgumentException("Attempting to unregister the wrong listener");
        }
        this.p = null;
    }
}
