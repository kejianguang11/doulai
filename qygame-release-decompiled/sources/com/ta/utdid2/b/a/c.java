package com.ta.utdid2.b.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import com.ta.utdid2.a.a.f;
import com.ta.utdid2.b.a.b;
import java.io.File;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class c {

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private SharedPreferences f5a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private b f7a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private d f8a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private String f9a;
    private String b;
    private boolean c;
    private boolean d;
    private boolean e;
    private boolean f;
    private Context mContext;
    private SharedPreferences.Editor a = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private b.a f6a = null;

    /* JADX WARN: Removed duplicated region for block: B:18:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x013d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public c(Context context, String str, String str2, boolean z, boolean z2) throws Throwable {
        long j;
        String externalStorageState;
        long j2;
        long j3;
        long j4;
        b bVarA;
        d dVar;
        d dVar2;
        this.f9a = "";
        this.b = "";
        this.c = false;
        this.d = false;
        this.e = false;
        this.f5a = null;
        this.f7a = null;
        this.mContext = null;
        this.f8a = null;
        this.f = false;
        this.c = z;
        this.f = z2;
        this.f9a = str2;
        this.b = str;
        this.mContext = context;
        if (context != null) {
            this.f5a = context.getSharedPreferences(str2, 0);
            j = this.f5a.getLong("t", 0L);
        } else {
            j = 0;
        }
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (Exception e) {
            e.printStackTrace();
            externalStorageState = null;
        }
        if (f.isEmpty(externalStorageState)) {
            this.e = false;
            this.d = false;
        } else if (externalStorageState.equals("mounted")) {
            this.e = true;
            this.d = true;
        } else if (externalStorageState.equals("mounted_ro")) {
            this.d = true;
            this.e = false;
        }
        if ((this.d || this.e) && context != null && !f.isEmpty(str)) {
            this.f8a = a(str);
            if (this.f8a != null) {
                try {
                    this.f7a = this.f8a.a(str2, 0);
                    j2 = this.f7a.getLong("t", 0L);
                    try {
                        if (z2) {
                            j3 = this.f5a.getLong("t2", 0L);
                            try {
                                j4 = this.f7a.getLong("t2", 0L);
                                try {
                                    if (j3 >= j4 || j3 <= 0) {
                                        if (j3 > j4 && j4 > 0) {
                                            a(this.f7a, this.f5a);
                                        } else if (j3 == 0 && j4 > 0) {
                                            a(this.f7a, this.f5a);
                                        } else if (j4 == 0 && j3 > 0) {
                                            a(this.f5a, this.f7a);
                                            dVar = this.f8a;
                                        } else if (j3 == j4) {
                                            a(this.f5a, this.f7a);
                                            bVarA = this.f8a.a(str2, 0);
                                            this.f7a = bVarA;
                                        }
                                        this.f5a = context.getSharedPreferences(str2, 0);
                                    } else {
                                        a(this.f5a, this.f7a);
                                        dVar = this.f8a;
                                    }
                                    bVarA = dVar.a(str2, 0);
                                    this.f7a = bVarA;
                                } catch (Exception unused) {
                                }
                            } catch (Exception unused2) {
                            }
                        } else {
                            if (j > j2) {
                                a(this.f5a, this.f7a);
                                dVar2 = this.f8a;
                            } else if (j < j2) {
                                a(this.f7a, this.f5a);
                                this.f5a = context.getSharedPreferences(str2, 0);
                            } else if (j == j2) {
                                a(this.f5a, this.f7a);
                                dVar2 = this.f8a;
                            } else {
                                j3 = j;
                                j4 = j2;
                            }
                            this.f7a = dVar2.a(str2, 0);
                        }
                        j2 = j4;
                        j = j3;
                    } catch (Exception unused3) {
                    }
                } catch (Exception unused4) {
                    j2 = 0;
                }
            } else {
                j2 = 0;
            }
        }
        if (j != j2 || (j == 0 && j2 == 0)) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (!this.f || (this.f && j == 0 && j2 == 0)) {
                if (this.f5a != null) {
                    SharedPreferences.Editor editorEdit = this.f5a.edit();
                    editorEdit.putLong("t2", jCurrentTimeMillis);
                    editorEdit.commit();
                }
                try {
                    if (this.f7a != null) {
                        b.a aVarA = this.f7a.a();
                        aVarA.a("t2", jCurrentTimeMillis);
                        aVarA.commit();
                    }
                } catch (Exception unused5) {
                }
            }
        }
    }

    private d a(String str) {
        File fileM11a = m11a(str);
        if (fileM11a == null) {
            return null;
        }
        this.f8a = new d(fileM11a.getAbsolutePath());
        return this.f8a;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private File m11a(String str) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        if (externalStorageDirectory == null) {
            return null;
        }
        File file = new File(String.format("%s%s%s", externalStorageDirectory.getAbsolutePath(), File.separator, str));
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private void a(SharedPreferences sharedPreferences, b bVar) {
        b.a aVarA;
        if (sharedPreferences == null || bVar == null || (aVarA = bVar.a()) == null) {
            return;
        }
        aVarA.b();
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                aVarA.a(key, (String) value);
            } else if (value instanceof Integer) {
                aVarA.a(key, ((Integer) value).intValue());
            } else if (value instanceof Long) {
                aVarA.a(key, ((Long) value).longValue());
            } else if (value instanceof Float) {
                aVarA.a(key, ((Float) value).floatValue());
            } else if (value instanceof Boolean) {
                aVarA.a(key, ((Boolean) value).booleanValue());
            }
        }
        aVarA.commit();
    }

    private void a(b bVar, SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editorEdit;
        if (bVar == null || sharedPreferences == null || (editorEdit = sharedPreferences.edit()) == null) {
            return;
        }
        editorEdit.clear();
        for (Map.Entry<String, ?> entry : bVar.getAll().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                editorEdit.putString(key, (String) value);
            } else if (value instanceof Integer) {
                editorEdit.putInt(key, ((Integer) value).intValue());
            } else if (value instanceof Long) {
                editorEdit.putLong(key, ((Long) value).longValue());
            } else if (value instanceof Float) {
                editorEdit.putFloat(key, ((Float) value).floatValue());
            } else if (value instanceof Boolean) {
                editorEdit.putBoolean(key, ((Boolean) value).booleanValue());
            }
        }
        editorEdit.commit();
    }

    private void b() {
        if (this.a == null && this.f5a != null) {
            this.a = this.f5a.edit();
        }
        if (this.e && this.f6a == null && this.f7a != null) {
            this.f6a = this.f7a.a();
        }
        m12b();
    }

    /* JADX INFO: renamed from: b, reason: collision with other method in class */
    private boolean m12b() {
        if (this.f7a == null) {
            return false;
        }
        boolean zMo10a = this.f7a.mo10a();
        if (!zMo10a) {
            commit();
        }
        return zMo10a;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean commit() {
        boolean z;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (this.a == null) {
            z = true;
        } else {
            if (!this.f && this.f5a != null) {
                this.a.putLong("t", jCurrentTimeMillis);
            }
            if (!this.a.commit()) {
                z = false;
            }
        }
        if (this.f5a != null && this.mContext != null) {
            this.f5a = this.mContext.getSharedPreferences(this.f9a, 0);
        }
        String externalStorageState = null;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!f.isEmpty(externalStorageState)) {
            if (externalStorageState.equals("mounted")) {
                if (this.f7a == null) {
                    d dVarA = a(this.b);
                    if (dVarA != null) {
                        this.f7a = dVarA.a(this.f9a, 0);
                        if (this.f) {
                            a(this.f7a, this.f5a);
                        } else {
                            a(this.f5a, this.f7a);
                        }
                        this.f6a = this.f7a.a();
                    }
                } else if (this.f6a != null && !this.f6a.commit()) {
                    z = false;
                }
            }
            if (externalStorageState.equals("mounted") || (externalStorageState.equals("mounted_ro") && this.f7a != null)) {
                try {
                    if (this.f8a != null) {
                        this.f7a = this.f8a.a(this.f9a, 0);
                    }
                } catch (Exception unused) {
                }
            }
        }
        return z;
    }

    public String getString(String str) {
        m12b();
        if (this.f5a != null) {
            String string = this.f5a.getString(str, "");
            if (!f.isEmpty(string)) {
                return string;
            }
        }
        return this.f7a != null ? this.f7a.getString(str, "") : "";
    }

    public void putString(String str, String str2) {
        if (f.isEmpty(str) || str.equals("t")) {
            return;
        }
        b();
        if (this.a != null) {
            this.a.putString(str, str2);
        }
        if (this.f6a != null) {
            this.f6a.a(str, str2);
        }
    }

    public void remove(String str) {
        if (f.isEmpty(str) || str.equals("t")) {
            return;
        }
        b();
        if (this.a != null) {
            this.a.remove(str);
        }
        if (this.f6a != null) {
            this.f6a.a(str);
        }
    }
}
