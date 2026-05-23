package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"BanParcelableUsage"})
final class FragmentState implements Parcelable {
    public static final Parcelable.Creator<FragmentState> CREATOR = new Parcelable.Creator<FragmentState>() { // from class: androidx.fragment.app.FragmentState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FragmentState createFromParcel(Parcel parcel) {
            return new FragmentState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FragmentState[] newArray(int i) {
            return new FragmentState[i];
        }
    };
    final String a;
    final String b;
    final boolean c;
    final int d;
    final int e;
    final String f;
    final boolean g;
    final boolean h;
    final boolean i;
    final Bundle j;
    final boolean k;
    final int l;
    Bundle m;

    FragmentState(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readInt() != 0;
        this.d = parcel.readInt();
        this.e = parcel.readInt();
        this.f = parcel.readString();
        this.g = parcel.readInt() != 0;
        this.h = parcel.readInt() != 0;
        this.i = parcel.readInt() != 0;
        this.j = parcel.readBundle();
        this.k = parcel.readInt() != 0;
        this.m = parcel.readBundle();
        this.l = parcel.readInt();
    }

    FragmentState(Fragment fragment) {
        this.a = fragment.getClass().getName();
        this.b = fragment.g;
        this.c = fragment.n;
        this.d = fragment.w;
        this.e = fragment.x;
        this.f = fragment.y;
        this.g = fragment.B;
        this.h = fragment.m;
        this.i = fragment.A;
        this.j = fragment.h;
        this.k = fragment.z;
        this.l = fragment.Q.ordinal();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentState{");
        sb.append(this.a);
        sb.append(" (");
        sb.append(this.b);
        sb.append(")}:");
        if (this.c) {
            sb.append(" fromLayout");
        }
        if (this.e != 0) {
            sb.append(" id=0x");
            sb.append(Integer.toHexString(this.e));
        }
        if (this.f != null && !this.f.isEmpty()) {
            sb.append(" tag=");
            sb.append(this.f);
        }
        if (this.g) {
            sb.append(" retainInstance");
        }
        if (this.h) {
            sb.append(" removing");
        }
        if (this.i) {
            sb.append(" detached");
        }
        if (this.k) {
            sb.append(" hidden");
        }
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeInt(this.c ? 1 : 0);
        parcel.writeInt(this.d);
        parcel.writeInt(this.e);
        parcel.writeString(this.f);
        parcel.writeInt(this.g ? 1 : 0);
        parcel.writeInt(this.h ? 1 : 0);
        parcel.writeInt(this.i ? 1 : 0);
        parcel.writeBundle(this.j);
        parcel.writeInt(this.k ? 1 : 0);
        parcel.writeBundle(this.m);
        parcel.writeInt(this.l);
    }
}
