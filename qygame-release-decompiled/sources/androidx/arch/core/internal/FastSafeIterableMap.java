package androidx.arch.core.internal;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.arch.core.internal.SafeIterableMap;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class FastSafeIterableMap<K, V> extends SafeIterableMap<K, V> {
    private HashMap<K, SafeIterableMap.Entry<K, V>> mHashMap = new HashMap<>();

    @Override // androidx.arch.core.internal.SafeIterableMap
    protected SafeIterableMap.Entry<K, V> a(K k) {
        return this.mHashMap.get(k);
    }

    public Map.Entry<K, V> ceil(K k) {
        if (contains(k)) {
            return this.mHashMap.get(k).d;
        }
        return null;
    }

    public boolean contains(K k) {
        return this.mHashMap.containsKey(k);
    }

    @Override // androidx.arch.core.internal.SafeIterableMap
    public V putIfAbsent(@NonNull K k, @NonNull V v) {
        SafeIterableMap.Entry<K, V> entryA = a(k);
        if (entryA != null) {
            return entryA.b;
        }
        this.mHashMap.put(k, a(k, v));
        return null;
    }

    @Override // androidx.arch.core.internal.SafeIterableMap
    public V remove(@NonNull K k) {
        V v = (V) super.remove(k);
        this.mHashMap.remove(k);
        return v;
    }
}
