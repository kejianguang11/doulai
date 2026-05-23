package com.zx.a.I8b7;

import android.text.TextUtils;
import com.zx.sdk.api.ZXIDChangedListener;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
public class i3 implements Runnable {
    public final /* synthetic */ String a;
    public final /* synthetic */ ZXIDChangedListener b;

    public i3(b3 b3Var, String str, ZXIDChangedListener zXIDChangedListener) {
        this.a = str;
        this.b = zXIDChangedListener;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            l3 l3VarA = b3.a();
            String str = this.a;
            ZXIDChangedListener zXIDChangedListener = this.b;
            u2 u2Var = l3VarA.c;
            u2Var.getClass();
            if (TextUtils.isEmpty(str)) {
                return;
            }
            LinkedList<ZXIDChangedListener> linkedList = u2Var.a.get(str);
            if (linkedList == null) {
                linkedList = new LinkedList<>();
            }
            linkedList.add(zXIDChangedListener);
            u2Var.a.put(str, linkedList);
        } catch (Throwable th) {
            k3.a(th, j3.a("ZXManager.allowPermissionDialog failed: "));
        }
    }
}
