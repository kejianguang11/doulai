package com.mobile.auth.b;

import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: classes.dex */
public class c {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r2v5, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r5v16 */
    /* JADX WARN: Type inference failed for: r5v17 */
    /* JADX WARN: Type inference failed for: r5v18, types: [java.io.InputStreamReader, java.io.Reader] */
    /* JADX WARN: Type inference failed for: r5v3, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r5v5, types: [java.io.InputStreamReader] */
    /* JADX WARN: Type inference failed for: r5v9 */
    public static String a(Context context) {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        try {
            ?? C = c(context);
            StringBuilder sb = new StringBuilder();
            if (C == 0) {
                return "";
            }
            ?? Exists = C.exists();
            try {
                if (Exists == 0) {
                    return "";
                }
                try {
                    Exists = new FileInputStream((File) C);
                    try {
                        C = new InputStreamReader(Exists);
                        try {
                            bufferedReader2 = new BufferedReader(C);
                            while (true) {
                                try {
                                    String line = bufferedReader2.readLine();
                                    if (line != null) {
                                        sb.append(line);
                                    } else {
                                        try {
                                            break;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } catch (Throwable th) {
                                    th = th;
                                    th.printStackTrace();
                                    if (bufferedReader2 != null) {
                                        try {
                                            bufferedReader2.close();
                                        } catch (Exception e2) {
                                            e2.printStackTrace();
                                        }
                                    }
                                    if (C != 0) {
                                        try {
                                            C.close();
                                        } catch (Exception e3) {
                                            e3.printStackTrace();
                                        }
                                    }
                                    if (Exists != 0) {
                                        try {
                                            Exists.close();
                                        } catch (Exception e4) {
                                            e = e4;
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                            bufferedReader2.close();
                            try {
                                C.close();
                            } catch (Exception e5) {
                                e5.printStackTrace();
                            }
                            try {
                                Exists.close();
                            } catch (Exception e6) {
                                e = e6;
                                e.printStackTrace();
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            bufferedReader2 = null;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        C = 0;
                        bufferedReader2 = null;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    C = 0;
                    Exists = 0;
                    bufferedReader = null;
                }
                return sb.toString();
            } catch (Throwable th5) {
                th = th5;
            }
        } catch (Throwable th6) {
            try {
                ExceptionProcessor.processException(th6);
                return null;
            } catch (Throwable th7) {
                ExceptionProcessor.processException(th7);
                return null;
            }
        }
    }

    public static void a(Context context, String str) {
        try {
            File fileC = c(context);
            if (fileC == null || !fileC.exists()) {
                a(b(context), str);
            } else {
                a(fileC, str);
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private static void a(File file, String str) {
        BufferedWriter bufferedWriter;
        FileWriter fileWriter;
        if (file == null) {
            return;
        }
        try {
            if (!file.exists()) {
                return;
            }
            BufferedWriter bufferedWriter2 = null;
            try {
                fileWriter = new FileWriter(file, false);
                try {
                    try {
                        bufferedWriter = new BufferedWriter(fileWriter);
                    } catch (Exception e) {
                        e = e;
                    }
                } catch (Throwable th) {
                    th = th;
                    bufferedWriter = bufferedWriter2;
                }
                try {
                    if (TextUtils.isEmpty(str)) {
                        str = "";
                    }
                    bufferedWriter.write(str);
                    bufferedWriter.flush();
                    try {
                        bufferedWriter.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    try {
                        fileWriter.close();
                    } catch (Exception e3) {
                        e = e3;
                        e.printStackTrace();
                    }
                } catch (Exception e4) {
                    e = e4;
                    bufferedWriter2 = bufferedWriter;
                    e.printStackTrace();
                    if (bufferedWriter2 != null) {
                        try {
                            bufferedWriter2.close();
                        } catch (Exception e5) {
                            e5.printStackTrace();
                        }
                    }
                    if (fileWriter != null) {
                        try {
                            fileWriter.close();
                        } catch (Exception e6) {
                            e = e6;
                            e.printStackTrace();
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedWriter != null) {
                        try {
                            bufferedWriter.close();
                        } catch (Exception e7) {
                            e7.printStackTrace();
                        }
                    }
                    if (fileWriter == null) {
                        throw th;
                    }
                    try {
                        fileWriter.close();
                        throw th;
                    } catch (Exception e8) {
                        e8.printStackTrace();
                        throw th;
                    }
                }
            } catch (Exception e9) {
                e = e9;
                fileWriter = null;
            } catch (Throwable th3) {
                th = th3;
                bufferedWriter = null;
                fileWriter = null;
            }
        } catch (Throwable th4) {
            try {
                ExceptionProcessor.processException(th4);
            } catch (Throwable th5) {
                ExceptionProcessor.processException(th5);
            }
        }
    }

    private static File b(Context context) {
        try {
            if (context != null) {
                try {
                    File file = new File(context.getFilesDir() + "/eAccount/Log/");
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    File file2 = new File(file, "ipa_ol.ds");
                    if (file2.exists()) {
                        file2.delete();
                    }
                    file2.createNewFile();
                    return file2;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private static File c(Context context) {
        try {
            if (context != null) {
                try {
                    File file = new File(context.getFilesDir() + "/eAccount/Log/");
                    if (!file.exists()) {
                        return null;
                    }
                    File file2 = new File(file, "ipa_ol.ds");
                    if (file2.exists()) {
                        return file2;
                    }
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
