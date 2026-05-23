package com.loc;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import com.amap.api.location.AMapLocationClientOption;
import com.mobile.auth.gatewayauth.Constant;
import java.nio.ByteBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class fg {
    private StringBuilder a = new StringBuilder();
    private AMapLocationClientOption b = new AMapLocationClientOption();

    private void a(eo eoVar, String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
            sb.append(" ");
        }
        if (!TextUtils.isEmpty(str2)) {
            a(str, str2, sb);
        }
        if (!TextUtils.isEmpty(str3)) {
            sb.append(str3);
            sb.append(" ");
        }
        if (!TextUtils.isEmpty(str4)) {
            sb.append(str4);
            sb.append(" ");
        }
        if (!TextUtils.isEmpty(str5)) {
            sb.append(str5);
            sb.append(" ");
        }
        if (!TextUtils.isEmpty(str6)) {
            a(str7, str6, sb, eoVar);
        }
        Bundle bundle = new Bundle();
        bundle.putString("citycode", eoVar.getCityCode());
        bundle.putString("desc", sb.toString());
        bundle.putString("adcode", eoVar.getAdCode());
        eoVar.setExtras(bundle);
        eoVar.g(sb.toString());
        String adCode = eoVar.getAdCode();
        eoVar.setAddress((adCode == null || adCode.trim().length() <= 0 || this.b.getGeoLanguage() == AMapLocationClientOption.GeoLanguage.EN) ? sb.toString() : sb.toString().replace(" ", ""));
    }

    private static void a(eo eoVar, short s) {
        if ("-1".equals(eoVar.d())) {
            if (s == 101) {
                s = 100;
            }
            eoVar.setConScenario(s);
        } else {
            if (s == -1) {
                s = 0;
            } else if (s == 0) {
                s = -1;
            }
            eoVar.setConScenario(s);
        }
    }

    private void a(String str, String str2, StringBuilder sb) {
        if (this.b.getGeoLanguage() == AMapLocationClientOption.GeoLanguage.EN) {
            if (str2.equals(str)) {
                return;
            }
            sb.append(str2);
            sb.append(" ");
            return;
        }
        if (str.contains("市") && str.equals(str2)) {
            return;
        }
        sb.append(str2);
        sb.append(" ");
    }

    private void a(String str, String str2, StringBuilder sb, eo eoVar) {
        String strConcat;
        if (TextUtils.isEmpty(str) || this.b.getGeoLanguage() == AMapLocationClientOption.GeoLanguage.EN) {
            sb.append("Near ".concat(String.valueOf(str2)));
            strConcat = "Near ".concat(String.valueOf(str2));
        } else {
            sb.append("靠近");
            sb.append(str2);
            sb.append(" ");
            strConcat = "在" + str2 + "附近";
        }
        eoVar.setDescription(strConcat);
    }

    private static String b(String str) {
        return "[]".equals(str) ? "" : str;
    }

    /* JADX WARN: Removed duplicated region for block: B:114:0x02b9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final eo a(eo eoVar, byte[] bArr, ei eiVar) {
        ByteBuffer byteBuffer;
        ByteBuffer byteBufferWrap;
        byte b;
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        eo eoVar2 = eoVar;
        try {
        } catch (Throwable th) {
            th = th;
            byteBuffer = null;
        }
        if (bArr == null) {
            eoVar2.setErrorCode(5);
            eiVar.f("#0504");
            this.a.append("binaryResult is null#0504");
            eoVar2.setLocationDetail(this.a.toString());
            this.a.delete(0, this.a.length());
            return eoVar2;
        }
        byteBufferWrap = ByteBuffer.wrap(bArr);
        try {
            try {
            } catch (Throwable th2) {
                th = th2;
                byteBuffer = byteBufferWrap;
                try {
                    eo eoVar3 = new eo("");
                    eoVar3.setErrorCode(5);
                    eiVar.f("#0505");
                    this.a.append("parser data error:" + th.getMessage() + "#0505");
                    fo.a((String) null, 2054);
                    eoVar3.setLocationDetail(this.a.toString());
                    if (byteBuffer != null) {
                        byteBuffer.clear();
                    }
                    eoVar2 = eoVar3;
                } catch (Throwable th3) {
                    th = th3;
                    byteBufferWrap = byteBuffer;
                    if (byteBufferWrap != null) {
                        byteBufferWrap.clear();
                    }
                    throw th;
                }
            }
            if (byteBufferWrap.get() == 0) {
                eoVar2.b(String.valueOf((int) byteBufferWrap.getShort()));
                byteBufferWrap.clear();
                if (byteBufferWrap != null) {
                    byteBufferWrap.clear();
                }
                return eoVar2;
            }
            eoVar2.setLongitude(fq.a(((double) byteBufferWrap.getInt()) / 1000000.0d));
            eoVar2.setLatitude(fq.a(((double) byteBufferWrap.getInt()) / 1000000.0d));
            eoVar2.setAccuracy(byteBufferWrap.getShort());
            eoVar2.c(String.valueOf((int) byteBufferWrap.get()));
            eoVar2.d(String.valueOf((int) byteBufferWrap.get()));
            if (byteBufferWrap.get() == 1) {
                String str8 = "";
                String str9 = "";
                byte[] bArr2 = new byte[byteBufferWrap.get() & 255];
                byteBufferWrap.get(bArr2);
                try {
                    eoVar2.setCountry(new String(bArr2, com.alipay.sdk.sys.a.m));
                } catch (Throwable unused) {
                }
                byte[] bArr3 = new byte[byteBufferWrap.get() & 255];
                byteBufferWrap.get(bArr3);
                try {
                    str = new String(bArr3, com.alipay.sdk.sys.a.m);
                    try {
                        eoVar2.setProvince(str);
                    } catch (Throwable unused2) {
                        str8 = str;
                        str = str8;
                    }
                } catch (Throwable unused3) {
                }
                byte[] bArr4 = new byte[byteBufferWrap.get() & 255];
                byteBufferWrap.get(bArr4);
                try {
                    str2 = new String(bArr4, com.alipay.sdk.sys.a.m);
                    try {
                        eoVar2.setCity(str2);
                    } catch (Throwable unused4) {
                        str9 = str2;
                        str2 = str9;
                    }
                } catch (Throwable unused5) {
                }
                byte[] bArr5 = new byte[byteBufferWrap.get() & 255];
                byteBufferWrap.get(bArr5);
                try {
                    str3 = new String(bArr5, com.alipay.sdk.sys.a.m);
                    try {
                        eoVar2.setDistrict(str3);
                    } catch (Throwable unused6) {
                    }
                } catch (Throwable unused7) {
                    str3 = "";
                }
                String str10 = str3;
                byte[] bArr6 = new byte[byteBufferWrap.get() & 255];
                byteBufferWrap.get(bArr6);
                try {
                    str4 = new String(bArr6, com.alipay.sdk.sys.a.m);
                    try {
                        eoVar2.setStreet(str4);
                        eoVar2.setRoad(str4);
                    } catch (Throwable unused8) {
                    }
                } catch (Throwable unused9) {
                    str4 = "";
                }
                String str11 = str4;
                byte[] bArr7 = new byte[byteBufferWrap.get() & 255];
                byteBufferWrap.get(bArr7);
                try {
                    str5 = new String(bArr7, com.alipay.sdk.sys.a.m);
                    try {
                        eoVar2.setNumber(str5);
                    } catch (Throwable unused10) {
                    }
                } catch (Throwable unused11) {
                    str5 = "";
                }
                String str12 = str5;
                byte[] bArr8 = new byte[byteBufferWrap.get() & 255];
                byteBufferWrap.get(bArr8);
                try {
                    str6 = new String(bArr8, com.alipay.sdk.sys.a.m);
                    try {
                        eoVar2.setPoiName(str6);
                    } catch (Throwable unused12) {
                    }
                } catch (Throwable unused13) {
                    str6 = "";
                }
                String str13 = str6;
                byte[] bArr9 = new byte[byteBufferWrap.get() & 255];
                byteBufferWrap.get(bArr9);
                try {
                    eoVar2.setAoiName(new String(bArr9, com.alipay.sdk.sys.a.m));
                } catch (Throwable unused14) {
                }
                byte[] bArr10 = new byte[byteBufferWrap.get() & 255];
                byteBufferWrap.get(bArr10);
                try {
                    str7 = new String(bArr10, com.alipay.sdk.sys.a.m);
                    try {
                        eoVar2.setAdCode(str7);
                    } catch (Throwable unused15) {
                    }
                } catch (Throwable unused16) {
                    str7 = "";
                }
                String str14 = str7;
                byte[] bArr11 = new byte[byteBufferWrap.get() & 255];
                byteBufferWrap.get(bArr11);
                try {
                    eoVar2.setCityCode(new String(bArr11, com.alipay.sdk.sys.a.m));
                } catch (Throwable unused17) {
                }
                b = 1;
                a(eoVar, str, str2, str10, str11, str12, str13, str14);
            } else {
                b = 1;
            }
            byteBufferWrap.get(new byte[byteBufferWrap.get() & 255]);
            if (byteBufferWrap.get() == b) {
                byteBufferWrap.getInt();
                byteBufferWrap.getInt();
                byteBufferWrap.getShort();
            }
            if (byteBufferWrap.get() == b) {
                byte[] bArr12 = new byte[byteBufferWrap.get() & 255];
                byteBufferWrap.get(bArr12);
                try {
                    eoVar2.setBuildingId(new String(bArr12, com.alipay.sdk.sys.a.m));
                } catch (Throwable unused18) {
                }
                byte[] bArr13 = new byte[byteBufferWrap.get() & 255];
                byteBufferWrap.get(bArr13);
                try {
                    eoVar2.setFloor(new String(bArr13, com.alipay.sdk.sys.a.m));
                } catch (Throwable unused19) {
                }
            }
            if (byteBufferWrap.get() == b) {
                byteBufferWrap.get();
                byteBufferWrap.getInt();
                byteBufferWrap.get();
            }
            if (byteBufferWrap.get() == b) {
                eoVar2.setTime(byteBufferWrap.getLong());
            }
            int i = byteBufferWrap.getShort();
            if (i > 0) {
                byte[] bArr14 = new byte[i];
                byteBufferWrap.get(bArr14);
                try {
                    if (bArr14.length > 0) {
                        eoVar2.a(new String(Base64.decode(bArr14, 0), com.alipay.sdk.sys.a.m));
                    }
                } catch (Throwable unused20) {
                }
            }
            int i2 = byteBufferWrap.getShort();
            if (i2 > 0) {
                byteBufferWrap.get(new byte[i2]);
            }
            if (Double.valueOf(fj.a).doubleValue() >= 5.1d) {
                a(eoVar2, byteBufferWrap.getShort());
                eoVar2.a(byteBufferWrap.get());
            }
            if (byteBufferWrap != null) {
                byteBufferWrap.clear();
            }
            if (this.a.length() > 0) {
                this.a.delete(0, this.a.length());
            }
            return eoVar2;
        } catch (Throwable th4) {
            th = th4;
            if (byteBufferWrap != null) {
            }
            throw th;
        }
    }

    public final eo a(String str) {
        String strB;
        String str2;
        String str3;
        try {
            eo eoVar = new eo("");
            JSONObject jSONObjectOptJSONObject = new JSONObject(str).optJSONObject("regeocode");
            JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject("addressComponent");
            eoVar.setCountry(b(jSONObjectOptJSONObject2.optString("country")));
            String strB2 = b(jSONObjectOptJSONObject2.optString("province"));
            eoVar.setProvince(strB2);
            String strB3 = b(jSONObjectOptJSONObject2.optString("citycode"));
            eoVar.setCityCode(strB3);
            String strOptString = jSONObjectOptJSONObject2.optString("city");
            if (!strB3.endsWith("010") && !strB3.endsWith("021") && !strB3.endsWith("022") && !strB3.endsWith("023")) {
                strB = b(strOptString);
                eoVar.setCity(strB);
            } else if (strB2 == null || strB2.length() <= 0) {
                strB = strOptString;
            } else {
                eoVar.setCity(strB2);
                strB = strB2;
            }
            if (TextUtils.isEmpty(strB)) {
                eoVar.setCity(strB2);
                str2 = strB2;
            } else {
                str2 = strB;
            }
            String strB4 = b(jSONObjectOptJSONObject2.optString("district"));
            eoVar.setDistrict(strB4);
            String strB5 = b(jSONObjectOptJSONObject2.optString("adcode"));
            eoVar.setAdCode(strB5);
            JSONObject jSONObjectOptJSONObject3 = jSONObjectOptJSONObject2.optJSONObject("streetNumber");
            String strB6 = b(jSONObjectOptJSONObject3.optString("street"));
            eoVar.setStreet(strB6);
            eoVar.setRoad(strB6);
            String strB7 = b(jSONObjectOptJSONObject3.optString(Constant.LOGIN_ACTIVITY_NUMBER));
            eoVar.setNumber(strB7);
            JSONArray jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("pois");
            if (jSONArrayOptJSONArray.length() > 0) {
                String strB8 = b(jSONArrayOptJSONArray.getJSONObject(0).optString("name"));
                eoVar.setPoiName(strB8);
                str3 = strB8;
            } else {
                str3 = null;
            }
            JSONArray jSONArrayOptJSONArray2 = jSONObjectOptJSONObject.optJSONArray("aois");
            if (jSONArrayOptJSONArray2.length() > 0) {
                eoVar.setAoiName(b(jSONArrayOptJSONArray2.getJSONObject(0).optString("name")));
            }
            a(eoVar, strB2, str2, strB4, strB6, strB7, str3, strB5);
            return eoVar;
        } catch (Throwable unused) {
            return null;
        }
    }

    public final eo a(String str, Context context, bm bmVar, ei eiVar) {
        eo eoVar = new eo("");
        eoVar.setErrorCode(7);
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append("#SHA1AndPackage#");
            stringBuffer.append(l.e(context));
            String str2 = bmVar.b.get("gsid").get(0);
            if (!TextUtils.isEmpty(str2)) {
                stringBuffer.append("#gsid#");
                stringBuffer.append(str2);
            }
            String str3 = bmVar.c;
            if (!TextUtils.isEmpty(str3)) {
                stringBuffer.append("#csid#".concat(String.valueOf(str3)));
            }
        } catch (Throwable unused) {
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.has("status") || !jSONObject.has("info")) {
                eiVar.f("#0702");
                StringBuilder sb = this.a;
                sb.append("json is error:");
                sb.append(str);
                sb.append(stringBuffer);
                sb.append("#0702");
            }
            String string = jSONObject.getString("status");
            String string2 = jSONObject.getString("info");
            String string3 = jSONObject.getString("infocode");
            if ("0".equals(string)) {
                eiVar.f("#0701");
                StringBuilder sb2 = this.a;
                sb2.append("auth fail:");
                sb2.append(string2);
                sb2.append(stringBuffer);
                sb2.append("#0701");
                fo.a(bmVar.d, string3, string2);
            }
        } catch (Throwable th) {
            eiVar.f("#0703");
            StringBuilder sb3 = this.a;
            sb3.append("json exception error:");
            sb3.append(th.getMessage());
            sb3.append(stringBuffer);
            sb3.append("#0703");
            fj.a(th, "parser", "paseAuthFailurJson");
        }
        eoVar.setLocationDetail(this.a.toString());
        if (this.a.length() > 0) {
            this.a.delete(0, this.a.length());
        }
        return eoVar;
    }

    public final void a(AMapLocationClientOption aMapLocationClientOption) {
        if (aMapLocationClientOption == null) {
            this.b = new AMapLocationClientOption();
        } else {
            this.b = aMapLocationClientOption;
        }
    }
}
