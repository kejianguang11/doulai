package org.cocos2dx.okhttp3;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import org.cocos2dx.okhttp3.internal.Util;
import org.cocos2dx.okhttp3.internal.tls.CertificateChainCleaner;
import org.cocos2dx.okio.ByteString;

/* JADX INFO: loaded from: classes.dex */
public final class CertificatePinner {
    public static final CertificatePinner DEFAULT = new Builder().build();

    @Nullable
    private final CertificateChainCleaner certificateChainCleaner;
    private final Set<Pin> pins;

    public static final class Builder {
        private final List<Pin> pins = new ArrayList();

        public Builder add(String str, String... strArr) {
            if (str == null) {
                throw new NullPointerException("pattern == null");
            }
            for (String str2 : strArr) {
                this.pins.add(new Pin(str, str2));
            }
            return this;
        }

        public CertificatePinner build() {
            return new CertificatePinner(new LinkedHashSet(this.pins), null);
        }
    }

    static final class Pin {
        private static final String WILDCARD = "*.";
        final String a;
        final String b;
        final String c;
        final ByteString d;

        Pin(String str, String str2) {
            StringBuilder sb;
            String str3;
            this.a = str;
            if (str.startsWith(WILDCARD)) {
                sb = new StringBuilder();
                sb.append("http://");
                str = str.substring(WILDCARD.length());
            } else {
                sb = new StringBuilder();
                sb.append("http://");
            }
            sb.append(str);
            this.b = HttpUrl.get(sb.toString()).host();
            if (str2.startsWith("sha1/")) {
                this.c = "sha1/";
                str3 = "sha1/";
            } else {
                if (!str2.startsWith("sha256/")) {
                    throw new IllegalArgumentException("pins must start with 'sha256/' or 'sha1/': " + str2);
                }
                this.c = "sha256/";
                str3 = "sha256/";
            }
            this.d = ByteString.decodeBase64(str2.substring(str3.length()));
            if (this.d != null) {
                return;
            }
            throw new IllegalArgumentException("pins must be base64: " + str2);
        }

        boolean a(String str) {
            if (!this.a.startsWith(WILDCARD)) {
                return str.equals(this.b);
            }
            int iIndexOf = str.indexOf(46);
            return (str.length() - iIndexOf) - 1 == this.b.length() && str.regionMatches(false, iIndexOf + 1, this.b, 0, this.b.length());
        }

        public boolean equals(Object obj) {
            if (obj instanceof Pin) {
                Pin pin = (Pin) obj;
                if (this.a.equals(pin.a) && this.c.equals(pin.c) && this.d.equals(pin.d)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return ((((527 + this.a.hashCode()) * 31) + this.c.hashCode()) * 31) + this.d.hashCode();
        }

        public String toString() {
            return this.c + this.d.base64();
        }
    }

    CertificatePinner(Set<Pin> set, @Nullable CertificateChainCleaner certificateChainCleaner) {
        this.pins = set;
        this.certificateChainCleaner = certificateChainCleaner;
    }

    public static String pin(Certificate certificate) {
        if (!(certificate instanceof X509Certificate)) {
            throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
        }
        return "sha256/" + sha256((X509Certificate) certificate).base64();
    }

    static ByteString sha1(X509Certificate x509Certificate) {
        return ByteString.of(x509Certificate.getPublicKey().getEncoded()).sha1();
    }

    static ByteString sha256(X509Certificate x509Certificate) {
        return ByteString.of(x509Certificate.getPublicKey().getEncoded()).sha256();
    }

    public void check(String str, List<Certificate> list) throws SSLPeerUnverifiedException {
        List<Pin> listFindMatchingPins = findMatchingPins(str);
        if (listFindMatchingPins.isEmpty()) {
            return;
        }
        if (this.certificateChainCleaner != null) {
            list = this.certificateChainCleaner.clean(list, str);
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            X509Certificate x509Certificate = (X509Certificate) list.get(i);
            int size2 = listFindMatchingPins.size();
            ByteString byteStringSha256 = null;
            ByteString byteStringSha1 = null;
            for (int i2 = 0; i2 < size2; i2++) {
                Pin pin = listFindMatchingPins.get(i2);
                if (pin.c.equals("sha256/")) {
                    if (byteStringSha256 == null) {
                        byteStringSha256 = sha256(x509Certificate);
                    }
                    if (pin.d.equals(byteStringSha256)) {
                        return;
                    }
                } else {
                    if (!pin.c.equals("sha1/")) {
                        throw new AssertionError("unsupported hashAlgorithm: " + pin.c);
                    }
                    if (byteStringSha1 == null) {
                        byteStringSha1 = sha1(x509Certificate);
                    }
                    if (pin.d.equals(byteStringSha1)) {
                        return;
                    }
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Certificate pinning failure!");
        sb.append("\n  Peer certificate chain:");
        int size3 = list.size();
        for (int i3 = 0; i3 < size3; i3++) {
            X509Certificate x509Certificate2 = (X509Certificate) list.get(i3);
            sb.append("\n    ");
            sb.append(pin(x509Certificate2));
            sb.append(": ");
            sb.append(x509Certificate2.getSubjectDN().getName());
        }
        sb.append("\n  Pinned certificates for ");
        sb.append(str);
        sb.append(":");
        int size4 = listFindMatchingPins.size();
        for (int i4 = 0; i4 < size4; i4++) {
            Pin pin2 = listFindMatchingPins.get(i4);
            sb.append("\n    ");
            sb.append(pin2);
        }
        throw new SSLPeerUnverifiedException(sb.toString());
    }

    public void check(String str, Certificate... certificateArr) throws SSLPeerUnverifiedException {
        check(str, Arrays.asList(certificateArr));
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof CertificatePinner) {
            CertificatePinner certificatePinner = (CertificatePinner) obj;
            if (Util.equal(this.certificateChainCleaner, certificatePinner.certificateChainCleaner) && this.pins.equals(certificatePinner.pins)) {
                return true;
            }
        }
        return false;
    }

    List<Pin> findMatchingPins(String str) {
        List<Pin> listEmptyList = Collections.emptyList();
        for (Pin pin : this.pins) {
            if (pin.a(str)) {
                if (listEmptyList.isEmpty()) {
                    listEmptyList = new ArrayList<>();
                }
                listEmptyList.add(pin);
            }
        }
        return listEmptyList;
    }

    public int hashCode() {
        return ((this.certificateChainCleaner != null ? this.certificateChainCleaner.hashCode() : 0) * 31) + this.pins.hashCode();
    }

    CertificatePinner withCertificateChainCleaner(@Nullable CertificateChainCleaner certificateChainCleaner) {
        return Util.equal(this.certificateChainCleaner, certificateChainCleaner) ? this : new CertificatePinner(this.pins, certificateChainCleaner);
    }
}
