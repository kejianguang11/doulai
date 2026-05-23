package androidx.arch.core.internal;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class SafeIterableMap<K, V> implements Iterable<Map.Entry<K, V>> {
    Entry<K, V> a;
    private Entry<K, V> mEnd;
    private WeakHashMap<SupportRemove<K, V>, Boolean> mIterators = new WeakHashMap<>();
    private int mSize = 0;

    static class AscendingIterator<K, V> extends ListIterator<K, V> {
        AscendingIterator(Entry<K, V> entry, Entry<K, V> entry2) {
            super(entry, entry2);
        }

        @Override // androidx.arch.core.internal.SafeIterableMap.ListIterator
        Entry<K, V> a(Entry<K, V> entry) {
            return entry.c;
        }

        @Override // androidx.arch.core.internal.SafeIterableMap.ListIterator
        Entry<K, V> b(Entry<K, V> entry) {
            return entry.d;
        }
    }

    private static class DescendingIterator<K, V> extends ListIterator<K, V> {
        DescendingIterator(Entry<K, V> entry, Entry<K, V> entry2) {
            super(entry, entry2);
        }

        @Override // androidx.arch.core.internal.SafeIterableMap.ListIterator
        Entry<K, V> a(Entry<K, V> entry) {
            return entry.d;
        }

        @Override // androidx.arch.core.internal.SafeIterableMap.ListIterator
        Entry<K, V> b(Entry<K, V> entry) {
            return entry.c;
        }
    }

    static class Entry<K, V> implements Map.Entry<K, V> {

        @NonNull
        final K a;

        @NonNull
        final V b;
        Entry<K, V> c;
        Entry<K, V> d;

        Entry(@NonNull K k, @NonNull V v) {
            this.a = k;
            this.b = v;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            return this.a.equals(entry.a) && this.b.equals(entry.b);
        }

        @Override // java.util.Map.Entry
        @NonNull
        public K getKey() {
            return this.a;
        }

        @Override // java.util.Map.Entry
        @NonNull
        public V getValue() {
            return this.b;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            return this.b.hashCode() ^ this.a.hashCode();
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            throw new UnsupportedOperationException("An entry modification is not supported");
        }

        public String toString() {
            return this.a + "=" + this.b;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public class IteratorWithAdditions implements SupportRemove<K, V>, Iterator<Map.Entry<K, V>> {
        private boolean mBeforeStart = true;
        private Entry<K, V> mCurrent;

        IteratorWithAdditions() {
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.mBeforeStart ? SafeIterableMap.this.a != null : (this.mCurrent == null || this.mCurrent.c == null) ? false : true;
        }

        @Override // java.util.Iterator
        public Map.Entry<K, V> next() {
            Entry<K, V> entry;
            if (this.mBeforeStart) {
                this.mBeforeStart = false;
                entry = SafeIterableMap.this.a;
            } else {
                entry = this.mCurrent != null ? this.mCurrent.c : null;
            }
            this.mCurrent = entry;
            return this.mCurrent;
        }

        @Override // androidx.arch.core.internal.SafeIterableMap.SupportRemove
        public void supportRemove(@NonNull Entry<K, V> entry) {
            if (entry == this.mCurrent) {
                this.mCurrent = this.mCurrent.d;
                this.mBeforeStart = this.mCurrent == null;
            }
        }
    }

    private static abstract class ListIterator<K, V> implements SupportRemove<K, V>, Iterator<Map.Entry<K, V>> {
        Entry<K, V> a;
        Entry<K, V> b;

        ListIterator(Entry<K, V> entry, Entry<K, V> entry2) {
            this.a = entry2;
            this.b = entry;
        }

        private Entry<K, V> nextNode() {
            if (this.b == this.a || this.a == null) {
                return null;
            }
            return a(this.b);
        }

        abstract Entry<K, V> a(Entry<K, V> entry);

        abstract Entry<K, V> b(Entry<K, V> entry);

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.b != null;
        }

        @Override // java.util.Iterator
        public Map.Entry<K, V> next() {
            Entry<K, V> entry = this.b;
            this.b = nextNode();
            return entry;
        }

        @Override // androidx.arch.core.internal.SafeIterableMap.SupportRemove
        public void supportRemove(@NonNull Entry<K, V> entry) {
            if (this.a == entry && entry == this.b) {
                this.b = null;
                this.a = null;
            }
            if (this.a == entry) {
                this.a = b(this.a);
            }
            if (this.b == entry) {
                this.b = nextNode();
            }
        }
    }

    interface SupportRemove<K, V> {
        void supportRemove(@NonNull Entry<K, V> entry);
    }

    protected Entry<K, V> a(K k) {
        Entry<K, V> entry = this.a;
        while (entry != null && !entry.a.equals(k)) {
            entry = entry.c;
        }
        return entry;
    }

    protected Entry<K, V> a(@NonNull K k, @NonNull V v) {
        Entry<K, V> entry = new Entry<>(k, v);
        this.mSize++;
        if (this.mEnd == null) {
            this.a = entry;
            this.mEnd = this.a;
            return entry;
        }
        this.mEnd.c = entry;
        entry.d = this.mEnd;
        this.mEnd = entry;
        return entry;
    }

    public Iterator<Map.Entry<K, V>> descendingIterator() {
        DescendingIterator descendingIterator = new DescendingIterator(this.mEnd, this.a);
        this.mIterators.put(descendingIterator, false);
        return descendingIterator;
    }

    public Map.Entry<K, V> eldest() {
        return this.a;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SafeIterableMap)) {
            return false;
        }
        SafeIterableMap safeIterableMap = (SafeIterableMap) obj;
        if (size() != safeIterableMap.size()) {
            return false;
        }
        Iterator<Map.Entry<K, V>> it = iterator();
        Iterator<Map.Entry<K, V>> it2 = safeIterableMap.iterator();
        while (it.hasNext() && it2.hasNext()) {
            Map.Entry<K, V> next = it.next();
            Map.Entry<K, V> next2 = it2.next();
            if ((next == null && next2 != null) || (next != null && !next.equals(next2))) {
                return false;
            }
        }
        return (it.hasNext() || it2.hasNext()) ? false : true;
    }

    public int hashCode() {
        Iterator<Map.Entry<K, V>> it = iterator();
        int iHashCode = 0;
        while (it.hasNext()) {
            iHashCode += it.next().hashCode();
        }
        return iHashCode;
    }

    @Override // java.lang.Iterable
    @NonNull
    public Iterator<Map.Entry<K, V>> iterator() {
        AscendingIterator ascendingIterator = new AscendingIterator(this.a, this.mEnd);
        this.mIterators.put(ascendingIterator, false);
        return ascendingIterator;
    }

    public SafeIterableMap<K, V>.IteratorWithAdditions iteratorWithAdditions() {
        SafeIterableMap<K, V>.IteratorWithAdditions iteratorWithAdditions = new IteratorWithAdditions();
        this.mIterators.put(iteratorWithAdditions, false);
        return iteratorWithAdditions;
    }

    public Map.Entry<K, V> newest() {
        return this.mEnd;
    }

    public V putIfAbsent(@NonNull K k, @NonNull V v) {
        Entry<K, V> entryA = a(k);
        if (entryA != null) {
            return entryA.b;
        }
        a(k, v);
        return null;
    }

    public V remove(@NonNull K k) {
        Entry<K, V> entryA = a(k);
        if (entryA == null) {
            return null;
        }
        this.mSize--;
        if (!this.mIterators.isEmpty()) {
            Iterator<SupportRemove<K, V>> it = this.mIterators.keySet().iterator();
            while (it.hasNext()) {
                it.next().supportRemove(entryA);
            }
        }
        if (entryA.d != null) {
            entryA.d.c = entryA.c;
        } else {
            this.a = entryA.c;
        }
        if (entryA.c != null) {
            entryA.c.d = entryA.d;
        } else {
            this.mEnd = entryA.d;
        }
        entryA.c = null;
        entryA.d = null;
        return entryA.b;
    }

    public int size() {
        return this.mSize;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<Map.Entry<K, V>> it = iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
