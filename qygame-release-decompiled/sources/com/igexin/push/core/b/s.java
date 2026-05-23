package com.igexin.push.core.b;

import com.igexin.push.core.d;
import com.igexin.push.extension.mod.BaseActionBean;

/* JADX INFO: loaded from: classes.dex */
public final class s extends BaseActionBean {
    public String a;
    public boolean b;
    public boolean c;
    public String d;

    private void a(String str) {
        this.a = str;
    }

    private String b() {
        return this.a;
    }

    private void b(String str) {
        this.d = str;
    }

    private boolean c() {
        return this.b;
    }

    private void d() {
        this.b = true;
    }

    private boolean e() {
        return this.c;
    }

    private void f() {
        this.c = true;
    }

    private String g() {
        return this.d;
    }

    public final String a() {
        StringBuilder sb;
        String str;
        StringBuilder sb2;
        String str2;
        String string = this.a;
        if (this.b) {
            if (string.indexOf("?") > 0) {
                sb2 = new StringBuilder();
                sb2.append(string);
                str2 = "&cid=";
            } else {
                sb2 = new StringBuilder();
                sb2.append(string);
                str2 = "?cid=";
            }
            sb2.append(str2);
            sb2.append(com.igexin.push.core.e.A);
            string = sb2.toString();
        }
        if (!this.c) {
            return string;
        }
        com.igexin.push.core.d unused = d.a.a;
        String strH = com.igexin.push.core.d.h();
        if (strH == null) {
            return string;
        }
        if (string.indexOf("?") > 0) {
            sb = new StringBuilder();
            sb.append(string);
            str = "&nettype=";
        } else {
            sb = new StringBuilder();
            sb.append(string);
            str = "?nettype=";
        }
        sb.append(str);
        sb.append(strH);
        return sb.toString();
    }
}
