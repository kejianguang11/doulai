package com.mobile.auth.gatewayauth.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: classes.dex */
public class k {
    private static int[] a = {3, 10, 13, 0, 14, 5, 1, 8, 9, 4, 6, 7, -1, 2, 11, 12};

    public static int a(Context context, String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return 0;
            }
            return context.getResources().getIdentifier(str, "drawable", context.getPackageName());
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

    public static Drawable a(Context context, String str, String str2) {
        Drawable drawableF;
        int iA;
        int iA2;
        try {
            try {
            } catch (Exception e) {
                e = e;
                drawableF = null;
            }
            if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
                return null;
            }
            drawableF = !TextUtils.isEmpty(str) ? (str.contains("/") || (iA2 = a(context, str)) == 0) ? f(context, str) : ResourcesCompat.getDrawable(context.getResources(), iA2, null) : null;
            if (drawableF == null) {
                try {
                    if (!TextUtils.isEmpty(str2) && (iA = a(context, str2)) != 0) {
                        return ResourcesCompat.getDrawable(context.getResources(), iA, null);
                    }
                } catch (Exception e2) {
                    e = e2;
                    i.a(e);
                }
            }
            return drawableF;
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

    public static void a(ViewGroup viewGroup, View view) {
        if (viewGroup == null || view == null) {
            return;
        }
        try {
            if (view.getParent() == null) {
                viewGroup.addView(view);
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void a(ViewGroup viewGroup, View view, int i) {
        if (viewGroup == null || view == null) {
            return;
        }
        try {
            if (view.getParent() != null || i < 0) {
                return;
            }
            viewGroup.addView(view, i);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static boolean a(int i) {
        for (int i2 = 0; i2 < a.length; i2++) {
            try {
                if (i == a[i2]) {
                    return true;
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return false;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean a(Context context) {
        try {
            return (context.getApplicationInfo().flags & 2) != 0;
        } catch (Exception unused) {
            return false;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public static int b(Context context, String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return 0;
            }
            return context.getResources().getIdentifier(str, com.igexin.push.core.b.C, context.getPackageName());
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

    public static Drawable c(Context context, String str) {
        try {
            return a(context, str, (String) null);
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

    public static ColorStateList d(Context context, String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            return ContextCompat.getColorStateList(context, e(context, str));
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

    public static int e(Context context, String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return 0;
            }
            return context.getResources().getIdentifier(str, "color", context.getPackageName());
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0043 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.io.InputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Drawable f(Context context, String str) {
        InputStream inputStreamOpen;
        Bitmap bitmapDecodeStream;
        try {
            try {
                try {
                    inputStreamOpen = context.getAssets().open(str);
                    try {
                        bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpen);
                    } catch (Exception e) {
                        e = e;
                        bitmapDecodeStream = null;
                    }
                    try {
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmapDecodeStream);
                        if (inputStreamOpen != null) {
                            try {
                                inputStreamOpen.close();
                            } catch (IOException e2) {
                                i.a(e2);
                            }
                        }
                        return bitmapDrawable;
                    } catch (Exception e3) {
                        e = e3;
                        i.a(e);
                        if (bitmapDecodeStream != null) {
                            bitmapDecodeStream.recycle();
                        }
                        if (inputStreamOpen == null) {
                            return null;
                        }
                        try {
                            inputStreamOpen.close();
                            return null;
                        } catch (IOException e4) {
                            i.a(e4);
                            return null;
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    if (str != 0) {
                        try {
                            str.close();
                        } catch (IOException e5) {
                            i.a(e5);
                        }
                    }
                    throw th;
                }
            } catch (Exception e6) {
                e = e6;
                inputStreamOpen = null;
                bitmapDecodeStream = null;
            } catch (Throwable th2) {
                th = th2;
                str = 0;
                if (str != 0) {
                }
                throw th;
            }
        } catch (Throwable th3) {
            try {
                ExceptionProcessor.processException(th3);
                return null;
            } catch (Throwable th4) {
                ExceptionProcessor.processException(th4);
                return null;
            }
        }
    }

    public static String g(Context context, String str) {
        try {
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(context.getResources().getAssets().open(str), "ISO_8859_1");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        inputStreamReader.close();
                        bufferedReader.close();
                        return sb.toString();
                    }
                    sb.append(line);
                }
            } catch (Exception e) {
                i.a(e);
                return null;
            }
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
