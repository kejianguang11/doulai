package androidx.collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class LruCache<K, V> {
    private int createCount;
    private int evictionCount;
    private int hitCount;
    private final LinkedHashMap<K, V> map;
    private int maxSize;
    private int missCount;
    private int putCount;
    private int size;

    public LruCache(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = i;
        this.map = new LinkedHashMap<>(0, 0.75f, true);
    }

    private int safeSizeOf(K k, V v) {
        int iA = a(k, v);
        if (iA >= 0) {
            return iA;
        }
        throw new IllegalStateException("Negative size: " + k + "=" + v);
    }

    protected int a(@NonNull K k, @NonNull V v) {
        return 1;
    }

    @Nullable
    protected V a(@NonNull K k) {
        return null;
    }

    protected void a(boolean z, @NonNull K k, @NonNull V v, @Nullable V v2) {
    }

    public final synchronized int createCount() {
        return this.createCount;
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final synchronized int evictionCount() {
        return this.evictionCount;
    }

    @Nullable
    public final V get(@NonNull K k) {
        V vPut;
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            V v = this.map.get(k);
            if (v != null) {
                this.hitCount++;
                return v;
            }
            this.missCount++;
            V vA = a(k);
            if (vA == null) {
                return null;
            }
            synchronized (this) {
                this.createCount++;
                vPut = this.map.put(k, vA);
                if (vPut != null) {
                    this.map.put(k, vPut);
                } else {
                    this.size += safeSizeOf(k, vA);
                }
            }
            if (vPut != null) {
                a(false, k, vA, vPut);
                return vPut;
            }
            trimToSize(this.maxSize);
            return vA;
        }
    }

    public final synchronized int hitCount() {
        return this.hitCount;
    }

    public final synchronized int maxSize() {
        return this.maxSize;
    }

    public final synchronized int missCount() {
        return this.missCount;
    }

    @Nullable
    public final V put(@NonNull K k, @NonNull V v) {
        V vPut;
        if (k == null || v == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            this.putCount++;
            this.size += safeSizeOf(k, v);
            vPut = this.map.put(k, v);
            if (vPut != null) {
                this.size -= safeSizeOf(k, vPut);
            }
        }
        if (vPut != null) {
            a(false, k, vPut, v);
        }
        trimToSize(this.maxSize);
        return vPut;
    }

    public final synchronized int putCount() {
        return this.putCount;
    }

    @Nullable
    public final V remove(@NonNull K k) {
        V vRemove;
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            vRemove = this.map.remove(k);
            if (vRemove != null) {
                this.size -= safeSizeOf(k, vRemove);
            }
        }
        if (vRemove != null) {
            a(false, k, vRemove, null);
        }
        return vRemove;
    }

    public void resize(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        synchronized (this) {
            this.maxSize = i;
        }
        trimToSize(i);
    }

    public final synchronized int size() {
        return this.size;
    }

    public final synchronized Map<K, V> snapshot() {
        return new LinkedHashMap(this.map);
    }

    public final synchronized String toString() {
        int i;
        i = this.hitCount + this.missCount;
        return String.format(Locale.US, "LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", Integer.valueOf(this.maxSize), Integer.valueOf(this.hitCount), Integer.valueOf(this.missCount), Integer.valueOf(i != 0 ? (this.hitCount * 100) / i : 0));
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0070, code lost:
    
        throw new java.lang.IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void trimToSize(int i) {
        K key;
        V value;
        while (true) {
            synchronized (this) {
                if (this.size >= 0 && (!this.map.isEmpty() || this.size == 0)) {
                    if (this.size <= i || this.map.isEmpty()) {
                        break;
                    }
                    Map.Entry<K, V> next = this.map.entrySet().iterator().next();
                    key = next.getKey();
                    value = next.getValue();
                    this.map.remove(key);
                    this.size -= safeSizeOf(key, value);
                    this.evictionCount++;
                } else {
                    break;
                }
            }
            a(true, key, value, null);
        }
    }
}
