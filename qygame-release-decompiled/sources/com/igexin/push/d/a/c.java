package com.igexin.push.d.a;

import com.igexin.c.a.b.a.a.h;
import com.igexin.c.a.b.d;
import com.igexin.push.d.c.e;
import com.igexin.push.g.g;
import java.io.IOException;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class c extends d {
    public static final String a = "com.igexin.push.d.a.c";
    public static int b = -1;
    private byte[] g;

    private c(String str) {
        super(str, (byte) 0);
    }

    private static byte a(h hVar) throws IOException {
        return (byte) b(hVar, 1);
    }

    public static d a() {
        c cVar = new c("socketProtocol");
        new a("command", cVar);
        return cVar;
    }

    private static e a(com.igexin.push.d.c.a aVar) {
        e eVar = new e();
        eVar.b = e.a;
        eVar.a(aVar.c);
        eVar.f = aVar.b > 0 ? 1 : 0;
        eVar.d = 7;
        eVar.c = 11;
        eVar.g = aVar.d;
        eVar.c += g.c().length;
        if (aVar.a <= 0) {
            if (eVar.i == 0) {
                eVar.p = 0;
            }
            com.igexin.c.a.b.e.c();
            return eVar;
        }
        eVar.q = g.e();
        eVar.r = (int) (System.currentTimeMillis() / 1000);
        eVar.o = g.a(aVar, eVar.q, eVar.r);
        eVar.p = eVar.o.length;
        eVar.c += eVar.p;
        com.igexin.c.a.b.e.c();
        return eVar;
    }

    private static Object a(h hVar, e eVar) throws Exception {
        if (eVar.i == 48) {
            com.igexin.c.a.c.a.a(a, "decodeAes, encryptType = 0x30, return");
            return null;
        }
        byte b2 = (byte) b(hVar, 1);
        if (b2 > 0) {
            a(hVar, b2);
        }
        eVar.g = (byte) b(hVar, 1);
        eVar.p = (byte) b(hVar, 1);
        if (eVar.p > 0) {
            eVar.o = a(hVar, eVar.p);
        }
        if (eVar.f == 0) {
            com.igexin.push.d.c.a aVar = new com.igexin.push.d.c.a();
            aVar.f = eVar.d;
            aVar.b = (byte) 0;
            return aVar;
        }
        byte[] bArrA = a(hVar, 11);
        int iC = com.igexin.c.a.b.g.c(bArrA, 0);
        if (iC <= b) {
            b = -1;
            throw new Exception("server packetId can't be less than previous");
        }
        b = iC;
        int iC2 = com.igexin.c.a.b.g.c(bArrA, 4);
        short sA = com.igexin.c.a.b.g.a(bArrA, 8);
        int i = bArrA[10] & 255;
        com.igexin.push.d.c.a aVar2 = new com.igexin.push.d.c.a();
        aVar2.a = sA;
        aVar2.b = (byte) i;
        aVar2.f = eVar.d;
        aVar2.g = eVar.i;
        if (sA > 0) {
            byte[] bArrA2 = a(hVar, sA);
            if (eVar.i == 16) {
                bArrA2 = g.d(bArrA2, g.b(com.igexin.c.a.b.g.b(iC2)));
            } else if (eVar.i == 32) {
                if (i != 26) {
                    return null;
                }
                com.igexin.c.a.c.a.a(a, "decodeAes, encryptType = 0x20, special");
                bArrA2 = g.e(bArrA2, com.igexin.c.a.b.g.b(iC2));
            } else if (eVar.i != 0) {
                if (eVar.i == 48) {
                }
                return null;
            }
            if (eVar.h == -128) {
                bArrA2 = com.igexin.c.a.b.g.b(bArrA2);
            } else if (eVar.h != 0) {
                return null;
            }
            aVar2.a(bArrA2);
            if (!Arrays.equals(eVar.o, g.a(aVar2, iC, iC2))) {
                com.igexin.c.a.c.a.a(a, "decode signature error!!!!");
                com.igexin.c.a.c.a.a(a + "|decode signature error!!!!", new Object[0]);
                return null;
            }
        } else if (aVar2.a < 0) {
            com.igexin.c.a.c.a.a(a, "data len < 0, error");
            com.igexin.c.a.c.a.a(a + "|data len < 0, error", new Object[0]);
            return null;
        }
        return aVar2;
    }

    private static byte[] a(h hVar, int i) throws IOException {
        byte[] bArr = new byte[i];
        hVar.a(bArr);
        return bArr;
    }

    private static int b(h hVar, int i) throws IOException {
        byte[] bArrA = a(hVar, i);
        if (i == 1) {
            return bArrA[0] & 255;
        }
        if (i == 2) {
            return com.igexin.c.a.b.g.a(bArrA, 0);
        }
        if (i == 4) {
            return com.igexin.c.a.b.g.c(bArrA, 0);
        }
        return 0;
    }

    private Object b(h hVar, e eVar) throws Exception {
        byte b2;
        if (eVar.i == 48 && (b2 = (byte) b(hVar, 1)) > 0) {
            this.g = a(hVar, b2);
        }
        if (eVar.f == 0) {
            com.igexin.push.d.c.a aVar = new com.igexin.push.d.c.a();
            aVar.f = eVar.d;
            aVar.b = (byte) 0;
            return aVar;
        }
        byte[] bArrA = a(hVar, 3);
        short sA = com.igexin.c.a.b.g.a(bArrA, 0);
        int i = bArrA[2] & 255;
        com.igexin.push.d.c.a aVar2 = new com.igexin.push.d.c.a();
        aVar2.a = sA;
        aVar2.b = (byte) i;
        aVar2.f = eVar.d;
        if (i != 26) {
            com.igexin.c.a.c.a.a(a, "decodeRC4, cmd != MsgFormatedReceive.COMMAND, return");
            return null;
        }
        if (aVar2.a > 0) {
            byte[] bArrA2 = a(hVar, sA);
            if (eVar.i == 48) {
                bArrA2 = com.igexin.c.a.a.a.a(bArrA2, this.g == null ? com.igexin.c.a.b.e.a().f : com.igexin.c.b.a.a(this.g));
            }
            if (eVar.h == -128) {
                bArrA2 = com.igexin.c.a.b.g.b(bArrA2);
            } else if (eVar.h != 0) {
                return null;
            }
            aVar2.a(bArrA2);
        }
        return aVar2;
    }

    private static short b(h hVar) throws IOException {
        return (short) b(hVar, 2);
    }

    private static int c(h hVar) throws IOException {
        return b(hVar, 4);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0138  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01be  */
    @Override // com.igexin.c.a.b.d
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object a(Object obj) throws Exception {
        int iA;
        String str;
        byte[] bArr = null;
        if (obj instanceof com.igexin.push.d.c.a) {
            com.igexin.push.d.c.a aVar = (com.igexin.push.d.c.a) obj;
            e eVar = new e();
            eVar.b = e.a;
            eVar.a(aVar.c);
            eVar.f = aVar.b > 0 ? 1 : 0;
            eVar.d = 7;
            eVar.c = 11;
            eVar.g = aVar.d;
            eVar.c += g.c().length;
            if (aVar.a > 0) {
                eVar.q = g.e();
                eVar.r = (int) (System.currentTimeMillis() / 1000);
                eVar.o = g.a(aVar, eVar.q, eVar.r);
                eVar.p = eVar.o.length;
            } else {
                if (eVar.i == 0) {
                    eVar.p = 0;
                }
                com.igexin.c.a.b.e.c();
                if (aVar.b > 0 && aVar.a > 0) {
                    if ((eVar.h & 192) == 128) {
                        aVar.a(com.igexin.c.a.b.g.a(aVar.e));
                    }
                    if ((eVar.i & 48) != 16) {
                        byte[] bArrB = g.b(com.igexin.c.a.b.g.b(eVar.r));
                        if ((eVar.g & 16) != 16) {
                            aVar.a(g.c(aVar.e, bArrB));
                        }
                    } else if ((eVar.i & 48) != 0) {
                        if ((eVar.i & 48) == 48) {
                            com.igexin.c.a.c.a.a(a, "encry type = 0x30 not support");
                            str = a + "|encry type = 0x30 not support";
                        } else if ((eVar.i & 48) == 32) {
                            com.igexin.c.a.c.a.a(a, "encry type = 0x20 reserved");
                            com.igexin.c.a.c.a.a(a + "|encry type = 0x20 reserved", new Object[0]);
                        } else {
                            com.igexin.c.a.c.a.a(a, "encry type = " + (eVar.i & 48) + " not support");
                            str = a + "|encry type = " + (eVar.i & 48) + " not support";
                        }
                        com.igexin.c.a.c.a.a(str, new Object[0]);
                        return null;
                    }
                }
                bArr = new byte[eVar.c + (aVar.b <= 0 ? 11 + aVar.a : 0)];
                com.igexin.c.a.b.g.a(e.a, bArr, 0);
                bArr[4] = (byte) eVar.c;
                bArr[5] = (byte) eVar.d;
                eVar.e |= eVar.h;
                eVar.e |= eVar.i;
                eVar.e |= eVar.j;
                bArr[6] = (byte) eVar.e;
                bArr[7] = (byte) eVar.f;
                byte[] bArrC = g.c();
                bArr[8] = (byte) bArrC.length;
                int iA2 = com.igexin.c.a.b.g.a(bArrC, bArr, 9, bArrC.length) + 9;
                eVar.g |= eVar.k;
                eVar.g |= eVar.l;
                eVar.g |= eVar.m;
                eVar.g |= eVar.n;
                bArr[iA2] = (byte) eVar.g;
                int i = iA2 + 1;
                if (aVar.a <= 0) {
                    bArr[i] = (byte) eVar.p;
                    int i2 = i + 1;
                    iA = i2 + com.igexin.c.a.b.g.a(eVar.o, bArr, i2, eVar.p);
                } else {
                    bArr[i] = 0;
                    iA = i + 1;
                }
                if (aVar.b > 0) {
                    int iA3 = iA + com.igexin.c.a.b.g.a(eVar.q, bArr, iA);
                    int iA4 = iA3 + com.igexin.c.a.b.g.a(eVar.r, bArr, iA3);
                    int iB = iA4 + com.igexin.c.a.b.g.b(aVar.a, bArr, iA4);
                    bArr[iB] = aVar.b;
                    int i3 = iB + 1;
                    if (aVar.a > 0) {
                        com.igexin.c.a.b.g.a(aVar.e, bArr, i3, aVar.a);
                    }
                }
            }
            eVar.c += eVar.p;
            com.igexin.c.a.b.e.c();
            if (aVar.b > 0) {
                if ((eVar.h & 192) == 128) {
                }
                if ((eVar.i & 48) != 16) {
                }
            }
            bArr = new byte[eVar.c + (aVar.b <= 0 ? 11 + aVar.a : 0)];
            com.igexin.c.a.b.g.a(e.a, bArr, 0);
            bArr[4] = (byte) eVar.c;
            bArr[5] = (byte) eVar.d;
            eVar.e |= eVar.h;
            eVar.e |= eVar.i;
            eVar.e |= eVar.j;
            bArr[6] = (byte) eVar.e;
            bArr[7] = (byte) eVar.f;
            byte[] bArrC2 = g.c();
            bArr[8] = (byte) bArrC2.length;
            int iA22 = com.igexin.c.a.b.g.a(bArrC2, bArr, 9, bArrC2.length) + 9;
            eVar.g |= eVar.k;
            eVar.g |= eVar.l;
            eVar.g |= eVar.m;
            eVar.g |= eVar.n;
            bArr[iA22] = (byte) eVar.g;
            int i4 = iA22 + 1;
            if (aVar.a <= 0) {
            }
            if (aVar.b > 0) {
            }
        }
        return bArr;
    }

    @Override // com.igexin.c.a.b.d
    public final Object b(Object obj) throws Exception {
        com.igexin.push.d.c.a aVar;
        byte b2;
        h hVar = obj instanceof h ? (h) obj : null;
        if (hVar == null) {
            com.igexin.c.a.c.a.a(a, "syncIns is null");
            com.igexin.c.a.c.a.a(a + "|syncIns is null", new Object[0]);
            return null;
        }
        byte[] bArrA = a(hVar, 8);
        if (com.igexin.c.a.b.g.c(bArrA, 0) != 1944742139) {
            return null;
        }
        e eVar = new e();
        eVar.c = bArrA[4] & 255;
        eVar.d = bArrA[5] & 255;
        eVar.a(bArrA[6]);
        eVar.f = bArrA[7] & 255;
        if (eVar.d == 7) {
            if (eVar.i == 48) {
                com.igexin.c.a.c.a.a(a, "decodeAes, encryptType = 0x30, return");
                return null;
            }
            byte b3 = (byte) b(hVar, 1);
            if (b3 > 0) {
                a(hVar, b3);
            }
            eVar.g = (byte) b(hVar, 1);
            eVar.p = (byte) b(hVar, 1);
            if (eVar.p > 0) {
                eVar.o = a(hVar, eVar.p);
            }
            if (eVar.f != 0) {
                byte[] bArrA2 = a(hVar, 11);
                int iC = com.igexin.c.a.b.g.c(bArrA2, 0);
                if (iC <= b) {
                    b = -1;
                    throw new Exception("server packetId can't be less than previous");
                }
                b = iC;
                int iC2 = com.igexin.c.a.b.g.c(bArrA2, 4);
                short sA = com.igexin.c.a.b.g.a(bArrA2, 8);
                int i = bArrA2[10] & 255;
                com.igexin.push.d.c.a aVar2 = new com.igexin.push.d.c.a();
                aVar2.a = sA;
                aVar2.b = (byte) i;
                aVar2.f = eVar.d;
                aVar2.g = eVar.i;
                if (sA > 0) {
                    byte[] bArrA3 = a(hVar, sA);
                    if (eVar.i == 16) {
                        bArrA3 = g.d(bArrA3, g.b(com.igexin.c.a.b.g.b(iC2)));
                    } else if (eVar.i == 32) {
                        if (i != 26) {
                            return null;
                        }
                        com.igexin.c.a.c.a.a(a, "decodeAes, encryptType = 0x20, special");
                        bArrA3 = g.e(bArrA3, com.igexin.c.a.b.g.b(iC2));
                    } else if (eVar.i != 0) {
                        if (eVar.i == 48) {
                        }
                        return null;
                    }
                    if (eVar.h == -128) {
                        bArrA3 = com.igexin.c.a.b.g.b(bArrA3);
                    } else if (eVar.h != 0) {
                        return null;
                    }
                    aVar2.a(bArrA3);
                    if (!Arrays.equals(eVar.o, g.a(aVar2, iC, iC2))) {
                        com.igexin.c.a.c.a.a(a, "decode signature error!!!!");
                        com.igexin.c.a.c.a.a(a + "|decode signature error!!!!", new Object[0]);
                        return null;
                    }
                } else if (aVar2.a < 0) {
                    com.igexin.c.a.c.a.a(a, "data len < 0, error");
                    com.igexin.c.a.c.a.a(a + "|data len < 0, error", new Object[0]);
                    return null;
                }
                return aVar2;
            }
            aVar = new com.igexin.push.d.c.a();
        } else {
            if (eVar.d != 1) {
                com.igexin.c.a.c.a.a(a, "server socket resp version = " + eVar.d + ", not support!!!");
                com.igexin.c.a.c.a.a(a + "|server socket resp version = " + eVar.d + ", not support !!!", new Object[0]);
                return null;
            }
            if (eVar.i == 48 && (b2 = (byte) b(hVar, 1)) > 0) {
                this.g = a(hVar, b2);
            }
            if (eVar.f != 0) {
                byte[] bArrA4 = a(hVar, 3);
                short sA2 = com.igexin.c.a.b.g.a(bArrA4, 0);
                int i2 = bArrA4[2] & 255;
                com.igexin.push.d.c.a aVar3 = new com.igexin.push.d.c.a();
                aVar3.a = sA2;
                aVar3.b = (byte) i2;
                aVar3.f = eVar.d;
                if (i2 != 26) {
                    com.igexin.c.a.c.a.a(a, "decodeRC4, cmd != MsgFormatedReceive.COMMAND, return");
                    return null;
                }
                if (aVar3.a > 0) {
                    byte[] bArrA5 = a(hVar, sA2);
                    if (eVar.i == 48) {
                        bArrA5 = com.igexin.c.a.a.a.a(bArrA5, this.g == null ? com.igexin.c.a.b.e.a().f : com.igexin.c.b.a.a(this.g));
                    }
                    if (eVar.h == -128) {
                        bArrA5 = com.igexin.c.a.b.g.b(bArrA5);
                    } else if (eVar.h != 0) {
                        return null;
                    }
                    aVar3.a(bArrA5);
                }
                return aVar3;
            }
            aVar = new com.igexin.push.d.c.a();
        }
        aVar.f = eVar.d;
        aVar.b = (byte) 0;
        return aVar;
    }
}
