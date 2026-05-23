package androidx.loader.content;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContentResolverCompat;
import androidx.core.os.CancellationSignal;
import androidx.core.os.OperationCanceledException;
import androidx.loader.content.Loader;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class CursorLoader extends AsyncTaskLoader<Cursor> {
    final Loader<Cursor>.ForceLoadContentObserver f;
    Uri g;
    String[] h;
    String i;
    String[] j;
    String k;
    Cursor l;
    CancellationSignal m;

    public CursorLoader(@NonNull Context context) {
        super(context);
        this.f = new Loader.ForceLoadContentObserver();
    }

    public CursorLoader(@NonNull Context context, @NonNull Uri uri, @Nullable String[] strArr, @Nullable String str, @Nullable String[] strArr2, @Nullable String str2) {
        super(context);
        this.f = new Loader.ForceLoadContentObserver();
        this.g = uri;
        this.h = strArr;
        this.i = str;
        this.j = strArr2;
        this.k = str2;
    }

    @Override // androidx.loader.content.AsyncTaskLoader
    public void cancelLoadInBackground() {
        super.cancelLoadInBackground();
        synchronized (this) {
            if (this.m != null) {
                this.m.cancel();
            }
        }
    }

    @Override // androidx.loader.content.Loader
    public void deliverResult(Cursor cursor) {
        if (isReset()) {
            if (cursor != null) {
                cursor.close();
                return;
            }
            return;
        }
        Cursor cursor2 = this.l;
        this.l = cursor;
        if (isStarted()) {
            super.deliverResult(cursor);
        }
        if (cursor2 == null || cursor2 == cursor || cursor2.isClosed()) {
            return;
        }
        cursor2.close();
    }

    @Override // androidx.loader.content.AsyncTaskLoader, androidx.loader.content.Loader
    @Deprecated
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        printWriter.print(str);
        printWriter.print("mUri=");
        printWriter.println(this.g);
        printWriter.print(str);
        printWriter.print("mProjection=");
        printWriter.println(Arrays.toString(this.h));
        printWriter.print(str);
        printWriter.print("mSelection=");
        printWriter.println(this.i);
        printWriter.print(str);
        printWriter.print("mSelectionArgs=");
        printWriter.println(Arrays.toString(this.j));
        printWriter.print(str);
        printWriter.print("mSortOrder=");
        printWriter.println(this.k);
        printWriter.print(str);
        printWriter.print("mCursor=");
        printWriter.println(this.l);
        printWriter.print(str);
        printWriter.print("mContentChanged=");
        printWriter.println(this.u);
    }

    @Override // androidx.loader.content.Loader
    protected void e() {
        if (this.l != null) {
            deliverResult(this.l);
        }
        if (takeContentChanged() || this.l == null) {
            forceLoad();
        }
    }

    @Override // androidx.loader.content.Loader
    protected void f() {
        cancelLoad();
    }

    @Override // androidx.loader.content.Loader
    protected void g() {
        super.g();
        f();
        if (this.l != null && !this.l.isClosed()) {
            this.l.close();
        }
        this.l = null;
    }

    @Nullable
    public String[] getProjection() {
        return this.h;
    }

    @Nullable
    public String getSelection() {
        return this.i;
    }

    @Nullable
    public String[] getSelectionArgs() {
        return this.j;
    }

    @Nullable
    public String getSortOrder() {
        return this.k;
    }

    @NonNull
    public Uri getUri() {
        return this.g;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.loader.content.AsyncTaskLoader
    public Cursor loadInBackground() {
        synchronized (this) {
            if (isLoadInBackgroundCanceled()) {
                throw new OperationCanceledException();
            }
            this.m = new CancellationSignal();
        }
        try {
            Cursor cursorQuery = ContentResolverCompat.query(getContext().getContentResolver(), this.g, this.h, this.i, this.j, this.k, this.m);
            if (cursorQuery != null) {
                try {
                    cursorQuery.getCount();
                    cursorQuery.registerContentObserver(this.f);
                } catch (RuntimeException e) {
                    cursorQuery.close();
                    throw e;
                }
            }
            synchronized (this) {
                this.m = null;
            }
            return cursorQuery;
        } catch (Throwable th) {
            synchronized (this) {
                this.m = null;
                throw th;
            }
        }
    }

    @Override // androidx.loader.content.AsyncTaskLoader
    public void onCanceled(Cursor cursor) {
        if (cursor == null || cursor.isClosed()) {
            return;
        }
        cursor.close();
    }

    public void setProjection(@Nullable String[] strArr) {
        this.h = strArr;
    }

    public void setSelection(@Nullable String str) {
        this.i = str;
    }

    public void setSelectionArgs(@Nullable String[] strArr) {
        this.j = strArr;
    }

    public void setSortOrder(@Nullable String str) {
        this.k = str;
    }

    public void setUri(@NonNull Uri uri) {
        this.g = uri;
    }
}
