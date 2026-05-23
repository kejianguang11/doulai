package com.tencent.mm.opensdk.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class b {
    public static Context a;
    private static final int b;
    private static final int c;
    private static final int d;
    public static ThreadPoolExecutor e;

    static {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        b = iAvailableProcessors;
        c = iAvailableProcessors + 1;
        d = (iAvailableProcessors * 2) + 1;
        e = new ThreadPoolExecutor(c, d, 1L, TimeUnit.SECONDS, new LinkedBlockingDeque());
    }

    public static int a(ContentResolver contentResolver, Uri uri) throws Throwable {
        Log.i("MicroMsg.SDK.Util", "getFileSize with content url");
        if (contentResolver == null || uri == null) {
            Log.w("MicroMsg.SDK.Util", "getFileSize fail, resolver or uri is null");
            return 0;
        }
        InputStream inputStream = null;
        try {
            try {
                InputStream inputStreamOpenInputStream = contentResolver.openInputStream(uri);
                if (inputStreamOpenInputStream == null) {
                    if (inputStreamOpenInputStream != null) {
                        try {
                            inputStreamOpenInputStream.close();
                        } catch (IOException unused) {
                        }
                    }
                    return 0;
                }
                try {
                    int iAvailable = inputStreamOpenInputStream.available();
                    try {
                        inputStreamOpenInputStream.close();
                    } catch (IOException unused2) {
                    }
                    return iAvailable;
                } catch (Exception e2) {
                    e = e2;
                    inputStream = inputStreamOpenInputStream;
                    Log.w("MicroMsg.SDK.Util", "getFileSize fail, " + e.getMessage());
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException unused3) {
                        }
                    }
                    return 0;
                } catch (Throwable th) {
                    th = th;
                    inputStream = inputStreamOpenInputStream;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException unused4) {
                        }
                    }
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static int a(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        File file = new File(str);
        if (file.exists()) {
            return (int) file.length();
        }
        if (a != null && str.startsWith(DBHelpTool.RecordEntry.COLUMN_NAME_CONTENT)) {
            try {
                return a(a.getContentResolver(), Uri.parse(str));
            } catch (Exception unused) {
            }
        }
        return 0;
    }

    public static int a(String str, int i) {
        if (str != null) {
            try {
                if (str.length() > 0) {
                    return Integer.parseInt(str);
                }
            } catch (Exception unused) {
                return i;
            }
        }
        return i;
    }

    public static boolean a(int i) {
        return i == 36 || i == 46;
    }

    public static boolean b(String str) {
        return str == null || str.length() <= 0;
    }
}
