package android.support.v4.os;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.os.IResultReceiver;
import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"BanParcelableUsage"})
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class ResultReceiver implements Parcelable {
    public static final Parcelable.Creator<ResultReceiver> CREATOR = new Parcelable.Creator<ResultReceiver>() { // from class: android.support.v4.os.ResultReceiver.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResultReceiver createFromParcel(Parcel parcel) {
            return new ResultReceiver(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResultReceiver[] newArray(int i) {
            return new ResultReceiver[i];
        }
    };
    final boolean a;
    final Handler b;
    IResultReceiver c;

    class MyResultReceiver extends IResultReceiver.Stub {
        MyResultReceiver() {
        }

        @Override // android.support.v4.os.IResultReceiver
        public void send(int i, Bundle bundle) {
            if (ResultReceiver.this.b != null) {
                ResultReceiver.this.b.post(ResultReceiver.this.new MyRunnable(i, bundle));
            } else {
                ResultReceiver.this.a(i, bundle);
            }
        }
    }

    class MyRunnable implements Runnable {
        final int a;
        final Bundle b;

        MyRunnable(int i, Bundle bundle) {
            this.a = i;
            this.b = bundle;
        }

        @Override // java.lang.Runnable
        public void run() {
            ResultReceiver.this.a(this.a, this.b);
        }
    }

    public ResultReceiver(Handler handler) {
        this.a = true;
        this.b = handler;
    }

    ResultReceiver(Parcel parcel) {
        this.a = false;
        this.b = null;
        this.c = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
    }

    protected void a(int i, Bundle bundle) {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void send(int i, Bundle bundle) {
        if (this.a) {
            if (this.b != null) {
                this.b.post(new MyRunnable(i, bundle));
                return;
            } else {
                a(i, bundle);
                return;
            }
        }
        if (this.c != null) {
            try {
                this.c.send(i, bundle);
            } catch (RemoteException unused) {
            }
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        synchronized (this) {
            if (this.c == null) {
                this.c = new MyResultReceiver();
            }
            parcel.writeStrongBinder(this.c.asBinder());
        }
    }
}
