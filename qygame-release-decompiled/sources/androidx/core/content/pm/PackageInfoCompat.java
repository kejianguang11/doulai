package androidx.core.content.pm;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.Size;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class PackageInfoCompat {

    @RequiresApi(28)
    private static class Api28Impl {
        private Api28Impl() {
        }

        static boolean a(@NonNull PackageManager packageManager, @NonNull String str, @NonNull byte[] bArr, int i) {
            return packageManager.hasSigningCertificate(str, bArr, i);
        }

        static boolean a(@NonNull SigningInfo signingInfo) {
            return signingInfo.hasMultipleSigners();
        }

        @Nullable
        static Signature[] b(@NonNull SigningInfo signingInfo) {
            return signingInfo.getApkContentsSigners();
        }

        @Nullable
        static Signature[] c(@NonNull SigningInfo signingInfo) {
            return signingInfo.getSigningCertificateHistory();
        }
    }

    private PackageInfoCompat() {
    }

    private static boolean byteArrayContains(@NonNull byte[][] bArr, @NonNull byte[] bArr2) {
        for (byte[] bArr3 : bArr) {
            if (Arrays.equals(bArr2, bArr3)) {
                return true;
            }
        }
        return false;
    }

    private static byte[] computeSHA256Digest(byte[] bArr) {
        try {
            return MessageDigest.getInstance("SHA256").digest(bArr);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Device doesn't support SHA256 cert checking", e);
        }
    }

    public static long getLongVersionCode(@NonNull PackageInfo packageInfo) {
        return Build.VERSION.SDK_INT >= 28 ? packageInfo.getLongVersionCode() : packageInfo.versionCode;
    }

    @NonNull
    public static List<Signature> getSignatures(@NonNull PackageManager packageManager, @NonNull String str) throws PackageManager.NameNotFoundException {
        Signature[] signatureArrB;
        if (Build.VERSION.SDK_INT >= 28) {
            SigningInfo signingInfo = packageManager.getPackageInfo(str, 134217728).signingInfo;
            signatureArrB = Api28Impl.a(signingInfo) ? Api28Impl.b(signingInfo) : Api28Impl.c(signingInfo);
        } else {
            signatureArrB = packageManager.getPackageInfo(str, 64).signatures;
        }
        return signatureArrB == null ? Collections.emptyList() : Arrays.asList(signatureArrB);
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x0138 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean hasSignatures(@NonNull PackageManager packageManager, @NonNull String str, @NonNull @Size(min = 1) Map<byte[], Integer> map, boolean z) throws PackageManager.NameNotFoundException {
        if (map.isEmpty()) {
            return false;
        }
        Set<byte[]> setKeySet = map.keySet();
        for (byte[] bArr : setKeySet) {
            if (bArr == null) {
                throw new IllegalArgumentException("Cert byte array cannot be null when verifying " + str);
            }
            Integer num = map.get(bArr);
            if (num == null) {
                throw new IllegalArgumentException("Type must be specified for cert when verifying " + str);
            }
            switch (num.intValue()) {
                case 0:
                case 1:
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported certificate type " + num + " when verifying " + str);
            }
        }
        List<Signature> signatures = getSignatures(packageManager, str);
        if (!z && Build.VERSION.SDK_INT >= 28) {
            for (byte[] bArr2 : setKeySet) {
                if (!Api28Impl.a(packageManager, str, bArr2, map.get(bArr2).intValue())) {
                    return false;
                }
            }
            return true;
        }
        if (signatures.size() != 0 && map.size() <= signatures.size() && (!z || map.size() == signatures.size())) {
            byte[][] bArr3 = null;
            if (map.containsValue(1)) {
                bArr3 = new byte[signatures.size()][];
                for (int i = 0; i < signatures.size(); i++) {
                    bArr3[i] = computeSHA256Digest(signatures.get(i).toByteArray());
                }
            }
            Iterator<byte[]> it = setKeySet.iterator();
            if (it.hasNext()) {
                byte[] next = it.next();
                Integer num2 = map.get(next);
                switch (num2.intValue()) {
                    case 0:
                        return signatures.contains(new Signature(next));
                    case 1:
                        if (!byteArrayContains(bArr3, next)) {
                            return false;
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported certificate type " + num2);
                }
            }
        }
        return false;
    }
}
