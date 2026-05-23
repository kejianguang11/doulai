package org.cocos2dx.okhttp3;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import javax.annotation.Nullable;
import org.cocos2dx.okhttp3.internal.Util;
import org.cocos2dx.okio.BufferedSink;
import org.cocos2dx.okio.ByteString;
import org.cocos2dx.okio.Okio;
import org.cocos2dx.okio.Source;

/* JADX INFO: loaded from: classes.dex */
public abstract class RequestBody {
    public static RequestBody create(@Nullable final MediaType mediaType, final File file) {
        if (file != null) {
            return new RequestBody() { // from class: org.cocos2dx.okhttp3.RequestBody.3
                @Override // org.cocos2dx.okhttp3.RequestBody
                public long contentLength() {
                    return file.length();
                }

                @Override // org.cocos2dx.okhttp3.RequestBody
                @Nullable
                public MediaType contentType() {
                    return mediaType;
                }

                @Override // org.cocos2dx.okhttp3.RequestBody
                public void writeTo(BufferedSink bufferedSink) throws Throwable {
                    Source source;
                    Source source2 = null;
                    try {
                        source = Okio.source(file);
                    } catch (Throwable th) {
                        th = th;
                    }
                    try {
                        bufferedSink.writeAll(source);
                        Util.closeQuietly(source);
                    } catch (Throwable th2) {
                        th = th2;
                        source2 = source;
                        Util.closeQuietly(source2);
                        throw th;
                    }
                }
            };
        }
        throw new NullPointerException("file == null");
    }

    public static RequestBody create(@Nullable MediaType mediaType, String str) {
        Charset charset = Util.UTF_8;
        if (mediaType != null && (charset = mediaType.charset()) == null) {
            charset = Util.UTF_8;
            mediaType = MediaType.parse(mediaType + "; charset=utf-8");
        }
        return create(mediaType, str.getBytes(charset));
    }

    public static RequestBody create(@Nullable final MediaType mediaType, final ByteString byteString) {
        return new RequestBody() { // from class: org.cocos2dx.okhttp3.RequestBody.1
            @Override // org.cocos2dx.okhttp3.RequestBody
            public long contentLength() throws IOException {
                return byteString.size();
            }

            @Override // org.cocos2dx.okhttp3.RequestBody
            @Nullable
            public MediaType contentType() {
                return mediaType;
            }

            @Override // org.cocos2dx.okhttp3.RequestBody
            public void writeTo(BufferedSink bufferedSink) throws IOException {
                bufferedSink.write(byteString);
            }
        };
    }

    public static RequestBody create(@Nullable MediaType mediaType, byte[] bArr) {
        return create(mediaType, bArr, 0, bArr.length);
    }

    public static RequestBody create(@Nullable final MediaType mediaType, final byte[] bArr, final int i, final int i2) {
        if (bArr == null) {
            throw new NullPointerException("content == null");
        }
        Util.checkOffsetAndCount(bArr.length, i, i2);
        return new RequestBody() { // from class: org.cocos2dx.okhttp3.RequestBody.2
            @Override // org.cocos2dx.okhttp3.RequestBody
            public long contentLength() {
                return i2;
            }

            @Override // org.cocos2dx.okhttp3.RequestBody
            @Nullable
            public MediaType contentType() {
                return mediaType;
            }

            @Override // org.cocos2dx.okhttp3.RequestBody
            public void writeTo(BufferedSink bufferedSink) throws IOException {
                bufferedSink.write(bArr, i, i2);
            }
        };
    }

    public long contentLength() throws IOException {
        return -1L;
    }

    @Nullable
    public abstract MediaType contentType();

    public abstract void writeTo(BufferedSink bufferedSink) throws IOException;
}
