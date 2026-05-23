package com.mobile.auth.c;

import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public final class l {
    private static final String a = "l";
    private static final Pattern b = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

    public static String a() {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                    String name = networkInterfaceNextElement.getName();
                    if (name == null || (!name.contains("wlan") && !name.equals("eth0"))) {
                        Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                        while (inetAddresses.hasMoreElements()) {
                            InetAddress inetAddressNextElement = inetAddresses.nextElement();
                            if (!inetAddressNextElement.isLoopbackAddress() && !inetAddressNextElement.isLinkLocalAddress()) {
                                String hostAddress = inetAddressNextElement.getHostAddress();
                                if (!TextUtils.isEmpty(hostAddress)) {
                                    if (stringBuffer.length() > 0) {
                                        stringBuffer.append(com.igexin.push.core.b.an);
                                    }
                                    stringBuffer.append(hostAddress);
                                }
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            return stringBuffer.toString();
        } catch (Throwable th2) {
            try {
                ExceptionProcessor.processException(th2);
                return null;
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
                return null;
            }
        }
    }

    public static String a(Context context, String str, String str2, String str3, String str4) {
        try {
            HashMap map = new HashMap();
            map.put("pipl", a());
            map.put("timeStamp", Long.toString(System.currentTimeMillis()));
            map.put("rl", "00000");
            map.put("bussinessType", str3);
            map.put("sdkversion", "SDK-JJ-v4.5.5");
            map.put("networkType", k.f(context));
            map.put("onlineType", k.g(context));
            map.put("aip", context.getPackageName());
            return a(str, str2, map, str4);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private static String a(String str, String str2, Map<String, String> map, String str3) {
        try {
            String strA = f.a(str3, f.a());
            String strA2 = f.a(a(map, com.alipay.sdk.sys.a.b), str3);
            HashMap map2 = new HashMap();
            map2.put("appId", str);
            map2.put("clientType", "30020");
            map2.put("format", "json");
            map2.put("paramKey", strA);
            map2.put("paramStr", strA2);
            map2.put("version", "3.0");
            map2.put("sign", b(map2, str2));
            return a(map2, com.alipay.sdk.sys.a.b);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private static String a(Map<String, String> map, String str) {
        try {
            StringBuilder sb = new StringBuilder();
            if (map != null && !map.isEmpty()) {
                if (TextUtils.isEmpty(str)) {
                    str = com.alipay.sdk.sys.a.b;
                }
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    sb.append(entry.getKey());
                    sb.append("=");
                    sb.append(entry.getValue());
                    sb.append(str);
                }
                sb = sb.deleteCharAt(sb.length() - 1);
            }
            return sb.toString();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private static String b(Map<String, String> map, String str) {
        try {
            ArrayList arrayList = new ArrayList(map.entrySet());
            Collections.sort(arrayList, new Comparator<Map.Entry<String, String>>() { // from class: com.mobile.auth.c.l.1
                public int a(Map.Entry<String, String> entry, Map.Entry<String, String> entry2) {
                    try {
                        return entry.getKey().compareTo(entry2.getKey());
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                            return -1;
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                            return -1;
                        }
                    }
                }

                @Override // java.util.Comparator
                public /* synthetic */ int compare(Map.Entry<String, String> entry, Map.Entry<String, String> entry2) {
                    try {
                        return a(entry, entry2);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                            return -1;
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                            return -1;
                        }
                    }
                }
            });
            String str2 = "";
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                str2 = str2 + ((String) ((Map.Entry) it.next()).getValue());
            }
            return f.c(str2, str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }
}
