package com.ta.utdid2.a.a;

import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public class f {
    private static final Pattern a = Pattern.compile("([\t\r\n])+");

    public static int hashCode(String str) {
        if (str.length() <= 0) {
            return 0;
        }
        int i = 0;
        for (char c : str.toCharArray()) {
            i = (i * 31) + c;
        }
        return i;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() <= 0;
    }
}
