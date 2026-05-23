package com.alipay.sdk.authjs;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;
import com.alipay.sdk.authjs.a;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import java.util.Timer;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class d {
    c a;
    Context b;

    public d(Context context, c cVar) {
        this.b = context;
        this.a = cVar;
    }

    private static /* synthetic */ int a(d dVar, a aVar) {
        if (aVar != null && "toast".equals(aVar.k)) {
            JSONObject jSONObject = aVar.m;
            String strOptString = jSONObject.optString(DBHelpTool.RecordEntry.COLUMN_NAME_CONTENT);
            int i = jSONObject.optInt("duration") < 2500 ? 0 : 1;
            Toast.makeText(dVar.b, strOptString, i).show();
            new Timer().schedule(new f(dVar, aVar), i);
        }
        return a.EnumC0002a.a;
    }

    private static void a(Runnable runnable) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            runnable.run();
        } else {
            new Handler(Looper.getMainLooper()).post(runnable);
        }
    }

    private void a(String str) {
        String string;
        try {
            JSONObject jSONObject = new JSONObject(str);
            string = jSONObject.getString(a.e);
            try {
                if (TextUtils.isEmpty(string)) {
                    return;
                }
                JSONObject jSONObject2 = jSONObject.getJSONObject(a.f);
                JSONObject jSONObject3 = jSONObject2 instanceof JSONObject ? jSONObject2 : null;
                String string2 = jSONObject.getString(a.g);
                String string3 = jSONObject.getString(a.d);
                a aVar = new a("call");
                aVar.j = string3;
                aVar.k = string2;
                aVar.m = jSONObject3;
                aVar.i = string;
                a(aVar);
            } catch (Exception unused) {
                if (TextUtils.isEmpty(string)) {
                    return;
                }
                try {
                    a(string, a.EnumC0002a.d);
                } catch (JSONException unused2) {
                }
            }
        } catch (Exception unused3) {
            string = null;
        }
    }

    private int b(a aVar) {
        if (aVar != null && "toast".equals(aVar.k)) {
            JSONObject jSONObject = aVar.m;
            String strOptString = jSONObject.optString(DBHelpTool.RecordEntry.COLUMN_NAME_CONTENT);
            int i = jSONObject.optInt("duration") < 2500 ? 0 : 1;
            Toast.makeText(this.b, strOptString, i).show();
            new Timer().schedule(new f(this, aVar), i);
        }
        return a.EnumC0002a.a;
    }

    private void c(a aVar) {
        JSONObject jSONObject = aVar.m;
        String strOptString = jSONObject.optString(DBHelpTool.RecordEntry.COLUMN_NAME_CONTENT);
        int i = jSONObject.optInt("duration") < 2500 ? 0 : 1;
        Toast.makeText(this.b, strOptString, i).show();
        new Timer().schedule(new f(this, aVar), i);
    }

    public final void a(a aVar) throws JSONException {
        if (TextUtils.isEmpty(aVar.k)) {
            a(aVar.i, a.EnumC0002a.c);
            return;
        }
        e eVar = new e(this, aVar);
        if (Looper.getMainLooper() == Looper.myLooper()) {
            eVar.run();
        } else {
            new Handler(Looper.getMainLooper()).post(eVar);
        }
    }

    public final void a(String str, int i) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("error", i - 1);
        a aVar = new a(a.c);
        aVar.m = jSONObject;
        aVar.i = str;
        this.a.a(aVar);
    }
}
