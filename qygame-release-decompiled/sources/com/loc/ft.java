package com.loc;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/* JADX INFO: loaded from: classes.dex */
public class ft {
    private static final ThreadLocal<CharsetDecoder> b = new ThreadLocal<CharsetDecoder>() { // from class: com.loc.ft.1
        private static CharsetDecoder a() {
            return Charset.forName(com.alipay.sdk.sys.a.m).newDecoder();
        }

        @Override // java.lang.ThreadLocal
        protected final /* synthetic */ CharsetDecoder initialValue() {
            return a();
        }
    };
    public static final ThreadLocal<Charset> a = new ThreadLocal<Charset>() { // from class: com.loc.ft.2
        private static Charset a() {
            return Charset.forName(com.alipay.sdk.sys.a.m);
        }

        @Override // java.lang.ThreadLocal
        protected final /* synthetic */ Charset initialValue() {
            return a();
        }
    };
    private static final ThreadLocal<CharBuffer> c = new ThreadLocal<>();
}
