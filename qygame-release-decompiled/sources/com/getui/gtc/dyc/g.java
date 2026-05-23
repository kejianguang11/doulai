package com.getui.gtc.dyc;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.getui.gtc.base.GtcProvider;
import com.getui.gtc.base.http.Call;
import com.getui.gtc.base.http.Response;
import com.getui.gtc.base.util.CommonUtil;
import com.getui.gtc.dyc.b.b;

/* JADX INFO: loaded from: classes.dex */
public class g {

    /* JADX INFO: renamed from: com.getui.gtc.dyc.g$1, reason: invalid class name */
    class AnonymousClass1 implements Call.Callback {
        final /* synthetic */ c a;
        final /* synthetic */ d c;
        final /* synthetic */ b d;

        @Override // com.getui.gtc.base.http.Call.Callback
        public void onFailure(Call call, Exception exc) {
            if (this.a != null) {
                this.a.a(exc);
            }
        }

        @Override // com.getui.gtc.base.http.Call.Callback
        public void onResponse(Call call, Response response) {
            try {
                h hVarA = this.c.a(this.d, response);
                if (this.a != null) {
                    this.a.a(hVarA);
                }
            } catch (Throwable th) {
                com.getui.gtc.dyc.a.a.a.c(th);
                if (this.a != null) {
                    this.a.a(th);
                }
            }
        }
    }

    static class a {
        private static final g a = new g(null);
    }

    public interface c {
        void a(h hVar);

        void a(Throwable th);
    }

    private g() {
        a(GtcProvider.context());
    }

    /* synthetic */ g(AnonymousClass1 anonymousClass1) {
        this();
    }

    static g a() {
        return a.a;
    }

    private void a(Context context) {
        try {
            Bundle bundle = CommonUtil.getAppInfoForSelf(context).metaData;
            if (bundle != null) {
                String string = bundle.getString("DYC_P");
                if (!TextUtils.isEmpty(string)) {
                    d.a = string;
                }
                String string2 = bundle.getString("DYC_K");
                if (TextUtils.isEmpty(string2)) {
                    return;
                }
                d.c = string2;
            }
        } catch (Throwable th) {
            com.getui.gtc.dyc.a.a.a.c(th);
        }
    }

    public h a(b bVar) throws Exception {
        return new d().a(bVar);
    }
}
