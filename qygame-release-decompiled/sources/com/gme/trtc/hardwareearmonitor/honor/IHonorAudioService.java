package com.gme.trtc.hardwareearmonitor.honor;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface IHonorAudioService extends IInterface {

    public static class Default implements IHonorAudioService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.gme.trtc.hardwareearmonitor.honor.IHonorAudioService
        public List getSupportedServices() throws RemoteException {
            return null;
        }

        @Override // com.gme.trtc.hardwareearmonitor.honor.IHonorAudioService
        public void init(String str, String str2) throws RemoteException {
        }

        @Override // com.gme.trtc.hardwareearmonitor.honor.IHonorAudioService
        public boolean isServiceSupported(int i) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub extends Binder implements IHonorAudioService {
        private static final String DESCRIPTOR = "com.hihonor.android.magicx.media.audioengine.IHnAudioService";

        public static class Proxy implements IHonorAudioService {
            public static IHonorAudioService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.gme.trtc.hardwareearmonitor.honor.IHonorAudioService
            public List getSupportedServices() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSupportedServices();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.gme.trtc.hardwareearmonitor.honor.IHonorAudioService
            public void init(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    if (this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) || Stub.getDefaultImpl() == null) {
                        parcelObtain2.readException();
                    } else {
                        Stub.getDefaultImpl().init(str, str2);
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.gme.trtc.hardwareearmonitor.honor.IHonorAudioService
            public boolean isServiceSupported(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) || Stub.getDefaultImpl() == null) {
                        parcelObtain2.readException();
                        return parcelObtain2.readInt() != 0;
                    }
                    boolean zIsServiceSupported = Stub.getDefaultImpl().isServiceSupported(i);
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    return zIsServiceSupported;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IHonorAudioService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IHonorAudioService)) ? new Proxy(iBinder) : (IHonorAudioService) iInterfaceQueryLocalInterface;
        }

        public static IHonorAudioService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static boolean setDefaultImpl(IHonorAudioService iHonorAudioService) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iHonorAudioService == null) {
                return false;
            }
            Proxy.sDefaultImpl = iHonorAudioService;
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    List supportedServices = getSupportedServices();
                    parcel2.writeNoException();
                    parcel2.writeList(supportedServices);
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean zIsServiceSupported = isServiceSupported(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsServiceSupported ? 1 : 0);
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    init(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    List getSupportedServices() throws RemoteException;

    void init(String str, String str2) throws RemoteException;

    boolean isServiceSupported(int i) throws RemoteException;
}
