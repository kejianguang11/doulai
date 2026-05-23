package com.alipay.sdk.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public final class e {
    private static boolean a;

    static {
        a = Build.VERSION.SDK_INT >= 11;
    }

    private static AlertDialog.Builder a(Context context, String str, DialogInterface.OnClickListener onClickListener, String str2, DialogInterface.OnClickListener onClickListener2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (a) {
            if (!TextUtils.isEmpty(str2) && onClickListener2 != null) {
                builder.setPositiveButton(str2, onClickListener2);
            }
            if (!TextUtils.isEmpty(str) && onClickListener != null) {
                builder.setNegativeButton(str, onClickListener);
            }
        } else {
            if (!TextUtils.isEmpty(str) && onClickListener != null) {
                builder.setPositiveButton(str, onClickListener);
            }
            if (!TextUtils.isEmpty(str2) && onClickListener2 != null) {
                builder.setNegativeButton(str2, onClickListener2);
            }
        }
        return builder;
    }

    public static Dialog a(Context context, String str, String str2, String str3, DialogInterface.OnClickListener onClickListener, String str4, DialogInterface.OnClickListener onClickListener2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (a) {
            if (!TextUtils.isEmpty(str4)) {
                builder.setPositiveButton(str4, onClickListener2);
            }
            if (!TextUtils.isEmpty(str3)) {
                builder.setNegativeButton(str3, onClickListener);
            }
        } else {
            if (!TextUtils.isEmpty(str3)) {
                builder.setPositiveButton(str3, onClickListener);
            }
            if (!TextUtils.isEmpty(str4)) {
                builder.setNegativeButton(str4, onClickListener2);
            }
        }
        builder.setTitle(str);
        builder.setMessage(str2);
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.setCanceledOnTouchOutside(false);
        alertDialogCreate.setOnKeyListener(new f());
        try {
            alertDialogCreate.show();
        } catch (Throwable unused) {
        }
        return alertDialogCreate;
    }
}
