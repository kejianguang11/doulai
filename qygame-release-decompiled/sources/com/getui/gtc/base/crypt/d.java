package com.getui.gtc.base.crypt;

import android.content.Context;
import android.os.Build;
import android.os.Process;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public final class d {
    a a;
    c b;
    Map<String, SecretKey> c = new HashMap();
    Map<String, SecretKey> d = new HashMap();
    Map<String, KeyPair> e = new HashMap();
    Map<String, IvParameterSpec> f = new HashMap();
    String g;

    private KeyPair d(String str) {
        try {
            String strDigestToHexString = CryptTools.digestToHexString("MD5", (str + "-rsa1024alias").getBytes());
            if (this.e.containsKey(strDigestToHexString)) {
                return this.e.get(strDigestToHexString);
            }
            KeyPair keyPairA = this.a.a(null, strDigestToHexString, false);
            if (keyPairA == null) {
                return null;
            }
            this.e.put(strDigestToHexString, keyPairA);
            return keyPairA;
        } catch (Throwable unused) {
            return null;
        }
    }

    final List<CryptException> a(Context context) throws CryptException {
        SecretKey secretKeyA;
        ArrayList arrayList = new ArrayList();
        this.g = String.valueOf(Process.myPid());
        String str = this.g + "-rsa1024alias";
        String str2 = this.g + "-aes128alias";
        String str3 = this.g + "-ivalias";
        String strDigestToHexString = CryptTools.digestToHexString("MD5", str.getBytes());
        String strDigestToHexString2 = CryptTools.digestToHexString("MD5", str2.getBytes());
        String strDigestToHexString3 = CryptTools.digestToHexString("MD5", str3.getBytes());
        this.a = new a();
        if (Build.VERSION.SDK_INT >= 19) {
            try {
                KeyPair keyPairA = this.a.a(context, strDigestToHexString, true);
                this.e.put(strDigestToHexString, keyPairA);
                this.b = new c(context, keyPairA);
                secretKeyA = this.b.a(strDigestToHexString2);
            } catch (Throwable th) {
                arrayList.add(new CryptException("above api 18, but second secret key create failed with android key store!", th));
                secretKeyA = null;
            }
        } else {
            secretKeyA = null;
        }
        if (secretKeyA == null) {
            try {
                this.b = new c(context, null);
                secretKeyA = this.b.a(strDigestToHexString2);
            } catch (Throwable th2) {
                throw new CryptException("second secret key create failed!", th2);
            }
        }
        this.d.put(strDigestToHexString2, secretKeyA);
        try {
            c cVar = this.b;
            IvParameterSpec ivParameterSpecB = cVar.b(strDigestToHexString3, true, cVar.a);
            if (ivParameterSpecB == null) {
                throw new CryptException("iv parameter spec create failed!");
            }
            this.f.put(strDigestToHexString3, ivParameterSpecB);
            return arrayList;
        } catch (Throwable th3) {
            throw new CryptException("iv parameter spec create failed!", th3);
        }
    }

    final SecretKey a() {
        return b(this.g);
    }

    final SecretKey a(String str) {
        try {
            String strDigestToHexString = CryptTools.digestToHexString("MD5", (str + "-aes128alias").getBytes());
            if (this.c.containsKey(strDigestToHexString)) {
                return this.c.get(strDigestToHexString);
            }
            SecretKey secretKeyA = this.a.a(strDigestToHexString);
            if (secretKeyA == null) {
                return null;
            }
            this.c.put(strDigestToHexString, secretKeyA);
            return secretKeyA;
        } catch (Throwable unused) {
            return null;
        }
    }

    final SecretKey b(String str) {
        try {
            String strDigestToHexString = CryptTools.digestToHexString("MD5", (str + "-aes128alias").getBytes());
            if (this.d.containsKey(strDigestToHexString)) {
                return this.d.get(strDigestToHexString);
            }
            SecretKey secretKeyA = this.b.a(strDigestToHexString, false, d(str));
            if (secretKeyA == null) {
                return null;
            }
            this.d.put(strDigestToHexString, secretKeyA);
            return secretKeyA;
        } catch (Throwable unused) {
            return null;
        }
    }

    final IvParameterSpec b() {
        return c(this.g);
    }

    final IvParameterSpec c(String str) {
        try {
            String strDigestToHexString = CryptTools.digestToHexString("MD5", (str + "-ivalias").getBytes());
            if (this.f.containsKey(strDigestToHexString)) {
                return this.f.get(strDigestToHexString);
            }
            IvParameterSpec ivParameterSpecB = this.b.b(strDigestToHexString, false, d(str));
            if (ivParameterSpecB == null) {
                return null;
            }
            this.f.put(strDigestToHexString, ivParameterSpecB);
            return ivParameterSpecB;
        } catch (Throwable unused) {
            return null;
        }
    }
}
