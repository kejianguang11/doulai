package com.gme.trtc.hardwareearmonitor.oplus;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.gme.liteav.base.util.LiteavLog;

/* JADX INFO: loaded from: classes.dex */
public interface OplusKaraokeServiceAidlInterface extends IInterface {

    public static class Default implements OplusKaraokeServiceAidlInterface {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.gme.trtc.hardwareearmonitor.oplus.OplusKaraokeServiceAidlInterface
        public void setActiveClient(String str) throws RemoteException {
        }

        @Override // com.gme.trtc.hardwareearmonitor.oplus.OplusKaraokeServiceAidlInterface
        public void setHeadsetState(boolean z) throws RemoteException {
        }

        @Override // com.gme.trtc.hardwareearmonitor.oplus.OplusKaraokeServiceAidlInterface
        public void setPermitBits(int i, int i2, int i3, String str) throws RemoteException {
        }
    }

    public static abstract class Stub extends Binder implements OplusKaraokeServiceAidlInterface {
        private static String DESCRIPTOR = "OplusKaraokeServiceAidlInterface";

        static class a implements OplusKaraokeServiceAidlInterface {
            public static OplusKaraokeServiceAidlInterface a;
            private IBinder b;

            a(IBinder iBinder) {
                this.b = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.b;
            }

            @Override // com.gme.trtc.hardwareearmonitor.oplus.OplusKaraokeServiceAidlInterface
            public final void setActiveClient(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    boolean zTransact = this.b.transact(2, parcelObtain, parcelObtain2, 0);
                    if (!zTransact) {
                        LiteavLog.e("setActiveClient", "setActiveClient error");
                    }
                    if (zTransact || Stub.getDefaultImpl() == null) {
                        parcelObtain2.readException();
                    } else {
                        Stub.getDefaultImpl().setActiveClient(str);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.gme.trtc.hardwareearmonitor.oplus.OplusKaraokeServiceAidlInterface
            public final void setHeadsetState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    boolean zTransact = this.b.transact(1, parcelObtain, parcelObtain2, 0);
                    if (!zTransact) {
                        LiteavLog.e("setHeadsetState", "setHeadsetState error");
                    }
                    if (zTransact || Stub.getDefaultImpl() == null) {
                        parcelObtain2.readException();
                    } else {
                        Stub.getDefaultImpl().setHeadsetState(z);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.gme.trtc.hardwareearmonitor.oplus.OplusKaraokeServiceAidlInterface
            public final void setPermitBits(int i, int i2, int i3, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    if (this.b.transact(3, parcelObtain, parcelObtain2, 0) || Stub.getDefaultImpl() == null) {
                        parcelObtain2.readException();
                    } else {
                        Stub.getDefaultImpl().setPermitBits(i, i2, i3, str);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static OplusKaraokeServiceAidlInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof OplusKaraokeServiceAidlInterface)) ? new a(iBinder) : (OplusKaraokeServiceAidlInterface) iInterfaceQueryLocalInterface;
        }

        public static OplusKaraokeServiceAidlInterface getDefaultImpl() {
            return a.a;
        }

        public static void setDESCRIPTOR(String str) {
            DESCRIPTOR = str;
        }

        public static boolean setDefaultImpl(OplusKaraokeServiceAidlInterface oplusKaraokeServiceAidlInterface) {
            if (a.a != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (oplusKaraokeServiceAidlInterface == null) {
                return false;
            }
            a.a = oplusKaraokeServiceAidlInterface;
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = DESCRIPTOR;
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface(str);
                    setHeadsetState(parcel.readInt() != 0);
                    break;
                case 2:
                    parcel.enforceInterface(str);
                    setActiveClient(parcel.readString());
                    break;
                case 3:
                    parcel.enforceInterface(str);
                    setPermitBits(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readString());
                    break;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel2.writeNoException();
            return true;
        }
    }

    void setActiveClient(String str) throws RemoteException;

    void setHeadsetState(boolean z) throws RemoteException;

    void setPermitBits(int i, int i2, int i3, String str) throws RemoteException;
}
