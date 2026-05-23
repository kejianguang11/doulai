package com.mobile.auth.gatewayauth.ui;

import android.content.Context;
import android.view.View;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.annotations.AuthNumber;

/* JADX INFO: loaded from: classes.dex */
@AuthNumber
public abstract class AbstractPnsViewDelegate {
    private a mPnsView;

    public final View findViewById(int i) {
        try {
            if (this.mPnsView != null) {
                return this.mPnsView.a(i);
            }
            return null;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public final Context getContext() {
        try {
            if (this.mPnsView != null) {
                return this.mPnsView.a();
            }
            return null;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public final View getRootView() {
        try {
            if (this.mPnsView != null) {
                return this.mPnsView.b();
            }
            return null;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public abstract void onViewCreated(View view);

    final void setPnsView(a aVar) {
        try {
            this.mPnsView = aVar;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }
}
