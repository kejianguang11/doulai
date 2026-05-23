package com.loc;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.text.TextUtils;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class ev {
    private File b;
    private Handler d;
    private String e;
    private boolean f;
    private LinkedList<eu> a = new LinkedList<>();
    private boolean c = false;
    private Runnable g = new Runnable() { // from class: com.loc.ev.1
        @Override // java.lang.Runnable
        public final void run() throws Throwable {
            if (ev.this.c) {
                return;
            }
            if (ev.this.f) {
                ev.this.b();
                ev.d(ev.this);
            }
            if (ev.this.d != null) {
                ev.this.d.postDelayed(ev.this.g, 60000L);
            }
        }
    };

    public ev(Context context, Handler handler) {
        this.e = null;
        this.d = handler;
        String path = context.getFilesDir().getPath();
        if (this.e == null) {
            this.e = fq.l(context);
        }
        try {
            this.b = new File(path, "hisloc");
        } catch (Throwable th) {
            eb.a(th);
        }
        a();
        if (this.d != null) {
            this.d.removeCallbacks(this.g);
            this.d.postDelayed(this.g, 60000L);
        }
    }

    private void a() {
        if (this.a == null || this.a.size() <= 0) {
            Iterator<String> it = fq.a(this.b).iterator();
            while (it.hasNext()) {
                try {
                    String str = new String(ey.b(p.b(it.next()), this.e), com.alipay.sdk.sys.a.m);
                    eu euVar = new eu();
                    euVar.a(new JSONObject(str));
                    this.a.add(euVar);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() throws Throwable {
        StringBuilder sb = new StringBuilder();
        Iterator<eu> it = this.a.iterator();
        while (it.hasNext()) {
            try {
                sb.append(p.b(ey.a(it.next().a().getBytes(com.alipay.sdk.sys.a.m), this.e)) + "\n");
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

    private static boolean b(ArrayList<es> arrayList, ArrayList<dy> arrayList2) {
        return arrayList == null || arrayList.size() <= 0 || arrayList2 == null || arrayList2.size() <= 0 || (((long) arrayList.size()) < 4 && ((long) arrayList2.size()) < 20);
    }

    static /* synthetic */ boolean d(ev evVar) {
        evVar.f = false;
        return false;
    }

    public final List<eu> a(ArrayList<es> arrayList, ArrayList<dy> arrayList2) {
        if (!b(arrayList, arrayList2)) {
            return null;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        ArrayList arrayList3 = new ArrayList();
        int i = 0;
        for (eu euVar : this.a) {
            if (jCurrentTimeMillis - euVar.d < 21600000000L) {
                arrayList3.add(euVar);
                i++;
            }
            if (i == 10) {
                break;
            }
        }
        return arrayList3;
    }

    public final void a(eu euVar) {
        eu euVar2 = null;
        int i = 0;
        eu euVar3 = null;
        for (eu euVar4 : this.a) {
            if (euVar4.a == 1) {
                if (euVar3 == null) {
                    euVar3 = euVar4;
                }
                i++;
                euVar2 = euVar4;
            }
        }
        if (euVar2 != null) {
            new Location("gps");
            if (euVar.d - euVar2.d < 20000 && fq.a(new double[]{euVar.b, euVar.c, euVar2.b, euVar2.c}) < 20.0f) {
                return;
            }
        }
        if (i >= 5) {
            this.a.remove(euVar3);
        }
        if (this.a.size() >= 10) {
            this.a.removeFirst();
        }
        this.a.add(euVar);
        this.f = true;
    }

    public final void a(boolean z) {
        if (!z) {
            this.g.run();
        }
        if (this.d != null) {
            this.d.removeCallbacks(this.g);
        }
        this.c = true;
    }

    public final void b(eu euVar) {
        if (this.a.size() > 0) {
            if (euVar.a != 6 && euVar.a != 5) {
                if (this.a.contains(euVar)) {
                    return;
                }
                if (this.a.size() >= 10) {
                    this.a.removeFirst();
                }
                this.a.add(euVar);
                this.f = true;
                return;
            }
            eu last = this.a.getLast();
            if (last.c == euVar.c && last.b == euVar.b && last.e == euVar.e) {
                return;
            }
            if (this.a.size() >= 10) {
                this.a.removeFirst();
            }
            this.a.add(euVar);
            this.f = true;
        }
    }
}
