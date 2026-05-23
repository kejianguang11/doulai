package com.ta.utdid2.b.a;

import com.ta.utdid2.b.a.b;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes.dex */
public class d {
    private static final Object b = new Object();
    private File a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private final Object f10a = new Object();

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private HashMap<File, a> f11a = new HashMap<>();

    private static final class a implements b {
        private static final Object c = new Object();
        private Map a;

        /* JADX INFO: renamed from: a, reason: collision with other field name */
        private WeakHashMap<b.InterfaceC0053b, Object> f12a;
        private final int b;

        /* JADX INFO: renamed from: b, reason: collision with other field name */
        private final File f13b;

        /* JADX INFO: renamed from: c, reason: collision with other field name */
        private final File f14c;
        private boolean g = false;

        /* JADX INFO: renamed from: com.ta.utdid2.b.a.d$a$a, reason: collision with other inner class name */
        public final class C0054a implements b.a {
            private final Map<String, Object> b = new HashMap();
            private boolean h = false;

            public C0054a() {
            }

            @Override // com.ta.utdid2.b.a.b.a
            public final b.a a(String str) {
                synchronized (this) {
                    this.b.put(str, this);
                }
                return this;
            }

            @Override // com.ta.utdid2.b.a.b.a
            public final b.a a(String str, float f) {
                synchronized (this) {
                    this.b.put(str, Float.valueOf(f));
                }
                return this;
            }

            @Override // com.ta.utdid2.b.a.b.a
            public final b.a a(String str, int i) {
                synchronized (this) {
                    this.b.put(str, Integer.valueOf(i));
                }
                return this;
            }

            @Override // com.ta.utdid2.b.a.b.a
            public final b.a a(String str, long j) {
                synchronized (this) {
                    this.b.put(str, Long.valueOf(j));
                }
                return this;
            }

            @Override // com.ta.utdid2.b.a.b.a
            public final b.a a(String str, String str2) {
                synchronized (this) {
                    this.b.put(str, str2);
                }
                return this;
            }

            @Override // com.ta.utdid2.b.a.b.a
            public final b.a a(String str, boolean z) {
                synchronized (this) {
                    this.b.put(str, Boolean.valueOf(z));
                }
                return this;
            }

            @Override // com.ta.utdid2.b.a.b.a
            public final b.a b() {
                synchronized (this) {
                    this.h = true;
                }
                return this;
            }

            @Override // com.ta.utdid2.b.a.b.a
            public final boolean commit() {
                boolean z;
                ArrayList arrayList;
                HashSet<b.InterfaceC0053b> hashSet;
                boolean zD;
                synchronized (d.b) {
                    z = a.this.f12a.size() > 0;
                    arrayList = null;
                    if (z) {
                        arrayList = new ArrayList();
                        hashSet = new HashSet(a.this.f12a.keySet());
                    } else {
                        hashSet = null;
                    }
                    synchronized (this) {
                        if (this.h) {
                            a.this.a.clear();
                            this.h = false;
                        }
                        for (Map.Entry<String, Object> entry : this.b.entrySet()) {
                            String key = entry.getKey();
                            Object value = entry.getValue();
                            if (value == this) {
                                a.this.a.remove(key);
                            } else {
                                a.this.a.put(key, value);
                            }
                            if (z) {
                                arrayList.add(key);
                            }
                        }
                        this.b.clear();
                    }
                    zD = a.this.d();
                    if (zD) {
                        a.this.a(true);
                    }
                }
                if (z) {
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        String str = (String) arrayList.get(size);
                        for (b.InterfaceC0053b interfaceC0053b : hashSet) {
                            if (interfaceC0053b != null) {
                                interfaceC0053b.a(a.this, str);
                            }
                        }
                    }
                }
                return zD;
            }
        }

        a(File file, int i, Map map) {
            this.f13b = file;
            this.f14c = d.a(file);
            this.b = i;
            this.a = map == null ? new HashMap() : map;
            this.f12a = new WeakHashMap<>();
        }

        private FileOutputStream a(File file) {
            try {
                return new FileOutputStream(file);
            } catch (FileNotFoundException unused) {
                if (!file.getParentFile().mkdir()) {
                    return null;
                }
                try {
                    return new FileOutputStream(file);
                } catch (FileNotFoundException unused2) {
                    return null;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean d() {
            if (this.f13b.exists()) {
                if (this.f14c.exists()) {
                    this.f13b.delete();
                } else if (!this.f13b.renameTo(this.f14c)) {
                    return false;
                }
            }
            try {
                FileOutputStream fileOutputStreamA = a(this.f13b);
                if (fileOutputStreamA == null) {
                    return false;
                }
                e.a(this.a, fileOutputStreamA);
                fileOutputStreamA.close();
                this.f14c.delete();
                return true;
            } catch (Exception unused) {
                if (this.f13b.exists()) {
                    this.f13b.delete();
                }
                return false;
            }
        }

        @Override // com.ta.utdid2.b.a.b
        public final b.a a() {
            return new C0054a();
        }

        public final void a(Map map) {
            if (map != null) {
                synchronized (this) {
                    this.a = map;
                }
            }
        }

        public final void a(boolean z) {
            synchronized (this) {
                this.g = z;
            }
        }

        @Override // com.ta.utdid2.b.a.b
        /* JADX INFO: renamed from: a */
        public final boolean mo10a() {
            return this.f13b != null && new File(this.f13b.getAbsolutePath()).exists();
        }

        public final boolean c() {
            boolean z;
            synchronized (this) {
                z = this.g;
            }
            return z;
        }

        @Override // com.ta.utdid2.b.a.b
        public final Map<String, ?> getAll() {
            HashMap map;
            synchronized (this) {
                map = new HashMap(this.a);
            }
            return map;
        }

        @Override // com.ta.utdid2.b.a.b
        public final long getLong(String str, long j) {
            synchronized (this) {
                Long l = (Long) this.a.get(str);
                if (l != null) {
                    j = l.longValue();
                }
            }
            return j;
        }

        @Override // com.ta.utdid2.b.a.b
        public final String getString(String str, String str2) {
            String str3;
            synchronized (this) {
                str3 = (String) this.a.get(str);
                if (str3 == null) {
                    str3 = str2;
                }
            }
            return str3;
        }
    }

    public d(String str) {
        if (str == null || str.length() <= 0) {
            throw new RuntimeException("Directory can not be empty");
        }
        this.a = new File(str);
    }

    private File a() {
        File file;
        synchronized (this.f10a) {
            file = this.a;
        }
        return file;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static File a(File file) {
        return new File(file.getPath() + ".bak");
    }

    private File a(File file, String str) {
        if (str.indexOf(File.separatorChar) < 0) {
            return new File(file, str);
        }
        throw new IllegalArgumentException("File " + str + " contains a path separator");
    }

    private File b(String str) {
        return a(a(), str + ".xml");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0054 A[EXC_TOP_SPLITTER, PHI: r0 r2
      0x0054: PHI (r0v9 java.io.FileInputStream) = (r0v8 java.io.FileInputStream), (r0v12 java.io.FileInputStream) binds: [B:30:0x0052, B:55:0x0089] A[DONT_GENERATE, DONT_INLINE]
      0x0054: PHI (r2v6 java.util.HashMap) = (r2v5 java.util.HashMap), (r2v7 java.util.HashMap) binds: [B:30:0x0052, B:55:0x0089] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x008f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v5, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.io.FileInputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public b a(String str, int i) throws Throwable {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        File fileB = b(str);
        synchronized (b) {
            a aVar = this.f11a.get(fileB);
            if (aVar != null && !aVar.c()) {
                return aVar;
            }
            File fileA = a(fileB);
            if (fileA.exists()) {
                fileB.delete();
                fileA.renameTo(fileB);
            }
            HashMap map = null;
            map = null;
            map = null;
            map = null;
            map = null;
            map = null;
            ?? r2 = 0;
            if (fileB.exists()) {
                ?? CanRead = fileB.canRead();
                try {
                    try {
                        if (CanRead != 0) {
                            try {
                                fileInputStream = new FileInputStream(fileB);
                                try {
                                    HashMap mapA = e.a(fileInputStream);
                                    try {
                                        fileInputStream.close();
                                        try {
                                            fileInputStream.close();
                                        } catch (Throwable unused) {
                                        }
                                        map = mapA;
                                    } catch (XmlPullParserException unused2) {
                                        map = mapA;
                                        try {
                                            FileInputStream fileInputStream3 = new FileInputStream(fileB);
                                            try {
                                                fileInputStream3.read(new byte[fileInputStream3.available()]);
                                                try {
                                                    fileInputStream3.close();
                                                } catch (Throwable unused3) {
                                                }
                                                fileInputStream = fileInputStream3;
                                            } catch (Exception unused4) {
                                                fileInputStream = fileInputStream3;
                                                if (fileInputStream != null) {
                                                    try {
                                                        fileInputStream.close();
                                                    } catch (Throwable unused5) {
                                                    }
                                                }
                                                if (fileInputStream != null) {
                                                }
                                                synchronized (b) {
                                                }
                                            } catch (Throwable th) {
                                                th = th;
                                                fileInputStream2 = fileInputStream3;
                                                if (fileInputStream2 != null) {
                                                    try {
                                                        fileInputStream2.close();
                                                    } catch (Throwable unused6) {
                                                    }
                                                }
                                                throw th;
                                            }
                                        } catch (Exception unused7) {
                                        } catch (Throwable th2) {
                                            th = th2;
                                            fileInputStream2 = fileInputStream;
                                        }
                                        if (fileInputStream != null) {
                                            try {
                                                fileInputStream.close();
                                            } catch (Throwable unused8) {
                                            }
                                        }
                                    } catch (Exception unused9) {
                                        map = mapA;
                                        if (fileInputStream != null) {
                                        }
                                    }
                                } catch (XmlPullParserException unused10) {
                                } catch (Exception unused11) {
                                }
                            } catch (XmlPullParserException unused12) {
                                fileInputStream = null;
                            } catch (Exception unused13) {
                                fileInputStream = null;
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        r2 = CanRead;
                    }
                } catch (Throwable th4) {
                    th = th4;
                }
            }
            synchronized (b) {
                try {
                    if (aVar != null) {
                        aVar.a(map);
                    } else {
                        aVar = this.f11a.get(fileB);
                        if (aVar == null) {
                            aVar = new a(fileB, i, map);
                            this.f11a.put(fileB, aVar);
                        }
                    }
                } catch (Throwable th5) {
                    throw th5;
                }
            }
            return aVar;
        }
    }
}
