package org.cocos2dx.javascript.alipay;

import android.text.TextUtils;
import com.alipay.sdk.util.h;
import com.alipay.sdk.util.j;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class PayResult {
    private String memo;
    private String result;
    private String resultStatus;

    public PayResult(Map<String, String> map) {
        if (map == null) {
            return;
        }
        for (String str : map.keySet()) {
            if (TextUtils.equals(str, j.a)) {
                this.resultStatus = map.get(str);
            } else if (TextUtils.equals(str, j.c)) {
                this.result = map.get(str);
            } else if (TextUtils.equals(str, j.b)) {
                this.memo = map.get(str);
            }
        }
    }

    public String getMemo() {
        return this.memo;
    }

    public String getResult() {
        return this.result;
    }

    public String getResultStatus() {
        return this.resultStatus;
    }

    public String toString() {
        return "resultStatus={" + this.resultStatus + "};memo={" + this.memo + "};result={" + this.result + h.d;
    }
}
