package com.loc;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
public abstract class ep<T> {
    public String a;
    private File b;
    private Handler e;
    private String f;
    private boolean g;
    private boolean c = false;
    private Map<String, a> d = new ConcurrentHashMap();
    private Runnable h = new Runnable() { // from class: com.loc.ep.2
        @Override // java.lang.Runnable
        public final void run() throws Throwable {
            if (ep.this.c) {
                if (ep.this.g) {
                    ep.this.e();
                    ep.e(ep.this);
                }
                if (ep.this.e != null) {
                    ep.this.e.postDelayed(ep.this.h, 60000L);
                }
            }
        }
    };

    static class a {
        int a;
        long b;
        long c;

        public a(int i, long j, long j2) {
            this.a = i;
            this.b = j;
            this.c = j2;
        }
    }

    public ep(Context context, String str, Handler handler) {
        this.f = null;
        if (context == null) {
            return;
        }
        this.e = handler;
        this.a = TextUtils.isEmpty(str) ? "unknow" : str;
        this.f = fq.l(context);
        try {
            this.b = new File(context.getFilesDir().getPath(), this.a);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        d();
    }

    public static int a(long j, long j2) {
        if (j < j2) {
            return -1;
        }
        return j == j2 ? 0 : 1;
    }

    private void b(T t, long j) {
        if (t == null || d(t) < 0) {
            return;
        }
        String strB = b(t);
        a aVar = this.d.get(strB);
        if (aVar == null) {
            a(t, j);
            this.d.put(strB, new a(c(t), d(t), j));
            this.g = true;
            return;
        }
        aVar.c = j;
        if (aVar.a == c(t)) {
            a(t, aVar.b);
            return;
        }
        a(t, j);
        aVar.a = c(t);
        aVar.b = d(t);
        this.g = true;
    }

    private void d() {
        try {
            StringBuilder sb = new StringBuilder("restore from：\n");
            Iterator<String> it = fq.a(this.b).iterator();
            while (it.hasNext()) {
                try {
                    String str = new String(ey.b(p.b(it.next()), this.f), com.alipay.sdk.sys.a.m);
                    sb.append(str);
                    sb.append("\n");
                    String[] strArrSplit = str.split(com.igexin.push.core.b.an);
                    this.d.put(strArrSplit[0], new a(Integer.parseInt(strArrSplit[1]), Long.parseLong(strArrSplit[2]), strArrSplit.length >= 4 ? Long.parseLong(strArrSplit[3]) : fq.b()));
                } catch (Throwable th) {
                    if (this.b.exists()) {
                        this.b.delete();
                    }
                    th.printStackTrace();
                }
            }
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() throws Throwable {
        if (c() > 0) {
            this.d.size();
            if (b() > 0) {
                long jB = fq.b();
                Iterator<Map.Entry<String, a>> it = this.d.entrySet().iterator();
                while (it.hasNext()) {
                    if (jB - this.d.get(it.next().getKey()).c > b()) {
                        it.remove();
                    }
                }
            }
            if (this.d.size() > c()) {
                ArrayList arrayList = new ArrayList(this.d.keySet());
                Collections.sort(arrayList, new Comparator<String>() { // from class: com.loc.ep.1
                    /* JADX INFO: Access modifiers changed from: private */
                    @Override // java.util.Comparator
                    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
                    public int compare(String str, String str2) {
                        return ep.a(((a) ep.this.d.get(str2)).c, ((a) ep.this.d.get(str)).c);
                    }
                });
                for (int iC = (int) c(); iC < arrayList.size(); iC++) {
                    this.d.remove(arrayList.get(iC));
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, a> entry : this.d.entrySet()) {
            try {
                sb.append(p.b(ey.a((entry.getKey() + com.igexin.push.core.b.an + entry.getValue().a + com.igexin.push.core.b.an + entry.getValue().b + com.igexin.push.core.b.an + entry.getValue().c).getBytes(com.alipay.sdk.sys.a.m), this.f)) + "\n");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String string = sb.toString();
        if (TextUtils.isEmpty(string)) {
            return;
        }
        fq.a(this.b, string);
    }

    static /* synthetic */ boolean e(ep epVar) {
        epVar.g = false;
        return false;
    }

    public final void a() {
        if (!this.c && this.e != null) {
            this.e.removeCallbacks(this.h);
            this.e.postDelayed(this.h, 60000L);
        }
        this.c = true;
    }

    public final void a(T t) {
        b(t, fq.b());
    }

    abstract void a(T t, long j);

    public final void a(List<T> list) {
        long jB = fq.b();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            b(it.next(), jB);
        }
        if (this.d.size() >= list.size()) {
            this.g = true;
        }
        if (this.d.size() > 16384 || c() <= 0) {
            this.d.clear();
            for (T t : list) {
                this.d.put(b(t), new a(c(t), d(t), jB));
            }
        }
    }

    public final void a(boolean z) {
        if (this.e != null) {
            this.e.removeCallbacks(this.h);
        }
        if (!z) {
            this.h.run();
        }
        this.c = false;
    }

    abstract long b();

    public abstract String b(T t);

    abstract int c(T t);

    abstract long c();

    abstract long d(T t);

    public final long e(T t) {
        return (fq.b() - d(t)) / 1000;
    }
}
