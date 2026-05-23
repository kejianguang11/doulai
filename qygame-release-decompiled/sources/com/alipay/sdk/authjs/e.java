package com.alipay.sdk.authjs;

import android.widget.Toast;
import com.alipay.sdk.authjs.a;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import java.util.Timer;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
final class e implements Runnable {
    final /* synthetic */ a a;
    final /* synthetic */ d b;

    e(d dVar, a aVar) {
        this.b = dVar;
        this.a = aVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        d dVar = this.b;
        a aVar = this.a;
        if (aVar != null && "toast".equals(aVar.k)) {
            JSONObject jSONObject = aVar.m;
            String strOptString = jSONObject.optString(DBHelpTool.RecordEntry.COLUMN_NAME_CONTENT);
            int i = jSONObject.optInt("duration") < 2500 ? 0 : 1;
            Toast.makeText(dVar.b, strOptString, i).show();
            new Timer().schedule(new f(dVar, aVar), i);
        }
        int i2 = a.EnumC0002a.a;
        if (i2 != a.EnumC0002a.a) {
            try {
                this.b.a(this.a.i, i2);
            } catch (JSONException unused) {
            }
        }
    }
}
