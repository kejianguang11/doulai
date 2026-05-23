package org.cocos2dx.javascript.alipay;

import com.alipay.sdk.packet.d;
import com.alipay.sdk.sys.a;
import com.igexin.push.g.s;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class OrderInfoUtil2_0 {
    private static String buildKeyValue(String str, String str2, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("=");
        if (z) {
            try {
                sb.append(URLEncoder.encode(str2, a.m));
            } catch (UnsupportedEncodingException unused) {
                sb.append(str2);
            }
        } else {
            sb.append(str2);
        }
        return sb.toString();
    }

    public static String buildOrderParam(Map<String, String> map) {
        ArrayList arrayList = new ArrayList(map.keySet());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arrayList.size() - 1; i++) {
            String str = (String) arrayList.get(i);
            sb.append(buildKeyValue(str, map.get(str), false));
            sb.append(a.b);
        }
        String str2 = (String) arrayList.get(arrayList.size() - 1);
        sb.append(buildKeyValue(str2, map.get(str2), false));
        return sb.toString();
    }

    public static Map<String, String> buildOrderParamMap(String str, String str2, String str3, String str4, String str5) {
        HashMap map = new HashMap();
        map.put("app_id", str);
        map.put("format", "JSON");
        map.put("notify_url", str2);
        map.put("biz_content", str4);
        map.put("charset", s.b);
        map.put(d.q, "alipay.trade.app.pay");
        map.put("sign_type", "RSA2");
        map.put(DBHelpTool.RecordEntry.COLUMN_NAME_TIMESTAMP, str3);
        map.put("version", "1.0");
        map.put("sign", str5);
        return map;
    }
}
