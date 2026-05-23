package com.igexin.push.core;

import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import com.igexin.assist.sdk.AssistPushConsts;
import com.igexin.push.core.d;
import com.igexin.push.extension.mod.BaseActionBean;
import com.igexin.push.extension.mod.PushMessageInterface;
import com.igexin.push.extension.mod.PushTaskBean;
import com.igexin.push.g.o;
import com.igexin.sdk.main.FeedbackImpl;
import com.mobile.auth.gatewayauth.Constant;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class n {
    private static final String b = "PushMessageExecutor";
    private static Set<String> d;
    private static volatile n f;
    private final Map<String, PushMessageInterface> c;
    protected HashMap<String, String> a = new HashMap<>();
    private final Map<String, String> e = new ConcurrentHashMap();

    private n() {
        d = new HashSet();
        this.c = new HashMap();
        d.add(b.s);
        d.add(b.n);
        d.add(b.o);
        d.add(b.p);
        d.add(b.q);
        d.add(b.r);
        d.add("null");
        d.add(b.u);
        d.add(b.v);
        d.add(b.w);
        d.add(b.x);
        d.add(b.y);
        d.add(b.t);
        d.add(b.z);
    }

    public static n a() {
        if (f == null) {
            synchronized (n.class) {
                if (f == null) {
                    f = new n();
                }
            }
        }
        return f;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private PushMessageInterface a(String str) {
        PushMessageInterface pushMessageInterface;
        Map<String, PushMessageInterface> map;
        String str2;
        PushMessageInterface fVar;
        ClassLoader classLoaderB;
        Class<?> clsLoadClass;
        if (this.c.containsKey(str)) {
            return this.c.get(str);
        }
        PushMessageInterface pushMessageInterface2 = null;
        try {
            if (!this.a.containsKey(str) || (classLoaderB = e.b(str)) == null || (clsLoadClass = classLoaderB.loadClass(this.a.get(str))) == null) {
                pushMessageInterface = null;
            } else {
                pushMessageInterface = (PushMessageInterface) clsLoadClass.newInstance();
                try {
                    this.c.put(str, pushMessageInterface);
                } catch (Throwable th) {
                    th = th;
                    com.igexin.c.a.c.a.a(th);
                }
            }
        } catch (Throwable th2) {
            th = th2;
            pushMessageInterface = null;
        }
        if (pushMessageInterface != null) {
            return pushMessageInterface;
        }
        if (!TextUtils.isEmpty(str) && d.contains(str) && (pushMessageInterface2 = this.c.get(str)) == null) {
            byte b2 = -1;
            switch (str.hashCode()) {
                case -1664373827:
                    if (str.equals(b.t)) {
                        b2 = 12;
                    }
                    break;
                case -1618888868:
                    if (str.equals(b.y)) {
                        b2 = 10;
                    }
                    break;
                case -1352939875:
                    if (str.equals(b.p)) {
                        b2 = 3;
                    }
                    break;
                case -1218913434:
                    if (str.equals(b.o)) {
                        b2 = 2;
                    }
                    break;
                case -631641375:
                    if (str.equals(b.x)) {
                        b2 = 9;
                    }
                    break;
                case 3178851:
                    if (str.equals(b.s)) {
                        b2 = 0;
                    }
                    break;
                case 3392903:
                    if (str.equals("null")) {
                        b2 = 6;
                    }
                    break;
                case 106852524:
                    if (str.equals(b.r)) {
                        b2 = 5;
                    }
                    break;
                case 595233003:
                    if (str.equals(b.n)) {
                        b2 = 1;
                    }
                    break;
                case 790184760:
                    if (str.equals(b.w)) {
                        b2 = com.igexin.push.core.b.n.l;
                    }
                    break;
                case 961723282:
                    if (str.equals(b.z)) {
                        b2 = 13;
                    }
                    break;
                case 1316799103:
                    if (str.equals(b.q)) {
                        b2 = 4;
                    }
                    break;
                case 1316819890:
                    if (str.equals(b.u)) {
                        b2 = 7;
                    }
                    break;
                case 1536890905:
                    if (str.equals(b.v)) {
                        b2 = 8;
                    }
                    break;
            }
            switch (b2) {
                case 0:
                    map = this.c;
                    str2 = b.s;
                    fVar = new com.igexin.push.core.a.c.f();
                    break;
                case 1:
                    map = this.c;
                    str2 = b.n;
                    fVar = new com.igexin.push.core.a.c.h();
                    break;
                case 2:
                    map = this.c;
                    str2 = b.o;
                    fVar = new com.igexin.push.core.a.c.m();
                    break;
                case 3:
                    map = this.c;
                    str2 = b.p;
                    fVar = new com.igexin.push.core.a.c.k();
                    break;
                case 4:
                    map = this.c;
                    str2 = b.q;
                    fVar = new com.igexin.push.core.a.c.j();
                    break;
                case 5:
                    map = this.c;
                    str2 = b.r;
                    fVar = new com.igexin.push.core.a.c.i();
                    break;
                case 6:
                    map = this.c;
                    str2 = "null";
                    fVar = new com.igexin.push.core.a.c.e();
                    break;
                case 7:
                    map = this.c;
                    str2 = b.u;
                    fVar = new com.igexin.push.core.a.c.l();
                    break;
                case 8:
                    map = this.c;
                    str2 = b.v;
                    fVar = new com.igexin.push.core.a.c.a();
                    break;
                case 9:
                    map = this.c;
                    str2 = b.x;
                    fVar = new com.igexin.push.core.a.c.d();
                    break;
                case 10:
                    map = this.c;
                    str2 = b.y;
                    fVar = new com.igexin.push.core.a.c.c();
                    break;
                case 11:
                    map = this.c;
                    str2 = b.w;
                    fVar = new com.igexin.push.core.a.c.b();
                    break;
                case 12:
                    map = this.c;
                    str2 = b.t;
                    fVar = new com.igexin.push.core.a.c.n();
                    break;
                case 13:
                    map = this.c;
                    str2 = b.z;
                    fVar = new com.igexin.push.core.a.c.g();
                    break;
                default:
                    pushMessageInterface2 = this.c.get(str);
                    break;
            }
            map.put(str2, fVar);
            pushMessageInterface2 = this.c.get(str);
        }
        return pushMessageInterface2;
    }

    public static void a(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString("taskid", str);
        bundle.putString("messageid", str2);
        Message messageObtain = Message.obtain();
        messageObtain.what = b.U;
        messageObtain.obj = bundle;
        d.a.a.a(messageObtain);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private PushMessageInterface b(String str) {
        Map<String, PushMessageInterface> map;
        String str2;
        PushMessageInterface fVar;
        if (TextUtils.isEmpty(str) || !d.contains(str)) {
            return null;
        }
        PushMessageInterface pushMessageInterface = this.c.get(str);
        if (pushMessageInterface != null) {
            return pushMessageInterface;
        }
        byte b2 = -1;
        switch (str.hashCode()) {
            case -1664373827:
                if (str.equals(b.t)) {
                    b2 = 12;
                }
                break;
            case -1618888868:
                if (str.equals(b.y)) {
                    b2 = 10;
                }
                break;
            case -1352939875:
                if (str.equals(b.p)) {
                    b2 = 3;
                }
                break;
            case -1218913434:
                if (str.equals(b.o)) {
                    b2 = 2;
                }
                break;
            case -631641375:
                if (str.equals(b.x)) {
                    b2 = 9;
                }
                break;
            case 3178851:
                if (str.equals(b.s)) {
                    b2 = 0;
                }
                break;
            case 3392903:
                if (str.equals("null")) {
                    b2 = 6;
                }
                break;
            case 106852524:
                if (str.equals(b.r)) {
                    b2 = 5;
                }
                break;
            case 595233003:
                if (str.equals(b.n)) {
                    b2 = 1;
                }
                break;
            case 790184760:
                if (str.equals(b.w)) {
                    b2 = com.igexin.push.core.b.n.l;
                }
                break;
            case 961723282:
                if (str.equals(b.z)) {
                    b2 = 13;
                }
                break;
            case 1316799103:
                if (str.equals(b.q)) {
                    b2 = 4;
                }
                break;
            case 1316819890:
                if (str.equals(b.u)) {
                    b2 = 7;
                }
                break;
            case 1536890905:
                if (str.equals(b.v)) {
                    b2 = 8;
                }
                break;
        }
        switch (b2) {
            case 0:
                map = this.c;
                str2 = b.s;
                fVar = new com.igexin.push.core.a.c.f();
                break;
            case 1:
                map = this.c;
                str2 = b.n;
                fVar = new com.igexin.push.core.a.c.h();
                break;
            case 2:
                map = this.c;
                str2 = b.o;
                fVar = new com.igexin.push.core.a.c.m();
                break;
            case 3:
                map = this.c;
                str2 = b.p;
                fVar = new com.igexin.push.core.a.c.k();
                break;
            case 4:
                map = this.c;
                str2 = b.q;
                fVar = new com.igexin.push.core.a.c.j();
                break;
            case 5:
                map = this.c;
                str2 = b.r;
                fVar = new com.igexin.push.core.a.c.i();
                break;
            case 6:
                map = this.c;
                str2 = "null";
                fVar = new com.igexin.push.core.a.c.e();
                break;
            case 7:
                map = this.c;
                str2 = b.u;
                fVar = new com.igexin.push.core.a.c.l();
                break;
            case 8:
                map = this.c;
                str2 = b.v;
                fVar = new com.igexin.push.core.a.c.a();
                break;
            case 9:
                map = this.c;
                str2 = b.x;
                fVar = new com.igexin.push.core.a.c.d();
                break;
            case 10:
                map = this.c;
                str2 = b.y;
                fVar = new com.igexin.push.core.a.c.c();
                break;
            case 11:
                map = this.c;
                str2 = b.w;
                fVar = new com.igexin.push.core.a.c.b();
                break;
            case 12:
                map = this.c;
                str2 = b.t;
                fVar = new com.igexin.push.core.a.c.n();
                break;
            case 13:
                map = this.c;
                str2 = b.z;
                fVar = new com.igexin.push.core.a.c.g();
                break;
            default:
                return this.c.get(str);
        }
        map.put(str2, fVar);
        return this.c.get(str);
    }

    public static void b() {
        try {
            if (!TextUtils.isEmpty(com.igexin.push.config.d.C) && !com.igexin.push.a.i.equals(com.igexin.push.config.d.C)) {
                List<String> listAsList = Arrays.asList(com.igexin.push.config.d.C.split(b.an));
                if (listAsList.isEmpty()) {
                    return;
                }
                ArrayList arrayList = new ArrayList();
                Iterator<Map.Entry<String, PushTaskBean>> it = e.ah.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, PushTaskBean> next = it.next();
                    String key = next.getKey();
                    PushTaskBean value = next.getValue();
                    if (!TextUtils.isEmpty(key)) {
                        for (String str : listAsList) {
                            if (!TextUtils.isEmpty(str) && key.startsWith(str)) {
                                arrayList.add(value.getTaskId());
                                it.remove();
                            }
                        }
                    }
                }
                if (arrayList.isEmpty()) {
                    return;
                }
                String[] strArr = new String[arrayList.size()];
                for (int i = 0; i < arrayList.size(); i++) {
                    strArr[i] = (String) arrayList.get(i);
                }
                d.a.a.i.a(b.Z, new String[]{"taskid"}, strArr);
            }
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
    }

    private static void b(JSONObject jSONObject, PushTaskBean pushTaskBean) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("condition");
            HashMap map = new HashMap();
            if (jSONObject2.has("wifi")) {
                map.put("wifi", jSONObject2.getString("wifi"));
            }
            if (jSONObject2.has("screenOn")) {
                map.put("screenOn", jSONObject2.getString("screenOn"));
            }
            if (jSONObject2.has("ssid")) {
                map.put("ssid", jSONObject2.getString("ssid"));
                if (jSONObject2.has("bssid")) {
                    map.put("bssid", jSONObject2.getString("bssid"));
                }
            }
            if (jSONObject2.has("duration")) {
                String string = jSONObject2.getString("duration");
                if (string.contains("-")) {
                    int iIndexOf = string.indexOf("-");
                    String strSubstring = string.substring(0, iIndexOf);
                    String strSubstring2 = string.substring(iIndexOf + 1, string.length());
                    map.put(Constant.START_TIME, strSubstring);
                    map.put("endTime", strSubstring2);
                }
            }
            if (jSONObject2.has("netConnected")) {
                map.put("netConnected", jSONObject2.getString("netConnected"));
            }
            if (jSONObject2.has("expireTime")) {
                String string2 = jSONObject2.getString("expireTime");
                if (!TextUtils.isEmpty(string2) && TextUtils.isDigitsOnly(string2)) {
                    map.put("expireTime", string2);
                }
            }
            pushTaskBean.setConditionMap(map);
        } catch (Exception e) {
            com.igexin.c.a.c.a.a(e);
        }
    }

    private void c(String str, String str2) {
        if (str2 == null || str == null) {
            return;
        }
        try {
            com.igexin.push.core.a.b.d();
            String strA = com.igexin.push.core.a.b.a(str, str2);
            PushTaskBean pushTaskBean = e.ah.get(strA);
            if (pushTaskBean == null) {
                return;
            }
            if (pushTaskBean.getStatus() == b.ag) {
                com.igexin.c.a.c.a.b(b, " has execute ".concat(String.valueOf(strA)));
                return;
            }
            pushTaskBean.setStatus(b.ag);
            com.igexin.c.a.c.a.b(b, " do processActionExecute ".concat(String.valueOf(strA)));
            if (b(str, str2) != PushMessageInterface.ActionPrepareState.success) {
                pushTaskBean.setStatus(b.af);
                return;
            }
            com.igexin.push.core.e.c.a();
            com.igexin.push.core.e.c.a(b.ag, str);
            pushTaskBean.setStatus(b.ag);
            if (a(str, str2, "1")) {
                return;
            }
            com.igexin.push.core.e.c.a();
            com.igexin.push.core.e.c.a(b.af, str);
            pushTaskBean.setStatus(b.af);
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
    }

    private void e() {
        com.igexin.c.a.c.a.a("PushMessageExecutor|--------checkConditionStatus the pushMessageMap from db because log gkt...", new Object[0]);
        try {
            if (com.igexin.push.g.c.a(System.currentTimeMillis())) {
                com.igexin.c.a.c.a.b(b, "message in silent time , ignored...");
                return;
            }
            if (d()) {
                return;
            }
            for (Map.Entry<String, PushTaskBean> entry : e.ah.entrySet()) {
                try {
                    entry.getKey();
                    PushTaskBean value = entry.getValue();
                    if (value != null && value.getStatus() == b.af) {
                        String taskId = value.getTaskId();
                        Map<String, String> conditionMap = value.getConditionMap();
                        if (conditionMap == null) {
                            return;
                        }
                        if (a(conditionMap, taskId, value)) {
                            a(taskId, value.getMessageId());
                        }
                    }
                } catch (Exception e) {
                    com.igexin.c.a.c.a.a(e);
                    com.igexin.c.a.c.a.a("PushMessageExecutor|" + e.toString(), new Object[0]);
                }
            }
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
            com.igexin.c.a.c.a.a("PushMessageExecutor|" + e2.toString(), new Object[0]);
        }
    }

    private boolean f() throws Throwable {
        Cursor cursorA;
        if (e.ah.isEmpty() && e.w.getAndSet(false)) {
            Cursor cursor = null;
            try {
                try {
                    cursorA = d.a.a.i.a(b.Z, new String[]{"status"}, new String[]{"0"}, null, null);
                    if (cursorA != null) {
                        while (cursorA.moveToNext()) {
                            try {
                                byte[] blob = cursorA.getBlob(cursorA.getColumnIndex("msgextra"));
                                try {
                                    JSONObject jSONObject = new JSONObject(new String(com.igexin.c.b.a.c(cursorA.getBlob(cursorA.getColumnIndex("info")))));
                                    String string = jSONObject.getString(b.C);
                                    String string2 = jSONObject.getString("appid");
                                    String string3 = jSONObject.getString("messageid");
                                    String string4 = jSONObject.getString("taskid");
                                    String string5 = jSONObject.getString(com.alipay.sdk.sys.a.f);
                                    JSONArray jSONArray = jSONObject.getJSONArray("action_chains");
                                    com.igexin.push.core.a.b.d();
                                    String strA = com.igexin.push.core.a.b.a(string4, string3);
                                    PushTaskBean pushTaskBean = new PushTaskBean();
                                    pushTaskBean.setAppid(string2);
                                    pushTaskBean.setMessageId(string3);
                                    pushTaskBean.setTaskId(string4);
                                    pushTaskBean.setId(string);
                                    pushTaskBean.setAppKey(string5);
                                    pushTaskBean.setCurrentActionid(1);
                                    pushTaskBean.setStatus(cursorA.getInt(cursorA.getColumnIndex("status")));
                                    if (blob != null) {
                                        pushTaskBean.setMsgExtra(blob);
                                    }
                                    if (jSONObject.has("condition")) {
                                        b(jSONObject, pushTaskBean);
                                    }
                                    if (jSONArray.length() > 0) {
                                        if (a(jSONObject, pushTaskBean)) {
                                            e.ah.put(strA, pushTaskBean);
                                        } else {
                                            com.igexin.c.a.c.a.a(b, "load task from db parseActionChains error, " + jSONObject.toString());
                                            com.igexin.c.a.c.a.a("PushMessageExecutor|load task from db parseActionChains error, " + jSONObject.toString(), new Object[0]);
                                        }
                                    }
                                } catch (JSONException e) {
                                    com.igexin.c.a.c.a.a(e);
                                }
                            } catch (Throwable th) {
                                th = th;
                                if (cursorA != null) {
                                    cursorA.close();
                                }
                                throw th;
                            }
                        }
                    }
                    if (cursorA != null) {
                        cursorA.close();
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                cursorA = cursor;
            }
        }
        return e.ah.isEmpty();
    }

    private void g() {
        try {
            List<ScanResult> listJ = o.j();
            this.e.clear();
            if (listJ == null || listJ.isEmpty()) {
                return;
            }
            for (int i = 0; i < listJ.size(); i++) {
                this.e.put(listJ.get(i).BSSID, listJ.get(i).SSID);
                String str = listJ.get(i).BSSID;
                String str2 = listJ.get(i).SSID;
            }
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
    }

    public final void a(Intent intent) throws Throwable {
        String stringExtra = intent.getStringExtra("taskid");
        String stringExtra2 = intent.getStringExtra("messageid");
        String stringExtra3 = intent.getStringExtra("actionid");
        String stringExtra4 = intent.getStringExtra("accesstoken");
        String stringExtra5 = intent.getStringExtra(Constant.PROTOCOL_WEB_VIEW_URL);
        String stringExtra6 = intent.getStringExtra("intentUri");
        String stringExtra7 = intent.getStringExtra(AssistPushConsts.MSG_TYPE_PAYLOAD);
        String stringExtra8 = intent.hasExtra("title") ? intent.getStringExtra("title") : "";
        String stringExtra9 = intent.hasExtra(DBHelpTool.RecordEntry.COLUMN_NAME_CONTENT) ? intent.getStringExtra(DBHelpTool.RecordEntry.COLUMN_NAME_CONTENT) : "";
        int intExtra = intent.getIntExtra("notifID", 0);
        NotificationManager notificationManager = (NotificationManager) e.l.getSystemService(b.n);
        if (intExtra != 0) {
            notificationManager.cancel(intExtra);
        } else if (e.ai.containsKey(stringExtra)) {
            intExtra = e.ai.get(stringExtra).intValue();
            notificationManager.cancel(intExtra);
        }
        e.ai.remove(stringExtra);
        if (stringExtra4.equals(e.an)) {
            l.a().b(stringExtra, stringExtra2, stringExtra8, stringExtra9, stringExtra5, stringExtra6, stringExtra7);
            b(stringExtra, stringExtra2, stringExtra3);
        }
    }

    public final boolean a(String str, String str2, String str3) {
        if (Thread.currentThread().getId() == d.a.a.a()) {
            return b(str, str2, str3);
        }
        Bundle bundle = new Bundle();
        bundle.putString("taskid", str);
        bundle.putString("messageid", str2);
        bundle.putString("actionid", str3);
        Message messageObtain = Message.obtain();
        messageObtain.what = b.S;
        messageObtain.obj = bundle;
        return d.a.a.a(messageObtain);
    }

    public final boolean a(Map<String, String> map, String str, PushTaskBean pushTaskBean) {
        if (!com.igexin.push.g.c.b(str)) {
            if (map != null && map.size() != 0) {
                if ((!map.containsKey("expireTime") || Long.parseLong(map.get("expireTime")) >= System.currentTimeMillis()) && (!map.containsKey("endTime") || Long.parseLong(map.get("endTime")) >= System.currentTimeMillis())) {
                    if (map.containsKey("wifi")) {
                        int i = Integer.parseInt(map.get("wifi"));
                        com.igexin.push.g.c.c();
                        if (i != e.x) {
                            return false;
                        }
                    }
                    if (map.containsKey("screenOn")) {
                        int i2 = Integer.parseInt(map.get("screenOn"));
                        com.igexin.push.g.c.d();
                        if (i2 != e.y) {
                            return false;
                        }
                    }
                    String str2 = "";
                    if (map.containsKey("ssid")) {
                        str2 = map.get("ssid");
                        try {
                            List<ScanResult> listJ = o.j();
                            this.e.clear();
                            if (listJ != null && !listJ.isEmpty()) {
                                for (int i3 = 0; i3 < listJ.size(); i3++) {
                                    this.e.put(listJ.get(i3).BSSID, listJ.get(i3).SSID);
                                    String str3 = listJ.get(i3).BSSID;
                                    String str4 = listJ.get(i3).SSID;
                                }
                            }
                        } catch (Throwable th) {
                            com.igexin.c.a.c.a.a(th);
                        }
                        if (!this.e.containsValue(str2)) {
                            return false;
                        }
                    }
                    if (map.containsKey("bssid")) {
                        String str5 = map.get("bssid");
                        if (!this.e.containsKey(str5)) {
                            return false;
                        }
                        String str6 = this.e.get(str5);
                        if (str6 != null && !str6.equals(str2)) {
                            return false;
                        }
                    }
                    if (map.containsKey(Constant.START_TIME) && Long.parseLong(map.get(Constant.START_TIME)) > System.currentTimeMillis()) {
                        return false;
                    }
                    if (map.containsKey("netConnected")) {
                        try {
                            if (Integer.parseInt(map.get("netConnected")) != com.igexin.push.g.c.e()) {
                                return false;
                            }
                        } catch (Exception e) {
                            com.igexin.c.a.c.a.a(e);
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        com.igexin.push.core.e.c.a();
        com.igexin.push.core.e.c.a(b.ah, str);
        pushTaskBean.setStatus(b.ag);
        return false;
    }

    public final boolean a(JSONObject jSONObject, PushTaskBean pushTaskBean) {
        com.igexin.c.a.c.a.a("PushMessageExecutor------parse pushmessage actionchain json start-------", new Object[0]);
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray jSONArray = jSONObject.getJSONArray("action_chains");
            for (int i = 0; i < jSONArray.length(); i++) {
                String string = ((JSONObject) jSONArray.get(i)).getString("type");
                if (!this.a.containsKey(string) && !d.contains(string)) {
                    com.igexin.c.a.c.a.a("PushMessageExecutor|" + string + " not support~", new Object[0]);
                    return false;
                }
            }
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                JSONObject jSONObject2 = (JSONObject) jSONArray.get(i2);
                String string2 = jSONObject2.getString("type");
                com.igexin.c.a.c.a.a("PushMessageExecutor|start parse type = ".concat(String.valueOf(string2)), new Object[0]);
                PushMessageInterface pushMessageInterfaceA = a(string2);
                if (pushMessageInterfaceA != null) {
                    arrayList.add(pushMessageInterfaceA.parseAction(jSONObject2));
                }
            }
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
        pushTaskBean.setActionChains(arrayList);
        com.igexin.c.a.c.a.b(b, "------parse pushmessage actionchain json end-------");
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x01af A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01ac A[PHI: r14
      0x01ac: PHI (r14v3 com.igexin.push.extension.mod.BaseActionBean) = 
      (r14v2 com.igexin.push.extension.mod.BaseActionBean)
      (r14v2 com.igexin.push.extension.mod.BaseActionBean)
      (r14v6 com.igexin.push.extension.mod.BaseActionBean)
     binds: [B:42:0x019a, B:44:0x01a0, B:46:0x01a8] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01c8 A[Catch: Throwable -> 0x0250, Exception -> 0x02b4, LOOP:0: B:39:0x0181->B:51:0x01c8, LOOP_END, TryCatch #1 {Throwable -> 0x0250, blocks: (B:28:0x00f8, B:30:0x0101, B:32:0x011a, B:34:0x015d, B:35:0x0165, B:37:0x016d, B:38:0x0170, B:39:0x0181, B:41:0x0187, B:43:0x019c, B:45:0x01a2, B:50:0x01af, B:51:0x01c8, B:52:0x01cb, B:69:0x024c), top: B:99:0x00f8, outer: #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean a(JSONObject jSONObject, byte[] bArr, boolean z) {
        boolean z2;
        PushMessageInterface pushMessageInterfaceA;
        try {
            if (!jSONObject.has("action") || !jSONObject.getString("action").equals(b.E)) {
                return true;
            }
            if (jSONObject.has("appid") && jSONObject.has("messageid") && jSONObject.has("taskid") && (!jSONObject.has("appid") || jSONObject.getString("appid").equals(e.a))) {
                String string = jSONObject.getString(b.C);
                String string2 = jSONObject.getString("appid");
                String string3 = jSONObject.getString("messageid");
                String string4 = jSONObject.getString("taskid");
                String string5 = jSONObject.getString(com.alipay.sdk.sys.a.f);
                JSONArray jSONArray = jSONObject.getJSONArray("action_chains");
                com.igexin.c.a.c.a.a("pushmessage|" + string4 + "|" + string3 + "|" + string2 + "|" + z, new Object[0]);
                final PushTaskBean pushTaskBean = new PushTaskBean();
                pushTaskBean.setAppid(string2);
                pushTaskBean.setMessageId(string3);
                pushTaskBean.setTaskId(string4);
                pushTaskBean.setId(string);
                pushTaskBean.setAppKey(string5);
                pushTaskBean.setCurrentActionid(1);
                com.igexin.push.core.a.b.d();
                String strA = com.igexin.push.core.a.b.a(string4, string3);
                if (z) {
                    FeedbackImpl.getInstance().asyncFeedback(new Runnable() { // from class: com.igexin.push.core.n.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            FeedbackImpl.getInstance().feedbackMessageAction(pushTaskBean, "0");
                        }
                    });
                    if (com.igexin.push.g.c.b(string4)) {
                        com.igexin.c.a.c.a.a("PushMessageExecutor|" + string4 + " in blacklist ###", new Object[0]);
                        return false;
                    }
                    if (com.igexin.push.g.c.a(jSONObject)) {
                        com.igexin.c.a.c.a.a("PushMessageExecutor|message have loop", new Object[0]);
                        return false;
                    }
                    try {
                        com.igexin.push.core.e.c.a();
                        if (com.igexin.push.core.e.c.a(string4)) {
                            com.igexin.c.a.c.a.a(b, "taskid = " + string4 + ", has already process @@####");
                            return false;
                        }
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("messageid", string3);
                        contentValues.put("taskid", string4);
                        contentValues.put("appid", string2);
                        contentValues.put("key", b.k.concat(String.valueOf(strA)));
                        contentValues.put("info", com.igexin.c.b.a.b(jSONObject.toString().getBytes()));
                        contentValues.put("createtime", Long.valueOf(System.currentTimeMillis()));
                        if (bArr != null) {
                            contentValues.put("msgextra", bArr);
                            pushTaskBean.setMsgExtra(bArr);
                        }
                        if (jSONObject.has("condition")) {
                            b(jSONObject, pushTaskBean);
                        }
                        pushTaskBean.setStatus(b.af);
                        contentValues.put("status", Integer.valueOf(b.af));
                        int i = 0;
                        while (true) {
                            if (i >= jSONArray.length()) {
                                break;
                            }
                            JSONObject jSONObject2 = (JSONObject) jSONArray.get(i);
                            String string6 = jSONObject2.getString("type");
                            BaseActionBean action = null;
                            if (!b.n.equals(string6) || (pushMessageInterfaceA = a(string6)) == null) {
                                z2 = false;
                                if (!z2) {
                                    com.igexin.push.core.b.l lVar = (com.igexin.push.core.b.l) action;
                                    contentValues.put("redisplay_freq", Integer.valueOf(lVar.r));
                                    contentValues.put("redisplay_duration", Long.valueOf(lVar.s));
                                    break;
                                }
                                i++;
                            } else {
                                action = pushMessageInterfaceA.parseAction(jSONObject2);
                                if (action instanceof com.igexin.push.core.b.l) {
                                    z2 = true;
                                }
                                if (!z2) {
                                }
                            }
                        }
                        com.igexin.push.core.e.c cVarA = com.igexin.push.core.e.c.a();
                        try {
                            if (cVarA.a == -1) {
                                cVarA.a = com.igexin.push.core.e.c.b();
                            }
                            if (cVarA.a >= 1000) {
                                int iA = d.a.a.i.a(b.Z, "id IN (SELECT id from message where status IS NULL or status=1 or status=2 order by id asc limit 250)");
                                cVarA.a -= iA;
                                if (iA < 250) {
                                    cVarA.a -= d.a.a.i.a(b.Z, "id IN (SELECT id from message where status=0 order by id asc limit " + (b.av - iA) + ")");
                                }
                                if (d.a.a.i.a(b.Z, contentValues) != -1) {
                                    cVarA.a++;
                                }
                            } else if (d.a.a.i.a(b.Z, contentValues) != -1) {
                                cVarA.a++;
                            }
                        } catch (Throwable th) {
                            com.igexin.c.a.c.a.a(th);
                        }
                    } catch (Throwable th2) {
                        com.igexin.c.a.c.a.a(th2);
                    }
                }
                if (jSONArray.length() > 0 && !a(jSONObject, pushTaskBean)) {
                    com.igexin.c.a.c.a.a(b, "parseActionChains result = false #######");
                    com.igexin.c.a.c.a.a("PushMessageExecutor parseActionChains result = false #######", new Object[0]);
                    return false;
                }
                if (!z) {
                    if (jSONObject.has("condition")) {
                        b(jSONObject, pushTaskBean);
                    }
                    e.ah.put(strA, pushTaskBean);
                    return true;
                }
                e.ah.put(strA, pushTaskBean);
                if (com.igexin.push.g.c.a(System.currentTimeMillis())) {
                    com.igexin.c.a.c.a.b(b, "message in silent time, ignored...");
                    return false;
                }
                if (jSONObject.has("condition")) {
                    c();
                    return true;
                }
                a(string4, string3);
                return true;
            }
            com.igexin.c.a.c.a.a("PushMessageExecutor receive error pushmessage", new Object[0]);
            return false;
        } catch (Exception e) {
            com.igexin.c.a.c.a.a(e);
            com.igexin.c.a.c.a.a("PushMessageExecutor " + e.toString(), new Object[0]);
            return true;
        }
    }

    public final PushMessageInterface.ActionPrepareState b(String str, String str2) {
        PushMessageInterface.ActionPrepareState actionPrepareState = PushMessageInterface.ActionPrepareState.success;
        com.igexin.push.core.a.b.d();
        PushTaskBean pushTaskBean = e.ah.get(com.igexin.push.core.a.b.a(str, str2));
        if (pushTaskBean == null) {
            return PushMessageInterface.ActionPrepareState.stop;
        }
        int i = 0;
        PushMessageInterface.ActionPrepareState actionPrepareState2 = actionPrepareState;
        boolean z = false;
        for (BaseActionBean baseActionBean : pushTaskBean.getActionChains()) {
            PushMessageInterface.ActionPrepareState actionPrepareStatePrepareExecuteAction = PushMessageInterface.ActionPrepareState.stop;
            if (baseActionBean == null) {
                return actionPrepareStatePrepareExecuteAction;
            }
            if (!z && b.r.equals(baseActionBean.getType())) {
                z = true;
            }
            PushMessageInterface pushMessageInterfaceA = a(baseActionBean.getType());
            if (pushMessageInterfaceA != null) {
                actionPrepareStatePrepareExecuteAction = pushMessageInterfaceA.prepareExecuteAction(pushTaskBean, baseActionBean);
            } else {
                baseActionBean.getType();
            }
            if (actionPrepareState2 == PushMessageInterface.ActionPrepareState.success) {
                actionPrepareState2 = actionPrepareStatePrepareExecuteAction;
            }
            if (actionPrepareStatePrepareExecuteAction == PushMessageInterface.ActionPrepareState.wait) {
                i++;
            }
        }
        return (i == 0 || z || e.a(str, Integer.valueOf(i))) ? actionPrepareState2 : PushMessageInterface.ActionPrepareState.success;
    }

    public final boolean b(String str, String str2, final String str3) throws Throwable {
        PushMessageInterface pushMessageInterfaceA;
        Cursor cursorA;
        com.igexin.push.core.a.b.d();
        String strA = com.igexin.push.core.a.b.a(str, str2);
        com.igexin.c.a.c.a.b(b, "executePushMessageAction taskid:" + str + ", actionid:" + str3);
        final PushTaskBean pushTaskBean = e.ah.get(strA);
        if (pushTaskBean == null) {
            Cursor cursor = null;
            try {
                try {
                    cursorA = d.a.a.i.a(b.Z, new String[]{"taskid", "messageid"}, new String[]{str, str2}, null, null);
                    if (cursorA != null) {
                        try {
                            if (cursorA.getCount() > 0) {
                                while (cursorA.moveToNext()) {
                                    a().a(new JSONObject(new String(com.igexin.c.b.a.c(cursorA.getBlob(cursorA.getColumnIndexOrThrow("info"))))), cursorA.getBlob(cursorA.getColumnIndexOrThrow("msgextra")), false);
                                    PushTaskBean pushTaskBean2 = e.ah.get(str + ":" + str2);
                                    if (pushTaskBean2 == null) {
                                        if (cursorA != null) {
                                            cursorA.close();
                                        }
                                        return false;
                                    }
                                    pushTaskBean = pushTaskBean2;
                                }
                                if (cursorA != null) {
                                    cursorA.close();
                                }
                            }
                        } catch (Throwable th) {
                            th = th;
                            cursor = cursorA;
                            com.igexin.c.a.c.a.a(th);
                            if (cursor != null) {
                                cursor.close();
                            }
                        }
                    }
                    if (cursorA != null) {
                        cursorA.close();
                    }
                    return false;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                cursorA = cursor;
            }
        }
        int executeTimes = pushTaskBean.getExecuteTimes();
        if (executeTimes >= 50) {
            try {
                e.ah.remove(strA);
            } catch (Exception e) {
                com.igexin.c.a.c.a.a(e);
                com.igexin.c.a.c.a.a("PushMessageExecutor|" + e.toString(), new Object[0]);
            }
            return true;
        }
        pushTaskBean.setExecuteTimes(executeTimes + 1);
        FeedbackImpl.getInstance().asyncFeedback(new Runnable() { // from class: com.igexin.push.core.n.2
            @Override // java.lang.Runnable
            public final void run() {
                FeedbackImpl.getInstance().feedbackMessageAction(pushTaskBean, str3);
            }
        });
        try {
            BaseActionBean baseAction = pushTaskBean.getBaseAction(str3);
            if (baseAction != null && (pushMessageInterfaceA = a(baseAction.getType())) != null) {
                return pushMessageInterfaceA.executeAction(pushTaskBean, baseAction);
            }
        } catch (Throwable th4) {
            com.igexin.c.a.c.a.a(th4);
        }
        return false;
    }

    public final void c() {
        try {
            if (com.igexin.push.g.c.a(System.currentTimeMillis())) {
                com.igexin.c.a.c.a.b(b, "message in silent time , ignored...");
                return;
            }
            if (f()) {
                return;
            }
            for (Map.Entry<String, PushTaskBean> entry : e.ah.entrySet()) {
                try {
                    entry.getKey();
                    PushTaskBean value = entry.getValue();
                    if (value != null && value.getStatus() == b.af) {
                        String taskId = value.getTaskId();
                        Map<String, String> conditionMap = value.getConditionMap();
                        if (conditionMap == null) {
                            return;
                        }
                        if (a(conditionMap, taskId, value)) {
                            a(taskId, value.getMessageId());
                        }
                    }
                } catch (Exception e) {
                    com.igexin.c.a.c.a.a(e);
                    com.igexin.c.a.c.a.a("PushMessageExecutor|" + e.toString(), new Object[0]);
                }
            }
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
            com.igexin.c.a.c.a.a("PushMessageExecutor|" + e2.toString(), new Object[0]);
        }
    }

    public final boolean d() throws Throwable {
        Cursor cursorA;
        Cursor cursor = null;
        try {
            try {
                cursorA = d.a.a.i.a(b.Z, new String[]{"status"}, new String[]{"0"}, null, null);
                if (cursorA != null) {
                    while (cursorA.moveToNext()) {
                        try {
                            byte[] blob = cursorA.getBlob(cursorA.getColumnIndex("msgextra"));
                            try {
                                JSONObject jSONObject = new JSONObject(new String(com.igexin.c.b.a.c(cursorA.getBlob(cursorA.getColumnIndex("info")))));
                                String string = jSONObject.getString(b.C);
                                String string2 = jSONObject.getString("appid");
                                String string3 = jSONObject.getString("messageid");
                                String string4 = jSONObject.getString("taskid");
                                String string5 = jSONObject.getString(com.alipay.sdk.sys.a.f);
                                JSONArray jSONArray = jSONObject.getJSONArray("action_chains");
                                com.igexin.push.core.a.b.d();
                                String strA = com.igexin.push.core.a.b.a(string4, string3);
                                PushTaskBean pushTaskBean = new PushTaskBean();
                                pushTaskBean.setAppid(string2);
                                pushTaskBean.setMessageId(string3);
                                pushTaskBean.setTaskId(string4);
                                pushTaskBean.setId(string);
                                pushTaskBean.setAppKey(string5);
                                pushTaskBean.setCurrentActionid(1);
                                pushTaskBean.setStatus(cursorA.getInt(cursorA.getColumnIndex("status")));
                                if (blob != null) {
                                    pushTaskBean.setMsgExtra(blob);
                                }
                                if (jSONObject.has("condition")) {
                                    b(jSONObject, pushTaskBean);
                                }
                                if (jSONArray.length() > 0) {
                                    if (!a(jSONObject, pushTaskBean)) {
                                        com.igexin.c.a.c.a.a(b, "load task from db parseActionChains error because load gkt, " + jSONObject.toString());
                                        com.igexin.c.a.c.a.a("PushMessageExecutor|load task from db parseActionChains error because load gkt, " + jSONObject.toString(), new Object[0]);
                                    } else if (!e.ah.containsKey(strA)) {
                                        e.ah.put(strA, pushTaskBean);
                                    }
                                }
                            } catch (JSONException e) {
                                com.igexin.c.a.c.a.a(e);
                            }
                        } catch (Throwable th) {
                            th = th;
                            cursor = cursorA;
                            com.igexin.c.a.c.a.a(th);
                            if (cursor != null) {
                                cursor.close();
                            }
                            return e.ah.isEmpty();
                        }
                    }
                }
                if (cursorA != null) {
                    cursorA.close();
                }
            } catch (Throwable th2) {
                th = th2;
                cursorA = cursor;
            }
        } catch (Throwable th3) {
            th = th3;
        }
        return e.ah.isEmpty();
    }
}
