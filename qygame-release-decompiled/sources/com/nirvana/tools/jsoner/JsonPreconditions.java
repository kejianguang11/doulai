package com.nirvana.tools.jsoner;

/* JADX INFO: loaded from: classes.dex */
public final class JsonPreconditions {
    private JsonPreconditions() {
        throw new UnsupportedOperationException();
    }

    public static void checkArgument(boolean z) {
        if (!z) {
            throw new IllegalArgumentException();
        }
    }

    public static <T> T checkNotNull(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }
}
