package com.mobile.auth.gatewayauth;

import android.content.Intent;
import com.mobile.auth.gatewayauth.annotations.AuthNumber;

/* JADX INFO: loaded from: classes.dex */
@AuthNumber
public interface ActivityResultListener {
    void onActivityResult(int i, int i2, Intent intent);
}
