package com.gcloudsdk.apollo.apollovoice.httpclient;

import com.gcloudsdk.apollo.ApolloVoiceLog;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/* JADX INFO: loaded from: classes.dex */
public class FastHostnameVerifier implements HostnameVerifier {
    public String mHost;

    public FastHostnameVerifier(String str) {
        this.mHost = str;
    }

    public boolean equals(Object obj) {
        String str;
        if (this.mHost == null || this.mHost.equals("") || !(obj instanceof FastHostnameVerifier) || (str = ((FastHostnameVerifier) obj).mHost) == null || str.equals("")) {
            return false;
        }
        return this.mHost.equals(str);
    }

    @Override // javax.net.ssl.HostnameVerifier
    public boolean verify(String str, SSLSession sSLSession) {
        boolean z = str.equalsIgnoreCase(this.mHost) || HttpsURLConnection.getDefaultHostnameVerifier().verify(this.mHost, sSLSession);
        ApolloVoiceLog.LogI("hostname: " + str + " verify result: " + z);
        return z;
    }
}
