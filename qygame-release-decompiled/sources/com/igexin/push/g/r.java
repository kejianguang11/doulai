package com.igexin.push.g;

import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public final class r {
    private static final char[] a = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz`~!@#$%^&*()-_=+[{}];:'/?.>,<".toCharArray();

    public static String a() {
        StringBuilder sb = new StringBuilder(16);
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            sb.append(a[random.nextInt(a.length)]);
        }
        return sb.toString();
    }

    public static String b() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(random.nextInt("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length())));
        }
        return sb.toString();
    }

    private static long c() {
        return ((long) (new Random().nextInt(6) + 2)) * 60000;
    }
}
