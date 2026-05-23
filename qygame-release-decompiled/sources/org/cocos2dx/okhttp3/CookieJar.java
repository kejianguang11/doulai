package org.cocos2dx.okhttp3;

import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface CookieJar {
    public static final CookieJar NO_COOKIES = new CookieJar() { // from class: org.cocos2dx.okhttp3.CookieJar.1
        @Override // org.cocos2dx.okhttp3.CookieJar
        public List<Cookie> loadForRequest(HttpUrl httpUrl) {
            return Collections.emptyList();
        }

        @Override // org.cocos2dx.okhttp3.CookieJar
        public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
        }
    };

    List<Cookie> loadForRequest(HttpUrl httpUrl);

    void saveFromResponse(HttpUrl httpUrl, List<Cookie> list);
}
