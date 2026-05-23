package com.loc;

/* JADX INFO: loaded from: classes.dex */
public final class dy {
    public long a;
    public String b;
    public int d;
    public long e;
    public short g;
    public boolean h;
    public int c = -113;
    public long f = 0;

    public dy(boolean z) {
        this.h = z;
    }

    public static long a(String str) {
        long j;
        if (str == null || str.length() == 0) {
            return 0L;
        }
        int i = 0;
        long j2 = 0;
        for (int length = str.length() - 1; length >= 0; length--) {
            long jCharAt = str.charAt(length);
            if (jCharAt < 48 || jCharAt > 57) {
                long j3 = 97;
                if (jCharAt < 97 || jCharAt > 102) {
                    j3 = 65;
                    if (jCharAt < 65 || jCharAt > 70) {
                        if (jCharAt != 58 && jCharAt != 124) {
                            return 0L;
                        }
                    }
                }
                j = (jCharAt - j3) + 10;
            } else {
                j = jCharAt - 48;
            }
            j2 += j << i;
            i += 4;
        }
        if (i != 48) {
            return 0L;
        }
        return j2;
    }

    public static String a(long j) {
        if (j < 0 || j > 281474976710655L) {
            return null;
        }
        return eg.a(eg.a(j), ":");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public dy clone() {
        dy dyVar = new dy(this.h);
        dyVar.a = this.a;
        dyVar.b = this.b;
        dyVar.c = this.c;
        dyVar.d = this.d;
        dyVar.e = this.e;
        dyVar.f = this.f;
        dyVar.g = this.g;
        dyVar.h = this.h;
        return dyVar;
    }

    public final String a() {
        return this.h + "#" + this.a;
    }

    public final String toString() {
        return "AmapWifi{mac=" + this.a + ", ssid='" + this.b + "', rssi=" + this.c + ", frequency=" + this.d + ", timestamp=" + this.e + ", lastUpdateUtcMills=" + this.f + ", freshness=" + ((int) this.g) + ", connected=" + this.h + '}';
    }
}
