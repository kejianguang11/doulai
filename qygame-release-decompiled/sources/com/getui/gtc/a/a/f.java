package com.getui.gtc.a.a;

import android.net.Network;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class f {
    public String a;
    public byte[] b;
    public boolean d;
    public e e;
    public boolean f;
    public boolean g;
    public Network c = null;
    public int h = 30000;
    public int i = 60000;
    public boolean j = true;
    public boolean k = true;
    public boolean l = false;
    public boolean m = true;
    public HashMap<String, String> n = new HashMap<>();

    public f() {
    }

    public f(String str) {
        this.a = str;
        this.n.put("Content-Type", "application/x-www-form-urlencoded");
    }

    public void a() {
    }

    public void a(int i) {
    }

    public void a(Map<String, List<String>> map, byte[] bArr) {
    }
}
