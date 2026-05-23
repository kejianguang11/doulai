package com.mobile.auth.gatewayauth;

import android.view.View;
import com.mobile.auth.gatewayauth.annotations.AuthNumber;

/* JADX INFO: loaded from: classes.dex */
@AuthNumber
public class AuthRegisterViewConfig {
    private CustomInterface customInterface;
    private int rootViewId;
    private View view;

    @AuthNumber
    public static class Builder {
        private CustomInterface customInterface;
        private int rootViewId;
        private View view;

        static /* synthetic */ CustomInterface access$000(Builder builder) {
            try {
                return builder.customInterface;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }

        static /* synthetic */ View access$100(Builder builder) {
            try {
                return builder.view;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }

        static /* synthetic */ int access$200(Builder builder) {
            try {
                return builder.rootViewId;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return -1;
            }
        }

        public AuthRegisterViewConfig build() {
            try {
                return new AuthRegisterViewConfig(this);
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }

        public Builder setCustomInterface(CustomInterface customInterface) {
            try {
                this.customInterface = customInterface;
                return this;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }

        public Builder setRootViewId(int i) {
            try {
                this.rootViewId = i;
                return this;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }

        public Builder setView(View view) {
            try {
                this.view = view;
                return this;
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }
    }

    @AuthNumber
    public static class RootViewId {
        public static final int ROOT_VIEW_ID_BODY = 0;
        public static final int ROOT_VIEW_ID_NUMBER = 2;
        public static final int ROOT_VIEW_ID_TITLE_BAR = 1;
    }

    private AuthRegisterViewConfig(Builder builder) {
        this.customInterface = Builder.access$000(builder);
        this.view = Builder.access$100(builder);
        this.rootViewId = Builder.access$200(builder);
    }

    public CustomInterface getCustomInterface() {
        try {
            return this.customInterface;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public int getRootViewId() {
        try {
            return this.rootViewId;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    public View getView() {
        try {
            return this.view;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }
}
