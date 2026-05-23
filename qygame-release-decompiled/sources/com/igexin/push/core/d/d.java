package com.igexin.push.core.d;

import android.text.TextUtils;
import com.getui.gtc.base.util.io.IOUtils;
import com.igexin.push.core.ServiceManager;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
public final class d {
    public static final String a = "grp.prop";
    public static final String b = "itmp";
    public static final String c = "itop";
    public static final String d = "c";
    public static final String e = "i";
    public static final String f = "p";
    public static final String g = "s";
    private static final String h = "RpConfig";
    private static final d i = new d();
    private long k;
    private final Map<String, String> l = new ConcurrentHashMap();
    private final String j = ServiceManager.b.getFilesDir().getAbsolutePath() + "/grp.prop";

    private d() {
        try {
            File file = new File(this.j);
            if (file.exists()) {
                return;
            }
            file.createNewFile();
        } catch (IOException e2) {
            com.igexin.c.a.c.a.a(e2);
        }
    }

    private int a(String str, int... iArr) {
        try {
            return Integer.parseInt(a(str));
        } catch (NumberFormatException e2) {
            com.igexin.c.a.c.a.a(e2);
            if (iArr == null || iArr.length != 1) {
                return -1;
            }
            return iArr[0];
        }
    }

    public static d a() {
        return i;
    }

    private ArrayList<String> a(String str, ArrayList<String> arrayList) throws Throwable {
        String strA = a(str);
        try {
            if (TextUtils.isEmpty(strA)) {
                return arrayList;
            }
            ArrayList<String> arrayList2 = new ArrayList<>();
            JSONArray jSONArray = new JSONArray(strA);
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                String strOptString = jSONArray.optString(i2);
                if (!TextUtils.isEmpty(strOptString)) {
                    arrayList2.add(strOptString);
                }
            }
            return arrayList2;
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
            return arrayList;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.igexin.push.core.g.a<RandomAccessFile> aVar) throws Throwable {
        RandomAccessFile randomAccessFile;
        FileLock fileLockLock;
        FileLock fileLock = null;
        try {
            try {
                randomAccessFile = new RandomAccessFile(new File(this.j), "rw");
                try {
                    fileLockLock = randomAccessFile.getChannel().lock();
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e3) {
            e = e3;
            randomAccessFile = null;
        } catch (Throwable th2) {
            th = th2;
            randomAccessFile = null;
        }
        try {
            if (fileLockLock.isValid()) {
                aVar.a(randomAccessFile);
            }
            if (fileLockLock != null && fileLockLock.isValid()) {
                try {
                    fileLockLock.release();
                } catch (IOException unused) {
                }
            }
            IOUtils.safeClose(randomAccessFile);
        } catch (Exception e4) {
            e = e4;
            fileLock = fileLockLock;
            com.igexin.c.a.c.a.a(e);
            com.igexin.c.a.c.a.a("RpConfig| getProcessLock err：" + e.toString(), new Object[0]);
            if (fileLock != null && fileLock.isValid()) {
                try {
                    fileLock.release();
                } catch (IOException unused2) {
                }
            }
            IOUtils.safeClose(randomAccessFile);
        } catch (Throwable th3) {
            th = th3;
            fileLock = fileLockLock;
            if (fileLock != null && fileLock.isValid()) {
                try {
                    fileLock.release();
                } catch (IOException unused3) {
                }
            }
            IOUtils.safeClose(randomAccessFile);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(RandomAccessFile randomAccessFile) {
        int i2;
        try {
            this.l.clear();
            while (true) {
                String line = randomAccessFile.readLine();
                if (line == null) {
                    return true;
                }
                int iIndexOf = line.indexOf("=");
                if (iIndexOf >= 0 && (i2 = iIndexOf + 1) != line.length()) {
                    this.l.put(line.substring(0, iIndexOf), line.substring(i2));
                }
            }
        } catch (Exception e2) {
            com.igexin.c.a.c.a.a(e2);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b() {
        long jLastModified = new File(this.j).lastModified();
        boolean z = this.k != jLastModified;
        this.k = jLastModified;
        return z;
    }

    private void c(final String str) {
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.d.d.2
            @Override // com.igexin.push.b.d
            public final void a_() throws Exception {
                d.this.a(new com.igexin.push.core.g.a<RandomAccessFile>() { // from class: com.igexin.push.core.d.d.2.2
                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    private void a2(RandomAccessFile randomAccessFile) {
                        if (d.this.b()) {
                            d.this.a(randomAccessFile);
                        }
                        d.this.l.remove(str);
                        try {
                            randomAccessFile.setLength(0L);
                            for (Map.Entry entry : d.this.l.entrySet()) {
                                randomAccessFile.writeBytes(((String) entry.getKey()) + "=" + ((String) entry.getValue()));
                                randomAccessFile.writeBytes("\n");
                            }
                        } catch (IOException e2) {
                            com.igexin.c.a.c.a.a(e2);
                        }
                    }

                    @Override // com.igexin.push.core.g.a
                    public final /* synthetic */ void a(RandomAccessFile randomAccessFile) {
                        RandomAccessFile randomAccessFile2 = randomAccessFile;
                        if (d.this.b()) {
                            d.this.a(randomAccessFile2);
                        }
                        d.this.l.remove(str);
                        try {
                            randomAccessFile2.setLength(0L);
                            for (Map.Entry entry : d.this.l.entrySet()) {
                                randomAccessFile2.writeBytes(((String) entry.getKey()) + "=" + ((String) entry.getValue()));
                                randomAccessFile2.writeBytes("\n");
                            }
                        } catch (IOException e2) {
                            com.igexin.c.a.c.a.a(e2);
                        }
                    }
                }.a(new com.igexin.push.core.g.a<RandomAccessFile>() { // from class: com.igexin.push.core.d.d.2.1
                    private void a() {
                        d.this.b();
                    }

                    @Override // com.igexin.push.core.g.a
                    public final /* bridge */ /* synthetic */ void a(RandomAccessFile randomAccessFile) {
                        d.this.b();
                    }
                }));
            }
        }, false, true);
    }

    public final long a(String str, long... jArr) {
        try {
            return Long.parseLong(a(str));
        } catch (NumberFormatException e2) {
            com.igexin.c.a.c.a.a(e2);
            if (jArr.length == 1) {
                return jArr[0];
            }
            return 0L;
        }
    }

    public final String a(String str) throws Throwable {
        if (b()) {
            a(new com.igexin.push.core.g.a<RandomAccessFile>() { // from class: com.igexin.push.core.d.d.3
                /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                private void a2(RandomAccessFile randomAccessFile) {
                    d.this.a(randomAccessFile);
                }

                @Override // com.igexin.push.core.g.a
                public final /* bridge */ /* synthetic */ void a(RandomAccessFile randomAccessFile) {
                    d.this.a(randomAccessFile);
                }
            });
        }
        return this.l.get(str);
    }

    public final void a(final String str, final Object obj) {
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d() { // from class: com.igexin.push.core.d.d.1
            @Override // com.igexin.push.b.d
            public final void a_() throws Exception {
                d.this.a(new com.igexin.push.core.g.a<RandomAccessFile>() { // from class: com.igexin.push.core.d.d.1.2
                    /* JADX INFO: renamed from: a, reason: avoid collision after fix types in other method */
                    private void a2(RandomAccessFile randomAccessFile) {
                        String string;
                        if (d.this.b()) {
                            d.this.a(randomAccessFile);
                        }
                        if (obj instanceof List) {
                            try {
                                string = new JSONArray((Collection) obj).toString();
                            } catch (Throwable th) {
                                com.igexin.c.a.c.a.a(th);
                                return;
                            }
                        } else {
                            string = String.valueOf(obj);
                        }
                        d.this.l.put(str, string);
                        try {
                            randomAccessFile.setLength(0L);
                            for (Map.Entry entry : d.this.l.entrySet()) {
                                randomAccessFile.writeBytes(((String) entry.getKey()) + "=" + ((String) entry.getValue()));
                                randomAccessFile.writeBytes("\n");
                            }
                        } catch (IOException e2) {
                            com.igexin.c.a.c.a.a(e2);
                        }
                    }

                    @Override // com.igexin.push.core.g.a
                    public final /* synthetic */ void a(RandomAccessFile randomAccessFile) {
                        String string;
                        RandomAccessFile randomAccessFile2 = randomAccessFile;
                        if (d.this.b()) {
                            d.this.a(randomAccessFile2);
                        }
                        if (obj instanceof List) {
                            try {
                                string = new JSONArray((Collection) obj).toString();
                            } catch (Throwable th) {
                                com.igexin.c.a.c.a.a(th);
                                return;
                            }
                        } else {
                            string = String.valueOf(obj);
                        }
                        d.this.l.put(str, string);
                        try {
                            randomAccessFile2.setLength(0L);
                            for (Map.Entry entry : d.this.l.entrySet()) {
                                randomAccessFile2.writeBytes(((String) entry.getKey()) + "=" + ((String) entry.getValue()));
                                randomAccessFile2.writeBytes("\n");
                            }
                        } catch (IOException e2) {
                            com.igexin.c.a.c.a.a(e2);
                        }
                    }
                }.a(new com.igexin.push.core.g.a<RandomAccessFile>() { // from class: com.igexin.push.core.d.d.1.1
                    private void a() {
                        d.this.b();
                    }

                    @Override // com.igexin.push.core.g.a
                    public final /* bridge */ /* synthetic */ void a(RandomAccessFile randomAccessFile) {
                        d.this.b();
                    }
                }));
            }
        }, false, true);
    }

    public final boolean b(String str) {
        return Boolean.parseBoolean(a(str));
    }
}
