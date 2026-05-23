package com.mobile.auth.gatewayauth.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private View a;
    private WeakReference<Context> b;
    private AbstractPnsViewDelegate c;
    private boolean d;

    public a(Context context, int i, AbstractPnsViewDelegate abstractPnsViewDelegate) {
        this(LayoutInflater.from(context).inflate(i, (ViewGroup) new FrameLayout(context), false), abstractPnsViewDelegate);
    }

    public a(View view, AbstractPnsViewDelegate abstractPnsViewDelegate) {
        this.d = true;
        this.c = abstractPnsViewDelegate;
        this.a = view;
        this.b = new WeakReference<>(view.getContext());
        if (this.c != null) {
            this.c.setPnsView(this);
            this.c.onViewCreated(this.a);
        }
    }

    public Context a() {
        Context context;
        try {
            return (this.b == null || (context = this.b.get()) == null) ? this.a.getContext() : context;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public View a(int i) {
        try {
            return this.a.findViewById(i);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public View b() {
        try {
            return this.a;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }
}
