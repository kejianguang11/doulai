package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"BanParcelableUsage"})
final class BackStackState implements Parcelable {
    public static final Parcelable.Creator<BackStackState> CREATOR = new Parcelable.Creator<BackStackState>() { // from class: androidx.fragment.app.BackStackState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackStackState createFromParcel(Parcel parcel) {
            return new BackStackState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackStackState[] newArray(int i) {
            return new BackStackState[i];
        }
    };
    private static final String TAG = "FragmentManager";
    final int[] a;
    final ArrayList<String> b;
    final int[] c;
    final int[] d;
    final int e;
    final String f;
    final int g;
    final int h;
    final CharSequence i;
    final int j;
    final CharSequence k;
    final ArrayList<String> l;
    final ArrayList<String> m;
    final boolean n;

    public BackStackState(Parcel parcel) {
        this.a = parcel.createIntArray();
        this.b = parcel.createStringArrayList();
        this.c = parcel.createIntArray();
        this.d = parcel.createIntArray();
        this.e = parcel.readInt();
        this.f = parcel.readString();
        this.g = parcel.readInt();
        this.h = parcel.readInt();
        this.i = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.j = parcel.readInt();
        this.k = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.l = parcel.createStringArrayList();
        this.m = parcel.createStringArrayList();
        this.n = parcel.readInt() != 0;
    }

    public BackStackState(BackStackRecord backStackRecord) {
        int size = backStackRecord.d.size();
        this.a = new int[size * 5];
        if (!backStackRecord.j) {
            throw new IllegalStateException("Not on back stack");
        }
        this.b = new ArrayList<>(size);
        this.c = new int[size];
        this.d = new int[size];
        int i = 0;
        int i2 = 0;
        while (i < size) {
            FragmentTransaction.Op op = backStackRecord.d.get(i);
            int i3 = i2 + 1;
            this.a[i2] = op.a;
            this.b.add(op.b != null ? op.b.g : null);
            int i4 = i3 + 1;
            this.a[i3] = op.c;
            int i5 = i4 + 1;
            this.a[i4] = op.d;
            int i6 = i5 + 1;
            this.a[i5] = op.e;
            this.a[i6] = op.f;
            this.c[i] = op.g.ordinal();
            this.d[i] = op.h.ordinal();
            i++;
            i2 = i6 + 1;
        }
        this.e = backStackRecord.i;
        this.f = backStackRecord.l;
        this.g = backStackRecord.c;
        this.h = backStackRecord.m;
        this.i = backStackRecord.n;
        this.j = backStackRecord.o;
        this.k = backStackRecord.p;
        this.l = backStackRecord.q;
        this.m = backStackRecord.r;
        this.n = backStackRecord.s;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BackStackRecord instantiate(FragmentManager fragmentManager) {
        BackStackRecord backStackRecord = new BackStackRecord(fragmentManager);
        int i = 0;
        int i2 = 0;
        while (i < this.a.length) {
            FragmentTransaction.Op op = new FragmentTransaction.Op();
            int i3 = i + 1;
            op.a = this.a[i];
            if (FragmentManager.a(2)) {
                Log.v(TAG, "Instantiate " + backStackRecord + " op #" + i2 + " base fragment #" + this.a[i3]);
            }
            String str = this.b.get(i2);
            op.b = str != null ? fragmentManager.b(str) : null;
            op.g = Lifecycle.State.values()[this.c[i2]];
            op.h = Lifecycle.State.values()[this.d[i2]];
            int i4 = i3 + 1;
            op.c = this.a[i3];
            int i5 = i4 + 1;
            op.d = this.a[i4];
            int i6 = i5 + 1;
            op.e = this.a[i5];
            op.f = this.a[i6];
            backStackRecord.e = op.c;
            backStackRecord.f = op.d;
            backStackRecord.g = op.e;
            backStackRecord.h = op.f;
            backStackRecord.a(op);
            i2++;
            i = i6 + 1;
        }
        backStackRecord.i = this.e;
        backStackRecord.l = this.f;
        backStackRecord.c = this.g;
        backStackRecord.j = true;
        backStackRecord.m = this.h;
        backStackRecord.n = this.i;
        backStackRecord.o = this.j;
        backStackRecord.p = this.k;
        backStackRecord.q = this.l;
        backStackRecord.r = this.m;
        backStackRecord.s = this.n;
        backStackRecord.a(1);
        return backStackRecord;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeIntArray(this.a);
        parcel.writeStringList(this.b);
        parcel.writeIntArray(this.c);
        parcel.writeIntArray(this.d);
        parcel.writeInt(this.e);
        parcel.writeString(this.f);
        parcel.writeInt(this.g);
        parcel.writeInt(this.h);
        TextUtils.writeToParcel(this.i, parcel, 0);
        parcel.writeInt(this.j);
        TextUtils.writeToParcel(this.k, parcel, 0);
        parcel.writeStringList(this.l);
        parcel.writeStringList(this.m);
        parcel.writeInt(this.n ? 1 : 0);
    }
}
