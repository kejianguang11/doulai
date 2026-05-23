package com.tencent.mm.opensdk.channel.a;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;

/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: com.tencent.mm.opensdk.channel.a.a$a, reason: collision with other inner class name */
    public static class C0055a {
        public String a;
        public String b;
        public String c;
        public long d;
        public Bundle e;
    }

    public static int a(Bundle bundle, String str, int i) {
        if (bundle == null) {
            return i;
        }
        try {
            return bundle.getInt(str, i);
        } catch (Exception e) {
            Log.e("MicroMsg.IntentUtil", "getIntExtra exception:" + e.getMessage());
            return i;
        }
    }

    public static Object a(int i, String str) {
        try {
            switch (i) {
                case 1:
                    return Integer.valueOf(str);
                case 2:
                    return Long.valueOf(str);
                case 3:
                    return str;
                case 4:
                    return Boolean.valueOf(str);
                case 5:
                    return Float.valueOf(str);
                case 6:
                    return Double.valueOf(str);
                default:
                    Log.e("MicroMsg.SDK.PluginProvider.Resolver", "unknown type");
                    return null;
            }
        } catch (Exception e) {
            Log.e("MicroMsg.SDK.PluginProvider.Resolver", "resolveObj exception:" + e.getMessage());
            return null;
        }
    }

    public static String a(Bundle bundle, String str) {
        if (bundle == null) {
            return null;
        }
        try {
            return bundle.getString(str);
        } catch (Exception e) {
            Log.e("MicroMsg.IntentUtil", "getStringExtra exception:" + e.getMessage());
            return null;
        }
    }

    public static boolean a(Context context, C0055a c0055a) {
        String str;
        String str2;
        if (context == null || c0055a == null) {
            str = "MicroMsg.SDK.MMessage";
            str2 = "send fail, invalid argument";
        } else {
            if (!b.b(c0055a.b)) {
                String str3 = null;
                if (!b.b(c0055a.a)) {
                    str3 = c0055a.a + ".permission.MM_MESSAGE";
                }
                Intent intent = new Intent(c0055a.b);
                Bundle bundle = c0055a.e;
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                String packageName = context.getPackageName();
                intent.putExtra(ConstantsAPI.SDK_VERSION, Build.SDK_INT);
                intent.putExtra(ConstantsAPI.APP_PACKAGE, packageName);
                intent.putExtra(ConstantsAPI.CONTENT, c0055a.c);
                intent.putExtra(ConstantsAPI.APP_SUPORT_CONTENT_TYPE, c0055a.d);
                intent.putExtra(ConstantsAPI.CHECK_SUM, a(c0055a.c, Build.SDK_INT, packageName));
                context.sendBroadcast(intent, str3);
                Log.d("MicroMsg.SDK.MMessage", "send mm message, intent=" + intent + ", perm=" + str3);
                return true;
            }
            str = "MicroMsg.SDK.MMessage";
            str2 = "send fail, action is null";
        }
        Log.e(str, str2);
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 2, insn: 0x0158: MOVE (r0 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:104:0x0158 */
    /* JADX WARN: Removed duplicated region for block: B:131:0x011f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0124 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:141:0x00eb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:143:0x00f0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:145:0x00f5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:163:0x011a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v23 */
    /* JADX WARN: Type inference failed for: r1v31 */
    /* JADX WARN: Type inference failed for: r1v35 */
    /* JADX WARN: Type inference failed for: r7v2, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r7v28 */
    /* JADX WARN: Type inference failed for: r7v29 */
    /* JADX WARN: Type inference failed for: r7v30 */
    /* JADX WARN: Type inference failed for: r7v31 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static byte[] a(String str, int i) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        ?? r7;
        ?? r1;
        Exception e;
        HttpURLConnection httpURLConnection;
        InputStream inputStream;
        IOException e2;
        HttpURLConnection httpURLConnection2;
        InputStream inputStream2;
        MalformedURLException e3;
        HttpURLConnection httpURLConnection3;
        InputStream inputStream3;
        ByteArrayOutputStream byteArrayOutputStream2;
        ByteArrayOutputStream byteArrayOutputStream3;
        ByteArrayOutputStream byteArrayOutputStream4;
        HttpURLConnection httpURLConnection4;
        HttpURLConnection httpURLConnection5;
        HttpURLConnection httpURLConnection6;
        HttpURLConnection httpURLConnection7;
        ByteArrayOutputStream byteArrayOutputStream5;
        ByteArrayOutputStream byteArrayOutputStream6 = null;
        if (str != null) {
            int length = str.length();
            try {
                if (length != 0) {
                    try {
                        httpURLConnection7 = (HttpURLConnection) new URL(str).openConnection();
                        try {
                            try {
                            } catch (Throwable th) {
                                th = th;
                                r1 = 0;
                                r7 = httpURLConnection7;
                            }
                        } catch (MalformedURLException e4) {
                            e3 = e4;
                            inputStream3 = null;
                            byteArrayOutputStream4 = null;
                            httpURLConnection6 = httpURLConnection7;
                        } catch (IOException e5) {
                            e2 = e5;
                            inputStream2 = null;
                            byteArrayOutputStream3 = null;
                            httpURLConnection5 = httpURLConnection7;
                        } catch (Exception e6) {
                            e = e6;
                            inputStream = null;
                            byteArrayOutputStream2 = null;
                            httpURLConnection4 = httpURLConnection7;
                        }
                    } catch (MalformedURLException e7) {
                        e3 = e7;
                        httpURLConnection3 = null;
                        inputStream3 = null;
                    } catch (IOException e8) {
                        e2 = e8;
                        httpURLConnection2 = null;
                        inputStream2 = null;
                    } catch (Exception e9) {
                        e = e9;
                        httpURLConnection = null;
                        inputStream = null;
                    } catch (Throwable th2) {
                        th = th2;
                        r7 = 0;
                        r1 = 0;
                    }
                    if (httpURLConnection7 == null) {
                        Log.e("MicroMsg.SDK.NetUtil", "open connection failed.");
                        if (httpURLConnection7 != null) {
                            try {
                                httpURLConnection7.disconnect();
                            } catch (Throwable unused) {
                            }
                        }
                        return null;
                    }
                    try {
                        httpURLConnection7.setRequestMethod("GET");
                        httpURLConnection7.setConnectTimeout(i);
                        httpURLConnection7.setReadTimeout(i);
                        if (httpURLConnection7.getResponseCode() >= 300) {
                            Log.e("MicroMsg.SDK.NetUtil", "httpURLConnectionGet 300");
                            try {
                                httpURLConnection7.disconnect();
                            } catch (Throwable unused2) {
                            }
                            return null;
                        }
                        InputStream inputStream4 = httpURLConnection7.getInputStream();
                        try {
                            byteArrayOutputStream5 = new ByteArrayOutputStream();
                        } catch (MalformedURLException e10) {
                            inputStream3 = inputStream4;
                            e3 = e10;
                            httpURLConnection3 = httpURLConnection7;
                            byteArrayOutputStream4 = null;
                            httpURLConnection6 = httpURLConnection3;
                        } catch (IOException e11) {
                            inputStream2 = inputStream4;
                            e2 = e11;
                            httpURLConnection2 = httpURLConnection7;
                            byteArrayOutputStream3 = null;
                            httpURLConnection5 = httpURLConnection2;
                            Log.e("MicroMsg.SDK.NetUtil", "httpGet ex:" + e2.getMessage());
                            if (httpURLConnection5 != null) {
                                try {
                                    httpURLConnection5.disconnect();
                                } catch (Throwable unused3) {
                                }
                            }
                            if (inputStream2 != null) {
                                try {
                                    inputStream2.close();
                                } catch (Throwable unused4) {
                                }
                            }
                            if (byteArrayOutputStream3 != null) {
                                try {
                                    byteArrayOutputStream3.close();
                                } catch (Throwable unused5) {
                                }
                            }
                            return null;
                        } catch (Exception e12) {
                            inputStream = inputStream4;
                            e = e12;
                            httpURLConnection = httpURLConnection7;
                            byteArrayOutputStream2 = null;
                            httpURLConnection4 = httpURLConnection;
                            Log.e("MicroMsg.SDK.NetUtil", "httpGet ex:" + e.getMessage());
                            if (httpURLConnection4 != null) {
                                try {
                                    httpURLConnection4.disconnect();
                                } catch (Throwable unused6) {
                                }
                            }
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (Throwable unused7) {
                                }
                            }
                            if (byteArrayOutputStream2 != null) {
                                try {
                                    byteArrayOutputStream2.close();
                                } catch (Throwable unused8) {
                                }
                            }
                            return null;
                        } catch (Throwable th3) {
                            r1 = inputStream4;
                            th = th3;
                            r7 = httpURLConnection7;
                        }
                        try {
                            byte[] bArr = new byte[1024];
                            while (true) {
                                int i2 = inputStream4.read(bArr);
                                if (i2 == -1) {
                                    break;
                                }
                                byteArrayOutputStream5.write(bArr, 0, i2);
                            }
                            byte[] byteArray = byteArrayOutputStream5.toByteArray();
                            Log.d("MicroMsg.SDK.NetUtil", "httpGet end");
                            try {
                                httpURLConnection7.disconnect();
                            } catch (Throwable unused9) {
                            }
                            try {
                                inputStream4.close();
                            } catch (Throwable unused10) {
                            }
                            try {
                                byteArrayOutputStream5.close();
                            } catch (Throwable unused11) {
                            }
                            return byteArray;
                        } catch (MalformedURLException e13) {
                            inputStream3 = inputStream4;
                            e3 = e13;
                            byteArrayOutputStream4 = byteArrayOutputStream5;
                            httpURLConnection6 = httpURLConnection7;
                        } catch (IOException e14) {
                            inputStream2 = inputStream4;
                            e2 = e14;
                            byteArrayOutputStream3 = byteArrayOutputStream5;
                            httpURLConnection5 = httpURLConnection7;
                            Log.e("MicroMsg.SDK.NetUtil", "httpGet ex:" + e2.getMessage());
                            if (httpURLConnection5 != null) {
                            }
                            if (inputStream2 != null) {
                            }
                            if (byteArrayOutputStream3 != null) {
                            }
                            return null;
                        } catch (Exception e15) {
                            inputStream = inputStream4;
                            e = e15;
                            byteArrayOutputStream2 = byteArrayOutputStream5;
                            httpURLConnection4 = httpURLConnection7;
                            Log.e("MicroMsg.SDK.NetUtil", "httpGet ex:" + e.getMessage());
                            if (httpURLConnection4 != null) {
                            }
                            if (inputStream != null) {
                            }
                            if (byteArrayOutputStream2 != null) {
                            }
                            return null;
                        } catch (Throwable th4) {
                            r1 = inputStream4;
                            th = th4;
                            byteArrayOutputStream6 = byteArrayOutputStream5;
                            r7 = httpURLConnection7;
                            if (r7 != 0) {
                                try {
                                    r7.disconnect();
                                } catch (Throwable unused12) {
                                }
                            }
                            if (r1 != 0) {
                                try {
                                    r1.close();
                                } catch (Throwable unused13) {
                                }
                            }
                            if (byteArrayOutputStream6 == null) {
                                throw th;
                            }
                            try {
                                byteArrayOutputStream6.close();
                                throw th;
                            } catch (Throwable unused14) {
                                throw th;
                            }
                        }
                    } catch (MalformedURLException e16) {
                        e3 = e16;
                        inputStream3 = null;
                        httpURLConnection3 = httpURLConnection7;
                    } catch (IOException e17) {
                        e2 = e17;
                        inputStream2 = null;
                        httpURLConnection2 = httpURLConnection7;
                    } catch (Exception e18) {
                        e = e18;
                        inputStream = null;
                        httpURLConnection = httpURLConnection7;
                    }
                    byteArrayOutputStream4 = null;
                    httpURLConnection6 = httpURLConnection3;
                    Log.e("MicroMsg.SDK.NetUtil", "httpGet ex:" + e3.getMessage());
                    if (httpURLConnection6 != null) {
                        try {
                            httpURLConnection6.disconnect();
                        } catch (Throwable unused15) {
                        }
                    }
                    if (inputStream3 != null) {
                        try {
                            inputStream3.close();
                        } catch (Throwable unused16) {
                        }
                    }
                    if (byteArrayOutputStream4 != null) {
                        try {
                            byteArrayOutputStream4.close();
                        } catch (Throwable unused17) {
                        }
                    }
                    return null;
                }
            } catch (Throwable th5) {
                th = th5;
                byteArrayOutputStream6 = byteArrayOutputStream;
                r1 = length;
                r7 = str;
            }
        }
        Log.e("MicroMsg.SDK.NetUtil", "httpGet, url is null");
        return null;
    }

    public static byte[] a(String str, int i, String str2) {
        String str3;
        StringBuffer stringBuffer = new StringBuffer();
        if (str != null) {
            stringBuffer.append(str);
        }
        stringBuffer.append(i);
        stringBuffer.append(str2);
        stringBuffer.append("mMcShCsTr");
        byte[] bytes = stringBuffer.toString().substring(1, 9).getBytes();
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);
            byte[] bArrDigest = messageDigest.digest();
            char[] cArr2 = new char[bArrDigest.length * 2];
            int i2 = 0;
            for (byte b : bArrDigest) {
                int i3 = i2 + 1;
                cArr2[i2] = cArr[(b >>> 4) & 15];
                i2 = i3 + 1;
                cArr2[i3] = cArr[b & 15];
            }
            str3 = new String(cArr2);
        } catch (Exception unused) {
            str3 = null;
        }
        return str3.getBytes();
    }
}
