package com.igexin.a;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    private static final String a = "com.igexin.push.extension.distribution.gws.action.guard.WakeCallback";
    private static final int c = 1;
    private IBinder b;

    public b(IBinder iBinder) {
        this.b = iBinder;
    }

    private IBinder a() {
        return this.b;
    }

    public final void a(Bundle bundle) throws RemoteException {
        if (this.b == null) {
            return;
        }
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken(a);
            parcelObtain.writeInt(1);
            bundle.writeToParcel(parcelObtain, 0);
            this.b.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }
}
