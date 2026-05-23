package com.zx.a.I8b7;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class d1 {
    public static final a a;
    public static volatile d1 b = null;
    public static Context c = null;
    public static boolean d = false;

    public interface a {
    }

    public static class b implements a {
        public String a;
        public final String e;
        public final String g;
        public final String h;
        public d i;
        public boolean b = false;
        public boolean c = false;
        public final CountDownLatch d = new CountDownLatch(1);
        public final String f = null;

        public b(String str, String str2, String str3, String str4) {
            this.e = str;
            this.g = str3;
            this.h = str4;
        }
    }

    public static class c implements IInterface {
        public IBinder a;

        public c(IBinder iBinder, String str) {
            this.a = iBinder;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this.a;
        }
    }

    public static class d implements ServiceConnection {
        public c a;
        public String b;
        public CountDownLatch c;
        public IBinder d;

        public d(String str, CountDownLatch countDownLatch) {
            this.b = str;
            this.c = countDownLatch;
        }

        public boolean a(Context context, Intent intent) {
            c cVar;
            if (this.a != null) {
                return true;
            }
            try {
                boolean zBindService = context.bindService(intent, this, 1);
                this.c.await(1L, TimeUnit.SECONDS);
                IBinder iBinder = this.d;
                String str = this.b;
                if (iBinder == null) {
                    cVar = null;
                } else {
                    IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(str);
                    cVar = iInterfaceQueryLocalInterface instanceof c ? (c) iInterfaceQueryLocalInterface : new c(iBinder, str);
                }
                this.a = cVar;
                return zBindService;
            } catch (Throwable unused) {
                return false;
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.d = iBinder;
                this.c.countDown();
            } catch (Throwable unused) {
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            this.a = null;
            this.d = null;
        }
    }

    public static class e extends b {
        public final CountDownLatch j;

        public class a extends b {
            public a() {
            }
        }

        public static abstract class b extends Binder implements IInterface {
            public b() {
                attachInterface(this, "com.hihonor.cloudservice.oaid.IOAIDCallBack");
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this;
            }

            @Override // android.os.Binder
            public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
                if (i != 2) {
                    if (i != 1598968902) {
                        return super.onTransact(i, parcel, parcel2, i2);
                    }
                    parcel2.writeString("com.hihonor.cloudservice.oaid.IOAIDCallBack");
                    return true;
                }
                parcel.enforceInterface("com.hihonor.cloudservice.oaid.IOAIDCallBack");
                int i3 = parcel.readInt();
                Bundle bundle = parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null;
                a aVar = (a) this;
                if (i3 == 0 && bundle != null) {
                    e.this.a = bundle.getString("oa_id_flag");
                }
                e.this.j.countDown();
                parcel2.writeNoException();
                return true;
            }
        }

        public e() {
            super("com.hihonor.id", null, "com.hihonor.id.HnOaIdService", "com.hihonor.cloudservice.oaid.IOAIDService");
            this.j = new CountDownLatch(1);
        }

        public String a(Context context) {
            d dVar;
            d dVar2;
            if (!TextUtils.isEmpty(this.a) || (dVar = this.i) == null || dVar.a == null) {
                return this.a;
            }
            try {
                IBinder iBinder = dVar.d;
                a aVar = new a();
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.hihonor.cloudservice.oaid.IOAIDService");
                    parcelObtain.writeStrongBinder(aVar);
                    iBinder.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    this.j.await(1L, TimeUnit.SECONDS);
                    if (!TextUtils.isEmpty(this.a) && (dVar2 = this.i) != null) {
                        context.unbindService(dVar2);
                    }
                } catch (Throwable th) {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    throw th;
                }
            } catch (Throwable unused) {
            }
            return this.a;
        }
    }

    static {
        String upperCase = Build.MANUFACTURER.toUpperCase();
        upperCase.getClass();
        a = !upperCase.equals("HONOR") ? null : new e();
    }

    public final boolean a() {
        a aVar;
        try {
            Context context = c;
            if (context == null || (aVar = a) == null) {
                return false;
            }
            b bVar = (b) aVar;
            if (!bVar.c) {
                if (context == null || TextUtils.isEmpty(bVar.e)) {
                    bVar.b = false;
                } else {
                    PackageInfo packageInfoA = m3.a(bVar.e, 0);
                    if (Build.VERSION.SDK_INT >= 28) {
                        return packageInfoA.getLongVersionCode() >= 1;
                    }
                    bVar.b = packageInfoA.versionCode >= 1;
                }
                bVar.c = true;
            }
            return bVar.b;
        } catch (Throwable unused) {
            return false;
        }
    }
}
