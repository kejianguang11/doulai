package com.loc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public final class ei implements Parcelable {
    public static final Parcelable.Creator<ei> CREATOR = new Parcelable.Creator<ei>() { // from class: com.loc.ei.1
        private static ei a(Parcel parcel) {
            ei eiVar = new ei();
            eiVar.c(parcel.readString());
            eiVar.d(parcel.readString());
            eiVar.e(parcel.readString());
            eiVar.f(parcel.readString());
            eiVar.b(parcel.readString());
            eiVar.c(parcel.readLong());
            eiVar.d(parcel.readLong());
            eiVar.a(parcel.readLong());
            eiVar.b(parcel.readLong());
            eiVar.a(parcel.readString());
            return eiVar;
        }

        private static ei[] a(int i) {
            return new ei[i];
        }

        @Override // android.os.Parcelable.Creator
        public final /* synthetic */ ei createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final /* synthetic */ ei[] newArray(int i) {
            return a(i);
        }
    };
    private String e;
    private String f;
    private long a = 0;
    private long b = 0;
    private long c = 0;
    private long d = 0;
    private String g = "first";
    private String h = "";
    private String i = "";
    private String j = null;

    public final long a() {
        if (this.d - this.c <= 0) {
            return 0L;
        }
        return this.d - this.c;
    }

    public final void a(long j) {
        this.c = j;
    }

    public final void a(String str) {
        this.i = str;
    }

    public final String b() {
        return this.i;
    }

    public final void b(long j) {
        this.d = j;
    }

    public final void b(String str) {
        this.j = str;
    }

    public final String c() {
        return this.j;
    }

    public final void c(long j) {
        this.a = j;
    }

    public final void c(String str) {
        this.e = str;
    }

    public final String d() {
        return this.e;
    }

    public final void d(long j) {
        this.b = j;
    }

    public final void d(String str) {
        this.f = str;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final String e() {
        return this.f;
    }

    public final void e(String str) {
        this.g = str;
    }

    public final String f() {
        return this.g;
    }

    public final void f(String str) {
        this.h = str;
    }

    public final String g() {
        return this.h;
    }

    public final long h() {
        if (this.b <= this.a) {
            return 0L;
        }
        return this.b - this.a;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        try {
            parcel.writeString(this.e);
            parcel.writeString(this.f);
            parcel.writeString(this.g);
            parcel.writeString(this.h);
            parcel.writeString(this.j);
            parcel.writeLong(this.a);
            parcel.writeLong(this.b);
            parcel.writeLong(this.c);
            parcel.writeLong(this.d);
            parcel.writeString(this.i);
        } catch (Throwable unused) {
        }
    }
}
