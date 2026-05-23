package com.igexin.push.core.e;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.igexin.push.core.b.n;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: loaded from: classes.dex */
public class e implements a {
    private static final String b = "RALDataManager";
    private static final int c = 318;
    private static final int d = 300;
    private static volatile e e;
    public final List<n> a = new CopyOnWriteArrayList();

    /* JADX INFO: renamed from: com.igexin.push.core.e.e$3, reason: invalid class name */
    public class AnonymousClass3 extends com.igexin.push.b.d {
        final /* synthetic */ long a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(ContentValues contentValues, long j) {
            super(contentValues);
            this.a = j;
        }

        @Override // com.igexin.push.b.d
        public final void a_() throws Exception {
            this.d.update(com.igexin.push.core.b.aa, this.h, "id=?", new String[]{String.valueOf(this.a)});
        }
    }

    private e() {
    }

    private int a(byte b2) {
        Iterator<n> it = this.a.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().c == b2) {
                i++;
            }
        }
        return i;
    }

    public static ContentValues a(n nVar) {
        if (nVar == null) {
            return null;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(com.igexin.push.core.b.C, Long.valueOf(nVar.a));
        contentValues.put(com.alipay.sdk.packet.d.k, com.igexin.c.b.a.b(nVar.b.getBytes()));
        contentValues.put("type", Byte.valueOf(nVar.c));
        contentValues.put("time", Long.valueOf(nVar.d));
        contentValues.put("send_times", Integer.valueOf(nVar.e));
        return contentValues;
    }

    public static e a() {
        if (e == null) {
            synchronized (e.class) {
                if (e == null) {
                    e = new e();
                }
            }
        }
        return e;
    }

    private boolean a(long j, long j2) {
        n nVarA = a(j);
        if (nVarA == null) {
            return false;
        }
        nVarA.d = j2;
        nVarA.e++;
        com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new AnonymousClass3(a(nVarA), j), true, true);
        return true;
    }

    private List<n> b() {
        return this.a;
    }

    private void b(byte b2) {
        n nVar = null;
        try {
            Iterator<n> it = this.a.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                n next = it.next();
                if (next.c == b2) {
                    nVar = next;
                    break;
                }
            }
            if (nVar != null) {
                a(nVar.a, true);
            }
        } catch (Throwable th) {
            com.igexin.c.a.c.a.a(th);
        }
    }

    public final n a(long j) {
        for (n nVar : this.a) {
            if (nVar.a == j) {
                return nVar;
            }
        }
        return null;
    }

    public final void a(final long j, boolean z) {
        n nVarA = a(j);
        if (nVarA != null) {
            this.a.remove(nVarA);
        }
        com.igexin.c.a.b.e.a().a(new com.igexin.push.b.d(a(nVarA)) { // from class: com.igexin.push.core.e.e.2
            @Override // com.igexin.push.b.d
            public final void a_() throws Exception {
                this.d.delete(com.igexin.push.core.b.aa, "id=?", new String[]{String.valueOf(j)});
            }
        }, z, !z);
    }

    @Override // com.igexin.push.core.e.a
    public final void a(SQLiteDatabase sQLiteDatabase) {
    }

    @Override // com.igexin.push.core.e.a
    public final void b(SQLiteDatabase sQLiteDatabase) throws Throwable {
        Cursor cursorQuery;
        Cursor cursor = null;
        try {
            try {
                cursorQuery = sQLiteDatabase.query(com.igexin.push.core.b.aa, new String[]{com.igexin.push.core.b.C, com.alipay.sdk.packet.d.k, "type", "time", "send_times"}, null, null, null, null, null);
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th) {
            th = th;
            cursorQuery = cursor;
        }
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (cursorQuery != null) {
                while (cursorQuery.moveToNext()) {
                    long j = cursorQuery.getLong(0);
                    byte b2 = (byte) cursorQuery.getInt(2);
                    long j2 = cursorQuery.getLong(3);
                    int i = cursorQuery.getInt(4);
                    if ((j2 == 0 || jCurrentTimeMillis - j2 <= 259200000) && i < com.igexin.push.config.d.N - 1) {
                        List<n> list = this.a;
                        n nVar = new n(j, new String(com.igexin.c.b.a.c(cursorQuery.getBlob(1))), b2, j2);
                        nVar.e = i;
                        list.add(nVar);
                    } else {
                        a(j, true);
                    }
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        } catch (Exception e3) {
            e = e3;
            cursor = cursorQuery;
            com.igexin.c.a.c.a.a(e);
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th2) {
            th = th2;
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    public final void b(final n nVar) {
        if (this.a.size() < c || nVar.c == 2 || nVar.c == 7) {
            switch (nVar.c) {
                case 2:
                case 7:
                    b(nVar.c);
                    break;
                case 3:
                    if (a((byte) 3) >= d) {
                        return;
                    }
                    break;
                case 5:
                    if (a((byte) 5) >= 3) {
                        return;
                    }
                    break;
                case 6:
                    if (a((byte) 6) >= 10) {
                        return;
                    }
                    break;
                case 8:
                    if (a((byte) 8) >= 3) {
                        return;
                    }
                    break;
            }
            this.a.add(nVar);
            com.igexin.c.a.b.e.a().a((com.igexin.c.a.d.f) new com.igexin.push.b.d(a(nVar)) { // from class: com.igexin.push.core.e.e.1
                @Override // com.igexin.push.b.d
                public final void a_() throws Exception {
                    this.d.replace(com.igexin.push.core.b.aa, null, this.h);
                }
            }, false, true);
        }
    }

    @Override // com.igexin.push.core.e.a
    public final void c(SQLiteDatabase sQLiteDatabase) {
    }
}
