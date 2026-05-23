package com.igexin.c.a.c.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.text.TextUtils;
import com.igexin.push.core.ServiceManager;
import com.igexin.sdk.IUserLoggerInterface;
import com.igexin.sdk.PushManager;

/* JADX INFO: loaded from: classes.dex */
public final class c extends Handler implements ServiceConnection {
    public static final String a = "SERVER_LOG";
    private static final String c = "LogController";
    public b b;
    private Messenger d;
    private Messenger e;

    static class a {
        private static final c a = new c(0);

        private a() {
        }
    }

    private c() {
        super(Looper.getMainLooper());
        this.b = new com.igexin.c.a.c.a.a();
    }

    /* synthetic */ c(byte b) {
        this();
    }

    public static c a() {
        return a.a;
    }

    private void a(Context context, IUserLoggerInterface iUserLoggerInterface) {
        if (iUserLoggerInterface == null) {
            e.a(c, "user logger register parameter can not be null!");
            return;
        }
        Context applicationContext = context.getApplicationContext();
        a(applicationContext);
        this.b.a(iUserLoggerInterface);
        this.b.a();
        a("[LogController] Sdk version = " + PushManager.getInstance().getVersion(applicationContext));
    }

    public final void a(Context context) {
        com.igexin.c.a.c.a.a("try to bind log server", new Object[0]);
        try {
            Intent intent = new Intent(context, (Class<?>) ServiceManager.getInstance().b(context));
            intent.setType(a);
            context.bindService(intent, this, 1);
        } catch (Exception e) {
            e.a(c, "bind service error = " + e.toString());
        }
    }

    public final void a(String str) {
        if (this.b != null) {
            this.b.a(str);
        }
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        if (message.what == 2) {
            String string = message.getData().getString(d.d);
            if (TextUtils.isEmpty(string)) {
                return;
            }
            if (!string.contains("\n")) {
                this.b.a(string);
                return;
            }
            for (String str : string.split("\n")) {
                this.b.a(str);
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        com.igexin.c.a.c.a.a("remote log service connected ", new Object[0]);
        try {
            this.e = new Messenger(iBinder);
            if (this.d == null) {
                this.d = new Messenger(this);
            }
            Message messageObtain = Message.obtain();
            messageObtain.replyTo = this.d;
            messageObtain.what = 1;
            this.e.send(messageObtain);
        } catch (Exception e) {
            com.igexin.c.a.c.a.a(c, "Client sent Message to Service error = ".concat(String.valueOf(e)));
            a("Client sent Message to Service error = ".concat(String.valueOf(e)));
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        this.e = null;
    }
}
