package com.mobile.auth.b;

import android.os.Build;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class b {
    private String l;
    private int o;
    private long q;
    private long r;
    private long v;
    private StringBuffer t = new StringBuffer();
    private String c = "";
    private String e = "";
    private String n = "";
    private String m = "";
    private String p = "";
    private String a = "1.1";
    private long u = System.currentTimeMillis();
    private String b = a(this.u);
    private String d = "";
    private String f = "";
    private String g = Build.BRAND;
    private String h = Build.MODEL;
    private String i = "Android";
    private String j = Build.VERSION.RELEASE;
    private String k = "SDK-JJ-v4.5.5";
    private String s = "0";
    private String w = "";

    public b(String str) {
        this.l = str;
    }

    public static String a(long j) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA).format(new Date(j));
        } catch (Throwable th) {
            try {
                th.printStackTrace();
                return "";
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                    return null;
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                    return null;
                }
            }
        }
    }

    public b a(int i) {
        try {
            this.o = i;
            return this;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public b a(String str) {
        try {
            this.d = str;
            return this;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public b b(long j) {
        try {
            this.q = j;
            return this;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public b b(String str) {
        try {
            this.e = str;
            return this;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public b c(String str) {
        try {
            this.f = str;
            return this;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public b d(String str) {
        try {
            this.m = str;
            return this;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public b e(String str) {
        try {
            this.n = str;
            return this;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public b f(String str) {
        try {
            this.p = str;
            return this;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public b g(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                this.s = str;
            }
            return this;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public b h(String str) {
        try {
            StringBuffer stringBuffer = this.t;
            stringBuffer.append(str);
            stringBuffer.append("\n");
            return this;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public b i(String str) {
        try {
            this.w = str;
            return this;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public String toString() {
        try {
            this.v = System.currentTimeMillis();
            this.r = this.v - this.u;
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("v", this.a);
            jSONObject.put("t", this.b);
            jSONObject.put("tag", this.c);
            jSONObject.put("ai", this.d);
            jSONObject.put("di", this.e);
            jSONObject.put("ns", this.f);
            jSONObject.put("br", this.g);
            jSONObject.put("ml", this.h);
            jSONObject.put("os", this.i);
            jSONObject.put("ov", this.j);
            jSONObject.put(com.alipay.sdk.sys.a.h, this.k);
            jSONObject.put("ri", this.l);
            jSONObject.put("api", this.m);
            jSONObject.put(com.igexin.push.core.d.d.f, this.n);
            jSONObject.put("rt", this.o);
            jSONObject.put("msg", this.p);
            jSONObject.put(com.igexin.push.core.b.ad, this.q);
            jSONObject.put("tt", this.r);
            jSONObject.put("ot", this.s);
            jSONObject.put("ep", this.t.toString());
            jSONObject.put("aip", this.w);
            return jSONObject.toString();
        } catch (Throwable th) {
            try {
                th.printStackTrace();
                return "";
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                    return null;
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                    return null;
                }
            }
        }
    }
}
