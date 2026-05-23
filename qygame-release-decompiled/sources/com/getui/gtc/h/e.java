package com.getui.gtc.h;

import android.util.Base64;
import com.getui.gtc.base.crypt.CryptTools;
import com.getui.gtc.base.http.Call;
import com.getui.gtc.base.http.Request;
import com.getui.gtc.base.http.Response;
import com.mobile.auth.gatewayauth.Constant;
import javax.crypto.spec.IvParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public final class e {
    private static int a;

    public interface a {
        void a(String str);
    }

    public static void a(final String str, final String str2, final a aVar) {
        d.a.newCall(new Request.Builder().url(str).method("GET").logFlags(1).tag("fetch servers").build()).enqueue(new Call.Callback() { // from class: com.getui.gtc.h.e.1
            @Override // com.getui.gtc.base.http.Call.Callback
            public final void onFailure(Call call, Exception exc) {
                com.getui.gtc.i.c.a.b("Failed! = " + call.request().url());
                com.getui.gtc.i.c.a.b(exc);
                if (e.a < 3) {
                    e.b();
                    try {
                        Thread.sleep(e.a * Constant.DEFAULT_TIMEOUT);
                    } catch (InterruptedException unused) {
                    }
                    e.a(str, str2, aVar);
                }
            }

            @Override // com.getui.gtc.base.http.Call.Callback
            public final void onResponse(Call call, Response response) {
                e.c();
                byte[] body = response.getBody();
                try {
                    byte[] bArrDecode = Base64.decode(str2, 2);
                    String str3 = new String(CryptTools.decrypt("AES/CFB/NOPADDING", CryptTools.wrapperKey("AES", bArrDecode), new IvParameterSpec(CryptTools.digest("MD5", bArrDecode)), body));
                    com.getui.gtc.i.c.a.a("fetch servers from " + call.request().url() + " :" + str3);
                    if (aVar != null) {
                        aVar.a(str3);
                    }
                } catch (Throwable th) {
                    com.getui.gtc.i.c.a.b(th);
                }
            }
        });
    }

    static /* synthetic */ int b() {
        int i = a;
        a = i + 1;
        return i;
    }

    static /* synthetic */ int c() {
        a = 0;
        return 0;
    }
}
