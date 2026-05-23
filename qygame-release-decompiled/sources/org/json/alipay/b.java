package org.json.alipay;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class b {
    public static final Object a = new a(0);
    private Map b;

    private static final class a {
        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }

        protected final Object clone() {
            return this;
        }

        public final boolean equals(Object obj) {
            return obj == null || obj == this;
        }

        public final String toString() {
            return "null";
        }
    }

    public b() {
        this.b = new HashMap();
    }

    public b(String str) {
        this(new c(str));
    }

    public b(Map map) {
        this.b = map == null ? new HashMap() : map;
    }

    public b(c cVar) throws JSONException {
        this();
        if (cVar.c() != '{') {
            throw cVar.a("A JSONObject text must begin with '{'");
        }
        while (true) {
            char c = cVar.c();
            if (c == 0) {
                throw cVar.a("A JSONObject text must end with '}'");
            }
            if (c == '}') {
                return;
            }
            cVar.a();
            String string = cVar.d().toString();
            char c2 = cVar.c();
            if (c2 == '=') {
                if (cVar.b() != '>') {
                    cVar.a();
                }
            } else if (c2 != ':') {
                throw cVar.a("Expected a ':' after a key");
            }
            Object objD = cVar.d();
            if (string == null) {
                throw new JSONException("Null key.");
            }
            if (objD != null) {
                b(objD);
                this.b.put(string, objD);
            } else {
                this.b.remove(string);
            }
            char c3 = cVar.c();
            if (c3 != ',' && c3 != ';') {
                if (c3 != '}') {
                    throw cVar.a("Expected a ',' or '}'");
                }
                return;
            } else if (cVar.c() == '}') {
                return;
            } else {
                cVar.a();
            }
        }
    }

    static String a(Object obj) throws JSONException {
        if (obj == null || obj.equals(null)) {
            return "null";
        }
        if (!(obj instanceof Number)) {
            return ((obj instanceof Boolean) || (obj instanceof b) || (obj instanceof org.json.alipay.a)) ? obj.toString() : obj instanceof Map ? new b((Map) obj).toString() : obj instanceof Collection ? new org.json.alipay.a((Collection) obj).toString() : obj.getClass().isArray() ? new org.json.alipay.a(obj).toString() : c(obj.toString());
        }
        Number number = (Number) obj;
        if (number == null) {
            throw new JSONException("Null pointer");
        }
        b(number);
        String string = number.toString();
        if (string.indexOf(46) <= 0 || string.indexOf(101) >= 0 || string.indexOf(69) >= 0) {
            return string;
        }
        while (string.endsWith("0")) {
            string = string.substring(0, string.length() - 1);
        }
        return string.endsWith(".") ? string.substring(0, string.length() - 1) : string;
    }

    private static void b(Object obj) throws JSONException {
        if (obj != null) {
            if (obj instanceof Double) {
                Double d = (Double) obj;
                if (d.isInfinite() || d.isNaN()) {
                    throw new JSONException("JSON does not allow non-finite numbers.");
                }
                return;
            }
            if (obj instanceof Float) {
                Float f = (Float) obj;
                if (f.isInfinite() || f.isNaN()) {
                    throw new JSONException("JSON does not allow non-finite numbers.");
                }
            }
        }
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:13:0x002c. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:14:0x002f. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String c(String str) {
        String str2;
        if (str == null || str.length() == 0) {
            return "\"\"";
        }
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer(length + 4);
        stringBuffer.append('\"');
        int i = 0;
        char c = 0;
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\"') {
                stringBuffer.append('\\');
                stringBuffer.append(cCharAt);
            } else if (cCharAt != '/') {
                if (cCharAt != '\\') {
                    switch (cCharAt) {
                        case '\b':
                            str2 = "\\b";
                            stringBuffer.append(str2);
                            break;
                        case '\t':
                            str2 = "\\t";
                            stringBuffer.append(str2);
                            break;
                        case '\n':
                            str2 = "\\n";
                            stringBuffer.append(str2);
                            break;
                        default:
                            switch (cCharAt) {
                                case '\f':
                                    str2 = "\\f";
                                    stringBuffer.append(str2);
                                    break;
                                case '\r':
                                    str2 = "\\r";
                                    stringBuffer.append(str2);
                                    break;
                                default:
                                    if (cCharAt < ' ' || ((cCharAt >= 128 && cCharAt < 160) || (cCharAt >= 8192 && cCharAt < 8448))) {
                                        str2 = "\\u" + ("000" + Integer.toHexString(cCharAt)).substring(r4.length() - 4);
                                        stringBuffer.append(str2);
                                    }
                                    break;
                            }
                            break;
                    }
                }
                stringBuffer.append(cCharAt);
            } else {
                if (c == '<') {
                }
                stringBuffer.append(cCharAt);
            }
            i++;
            c = cCharAt;
        }
        stringBuffer.append('\"');
        return stringBuffer.toString();
    }

    public final Object a(String str) throws JSONException {
        Object obj = str == null ? null : this.b.get(str);
        if (obj != null) {
            return obj;
        }
        throw new JSONException("JSONObject[" + c(str) + "] not found.");
    }

    public final Iterator a() {
        return this.b.keySet().iterator();
    }

    public final boolean b(String str) {
        return this.b.containsKey(str);
    }

    public String toString() {
        try {
            Iterator itA = a();
            StringBuffer stringBuffer = new StringBuffer("{");
            while (itA.hasNext()) {
                if (stringBuffer.length() > 1) {
                    stringBuffer.append(',');
                }
                Object next = itA.next();
                stringBuffer.append(c(next.toString()));
                stringBuffer.append(':');
                stringBuffer.append(a(this.b.get(next)));
            }
            stringBuffer.append('}');
            return stringBuffer.toString();
        } catch (Exception unused) {
            return null;
        }
    }
}
