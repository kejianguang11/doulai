package org.cocos2dx.okhttp3.internal.http;

import javax.annotation.Nullable;
import org.cocos2dx.okhttp3.MediaType;
import org.cocos2dx.okhttp3.ResponseBody;
import org.cocos2dx.okio.BufferedSource;

/* JADX INFO: loaded from: classes.dex */
public final class RealResponseBody extends ResponseBody {
    private final long contentLength;

    @Nullable
    private final String contentTypeString;
    private final BufferedSource source;

    public RealResponseBody(@Nullable String str, long j, BufferedSource bufferedSource) {
        this.contentTypeString = str;
        this.contentLength = j;
        this.source = bufferedSource;
    }

    @Override // org.cocos2dx.okhttp3.ResponseBody
    public long contentLength() {
        return this.contentLength;
    }

    @Override // org.cocos2dx.okhttp3.ResponseBody
    public MediaType contentType() {
        if (this.contentTypeString != null) {
            return MediaType.parse(this.contentTypeString);
        }
        return null;
    }

    @Override // org.cocos2dx.okhttp3.ResponseBody
    public BufferedSource source() {
        return this.source;
    }
}
