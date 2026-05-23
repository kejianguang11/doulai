package androidx.collection;

import androidx.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
abstract class MapCollections<K, V> {

    @Nullable
    MapCollections<K, V>.EntrySet b;

    @Nullable
    MapCollections<K, V>.KeySet c;

    @Nullable
    MapCollections<K, V>.ValuesCollection d;

    final class ArrayIterator<T> implements Iterator<T> {
        final int a;
        int b;
        int c;
        boolean d = false;

        ArrayIterator(int i) {
            this.a = i;
            this.b = MapCollections.this.a();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.c < this.b;
        }

        @Override // java.util.Iterator
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T t = (T) MapCollections.this.a(this.c, this.a);
            this.c++;
            this.d = true;
            return t;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (!this.d) {
                throw new IllegalStateException();
            }
            this.c--;
            this.b--;
            this.d = false;
            MapCollections.this.a(this.c);
        }
    }

    final class EntrySet implements Set<Map.Entry<K, V>> {
        EntrySet() {
        }

        @Override // java.util.Set, java.util.Collection
        public boolean add(Map.Entry<K, V> entry) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(Collection<? extends Map.Entry<K, V>> collection) {
            int iA = MapCollections.this.a();
            for (Map.Entry<K, V> entry : collection) {
                MapCollections.this.a(entry.getKey(), entry.getValue());
            }
            return iA != MapCollections.this.a();
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            MapCollections.this.c();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            int iA = MapCollections.this.a(entry.getKey());
            if (iA < 0) {
                return false;
            }
            return ContainerHelpers.equal(MapCollections.this.a(iA, 1), entry.getValue());
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean equals(Object obj) {
            return MapCollections.equalsSetHelper(this, obj);
        }

        @Override // java.util.Set, java.util.Collection
        public int hashCode() {
            int iHashCode = 0;
            for (int iA = MapCollections.this.a() - 1; iA >= 0; iA--) {
                Object objA = MapCollections.this.a(iA, 0);
                Object objA2 = MapCollections.this.a(iA, 1);
                iHashCode += (objA == null ? 0 : objA.hashCode()) ^ (objA2 == null ? 0 : objA2.hashCode());
            }
            return iHashCode;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return MapCollections.this.a() == 0;
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<Map.Entry<K, V>> iterator() {
            return new MapIterator();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return MapCollections.this.a();
        }

        @Override // java.util.Set, java.util.Collection
        public Object[] toArray() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            throw new UnsupportedOperationException();
        }
    }

    final class KeySet implements Set<K> {
        KeySet() {
        }

        @Override // java.util.Set, java.util.Collection
        public boolean add(K k) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            MapCollections.this.c();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean contains(Object obj) {
            return MapCollections.this.a(obj) >= 0;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            return MapCollections.containsAllHelper(MapCollections.this.b(), collection);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean equals(Object obj) {
            return MapCollections.equalsSetHelper(this, obj);
        }

        @Override // java.util.Set, java.util.Collection
        public int hashCode() {
            int iHashCode = 0;
            for (int iA = MapCollections.this.a() - 1; iA >= 0; iA--) {
                Object objA = MapCollections.this.a(iA, 0);
                iHashCode += objA == null ? 0 : objA.hashCode();
            }
            return iHashCode;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return MapCollections.this.a() == 0;
        }

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<K> iterator() {
            return new ArrayIterator(0);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean remove(Object obj) {
            int iA = MapCollections.this.a(obj);
            if (iA < 0) {
                return false;
            }
            MapCollections.this.a(iA);
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            return MapCollections.removeAllHelper(MapCollections.this.b(), collection);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            return MapCollections.retainAllHelper(MapCollections.this.b(), collection);
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return MapCollections.this.a();
        }

        @Override // java.util.Set, java.util.Collection
        public Object[] toArray() {
            return MapCollections.this.toArrayHelper(0);
        }

        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) MapCollections.this.toArrayHelper(tArr, 0);
        }
    }

    final class MapIterator implements Iterator<Map.Entry<K, V>>, Map.Entry<K, V> {
        int a;
        boolean c = false;
        int b = -1;

        MapIterator() {
            this.a = MapCollections.this.a() - 1;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!this.c) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return ContainerHelpers.equal(entry.getKey(), MapCollections.this.a(this.b, 0)) && ContainerHelpers.equal(entry.getValue(), MapCollections.this.a(this.b, 1));
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            if (this.c) {
                return (K) MapCollections.this.a(this.b, 0);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            if (this.c) {
                return (V) MapCollections.this.a(this.b, 1);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.b < this.a;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            if (!this.c) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            }
            Object objA = MapCollections.this.a(this.b, 0);
            Object objA2 = MapCollections.this.a(this.b, 1);
            return (objA == null ? 0 : objA.hashCode()) ^ (objA2 != null ? objA2.hashCode() : 0);
        }

        @Override // java.util.Iterator
        public Map.Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.b++;
            this.c = true;
            return this;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (!this.c) {
                throw new IllegalStateException();
            }
            MapCollections.this.a(this.b);
            this.b--;
            this.a--;
            this.c = false;
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            if (this.c) {
                return (V) MapCollections.this.a(this.b, v);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public String toString() {
            return getKey() + "=" + getValue();
        }
    }

    final class ValuesCollection implements Collection<V> {
        ValuesCollection() {
        }

        @Override // java.util.Collection
        public boolean add(V v) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public boolean addAll(Collection<? extends V> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public void clear() {
            MapCollections.this.c();
        }

        @Override // java.util.Collection
        public boolean contains(Object obj) {
            return MapCollections.this.b(obj) >= 0;
        }

        @Override // java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return MapCollections.this.a() == 0;
        }

        @Override // java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return new ArrayIterator(1);
        }

        @Override // java.util.Collection
        public boolean remove(Object obj) {
            int iB = MapCollections.this.b(obj);
            if (iB < 0) {
                return false;
            }
            MapCollections.this.a(iB);
            return true;
        }

        @Override // java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            int iA = MapCollections.this.a();
            int i = 0;
            boolean z = false;
            while (i < iA) {
                if (collection.contains(MapCollections.this.a(i, 1))) {
                    MapCollections.this.a(i);
                    i--;
                    iA--;
                    z = true;
                }
                i++;
            }
            return z;
        }

        @Override // java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            int iA = MapCollections.this.a();
            int i = 0;
            boolean z = false;
            while (i < iA) {
                if (!collection.contains(MapCollections.this.a(i, 1))) {
                    MapCollections.this.a(i);
                    i--;
                    iA--;
                    z = true;
                }
                i++;
            }
            return z;
        }

        @Override // java.util.Collection
        public int size() {
            return MapCollections.this.a();
        }

        @Override // java.util.Collection
        public Object[] toArray() {
            return MapCollections.this.toArrayHelper(1);
        }

        @Override // java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) MapCollections.this.toArrayHelper(tArr, 1);
        }
    }

    MapCollections() {
    }

    public static <K, V> boolean containsAllHelper(Map<K, V> map, Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!map.containsKey(it.next())) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean equalsSetHelper(Set<T> set, Object obj) {
        if (set == obj) {
            return true;
        }
        if (obj instanceof Set) {
            Set set2 = (Set) obj;
            try {
                if (set.size() == set2.size()) {
                    if (set.containsAll(set2)) {
                        return true;
                    }
                }
                return false;
            } catch (ClassCastException | NullPointerException unused) {
            }
        }
        return false;
    }

    public static <K, V> boolean removeAllHelper(Map<K, V> map, Collection<?> collection) {
        int size = map.size();
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            map.remove(it.next());
        }
        return size != map.size();
    }

    public static <K, V> boolean retainAllHelper(Map<K, V> map, Collection<?> collection) {
        int size = map.size();
        Iterator<K> it = map.keySet().iterator();
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                it.remove();
            }
        }
        return size != map.size();
    }

    protected abstract int a();

    protected abstract int a(Object obj);

    protected abstract Object a(int i, int i2);

    protected abstract V a(int i, V v);

    protected abstract void a(int i);

    protected abstract void a(K k, V v);

    protected abstract int b(Object obj);

    protected abstract Map<K, V> b();

    protected abstract void c();

    public Set<Map.Entry<K, V>> getEntrySet() {
        if (this.b == null) {
            this.b = new EntrySet();
        }
        return this.b;
    }

    public Set<K> getKeySet() {
        if (this.c == null) {
            this.c = new KeySet();
        }
        return this.c;
    }

    public Collection<V> getValues() {
        if (this.d == null) {
            this.d = new ValuesCollection();
        }
        return this.d;
    }

    public Object[] toArrayHelper(int i) {
        int iA = a();
        Object[] objArr = new Object[iA];
        for (int i2 = 0; i2 < iA; i2++) {
            objArr[i2] = a(i2, i);
        }
        return objArr;
    }

    public <T> T[] toArrayHelper(T[] tArr, int i) {
        int iA = a();
        if (tArr.length < iA) {
            tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), iA));
        }
        for (int i2 = 0; i2 < iA; i2++) {
            tArr[i2] = a(i2, i);
        }
        if (tArr.length > iA) {
            tArr[iA] = null;
        }
        return tArr;
    }
}
