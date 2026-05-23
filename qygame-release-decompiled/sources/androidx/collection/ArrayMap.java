package androidx.collection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class ArrayMap<K, V> extends SimpleArrayMap<K, V> implements Map<K, V> {

    @Nullable
    MapCollections<K, V> a;

    public ArrayMap() {
    }

    public ArrayMap(int i) {
        super(i);
    }

    public ArrayMap(SimpleArrayMap simpleArrayMap) {
        super(simpleArrayMap);
    }

    private MapCollections<K, V> getCollection() {
        if (this.a == null) {
            this.a = new MapCollections<K, V>() { // from class: androidx.collection.ArrayMap.1
                @Override // androidx.collection.MapCollections
                protected int a() {
                    return ArrayMap.this.h;
                }

                @Override // androidx.collection.MapCollections
                protected int a(Object obj) {
                    return ArrayMap.this.indexOfKey(obj);
                }

                @Override // androidx.collection.MapCollections
                protected Object a(int i, int i2) {
                    return ArrayMap.this.g[(i << 1) + i2];
                }

                @Override // androidx.collection.MapCollections
                protected V a(int i, V v) {
                    return ArrayMap.this.setValueAt(i, v);
                }

                @Override // androidx.collection.MapCollections
                protected void a(int i) {
                    ArrayMap.this.removeAt(i);
                }

                @Override // androidx.collection.MapCollections
                protected void a(K k, V v) {
                    ArrayMap.this.put(k, v);
                }

                @Override // androidx.collection.MapCollections
                protected int b(Object obj) {
                    return ArrayMap.this.a(obj);
                }

                @Override // androidx.collection.MapCollections
                protected Map<K, V> b() {
                    return ArrayMap.this;
                }

                @Override // androidx.collection.MapCollections
                protected void c() {
                    ArrayMap.this.clear();
                }
            };
        }
        return this.a;
    }

    public boolean containsAll(@NonNull Collection<?> collection) {
        return MapCollections.containsAllHelper(this, collection);
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        return getCollection().getEntrySet();
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        return getCollection().getKeySet();
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        ensureCapacity(this.h + map.size());
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public boolean removeAll(@NonNull Collection<?> collection) {
        return MapCollections.removeAllHelper(this, collection);
    }

    public boolean retainAll(@NonNull Collection<?> collection) {
        return MapCollections.retainAllHelper(this, collection);
    }

    @Override // java.util.Map
    public Collection<V> values() {
        return getCollection().getValues();
    }
}
