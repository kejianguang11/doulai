package com.mobile.auth.y;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/* JADX INFO: loaded from: classes.dex */
public final class o extends SSLSocketFactory {
    private static final String[] b = {"TLSv1.1", "TLSv1.2"};
    final SSLSocketFactory a;

    public o(SSLSocketFactory sSLSocketFactory) {
        this.a = sSLSocketFactory;
    }

    private static Socket a(Socket socket) {
        try {
            if (socket instanceof SSLSocket) {
                ((SSLSocket) socket).setEnabledProtocols(b);
            }
            return socket;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @Override // javax.net.SocketFactory
    public final Socket createSocket(String str, int i) {
        try {
            return a(this.a.createSocket(str, i));
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @Override // javax.net.SocketFactory
    public final Socket createSocket(String str, int i, InetAddress inetAddress, int i2) {
        try {
            return a(this.a.createSocket(str, i, inetAddress, i2));
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @Override // javax.net.SocketFactory
    public final Socket createSocket(InetAddress inetAddress, int i) {
        try {
            return a(this.a.createSocket(inetAddress, i));
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @Override // javax.net.SocketFactory
    public final Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) {
        try {
            return a(this.a.createSocket(inetAddress, i, inetAddress2, i2));
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public final Socket createSocket(Socket socket, String str, int i, boolean z) {
        try {
            return a(this.a.createSocket(socket, str, i, z));
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public final String[] getDefaultCipherSuites() {
        try {
            return this.a.getDefaultCipherSuites();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public final String[] getSupportedCipherSuites() {
        try {
            return this.a.getSupportedCipherSuites();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }
}
