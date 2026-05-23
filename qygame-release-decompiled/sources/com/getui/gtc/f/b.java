package com.getui.gtc.f;

import android.text.TextUtils;
import android.util.Base64;
import com.getui.gtc.api.OnDycEnableChangedListener;
import com.igexin.push.core.b.n;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    private static final b c = new b();
    public final List<OnDycEnableChangedListener> a = new ArrayList();
    public final AtomicBoolean b = new AtomicBoolean(false);

    private b() {
    }

    public static b a() {
        return c;
    }

    public static String a(Map<String, Boolean> map) {
        StringBuilder sb = new StringBuilder();
        String[] strArr = {"dim-2-1-21-5", "dim-2-1-21-3", "dim-2-1-21-1", "dim-2-1-21-2"};
        for (int i = 0; i < 4; i++) {
            Boolean bool = map.get(strArr[i]);
            sb.append((bool == null || !bool.booleanValue()) ? "0" : "1");
        }
        return sb.toString();
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:153:0x026c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Map<String, Boolean> b(Map<String, String> map) {
        byte b;
        String str;
        HashMap map2 = new HashMap();
        try {
            String str2 = map.get("sdk.gtc.dim.sys.call.enable");
            if (!TextUtils.isEmpty(str2)) {
                for (String str3 : str2.split(com.igexin.push.core.b.an)) {
                    boolean z = true;
                    if (str3.contains(":")) {
                        String[] strArrSplit = str3.split(":");
                        String str4 = strArrSplit[0];
                        ArrayList arrayList = new ArrayList();
                        String strEncodeToString = Base64.encodeToString(str4.getBytes(), 2);
                        switch (strEncodeToString.hashCode()) {
                            case -2095750756:
                                b = strEncodeToString.equals("Y2VsbF9pbmZvX25ldw==") ? (byte) 40 : (byte) -1;
                                break;
                            case -2048226647:
                                if (strEncodeToString.equals("bmV0d29yaw==")) {
                                    b = 26;
                                    break;
                                }
                                break;
                            case -1779042063:
                                if (strEncodeToString.equals("b2FpZF9ob25vcg==")) {
                                    b = 17;
                                    break;
                                }
                                break;
                            case -1767879989:
                                if (strEncodeToString.equals("bWFudWZhY3R1cmVy")) {
                                    b = 23;
                                    break;
                                }
                                break;
                            case -1762514687:
                                if (strEncodeToString.equals("b2FpZA==")) {
                                    b = 16;
                                    break;
                                }
                                break;
                            case -1659575288:
                                if (strEncodeToString.equals("d2lmaV9pbmZvX2d0")) {
                                    b = 37;
                                    break;
                                }
                                break;
                            case -1559644363:
                                if (strEncodeToString.equals("Z2V0dWlfZGV2aWNlX2lk")) {
                                    b = 46;
                                    break;
                                }
                                break;
                            case -1464199328:
                                if (strEncodeToString.equals("aWNjaWRfbWFpbg==")) {
                                    b = 9;
                                    break;
                                }
                                break;
                            case -1400038172:
                                if (strEncodeToString.equals("bWFjX3dsYW4w")) {
                                    b = 13;
                                    break;
                                }
                                break;
                            case -1337142239:
                                if (strEncodeToString.equals("aW1laQ==")) {
                                    b = 0;
                                    break;
                                }
                                break;
                            case -1337139108:
                                if (strEncodeToString.equals("aW1laTE=")) {
                                    b = 2;
                                    break;
                                }
                                break;
                            case -1337138984:
                                if (strEncodeToString.equals("aW1laTI=")) {
                                    b = 3;
                                    break;
                                }
                                break;
                            case -1324212945:
                                if (strEncodeToString.equals("aW1zaQ==")) {
                                    b = 4;
                                    break;
                                }
                                break;
                            case -1324209814:
                                if (strEncodeToString.equals("aW1zaTE=")) {
                                    b = 6;
                                    break;
                                }
                                break;
                            case -1324209690:
                                if (strEncodeToString.equals("aW1zaTI=")) {
                                    b = 7;
                                    break;
                                }
                                break;
                            case -1218674678:
                                if (strEncodeToString.equals("YWR2ZXJ0aXNpbmdfaWQ=")) {
                                    b = 19;
                                    break;
                                }
                                break;
                            case -1217318981:
                                if (strEncodeToString.equals("d2lmaV9zY2FuX2xpc3Q=")) {
                                    b = 35;
                                    break;
                                }
                                break;
                            case -1033769178:
                                if (strEncodeToString.equals("c2VyaWFsX251bWJlcg==")) {
                                    b = 15;
                                    break;
                                }
                                break;
                            case -997367387:
                                if (strEncodeToString.equals("bG9jYXRpb25fZ3Bz")) {
                                    b = 30;
                                    break;
                                }
                                break;
                            case -814408606:
                                if (strEncodeToString.equals("d2lmaV9pbmZv")) {
                                    b = 34;
                                    break;
                                }
                                break;
                            case -811004243:
                                if (strEncodeToString.equals("d2lmaV9tYWM=")) {
                                    b = 36;
                                    break;
                                }
                                break;
                            case -508737516:
                                if (strEncodeToString.equals("aWNjaWQ=")) {
                                    b = 8;
                                    break;
                                }
                                break;
                            case -508737457:
                                if (strEncodeToString.equals("aWNjaWQx")) {
                                    b = 10;
                                    break;
                                }
                                break;
                            case -508737456:
                                if (strEncodeToString.equals("aWNjaWQy")) {
                                    b = n.l;
                                    break;
                                }
                                break;
                            case -367876355:
                                if (strEncodeToString.equals("bG9jYXRpb25fbmV0d29yaw==")) {
                                    b = 31;
                                    break;
                                }
                                break;
                            case -17088614:
                                if (strEncodeToString.equals("aXB2NA==")) {
                                    b = 28;
                                    break;
                                }
                                break;
                            case -17052096:
                                if (strEncodeToString.equals("aXB2Ng==")) {
                                    b = 29;
                                    break;
                                }
                                break;
                            case 2976371:
                                if (strEncodeToString.equals("aXA=")) {
                                    b = 27;
                                    break;
                                }
                                break;
                            case 3001790:
                                if (strEncodeToString.equals("c3lz")) {
                                    b = 24;
                                    break;
                                }
                                break;
                            case 3005401:
                                if (strEncodeToString.equals("bWFj")) {
                                    b = 12;
                                    break;
                                }
                                break;
                            case 3055941:
                                if (strEncodeToString.equals("cm9t")) {
                                    b = 22;
                                    break;
                                }
                                break;
                            case 79802473:
                                if (strEncodeToString.equals("YXBwX2xpc3RfYnlfbG9jYWxfZGlycw==")) {
                                    b = 43;
                                    break;
                                }
                                break;
                            case 142694456:
                                if (strEncodeToString.equals("YXBwX2xpc3RfYnlfY29tcGxleF9wb2xpY3k=")) {
                                    b = 45;
                                    break;
                                }
                                break;
                            case 193136789:
                                if (strEncodeToString.equals("Y2VsbA==")) {
                                    b = 38;
                                    break;
                                }
                                break;
                            case 261839736:
                                if (strEncodeToString.equals("Y2VsbF9pbmZv")) {
                                    b = 39;
                                    break;
                                }
                                break;
                            case 308427546:
                                if (strEncodeToString.equals("Y2Fycmllcg==")) {
                                    b = 25;
                                    break;
                                }
                                break;
                            case 372428747:
                                if (strEncodeToString.equals("bG9jYXRpb25fZ3BzX2d0")) {
                                    b = 33;
                                    break;
                                }
                                break;
                            case 633576810:
                                if (strEncodeToString.equals("bW9kZWw=")) {
                                    b = 21;
                                    break;
                                }
                                break;
                            case 1148798111:
                                if (strEncodeToString.equals("bWFjX2FsbA==")) {
                                    b = 14;
                                    break;
                                }
                                break;
                            case 1196833449:
                                if (strEncodeToString.equals("bG9jYXRpb25fbmV0d29ya19ndA==")) {
                                    b = 32;
                                    break;
                                }
                                break;
                            case 1321129970:
                                if (strEncodeToString.equals("YXBwX2xpc3RfYnlfcXVlcnlfaW50ZW50")) {
                                    b = 41;
                                    break;
                                }
                                break;
                            case 1396586978:
                                if (strEncodeToString.equals("YXBwX2xpc3RfYnlfcG1fbGlzdA==")) {
                                    b = 44;
                                    break;
                                }
                                break;
                            case 1444607276:
                                if (strEncodeToString.equals("YXBwX2xpc3RfYnlfaW5zdGFsbGVkX3BhY2thZ2U=")) {
                                    b = 42;
                                    break;
                                }
                                break;
                            case 1550084458:
                                if (strEncodeToString.equals("YnJhbmQ=")) {
                                    b = 20;
                                    break;
                                }
                                break;
                            case 1550874440:
                                if (strEncodeToString.equals("aW1laV9tYWlu")) {
                                    b = 1;
                                    break;
                                }
                                break;
                            case 1846142911:
                                if (strEncodeToString.equals("YW5kcm9pZF9pZA==")) {
                                    b = 18;
                                    break;
                                }
                                break;
                            case 2016315734:
                                if (strEncodeToString.equals("aW1zaV9tYWlu")) {
                                    b = 5;
                                    break;
                                }
                                break;
                            default:
                                break;
                        }
                        switch (b) {
                            case 0:
                                arrayList.add("dim-2-1-1-1");
                                arrayList.add("dim-2-1-1-3");
                                str = "dim-2-1-1-4";
                                arrayList.add(str);
                                break;
                            case 1:
                                str = "dim-2-1-1-1";
                                arrayList.add(str);
                                break;
                            case 2:
                                str = "dim-2-1-1-3";
                                arrayList.add(str);
                                break;
                            case 3:
                                str = "dim-2-1-1-4";
                                arrayList.add(str);
                                break;
                            case 4:
                                arrayList.add("dim-2-1-2-1");
                                arrayList.add("dim-2-1-2-3");
                                str = "dim-2-1-2-4";
                                arrayList.add(str);
                                break;
                            case 5:
                                str = "dim-2-1-2-1";
                                arrayList.add(str);
                                break;
                            case 6:
                                str = "dim-2-1-2-3";
                                arrayList.add(str);
                                break;
                            case 7:
                                str = "dim-2-1-2-4";
                                arrayList.add(str);
                                break;
                            case 8:
                                arrayList.add("dim-2-1-6-1");
                                arrayList.add("dim-2-1-6-3");
                                str = "dim-2-1-6-4";
                                arrayList.add(str);
                                break;
                            case 9:
                                str = "dim-2-1-6-1";
                                arrayList.add(str);
                                break;
                            case 10:
                                str = "dim-2-1-6-3";
                                arrayList.add(str);
                                break;
                            case 11:
                                str = "dim-2-1-6-4";
                                arrayList.add(str);
                                break;
                            case 12:
                                arrayList.add("dim-2-1-3-1");
                                str = "dim-2-1-3-2";
                                arrayList.add(str);
                                break;
                            case 13:
                                str = "dim-2-1-3-1";
                                arrayList.add(str);
                                break;
                            case 14:
                                str = "dim-2-1-3-2";
                                arrayList.add(str);
                                break;
                            case 15:
                                str = "dim-2-1-4-1";
                                arrayList.add(str);
                                break;
                            case 16:
                                str = "dim-2-1-5-1";
                                arrayList.add(str);
                                break;
                            case 17:
                                str = "dim-2-1-5-2";
                                arrayList.add(str);
                                break;
                            case 18:
                                str = "dim-2-1-7-1";
                                arrayList.add(str);
                                break;
                            case 19:
                                str = "dim-2-1-8-1";
                                arrayList.add(str);
                                break;
                            case 20:
                                str = "dim-2-1-9-1";
                                arrayList.add(str);
                                break;
                            case 21:
                                str = "dim-2-1-10-1";
                                arrayList.add(str);
                                break;
                            case 22:
                                str = "dim-2-1-11-1";
                                arrayList.add(str);
                                break;
                            case 23:
                                str = "dim-2-1-12-1";
                                arrayList.add(str);
                                break;
                            case 24:
                                str = "dim-2-1-13-1";
                                arrayList.add(str);
                                break;
                            case 25:
                                str = "dim-2-1-14-1";
                                arrayList.add(str);
                                break;
                            case 26:
                                str = "dim-2-1-15-1";
                                arrayList.add(str);
                                break;
                            case 27:
                                arrayList.add("dim-2-1-16-1");
                                str = "dim-2-1-16-2";
                                arrayList.add(str);
                                break;
                            case 28:
                                str = "dim-2-1-16-1";
                                arrayList.add(str);
                                break;
                            case 29:
                                str = "dim-2-1-16-2";
                                arrayList.add(str);
                                break;
                            case 30:
                                str = "dim-2-1-17-1";
                                arrayList.add(str);
                                break;
                            case 31:
                                str = "dim-2-1-17-2";
                                arrayList.add(str);
                                break;
                            case 32:
                                str = "dim-2-1-17-4";
                                arrayList.add(str);
                                break;
                            case 33:
                                str = "dim-2-1-17-3";
                                arrayList.add(str);
                                break;
                            case 34:
                                str = "dim-2-1-18-1";
                                arrayList.add(str);
                                break;
                            case 35:
                                str = "dim-2-1-18-2";
                                arrayList.add(str);
                                break;
                            case 36:
                                str = "dim-2-1-18-3";
                                arrayList.add(str);
                                break;
                            case 37:
                                str = "dim-2-1-18-4";
                                arrayList.add(str);
                                break;
                            case 38:
                                arrayList.add("dim-2-1-19-1");
                                str = "dim-2-1-19-2";
                                arrayList.add(str);
                                break;
                            case 39:
                                str = "dim-2-1-19-1";
                                arrayList.add(str);
                                break;
                            case 40:
                                str = "dim-2-1-19-2";
                                arrayList.add(str);
                                break;
                            case 41:
                                str = "dim-2-1-21-1";
                                arrayList.add(str);
                                break;
                            case 42:
                                str = "dim-2-1-21-2";
                                arrayList.add(str);
                                break;
                            case 43:
                                str = "dim-2-1-21-3";
                                arrayList.add(str);
                                break;
                            case 44:
                                str = "dim-2-1-21-5";
                                arrayList.add(str);
                                break;
                            case 45:
                                str = "dim-2-1-21-4";
                                arrayList.add(str);
                                break;
                            case 46:
                                str = "dim-2-1-22-1";
                                arrayList.add(str);
                                break;
                        }
                        int i = Integer.parseInt(strArrSplit[1]);
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            map2.put((String) it.next(), Boolean.valueOf(i != 0));
                        }
                    } else {
                        if (Integer.parseInt(str3) == 0) {
                            z = false;
                        }
                        map2.put("dim-2-2-0-1", Boolean.valueOf(z));
                    }
                }
            }
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.b(th);
        }
        return map2;
    }

    public final void a(OnDycEnableChangedListener onDycEnableChangedListener) {
        if (this.b.get() || onDycEnableChangedListener == null) {
            return;
        }
        synchronized (this.a) {
            this.a.add(onDycEnableChangedListener);
        }
    }
}
