package com.nirvana.tools.core;

import android.text.TextUtils;
import androidx.core.view.InputDeviceCompat;
import com.alipay.sdk.sys.a;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class EncodeUtil {
    public static String decode(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            StringBuffer stringBuffer = new StringBuffer(str);
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(str.length() / 2);
            int i = 0;
            while (i < str.length()) {
                int i2 = i + 1;
                int i3 = Integer.parseInt(stringBuffer.substring(i, i2), 16) << 4;
                i += 2;
                byteBufferAllocate.put((byte) (Integer.parseInt(stringBuffer.substring(i2, i), 16) | i3));
            }
            byte[] bArrArray = byteBufferAllocate.array();
            for (int i4 = 0; i4 < bArrArray.length; i4++) {
                if (i4 == bArrArray.length - 1) {
                    int length = bArrArray.length - 1;
                    bArrArray[length] = (byte) (bArrArray[length] ^ 98);
                } else {
                    int i5 = i4 + 1;
                    if (bArrArray[i4] != bArrArray[i5]) {
                        bArrArray[i4] = (byte) (bArrArray[i4] ^ bArrArray[i5]);
                    }
                }
            }
            for (int length2 = bArrArray.length - 1; length2 >= 0; length2--) {
                if (length2 == 0) {
                    bArrArray[0] = (byte) (bArrArray[0] ^ 69);
                } else {
                    int i6 = length2 - 1;
                    if (bArrArray[length2] != bArrArray[i6]) {
                        bArrArray[length2] = (byte) (bArrArray[length2] ^ bArrArray[i6]);
                    }
                }
            }
            return new String(bArrArray, a.m);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encode(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        byte[] bytes = str.getBytes();
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                bytes[0] = (byte) (bytes[0] ^ 69);
            } else {
                int i2 = i - 1;
                if (bytes[i] != bytes[i2]) {
                    bytes[i] = (byte) (bytes[i] ^ bytes[i2]);
                }
            }
        }
        int i3 = length - 1;
        for (int i4 = i3; i4 >= 0; i4--) {
            if (i4 == i3) {
                bytes[i4] = (byte) (bytes[i4] ^ 98);
            } else {
                int i5 = i4 + 1;
                if (bytes[i4] != bytes[i5]) {
                    bytes[i4] = (byte) (bytes[i4] ^ bytes[i5]);
                }
            }
        }
        String str2 = "";
        for (byte b : bytes) {
            str2 = str2 + Integer.toHexString((b & 255) | InputDeviceCompat.SOURCE_ANY).substring(6);
        }
        return str2;
    }
}
