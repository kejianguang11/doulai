package com.getui.gtc.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.SparseArray;
import com.igexin.push.core.b;
import com.mobile.auth.gatewayauth.Constant;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class a {
    public final SparseArray<C0020a> a = new SparseArray<>();
    public boolean b = false;
    private String c;

    /* JADX INFO: renamed from: com.getui.gtc.entity.a$a, reason: collision with other inner class name */
    public static class C0020a implements Parcelable {
        public static final Parcelable.Creator<C0020a> CREATOR = new Parcelable.Creator<C0020a>() { // from class: com.getui.gtc.entity.a.a.1
            @Override // android.os.Parcelable.Creator
            public final /* synthetic */ C0020a createFromParcel(Parcel parcel) {
                return new C0020a(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final /* bridge */ /* synthetic */ C0020a[] newArray(int i) {
                return new C0020a[i];
            }
        };
        public int a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public long g;
        public String h;
        public boolean i;
        public boolean j;

        public C0020a() {
        }

        protected C0020a(Parcel parcel) {
            this.a = parcel.readInt();
            this.b = parcel.readString();
            this.c = parcel.readString();
            this.d = parcel.readString();
            this.e = parcel.readString();
            this.f = parcel.readString();
            this.g = parcel.readLong();
            this.h = parcel.readString();
            this.i = parcel.readByte() != 0;
            this.j = parcel.readByte() != 0;
        }

        public final String a() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(b.C, this.a);
                jSONObject.put("version", this.b);
                jSONObject.put("name", this.c);
                jSONObject.put("cls_name", this.d);
                jSONObject.put(Constant.PROTOCOL_WEB_VIEW_URL, this.h);
                jSONObject.put("isdestroy", this.i);
                jSONObject.put("effective", String.valueOf(this.g));
                jSONObject.put("key", this.f);
                jSONObject.put("checksum", this.e);
            } catch (Exception e) {
                com.getui.gtc.i.c.a.b(e);
            }
            return jSONObject.toString();
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.a);
            parcel.writeString(this.b);
            parcel.writeString(this.c);
            parcel.writeString(this.d);
            parcel.writeString(this.e);
            parcel.writeString(this.f);
            parcel.writeLong(this.g);
            parcel.writeString(this.h);
            parcel.writeByte(this.i ? (byte) 1 : (byte) 0);
            parcel.writeByte(this.j ? (byte) 1 : (byte) 0);
        }
    }

    public static a a(Map<String, String> map) {
        a aVar;
        String str = map.get("ext_infos");
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            aVar = new a();
            aVar.c = jSONObject.getString("version");
            String str2 = map.get("sdk.gtc.gws.load.enable");
            if (!TextUtils.isEmpty(str2) && !com.igexin.push.a.i.equals(str2)) {
                aVar.b = Boolean.parseBoolean(str2);
            }
            JSONArray jSONArray = jSONObject.getJSONArray("extensions");
            if (jSONArray.length() > 0) {
                int length = jSONArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    C0020a c0020a = new C0020a();
                    c0020a.a = jSONObject2.getInt(b.C);
                    c0020a.b = jSONObject2.getString("version");
                    c0020a.c = jSONObject2.getString("name");
                    c0020a.d = jSONObject2.getString("cls_name");
                    c0020a.h = jSONObject2.getString(Constant.PROTOCOL_WEB_VIEW_URL);
                    c0020a.e = jSONObject2.getString("checksum");
                    c0020a.f = jSONObject2.getString("key");
                    if (jSONObject2.has("isdestroy")) {
                        c0020a.i = jSONObject2.getBoolean("isdestroy");
                    }
                    if (jSONObject2.has("effective")) {
                        long j = 0;
                        try {
                            j = 1000 * Long.parseLong(jSONObject2.getString("effective"));
                        } catch (Exception e) {
                            com.getui.gtc.i.c.a.c(e);
                        }
                        c0020a.g = j;
                    }
                    aVar.a.put(c0020a.a, c0020a);
                }
            }
        } catch (Throwable th) {
            com.getui.gtc.i.c.a.c(th);
            aVar = null;
        }
        String str3 = map.get("sdk.push.plugins");
        if (aVar != null && !TextUtils.isEmpty(str3)) {
            for (String str4 : str3.split(b.an)) {
                try {
                    C0020a c0020aB = aVar.b(Integer.parseInt(str4));
                    if (c0020aB != null) {
                        c0020aB.j = true;
                    }
                } catch (Exception unused) {
                }
            }
        }
        return aVar;
    }

    public final C0020a a(int i) {
        return this.a.get(this.a.keyAt(i));
    }

    public final String a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("version", this.c);
            JSONArray jSONArray = new JSONArray();
            jSONObject.put("extensions", jSONArray);
            int size = this.a.size();
            for (int i = 0; i < size; i++) {
                jSONArray.put(i, new JSONObject(this.a.get(this.a.keyAt(i)).a()));
            }
        } catch (Exception e) {
            com.getui.gtc.i.c.a.b(e);
        }
        return jSONObject.toString();
    }

    public final C0020a b(int i) {
        return this.a.get(i);
    }

    public final void c(int i) {
        this.a.removeAt(i);
    }
}
