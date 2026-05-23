package com.getui.gtc.base.crypt;

import android.content.Context;
import com.getui.gtc.base.util.io.IOUtils;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public final class c {
    KeyPair a;
    private b b;
    private File c;

    c(Context context, KeyPair keyPair) throws NoSuchAlgorithmException {
        this.c = context.getFilesDir();
        this.a = keyPair;
        if (keyPair == null) {
            this.b = new b(context.getPackageName());
        }
    }

    public final SecretKey a(String str) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {
        return a(str, true, this.a);
    }

    public final SecretKey a(String str, boolean z, KeyPair keyPair) throws Throwable {
        File file = new File(this.c, str);
        if (file.exists() || !z) {
            try {
                byte[] file2 = IOUtils.readFile(file);
                return CryptTools.wrapperKey("AES", keyPair != null ? CryptTools.decrypt("RSA/ECB/PKCS1Padding", keyPair.getPrivate(), file2) : this.a != null ? CryptTools.decrypt("RSA/ECB/PKCS1Padding", this.a.getPrivate(), file2) : this.b.b(file2));
            } catch (IOException | InvalidAlgorithmParameterException unused) {
                return null;
            }
        }
        SecretKey secretKeyGenerateKey = CryptTools.generateKey("AES", 128);
        byte[] encoded = secretKeyGenerateKey.getEncoded();
        IOUtils.saveToFile(this.a != null ? CryptTools.encrypt("RSA/ECB/PKCS1Padding", this.a.getPublic(), encoded) : this.b.a(encoded), new File(this.c, str));
        return secretKeyGenerateKey;
    }

    public final IvParameterSpec b(String str, boolean z, KeyPair keyPair) throws Throwable {
        File file = new File(this.c, str);
        if (file.exists() || !z) {
            try {
                byte[] file2 = IOUtils.readFile(file);
                return new IvParameterSpec(keyPair != null ? CryptTools.decrypt("RSA/ECB/PKCS1Padding", keyPair.getPrivate(), file2) : this.a != null ? CryptTools.decrypt("RSA/ECB/PKCS1Padding", this.a.getPrivate(), file2) : this.b.b(file2));
            } catch (IOException | InvalidAlgorithmParameterException unused) {
                return null;
            }
        }
        byte[] bArr = new byte[16];
        new SecureRandom().nextBytes(bArr);
        IOUtils.saveToFile(this.a != null ? CryptTools.encrypt("RSA/ECB/PKCS1Padding", this.a.getPublic(), bArr) : this.b.a(bArr), new File(this.c, str));
        return new IvParameterSpec(bArr);
    }
}
