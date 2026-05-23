package com.ta.utdid2.device;

import android.content.Context;
import android.os.Binder;
import android.provider.Settings;
import android.text.TextUtils;
import com.igexin.push.core.b.n;
import com.ta.utdid2.a.a.f;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class c {

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private com.ta.utdid2.b.a.c f15a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private d f16a;
    private com.ta.utdid2.b.a.c b;
    private String h;
    private String i;
    private Context mContext;
    private static final Object e = new Object();
    private static c a = null;
    private static final String j = ".UTSystemConfig" + File.separator + "Global";
    private String g = null;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private Pattern f17b = Pattern.compile("[^0-9a-zA-Z=/+]+");

    private c(Context context) {
        this.mContext = null;
        this.f16a = null;
        this.h = "xx_utdid_key";
        this.i = "xx_utdid_domain";
        this.f15a = null;
        this.b = null;
        this.mContext = context;
        this.b = new com.ta.utdid2.b.a.c(context, j, "Alvin2", false, true);
        this.f15a = new com.ta.utdid2.b.a.c(context, ".DataStorage", "ContextData", false, true);
        this.f16a = new d();
        this.h = String.format("K_%d", Integer.valueOf(f.hashCode(this.h)));
        this.i = String.format("D_%d", Integer.valueOf(f.hashCode(this.i)));
    }

    public static c a(Context context) {
        if (context != null && a == null) {
            synchronized (e) {
                if (a == null) {
                    c cVar = new c(context);
                    a = cVar;
                    cVar.m15c();
                }
            }
        }
        return a;
    }

    private boolean a(String str) {
        if (str != null) {
            if (str.endsWith("\n")) {
                str = str.substring(0, str.length() - 1);
            }
            if (24 == str.length() && !this.f17b.matcher(str).find()) {
                return true;
            }
        }
        return false;
    }

    public static String b(byte[] bArr) throws Exception {
        byte[] bArr2 = {69, 114, 116, -33, 125, -54, -31, 86, -11, n.l, -78, -96, -17, -99, 64, 23, -95, -126, -82, -64, 113, 116, -16, -103, 49, -30, 9, -39, 33, -80, -68, -78, -117, 53, 30, -122, 64, -104, 74, -49, 106, 85, -38, -93};
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(com.ta.utdid2.a.a.e.a(bArr2), mac.getAlgorithm()));
        return com.ta.utdid2.a.a.b.encodeToString(mac.doFinal(bArr), 2);
    }

    private byte[] b() throws Exception {
        String string;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        int iNextInt = new Random().nextInt();
        byte[] bytes = com.ta.utdid2.a.a.c.getBytes(iCurrentTimeMillis);
        byte[] bytes2 = com.ta.utdid2.a.a.c.getBytes(iNextInt);
        byteArrayOutputStream.write(bytes, 0, 4);
        byteArrayOutputStream.write(bytes2, 0, 4);
        byteArrayOutputStream.write(3);
        byteArrayOutputStream.write(0);
        try {
            string = com.ta.utdid2.a.a.d.getImei(this.mContext);
        } catch (Exception unused) {
            StringBuilder sb = new StringBuilder();
            sb.append(new Random().nextInt());
            string = sb.toString();
        }
        byteArrayOutputStream.write(com.ta.utdid2.a.a.c.getBytes(f.hashCode(string)), 0, 4);
        byteArrayOutputStream.write(com.ta.utdid2.a.a.c.getBytes(f.hashCode(b(byteArrayOutputStream.toByteArray()))));
        return byteArrayOutputStream.toByteArray();
    }

    private String c() {
        if (this.b == null) {
            return null;
        }
        String string = this.b.getString("UTDID2");
        if (f.isEmpty(string) || this.f16a.c(string) == null) {
            return null;
        }
        return string;
    }

    /* JADX INFO: renamed from: c, reason: collision with other method in class */
    private void m15c() {
        if (this.b != null) {
            if (f.isEmpty(this.b.getString("UTDID2"))) {
                String string = this.b.getString("UTDID");
                if (!f.isEmpty(string)) {
                    d(string);
                }
            }
            boolean z = false;
            if (!f.isEmpty(this.b.getString("DID"))) {
                this.b.remove("DID");
                z = true;
            }
            if (!f.isEmpty(this.b.getString("EI"))) {
                this.b.remove("EI");
                z = true;
            }
            if (!f.isEmpty(this.b.getString("SI"))) {
                this.b.remove("SI");
                z = true;
            }
            if (z) {
                this.b.commit();
            }
        }
    }

    private void d(String str) {
        if (a(str)) {
            if (str.endsWith("\n")) {
                str = str.substring(0, str.length() - 1);
            }
            if (str.length() != 24 || this.b == null) {
                return;
            }
            this.b.putString("UTDID2", str);
            this.b.commit();
        }
    }

    private void e(String str) {
        if (str == null || this.f15a == null || str.equals(this.f15a.getString(this.h))) {
            return;
        }
        this.f15a.putString(this.h, str);
        this.f15a.commit();
    }

    private boolean e() {
        return this.mContext.checkPermission("android.permission.WRITE_SETTINGS", Binder.getCallingPid(), Binder.getCallingUid()) == 0;
    }

    private void f(String str) {
        if (e() && a(str)) {
            if (str.endsWith("\n")) {
                str = str.substring(0, str.length() - 1);
            }
            if (24 == str.length()) {
                String string = null;
                try {
                    string = Settings.System.getString(this.mContext.getContentResolver(), "mqBRboGZkQPcAkyk");
                } catch (Exception unused) {
                }
                if (a(string)) {
                    return;
                }
                try {
                    Settings.System.putString(this.mContext.getContentResolver(), "mqBRboGZkQPcAkyk", str);
                } catch (Exception unused2) {
                }
            }
        }
    }

    private void g(String str) {
        String string;
        try {
            string = Settings.System.getString(this.mContext.getContentResolver(), "dxCRMxhQkdGePGnp");
        } catch (Exception unused) {
            string = null;
        }
        if (str.equals(string)) {
            return;
        }
        try {
            Settings.System.putString(this.mContext.getContentResolver(), "dxCRMxhQkdGePGnp", str);
        } catch (Exception unused2) {
        }
    }

    private void h(String str) {
        if (!e() || str == null) {
            return;
        }
        g(str);
    }

    public synchronized String d() {
        this.g = m16e();
        if (!TextUtils.isEmpty(this.g)) {
            return this.g;
        }
        try {
            byte[] bArrB = b();
            if (bArrB != null) {
                this.g = com.ta.utdid2.a.a.b.encodeToString(bArrB, 2);
                d(this.g);
                String strC = this.f16a.c(bArrB);
                if (strC != null) {
                    h(strC);
                    e(strC);
                }
                return this.g;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return null;
    }

    /* JADX INFO: renamed from: e, reason: collision with other method in class */
    public synchronized String m16e() {
        String string;
        String string2 = "";
        try {
            string2 = Settings.System.getString(this.mContext.getContentResolver(), "mqBRboGZkQPcAkyk");
        } catch (Exception unused) {
        }
        if (a(string2)) {
            return string2;
        }
        e eVar = new e();
        boolean z = false;
        try {
            string = Settings.System.getString(this.mContext.getContentResolver(), "dxCRMxhQkdGePGnp");
        } catch (Exception unused2) {
            string = null;
        }
        if (f.isEmpty(string)) {
            z = true;
        } else {
            String strE = eVar.e(string);
            if (a(strE)) {
                f(strE);
                return strE;
            }
            String strD = eVar.d(string);
            if (a(strD)) {
                String strC = this.f16a.c(strD);
                if (!f.isEmpty(strC)) {
                    h(strC);
                    try {
                        string = Settings.System.getString(this.mContext.getContentResolver(), "dxCRMxhQkdGePGnp");
                    } catch (Exception unused3) {
                    }
                }
            }
            String strD2 = this.f16a.d(string);
            if (a(strD2)) {
                this.g = strD2;
                d(strD2);
                e(string);
                f(this.g);
                return this.g;
            }
        }
        String strC2 = c();
        if (a(strC2)) {
            String strC3 = this.f16a.c(strC2);
            if (z) {
                h(strC3);
            }
            f(strC2);
            e(strC3);
            this.g = strC2;
            return strC2;
        }
        String string3 = this.f15a.getString(this.h);
        if (!f.isEmpty(string3)) {
            String strD3 = eVar.d(string3);
            if (!a(strD3)) {
                strD3 = this.f16a.d(string3);
            }
            if (a(strD3)) {
                String strC4 = this.f16a.c(strD3);
                if (!f.isEmpty(strD3)) {
                    this.g = strD3;
                    if (z) {
                        h(strC4);
                    }
                    d(this.g);
                    return this.g;
                }
            }
        }
        return null;
    }

    public synchronized String getValue() {
        if (this.g != null) {
            return this.g;
        }
        return d();
    }
}
