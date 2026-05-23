package org.cocos2dx.okio;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.Nullable;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public final class HashingSink extends ForwardingSink {

    @Nullable
    private final Mac mac;

    @Nullable
    private final MessageDigest messageDigest;

    private HashingSink(Sink sink, String str) {
        super(sink);
        try {
            this.messageDigest = MessageDigest.getInstance(str);
            this.mac = null;
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }

    private HashingSink(Sink sink, ByteString byteString, String str) {
        super(sink);
        try {
            this.mac = Mac.getInstance(str);
            this.mac.init(new SecretKeySpec(byteString.toByteArray(), str));
            this.messageDigest = null;
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }

    public static HashingSink hmacSha1(Sink sink, ByteString byteString) {
        return new HashingSink(sink, byteString, "HmacSHA1");
    }

    public static HashingSink hmacSha256(Sink sink, ByteString byteString) {
        return new HashingSink(sink, byteString, "HmacSHA256");
    }

    public static HashingSink hmacSha512(Sink sink, ByteString byteString) {
        return new HashingSink(sink, byteString, "HmacSHA512");
    }

    public static HashingSink md5(Sink sink) {
        return new HashingSink(sink, "MD5");
    }

    public static HashingSink sha1(Sink sink) {
        return new HashingSink(sink, "SHA-1");
    }

    public static HashingSink sha256(Sink sink) {
        return new HashingSink(sink, "SHA-256");
    }

    public static HashingSink sha512(Sink sink) {
        return new HashingSink(sink, "SHA-512");
    }

    public final ByteString hash() {
        return ByteString.of(this.messageDigest != null ? this.messageDigest.digest() : this.mac.doFinal());
    }

    @Override // org.cocos2dx.okio.ForwardingSink, org.cocos2dx.okio.Sink
    public void write(Buffer buffer, long j) throws IOException {
        Util.checkOffsetAndCount(buffer.size, 0L, j);
        Segment segment = buffer.head;
        long j2 = 0;
        while (j2 < j) {
            int iMin = (int) Math.min(j - j2, segment.c - segment.b);
            if (this.messageDigest != null) {
                this.messageDigest.update(segment.a, segment.b, iMin);
            } else {
                this.mac.update(segment.a, segment.b, iMin);
            }
            j2 += (long) iMin;
            segment = segment.f;
        }
        super.write(buffer, j);
    }
}
